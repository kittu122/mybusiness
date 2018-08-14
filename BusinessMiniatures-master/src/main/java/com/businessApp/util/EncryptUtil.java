package com.businessApp.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encryption and Decryption of String data; PBE(Password Based Encryption and
 * Decryption)
 * 
 */
public class EncryptUtil
{

	private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	static Cipher ecipher;
	static Cipher dcipher;
	// 8-byte Salt
	static byte[] salt = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03};
	// Iteration count
	static int iterationCount = 28;
	private static String secretKey = "businessMinis";
	public EncryptUtil() {

	}

	public static String encrypt(String plainText)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException
	{

		logger.info("In encrypt  method is");

		String encryptedStr = null;
		try
		{
			encryptedStr = AESEncryptUtil.encryptText(plainText);
		} catch (Exception e)
		{
			logger.error("In ecxeption");
		}
		return encryptedStr;
	}

	public static String decrypt(String encryptedText)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException, IOException
	{

		String plainStr = null;
		try
		{

			logger.info("In decrypt  method is");
			plainStr = AESEncryptUtil.decryptText(encryptedText);
		} catch (Exception e)
		{
		}
		return plainStr;
	}

	public static void main(String[] args) throws Exception
	{
		EncryptUtil cryptoUtil = new EncryptUtil();

		String text = "ramesh";
		String encText = EncryptUtil.encrypt(text);

		System.out.println("Original  text is " + text);
		System.out.println("Encrypted text is " + encText);
		System.out.println("Decrypted text is " + EncryptUtil.decrypt(encText));
	}

}
