package application.classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class GraphicObject {
	protected Node corps;
	private boolean alive = true;

	public GraphicObject() {}
	
	public GraphicObject(Node corps) {
		this.corps = corps;
	}
	
	public GraphicObject(String imageName, Zone zone) {
		try {
			Image image = new Image(new FileInputStream("images/" + imageName));
			corps = new ImageView(image);
			((ImageView)corps).setX(0);
			((ImageView)corps).setY(0);
			
			double x = zone.getX1() + (zone.getX2() - zone.getX1()) * Math.random();
			double y = zone.getY1() + (zone.getY2() - zone.getY1()) * Math.random();
			
			corps.setTranslateX(x);
			corps.setTranslateY(y);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean touch(GraphicObject graphicObject) {
    	boolean isTouched = corps.getBoundsInParent().intersects(graphicObject.getCorps().getBoundsInParent());
    	/*if(isTouched) {
    		AudioClip audio = new AudioClip(this.getClass().getResource("explosion.wav").toString());
    		audio.play();    		
    	}*/
        return isTouched;
    }

    public boolean isDead() {
        return !alive  ;
    }

	public Node getCorps() {
		return corps;
	}

	public void setCorps(Node corps) {
		this.corps = corps;
	}
	
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
