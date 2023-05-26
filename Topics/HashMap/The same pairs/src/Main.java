import java.util.*;


class MapFunctions {

    public static void calcTheSamePairs(Map<String, String> map1, Map<String, String> map2) {
        // write your code here
        int count = 0;
        for (var entry: map1.entrySet()) {
            count += map2.containsKey(entry.getKey()) && (map2.get(entry.getKey()).equals(entry.getValue())) ? 1 : 0;
        }
        System.out.println(count);
    }
}