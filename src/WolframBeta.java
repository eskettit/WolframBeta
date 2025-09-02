import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class WolframBeta extends JFrame {

    private GraphPanel graphPanel;
    private JPanel controlsPanel;
    private JTextField functionInput;
    private JButton graphButton;

    public WolframBeta() {
        setTitle("WolframBeta");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graphPanel = new GraphPanel();
        graphPanel.setBackground(Color.WHITE);

        controlsPanel = new JPanel();

        functionInput = new JTextField("sin(x)", 30);
        graphButton = new JButton("Draw Graph");

        controlsPanel.add(new JLabel("y = "));
        controlsPanel.add(functionInput);
        controlsPanel.add(graphButton);

        setLayout(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.SOUTH);

        // Action listener for the button (still useful for manual updates)
        graphButton.addActionListener(e -> drawGraph());

        // This is the new part for real-time updates
        functionInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                drawGraph();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                drawGraph();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                drawGraph();
            }
        });

        // Call the method to draw the default function on startup
        SwingUtilities.invokeLater(() -> {
            drawGraph();
        });
    }

    private void drawGraph() {
        String expression = functionInput.getText();
        try {
            Tokenizer tokenizer = new Tokenizer(expression);
            List<Token> tokens = tokenizer.tokenize();

            Parser parser = new Parser(tokens);
            Node ast = parser.parse();

            graphPanel.setFunction(ast);
        } catch (IllegalArgumentException ex) {
            // do nothing, but prevents lag
        }
    }

    public static void main(String[] args) {
        WolframBeta calculator = new WolframBeta();
        calculator.setVisible(true);
    }
}