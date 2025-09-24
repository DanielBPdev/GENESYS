package com.asopagos.validaciones.fovis.ejb;

import java.util.LinkedHashMap;
import java.util.Map;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;

/**
 * <b>Descripción:</b> Clase que representa la fábrica de validaciones
 * <b>Historia de Usuario: </b>TRA
 * 
 * @author Jorge Leonardo Camargo Cuervo
 *         <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 *         </a>
 */
public class ValidadorFovisCoreFactory {

	/**
	 * Mapa con las referencias de los validadores definidos
	 */
	private Map<String, ValidadorFovisCore> validadores;
	/**
	 * Instancia Singleton de la clase
	 */
	private static ValidadorFovisCoreFactory instance;

	/**
	 * Método que obtiene la instancia singleton de la clase
	 * 
	 * @return Instancia Singleton
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static ValidadorFovisCoreFactory getInstance()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (instance == null) {
			instance = new ValidadorFovisCoreFactory();
		}
		return instance;
	}

	/**
	 * Constructor de la clase
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private ValidadorFovisCoreFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		initMapValidadores();
	}

	/**
	 * Método que inicializa el mapa de validadores
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void initMapValidadores() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		validadores = new LinkedHashMap<>();
		for (ValidacionCoreEnum defValidador : ValidacionCoreEnum.values()) {
			String claseDisponible = defValidador.getPrimeraDefinicionDisponible();
			System.out.println("clase " + claseDisponible);
			if (claseDisponible != null) {
				try {
					Class<ValidadorFovisCore> clazz = (Class<ValidadorFovisCore>) Class.forName(claseDisponible);
					ValidadorFovisCore validador = clazz.newInstance();
					validadores.put(defValidador.getCodigo(), validador);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					System.err.println("No se pudo cargar el validador: " + claseDisponible);
				}
			} else {
				System.err.println("No se encontró ninguna clase válida para: " + defValidador.getCodigo());
			}
		}
	}

	/**
	 * Método que consulta una validación específica
	 * 
	 * @param codigo
	 *            Es el código de la validación a buscar
	 * @return Objeto ValidadorCore con la implementación del validador
	 */
	public ValidadorFovisCore getValidador(String codigo) {
		return validadores.getOrDefault(codigo, null);
	}

}
