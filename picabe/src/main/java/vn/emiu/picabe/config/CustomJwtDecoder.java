package vn.emiu.picabe.config;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import vn.emiu.picabe.entity.User;
import vn.emiu.picabe.entity.enums.RoleType;
import vn.emiu.picabe.exception.UnauthorizedException;

@Slf4j
@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${application.jwt.secret}")
    private String signerKey;
    private static final long EXPIRATION_TIME = 86400000;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }

    public String generateToken(User user, boolean isAccessToken) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        long expiryTime = isAccessToken ? EXPIRATION_TIME : EXPIRATION_TIME;
        Date issueTime = new Date();
        Date expiryDate = new Date(Instant.ofEpochMilli(issueTime.getTime())
                .plus(expiryTime, ChronoUnit.SECONDS)
                .toEpochMilli());

        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(issueTime)
                .expirationTime(expiryDate)
                .jwtID(UUID.randomUUID().toString())
                .claim("accountId", user.getId());

// Nếu role là ADMIN thì thêm scope
        if (user.getRole() == RoleType.ADMIN) {
            claimsBuilder.claim("scope", "ADMIN");
        }

        JWTClaimsSet jwtClaimsSet = claimsBuilder.build();


        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token {0}", e);
            throw new UnauthorizedException("Unauthorized");
        }
    }


}