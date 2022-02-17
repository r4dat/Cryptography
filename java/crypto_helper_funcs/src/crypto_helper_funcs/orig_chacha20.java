package crypto_helper_funcs;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class orig_chacha20 {

	public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException, Exception {

    byte[] message = "Hello World".getBytes();

	String plainText = "This is a plain text which will be encrypted by ChaCha20 Poly1305 Algorithm";
	KeyGenerator keyGenerator = KeyGenerator.getInstance("ChaCha20");keyGenerator.init(256);

	// Generate Key
	SecretKey key = keyGenerator.generateKey();

	System.out.println("Original Text  : "+plainText);

	byte[] cipherText = encrypt(plainText.getBytes(),key);
	System.out.println("Encrypted Text : "+Base64.getEncoder().encodeToString(cipherText));

	String decryptedText = decrypt(cipherText, key);
	System.out.println("DeCrypted Text : "+decryptedText);

	}

	public static byte[] encrypt(byte[] plaintext, SecretKey key) throws Exception {
		byte[] nonceBytes = new byte[12];

		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305/None/NoPadding");

		// Create IvParamterSpec
		AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(nonceBytes);

		// Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "ChaCha20");

		// Initialize Cipher for ENCRYPT_MODE
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

		// Perform Encryption
		byte[] cipherText = cipher.doFinal(plaintext);

		return cipherText;
	}

	public static String decrypt(byte[] cipherText, SecretKey key) throws Exception {
		byte[] nonceBytes = new byte[12];

		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305/None/NoPadding");

		// Create IvParamterSpec
		AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(nonceBytes);

		// Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "ChaCha20");

		// Initialize Cipher for DECRYPT_MODE
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

		// Perform Decryption
		byte[] decryptedText = cipher.doFinal(cipherText);

		return new String(decryptedText);
	}
}