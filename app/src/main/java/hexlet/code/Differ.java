package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringJoiner;

public class Differ {

    private static final String NOT_CHANGED_SYMBOL = " ";
    private static final String ADDED_SYMBOL = "+";
    private static final String DELETED_SYMBOL = "-";

    // Returns path of specified file
    public static Path getPath(String fileName) {
        return Paths.get(fileName).toAbsolutePath().normalize();
    }

    // Returns content of specified file
    public static String readFile(String fileName) throws IOException {
        Path filePath = getPath(fileName);
        return Files.readString(filePath);
    }

    // Returns Map from specified JSON file
    public static Map<String, Object> getJsonMap(String fileName) throws IOException {
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
    public static Map<String, Object> getDiffsMap(Map<String, Object> map1, Map<String, Object> map2) {

        List<String> allKeys = getSortedKeysFromMaps(map1, map2);

        Map<String, Object> diffsMap = new LinkedHashMap<>();

        for (String key: allKeys) {
            var firstMapHasKey = map1.containsKey(key);
            var secondMapHasKey = map2.containsKey(key);
            var bothMapsHaveKey = firstMapHasKey && secondMapHasKey;

            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            if (bothMapsHaveKey && value1.equals(value2)) {
                var newKey = NOT_CHANGED_SYMBOL + " " + key;
                diffsMap.put(newKey, value1);

            } else if (bothMapsHaveKey && !value1.equals(value2)) {
                var newKey1 = DELETED_SYMBOL + " " + key;
                var newKey2 = ADDED_SYMBOL + " " + key;
                diffsMap.put(newKey1, value1);
                diffsMap.put(newKey2, value2);

            } else {
                String symbol = firstMapHasKey ? DELETED_SYMBOL : ADDED_SYMBOL;
                var newKey = symbol + " " + key;
                var value = firstMapHasKey ? value1 : value2;
                diffsMap.put(newKey, value);
            }
        }

        return diffsMap;
    }

    // Returns String of differences
    public static String diffsToString(Map<String, Object> diffsMap) {
        StringJoiner sj = new StringJoiner("\n  ", "{\n  ", "\n}");

        for (Map.Entry<String, Object> entry: diffsMap.entrySet()) {
            sj.add(entry.getKey() + ": " + entry.getValue());
        }

        return sj.toString();
    }

    // Returns differences of two JSON files
    public static String generate(String fileName1, String fileName2) throws IOException {

        Map<String, Object> fileContent1 = getJsonMap(fileName1);
        Map<String, Object> fileContent2 = getJsonMap(fileName2);

        Map<String, Object> diffsMap = getDiffsMap(fileContent1, fileContent2);
        return diffsToString(diffsMap);
    }
}
