package com.bestbuy.bbym.ise.drp.utils;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {

    private static final String ALGORITHM = "HmacSHA1";

    /**
     * generate HMAC
     */
    public static String getHmacMD5(String privateKey, String input) throws Exception {
	byte[] keyBytes = privateKey.getBytes();
	Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, ALGORITHM);
	Mac mac = Mac.getInstance(ALGORITHM);
	mac.init(key);
	return byteArrayToHex(mac.doFinal(input.getBytes()));
    }

    public static String buildHmacMD5Signature(String pKey, String pStringToSign) {
	String lSignature = "None";
	try{
	    Mac lMac = Mac.getInstance("HmacMD5");
	    SecretKeySpec lSecret = new SecretKeySpec(pKey.getBytes(), "HmacMD5");
	    lMac.init(lSecret);

	    byte[] lDigest = lMac.doFinal(pStringToSign.getBytes());
	    BigInteger lHash = new BigInteger(1, lDigest);
	    lSignature = lHash.toString(16);
	    if ((lSignature.length() % 2) != 0){
		lSignature = "0" + lSignature;
	    }
	}catch(NoSuchAlgorithmException lEx){
	    throw new RuntimeException("Problems calculating HMAC", lEx);
	}catch(InvalidKeyException lEx){
	    throw new RuntimeException("Problems calculating HMAC", lEx);
	}

	return lSignature;
    }

    /**
     * get HEX representation of HMAC
     */
    private static String byteArrayToHex(byte[] a) {
	int hn, ln, cx;
	String hexDigitChars = "0123456789abcdef";
	StringBuffer buf = new StringBuffer(a.length * 2);
	for(cx = 0; cx < a.length; cx++){
	    hn = ((int) (a[cx]) & 0x00ff) / 16;
	    ln = ((int) (a[cx]) & 0x000f);
	    buf.append(hexDigitChars.charAt(hn));
	    buf.append(hexDigitChars.charAt(ln));
	}
	return buf.toString();
    }

}
