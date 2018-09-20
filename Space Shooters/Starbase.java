import java.applet.Applet;
import java.awt.*;
import javax.swing.SwingUtilities;
import java.applet.AudioClip;
import java.util.ArrayList;

//class contains proporties of game object Starbase (the user's object)
public class Starbase extends GameObject{

   Rectangle base;
   Image ii;
   Graphics g;
   ArrayList<Missle> missles;   
   
   //sets location and missile array for starbase
   public Starbase(int x, int y){
      super(x,y);
      missles = new ArrayList<Missle>();
   }
   
   //generates missiles from Starbase
   public Missle fire(int dx, int dy){
      Missle missle = new Missle(this.x, height-(768-525), g, dx, dy);
      missles.add(missle);
      return missle;
   }
   
   //accounts for all active missiles
   public ArrayList getMissles(){ 
      return missles;
   }
   
   public void setGraphics(Graphics g) {
      this.g = g; 
   }
   
   //sets enemy threshold
   public void drawBarrierLine(int w, int h){
      height = h;
      g.drawLine(0, h-240, w, h-240);
   }
}
   
