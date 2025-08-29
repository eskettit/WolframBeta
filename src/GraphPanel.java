import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class GraphPanel extends JPanel implements MouseWheelListener {

    public GraphPanel() {
        addMouseWheelListener(this);
    }

    private Node ast;
    private double scale = 20.0;

    public void setFunction(Node ast) {
        this.ast = ast;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw axes
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        // X-axis grid
        for (int i = 1; i <= getWidth() / 2.0/ scale; i++) {
            int xPos = xCenter + (int) (i * scale);
            g.drawString(String.valueOf(i), xPos, yCenter + 15);

            int xNegPos = xCenter - (int) (i * scale);
            g.drawString(String.valueOf(-i), xNegPos, yCenter + 15);
        }
        // Y-axis grid
        for (int i = 1; i <= getHeight() / 2.0 / scale; i++) {
            int yPos = yCenter - (int) (i * scale);
            g.drawString(String.valueOf(i), xCenter + 5, yPos);

            int yNegPos = yCenter + (int) (i * scale);
            g.drawString(String.valueOf(-i), xCenter + 5, yNegPos);
        }


        if (ast == null) {
            return;
        }

        int prevPixelX = 0;
        double prevX = (0 - getWidth() / 2.0) / scale;
        double prevY = ast.evaluate(prevX);
        int prevPixelY = (int) (getHeight() / 2.0 - prevY * scale);

        // Draw the curve
        for (int pixelX = 1; pixelX < getWidth(); pixelX++) {
            double x = (pixelX - getWidth() / 2.0) / scale;
            double y = ast.evaluate(x);
            int pixelY = (int) (getHeight() / 2.0 - y * scale);

            g.drawLine(prevPixelX, prevPixelY, pixelX, pixelY);

            prevPixelX = pixelX;
            prevPixelY = pixelY;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            // Scroll up: zoom in
            scale *= 1.2;
        } else {
            scale /= 1.2;
        }
        repaint();
    }
}