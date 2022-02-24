public class CounterId {
    private static int count = 0;

    static public int newCount() {
        return count++;
    }
}
