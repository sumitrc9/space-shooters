/****************************************
SpaceShooters!
*****************************************/
//Note: Missile will be consistently spelt incorrectly throughout this program

//importing essential libraries
import java.lang.Exception;
import java.util.ArrayList;
import java.applet.Applet;
import java.awt.*;
import javax.swing.SwingUtilities;
import java.applet.AudioClip;
 
 
public class SpaceShooters extends Applet
{
   //encapsulating main arguments
   Image virtualMem;
   Image background;
	Graphics gBuffer;
	int oldX, oldY, newX, newY;
	int appletWidth;
	int appletHeight;
   Image base; 
   AudioClip audio;
   AudioClip audio2;
   AudioClip audio3;
   AudioClip audio4;
   AudioClip theme;
   AudioClip boom;
   Starbase starbase;
   EnemyBuilder enemyBuilder;
   ArrayList<Missle> missles;
   ArrayList<Enemy> enemies;
   boolean runGame = false;
   boolean endGame = false;
   int enemySpeed;
   int numHits;
   int numPassed;
   Rectangle easy;
   Rectangle hard;
   Rectangle insane;
   Rectangle playAgain;
   EnemyThread enemyThread;
   PaintThread paintThread;
   
   //assigning main parameters and initializing threads to begin gameplay
	public void init()
	{
      enemySpeed = 1;
      numHits = 0;
      numPassed = 0;
      
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
 
      //importing external audio and image filse for use in game and setting the display screen
		appletWidth = (int)screenSize.getWidth();
		appletHeight = (int)screenSize.getHeight();
		virtualMem = createImage(appletWidth,appletHeight);
      background = getImage(getDocumentBase(),"background.jpg");
		gBuffer = virtualMem.getGraphics();
		gBuffer.setColor(Color.white);
		gBuffer.fillRect(0,0,appletWidth,appletHeight);     
      audio = getAudioClip(getDocumentBase(),"pew.wav");
      audio2 = getAudioClip(getDocumentBase(),"audio2.wav");
      audio3 = getAudioClip(getDocumentBase(),"audio3.wav");
      audio4 = getAudioClip(getDocumentBase(),"audio4.wav");
      theme = getAudioClip(getDocumentBase(),"EndCredits.wav");
      boom = getAudioClip(getDocumentBase(),"boom.wav");
      
      easy = new Rectangle(appletWidth/4-50,appletHeight/2-50,100,100);
      hard = new Rectangle(appletWidth/2-50,appletHeight/2-50,100,100);
      insane = new Rectangle((3*(appletWidth/4))-50,appletHeight/2-50,100,100);
      playAgain = new Rectangle(appletWidth/2-75,appletHeight/2,150,100);
       
      base = getImage(getDocumentBase(),"base.png");
      starbase = new Starbase(appletWidth/2, appletHeight-400);
      starbase.setGraphics(gBuffer);
      base = base.getScaledInstance(140,180, Image.SCALE_DEFAULT);
      background = background.getScaledInstance(appletWidth,appletHeight, Image.SCALE_DEFAULT);

      
      //intializing game
      enemyBuilder = new EnemyBuilder(appletWidth);
      
      theme.loop();
      
      paintThread = new PaintThread();
      paintThread.start();
      
      enemyThread = new EnemyThread();
      
      
           
	}
   //main paint method where applet runs
	public void paint(Graphics g)
	{  
	  //algorithims for when the end game screen has not been reached
     if(endGame==false){
     
      gBuffer.drawImage(background, 0 , 0, this);
      
      gBuffer.drawImage(base, appletWidth/2-70, appletHeight-(768-510), this);
      
      starbase.drawBarrierLine(appletWidth, appletHeight);
      //algorithim for opening screen 
      if(runGame==false){
         
         gBuffer.setColor(Color.black);
         gBuffer.fillRect(appletWidth/4-50,appletHeight/2-50,100,100);
         gBuffer.fillRect(appletWidth/2-50,appletHeight/2-50,100,100);
         gBuffer.fillRect((3*(appletWidth/4))-50,appletHeight/2-50,100,100);
         gBuffer.setColor(Color.green);
         gBuffer.drawRect(appletWidth/4-50,appletHeight/2-50,100,100);
         gBuffer.drawRect(appletWidth/2-50,appletHeight/2-50,100,100);
         gBuffer.drawRect((3*(appletWidth/4))-50,appletHeight/2-50,100,100);
         
         gBuffer.setFont(new Font("Braggadocio", Font.BOLD+Font.ITALIC, 96 ));
		   gBuffer.drawString("SPACE SHOOTERS",appletWidth/2-440,appletHeight/4); 
         
         gBuffer.setFont(new Font("Braggadocio", Font.PLAIN, 20 ));
         gBuffer.drawString("Easy",appletWidth/4-25,appletHeight/2); 
         gBuffer.drawString("Hard",appletWidth/2-25,appletHeight/2); 
         gBuffer.drawString("Insane",(3*(appletWidth/4))-25,appletHeight/2);
         gBuffer.drawString("Don't let 3 enemies pass. Missiles take 1 second to reach target", appletWidth/2-260,appletHeight/4+80);
         
         gBuffer.setFont(new Font("Braggadocio", Font.BOLD, 15 )); 
         //gBuffer.drawString("© 2017 by Sumit Choudhury & Krissy Stoyanova" , appletWidth - 400, appletHeight-125 );
         
       }  
         //algorithim for whil main game is running
       else{
      
      gBuffer.setColor(Color.black); 
      gBuffer.fillRect(appletWidth - 400, appletHeight-145, 230, 24);
      gBuffer.setColor(Color.green);
      gBuffer.setFont(new Font("Braggadocio", Font.BOLD, 20 ));
      gBuffer.drawString(("Score: " + numHits + "  Base Health: " + (3-numPassed)) , appletWidth - 400, appletHeight-125); 
      
       

      
		
		gBuffer.setColor(Color.red);
		gBuffer.drawOval(newX-10,newY-10,20,20);
      
      //tracks status of missiles which will be used to attack enemies by user while game is running
      missles = starbase.getMissles();
      
      for(Missle m: missles){     
           
         gBuffer.setColor(Color.green);
         gBuffer.fillOval(m.getX(), m.getY(), 20, 20); 
         if(m.vis == false){
            missles.remove(m);
         }   
      }
      

      //tracks status of enemies while game is running
      enemies = enemyBuilder.getEnemies();
      
      for(Enemy e: enemies){
         if(e.hit){
            enemies.remove(e);
            boom.play();  
            continue;       
         }         
         //generates enemies
         Polygon enemy = new Polygon();
         enemy.addPoint(e.getX()+15,e.getY()+50);
         enemy.addPoint(e.getX(), e.getY());
         enemy.addPoint(e.getX()+15, e.getY()+20);
         enemy.addPoint(e.getX()+30, e.getY()); 
         
         if(detectCollision(e)==false && e.hit==false){
            gBuffer.setColor(Color.red);
            gBuffer.fillPolygon(enemy);
          }
          else{
            e.hit = true;
            gBuffer.setColor(Color.orange);
            gBuffer.fillRect(e.getX(), e.getY(), 30, 50);
            numHits++;
                        
         }
         //enemy status
         if(enemySuccess(e) == true && e.hit==false){
            e.attack = true;
            e.vis = false;
            numPassed++;
         }
          
         if(e.vis == false){
            enemies.remove(e);
            
         }                 
         
      }
      //keeps track of player status
      if(numPassed == 3){
         endGame = true;
         runGame = false;
         for(Enemy e: enemies){
            e.vis =false;
         }
         }
      
      }
      }
      //algorithim to generate end game and restart game sequences
      else{
         enemyThread.stop();
         gBuffer.setColor(Color.black);
         gBuffer.fillRect(0,0,appletWidth, appletHeight);
         gBuffer.fillRect(appletWidth/2-75,appletHeight/2,150,100);
         gBuffer.setColor(Color.white);
         gBuffer.setFont(new Font("Braggadocio", Font.BOLD+Font.ITALIC, 96 ));
		   gBuffer.drawString("GAME OVER",appletWidth/2-300,appletHeight/4);
         gBuffer.setFont(new Font("Braggadocio", Font.PLAIN, 20 ));
         gBuffer.drawString(("Final Score: " + numHits), appletWidth/2-60,appletHeight/4+80);
         gBuffer.drawRect(appletWidth/2-75,appletHeight/2,150,100);
         gBuffer.drawString("Play Again?" , appletWidth/2-50,appletHeight/4+250);
         paintThread.suspend();
         
      }
      //updates applet whil running
		g.drawImage(virtualMem,0,0,this);
   }
   //subroutine identifies and returns true if a user missile collides with an enemy
   private boolean detectCollision(Enemy e){
      
      for(Missle m: missles){
         if(m.getBounds().intersects(e.getBounds())){
            missles.remove(m);
            return true;
         }
         
      }
      return false;
   }
   //subroutine identifies whether or not enemy reaches threshold to affect player health
   private boolean enemySuccess(Enemy e){
      
      if(e.getY()>(appletHeight-240)){
         return true;
      }
      return false;
   }
      
