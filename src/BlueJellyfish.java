package src;

import java.awt.*;

public class BlueJellyfish
{
    private int x;
    private int y;
    private int diam;
    private Color col;


    public BlueJellyfish(int xC, int yC)
    {
        x = xC;
        y = yC;
        diam = 50;
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