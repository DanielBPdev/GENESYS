package com.asopagos.dto.aportes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * 
 * DTO que contiene los datos de la corrección.
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class CorreccionAportanteDTO implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Tipo Aportante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoAportante;
    /**
     * Tipo de identificación del aportante.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación del aportante.
     */
    private String numeroIdentificacion;
    /**
     * Empleador es una persona
     */
    private Boolean empleadorPersona;
    /**
     * Primer nombre del aportante
     */
    private String primerNombre;
    /**
     * Segundo nombre del aportante
     */
    private String segundoNombre;
    /**
     * Primer apellido del aportante
     */
    private String primerApellido;
    /**
     * Segundo apellido del aportante
     */
    private String segundoApellido;
    /**
     * Tipo de persona
     */
    private TipoPersonaEnum tipoPersona;
    /**
     * Se acoge a beneficios
     */
    private Boolean seAcogeBeneficios;
    /**
     * Clase de aportante
     */
    private ClaseAportanteEnum claseAportante;
    /**
     * Origen del aportante
     */
    private OrigenAporteEnum origenAportante;
    /**
     * Periodo de pago del aportante
     */
    private Long periodoPago;
    /**
     * Comentario del aportante
     */
    private String comentario;
    /**
     * Listado de cotizantes nuevos
     */
    private List<CotizanteDTO> cotizantesBusqueda;

    /**
     * Listado de cotizantes nuevos
     */
    private List<CotizanteDTO> cotizantesNuevos;
    /**
     * Digito de verificación
     */
    private Short dv;
    /**
     * Razón social
     */
    private String razonSocial;

    /**
     * Codigo financiero.
     */
    private String codigoFinanciero;
    /**
     * Id del registro general.
     */
    private Long idRegistroGeneral;
    /**
     * Indica el aporte obligatorio del aporte
     */
    private BigDecimal valTotalApoObligatorio;
    /**
     * Indica el valor interés de mora para un pensionado
     */
    private BigDecimal valorIntMora;
    /**
     * Atributo que contiene el código de la sucursal.
     */
    private String codigoSucursal;
    /**
     * Atributo que contiene el nombre de la sucursal.
     */
    private String nombreSucursal;
    
    /**
     * Marca de Referencia que indica que el aportante es "pagador por terceros"
     * para el aporte asociado
     */
    private Boolean pagadorPorTerceros;

    // Identificador para alimentar el aporte que vbiene por correccion masiva
    private Long idAporte;

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud
     *        entidad a convertir.
     */
    public RegistroGeneralModeloDTO convertToDTO() {
        RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();
        registroGeneralDTO.setClaseAportante(this.getClaseAportante()!=null?this.getClaseAportante().getCodigo():null);
        registroGeneralDTO.setDigVerAportante(this.getDv());
        registroGeneralDTO.setFechaRecaudo(new Date().getTime());
        registroGeneralDTO.setEsAportePensionados((TipoAfiliadoEnum.PENSIONADO.name().equals(this.getTipoAportante()!=null?this.getTipoAportante().name():"")) ? true : false);
        //registroGeneralDTO.setModalidadPlanilla();
        StringBuilder nombreAportante = new StringBuilder();
        if (this.getRazonSocial() == null) {
            nombreAportante.append(this.getPrimerNombre() + " ");
            nombreAportante.append(this.getSegundoNombre() != null ? this.getSegundoNombre() + " " : "");
            nombreAportante.append(this.getPrimerApellido() + " ");
            nombreAportante.append(this.getSegundoApellido() != null ? this.getSegundoApellido() : "");
        }
        else {
            nombreAportante.append(this.getRazonSocial());
        }
        if (TipoAfiliadoEnum.PENSIONADO.name().equals(this.getTipoAportante().name()) ||
                TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name().equals(this.getTipoAportante().name())){
            registroGeneralDTO.setOutPrimerNombreAportante(this.getPrimerNombre());
            registroGeneralDTO.setOutPrimerApellidoAportante(this.getPrimerApellido());
            registroGeneralDTO.setOutSegundoNombreAportante(this.getSegundoNombre());
            registroGeneralDTO.setOutSegundoApellidoAportante(this.getSegundoApellido());
        }
        registroGeneralDTO.setNombreAportante(nombreAportante.toString());
        //registroGeneralDTO.setNomSucursal(this.getNombreSucursal());
        registroGeneralDTO.setNumeroIdentificacionAportante(this.getNumeroIdentificacion());
        registroGeneralDTO.setTipoIdentificacionAportante(this.getTipoIdentificacion());
        registroGeneralDTO.setValTotalApoObligatorio(this.getValTotalApoObligatorio()!=null?this.getValTotalApoObligatorio():BigDecimal.ZERO);
        registroGeneralDTO.setValorIntMora(this.getValorIntMora()!=null?this.getValorIntMora():BigDecimal.ZERO);
        //registroGeneralDTO.setOperadorInformacion(this.getInformacionPagoDTO().getCodigoOperador());
        if (this.getPeriodoPago() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            registroGeneralDTO.setPeriodoAporte(dateFormat.format(this.getPeriodoPago()));
        }
        //        /if(this.getInformacionPagoDTO().getTipoPlanilla()!=null){
        //            registroGeneralDTO.setTipoPlanilla(this.getInformacionPagoDTO().getTipoPlanilla().getCodigo());
        //        }
        registroGeneralDTO.setOutFinalizadoProcesoManual(Boolean.FALSE);
        registroGeneralDTO.setEsSimulado(Boolean.FALSE);
        registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
        registroGeneralDTO.setNomSucursal(this.getNombreSucursal());
        registroGeneralDTO.setCodSucursal(this.getCodigoSucursal());
        //registroGeneralDTO.setNumPlanilla();
        return registroGeneralDTO;
    }

    /**
     * Método que retorna el valor de tipoAportante.
     * 
     * @return valor de tipoAportante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return tipoAportante;
    }

    /**
     * Método encargado de modificar el valor de tipoAportante.
     * 
     * @param valor
     *        para modificar tipoAportante.
     */
    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
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
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * 
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * 
     * @param valor
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de empleadorPersona.
     * 
     * @return valor de empleadorPersona.
     */
    public Boolean getEmpleadorPersona() {
        return empleadorPersona;
    }

    /**
     * Método encargado de modificar el valor de empleadorPersona.
     * 
     * @param valor
     *        para modificar empleadorPersona.
     */
    public void setEmpleadorPersona(Boolean empleadorPersona) {
        this.empleadorPersona = empleadorPersona;
    }

    /**
     * Método que retorna el valor de primerNombre.
     * 
     * @return valor de primerNombre.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     * 
     * @param valor
     *        para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método que retorna el valor de segundoNombre.
     * 
     * @return valor de segundoNombre.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     * 
     * @param valor
     *        para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método que retorna el valor de primerApellido.
     * 
     * @return valor de primerApellido.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     * 
     * @param valor
     *        para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método que retorna el valor de segundoApellido.
     * 
     * @return valor de segundoApellido.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     * 
     * @param valor
     *        para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Método que retorna el valor de tipoPersona.
     * 
     * @return valor de tipoPersona.
     */
    public TipoPersonaEnum getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Método encargado de modificar el valor de tipoPersona.
     * 
     * @param valor
     *        para modificar tipoPersona.
     */
    public void setTipoPersona(TipoPersonaEnum tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * Método que retorna el valor de seAcogeBeneficios.
     * 
     * @return valor de seAcogeBeneficios.
     */
    public Boolean getSeAcogeBeneficios() {
        return seAcogeBeneficios;
    }

    /**
     * Método encargado de modificar el valor de seAcogeBeneficios.
     * 
     * @param valor
     *        para modificar seAcogeBeneficios.
     */
    public void setSeAcogeBeneficios(Boolean seAcogeBeneficios) {
        this.seAcogeBeneficios = seAcogeBeneficios;
    }

    /**
     * Método que retorna el valor de claseAportante.
     * 
     * @return valor de claseAportante.
     */
    public ClaseAportanteEnum getClaseAportante() {
        return claseAportante;
    }

    /**
     * Método encargado de modificar el valor de claseAportante.
     * 
     * @param valor
     *        para modificar claseAportante.
     */
    public void setClaseAportante(ClaseAportanteEnum claseAportante) {
        this.claseAportante = claseAportante;
    }

    /**
     * Método que retorna el valor de origenAportante.
     * 
     * @return valor de origenAportante.
     */
    public OrigenAporteEnum getOrigenAportante() {
        return origenAportante;
    }

    /**
     * Método encargado de modificar el valor de origenAportante.
     * 
     * @param valor
     *        para modificar origenAportante.
     */
    public void setOrigenAportante(OrigenAporteEnum origenAportante) {
        this.origenAportante = origenAportante;
    }

    /**
     * Método que retorna el valor de periodoPago.
     * 
     * @return valor de periodoPago.
     */
    public Long getPeriodoPago() {
        return periodoPago;
    }

    /**
     * Método encargado de modificar el valor de periodoPago.
     * 
     * @param valor
     *        para modificar periodoPago.
     */
    public void setPeriodoPago(Long periodoPago) {
        this.periodoPago = periodoPago;
    }

    /**
     * Método que retorna el valor de comentario.
     * 
     * @return valor de comentario.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Método encargado de modificar el valor de comentario.
     * 
     * @param valor
     *        para modificar comentario.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Método que retorna el valor de cotizantesBusqueda.
     * 
     * @return valor de cotizantesBusqueda.
     */
    public List<CotizanteDTO> getCotizantesBusqueda() {
        return cotizantesBusqueda;
    }

    /**
     * Método encargado de modificar el valor de cotizantesBusqueda.
     * 
     * @param valor
     *        para modificar cotizantesBusqueda.
     */
    public void setCotizantesBusqueda(List<CotizanteDTO> cotizantesBusqueda) {
        this.cotizantesBusqueda = cotizantesBusqueda;
    }

    /**
     * Método que retorna el valor de cotizantesNuevos.
     * 
     * @return valor de cotizantesNuevos.
     */
    public List<CotizanteDTO> getCotizantesNuevos() {
        return cotizantesNuevos;
    }

    /**
     * Método encargado de modificar el valor de cotizantesNuevos.
     * 
     * @param valor
     *        para modificar cotizantesNuevos.
     */
    public void setCotizantesNuevos(List<CotizanteDTO> cotizantesNuevos) {
        this.cotizantesNuevos = cotizantesNuevos;
    }

    /**
     * Método que retorna el valor de dv.
     * 
     * @return valor de dv.
     */
    public Short getDv() {
        return dv;
    }

    /**
     * Método que retorna el valor de razonSocial.
     * 
     * @return valor de razonSocial.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Método encargado de modificar el valor de dv.
     * 
     * @param valor
     *        para modificar dv.
     */
    public void setDv(Short dv) {
        this.dv = dv;
    }

    /**
     * Método encargado de modificar el valor de razonSocial.
     * 
     * @param valor
     *        para modificar razonSocial.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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
     * @param valor
     *        para modificar codigoFinanciero.
     */
    public void setCodigoFinanciero(String codigoFinanciero) {
        this.codigoFinanciero = codigoFinanciero;
    }

    /**
     * Método que retorna el valor de idRegistroGeneral.
     * @return valor de idRegistroGeneral.
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * Método encargado de modificar el valor de idRegistroGeneral.
     * @param valor
     *        para modificar idRegistroGeneral.
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the valTotalApoObligatorio
     */
    public BigDecimal getValTotalApoObligatorio() {
        return valTotalApoObligatorio;
    }

    /**
     * @param valTotalApoObligatorio the valTotalApoObligatorio to set
     */
    public void setValTotalApoObligatorio(BigDecimal valTotalApoObligatorio) {
        this.valTotalApoObligatorio = valTotalApoObligatorio;
    }

    /**
     * @return the valorIntMora
     */
    public BigDecimal getValorIntMora() {
        return valorIntMora;
    }

    /**
     * @param valorIntMora the valorIntMora to set
     */
    public void setValorIntMora(BigDecimal valorIntMora) {
        this.valorIntMora = valorIntMora;
    }

    /**
     * @return the pagadorPorTerceros
     */
    public Boolean getPagadorPorTerceros() {
        return pagadorPorTerceros;
    }

    /**
     * @param pagadorPorTerceros the pagadorPorTerceros to set
     */
    public void setPagadorPorTerceros(Boolean pagadorPorTerceros) {
        this.pagadorPorTerceros = pagadorPorTerceros;
    }

    /**
     * @return the codigoSucursal
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * @param codigoSucursal the codigoSucursal to set
     */
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * @return the nombreSucursal
     */
    public String getNombreSucursal() {
        return nombreSucursal;
    }
    public Long getIdAporte() {
        return this.idAporte;
    }

    public void setIdAporte(Long idAporte) {
        this.idAporte = idAporte;
    }
    
    @Override
    public String toString() {
        return "{" +
            " tipoAportante='" + getTipoAportante() + "'" +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", empleadorPersona='" + getEmpleadorPersona() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", tipoPersona='" + getTipoPersona() + "'" +
            ", seAcogeBeneficios='" + getSeAcogeBeneficios() + "'" +
            ", claseAportante='" + getClaseAportante() + "'" +
            ", origenAportante='" + getOrigenAportante() + "'" +
            ", periodoPago='" + getPeriodoPago() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", cotizantesBusqueda='" + getCotizantesBusqueda() + "'" +
            ", cotizantesNuevos='" + getCotizantesNuevos() + "'" +
            ", dv='" + getDv() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", codigoFinanciero='" + getCodigoFinanciero() + "'" +
            ", idRegistroGeneral='" + getIdRegistroGeneral() + "'" +
            ", valTotalApoObligatorio='" + getValTotalApoObligatorio() + "'" +
            ", valorIntMora='" + getValorIntMora() + "'" +
            ", codigoSucursal='" + getCodigoSucursal() + "'" +
            ", nombreSucursal='" + getNombreSucursal() + "'" +
            ", pagadorPorTerceros='" + getPagadorPorTerceros() + "'" +
            ", idAporte='" + getIdAporte() + "'" +
            "}";
    }
}