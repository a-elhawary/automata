package gui.screens;

import engine.dfa.Logic.NFAtoDFA;
import gui.AppWindow;
import gui.components.FiniteAutomata;
import gui.settings.GUIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FAScreen extends Screen {
    FiniteAutomata automataCanvas;
    public FAScreen(){
        this.setLayout(new BorderLayout());

        automataCanvas = new FiniteAutomata();

        JPanel automataControls = new JPanel();
        JLabel label1 = new JLabel("Transition on: ");
        JTextField inputField = new JTextField(5);
        JButton actButton = new JButton("Add Transition");
        actButton.setBorderPainted(false);
        actButton.setForeground(GUIColors.primaryColor);
        actButton.setBackground(GUIColors.primaryContrastColor);
        actButton.setOpaque(true);
        actButton.addActionListener((e) -> {
            automataCanvas.addTransition(inputField.getText());
        });
        automataControls.add(label1);
        automataControls.add(inputField);
        automataControls.add(actButton);

        this.add(automataControls, BorderLayout.NORTH);
        this.add(automataCanvas, BorderLayout.CENTER);

        JLabel label = new JLabel("Double Click To Create a State", SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(0,0,20,0));
        this.add(label, BorderLayout.SOUTH);
    }

    public void convert(){
        automataCanvas.convert();

        NFAtoDFA nfaToDfaConverter = new NFAtoDFA();
        try {
            String dfa = nfaToDfaConverter.convertToDfa();
            JDialog d = new JDialog(AppWindow.frame, "Incorrect Input");
            d.setLayout(new BorderLayout());
            String labelText = "<html>";
            labelText += dfa.toString();
            labelText += "</html>";
            labelText = labelText.replace("\n", "<br/>");
            JLabel label = new JLabel(labelText);
            label.setAlignmentY(CENTER_ALIGNMENT);
            d.add(label, BorderLayout.CENTER);
            d.setSize(1000,1000);
            d.setLocationRelativeTo(AppWindow.frame);
            d.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
