public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        for (Status s : Status.values()) {
            System.out.println(s.getMessage());
        }


    }
}
