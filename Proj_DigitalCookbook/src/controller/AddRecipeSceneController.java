package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataAccess.DAO_Ingredient;
import dataAccess.DAO_Instruction;
import dataAccess.DAO_Recipe;
import javafx.event.ActionEvent;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TableView;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.ActionStage;
import util.AlertScreen;
import util.CopyImage;
import util.IsNumeric;
import util.Table_ingredient;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import javafx.scene.control.TableColumn;

/**
 * add a new recipe need to type the recipe name, cookTime,
 * prepareTime,ServiceNumber, ingredients and steps also you need to import
 * photos in for the recipe and each step
 * 
 * @author Liu Tao
 *
 */
public class AddRecipeSceneController implements Initializable {
	@FXML
	private TextField text_addName;
	@FXML
	private ImageView image_addImage;
	@FXML
	private TextField text_addDefaultNumber;
	@FXML
	private Button btn_finish;
	@FXML
	private ScrollPane scp_step;
	@FXML
	private VBox vb_instruc;
	@FXML
	private HBox hb_step2;
	@FXML
	private Button btn_step1;
	@FXML
	private ImageView image_step1;
	@FXML
	private TextArea text_step1;
	@FXML
	private HBox hb_step3;
	@FXML
	private Button btn_step2;
	@FXML
	private ImageView image_step2;
	@FXML
	private TextArea text_step2;
	@FXML
	private HBox hb_step1;
	@FXML
	private Button btn_step3;
	@FXML
	private ImageView image_step3;
	@FXML
	private TextArea text_step3;
	@FXML
	private HBox hb_step21;
	@FXML
	private Button btn_step4;
	@FXML
	private ImageView image_step4;
	@FXML
	private TextArea text_step4;
	@FXML
	private HBox hb_step22;
	@FXML
	private Button btn_step5;
	@FXML
	private ImageView image_step5;
	@FXML
	private TextArea text_step5;
	@FXML
	private HBox hb_step221;
	@FXML
	private Button btn_step6;
	@FXML
	private ImageView image_step6;
	@FXML
	private TextArea text_step6;
	@FXML
	private TextField text_cookTime;
	@FXML
	private TextField text_preTime;
	@FXML
	private TextField ing_name1;
	@FXML
	private TextField ing_quanti1;
	@FXML
	private TextField ing_name2;
	@FXML
	private TextField ing_quanti2;
	@FXML
	private TextField ing_name3;
	@FXML
	private TextField ing_quanti3;
	@FXML
	private TextField ing_name4;
	@FXML
	private TextField ing_quanti4;
	@FXML
	private TextField ing_name5;
	@FXML
	private TextField ing_quanti5;
	@FXML
	private TextField ing_name6;
	@FXML
	private TextField ing_quanti6;
	@FXML
	private TextField ing_name7;
	@FXML
	private TextField ing_quanti7;
	@FXML
	private TextField ing_name8;
	@FXML
	private TextField ing_quanti8;
	@FXML
	private TextField ing_name9;
	@FXML
	private TextField ing_quanti9;
	@FXML
	private TextField ing_name10;
	@FXML
	private TextField ing_quanti10;
	@FXML
	private TextField ing_name11;
	@FXML
	private TextField ing_quanti11;
	@FXML
	private TextField ing_name12;
	@FXML
	private TextField ing_quanti12;
	@FXML
	private TextField ing_name13;
	@FXML
	private TextField ing_quanti13;
	@FXML
	private TextField ing_name14;
	@FXML
	private TextField ing_quanti14;
	@FXML
	private TextField ing_name15;
	@FXML
	private TextField ing_quanti15;

	Recipe newRec = new Recipe();

	public int currentRecID;
	public int currentIngID;
	public int currentInsID;

	public String recImagePath;

	File file = new File("src/image");
	int pathLenth = 0;

	LinkedList<ImageView> q_image = new LinkedList<>();
	LinkedList<TextArea> q_label = new LinkedList<>();

	LinkedList<TextField> ing_name = new LinkedList<>();
	LinkedList<TextField> ing_quanti = new LinkedList<>();

	LinkedList<String> InstrucImage = new LinkedList<String>();
	LinkedList<String> InstrucDesrip = new LinkedList<String>();

	LinkedList<Instruction> targetIns = new LinkedList<>();
	LinkedList<Ingredient> targetIng = new LinkedList<>();

