package crt;
import java.math.BigInteger;
/*
 * Written By: Gregory Owen
 * Date: 10/10/11
 * Finds a single congruence equivalent to multiple given congruences
 * (assuming that one exists) via the Chinese Remainder Theorem
 */

public class CRT_BigInt
{
  /*
   * performs the Euclidean algorithm on a and b to find a pair of coefficients
   * (stored in the output array) that correspond to x and y in the equation
   * ax + by = gcd(a,b)
   * constraint: a > b
   */
  public static BigInteger[] euclidean(BigInteger a, BigInteger b)
  {
    if(b.compareTo(a)==1)
    {
      //reverse the order of inputs, run through this method, then reverse outputs
      BigInteger[] coeffs = euclidean(b, a);
      BigInteger[] output = {coeffs[1], coeffs[0]};
      return output;
    }

    BigInteger q = a.divide(b);
    //a = q*b + r --> r = a - q*b
    BigInteger r = a.subtract((q.multiply(b)));
    
    //when there is no remainder, we have reached the gcd and are done
    if(r.compareTo(BigInteger.valueOf(0)) == 0)
    {
      BigInteger[] output = {BigInteger.valueOf(0), BigInteger.valueOf(1)};
      return output;
    }
    
    //call the next iteration down (b = qr + r_2)
    BigInteger[] next = euclidean(b, r);
    
    //BigInteger[] output = {next[1], next[0] - q*next[1]};
    BigInteger[] output = {next[1], next[0].subtract( (q.multiply(next[1])) )};
    return output;
  }
  
  //finds the least positive integer equivalent to a mod m
  public static BigInteger leastPosEquiv(BigInteger a, BigInteger m)
  {
	BigInteger neg_a, tmp_lpe;
    //a eqivalent to b mod -m <==> a equivalent to b mod m
    if(m.compareTo(BigInteger.valueOf(0)) == -1)
    	return leastPosEquiv(a, m.multiply(BigInteger.valueOf(-1)));
    //if 0 <= a < m, then a is the least positive integer equivalent to a mod m
    //if(a >= 0 && a < m)
	if( (a.compareTo(BigInteger.valueOf(0)) >= 0) && (a.compareTo(m)==-1))
		return a;
    
    //for negative a, find the least negative integer equivalent to a mod m
    //then add m
    if( (a.compareTo(BigInteger.valueOf(0))) == -1) {
    	neg_a = BigInteger.valueOf(-1).multiply(a);
		tmp_lpe = leastPosEquiv(neg_a,m);
    	BigInteger neg_tmp_lpe = BigInteger.valueOf(-1).multiply(tmp_lpe);
    	return (neg_tmp_lpe.add(m));
    }
	
    	

    BigInteger q = a.divide(m);
    
    /*
     * a = qm + r, with 0 <= r < m
     * r = a - qm is equivalent to a mod m
     * and is the least such non-negative number (since r < m)
     */
    return a.subtract((q.multiply(m)));
  }
  
  public static void main(String[] args)
  {
	/*
	 * the current setup finds a number x such that:
	 *	x = 2 mod 5, x = 3 mod 7, x = 4 mod 9, and x = 5 mod 11
	 * note that the values in mods must be mutually prime
	 */
//    int[] constraints = {2,3,4,5}; //put modular contraints here
//    int[] mods = {5,7,9,11}; //put moduli here

	// simple worked example
	BigInteger[] constraints = {BigInteger.valueOf(5),BigInteger.valueOf(3)}; //put modular contraints here
    BigInteger[] mods = {BigInteger.valueOf(8),BigInteger.valueOf(5)}; //put moduli here
	  
	  
    //M is the product of the mods
    BigInteger M = BigInteger.valueOf(1);
    for(int i = 0; i < mods.length; i++)
    	M = M.multiply(mods[i]);
    
    BigInteger[] multInv = new BigInteger[constraints.length];
    
    /*
     * this loop applies the Euclidean algorithm to each pair of M/mods[i] and mods[i]
     * since it is assumed that the various mods[i] are pairwise coprime,
     * the end result of applying the Euclidean algorithm will be
     * gcd(M/mods[i], mods[i]) = 1 = a(M/mods[i]) + b(mods[i]), or that a(M/mods[i]) is
     * equivalent to 1 mod (mods[i]). This a is then the multiplicative
     * inverse of (M/mods[i]) mod mods[i], which is what we are looking to multiply
     * by our constraint constraints[i] as per the Chinese Remainder Theorem
     * euclidean(M/mods[i], mods[i])[0] returns the coefficient a
     * in the equation a(M/mods[i]) + b(mods[i]) = 1
     */
    for(int i = 0; i < multInv.length; i++)
    	multInv[i] = euclidean(M.divide(mods[i]), mods[i])[0];
    
    BigInteger x = BigInteger.valueOf(0);
    
    //x = the sum over all given i of (M/mods[i])(constraints[i])(multInv[i])
    BigInteger mods_idx,Mdivm,mult,constraints_idx,multInv_idx;
    for(int j = 0; j < mods.length; j++) {
    	mods_idx = mods[j];
    	constraints_idx = constraints[j];
    	multInv_idx = multInv[j];
    	Mdivm = M.divide(mods_idx);
    	mult = Mdivm.multiply(constraints_idx).multiply(multInv_idx);
    	x = x.add(mult);
    }	
    	
	x = leastPosEquiv(x, M);
    
    System.out.println("x is equivalent to " + x + " mod " + M);
    
    //test
    BigInteger fct = new BigInteger("14489704866772099");
    BigInteger sqrt_fct = fct.sqrt();
    System.out.println("Test");
    for (int i=0; i<10000;i++) {
    	BigInteger ot= fct.divide((sqrt_fct.add(BigInteger.valueOf(i))));
    	if (ot.compareTo(BigInteger.valueOf(0))==0) {
    		System.out.println(sqrt_fct.add(BigInteger.valueOf(i)));
    	}	
    }
  }
}
