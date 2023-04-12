import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  idea
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.vegura"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

apply(plugin = "java")
apply(plugin = "idea")
apply(plugin = "application")

val vertxVersion = "4.4.0"
val reactivePgClientVersion = "0.11.4"
val scramClientVersion = "2.1"
val awsVersion = "1.12.435"
val logBackVersion = "1.2.9"

val lombokVersion = "1.18.26"

val junitJupiterVersion = "5.9.1"

val mainVerticleName = "me.vegura.resource.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

val generatedSrcPath = "${projectDir}/src/main/generated"
val generatedSrcDir = file(generatedSrcPath)

idea {
  module {
    generatedSourceDirs.add(generatedSrcDir)
    sourceDirs.add(generatedSrcDir)
  }
}

application {
  mainClass.set(launcherClassName)
}

java.sourceSets["main"].java {
  srcDir(generatedSrcDir)
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
//  implementation("io.reactiverse:reactive-pg-client:$reactivePgClientVersion")
  implementation("io.vertx:vertx-pg-client:4.4.1")

  implementation("io.vertx:vertx-core:$vertxVersion")
  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("com.ongres.scram:client:$scramClientVersion")
  implementation("com.amazonaws:aws-java-sdk-s3:$awsVersion")
  implementation("ch.qos.logback:logback-classic:$logBackVersion")
  implementation("io.vertx:vertx-config:$vertxVersion")
  implementation("io.vertx:vertx-codegen:$vertxVersion")
  implementation("io.vertx:vertx-rx-java2:$vertxVersion")
  implementation("io.vertx:vertx-rx-java2-gen:$vertxVersion")
  implementation("io.vertx:vertx-web-validation:$vertxVersion")
  implementation("io.vertx:vertx-service-proxy:$vertxVersion")

  compileOnly("org.projectlombok:lombok:$lombokVersion")

  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")

  annotationProcessor("org.projectlombok:lombok:$lombokVersion")
  annotationProcessor("io.vertx:vertx-codegen:$vertxVersion")
  annotationProcessor("io.vertx:vertx-service-proxy:$vertxVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to mainVerticleName))
  }
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}

tasks.withType<JavaExec> {
  args = listOf("run", mainVerticleName, "--redeploy=$watchForChange", "--launcher-class=$launcherClassName", "--on-redeploy=$doOnChange")
}

tasks.create<JavaExec>("runCustom") {
  main = project.properties.getOrDefault("mainClass", "me.vegura.resource.MainVerticle") as String
  classpath = sourceSets["main"].runtimeClasspath
  systemProperties["vertx.logger-delegate-factory-class-name"] = "io.vertx.core.logging.SLF4JLogDelegateFactory"
}

tasks.register<JavaCompile>("generateServiceProxy") {
  group = "build"
  description = "Generates the Vertx service proxies"
  source = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).java
  destinationDir = generatedSrcDir
  destinationDirectory.file(generatedSrcPath)
  classpath = configurations.compileClasspath.get()
  options.annotationProcessorPath = configurations.compileClasspath.get()
  options.compilerArgs = listOf(
    "-proc:only",
    "-processor", "io.vertx.codegen.CodeGenProcessor", // vertx processor here
    "-Acodegen.output=${generatedSrcDir.absolutePath}"
  )
  idea.module.generatedSourceDirs.add(generatedSrcDir)
  idea.module.sourceDirs.add(generatedSrcDir)
}

tasks.compileJava {
  dependsOn(tasks.named("generateServiceProxy"))
  options.compilerArgs.add("-Acodetrans.output=${projectDir}/src/main")
}

tasks.clean {
  delete.add(generatedSrcDir)
}

