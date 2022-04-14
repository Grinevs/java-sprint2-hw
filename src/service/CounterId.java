package service;

import java.util.ArrayList;
import java.util.List;

public class CounterId {
    private int count;
    private static List<Integer> usedId = new ArrayList<>();

    public CounterId(int id) {
        this.count = id;
    }

    public int generateId() {
        while (isContainsId(count)) {
            count++;
        }
        addUsedId(count);
        return count++;
    }

    public static void addUsedId(int id) {
        usedId.add(id);
    }

    public static Boolean isContainsId(int id) {
        return usedId.contains(id);
    }
}
