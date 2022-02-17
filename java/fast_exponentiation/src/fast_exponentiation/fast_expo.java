package fast_exponentiation;

import java.math.BigInteger;

public class fast_expo {

	public static void main(String[] args) {
	power p_func = new power();
	int out;
	out = p_func.power(2,3);
	System.out.println(out);
	
	BigInteger bigOut;
	bigOut = p_func.bigPower(BigInteger.valueOf(2), BigInteger.valueOf(3));
	System.out.println(bigOut);
	
	  int bigx = -125401;
    int bigy = 130325;
    int bigp = 217891; // usually p ; x^y mod p or x^y % p
    System.out.println("Power is " + p_func.otherPower(bigx, bigy, bigp));
    
    BigInteger biga = BigInteger.valueOf(-125401);
    BigInteger bigb = BigInteger.valueOf(130325);
    BigInteger bigc = BigInteger.valueOf(217891);
    System.out.println("Power is " + p_func.bigOtherPower(biga, bigb, bigc));
	
	int x = 7;
    int y = 66;
    int p = 101; // usually p ; x^y mod p or x^y % p
    System.out.println("Power is " + p_func.otherPower(x, y, p));
    
    BigInteger a = BigInteger.valueOf(7);
    BigInteger b = BigInteger.valueOf(66);
    BigInteger c = BigInteger.valueOf(101);
    System.out.println("Power is " + p_func.bigOtherPower(a, b, c));
	}

}
