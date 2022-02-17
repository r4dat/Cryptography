package extended_euclidean;
import java.math.BigInteger;
public class Runner {

	public static void main(String[] args) {
		ExtendedEuclid e = new ExtendedEuclid();
		//int p = 15;
		//int q = 26;
		//BigInteger p = new BigInteger("4097525640");
		//BigInteger q = new BigInteger("1410623581");
		//BigInteger p = new BigInteger("15");
		//BigInteger q = new BigInteger("26");
		BigInteger p = new BigInteger("683");
		BigInteger q = new BigInteger("727");
		BigInteger vals[] = e.gcd(p, q);
		System.out.println("gcd(" + p + ", " + q + ") = " + vals[0]);
		System.out.println(vals[1] + "(" + p + ") + " + vals[2] + "(" + q + ") = " + vals[0]);

	}

}
