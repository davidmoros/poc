package es.davidmoros.poc.jwt.service;

import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.Date;

import es.davidmoros.poc.jwt.model.JWTAuthData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;

public class JWTService {

	public static final String TOKEN_ISSUER = "davidmoros.es";
	public static final String TOKEN_SECRETKEY = "secretkey_1234";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";
	public static final String TOKEN_CLAIM_ROLES_KEY = "roles";
	public static final String TOKEN_HEADER_TYPE_KEY = "typ";
	public static final String TOKEN_HEADER_TYPE_VALUE = "JWT";
	
	public static final SignatureAlgorithm TOKEN_SIGNATUREALGORITHM = SignatureAlgorithm.HS512;
	
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
	
	/**
	 * Genera un token JWT que contiene la información pasada por parámetro
	 * @param username nombre de usuario
	 * @return token JWT generado
	 */
	public static String generateJWT(@NonNull String username) {
		return generateJWT(new JWTAuthData(username, emptyList()));
	}
	
	/**
	 * Genera un token JWT que contiene la información pasada por parámetro
	 * @param username nombre de usuario
	 * @param roles roles del usuario
	 * @return token JWT generado
	 */
	public static String generateJWT(@NonNull String username, @NonNull Collection<String> roles) {
		return generateJWT(new JWTAuthData(username, roles));
	}

	/**
	 * Genera un token JWT que contiene la información pasada por parámetro
	 * @param jwtAuthData {@link JWTAuthData} datos de autorización/autenticación del usuario
	 * @return token JWT generado
	 */
	public static String generateJWT(@NonNull JWTAuthData jwtAuthData) {
		
		String jwt = Jwts.builder()
				.setHeaderParam(TOKEN_HEADER_TYPE_KEY, TOKEN_HEADER_TYPE_VALUE)
				.setIssuer(TOKEN_ISSUER)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.setSubject(jwtAuthData.getUsername())
				.claim(TOKEN_CLAIM_ROLES_KEY, jwtAuthData.getRoles())
				.signWith(TOKEN_SIGNATUREALGORITHM, TOKEN_SECRETKEY)
				.compact();

		return jwt;
	}
	
	/**
	 * Parsea un token JWT y obtiene los datos de autorización/autenticación 
	 * @param jwt token JWT a parsear
	 * @return {@link JWTAuthData} datos parseados de autorización/autenticación del usuario
	 */
	public static JWTAuthData parseJWT (@NonNull String jwt) {
		Claims claims = Jwts.parser()
				.setSigningKey(TOKEN_SECRETKEY)
				.parseClaimsJws(jwt)
				.getBody();

		String username = claims.getSubject();
		Collection<String> roles = claims.get(TOKEN_CLAIM_ROLES_KEY, Collection.class);
		
		return new JWTAuthData(username, roles);
	}
}
