package edu.ntust.afda.domain;

import java.io.Serializable;

/**
 * 
 * @author Ssu-Wei,Tang
 *
 */

public class APKSubFileFingerPrint implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sha1Base64;
	private String sha1Hex;
	private String fileName;

	public String getSha1Base64() {
		return sha1Base64;
	}

	public void setSha1Base64(String sha1Base64) {
		this.sha1Base64 = sha1Base64;
	}

	public String getSha1Hex() {
		return sha1Hex;
	}

	public void setSha1Hex(String sha1Hex) {
		this.sha1Hex = sha1Hex;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
