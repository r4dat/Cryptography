package crypto_helper_funcs;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class aes_manual_key {

    public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException {

        byte[] message = "Hello World".getBytes();
        String key = "5u7x!A%D*G-KaPdSgVkYp3s6v9y/B?E(";
        String payload = "Test string to test AES 256 ECB";
        String base64payload = Base64.getEncoder().encodeToString(payload.getBytes());
        String encrypted = "";
        String decrypted = "";
        
        String unbase64 = new String(Base64.getDecoder().decode(base64payload));
        SecretKeySpec Key = new SecretKeySpec(key.getBytes(), "AES");

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