package hexlet.code.formatters;

import hexlet.code.Differ;

import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

public class Plain {

    // Returns differences in plain format
    public static String plain(Map<String, Object> map1, Map<String, Object> map2,
                               Map<String, String> diffMap) throws IOException {

        StringJoiner sj = new StringJoiner("\n");

        for (Map.Entry<String, String> entry: diffMap.entrySet()) {
            var key = entry.getKey();
            var status = entry.getValue();

            if (status.equals(Differ.STATUS_NOT_CHANGED)) {
                continue;
            }

            switch (status) {
                case Differ.STATUS_CHANGED -> {
                    var value1 = toPlainValue(map1.get(key));
                    var value2 = toPlainValue(map2.get(key));
                    sj.add("Property '" + key + "' was updated. From " + value1 + " to " + value2);
                }
                case Differ.STATUS_DELETED -> sj.add("Property '" + key + "' was removed");
                case Differ.STATUS_ADDED -> {
                    var value = toPlainValue(map2.get(key));
                    sj.add("Property '" + key + "' was added with value: " + value);
                }
                default -> throw new IOException("Unsupported status");
            }
        }

        return sj.toString();
    }

    public static String toPlainValue(Object value) {

        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return "'" + value + "'";
        }

        var stringValue = value.toString();
        var isComplexValue = (stringValue.startsWith("[") && stringValue.endsWith("]"))
                || (stringValue.startsWith("{") && stringValue.endsWith("}"));

        if (isComplexValue) {
            stringValue = "[complex value]";
        }

        return stringValue;
    }
}
