package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.SortedSet;
import java.util.TreeSet;
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

    // returns parameterized map from non-parameterized
    public static Map<String, String> toParameterizedMap(Map nonParameterizedMap) {

        Map<String, String> result = new HashMap<>();

        for (Object key: nonParameterizedMap.keySet()) {
            var stringKey = key.toString();
            var stringValue = nonParameterizedMap.get(key).toString();
            result.put(stringKey, stringValue);
        }

        return result;
    }

    // Returns Map from specified JSON file
    public static Map<String, String> getJsonMap(String fileName) throws IOException {

        String file = readFile(fileName);

        ObjectMapper mapper = new ObjectMapper();
        Map result =  mapper.readValue(file, Map.class);

        return toParameterizedMap(result);
    }

    // Returns sorted List of keys from two Maps
    public static SortedSet<String> getSortedKeysFromMaps(Map<String, String> map1, Map<String, String> map2) {

        SortedSet<String> sortedKeys = new TreeSet<>();
        sortedKeys.addAll(map1.keySet());
        sortedKeys.addAll(map2.keySet());

        return sortedKeys;
    }

    // Returns String that contains differences of two maps
    public static Map<String, String> getDiffsMap(Map<String, String> map1, Map<String, String> map2) {

        SortedSet<String> allKeys = getSortedKeysFromMaps(map1, map2);
        Map<String, String> diffsMap = new LinkedHashMap<>();

        for (String key: allKeys) {
            var firstMapHasKey = map1.containsKey(key);
            var secondMapHasKey = map2.containsKey(key);
            var bothMapsHaveKey = firstMapHasKey && secondMapHasKey;

            String value1 = map1.get(key);
            String value2 = map2.get(key);

            if (bothMapsHaveKey && value1.equals(value2)) {
                var newKey = NOT_CHANGED_SYMBOL + " " + key;
                diffsMap.put(newKey, value1);

            } else if (bothMapsHaveKey) {
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
    public static String diffsToString(Map<String, String> diffsMap) {

        StringJoiner sj = new StringJoiner("\n  ", "{\n  ", "\n}");

        for (Map.Entry<String, String> entry: diffsMap.entrySet()) {
            sj.add(entry.getKey() + ": " + entry.getValue());
        }

        return sj.toString();
    }

    // Returns differences of two JSON files
    public static String generate(String fileName1, String fileName2) throws IOException {

        Map<String, String> fileContent1 = getJsonMap(fileName1);
        Map<String, String> fileContent2 = getJsonMap(fileName2);

        Map<String, String> diffsMap = getDiffsMap(fileContent1, fileContent2);
        return diffsToString(diffsMap);
    }
}
