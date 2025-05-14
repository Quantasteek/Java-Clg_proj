import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class BiodataApp extends JFrame implements ActionListener {

    JTextField nameField, dobField, contactField, addressField, occupationField;
    JTextField fatherNameField, fatherOccupationField, motherNameField, motherOccupationField;
    JButton generateButton, uploadButton, saveButton;
    JLabel photoLabel;
    String photoPath = "";
    JPanel contentPanel;

    public BiodataApp() {
        setTitle("Biodata Creator - Traditional Style");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLayout(new GridLayout(13, 2, 10, 10));

        // Form fields
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Date of Birth:"));
        dobField = new JTextField();
        add(dobField);

        add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        add(contactField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Occupation:"));
        occupationField = new JTextField();
        add(occupationField);

        add(new JLabel("Father's Name:"));
        fatherNameField = new JTextField();
        add(fatherNameField);

        add(new JLabel("Father's Occupation:"));
        fatherOccupationField = new JTextField();
        add(fatherOccupationField);

        add(new JLabel("Mother's Name:"));
        motherNameField = new JTextField();
        add(motherNameField);

        add(new JLabel("Mother's Occupation:"));
        motherOccupationField = new JTextField();
        add(motherOccupationField);

        uploadButton = new JButton("Upload Photo");
        uploadButton.addActionListener(this);
        add(uploadButton);

        photoLabel = new JLabel();
        add(photoLabel);

        generateButton = new JButton("Generate Biodata");
        generateButton.addActionListener(this);
        add(generateButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                photoPath = file.getAbsolutePath();
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(photoPath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                photoLabel.setIcon(imageIcon);
            }
        } else if (e.getSource() == generateButton) {
            generateBiodataCard();
        }
    }

    private void generateBiodataCard() {
        JFrame cardFrame = new JFrame("Your Biodata Card");
        cardFrame.setSize(600, 800);
        cardFrame.setLayout(new BorderLayout());

        contentPanel = new DecoratedPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(10, 30, 60));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        if (!photoPath.equals("")) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(photoPath).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
            JLabel picLabel = new JLabel(imageIcon);
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(picLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JLabel title = new JLabel("Personal Details", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(new Color(255, 215, 0)); // Golden color
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createStyledLabel("Name: " + nameField.getText()));
        contentPanel.add(createStyledLabel("Date of Birth: " + dobField.getText()));
        contentPanel.add(createStyledLabel("Contact: " + contactField.getText()));
        contentPanel.add(createStyledLabel("Address: " + addressField.getText()));
        contentPanel.add(createStyledLabel("Occupation: " + occupationField.getText()));

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel familyTitle = new JLabel("Family Details", JLabel.CENTER);
        familyTitle.setFont(new Font("Serif", Font.BOLD, 26));
        familyTitle.setForeground(new Color(255, 215, 0));
        familyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(familyTitle);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createStyledLabel("Father's Name: " + fatherNameField.getText()));
        contentPanel.add(createStyledLabel("Father's Occupation: " + fatherOccupationField.getText()));
        contentPanel.add(createStyledLabel("Mother's Name: " + motherNameField.getText()));
        contentPanel.add(createStyledLabel("Mother's Occupation: " + motherOccupationField.getText()));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        cardFrame.add(scrollPane, BorderLayout.CENTER);

        saveButton = new JButton("Save as Image");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savePanelAsImage(contentPanel);
            }
        });
        cardFrame.add(saveButton, BorderLayout.SOUTH);

        cardFrame.setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return label;
    }

    private void savePanelAsImage(JPanel panel) {
        try {
            BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            panel.paint(g2);
            g2.dispose();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Biodata Image");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(image, "png", new File(file.getAbsolutePath() + ".png"));
                JOptionPane.showMessageDialog(null, "Biodata saved successfully!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Class to add decorative border
    class DecoratedPanel extends JPanel {
        Image cornerImage;
    
        public DecoratedPanel() {
            try {
                cornerImage = new ImageIcon("corner_flower.png").getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Draw background border
            g2d.setColor(new Color(255, 215, 0));
            g2d.setStroke(new BasicStroke(8));
            int margin = 20;
            g2d.drawRect(margin, margin, getWidth() - 2 * margin, getHeight() - 2 * margin);
    
            if (cornerImage != null) {
                int imgSize = 60;
                
                // Top-left
                g2d.drawImage(cornerImage, margin - 10, margin - 10, imgSize, imgSize, null);
                // Top-right
                g2d.drawImage(cornerImage, getWidth() - margin - imgSize + 10, margin - 10, imgSize, imgSize, null);
                // Bottom-left
                g2d.drawImage(cornerImage, margin - 10, getHeight() - margin - imgSize + 10, imgSize, imgSize, null);
                // Bottom-right
                g2d.drawImage(cornerImage, getWidth() - margin - imgSize + 10, getHeight() - margin - imgSize + 10, imgSize, imgSize, null);
            }
        }
    }
    

    public static void main(String[] args) {
        new BiodataApp();
    }
}
