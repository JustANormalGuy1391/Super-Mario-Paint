package smp.fx;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import smp.ImageLoader;
import smp.SoundfontLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Super Mario Paint <br>
 * Based on the old SNES game from 1992, Mario Paint <br>
 * Inspired by:<br>
 * MarioSequencer (2002) <br>
 * TrioSequencer <br>
 * Robby Mulvany's Mario Paint Composer 1.0 / 2.0 (2007-2008) <br>
 * FordPrefect's Advanced Mario Sequencer (2009) <br>
 * JavaFX2.2 has been released. Testing some application development.
 * It might take a little longer to do this, but it promises to be 
 * faster than working with swing... at least in the maintenance
 * aspect... <br>
 * @author RehdBlob
 * @since 2012.08.16
 * @version 1.00
 */
public class SuperMarioPaintFX extends Application {
	
	/**
	 * Location of the Main Window fxml file.
	 */
	private String mainFxml = "./MainWindow.fxml";
	
	/**
	 * Location of the Arrangement Window fxml file.
	 */
	@SuppressWarnings("unused")
	private String arrFxml = "./ArrWindow.fxml";
	
	/**
	 * Location of the Advanced Mode (super secret!!)
	 * fxml file.
	 */
	@SuppressWarnings("unused")
	private String advFxml = "./AdvWindow.fxml";
	
	/**
	 * Until I figure out the mysteries of the Preloader class in 
	 * JavaFX, I will stick to what I know, which is swing, unfortunately.
	 */
	private SplashScreen dummyPreloader = new SplashScreen();
	
	/**
	 * Loads all the sprites that will be used in Super Mario Paint.
	 */
	private ImageLoader imgLoader = new ImageLoader();
	
	/**
	 * Loads the soundfonts that will be used in Super Mario Paint.
	 */
	private SoundfontLoader sfLoader = new SoundfontLoader();
	
	/**
	 * Starts three <code>Thread</code>s. One of them is currently 
	 * a dummy splash screen, the second an <code>ImageLoader</code>,
	 * and the third one a <code>SoundfontLoader</code>.
	 * @see ImageLoader
	 * @see SoundfontLoader
	 * @see SplashScreen
	 */
	@Override
	public void init() {
		Thread splash = new Thread(dummyPreloader);
		Thread imgLd = new Thread(imgLoader);
		Thread sfLd = new Thread(sfLoader);
		splash.start();
		imgLd.start();
		sfLd.start();
		while(imgLd.isAlive() || sfLd.isAlive())
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Starts the application and loads the FXML file that contains
	 * a lot of the class hierarchy.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			dummyPreloader.updateStatus(30.0);
			Parent root = 
				(Parent) FXMLLoader.load(new File(mainFxml).toURI().toURL());
			primaryStage.setTitle("Super Mario Paint");
			dummyPreloader.updateStatus(60.0);
			primaryStage.setResizable(false);
			primaryStage.setScene(new Scene(root, 800, 600));
			dummyPreloader.updateStatus(100);
			primaryStage.show();
			dummyPreloader.dispose();
			SMPFXController.check();
		} catch (MalformedURLException e) {
			// Can't recover.
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			// Can't recover.
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Launches the application.
	 * @param args String args.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
