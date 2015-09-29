package edu.ntust.afda.fingerprint;

import java.math.BigInteger;
import java.util.List;

import edu.ntust.afda.domain.APKSubFileFingerPrint;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class XORChainCalculator {
	
	public static String calculateXORSha1Value(List<APKSubFileFingerPrint> listForSubFileFingerPrint) {
		BigInteger calcularorXorSha1Value = null ;
		BigInteger xorSha1InitValue = new BigInteger(listForSubFileFingerPrint.get(0).getSha1Hex(), 16) ;
		for (int j=1;j<listForSubFileFingerPrint.size();j++)
		{
			calcularorXorSha1Value = new BigInteger(listForSubFileFingerPrint.get(j).getSha1Hex(), 16);
			calcularorXorSha1Value = calcularorXorSha1Value.xor(xorSha1InitValue);
			xorSha1InitValue = calcularorXorSha1Value;
		}

		return String.format("%06x", calcularorXorSha1Value);		
	}

}
