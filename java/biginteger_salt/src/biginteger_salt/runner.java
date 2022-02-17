package biginteger_salt;
import java.math.BigInteger;
public class runner {

	public static void main(String[] args) {
		BigInteger twobi = BigInteger.valueOf(2);
		BigInteger cap = twobi.pow(64).subtract(BigInteger.valueOf(1));
		byte[] bytebuffer;
		bytebuffer = cap.toByteArray();
		System.out.println("Check Buffer");
	}

}
