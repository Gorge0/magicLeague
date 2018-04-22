package gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import bean.Card;
import engine.MouseController;

public class Tela extends JPanel implements Runnable {

	public static final Integer PWIDTH = 1600;   // size of panel
	public static final Integer PHEIGHT = 1200;

	private Thread animator;            // for the animation

	private volatile boolean running = false;    // stops the animation
	private volatile boolean gameOver = false;   // for game termination

	private Graphics dbg; 
	private Image dbImage = null;
	
	public String champ = "";

	public void setGameOver(boolean b){
		this.gameOver = b;
	}
	public boolean getGameOver(){
		return this.gameOver;
	}
	
	public Tela(){
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

		setFocusable(true);  
		requestFocus();    // JPanel now receives key events  
		readyForTermination();

		try {
			Thread.sleep(20);  // sleep a bit  
		}  catch(InterruptedException ex){
			
		}
	}

	private void readyForTermination() {
		MouseController mouse = new MouseController(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
//		setAutoscrolls(true);
		
	}
	
	public void addNotify()  /* Wait for the JPanel to be added to the     JFrame/JApplet before starting. */  {    
		super.addNotify();   // creates the peer    
		startGame();         // start the thread  }
	}

	private void startGame() { // initialise and start the thread      
		if (animator == null || !running) {      
			animator = new Thread(this);      
			animator.start();    
		}  
	} // end of startGame()

	// called by the user to stop execution  
	public void stopGame() {
		running = false;   
	}

	private void gameUpdate(){
//		Point p = MouseInfo.getPointerInfo().getLocation();
//		int maxX = (int) Main.frame.getSize().getWidth();
//		int maxY = (int) Main.frame.getSize().getHeight();
//		if(maxX - p.getX() < 100) {
////			Main.scroll.getHorizontalScrollBar().setValue(Main.scroll.getHorizontalScrollBar().getValue()+
////					(30 - (maxX - e.getX()) ));
//			Main.scroll.getHorizontalScrollBar().setValue(Main.scroll.getHorizontalScrollBar().getValue()+5);
////			try {
////				Thread.sleep(1);  // sleep a bit    }
////			}
////			catch(InterruptedException ex){
////				ex.printStackTrace();
////			}
//		}
		if(!gameOver){
			
		}
	}

	// draw the current frame to an image buffer
	private void gameRender() { 
		Dimension d = Main.frame.getSize();
		dbImage = createImage(PWIDTH, PHEIGHT);
		if (dbImage == null) {
			System.out.println("dbImage is null");
			return;
		} else{
		}
		dbg = dbImage.getGraphics();

		// clear the background
		dbg.setColor(Color.white);
		dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
		
		for(Card c : Main.cards) {
			dbg.drawImage(c.getImage(), c.getX(), c.getY(), c.getW(), c.getH(), null);
		}
		
//		dbg.dispose();
	}

	public void run() /* Repeatedly update, render, sleep */ {
		
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.currentTimeMillis( );
		
		running = true;
		while(running) {
			gameUpdate();   // game state is updated    
			gameRender();   // render to a buffer    
			paintScreen();  // draw buffer to screen
			
			timeDiff = System.currentTimeMillis( ) - beforeTime;
			sleepTime = 15 - timeDiff; // time left in this loop
			if (sleepTime <= 0) // update/render took longer than period
				sleepTime = 5; // sleep a bit anyway
			
			try {
				Thread.sleep(sleepTime);  // sleep a bit    }
			}
			catch(InterruptedException ex){
				ex.printStackTrace();
			} 
			beforeTime = System.currentTimeMillis( );
		}
		System.exit(0);
	}

	private void paintScreen() {
		// actively render the buffer image to the screen 
		Graphics g;  
		try {
			g = this.getGraphics();  // get the panel's graphic context
			if ((g != null) && (dbImage != null)){
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();  // sync the display on some systems    
			g.dispose();  }
		catch (Exception e)  {
			System.out.println("Graphics context error: " + e);  
		}
	}
}
