import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class FuturisticCalc extends JFrame implements ActionListener {

    JTextField display;
    JPanel buttonPanel;
    String firstOperand = "";
    String operator = "";
    Font futuristicFont;

    public FuturisticCalc() {
        setTitle("Futuristic Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 30));

        // Load Orbitron Font
        try {
            futuristicFont = Font.createFont(Font.TRUETYPE_FONT, new File("Orbitron-Regular.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(futuristicFont);
        } catch (IOException | FontFormatException e) {
            futuristicFont = new Font("SansSerif", Font.PLAIN, 24); // Fallback
        }

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setFont(futuristicFont.deriveFont(32f));
        display.setForeground(Color.WHITE);
        display.setBackground(new Color(30, 30, 40));
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Buttons
        buttonPanel = new JPanel(new GridLayout(5, 4, 15, 15));
        buttonPanel.setBackground(new Color(20, 20, 30));
        String[] buttons = {
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String text : buttons) {
            JButton button = createButton(text);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(futuristicFont);
        button.setForeground(new Color(200, 100, 255));
        button.setBackground(new Color(30, 30, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 20, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 30, 50));
            }
        });

        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = ((JButton)e.getSource()).getText();
        
        if (command.matches("[0-9]") || command.equals(".")) {
            display.setText(display.getText() + command);
        } else if (command.equals("C")) {
            display.setText("");
            firstOperand = "";
            operator = "";
        } else if (command.equals("=")) {
            if (!firstOperand.equals("") && !operator.equals("")) {
                double result = calculate(firstOperand, display.getText(), operator);
                display.setText(Double.toString(result));
                firstOperand = "";
                operator = "";
            }
        } else {
            firstOperand = display.getText();
            operator = command;
            display.setText("");
        }
    }

    private double calculate(String a, String b, String op) {
        double x = Double.parseDouble(a);
        double y = Double.parseDouble(b);
        switch (op) {
            case "+": return x + y;
            case "-": return x - y;
            case "×": return x * y;
            case "÷": return x / y;
        }
        return 0;
    }

    public static void main(String[] args) {
        new FuturisticCalc();
    }
}
