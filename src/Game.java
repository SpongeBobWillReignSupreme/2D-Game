package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private static final int dirtHEIGHT = 450;
    private static final int grassHEIGHT = 435;
    private static final int livesLeft = 3;
    private static final int scoreboxWIDTH = 180;
    private static final int scoreboxHEIGHT = 50;
    private static final int scoreboxX = 800;
    private static final int scoreboxY = 20;
    private static final int scoreboxFontSize = 30;
    private static final int enemyX = 450;
    private static final int jellyfishX = 200;
    private static final int jellyfishY = 180;
    private static final int TICKS = 60;
    private static final long lifeLostDelay = 2100;

    private ArrayList<Jellyfish> pinkJellyfishes = new ArrayList<>();
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Player player = new Player();
    private Platform dirt;
    private Platform grass;
    private int score;
    private int playerLives;
    private long lastLifeLostTime;

    public Game()
    {
        initializeGame();
        setupGUI();
    }

    private void initializeGame()
    {
        pinkJellyfishes.add(new Jellyfish(jellyfishX, jellyfishY));
        pinkJellyfishes.add(new Jellyfish(700, jellyfishY));
        dirt = new Platform(0, dirtHEIGHT, WIDTH, HEIGHT, new Color(87, 52, 41));
        grass = new Platform(0, grassHEIGHT, WIDTH, dirtHEIGHT - grassHEIGHT, Color.GREEN);
        platforms.add(new Platform(Color.YELLOW));
        platforms.add(new Platform(100, 250, 250, 50, Color.YELLOW));
        enemies.add(new Enemy(Color.ORANGE, enemyX));
        playerLives = livesLeft;
    }

    private void setupGUI()
    {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Learning Graphics");
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);
    }

    public void keyPressed(KeyEvent e)
    {
        player.movePlayer(e);
    }

    public void paintComponent(Graphics g)
    {
        drawBackground(g);
        drawGround(g);
        drawJellyfishes(g);
        drawPlayer(g);
        drawPlatforms(g);
        drawEnemies(g);
        drawScorebox(g);
    }

    private void drawBackground(Graphics g)
    {
        g.setColor(new Color(Color.HSBtoRGB(0.56f, 0.3f, 0.9f)));
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void drawGround(Graphics g)
    {
        dirt.drawSelf(g);
        grass.drawSelf(g);
    }

    private void drawJellyfishes(Graphics g)
    {
        for (Jellyfish jellyfish : pinkJellyfishes)
        {
            jellyfish.drawSelf(g);
        }
    }

    private void drawPlayer(Graphics g)
    {
        player.drawPlayer(g);
    }

    private void drawPlatforms(Graphics g)
    {
        for (Platform platform : platforms)
        {
            platform.drawSelf(g);
        }
    }

    private void drawEnemies(Graphics g)
    {
        for (Enemy enemy : enemies)
        {
            enemy.drawSelf(g);
        }
    }

    private void drawScorebox(Graphics g)
    {
        g.setColor(Color.GRAY);
        g.fillRect(scoreboxX, scoreboxY, scoreboxWIDTH, scoreboxHEIGHT);
        Font font = new Font("Arial", Font.BOLD, scoreboxFontSize);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Score = " + score, scoreboxX + 5, scoreboxY + 40);
    }

    public void loop()
    {
        long currentTime = System.currentTimeMillis();
        player.movement(grassHEIGHT, platforms);
        handleJellyfishes();
        handleEnemies(currentTime);
        repaint();
    }

    private void handleJellyfishes()
    {
        for (int i = 0; i < pinkJellyfishes.size(); i++)
        {
            if (pinkJellyfishes.get(i).checkTouch(player))
            {
                pinkJellyfishes.remove(i);
                i--;
                score += 100;
            }
        }
    }

    private void handleEnemies(long currentTime)
    {
        for(int i = 0; i < enemies.size(); i++)
        {
            Enemy enemy = enemies.get(i);
            enemy.enemyMove();
            if(enemy.checkTouch(player) && !player.getColor().equals(Color.RED))
            {
                handlePlayerTouchedByEnemy(currentTime);
                System.out.println("Player lives: " + playerLives);
            }
            else if(enemy.checkStomp(player))
            {
                enemies.remove(i);
                score += 50;
            }
            if(currentTime - lastLifeLostTime > lifeLostDelay)
            {
                player.setColor(Color.ORANGE);
            }
        }
    }

    private void handlePlayerTouchedByEnemy(long currentTime)
    {
        player.setColor(Color.RED);
        playerLives--;
        lastLifeLostTime = currentTime;
    }

    public void fireBall()
    {
        //if()
    }

    public void keyReleased(KeyEvent e)
    {
        player.stopHorizontal(e);
    }

    public void start(final int ticks) {
        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    loop();
                    try {
                        Thread.sleep(1000 / ticks);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        gameThread.start();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start(TICKS);
    }

    // Empty methods required by the compiler
    public void keyTyped(KeyEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
}