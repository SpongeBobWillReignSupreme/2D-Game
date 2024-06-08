package src;

import java.awt.*;

public class FireBall
{
    private static final int diam = 40;
   // private static final int xVelo = 20;
    private static final Color color = Color.GRAY;

    private int fX;
    private int fY;
    private int vx;


    public FireBall(Player thePlayer)
    {

        fX = thePlayer.getX() + thePlayer.getWidth()/2;
        fY = thePlayer.getY() + thePlayer.getHeight()/2;
        vx = 0;
    }

    //public int getVelo() { return xVelo; }

    public void drawFireBall(Graphics g, int screenWIDTH, Player p)
    {
        g.setColor(color);
        int distToPlayerX = fX - p.getX();
        g.fillOval(screenWIDTH/2 + p.getWidth()/2 + distToPlayerX, p.getY() + p.getHeight()/2 , diam, diam);

    }
    public void shootRight()
    {
        vx = 20;
    }
    public void act()
    {
        fX += vx;
    }
    public int getX()
    {
        return fX;
    }
}
