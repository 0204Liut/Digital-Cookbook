package dataAccess;
import java.sql.*;
import java.util.LinkedList;

import model.Recipe;
/**
 * data access object
 * this class can be used to query, delete, update and add data to/from the recipe table
 * @author kuangzheng
 *
 */
public class DAO_Recipe {
	
	
	
	/**
	 * add a new recipe if the recipe format is valid and there are no recipes with the same ID exist in the table
	 * @param rec: the new recipe to be added
	 * 
	 * @return: true if a recipe is successfully added
	 * @throws SQLException
	 */
	public static boolean addRecipe(Recipe rec) throws SQLException {
		if(!isRecipeExisted(rec.getRecipeID()) && isRecipeValid(rec)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="INSERT INTO recipe VALUES ("+rec.getRecipeID()+","+"'"+rec.getRecipeName()+"'"+","+rec.getEditable()+","+rec.getIsFavorite()+","+"'"+rec.getEndProductImage()+"'"+","+rec.getDefaultServeNumber()+","+rec.getCookTime()+","+rec.getPrepTime()+")";
			//stmt.executeUpdate("INSERT INTO recipe VALUES (rec.getRecipeID(),rec.getRecipeName(),rec.getEditable(),rec.getIsFavorite(),rec.getEndProductImage(),rec.getDefaultServeNumber()");
			stmt.executeUpdate(tempStmt);
			con.close();
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * delete a recipe specified by the recipe ID
	 * @param recID: the ID of the recipe to be deleted
	 * 
	 * @return: true if delete is successful, otherwise false
	 * @throws SQLException
	 */
	public static boolean deleteRecipe(int recID) throws SQLException{
		if(isRecipeExisted(recID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="DELETE FROM recipe WHERE recipeID="+recID;
			stmt.executeUpdate(tempStmt);
			con.close();
			return true;
		}
		else {
			return false;
		}
		
		
	}
	/**
	 * update the recipe that has the same ID with the parameter rec. 
	 * The update will be successfull if:
	 * 1.A recipe that has the same ID with the parameter rec exists in the table.
	 * 2.The parameter rec is valid, i.e., it has the right data format.
	 * @param rec: specifies which recipe is to be updated and the updated values.
	 *
	 * @return: true if update is successful.
	 * @throws SQLException
	 */
	public static boolean updateRecipe(Recipe rec)throws SQLException{
		if(isRecipeValid(rec) && isRecipeExisted(rec.getRecipeID())) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="UPDATE recipe SET recipeName="+"'"+rec.getRecipeName()+"'"+",editable="+rec.getEditable()+",isFavorite="+rec.getIsFavorite()+",endProductImage="+"'"+rec.getEndProductImage()+"'"+",defaultServeNumber="+rec.getDefaultServeNumber()+",cookTime="+rec.getCookTime()+",prepTime="+rec.getPrepTime()+" WHERE recipeID="+rec.getRecipeID();                                            ;
			int rowCount=stmt.executeUpdate(tempStmt);
			
			con.close();
			return true;
		}
		else {
			return false;
		}
		
	}
	/**
	 * query for a recipe specified by the recipe ID, return the queried recipe
	 * @param recID: the recipe to be queried
	 * 
	 * @return: the queried recipe. If the recipe doesn't exist, it will return a null recipe.
	 * @throws SQLException
	 */
	public static Recipe queryRecipe(int recID)throws SQLException{
		if(isRecipeExisted(recID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from recipe WHERE recipeID="+recID;
			ResultSet rset=stmt.executeQuery(tempStmt);
			Recipe rec=new Recipe();
			rset.next();
			rec.setRecipeID(rset.getInt("recipeID"));
			rec.setRecipeName(rset.getString("recipeName"));
			rec.setEditable(rset.getInt("editable"));
			rec.setIsFavorite(rset.getInt("isFavorite"));
			rec.setEndProductImage(rset.getString("endProductImage"));
			rec.setDefaultServeNumber(rset.getInt("defaultServeNumber"));
			rec.setCookTime(rset.getInt("cookTime"));
			rec.setPrepTime(rset.getInt("prepTime"));
			return rec;
		}
		else {
			return new Recipe();
		}
	}
	
	/**
	 * query for a list of recipes whose name contains the specified String
	 * can be used to implement fuzzy search
	 * @param recName: the specified name
	 * @return a list of recipes that have the specified name. If the recipe does not exist, it will return an emoty list.
	 * @throws SQLException
	 */
	public static LinkedList<Recipe> queryRecipebyName(String recName) throws SQLException{
		if(isRecipeExistedByName(recName)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from recipe WHERE recipeName like"+"'"+"%"+recName+"%"+"'";
			ResultSet rset=stmt.executeQuery(tempStmt);
			LinkedList<Recipe> recs=new LinkedList<Recipe>();
			while(rset.next()) {
				Recipe rec=new Recipe();
				rec.setRecipeID(rset.getInt("recipeID"));
				rec.setRecipeName(rset.getString("recipeName"));
				rec.setEditable(rset.getInt("editable"));
				rec.setIsFavorite(rset.getInt("isFavorite"));
				rec.setEndProductImage(rset.getString("endProductImage"));
				rec.setDefaultServeNumber(rset.getInt("defaultServeNumber"));
				rec.setCookTime(rset.getInt("cookTime"));
				rec.setPrepTime(rset.getInt("prepTime"));
				recs.add(rec);
		    }
			return recs;
		}	
		else {
			return new LinkedList<Recipe>();
		}
		
	}
	
	/**
	 * query for all the favorite recipes exist in the recipe table
	 * @return a list of all favorite recipes
	 * @throws SQLException
	 */
	public static LinkedList<Recipe> queryRecipebyFavorite() throws SQLException{
		if(isRecipeExistedByFavorite()) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from recipe WHERE isFavorite="+1;
			ResultSet rset=stmt.executeQuery(tempStmt);
			LinkedList<Recipe> recs=new LinkedList<Recipe>();
			while(rset.next()) {
				Recipe rec=new Recipe();
				rec.setRecipeID(rset.getInt("recipeID"));
				rec.setRecipeName(rset.getString("recipeName"));
				rec.setEditable(rset.getInt("editable"));
				rec.setIsFavorite(rset.getInt("isFavorite"));
				rec.setEndProductImage(rset.getString("endProductImage"));
				rec.setDefaultServeNumber(rset.getInt("defaultServeNumber"));
				rec.setCookTime(rset.getInt("cookTime"));
				rec.setPrepTime(rset.getInt("prepTime"));
				recs.add(rec);
		    }
			return recs;
		}	
		else {
			return new LinkedList<Recipe>();
			
		}
		
	}
	
	
	
	
	/**
	 * query for a recipe with exactly the same name with the specified recName
	 * @param recName: the precise name of the recipe
	 * @return a recipe with exactly the same name with the recName
	 * @throws SQLException
	 */
	public static Recipe queryRecipebyNamePrecise(String recName) throws SQLException{
		if(isRecipeExistedByNamePrecise(recName)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from recipe WHERE recipeName="+"'"+recName+"'";
			ResultSet rset=stmt.executeQuery(tempStmt);
		
			Recipe rec=new Recipe();
			rset.next();
			rec.setRecipeID(rset.getInt("recipeID"));
			rec.setRecipeName(rset.getString("recipeName"));
			rec.setEditable(rset.getInt("editable"));
			rec.setIsFavorite(rset.getInt("isFavorite"));
			rec.setEndProductImage(rset.getString("endProductImage"));
			rec.setDefaultServeNumber(rset.getInt("defaultServeNumber"));
			rec.setCookTime(rset.getInt("cookTime"));
			rec.setPrepTime(rset.getInt("prepTime"));
			return rec;
		}	
		else {
			return new Recipe();
		}
	}
	
	/**
	  * return the max recipe ID in the table. Used for generating a new recipe ID when add a new recipe.
	  * @return the max recipe ID in the table
	  * @throws SQLException
	  */
	 public static int queryMaxRecID() throws SQLException{
	  Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
	  Statement stmt = con.createStatement();
	  String tempStmt="SELECT MAX(recipeID) FROM recipe";
	  ResultSet rset=stmt.executeQuery(tempStmt);
	  rset.next();
	  return rset.getInt("MAX(recipeID)");
	 }
	
	
	/**
	 * check if a recipe is in accord with the data format defined in the recipe table
	 * 
	 * @param rec: the recipe to be checked
	 * @return: true if the recipe is valid, false if it is invalid.
	 */
   public static boolean isRecipeValid(Recipe rec) {
		boolean result=true;
		if(rec.getRecipeID()<=0) {
			result=false;
		}
		if(rec.getRecipeName().length()>1000) {
			result=false;
		}
		if(rec.getEditable()!=0 && rec.getEditable()!=1) {
			result=false;
		}
		if(rec.getIsFavorite()!=0 && rec.getIsFavorite()!=1) {
			result=false;
		}
		if(rec.getEndProductImage().length()>1000) {
			result=false;
		}
		if(rec.getDefaultServeNumber()<=0) {
			result=false;
		}
		return result;
	}
   
   
   
   
   
	/**
	 * check if the recipe is already existed in the data base.
	 * @param recipeID
	 * @return true if existed.
	 */
	public static boolean isRecipeExisted(int recipeID) {
		boolean result=true;
		if(recipeID<=0) {
			result=false;
		}
		else {
			Connection con;
			try {
				con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
				Statement stmt = con.createStatement();
				String tempStmt="SELECT * from recipe WHERE recipeID="+recipeID;
				ResultSet rset=stmt.executeQuery(tempStmt);
				
				if(!rset.next()) {
					result=false;
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	/**
	 * check if the recipe with the specified name(fuzzy) exists in recipe table
	 * @param recName
	 * @return true if exists
	 */
	public static boolean isRecipeExistedByName(String recName) {
		boolean result=true;
		Connection con;
		try {
			con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from recipe WHERE recipeName like"+"'"+"%"+recName+"%"+"'";
			ResultSet rset=stmt.executeQuery(tempStmt);
			
			if(!rset.next()) {
				result=false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * check if the recipe with the specified name(precise) exists in recipe table
	 * @param recName
	 * @return true if exists
	 */
	public static boolean isRecipeExistedByNamePrecise(String recName) {
		boolean result=true;
		Connection con;
		try {
			con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from recipe WHERE recipeName="+"'"+recName+"'";
			ResultSet rset=stmt.executeQuery(tempStmt);
			
			if(!rset.next()) {
				result=false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * check if the recipe with isFavorite equals 1 exists in recipe table
	 * @param recName
	 * @return true if exists
	 */
	public static boolean isRecipeExistedByFavorite() {
		boolean result=true;
		Connection con;
		try {
			con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from recipe WHERE isFavorite="+1;
			ResultSet rset=stmt.executeQuery(tempStmt);
			
			if(!rset.next()) {
				result=false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	
}
