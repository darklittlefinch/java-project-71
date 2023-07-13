package hexlet.code.formatters;

import hexlet.code.DiffTree;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Stylish {

    private static final String SYMBOL_NOT_CHANGED = " ";
    private static final String SYMBOL_ADDED = "+";
    private static final String SYMBOL_DELETED = "-";

    // Returns differences in stylish format
    public static String stylish(List<Map<String, Object>> diff) throws IOException {

        StringJoiner sj = new StringJoiner("\n  ", "{\n  ", "\n}");

        for (Map<String, Object> map: diff) {
            var status = map.get(DiffTree.KEY_STATUS).toString();
            var key = map.get(DiffTree.KEY_NAME);
            var value1 = map.get(DiffTree.KEY_VALUE1);
            var value2 = map.get(DiffTree.KEY_VALUE2);

            switch (status) {
                case DiffTree.STATUS_NOT_CHANGED -> sj.add(SYMBOL_NOT_CHANGED + " " + key + ": " + value1);
                case DiffTree.STATUS_CHANGED -> {
                    sj.add(SYMBOL_DELETED + " " + key + ": " + value1);
                    sj.add(SYMBOL_ADDED + " " + key + ": " + value2);
                }
                case DiffTree.STATUS_DELETED -> sj.add(SYMBOL_DELETED + " " + key + ": " + value1);
                case DiffTree.STATUS_ADDED -> sj.add(SYMBOL_ADDED + " " + key + ": " + value1);
                default -> throw new IOException("Unsupported status");
            }
        }

        return sj.toString();
    }
}
