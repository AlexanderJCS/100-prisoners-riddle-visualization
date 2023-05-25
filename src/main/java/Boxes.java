import jangl.coords.PixelCoords;
import jangl.coords.ScreenCoords;
import jangl.graphics.font.parser.Font;
import jangl.shapes.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boxes implements AutoCloseable {
    private final Box[] boxes;

    public Boxes(ScreenCoords center, float screenWidth, float screenHeight, int boxesWidth, int boxesHeight) {
        this.boxes = new Box[boxesWidth * boxesHeight];
        this.generateBoxes(center, screenWidth, screenHeight, boxesWidth, boxesHeight);
    }

    private void generateBoxes(ScreenCoords center, float screenWidth, float screenHeight, int boxesWidth, int boxesHeight) {
        float xScreenDistPerBox = screenWidth / boxesWidth;
        float yScreenDistPerBox = screenHeight / boxesHeight;

        float xSpace = PixelCoords.distXtoScreenDist(5);
        float ySpace = PixelCoords.distYtoScreenDist(5);

        // Generate the box numbers
        List<Integer> boxValues = new ArrayList<>(boxesWidth * boxesHeight);
        for (int i = 1; i <= boxesWidth * boxesHeight; i++) {
            boxValues.add(i);
        }

        Collections.shuffle(boxValues);

        // Generate the boxes
        int boxIndex = 0;
        for (int w = 0; w < boxesWidth; w++) {
            for (int h = 0; h < boxesHeight; h++) {
                Rect rect = new Rect(
                        new ScreenCoords(
                                xScreenDistPerBox * w + center.x,
                                yScreenDistPerBox * h + yScreenDistPerBox + center.y
                        ),
                        xScreenDistPerBox - xSpace,
                        yScreenDistPerBox - ySpace
                );

                this.boxes[boxIndex] = new Box(
                        rect, boxIndex + 1, boxValues.remove(0)
                );

                boxIndex++;
            }
        }
    }

    public int revealBox(int boxID) {
        boxes[boxID - 1].show();
        return boxes[boxID - 1].getContainingNumber();
    }

    public void hideAll() {
        for (Box box : boxes) {
            box.hide();
        }
    }

    public void draw() {
        for (Box box : boxes) {
            box.draw();
        }
    }

    @Override
    public void close() {
        for (Box box : boxes) {
            box.close();
        }
    }
}
