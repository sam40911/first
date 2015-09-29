package edu.ntust.afda.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class APKFileHandler {

	public static String getAPKPackageName(Context context, String apkPath) {
		PackageInfo packInfo = context.getPackageManager()
				.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);

		if (packInfo != null) {
			return packInfo.packageName;
		}
		return null;
	}

	public static String getAPKVersionName(Context context, String apkPath) {

		PackageInfo packInfo = context.getPackageManager()
				.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if (packInfo != null) {
			return packInfo.versionName;
		}
		return null;
	}

	public static int getAPKVersionCode(Context context, String apkPath) {
		PackageInfo packInfo = context.getPackageManager()
				.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if (packInfo != null) {
			return packInfo.versionCode;
		}
		return 0;
	}

	public static Drawable getApkIcon(Context context, String apkPath) {
		PackageInfo packInfo = context.getPackageManager()
				.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if (packInfo != null) {
			ApplicationInfo appInfo = packInfo.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try {
				return appInfo.loadIcon(context.getPackageManager());
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
