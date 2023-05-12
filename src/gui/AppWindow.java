package gui;
import gui.components.AppBar;
import gui.screens.FAScreen;
import gui.screens.PDScreen;
import gui.screens.Screen;

import javax.swing.*;
import java.awt.BorderLayout;

public class AppWindow {
    public static JFrame frame;
    Screen screens[] = new Screen[2];
    int currentScreen = 0;

    public AppWindow(){
        // init screens
        this.screens[0] = new FAScreen();
        this.screens[1] = new PDScreen();

        // init window
        frame = new JFrame("Automata");
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // add AppBar
        AppBar appBar = new AppBar(this);
        frame.setLayout(new BorderLayout());
        frame.add(appBar, BorderLayout.NORTH);
        frame.add(this.screens[0], BorderLayout.CENTER);

        // show window
        frame.setVisible(true);
    }

    public void convert(){
        screens[currentScreen].convert();
    }

    public void setScreen(int screenIndex){
        if(screenIndex == currentScreen) return;
        frame.getContentPane().remove(this.screens[currentScreen]);
        frame.add(this.screens[screenIndex]);
        frame.revalidate();
        frame.repaint();
        this.currentScreen = screenIndex;
    }

    public static void main(String args[]){
        new AppWindow();
    }
}
