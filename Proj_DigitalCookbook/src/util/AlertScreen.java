package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * show alert/reminding Scene (popup message) better used in controller
 * 
 * @author Liu Tao
 *
 */
public class AlertScreen {

	/**
	 * 
	 * @param title title of alert scene
	 * @param msg   message in alert scene
	 * @return an Alert Object
	 */
	public static Boolean getMessageScreen(String title, String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, new ButtonType("cancel", ButtonBar.ButtonData.NO),
				new ButtonType("confirm", ButtonBar.ButtonData.YES));
		alert.setHeaderText(title);

		alert.setContentText(msg);
		Optional<ButtonType> buttonType = alert.showAndWait();
		if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
			return true;
		} else {
			return false;
		}
	}
}
