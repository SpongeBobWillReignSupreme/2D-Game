package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;

public class LearningGraphics extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    //instance variables
    private int WIDTH;
    private int HEIGHT;
    private int rX;
    private int rY;
    private int rW;
    private int rH;
    private int cX;
    private int cY;
    private int diam;
    private int cVx;
    private int cVy;

    private Bubble bub = new Bubble(100, 100, 50, Color.RED);
    private Bubble anotherBub = new Bubble(200, 200, 50, Color.GREEN);
    private Bubble bubArray[][] = new Bubble[4][3]; // Creating a 2D array of Bubbles

    //Default Constructor
    public LearningGraphics()
    {
        //initializing instance variables
        WIDTH = 1000;
        HEIGHT = 500;
        rX = 300;
        rY = 300;
        rW = 50;
        rH = 100;
        cX = 500;
        cY = 300;
        diam = 70;
        cVx = 5;
        cVy = 5;

        // Filling the 2D array of Bubbles with Bubbles and initializing each Bubble
        for(int i = 0; i < bubArray.length; i++)
        {
            for(int j = 0; j < bubArray[i].length; j++)
            {
                bubArray[i][j] = new Bubble((int)(Math.random() * 1001), (int)(Math.random() * 501), (int)(Math.random() * 31) + 30, new Color(Color.HSBtoRGB((float)(Math.random()), 1f, 0.85f)));
            }
        }

        //Setting up the GUI
        JFrame gui = new JFrame(); // This makes the gui box
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes sure program can close
        gui.setTitle("Learning Graphics"); // This is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30)); // Setting the size for gui
        gui.setResizable(false); // Makes it so the gui can't be resized
        gui.getContentPane().add(this); // Adding this class to the gui

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

    // This method will acknowledge user input
    public void keyPressed(KeyEvent e)
    {
        //getting the key pressed
        int key = e.getKeyCode();
        System.out.println(key);
        //moving the rectangle
        if(key == 38)
        {
            rY -= 10;
        }
        else if(key == 40)
        {
            rY += 10;
        }
        else if(key == 37)
        {
            rX -= 10;
        }
        else if(key == 39)
        {
            rX += 10;
        }
    }
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {
        //Drawing a Blue Rectangle to be the background
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //Drawing Hello World!! at the center of the GUI
        Font font = new Font("Arial", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Hello World!!", WIDTH / 2, HEIGHT / 2);
        //Drawing the user-controlled rectangle
        g.setColor(Color.RED);
        g.fillRect(rX, rY, rW, rH);
        //Drawing the autonomous circle
        g.setColor(Color.YELLOW);
        g.fillOval(cX, cY, diam, diam);
        // Calling the drawSelf() method from Bubble class
        //bub.drawSelf(g);
        //anotherBub.drawSelf(g);
        // Iterating through the 2D array of Bubbles to draw them using a ranged for loop
        for(Bubble[] bubbles : bubArray)
        {
            for(Bubble bubble : bubbles)
            {
                bubble.drawSelf(g);
            }
        }
    }
    public void loop()
    {
        //making the autonomous circle move
        cX += cVx;
        cY += cVy;
        //handling when the circle collides with the edges
        int nextX = cX + cVx;
        int nextY = cY + cVy;
        if(nextY + diam > HEIGHT || nextY < 0)
        {
            cVy *= -1;
        }
        else if(nextX + diam > WIDTH || nextX < 0)
        {
            cVx *= -1;
        }
        //handling the collision of the circle with the rectangle
        if(detectCollision())
        {
            cVx *= -1;
            cVy *= -1;
        }
        // Calling the act() method from Bubble Class
        //bub.act(WIDTH, HEIGHT);
        //anotherBub.act(WIDTH, HEIGHT);
        // Iterating through the 2D array of Bubbles to call the act() method on each Bubble using a ranged for loop
        for(Bubble[] bubbles : bubArray)
        {
            for(Bubble bubble : bubbles)
            {
                bubble.act(WIDTH, HEIGHT);
            }
        }
        // Calling the handleCollision() method from Bubble Class
        //bub.handleCollision(anotherBub);
        //anotherBub.handleCollision(bub);

        // Iterating through the 2D array of Bubbles to call the handleCollision() method on each Bubble using a ranged for loop
        for(Bubble[] bubbles : bubArray)
        {
            for(Bubble bubble : bubbles)
            {
                for(Bubble[] value : bubArray)
                {
                    for(Bubble placeholder : value)
                    {
                        if(bubbles != value || bubble != placeholder)
                        {
                            bubble.handleCollision(placeholder);
                        }
                    }
                }
            }
        }
        //Do not write below this
        repaint();
    }
    public double distance(int x1, int y1, int x2, int y2)
    {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
    public boolean detectCollision()
    {
        int radius = diam / 2;
        int nextX = cX + cVx;
        int nextY = cY + cVy;
        int centerX = nextX + radius;
        int centerY = nextY + radius;
        boolean output = false;

        for(int x = rX; x <= rX + rW; x++)
        {
            for(int y = rY; y <= rY + rH; y++)
            {
                if(distance(x, y, centerX, centerY) < radius)
                {
                    output = true;
                }
            }
        }
        return output;
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
        LearningGraphics g = new LearningGraphics();
        g.start(60);
    }
}