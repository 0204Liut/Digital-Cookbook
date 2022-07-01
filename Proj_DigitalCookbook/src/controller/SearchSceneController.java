package controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataAccess.DAO_Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Recipe;
import util.ActionStage;

/**
 * 
 * Search Scene's controller define operation in SearchScene. show search
 * results according to MainScene's search content
 * 
 * @author Liu Tao
 *
 */
public class SearchSceneController implements Initializable {
	@FXML
	private HBox hb_1;
	@FXML
	private ImageView image_1;
	@FXML
	private Label label_1;
	@FXML
	private HBox hb_2;
	@FXML
	private ImageView image_2;
	@FXML
	private Label label_2;
	@FXML
	private HBox hb_3;
	@FXML
	private ImageView image_3;
	@FXML
	private Label label_3;
	@FXML
	private HBox hb_4;
	@FXML
	private ImageView image_4;
	@FXML
	private Label label_4;
	@FXML
	private HBox hb_5;
	@FXML
	private ImageView image_5;
	@FXML
	private Label label_5;
	@FXML
	private TextField text_search;
	@FXML
	private Button btn_search;
	@FXML
	private Button btn_former;
	@FXML
	private TextField text_page;
	@FXML
	private Button btn_later;

	public String page1 = "1";
	public String page2 = "2";
	public String page3 = "3";
	public String page4 = "4";

	private int page = 1;

	public static String searchResults_RecName;

	LinkedList<String> RecImage = new LinkedList<String>();
	LinkedList<String> RecName = new LinkedList<String>();

	LinkedList<ImageView> q_image = new LinkedList<>();
	LinkedList<Label> q_label = new LinkedList<>();

	File file = new File("src/image");
	int pathLenth = 0;

	/**
	 * initialized operation in SearchScene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		label_1.autosize();
		label_2.autosize();
		label_3.autosize();
		label_4.autosize();
		label_5.autosize();

		try {
			text_search.setText(MainSceneController.searchText);

			if (DAO_Recipe.queryRecipebyName(MainSceneController.searchText).isEmpty() == false) {
				for (int i = 0; i < DAO_Recipe.queryRecipebyName(MainSceneController.searchText).size(); i++) {
					RecImage.add(DAO_Recipe.queryRecipebyName(MainSceneController.searchText).get(i)
							.getEndProductImage());
					RecName.add(DAO_Recipe.queryRecipebyName(MainSceneController.searchText).get(i).getRecipeName());
				}

			} else if (DAO_Recipe.queryRecipebyName(MainSceneController.searchText).isEmpty()) {
				text_page.setText(page1);
			}

			q_image.offer(image_1);
			q_image.offer(image_2);
			q_image.offer(image_3);
			q_image.offer(image_4);
			q_image.offer(image_5);

			q_label.offer(label_1);
			q_label.offer(label_2);
			q_label.offer(label_3);
			q_label.offer(label_4);
			q_label.offer(label_5);

			if (RecImage.size() <= 5 && RecName.size() <= 5) {

				int j = 0;

				for (; j < RecImage.size(); j++) {
					q_image.get(j)
							.setImage(new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + RecImage.get(j).substring(10)));
				}

				int i = 0;

				for (; i < RecName.size(); i++) {
					q_label.get(i).setText(RecName.get(i));
				}

				text_page.setText(page + "");

				btn_former.setVisible(false);
				btn_later.setVisible(false);
			}

			else if (RecImage.size() > 5 && RecName.size() > 5) {

				int j = 0;
				for (; j < 5; j++) {
					q_image.get(j)
							.setImage(new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + RecImage.get(j).substring(10)));
				}
				int i = 0;

				for (; i < 5; i++) {
					q_label.get(i).setText(RecName.get(i));
				}
				text_page.setText(page + "");

				btn_former.setVisible(false);
				btn_later.setVisible(true);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * go back to former pages for searching results
	 * 
	 * @param event action event
	 */
	public void eventButtonLeft(ActionEvent event) {

		btn_later.setVisible(true);

		for (int t = 0; t < 5; t++) {
			q_image.get(t).setImage(null);
		}
		for (int t = 0; t < 5; t++) {
			q_label.get(t).setText(null);
		}

		for (int j = 0; j < 5; j++) {
			q_image.get(j).setImage(
					new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + RecImage.get(j + 5 * (page - 2)).substring(10)));
		}

		for (int i = 0; i < 5; i++) {
			q_label.get(i).setText(RecName.get(i + 5 * (page - 2)));
		}

		page--;
		text_page.setText(String.valueOf(page));

