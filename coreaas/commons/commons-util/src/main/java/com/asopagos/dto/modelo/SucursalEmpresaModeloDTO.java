package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.MotivoInactivacionRetencionSubsidioEnum;
import com.asopagos.enumeraciones.core.MotivoRetencionSubsidioEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * DTO que transporta los datos de una Sucursal Empresa.
 * 
 * @author Angélica Toro Murillo <flopez@heinsohn.com.co>
 */
@XmlRootElement
public class SucursalEmpresaModeloDTO implements Serializable {

	/**
	 * Código identificador de llave primaria de la sucursal de la empresa 
	 */
	private Long idSucursalEmpresa;
	
	/**
	 * Id que identifica a la empresa asociada a la sucursal de la empresa
	 */
	private Long idEmpresa;
	
	/**
	 * Descripción del código asociado a la empresa
	 */
	private String codigo;
	
	/**
	 * Descripción del nombre de la empresa
	 */
	private String nombre;
	
	/**
	 * Id que identifica la ubicación asociada a la sucursal de la empresa
	 */
	private UbicacionModeloDTO ubicacion;
	
	/**
	 * Id que identifica el código CIIU asociada a la sucursal de la empresa
	 */
	private CodigoCIIU codigoCIIU;
    
	/**
	 * Id que identifica el la parametrización del pago asociada a la sucursal de la empresa
	 */
	private TipoMedioDePagoEnum medioDePagoSubsidioMonetario;
		
	/**
	 * Estado de la sucursal de la empresa
	 */
	private EstadoActivoInactivoEnum estadoSucursal;
	
	/**
	 * Atributo que indica si el codigo debe coincidir con piola.
	 */
	private Boolean coincidirCodigoPila;
	
	/**
     * Motivo de activacion retencion de subsidio
     */
    private Boolean retencionSubsidioActiva;

    /**
     * Motivo de activacion retencion de subsidio
     */
    private MotivoRetencionSubsidioEnum motivoRetencionSubsidio;

    /**
     * Motivo de inactivacion retencion de subsidio
     */
    private MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidio;

    /**
     * Indica si la sucursal es principal
     */
    private Boolean sucursalPrincipal;
    
    /**
     * Constructor por defecto
     * */
    public SucursalEmpresaModeloDTO(){
        super();
    }
    
    /**
     * Constructor con base en entity
     * */
    public SucursalEmpresaModeloDTO(SucursalEmpresa sucursalEmpresa){
        super();
        this.convertToDTO(sucursalEmpresa);
    }
	
	/**
	 * Método encaragado de convertir de Entidad a DTO.
	 * @param sucursalEmpresa entidad a convertir.
	 */
	public void convertToDTO(SucursalEmpresa sucursalEmpresa) {
		this.setCodigo(sucursalEmpresa.getCodigo());
		if (sucursalEmpresa.getCodigoCIIU() != null && sucursalEmpresa.getCodigoCIIU().getIdCodigoCIIU()!=null) {
			CodigoCIIU codigoCiiu = new CodigoCIIU();
			codigoCiiu.setCodigo(sucursalEmpresa.getCodigoCIIU().getCodigo());
			codigoCiiu.setDescripcion(sucursalEmpresa.getCodigoCIIU().getDescripcion());
			codigoCiiu.setIdCodigoCIIU(sucursalEmpresa.getCodigoCIIU().getIdCodigoCIIU());
			codigoCiiu.setCodigoSeccion(sucursalEmpresa.getCodigoCIIU().getCodigoSeccion());
			codigoCiiu.setDescripcionSeccion(sucursalEmpresa.getCodigoCIIU().getDescripcionSeccion());
			codigoCiiu.setCodigoDivision(sucursalEmpresa.getCodigoCIIU().getCodigoDivision());           
			codigoCiiu.setDescripcionDivision(sucursalEmpresa.getCodigoCIIU().getDescripcionDivision());
			codigoCiiu.setCodigoGrupo(sucursalEmpresa.getCodigoCIIU().getCodigoGrupo());
			codigoCiiu.setDescripcionGrupo(sucursalEmpresa.getCodigoCIIU().getCodigoGrupo());
			this.setCodigoCIIU(codigoCiiu);
		}
		this.setEstadoSucursal(sucursalEmpresa.getEstadoSucursal());
		this.setIdEmpresa(sucursalEmpresa.getIdEmpresa());
		this.setIdSucursalEmpresa(sucursalEmpresa.getIdSucursalEmpresa());
		this.setMedioDePagoSubsidioMonetario(sucursalEmpresa.getMedioDePagoSubsidioMonetario());
		this.setNombre(sucursalEmpresa.getNombre());
		if (sucursalEmpresa.getUbicacion() != null && sucursalEmpresa.getUbicacion().getIdUbicacion()!=null) {
			UbicacionModeloDTO ubicacionDTO = new UbicacionModeloDTO();
			ubicacionDTO.convertToDTO(sucursalEmpresa.getUbicacion());
			this.setUbicacion(ubicacionDTO);
		}
		this.setCoincidirCodigoPila(sucursalEmpresa.getCoincidirCodigoPila());
		this.setRetencionSubsidioActiva(sucursalEmpresa.getRetencionSubsidioActiva());
		this.setMotivoRetencionSubsidio(sucursalEmpresa.getMotivoRetencionSubsidio());
		this.setMotivoInactivaRetencionSubsidio(sucursalEmpresa.getMotivoInactivaRetencionSubsidio());
		this.setSucursalPrincipal(sucursalEmpresa.getSucursalPrincipal());

	}
	
