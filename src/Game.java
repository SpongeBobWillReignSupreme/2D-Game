package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    // CONSTANT VARIABLES
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private static final int dirtHEIGHT = 460;
    private static final int grassHEIGHT = 445;
    private static final int livesLeft = 3;
    private static final int scoreboxWIDTH = 195;
    private static final int scoreboxHEIGHT = 50;
    private static final int scoreboxX = 785;
    private static final int scoreboxY = 10;
    private static final int scoreboxFontSize = 30;
    private static final int defaultJellyfishY = 250;
    private static final int TICKS = 60;
    private static final long lifeLostDelay = 2100;
    private static final long powerupDuration = 8000;
    private static final int fireBallCooldown = 800;
    private static final int interval = 100;
    private static final int gameTimer = 90000;
    private static final int reachEndX = 10000;
    //private static final AudioClip fireBall = Applet.newAudioClip(Game.class.getResource("fire-spell.wav"));

    // INSTANCE VARIABLES
    private ArrayList<Jellyfish> regJellies = new ArrayList<>();
    private ArrayList<BlueJellyfish> blueJellies = new ArrayList<>();
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<FireBall> fireBalls = new ArrayList<>();
    private ArrayList<FloatingScore> floatingScores = new ArrayList<>();
    private Player player = new Player();
    private Platform dirt;
    private Platform grass;
    private int score;
    private int playerLives;
    private long lastLifeLostTime;
    private boolean powerupActive;
    private long powerupStartTime;
    private long lastFireBallTime;
    private boolean isGameOver;
    private long lastFootstepTime = 0;
    private long infot;
    private long gameStartTime;
    private int mouseX;
    private int mouseY;
    private int mouseDiam;
    // SCENES
    private boolean onMenu;
    private boolean onLevelSelect;
    private boolean onLevel1;
    private boolean onLevel2;

    public Game()
    {
        initializeGame();
        setupGUI();
    }

    private void initializeGame()
    {
        // Initializing the player lives to 3
        playerLives = livesLeft;
        // Initializing powerupActive to false
        powerupActive = false;
        isGameOver = false;
        mouseX = -10;
        mouseY = -10;
        mouseDiam = 10;
        onMenu = true;
        onLevelSelect = false;
        onLevel1 = false;
        onLevel2 = false;
        setupLevel();
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

        long currentTime = System.currentTimeMillis();
        if(key == 32 && powerupActive)
        {
            FireBall f = new FireBall(player);
            if(currentTime - lastFireBallTime > fireBallCooldown)
            {
                if(player.getMovingLeft())
                    f.shootLeft();
                else if(player.getMovingRight())
                    f.shootRight();
                fireBalls.add(f);
                lastFireBallTime = currentTime;
                //fireBall.play(); // Play the sound effect on JDK 9 or earlier
                playSound("fire-spell.wav");
            }
        }
    }

    public void setupLevel()
    {
        if(!onMenu && !onLevelSelect)
        {
            if(onLevel1)
            {
                // Adding regular jellies
                regJellies.add(new Jellyfish(180, defaultJellyfishY));
                regJellies.add(new Jellyfish(680, defaultJellyfishY));
                regJellies.add(new Jellyfish(1280, defaultJellyfishY));
                regJellies.add(new Jellyfish(1680, defaultJellyfishY));
                regJellies.add(new Jellyfish(2780, defaultJellyfishY));
                regJellies.add(new Jellyfish(3480, defaultJellyfishY));
                regJellies.add(new Jellyfish(4080, defaultJellyfishY));
                regJellies.add(new Jellyfish(5080, defaultJellyfishY));
                regJellies.add(new Jellyfish(5680, defaultJellyfishY));
                regJellies.add(new Jellyfish(6180, defaultJellyfishY));
                regJellies.add(new Jellyfish(7180, defaultJellyfishY));
                regJellies.add(new Jellyfish(7680, defaultJellyfishY));
                regJellies.add(new Jellyfish(8480, defaultJellyfishY));
                regJellies.add(new Jellyfish(9380, defaultJellyfishY));
                // Adding blue jellies
                blueJellies.add(new BlueJellyfish(400, defaultJellyfishY));
                blueJellies.add(new BlueJellyfish(1980, 150));
                blueJellies.add(new BlueJellyfish(4380, 150));
                blueJellies.add(new BlueJellyfish(6480, 150));
                blueJellies.add(new BlueJellyfish(8780, 150));
                // Adding the platforms
                platforms.add(new Platform(100, 310, Color.YELLOW));
                platforms.add(new Platform(600, 310, Color.YELLOW));
                platforms.add(new Platform(1200, 310, Color.YELLOW));
                platforms.add(new Platform(1600, 310, Color.YELLOW));
                platforms.add(new Platform(1900, 210, Color.YELLOW));
                platforms.add(new Platform(2700, 310, Color.YELLOW));
                platforms.add(new Platform(3400, 310, Color.YELLOW));
                platforms.add(new Platform(4000, 310, Color.YELLOW));
                platforms.add(new Platform(4300, 210, Color.YELLOW));
                platforms.add(new Platform(5000, 310, Color.YELLOW));
                platforms.add(new Platform(5600, 310, Color.YELLOW));
                platforms.add(new Platform(6100, 310, Color.YELLOW));
                platforms.add(new Platform(6400, 210, Color.YELLOW));
                platforms.add(new Platform(7100, 310, Color.YELLOW));
                platforms.add(new Platform(7600, 310, Color.YELLOW));
                platforms.add(new Platform(8400, 310, Color.YELLOW));
                platforms.add(new Platform(8700, 210, Color.YELLOW));
                platforms.add(new Platform(9300, 310, Color.YELLOW));
                // Adding the enemies
                enemies.add(new Enemy(440));
                enemies.add(new Enemy(700));
                enemies.add(new Enemy(1490));
                enemies.add(new Enemy(2200));
                enemies.add(new Enemy(3000));
                enemies.add(new Enemy(3800));
                enemies.add(new Enemy(4600));
                enemies.add(new Enemy(5100));
                enemies.add(new Enemy(5900));
                enemies.add(new Enemy(6500));
                enemies.add(new Enemy(7100));
                enemies.add(new Enemy(7700));
                enemies.add(new Enemy(8300));
                enemies.add(new Enemy(8800));
                enemies.add(new Enemy(9400));
            }
            else if(onLevel2)
            {

            }
            // Adding the plats for the ground

        }
        dirt = new Platform(0, dirtHEIGHT, WIDTH, HEIGHT, new Color(87, 52, 41));
        grass = new Platform(0, grassHEIGHT, WIDTH, dirtHEIGHT - grassHEIGHT, Color.GREEN);
    }

    public void paintComponent(Graphics g)
    {
        long currentTime = System.currentTimeMillis();
        // For images
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        // Regular shapes
        drawBackground(g2d);
        drawGround(g);
        drawJellyfishes(g);
        //drawPlayer(g);
        drawEnemies(g);
        drawFireBalls(g2d);
        drawPlatforms(g);
        drawPlayer(g2d);
        drawFloatingScores(g);
        drawScorebox(g);
        drawTimer(g2d);
        drawHearts(g2d);
        drawEndScene(g, currentTime);
        drawMenu(g2d);
        drawLevelSelect(g2d);
    }

    private void drawBackground(Graphics g2d)
    {
        if(!isGameOver)
        {
            ImageIcon backgroundIcon = new ImageIcon(Game.class.getResource("Images/background.png"));
            Image background = backgroundIcon.getImage();
            g2d.drawImage(background, 0, 0, WIDTH, grassHEIGHT, null);
        }
    }

    private void drawGround(Graphics g)
    {
        if(!isGameOver)
        {
            dirt.drawSelf(g);
            grass.drawSelf(g);
        }
    }

    private void drawJellyfishes(Graphics g2d)
    {
        if(!isGameOver)
        {
            // Drawing regular jellies
            ImageIcon jellyIcon = new ImageIcon(Game.class.getResource("Images/jelly.png"));
            Image jelly = jellyIcon.getImage();
            for (Jellyfish j : regJellies)
            {
                j.drawSelf(g2d, jelly, WIDTH, player);
            }
            // Drawing blue jellies
            ImageIcon blueJellyIcon = new ImageIcon(Game.class.getResource("Images/bluejelly.png"));
            Image blueJelly = blueJellyIcon.getImage();
            for (BlueJellyfish blueJ : blueJellies)
            {
                blueJ.drawSelf(g2d, blueJelly,WIDTH, player);
            }
        }
    }

    private void drawFireBalls(Graphics g2d)
    {
        if(!isGameOver)
        {
            ImageIcon fireballIcon = new ImageIcon(Game.class.getResource("Images/fireball.png"));
            Image fireball = fireballIcon.getImage();
            for(FireBall f : fireBalls)
            {
                f.drawFireBall(g2d, fireball, WIDTH, player, null);
            }
        }
    }

    private void drawPlatforms(Graphics g)
    {
        if(!isGameOver)
        {
            for(Platform platform : platforms)
            {
                platform.drawSelf(g, WIDTH, player);
            }
        }
    }

    private void drawEnemies(Graphics g2d)
    {
        if(!isGameOver)
        {
            ImageIcon enemyIcon = new ImageIcon(Game.class.getResource("Images/vital.png"));
            Image enemyI = enemyIcon.getImage();
            for(Enemy e : enemies)
            {
                e.drawEnemy(g2d, enemyI, WIDTH, player, null);
            }
        }
    }

    private void drawPlayer(Graphics2D g2d)
    {
        if(!isGameOver)
        {
            int pWidth = player.getWidth() + 80;
            int pHeight = player.getHeight() + 20;

            int netWidth = 80;
            int netHeight = 50;
            int y = player.getY();

            ImageIcon playerIcon;
            if(powerupActive)
                playerIcon = new ImageIcon(Game.class.getResource("Images/poweredaron.png"));
            else if(player.getColor().equals(Color.RED))
                playerIcon = new ImageIcon(Game.class.getResource("Images/aronhurt.png"));
            else
                playerIcon = new ImageIcon(Game.class.getResource("Images/aron.png"));
            Image player = playerIcon.getImage();
            g2d.drawImage(player, WIDTH / 4 - pWidth / 2, y - 10, pWidth, pHeight, null);

            ImageIcon netIcon = new ImageIcon(Game.class.getResource("Images/jellynet.png"));
            Image net = netIcon.getImage();
            g2d.drawImage(net, WIDTH / 4 - pWidth / 2 - 20, y - 10, netWidth, netHeight, null);
        }
    }

    private void drawFloatingScores(Graphics g)
    {
        for(FloatingScore fs : floatingScores)
        {
            fs.draw(g, WIDTH, player);
        }
    }

    private void drawScorebox(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRoundRect(scoreboxX - 2, scoreboxY - 2, scoreboxWIDTH + 4, scoreboxHEIGHT + 4, 20, 20);
        g.setColor(Color.WHITE);
        g.fillRoundRect(scoreboxX, scoreboxY, scoreboxWIDTH, scoreboxHEIGHT, 20, 20);
        Font font = new Font("Arial", Font.BOLD, scoreboxFontSize);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, scoreboxX + 5, scoreboxY + 35);
    }
    private void drawTimer(Graphics2D g2d)
    {
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(scoreboxX - 172, scoreboxY - 2, scoreboxWIDTH - 31, scoreboxHEIGHT + 4, 20, 20);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(scoreboxX - 170, scoreboxY, scoreboxWIDTH - 35, scoreboxHEIGHT, 20, 20);
        long currentTime = System.currentTimeMillis();
        long timeLeft = gameTimer - (currentTime - gameStartTime);
        int seconds = (int)timeLeft / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String time = minutes + ":" + seconds;
        Font font = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Time: " + time, scoreboxX - 165, scoreboxY + 35);
    }
    private void drawHearts(Graphics2D g2d)
    {
        ImageIcon heartIcon = new ImageIcon(Game.class.getResource("Images/heart.png"));
        Image heart = heartIcon.getImage();
        if(playerLives == 3)
        {
            g2d.drawImage(heart, 30, 30, 40, 40, null);
            g2d.drawImage(heart, 80, 30, 40, 40, null);
            g2d.drawImage(heart, 130, 30, 40, 40, null);
        }
        else if(playerLives == 2)
        {
            g2d.drawImage(heart, 30, 30, 40, 40, null);
            g2d.drawImage(heart, 80, 30, 40, 40, null);
        }
        else if(playerLives == 1)
        {
            g2d.drawImage(heart, 30, 30, 40, 40, null);
        }
    }
    public void drawEndScene(Graphics g2d, long currentTime)
    {
        if(isGameOver)
        {
            if(playerLives <= 0)
            {
                ImageIcon lossPage = new ImageIcon(Game.class.getResource("Images/loss.png"));
                Image loss = lossPage.getImage();
                g2d.drawImage(loss, 0, 0, WIDTH, HEIGHT, null);
                Font font = new Font("Times New Roman", Font.BOLD, 100);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Game Over", WIDTH/2 - 275, HEIGHT/2 - 150);
                Font font2 = new Font("Arial", Font.BOLD, 25);
                g2d.setFont(font2);
                g2d.drawString("You Ran Out Of Lives", WIDTH/2 - 140, HEIGHT/2 - 100);

                // Adding try again button
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(WIDTH/2 - 143, HEIGHT/2 - 55, 250, 85, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(WIDTH/2 - 138, HEIGHT/2 - 50, 240, 75, 20, 20);
                g2d.setColor(Color.BLACK);
                Font font3 = new Font("Arial", Font.BOLD, 50);
                g2d.setFont(font3);
                g2d.drawString("Try Again", WIDTH/2 - 133, HEIGHT/2 + 5);

                // Adding quit button
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(WIDTH/2 - 105, HEIGHT/2 + 55, 175, 85, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(WIDTH/2 - 100, HEIGHT/2 + 60, 165, 75, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.setFont(font3);
                g2d.drawString("Quit", WIDTH/2 - 73, HEIGHT/2 + 115);
            }
            else if(currentTime - gameStartTime > gameTimer)
            {
                ImageIcon lossPage = new ImageIcon(Game.class.getResource("Images/loss.png"));
                Image loss = lossPage.getImage();
                g2d.drawImage(loss, 0, 0, WIDTH, HEIGHT, null);
                Font font = new Font("Times New Roman", Font.BOLD, 100);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Game Over", WIDTH/2 - 275, HEIGHT/2 - 150);
                Font font2 = new Font("Arial", Font.BOLD, 25);
                g2d.setFont(font2);
                g2d.drawString("You Ran Out Of Time", WIDTH/2 - 140, HEIGHT/2 - 100);

                // Adding try again button
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(WIDTH/2 - 143, HEIGHT/2 - 55, 250, 85, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(WIDTH/2 - 138, HEIGHT/2 - 50, 240, 75, 20, 20);
                g2d.setColor(Color.BLACK);
                Font font3 = new Font("Arial", Font.BOLD, 50);
                g2d.setFont(font3);
                g2d.drawString("Try Again", WIDTH/2 - 133, HEIGHT/2 + 5);

                // Adding quit button
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(WIDTH/2 - 105, HEIGHT/2 + 55, 175, 85, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(WIDTH/2 - 100, HEIGHT/2 + 60, 165, 75, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.setFont(font3);
                g2d.drawString("Quit", WIDTH/2 - 73, HEIGHT/2 + 115);
            }
            else if(player.getX() >= reachEndX)
            {
                ImageIcon winPage = new ImageIcon(Game.class.getResource("Images/win.png"));
                Image win = winPage.getImage();
                g2d.drawImage(win, 0, 0, WIDTH, HEIGHT, null);
                Font font = new Font("Times New Roman", Font.BOLD, 100);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("You Won", WIDTH/2 - 200, HEIGHT/2 - 150);

                // Adding play again button
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(WIDTH/2 - 143, HEIGHT/2 - 55, 250, 85, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(WIDTH/2 - 138, HEIGHT/2 - 50, 240, 75, 20, 20);
                g2d.setColor(Color.BLACK);
                Font font3 = new Font("Arial", Font.BOLD, 50);
                g2d.setFont(font3);
                g2d.drawString("Try Again", WIDTH/2 - 133, HEIGHT/2 + 5);

                // Adding quit button
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(WIDTH/2 - 105, HEIGHT/2 + 55, 175, 85, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(WIDTH/2 - 100, HEIGHT/2 + 60, 165, 75, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.setFont(font3);
                g2d.drawString("Quit", WIDTH/2 - 73, HEIGHT/2 + 115);
            }
        }
    }
    public void drawMenu(Graphics2D g2d)
    {
        if(onMenu)
        {
            // Adding Title
            ImageIcon menuPage = new ImageIcon(Game.class.getResource("Images/win.png"));
            Image menu = menuPage.getImage();
            g2d.setColor(Color.GRAY);
            g2d.drawImage(menu,0, 0, WIDTH, HEIGHT, null);
            Font font = new Font("Times New Roman", Font.BOLD, 100);
            g2d.setFont(font);
            g2d.setColor(Color.PINK);
            g2d.drawString("Jellyfish Jam", WIDTH/2 - 275, HEIGHT/2 - 100);

            // Adding play button
            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(WIDTH/2 - 105, HEIGHT/2 - 55, 210, 85, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(WIDTH/2 - 100, HEIGHT/2 - 50, 200, 75, 20, 20);
            g2d.setColor(Color.BLACK);
            Font font2 = new Font("Arial", Font.BOLD, 50);
            g2d.setFont(font2);
            g2d.drawString("Play", WIDTH/2 - 57, HEIGHT/2 + 5);

            // Adding quit button
            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(WIDTH/2 - 105, HEIGHT - HEIGHT/4 - 5, 210, 85, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(WIDTH/2 - 100, HEIGHT - HEIGHT/4, 200, 75, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Quit", WIDTH/2 - 58, HEIGHT - HEIGHT/4 + 55);
        }
    }
    public void drawLevelSelect(Graphics2D g2d)
    {
        if(onLevelSelect)
        {
            ImageIcon winPage = new ImageIcon(Game.class.getResource("Images/win.png"));
            Image win = winPage.getImage();
            g2d.drawImage(win, 0, 0, WIDTH, HEIGHT, null);

            // Drawing the select title
            Font font = new Font("Times New Roman", Font.BOLD, 100);
            g2d.setFont(font);
            g2d.setColor(Color.PINK);
            g2d.drawString("Select Level", WIDTH / 2 - 260, HEIGHT / 2 - 95);

            // Drawing the level 1 button
            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(WIDTH/2 - 105, HEIGHT/2 - 50, 210, 85, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(WIDTH/2 - 100, HEIGHT/2 - 45, 200, 75, 20, 20);
            g2d.setColor(Color.BLACK);
            Font font2 = new Font("Arial", Font.BOLD, 50);
            g2d.setFont(font2);
            g2d.drawString("Level 1", WIDTH/2 - 83, HEIGHT/2 + 10);
        }
    }

    public void loop()
    {
        long currentTime = System.currentTimeMillis();
        player.movement(grassHEIGHT, platforms);
        handleJellyfishes(currentTime);
        handleEnemies(currentTime);
        handleFireBalls();
        handleFloatingScores();
        handleLoss(currentTime);

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
                floatingScores.add(new FloatingScore("+100", jellyfish.getX() + jellyfish.getDiam()/2, jellyfish.getY()));
                playSound("beep.wav");
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
                floatingScores.add(new FloatingScore("+200", blueJellyfish.getbJX() + blueJellyfish.getDiam()/2, blueJellyfish.getY()));
                floatingScores.add(new FloatingScore("POWERED UP!", player.getX(), player.getY()));
                playSound("beep.wav");
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
                    System.out.println("poop");
                }
                if(enemy.checkStomp(player))
                {
                    enemies.remove(i);
                    score += 50;
                    floatingScores.add(new FloatingScore("+50", enemy.getX() + enemy.getWidth()/2, enemy.getY()));
                    playSound("beep.wav");
                    player.setColor(Color.ORANGE);
                    System.out.println("true " + player.getX() + " " + player.getY() + " " + enemy.getX() + " " + enemy.getY() + " " + player.getIsJumping() + " " + player.getOnPlat() + " " + player.getVY() + "\n");
                }
                else
                {
                    if(currentTime - interval >= infot)
                    {
                        player.setColor(Color.ORANGE);
                        infot = currentTime;
                        System.out.println("false " + player.getX() + " " + player.getY() + " " + enemy.getX() + " " + enemy.getY() + " " + player.getIsJumping() + " " + player.getOnPlat() + " " + player.getVY() + "\n");
                    }
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
                    playSound("beep.wav");
                    floatingScores.add(new FloatingScore("+50", enemy.getX() + enemy.getWidth()/2, enemy.getY()));
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
            score -= 50;
            floatingScores.add(new FloatingScore("-50", player.getX() + player.getWidth()/2, player.getY()));
            playerLives--;
            lastLifeLostTime = currentTime;
        }
    }

    private void handleFloatingScores()
    {
        for (FloatingScore fs : floatingScores)
        {
            fs.act();
        }
        floatingScores.removeIf(fs -> fs.getY() < 0); // remove if off the screen
    }

    public void handleLoss(long currentTime)
    {
        if(!isGameOver && !onMenu && !onLevelSelect)
        {
            if(player.getX() >= reachEndX)
            {
                isGameOver = true;
                playSound("winsound.wav");
            }
            else if(playerLives <= 0)
            {
                //playSound("dead-8bit.wav");
                playSound("end.wav");
                isGameOver = true;
            }
            else if(currentTime - gameStartTime > gameTimer)
            {
                System.out.println("currentTime: " + currentTime + " gameStartTime: " + gameStartTime + " gameTimer: " + gameTimer);
                //playSound("dead-8bit.wav");
                playSound("end.wav");
                isGameOver = true;
            }
        }
    }

    public void keyReleased(KeyEvent e)
    {
        player.stopHorizontal(e);
    }

    public static synchronized void playSound(final String url)
    {
        new Thread(new Runnable()
        {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run()
            {
                try
                {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Game.class.getResourceAsStream("/src/SoundEffects/" + url));
                    clip.open(inputStream);
                    clip.start();
                }
                catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
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
    public void mouseClicked(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();

        if(onMenu)
        {
            if(mouseX >= WIDTH / 2 - 105 && mouseX <= WIDTH / 2 + 105 && mouseY >= HEIGHT / 2 - 55 && mouseY <= HEIGHT / 2 + 30)
            {
                onLevelSelect = true;
                onMenu = false;
            }

            if(mouseX >= WIDTH / 2 - 105 && mouseX <= WIDTH + 105 && mouseY >= HEIGHT - HEIGHT / 4 - 5 && mouseY <= HEIGHT - HEIGHT / 4 + 80)
            {
                System.exit(0);
            }
        }
        else if(onLevelSelect)
        {
            if(mouseX >= WIDTH/2 - 105 && mouseX <= WIDTH/2 + 105 && mouseY >= HEIGHT/2 - 50 && mouseY <= HEIGHT/2 + 35)
            {
                onLevelSelect = false;
                onLevel1 = true;
                setupLevel();
                gameStartTime = System.currentTimeMillis();
            }
        }
        else if(isGameOver)
        {
            if(mouseX >= WIDTH/2 - 143 && mouseX <= WIDTH/2 + 107 && mouseY >= HEIGHT/2 - 55 && mouseY <= HEIGHT/2 + 30)
            {
                initializeGame();
            }
            if(mouseX >= WIDTH/2 - 105 && mouseX <= WIDTH/2 + 70 && mouseY >= HEIGHT/2 + 55 && mouseY <= HEIGHT/2 + 140)
            {
                System.exit(0);
            }
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
}