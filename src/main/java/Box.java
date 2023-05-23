import jangl.coords.ScreenCoords;
import jangl.graphics.font.Text;
import jangl.graphics.font.parser.Font;
import jangl.graphics.shaders.ColorShader;
import jangl.shapes.Rect;

public class Box implements AutoCloseable {
    private final Rect rect;
    private final Text text;
    private final int number;
    private final int containingNumber;
    private ColorShader color;
    private boolean shown;

    public Box(Rect rect, Font font, int number, int containingNumber) {
        this.shown = false;
        this.rect = rect;

        this.number = number;
        this.containingNumber = containingNumber;

        ScreenCoords rectCenter = this.rect.getCenter();

        this.text = new Text(
                font, String.valueOf(number),
                new ScreenCoords(rectCenter.x - this.rect.getWidth() / 2, rectCenter.y + this.rect.getHeight() / 2),
                this.rect.getHeight() / 3
        );

        this.color = new ColorShader(1, 0, 0, 1);
    }

    public void draw() {
        this.rect.draw(this.color);
        this.text.draw();
    }

    public void hide() {
        if (!this.shown) {
            return;
        }

        this.shown = false;
        this.color = new ColorShader(1, 0, 0, 1);
        this.text.setText(String.valueOf(this.number));
    }

    public void show() {
        if (this.shown) {
            return;
        }

        this.shown = true;
        this.color = new ColorShader(0, 1, 0, 1);
        this.text.setText(String.valueOf(this.containingNumber));
    }

    public int getContainingNumber() {
        return containingNumber;
    }

    @Override
    public void close() {
        this.rect.close();
        this.color.close();
    }
}
