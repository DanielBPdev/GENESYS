/**
 * 
 */
package com.asopagos.dto.cargaMultiple;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.GrupoFamiliarDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; 

/**
 * <b>Descripción:</b> DTO para afiliar trabajador candidato <b>Historia de
 * Usuario:</b> 122-360,122-364,122-369,122-361,123-374,123-375
 *
 * @author Julian Andres Sanchez<jusanchez@heinsohn.com.co>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class AfiliarTrabajadorCandidatoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AfiliadoInDTO afiliadoInDTO;

	private Long idSolicitudGlobal;

	private FormatoEntregaDocumentoEnum metodoEnvio;

	private String usuarioRadicacion;

	private InformacionLaboralTrabajadorDTO informacionLaboralTrabajador;

	private IdentificacionUbicacionPersonaDTO identificadorUbicacionPersona;

	private Long codigoCargueMultiple;

	private TipoTransaccionEnum tipoTransaccion;

	private Boolean afiliable;

	private String numeroIdentificacionEmpleador;

	private TipoIdentificacionEnum tipoIdentificacionEmpleador;

	private ClasificacionEnum clasificacion;

	private String razonSocialNombre;

	private String correoEmpleador;

	private String direccionEmpleador;

	private String telefonoEmpleador;

	private String nombreYApellidosRepresentanteLegal;

	private String nombreArchivo;
	
	private CausaIntentoFallidoAfiliacionEnum causaIntentoFallido;
	
	private Boolean iniciarProceso;
	
	private List<BeneficiarioDTO> beneficiarios;
	
	private List<GrupoFamiliarDTO> gruposFamiliares;

	public AfiliarTrabajadorCandidatoDTO() {
	}

	/**
	 * @return the clasificacion
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion
	 *            the clasificacion to set
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * @return the metodoEnvio
	 */
	public FormatoEntregaDocumentoEnum getMetodoEnvio() {
		return metodoEnvio;
	}

	/**
	 * @return the usuarioRadicacion
	 */
	public String getUsuarioRadicacion() {
		return usuarioRadicacion;
	}

	/**
	 * @param metodoEnvio
	 *            the metodoEnvio to set
	 */
	public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
		this.metodoEnvio = metodoEnvio;
	}

	/**
	 * @param usuarioRadicacion
	 *            the usuarioRadicacion to set
	 */
	public void setUsuarioRadicacion(String usuarioRadicacion) {
		this.usuarioRadicacion = usuarioRadicacion;
	}

	/**
	 * @return the informacionLaboralTrabajador
	 */
	public InformacionLaboralTrabajadorDTO getInformacionLaboralTrabajador() {
		return informacionLaboralTrabajador;
	}

	/**
	 * @return the identificadorUbicacionPersona
	 */
	public IdentificacionUbicacionPersonaDTO getIdentificadorUbicacionPersona() {
		return identificadorUbicacionPersona;
	}

	/**
	 * @return the afiliadoInDTO
	 */
	public AfiliadoInDTO getAfiliadoInDTO() {
		return afiliadoInDTO;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo
	 *            the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * @param afiliadoInDTO
	 *            the afiliadoInDTO to set
	 */
	public void setAfiliadoInDTO(AfiliadoInDTO afiliadoInDTO) {
		this.afiliadoInDTO = afiliadoInDTO;
	}

	/**
	 * @param informacionLaboralTrabajador
	 *            the informacionLaboralTrabajador to set
	 */
	public void setInformacionLaboralTrabajador(InformacionLaboralTrabajadorDTO informacionLaboralTrabajador) {
		this.informacionLaboralTrabajador = informacionLaboralTrabajador;
	}

	/**
	 * @param identificadorUbicacionPersona
	 *            the identificadorUbicacionPersona to set
	 */
	public void setIdentificadorUbicacionPersona(IdentificacionUbicacionPersonaDTO identificadorUbicacionPersona) {
		this.identificadorUbicacionPersona = identificadorUbicacionPersona;
	}

	/**
	 * @return the idSolicitudGlobal
	 */
	public Long getIdSolicitudGlobal() {
		return idSolicitudGlobal;
	}

	/**
	 * @param idSolicitudGlobal
	 *            the idSolicitudGlobal to set
	 */
	public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
		this.idSolicitudGlobal = idSolicitudGlobal;
	}

	/**
	 * @return the codigoCargueMultiple
	 */
	public Long getCodigoCargueMultiple() {
		return codigoCargueMultiple;
	}

	/**
	 * @param codigoCargueMultiple
	 *            the codigoCargueMultiple to set
	 */
	public void setCodigoCargueMultiple(Long codigoCargueMultiple) {
		this.codigoCargueMultiple = codigoCargueMultiple;
	}

	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public Boolean getAfiliable() {
		return afiliable;
	}

	public void setAfiliable(Boolean afiliable) {
		this.afiliable = afiliable;
	}

	/**
	 * @return the numeroIdentificacionEmpleador
	 */
	public String getNumeroIdentificacionEmpleador() {
		return numeroIdentificacionEmpleador;
	}

	/**
	 * @return the tipoIdentificacionEmpleador
	 */
	public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
		return tipoIdentificacionEmpleador;
	}

	/**
	 * @param numeroIdentificacionEmpleador
	 *            the numeroIdentificacionEmpleador to set
	 */
	public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
	}

	/**
	 * @param tipoIdentificacionEmpleador
	 *            the tipoIdentificacionEmpleador to set
	 */
	public void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
		this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
	}

	/**
	 * @return the razonSocialNombre
	 */
	public String getRazonSocialNombre() {
		return razonSocialNombre;
	}

	/**
	 * @param razonSocialNombre
	 *            the razonSocialNombre to set
	 */
	public void setRazonSocialNombre(String razonSocialNombre) {
		this.razonSocialNombre = razonSocialNombre;
	}

	/**
	 * @return the direccionEmpleador
	 */
	public String getDireccionEmpleador() {
		return direccionEmpleador;
	}

	/**
	 * @return the telefonoEmpleador
	 */
	public String getTelefonoEmpleador() {
		return telefonoEmpleador;
	}

	/**
	 * @param direccionEmpleador
	 *            the direccionEmpleador to set
	 */
	public void setDireccionEmpleador(String direccionEmpleador) {
		this.direccionEmpleador = direccionEmpleador;
	}

	/**
	 * @param telefonoEmpleador
	 *            the telefonoEmpleador to set
	 */
	public void setTelefonoEmpleador(String telefonoEmpleador) {
		this.telefonoEmpleador = telefonoEmpleador;
	}

	/**
	 * @return the correoEmpleador
	 */
	public String getCorreoEmpleador() {
		return correoEmpleador;
	}

	/**
	 * @return the nombreYApellidosRepresentanteLegal
	 */
	public String getNombreYApellidosRepresentanteLegal() {
		return nombreYApellidosRepresentanteLegal;
	}

	/**
	 * @param nombreYApellidosRepresentanteLegal
	 *            the nombreYApellidosRepresentanteLegal to set
	 */
	public void setNombreYApellidosRepresentanteLegal(String nombreYApellidosRepresentanteLegal) {
		this.nombreYApellidosRepresentanteLegal = nombreYApellidosRepresentanteLegal;
	}

	/**
	 * @param correoEmpleador
	 *            the correoEmpleador to set
	 */
	public void setCorreoEmpleador(String correoEmpleador) {
		this.correoEmpleador = correoEmpleador;
	}

	/**
	 * @return the causaIntentoFallido
	 */
	public CausaIntentoFallidoAfiliacionEnum getCausaIntentoFallido() {
		return causaIntentoFallido;
	}

	/**
	 * @param causaIntentoFallido the causaIntentoFallido to set
	 */
	public void setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum causaIntentoFallido) {
		this.causaIntentoFallido = causaIntentoFallido;
	}

    /**
     * @return the iniciarProceso
     */
    public Boolean getIniciarProceso() {
        return iniciarProceso;
    }

    /**
     * @param iniciarProceso the iniciarProceso to set
     */
    public void setIniciarProceso(Boolean iniciarProceso) {
        this.iniciarProceso = iniciarProceso;
    }

    /**
     * @return the beneficiarios
     */
    public List<BeneficiarioDTO> getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios the beneficiarios to set
     */
    public void setBeneficiarios(List<BeneficiarioDTO> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    /**
     * @return the gruposFamiliares
     */
    public List<GrupoFamiliarDTO> getGruposFamiliares() {
        return gruposFamiliares;
    }

    /**
     * @param gruposFamiliares the gruposFamiliares to set
     */
    public void setGruposFamiliares(List<GrupoFamiliarDTO> gruposFamiliares) {
        this.gruposFamiliares = gruposFamiliares;
    }

}
