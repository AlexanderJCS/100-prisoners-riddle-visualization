import jangl.JANGL;
import jangl.io.Window;

public class Main {
    public static void main(String[] args) {
        JANGL.init(1600, 900);

        new PrisonerRiddle().run();

        Window.close();
    }
}
