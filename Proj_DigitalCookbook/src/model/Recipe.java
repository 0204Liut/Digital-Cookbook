package model;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import dataAccess.DAO_Ingredient;
import dataAccess.DAO_Instruction;

/**
 * The recipe class. It stores the information of a recipe.
 * @author kuangzheng
 *
 */
public class Recipe {
	private int recipeID;
	private String recipeName;
	private int editable;
	private int isFavorite;
	private List<Instruction> instructions;
	private Map<Ingredient,String> ingredients; //用值来表示用量
	private String endProductImage; //应为image类型
	private int defaultServeNumber;
	private int cookTime;
	private int prepTime;
    
	/*public Recipe(int recipeID,String recipeName,int editable,int isFavorite,int[] instructionIDs,int[] ingredientIDs,String endProductImage,int defaultServeNumber) {
		this.recipeID=recipeID;
		this.recipeName=recipeName;
		this.editable=editable;
		this.isFavorite=isFavorite;
		this.instructions=new LinkedList<Instruction>();
		
	}*/
	/**
	 * the construction method without parameters. It sets all fields to default values.
	 */
	public Recipe() {
		this.recipeID=0;
		this.recipeName="temp recipe";
		this.editable=0;
		this.isFavorite=0;
		this.instructions=new LinkedList<Instruction>();
		this.ingredients=new TreeMap<Ingredient,String>();
		this.endProductImage="";
		this.defaultServeNumber=0;
		this.cookTime=0;
		this.prepTime=0;
	}
	
	/**
	 * the construction method with all fields. Sets all fields of this recipe.
	 * @param recipeID
	 * @param recipeName
	 * @param editable
	 * @param isFavorite
	 * @param instructionIDs
	 * @param ingredientIDs
	 * @param endProductImage
	 * @param defaultServeNumber
	 * @throws SQLException
	 */
	public Recipe(int recipeID,String recipeName,int editable,int isFavorite,int[] instructionIDs,int[] ingredientIDs,String endProductImage,int defaultServeNumber,int cookTime,int prepTime) throws SQLException {
		this.recipeID=recipeID;
		this.recipeName=recipeName;
		this.editable=editable;
		this.isFavorite=isFavorite;
		this.defaultServeNumber=defaultServeNumber;
		this.endProductImage=endProductImage;
		this.instructions=new LinkedList<Instruction>();
		for(int i: instructionIDs) {
			Instruction ins=DAO_Instruction.queryInstruction(i);
			this.instructions.add(ins);
		}
		this.ingredients=new TreeMap<Ingredient,String>();
		for(int j: ingredientIDs) {
			Ingredient ing=DAO_Ingredient.queryIngredient(j);
			this.ingredients.put(ing, ""+ing.getDefaultAmount()*defaultServeNumber);
		}
		this.cookTime=cookTime;
		this.prepTime=prepTime;
	}
	/**
	 * the construction method with all fields except ingredients and instructions.
	 * should be used along with the bindInsAndIng method
	 * @param recipeID
	 * @param recipeName
	 * @param endProductImage
	 * @param editable
	 * @param isFavorite
	 * @param defaultServeNumber
	 */
	public Recipe(int recipeID, String recipeName,String endProductImage,int editable,int isFavorite,int defaultServeNumber,int cookTime,int prepTime) {
		this.recipeID=recipeID;
		this.recipeName=recipeName;
		this.editable=editable;
		this.isFavorite=isFavorite;
		this.defaultServeNumber=defaultServeNumber;
		this.endProductImage=endProductImage;
		this.instructions=new LinkedList<Instruction>();
		this.ingredients=new TreeMap<Ingredient,String>();
		this.cookTime=cookTime;
		this.prepTime=prepTime;
	}
	
	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	/**
	 * binds all ingredients and instructions exist in data base to this recipe
	 * @throws SQLException
	 */
	public void bindInsAndIng() throws SQLException {
		LinkedList<Instruction> inss=new LinkedList<>();
		inss=DAO_Instruction.queryInstructionByRecID(this.getRecipeID());
		this.instructions=inss;
		LinkedList<Ingredient> ings=new LinkedList<>();
		ings=DAO_Ingredient.queryIngredientByRecID(this.getRecipeID());
		for(Ingredient ing:ings) {
			this.ingredients.put(ing,""+ing.getDefaultAmount()*defaultServeNumber);
		}
	}
	
	/**
	 * update the amount of ingredients associated with this recipe.
	 * should be followed by an update of recipe in the database.
	 */
	public void updateServeNumber() {
		for(Entry<Ingredient, String> ent:ingredients.entrySet()) {
			ent.setValue(""+defaultServeNumber*ent.getKey().getDefaultAmount());
		}
	}
	
	
	public int getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public int getEditable() {
		return editable;
	}
	public void setEditable(int editable) {
		this.editable = editable;
	}
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}
	public String getEndProductImage() {
		return endProductImage;
	}
	public void setEndProductImage(String endProductImage) {
		this.endProductImage = endProductImage;
	}
	public int getDefaultServeNumber() {
		return defaultServeNumber;
	}
	public void setDefaultServeNumber(int defaultServeNumber) {
		this.defaultServeNumber = defaultServeNumber;
	}
	
	public List<Instruction> getInstructions(){
		return this.instructions;
	}
	public Map<Ingredient,String> getIngredients(){
		return this.ingredients;
	}
	public String toString() {
		  return this.recipeID+this.recipeName+this.editable+this.isFavorite+this.endProductImage+this.defaultServeNumber+this.cookTime+this.prepTime;
		 }
	
}
