package edu.ntust.afda.modelImpl;

import java.io.IOException;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;



import edu.ntust.afda.domain.APKManifest;
import edu.ntust.afda.model.APKInfoExtractor;
import edu.ntust.afda.parameter.APKConstantDescriptor;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class APKInfoExtractorImpl implements APKInfoExtractor{
	
	
	public APKManifest extractManifestInfo(String apkFilePath,String apkFileName,int versionCode) {
		
		APKManifest apkManifest = null;
		try {
			JarFile apkSourceFile = new JarFile(apkFilePath);

			Manifest manifest = apkSourceFile.getManifest();
			Map<String, Attributes> entryMap = manifest.getEntries();
			apkManifest = new APKManifest();
			apkManifest.setApkFileName(apkFileName);
			apkManifest.setShaForAndroidManifestXml(entryMap.get(APKConstantDescriptor.ANDROID_MANIFEST_XML).getValue(APKConstantDescriptor.TARGET_KEY));
			apkManifest.setShaForResourcesArsc(entryMap.get(APKConstantDescriptor.RESOURCES_ARSC).getValue(APKConstantDescriptor.TARGET_KEY));
			apkManifest.setShaForClassesDex(entryMap.get(APKConstantDescriptor.CLASSES_DEX).getValue(APKConstantDescriptor.TARGET_KEY));
			apkManifest.setMnftEntrySize(entryMap.size());
			apkManifest.setVersionCode(String.valueOf(versionCode));
			apkSourceFile.close();
		} catch (IOException e) {
			
		}
		return apkManifest;
	}

}
