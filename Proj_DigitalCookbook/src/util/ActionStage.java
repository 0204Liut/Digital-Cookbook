package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * methods for load new scene, better used in controller
 * 
 * @author Liu Tao
 *
 */
public class ActionStage {

	FXMLLoader loader = null;

	/**
	 * Initialization of the new scene
	 * 
	 * @param stage  stage
	 * @param path   fxml path
	 * @param title  title
	 * @param width  width of scene
	 * @param height height of scene
	 */
	public ActionStage(Stage stage, String path, String title, double width, double height) {

		File file = new File("src/image");

		try {
			loader = new FXMLLoader(getClass().getResource(path));
			Parent root = loader.load();
			Scene scene = new Scene(root, width, height);
			stage.setScene(scene);
			stage.setTitle(title);
			stage.setResizable(false);
			stage.getIcons().add(new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + "/logo.png"));
			stage.show();
		} catch (IOException e) {
			System.out.println("ΩÁ√Êº”‘ÿ ß∞‹£°£°£°");
			e.printStackTrace();
		}

	}

	/**
	 * load FXML file
	 * 
	 * @return
	 */
	public FXMLLoader getLoader() {
		return loader;
	}
}
