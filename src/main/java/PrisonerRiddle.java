import jangl.JANGL;
import jangl.coords.ScreenCoords;
import jangl.graphics.font.Text;
import jangl.io.Window;
import jangl.time.Clock;

public class PrisonerRiddle {
    private static final int PRISONERS = 100;

    private final Text releasedText;
    private final Boxes boxes;
    private Prisoner prisoner;
    private int prisonerIndex;

    public PrisonerRiddle() {
        this.boxes = new Boxes(new ScreenCoords(-1, -1f), 2, 1.6f, 10, 10);
        this.prisonerIndex = 1;
        this.prisoner = new Prisoner(this.boxes, this.prisonerIndex, 0.01);
        this.releasedText = new Text(Consts.FONT, "Prisoners Released: 0", new ScreenCoords(-0.95f, 0.95f), 0.1f);
    }

    public void update() {
        if (prisoner.getStatus() == Status.IN_PROGRESS) {
            prisoner.update();

        } else if (prisoner.getStatus() == Status.FAILED) {
            Window.setClearColor(1, 0, 0, 1);

        } else if (prisoner.getStatus() == Status.SUCCEEDED && prisonerIndex < PRISONERS) {
            prisonerIndex++;
            prisoner = new Prisoner(this.boxes, prisonerIndex, 0.0075);
            this.boxes.hideAll();

            this.releasedText.setText("Prisoners released: " + (prisonerIndex - 1));

        } else if (prisonerIndex >= PRISONERS) {
            this.releasedText.setText("Prisoners released: " + prisonerIndex);
            Window.setClearColor(0, 1, 0, 1);
            this.boxes.showAll();
        }
    }

    public void run() {
        while (Window.shouldRun()) {
            Window.clear();

            this.update();

            this.boxes.draw();
            this.releasedText.draw();

            JANGL.update();

            try {
                Clock.smartTick(60);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
