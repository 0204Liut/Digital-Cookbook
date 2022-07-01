package model;


/**
 * The instruction class. It stores the information of an instruction.
 * @author kuangzheng
 *
 */
public class Instruction {
	private int recipeID;
	private int instructionID;
	private String ins_description;
	private String ins_image;
	
	public Instruction() {
		this.recipeID=0;
		this.instructionID=0;
		this.ins_description="";
		this.ins_image="";
	}
    public Instruction(int recipeID, int instructionID, String ins_description, String ins_image) {
		super();
		this.recipeID = recipeID;
		this.instructionID = instructionID;
		this.ins_description = ins_description;
		this.ins_image = ins_image;
	}
	public int getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	public int getInstructionID() {
		return instructionID;
	}
	public void setInstructionID(int instructionID) {
		this.instructionID = instructionID;
	}
	public String getIns_description() {
		return ins_description;
	}
	public void setIns_description(String ins_description) {
		this.ins_description = ins_description;
	}
	public String getIns_image() {
		return ins_image;
	}
	public void setIns_image(String ins_image) {
		this.ins_image = ins_image;
	}
	
	public String toString() {
	     return this.instructionID+" "+this.recipeID+this.ins_description+this.ins_image;
	    }
	
}
