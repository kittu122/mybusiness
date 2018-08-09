package com.businessApp.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class AESEncryptUtil
{

	static String salt = "businessMinis";
	static SecretKeySpec secretKeySpec = null;

	static
	{
		try
		{
			getSecretEncryptionKey();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static SecretKeySpec getSecretEncryptionKey() throws Exception
	{

		byte[] key = salt.getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		key = sha.digest(key);

		secretKeySpec = new SecretKeySpec(key, "AES");
		return secretKeySpec;
	}

	/**
	 * Encrypts plainText in AES using the secret key
	 * 
	 * @param plainText
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static String encryptText(String plainText) throws Exception
	{
		// AES defaults to AES/ECB/PKCS5Padding in Java 7

		// getSecretEncryptionKey();

		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

		String encStr = Base64.encodeBase64String(byteCipherText);

		return encStr;
	}

	/**
	 * Decrypts encrypted byte array using the key used for encryption.
	 * 
	 * @param byteCipherText
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static String decryptText(String CipherText) throws Exception
	{
		// AES defaults to AES/ECB/PKCS5Padding in Java 7

		// getSecretEncryptionKey();
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		byte[] enc = Base64.decodeBase64(CipherText);

		byte[] bytePlainText = aesCipher.doFinal(enc);
		return new String(bytePlainText);
	}

	public static void main(String[] args) throws Exception
	{

		getSecretEncryptionKey();

	}

}
