package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Map;

import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene el valor de los datos del
 * comunicado <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> Juan Diego Ocampo Q.</a>
 */
public class DatosComunicadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Contiene el valor de la variable ${periodosLiquidados} del comunicado */
	public static final String PERIODOS_LIQUIDADOS = "periodosLiquidados";

	/** Contiene el valor de la variable ${montoLiquidado} del comunicado */
	public static final String MONTO_LIQUIDADO = "montoLiquidado";

	/** Contiene el valor de la variable ${numeroDeTrabajadores} del comunicado */
	public static final String NUMERO_TRABAJADORES = "numeroDeTrabajadores";

	/** Contiene el valor de la variable ${numeroDeBeneficiarios} del comunicado */
	public static final String NUMERO_BENEFICIARIOS = "numeroDeBeneficiarios";

	/** Contiene el valor de la variable ${nombreFallecido} del comunicado */
	public static final String NOMBRE_FALLECIDO = "nombreFallecido";
	
	//----- VARIABLES COMUNICADOS 137 Y 138  POR FALLECIMIENTO---------
	/** Contiene el valor de la variable ${nombresAdminSubsidio} del comunicado */
	public static final String NOMBRE_ADMIN_SUBSIDIO = "nombresAdminSubsidio";
	
	/** Contiene el valor de la variable ${tipoIdentificacionAdminSubsidio} del comunicado */
	public static final String TIPO_ID_ADMIN_SUBSIDIO = "tipoIdentificacionAdminSubsidio";
	
	/** Contiene el valor de la variable ${noIdentificacionAdminSubsidio} del comunicado */
	public static final String NUM_ID_ADMIN_SUBSIDIO = "noIdentificacionAdminSubsidio";
	
	/** Contiene el valor de la variable ${nombreBeneficiarioOTrabajador} del comunicado */
	public static final String NOMBRE_BENEFICIARIO_O_TRABAJADOR= "nombreBeneficiarioOTrabajador";
	
	/** Contiene el valor de la variable ${causa} del comunicado */
	public static final String CAUSA= "causa";
	
	/** Contiene el valor de la variable ${nombresTrabajadorOPareja} del comunicado */
	public static final String NOMBRE_TRABAJADOR_O_CONYUGE= "nombresTrabajadorOPareja";
	
	/** Contiene el valor de la variable ${tipoIdentificacionTrabajadorOPareja} del comunicado */
	public static final String TIPO_ID_TRABAJADOR_O_PAREJA= "tipoIdentificacionTrabajadorOPareja";
	
	/** Contiene el valor de la variable ${noIdentificacionTrabajadorOPareja} del comunicado */
	public static final String NUM_ID_TRABAJADOR_O_PAREJA="noIdentificacionTrabajadorOPareja";
	
   //-----VARIABLE COMUNICADOS FALLECIMIENTO 54,55, 57 Y 58 -------------
    
    public static final String REPORTE_BENEFICIARIOS_PERIODOS = "reporteDeBeneficiarios";
    
    public static final String DIRECCION = "direccion";
    
    public static final String MUNICIPIO = "municipio";
    
    public static final String DEPARTAMENTO = "departamento";
    
    public static final String TELEFONO = "telefono";
	
	/**
	 * Identificador de la persona ya sea como empresa, trabajador o administrador
	 * del subsidio
	 */
	private Long idPersona;

	/** Contiene el correo al cual se le debe enviar el comunicado */
	private String destinatario;
	
	/** Contiene el valor de la etiqueta de la plantilla a usar */
	private EtiquetaPlantillaComunicadoEnum etiqueta;
	
	/** Contiene el correo al cual se le debe enviar el comunicado */
	private Map<String, String> variables;
	
	/**
	 * Indica si la persona destinatario autoriza el envio de comunicados
	 */
	private Boolean autorizaEnvio;
	
	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona
	 *            the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario
	 *            the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the variables
	 */
	public Map<String, String> getVariables() {
		return variables;
	}

	/**
	 * @param variables the variables to set
	 */
	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	/**
	 * @return the etiqueta
	 */
	public EtiquetaPlantillaComunicadoEnum getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(EtiquetaPlantillaComunicadoEnum etiqueta) {
		this.etiqueta = etiqueta;
	}

    /**
     * @return the autorizaEnvio
     */
    public Boolean getAutorizaEnvio() {
        return autorizaEnvio;
    }

    /**
     * @param autorizaEnvio the autorizaEnvio to set
     */
    public void setAutorizaEnvio(Boolean autorizaEnvio) {
        this.autorizaEnvio = autorizaEnvio;
    }
}
