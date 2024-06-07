package src;

import java.awt.*;
public class Platform
{
    private int x;
    private int y;
    private int w;
    private int h;
    private Color c;

    public Platform(Color color)
    {
        x = 600;
        y = 250;
        w = 250;
        h = 50;
        c = color;
    }
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
}

