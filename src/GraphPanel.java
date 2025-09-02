import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class GraphPanel extends JPanel implements MouseWheelListener {

    private Node ast;
    private double scale = 30.0;

    public GraphPanel() {
        addMouseWheelListener(this);
    }

    public void setFunction(Node ast) {
        this.ast = ast;
        repaint();
    }

    // calculates the label interval dynamically
    private double getLabelInterval() {
        double pixelInterval = 100;
        double mathInterval = pixelInterval / scale;

        // find the nearest integer number
        double tempInterval = Math.pow(10, Math.floor(Math.log10(mathInterval)));
        if (mathInterval / tempInterval >= 5) {
            return tempInterval * 5;
        } else if (mathInterval / tempInterval >= 2) {
            return tempInterval * 2;
        } else {
            return tempInterval;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double offsetX = 0.0;
        int originX = (int) (getWidth() / 2.0 + offsetX * scale);
        double offsetY = 0.0;
        int originY = (int) (getHeight() / 2.0 - offsetY * scale);

        g.drawLine(0, originY, getWidth(), originY);
        g.drawLine(originX, 0, originX, getHeight());

        // adaptive label interval
        double labelInterval = getLabelInterval();

        // Draw X-axis labels
        double startX = -offsetX - (getWidth() / 2.0) / scale;
        startX = Math.ceil(startX / labelInterval) * labelInterval;
        for (double i = startX; i <= -offsetX + (getWidth() / 2.0) / scale; i += labelInterval) {
            int xPos = (int) (originX + i * scale);
            g.drawString(String.format("%.1f", i), xPos, originY + 15);
        }

        // Draw Y-axis labels
        double startY = offsetY - (getHeight() / 2.0) / scale;
        startY = Math.ceil(startY / labelInterval) * labelInterval;
        for (double i = startY; i <= offsetY + (getHeight() / 2.0) / scale; i += labelInterval) {
            int yPos = (int) (originY - i * scale);
            g.drawString(String.format("%.1f", i), originX + 5, yPos);
        }

        if (ast == null) {
            return;
        }

        // Draw the curve
        for (int pixelX = 0; pixelX < getWidth(); pixelX++) {
            double x = (pixelX - getWidth() / 2.0) / scale - offsetX;
            double y = ast.evaluate(x);
            int pixelY = (int) (getHeight() / 2.0 - (y + offsetY) * scale);

            // draw a dot for each point
            g.drawRect(pixelX, pixelY, 1, 1);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            scale *= 1.2;
        } else {
            scale /= 1.2;
        }
        repaint();
    }
}