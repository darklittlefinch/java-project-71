package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

public class Parser {

    // Supported extensions
    private static final String JSON = ".json";
    private static final String YAML = ".yaml";
    private static final String YML = ".yml";

    // Return parsed file
    public static Map<String, Object> parse(String fileName) throws IOException {
        if (fileName.endsWith(JSON)) {
            return parseJson(fileName);
        } else if (fileName.endsWith(YML) || fileName.endsWith(YAML)) {
            return parseYaml(fileName);
        } else {
            throw new IOException("Unsupported extension");
        }
    }

    // Returns Map from specified JSON file
    public static Map<String, Object> parseJson(String fileName) throws IOException {
        String file = readFile(fileName);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, Map.class);
    }

    // Returns Map from specified YAML file
    public static Map<String, Object> parseYaml(String fileName) throws IOException {
        String file = readFile(fileName);

        ObjectMapper mapper = new YAMLMapper();
        return mapper.readValue(file, Map.class);
    }

    // Returns content of specified file
    public static String readFile(String fileName) throws IOException {
        Path filePath = getPath(fileName);
        return Files.readString(filePath);
    }

    // Returns path of specified file
    public static Path getPath(String fileName) {
        return Paths.get(fileName).toAbsolutePath().normalize();
    }
}
