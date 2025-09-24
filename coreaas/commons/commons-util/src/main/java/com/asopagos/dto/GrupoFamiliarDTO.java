package com.asopagos.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.validacion.GrupoActualizacion;

/**
 * <b>Descripción:</b> DTO que transporta los datos de un grupo familiar
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class GrupoFamiliarDTO implements Serializable {

	private Boolean ubicacionAfiliadoPrincipal;
	private UbicacionDTO ubicacion;
	private Boolean afiliadoAdministradorSubsidio;
	private DatosBasicosIdentificacionDTO administradorSubsidio;
	private Short idRelacionAdministradorSubsidio;
	private String observaciones;
	private MedioDePagoModeloDTO medioDePagoModeloDTO;
	private Boolean emitirTarjeta;
	private Boolean emitirTarjetaInmediatamente;
	private Boolean autorizacionUsoDatosPersonales;	
	private AfiliadoInDTO afiliadoInDTO;
	private Boolean inembargable;
	
	@NotNull(groups = { GrupoActualizacion.class })
	private Byte numero;	
	@NotNull(groups = { GrupoActualizacion.class })
	private Long idGrupoFamiliar;

	
	/**
	 * Convierte los datos de GrupoFamiliar a GrupoFamiliarDTO.
	 * @param grupoFamiliar
	 * @return
	 */
	public static GrupoFamiliarDTO convertGrupoFamiliarToDTO(GrupoFamiliar grupoFamiliar) {
		GrupoFamiliarDTO grupoFamiliarDTO = new GrupoFamiliarDTO();
		grupoFamiliarDTO.setIdGrupoFamiliar(grupoFamiliar.getIdGrupoFamiliar());
		if (grupoFamiliar.getAfiliado() != null) {
			grupoFamiliarDTO.setAfiliadoInDTO(AfiliadoInDTO.convertAfiliadoToDTO(grupoFamiliar.getAfiliado(), null));
		}
		grupoFamiliarDTO.setNumero(grupoFamiliar.getNumero());
		grupoFamiliarDTO.setObservaciones(grupoFamiliar.getObservaciones());
		if (grupoFamiliar.getUbicacion() != null) {
			grupoFamiliarDTO.setUbicacion(UbicacionDTO.obtenerUbicacionDTO(grupoFamiliar.getUbicacion()));
		}
		return grupoFamiliarDTO;
    }
	 
	/**
	 * Asocia los datos modificados en el DTO al Objeto GrupoFamiliar.
	 * @param grupoFamiliar
	 */
	public void obtenerDatosGrupoFamiliar (GrupoFamiliar grupoFamiliar) {
		if (getAfiliadoInDTO() != null) {
			Afiliado afiliado = new Afiliado();
			afiliado.setIdAfiliado(getAfiliadoInDTO().getIdAfiliado());
			grupoFamiliar.setAfiliado(afiliado);
		}
		if (getNumero() != null) {
			grupoFamiliar.setNumero(getNumero());
		}
		if (getObservaciones() != null) {
			grupoFamiliar.setObservaciones(getObservaciones());
		}
		if (getUbicacion() != null) {
			Ubicacion ubicacion = new Ubicacion();
			ubicacion.setIdUbicacion(getUbicacion().getIdUbicacion());
			grupoFamiliar.setUbicacion(ubicacion);
		}
	}
	
	/**
	 * @return the ubicacionAfiliadoPrincipal
	 */
	public Boolean getUbicacionAfiliadoPrincipal() {
		return ubicacionAfiliadoPrincipal;
	}

	/**
	 * @param ubicacionAfiliadoPrincipal
	 *            the ubicacionAfiliadoPrincipal to set
	 */
	public void setUbicacionAfiliadoPrincipal(Boolean ubicacionAfiliadoPrincipal) {
		this.ubicacionAfiliadoPrincipal = ubicacionAfiliadoPrincipal;
	}

	/**
	 * @return the ubicacion
	 */
	public UbicacionDTO getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion
	 *            the ubicacion to set
	 */
	public void setUbicacion(UbicacionDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the afiliadoAdministradorSubsidio
	 */
	public Boolean getAfiliadoAdministradorSubsidio() {
		return afiliadoAdministradorSubsidio;
	}

	/**
	 * @param afiliadoAdministradorSubsidio
	 *            the afiliadoAdministradorSubsidio to set
	 */
	public void setAfiliadoAdministradorSubsidio(Boolean afiliadoAdministradorSubsidio) {
		this.afiliadoAdministradorSubsidio = afiliadoAdministradorSubsidio;
	}

	/**
	 * @return the administradorSubsidio
	 */
	public DatosBasicosIdentificacionDTO getAdministradorSubsidio() {
		return administradorSubsidio;
	}

	/**
	 * @param administradorSubsidio
	 *            the administradorSubsidio to set
	 */
	public void setAdministradorSubsidio(DatosBasicosIdentificacionDTO administradorSubsidio) {
		this.administradorSubsidio = administradorSubsidio;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the emitirTarjeta
	 */
	public Boolean getEmitirTarjeta() {
		return emitirTarjeta;
	}

	/**
	 * @param emitirTarjeta
	 *            the emitirTarjeta to set
	 */
	public void setEmitirTarjeta(Boolean emitirTarjeta) {
		this.emitirTarjeta = emitirTarjeta;
	}

	/**
	 * @return the emitirTarjetaInmediatamente
	 */
	public Boolean getEmitirTarjetaInmediatamente() {
		return emitirTarjetaInmediatamente;
	}

	/**
	 * @param emitirTarjetaInmediatamente
	 *            the emitirTarjetaInmediatamente to set
	 */
	public void setEmitirTarjetaInmediatamente(Boolean emitirTarjetaInmediatamente) {
		this.emitirTarjetaInmediatamente = emitirTarjetaInmediatamente;
	}

	/**
	 * @return the autorizacionUsoDatosPersonales
	 */
	public Boolean getAutorizacionUsoDatosPersonales() {
		return autorizacionUsoDatosPersonales;
	}

	/**
	 * @param autorizacionUsoDatosPersonales
	 *            the autorizacionUsoDatosPersonales to set
	 */
	public void setAutorizacionUsoDatosPersonales(Boolean autorizacionUsoDatosPersonales) {
		this.autorizacionUsoDatosPersonales = autorizacionUsoDatosPersonales;
	}

	/**
	 * @return the idRelacionAdministradorSubsidio
	 */
	public Short getIdRelacionAdministradorSubsidio() {
		return idRelacionAdministradorSubsidio;
	}

	/**
	 * @param idRelacionAdministradorSubsidio the idRelacionAdministradorSubsidio to set
	 */
	public void setIdRelacionAdministradorSubsidio(Short idRelacionAdministradorSubsidio) {
		this.idRelacionAdministradorSubsidio = idRelacionAdministradorSubsidio;
	}

	/**
	 * @return the numero
	 */
	public Byte getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(Byte numero) {
		this.numero = numero;
	}

	/**
	 * @return the idGrupoFamiliar
	 */
	public Long getIdGrupoFamiliar() {
		return idGrupoFamiliar;
	}

	/**
	 * @param idGrupoFamiliar the idGrupoFamiliar to set
	 */
	public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	/**
	 * Método que retorna el valor de inembargable.
	 * @return valor de inembargable.
	 */
	public Boolean getInembargable() {
		return inembargable;
	}

	/**
	 * Método encargado de modificar el valor de inembargable.
	 * @param valor para modificar inembargable.
	 */
	public void setInembargable(Boolean inembargable) {
		this.inembargable = inembargable;
	}
/**
	 * @return the afiliadoInDTO
	 */
	public AfiliadoInDTO getAfiliadoInDTO() {
		return afiliadoInDTO;
	}

	/**
	 * @param afiliadoInDTO the afiliadoInDTO to set
	 */
	public void setAfiliadoInDTO(AfiliadoInDTO afiliadoInDTO) {
		this.afiliadoInDTO = afiliadoInDTO;
	}

	/**
	 * @return the medioDePagoModeloDTO
	 */
	public MedioDePagoModeloDTO getMedioDePagoModeloDTO() {
		return medioDePagoModeloDTO;
	}

	/**
	 * @param medioDePagoModeloDTO the medioDePagoModeloDTO to set
	 */
	public void setMedioDePagoModeloDTO(MedioDePagoModeloDTO medioDePagoModeloDTO) {
		this.medioDePagoModeloDTO = medioDePagoModeloDTO;
	}
	
}
