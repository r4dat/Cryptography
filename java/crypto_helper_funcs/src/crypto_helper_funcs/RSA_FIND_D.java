package crypto_helper_funcs;

import java.math.BigInteger;
public class RSA_FIND_D {
    public static void main(String args[])
    {
        int i;
        BigInteger p,q,n,z,e,mod;
        BigInteger d = new BigInteger("0");
        p = new BigInteger("938251314208231");
        q = new BigInteger("912379972470011");
        e = new BigInteger("227");
        BigInteger one = new BigInteger("1");
        // Step 1: calculate n = p* q
             n = p.multiply(q);
        //Step 2: Compute ϕ(n) = (p – 1) * (q – 1), here z is representing ϕ(n)
        z = (p.subtract(one)).multiply(q.subtract(one));
        System.out.println("the value of z = " + z);// Printing ϕ(n) value
        //Step 3: Choose e such gcd(e , ϕ(n) ) = 1, calculating gcd of e and z
        /* We will start with e=2, and will keep incrementing till e<z,
         * we wil break if we find any value of e where GCD(E and z) is equal to 1. */
            String e_value = e.toString();
            String z_value = z.toString();
            int GCD_VALUE = find_gcd(e_value,z_value);// calling function to calculate GCD of e and z
            if (GCD_VALUE == 1) { // checking if gcd of e , ϕ(n) ) = 1
              System.out.println("GCD Value is: "+GCD_VALUE);
            }

        // e is for public key exponent
        System.out.println("the value of e = " + e.toString());
        //Step 5: Calculate private key d parameter, modular inverse of e
        //Calculate d such e*d mod ϕ(n) = 1
        d = e.modInverse(z);
        System.out.println("the value of d = " + d.toString());
    }

    // FInd GCD
    public static int find_gcd(String num1, String num2)
    {

        // BigInteger object to store the result
        BigInteger result;
        // Holds the values to calculate gcd
        String input1 = num1; // We have value in String and later will convert them to BigInteger
        String input2 = num2;
        // Creating two BigInteger objects
        BigInteger a
                = new BigInteger(num1);
        BigInteger b
                = new BigInteger(num2);
        // Calculate gcd
        result = a.gcd(b);
        return result.intValue(); // returning integer value of Big Integer
    }
}







