import jangl.time.Clock;

public class Prisoner {
    public static final int MAX_ATTEMPTS = 50;

    private final Boxes boxes;
    private int boxToGoTo;
    private final double updateTime;
    private double timeToUpdate;
    private final int prisonerNumber;
    private Status status;
    private int attempts;

    public Prisoner(Boxes boxes, int prisonerNumber, double updateTime) {
        this.boxes = boxes;
        this.prisonerNumber = prisonerNumber;
        this.boxToGoTo = prisonerNumber;
        this.updateTime = updateTime;
        this.status = Status.IN_PROGRESS;
    }

    private void goToNextBox() {
        this.boxToGoTo = this.boxes.showBox(this.boxToGoTo);
        attempts++;

        if (this.boxToGoTo == prisonerNumber) {
            this.status = Status.SUCCEEDED;
        } else if (attempts >= MAX_ATTEMPTS) {
            this.status = Status.FAILED;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void update() {
        this.timeToUpdate += Clock.getTimeDelta();

        while (timeToUpdate > updateTime) {
            timeToUpdate -= updateTime;
            this.goToNextBox();
        }
    }
}
