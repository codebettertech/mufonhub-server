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
public class PBKDF2Encoder {
	
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
}
