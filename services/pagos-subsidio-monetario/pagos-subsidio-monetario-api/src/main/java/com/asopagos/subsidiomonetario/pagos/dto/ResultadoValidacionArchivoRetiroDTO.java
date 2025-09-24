package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoRetiroTercerPagadorEnum;

/**
 * <b>Descripción:</b> DTO que contiene el resultado de la validación del cargue del archivo
 * de retiro del tercero pagador o comercial.<b>Historia de Usuario:</b> HU-31-205
 * 
 * author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class ResultadoValidacionArchivoRetiroDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4764738558756766928L;

    /**
     * Identificador de la transacción de registro del archivo de retio
     * para posteriormente buscar una inconsistencia, si la carga no fue exitosa.
     */
    private String idArchivoRetiroTerceroPagador;

    /**
     * Nombre de archivo del retiro
     */
    private String nombreArchivoRetiro;

    /**
     * Estado de la validación de la carga del retiro por si fue
     * exitosa o fallo.
     */
    private EstadoArchivoRetiroTercerPagadorEnum estadoValidacion;

    /**
     * Identificador del archivo de consumo que se cargan en Genesys generados por parte del servicio ANIBOL
     */
    private Long idArchivoConsumoAnibol;
    

    /**
     * Nombre de archivo del retiro
     */
    private String mensaje;
    
    /**
     * Nombre de archivo del retiro
     */
    private Long idConvenio;

    /**
     * @return the idArchivoRetiroTerceroPagador
     */
    public String getIdArchivoRetiroTerceroPagador() {
        return idArchivoRetiroTerceroPagador; 
    }

    /**
     * @param idArchivoRetiroTerceroPagador
     *        the idArchivoRetiroTerceroPagador to set
     */
    public void setIdArchivoRetiroTerceroPagador(String idArchivoRetiroTerceroPagador) {
        this.idArchivoRetiroTerceroPagador = idArchivoRetiroTerceroPagador;
    }

    /**
     * @return the nombreArchivoRetiro
     */
    public String getNombreArchivoRetiro() {
        return nombreArchivoRetiro;
    }

    /**
     * @param nombreArchivoRetiro
     *        the nombreArchivoRetiro to set
     */
    public void setNombreArchivoRetiro(String nombreArchivoRetiro) {
        this.nombreArchivoRetiro = nombreArchivoRetiro;
    }

    /**
     * @return the estadoValidacion
     */
    public EstadoArchivoRetiroTercerPagadorEnum getEstadoValidacion() {
        return estadoValidacion;
    }

    /**
     * @param estadoValidacion
     *        the estadoValidacion to set
     */
    public void setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum estadoValidacion) {
        this.estadoValidacion = estadoValidacion;
    }

    /**
     * @return the idArchivoConsumoAnibol
     */
    public Long getIdArchivoConsumoAnibol() {
        return idArchivoConsumoAnibol;
    }

    /**
     * @param idArchivoConsumoAnibol
     *        the idArchivoConsumoAnibol to set
     */
    public void setIdArchivoConsumoAnibol(Long idArchivoConsumoAnibol) {
        this.idArchivoConsumoAnibol = idArchivoConsumoAnibol;
    }

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}
    
    

}
