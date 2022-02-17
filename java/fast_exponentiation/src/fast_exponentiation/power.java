package fast_exponentiation;
import java.math.BigInteger;
public class power {
static int power(int x, int y){
	    int res = 1;     // Initialize result
	  
	    while (y > 0)
	    {
	       
	        // If y is odd, multiply x with result
	        if ((y & 1) != 0)
	            res = res * x;
	  
	        // y must be even now
	        y = y >> 1; // y = y/2
	        x = x * x;  // Change x to x^2
	    }
	    return res;
	}

static BigInteger bigPower(BigInteger x, BigInteger y){
    BigInteger res = BigInteger.valueOf(1);     // Initialize result
  
    while (y.compareTo(BigInteger.valueOf(0)) == 1)
    {
       
        // If y is odd, multiply x with result
        if ( (y.mod(BigInteger.valueOf(2))).compareTo(BigInteger.valueOf(0)) != 0)
            res = res.multiply(x);
  
        // y must be even now
        y = y.divide(BigInteger.valueOf(2)); // y = y/2
        x = x.multiply(x);  // Change x to x^2
    }
    return res;
}

  /* Iterative Function to calculate (x^y) modulo p in O(log y) */
  static BigInteger bigOtherPower(BigInteger x, BigInteger y, BigInteger p)
  {
    BigInteger res = BigInteger.valueOf(1); // Initialize result
 
    x = x.mod(p); // Update x if it is more than or
    // equal to p
 
    if (x.compareTo(BigInteger.valueOf(0))==0)
      return BigInteger.valueOf(0); // In case x is divisible by p;
 
    while (y.compareTo(BigInteger.valueOf(0)) > 0)
    {
 
      // If y is odd, multiply x with result
      if ( (y.mod(BigInteger.valueOf(2))).compareTo(BigInteger.valueOf(0)) !=0 ) // y.mod(2) <> 0 
        res = (res.multiply(x)).mod(p);
 
      // y must be even now
      y = (y.divide(BigInteger.valueOf(2))); // y = y/2
      x = (x.multiply(x)).mod(p);
    }
    return res;
  }

  /* Iterative Function to calculate (x^y) in O(log y) */
  static int otherPower(int x, int y, int p)
  {
    int res = 1; // Initialize result
 
    x = x % p; // Update x if it is more than or
    // equal to p
 
    if (x == 0)
      return 0; // In case x is divisible by p;
 
    while (y > 0)
    {
 
      // If y is odd, multiply x with result
      if ((y & 1) != 0)
        res = (res * x) % p;
 
      // y must be even now
      y = y >> 1; // y = y/2
      x = (x * x) % p;
    }
    return res;
  }
  
}

