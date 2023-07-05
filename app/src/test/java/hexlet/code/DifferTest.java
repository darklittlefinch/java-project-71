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

        String actual = Differ.generate(FILEPATH_1, FILEPATH_2, Formatter.FORMAT_STYLISH);
        assertThat(actual).isEqualTo(expected);
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

        String actual = Differ.generate(FILEPATH_3, FILEPATH_4, Formatter.FORMAT_STYLISH);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateDefaultFormat() throws IOException {
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

        String actual = Differ.generate(FILEPATH_1, FILEPATH_2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGeneratePlainFormat() throws IOException {
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        String actual = Differ.generate(FILEPATH_1, FILEPATH_2, Formatter.FORMAT_PLAIN);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateJsonFormat() throws IOException {

        String expected = "{\"chars1\":\"[a, b, c]\",\"- chars2\":\"[d, e, f]\",\"+ chars2\":\"false\",\"- checked\""
                + ":\"false\",\"+ checked\":\"true\",\"- default\":\"null\",\"+ default\":\"[value1, value2]\",\"- id\""
                + ":\"45\",\"+ id\":\"null\",\"- key1\":\"value1\",\"+ key2\":\"value2\",\"numbers1\":\"[1, 2, 3, 4]\","
                + "\"- numbers2\":\"[2, 3, 4, 5]\",\"+ numbers2\":\"[22, 33, 44, 55]\",\"- numbers3\":\"[3, 4, 5]\","
                + "\"+ numbers4\":\"[4, 5, 6]\",\"+ obj1\":\"{nestedKey=value, isNested=true}\",\"- setting1\":\""
                + "Some value\",\"+ setting1\":\"Another value\",\"- setting2\":\"200\",\"+ setting2\":\"300\","
                + "\"- setting3\":\"true\",\"+ setting3\":\"none\"}";

        String actual = Differ.generate(FILEPATH_1, FILEPATH_2, Formatter.FORMAT_JSON);
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
