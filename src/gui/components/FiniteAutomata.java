package gui.components;
import engine.dfax.FA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class FiniteAutomata extends JPanel{
    ArrayList<Node> nodes = new ArrayList<>();
    FA finiteAutomata = new FA();
    Node selectedNodes[];
    int selectedIndex = 0;
    ArrayList<Integer[]> transitionsStart;
    ArrayList<Integer[]> transitions;
    ArrayList<String> transitionText;

    public FiniteAutomata(){
        setLayout(null);
        MyMouseAdapter adapter = new MyMouseAdapter(this);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        selectedNodes = new Node[2];
        transitions = new ArrayList<>();
        transitionsStart = new ArrayList<>();
        transitionText = new ArrayList<>();
    }

    public void paintComponent(Graphics g){
        for(int i = 0; i < transitions.size(); i++){
            g.setColor(new Color(0x000000));
            g.drawLine(transitionsStart.get(i)[0]+10, transitionsStart.get(i)[1]+10, transitions.get(i)[0]+10, transitions.get(i)[1]+10);
            g.fillOval(transitions.get(i)[0] +8, transitions.get(i)[1]+8, 5, 5);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawString(transitionText.get(i), (transitionsStart.get(i)[0] + transitions.get(i)[0] )/2, (transitionsStart.get(i)[1] + transitions.get(i)[1] )/2);
        }
        for(int i = 0; i < nodes.size(); i++){
            nodes.get(i).render(g);
        }
    }

    public void addNode(int x, int y){
        String text = "q" + nodes.size();
        nodes.add(new Node(text, x, y));
        finiteAutomata.addState(nodes.size());
        if(nodes.size() == 0){
            finiteAutomata.addStartState(0);
        }
        this.revalidate();
        this.repaint();
    }

    public ArrayList<Node> getNodes(){
        return nodes;
    }

    public void addTransition(String input){
        if(selectedIndex < 2) return;
        if(input.isBlank()) return;
        selectedIndex = 0;
        finiteAutomata.addTransition(Integer.parseInt(selectedNodes[0].text.substring(1)), input.charAt(0), Integer.parseInt(selectedNodes[1].text.substring(1)));
        transitionsStart.add(new Integer[]{selectedNodes[0].x, selectedNodes[0].y});
        transitions.add(new Integer[]{selectedNodes[1].x, selectedNodes[1].y});
        transitionText.add(input);
        selectedNodes[0].isSelected = false;
        selectedNodes[1].isSelected = false;
        this.revalidate();
        this.repaint();
    }

    public void convert(){
       finiteAutomata.saveToFile();
    }
}

class MyMouseAdapter implements MouseListener, MouseMotionListener {
    FiniteAutomata fa;
    Node dragging = null;

    public MyMouseAdapter(FiniteAutomata fa){
        this.fa = fa;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            ArrayList<Node> nodes = fa.getNodes();
            for(int i = 0; i < nodes.size(); i++) {
                boolean isXClose = e.getX() >= nodes.get(i).x && e.getX() <= (nodes.get(i).x + nodes.get(i).radius);
                boolean isYClose = e.getY() >= nodes.get(i).y && e.getY() <= (nodes.get(i).y + nodes.get(i).radius);
                if( isXClose && isYClose){
                    System.out.println("right click on node " + nodes.get(i).text);
                    fa.finiteAutomata.addFinalState(Integer.parseInt(nodes.get(i).text.substring(1)));
                    nodes.get(i).type = "final";
                    fa.revalidate();
                    fa.repaint();
                }
            }
        }else if(e.getClickCount() % 2 == 0 && !e.isConsumed()){
           e.consume();
           fa.addNode(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        ArrayList<Node> nodes = fa.getNodes();
        for(int i = 0; i < nodes.size(); i++) {
            boolean isXClose = e.getX() >= nodes.get(i).x && e.getX() <= (nodes.get(i).x + nodes.get(i).radius);
            boolean isYClose = e.getY() >= nodes.get(i).y && e.getY() <= (nodes.get(i).y + nodes.get(i).radius);
            if( isXClose && isYClose){
                dragging = nodes.get(i);
                if(!nodes.get(i).isSelected && fa.selectedIndex < 2){
                    nodes.get(i).isSelected = true;
                    fa.selectedNodes[fa.selectedIndex] = nodes.get(i);
                    fa.selectedIndex++;
                    fa.revalidate();
                    fa.repaint();
                }else if(nodes.get(i).isSelected){
                    nodes.get(i).isSelected = false;
                    if(fa.selectedNodes[0] == nodes.get(i)){
                        fa.selectedNodes[0] = fa.selectedNodes[1];
                    }
                    fa.selectedIndex--;
                    fa.revalidate();
                    fa.repaint();
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(dragging == null) return;
        dragging.x = e.getX();
        dragging.y = e.getY();
        fa.revalidate();
        fa.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e){
        if(dragging != null){
            dragging = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
