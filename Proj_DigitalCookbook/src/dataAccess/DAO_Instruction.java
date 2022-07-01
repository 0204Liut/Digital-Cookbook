package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import model.*;







/**
 * check if the instruction already exists in the instruction table
 * @param insID
 * @return true if exists.
 */
public class DAO_Instruction {
	
	/**
	 * add a new instruction. If there are no instructions with the same ID exist in the instruction table, and the added instruction is valid, the data will be successfully added.
	 * @param ing: the instruction to be added
	 * @return true if successfully add
	 * @throws SQLException
	 */
	public static boolean addInstruction(Instruction ins) throws SQLException {
		if(isInstructionValid(ins) && !isInstructionExisted(ins.getInstructionID())) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="INSERT INTO instruction VALUES ("+ins.getInstructionID()+","+"'"+ins.getIns_description()+"'"+","+ins.getRecipeID()+","+"'"+ins.getIns_image()+"'"+")";
		
			stmt.executeUpdate(tempStmt);
			con.close();
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * delete an instruction specified by ID. The instruction must exist in the table, otherwise the method will do nothing.
	 * @param insID: the instruction to be deleted
	 * @return true if delete successfully
	 * @throws SQLException
	 */
	public static boolean deleteInstruction(int insID) throws SQLException {
		if(isInstructionExisted(insID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="DELETE FROM instruction WHERE instructionID="+insID;
			stmt.executeUpdate(tempStmt);
			con.close();
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * update an instruction that has the same ID with the parameter ins. Set ins_description, recipeID and ins_image of the instruction.
	 * the update will be successful if:
	 * 1.An instruction that has the same ID with the parameter ins exists in the table.
	 * 2.The parameter ins is valid, i.e., it has the right data format and correctly binds a recipe.
	 * @param ins:specifies which instruction is to be updated and the updated values.
	 * @return true if update successfully
	 * @throws SQLException
	 */
	public static boolean updateInstruction(Instruction ins) throws SQLException {
		if(isInstructionValid(ins) && isInstructionExisted(ins.getInstructionID())) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="UPDATE instruction SET ins_description="+"'"+ins.getIns_description()+"'"+",recipeID="+ins.getRecipeID()+",ins_image="+"'"+ins.getIns_image()+"'"+" WHERE instructionID="+ins.getInstructionID();                                            ;
			int rowCount=stmt.executeUpdate(tempStmt);
			
			con.close();
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * query for an instruction specified by the instruction ID, return the queried instruction
	 * @param insID: the instruction to be queried
	 * @return: the queried instruction. If the instruction doesn't exist, it will return a null instruction.
	 * @throws SQLException
	 */
	public static Instruction queryInstruction(int insID) throws SQLException {
		if(isInstructionExisted(insID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from instruction WHERE instructionID="+insID;
			ResultSet rset=stmt.executeQuery(tempStmt);
			Instruction ins=new Instruction();
			rset.next();
			ins.setInstructionID(rset.getInt("instructionID"));
			ins.setIns_description(rset.getString("ins_description"));
			ins.setRecipeID(rset.getInt("recipeID"));
			ins.setIns_image(rset.getString("ins_image"));
			
	
			return ins;
		}
		else {
			return new Instruction();
		}
	}
	
	/**
	 * query for a list of instructions that have the specified recipeID. Can be used for the assignment of the "instructions" field in recipe 
	 * @param recID: the specified recipe ID
	 * @return a list of instructions that have the specified recipeID. If the instructions do not exist, it will return an empty list.
	 * @throws SQLException
	 */
	public static LinkedList<Instruction> queryInstructionByRecID(int recID) throws SQLException{
		if(isInstructionExistedByRecID(recID)) {
			Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
			Statement stmt = con.createStatement();
			String tempStmt="SELECT * from instruction WHERE recipeID="+recID;
			ResultSet rset=stmt.executeQuery(tempStmt);
			LinkedList<Instruction> inss=new LinkedList<Instruction>();
			while(rset.next()) {
				Instruction ins=new Instruction();
				ins.setInstructionID(rset.getInt("instructionID"));
				ins.setIns_description(rset.getString("ins_description"));
				ins.setRecipeID(rset.getInt("recipeID"));
				ins.setIns_image(rset.getString("ins_image"));
				inss.add(ins);
			}
			
			return inss;
		}
		else {
			return new LinkedList<Instruction>();
		}
	}
	
	/**
	  * return the max instruction ID in the table. Used for generating a new recipe ID when add a new recipe.
	  * @return the max instruction ID in the table
	  * @throws SQLException
	  */
	 public static int queryMaxInsID() throws SQLException{
	  Connection con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
	  Statement stmt = con.createStatement();
	  String tempStmt="SELECT MAX(instructionID) FROM instruction";
	  ResultSet rset=stmt.executeQuery(tempStmt);
	  rset.next();
	  return rset.getInt("MAX(instructionID)");
	 }
	
	
	/**
	 * check if an instruction is in accord with the format defined in the instruction table, and whether it binds an existed recipe.
	 * @param ing
	 * @return true if the instruction is valid. if any of the fields' format is not right, or the recipe ID of this instruction does not exist in the recipe table, it will return false.
	 */
	public static boolean isInstructionValid(Instruction ins) {
		boolean result=true;
		if(ins.getInstructionID()<=0) {
			result=false;
		}
		if(ins.getIns_description().length()>3000) {
			result=false;
		}
		if(ins.getRecipeID()<=0 || !DAO_Recipe.isRecipeExisted(ins.getRecipeID())) {
			result=false;
		}
		if(ins.getIns_image().length()>1000) {
			result=false;
		}
		return result;
	}
	
	
	
	
	public static boolean isInstructionExisted(int insID) {
		boolean result=true;
		if(insID<=0) {
			result=false;
		}
		else {
			Connection con;
			try {
				con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
				Statement stmt = con.createStatement();
				String tempStmt="SELECT * from instruction WHERE instructionID="+insID;
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
	 * check if there are instructions that belong to the specified recipe exist in the instruction table.
	 * @param recID: specify the recipe to be check
	 * @return true if exists
	 */
	public static boolean isInstructionExistedByRecID(int recID) {
		boolean result=true;
		if(recID<=0) {
			result=false;
		}
		else {
			Connection con;
			try {
				con = DriverManager.getConnection(Driver.url, Driver.user,Driver.password);
				Statement stmt = con.createStatement();
				String tempStmt="SELECT * from instruction WHERE recipeID="+recID;
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
