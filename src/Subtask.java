public class Subtask extends Task {
    private Epic epic;

    public Subtask(String title, String description, Status status, Epic epic) {
        super(title, description, status);
        this.epic = epic;
        epic.addSubTask(this);
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return super.toString() + "," + "Subtask{" +
                "epicId=" + epic.getId() +
                "}";
    }
}
// так как незнаю формат получения на фронтэнде, я понимаю у нас полная импровизация? =)