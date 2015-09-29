package edu.ntust.afda.fingerprint;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import java.util.Formatter;

import android.util.Base64;
import edu.ntust.afda.domain.APKSubFileFingerPrint;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class SHA1Generator {
	
	
	private static final String ALGORITHM = "SHA1";
	private static MessageDigest md;
	private static byte[] digets;
	
	public static APKSubFileFingerPrint generateSha1ForFile(InputStream inputData, String fileName) {
		APKSubFileFingerPrint apksubfilesha1 = new APKSubFileFingerPrint();
		try {
			md = MessageDigest.getInstance(ALGORITHM);
			byte[] buffer = new byte[4096];
			int num;
			while ((num = inputData.read(buffer)) > 0) {
				md.update(buffer, 0, num);
			}
			digets = md.digest();
			apksubfilesha1.setFileName(fileName);
//			apksubfilesha1.setSha1Base64(Base64.encodeBase64String(digets));
			apksubfilesha1.setSha1Base64(Base64.encodeToString(digets, Base64.DEFAULT));
			apksubfilesha1.setSha1Hex(convertDigestsToHex(digets));
			inputData.close();
			

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return apksubfilesha1;
	}

	public static String generateSha1ForString(String text) {
		String HEXdigets = null;
		try {
			md = MessageDigest.getInstance(ALGORITHM);
			md.reset();
			md.update(text.getBytes("UTF-8"));
			digets = md.digest();
			HEXdigets = convertDigestsToHex(digets);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return HEXdigets;
	}

	private static String convertDigestsToHex(byte[] digets) {
		String HEXdigets = "";
		try (Formatter formatter = new Formatter()) {
			for (final byte b : digets) {
				formatter.format("%02x", b);
			}
			HEXdigets = formatter.toString();
		}
		return HEXdigets;
	}


}
