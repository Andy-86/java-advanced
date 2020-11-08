package collection;

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<Integer, String> treeMap1 = new TreeMap<>(Comparator.naturalOrder());
        treeMap1.put(1, "sky");
        treeMap1.put(2, "moon");
        treeMap1.put(3, "infi");
        treeMap1.put(4, "fly");
        treeMap1.put(5, "thooo");
        System.out.println(treeMap1.toString());

        TreeMap<Integer, String> treeMap2 = new TreeMap<>(Comparator.reverseOrder());
        treeMap2.putAll(treeMap1);
        System.out.println(treeMap2);
    }
}
