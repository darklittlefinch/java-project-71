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

    private static final String FORMAT_STYLISH = "stylish";

    @Test
    public void testGenerateJson() throws IOException {
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        String actual1 = Differ.generate(FORMAT_STYLISH, FILEPATH_1, FILEPATH_2);
        assertThat(actual1).isEqualTo(expected);
    }

    @Test
    public void testGenerateYml() throws IOException {
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        String actual2 = Differ.generate(FORMAT_STYLISH, FILEPATH_3, FILEPATH_4);
        assertThat(actual2).isEqualTo(expected);
    }

    @Test
    public void testGenerateWrongPath() {
        var thrown = catchThrowable(
                () -> Differ.generate(FORMAT_STYLISH, WRONG_PATH, WRONG_PATH)
        );

        assertThat(thrown).isInstanceOf(IOException.class);
    }
}
