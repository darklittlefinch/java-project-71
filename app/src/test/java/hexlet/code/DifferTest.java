package hexlet.code;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DifferTest {

    private static final String FILEPATH_1 = "src/test/resources/files/file1.json";
    private static final String FILEPATH_2 = "src/test/resources/files/file2.json";
    private static final String FILEPATH_3 = "src/test/resources/files/file1.yml";
    private static final String FILEPATH_4 = "src/test/resources/files/file2.yml";
    private static final String WRONG_PATH = "123";

    @Test
    public void testGenerateJson() throws IOException {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual1 = Differ.generate(FILEPATH_1, FILEPATH_2);
        assertThat(actual1).isEqualTo(expected);
    }

    @Test
    public void testGenerateYml() throws IOException {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual2 = Differ.generate(FILEPATH_3, FILEPATH_4);
        assertThat(actual2).isEqualTo(expected);
    }

    @Test
    public void testGenerateWrongPath() {
        var thrown = catchThrowable(
                () -> Differ.generate(WRONG_PATH, WRONG_PATH)
        );

        assertThat(thrown).isInstanceOf(IOException.class);
    }
}
