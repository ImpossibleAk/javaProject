package com.project.utility;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author -> Aswin K
 *
 */
public class Encryption {


	public static String enCrypt(String data) throws Exception {
		// Generate AES key
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128); // 128-bit key
		SecretKey secretKey = keyGen.generateKey();
		byte[] secretKeyBytes = secretKey.getEncoded();
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");

		// Encrypt the data
		Cipher encryptCipher = Cipher.getInstance("AES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] encryptedBytes = encryptCipher.doFinal(data.getBytes());
		String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);

		return encryptedData;
	}

	public static String deCrypt(String data) throws Exception {
		// Generate AES key
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128); // 128-bit key
		SecretKey secretKey = keyGen.generateKey();
		byte[] secretKeyBytes = secretKey.getEncoded();
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");

		// Decrypt the data
		Cipher decryptCipher = Cipher.getInstance("AES");
		decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] decryptedBytes = decryptCipher.doFinal(Base64.getDecoder().decode(data));
		String decryptedData = new String(decryptedBytes);
		return decryptedData;
	}
}
