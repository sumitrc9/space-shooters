import java.applet.Applet;
import java.awt.*;
 import javax.swing.SwingUtilities;
 import java.applet.AudioClip;

//class contains properties of the game objects: missiles 
public class Missle extends GameObject{

   Graphics g;
   protected int dx;
   protected int dy;
   private double percentX;
   private double percentY;
   
   //sets location, speed, and size of missiles
   public Missle(int x, int y, Graphics g, int dx, int dy){
      super(x,y);
      this.g = g;
      this.dx = dx;
      this.dy = dy;
      width = 20;
      height = 20;
   }
   
 
  
   //method manages the movement of all active missiles
   public void updateMisslePos(){
     calcMissileTrajectory();
     int initialX = x;
     int initialY = y;
     
     while(vis){
        
          try {
            Thread.sleep(100);
          } 
          catch (InterruptedException e) {         
          }
      
         if(x>0 || initialX>dx){
            x+=percentX;
         } 
         else if(x<2000  || initialX<dx){
            x+=percentX;
         }
         
         if(y>0  || initialY>dy) {
            y+=percentY;
         } 
         else if(y<2000 || initialY<dy){
            y+=percentY;
         }
         
         if(x<=0 || x>=2000)
         {  
            vis = false;
         }
         
         if(y<=0 || y>= 2000){
            vis = false;
         }
     }
         
    
   }
   //sets missile trajectory
   private void calcMissileTrajectory(){
      percentX = .1 * (dx-x);
      percentY = .1 * (dy-y);
   }
   
  
}