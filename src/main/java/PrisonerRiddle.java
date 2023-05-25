import jangl.JANGL;
import jangl.coords.ScreenCoords;
import jangl.io.Window;
import jangl.time.Clock;

public class PrisonerRiddle {
    private static final int PRISONERS = 100;

    public void run() {
        try (Boxes boxes = new Boxes(new ScreenCoords(-1, -1f), 2, 2f, 10, 10)) {
            int prisonerIndex = 1;
            Prisoner prisoner = new Prisoner(boxes, prisonerIndex, 0.01);


            while (Window.shouldRun()) {
                Window.clear();

                if (prisoner.getStatus() == Status.IN_PROGRESS) {
                    prisoner.update();
                } else if (prisoner.getStatus() == Status.FAILED) {
                    Window.setClearColor(1, 0, 0, 1);
                } else if (prisoner.getStatus() == Status.SUCCEEDED && prisonerIndex < PRISONERS) {
                    prisonerIndex++;
                    prisoner = new Prisoner(boxes, prisonerIndex, 0.01);
                    boxes.hideAll();
                } else if (prisonerIndex >= PRISONERS) {
                    Window.setClearColor(0, 1, 0, 1);
                    boxes.showAll();
                }

                boxes.draw();

                JANGL.update();

                try {
                    Clock.smartTick(60);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
