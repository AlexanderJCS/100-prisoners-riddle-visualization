import jangl.coords.PixelCoords;
import jangl.coords.ScreenCoords;
import jangl.graphics.font.parser.Font;
import jangl.io.Window;
import jangl.shapes.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boxes implements AutoCloseable {
    private final Font font;
    private final Box[] boxes;

    public Boxes(int width, int height) {
        this.font = new Font("src/main/resources/arial/arial.fnt", "src/main/resources/arial/arial.png");
        this.boxes = new Box[width * height];

        this.generateBoxes(width, height);
    }

    private void generateBoxes(int width, int height) {
        float xPixelsPerBox = (float) Window.getScreenWidth() / width;
        float yPixelsPerBox = (float) Window.getScreenHeight() / height;

        float xScreenDistPerBox = PixelCoords.distXtoScreenDist(xPixelsPerBox);
        float yScreenDistPerBox = PixelCoords.distYtoScreenDist(yPixelsPerBox);

        float xSpace = PixelCoords.distXtoScreenDist(5);
        float ySpace = PixelCoords.distYtoScreenDist(5);

        List<Integer> boxValues = new ArrayList<>(width * height);
        for (int i = 1; i <= width * height; i++) {
            boxValues.add(i);
        }

        Collections.shuffle(boxValues);

        int boxIndex = 0;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                Rect rect = new Rect(
                        new ScreenCoords(
                                xScreenDistPerBox * w - 1,
                                yScreenDistPerBox * h - 1 + yScreenDistPerBox
                        ),
                        xScreenDistPerBox - xSpace,
                        yScreenDistPerBox - ySpace
                );

                this.boxes[boxIndex] = new Box(
                        rect, this.font,
                        boxIndex + 1, boxValues.remove(0)
                );

                boxIndex++;
            }
        }
    }

    public int revealBox(int boxID) {
        boxes[boxID - 1].show();
        return boxes[boxID - 1].getContainingNumber();
    }

    public void draw() {
        for (Box box : boxes) {
            box.draw();
        }
    }

    @Override
    public void close() {
        this.font.close();

        for (Box box : boxes) {
            box.close();
        }
    }
}
