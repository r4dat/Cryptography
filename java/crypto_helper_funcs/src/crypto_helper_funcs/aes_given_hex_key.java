package crypto_helper_funcs;
import javax.crypto.*;
import java.math.BigInteger;
import javax.crypto.spec.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class aes_given_hex_key {

    public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException {

    	
        String hexkey = "EE:EE:EE:EE:EE:EE:EE:EE:EE:EE:EE:EE:EE:EE:EE:EE:AA:AA:AA:AA:AA:AA:AA:AA:EE:EE:EE:EE:EE:EE:EE:EE"; 
        
      //String cipherText64 = "L4JwZr1hziltX2EqVzQeWPjGxgaAuMKWPI6LhNCKszztln+ekxR9DQCIeZS77VJ0";
      //String cipherText64 = "L4JwZr1hziltX2EqVzQeWPjGxgaAuMKWPI6LhNCKszztln+ekxR9DQCIeZS77VJ0";
      String cipherText64 =   "L4JwZr1hziItX2EqVzQeWPjGxgaAuMKWPI6LhNCKszztIn+ekxR9DQCleZS77VJ0";
        
        byte[] byte_key = Crypto_utils.hexStrtoBytes(hexkey);
        
//        String base64payload = Base64.getEncoder().encodeToString(payload.getBytes());
        String encrypted = "";
        String decrypted = "";
        
        byte[] unbase64 = Base64.getDecoder().decode(cipherText64);
        SecretKeySpec Key = new SecretKeySpec(byte_key, "AES");

        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        
        aesCipher.init(Cipher.DECRYPT_MODE,Key);
        byte[] decryptedMessage = aesCipher.doFinal(unbase64);
        System.out.println("Decrypted String:");
        System.out.println(new String(decryptedMessage));
    }
}