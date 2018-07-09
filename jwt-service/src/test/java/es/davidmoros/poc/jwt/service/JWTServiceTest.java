package es.davidmoros.poc.jwt.service;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import es.davidmoros.poc.jwt.model.JWTAuthData;

public class JWTServiceTest {

	static Stream<JWTAuthData> jwtAuthDataProvider() {
		JWTAuthData jwtAuthData1 = new JWTAuthData();
		jwtAuthData1.setUsername("username");
		JWTAuthData jwtAuthData2 = new JWTAuthData("username");
		JWTAuthData jwtAuthData3 = new JWTAuthData("username", emptyList());
		JWTAuthData jwtAuthData4 = new JWTAuthData("username", Arrays.asList("role1", "role2"));
		
	    return Stream.of(jwtAuthData1, jwtAuthData2, jwtAuthData3, jwtAuthData4);
	}

	@Test
	public void generateJWT_nulls_1() {
		assertThrows(NullPointerException.class, () -> {
			JWTService.generateJWT((String)null);
		});
	}
	
	@Test
	public void generateJWT_nulls_2() {
		assertThrows(NullPointerException.class, () -> {
			JWTService.generateJWT(null, null);
		});
	}
	
	@Test
	public void generateJWT_nulls_3() {
		assertThrows(NullPointerException.class, () -> {
			JWTService.generateJWT(null, emptyList());
		});
	}

	@Test
	public void generateJWT_nulls_4() {
		assertThrows(NullPointerException.class, () -> {
			JWTService.generateJWT("", null);
		});
	}

	@Test
	public void generateJWT_nulls_5() {
		assertThrows(NullPointerException.class, () -> {
			JWTService.generateJWT((JWTAuthData)null);
		});
	}
	
	@Test
	public void generateJWT_1() {
		String jwt = JWTService.generateJWT("username");
		
        assertNotNull(jwt);
        assertFalse(jwt.isEmpty());
        assertTrue(jwt.indexOf(".", jwt.indexOf("."))!=-1);	
	}
	
	@Test
	public void generateJWT_2() {
		String jwt = JWTService.generateJWT("username", emptyList());
		
        assertNotNull(jwt);
        assertFalse(jwt.isEmpty());
        assertTrue(jwt.indexOf(".", jwt.indexOf("."))!=-1);	
	}

	@Test
	public void generateJWT_3() {
		String jwt = JWTService.generateJWT("username", Arrays.asList("role1", "role2"));
		
        assertNotNull(jwt);
        assertFalse(jwt.isEmpty());
        assertTrue(jwt.indexOf(".", jwt.indexOf("."))!=-1);	
	}
	
	@ParameterizedTest
	@MethodSource("jwtAuthDataProvider")
	public void generateJWT_4(JWTAuthData jwtAuthData) {
		String jwt = JWTService.generateJWT(jwtAuthData);
		
        assertNotNull(jwt);
        assertFalse(jwt.isEmpty());
        assertTrue(jwt.indexOf(".", jwt.indexOf("."))!=-1);		
	}
	
	@Test
	public void parseJWT_nulls_1() {
		assertThrows(NullPointerException.class, () -> {
			JWTService.parseJWT(null);
		});
	}
	
	@ParameterizedTest
	@MethodSource("jwtAuthDataProvider")
	public void parseJWT(JWTAuthData jwtAuthDataIn) {
		String jwt = JWTService.generateJWT(jwtAuthDataIn);
		JWTAuthData jwtAuthDataOut = JWTService.parseJWT(jwt);
		
        assertNotNull(jwtAuthDataOut);
        assertEquals(jwtAuthDataIn, jwtAuthDataOut);
        assertEquals(jwtAuthDataIn.getUsername(), jwtAuthDataOut.getUsername());
        assertEquals(jwtAuthDataIn.getRoles(), jwtAuthDataOut.getRoles());
	}	
}
