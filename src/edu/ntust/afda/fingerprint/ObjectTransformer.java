package edu.ntust.afda.fingerprint;

import edu.ntust.afda.domain.APKSubFileFingerPrint;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class ObjectTransformer {
	
	public static APKSubFileFingerPrint transformSubCategoryOfResToListAPKSubFileFingerPrint(String ResCategory, String ResSubCategoryFingerPrint) {
		APKSubFileFingerPrint FingerResSubCategory = new APKSubFileFingerPrint();
		FingerResSubCategory.setFileName(ResCategory);
		FingerResSubCategory.setSha1Hex(ResSubCategoryFingerPrint);
		return FingerResSubCategory;
	}

}
