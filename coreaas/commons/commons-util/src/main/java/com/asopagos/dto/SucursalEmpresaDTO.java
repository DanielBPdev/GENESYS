package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.MotivoInactivacionRetencionSubsidioEnum;
import com.asopagos.enumeraciones.core.MotivoRetencionSubsidioEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos de una Sucursal Empresa
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 */
@XmlRootElement
public class SucursalEmpresaDTO implements Serializable {

	private Long idSucursalEmpresa;

	private Long idEmpresa;

	private Long idEmpleador;

	private String codigo;

	private String nombre;

	private Long idUbicacion;

	private Short idCodigoCIIU;

	private TipoMedioDePagoEnum medioDePagoSubsidioMonetario;

	private EstadoActivoInactivoEnum estadoSucursal;

    private Boolean retencionSubsidioActiva;

    private MotivoRetencionSubsidioEnum motivoRetencionSubsidio;

    private MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidio;

    private Boolean sucursalPrincipal;


	/**
	 * Método encargado de crear un objeto SucursalEmpresaDTO mediante la entidad
	 * SucursalEmpresa
	 * 
	 * @param sucursalEmpresa,
	 *            entidad SucursalEmpresa
	 * @return el objeto dto SucursalEmpresa
	 */
	public static SucursalEmpresaDTO obtenerSucursalEmpresaDTO(SucursalEmpresa sucursalEmpresa) {
		if (sucursalEmpresa != null) {
			SucursalEmpresaDTO sucursalEmpresaDTO = new SucursalEmpresaDTO();
			sucursalEmpresaDTO.setIdSucursalEmpresa(sucursalEmpresa.getIdSucursalEmpresa());
			sucursalEmpresaDTO.setCodigo(sucursalEmpresa.getCodigo());
			sucursalEmpresaDTO.setEstadoSucursal(sucursalEmpresa.getEstadoSucursal());
			if (sucursalEmpresa.getCodigoCIIU() != null && sucursalEmpresa.getCodigoCIIU().getIdCodigoCIIU() != null) {
				sucursalEmpresaDTO.setIdCodigoCIIU(sucursalEmpresa.getCodigoCIIU().getIdCodigoCIIU());
			}
			sucursalEmpresaDTO.setIdEmpresa(sucursalEmpresa.getIdEmpresa());
			sucursalEmpresaDTO.setMedioDePagoSubsidioMonetario(sucursalEmpresa.getMedioDePagoSubsidioMonetario());
			if (sucursalEmpresa.getUbicacion() != null && 
					sucursalEmpresa.getUbicacion().getIdUbicacion() != null) {
				sucursalEmpresaDTO.setIdUbicacion(sucursalEmpresa.getUbicacion().getIdUbicacion());	
			}
			sucursalEmpresaDTO.setNombre(sucursalEmpresa.getNombre());
			sucursalEmpresaDTO.setRetencionSubsidioActiva(sucursalEmpresa.getRetencionSubsidioActiva());
			sucursalEmpresaDTO.setMotivoRetencionSubsidio(sucursalEmpresa.getMotivoRetencionSubsidio());
			sucursalEmpresaDTO.setMotivoInactivaRetencionSubsidio(sucursalEmpresa.getMotivoInactivaRetencionSubsidio());
			sucursalEmpresaDTO.setSucursalPrincipal(sucursalEmpresa.getSucursalPrincipal());
			return sucursalEmpresaDTO;
		}
		return null;
	}
	
