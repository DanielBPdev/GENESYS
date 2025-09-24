package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralValidacionEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos de identificacion ubicacion
 * persona
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class IdentificacionUbicacionPersonaDTO extends BeneficiarioDTO implements Serializable {
	
	/**
     * Serial Version id
     */
    private static final long serialVersionUID = 169820347946654831L;

    private PersonaDTO persona;
	
	private UbicacionDTO ubicacion;

    private Boolean autorizacionUsoDatosPersonales;

    private Boolean resideSectorRural;

    private ClaseIndependienteEnum claseIndependiente;

    private BigDecimal porcentajeAportes;

    private BigDecimal valorMesadaSalarioIngresos;

    // private Long idPagadorPension; :: REFACTORIZACIÓN A SOLICITUD DE ASOPAGOS
    private Short idPagadorPension;

    private Long idEntidadPagadora;

    private String identificadorAnteEntidadPagadora;

    private Long idGrupoFamiliar;

    private ResultadoGeneralValidacionEnum resultadoValidacion;

    private Boolean labora;

    private BigDecimal salarioMensualBeneficiario;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private List<ItemChequeoDTO> listaChequeo;

    private Date fechaAfiliacion;
    
    private Long idBeneficiario;
    
    private MedioDePagoModeloDTO medioDePago;
    /**
     * Afiliacion a la que pertenece el beneficiario
     */
    private Long idRolAfiliado;
    
    private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
    
    private PeriodoPagoPlanillaEnum oportunidadPago;
    
    private Date fechaRetiro;

    /**
     * Representa si al beneficiario que ya se encuentra activo respecto a un afiliado principal se le van a omitir sus validaciones o no
     */
    private Boolean omitirValidaciones;
    
    /**
     * Identificador del municipio asociado a donde el trabjador realiza el desempeño de sus labores.
     */
    private Short municipioDesempenioLabores;

	/**
	 * @return the persona
	 */
	public PersonaDTO getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	/**
	 * @return the ubicacion
	 */
	public UbicacionDTO getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion the ubicacion to set
	 */
	public void setUbicacion(UbicacionDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the autorizacionUsoDatosPersonales
	 */
	public Boolean getAutorizacionUsoDatosPersonales() {
		return autorizacionUsoDatosPersonales;
	}

	/**
	 * @param autorizacionUsoDatosPersonales the autorizacionUsoDatosPersonales to set
	 */
	public void setAutorizacionUsoDatosPersonales(Boolean autorizacionUsoDatosPersonales) {
		this.autorizacionUsoDatosPersonales = autorizacionUsoDatosPersonales;
	}

	/**
	 * @return the resideSectorRural
	 */
	public Boolean getResideSectorRural() {
		return resideSectorRural;
	}

	/**
	 * @param resideSectorRural the resideSectorRural to set
	 */
	public void setResideSectorRural(Boolean resideSectorRural) {
		this.resideSectorRural = resideSectorRural;
	}

	/**
	 * @return the claseIndependiente
	 */
	public ClaseIndependienteEnum getClaseIndependiente() {
		return claseIndependiente;
	}

	/**
	 * @param claseIndependiente the claseIndependiente to set
	 */
	public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
		this.claseIndependiente = claseIndependiente;
	}

	/**
	 * @return the porcentajeAportes
	 */
	public BigDecimal getPorcentajeAportes() {
		return porcentajeAportes;
	}

	/**
	 * @param porcentajeAportes the porcentajeAportes to set
	 */
	public void setPorcentajeAportes(BigDecimal porcentajeAportes) {
		this.porcentajeAportes = porcentajeAportes;
	}

	/**
	 * @return the valorMesadaSalarioIngresos
	 */
	public BigDecimal getValorMesadaSalarioIngresos() {
		return valorMesadaSalarioIngresos;
	}

	/**
	 * @param valorMesadaSalarioIngresos the valorMesadaSalarioIngresos to set
	 */
	public void setValorMesadaSalarioIngresos(BigDecimal valorMesadaSalarioIngresos) {
		this.valorMesadaSalarioIngresos = valorMesadaSalarioIngresos;
	}


//	:: REFACTORIZACIÓN A SOLICITUD DE ASOPAGOS - 2017-01-13
//	/**
//	 * @return the idPagadorPension
//	 */
//	public Long getIdPagadorPension() {
//		return idPagadorPension;
//	}
//
//	/**
//	 * @param idPagadorPension the idPagadorPension to set
//	 */
//	public void setIdPagadorPension(Long idPagadorPension) {
//		this.idPagadorPension = idPagadorPension;
//	}

	/**
	 * @return the idPagadorPension
	 */
	public Short getIdPagadorPension() {
		return idPagadorPension;
	}

	/**
	 * @param idPagadorPension the idPagadorPension to set
	 */
	public void setIdPagadorPension(Short idPagadorPension) {
		this.idPagadorPension = idPagadorPension;
	}

	/**
	 * @return the idEntidadPagadora
	 */
	public Long getIdEntidadPagadora() {
		return idEntidadPagadora;
	}

	/**
	 * @param idEntidadPagadora the idEntidadPagadora to set
	 */
	public void setIdEntidadPagadora(Long idEntidadPagadora) {
		this.idEntidadPagadora = idEntidadPagadora;
	}

	/**
	 * @return the identificadorAnteEntidadPagadora
	 */
	public String getIdentificadorAnteEntidadPagadora() {
		return identificadorAnteEntidadPagadora;
	}

	/**
	 * @param identificadorAnteEntidadPagadora the identificadorAnteEntidadPagadora to set
	 */
	public void setIdentificadorAnteEntidadPagadora(String identificadorAnteEntidadPagadora) {
		this.identificadorAnteEntidadPagadora = identificadorAnteEntidadPagadora;
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
	 * @return the resultadoValidacion
	 */
	public ResultadoGeneralValidacionEnum getResultadoValidacion() {
		return resultadoValidacion;
	}

	/**
	 * @param resultadoValidacion the resultadoValidacion to set
	 */
	public void setResultadoValidacion(ResultadoGeneralValidacionEnum resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}

	/**
	 * @return the labora
	 */
	public Boolean getLabora() {
		return labora;
	}

	/**
	 * @param labora the labora to set
	 */
	public void setLabora(Boolean labora) {
		this.labora = labora;
	}

	/**
	 * @return the salarioMensualBeneficiario
	 */
	public BigDecimal getSalarioMensualBeneficiario() {
		return salarioMensualBeneficiario;
	}

	/**
	 * @param salarioMensualBeneficiario the salarioMensualBeneficiario to set
	 */
	public void setSalarioMensualBeneficiario(BigDecimal salarioMensualBeneficiario) {
		this.salarioMensualBeneficiario = salarioMensualBeneficiario;
	}

	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * @param primerNombre the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * @param segundoNombre the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * @return the listaChequeo
	 */
	public List<ItemChequeoDTO> getListaChequeo() {
		return listaChequeo;
	}

	/**
	 * @param listaChequeo the listaChequeo to set
	 */
	public void setListaChequeo(List<ItemChequeoDTO> listaChequeo) {
		this.listaChequeo = listaChequeo;
	}

	/**
	 * @return the fechaAfiliacion
	 */
	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	/**
	 * @param fechaAfiliacion the fechaAfiliacion to set
	 */
	public void setFechaAfiliacion(Date fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	/**
	 * @return the idBeneficiario
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @param idBeneficiario the idBeneficiario to set
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

    /**
	 * @return the medioDePago
	 */
	public MedioDePagoModeloDTO getMedioDePago() {
		return medioDePago;
	}

	/**
	 * @param medioDePago the medioDePago to set
	 */
	public void setMedioDePago(MedioDePagoModeloDTO medioDePago) {
		this.medioDePago = medioDePago;
	}

	/**
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    /**
     * @param idRolAfiliado the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the oportunidadPago
     */
    public PeriodoPagoPlanillaEnum getOportunidadPago() {
        return oportunidadPago;
    }

    /**
     * @param oportunidadPago the oportunidadPago to set
     */
    public void setOportunidadPago(PeriodoPagoPlanillaEnum oportunidadPago) {
        this.oportunidadPago = oportunidadPago;
    }

	/**
	 * @return the fechaRetiro
	 */
	public Date getFechaRetiro() {
		return fechaRetiro;
	}

	/**
	 * @param fechaRetiro the fechaRetiro to set
	 */
	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

    /**
     * @return the municipioDesempenioLabores
     */
    public Short getMunicipioDesempenioLabores() {
        return municipioDesempenioLabores;
    }

    /**
     * @param municipioDesempenioLabores the municipioDesempenioLabores to set
     */
    public void setMunicipioDesempenioLabores(Short municipioDesempenioLabores) {
        this.municipioDesempenioLabores = municipioDesempenioLabores;
    }

    /**
     * @return the omitirValidaciones
     */
    public Boolean getOmitirValidaciones() {
        return omitirValidaciones;
    }

    /**
     * @param omitirValidaciones the omitirValidaciones to set
     */
    public void setOmitirValidaciones(Boolean omitirValidaciones) {
        this.omitirValidaciones = omitirValidaciones;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdentificacionUbicacionPersonaDTO [persona=");
		builder.append(persona);
		builder.append(", ubicacion=");
		builder.append(ubicacion);
		builder.append(", autorizacionUsoDatosPersonales=");
		builder.append(autorizacionUsoDatosPersonales);
		builder.append(", resideSectorRural=");
		builder.append(resideSectorRural);
		builder.append(", claseIndependiente=");
		builder.append(claseIndependiente);
		builder.append(", porcentajeAportes=");
		builder.append(porcentajeAportes);
		builder.append(", valorMesadaSalarioIngresos=");
		builder.append(valorMesadaSalarioIngresos);
		builder.append(", idPagadorPension=");
		builder.append(idPagadorPension);
		builder.append(", idEntidadPagadora=");
		builder.append(idEntidadPagadora);
		builder.append(", identificadorAnteEntidadPagadora=");
		builder.append(identificadorAnteEntidadPagadora);
		builder.append(", idGrupoFamiliar=");
		builder.append(idGrupoFamiliar);
		builder.append(", resultadoValidacion=");
		builder.append(resultadoValidacion);
		builder.append(", labora=");
		builder.append(labora);
		builder.append(", salarioMensualBeneficiario=");
		builder.append(salarioMensualBeneficiario);
		builder.append(", primerNombre=");
		builder.append(primerNombre);
		builder.append(", segundoNombre=");
		builder.append(segundoNombre);
		builder.append(", primerApellido=");
		builder.append(primerApellido);
		builder.append(", segundoApellido=");
		builder.append(segundoApellido);
		builder.append(", listaChequeo=");
		builder.append(listaChequeo);
		builder.append(", fechaAfiliacion=");
		builder.append(fechaAfiliacion);
		builder.append(", idBeneficiario=");
		builder.append(idBeneficiario);
		builder.append(", medioDePago=");
		builder.append(medioDePago);
		builder.append(", idRolAfiliado=");
		builder.append(idRolAfiliado);
		builder.append(", motivoDesafiliacion=");
		builder.append(motivoDesafiliacion);
		builder.append(", oportunidadPago=");
		builder.append(oportunidadPago);
		builder.append(", fechaRetiro=");
		builder.append(fechaRetiro);
		builder.append(", omitirValidaciones=");
		builder.append(omitirValidaciones);
		builder.append(", municipioDesempenioLabores=");
		builder.append(municipioDesempenioLabores);
		builder.append("]");
		return builder.toString();
	}
    
    
}
