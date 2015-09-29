package edu.ntust.afda.model;

import edu.ntust.afda.domain.APKManifest;

/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public interface APKInfoExtractor {
	
	public  APKManifest extractManifestInfo(String apkFilePath,String apkFileName,int versionCode);

}
