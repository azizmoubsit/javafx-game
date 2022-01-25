package application.classes;


import application.Presentation;

public class Hero extends GraphicObject {
	public Hero(Zone zone) {
		super("hero.png", zone);
	}
	
	public void goRight(Arme arme) {
        if (corps.getTranslateX() <= new Presentation().getWindowWidth() - 100) {
        	corps.setTranslateX(corps.getTranslateX() + 5);
        	arme.getCorps().setTranslateX(arme.getCorps().getTranslateX() + 5);
        }
        
    }
	
    public void goDown(Arme arme) {
        if (corps.getTranslateY() <= new Presentation().getWindowHeight() - 150) {
        	corps.setTranslateY(corps.getTranslateY() + 5);
        	arme.getCorps().setTranslateY(arme.getCorps().getTranslateY() + 5);
        }
    }

    public void goLeft(Arme arme) {
        if (corps.getTranslateX() >= 0) {
        	corps.setTranslateX(corps.getTranslateX() - 5);
        	arme.getCorps().setTranslateX(arme.getCorps().getTranslateX() - 5);
        }
    }

    public void goUp(Arme arme) {
        if (corps.getTranslateY() >= 200) {
        	corps.setTranslateY(corps.getTranslateY() - 5);
        	arme.getCorps().setTranslateY(arme.getCorps().getTranslateY() - 5);
        }
    }
}
