package com.asopagos.entidades.pagadoras.dto;

/**
 * La siguiente estructura de los registros está dada para el convenio de la caja de compensación “Confa” y “Colpensiones”.
 * Estructura Solicitud de Alta
 * 
 * 
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class EstructuraSolicitudAltaRetiroDTO {

    private String numeroAfiliacion;

    private String nroDocumento;

    private String nitDeEntidad;

    private String tipoNovedad;

    /**
     * @return the numeroAfiliacion
     */
    public String getNumeroAfiliacion() {
        return numeroAfiliacion;
    }

    /**
     * @param numeroAfiliacion
     *        the numeroAfiliacion to set
     */
    public void setNumeroAfiliacion(String numeroAfiliacion) {
        this.numeroAfiliacion = numeroAfiliacion;
    }

    /**
     * @return the tipoNovedad
     */
    public String getTipoNovedad() {
        return tipoNovedad;
    }

    /**
     * @param tipoNovedad
     *        the tipoNovedad to set
     */
    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

	/**
	 * @return the nroDocumento
	 */
	public String getNroDocumento() {
		return nroDocumento;
	}

	/**
	 * @param nroDocumento the nroDocumento to set
	 */
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	/**
	 * @return the nitDeEntidad
	 */
	public String getNitDeEntidad() {
		return nitDeEntidad;
	}

	/**
	 * @param nitDeEntidad the nitDeEntidad to set
	 */
	public void setNitDeEntidad(String nitDeEntidad) {
		this.nitDeEntidad = nitDeEntidad;
	}

}
