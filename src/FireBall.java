package src;

import java.awt.*;

public class FireBall
{
    private static final int diam = 50;
    private static final Color color = Color.GRAY;

    private int fX;
    private int fY;
    private int vX;


    public FireBall(Player p)
    {
        if(p.getMovingLeft())
            fX = p.getX() - p.getWidth()/2;
        else
            fX = p.getX() + p.getWidth()/2;
        vX = 0;

        fY = p.getY() + p.getHeight()/2;
    }

    //public int getVelo() { return xVelo; }
    public int getX() { return fX; }
    public int getY() { return fY; }
    public int getDiam() { return diam; }

    public void drawFireBall(Graphics g, int screenWIDTH, Player p)
    {
        g.setColor(color);
        int distToPlayerX;
        if(p.getMovingLeft())
        {
            distToPlayerX = p.getX() - fX;
            g.fillOval(screenWIDTH/2 - p.getWidth()/2 - distToPlayerX, p.getY() + p.getHeight()/2, diam, diam);
        }
        else
        {
            distToPlayerX = fX - p.getX();
            g.fillOval(screenWIDTH / 2 + p.getWidth() / 2 + distToPlayerX, p.getY() + p.getHeight() / 2, diam, diam);
        }
    }
    public void shootLeft()
    {
        vX = -20;
    }
    public void shootRight()
    {
        vX = 20;
    }
    public void act()
    {
        fX += vX;
    }
}
