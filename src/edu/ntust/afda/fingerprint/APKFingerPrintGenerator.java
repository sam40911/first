package edu.ntust.afda.fingerprint;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import edu.ntust.afda.domain.APKFingerPrintInfo;
import edu.ntust.afda.domain.APKSubFileFingerPrint;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class APKFingerPrintGenerator {
	
	public static String generateFingerPrintForPrimaryResource(JarFile apkSourceFile, JarEntry entry, String name) throws IOException {
		return XORChainCalculator.calculateXORSha1Value(generateFingerPrintListForSubFileOfPrimaryResource(apkSourceFile, entry, name));
	}

	public static String generateFingerPrintForSubCategoryOfSecondaryResource(List<APKSubFileFingerPrint> fingerPrintListForSubCategoryOfSecondaryResource) {
		return XORChainCalculator.calculateXORSha1Value(fingerPrintListForSubCategoryOfSecondaryResource);
	}

	public static String generateFingerPrintForSecondaryResource(List<APKSubFileFingerPrint> fingerPrintListForSecondaryResource) {
		return XORChainCalculator.calculateXORSha1Value(fingerPrintListForSecondaryResource);
	}

	public static String generateFingerPrintForEntireAPKFile(APKFingerPrintInfo apkFingerPrint) {
		return SHA1Generator.generateSha1ForString(combineAllFingerprints(apkFingerPrint));
	}
	
	private static final int NUMBER_OF_FILE_SEPERATION = 4;
	public static List<APKSubFileFingerPrint> generateFingerPrintListForSubFileOfPrimaryResource(
			JarFile apkSourceFile, JarEntry jarEntry, String entryName) throws IOException {
		List<APKSubFileFingerPrint> fingerPrintListForPrimaryResource = new ArrayList<APKSubFileFingerPrint>();

		fingerPrintListForPrimaryResource = new ArrayList<APKSubFileFingerPrint>();
		int count = NUMBER_OF_FILE_SEPERATION;
		String fileFolder = apkSourceFile.getName().substring(0, apkSourceFile.getName().indexOf(".apk"));
		String fileName = entryName.substring(0, entryName.indexOf("."));
		String fileSuffix = entryName.substring(entryName.indexOf("."));
		APKFingerPrintHandler.cutFile(apkSourceFile.getInputStream(jarEntry),jarEntry.getSize(), fileFolder, fileName, fileSuffix, count);
		
		
		
		for (int i = 0; i < count; i++) {
			
			InputStream is = new BufferedInputStream(new FileInputStream(fileFolder + "/" + fileName + (i + 1) + fileSuffix));
			fingerPrintListForPrimaryResource.add(SHA1Generator.generateSha1ForFile(is,  fileName + (i + 1) + fileSuffix));
		}
		return fingerPrintListForPrimaryResource;
	}
	
	private static String combineAllFingerprints(APKFingerPrintInfo apkFingerPrint) {
		return apkFingerPrint.getFingerPrintForClassesDex().
				concat(apkFingerPrint.getFingerPrintForResourcesArsc()).
				concat(apkFingerPrint.getFingerPrintForAndroidManifestXml()).
				concat(apkFingerPrint.getFingerPrintForRes());
	}

}
