package src;

import java.awt.Graphics;
import java.awt.Color;

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

    public String toString()
    {
        return "(" + x + "," + y + ")\n" + diam;
    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getDiam()
    {
        return diam;
    }
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
            return true;//we touched
        }
        return false; //we didn't touch
    }
   /* public void checkStomp(Player p, ArrayList<Jellyfish> jellies)
    {
        for(int i = 0; i < jellies.size(); i++)
        {
            Jellyfish jelly = jellies.get(i);
            if(p.getX() + p.getWidth() >= x + 5 && p.getX() <= x + diam - 5 && p.getY() + p.getHeight() >= y && p.getY() <= y + diam)
            {
                jellies.remove(i);
                System.out.println("Touched jelly");
            }
        }
    }*/
}

