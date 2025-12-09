package io.codebetter.mufonhub.service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import io.codebetter.mufonhub.entity.User;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author ard333
 */
@RequestScoped
public class TokenService {

    public static String generateToken(User user, Set<String> roles, Long duration, String issuer) throws Exception {
        String privateKeyLocation = "/privatekey.pem";
        PrivateKey privateKey = readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();

        Set<String> groups = new HashSet<>();
        for (String role : roles) groups.add(role.toString());

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject("MufonHub");
        claimsBuilder.upn("username");
        claimsBuilder.audience("https://mufonhub.org");
        claimsBuilder.claim("email",user.getUsername());
        claimsBuilder.claim("username",user.getUsername());
        claimsBuilder.claim("firstName",user.getFirstName());
        claimsBuilder.claim("lastName",user.getLastName());
        claimsBuilder.claim("duration",duration);
        claimsBuilder.claim("groups",groups);
        claimsBuilder.expiresAt(duration);
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresAt(currentTimeInSecs + duration);
        claimsBuilder.groups(groups);

        return claimsBuilder.jws().signatureKeyId(privateKeyLocation).sign(privateKey);
    }

    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try (InputStream contentIS = TokenService.class.getResourceAsStream(pemResName)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
        }
    }

    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    public static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}