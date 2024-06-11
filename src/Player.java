package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Player extends JComponent
{
    // Constants
    private static final int pW = 40;
    private static final int pH = 80;

    //instance variables
    private int pX;
    private int pY;
    private int vX;
    private int vY;
    private boolean isJumping;
    private boolean onPlat;
    private Color color;
    private boolean movingLeft;
    private boolean movingRight;
    private long startSound;

    //Default Constructor
    public Player()
    {
        //initializing instance variables
        pX = 70;
        pY = 365;
        vX = 0;
        vY = 0;
        isJumping = false;
        onPlat = false;
        color = Color.ORANGE;
    }

    public int getWidth() { return pW; }
    public int getHeight() { return pH; }
    public int getX() { return pX; }
    public int getY() { return pY; }
    public Color getColor() { return color; }
    public int getVX() { return vX; }
    public int getVY() { return vY; }
    public boolean getIsJumping() { return isJumping; }
    public boolean getMovingLeft() { return movingLeft; }
    public boolean getMovingRight() { return movingRight; }
    public boolean getOnPlat() { return onPlat; }
    public void setColor(Color c) { color = c; }
    public void setX(int x) { pX = x; }
    public void setY(int y) { pY = y; }
    public void setVY(int yVel) { vY = yVel; }
    public void setVX(int xVel) { vX = xVel; }

    public void movePlayer(KeyEvent e)
    {
        long currentTime = System.currentTimeMillis();
        int key = e.getKeyCode();
        if(key == 65) // Left
        {
            vX = -7;
            if(currentTime - startSound > 200 && !isJumping)
            {
                playSound("step.wav");
                startSound = currentTime;
            }
            movingLeft = true;
            movingRight = false;
        }
        if(key == 68) // Right
        {
            vX = 7;
            if(currentTime - startSound > 200 && !isJumping)
            {
                playSound("step.wav");
                startSound = currentTime;
            }
            movingRight = true;
            movingLeft = false;
        }
        if(key == 87) // Up
        {
            if(!isJumping && vY == 0)
            {
                vY = -18;
                isJumping = true;
                onPlat = false;
            }
        }
        //  movement();
    }
    public void stopHorizontal(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == 65) // Left
        {
            vX = 0;
        }
        else if(key == 68) // Right
        {
            vX = 0;
        }
    }

    public void movement(int floor, ArrayList<Platform> plats)
    {
        pX += vX;
        pY += vY;
        //System.out.println(vY);

        //use a loop to go through all the platforms in plats and check if this is true for any of them.  If so exit loop.
        boolean dropped = false;
        for(int i = 0; i < plats.size(); i++)
        {
            Platform plat = plats.get(i);
            if(vY > 0 && pY + pH >= plat.getY() && pY + pH <= plat.getY() + plat.getHeight() && (pX >= plat.getX() && pX <= plat.getX() + plat.getWidth() - 5 || pX + pW >= plat.getX() + 5 && pX + pW <= plat.getX() + plat.getWidth()))
            {
                vY = 0;
                pY = plat.getY() - pH;
                isJumping = false;
                onPlat = true;
                i = plats.size();
            }
            else if(vY < 0 && pY <= plat.getY() + plat.getHeight() - 10 && pY >= plat.getY() && (pX >= plat.getX() && pX <= plat.getX() + plat.getWidth() - 5 || pX + pW >= plat.getX() + 5 && pX + pW <= plat.getX() + plat.getWidth()))
            {
                vY = 0; // stop upward movement
                pY = plat.getY() + plat.getHeight() - 10; // position player below the platform
                vY = 1; // start downward movement to simulate bounce
                i = plats.size();
                onPlat = false;
            }
            else
            {
                onPlat = false;
            }

            if (pY + pH >= floor && !onPlat)//landing on the floor
            {
                //System.out.println("on floor");
                vY = 0;
                if(vX != -7 && vX != 7)
                {
                    vX = 0;
                }
                pY = floor - pH;
                isJumping = false;
            }
            else if (!onPlat&&!dropped)
            {
                vY++;
                dropped = true;
            }
        }
    }

    public static synchronized void playSound(final String url)
    {
        new Thread(new Runnable()
        {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run()
            {
                try
                {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Game.class.getResourceAsStream("/src/SoundEffects/" + url));
                    clip.open(inputStream);
                    clip.start();
                }
                catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public void drawPlayer(Graphics g, int screenWIDTH)
    {
        // Drawing the body of the player
        g.setColor(color);
        g.fillRect(screenWIDTH/4 - pW/2, pY, pW, pH);
        // Drawing the head of the player
    }
}