package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import bean.Card;

public class Tela extends JPanel implements Runnable, MouseMotionListener {

	public static final Integer PWIDTH = 800;   // size of panel
	public static final Integer PHEIGHT = 600;

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
		addMouseMotionListener(this);

//		addMouseListener( new MouseAdapter() {
//			public void mousePressed(MouseEvent e)    {
//				if(!gameOver){
//					int x = e.getX();
//					int y = e.getY();
//
//					Dimension d = Main.frame.getSize();
//					
//					
//					//more
//
//				}
//			}
//		});
		try {
			Thread.sleep(20);  // sleep a bit  
		}  catch(InterruptedException ex){
			
		}
	}

	private void readyForTermination() {  
		
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
		if(!gameOver){
			
		}
	}

	// draw the current frame to an image buffer
	private void gameRender() { 
		Dimension d = Main.frame.getSize();
		dbImage = createImage(d.width, d.height);
		if (dbImage == null) {
			System.out.println("dbImage is null");
			return;
		} else{
		}
		dbg = dbImage.getGraphics();

		// clear the background
		dbg.setColor(Color.white);
		dbg.fillRect (0, 0, d.width, d.height);
		
		for(Card c : Main.cards) {
			dbg.drawImage(c.getImage(), c.getX(), c.getY(), c.getW(), c.getH(), null);
		}
		
		dbg.dispose();
	}

	public void run() /* Repeatedly update, render, sleep */ {  
		running = true;
		while(running) {
			gameUpdate();   // game state is updated    
			gameRender();   // render to a buffer    
			paintScreen();  // draw buffer to screen
		}
		try {
			Thread.sleep(20);  // sleep a bit    }
		}
		catch(InterruptedException ex){
			ex.printStackTrace();
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
	@Override
	public void mouseDragged(MouseEvent e) {
		Card card = null;
		for(Card c : Main.cards) {
			if(c.isHere(e.getX(), e.getY())) {
//				System.out.println("here");
				card = c;
				break;
			}
		}
		if(card != null) {
			card.changePositionOnMouse(e.getX(), e.getY());
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
