package edu.ntust.afda.domain;

import java.io.Serializable;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class APKFingerPrintInfo  implements Serializable{
private static final long serialVersionUID = 1L;
	private String fingerPrintForResXml;
	private String fingerPrintForResImg;
	private String fingerPrintForClassesDex;
	private String fingerPrintForAndroidManifestXml;
	private String fingerPrintForResourcesArsc;
	private String fingerPrintForRes;
	private String fingerPrintForEntireAPKFile;
	public String getFingerPrintForResXml() {
		return fingerPrintForResXml;
	}
	public void setFingerPrintForResXml(String fingerPrintForResXml) {
		this.fingerPrintForResXml = fingerPrintForResXml;
	}
	public String getFingerPrintForResImg() {
		return fingerPrintForResImg;
	}
	public void setFingerPrintForResImg(String fingerPrintForResImg) {
		this.fingerPrintForResImg = fingerPrintForResImg;
	}
	public String getFingerPrintForClassesDex() {
		return fingerPrintForClassesDex;
	}
	public void setFingerPrintForClassesDex(String fingerPrintForClassesDex) {
		this.fingerPrintForClassesDex = fingerPrintForClassesDex;
	}
	public String getFingerPrintForAndroidManifestXml() {
		return fingerPrintForAndroidManifestXml;
	}
	public void setFingerPrintForAndroidManifestXml(
			String fingerPrintForAndroidManifestXml) {
		this.fingerPrintForAndroidManifestXml = fingerPrintForAndroidManifestXml;
	}
	public String getFingerPrintForResourcesArsc() {
		return fingerPrintForResourcesArsc;
	}
	public void setFingerPrintForResourcesArsc(String fingerPrintForResourcesArsc) {
		this.fingerPrintForResourcesArsc = fingerPrintForResourcesArsc;
	}
	public String getFingerPrintForRes() {
		return fingerPrintForRes;
	}
	public void setFingerPrintForRes(String fingerPrintForRes) {
		this.fingerPrintForRes = fingerPrintForRes;
	}
	public String getFingerPrintForEntireAPKFile() {
		return fingerPrintForEntireAPKFile;
	}
	public void setFingerPrintForEntireAPKFile(String fingerPrintForEntireAPKFile) {
		this.fingerPrintForEntireAPKFile = fingerPrintForEntireAPKFile;
	}
	
	@Override
	public String toString() {
		return "APKFingerPrintInfo [fingerPrintForResXml="
				+ fingerPrintForResXml + ", fingerPrintForResImg="
				+ fingerPrintForResImg + ", fingerPrintForClassesDex="
				+ fingerPrintForClassesDex
				+ ", fingerPrintForAndroidManifestXml="
				+ fingerPrintForAndroidManifestXml
				+ ", fingerPrintForResourcesArsc="
				+ fingerPrintForResourcesArsc + ", fingerPrintForRes="
				+ fingerPrintForRes + ", fingerPrintForEntireAPKFile="
				+ fingerPrintForEntireAPKFile + "]";
	}
	
}
