package src;

import java.awt.*;

public class FloatingScore
{
    private static final Font font = new Font("Arial", Font.PLAIN, 20);

    private String text;
    private int x;
    private int y;
    private int vy;

    public FloatingScore(String text, int x, int y)
    {
        this.text = text;
        this.x = x;
        this.y = y;
        this.vy = -2; // adjust this value to control the speed of floating up
    }

    public int getY() { return y; }

    public void act()
    {
        y += vy;
    }

    public void draw(Graphics g, int screenWIDTH, Player p)
    {
        g.setColor(Color.WHITE);
        g.setFont(font);
        int distToPlayerX = x - p.getX();
        g.drawString(text, screenWIDTH/4 - p.getWidth()/2 + distToPlayerX, y);
    }

}