   //subroutine which contains algotrithims for events that occur on-click
   public boolean mouseDown(Event e, int x, int y){
      
      if(runGame==false && endGame==false){
      //selecting game difficulty on intro screen
      if(easy.inside(x,y)){
			enemySpeed = 6;
         runGame = true;
         enemyThread.start();
         theme.stop();
         }
		else if(hard.inside(x,y)){
			enemySpeed = 2;
         runGame = true;
         enemyThread.start();
         theme.stop();
         }
		else if(insane.inside(x,y)){
			enemySpeed = 1;
         runGame = true;
         enemyThread.start();
         theme.stop();
         }
      else{
       return false;
       }
      
      }
      else if(endGame){
         //selecting play again
         if(playAgain.inside(x,y)){
			   endGame= false;
            runGame = false;
            numPassed = 0;
            numHits = 0;
            enemies.clear();
            missles.clear();
            theme.loop();
            paintThread.resume();
            enemyThread = new EnemyThread();
         }
         else{
             return false;
         }
      } 
      //allows player to fire missiles
      else {
         MissileThread missileThread = new MissileThread(x,y);
         missileThread.start();
      }
      
      
      
      
      return true;
   }
   //generates user cursor
	public boolean mouseMove(Event e, int x, int y)
	{
		newX = x;
		newY = y;
		oldX = newX;
		oldY = newY;
		repaint();
		return true;
	}


