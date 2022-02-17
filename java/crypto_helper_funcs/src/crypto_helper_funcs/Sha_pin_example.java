package crypto_helper_funcs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Sha_pin_example {

	public static void main(String[] args) {
		ArrayList<byte[]> tmp = Crypto_utils.generate_char_seq('!', 50, 3);
		String inputStr = "This is the file to sha-1 MAC";
		String digest_string = "lvlb8MIRyeq5sxEC9Ot+NTdUaC4=";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
			md.reset();
		} catch (NoSuchAlgorithmException e) {
		}
		for (int i = 0; i < tmp.size(); i++) {
			md.reset();
			md.update(inputStr.getBytes());
			md.update(tmp.get(i));
			byte[] hash = md.digest();
			String out_hash = Base64.getEncoder().encodeToString(hash);
			if (out_hash.compareTo(digest_string)==0) {
				System.out.println("Correct");
				System.out.println((new String(tmp.get(i))));
			}
		}
	}
}
