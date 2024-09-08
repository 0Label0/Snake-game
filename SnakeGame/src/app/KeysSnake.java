package app;


import java.awt.event.*;

public class KeysSnake extends KeyAdapter {

    private FrameSnake frameSnake;

    public KeysSnake(FrameSnake frameSnake) {
        this.frameSnake = frameSnake;
    }


    public void keyPressed(KeyEvent e){
        // Pulsar Esc para salir del juego
        int currentDirections = frameSnake.getDirection();
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_ESCAPE){
            System.exit(0);

        /*
         * CONTROL DE LA SERPIENTE
         * Direcciones con las flechas del teclado
         * Direcciones con WASD
         * En ambos casos no se puede ir a la dirección contraria de la que se está llendo: si va arriba no puede ir abajo
         */

        // Arriba
        }else if(keyCode == KeyEvent.VK_UP  && currentDirections != KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_W && currentDirections != KeyEvent.VK_DOWN){
                frameSnake.setDirection(KeyEvent.VK_UP);
        
        // Abajo
        }else if(keyCode == KeyEvent.VK_DOWN && currentDirections != KeyEvent.VK_UP || keyCode == KeyEvent.VK_S && currentDirections != KeyEvent.VK_UP){
                frameSnake.setDirection(KeyEvent.VK_DOWN);
        
        // Izquierda
        }else if(keyCode == KeyEvent.VK_LEFT && currentDirections != KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_A && currentDirections != KeyEvent.VK_RIGHT){
                frameSnake.setDirection(KeyEvent.VK_LEFT);
        
        // Derecha
        }else if(keyCode == KeyEvent.VK_RIGHT && currentDirections != KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_D && currentDirections != KeyEvent.VK_LEFT){
                frameSnake.setDirection(KeyEvent.VK_RIGHT);

            
        }


    }


}
