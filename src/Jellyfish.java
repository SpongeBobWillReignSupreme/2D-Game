package src;

import java.awt.*;

public class Jellyfish
{
    // Constants
    private static final int diam = 40;
    private static final Color col = Color.PINK;

    // Instance variables
    private int jX;
    private int jY;


    public Jellyfish(int x, int y)
    {
        jX = x;
        jY = y;
    }

    public int getX() { return jX; }
    public int getY() { return jY; }
    public int getDiam() { return diam; }

    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillOval(jX, jY, diam, diam);
    }

    public boolean checkCatch(Player p)
    {
        if(p.getX() + p.getWidth() >= jX + 5 && p.getX() <= jX + diam - 5 && p.getY() + p.getHeight() >= jY && p.getY() <= jY + diam)
        {
            return true;// We touched
        }
        else
        {
            return false; // We didn't touch
        }
    }
    public void drawSelf(Graphics g2d, Image jelly, int screenWIDTH, Player p)
    {
        int distToPlayerX = jX - p.getX();
        g2d.drawImage(jelly, screenWIDTH/4 - p.getWidth()/2 + distToPlayerX, jY, diam, diam, null);
    }
}
