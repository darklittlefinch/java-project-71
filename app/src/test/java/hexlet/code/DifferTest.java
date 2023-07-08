package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DifferTest {

    private static final String ROOT = "src/test/resources/files/";
    private static final String RESULTS = "results/";
    private static final String WRONG_PATH = "123";

    private static String fileJson1 = "file1.json";
    private static String fileJson2 = "file2.json";
    private static String fileYaml1 = "file1.yml";
    private static String fileYaml2 = "file2.yml";

    private static String stylish = "stylish";
    private static String plain = "plain";
    private static String json = "json";

    public static String getPath(String... filesAndDirs) {

        StringBuilder filepath = new StringBuilder(ROOT);
        for (String fileOrDir: filesAndDirs) {
            filepath.append(fileOrDir);
        }
        return filepath.toString();
    }

    public static String getContent(String filepath) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String lineOfFile = reader.readLine();

        var result = new StringJoiner("\n");

        while (lineOfFile != null) {
            result.add(lineOfFile);
            lineOfFile = reader.readLine();
        }

        return result.toString();
    }

    @BeforeAll
    public static void beforeAll() {

        fileJson1 = getPath(fileJson1);
        fileJson2 = getPath(fileJson2);
        fileYaml1 = getPath(fileYaml1);
        fileYaml2 = getPath(fileYaml2);

        stylish = getPath(RESULTS, stylish);
        plain = getPath(RESULTS, plain);
        json = getPath(RESULTS, json);
    }

    @Test
    public void testGenerateJson() throws IOException {

        String expected = getContent(stylish);
        String actual = Differ.generate(fileJson1, fileJson2, Formatter.FORMAT_STYLISH);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateYml() throws IOException {

        String expected = getContent(stylish);
        String actual = Differ.generate(fileYaml1, fileYaml2, Formatter.FORMAT_STYLISH);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateDefaultFormat() throws IOException {

        String expected = getContent(stylish);
        String actual = Differ.generate(fileJson1, fileJson2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGeneratePlainFormat() throws IOException {

        String expected = getContent(plain);
        String actual = Differ.generate(fileJson1, fileJson2, Formatter.FORMAT_PLAIN);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateJsonFormat() throws IOException {

        String expected = getContent(json);
        String actual = Differ.generate(fileJson1, fileJson2, Formatter.FORMAT_JSON);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateWrongPath() {

        var thrown = catchThrowable(
                () -> Differ.generate(WRONG_PATH, WRONG_PATH, Formatter.FORMAT_STYLISH)
        );

        assertThat(thrown).isInstanceOf(IOException.class);
    }
}
