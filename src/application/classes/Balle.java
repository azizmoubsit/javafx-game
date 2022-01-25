package application.classes;

import javafx.geometry.Point2D;

public class Balle extends GraphicObject {
	private Arme arme;
	private Point2D direction;
	
	public Balle(Arme arme, Zone zone) {
		super("balle.png", zone);
		this.arme = arme;
		corps.setRotate(arme.getCorps().getRotate());
		updateBalle();
		updateDirection(arme.getRotate());
	}
	
	public double getRotate() {
		return corps.getRotate() - 90;
	}

	public void updateBalle() {
		corps.setTranslateX(arme.getCorps().getTranslateX()+3);
		corps.setTranslateY(arme.getCorps().getTranslateY()+15);
	}
	
	public void updateDirection(double rotation) {
		Point2D p;
        double x = Math.cos(Math.toRadians(rotation));
        double y = Math.sin(Math.toRadians(rotation));
        p = new Point2D(x, y);
        direction = p.normalize().multiply(7);//pour la rapidité des balles
	}
	
	public void updateTranslateXY() {
		corps.setTranslateX(corps.getTranslateX() + direction.getX());
        corps.setTranslateY(corps.getTranslateY() + direction.getY());
	}
}
