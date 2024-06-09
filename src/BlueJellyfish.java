package src;

import java.awt.*;

public class BlueJellyfish
{
    // Constants
    private static final int diam = 45;
    private static final Color col = Color.BLUE;

    // Instance variables
    private int bJX;
    private int bJY;


    public BlueJellyfish(int x, int y)
    {
        bJX = x;
        bJY = y;
    }

    public int getbJX() { return bJX; }
    public int getY() { return bJY; }
    public int getDiam() { return diam; }

    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillOval(bJX,bJY, diam, diam);
    }

    public boolean checkCatchBlue(Player p)
    {
        if(p.getX() + p.getWidth() >= bJX + 5 && p.getX() <= bJX + diam - 5 && p.getY() + p.getHeight() >= bJY && p.getY() <= bJY + diam)
        {
            return true;// We touched
        }
        else
        {
            return false; // We didn't touch
        }
    }
    public void drawSelf (Graphics g, int screenWIDTH, Player p)
    {
        g.setColor(col);
        int distToPlayerX = bJX - p.getX();
        g.fillOval(screenWIDTH/4 - p.getWidth()/2 + distToPlayerX, bJY, diam, diam);
    }
}