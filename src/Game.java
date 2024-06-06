    package src;

    import java.awt.*;
    import java.awt.event.KeyEvent;
    import java.awt.event.KeyListener;
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseListener;
    import java.awt.event.MouseMotionListener;
    import java.util.ArrayList;
    import javax.swing.JComponent;
    import javax.swing.JFrame;

    public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener
    {
        //instance variables
        private int WIDTH;
        private int HEIGHT;
        private int dirtHeight;
        private int grassHeight;
        private Player player = new Player();
        private Jellyfish j;
        private ArrayList<Jellyfish> jellies = new ArrayList<Jellyfish>();
        private Platform p;
        private ArrayList<Platform> plats = new ArrayList<Platform>();
        private Platform dirt;
        private Platform grass;
        private int score;
        private Enemy enemy;
        private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        private int playerLives;
        private long lastLifeLostTime;



        public Game()
        {
            //initializing instance variables
            WIDTH = 1000;
            HEIGHT = 500;
            dirtHeight = 450;
            grassHeight = 435;
            j = new Jellyfish(200, 180);
            jellies.add(j);
            jellies.add(new Jellyfish(700, 180));
            dirt = new Platform(0, dirtHeight, WIDTH, HEIGHT, new Color(87, 52, 41));
            grass = new Platform(0, grassHeight, WIDTH, dirtHeight - grassHeight, Color.GREEN);
            p = new Platform(Color.YELLOW);
            // adding all the new platforms to the arrayList
            plats.add(p);
            plats.add(new Platform(100, 250, 250, 50, Color.YELLOW));
            score = 0;
            enemy = new Enemy(Color.ORANGE, 450);
            playerLives = 3;
            enemies.add(enemy);

            JFrame gui = new JFrame(); //This makes the gui box
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
            gui.setTitle("Learning Graphics"); //This is the title of the game, you can change it
            gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30)); //Setting the size for gui
            gui.setResizable(false); //Makes it so the gui cant be resized
            gui.getContentPane().add(this); //Adding this class to the gui

            /*
            Declare buttons here
            */

            gui.pack(); // Packs everything together
            gui.setLocationRelativeTo(null); // Makes so the gui opens in the center of the screen
            gui.setVisible(true); // Makes the gui visible
            gui.addKeyListener(this); // Stating that this object will listen to key events
            gui.addMouseListener(this); // Stating that this object will listen to mouse events
            gui.addMouseMotionListener(this); // stating that this object will acknowledge mouse movement
        }

        // This method will acknowledge user input
        public void keyPressed(KeyEvent e)
        {
            //getting the key pressed
            int key = e.getKeyCode();
            System.out.println(key);
            //moving the player
            player.movePlayer(e);
        }

        //All your UI drawing goes in here
        public void paintComponent(Graphics g)
        {
            // Drawing a Blue Rectangle to be the background
            g.setColor(new Color(Color.HSBtoRGB(0.56f, 0.3f, 0.9f)));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            // Making the ground
            dirt.drawSelf(g);
            //Making the grass
            grass.drawSelf(g);
            // Drawing the Jellyfish
            //j.drawSelf(g);
            for(int i = 0; i < jellies.size(); i++)
            {
                jellies.get(i).drawSelf(g);
            }

            // Making the player
            player.drawPlayer(g);
            //player.movement();
            // drawing the platforms
            for(int i = 0; i < plats.size(); i++)
            {
                plats.get(i).drawSelf(g);
            }
            // drawing the enemies
            for(int i = 0; i < enemies.size(); i++)
            {
                enemies.get(i).drawSelf(g);
            }

            // Making the scorebox
            g.setColor(Color.GRAY);
            g.fillRect(800, 20, 180, 50);
            Font font = new Font("Arial", Font.BOLD, 30);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Score = " + score, 805, 60);
            //enemy.drawSelf(g);
        }

        public void loop()
        {
            long currentTime = System.currentTimeMillis();

            player.movement(grassHeight, plats);
            for(int i = 0; i < jellies.size(); i++) {
                if(jellies.get(i).checkTouch(player))
                {
                    jellies.remove(i);
                    i--;
                    score += 100;
                }
            }
            for(int i = 0; i < enemies.size(); i++)
            {
                enemies.get(i).enemyMove();

                //ifenemies.get i .checktoch player
                //the hurt code

                if(enemies.get(i).checkTouch(player))
                {
                    //if(currentTime - lastLifeLostTime >= 2100)
                    {
                        player.setColor(Color.RED);
                        playerLives--;
                        lastLifeLostTime = currentTime;
                    }
                }
                //this hsould be else if  checkStomp
                else if(enemies.get(i).checkStomp(player))
                {
                    //just remove
                    enemies.remove(i);
                    score += 50;
                }

                if(currentTime - lastLifeLostTime > 2100) // 2100 milliseconds = 2.1 seconds
                {

                    player.setColor(Color.ORANGE);
                }
            }



            System.out.println("Player lives: " + playerLives);
            repaint();
        }
        //These methods are required by the compiler.
        //You might write code in these methods depending on your goal.
        public void keyTyped(KeyEvent e)
        {
        }
        public void keyReleased(KeyEvent e)
        {
            player.stopHorizontal(e);
        }
        public void mousePressed(MouseEvent e)
        {
        }
        public void mouseReleased(MouseEvent e)
        {
        }
        public void mouseClicked(MouseEvent e)
        {
        }
        public void mouseEntered(MouseEvent e)
        {
        }
        public void mouseExited(MouseEvent e)
        {
        }
        public void mouseMoved(MouseEvent e)
        {
        }
        public void mouseDragged(MouseEvent e)
        {
        }
        public void start(final int ticks){
            Thread gameThread = new Thread(){
                public void run(){
                    while(true){
                        loop();
                        try{
                            Thread.sleep(1000 / ticks);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            };
            gameThread.start();
        }

        public static void main(String[] args)
        {
            Game g = new Game();
            g.start(60);
        }
    }