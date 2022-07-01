package application;

import java.io.File;
import dataAccess.Driver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.CopyImage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Main extends Application {
	File file = new File("src/image");

	@Override
	public void start(Stage primaryStage) {

		CopyImage.setDest("src/image");
		try {
			//Driver.setConnectionForAllTables("jdbc:mysql://localhost:3306/mydb", "root", "lt123456");
			Pane mainPane = FXMLLoader.load(getClass().getResource("/view/LoginScene.fxml"));
			Scene mainScene = new Scene(mainPane, 680, 475);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Connect to Your Digital Cook Book");
			primaryStage.getIcons().add(new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + "/logo.png"));
			primaryStage.show();
			primaryStage.setResizable(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
