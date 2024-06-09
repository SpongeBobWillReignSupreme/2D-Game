package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    // Constant variables
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private static final int dirtHEIGHT = 450;
    private static final int grassHEIGHT = 435;
    private static final int defaultPlatWIDTH = 250;
    private static final int defaultPlatHEIGHT = 50;
    private static final int livesLeft = 3;
    private static final int scoreboxWIDTH = 180;
    private static final int scoreboxHEIGHT = 50;
    private static final int scoreboxX = 800;
    private static final int scoreboxY = 20;
    private static final int scoreboxFontSize = 30;
    private static final int jellyfishY = 180;
    private static final int TICKS = 60;
    private static final long lifeLostDelay = 2100;
    private static final long powerupDuration = 10000;

    // Instance variables
    private ArrayList<Jellyfish> regJellies = new ArrayList<>();
    private ArrayList<BlueJellyfish> blueJellies = new ArrayList<>();
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Player player = new Player();
    private ArrayList<FireBall> fireBalls = new ArrayList<>();
    private Platform dirt;
    private Platform grass;
    private int score;
    private int playerLives;
    private long lastLifeLostTime;
    private boolean powerupActive;
    private long powerupStartTime;

    public Game()
    {
        initializeGame();
        setupGUI();
    }

    private void initializeGame()
    {
        // Adding regular jellies
        regJellies.add(new Jellyfish(200, jellyfishY));
        regJellies.add(new Jellyfish(700, jellyfishY));
        // Adding blue jellies
        blueJellies.add(new BlueJellyfish(400, jellyfishY));
        // Adding the plats for the ground
        dirt = new Platform(0, dirtHEIGHT, WIDTH, HEIGHT, new Color(87, 52, 41));
        grass = new Platform(0, grassHEIGHT, WIDTH, dirtHEIGHT - grassHEIGHT, Color.GREEN);
        // Adding the platforms
        platforms.add(new Platform(600, 275, defaultPlatWIDTH, defaultPlatHEIGHT, Color.YELLOW));
        platforms.add(new Platform(100, 275, defaultPlatWIDTH, defaultPlatHEIGHT, Color.YELLOW));
        platforms.add(new Platform(1200, 275, defaultPlatWIDTH, defaultPlatHEIGHT, Color.YELLOW));
        // Adding the enemies
        enemies.add(new Enemy(450));
        enemies.add(new Enemy(1500));
        enemies.add(new Enemy(600));
        // Initializing the player lives to 3
        playerLives = livesLeft;
        // Initializing powerupActive to false
        powerupActive = false;
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
        int key = e.getKeyCode();

        player.movePlayer(e);

        if(key == 32 && powerupActive)
        {
            FireBall f = new FireBall(player);
            if(player.getMovingLeft())
                f.shootLeft();
            else if(player.getMovingRight())
                f.shootRight();
            fireBalls.add(f);
        }
    }

    public void paintComponent(Graphics g)
    {
        drawBackground(g);
        drawGround(g);
        drawJellyfishes(g);
        drawPlayer(g);
        drawFireBalls(g);
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
        // Drawing regular jellies
        for(Jellyfish jellyfish : regJellies)
        {
            jellyfish.drawSelf(g, WIDTH, player);
        }
        // Drawing blue jellies
        for(BlueJellyfish blueJellyfish : blueJellies)
        {
            blueJellyfish.drawSelf(g, WIDTH, player);
        }
    }

    private void drawPlayer(Graphics g)
    {
        player.drawPlayer(g, WIDTH);
    }

    private void drawFireBalls(Graphics g)
    {
        for(FireBall f: fireBalls)
            f.drawFireBall(g, WIDTH, player);
    }

    private void drawPlatforms(Graphics g)
    {
        for (Platform platform : platforms)
            platform.drawSelf(g, WIDTH, player);
    }

    private void drawEnemies(Graphics g)
    {
        for (Enemy enemy : enemies)
            enemy.drawSelf(g, WIDTH, player);
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
        handleJellyfishes(currentTime);
        handleEnemies(currentTime);
        handleFireBalls();

        repaint();
    }

    private void handleJellyfishes(long currentTime)
    {
        for(int i = 0; i < regJellies.size(); i++)
        {
            Jellyfish jellyfish = regJellies.get(i);
            if (jellyfish.checkCatch(player))
            {
                regJellies.remove(i);
                i--;
                score += 100;
            }
        }
        for(int i = 0; i < blueJellies.size(); i++)
        {
            BlueJellyfish blueJellyfish = blueJellies.get(i);
            if (blueJellyfish.checkCatchBlue(player))
            {
                blueJellies.remove(i);
                i--;
                score += 200;
                player.setColor(Color.BLUE);
                powerupActive = true;
                powerupStartTime = currentTime;
                System.out.println("Got powerup!");
            }
        }
        if(currentTime - powerupStartTime > powerupDuration && powerupActive)
        {
            powerupActive = false;
            player.setColor(Color.ORANGE);
        }
    }

    public void handleFireBalls()
    {
        for(FireBall f : fireBalls)
            f.act();

        for(int i = 0; i < fireBalls.size(); i++)
        {
            FireBall f = fireBalls.get(i);
            if (Math.abs(f.getX() - player.getX()) > WIDTH)
            {
                fireBalls.remove(i);
                i--;
            }
        }
    }

    private void handleEnemies(long currentTime)
    {
        for(int i = 0; i < enemies.size(); i++)
        {
            Enemy enemy = enemies.get(i);
            enemy.enemyMove();
            if(player.getColor().equals(Color.ORANGE))
            {
                if(enemy.checkTouch(player))
                {
                    handlePlayerTouchedByEnemy(currentTime);
                    System.out.println("Player lives: " + playerLives);
                }
                else if (enemy.checkStomp(player))
                {
                    enemies.remove(i);
                    score += 50;
                }
            }
            for(int a = 0; a < fireBalls.size(); a++)
            {
                FireBall f = fireBalls.get(a);
                if(enemy.checkTouchFireBall(f))
                {
                    enemies.remove(i);
                    fireBalls.remove(a);
                    score += 50;
                }
            }
            if(currentTime - lastLifeLostTime > lifeLostDelay && !player.getColor().equals(Color.BLUE))
            {
                player.setColor(Color.ORANGE);
            }
        }
    }

    private void handlePlayerTouchedByEnemy(long currentTime)
    {
        if(!powerupActive)
        {
            player.setColor(Color.RED);
            playerLives--;
            lastLifeLostTime = currentTime;
        }
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