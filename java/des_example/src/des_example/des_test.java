package des_example;
import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import Read_write_serial_key;

public class des_test {

    public static void main(String[] args) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  BadPaddingException,
                                                  IllegalBlockSizeException,
                                                  InvalidKeyException {
    	Read_write_serial_key a = new Read_write_serial_key();

        byte[] message = "Hello World".getBytes();

        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
        SecretKey desKey = keygenerator.generateKey();
        //base64 key
        System.out.println("B64 Key is: ");
        byte[] tmp_buff = desKey.getEncoded().clone();
        String tmp = Base64.getEncoder().encodeToString(desKey.getEncoded());
        System.out.println(tmp);

        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, desKey);

        byte[] encryptedMessage = desCipher.doFinal(message);
        System.out.println(Base64.getEncoder().encodeToString(encryptedMessage));
        
        desCipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] decryptedMessage = desCipher.doFinal(encryptedMessage);
        System.out.println("Decrypted String:");
        System.out.println(new String(decryptedMessage));
    }
}