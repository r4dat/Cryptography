package crypto_helper_funcs;
import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class aes_test {

    public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException {

        byte[] message = "Hello World".getBytes();

        KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
        SecretKey Key = keygenerator.generateKey();
        
        //base64 key
        System.out.println("B64 Key is: ");
        byte[] tmp_buff = Key.getEncoded().clone();
        String tmp = Base64.getEncoder().encodeToString(Key.getEncoded());
        System.out.println(tmp);

        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, Key);

        byte[] encryptedMessage = aesCipher.doFinal(message);
        System.out.println(Base64.getEncoder().encodeToString(encryptedMessage));
        
        aesCipher.init(Cipher.DECRYPT_MODE, Key);
        byte[] decryptedMessage = aesCipher.doFinal(encryptedMessage);
        System.out.println("Decrypted String:");
        System.out.println(new String(decryptedMessage));
    }
}