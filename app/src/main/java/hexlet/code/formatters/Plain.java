package hexlet.code.formatters;

import hexlet.code.DiffTree;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Plain {

    // Returns differences in plain format
    public static String plain(List<Map<String, Object>> diff) throws IOException {

        StringJoiner sj = new StringJoiner("\n");

        for (Map<String, Object> map: diff) {

            var status = map.get(DiffTree.KEY_STATUS).toString();
            var key = map.get(DiffTree.KEY_NAME);
            var value1 = map.get(DiffTree.KEY_VALUE1);
            var value2 = map.get(DiffTree.KEY_VALUE2);

            if (status.equals(DiffTree.STATUS_NOT_CHANGED)) {
                continue;
            }

            switch (status) {
                case DiffTree.STATUS_CHANGED -> {
                    var plainValue1 = toPlainValue(value1);
                    var plainValue2 = toPlainValue(value2);
                    sj.add("Property '" + key + "' was updated. From " + plainValue1 + " to " + plainValue2);
                }
                case DiffTree.STATUS_DELETED -> sj.add("Property '" + key + "' was removed");
                case DiffTree.STATUS_ADDED -> {
                    var value = toPlainValue(value1);
                    sj.add("Property '" + key + "' was added with value: " + value);
                }
                default -> throw new IOException("Unsupported status");
            }
        }

        return sj.toString();
    }

    // Returns formatted value in string
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