	/**
	 * initialized operation in AddRecipeScene
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根

		q_image.offer(image_step1);
		q_image.offer(image_step2);
		q_image.offer(image_step3);
		q_image.offer(image_step4);
		q_image.offer(image_step5);
		q_image.offer(image_step6);

		q_label.offer(text_step1);
		q_label.offer(text_step2);
		q_label.offer(text_step3);
		q_label.offer(text_step4);
		q_label.offer(text_step5);
		q_label.offer(text_step6);

		ing_name.offer(ing_name1);
		ing_name.offer(ing_name2);
		ing_name.offer(ing_name3);
		ing_name.offer(ing_name4);
		ing_name.offer(ing_name5);
		ing_name.offer(ing_name6);
		ing_name.offer(ing_name7);
		ing_name.offer(ing_name8);
		ing_name.offer(ing_name9);
		ing_name.offer(ing_name10);
		ing_name.offer(ing_name11);
		ing_name.offer(ing_name12);
		ing_name.offer(ing_name13);
		ing_name.offer(ing_name14);
		ing_name.offer(ing_name15);

		ing_quanti.offer(ing_quanti1);
		ing_quanti.offer(ing_quanti2);
		ing_quanti.offer(ing_quanti3);
		ing_quanti.offer(ing_quanti4);
		ing_quanti.offer(ing_quanti5);
		ing_quanti.offer(ing_quanti6);
		ing_quanti.offer(ing_quanti7);
		ing_quanti.offer(ing_quanti8);
		ing_quanti.offer(ing_quanti9);
		ing_quanti.offer(ing_quanti10);
		ing_quanti.offer(ing_quanti11);
		ing_quanti.offer(ing_quanti12);
		ing_quanti.offer(ing_quanti13);
		ing_quanti.offer(ing_quanti14);
		ing_quanti.offer(ing_quanti15);

		try {
			currentRecID = DAO_Recipe.queryMaxRecID();
			currentIngID = DAO_Ingredient.queryMaxIngID();
			currentInsID = DAO_Instruction.queryMaxInsID();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		newRec.setEditable(1);
		newRec.setIsFavorite(0);

		pathLenth = file.getAbsolutePath().length();

	}

	// Event Listener on Button[#btn_finish].onAction
	/**
	 * commit to add the new recipe to database Checking the input data whether they
	 * satisfy the needs before write these information to database
	 * 
	 * @param event action event
	 * @throws SQLException
	 */
	@FXML
	public void eventButtonFinished(ActionEvent event) throws SQLException {
		// TODO Autogenerated

		boolean b_InsDescripLength = true;

		boolean b_InsDescripNotEmpty = true;

		boolean b_InsImageLength = true;

		boolean b_InsImageNotEmpty = true;

		boolean b_RecImageLength = true;
		if (!(recImagePath == null) && recImagePath.length() > 1000) {
			b_RecImageLength = false;
		}

		boolean b_RecImageNotEmpty = true;
		if (recImagePath == null) {
			b_RecImageNotEmpty = false;
		}

		boolean b_RecNameLength = true;
		if (text_addName.getText().length() > 100) {
			b_RecNameLength = false;
		}

		boolean b_RecNameNotEmpty = true;
		if (text_addName.getText().isEmpty() == true) {
			b_RecNameNotEmpty = false;
		}

		boolean b_IngNameLength = true;

		boolean b_IngNameNotEmpty = true;

		boolean b_IngQuantiNotEmpty = true;

		boolean b_CookTimeNotEmpty = true;
		if (text_cookTime.getText().isEmpty() == true) {
			b_CookTimeNotEmpty = false;
		}

		boolean b_PrepTimeNotEmpty = true;
		if (text_preTime.getText().isEmpty() == true) {
			b_PrepTimeNotEmpty = false;
		}

		boolean b_DefaultNumNotEmpty = true;
		if (text_addDefaultNumber.getText().isEmpty() == true) {
			b_DefaultNumNotEmpty = false;
		}

		boolean b_InsCorresponding = true;
		boolean b_IngCorresponding = true;

		newRec.setRecipeName(text_addName.getText());

		if (b_DefaultNumNotEmpty) {
			newRec.setDefaultServeNumber(Integer.valueOf(text_addDefaultNumber.getText()));
		}
		if (b_CookTimeNotEmpty) {
			newRec.setCookTime(Integer.valueOf(text_cookTime.getText()));
		}
		if (b_PrepTimeNotEmpty) {
			newRec.setPrepTime(Integer.valueOf(text_preTime.getText()));
		}
		if (b_RecImageNotEmpty) {
			newRec.setEndProductImage(recImagePath);
		}
		newRec.setRecipeID(currentRecID + 1);

		int checkFilled_insImage = 0;
		int checkFilled_insDescrip = 0;

		for (int i = 0; i < q_label.size(); i++) {
			if (q_image.get(i).getImage() != null) {
				checkFilled_insImage++;
			}

			if (q_label.get(i).getText().isEmpty() == false) {
				checkFilled_insDescrip++;
			}
		}

		if (checkFilled_insImage == checkFilled_insDescrip) {

			for (int i = 0; i < InstrucDesrip.size(); i++) {
				if (InstrucDesrip.get(i).length() > 3000) {
					b_InsDescripLength = false;
				}
			}

			if (q_label.get(0).getText() == null) {
				b_InsDescripNotEmpty = false;
			}

			for (int i = 0; i < InstrucImage.size(); i++) {
				if (InstrucImage.get(i).length() > 1000) {
					b_InsImageLength = false;
				}
			}

			if (InstrucImage.size() == 0) {
				b_InsImageNotEmpty = false;
			}

			for (int i = 0; i < checkFilled_insImage; i++) {
				targetIns.add(new Instruction(currentRecID + 1, currentInsID + i + 1, q_label.get(i).getText(),
						InstrucImage.get(i)));
			}
		} else {
			AlertScreen.getMessageScreen("warning", "description not corresponding");
			b_InsCorresponding = false;
		}

		int checkFilled_ingQuanti = 0;
		int checkFilled_ingName = 0;
		for (int i = 0; i < ing_name.size(); i++) {
			if (ing_quanti.get(i).getText().isEmpty() == false) {
				checkFilled_ingQuanti++;
			}

			if (ing_name.get(i).getText().isEmpty() == false) {
				checkFilled_ingName++;
			}
		}

		if (checkFilled_ingQuanti == checkFilled_ingName) {

			for (int i = 0; i < ing_name.size(); i++) {
				if (ing_name.get(i).getLength() > 100) {
					b_IngNameLength = false;
				}
			}

			for (int i = 0; i < 3; i++) {
				if (ing_name.get(i).getText().isEmpty() == true) {
					b_IngNameNotEmpty = false;
				}
			}

			for (int i = 0; i < 3; i++) {
				if (ing_quanti.get(i).getText().isEmpty() == true) {
					b_IngQuantiNotEmpty = false;
				}
			}

			for (int i = 0; i < checkFilled_ingName; i++) {
				targetIng.add(new Ingredient(currentRecID + 1, currentIngID + i + 1, ing_name.get(i).getText(),
						Integer.valueOf(ing_quanti.get(i).getText())));
			}
		} else {
			AlertScreen.getMessageScreen("warning", "ingredient not corresponding");
			b_IngCorresponding = false;
		}

		if (b_InsDescripLength == false) {
			AlertScreen.getMessageScreen("warning", "Description too long! Max length is 3000");
		}

		if (b_InsDescripNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Instruction Description is empty! At least add 1 Instruction");
		}

		if (b_InsImageLength == false) {
			AlertScreen.getMessageScreen("warning", "Path of Instruction Image too long! Maximum length is 1000");
		}

		if (b_InsImageNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Instruction Image is empty!");
		}

		if (b_RecImageLength == false) {
			AlertScreen.getMessageScreen("warning", "Path of Recipe Image too long! Maximum length is 1000!");
		}

		if (b_RecImageNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Recipe Image is empty!");
		}

		if (b_RecNameLength == false) {
			AlertScreen.getMessageScreen("warning", "Recipe Name too long! Maximum length is 100!");
		}

		if (b_RecNameNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Recipe Name is empty!");
		}

		if (b_IngNameLength == false) {
			AlertScreen.getMessageScreen("warning", "Ingredient Name too long! Maximum length is 100!");
		}

		if (b_IngNameNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Ingredient Name is empty! At least add 3 Ingredient");
		}

		if (b_IngQuantiNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Ingredient Quantity is empty! At least add 3 Ingredient");
		}

		if (b_CookTimeNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Cook Time is empty!");
		}

		if (b_PrepTimeNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Prepration Time is empty!");
		}

		if (b_DefaultNumNotEmpty == false) {
			AlertScreen.getMessageScreen("warning", "Service Number is empty!");
		}

		boolean preCheck = b_InsDescripLength && b_InsDescripNotEmpty && b_InsImageLength && b_InsImageNotEmpty
				&& b_RecImageLength && b_RecImageNotEmpty && b_RecNameLength && b_RecNameNotEmpty && b_IngNameLength
				&& b_IngNameNotEmpty && b_IngQuantiNotEmpty && b_CookTimeNotEmpty && b_PrepTimeNotEmpty
				&& b_DefaultNumNotEmpty && b_IngCorresponding && b_InsCorresponding;

		if (preCheck) {
			
			Boolean addRecipe = AlertScreen.getMessageScreen("Confirm", "add this recipe");
			if (addRecipe) {
				DAO_Recipe.addRecipe(newRec);
				for (int i = 0; i < targetIns.size(); i++) {
					DAO_Instruction.addInstruction(targetIns.get(i));
				}

				for (int i = 0; i < checkFilled_ingQuanti; i++) {
					DAO_Ingredient.addIngredient(targetIng.get(i));
				}
				FXMLLoader loader = new ActionStage(new Stage(), "/view/MainScene.fxml", "Digital CookBook", 735, 555)
						.getLoader();
				Stage stage = (Stage) scp_step.getScene().getWindow();
				stage.close();
			}
		} else {
			AlertScreen.getMessageScreen("Reminding", "Please fill in all conponents!!!");
		}

	}

