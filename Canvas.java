import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Gestiona el lienzo gráfico central para dibujar los componentes.
 */
public class Canvas {

    private static Canvas canvasSingleton;

    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("Simulador Slot Machine", 600, 400, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private Map<Object, ShapeDescription> shapes;

    private Canvas(String title, int width, int height, Color bgColor) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColor = bgColor;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();
    }

    public void setVisible(boolean visible) {
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    public void draw(Object referenceObject, String colorString, Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, colorString));
        redraw();
    }

    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // Sin manejo avanzado de excepciones
        }
    }

    private void redraw() {
        eraseCanvas();
        for (int i = 0; i < objects.size(); i++) {
            Object shapeKey = objects.get(i);
            shapes.get(shapeKey).draw(graphic);
        }
        canvas.repaint();
    }

    private void eraseCanvas() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fillRect(0, 0, size.width, size.height);
        graphic.setColor(original);
    }

    private static Color parseCSSColor(String colorString) {
        if (colorString == null) return Color.BLACK;
        String lower = colorString.toLowerCase().trim();

        if (lower.equals("red")) return Color.RED;
        if (lower.equals("blue")) return Color.BLUE;
        if (lower.equals("green")) return Color.GREEN;
        if (lower.equals("yellow")) return Color.YELLOW;
        if (lower.equals("black")) return Color.BLACK;
        if (lower.equals("white")) return Color.WHITE;
        if (lower.equals("gray") || lower.equals("grey")) return Color.GRAY;
        if (lower.equals("orange")) return Color.ORANGE;
        if (lower.equals("cyan")) return Color.CYAN;
        if (lower.equals("magenta")) return Color.MAGENTA;
        if (lower.equals("pink")) return Color.PINK;
        if (lower.equals("gold")) return new Color(255, 215, 0);
        if (lower.equals("purple")) return new Color(128, 0, 128);

        return Color.LIGHT_GRAY;
    }

    private class CanvasPane extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    private class ShapeDescription {
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String colorString) {
            this.shape = shape;
            this.colorString = colorString;
        }

        public void draw(Graphics2D g) {
            g.setColor(parseCSSColor(colorString));
            g.fill(shape);
        }
    }
}