package com.asopagos.util;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;

/**
 * <b>Descripción:</b> Clase que contiene métodos utilitarios para trabajar con
 * la entidad Persona <b>Historia de Usuario:</b> 133, 109
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class PersonasUtils {

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(PersonasUtils.class);

	/**
	 * Método que permite obtener el nombre o la razón social según el tipo de
	 * documento
	 * 
	 * @param persona
	 *            instancia de la persona
	 * @param tipoDocumento
	 *            tipo de documento de la persona
	 * 
	 * @param numeroDocumento
	 *            nro de documento de la persona
	 * 
	 * @return nombre o razón social según el caso, nombre si tipo doc Cédula
	 *         ciudadanía de lo contrario razón social
	 */
	public static String obtenerNombreORazonSocial(Persona persona) {

		String nombreORazonSocial = "";
		if (persona != null) {
			try {
				if (persona.getTipoIdentificacion() != null
						&& persona.getTipoIdentificacion().equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {

					if (persona.getPrimerNombre() != null && persona.getPrimerApellido() != null) {
						nombreORazonSocial = persona.getPrimerNombre()
								.concat(persona.getSegundoNombre() != null
										&& !persona.getSegundoNombre().trim().equals("")
												? " " + persona.getSegundoNombre() : "")
								.concat(" " + persona.getPrimerApellido())
								.concat(persona.getSegundoApellido() != null
										&& !persona.getSegundoApellido().trim().equals("")
												? " " + persona.getSegundoApellido() : "");
					}
					return nombreORazonSocial;
				} else {
					return persona.getRazonSocial();
				}
			} catch (NullPointerException e) {
				return persona.getRazonSocial();
			}
		} else {
			logger.debug("Finaliza obtenerNombreORazonSocial(Persona, TipoIdentificacionEnum, String)");
			logger.debug("Se debe ingresar persona");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/**
	 * Método encargado de obtener el nombre la persona si los tiene y si no
	 * devuelve la razon social
	 * 
	 * @param persona,
	 *            persona a concatenar los datos que conforman el nombre
	 * @return retorna el nombre de la persona
	 */
	public static String obtenerNombrePersona(Persona persona) {
		String nombrePersona = "";
		if (persona != null) {
			try {
				nombrePersona = persona.getPrimerNombre()
						.concat(persona.getSegundoNombre() != null && !persona.getSegundoNombre().trim().equals("")
								? " " + persona.getSegundoNombre() : "")
						.concat(" " + persona.getPrimerApellido())
						.concat(persona.getSegundoApellido() != null && !persona.getSegundoApellido().trim().equals("")
								? " " + persona.getSegundoApellido() : "");

				if (!nombrePersona.trim().equals("") && nombrePersona != null) {
					return nombrePersona;
				} else {
					return persona.getRazonSocial();
				}
			} catch (NullPointerException e) {
				return persona.getRazonSocial();
			}
		} else {
			logger.debug("Finaliza obtenerNombreORazonSocial(Persona, TipoIdentificacionEnum, String)");
			logger.debug("Se debe ingresar persona");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/**
	 * Método que lista la clasificacion para afiliado
	 * 
	 * @return List con la clasificacion
	 */
	public static List<ClasificacionEnum> ListarClasificacionAfiliado() {
		List<ClasificacionEnum> trabajador = new ArrayList<ClasificacionEnum>();
		trabajador.add(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO);
		trabajador.add(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO);
		trabajador.add(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
		trabajador.add(ClasificacionEnum.FIDELIDAD_25_ANIOS);
		trabajador.add(ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
		trabajador.add(ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
		trabajador.add(ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
		trabajador.add(ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO);
		trabajador.add(ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
		//trabajador.add(ClasificacionEnum.PENSION_FAMILIAR);

		return trabajador;
	}

	/**
	 * Método que lista la clasificacion para beneficiario
	 * 
	 * @return List con la clasificacion
	 */
	public static List<ClasificacionEnum> ListarClasificacionBeneficiario() {
		List<ClasificacionEnum> beneficiario = new ArrayList<ClasificacionEnum>();
		beneficiario.add(ClasificacionEnum.CONYUGE);
		beneficiario.add(ClasificacionEnum.PADRE);
		beneficiario.add(ClasificacionEnum.MADRE);
		beneficiario.add(ClasificacionEnum.HIJO_BIOLOGICO);
		beneficiario.add(ClasificacionEnum.HIJO_ADOPTIVO);
		beneficiario.add(ClasificacionEnum.HIJASTRO);
		beneficiario.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
		beneficiario.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);

		return beneficiario;
	}
	
    /**
     * Método encargado de obtener el nombre la persona si los tiene y si no
     * devuelve la razon social
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param persona,
     *            persona a concatenar los datos que conforman el nombre
     * @return retorna el nombre de la persona
     */
    public static String obtenerNombrePersona(PersonaModeloDTO persona) {
        String [] nombrePersona = {persona.getPrimerNombre(),persona.getSegundoNombre(),persona.getPrimerApellido(),persona.getSegundoApellido()};
        String nombreCompleto = "";
        String nombreApellido = "";
        for (int i = 0; i<nombrePersona.length;i++) {
            nombreApellido = nombrePersona[i];
            if(nombreApellido != null && !nombreApellido.isEmpty()) {
                nombreCompleto = nombreCompleto + nombreApellido + " ";
            }
        }
        if (nombreCompleto.equals("")) {
            return persona.getRazonSocial() != null ? persona.getRazonSocial() : nombreCompleto;
        }
        return nombreCompleto.charAt(nombreCompleto.length()-1) == ' ' ? nombreCompleto.substring(0, nombreCompleto.length()-1) : nombreCompleto;
    }

}
