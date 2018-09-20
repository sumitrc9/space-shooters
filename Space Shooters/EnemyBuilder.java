import java.applet.Applet;
import java.awt.*;
import javax.swing.SwingUtilities;
import java.applet.AudioClip;
import java.util.ArrayList;

//class construncts enemies
public class EnemyBuilder{

  
   ArrayList<Enemy> enemies;
   private int width;   

   //contructor for enemy array 
   public EnemyBuilder(int width){
      enemies = new ArrayList<Enemy>();
      this.width = width;
   }
   
   //randomly generates enemy at the top of the applet
   public Enemy spawnEnemy() {
     int enemySpawnLocation = (int)(Math.random()*(width-30));  
     Enemy enemy = new Enemy(enemySpawnLocation,-50);         
     enemies.add(enemy);
     return enemy;
   }
      
  
   //accounts for all active enemies
   public ArrayList getEnemies(){ 
      return enemies;
   }
   
   
}