	/**
	 * add a local image as the main image for this recipe copy the local image to
	 * this project, only do operations to the copied image
	 * 
	 * @param event action event
	 * @throws IOException
	 */
	public void eventButtonAddMainImage(ActionEvent event) throws IOException {

		Stage selectImage = new Stage();

		FileChooser fc = new FileChooser();

		fc.setTitle("choose your main image for recipe");
		fc.getExtensionFilters().add(new ExtensionFilter("image type", "*.jpg", "*.png"));

		File file = fc.showOpenDialog(selectImage);
		if (file == null) {
			return;
		}

		if (CopyImage.copyFile(file)) {

			String path = CopyImage.getFilePathInDir(file);
			File cpyFile = new File(path);
			recImagePath = cpyFile.getAbsolutePath().substring(pathLenth - 10).replaceAll("\\\\", "/");
			image_addImage.setImage(new Image(cpyFile.getAbsolutePath().replaceAll("\\\\", "/")));
		} else {
			AlertScreen.getMessageScreen("Warning", "image name duplicate!");
		}

	}

	/**
	 * add the local image as the instruction image for this recipe copy the local
	 * images to this project, only do operations to the copied images
	 * 
	 * @param event action event
	 * @throws IOException
	 */
	public void eventButtonAddStepImage(ActionEvent event) throws IOException {

		Stage stepImage = new Stage();

		FileChooser fc = new FileChooser();

		fc.setTitle("choose your step image for recipe");
		fc.getExtensionFilters().add(new ExtensionFilter("image type", "*.jpg", "*.png", "*.PNG"));

		File file = fc.showOpenDialog(stepImage);

		if (file == null) {
			return;
		}

		String t = event.getTarget().toString();
		String p = "(?<=step).*(?=,)";
		Pattern P = Pattern.compile(p);
		Matcher matcher1 = P.matcher(t);

		if (CopyImage.copyFile(file)) {
			if (matcher1.find()) {
				String a = matcher1.group(0).replaceAll(p, "$1");

				String path = CopyImage.getFilePathInDir(file);
				File cpyFile = new File(path);
				switch (a) {
				case "1": {
					image_step1.setImage(new Image(cpyFile.getAbsolutePath().replaceAll("\\\\", "/")));
					InstrucImage.offer(cpyFile.getAbsolutePath().substring(pathLenth - 10).replaceAll("\\\\", "/"));
					break;
				}
				case "2": {
					image_step2.setImage(new Image(cpyFile.getAbsolutePath().replaceAll("\\\\", "/")));
					InstrucImage.offer(cpyFile.getAbsolutePath().substring(pathLenth - 10).replaceAll("\\\\", "/"));
					break;
				}
				case "3": {
					image_step3.setImage(new Image(cpyFile.getAbsolutePath().replaceAll("\\\\", "/")));
					InstrucImage.offer(cpyFile.getAbsolutePath().substring(pathLenth - 10).replaceAll("\\\\", "/"));
					break;
				}
				case "4": {
					image_step4.setImage(new Image(cpyFile.getAbsolutePath().replaceAll("\\\\", "/")));
					InstrucImage.offer(cpyFile.getAbsolutePath().substring(pathLenth - 10).replaceAll("\\\\", "/"));
					break;
				}
				case "5": {
					image_step5.setImage(new Image(cpyFile.getAbsolutePath().replaceAll("\\\\", "/")));
					InstrucImage.offer(cpyFile.getAbsolutePath().substring(pathLenth - 10).replaceAll("\\\\", "/"));
					break;
				}
				case "6": {
					image_step6.setImage(new Image(cpyFile.getAbsolutePath().replaceAll("\\\\", "/")));
					InstrucImage.offer(cpyFile.getAbsolutePath().substring(pathLenth - 10).replaceAll("\\\\", "/"));
					break;
				}
				}
			}

		} else {
			AlertScreen.getMessageScreen("Warning", "image name duplicate!");
		}
	}

