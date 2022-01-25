package application.classes;

public class Arme extends GraphicObject {
	private Balle balle;
	private final double maxRotate = 70;

	public Arme(GraphicObject hero, Zone zone) {
		super("arme.png", zone);
		corps.setTranslateX(hero.getCorps().getTranslateX() + 15);
		corps.setTranslateY(hero.getCorps().getTranslateY());
	}
	
	public double getRotate() {
		return corps.getRotate() - 90;
	}
	
	public void rotateLeft() {
		if(corps.getRotate() >= -maxRotate) 
			corps.setRotate(corps.getRotate() - 5);
	}
	
	public void rotateRight() {
		if(corps.getRotate() <= maxRotate)
			corps.setRotate(corps.getRotate() + 5);
	}
	
	public Balle getBalle() {
		return balle;
	}

	public void setBalle(Balle balle) {
		this.balle = balle;
	}
}
