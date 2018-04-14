package bean;

import java.awt.image.BufferedImage;

public class Card {

	private String nome;
	private BufferedImage image;
	
	private Integer x;
	private Integer y;
	private Integer w;
	private Integer h;
	
	public Card(String nome, BufferedImage img) {
		this.nome = nome;
		this.image = img;
		this.x = 0;
		this.y = 0;
		this.w = img.getWidth();
		this.h = img.getHeight();
//		changeSize(0.90);
	}

	public void changeSize(Double rate) {
		this.w = (int)(this.w * rate);
		this.h = (int)(this.h * rate);
	}
	
	public boolean isHere(Integer x, Integer y) {
		if(x > this.x && x < this.w+this.x && y > this.y && y < this.h+this.y) {
			return true;
		}else {
			return false;
		}
	}
	
	public void changePositionOnMouse(Integer x, Integer y) {
		int centroX = x - (int)(this.w/2); 
		this.x = centroX;
		
		int centroY = y - (int)(this.h/2); 
		this.y = centroY;
	}

	public String getNome() {
		return nome;
	}

	public BufferedImage getImage() {
		return image;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Integer getW() {
		return w;
	}

	public Integer getH() {
		return h;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public void setW(Integer w) {
		this.w = w;
	}

	public void setH(Integer h) {
		this.h = h;
	}
	
}
