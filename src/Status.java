public enum Status {
    NEW("new task"),
    IN_PROGRESS("task in progress"),
    DONE("task done");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
