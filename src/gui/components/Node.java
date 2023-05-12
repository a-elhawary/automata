package gui.components;
import javax.swing.JComponent;
import java.awt.*;
import java.util.ArrayList;

public class Node extends JComponent{
    String text = "";
    int radius = 70;
    String type = "normal";
    boolean isSelected = false;


    public int x;
    public int y;

    public Node(String text, int x, int y){
        this.text = text;
        this.x = x;
        this.y = y;
    }


    public void render(Graphics g){
        g.setColor(new Color(0xffffff));
        g.fillOval(x,y,radius,radius);
        if(isSelected){
            g.setColor(new Color(0x20aa20));
        }else{
            g.setColor(new Color(0x000000));
        }
        g.drawString(text, x+10, y+radius/2);
        g.drawOval(x,y,radius,radius);
        if(type == "final"){
            g.setColor(new Color(0x000000));
            g.drawOval(x+5,y+5,radius-10,radius-10);
        }
    }
}