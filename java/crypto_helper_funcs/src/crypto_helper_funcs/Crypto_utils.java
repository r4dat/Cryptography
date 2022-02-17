package crypto_helper_funcs;

import javax.crypto.*;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.math.BigInteger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.security.InvalidKeyException;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Formatter;
import javax.crypto.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.lang.Exception;
import java.security.*;
import java.util.Arrays;

public class Crypto_utils {
	static SecretKey inputKey;
	static SecretKey outputKey;

	public static SecretKey getKeyObj(String file_path)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fi = new FileInputStream(new File(file_path));
		ObjectInputStream oi = new ObjectInputStream(fi);

		outputKey = (SecretKey) oi.readObject();
		oi.close();
		fi.close();
		System.out.println(outputKey.getAlgorithm());
		System.out.println(outputKey.getFormat());
		return outputKey;
	}

	public static void putKeyObj(String file_path, SecretKey keyObj) throws FileNotFoundException, IOException {
		inputKey = keyObj;
		FileOutputStream f = new FileOutputStream(new File(file_path));
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(inputKey);

		o.close();
		f.close();
	}

	public static List<String> stringsFromFile(String filePath) {
		List<String> file_strings = null;
		try {
			file_strings = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
		}
		;
		return file_strings;
	}

	public static byte[] allBytesFromFile(String filePath) {
		byte[] file_bytes = null;
		try {
			file_bytes = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
		}
		;
		return file_bytes;
	}

	public static ArrayList<byte[]> byteRowsFromFile(String filePath) {
		List<String> file_strings = null;
		ArrayList<byte[]> file_row_bytes = new ArrayList<byte[]>();
		try {
			file_strings = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
		}
		;

		for (int i = 0; i < file_strings.size(); i++) {
			file_row_bytes.add(file_strings.get(i).getBytes());
		}
		return file_row_bytes;
	}

	public static byte[] hexStrtoBytes(String hexstring) {
		String inputString = hexstring;
		String[] strParts = inputString.split(":");

		// convert hex string to byte values
		byte[] outputBytes = new byte[strParts.length];
		for (int i = 0; i < strParts.length; i++) {
			Integer hex = Integer.parseInt(strParts[i], 16);
			outputBytes[i] = hex.byteValue();
		}
		return outputBytes;
	}

	public static String hexStrtoDecStr(String hexstring) {
		String inputString = hexstring;
		char[] chStr = inputString.toCharArray();
		StringBuilder outStr = new StringBuilder();
		for (int i = 0; i < chStr.length; i++) {
			if (chStr[i] != ':') {
				outStr.append((int) chStr[i]);
			}
		}

		System.out.println(outStr.toString());
		return outStr.toString();
	}

	public static String decStrtoHexStr(String decstring) {
		String inputString = decstring;
		char[] chStr = inputString.toCharArray();
		StringBuilder outStr = new StringBuilder();
		for (int i = 0; i < chStr.length - 1; i += 2) {
			outStr.append((char) (Integer.parseInt(inputString.substring(i, i + 2))));
		}

		System.out.println(outStr.toString());
		return outStr.toString();
	}

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		formatter.close();
		return sb.toString();
	}

	public static String stringTo64(String s) {
		String out = Base64.getEncoder().encodeToString(s.getBytes());
		return out;
	}

	public static String base64ToString(String s) {
		String tmp = new String(Base64.getDecoder().decode(s));
		return tmp;
	}

	public static ArrayList<byte[]> generate_char_seq(char known_char, int ascii_limit, int size) {
		ArrayList<byte[]> out_buff_list = new ArrayList<byte[]>();
		ArrayList<String> out_string_list = new ArrayList<String>();
		ArrayList<int[]> combinations = new ArrayList<int[]>();

		// ascii limit 50, sequence size 3
		int[] seq_arr = new int[size];
		for (int i = 0; i < ascii_limit; i++) {
			for (int j = 0; j < ascii_limit; j++) {
				for (int k = 0; k < ascii_limit; k++) {
					seq_arr[0] = i;
					seq_arr[1] = j;
					seq_arr[2] = k;
					combinations.add(seq_arr.clone());
				}
			}
		}

		// for row in combinations
		// for element in row
		for (int i = 0; i < combinations.size(); i++) {
			byte[] byte_buff = new byte[size];
			int[] row = combinations.get(i);
			for (int e = 0; e < size; e++) {
				byte_buff[(e)] = (byte) ((char) row[e]);
			}
			byte[] first = { (byte) known_char };
			byte[] out = { first[0], byte_buff[0], byte_buff[1], byte_buff[2] };
			out_string_list.add(new String(out));
			out_buff_list.add(out);
		}
		return out_buff_list;

	}

	public static String hashMac(String text, String secretKey, String hash_algo) throws SignatureException {
		/*
		 * HmacSHA1 HmacSHA224 HmacSHA256 HmacSHA384 HmacSHA512 HmacSHA512/224
		 * HmacSHA512/256 HmacSHA3-224 HmacSHA3-256 HmacSHA3-384 HmacSHA3-512
		 */
		String HASH_ALGORITHM = hash_algo;
		try {
			SecretKey sk = new SecretKeySpec(secretKey.getBytes(), HASH_ALGORITHM);
			Mac mac = Mac.getInstance(sk.getAlgorithm());
			mac.init(sk);
			final byte[] hmac = mac.doFinal(text.getBytes());
			return Crypto_utils.toHexString(hmac);
		} catch (NoSuchAlgorithmException e1) {
			// throw an exception or pick a different encryption method
			throw new SignatureException("error building signature, no such algorithm" + HASH_ALGORITHM);
		} catch (InvalidKeyException e) {
			throw new SignatureException("error building signature, invalid key " + HASH_ALGORITHM);
		}
	}
	
	// Big Integer fast exponentiation
	public static BigInteger pow(BigInteger x, BigInteger y) {
		  if (y.compareTo(BigInteger.ZERO) < 0)
		    throw new IllegalArgumentException();
		  BigInteger z = x; // z will successively become x^2, x^4, x^8, x^16, x^32...
		  BigInteger result = BigInteger.ONE;
		  byte[] bytes = y.toByteArray();
		  for (int i = bytes.length - 1; i >= 0; i--) {
		    byte bits = bytes[i];
		    for (int j = 0; j < 8; j++) {
		      if ((bits & 1) != 0)
		        result = result.multiply(z);
		      // short cut out if there are no more bits to handle:
		      if ((bits >>= 1) == 0 && i == 0)
		        return result;
		      z = z.multiply(z);
		    }
		  }
		  return result;
		}


}
