package gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import bean.Card;

public class Main {
	
	public static final JFrame frame = new JFrame("Jogo da Velha");
	public static final Tela tela = new Tela();
	
	public static JScrollPane scroll = new JScrollPane(tela);
	
	public static ArrayList<Card> cards = new ArrayList<>();

	public static void main(String[] args) {

		// Run GUI codes in the Event-Dispatching thread for thread safety
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	        	 frame.setBounds(200, 100, Tela.PWIDTH, Tela.PHEIGHT);
	     		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     		 
	             frame.add(scroll);  // Let the constructor do the job
	             frame.setVisible(true);
	             
	             try {
					cards.add(new Card("Act of Heroism", ImageIO.read(new File("card.png"))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	      });
	      
	}

}
