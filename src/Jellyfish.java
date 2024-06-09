package src;

import java.awt.*;

public class Jellyfish
{
    private int jX;
    private int jY;
    private static final int diam = 50;
    private Color col;


    public Jellyfish(int x, int y)
    {
        jX = x;
        jY = y;
        col = Color.PINK;
    }

    public int getjX() { return jX; }
    public int getjY() { return jY; }
    public int getDiam() { return diam; }

    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillOval(jX, jY, diam, diam);
    }
    public void drawSelf (Graphics g, int screenWIDTH, Player p)
    {
        g.setColor(col);
        int distToPlayerX = jX - p.getX();
        g.fillOval(screenWIDTH/2 - p.getWidth()/2 + distToPlayerX, jY, diam, diam);
    }

    public boolean checkCatch(Player p)
    {
        if(p.getX() + p.getWidth() >= jX + 5 && p.getX() <= jX + diam - 5 && p.getY() + p.getHeight() >= jY && p.getY() <= jY + diam)
        {
            col = new Color(Color.HSBtoRGB(0.56f, 0.3f, 0.9f));
            return true;// We touched
        }
        else
        {
            return false; // We didn't touch
        }
    }
}
