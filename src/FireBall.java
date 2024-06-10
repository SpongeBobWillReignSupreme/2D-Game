package src;

import java.awt.*;
import java.awt.image.ImageObserver;

public class FireBall
{
    private static final int diam = 40;
    private static final Color color = Color.GRAY;

    private int fX;
    private static int fY;
    private int vX;


    public FireBall(Player p)
    {
        if(p.getMovingLeft())
            fX = p.getX() - p.getWidth()/2;
        else
            fX = p.getX() + p.getWidth()/2;
        vX = 0;

        fY = p.getY() + p.getHeight()/2 - diam/2;
    }

    public int getX() { return fX; }
    public int getY() { return fY; }
    public int getDiam() { return diam; }

    public void shootLeft() { vX = -20; }
    public void shootRight() { vX = 20; }

    public void act() { fX += vX; }

    public void drawFireBall(Graphics g2d, Image fireball, int screenWIDTH, Player p, ImageObserver obs)
    {
        int distToPlayerX;
        if(p.getMovingLeft())
        {
            distToPlayerX = p.getX() - fX + diam/2;
            g2d.drawImage(fireball, screenWIDTH/4 - p.getWidth()/2 - distToPlayerX, fY, diam, diam, obs);
        }
        else
        {
            distToPlayerX = fX - p.getX() - diam/2;
            g2d.drawImage(fireball, screenWIDTH/4 + p.getWidth()/2 + distToPlayerX, fY, diam, diam, obs);
        }
    }
}
