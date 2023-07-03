package hexlet.code;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DifferTest {

    private static final String FILEPATH_1 = "src/main/resources/files/file1.json";
    private static final String FILEPATH_2 = "src/main/resources/files/file2.json";
    private static final String WRONG_PATH = "123";

    @Test
    public void testGenerate() throws Exception {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        String actual = Differ.generate(FILEPATH_1, FILEPATH_2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateWithWrongPath() {
        var thrown = catchThrowable(
                () -> Differ.generate(WRONG_PATH, WRONG_PATH)
        );

        assertThat(thrown).isInstanceOf(IOException.class);
    }
}
