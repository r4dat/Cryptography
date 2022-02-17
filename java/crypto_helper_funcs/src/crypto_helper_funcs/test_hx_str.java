package crypto_helper_funcs;

public class test_hx_str {

	public static void main(String[] args) {
		String t = "AA:BB:CC:DD:EE:FF:EE:11";
		String out = Crypto_utils.hexStrtoDecStr(t);
		System.out.println(out);
		System.out.println("65656666676768686969707069694949");
		System.out.println(Crypto_utils.decStrtoHexStr("65656666676768686969707069694949"));
		String new_to_hex = "4949494949496565656565657070";
		System.out.println(Crypto_utils.decStrtoHexStr(new_to_hex));
	}

}
