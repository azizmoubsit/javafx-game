package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import application.classes.Arme;
import application.classes.Balle;
import application.classes.GraphicObject;
import application.classes.Hero;
import application.classes.Monstre;
import application.classes.Zone;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Presentation extends Application {
	private double windowWidth = 800;
	private double windowHeight= 600;
	private double pourcentageDajoutDeMonstre = .015;
	private BorderPane container = new BorderPane();
	private Line line = new Line(0, 200, windowWidth, 200);
	private Zone zone1 = new Zone(0, 0, line.getEndX() - 85, line.getEndY() - 85);
    private Zone zone2 = new Zone(line.getStartX(), line.getStartY(), line.getEndX() - 90, windowHeight - 150);
    Hero hero = new Hero(zone2);
	List<Monstre> monstres = new ArrayList<Monstre>();
	private List<Balle> balles = new ArrayList<Balle>();
	Arme arme = new Arme(hero, zone2);
	private boolean isPause = false;
	private int nbrDeadMonsters = 0;
    private Label score = new Label("Score : 0");
    AudioClip shot = new AudioClip(this.getClass().getResource("shot.mp3").toString());
    AudioClip explosion = new AudioClip(this.getClass().getResource("explosion.wav").toString());
	
	AnimationTimer animationTimer = new AnimationTimer() {
		
		@Override
		public void handle(long now) {
			if(isPause)
				return;
			
			for(Balle balle : balles) {
				for(Monstre monstre: monstres) {
					if(balle.touch(monstre)) {
						container.getChildren().removeAll(balle.getCorps(), monstre.getCorps());
						balle.setAlive(false);
						monstre.setAlive(false);	
						explosion.play();
						nbrDeadMonsters++;
	                    String str = "Score : " + nbrDeadMonsters;
	                    score.setText(str);
					}
				}
			}
			monstres.removeIf(GraphicObject::isDead);
			balles.removeIf(GraphicObject::isDead);
			for(Balle balle : balles) {
				balle.updateTranslateXY();
			}
			if(Math.random() < pourcentageDajoutDeMonstre) {				
				Monstre monstre = new Monstre(zone1);
				monstres.add(monstre);
				container.getChildren().add(monstre.getCorps());
			}
		}
	};
	
	EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent arg0) {
			switch(arg0.getCode()) {
			case W:
				arme.rotateLeft();
				break;
			case X:
				arme.rotateRight();
				break;
			case SPACE:
				shot.play();
				Balle balle = new Balle(arme, zone2);
				container.getChildren().add(balle.getCorps());
				arme.setBalle(balle);
				balles.add(balle);
				break;
			case LEFT:
				hero.goLeft(arme);
				break;
			case RIGHT:
				hero.goRight(arme);
				break;
			case UP:
				hero.goUp(arme);
				break;
			case DOWN:
				hero.goDown(arme);
				break;
			case G:
				// affichage de guide du jeu
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Guide du jeu");
                alert.setHeaderText("Guide du jeu");
                alert.setContentText("X \t\t=> \tTourner le pistolet vers la droite "
            					+ "\nW \t\t=> \tTourner le pistolet vers la gauche"
            					+ "\nEspace \t=> \tTirer"
            					+ "\n↑ \t\t=> \tDéplacer l'héro vers le haut"
            					+ "\n↓ \t\t=> \tDéplacer l'héro vers le bas"
            					+ "\n→ \t\t=> \tDéplacer l'héro vers la droite"
                				+ "\n← \t\t=> \tDéplacer l'héro vers la gauche"
                				+ "\nG \t\t=> \tLe guide du jeu");
                isPause = true;
                if (alert.showAndWait().get() == ButtonType.OK) {
                	isPause = false;                    
                }
				break;
			}
		}
	};
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	private void createContent() {
		container.getChildren().addAll(line, hero.getCorps(), arme.getCorps());
	}
	
	@Override
	public void start(Stage window) throws Exception {
		window.setWidth(windowWidth);
		window.setHeight(windowHeight);
		window.getIcons().add(new Image(new FileInputStream("images/hero.png")));
		window.setTitle("Jeu de guerre");
		window.setResizable(false);
		Scene scene = new Scene(container);
		createContent();
		animationTimer.start();
		score.setStyle("-fx-background-color:#000;-fx-background-radius: 6px;-fx-label-padding:10;-fx-text-fill:WHITE;");
		container.getChildren().add(score);
        container.setBottom(new StackPane(score));
        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream("images/bg.png"),windowWidth,windowHeight,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        container.setBackground(new Background(myBI));
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Etes-vous sûr ?");
                windowEvent.consume();
                isPause = true;
                if (alert.showAndWait().get() == ButtonType.OK) {
                    System.exit(0);
                }
                isPause = false;
            }
        });
		scene.setOnKeyPressed(keyEventHandler);
		window.setScene(scene);
		window.show();
	}
	
	public double getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(double windowWidth) {
		this.windowWidth = windowWidth;
	}

	public double getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(double windowHeight) {
		this.windowHeight = windowHeight;
	}
}
