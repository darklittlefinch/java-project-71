package hexlet.code;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Differ {

    public static final String STATUS_NOT_CHANGED = "not changed";
    public static final String STATUS_CHANGED = "changed";
    public static final String STATUS_DELETED = "deleted";
    public static final String STATUS_ADDED = "added";

    // Returns differences of two JSON files
    public static String generate(String fileName1, String fileName2, String format) throws IOException {

        Map<String, Object> fileContent1 = Parser.parse(fileName1);
        Map<String, Object> fileContent2 = Parser.parse(fileName2);

        Map<String, String> diffMap = genDiff(fileContent1, fileContent2);
        return Formatter.format(fileContent1, fileContent2, diffMap, format);
    }

    public static String generate(String fileName1, String fileName2) throws IOException {
        return generate(fileName1, fileName2, Formatter.FORMAT_STYLISH);
    }

    // Returns Map of differences status
    public static Map<String, String> genDiff(Map<String, Object> map1, Map<String, Object> map2) {

        List<String> allKeysSorted = getSortedKeysFromMaps(map1, map2);
        Map<String, String> diffMap = new LinkedHashMap<>();

        for (String key: allKeysSorted) {

            Object value1 = map1.getOrDefault(key, "This map doesn't have value with specified key");
            Object value2 = map2.getOrDefault(key, "This map doesn't have value with specified key");

            var bothMapsHaveKey = map1.containsKey(key) && map2.containsKey(key);

            var mapHasNull = value1 == null || value2 == null;
            var bothAreNull = value1 == null && value2 == null;

            if (mapHasNull) {
                if (bothAreNull) {
                    diffMap.put(key, STATUS_NOT_CHANGED);
                } else {
                    diffMap.put(key, STATUS_CHANGED);
                }
            } else if (bothMapsHaveKey && value1.equals(value2)) {
                diffMap.put(key, STATUS_NOT_CHANGED);
            } else if (bothMapsHaveKey) {
                diffMap.put(key, STATUS_CHANGED);
            } else {
                diffMap.put(key, map1.containsKey(key) ? STATUS_DELETED : STATUS_ADDED);
            }

        }

        return diffMap;
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
}
