package src;

import java.awt.*;

public class BlueJellyfish
{
    // Constants
    private static final int diam = 45;

    // Instance variables
    private int x;
    private int y;
    private Color col;


    public BlueJellyfish(int xC, int yC)
    {
        x = xC;
        y = yC;
        col = Color.BLUE;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiam() { return diam; }

    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillOval(x,y, diam, diam);
    }

    public void drawSelf (Graphics g, int screenWIDTH, Player p)
    {
        g.setColor(col);
        int distToPlayerX = x - p.getX();
        g.fillOval(screenWIDTH/4 - p.getWidth()/2 + distToPlayerX, y, diam, diam);
    }
    public boolean checkCatchBlue(Player p)
    {
        if(p.getX() + p.getWidth() >= x + 5 && p.getX() <= x + diam - 5 && p.getY() + p.getHeight() >= y && p.getY() <= y + diam)
        {
            return true;// We touched
        }
        else
        {
            return false; // We didn't touch
        }
    }
}