package src;

import java.awt.*;

public class Jellyfish
{
    private int x;
    private int y;
    private int diam;
    private Color col;


    public Jellyfish(int xC, int yC)
    {
        x = xC;
        y = yC;
        diam = 50;
        col = Color.PINK;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getDiam() {return diam;}

    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillOval(x,y, diam, diam);
    }

    public boolean checkTouch(Player p)
    {
        if(p.getX() + p.getWidth() >= x + 5 && p.getX() <= x + diam - 5 && p.getY() + p.getHeight() >= y && p.getY() <= y + diam)
        {
            col = new Color(Color.HSBtoRGB(0.56f, 0.3f, 0.9f));
            return true;// We touched
        }
        return false; // We didn't touch
    }
}
