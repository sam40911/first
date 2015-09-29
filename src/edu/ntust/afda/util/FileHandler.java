package edu.ntust.afda.util;

import java.io.File;
import java.io.FileFilter;
import java.sql.Date;
import java.text.DateFormat;

import android.graphics.Bitmap;
import android.graphics.Matrix;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class FileHandler {

	public static long getFilelistSize(File f, FileFilter fileFilter) {
		long size = 0;
		File flist[] = f.listFiles(fileFilter);
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()&&flist[i].isFile()) {
			}
			if(!flist[i].isHidden())
			{	
				size++;
			}
		}
		return size;
	}

	public static String getFileLastDate(File f) {

		Date lastModDate = new Date(f.lastModified());
		DateFormat formater = DateFormat.getDateTimeInstance();
		return formater.format(lastModDate);
	}
	
	
	
	
	
}
