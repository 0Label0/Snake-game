package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class FrameSnake extends JFrame {
   
    private int width = 600;
    private int height = 504;
    private Point snake;
    private int widthPoint = 40;
    private int heightPoint = 40;
    private GameCanvas GameCanvas;
    private Point fruit;

    private int address;
    private ArrayList<Point> list;
    private boolean gameOver = false;
    private boolean snakeHitBorder = false;
    private int frecuence = 90;
    private long pointCount = 0;

    private Image snakeImage;
    private Image fruitImage;

    public FrameSnake(){
        setTitle("Snake Game");

        // Centrar el marco
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(dimension.width / 2 - width / 2, dimension.height / 2 - height /2,width,height);


        // Añadir las teclas
        KeysSnake teclas = new KeysSnake(this);
        this.addKeyListener(teclas);
        
        setLayout(new BorderLayout());
        // Añadir imagen de la serpiente
        GameCanvas = new GameCanvas();
        this.getContentPane().add(GameCanvas,BorderLayout.CENTER);


        // Añadir el movimiento automático
        Moment momento = new Moment();
        Thread thread = new Thread(momento);
        thread.start();

        startGame();

        setResizable(false);
        setVisible(true);
    }
        


    private class GameCanvas extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            drawSnake(g);
            drawFruit(g);
            
            // Añadir la imagen de la serpiente y la fruta
            try{
                snakeImage = new ImageIcon("C:/Users/karl7/Documents/cursos_programacion/SnakeJuego/SnakeGame/Img/HeadSnake.gif").getImage();
                fruitImage = new ImageIcon("C:/Users/karl7/Documents/cursos_programacion/SnakeJuego/SnakeGame/Img/FruitBanana.gif").getImage();
            }catch(Exception e){
                e.printStackTrace();
            }

        }

        public void drawFruit(Graphics g){
            // Creación de la imagen de la fruta
            
            g.drawImage(fruitImage, fruit.x, fruit.y, widthPoint, heightPoint, this);
        }
        public void drawSnake(Graphics g){
            // Creación de la imagen de la serpiente
            for (Point point : list) {
                g.drawImage(snakeImage, point.x, point.y, widthPoint, heightPoint, this);
            }
        }

    }



    public void startGame(){
        fruit = new Point(200,200);
        snake = new Point(width/2, height/2);
        setFruits();

        // Instancia y agrega la lista
        list = new ArrayList<Point>();
        list.add(snake);

    }

    public void update(){
        // Para que se mueva la serpiente
        GameCanvas.repaint();
        // Añade y remueve elementos de la lista según se mueva la serpiente
        list.add(0,new Point(snake.x,snake.y));
        list.remove((list.size()-1));

        paneGameOver();
        eatFruit();

    }

    public void paneGameOver(){
        // GAME OVER por choque con sigo misma
        for(int i = 1; i < list.size(); i++){
            Point point = list.get(i);
            if(snake.x == point.x && snake.y == point.y || snakeHitBorder){
                gameOver = true;
            }
        }
        if(gameOver){
            int choice = JOptionPane.showOptionDialog(this, "Points " + pointCount, "GAME OVER", 2, 2, null, new String[] {"TRY AGAIN!","QUIT"}, 2);
            // Reiniciar nivel
            if(choice == JOptionPane.YES_OPTION){
                setVisible(false);
                FrameSnake marcoSnake = new FrameSnake();
                marcoSnake.setVisible(true);
        
                }           
            // Cerrar juego
            if(choice == JOptionPane.NO_OPTION){
                System.exit(choice);
                }
                
            }

        }
    private class Moment extends Thread {

        long last = 0;
    
        public void run() {
    
            while (!gameOver) {
                // Movimiento automático
                if ((System.currentTimeMillis() - last) > frecuence) {
                    if (address == KeyEvent.VK_UP) {
                        snake.y -= heightPoint;
                    } else if (address == KeyEvent.VK_DOWN) {
                        snake.y += heightPoint;
                    } else if (address == KeyEvent.VK_LEFT) {
                        snake.x -= widthPoint;
                    } else if (address == KeyEvent.VK_RIGHT) {
                        snake.x += widthPoint;
                    }


                    // Margenees de la serpiente
                    int leftMargin = 0;
                    int topMargin = 0;
                    int bottomMargin = 39;
                    int rightMargin = 15;
                    
                    // Si la serpiente toca cualquiera de los bordes se detiene y se activa GAME OVER
                    if (snake.x < leftMargin) {
                        snake.x = leftMargin;
                        gameOver = true;
                    }
                    if (snake.y < topMargin) {
                        snake.y = topMargin;
                        gameOver = true;
                    }
                    if (snake.x >= width - widthPoint - rightMargin) {
                        snake.x = width - widthPoint - rightMargin;
                        gameOver = true;
                    }
                    if (snake.y >= height - heightPoint - bottomMargin) {
                        snake.y = height - heightPoint - bottomMargin;
                        gameOver = true;
                    }

                    // Para que no se sature el bucle
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    update();
                    last = System.currentTimeMillis();

                }
            }
        }
    }
    


    public void setFruits() {
        /* Es lo que hará que las frutas aparezcan en un lugar aleatorio del mapa */
        Random r = new Random();
        fruit.x = r.nextInt(width);
        fruit.y = r.nextInt(height);

        // el máximo alcance que tendrán las frutas
        int maxX = width - widthPoint - 5;
        int maxY = height - heightPoint - 30;
    
        if ((fruit.x % 5) > 0) {
            fruit.x = fruit.x - (fruit.x % 5);
        }
        if (fruit.x < 5) {
            fruit.x = fruit.x + 40;
        }
        if ((fruit.y % 5) > 0) {
            fruit.y = fruit.y - (fruit.y % 5);
        }
        if (fruit.y < 5) {
            fruit.y = fruit.y + 40;
        }
    
        fruit.x = r.nextInt(maxX / widthPoint) * widthPoint;
        fruit.y = r.nextInt(maxY / heightPoint) * heightPoint;
    }
    public void eatFruit(){
        // Cuando la serpiente choca con la fruta, se elimina la fruta
        if (snake.x > (fruit.x - 40) && snake.x < (fruit.x + 40) && snake.y > (fruit.y - 40) && snake.y < (fruit.y + 40)) {
            pointCount++;
            // Añade más cola a la serpiente
            list.add(0,new Point(snake.x,snake.y));
            // Añade una fruta más al mapa
            setFruits();
        }
    }


    public int getDirection() {
        return address;
    }

    public void setDirection(int address) {
        this.address = address;
    }



}

