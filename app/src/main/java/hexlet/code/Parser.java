package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

public class Parser {

    // return parsed file
    public static Map<String, Object> parse(String fileName) throws IOException {
        if (fileName.endsWith(".json")) {
            return parseJson(fileName);
        } else if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
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
