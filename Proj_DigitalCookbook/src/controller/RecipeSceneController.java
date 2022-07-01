package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import dataAccess.DAO_Ingredient;
import dataAccess.DAO_Instruction;
import dataAccess.DAO_Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import util.ActionStage;
import util.AlertScreen;
import util.IsNumeric;
import util.Table_ingredient;
import javafx.scene.control.CheckBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Recipe Scene's controller show detail information about the Recipe
 * 
 * @author Liu Tao
 *
 */
public class RecipeSceneController implements Initializable {
	@FXML
	private ImageView image_mainImage;
	@FXML
	private Label text_RecName;
	@FXML
	private TextField text_defaultNumber;
	@FXML
	private TextField text_cookTime;
	@FXML
	private TextField text_preTime;
	@FXML
	private ImageView image_imstruc;
	@FXML
	private TextArea text_instruc;
	@FXML
	private TableView tableView_ingredients;
	@FXML
	private TableColumn column_name;
	@FXML
	private TableColumn column_quanti;
	@FXML
	private Button btn_later;
	@FXML
	private Button btn_former;
	@FXML
	private Button btn_edit;
	@FXML
	private CheckBox chbox_favorate;
	@FXML
	private TextField text_InstrucPage;

	public static int RecipeID;

	Recipe rec = new Recipe();

//	Ingredient targetIngred = new Ingredient();

	LinkedList<Instruction> targetInstruc = new LinkedList<Instruction>();

	LinkedList<String> InstrucImage = new LinkedList<String>();

	LinkedList<String> InstrucDesrip = new LinkedList<String>();

	private int page = 1;

	File file = new File("src/image");
	int pathLength = 0;

	/**
	 * initialized operation in RecipeScene
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		pathLength = file.getAbsolutePath().length();

		try {

			rec = DAO_Recipe.queryRecipebyNamePrecise(SearchSceneController.searchResults_RecName);
			rec.bindInsAndIng();
			image_mainImage.setImage(new Image(file.getAbsolutePath().replaceAll("\\\\", "/")
					+ rec.getEndProductImage().substring(10).replaceAll("\\\\", "/")));
			text_RecName.setText(rec.getRecipeName());
			text_defaultNumber.setText(String.valueOf(rec.getDefaultServeNumber()));
			text_cookTime.setText(String.valueOf(rec.getCookTime()));
			text_preTime.setText(String.valueOf(rec.getPrepTime()));

			int RecipeID = rec.getRecipeID();

			targetInstruc = DAO_Instruction.queryInstructionByRecID(RecipeID);
			if (targetInstruc.isEmpty() == false) {
				for (int i = 0; i < targetInstruc.size(); i++) {
					InstrucImage.add(targetInstruc.get(i).getIns_image());
					InstrucDesrip.add(targetInstruc.get(i).getIns_description());
				}
			}

			image_imstruc.setImage(new Image(file.getAbsolutePath() + InstrucImage.get(0).substring(10)));
			text_instruc.setText(InstrucDesrip.get(0));
			text_InstrucPage.setText(String.valueOf(page));

			btn_former.setVisible(false);
			if (InstrucDesrip.size() == 1) {
				btn_later.setVisible(false);
			}

			column_name.setCellValueFactory(new PropertyValueFactory<>("name"));

			column_quanti.setCellValueFactory(new PropertyValueFactory<>("finalAmmout"));

			LinkedList<Ingredient> targetIngred = DAO_Ingredient.queryIngredientByRecID(RecipeID);
			for (int i = 0; i < targetIngred.size(); i++) {
				Table_ingredient ingred = new Table_ingredient(targetIngred.get(i).getIngredientName(),
						(targetIngred.get(i).getDefaultAmount() * rec.getDefaultServeNumber() + ""));
				tableView_ingredients.getItems().add(ingred);
			}

			if (rec.getIsFavorite() == 1) {
				chbox_favorate.setSelected(true);
			} else {
				chbox_favorate.setSelected(false);
			}

			if (rec.getEditable() == 0) {
				btn_edit.setVisible(false);
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	/**
	 * go back to the last steps of recipe instruction if now it is in the first
	 * step, the button will invisible
	 * 
	 * @param event
	 */
	public void eventButtonLeft(ActionEvent event) {

		btn_later.setVisible(true);

		image_imstruc.setImage(null);
		text_instruc.setText(null);
		image_imstruc.setImage(new Image(file.getAbsolutePath() + InstrucImage.get(page - 2).substring(10)));
		text_instruc.setText(InstrucDesrip.get(page - 2));
		page--;
		text_InstrucPage.setText(String.valueOf(page));

		if (text_InstrucPage.getText().equals("1")) {
			btn_former.setVisible(false);
		}

	}

