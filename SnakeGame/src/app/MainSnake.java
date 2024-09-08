package app;

import javax.swing.JFrame;

public class MainSnake{
    public static void main(String[] args) throws Exception {
        try{
            FrameSnake marco = new FrameSnake();
            marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }catch(Exception e){
            System.out.println("Error");
            e.printStackTrace();

        }
    }

}
