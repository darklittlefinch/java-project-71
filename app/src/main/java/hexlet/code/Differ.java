package hexlet.code;

//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Differ {
//    private static final String FILEPATH_1 = "src/main/resources/files/file1.json";
//    private static final String FILEPATH_2 = "src/main/resources/files/file2.json";

    private static final String NOT_CHANGED_SYMBOL = " ";
    private static final String ADDED_SYMBOL = "+";
    private static final String DELETED_SYMBOL = "-";

    // Returns path of specified file
    public static Path getPath(String fileName) {
        return Paths.get(fileName).toAbsolutePath().normalize();
    }

    // Returns content of specified file
    public static String readFile(String fileName) throws Exception {
        Path filePath = getPath(fileName);
        return Files.readString(filePath);
    }

    // Returns Map from specified JSON file
    public static Map<String, Object> getJsonMap(String fileName) throws Exception {
        String file = readFile(fileName);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, Map.class);
    }

    // Returns sorted List of keys from two Maps
    public static List<String> getSortedKeysFromMaps(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> allKeys = new HashMap<>();
        allKeys.putAll(map1);
        allKeys.putAll(map2);

        return allKeys.keySet().stream()
                .sorted()
                .toList();
    }

    // Returns String that contains differences of two maps
    public static String getDiffs(Map<String, Object> map1, Map<String, Object> map2) {

        List<String> allKeys = getSortedKeysFromMaps(map1, map2);

        StringJoiner sj = new StringJoiner("\n  ", "{\n  ", "\n}");

        for (String key: allKeys) {
            var firstMapHasKey = map1.containsKey(key);
            var secondMapHasKey = map2.containsKey(key);
            var bothMapsHaveKey = firstMapHasKey && secondMapHasKey;

            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            if (bothMapsHaveKey && value1.equals(value2)) {
                sj.add(NOT_CHANGED_SYMBOL + " " + key + ": " + value2);
            } else if (bothMapsHaveKey && !value1.equals(value2)) {
                sj.add(DELETED_SYMBOL + " " + key + ": " + value1);
                sj.add(ADDED_SYMBOL + " " + key + ": " + value2);
            } else {
                String symbol = firstMapHasKey ? DELETED_SYMBOL : ADDED_SYMBOL;
                var value = firstMapHasKey ? value1 : value2;
                sj.add(symbol + " " + key + ": " + value);
            }
        }

        return sj.toString();
    }

    // Returns differences of two JSON files
    public static String generate(String fileName1, String fileName2) throws Exception {

        Map<String, Object> fileContent1 = getJsonMap(fileName1);
        Map<String, Object> fileContent2 = getJsonMap(fileName2);

        return getDiffs(fileContent1, fileContent2);
    }
}
