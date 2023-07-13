package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import hexlet.code.DiffTree;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Json {

    private static final String SYMBOL_ADDED = "+";
    private static final String SYMBOL_DELETED = "-";

    public static String json(List<Map<String, Object>> diff) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> mapOfDiffs = getMapOfDiffs(diff);
        return mapper.writeValueAsString(mapOfDiffs);
    }

    public static Map<String, String> getMapOfDiffs(List<Map<String, Object>> diff) throws IOException {

        Map<String, String> mapOfDiffs = new LinkedHashMap<>();

        for (Map<String, Object> map: diff) {

            var status = map.get(DiffTree.KEY_STATUS).toString();
            var key = map.get(DiffTree.KEY_NAME).toString();
            var value1 = map.get(DiffTree.KEY_VALUE1);
            var value2 = map.get(DiffTree.KEY_VALUE2);

            switch (status) {
                case DiffTree.STATUS_NOT_CHANGED -> {
                    var value = toStringValue(value1);
                    mapOfDiffs.put(key, value);
                }
                case DiffTree.STATUS_CHANGED -> {
                    var deletedKey = SYMBOL_DELETED + " " + key;
                    var deletedValue = toStringValue(value1);
                    mapOfDiffs.put(deletedKey, deletedValue);

                    var addedKey = SYMBOL_ADDED + " " + key;
                    var addedValue = toStringValue(value2);
                    mapOfDiffs.put(addedKey, addedValue);
                }
                case DiffTree.STATUS_DELETED -> {
                    var deletedKey = SYMBOL_DELETED + " " + key;
                    var deletedValue = toStringValue(value1);
                    mapOfDiffs.put(deletedKey, deletedValue);
                }
                case DiffTree.STATUS_ADDED -> {
                    var addedKey = SYMBOL_ADDED + " " + key;
                    var addedValue = toStringValue(value1);
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
