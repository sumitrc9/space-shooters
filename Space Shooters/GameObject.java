
import java.applet.Applet;
import java.awt.*;
import javax.swing.SwingUtilities;
import java.applet.AudioClip;

//superclass used for abstraction (indicates characteristics of all game objects without showing their implementations)
public class GameObject{
   
   protected int x;
   protected int y;
   protected boolean vis;
   protected int width;
   protected int height;
      
   public GameObject(int x, int y){
   
      this.x = x;
      this.y = y;
      vis = true;
      
   }
   
   //get, set methhods for all game objects
   public void setX(int x){ this.x = x; }
   public void setY(int y){ this.y = y; }
   public int getX() { return x;}
   public int getY() {return y;}
   
   

   //returns object's visibility in applet
    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }
   
   //established collision zones for all game objects
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
     

}
   
   