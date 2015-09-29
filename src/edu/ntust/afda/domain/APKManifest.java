package edu.ntust.afda.domain;

import java.io.Serializable;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class APKManifest  implements Serializable{
	
	private Long id;
	private String apkFileName;
	private String shaForAndroidManifestXml;
	private String shaForResourcesArsc;
	private String shaForClassesDex;
	private int mnftEntrySize;
	private String versionCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApkFileName() {
		return apkFileName;
	}
	public void setApkFileName(String apkFileName) {
		this.apkFileName = apkFileName;
	}
	public String getShaForAndroidManifestXml() {
		return shaForAndroidManifestXml;
	}
	public void setShaForAndroidManifestXml(String shaForAndroidManifestXml) {
		this.shaForAndroidManifestXml = shaForAndroidManifestXml;
	}
	public String getShaForResourcesArsc() {
		return shaForResourcesArsc;
	}
	public void setShaForResourcesArsc(String shaForResourcesArsc) {
		this.shaForResourcesArsc = shaForResourcesArsc;
	}
	public String getShaForClassesDex() {
		return shaForClassesDex;
	}
	public void setShaForClassesDex(String shaForClassesDex) {
		this.shaForClassesDex = shaForClassesDex;
	}
	public int getMnftEntrySize() {
		return mnftEntrySize;
	}
	public void setMnftEntrySize(int mnftEntrySize) {
		this.mnftEntrySize = mnftEntrySize;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	@Override
	public String toString() {
		return "APKManifest [id=" + id + ", apkFileName=" + apkFileName
				+ ", shaForAndroidManifestXml=" + shaForAndroidManifestXml
				+ ", shaForResourcesArsc=" + shaForResourcesArsc
				+ ", shaForClassesDex=" + shaForClassesDex + ", mnftEntrySize="
				+ mnftEntrySize + ", versionCode=" + versionCode + "]";
	}

}
