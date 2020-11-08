package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SyncListDemo {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        list1.set(1, 11);
        //list1.add(1);
        //list1.remove(2);

        List<Integer> list2 = new ArrayList<>();
        list2.addAll(list1);
        Collections.synchronizedCollection(list2);
        list2.add(6);
        System.out.println(Arrays.asList(list2.toArray()));

        List<Integer> list3 = new ArrayList<>();
        list3.addAll(list2);
        List<Integer> list4 = Collections.unmodifiableList(list3);
        list3.add(7);
        list3.add(8);
        System.out.println(Arrays.asList(list3.toArray()));

        //list4.add(8);
        System.out.println(Arrays.asList(list4.toArray()));
    }
}
