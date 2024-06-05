package src;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy
{
    private int eX;
    private int eY;
    private int eW;
    private int eH;
    private int vX;
    private int moveCount;
    private Color col;


    public Enemy(Color c, int x)
    {
        eX = x;
        eY = 365;
        eW = 50;
        eH = 70;
        vX = 1;
        moveCount = 0;
        col = c;
    }

    public int getX()
    {
        return eX;
    }
    public int getY()
    {
        return eY;
    }
    public int getWidth()
    {
        return eW;
    }
    public int getHeight()
    {
        return eH;
    }

    public void enemyMove()
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
            if(moveCount == 400)
                moveCount = 0;
        }
    }
    public boolean checkStomp(Player p)
    {
        //right side of player stomp
        if(p.getX() + p.getWidth() >= eX + 5 && p.getX() + p.getWidth() <= eX + eW - 5 && p.getY() + p.getHeight() >= eY + 5 && p.getY() + (p.getHeight()/6) <= eY + 5 || p.getX() >= eX + 5 && p.getX() <= eX + eW - 5 && p.getY() + p.getHeight() >= eY + 5&& p.getY() + p.getHeight()/6 <= eY + eH)
        {
            p.setVY(-15);
            p.setVX(-5);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkTouch(Player p)
    {
        if(p.getX() + p.getWidth() >= eX + 5 && p.getX() + p.getWidth() <= eX + eW - 5 && p.getY() + (p.getHeight() / 2) <= eY + eH && p.getY() + (p.getHeight() / 2) >= eY && p.getY() + p.getHeight() <= eY + eH)
        {
            p.setVY(-15);
            p.setVX(-5);

            return true;
        }
        else
        {
            return false;
        }
    }
    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillRect(eX, eY, eW, eH);
    }
}
