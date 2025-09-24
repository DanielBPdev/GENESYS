package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoRetiroTercerPagadorEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoTerceroPagadorEfectivo;

/**
 * <b>Descripción:</b> DTO que contiene el resultado de la validación del cargue del archivo
 * de retiro del tercero pagador o comercial.<b>Historia de Usuario:</b> HU-31-205
 * 
 * author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class ResultadoValidacionArchivoRetiroTerceroPagDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4764738558756766928L;

    /**
     * Nombre de archivo del retiro
     */
    private String nombreArchivoRetiro;

    /**
     * Estado de la validación de la carga del retiro por si fue
     * exitosa o fallo.
     */
    private EstadoArchivoConsumoTerceroPagadorEfectivo estadoValidacion;

    /**
     * Identificador del archivo de consumo que se cargan en Genesys generados por parte del servicio ANIBOL
     */
    private Long idArchivoRetiroTerceroPagadorEfectivo;
    

    /**
     * Nombre de archivo del retiro
     */
    private String mensaje;
    
    /**
     * Nombre de archivo del retiro
     */
    private Long idConvenio;
    
    /**
     * id del archivo ECM
     */
    private String idArchivoRetiroTerceroPagador;

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
    public EstadoArchivoConsumoTerceroPagadorEfectivo getEstadoValidacion() {
        return estadoValidacion;
    }

    /**
     * @param estadoValidacion
     *        the estadoValidacion to set
     */
    public void setEstadoValidacion(EstadoArchivoConsumoTerceroPagadorEfectivo estadoValidacion) {
        this.estadoValidacion = estadoValidacion;
    }

    /**
     * @return the idArchivoConsumoAnibol
     */
    public Long getIdArchivoRetiroTerceroPagadorEfectivo() {
        return idArchivoRetiroTerceroPagadorEfectivo;
    }

    /**
     * @param idArchivoConsumoAnibol
     *        the idArchivoConsumoAnibol to set
     */
    public void setIdArchivoRetiroTerceroPagadorEfectivo(Long idArchivoRetiroTerceroPagadorEfectivo) {
        this.idArchivoRetiroTerceroPagadorEfectivo = idArchivoRetiroTerceroPagadorEfectivo;
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

	public String getIdArchivoRetiroTerceroPagador() {
		return idArchivoRetiroTerceroPagador;
	}

	public void setIdArchivoRetiroTerceroPagador(String idArchivoRetiroTerceroPagador) {
		this.idArchivoRetiroTerceroPagador = idArchivoRetiroTerceroPagador;
	}
    
    

}
