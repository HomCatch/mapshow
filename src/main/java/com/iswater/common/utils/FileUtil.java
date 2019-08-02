package com.iswater.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	private static String descFileName;

	/**
	 * 创建文件
	 * 
	 * @param descFileDir
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(String descFileDir, String fileName) {
		descFileName = descFileDir + File.separator + fileName;
		File file = new File(descFileName);
		if (file.exists()) {
			file.delete();
		}
		if (descFileName.endsWith(File.separator)) {
			System.out.println(fileName + " is Dir，Can't Create Dir!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				System.out.println("Create Dir Failed!");
				return false;
			}
		}
		// 创建文件
		try {
			if (file.createNewFile()) {
				System.out.println(fileName + " CreateFile Success!");
				return true;
			} else {
				System.out.println(fileName + " CreateFile Failed!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(fileName + " CreateFile Failed!");
			return false;
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 *            要写入的文件
	 */
	public static void writeToFile(String content, boolean append) {
		try {
			FileUtils.write(new File(descFileName), content, "utf-8", append);
		} catch (IOException e) {
			System.out.println("File " + descFileName + " Write Failed! " + e.getMessage());
		}
	}

	/**
	 * 写文件
	 * 
	 * @param content
	 * @param append
	 */
	public static void writeByteArrayToFile(byte[] content, boolean append) {
		try {
			FileUtils.writeByteArrayToFile(new File(descFileName), content, append);
		} catch (IOException e) {
			System.out.println("File " + descFileName + " Write Failed! " + e.getMessage());
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 *            要写入的文件
	 */
	public static void writeToFile(String content, String encoding, boolean append) {
		try {
			FileUtils.write(new File(descFileName), content, encoding, append);
		} catch (IOException e) {
			System.out.println("File " + descFileName + " Write Failed! " + e.getMessage());
		}
	}

	/**
	 * 读取文件
	 * 
	 * @return
	 */
	public static List<String> readFile() {
		List<String> contents = new ArrayList<String>();
		try {
			contents = FileUtils.readLines(new File(descFileName), "utf-8");
		} catch (IOException e) {
			System.out.println("File " + descFileName + " Read Failed! " + e.getMessage());
		}
		return contents;
	}

	/**
	 * 读取文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToByteArray(String fileName) throws IOException {
		return FileUtils.readFileToByteArray(new File(fileName));
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filename
	 * @return
	 */
	public static List<String> readFile(String filename) {
		List<String> contents = new ArrayList<String>();
		try {
			contents = FileUtils.readLines(new File(filename), "utf-8");
		} catch (IOException e) {
			System.out.println("File " + descFileName + " Read Failed! " + e.getMessage());
		}
		return contents;
	}

	/**
	 * 获取文件的最后修改时间
	 * @param fileName
	 * @return
	 */
	public static String getModifiedTime(String fileName) {
		File f = new File(fileName);
		String date = "";
		if(f.exists()){
			Calendar cal = Calendar.getInstance();
			long time = f.lastModified();
			cal.setTimeInMillis(time);
			date = DateUtil.format(cal.getTime(), "yyyyMMddHHmmss");
		}else{
			date = "L"+DateUtil.formatNow("yyyyMMddHHmmss");
		}
		return date;
	}
}
