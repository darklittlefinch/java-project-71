package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Differ;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Json {

    private static final String SYMBOL_ADDED = "+";
    private static final String SYMBOL_DELETED = "-";

    public static String json(Map<String, Object> map1, Map<String, Object> map2,
                              Map<String, String> diffMap) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> mapOfDiffs = getMapOfDiffs(map1, map2, diffMap);
        return mapper.writeValueAsString(mapOfDiffs);
    }

    public static Map<String, String> getMapOfDiffs(Map<String, Object> map1, Map<String, Object> map2,
                                                 Map<String, String> diffMap) throws IOException {

        Map<String, String> mapOfDiffs = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry: diffMap.entrySet()) {
            var key = entry.getKey();
            var status = entry.getValue();

            switch (status) {
                case Differ.STATUS_NOT_CHANGED -> {
                    var value = toStringValue(map1.get(key));
                    mapOfDiffs.put(key, value);
                }
                case Differ.STATUS_CHANGED -> {
                    var deletedKey = SYMBOL_DELETED + " " + key;
                    var deletedValue = toStringValue(map1.get(key));
                    mapOfDiffs.put(deletedKey, deletedValue);

                    var addedKey = SYMBOL_ADDED + " " + key;
                    var addedValue = toStringValue(map2.get(key));
                    mapOfDiffs.put(addedKey, addedValue);
                }
                case Differ.STATUS_DELETED -> {
                    var deletedKey = SYMBOL_DELETED + " " + key;
                    var deletedValue = toStringValue(map1.get(key));
                    mapOfDiffs.put(deletedKey, deletedValue);
                }
                case Differ.STATUS_ADDED -> {
                    var addedKey = SYMBOL_ADDED + " " + key;
                    var addedValue = toStringValue(map2.get(key));
                    mapOfDiffs.put(addedKey, addedValue);
                }
                default -> throw new IOException("Unsupported status");
            }
        }

        return mapOfDiffs;
    }

    public static String toStringValue(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
