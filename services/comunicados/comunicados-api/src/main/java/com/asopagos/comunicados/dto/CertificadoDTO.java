package com.asopagos.comunicados.dto;

import java.util.Date;
import com.asopagos.entidades.ccf.personas.Certificado;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoCertificadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * 
 * <b>Descripcion:</b> DTO que contiene los datos del certificado.<br/>
 * <b>Módulo:</b> Asopagos - Vistas 360 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class CertificadoDTO {

    /**
     * Código identificador de llave primaria del certificado
     */
    private Long idCertificado;
    /**
     * Representa el tipo de certificado generado
     */
    private TipoCertificadoEnum tipoCertificado;
    /**
     * Indica la fecha en la que se generó el certificado
     */
    private Long fechaGeneracion;
    /**
     * Indica la caja de compensación o entidad a la cual va dirigido el certificado
     */
    private String dirigidoA;
    /**
     * Es el identificador de la persona a la cual se le genera el certificado
     */
    private Long idPersona;
    /**
     * Indica si el certificado fue generado para la persona como empleador
     */
    private Boolean generadoComoEmpleador;
    /**
     * Indica el tipo solicitud (no especificado a que se refiere en la historia)
     */
    private String tipoSolicitud;
    /**
     * Indica el identificador del comunicado enviado
     */
    private Long idComunicado;
    /**
     * Año, aplica para cuando el tipo de certificado de aportes por año
     */
    private Short anio;
    /**
     * Indica el tipo de afiliado con el cual genera el certificado (si no hay valor es porque es como empleador)
     */
    private TipoAfiliadoEnum tipoAfiliado;
    /**
     * Indica el identificador del empleador para cuando el tipo de afiliado es dependiente
     */
    private Long idEmpleador;
    
	/**
     * Indica la plantilla del comunicado que se genero
     */
    private EtiquetaPlantillaComunicadoEnum etiqueta;
    
    /**
     * Indica el identificado del archivo de almacenamiento del certificado
     */
    private String identificadorArchivoCertificado;
    
    /**
     * Indica el email del destinatario
     */
    private String correoDestinatario;


	public CertificadoDTO() {

    }

    public CertificadoDTO(Long idPersona, TipoCertificadoEnum tipoCertificadoEnum, Boolean empleador, String dirigidoA, Short anio,
            TipoAfiliadoEnum tipoAfiliado, Long idEmpleador) {
        this.setGeneradoComoEmpleador(empleador);
        this.setDirigidoA(dirigidoA);
        this.setIdPersona(idPersona);
        this.setTipoCertificado(tipoCertificadoEnum);
        this.setAnio(anio);
        this.setFechaGeneracion((new Date()).getTime());
        this.setTipoAfiliado(tipoAfiliado);
        this.setIdEmpleador(idEmpleador);
    }

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase.
     */
    public CertificadoDTO(Certificado certificado) {
        copyEntityToDTO(certificado);
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * 
     * @return Entity Legalizacion
     */
    public Certificado convertToEntity() {
        Certificado certificado = new Certificado();
        certificado.setIdCertificado(this.getIdCertificado());
        certificado.setFechaGeneracion(this.getFechaGeneracion() != null ? new Date(this.getFechaGeneracion()) : null);
        certificado.setDirigidoA(this.getDirigidoA());
        certificado.setGeneradoComoEmpleador(this.getGeneradoComoEmpleador());
        certificado.setIdComunicado(this.getIdComunicado());
        certificado.setIdPersona(this.getIdPersona());
        certificado.setTipoCertificado(this.getTipoCertificado());
        certificado.setTipoSolicitud(this.getTipoSolicitud());
        certificado.setAnio(this.getAnio());
        certificado.setTipoAfiliado(this.getTipoAfiliado());
        certificado.setIdEmpleador(this.getIdEmpleador());
        return certificado;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro.
     * 
     * @param certificado
     * @return El Entity con las propiedades modificadas.
     */
    public Certificado copyDTOToEntity(Certificado certificado) {
        if (this.getIdCertificado() != null) {
            certificado.setIdCertificado(this.getIdCertificado());
        }
        if (this.getFechaGeneracion() != null) {
            certificado.setFechaGeneracion(new Date(this.getFechaGeneracion()));
        }
        if (this.getGeneradoComoEmpleador() != null) {
            certificado.setGeneradoComoEmpleador(this.getGeneradoComoEmpleador());
        }
        if (this.getIdComunicado() != null) {
            certificado.setIdComunicado(this.getIdComunicado());
        }
        if (this.getDirigidoA() != null) {
            certificado.setDirigidoA(this.getDirigidoA());
        }
        if (this.getIdPersona() != null) {
            certificado.setIdPersona(this.getIdPersona());
        }
        if (this.getTipoCertificado() != null) {
            certificado.setTipoCertificado(this.getTipoCertificado());
        }
        if (this.getTipoSolicitud() != null) {
            certificado.setTipoSolicitud(this.getTipoSolicitud());
        }
        if (this.getAnio() != null) {
            certificado.setAnio(this.getAnio());
        }
        if (this.getTipoAfiliado() != null) {
            certificado.setTipoAfiliado(this.getTipoAfiliado());
        }
        if (this.getIdEmpleador() != null) {
            certificado.setIdEmpleador(this.getIdEmpleador());
        }
        return certificado;
    }

    /**
     * Copia las propiedades del entity que llega por parámetro al actual DTO.
     * 
     * @param certificado
     */
    public void copyEntityToDTO(Certificado certificado) {
        if (certificado.getIdCertificado() != null) {
            this.setIdCertificado(certificado.getIdCertificado());
        }
        if (certificado.getFechaGeneracion() != null) {
            this.setFechaGeneracion(certificado.getFechaGeneracion().getTime());
        }
        if (certificado.getGeneradoComoEmpleador() != null) {
            this.setGeneradoComoEmpleador(certificado.getGeneradoComoEmpleador());
        }
        if (certificado.getIdComunicado() != null) {
            this.setIdComunicado(certificado.getIdComunicado());
        }
        if (certificado.getDirigidoA() != null) {
            this.setDirigidoA(certificado.getDirigidoA());
        }
        if (certificado.getIdPersona() != null) {
            this.setIdPersona(certificado.getIdPersona());
        }
        if (certificado.getTipoCertificado() != null) {
            this.setTipoCertificado(certificado.getTipoCertificado());
        }
        if (certificado.getTipoSolicitud() != null) {
            this.setTipoSolicitud(certificado.getTipoSolicitud());
        }
        if (certificado.getAnio() != null) {
            this.setAnio(certificado.getAnio());
        }
        if (certificado.getTipoAfiliado() != null) {
            this.setTipoAfiliado(certificado.getTipoAfiliado());
        }
        if (certificado.getIdEmpleador() != null) {
            this.setIdEmpleador(certificado.getIdEmpleador());
        }
    }

    /**
     * @return the idCertificado
     */
    public Long getIdCertificado() {
        return idCertificado;
    }

    /**
     * @param idCertificado
     *        the idCertificado to set
     */
    public void setIdCertificado(Long idCertificado) {
        this.idCertificado = idCertificado;
    }

    /**
     * @return the tipoCertificado
     */
    public TipoCertificadoEnum getTipoCertificado() {
        return tipoCertificado;
    }

    /**
     * @param tipoCertificado
     *        the tipoCertificado to set
     */
    public void setTipoCertificado(TipoCertificadoEnum tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    /**
     * @return the fechaGeneracion
     */
    public Long getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * @param fechaGeneracion
     *        the fechaGeneracion to set
     */
    public void setFechaGeneracion(Long fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    /**
     * @return the dirigidoA
     */
    public String getDirigidoA() {
        return dirigidoA;
    }

    /**
     * @param dirigidoA
     *        the dirigidoA to set
     */
    public void setDirigidoA(String dirigidoA) {
        this.dirigidoA = dirigidoA;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona
     *        the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the generadoComoEmpleador
     */
    public Boolean getGeneradoComoEmpleador() {
        return generadoComoEmpleador;
    }

    /**
     * @param generadoComoEmpleador
     *        the generadoComoEmpleador to set
     */
    public void setGeneradoComoEmpleador(Boolean generadoComoEmpleador) {
        this.generadoComoEmpleador = generadoComoEmpleador;
    }

    /**
     * @return the tipoSolicitud
     */
    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud
     *        the tipoSolicitud to set
     */
    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the idComunicado
     */
    public Long getIdComunicado() {
        return idComunicado;
    }

    /**
     * @param idComunicado
     *        the idComunicado to set
     */
    public void setIdComunicado(Long idComunicado) {
        this.idComunicado = idComunicado;
    }

    /**
     * @return the anio
     */
    public Short getAnio() {
        return anio;
    }

    /**
     * @param anio
     *        the anio to set
     */
    public void setAnio(Short anio) {
        this.anio = anio;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado
     *        the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador
     *        the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }
    
    /**
     * 
     * @return
     */
    public EtiquetaPlantillaComunicadoEnum getEtiqueta() {
		return etiqueta;
	}

    /**
     * 
     * @param etiqueta
     */
	public void setEtiqueta(EtiquetaPlantillaComunicadoEnum etiqueta) {
		this.etiqueta = etiqueta;
	}
	
    public String getIdentificadorArchivoCertificado() {
		return identificadorArchivoCertificado;
	}
    /**
     * 
     * @param identificadorArchivoCertificado
     */
	public void setIdentificadorArchivoCertificado(String identificadorArchivoCertificado) {
		this.identificadorArchivoCertificado = identificadorArchivoCertificado;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCorreoDestinatario() {
		return correoDestinatario;
	}

	/**
	 * 
	 * @param correoDestinatario
	 */
	public void setCorreoDestinatario(String correoDestinatario) {
		this.correoDestinatario = correoDestinatario;
	}

    @Override
    public String toString() {
        return "{" +
            " idCertificado='" + getIdCertificado() + "'" +
            ", tipoCertificado='" + getTipoCertificado() + "'" +
            ", fechaGeneracion='" + getFechaGeneracion() + "'" +
            ", dirigidoA='" + getDirigidoA() + "'" +
            ", idPersona='" + getIdPersona() + "'" +
            ", tipoSolicitud='" + getTipoSolicitud() + "'" +
            ", idComunicado='" + getIdComunicado() + "'" +
            ", anio='" + getAnio() + "'" +
            ", tipoAfiliado='" + getTipoAfiliado() + "'" +
            ", idEmpleador='" + getIdEmpleador() + "'" +
            ", etiqueta='" + getEtiqueta() + "'" +
            ", identificadorArchivoCertificado='" + getIdentificadorArchivoCertificado() + "'" +
            ", correoDestinatario='" + getCorreoDestinatario() + "'" +
            "}";
    }

}
