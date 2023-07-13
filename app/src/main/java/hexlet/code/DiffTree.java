package hexlet.code;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class DiffTree {

    public static final String STATUS_NOT_CHANGED = "not changed";
    public static final String STATUS_CHANGED = "changed";
    public static final String STATUS_DELETED = "deleted";
    public static final String STATUS_ADDED = "added";

    public static final String KEY_STATUS = "status";
    public static final String KEY_NAME = "name";
    public static final String KEY_VALUE1 = "value1";
    public static final String KEY_VALUE2 = "value2";

    public static final String DEFAULT_MESSAGE = "This map doesn't have value with specified key";

    //Returns list of maps of differences
    public static List<Map<String, Object>> genDiffTree(Map<String, Object> map1, Map<String, Object> map2) {

        List<String> allKeysSorted = getSortedKeysFromMaps(map1, map2);
        List<Map<String, Object>> diffTree = new ArrayList<>();

        for (String key: allKeysSorted) {

            Object value1 = map1.getOrDefault(key, DEFAULT_MESSAGE);
            Object value2 = map2.getOrDefault(key, DEFAULT_MESSAGE);

            var bothMapsHaveKey = map1.containsKey(key) && map2.containsKey(key);

            Map<String, Object> map = new HashMap<>();

            if (bothMapsHaveKey && Objects.equals(value1, value2)) {
                map = getDiffMap(STATUS_NOT_CHANGED, key, value1);
            } else if (bothMapsHaveKey) {
                map = getDiffMap(STATUS_CHANGED, key, value1, value2);
            } else {
                var status = map1.containsKey(key) ? STATUS_DELETED : STATUS_ADDED;
                var value = map1.containsKey(key) ? value1 : value2;
                map = getDiffMap(status, key, value);
            }

            diffTree.add(map);
        }

        return diffTree;
    }

    // Returns one map of differences with one value
    public static Map<String, Object> getDiffMap(String status, String name, Object value1) {

        Map<String, Object> result = new HashMap<>();
        result.put(KEY_STATUS, status);
        result.put(KEY_NAME, name);

        if (value1 == null) {
            result.put(KEY_VALUE1, null);
        } else {
            result.put(KEY_VALUE1, value1);
        }

        return result;
    }

    // Returns one map of differences with two values
    public static Map<String, Object> getDiffMap(String status, String name, Object value1, Object value2) {

        Map<String, Object> result = getDiffMap(status, name, value1);

        if (value2 == null) {
            result.put(KEY_VALUE2, null);
        } else {
            result.put(KEY_VALUE2, value2);
        }

        return result;
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
