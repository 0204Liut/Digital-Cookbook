package util;

/**
 * class for store ingredient data in tableView
 * used in RecipeSceneController.java
 * @author Liu Tao
 *
 */
public class Table_ingredient {
	String name;
	String finalAmmout;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFinalAmmout() {
		return finalAmmout;
	}

	public void setFinalAmmout(String finalAmmout) {
		this.finalAmmout = finalAmmout;
	}

	/**
	 * constructor of Table_ingredient
	 * 
	 * @param name        first row, ingredient's name
	 * @param finalAmmout second row, ingredient's final Amount
	 */
	public Table_ingredient(String name, String finalAmmout) {
		super();
		this.name = name;
		this.finalAmmout = finalAmmout;
	}
}