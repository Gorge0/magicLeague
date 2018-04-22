package engine;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JScrollBar;

import bean.Card;
import gui.Main;

public class MouseController extends MouseAdapter{

	private Point origin;
	private Point lastPos;
	private Point lastPosScreen;
	
	private final int limit = 100;
	
	private JComponent component;

	public MouseController(JComponent comp) {
		this.component = comp;
	}
	
    @Override
    public void mousePressed(MouseEvent e) {
        origin = new Point(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    	Card card = null;
		for(Card c : Main.cards) {
			if(c.isHere(e.getX(), e.getY())) {
				card = c;
				break;
			}
		}
		if(card != null) {
			card.changePositionOnMouse(e.getX(), e.getY());
		}else {
		}
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    	JScrollBar horizontal = Main.scroll.getHorizontalScrollBar();
    	JScrollBar vertical = Main.scroll.getVerticalScrollBar();
    	
    	int xScreen = e.getXOnScreen();
    	int yScreen = e.getYOnScreen();
    	
    	int xFrame = e.getX() - horizontal.getValue();
    	int yFrame = e.getY() - vertical.getValue();
    	
    	if(lastPos != null) {
    		try {
    			Robot robot = new Robot();
    			
    			int maxX = (int) Main.frame.getSize().getWidth();
    			int maxY = (int) Main.frame.getSize().getHeight();
    			
    			boolean isTotalRight = (horizontal.getValue() == 
    					(horizontal.getMaximum() - horizontal.getVisibleAmount()));
    			boolean isTotalLeft = (horizontal.getValue() == 0);
    			
    			boolean isTotalDown = (vertical.getValue() == 
    					(vertical.getMaximum() - vertical.getVisibleAmount()));
    			boolean isTotalUp = (vertical.getValue() == 0);
    			
    			System.out.println(maxX + " - " + xFrame + " - " + isTotalRight);
    			
    			if(maxX - xFrame < limit && !isTotalRight) {
    				if(xFrame > lastPos.getX()) {
    					horizontal.setValue(horizontal.getValue()+5);
    					robot.mouseMove((int)lastPosScreen.getX(),yScreen);
    				}
    			}
    			
    			if(xFrame < limit && !isTotalLeft) {
    				if(xFrame < lastPos.getX()) {
    					horizontal.setValue(horizontal.getValue()-5);
    					robot.mouseMove((int)lastPosScreen.getX(),yScreen);
    				}
    			}
    			
    			if(maxY - yFrame < limit && !isTotalDown) {
    				if(yFrame > lastPos.getY()) {
    					vertical.setValue(vertical.getValue()+5);
    					robot.mouseMove(xScreen,(int)lastPosScreen.getY());
    				}
    			}
    			
    			if(yFrame < limit && !isTotalUp) {
    				if(yFrame < lastPos.getY()) {
    					vertical.setValue(vertical.getValue()-5);
    					robot.mouseMove(xScreen,(int)lastPosScreen.getY());
    				}
    			}
    			
    		} catch (AWTException e1) {
    			e1.printStackTrace();
    		} 
    	}
    	lastPos = new Point(xFrame, yFrame);
    	lastPosScreen = new Point(xScreen,yScreen);
    }
    
}
