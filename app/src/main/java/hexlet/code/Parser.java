package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;

import java.util.Map;

public class Parser {

    // Supported extensions
    public static final String JSON = "json";
    public static final String YAML = "yaml";
    public static final String YML = "yml";

    // Return parsed file
    public static Map<String, Object> parse(String content, String extension) throws IOException {
        if (extension.equals(JSON)) {
            return parseJson(content);
        } else if (extension.endsWith(YML) || extension.endsWith(YAML)) {
            return parseYaml(content);
        } else {
            throw new IOException("Unsupported extension");
        }
    }

    // Returns Map from specified JSON file
    public static Map<String, Object> parseJson(String content) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    // Returns Map from specified YAML file
    public static Map<String, Object> parseYaml(String content) throws IOException {

        ObjectMapper mapper = new YAMLMapper();
        return mapper.readValue(content, Map.class);
    }
}
