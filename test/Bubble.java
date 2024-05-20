package test;

import java.awt.Graphics;
import java.awt.Color;

public class Bubble
{
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int diam;
    private Color col;

    public Bubble(int xCoor, int yCoor, int d, Color c)
    {
        x = xCoor;
        y = yCoor;
        vx = 1;
        vy = 1;
        diam = d;
        col = c;
    }
    public String toString()
    {
        return "Bubble at (" + x + ", " + y + ") with diameter of " + diam;
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
    private double distance(int x1, int y1, int x2, int y2)
    {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
    public int getCenterX()
    {
        return x + diam / 2;
    }
    public int getCenterY()
    {
        return y + diam / 2;
    }
    public void act(int w, int h)
    {
        //get the next x and y coordinates
        int nextX = x + vx;
        int nextY = y + vy;
        //if-statements to handle the Bubble bouncing off of the 4 walls
        if(nextX + diam > w || nextX < 0)
        {
            vx *= -1;
        }
        else if(nextY + diam > h || nextY < 0)
        {
            vy *= -1;
        }
        //updating x and y
        x += vx;
        y += vy;
    }
    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillOval(x, y, diam, diam);
    }
    public void handleCollision(Bubble anotherBubble)
    {
        //Getting the center of this Bubble and anotherBubble
        int thisXCenter = getCenterX();
        int thisYCenter = getCenterY();
        int aBXCenter = anotherBubble.getCenterX();
        int aBYCenter = anotherBubble.getCenterY();
        //getting the radius of this Bubble and anotherBubble
        int thisRadius = diam / 2;
        int anotherRadius = anotherBubble.getDiam() / 2;
        //checking if this Bubble Collided with anotherBubble
        if(distance(thisXCenter, thisYCenter, aBXCenter, aBYCenter) <= thisRadius + anotherRadius)
        {
            //calculating the velocities of this Bubble after colliding with anotherBubble
            vx = thisXCenter - aBXCenter;
            vy = thisYCenter - aBYCenter;

            //Slowing down the velocities.  otherwise they go crazy
            int maxSpeed = 5;

            if(vx<=-maxSpeed)
                vx = -maxSpeed;
            else if(vx>=maxSpeed)
                vx = maxSpeed;

            if(vy <= -maxSpeed)
                vy = -maxSpeed;
            else if(vy >= maxSpeed)
                vy = maxSpeed;
        }
    }
}