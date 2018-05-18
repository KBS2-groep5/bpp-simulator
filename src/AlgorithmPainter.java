import java.awt.*;
import java.util.List;
import javax.swing.*;

public class AlgorithmPainter extends JPanel {
    private List<Container> containers;
    private List<Box> boxes;

    AlgorithmPainter(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;
        setPreferredSize(new Dimension(860, 480));
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int x = 0, y = 0;
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        for (int i = 0; i < this.containers.size(); i++) {
            g.setColor(Color.BLACK);
            g.fillRect(8 + (20 * x), 10 + (80 * y), 18, 70);
            g.setColor(Color.RED);
            g.fillRect(9 + (20 * x), 11 + (80 * y), 16, 68);
            g.setColor(Color.GREEN);
            g.fillRect(9 + (20 * x), 11 + (80 * y), 16, (int) ((float) this.containers.get(i).getPercentageFilled() / 100 * 68));
            g.setColor(Color.BLACK);
            g.drawString("" + this.containers.get(i).getPercentageFilled(), 10 + (20 * x), 8 + (80 * y));

            int offsetTop = 0;
            for (Box b : this.containers.get(i).getBoxes()) {
                offsetTop += (int) ((float) b.getHeight() / 100 * 70);
                g.drawLine(8 + (20 * x), 10 + (80 * y) + offsetTop, 25 + (20 * x), 10 + (80 * y) + offsetTop);
                System.out.println(offsetTop);
            }

            x += 1;
            if (x > 22) {
                x = 0;
                y += 1;
            }
        }

        g.setColor(new Color(238, 238, 238));
        g.fillRect(475, 0, 15, 500);

        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.BLACK);
        g.drawString("Box-groottes:", 500, 16);

        x = 0;
        y = 0;
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        for (int i = 0; i < this.boxes.size(); i++) {
            g.drawString("" + (i + 1) + ": " + this.boxes.get(i).getHeight(), 500 + x * 60, 32 + y * 20);
            y += 1;
            if (y > 22) {
                y = 0;
                x += 1;
            }
        }
    }

    void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
