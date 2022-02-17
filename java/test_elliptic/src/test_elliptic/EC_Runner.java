package test_elliptic;


import java.math.BigInteger;
import java.nio.ByteBuffer;

import java.security.MessageDigest;

public class EC_Runner {
    public static void main(String[] args) throws Exception {
    	// a, b, p
    	EllipticCurve ep = new EllipticCurve("28208962", "9040486", "29526661");
    	// not actually functional here since the processing code is in python. 

        BigInteger px = new BigInteger("6577231207538257");
        BigInteger py = new BigInteger("12878151309025200");
        BigInteger p = new BigInteger("63961671455363581");
        BigInteger s = new BigInteger("66695901066698266");

        BigInteger qx = new BigInteger("43005954890049160");
        BigInteger qy = new BigInteger("35677948516347695");

        // initial compressed point y1, y2 == hash. 
        BigInteger[] y1 = { new BigInteger("61250630813509568"), ep.ZERO };
        BigInteger y2 = new BigInteger("44781300880417642");

        // // decrypt ...

        System.out.println();
        System.out.println("Decryption: ");
        // Decompress y1 in python. 
        y1[1] = new BigInteger("52011375677253080");
        System.out.println("Decompress(y1) = (" + y1[0] + "," + y1[1] + ")");
        // Calculate R = nP [parameter sP using double_and_add in python. 
        BigInteger[] R = { new BigInteger("21442398655148527"), new BigInteger("44170805924665530") };
        System.out.println("R = s x decompress(y1) = (" + R[0] + ", " + R[1] + ")");
        System.out.println("R = s x decompress(y1) = (" + byteArrayToHex(R[0].toByteArray()) + ", "
                + byteArrayToHex(R[1].toByteArray()) + ")");

        MessageDigest mdDecryption = MessageDigest.getInstance("SHA3-256");

        System.out.println("Bit length of p: " + p.bitLength());
        mdDecryption.update(R[0].toByteArray());
        mdDecryption.update(R[1].toByteArray());
        byte[] hashDecryption = mdDecryption.digest();
        System.out.println(byteArrayToHex(hashDecryption));

        BigInteger firstNBitsofModulo = new BigInteger(hashDecryption);
        if (firstNBitsofModulo.compareTo(ep.ZERO) < 0) {
            firstNBitsofModulo = new BigInteger(1, firstNBitsofModulo.toByteArray());
        }
        firstNBitsofModulo = firstNBitsofModulo.shiftRight(firstNBitsofModulo.bitLength() - p.bitLength());

        System.out.println("H(kQ) first n bits: " + byteArrayToHex(firstNBitsofModulo.toByteArray()));
        System.out.println("H(kQ) first n bits value: " + firstNBitsofModulo);
        System.out.println(firstNBitsofModulo.bitLength());
        // // BigInteger H_R = BigInteger.valueOf(firstNBitsofModulo);
        BigInteger H_R = firstNBitsofModulo;
        // // BigInteger H_R = new BigInteger(hashDecryption);
        System.out.println("Y2: " + y2);
        BigInteger message = y2.subtract(H_R).mod(p);
        System.out.println("Message: " + message);
        System.out.println("Message Hex: " + String.format("%x", message));
        System.out.println("Message Hex: " + byteArrayToHex(message.toByteArray()));
        System.out.println(y2.bitLength());

    }


    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x:", b));
        return sb.toString();
    }

}