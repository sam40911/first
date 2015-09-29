package edu.ntust.afda.modelImpl;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import android.os.Handler;
import edu.ntust.afda.domain.APKFingerPrintInfo;
import edu.ntust.afda.domain.APKSubFileFingerPrint;
import edu.ntust.afda.fingerprint.APKFingerPrintGenerator;
import edu.ntust.afda.fingerprint.APKFingerPrintHandler;
import edu.ntust.afda.fingerprint.ObjectTransformer;
import edu.ntust.afda.fingerprint.SHA1Generator;
import edu.ntust.afda.model.APKFingerPrintModel;
import edu.ntust.afda.parameter.APKConstantDescriptor;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class APKFingerPrintModelImpl  implements APKFingerPrintModel{

	@Override
	public APKFingerPrintInfo generateAPKFingerPrintFromAPKFile(String apkFile) {
	
		JarFile apkSourceFile;
		APKFingerPrintInfo apkFingerPrint = null;
		try {
			apkSourceFile = new JarFile(apkFile);
			apkFingerPrint = getAPKFingerPrint(apkSourceFile);
			return apkFingerPrint;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return apkFingerPrint;
	}
	
	private static APKFingerPrintInfo getAPKFingerPrint(JarFile apkSourceFile) throws IOException {
		TreeMap<String, JarEntry> entryMap = APKFingerPrintHandler.getAPKFileMap(apkSourceFile);
		List<APKSubFileFingerPrint> fingerPrintListForResXml = new ArrayList<APKSubFileFingerPrint>();
		List<APKSubFileFingerPrint> fingerPrintListForResImg = new ArrayList<APKSubFileFingerPrint>();
		List<APKSubFileFingerPrint> fingerPrintListForRes = new ArrayList<APKSubFileFingerPrint>();
		APKFingerPrintInfo apkFingerPrint = new APKFingerPrintInfo();

		for (JarEntry entry : entryMap.values()) {
			String name = entry.getName();
			if (!isMETAINFResources(entry)) {
				if (!isPrimaryResources(name)) {
					if (name.endsWith(APKConstantDescriptor.RES_CATEGORY_XML)) {	
						fingerPrintListForResXml.add(SHA1Generator.generateSha1ForFile(apkSourceFile.getInputStream(entry), name));
					} else if (isImageResources(name)) {
						fingerPrintListForResImg.add(SHA1Generator.generateSha1ForFile(apkSourceFile.getInputStream(entry), name));
					}
				} else if (isPrimaryResources(name)) {
					if (name.equals(APKConstantDescriptor.CLASSES_DEX)) {
						apkFingerPrint.setFingerPrintForClassesDex(APKFingerPrintGenerator.generateFingerPrintForPrimaryResource(apkSourceFile, entry, name));
					} else if (name.equals(APKConstantDescriptor.ANDROID_MANIFEST_XML)) {
						apkFingerPrint.setFingerPrintForAndroidManifestXml(APKFingerPrintGenerator.generateFingerPrintForPrimaryResource(apkSourceFile, entry, name));
					} else if (name.equals(APKConstantDescriptor.RESOURCES_ARSC)) {
						apkFingerPrint.setFingerPrintForResourcesArsc(APKFingerPrintGenerator.generateFingerPrintForPrimaryResource(apkSourceFile, entry, name));
					}
				}
			}
		}
		
		apkFingerPrint.setFingerPrintForResImg(APKFingerPrintGenerator.generateFingerPrintForSubCategoryOfSecondaryResource(fingerPrintListForResImg));
		apkFingerPrint.setFingerPrintForResXml(APKFingerPrintGenerator.generateFingerPrintForSubCategoryOfSecondaryResource(fingerPrintListForResXml));
	

		fingerPrintListForRes.add(ObjectTransformer.transformSubCategoryOfResToListAPKSubFileFingerPrint(APKConstantDescriptor.RES_CATEGORY_XML, apkFingerPrint.getFingerPrintForResXml()));
		fingerPrintListForRes.add(ObjectTransformer.transformSubCategoryOfResToListAPKSubFileFingerPrint(APKConstantDescriptor.RES_CATEGORY_PNG, apkFingerPrint.getFingerPrintForResImg()));
		
		apkFingerPrint.setFingerPrintForRes(APKFingerPrintGenerator.generateFingerPrintForSecondaryResource(fingerPrintListForRes));
		apkFingerPrint.setFingerPrintForEntireAPKFile(APKFingerPrintGenerator.generateFingerPrintForEntireAPKFile(apkFingerPrint));
		
//		System.out.println(APKFingerPrintHandler.deleteDirectoryForCutFile(new File(apkSourceFile.getName().substring(0, apkSourceFile.getName().indexOf(".apk")))));
		
		apkSourceFile.close();		
		return apkFingerPrint;
	}

	private static boolean isMETAINFResources(JarEntry entry) {
		boolean result = true;
		String entryName = entry.getName();
		if(!entry.isDirectory() && !entryName.equals(JarFile.MANIFEST_NAME) && !entryName.equals(APKConstantDescriptor.CERT_SF) && !entryName.equals(APKConstantDescriptor.CERT_RSA)) {
			result = false;
		}
		return result;
	}
	
	private static boolean isPrimaryResources(String name) {
		boolean result = false;
		if(name.equals(APKConstantDescriptor.RESOURCES_ARSC)
				||name.equals(APKConstantDescriptor.ANDROID_MANIFEST_XML)
				|| name.equals(APKConstantDescriptor.CLASSES_DEX)) {
			result = true;
		}
		return result;
	}
	
	private static boolean isImageResources(String name) {
		boolean result = false;
		if(name.endsWith(APKConstantDescriptor.RES_CATEGORY_PNG) || name.endsWith(APKConstantDescriptor.RES_CATEGORY_JPG)) {
			result = true;
		}
		return result;
	}
	

}
