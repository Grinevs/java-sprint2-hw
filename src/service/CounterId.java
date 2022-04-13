package service;

import java.util.ArrayList;
import java.util.List;

public class CounterId {
    private static int count = 0;
    private static List<Integer> usedId = new ArrayList<>();

    public static int generateId() {
        while (isContainsId(count)) {
            count++;
        }
        return count++;
    }

    public static void addUsedId(int id) {
        usedId.add(id);
    }

    public static Boolean isContainsId(int id) {
        return usedId.contains(id);
    }
}
