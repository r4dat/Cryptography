package calc_curve;
import java.math.BigInteger;
public class Runner_curve_calc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger a = new BigInteger("10007");
		BigInteger u = new BigInteger("546181666269001"); //x
		BigInteger v = new BigInteger("128690356290639"); //y
		BigInteger p = new BigInteger("760272393654373");
		BigInteger b = (v.pow(2).subtract(u.pow(3)).subtract((a.multiply(u)))).mod(p);
		BigInteger qmult = new BigInteger("190068100249727");
		System.out.println(b);
		System.out.println((u.multiply(qmult)).mod(p));
		
		// ((3x_1^2 - a) /2y_1) mod n
		BigInteger s =  ((((u.pow(2)).multiply(BigInteger.valueOf(3))).add(a)).divide((v.multiply(BigInteger.TWO)))).mod(p);
		System.out.println("S "+s);
		BigInteger tmp = (v.multiply(BigInteger.valueOf(-1))).mod(p);
		System.out.println(tmp);
		System.out.println();
		
		System.out.println(MultiScalar.multiplyScalarPoint(8));
		
		
		
		}

}
