package src;

import java.awt.*;
public class Platform
{
    private int xPos;
    private int yPos;
    private int platW;
    private int platH;
    private Color color;

    public Platform(int x, int y, Color c)
    {
        xPos = x;
        yPos = y;
        platW = 200;
        platH = 40;
        color = c;
    }
    public Platform(int x, int y, int wVal, int hVal, Color c)
    {
        xPos = x;
        yPos = y;
        platW = wVal;
        platH = hVal;
        color = c;
    }

    public int getX() { return xPos; }
    public int getY() { return yPos; }
    public int getWidth() { return platW; }
    public int getHeight() { return platH; }

    public void drawSelf(Graphics g)
    {
        g.setColor(color);
        g.fillRect(xPos, yPos, platW, platH);
    }
    public void drawSelf (Graphics g2d, int screenWIDTH, Player p)
    {
        g2d.setColor(color);
        int distToPlayerX = xPos - p.getX();
        g2d.fillRoundRect(screenWIDTH/4 - p.getWidth()/2 + distToPlayerX, yPos, platW, platH, 20, 20);
    }
}

