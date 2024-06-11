package src;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Enemy
{
    private static final Color col = Color.ORANGE;
    private static final int eW = 40;
    private static final int eH = 56;

    private int eX;
    private int eY;
    private int vX;
    private int moveCount;


    public Enemy(int x)
    {
        eX = x;
        eY = 389;
        vX = 1;
        moveCount = 0;
    }
    public Enemy(int x, int y)
    {
        eX = x;
        eY = y;
        vX = 1;
        moveCount = 0;
    }

    public int getX() { return eX; }
    public int getY() { return eY; }
    public int getWidth() { return eW; }
    public int getHeight() { return eH; }

    public void enemyMove()
    {
        if(eY == 389)
        {
            if(moveCount < 200)
            {
                eX += vX;
                moveCount++;
            }
            if(moveCount >= 200)
            {
                eX -= vX;
                moveCount++;
                if (moveCount == 400)
                    moveCount = 0;
            }
        }
        else
        {
            if(moveCount < 100)
            {
                eX += vX;
                moveCount++;
            }
            if(moveCount >= 100)
            {
                eX -= vX;
                moveCount++;
                if (moveCount == 200)
                    moveCount = 0;
            }
        }
    }
    public boolean checkStomp(Player p)
    {
        // If the players right side is in between the x of the right and left side of the enemy; the players bottom is touching the top of the enemy(5 pixels of leeway); OR the players left side is in between the x of the right and left side of the enemy; the players bottom is touching the top of the enemy(5 pixels of leeway)
        if(!p.getColor().equals(Color.RED) && (p.getX() + p.getWidth() >= eX && p.getX() + p.getWidth() <= eX + eW && p.getY() + p.getHeight() >= eY && p.getY() + p.getHeight() <= eY + eH/6 || p.getX() >= eX && p.getX() <= eX + eW && p.getY() + p.getHeight() >= eY && p.getY() + p.getHeight() <= eY + eH/6))
        {
            p.setVY(-10);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkTouch(Player p)
    {
        if((!p.getColor().equals(Color.RED)) && (p.getX() + p.getWidth() >= eX + 5 && p.getX() + p.getWidth() <= eX + eW && p.getY() + p.getHeight()/2 > eY && p.getY() + p.getHeight()/2 <= eY + eH) || (p.getX() >= eX && p.getX() <= eX + eW - 5 && p.getY() + p.getHeight()/2 > eY && p.getY() + p.getHeight()/2 <= eY + eH))
        {
            p.setVY(-10);

            if(eX > p.getX() && p.getVX() == 0)
            {
                p.setVX(-5);
            }
            else if(eX < p.getX() && p.getVX() == 0)
            {
                p.setVX(5);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkTouchFireBall(FireBall f)
    {
        if(f.getX() + f.getDiam() >= eX && f.getX() <= eX + eW && f.getY() + f.getDiam() >= eY && f.getY() <= eY + eH)
            return true;
        else
            return false;
    }
    public void drawEnemy(Graphics g)
    {
        g.setColor(col);
        g.fillRect(eX, eY, eW, eH);
    }
    public void drawEnemy(Graphics g2d, Image enemy, int screenWIDTH, Player p, ImageObserver obs)
    {
        int distToPlayerX = eX - p.getX();
        g2d.drawImage(enemy, screenWIDTH/4 - p.getWidth()/2 + distToPlayerX, eY - 10, eW + 20, eH + 20, obs);
    }
}