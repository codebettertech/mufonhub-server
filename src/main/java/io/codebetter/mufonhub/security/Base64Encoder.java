package io.codebetter.mufonhub.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author ard333
 */
@RequestScoped
public class Base64Encoder {

    private static final byte[] map = new byte[128];

    @ConfigProperty(name = "io.codebetter.mufonhub.jwt.password.secret")  private String secret;
	@ConfigProperty(name = "io.codebetter.mufonhub.jwt.password.iteration")  private Integer iteration;
	@ConfigProperty(name = "io.codebetter.mufonhub.jwt.password.keylength")  private Integer keylength;

	/**
	 * More info (https://www.owasp.org/index.php/Hashing_Java) 404 :(
	 * @param cs password
	 * @return encoded password
	 */
	public String encode(CharSequence cs) {
		try {
			byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
											.generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
											.getEncoded();
			return Base64.getEncoder().encodeToString(result);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded input data.
     *
     * @param s A Base64 String to be decoded.
     * @return An array containing the decoded data bytes.
     * @throws IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static byte[] decode(String s) {
        return decode(s.toCharArray());
    }

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded input data.
     *
     * @param in A character array containing the Base64 encoded data.
     * @return An array containing the decoded data bytes.
     * @throws IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static byte[] decode(char[] in) {
        return decode(in, 0, in.length);
    }

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded input data.
     *
     * @param in   A character array containing the Base64 encoded data.
     * @param iOff Offset of the first character in <code>in</code> to be processed.
     * @param iLen Number of characters to process in <code>in</code>, starting at <code>iOff</code>.
     * @return An array containing the decoded data bytes.
     * @throws IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static byte[] decode(char[] in, int iOff, int iLen) {
        if (iLen % 4 != 0)
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        while (iLen > 0 && in[iOff + iLen - 1] == '=') iLen--;
        int oLen = (iLen * 3) / 4;
        byte[] out = new byte[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;
        while (ip < iEnd) {
            int i0 = in[ip++];
            int i1 = in[ip++];
            int i2 = ip < iEnd ? in[ip++] : 'A';
            int i3 = ip < iEnd ? in[ip++] : 'A';
            if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            int b0 = map[i0];
            int b1 = map[i1];
            int b2 = map[i2];
            int b3 = map[i3];
            if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            int o0 = (b0 << 2) | (b1 >>> 4);
            int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
            int o2 = ((b2 & 3) << 6) | b3;
            out[op++] = (byte) o0;
            if (op < oLen) out[op++] = (byte) o1;
            if (op < oLen) out[op++] = (byte) o2;
        }
        return out;
    }
}
