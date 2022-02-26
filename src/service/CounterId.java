package service;

public class CounterId {
    private static int count = 0;

    public static int generateId() {
        return count++;
    }
}
