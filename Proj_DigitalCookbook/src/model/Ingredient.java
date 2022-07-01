package model;

/**
 * The ingredient class. It stores the information of an ingredient.
 * @author kuangzheng
 *
 */
public class Ingredient implements Comparable<Ingredient>{
	private int recipeID;
	private int ingredientID;
	private String ingredientName;
	private int defaultAmount;//一个人的用量
	
	public Ingredient() {
		this.recipeID=0;
		this.ingredientID=0;
		this.ingredientName="";
		this.defaultAmount=0;
	}
	public Ingredient(int recipeID, int ingredientID, String ingredientName, int defaultAmount) {
		super();
		this.recipeID = recipeID;
		this.ingredientID = ingredientID;
		this.ingredientName = ingredientName;
		this.defaultAmount = defaultAmount;
	}
	public Ingredient(String ingredientName, int defaultAmount) {
		super();
		this.ingredientName = ingredientName;
		this.defaultAmount = defaultAmount;
	}
	
	public int getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	public int getIngredientID() {
		return ingredientID;
	}
	public void setIngredientID(int ingredientID) {
		this.ingredientID = ingredientID;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public int getDefaultAmount() {
		return defaultAmount;
	}
	public void setDefaultAmount(int defaultAmount) {
		this.defaultAmount = defaultAmount;
	}
	@Override
	public int compareTo(Ingredient o) {
		// TODO 自动生成的方法存根
		return this.ingredientID-o.getIngredientID();
		
	}
	
	public String toString() {
		  return this.ingredientID+" "+this.recipeID+this.ingredientName+this.defaultAmount;
		 }

}