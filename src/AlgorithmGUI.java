import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class AlgorithmGUI extends JFrame implements ActionListener {
    private BPPAlgorithm algorithm;

    private int cursor = 0;

    private AlgorithmPainter panel;
    private JTextField containerCountInput;
    private JTextField boxCountInput;
    private JButton previousButton;
    private JButton nextButton;
    private JComboBox algorithmSelector;
    private JLabel cursorDisplay;
    private JButton solveFullyButton;
    private JLabel timeDisplay;

    AlgorithmGUI(BPPAlgorithm algorithm) {
        super();

        this.algorithm = algorithm;

        setTitle("Bin Packing Problem");
        setSize(1200, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel = new AlgorithmPainter(this.algorithm.solveSteps(1), this.algorithm.getBoxes());
        panel.setBounds(10, 10, 860, 480);
        add(panel);

        JLabel algorithmSelectorLabel = new JLabel("Algorithme:");
        algorithmSelectorLabel.setBounds(900, 20, 120, 20);
        add(algorithmSelectorLabel);

        algorithmSelector = new JComboBox<>(new String[]{"Nextfit","Firstfit","Bestfit","KorfBinCompletion"});
        algorithmSelector.setBounds(1040, 20, 110, 20);
        algorithmSelector.addActionListener(this);
        add(algorithmSelector);

        JLabel containerCountLabel = new JLabel("Aantal containers:");
        containerCountLabel.setBounds(900, 50, 120, 20);
        add(containerCountLabel);

        JLabel containerCountNumber = new JLabel(String.valueOf(this.algorithm.getContainerCount()));
        containerCountNumber.setBounds(1040, 50, 110, 20);
        add(containerCountNumber);

        JLabel boxCountLabel = new JLabel("Aantal pakketjes:");
        boxCountLabel.setBounds(900, 80, 120, 20);
        add(boxCountLabel);

        boxCountInput = new JTextField();
        boxCountInput.setBounds(1040, 80, 110, 20);
        boxCountInput.setText("138");
        add(boxCountInput);
        boxCountInput.addActionListener(this);

        JLabel stepButtonsLabel = new JLabel("Stap voor stap:");
        stepButtonsLabel.setBounds(900, 110, 120, 20);
        add(stepButtonsLabel);

        previousButton = new JButton("<--");
        ImageIcon previousIcon = loadIcon("back.png");
        if (previousIcon != null) {
            previousButton.setText("");
            previousButton.setIcon(previousIcon);
        }
        previousButton.setBounds(1040, 110, 50, 20);
        add(previousButton);
        previousButton.addActionListener(this);

        nextButton = new JButton("-->");
        ImageIcon nextIcon = loadIcon("next.png");
        if (nextIcon != null) {
            nextButton.setText("");
            nextButton.setIcon(nextIcon);
        }
        nextButton.setBounds(1100, 110, 50, 20);
        add(nextButton);
        nextButton.addActionListener(this);

        JLabel cursorLabel = new JLabel("Aantal stappen:");
        cursorLabel.setBounds(900, 140, 120, 20);
        add(cursorLabel);

        cursorDisplay = new JLabel("" + this.cursor);
        cursorDisplay.setBounds(1040, 140, 120, 20);
        add(cursorDisplay);

        JLabel solveFullyLabel = new JLabel("Algoritme starten");
        solveFullyLabel.setBounds(900, 170, 120, 20);
        add(solveFullyLabel);

        solveFullyButton = new JButton("Start");
        solveFullyButton.setBounds(1040, 170, 110, 20);
        add(solveFullyButton);
        solveFullyButton.addActionListener(this);

        JLabel timeLabel = new JLabel("Tijd:");
        timeLabel.setBounds(900, 200, 120, 20);
        add(timeLabel);

        timeDisplay = new JLabel(BPPTimer.getHumanReadableAverageTime(this.algorithm));
        timeDisplay.setBounds(1040, 200, 120, 20);
        add(timeDisplay);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent source = (JComponent) e.getSource();

        if (source == previousButton && this.cursor > 0) {
            this.cursor -= 1;
        }

        if (source == nextButton) {
            this.cursor += 1;
        }

        if (source == solveFullyButton) {
            this.cursor = this.algorithm.getBoxCount();
            this.cursorDisplay.setText("" + this.cursor);
        }

        this.cursorDisplay.setText("" + this.cursor);

        int boxCount = 1;
        try {
            boxCount = Integer.parseInt(boxCountInput.getText());
            if (boxCount > 138) {
                boxCount = 138;
                JOptionPane.showMessageDialog(null, "Hee dikkie het maximum is 138");
                boxCountInput.setText("" + 138);
            }
            if (boxCount < 0) {
                boxCount = 0;
                JOptionPane.showMessageDialog(null, "Hee dikkie het minimum is 0");
                boxCountInput.setText("" + 0);
            }
        } catch (Exception exc) { /* why do we have to catch this shit */ }


        if (this.algorithm.getBoxCount() != boxCount) {
            List<Box> boxes = new ArrayList<>();
            Random rand = new Random(5);
            for (int i = 0; i < boxCount; i++) {
                boxes.add(new Box(5 + rand.nextInt(95)));
            }
            this.algorithm.setBoxes(boxes);
            this.panel.setBoxes(boxes);
        }

        if(e.getSource() == algorithmSelector) {
            @SuppressWarnings("unchecked")
            JComboBox<String> source2 = (JComboBox<String>) e.getSource();
            String selected = (String) source2.getSelectedItem();
            if(selected == null) return;

            if(selected.equals(NextFitAlgorithm.NAME)) {
                List<Box> boxes = this.algorithm.getBoxes();
                List<Container> containers = this.algorithm.getContainers();
                this.algorithm = new NextFitAlgorithm(containers, boxes);
            }
            if(selected.equals(FirstFitAlgorithm.NAME)) {
                List<Box> boxes = this.algorithm.getBoxes();
                List<Container> containers = this.algorithm.getContainers();
                this.algorithm = new FirstFitAlgorithm(containers,boxes);
            }
            if(selected.equals(BestFitAlgorithm.NAME)) {
                List<Box> boxes = this.algorithm.getBoxes();
                List<Container> containers = this.algorithm.getContainers();
                this.algorithm = new BestFitAlgorithm(containers,boxes);
            }

        }

        this.timeDisplay.setText(BPPTimer.getHumanReadableAverageTime(this.algorithm));


        repaint();
    }

    private ImageIcon loadIcon(String name) {
        try {
            java.net.URL path = this.getClass().getResource(name);
            return new ImageIcon(path);
        } catch (Exception e) {
            System.out.println("Failed to load icon: " + name);
            return null;
        }
    }
}
