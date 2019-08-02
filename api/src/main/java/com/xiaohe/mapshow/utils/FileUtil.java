package com.xiaohe.mapshow.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文件读写类
 * @author terryhuang
 * @date 2017年2月21日 下午5:20:19
 */
public class FileUtil {

	//创建文件
	public static boolean createFile(String dir, String fileName) {
		File fileDir = new File(dir);
		//如果文件夹不存在则创建
		if (!fileDir.exists() || fileDir.isFile())
			fileDir.mkdirs();

		boolean result = false;

		File file = new File(dir, fileName);
		try {
			if (file.isFile() && file.exists())
				file.delete();
			file.createNewFile(); // 创建文件

			result = true;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	public static String readFileFirstLine(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			if ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
			return tempString;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e1) {
				}
		}

		return null;
	}

	/**
	 * 另存文件
	 * @param file
	 * @param targetFile
	 * @return
	 */
	public static boolean saveFile(MultipartFile file, String targetFile) {
		//		 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				//              转存文件
				file.transferTo(new File(targetFile));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * B方法追加文件：使用FileWriter
	 * @param fileName
	 * @param content
	 */
	public static void appendLine(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content + "\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e1) {
				}
		}
	}

	/**
	 * 读取文件并转换为Map
	 * @param file
	 * @return
	 */
	public static Map<String, String> readLine(File file) {
		Map<String, String> map = new LinkedHashMap<>();
		try {
			List<String> readAllLines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			if (null != readAllLines)
				for (String line : readAllLines)
					if (!StringUtils.isBlank(line)) {
						String k = "";
						String v = "";
						if (line.indexOf("=") != -1) {
							k = line.substring(0, line.indexOf("="));
							v = line.substring(line.indexOf("=") + 1);
						} else
							k = line.trim();
						map.put(k, v);
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 按行读取文件
	 * @param file
	 * @return
	 */
	public static List<String> readLines(File file) {
		List<String> list = new LinkedList<>();
		try {
			list = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 写文件
	 * @param file
	 * @param list
	 * @param is true：追加，false：覆盖
	 */
	public static void writeLine(File file, List<String> list, boolean is) {
		try {
			if (is)
				Files.write(file.toPath(), list, Charset.forName("UTF-8"), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			else
				Files.write(file.toPath(), list, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除目录下的文件
	 * @param file
	 */
	public static void delDirectoryToFile(File file) {
		delDirectoryToFileByFileName(file, null);
	}

	/**
	 * 根据文件名删除目录下的文件，文件名为 null 代表删除全部
	 * @param file 文件名
	 * @param names
	 */
	public static void delDirectoryToFileByFileName(File file, String names) {
		File[] listFiles = file.listFiles();
		for (File reF : listFiles) {
			if (reF.isDirectory())
				continue;
			if (null != names) {
				if (reF.getName().indexOf(names) != -1)
					reF.delete();
			} else
				reF.delete();
		}
	}
}
