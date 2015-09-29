package edu.ntust.afda.model;

import edu.ntust.afda.domain.APKFingerPrintInfo;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public interface APKFingerPrintModel {
	public APKFingerPrintInfo generateAPKFingerPrintFromAPKFile(String apkFile);

}
