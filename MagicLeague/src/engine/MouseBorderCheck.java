package engine;

import java.awt.MouseInfo;
import java.awt.Point;

import gui.Main;

public class MouseBorderCheck extends Thread {

	@Override
	public void run() {
		while(true) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			int maxX = (int) Main.frame.getSize().getWidth();
			int maxY = (int) Main.frame.getSize().getHeight();
			if(maxX - p.getX() < 100) {
//				Main.scroll.getHorizontalScrollBar().setValue(Main.scroll.getHorizontalScrollBar().getValue()+
//						(30 - (maxX - e.getX()) ));
				Main.scroll.getHorizontalScrollBar().setValue(Main.scroll.getHorizontalScrollBar().getValue()+1);
				try {
					Thread.sleep(1);  // sleep a bit    }
				}
				catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		}
	}
}
