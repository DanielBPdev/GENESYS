package com.asopagos.rest.security.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.util.DesEncrypter;

public class PreguntasSeguridadUtil {
	
	public static String aplicarHashPregunta(String nombreUsuario,String pregunta){
		try {
			String salt=generarSalt(nombreUsuario);
			StringBuilder builder=new StringBuilder();
			builder.append(salt);
			builder.append(pregunta);
			return aplicarHash(builder.toString());
		} catch (Exception e) {
			throw new ParametroInvalidoExcepcion("No fue posible generar el hash de la respuesta");
		}
	}
	
	public static String obtenerPregunta(String nombreUsuario,String preguntaHash){
		try {
			String salt=generarSalt(nombreUsuario);
			String unhashed=DesEncrypter.getInstance().decrypt(preguntaHash);
			String pregunta=unhashed.replace(salt, "");
			return pregunta;
		} catch (Exception e) {
			throw new ParametroInvalidoExcepcion("No fue posible generar el hash de la respuesta");
		}
	}
	
	private static String generarSalt(String nombreUsuario) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		StringBuilder builder=new StringBuilder();
		builder.append(nombreUsuario);		
		return aplicarHash(builder.toString());
	}
	
	private static String aplicarHash(String cadena) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		return DesEncrypter.getInstance().encrypt(cadena);
	}
}