	/**
	 * Método encaragado de convertir de DTO a Entidad.
	 * @param this entidad a convertir.
	 * @return DTO convertido.
	 */
	public SucursalEmpresa convertToEntity() {
		SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
		sucursalEmpresa.setCodigo(this.getCodigo());
		sucursalEmpresa.setCodigoCIIU(this.getCodigoCIIU());
		sucursalEmpresa.setEstadoSucursal(this.getEstadoSucursal());
		sucursalEmpresa.setIdEmpresa(this.getIdEmpresa());
		sucursalEmpresa.setIdSucursalEmpresa(this.getIdSucursalEmpresa());
		sucursalEmpresa.setMedioDePagoSubsidioMonetario(this.getMedioDePagoSubsidioMonetario());
		sucursalEmpresa.setNombre(this.getNombre());
		sucursalEmpresa.setUbicacion(this.getUbicacion().convertToEntity());
		sucursalEmpresa.setCoincidirCodigoPila(this.getCoincidirCodigoPila());
		sucursalEmpresa.setRetencionSubsidioActiva(this.getRetencionSubsidioActiva());
		sucursalEmpresa.setMotivoRetencionSubsidio(this.getMotivoRetencionSubsidio());
		sucursalEmpresa.setMotivoInactivaRetencionSubsidio(this.getMotivoInactivaRetencionSubsidio());
		sucursalEmpresa.setSucursalPrincipal(this.getSucursalPrincipal());
		return sucursalEmpresa;
	}
	/**
	 * Método que copia de un DTO  a una entidad.
	 * @param sucursalEmpresa sucursal previamente consultada.
	 */
	public SucursalEmpresa copyDTOToEntity(SucursalEmpresa sucursalEmpresa){
		if(this.getCodigo()!=null){
		sucursalEmpresa.setCodigo(this.getCodigo());
		}
		if(this.getCodigoCIIU()!=null){
		sucursalEmpresa.setCodigoCIIU(this.getCodigoCIIU());
		}
		if(this.getEstadoSucursal()!=null){
		sucursalEmpresa.setEstadoSucursal(this.getEstadoSucursal());
		}
		if(this.getIdEmpresa()!=null){
		sucursalEmpresa.setIdEmpresa(this.getIdEmpresa());
		}
		if(this.getIdSucursalEmpresa()!=null){
		sucursalEmpresa.setIdSucursalEmpresa(this.getIdSucursalEmpresa());
		}
		if(this.getMedioDePagoSubsidioMonetario()!=null){
		sucursalEmpresa.setMedioDePagoSubsidioMonetario(this.getMedioDePagoSubsidioMonetario());
		}
		if(this.getNombre()!=null){
		sucursalEmpresa.setNombre(this.getNombre());
		}
		if(this.getUbicacion()!=null){
		sucursalEmpresa.setUbicacion(this.getUbicacion().copyDTOToEntity(sucursalEmpresa.getUbicacion()));
		}
		if(this.getCoincidirCodigoPila()!=null){
			sucursalEmpresa.setCoincidirCodigoPila(this.getCoincidirCodigoPila());
		}
		if(this.getRetencionSubsidioActiva() != null){
		    sucursalEmpresa.setRetencionSubsidioActiva(this.getRetencionSubsidioActiva());
		}
		if(this.getMotivoRetencionSubsidio() != null){
		    sucursalEmpresa.setMotivoRetencionSubsidio(this.getMotivoRetencionSubsidio());
		}
		if(this.getMotivoInactivaRetencionSubsidio() != null){
		    sucursalEmpresa.setMotivoInactivaRetencionSubsidio(this.getMotivoInactivaRetencionSubsidio());
		}
		if (this.getSucursalPrincipal() != null) {
            sucursalEmpresa.setSucursalPrincipal(this.getSucursalPrincipal());
        }
		return sucursalEmpresa;
	}

