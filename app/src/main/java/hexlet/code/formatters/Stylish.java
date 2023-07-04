package hexlet.code.formatters;

import hexlet.code.Differ;

import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

public class Stylish {

    private static final String SYMBOL_NOT_CHANGED = " ";
    private static final String SYMBOL_ADDED = "+";
    private static final String SYMBOL_DELETED = "-";

    // Returns differences in stylish format
    public static String stylish(Map<String, Object> map1, Map<String, Object> map2,
                                 Map<String, String> diffMap) throws IOException {

        StringJoiner sj = new StringJoiner("\n  ", "{\n  ", "\n}");

        for (Map.Entry<String, String> entry: diffMap.entrySet()) {
            var key = entry.getKey();
            var status = entry.getValue();

            switch (status) {
                case Differ.STATUS_NOT_CHANGED -> sj.add(SYMBOL_NOT_CHANGED + " " + key + ": " + map1.get(key));
                case Differ.STATUS_CHANGED -> {
                    sj.add(SYMBOL_DELETED + " " + key + ": " + map1.get(key));
                    sj.add(SYMBOL_ADDED + " " + key + ": " + map2.get(key));
                }
                case Differ.STATUS_DELETED -> sj.add(SYMBOL_DELETED + " " + key + ": " + map1.get(key));
                case Differ.STATUS_ADDED -> sj.add(SYMBOL_ADDED + " " + key + ": " + map2.get(key));
                default -> throw new IOException("Unsupported status");
            }
        }

        return sj.toString();
    }
}
