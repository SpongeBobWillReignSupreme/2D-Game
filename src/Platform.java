package src;

import java.awt.*;
public class Platform
{
    private int x;
    private int y;
    private int w;
    private int h;
    private Color c;

    public Platform(int xPos, int yPos, int wVal, int hVal, Color color)
    {
        x = xPos;
        y = yPos;
        w = wVal;
        h = hVal;
        c = color;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }

    public void drawSelf (Graphics g)
    {
        g.setColor(c);
        g.fillRect(x, y, w, h);
    }
    public void drawSelf (Graphics g, int screenWIDTH, Player p)
    {
        g.setColor(c);
        int distToPlayerX = x - p.getX();
        g.fillRect(screenWIDTH/2 - p.getWidth()/2 + distToPlayerX, y, w, h);
    }
}

