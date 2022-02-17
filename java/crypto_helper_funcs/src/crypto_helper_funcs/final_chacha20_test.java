package crypto_helper_funcs;

import java.math.BigInteger;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sha3_hash_crack.Salt_generator;

public class final_chacha20_test {

	public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException, Exception {

    String cipherText = "IjbEsfe86phPa2+6sWZOSE/52Gnx7ZqZMeTLTg==";
    String hexKey = "CA:B4:56:AF:43:C9:37:0E:AC:B5:45:94:A5:08:95:6D:54:E2:57:F9:CC:C5:A2:C0:21:41:D3:A6:F3:30:94:08";
    String partial_nonce = "00:01:02:03:04:05:06:07:08:09";
    
    byte[] unbase64 = Base64.getDecoder().decode(cipherText);
    byte[] byte_key = Crypto_utils.hexStrtoBytes(hexKey);
    byte[] partial_nonce_bytes = Crypto_utils.hexStrtoBytes(partial_nonce);
    
	
    SecretKey key = Crypto_utils.getKeyObj("ChaCha20SecretKey.ser");
    
    
    int counter = 17;
    
    ArrayList<byte[]> salts = new Salt_generator().get_salt_bytes();
    
    ArrayList<byte[]> nonces = new ArrayList<byte[]>();
    
    
	String decryptedText = decrypt(unbase64, key,counter,nonce_test);
	System.out.println("DeCrypted Text : "+decryptedText);
	System.out.println(out);
	}


	
	public static String decrypt(byte[] cipherText, SecretKey key, int counter, byte[] nonce_value) throws Exception {
		byte[] nonceBytes = nonce_value;
		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("ChaCha20");

		// Create IvParamterSpec
		ChaCha20ParameterSpec param = new ChaCha20ParameterSpec(nonceBytes, counter);

		 // Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "ChaCha20");

		// Initialize Cipher for DECRYPT_MODE
		cipher.init(Cipher.DECRYPT_MODE, keySpec, param);

		// Perform Decryption
		byte[] decryptedText = cipher.doFinal(cipherText);

		return new String(decryptedText);
	}
	
}