	/**
	 * Método encargado de crear un objeto SucursalEmpresa mediante el DTO
	 * SucursalEmpresaDTO
	 * 
	 * @param sucursalEmpresaDTO,
	 *            DTO SucursalEmpresa
	 * @return el objeto SucursalEmpresa
	 */
	public static SucursalEmpresa obtenerSucursalEmpresa(SucursalEmpresaModeloDTO sucursalEmpresaDTO) {
		if (sucursalEmpresaDTO != null) {
			SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
			sucursalEmpresa.setCodigo(sucursalEmpresaDTO.getCodigo());
			if(sucursalEmpresaDTO.getCodigoCIIU() != null){
				CodigoCIIU codigoCIIU = new CodigoCIIU();
				codigoCIIU.setIdCodigoCIIU(sucursalEmpresaDTO.getCodigoCIIU().getIdCodigoCIIU());
				codigoCIIU.setCodigoSeccion(sucursalEmpresaDTO.getCodigoCIIU().getCodigoSeccion());
				codigoCIIU.setDescripcionSeccion(sucursalEmpresaDTO.getCodigoCIIU().getDescripcionSeccion());
				codigoCIIU.setCodigoDivision(sucursalEmpresaDTO.getCodigoCIIU().getCodigoDivision());           
				codigoCIIU.setDescripcionDivision(sucursalEmpresaDTO.getCodigoCIIU().getDescripcionDivision());
	            codigoCIIU.setCodigoGrupo(sucursalEmpresaDTO.getCodigoCIIU().getCodigoGrupo());
	            codigoCIIU.setDescripcionGrupo(sucursalEmpresaDTO.getCodigoCIIU().getCodigoGrupo());
				sucursalEmpresa.setCodigoCIIU(codigoCIIU);
			}
			sucursalEmpresa.setEstadoSucursal(sucursalEmpresaDTO.getEstadoSucursal());
			sucursalEmpresa.setIdEmpresa(sucursalEmpresaDTO.getIdEmpresa());
			sucursalEmpresa.setIdSucursalEmpresa(sucursalEmpresaDTO.getIdSucursalEmpresa());
			sucursalEmpresa.setMedioDePagoSubsidioMonetario(sucursalEmpresaDTO.getMedioDePagoSubsidioMonetario());
			sucursalEmpresa.setNombre(sucursalEmpresaDTO.getNombre());
			if(sucursalEmpresaDTO.getUbicacion() != null){
				Ubicacion ubicacion = new Ubicacion();
				ubicacion.setIdUbicacion(sucursalEmpresaDTO.getUbicacion().getIdUbicacion());
				ubicacion.setAutorizacionEnvioEmail(sucursalEmpresaDTO.getUbicacion().getAutorizacionEnvioEmail());
				ubicacion.setCodigoPostal(sucursalEmpresaDTO.getUbicacion().getCodigoPostal());
				ubicacion.setDescripcionIndicacion(sucursalEmpresaDTO.getUbicacion().getDescripcionIndicacion());
				ubicacion.setDireccionFisica(sucursalEmpresaDTO.getUbicacion().getDireccionFisica());
				ubicacion.setEmail(sucursalEmpresaDTO.getUbicacion().getEmail());
				ubicacion.setIndicativoTelFijo(sucursalEmpresaDTO.getUbicacion().getIndicativoTelFijo());
				Municipio municipio = new Municipio();
				municipio.setIdMunicipio(sucursalEmpresaDTO.getUbicacion().getIdMunicipio());
				ubicacion.setMunicipio(municipio);
				ubicacion.setTelefonoCelular(sucursalEmpresaDTO.getUbicacion().getTelefonoCelular());
				ubicacion.setTelefonoFijo(sucursalEmpresaDTO.getUbicacion().getTelefonoFijo());
				
				sucursalEmpresa.setUbicacion(ubicacion);
			}
			sucursalEmpresa.setRetencionSubsidioActiva(sucursalEmpresaDTO.getRetencionSubsidioActiva());
			sucursalEmpresa.setMotivoRetencionSubsidio(sucursalEmpresaDTO.getMotivoRetencionSubsidio());
			sucursalEmpresa.setMotivoInactivaRetencionSubsidio(sucursalEmpresaDTO.getMotivoInactivaRetencionSubsidio());
			sucursalEmpresa.setSucursalPrincipal(sucursalEmpresaDTO.getSucursalPrincipal());
			return sucursalEmpresa;
			
		}
		return null;
	}

