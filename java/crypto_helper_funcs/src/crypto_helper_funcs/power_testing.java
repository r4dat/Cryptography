package crypto_helper_funcs;

import java.math.BigDecimal;
import java.math.BigInteger;

public class power_testing {
	public static void main(String[] args) {
	String base_str = "13";
	String base_exp = "1024";
	BigInteger a = new BigInteger(base_str);
	BigInteger b = new BigInteger(base_exp);
	
	BigInteger out_big_pow = Crypto_utils.pow(a, b);
	BigInteger native_pow = a.pow(Integer.valueOf(base_exp));

	System.out.println("Native and fast_exp bigpow are equal:");
	System.out.println(out_big_pow.equals(native_pow));
	assert(out_big_pow.equals(native_pow));
}
}