	/**
	 * go to the next step of recipe instruction if now it is in the last step, the
	 * button will invisible
	 * 
	 * @param event
	 */
	public void eventButtonRight(ActionEvent event) {

		btn_former.setVisible(true);

		image_imstruc.setImage(null);
		text_instruc.setText(null);
		image_imstruc.setImage(new Image(file.getAbsolutePath() + InstrucImage.get(page).substring(10)));
		text_instruc.setText(InstrucDesrip.get(page));
		page++;
		text_InstrucPage.setText(String.valueOf(page));

		if (text_InstrucPage.getText().equals(rec.getInstructions().size() + "")) {
			btn_later.setVisible(false);
		}

	}

	/**
	 * change the Service number, and the quantities in ingredient will also change
	 * correspondingly
	 * 
	 * @param event action event
	 * @throws SQLException
	 */
	public void eventChangeDefaultNumber(ActionEvent event) throws SQLException {
		String d_ammount = text_defaultNumber.getText();
		if (IsNumeric.isNumeric(d_ammount) == false) {
			text_defaultNumber.setText(rec.getDefaultServeNumber() + "");
			AlertScreen.getMessageScreen("Warning", "please type a positive number only!");

		} else {
			int d_number = Integer.parseInt(d_ammount);
			rec.setDefaultServeNumber(d_number);
			rec.updateServeNumber();
			if (DAO_Recipe.updateRecipe(rec)) {

				tableView_ingredients.getItems().clear();
				column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
				column_quanti.setCellValueFactory(new PropertyValueFactory<>("finalAmmout"));

				LinkedList<Ingredient> targetIngred = DAO_Ingredient.queryIngredientByRecID(rec.getRecipeID());
				for (int i = 0; i < targetIngred.size(); i++) {
					Table_ingredient ingred = new Table_ingredient(targetIngred.get(i).getIngredientName(),
							(targetIngred.get(i).getDefaultAmount() * rec.getDefaultServeNumber() + ""));
					tableView_ingredients.getItems().add(ingred);
				}
			} else {
				AlertScreen.getMessageScreen("Warning", "updata unsuccessful!");
			}
		}

	}

	/**
	 * additional task: an attribute for recipe: if the check is chosen, this recipe
	 * will be added to the favorite list and shown in the mainScence
	 * 
	 * @param event
	 * @throws SQLException
	 */
	public void eventIsFavorite(ActionEvent event) throws SQLException {

		if (chbox_favorate.isSelected()) {

			rec.setIsFavorite(1);
			DAO_Recipe.updateRecipe(rec);
			Boolean addFavor = AlertScreen.getMessageScreen("Info", "add successfully!");

		} else {

			Boolean cancelFavor = AlertScreen.getMessageScreen("Reminding", "remove this recipe from your favorite?");
			if (cancelFavor) {
				chbox_favorate.setSelected(false);

				rec.setIsFavorite(0);
				DAO_Recipe.updateRecipe(rec);
				AlertScreen.getMessageScreen("Info", "remove successfully!");
			} else {
				chbox_favorate.setSelected(true);
			}
		}

	}

	/**
	 * go back to the MainScene
	 * 
	 * @param event action event
	 */
	public void eventButtonBack(ActionEvent event) {
		FXMLLoader loader = new ActionStage(new Stage(), "/view/MainScene.fxml", "Digital CookBook", 735, 555)
				.getLoader();
		Stage stage = (Stage) btn_later.getScene().getWindow();
		stage.close();
	}

	/**
	 * edit the recipe once click the edit recipe scene will be shown
	 * 
	 * @param event
	 */
	public void eventButtonEdit(ActionEvent event) {

		RecipeID = rec.getRecipeID();
		FXMLLoader loader = new ActionStage(new Stage(), "/view/EditRecipeScene.fxml", "Edit Recipe", 1050, 640)
				.getLoader();
		Stage stage = (Stage) btn_later.getScene().getWindow();
		stage.close();
	}

	/**
	 * delete this recipe have to confirm the deletion after clicking the button
	 * 
	 * @param event action event
	 * @throws SQLException
	 * @throws IOException
	 */
	public void eventButtonDelete(ActionEvent event) throws SQLException, IOException {

		Boolean deleteRecipe = AlertScreen.getMessageScreen("Confirm", "deleting this recipe?");

		if (deleteRecipe) {

			DAO_Recipe.deleteRecipe(rec.getRecipeID());

			Path RecPath = Paths.get(file.getAbsolutePath() + rec.getEndProductImage().substring(10));
			Files.deleteIfExists(RecPath);

			for (int i = 0; i < InstrucImage.size(); i++) {
				Path instrucPath = Paths.get(file.getAbsolutePath() + InstrucImage.get(i).substring(10));
				Files.deleteIfExists(instrucPath);
			}

			AlertScreen.getMessageScreen("Message", "you have deleted this recipe.");
			FXMLLoader loader = new ActionStage(new Stage(), "/view/MainScene.fxml", "Digital CookBook", 735, 555)
					.getLoader();
			Stage stage = (Stage) btn_later.getScene().getWindow();
			stage.close();

		}
	}

}