	/**
	 * @return the idSucursalEmpresa
	 */
	public Long getIdSucursalEmpresa() {
		return idSucursalEmpresa;
	}

	/**
	 * @param idSucursalEmpresa the idSucursalEmpresa to set
	 */
	public void setIdSucursalEmpresa(Long idSucursalEmpresa) {
		this.idSucursalEmpresa = idSucursalEmpresa;
	}

	/**
	 * @return the idEmpresa
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return the idEmpresa
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the idUbicacion
	 */
	public Long getIdUbicacion() {
		return idUbicacion;
	}

	/**
	 * @param idUbicacion the idUbicacion to set
	 */
	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	/**
	 * @return the idCodigoCIIU
	 */
	public Short getIdCodigoCIIU() {
		return idCodigoCIIU;
	}

	/**
	 * @param idCodigoCIIU the idCodigoCIIU to set
	 */
	public void setIdCodigoCIIU(Short idCodigoCIIU) {
		this.idCodigoCIIU = idCodigoCIIU;
	}

	/**
	 * @return the medioDePagoSubsidioMonetario
	 */
	public TipoMedioDePagoEnum getMedioDePagoSubsidioMonetario() {
		return medioDePagoSubsidioMonetario;
	}

	/**
	 * @param medioDePagoSubsidioMonetario the medioDePagoSubsidioMonetario to set
	 */
	public void setMedioDePagoSubsidioMonetario(TipoMedioDePagoEnum medioDePagoSubsidioMonetario) {
		this.medioDePagoSubsidioMonetario = medioDePagoSubsidioMonetario;
	}

	/**
	 * @return the estadoSucursal
	 */
	public EstadoActivoInactivoEnum getEstadoSucursal() {
		return estadoSucursal;
	}

	/**
	 * @param estadoSucursal the estadoSucursal to set
	 */
	public void setEstadoSucursal(EstadoActivoInactivoEnum estadoSucursal) {
		this.estadoSucursal = estadoSucursal;
	}

    /**
     * @return the retencionSubsidioActiva
     */
    public Boolean getRetencionSubsidioActiva() {
        return retencionSubsidioActiva;
    }

    /**
     * @param retencionSubsidioActiva the retencionSubsidioActiva to set
     */
    public void setRetencionSubsidioActiva(Boolean retencionSubsidioActiva) {
        this.retencionSubsidioActiva = retencionSubsidioActiva;
    }

    /**
     * @return the motivoRetencionSubsidio
     */
    public MotivoRetencionSubsidioEnum getMotivoRetencionSubsidio() {
        return motivoRetencionSubsidio;
    }

    /**
     * @param motivoRetencionSubsidio the motivoRetencionSubsidio to set
     */
    public void setMotivoRetencionSubsidio(MotivoRetencionSubsidioEnum motivoRetencionSubsidio) {
        this.motivoRetencionSubsidio = motivoRetencionSubsidio;
    }

    /**
     * @return the motivoInactivaRetencionSubsidio
     */
    public MotivoInactivacionRetencionSubsidioEnum getMotivoInactivaRetencionSubsidio() {
        return motivoInactivaRetencionSubsidio;
    }

    /**
     * @param motivoInactivaRetencionSubsidio the motivoInactivaRetencionSubsidio to set
     */
    public void setMotivoInactivaRetencionSubsidio(MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidio) {
        this.motivoInactivaRetencionSubsidio = motivoInactivaRetencionSubsidio;
    }

    /**
     * @return the sucursalPrincipal
     */
    public Boolean getSucursalPrincipal() {
        return sucursalPrincipal;
    }

    /**
     * @param sucursalPrincipal the sucursalPrincipal to set
     */
    public void setSucursalPrincipal(Boolean sucursalPrincipal) {
        this.sucursalPrincipal = sucursalPrincipal;
    }

	
}
