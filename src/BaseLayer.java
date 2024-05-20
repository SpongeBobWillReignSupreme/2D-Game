package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GradientPaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;

public class BaseLayer extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    //instance variables
    private int WIDTH;
    private int HEIGHT;
    private int rX;
    private int rY;
    private int rW;
    private int rH;

    public BaseLayer()
    {
        //initializing instance variables
        WIDTH = 1000;
        HEIGHT = 500;
        rX = 300;
        rY = 300;
        rW = 50;
        rH = 100;

        JFrame gui = new JFrame(); //This makes the gui box
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
        gui.setTitle("Learning Graphics"); //This is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30)); //Setting the size for gui
        gui.setResizable(false); //Makes it so the gui cant be resized
        gui.getContentPane().add(this); //Adding this class to the gui

        /*
        Declare buttons here
        */

        gui.pack(); // Packs everything together
        gui.setLocationRelativeTo(null); // Makes so the gui opens in the center of the screen
        gui.setVisible(true); // Makes the gui visible
        gui.addKeyListener(this); // Stating that this object will listen to key events
        gui.addMouseListener(this); // Stating that this object will listen to mouse events
        gui.addMouseMotionListener(this); // stating that this object will acknowledge mouse movement
    }

    //This method will acknowledge user input
    public void keyPressed(KeyEvent e)
    {
        //getting the key pressed

        //moving the rectangle

    }
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {
        // Drawing a Blue Rectangle to be the background
        g.setColor(new Color(Color.HSBtoRGB(0.56f, 0.3f, 0.9f)));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // Making the ground
        g.setColor(new Color(110, 67, 55));
        g.fillRect(0, HEIGHT - 50, WIDTH, 50);
        //Making the grass
        g.setColor(Color.GREEN);
        g.fillRect(0, HEIGHT - 65, WIDTH, 15);
    }
    public void loop()
    {
        //making the autonomous circle move

        //handling when the circle collides with the edges

        //handling the collision of the circle with the rectangle

        //Do not write below this
        repaint();
    }
    //These methods are required by the compiler.
    //You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyReleased(KeyEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mouseMoved(MouseEvent e)
    {
    }
    public void mouseDragged(MouseEvent e)
    {
    }
    public void start(final int ticks){
        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        gameThread.start();
    }

    public static void main(String[] args)
    {
        BaseLayer g = new BaseLayer();
        g.start(60);
    }
}
