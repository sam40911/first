package edu.ntust.afda.fingerprint;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import android.os.Environment;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class APKFingerPrintHandler {
	
	public static TreeMap<String, JarEntry> getAPKFileMap(JarFile apkSourceFile) {
		TreeMap<String, JarEntry> apkTreeMap = new TreeMap<String, JarEntry>();
		for (Enumeration<JarEntry> e = apkSourceFile.entries(); e
				.hasMoreElements();) {
			JarEntry entry = e.nextElement();
			apkTreeMap.put(entry.getName(), entry);
		}
		return apkTreeMap;
	}

	public static void cutFile(InputStream inputData, long size, String outputFolder,
			String outputfile, String fileSuffix, int fileCutNumber) {
		makeDir(outputFolder);
		
		
		try {
			byte[] b = new byte[1];
			int splitSize;
			
			if ((int)size % fileCutNumber == 0) {
				splitSize = (int) (size / fileCutNumber);
			} else {
				splitSize = (int)size / fileCutNumber;
				splitSize++;
			}
			for (int i = 0; i < fileCutNumber; i++) {
				int num = 0;
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(outputFolder + "/" + outputfile
								+ (i + 1) + fileSuffix));
				while (inputData.read(b) != -1) {
					bos.write(b);
					num++;
					if (num == splitSize) {
						bos.flush();
						bos.close();
						break;
					}
				}
				if (num < splitSize) {
					bos.flush();
					bos.close();
				}
			}
			inputData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean makeDir(String dir) {

		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory()+ dir.replace("/sdcard", ""));
			if (!file.exists()) {
				return file.mkdir();
			}
		}
		return false;
	}
	
	public static boolean deleteDirectoryForCutFile(File fileFolder) {
		File[] files = fileFolder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					deleteDirectoryForCutFile(f);
				} else if (f.isFile() && f.exists()) {
					f.delete();
				}
			}
		}
		return fileFolder.delete();
	}
	

}
