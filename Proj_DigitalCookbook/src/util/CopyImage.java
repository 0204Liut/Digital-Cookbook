package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
/**
 * this class can copy a file to the destination directory.
 * The user should first set the destDir.
 * @author kuangzheng
 *
 */
public class CopyImage {

	private static String destDir;
	private static int imageCounter;
	private static int trueImageCounter;
	public static void setDest(String destDir) {
		CopyImage.destDir=destDir;
	}
		
		
		
	/**
	 * copy the source file to the directory specified by the attribute destDir. 
	 * if the file with the same name as srcFile already exists in the directory, the method will not copy the srcfile.
	 * @param srcFile: the source file to be copied.
	 * @return false if the file with the same name as srcFile already exists in the directory. Return true otherwise.
	 * @throws IOException
	 */
	public static boolean copyFile(File srcFile) throws IOException {
		   //该方法为复制文件，srcFile是原文件所在地址，destFile是目标文件所在地址
		     
		     //File[] fileArray1 = srcFile.listFiles();
		     //File[] fileArray2 = destFile.listFiles();
		    
		     File test1=new File("imageCounter.txt");
		     BufferedReader rd=new BufferedReader(new FileReader(test1));
			 String s1=rd.readLine();
			 imageCounter=s1.hashCode();
			 trueImageCounter=imageCounter;
			 BufferedWriter wt=new BufferedWriter(new FileWriter(test1));
			 
			 
		     File directory=new File(destDir); //the destination directory
		     String[] strs=directory.list();
		     LinkedList<String> lst=new LinkedList<String>(Arrays.asList(strs));
		     if(lst.contains(imageCounter+srcFile.getName().substring(srcFile.getName().length()-4))) {
		    	 return false;
		     }
		     else {
		    	 String destPath=CopyImage.destDir+File.separator+imageCounter+srcFile.getName().substring(srcFile.getName().length()-4);
			     File destFile=new File(destPath);
			     
			     imageCounter/=7;
				 wt.write(imageCounter+1+"");
				 wt.close();
				 
			     BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
			     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
			     byte[] bys = new byte[1024];
			     int len = 0;
			     while ((len = bis.read(bys)) != -1) 
			        {
			        bos.write(bys, 0, len);
			        }
			     bos.close();
			     bis.close();
			     return true;
		     }
		     
		    } 
	/**
	 * the method to get the final path of the file that will be written into database.
	 * @param srcFile: the copied file.
	 * @return the final path of the file
	 */
	public static String getFilePathInDir(File srcFile) {
		return (CopyImage.destDir+"/"+trueImageCounter+srcFile.getName().substring(srcFile.getName().length()-4)).replaceAll("\\\\", "/");
		
	}
	
}