   //updates applet
   public void update(Graphics g){
      paint(g);
     
   }
   
   //constantly repaints applet to update game sequences
   public class PaintThread extends Thread {
       @Override
       public void run() {
          while (true) {
               SpaceShooters.this.repaint();
               try {
                   Thread.sleep(100);
               } catch (InterruptedException e) {
                   break;
               }
           }
       }
   }
   
   
   //allows player to fire missiles constantly while game is running
   public class MissileThread extends Thread {
      
      public int x;
      public int y;
      
      public MissileThread(int x, int y){     
         this.x = x;
         this.y = y;
      }
      
      
       @Override
       public void run() {
         Missle m = starbase.fire(x,y);
         //missile audio effects
          switch((int)(Math.random() * 4) + 1){
                case 1:      audio.play(); 
                                 break;
                case 2:    audio2.play(); 
                                 break;
                case 3:    audio3.play(); 
                                 break;
                case 4:    audio4.play(); 
                                 break;
            }

         m.updateMisslePos();
       }
   }
   
   
   //constantly creates enemies   
   public class EnemyCreator extends Thread {
      
       @Override
       public void run() {
         
            Enemy e = enemyBuilder.spawnEnemy();
            e.updateEnemyPos();
            
           
       }
   }
   //constatnly manages enemies
    public class EnemyThread extends Thread {
      
       @Override
       public void run() {
            
         while(true){     
            EnemyCreator enemyCreator = new EnemyCreator();
            enemyCreator.start();
         
         
         try {
            Thread.sleep(enemySpeed * 500);
          } 
          catch (InterruptedException e) {         
          }
         }
       }
       
   }
}  
   
   
