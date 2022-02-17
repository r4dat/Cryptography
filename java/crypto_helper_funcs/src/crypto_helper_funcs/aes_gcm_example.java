package crypto_helper_funcs;
import javax.crypto.*;
import java.util.ArrayList;
import java.math.BigInteger;
import javax.crypto.spec.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class aes_gcm_example {

    public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException,
    										      InvalidAlgorithmParameterException{

    	
        String hexkey = "71:DE:54:0C:34:95:A3:C6:C1:41:48:C5:38:CB:FE:A5:44:08:37:68:CD:2C:ED:60:48:FB:8C:37:29:2E:DD:70"; 
        byte[] byte_key = Crypto_utils.hexStrtoBytes(hexkey);
        String cipherText64 = "qIEh1aaZK32XMX2jB6ghMqIjsQVk";        
        String IV = "8bV/oQ3WMjuQJkBw";
        byte[] unbaseIV = Base64.getDecoder().decode(IV.getBytes());
        byte[] iv = unbaseIV;
        int GCM_TAG_LENGTH = 128; //16 bytes
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        
        ArrayList<String> aad_list = new ArrayList<>();
        for(char c : alphabet) {
        	String tmp = "ab"+c;
        	aad_list.add(tmp);
        }
        

        
        byte[] unbase64 = Base64.getDecoder().decode(cipherText64);
        SecretKeySpec Key = new SecretKeySpec(byte_key, "AES");

        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

        for(String s : aad_list) {
        aesCipher.init(Cipher.DECRYPT_MODE,Key,new GCMParameterSpec(GCM_TAG_LENGTH, iv));
        aesCipher.updateAAD(s.getBytes());
        try {
        byte[] decryptedMessage = aesCipher.doFinal(unbase64);
        System.out.println("Decrypted String:");
        System.out.println(new String(decryptedMessage));
        System.out.println("AAD is:" +s);
        }
        catch(AEADBadTagException e) {
        // pass. Expect errors if AAD is incorrect. 	
        }
        }
}
}