		if (text_page.getText().equals("1")) {
			btn_former.setVisible(false);
		}
	}

	/**
	 * if results more than 5 items, one page cannot show entirely, after click this
	 * ">" button go to next pages for additional searching results
	 * 
	 * @param event action event
	 */
	public void eventButtonRight(ActionEvent event) {
		
		btn_former.setVisible(true);

		for (int t = 0; t < 5; t++) {
			q_image.get(t).setImage(null);
		}
		for (int t = 0; t < 5; t++) {
			q_label.get(t).setText(null);
		}

		for (int i = 0; i < RecName.size() - 5 * page; i++) {
			q_label.get(i).setText(RecName.get(i + 5 * page));
		}

		for (int j = 0; j < RecImage.size() - 5 * page; j++) {
			q_image.get(j)
					.setImage(new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + RecImage.get(j + (5 * page)).substring(10)));
		}

		page++;
		text_page.setText(String.valueOf(page));

		if (text_page.getText().equals((RecName.size() / 5) + 1 + "")) {
			btn_later.setVisible(false);
		}

	}

	/**
	 * search again according text field new search results will show after clicking
	 * "search" button
	 * 
	 * @param event action event
	 * @throws SQLException
	 */
	public void eventButtonSearch(ActionEvent event) throws SQLException {

		RecImage.clear();
		RecName.clear();
		q_image.clear();
		q_label.clear();
		if (DAO_Recipe.queryRecipebyName(text_search.getText()).isEmpty() == false) {
			for (int i = 0; i < DAO_Recipe.queryRecipebyName(text_search.getText()).size(); i++) {
				RecImage.add(DAO_Recipe.queryRecipebyName(text_search.getText()).get(i).getEndProductImage());
				RecName.add(DAO_Recipe.queryRecipebyName(text_search.getText()).get(i).getRecipeName());
			}

		}

		q_image.offer(image_1);
		q_image.offer(image_2);
		q_image.offer(image_3);
		q_image.offer(image_4);
		q_image.offer(image_5);

		q_label.offer(label_1);
		q_label.offer(label_2);
		q_label.offer(label_3);
		q_label.offer(label_4);
		q_label.offer(label_5);

		if (RecImage.size() <= 5 && RecName.size() <= 5) {
			for (int t = 0; t < 5; t++) {
				q_image.get(t).setImage(null);
			}
			for (int t = 0; t < 5; t++) {
				q_label.get(t).setText(null);
			}
			int j = 0;
			for (; j < RecImage.size(); j++) {
				q_image.get(j).setImage(new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + RecImage.get(j).substring(10)));
			}

			int i = 0;
			for (; i < RecName.size(); i++) {
				q_label.get(i).setText(RecName.get(i));
			}
			text_page.setText(page+"");

			btn_former.setVisible(false);
			btn_later.setVisible(false);
		}

		else if (RecImage.size() > 5 && RecName.size() > 5) {

			int j = 0;
			for (; j < 5; j++) {
				q_image.get(j).setImage(new Image(file.getAbsolutePath().replaceAll("\\\\", "/") + RecImage.get(j).substring(10)));
			}
			int i = 0;
			for (; i < 5; i++) {
				q_label.get(i).setText(RecName.get(i));
			}
			text_page.setText(page+"");

			btn_former.setVisible(false);
			btn_later.setVisible(true);
		}

	}

	/**
	 * event for click recipe label details of this recipe will show
	 * 
	 * @param event Mouse event, clicking the label event will be triggered
	 */
	public void clickRecipe(MouseEvent event) {

		searchResults_RecName = null;
		String t = event.getPickResult().getIntersectedNode().toString();
		String p = "\"([^\"]*)\"";
		Pattern P = Pattern.compile(p);
		Matcher matcher1 = P.matcher(t);
		if (matcher1.find()) {
			searchResults_RecName = matcher1.group(0).replaceAll(p, "$1");
		}

		FXMLLoader loader = new ActionStage(new Stage(), "/view/RecipeScene.fxml", "Recipe", 935, 780).getLoader();
		Stage stage = (Stage) text_search.getScene().getWindow();
		stage.close();
	}

	/**
	 * Back to mainScene
	 * 
	 * @param event action event
	 */
	public void eventButtonBack(ActionEvent event) {

		FXMLLoader loader = new ActionStage(new Stage(), "/view/MainScene.fxml", "Digital CookBook", 735, 555)
				.getLoader();
		Stage stage = (Stage) text_search.getScene().getWindow();
		stage.close();
	}

	public void event_highlightLabel_1(MouseEvent event) {
		label_1.setStyle("-fx-text-fill:red;");
		label_1.setUnderline(true);
	}

	public void event_cancelHighlightLabel_1(MouseEvent event) {
		label_1.setStyle("-fx-text-fill:black;");
		label_1.setUnderline(false);
	}

	public void event_highlightLabel_2(MouseEvent event) {
		label_2.setStyle("-fx-text-fill:red;");
		label_2.setUnderline(true);
	}

	public void event_cancelHighlightLabel_2(MouseEvent event) {
		label_2.setStyle("-fx-text-fill:black;");
		label_2.setUnderline(false);
	}

	public void event_highlightLabel_3(MouseEvent event) {
		label_3.setStyle("-fx-text-fill:red;");
		label_3.setUnderline(true);
	}

	public void event_cancelHighlightLabel_3(MouseEvent event) {
		label_3.setStyle("-fx-text-fill:black;");
		label_3.setUnderline(false);
	}

	public void event_highlightLabel_4(MouseEvent event) {
		label_4.setStyle("-fx-text-fill:red;");
		label_4.setUnderline(true);
	}

	public void event_cancelHighlightLabel_4(MouseEvent event) {
		label_4.setStyle("-fx-text-fill:black;");
		label_4.setUnderline(false);
	}

	public void event_highlightLabel_5(MouseEvent event) {
		label_5.setStyle("-fx-text-fill:red;");
		label_5.setUnderline(true);
	}

	public void event_cancelHighlightLabel_5(MouseEvent event) {
		label_5.setStyle("-fx-text-fill:black;");
		label_5.setUnderline(false);
	}

}