	// Event Listener on TextField[#text_addDefaultNumber].onAction
	/**
	 * check the input for service number if it is not a positive number, an alert
	 * scene will show and the written data will be deleted
	 * 
	 * @param event key event, once typing the alert will be shown if not a positive
	 *              number
	 */
	@FXML
	public void eventServiceNumber(KeyEvent event) {
		if (text_addDefaultNumber.getText().isEmpty() == false
				&& IsNumeric.isNumeric(text_addDefaultNumber.getText()) == false) {
			text_addDefaultNumber.setText(null);
			AlertScreen.getMessageScreen("Warning", "please type a positive number only!");
		}
	}

	/**
	 * check the input for cook time if it is not a positive number, an alert scene
	 * will show and the written data will be deleted
	 * 
	 * @param event key event, once typing the alert will be shown if not a positive
	 *              number
	 */
	public void eventCookTime(KeyEvent event) {
		if (text_cookTime.getText().isEmpty() == false && IsNumeric.isNumeric(text_cookTime.getText()) == false) {
			text_cookTime.setText(null);
			AlertScreen.getMessageScreen("Warning", "please type a positive number only!");
		}
	}

	// Event Listener on TextField[#text_preTime].onAction
	/**
	 * check the input for preparation time if it is not a positive number, an alert
	 * scene will show and the written data will be deleted
	 * 
	 * @param event key event, once typing the alert will be shown if not a positive
	 *              number
	 */
	@FXML
	public void eventPreTIme(KeyEvent event) {
		if (text_preTime.getText().isEmpty() == false && IsNumeric.isNumeric(text_preTime.getText()) == false) {
			text_preTime.setText(null);
			AlertScreen.getMessageScreen("Warning", "please type a positive number only!");
		}
	}

