package es.davidmoros.poc.jwt.model;

import static java.util.Collections.emptyList;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Contiene los datos de autenticación/autorización necesarios de un usuario para generar un token JWT,
 * o bien para parsear la información del mismo
 * 
 * @author david
 * 
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class JWTAuthData {

	@NonNull
	private String username;
	@NonNull
	private Collection<String> roles = emptyList();
}