	/**
	 * Método que retorna el valor de idSucursalEmpresa.
	 * @return valor de idSucursalEmpresa.
	 */
	public Long getIdSucursalEmpresa() {
		return idSucursalEmpresa;
	}

	/**
	 * Método encargado de modificar el valor de idSucursalEmpresa.
	 * @param valor para modificar idSucursalEmpresa.
	 */
	public void setIdSucursalEmpresa(Long idSucursalEmpresa) {
		this.idSucursalEmpresa = idSucursalEmpresa;
	}

	/**
	 * Método que retorna el valor de idEmpresa.
	 * @return valor de idEmpresa.
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * Método encargado de modificar el valor de idEmpresa.
	 * @param valor para modificar idEmpresa.
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * Método que retorna el valor de codigo.
	 * @return valor de codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método encargado de modificar el valor de codigo.
	 * @param valor para modificar codigo.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método que retorna el valor de nombre.
	 * @return valor de nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método encargado de modificar el valor de nombre.
	 * @param valor para modificar nombre.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método que retorna el valor de ubicacion.
	 * @return valor de ubicacion.
	 */
	public UbicacionModeloDTO getUbicacion() {
		return ubicacion;
	}

	/**
	 * Método encargado de modificar el valor de ubicacion.
	 * @param valor para modificar ubicacion.
	 */
	public void setUbicacion(UbicacionModeloDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * Método que retorna el valor de codigoCIIU.
	 * @return valor de codigoCIIU.
	 */
	public CodigoCIIU getCodigoCIIU() {
		return codigoCIIU;
	}

	/**
	 * Método encargado de modificar el valor de codigoCIIU.
	 * @param valor para modificar codigoCIIU.
	 */
	public void setCodigoCIIU(CodigoCIIU codigoCIIU) {
		this.codigoCIIU = codigoCIIU;
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
	 * Método que retorna el valor de estadoSucursal.
	 * @return valor de estadoSucursal.
	 */
	public EstadoActivoInactivoEnum getEstadoSucursal() {
		return estadoSucursal;
	}

	/**
	 * Método encargado de modificar el valor de estadoSucursal.
	 * @param valor para modificar estadoSucursal.
	 */
	public void setEstadoSucursal(EstadoActivoInactivoEnum estadoSucursal) {
		this.estadoSucursal = estadoSucursal;
	}

	/**
	 * Método que retorna el valor de coincidirCodigoPila.
	 * @return valor de coincidirCodigoPila.
	 */
	public final Boolean getCoincidirCodigoPila() {
		return coincidirCodigoPila;
	}

	/**
	 * Método encargado de modificar el valor de coincidirCodigoPila.
	 * @param valor para modificar coincidirCodigoPila.
	 */
	public final void setCoincidirCodigoPila(Boolean coincidirCodigoPila) {
		this.coincidirCodigoPila = coincidirCodigoPila;
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
