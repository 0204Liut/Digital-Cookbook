package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import model.Ingredient;
import model.Recipe;
/**
 * data access object
 * this class can be used to query, delete, update and add data to/from the ingredient table
 * @author kuangzheng
 *
 */
public class DAO_Ingredient {
	/**
	 * add a new ingredient. If there are no ingredients with the same ID exist in the ingredient table, and the added ingredient is valid, the data will be successfully added.
	 * @param ing: the ingredient to be added
	 * @return true if successfully add
	 * @throws SQLException
	 */
	public static boolean addIngredient(Ingredient ing) throws SQLException {
		if(isIngredientValid(ing) && !isIngredientExisted(ing.getIngredientID())) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="INSERT INTO ingredient VALUES ("+ing.getIngredientID()+","+"'"+ing.getIngredientName()+"'"+","+ing.getRecipeID()+","+ing.getDefaultAmount()+")";
		
			stmt.executeUpdate(tempStmt);
			con.close();
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * delete an ingredient specified by ID. The ingredient must exist in the table, otherwise the method will do nothing.
	 * @param ingID: the ingredient to be deleted
	 * @return true if delete successfully
	 * @throws SQLException
	 */
	public static boolean deleteIngredient(int ingID) throws SQLException {
		if(isIngredientExisted(ingID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="DELETE FROM ingredient WHERE ingredientID="+ingID;
			stmt.executeUpdate(tempStmt);
			con.close();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * update an ingredient that has the same ID with the parameter ing. Set ingredientName, recipeID and defaultAmount of the ingredient.
	 * the update will be successful if:
	 * 1.An ingredient that has the same ID with the parameter ing exists in the table.
	 * 2.The parameter ing is valid, i.e., it has the right data format and correctly binds a recipe.
	 * @param ing:specifies which ingredient is to be updated and the updated values.
	 * @return true if update successfully
	 * @throws SQLException
	 */
	public static boolean updateIngredient(Ingredient ing) throws SQLException {
		if(isIngredientValid(ing) && isIngredientExisted(ing.getIngredientID())) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="UPDATE ingredient SET ingredientName="+"'"+ing.getIngredientName()+"'"+",recipeID="+ing.getRecipeID()+",defaultAmount="+ing.getDefaultAmount()+" WHERE ingredientID="+ing.getIngredientID();                                            ;
			int rowCount=stmt.executeUpdate(tempStmt);
			
			con.close();
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * query for an ingredient specified by the ingredient ID, return the queried ingredient
	 * @param ingID: the ingredient to be queried
	 * @return: the queried ingredient. If the ingredient doesn't exist, it will return a null ingredient.
	 * @throws SQLException
	 */
	public static Ingredient queryIngredient(int ingID) throws SQLException {
		if(isIngredientExisted(ingID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from ingredient WHERE ingredientID="+ingID;
			ResultSet rset=stmt.executeQuery(tempStmt);
			Ingredient ing=new Ingredient();
			rset.next();
			ing.setIngredientID(rset.getInt("ingredientID"));
			ing.setIngredientName(rset.getString("ingredientName"));
			ing.setRecipeID(rset.getInt("recipeID"));
			ing.setDefaultAmount(rset.getInt("defaultAmount"));
			
			return ing;
		}
		else {
			return new Ingredient();
		}
	}
	/**
	 * query for a list of ingredients that have the specified recipeID. Can be used for the assignment of the "ingredients" field in recipe 
	 * @param recID: the specified recipe ID
	 * @return a list of ingredients that have the specified recipeID. If the ingredient do not exist, it will return an empty list.
	 * @throws SQLException
	 */
	public static LinkedList<Ingredient> queryIngredientByRecID(int recID) throws SQLException{
		if(isIngredientExistedByRecID(recID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from ingredient WHERE recipeID="+recID;
			ResultSet rset=stmt.executeQuery(tempStmt);
			LinkedList<Ingredient> ings=new LinkedList<Ingredient>();
			while(rset.next()) {
				Ingredient ing=new Ingredient();
				ing.setIngredientID(rset.getInt("ingredientID"));
				ing.setIngredientName(rset.getString("ingredientName"));
				ing.setRecipeID(rset.getInt("recipeID"));
				ing.setDefaultAmount(rset.getInt("defaultAmount"));
				ings.add(ing);
			}
			
			return ings;
		}
		else {
			return new LinkedList<Ingredient>();
		}
	}
	
	/**
	  * return the max ingredient ID in the table. Used for generating a new recipe ID when add a new recipe.
	  * @return the max ingredient ID in the table
	  * @throws SQLException
	  */
	 public static int queryMaxIngID() throws SQLException{
	  Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
	  Statement stmt = con.createStatement();
	  String tempStmt="SELECT MAX(ingredientID) FROM ingredient";
	  ResultSet rset=stmt.executeQuery(tempStmt);
	  rset.next();
	  return rset.getInt("MAX(ingredientID)");
	 }
	
	/**
	 * check if an ingredient is in accord with the format defined in the ingredient table, and whether it binds an existed recipe.
	 * @param ing
	 * @return true if the ingredient is valid. if any of the fields' format is not right, or the recipe ID of this ingredient does not exist in the recipe table, it will return false.
	 */
	public static boolean isIngredientValid(Ingredient ing) {
		boolean result=true;
		if(ing.getIngredientID()<=0) {
			result=false;
		}
		if(ing.getIngredientName().length()>100) {
			result=false;
		}
		if(ing.getRecipeID()<=0 || !DAO_Recipe.isRecipeExisted(ing.getRecipeID())) {
			result=false;
		}
		if(ing.getDefaultAmount()<=0) {
			result=false;
		}
		return result;
	}
	/**
	 * check if the ingredient already exists in the ingredient table
	 * @param ingID
	 * @return true if exists.
	 */
	public static boolean isIngredientExisted(int ingID) {
		boolean result=true;
		if(ingID<=0) {
			result=false;
		}
		else {
			Connection con;
			try {
				con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
				Statement stmt = con.createStatement();
				String tempStmt="SELECT * from ingredient WHERE ingredientID="+ingID;
				ResultSet rset=stmt.executeQuery(tempStmt);
				
				if(!rset.next()) {
					result=false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	
	/**
	 * check if there are ingredients that belong to the specified recipe exist in the ingredient table.
	 * @param recID: specify the recipe to be check
	 * @return true if exists
	 */
	public static boolean isIngredientExistedByRecID(int recID) {
		boolean result=true;
		if(recID<=0) {
			result=false;
		}
		else {
			Connection con;
			try {
				con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
				Statement stmt = con.createStatement();
				String tempStmt="SELECT * from ingredient WHERE recipeID="+recID;
				ResultSet rset=stmt.executeQuery(tempStmt);
				
				if(!rset.next()) {
					result=false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	
	
	
	
	
}