	/**
	 * check the input for quantity of ingredients if it is not a positive number,
	 * an alert scene will show and the written data will be deleted
	 * 
	 * @param event key event, once typing the alert will be shown if not a positive
	 *              number
	 */
	public void eventIngQuanti(KeyEvent event) {
		for (int i = 0; i < ing_quanti.size(); i++) {
			if (ing_quanti.get(i).getText().isEmpty() == false
					&& IsNumeric.isNumeric(ing_quanti.get(i).getText()) == false) {
				AlertScreen.getMessageScreen("Type Again", "Quantities can be postive number only!");
				ing_quanti.get(i).setText(null);
			}
		}
	}

	/**
	 * cancel the process of adding the recipe all information will not be saved if
	 * you have added a image before, the copied image will also be deleted
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void eventButtonQuit(ActionEvent event) throws IOException {
		Boolean quitAdding = AlertScreen.getMessageScreen("Warning",
				"Quit adding recipe, all content will not be saved");

		if (quitAdding) {
			FXMLLoader loader = new ActionStage(new Stage(), "/view/MainScene.fxml", "Digital CookBook", 735, 555)
					.getLoader();
			Stage stage = (Stage) scp_step.getScene().getWindow();
			stage.close();

//			if (!(recImagePath == null)) {
//				Path RecPath = Paths.get(file.getAbsolutePath() + recImagePath.substring(10));
//				Files.deleteIfExists(RecPath);
//			}
//
//			for (int i = 0; i < InstrucImage.size(); i++) {
//				Path instrucPath = Paths.get(file.getAbsolutePath() + InstrucImage.get(i).substring(10));
//				Files.deleteIfExists(instrucPath);
//			}

		}
	}

}
