package cn.edu.fudan.se.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * @author jiahuanzheng
 * */
@SuppressWarnings("unused")
public class GoalModelOperations {

	private static String defaultUser = "default";
	private static boolean ignoreUsername = true;
	private static String currentFolder = System.getProperties().getProperty("user.home") + "/Desktop";
	private static String rootDirectory = currentFolder + "/GoalModel";
	private static String defaultUserDirectory = rootDirectory + "/"
			+ defaultUser;

	static {
		// this move guarantees related cruical directories will be built before
		// the main function executes.
		createFolder(rootDirectory);
		createFolder(defaultUserDirectory);

	}

	private static void createFolder(String folderPath) {
		//System.out.println(folderPath);
		File folder = new File(folderPath);
		if (!folder.exists())
			folder.mkdirs();
 		//System.out.println(folderPath + " :  folder.exists()  :  " + folder.exists());
	}

	private static String synthesizeFilePath(String... para) {

		int length = para.length;
		if (length < 1)
			return "";

		StringBuffer sb = new StringBuffer(rootDirectory + "/" + para[0]);
		if (ignoreUsername)
			sb = new StringBuffer(rootDirectory + "/" + defaultUser);

		for (int i = 1; i < length; i++) {
			sb.append("/" + para[i]);
		}
System.out.println("path："+sb);
		return sb.toString();
	}

	private static boolean isTemplateFolderExists(String username,
			String goalModelName, String templateName) {

		return new File(synthesizeFilePath(username, goalModelName,
				templateName)).exists();
	}

	private static boolean isGoalModelFolderExists(String username,
			String goalModelName) {

		return new File(synthesizeFilePath(username, goalModelName)).exists();
	}

	public static void main(String[] args) throws IOException {
		// ByteArrayInputStream byteArrayInputStream = new
		// ByteArrayInputStream(getTemplateContent("", "Book Acquisition",
		// "Get Book Info"));
		//System.out.println(getTemplateContent("", "Book Acquisition",
		//		"Get Book Info"));
		//writeTemplateInstance("", "Book Acquisition", "Get Book Info",
				//getTemplateContent("", "Book Acquisition", "Get Book Info"));
		//System.out.println("abc hello world !");
	}

	public static void test(){
	//	System.out.println("abc hello world !");
	}

	public static List<String> getGoalModelList() {
		// TODO: when ignoreUsername is not true
		ArrayList<String> goalModelList = new ArrayList<String>();
		if (ignoreUsername)
			for (File file : new File(defaultUserDirectory).listFiles()) {
				goalModelList.add(file.getName());
			}

		return goalModelList;
	}

	public static List<String> getTemplatesList(String username,
			String goalModelName) {
		ArrayList<String> templatesList = new ArrayList<String>();
		if (isGoalModelFolderExists(username, goalModelName)) {
			for (File file : new File(synthesizeFilePath(username,goalModelName)).listFiles()){
				if (file.isDirectory()){
					templatesList.add(file.getName());
				}
			}
			return templatesList;
		}

		return templatesList;
	}

	public static String getTemplateContent(String username,
			String goalModelName, String templateName) {
		File file;
		FileInputStream fis = null;
		ArrayList<String> templateContent = new ArrayList<String>();
		if (isTemplateFolderExists(username, goalModelName, templateName)) {
			file = new File(synthesizeFilePath(username, goalModelName,
					templateName, templateName + ".xml"));
			try {
				fis = new FileInputStream(file);
				byte[] bytes = new byte[(int) file.length()];
				fis.read(bytes);
				return new String(bytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return "";
	}

	public static void writeTemplateInstance(String username,
			String goalModelName, String templateName, String templateInstance) {
		// TODO: Write the template instance into corresponding file directory
		// identified by username, goalModelName.
		// TODO: The name of the file which contains the template instance
		// content consists of templateName and timestamp.
		File file;
		file = new File(synthesizeFilePath(username, goalModelName,
				templateName, templateName + "." + new Date().getTime()
						+ ".xml"));
		FileOutputStream fos = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(templateInstance.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
