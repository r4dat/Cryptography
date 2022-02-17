package extended_euclidean;
import java.math.BigInteger;
public class ExtendedEuclid {
//  return array [d, a, b] such that d = gcd(p, q), ap + bq = d
   static BigInteger[] gcd(BigInteger p, BigInteger q) {
      if (q.compareTo(BigInteger.valueOf(0))==0)
         return new BigInteger[] { p, BigInteger.valueOf(1), BigInteger.valueOf(0) };
      
      BigInteger[] vals = gcd(q, p.mod(q));
      BigInteger d = vals[0];
      BigInteger a = vals[2];
      BigInteger b = vals[1].subtract( ((p.divide(q)).multiply(vals[2])));
      return new BigInteger[] { d, a, b };
   }


}



