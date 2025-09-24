/**
 * 
 */
package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO con los datos para radicar una solicitud de aporte manual.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class RadicacionAporteManualDTO implements Serializable{

    /**
     * Tipo de identificación del aportante.
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación del aportante.
     */
    private String numeroIdentificacion;

    /**
     * Digito de verificación en caso de que sea NIT.
     */
    private Short dv;
    /**
     * Primer nombre del aportante(caso Persona).
     */
    private String primerNombre;
    /**
     * Segundo nombre del aportante(caso Persona).
     */
    private String segundoNombre;
    /**
     * Primer apellido del aportante (caso Persona).
     */
    private String primerApellido;
    /**
     * Segundo apellido del aportante (caso Persona).
     */
    private String segundoApellido;
    /**
     * Razón social del aportante (caso Empleador)
     */
    private String razonSocialAportante;
    /**
     * Tipo de aportante.
     */
    private TipoSolicitanteMovimientoAporteEnum tipo;
    /**
     * Fecha de recepción del aporte.
     */
    private Long fechaRecepcionAporte;
    /**
     * Origen del aporte.
     */
    private OrigenAporteEnum origenAporte;

    /**
     * Caja de compensación.
     */
    private Integer cajaAporte;
    /**
     * Sede de la caja.
     */
    private Long sedeCaja;
    /**
     * Código financiero.
     */
    private String codigoFinanciero;
    /**
     * Monto del aporte.
     */
    private BigDecimal montoAporte;
    /**
     * Mora del aporte
     */
    private BigDecimal moraAporte;
    /**
     * Total del aporte.
     */
    private BigDecimal totalAporte;
    /**
     * Periodo de pago del aporte.
     */
    private Long periodoPago;

    /**
     * Observaciones del aporte.
     */
    private String observaciones;

    /**
     * Variable que indica si se trata de un empleador o una empresa.
     */
    private Boolean empleador;

    /**
     * Variable que indica para el caso de dependientes o pensionados si son pagadores por tercero.
     */
    private DecisionSiNoEnum pagadorTercero;

    /**
     * Variable que indica el periodo de pago en terminos de AAAA-MM
     */
    private String periodoPagoString;
    
    /**
     * Variable que indica el id de la cuentaBancariaRecaudo
     */
    private Integer cuentaBancariaRecaudo;

    /**
     * Lista que contiene los documentos adjuntos.
     */
    private List<DocumentoAdministracionEstadoSolicitudDTO> documentos;

    /**
     * Método encargado de convertir de entidad al aporte DTO.
     * @return dto convertido.
     */
    public AporteGeneralModeloDTO convertToAporteDTO() {
        AporteGeneralModeloDTO aporteGeneralDTO = new AporteGeneralModeloDTO();
        aporteGeneralDTO.setFechaRecaudo(this.getFechaRecepcionAporte());
        Calendar periodoAporte = Calendar.getInstance();
        periodoAporte.setTimeInMillis(this.getPeriodoPago());
        aporteGeneralDTO.setPeriodoAporte(periodoAporte.get(Calendar.YEAR) + "-" + periodoAporte.get(Calendar.MONTH));
        aporteGeneralDTO.setValorTotalAporteObligatorio(this.getMontoAporte());
        aporteGeneralDTO.setValorInteresesMora(this.getMoraAporte());
        aporteGeneralDTO.setCuentaBancariaRecaudo(this.getCuentaBancariaRecaudo());
        //TODO validar si el codigo puede o no ser alfanúmerico para cmabiar el tipo en la entidad
        //aporteGeneralDTO.setCodigoEntidadFinanciera(this.getCodigoFinanciero());
        //TODO faltan: origen, caja origen, sede caja, tipo de aportante o afiliado, pagador tercero, comentarios
        aporteGeneralDTO.setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum.MANUAL);
        return aporteGeneralDTO;

    }

    /**
     * Método encargado de convertir un DTO de aporte geeral a una Radicación
     * @param aporteDTO
     *        aporte a convertir.
     */
    public void convertToDTO(AporteGeneralModeloDTO aporteDTO) {
        this.setFechaRecepcionAporte(aporteDTO.getFechaRecaudo());
        this.setPeriodoPagoString(aporteDTO.getPeriodoAporte());
        this.setMontoAporte(aporteDTO.getValorTotalAporteObligatorio());
        this.setMoraAporte(aporteDTO.getValorInteresesMora());
        this.setCuentaBancariaRecaudo(aporteDTO.getCuentaBancariaRecaudo());
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Integer getCuentaBancariaRecaudo() {
        return cuentaBancariaRecaudo;
    }

    public void setCuentaBancariaRecaudo(Integer cuentaBancariaRecaudo) {
        this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;
    }

    
    

    /**
     * Método que retorna el valor de dv.
     * @return valor de dv.
     */
    public Short getDv() {
        return dv;
    }

    /**
     * Método encargado de modificar el valor de dv.
     * @param valor para modificar dv.
     */
    public void setDv(Short dv) {
        this.dv = dv;
    }

    /**
     * Método que retorna el valor de primerNombre.
     * @return valor de primerNombre.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     * @param valor para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método que retorna el valor de segundoNombre.
     * @return valor de segundoNombre.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     * @param valor para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método que retorna el valor de primerApellido.
     * @return valor de primerApellido.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     * @param valor para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método que retorna el valor de segundoApellido.
     * @return valor de segundoApellido.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     * @param valor para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Método que retorna el valor de razonSocialAportante.
     * @return valor de razonSocialAportante.
     */
    public String getRazonSocialAportante() {
        return razonSocialAportante;
    }

    /**
     * Método encargado de modificar el valor de razonSocialAportante.
     * @param valor para modificar razonSocialAportante.
     */
    public void setRazonSocialAportante(String razonSocialAportante) {
        this.razonSocialAportante = razonSocialAportante;
    }

    /**
     * Método que retorna el valor de tipo.
     * @return valor de tipo.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipo() {
        return tipo;
    }

    /**
     * Método encargado de modificar el valor de tipo.
     * @param valor para modificar tipo.
     */
    public void setTipo(TipoSolicitanteMovimientoAporteEnum tipo) {
        this.tipo = tipo;
    }

    /**
     * Método que retorna el valor de fechaRecepcionAporte.
     * @return valor de fechaRecepcionAporte.
     */
    public Long getFechaRecepcionAporte() {
        return fechaRecepcionAporte;
    }

    /**
     * Método encargado de modificar el valor de fechaRecepcionAporte.
     * @param valor para modificar fechaRecepcionAporte.
     */
    public void setFechaRecepcionAporte(Long fechaRecepcionAporte) {
        this.fechaRecepcionAporte = fechaRecepcionAporte;
    }

    /**
     * Método que retorna el valor de origenAporte.
     * @return valor de origenAporte.
     */
    public OrigenAporteEnum getOrigenAporte() {
        return origenAporte;
    }

    /**
     * Método encargado de modificar el valor de origenAporte.
     * @param valor para modificar origenAporte.
     */
    public void setOrigenAporte(OrigenAporteEnum origenAporte) {
        this.origenAporte = origenAporte;
    }

    /**
     * Método que retorna el valor de cajaAporte.
     * @return valor de cajaAporte.
     */
    public Integer getCajaAporte() {
        return cajaAporte;
    }

    /**
     * Método encargado de modificar el valor de cajaAporte.
     * @param valor para modificar cajaAporte.
     */
    public void setCajaAporte(Integer cajaAporte) {
        this.cajaAporte = cajaAporte;
    }

    /**
     * Método que retorna el valor de sedeCaja.
     * @return valor de sedeCaja.
     */
    public Long getSedeCaja() {
        return sedeCaja;
    }

    /**
     * Método encargado de modificar el valor de sedeCaja.
     * @param valor para modificar sedeCaja.
     */
    public void setSedeCaja(Long sedeCaja) {
        this.sedeCaja = sedeCaja;
    }

    /**
     * Método que retorna el valor de codigoFinanciero.
     * @return valor de codigoFinanciero.
     */
    public String getCodigoFinanciero() {
        return codigoFinanciero;
    }

    /**
     * Método encargado de modificar el valor de codigoFinanciero.
     * @param valor para modificar codigoFinanciero.
     */
    public void setCodigoFinanciero(String codigoFinanciero) {
        this.codigoFinanciero = codigoFinanciero;
    }

    /**
     * Método que retorna el valor de montoAporte.
     * @return valor de montoAporte.
     */
    public BigDecimal getMontoAporte() {
        return montoAporte;
    }

    /**
     * Método encargado de modificar el valor de montoAporte.
     * @param valor para modificar montoAporte.
     */
    public void setMontoAporte(BigDecimal montoAporte) {
        this.montoAporte = montoAporte;
    }

    /**
     * Método que retorna el valor de moraAporte.
     * @return valor de moraAporte.
     */
    public BigDecimal getMoraAporte() {
        return moraAporte;
    }

    /**
     * Método encargado de modificar el valor de moraAporte.
     * @param valor para modificar moraAporte.
     */
    public void setMoraAporte(BigDecimal moraAporte) {
        this.moraAporte = moraAporte;
    }

    /**
     * Método que retorna el valor de totalAporte.
     * @return valor de totalAporte.
     */
    public BigDecimal getTotalAporte() {
        return totalAporte;
    }

    /**
     * Método encargado de modificar el valor de totalAporte.
     * @param valor para modificar totalAporte.
     */
    public void setTotalAporte(BigDecimal totalAporte) {
        this.totalAporte = totalAporte;
    }

    /**
     * Método que retorna el valor de periodoPago.
     * @return valor de periodoPago.
     */
    public Long getPeriodoPago() {
        return periodoPago;
    }

    /**
     * Método encargado de modificar el valor de periodoPago.
     * @param valor para modificar periodoPago.
     */
    public void setPeriodoPago(Long periodoPago) {
        this.periodoPago = periodoPago;
    }

    /**
     * Método que retorna el valor de observaciones.
     * @return valor de observaciones.
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Método encargado de modificar el valor de observaciones.
     * @param valor para modificar observaciones.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Método que retorna el valor de empleador.
     * @return valor de empleador.
     */
    public Boolean getEmpleador() {
        return empleador;
    }

    /**
     * Método encargado de modificar el valor de empleador.
     * @param valor para modificar empleador.
     */
    public void setEmpleador(Boolean empleador) {
        this.empleador = empleador;
    }

    /**
     * Método que retorna el valor de pagadorTercero.
     * @return valor de pagadorTercero.
     */
    public DecisionSiNoEnum getPagadorTercero() {
        return pagadorTercero;
    }

    /**
     * Método encargado de modificar el valor de pagadorTercero.
     * @param valor para modificar pagadorTercero.
     */
    public void setPagadorTercero(DecisionSiNoEnum pagadorTercero) {
        this.pagadorTercero = pagadorTercero;
    }

    /**
     * Método que retorna el valor de periodoPagoString.
     * @return valor de periodoPagoString.
     */
    public String getPeriodoPagoString() {
        return periodoPagoString;
    }

    /**
     * Método encargado de modificar el valor de periodoPagoString.
     * @param valor para modificar periodoPagoString.
     */
    public void setPeriodoPagoString(String periodoPagoString) {
        this.periodoPagoString = periodoPagoString;
    }

    /**
     * Método que retorna el valor de documentos.
     * @return valor de documentos.
     */
    public List<DocumentoAdministracionEstadoSolicitudDTO> getDocumentos() {
        return documentos;
    }

    /**
     * Método encargado de modificar el valor de documentos.
     * @param valor para modificar documentos.
     */
    public void setDocumentos(List<DocumentoAdministracionEstadoSolicitudDTO> documentos) {
        this.documentos = documentos;
    }

       
}
