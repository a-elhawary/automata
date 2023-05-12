package gui.screens;

import engine.pda.PDAutomata;
import engine.pda.PushDown;
import gui.AppWindow;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PDScreen extends Screen {
    JTextArea grammarArea;
    JTextField startTerm;
    JLabel pdaLabel;
    public PDScreen(){
        this.setLayout(new BorderLayout());

        JPanel termInput = new JPanel();
        startTerm = new JTextField(5);
        JLabel label = new JLabel("Starting Terminal:");
        label.setBorder(new EmptyBorder(10,10,10,10));
        termInput.add(label);
        termInput.add(startTerm);


        JPanel grammarInput = new JPanel();
        grammarArea = new JTextArea( 50, 30);
        grammarInput.setLayout(new BorderLayout());
        JLabel label1 = new JLabel("Input Grammar Here");
        label.setBorder(new EmptyBorder(10,10,10,10));
        grammarInput.add(label1, BorderLayout.NORTH);
        grammarInput.add(grammarArea, BorderLayout.CENTER);

        JPanel leftInput = new JPanel(new BorderLayout());
        leftInput.setBorder(new BevelBorder(BevelBorder.LOWERED));
        leftInput.add(termInput, BorderLayout.NORTH);
        leftInput.add(grammarInput, BorderLayout.CENTER);
        pdaLabel = new JLabel("<html>Enter Valid grammar.<br/> Terminals must be a single capital letter</html>");

        JScrollPane pdaScroll = new JScrollPane(pdaLabel);
        pdaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pdaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(leftInput, BorderLayout.WEST);
        this.add(pdaScroll, BorderLayout.CENTER);
    }

    public void convert(){
        System.out.println("PDA convert");
        if(startTerm.getText().isEmpty() || startTerm.getText().isBlank()){
            JDialog d = new JDialog(AppWindow.frame, "Incorrect Input");
            d.setLayout(new BorderLayout());
            JLabel label = new JLabel("Must Define which terminal (from LHS of grammar rules) is start");
            label.setAlignmentY(CENTER_ALIGNMENT);
            d.add(label, BorderLayout.CENTER);
            d.setSize(500,500);
            d.setLocationRelativeTo(AppWindow.frame);
            d.setVisible(true);
            return;
        }
        if(grammarArea.getText().isEmpty() || grammarArea.getText().isBlank()){
            JDialog d = new JDialog(AppWindow.frame, "Incorrect Input");
            d.setLayout(new BorderLayout());
            JLabel label = new JLabel("Must Define which terminal (from LHS of grammar rules) is start");
            label.setAlignmentY(CENTER_ALIGNMENT);
            d.add(label, BorderLayout.CENTER);
            d.setSize(500,500);
            d.setLocationRelativeTo(AppWindow.frame);
            d.setVisible(true);
            return;
        }
        PDAutomata pda = PushDown.generatePDA(grammarArea.getText(), startTerm.getText());
        String labelText = "<html>";
        labelText += pda.toString();
        labelText += "</html>";
        labelText = labelText.replace("\n", "<br/>");
        pdaLabel.setText(labelText);
        System.out.println(pda.toString());
    }
}
