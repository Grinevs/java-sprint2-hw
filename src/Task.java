public class Task {
    private static int count = 0;
    private int id;
    private String title;
    private String description;
    protected Status status;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.id = count++;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
