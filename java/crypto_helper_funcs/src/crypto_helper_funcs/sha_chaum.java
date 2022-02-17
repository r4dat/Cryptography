package crypto_helper_funcs;

import java.security.MessageDigest;
import java.math.BigInteger;
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

public class sha_chaum {

	public static void main(String[] args) {

		String messageStr = "I always lie. I did sign this document.";
		byte[] messageBytes = messageStr.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA3-512");
			md.reset();
		} catch (NoSuchAlgorithmException e) {
		}
	
			byte[] hash = md.digest(messageBytes);
			byte byte_zero = (byte)0;
			byte[] combined = new byte[1 + hash.length];

			combined[0] = (byte)0;
			System.arraycopy(hash,0,combined,1,hash.length);
			
			BigInteger q = new BigInteger("1866376703295234427329404442659647807110788528083101525650276469870064434235406663780789261");
			BigInteger B = new BigInteger(combined);
			
			BigInteger final_h = B.mod(q);
			
			System.out.println(final_h);
			

			
		}
}
