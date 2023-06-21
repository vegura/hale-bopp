import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.io.FileUtils.listFiles;

public class ResourceProcessorTests {
    private static final Logger logger = LoggerFactory.getLogger(ResourceProcessorTests.class.getName());
    private static final String KARATE_SYSTEM_PROPERTY_KEY = "karate.tags";
    private static final List<String> DEFAULT_KARATE_TAGS = List.of(
            "~@ignore",
            "~@performance"
    );

    @Test
    public void runAll() {
        String extraTags = System.getProperty(KARATE_SYSTEM_PROPERTY_KEY);
        List<String> karateTags = parseKarateTags(extraTags);
        Results testCasesResults = Runner.path("classpath:")
                .tags(karateTags)
                .parallel(3);

        generateReport(testCasesResults.getReportDir());
        Assertions.assertEquals(0, testCasesResults.getFailCount(), testCasesResults.getErrorMessages());
    }

    private void generateReport(String outputPath) {
        new ReportBuilder(listFiles(new File(outputPath), new String[]{"json"}, true)
                    .stream()
                    .map(File::getAbsolutePath).collect(Collectors.toList()),
                new Configuration(new File("build"), "resource-processor"))
                    .generateReports();
    }

    private List<String> parseKarateTags(String tagsProperties) {
        if (StringUtils.isBlank(tagsProperties)) return DEFAULT_KARATE_TAGS;
        else if (tagsProperties.contains(",")) return Arrays.stream(tagsProperties.split(",")).toList();
        else return List.of(tagsProperties);
    }
}
