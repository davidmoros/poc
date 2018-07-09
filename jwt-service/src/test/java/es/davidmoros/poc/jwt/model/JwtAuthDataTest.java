package es.davidmoros.poc.jwt.model;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

public class JwtAuthDataTest {

	@Test
	public void contructor_2_nulls_1() {
		assertThrows(NullPointerException.class, () -> {
			new JWTAuthData(null);
		});
	}
	
	@Test
	public void contructor_3_nulls_1() {
		assertThrows(NullPointerException.class, () -> {
			new JWTAuthData(null, null);
		});
	}	
	
	@Test
	public void contructor_3_nulls_2() {
		assertThrows(NullPointerException.class, () -> {
			new JWTAuthData(null, emptyList());
		});
	}
	
	@Test
	public void contructor_3_nulls_3() {
		assertThrows(NullPointerException.class, () -> {
			new JWTAuthData("", null);
		});
	}	
	
	@Test
	public void contructor_1() {
		JWTAuthData jwtAuthData = new JWTAuthData();
		
		assertNull(jwtAuthData.getUsername());
		assertNotNull(jwtAuthData.getRoles());
		assertTrue(jwtAuthData.getRoles().isEmpty());
	}	
	
	@Test
	public void contructor_2() {
		JWTAuthData jwtAuthData = new JWTAuthData("name");
		
		assertNotNull(jwtAuthData.getUsername());
		assertNotNull(jwtAuthData.getRoles());
		assertTrue(jwtAuthData.getRoles().isEmpty());
	}	

	@Test
	public void contructor_3() {
		JWTAuthData jwtAuthData = new JWTAuthData("username", Arrays.asList("role1", "role2"));
		
		assertNotNull(jwtAuthData.getUsername());
		assertNotNull(jwtAuthData.getRoles());
		assertFalse(jwtAuthData.getRoles().isEmpty());
	}
	
	@Test
	public void gettersSetters() {
		JWTAuthData jwtAuthData = new JWTAuthData();
		
		assertThrows(NullPointerException.class, () -> {
			jwtAuthData.setUsername(null);
		});
		assertThrows(NullPointerException.class, () -> {
			jwtAuthData.setRoles(null);
		});
		
		String username = "username";
		Collection<String> roles = Arrays.asList("role1", "role2"); 
		
		jwtAuthData.setUsername(username);
		jwtAuthData.setRoles(roles);
		
		assertNotNull(jwtAuthData.getUsername());
		assertNotNull(jwtAuthData.getRoles());
		assertFalse(jwtAuthData.getRoles().isEmpty());
		
		assertEquals(username, jwtAuthData.getUsername());
		assertEquals(roles, jwtAuthData.getRoles());
	}	
}
