package crypto_helper_funcs;

import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;

public class ElGamal
{
    public static void main(String[] args) throws IOException
    {
        BigInteger p, g, y,m, secretKey;
        //public is p,g,y
        Random sc = new SecureRandom();
        // x is secret key
        secretKey = new BigInteger("237");
        p = BigInteger.valueOf(1009);
        g = BigInteger.valueOf(101);
        y = BigInteger.valueOf(482);
        //
        // public key calculation
        //
        //p = BigInteger.probablePrime(64, sc);
        m = BigInteger.valueOf(559); // MESSAGE
        BigInteger k = BigInteger.valueOf(291); // Random value.
        
        BigInteger x_val = g.modPow(k, p);
        BigInteger y_val = m.multiply(y.modPow(k,p)).mod(p);
		String x_val_str = new String(x_val.toString());
		String y_val_str = new String(y_val.toString());
		System.out.println("Encrypted Value: ("+x_val_str+", "+y_val_str+")");
		
		
        // Decryption
        //
		BigInteger cipher_x = BigInteger.valueOf(661);
        BigInteger cipher_y = BigInteger.valueOf(421);
        
        //private key secretKey
		
        BigInteger a = cipher_x;
        BigInteger b = cipher_y;
        
        BigInteger piece_one = a.modPow(secretKey, p);
        BigInteger piece_two = piece_one.modInverse(p);
        BigInteger result = b.multiply(piece_two).mod(p);
        System.out.println(new String(result.toString()));

        
        

    }
}