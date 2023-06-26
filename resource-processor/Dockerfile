FROM gradle:8.1.1-jdk17 as builder
ENV LANG=C.UTF-8 \
    TERM=xterm

RUN apt update -y && apt upgrade -y
WORKDIR /opt/resource-processor-service
COPY . .

RUN ./gradlew clean build
RUN cd build/libs && java -Djarmode=layertools -jar resource-processor-service-server.jar extract

################################################
FROM gradle:8.1.1-jdk17 as runner

ENV LANG=C.UTF-8 \
    TERM=xterm

WORKDIR /opt/resource-processor-service

ARG EXTRACTED=/opt/resource-processor-service/build/libs
COPY --from=builder ${EXTRACTED}/dependencies ./
COPY --from=builder ${EXTRACTED}/snapshot-dependencies ./
COPY --from=builder ${EXTRACTED}/spring-boot-loader ./
COPY --from=builder ${EXTRACTED}/application ./

RUN addgroup --system javagroup
RUN adduser --system --shell /bin/false --no-create-home --ingroup javagroup --disabled-login javauser
RUN chown --recursive javauser:javagroup /opt/resource-processor-service
USER javauser

EXPOSE 8089

ENTRYPOINT ["java", "-server", "-Dfile.encoding=UTF-8", "-Dspring.profiles.active=docker", "--enable-preview", "org.springframework.boot.loader.JarLauncher"]


