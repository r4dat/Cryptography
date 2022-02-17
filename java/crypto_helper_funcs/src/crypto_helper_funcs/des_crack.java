package crypto_helper_funcs;
import javax.crypto.*;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.ArrayList;
import java.util.Arrays;


public class des_crack {

    public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException {

        byte[] message = "PrimeCurios".getBytes();
        String cipher64 = "g4T1HQebkEg5lHVM+lre4g==";
        byte[] cipherBytes = Base64.getDecoder().decode(cipher64);
        ArrayList<byte[]> key_list = new ArrayList<byte[]>();
        ArrayList<String> string_list = new ArrayList<String>();
        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
        //byte[] manual_key = new byte[8];

        //04:91:04:CD:57:01
        int[] key_piece = {4,145,4,205,87,1};
        byte[] manual_key = new byte[8];
        for(int i=0; i<key_piece.length;i++) {
        	manual_key[i] = (byte)key_piece[i];
        }
        
        for(int i=0; i<256;i++) {
        	for(int j=0; j<256; j++) {
        		manual_key[6]=(byte)i;
        		manual_key[7]=(byte)j;
        		key_list.add(manual_key.clone());
        		string_list.add(Crypto_utils.toHexString(manual_key.clone()));
        		
        	}
        }
        
        System.out.println("Test");
        
//        byte[] new_manual_key = Crypto_utils.hexStrtoBytes("EE:EE:EE:EE:EE:EE:EE:EE");
       // SecretKeySpec desKey = new SecretKeySpec(manual_key, "DES");
        
        
        //base64 key

//        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        for(int i = 0; i<key_list.size();i++) {
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        SecretKeySpec desKey = new SecretKeySpec(key_list.get(i), "DES");
        desCipher.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] encryptedMessage = desCipher.doFinal(message);
        String encString = Base64.getEncoder().encodeToString(encryptedMessage);
        
        if(encString.compareTo(cipher64)==0) {
        	System.out.println("Success");
        	System.out.println(i);
        	System.out.println(key_list.get(i));
        	System.out.println(string_list.get(i));
        }

        }
        System.out.println("go");
//        
//        desCipher.init(Cipher.DECRYPT_MODE, desKey);
//        byte[] decryptedMessage = desCipher.doFinal(encryptedMessage);
//        System.out.println("Decrypted String:");
//        System.out.println(new String(decryptedMessage));
    }
}