/**
 * 
 */
package com.asopagos.usuarios.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author alopez
 *
 */
public class SecurePasswordGenerator {
	
	public static String generatePassword(int num){
		String nums=RandomStringUtils.randomNumeric(1);
		String lowLetter=RandomStringUtils.random(1,"abcdefghijklmnopqrstuvwxyz".toCharArray());
		String upLetter=RandomStringUtils.random(1,"ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray());	
		
		//Se eliminan de la lista los caracteres []// debido a que son la posible causa del error reportado en mantis 258230
		String symbols=RandomStringUtils.random(2,"!$%&({}-+*)=?_-<>|".toCharArray());
		
		String compl=RandomStringUtils.randomAlphanumeric(num-5);		
		String pass=lowLetter+nums+upLetter+compl+symbols;	
		return pass;
	}
}
