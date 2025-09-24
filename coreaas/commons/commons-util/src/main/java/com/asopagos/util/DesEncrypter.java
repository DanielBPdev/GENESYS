package com.asopagos.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import co.com.heinsohn.lion.common.exception.LionCommonException;

/**
 * -----------------------------------------------------------------------------
 * The following example implements a class for encrypting and decrypting
 * strings using several Cipher algorithms. The class is created with a key and
 * can be used repeatedly to encrypt and decrypt strings using that key. Some of
 * the more popular algorithms are: Blowfish DES DESede PBEWithMD5AndDES
 * PBEWithMD5AndTripleDES TripleDES
 */

public class DesEncrypter {

	private Cipher ecipher;
	private Cipher dcipher;
	private static DesEncrypter instance;

	/**
	 * Log
	 */
	private transient Logger log = Logger.getLogger(DesEncrypter.class);

	/**
	 * Constructor used to create this object. Responsible for setting and
	 * initializing this object's encrypter and decrypter Chipher instances
	 * given a Secret Key and algorithm.
	 * 
	 * @param key
	 *            Secret Key used to initialize both the encrypter and decrypter
	 *            instances.
	 * @param algorithm
	 *            Which algorithm to use for creating the encrypter and
	 *            decrypter instances.
	 */
	DesEncrypter(SecretKey key, String algorithm) {
		try {
			ecipher = Cipher.getInstance(algorithm);
			dcipher = Cipher.getInstance(algorithm);
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchPaddingException e) {

			log.error(e);

		} catch (NoSuchAlgorithmException e) {

			log.error(e);
		} catch (InvalidKeyException e) {

			log.error(e);
		}
	}

	/**
	 * Constructor used to create this object. Responsible for setting and
	 * initializing this object's encrypter and decrypter Chipher instances
	 * given a Pass Phrase and algorithm.
	 * 
	 * @param passPhrase
	 *            Pass Phrase used to initialize both the encrypter and
	 *            decrypter instances.
	 */
	DesEncrypter(String passPhrase) {

		// 8-bytes Salt
		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3,
				(byte) 0x03 };

		// Iteration count
		int iterationCount = 19;

		try {

			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameters to the cipthers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (InvalidAlgorithmParameterException e) {
			log.error(e);
		} catch (InvalidKeySpecException e) {
			log.error(e);
		} catch (NoSuchPaddingException e) {
			log.error(e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
		} catch (InvalidKeyException e) {

			log.error(e);
		}
	}

	/**
	 * Takes a single String as an argument and returns an Encrypted version of
	 * that String.
	 * 
	 * @param str
	 *            String to be encrypted
	 * @return <code>String</code> Encrypted version of the provided String
	 */
	public String encrypt(String str) {

		log.debug("Inicio-encrypt");

		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			log.debug("Fin-encrypt");
			String encripter = new String(Base64.encodeBase64(enc));
			return encripter;

		} catch (BadPaddingException e) {
			log.error(e);
		} catch (IllegalBlockSizeException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * Takes a encrypted String as an argument, decrypts and returns the
	 * decrypted String.
	 * 
	 * @param str
	 *            Encrypted String to be decrypted
	 * @return <code>String</code> Decrypted version of the provided String
	 */
	public String decrypt(String str) {

		log.debug("Inicio-decrypt");

		try {

			// Decode base64 to get bytes
			byte[] dec = Base64.decodeBase64(str.getBytes());
			// Decrypt
			log.debug("Inicio-dcipher.doFinal dec: "+dec);
			byte[] utf8 = dcipher.doFinal(dec);

			log.debug("Fin-decrypt utf8: "+utf8);
			// Decode using utf-8
			return new String(utf8, "UTF8");

		} catch (BadPaddingException e) {
			log.error(e);
		} catch (IllegalBlockSizeException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return null;
	}
 
	/**
	 * The following method is used for testing the String Encrypter class. This
	 * method is responsible for encrypting and decrypting a sample String using
	 * several symmetric temporary Secret Keys.
	 */
	@SuppressWarnings("unused")
	public static void testUsingSecretKey() {

		try {

			String secretString = "Attack at dawn!";

			// Generate a temporary key for this example. In practice, you would
			// save this key somewhere. Keep in mind that you can also use a
			// Pass Phrase.
			SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
			SecretKey blowfishKey = KeyGenerator.getInstance("Blowfish").generateKey();
			SecretKey desedeKey = KeyGenerator.getInstance("DESede").generateKey();

			// Create encrypter/decrypter class
			DesEncrypter desEncrypter = new DesEncrypter(desKey, desKey.getAlgorithm());
			DesEncrypter blowfishEncrypter = new DesEncrypter(blowfishKey, blowfishKey.getAlgorithm());
			DesEncrypter desedeEncrypter = new DesEncrypter(desedeKey, desedeKey.getAlgorithm());

			// String str = new
			// sun.misc.BASE64Encoder().encode(desedeKey.getEncoded());

			// Encrypt the string
			String desEncrypted = desEncrypter.encrypt(secretString);
			String blowfishEncrypted = blowfishEncrypter.encrypt(secretString);
			String desedeEncrypted = desedeEncrypter.encrypt(secretString);

			// Decrypt the string
			String desDecrypted = desEncrypter.decrypt(desEncrypted);
			String blowfishDecrypted = blowfishEncrypter.decrypt(blowfishEncrypted);
			String desedeDecrypted = desedeEncrypter.decrypt(desedeEncrypted);

		} catch (NoSuchAlgorithmException e) {

		}
	}

	/**
	 * The following method is used for testing the String Encrypter class. This
	 * method is responsible for encrypting and decrypting a sample String using
	 * using a Pass Phrase.
	 */
	@SuppressWarnings("unused")
	public static void testUsingPassPhrase() {

		String secretString = "Attack at dawn!";
		String passPhrase = "My Pass Phrase";

		// Create encrypter/decrypter class
		DesEncrypter desEncrypter = new DesEncrypter(passPhrase);

		// Encrypt the string
		String desEncrypted = desEncrypter.encrypt(secretString);

		// Decrypt the string
		String desDecrypted = desEncrypter.decrypt(desEncrypted);

	}

	/**
	 * Método encargado de obtener una instancia del objeto de tipo DesEncryter
	 * con respecto a un llave almacenada en un archivo de propiedades.
	 * 
	 * @return Una instancia de la clase DesEncrypter.
	 * @throws LionCommonException
	 *             Si se presenta alguna excepción o error durante el proceso.
	 */
	public static DesEncrypter getInstance() {
		if (instance == null) {
			String key = (String) CacheManager.getConstante(ConstantesSistemaConstants.DESENCRYPTER_KEY);
			instance = new DesEncrypter(key);
		}
		return instance;
	}

	/**
	 * Sole entry point to the class and application used for testing the String
	 * Encrypter class.
	 * 
	 * @param args
	 *            Array of String arguments.
	 */
	public static void main(String[] args) {
		testUsingSecretKey();
		testUsingPassPhrase();

		//DesEncrypter d = new DesEncrypter("euNuWPuienBrq2t/lEnpCw3f3IpFuQRA");
		//String desEncrypted = d.encrypt("Asdf1234$");
		//System.out.println(desEncrypted);
	}

}
