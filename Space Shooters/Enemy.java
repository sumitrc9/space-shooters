
import java.applet.Applet;
import java.awt.*;
import javax.swing.SwingUtilities;
import java.applet.AudioClip;

//class contains properties of the game objects: enemies 
public class Enemy extends GameObject{
   
   
    public boolean hit;
    public boolean attack;
     
   //sets enemy position and status
    public Enemy(int x, int y){
   
      super(x,y);
      width = 30;
      height = 50;
      hit = false;
      attack = false;     
      
   }
   
   //moves enemy down the screen
   public void updateEnemyPos(){
        
      while(vis){
      
         y+=10;
          try {
            Thread.sleep(100);
          } 
          catch (InterruptedException e) {         
          }
         if(y>1000){
            vis = false;
         }     
      }
   }
      
   
}