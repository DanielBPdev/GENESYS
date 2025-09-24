package com.asopagos.aportes.composite.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * Clase DTO con los datos manejados temporalmente.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
/**
 * Clase que contiene la lógica para validar
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class AporteManualDTO {

    @Override
	public String toString() {
		return "AporteManualDTO [radicacionDTO=" + radicacionDTO + ", informacionPagoDTO=" + informacionPagoDTO
				+ ", cotizantes=" + cotizantes + ", cotizantesTemporales=" + cotizantesTemporales
				+ ", idRegistroGeneral=" + idRegistroGeneral + ", idTransaccion=" + idTransaccion
				+ ", informacionFaltante=" + informacionFaltante + "]";
	}

	/**
     * Datos de la radicación de la solicitud..
     */
    private RadicacionAporteManualDTO radicacionDTO;

    /**
     * Información de pago del aportante.
     */
    private InformacionPagoDTO informacionPagoDTO;

    /**
     * Cotizantes (reporte detallado)
     */
    private List<CotizanteDTO> cotizantes;

    /**
     * Cotizantes (reporte detallado) temporal.
     */
    private List<CotizanteDTO> cotizantesTemporales;

    /**
     * Id del registro general almacenado en la primera simulación.
     */
    private Long idRegistroGeneral;
    
    /**
     * Id de la última transacción del registro general
     * */
    private Long idTransaccion;

    /**
     * Información faltante.
     */
    private GestionInformacionFaltanteDTO informacionFaltante;

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param solicitud
     *        entidad a convertir.
     */
    public RegistroGeneralModeloDTO convertToDTO() {
        RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();
        
        registroGeneralDTO.setCuentaBancariaRecaudo(this.getRadicacionDTO().getCuentaBancariaRecaudo());

        if (this.getInformacionPagoDTO().getClaseAportante() != null) {
            registroGeneralDTO.setClaseAportante(this.getInformacionPagoDTO().getClaseAportante().getCodigo());
        }
        // TODO
        // registroGeneralDTO.setCodigoEntidadFinanciera(this.getRadicacionDTO().getCodigoFinanciero());
        registroGeneralDTO.setCodSucursal(this.getInformacionPagoDTO().getCodigoSucursal());
        registroGeneralDTO.setDigVerAportante(this.getRadicacionDTO().getDv());
        registroGeneralDTO.setFechaRecaudo(this.getRadicacionDTO().getFechaRecepcionAporte());
        if (this.getRadicacionDTO().getTipo() != null) {
            registroGeneralDTO.setEsAportePensionados(
                    (TipoAfiliadoEnum.PENSIONADO.name().equals(this.getRadicacionDTO().getTipo().name())) ? true : false);
        }
        registroGeneralDTO.setModalidadPlanilla(this.getInformacionPagoDTO().getModalidadPlantilla());
        StringBuilder nombreAportante = new StringBuilder();
        if (this.getRadicacionDTO().getRazonSocialAportante() == null) {
            nombreAportante.append(this.getRadicacionDTO().getPrimerNombre() + " ");
            nombreAportante
                    .append(this.getRadicacionDTO().getSegundoNombre() != null ? this.getRadicacionDTO().getSegundoNombre() + " " : "");
            nombreAportante.append(this.getRadicacionDTO().getPrimerApellido() + " ");
            nombreAportante
                    .append(this.getRadicacionDTO().getSegundoApellido() != null ? this.getRadicacionDTO().getSegundoApellido() : "");
        }
        else {
            nombreAportante.append(this.getRadicacionDTO().getRazonSocialAportante());
        }
        if (TipoAfiliadoEnum.PENSIONADO.name().equals(this.getRadicacionDTO().getTipo().name()) ||
                TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name().equals(this.getRadicacionDTO().getTipo().name())){
            registroGeneralDTO.setOutPrimerNombreAportante(this.getRadicacionDTO().getPrimerNombre());
            registroGeneralDTO.setOutPrimerApellidoAportante(this.getRadicacionDTO().getPrimerApellido());
            registroGeneralDTO.setOutSegundoNombreAportante(this.getRadicacionDTO().getSegundoNombre());
            registroGeneralDTO.setOutSegundoApellidoAportante(this.getRadicacionDTO().getSegundoApellido());
        }
        
        registroGeneralDTO.setNombreAportante(nombreAportante.toString());
        registroGeneralDTO.setNomSucursal(this.getInformacionPagoDTO().getNombreSucursal());
        registroGeneralDTO.setNumeroIdentificacionAportante(this.getRadicacionDTO().getNumeroIdentificacion());
        registroGeneralDTO.setOperadorInformacion(this.getInformacionPagoDTO().getCodigoOperador());
        if (this.getRadicacionDTO().getPeriodoPago() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            registroGeneralDTO.setPeriodoAporte(dateFormat.format(this.getRadicacionDTO().getPeriodoPago()));
        }

        registroGeneralDTO.setTipoIdentificacionAportante(this.getRadicacionDTO().getTipoIdentificacion());
        if (this.getInformacionPagoDTO().getTipoPlanilla() != null) {
            registroGeneralDTO.setTipoPlanilla(this.getInformacionPagoDTO().getTipoPlanilla().getCodigo());
        }
        BigDecimal mora = this.getRadicacionDTO().getMoraAporte();
        if (mora == null) {
            mora = BigDecimal.ZERO;
        }
        registroGeneralDTO.setValorIntMora(mora);
        registroGeneralDTO.setValTotalApoObligatorio(this.getRadicacionDTO().getMontoAporte());
        registroGeneralDTO.setOutFinalizadoProcesoManual(Boolean.FALSE);
        registroGeneralDTO.setEsSimulado(Boolean.FALSE);
        registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
        registroGeneralDTO.setNumPlanilla(this.getInformacionPagoDTO().getNumeroRadicacion());
        registroGeneralDTO.setDireccion(this.getInformacionPagoDTO().getDireccion());
        if (this.getInformacionPagoDTO().getCodigoMunicipio() != null) {
            registroGeneralDTO.setCodCiudad(this.getInformacionPagoDTO().getCodigoMunicipio().substring(2, 5));
        }
        registroGeneralDTO.setCodDepartamento(this.getInformacionPagoDTO().getCodigoDepartamento());
        registroGeneralDTO.setEmail(this.getInformacionPagoDTO().getCorreoElectronico());
        if (this.getInformacionPagoDTO().getTelefono() != null) {
            registroGeneralDTO.setTelefono(new Long(this.getInformacionPagoDTO().getTelefono()));
        }
        if (this.getInformacionPagoDTO().getFax() != null) {
            registroGeneralDTO.setFax(new Long(this.getInformacionPagoDTO().getFax()));
        }
        if (this.getInformacionPagoDTO().getNaturalezaJuridica() != null) {
            registroGeneralDTO.setNaturalezaJuridica(this.getInformacionPagoDTO().getNaturalezaJuridica().getCodigo().shortValue());
        }
        registroGeneralDTO.setFechaMatricula(this.getInformacionPagoDTO().getFechaMatriculaMercantil());
        registroGeneralDTO.setDiasMora(this.getInformacionPagoDTO().getDiasMora()!=null?this.getInformacionPagoDTO().getDiasMora().shortValue():null);
        registroGeneralDTO.setFechaPlanilla(this.getInformacionPagoDTO().getFechaPagoPlantilla()!=null? new Date(this.getInformacionPagoDTO().getFechaPagoPlantilla()):null);
        registroGeneralDTO.setFormaPresentacion(this.getInformacionPagoDTO().getFormaPresentacion()!=null?this.getInformacionPagoDTO().getFormaPresentacion().getCodigo():null);
        registroGeneralDTO.setCantEmpleados(this.getInformacionPagoDTO().getNumeroEmpleados()!=null?this.getInformacionPagoDTO().getNumeroEmpleados().intValue():null);
        registroGeneralDTO.setCantAfiliados(this.getInformacionPagoDTO().getNumeroAfiliadosAdministradora()!=null?this.getInformacionPagoDTO().getNumeroAfiliadosAdministradora().intValue():null);
        registroGeneralDTO.setTipoPersona(this.getInformacionPagoDTO().getTipoPersona()!=null?this.getInformacionPagoDTO().getTipoPersona().getCodigo():null);
        
        registroGeneralDTO.setTransaccion(this.getIdTransaccion());
        
        return registroGeneralDTO; 
    }

    /**
     * Método que retorna el valor de radicacionDTO.
     * 
     * @return valor de radicacionDTO.
     */
    public RadicacionAporteManualDTO getRadicacionDTO() {
        return radicacionDTO;
    }

    /**
     * Método encargado de modificar el valor de radicacionDTO.
     * 
     * @param valor
     *        para modificar radicacionDTO.
     */
    public void setRadicacionDTO(RadicacionAporteManualDTO radicacionDTO) {
        this.radicacionDTO = radicacionDTO;
    }

    /**
     * Método que retorna el valor de informacionPagoDTO.
     * 
     * @return valor de informacionPagoDTO.
     */
    public InformacionPagoDTO getInformacionPagoDTO() {
        return informacionPagoDTO;
    }

    /**
     * Método encargado de modificar el valor de informacionPagoDTO.
     * 
     * @param valor
     *        para modificar informacionPagoDTO.
     */
    public void setInformacionPagoDTO(InformacionPagoDTO informacionPagoDTO) {
        this.informacionPagoDTO = informacionPagoDTO;
    }

    /**
     * Método que retorna el valor de cotizantes.
     * 
     * @return valor de cotizantes.
     */
    public List<CotizanteDTO> getCotizantes() {
        return cotizantes;
    }

    /**
     * Método encargado de modificar el valor de cotizantes.
     * 
     * @param valor
     *        para modificar cotizantes.
     */
    public void setCotizantes(List<CotizanteDTO> cotizantes) {
        this.cotizantes = cotizantes;
    }

    /**
     * Método que retorna el valor de cotizantesTemporales.
     * 
     * @return valor de cotizantesTemporales.
     */
    public List<CotizanteDTO> getCotizantesTemporales() {
        return cotizantesTemporales;
    }

    /**
     * Método encargado de modificar el valor de cotizantesTemporales.
     * 
     * @param valor
     *        para modificar cotizantesTemporales.
     */
    public void setCotizantesTemporales(List<CotizanteDTO> cotizantesTemporales) {
        this.cotizantesTemporales = cotizantesTemporales;
    }

    /**
     * Método que retorna el valor de idRegistroGeneral.
     * 
     * @return valor de idRegistroGeneral.
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * Método encargado de modificar el valor de idRegistroGeneral.
     * 
     * @param valor
     *        para modificar idRegistroGeneral.
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * Método que retorna el valor de informacionFaltante.
     * 
     * @return valor de informacionFaltante.
     */
    public GestionInformacionFaltanteDTO getInformacionFaltante() {
        return informacionFaltante;
    }

    /**
     * Método encargado de modificar el valor de informacionFaltante.
     * 
     * @param valor
     *        para modificar informacionFaltante.
     */
    public void setInformacionFaltante(GestionInformacionFaltanteDTO informacionFaltante) {
        this.informacionFaltante = informacionFaltante;
    }

    /**
     * @return the idTransaccion
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion the idTransaccion to set
     */
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

}
