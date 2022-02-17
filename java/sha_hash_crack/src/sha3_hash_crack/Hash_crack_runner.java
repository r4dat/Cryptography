package sha3_hash_crack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Hash_crack_runner {

	public static void main(String[] args) {
		final String[] sha_algos = {"SHA-1",
									"SHA-224", "SHA-256","SHA-384", "SHA-512",
									"SHA3-224", "SHA3-256","SHA3-384", "SHA3-512" };

		final String STRING_TO_MATCH = "2PTGEi98+px2aL7vd0MEE5oRfdjliw/ISSvLEl1QB6VRY63zi5z/kTUfTX4bJ6Z7KfOr9Ehpj1a8q4WSp7q5UQ==";
		String filePath = "C:\\Users\\Rob Roy\\eclipse-workspace-crypto\\sha_hash_crack\\src\\21Pwd.txt";
		List<String> pass_list = null;
		try {
			pass_list = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {};
		MessageDigest md = null;
		// list of all possible 2 byte salts stored as [0],[1]
		ArrayList<byte[]> salts = new Salt_generator().get_salt_bytes();
		outerloop:{
		for (int i = 0; i < sha_algos.length; i++) {
			try {
				md = MessageDigest.getInstance(sha_algos[i]);
				md.reset();
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but " + sha_algos[i] + " is not a valid message digest algorithm");
			}
			for (int j = 0; j < pass_list.size(); j++) {
				System.out.println("Trying: "+sha_algos[i]+ " "+pass_list.get(j));
				for (int k = 0; k < salts.size(); k++) {
					md.reset();
					md.update(salts.get(k));
					byte[] pass_bytes = (pass_list.get(j)).getBytes();
					md.update(pass_bytes);
					byte[] hash = md.digest();
					String test = Base64.getEncoder().encodeToString(hash);
					if (STRING_TO_MATCH.contentEquals(test)) {
						System.out.println();
						System.out.println(sha_algos[i]);
						System.out.println(pass_list.get(j));
						System.out.println(salts.get(k));
						System.out.println(test);
						System.out.println(STRING_TO_MATCH);
						break outerloop;
					}
				}
			}
		}

	}
	}

}
