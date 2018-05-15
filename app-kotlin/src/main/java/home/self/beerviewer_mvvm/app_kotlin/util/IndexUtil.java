package home.self.beerviewer_mvvm.app_kotlin.util;

import java.util.HashMap;
import java.util.Map;

public class IndexUtil {

    //TODO : hashmap -> sparseintarray
    private static final Map<Integer, Integer> indexMap = new HashMap<>();

    static {
        indexMap.put(1, 1); indexMap.put(2, 26);
        indexMap.put(3, 51); indexMap.put(4, 76);
        indexMap.put(5, 101); indexMap.put(6, 126);
        indexMap.put(7, 151); indexMap.put(8, 176);
        indexMap.put(9, 201); indexMap.put(10, 226);
    }

    public static int getIndex(int key) {
        return indexMap.get(key);
    }
}
