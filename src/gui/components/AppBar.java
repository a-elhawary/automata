package gui.components;
import gui.AppWindow;
import gui.settings.GUIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppBar extends JPanel{
    public AppBar(AppWindow parentWindow){
        // set AppBar colors
        this.setBackground(GUIColors.primaryColor);

        this.setLayout(new BorderLayout());

        // Add "Logo" on the left
        JLabel label = new JLabel("Automata");
        label.setForeground(GUIColors.primaryContrastColor);
        label.setBorder(new EmptyBorder(10, 30, 10 , 10));
        this.add(label, BorderLayout.WEST);

        // Create Finite Automata Convert Button
        JButton faButton = new JButton("Finite Automata");
        faButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        faButton.setBorderPainted(false);
        faButton.setBackground(GUIColors.primaryColor);
        faButton.setForeground(GUIColors.primaryContrastColor);
        faButton.addActionListener((e)->{
            parentWindow.setScreen(0);
        });
        faButton.setOpaque(true);

        // Create PushDown Automata Convert Button
        JButton pdButton = new JButton("Push Down Automata");
        pdButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pdButton.setBorderPainted(false);
        pdButton.setBackground(GUIColors.primaryColor);
        pdButton.setForeground(GUIColors.primaryContrastColor);
        pdButton.addActionListener((e)->{
            parentWindow.setScreen(1);
        });
        pdButton.setOpaque(true);

        // Create action Automata Convert Button
        JButton actButton = new JButton("Convert");
        actButton.setBorderPainted(false);
        actButton.setForeground(GUIColors.primaryColor);
        actButton.setBackground(GUIColors.primaryContrastColor);
        actButton.setOpaque(true);
        actButton.addActionListener((e) -> {
            parentWindow.convert();
        });

        // Add Buttons on the left
        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setBackground(GUIColors.primaryColor);
        buttonsContainer.setBorder(new EmptyBorder(0,0,0,30));
        buttonsContainer.add(faButton);
        buttonsContainer.add(pdButton);
        buttonsContainer.add(actButton);
        this.add(buttonsContainer, BorderLayout.EAST);
    }
}
