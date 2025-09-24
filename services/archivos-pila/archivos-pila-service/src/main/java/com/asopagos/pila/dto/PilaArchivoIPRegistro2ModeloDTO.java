package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.asopagos.constants.QueriesConstants;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro2;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.util.FuncionesValidador;

/**
 * <b>Descripcion:</b> DTO que representa la entidad PilaArchivoIPRegistro2<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class PilaArchivoIPRegistro2ModeloDTO implements Serializable {
    private static final long serialVersionUID = 5817878704696563429L;

    /**
     * Lista de los nombres de campos de la tabla
     */
    private List<String> nombresCampos;

    /**
     * @return the nombresCampos
     */
    public List<String> getNombresCampos() {
        return nombresCampos;
    }

    /**
     * Código identificador de llave primaria del registro tipo 2
     */
    private Long id;

    /**
     * Referencia a la entrada del índice de planilla de Operador de Información para el registro
     */
    private IndicePlanilla indicePlanilla;

    /**
     * Contenido del Registro tipo 2 campo 2: Secuencia
     */
    private Integer secuencia;

    /**
     * Contenido del Registro tipo 2 campo 3: Tipo identificación del pensionado
     */
    private String tipoIdPensionado;

    /**
     * Contenido del Registro tipo 2 campo 4: No. de identificación del pensionado
     */
    private String idPensionado;

    /**
     * Contenido del Registro tipo 2 campo 5: Primer apellido del pensionado
     */
    private String primerApellido;

    /**
     * Contenido del Registro tipo 2 campo 6: Segundo apellido del pensionado
     */
    private String segundoApellido;

    /**
     * Contenido del Registro tipo 2 campo 7: Primer nombre del pensionado
     */
    private String primerNombre;

    /**
     * Contenido del Registro tipo 2 campo 8: Segundo nombre del pensionado
     */
    private String segundoNombre;

    /**
     * Contenido del Registro tipo 2 campo 9: Código del departamento de la ubicación de residencia
     */
    private String codDepartamento;

    /**
     * Contenido del Registro tipo 2 campo 10: Código del municipio de la ubicación de residencia
     */
    private String codMunicipio;

    /**
     * Contenido del Registro tipo 2 campo 11: Tarifa.
     */
    private BigDecimal tarifa;

    /**
     * Contenido del Registro tipo 2 campo 12: Valor aporte
     */
    private BigDecimal valorAporte;

    /**
     * Contenido del Registro tipo 2 campo 13: Valor de la mesada pensional
     */
    private BigDecimal valorMesada;

    /**
     * Contenido del Registro tipo 2 campo 14: Número de días cotizados
     */
    private Short diasCotizados;

    /**
     * Contenido del Registro tipo 2 campo 15: marcación de novedad ING: Ingreso
     */
    private String novING;

    /**
     * Contenido del Registro tipo 2 campo 16: marcación de novedad RET: Retiro
     */
    private String novRET;

    /**
     * Contenido del Registro tipo 2 campo 17: marcación de novedad VSP: Variación
     * permanente de la mesada pensional
     */
    private String novVSP;

    /**
     * Contenido del Registro tipo 2 campo 18: marcación de novedad SUS: Suspensión
     */
    private String novSUS;

    /**
     * Contenido del Registro tipo 2 campo 19: Fecha de novedad ingreso
     */
    private Date fechaIngreso;

    /**
     * Contenido del Registro tipo 2 campo 20: Fecha de novedad retiro
     */
    private Date fechaRetiro;

    /**
     * Contenido del Registro tipo 2 campo 21: Fecha inicio novedad VSP
     */
    private Date fechaInicioVSP;

    /**
     * Contenido del Registro tipo 2 campo 22: Fecha inicio novedad suspensión
     */
    private Date fechaInicioSuspension;

    /**
     * Contenido del Registro tipo 2 campo 23: Fecha fin novedad suspensión
     */
    private Date fechaFinSuspension;

    /**
     * Contenido del Registro tipo 2 campo 24: Correcion
     */
    private String correcion;


    /**
     * Constructor con lista de campos
     */
    public PilaArchivoIPRegistro2ModeloDTO() {
        super();
        this.nombresCampos = new ArrayList<>();
        this.nombresCampos.add("ip2Id");
        this.nombresCampos.add("ip2IndicePlanilla");
        this.nombresCampos.add("ip2Secuencia");
        this.nombresCampos.add("ip2TipoIdPensionado");
        this.nombresCampos.add("ip2IdPensionado");
        this.nombresCampos.add("ip2PrimerApellido");
        this.nombresCampos.add("ip2SegundoApellido");
        this.nombresCampos.add("ip2PrimerNombre");
        this.nombresCampos.add("ip2SegundoNombre");
        this.nombresCampos.add("ip2CodDepartamento");
        this.nombresCampos.add("ip2CodMunicipio");
        this.nombresCampos.add("ip2Tarifa");
        this.nombresCampos.add("ip2ValorAporte");
        this.nombresCampos.add("ip2ValorMesada");
        this.nombresCampos.add("ip2DiasCotizados");
        this.nombresCampos.add("ip2NovING");
        this.nombresCampos.add("ip2NovRET");
        this.nombresCampos.add("ip2NovVSP");
        this.nombresCampos.add("ip2NovSUS");
        this.nombresCampos.add("ip2FechaIngreso");
        this.nombresCampos.add("ip2FechaRetiro");
        this.nombresCampos.add("ip2FechaInicioVSP");
        this.nombresCampos.add("ip2FechaInicioSuspension");
        this.nombresCampos.add("ip2FechaFinSuspension");
        this.nombresCampos.add("ip2Correcion");

    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the indicePlanilla
     */
    public IndicePlanilla getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla
     *        the indicePlanilla to set
     */
    public void setIndicePlanilla(IndicePlanilla indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the secuencia
     */
    public Integer getSecuencia() {
        return secuencia;
    }

    /**
     * @param secuencia
     *        the secuencia to set
     */
    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    /**
     * @return the tipoIdPensionado
     */
    public String getTipoIdPensionado() {
        return tipoIdPensionado;
    }

    /**
     * @param tipoIdPensionado
     *        the tipoIdPensionado to set
     */
    public void setTipoIdPensionado(String tipoIdPensionado) {
        this.tipoIdPensionado = tipoIdPensionado;
    }

    /**
     * @return the idPensionado
     */
    public String getIdPensionado() {
        return idPensionado;
    }

    /**
     * @param idPensionado
     *        the idPensionado to set
     */
    public void setIdPensionado(String idPensionado) {
        this.idPensionado = idPensionado;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido
     *        the primerApellido to set
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
     * @param segundoApellido
     *        the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre
     *        the primerNombre to set
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
     * @param segundoNombre
     *        the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the codDepartamento
     */
    public String getCodDepartamento() {
        return codDepartamento;
    }

    /**
     * @param codDepartamento
     *        the codDepartamento to set
     */
    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    /**
     * @return the codMunicipio
     */
    public String getCodMunicipio() {
        return codMunicipio;
    }

    /**
     * @param codMunicipio
     *        the codMunicipio to set
     */
    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    /**
     * @return the tarifa
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa
     *        the tarifa to set
     */
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * @return the valorAporte
     */
    public BigDecimal getValorAporte() {
        return valorAporte;
    }

    /**
     * @param valorAporte
     *        the valorAporte to set
     */
    public void setValorAporte(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }

    /**
     * @return the valorMesada
     */
    public BigDecimal getValorMesada() {
        return valorMesada;
    }

    /**
     * @param valorMesada
     *        the valorMesada to set
     */
    public void setValorMesada(BigDecimal valorMesada) {
        this.valorMesada = valorMesada;
    }

    /**
     * @return the diasCotizados
     */
    public Short getDiasCotizados() {
        return diasCotizados;
    }

    /**
     * @param diasCotizados
     *        the diasCotizados to set
     */
    public void setDiasCotizados(Short diasCotizados) {
        this.diasCotizados = diasCotizados;
    }

    /**
     * @return the novING
     */
    public String getNovING() {
        return novING;
    }

    /**
     * @param novING
     *        the novING to set
     */
    public void setNovING(String novING) {
        this.novING = novING;
    }

    /**
     * @return the novRET
     */
    public String getNovRET() {
        return novRET;
    }

    /**
     * @param novRET
     *        the novRET to set
     */
    public void setNovRET(String novRET) {
        this.novRET = novRET;
    }

    /**
     * @return the novVSP
     */
    public String getNovVSP() {
        return novVSP;
    }

    /**
     * @param novVSP
     *        the novVSP to set
     */
    public void setNovVSP(String novVSP) {
        this.novVSP = novVSP;
    }

    /**
     * @return the novSUS
     */
    public String getNovSUS() {
        return novSUS;
    }

    /**
     * @param novSUS
     *        the novSUS to set
     */
    public void setNovSUS(String novSUS) {
        this.novSUS = novSUS;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso
     *        the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro
     *        the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the fechaInicioVSP
     */
    public Date getFechaInicioVSP() {
        return fechaInicioVSP;
    }

    /**
     * @param fechaInicioVSP
     *        the fechaInicioVSP to set
     */
    public void setFechaInicioVSP(Date fechaInicioVSP) {
        this.fechaInicioVSP = fechaInicioVSP;
    }

    /**
     * @return the fechaInicioSuspension
     */
    public Date getFechaInicioSuspension() {
        return fechaInicioSuspension;
    }

    /**
     * @param fechaInicioSuspension
     *        the fechaInicioSuspension to set
     */
    public void setFechaInicioSuspension(Date fechaInicioSuspension) {
        this.fechaInicioSuspension = fechaInicioSuspension;
    }

    /**
     * @return the fechaFinSuspension
     */
    public Date getFechaFinSuspension() {
        return fechaFinSuspension;
    }

    /**
     * @param fechaFinSuspension
     *        the fechaFinSuspension to set
     */
    public void setFechaFinSuspension(Date fechaFinSuspension) {
        this.fechaFinSuspension = fechaFinSuspension;
    }

    public String getCorrecion() {
        return this.correcion;
    }

    public void setCorrecion(String correcion) {
        this.correcion = correcion;
    }


    /**
     * Método encargado de añadir un registro a manera de value para insert
     */
    public String crearLineaValues(PilaArchivoIPRegistro2 registro) {
        StringBuilder sentenciaTemp = new StringBuilder();

        sentenciaTemp.append(QueriesConstants.LEFT_PARENTHESIS_SYMBOL);
        //sentenciaTemp.append(QueriesConstants.NEXT_VALUE_FOR_CLAUSE + ConstantesComunesProcesamientoPILA.SEC_PILA_ARCHIVO_IP_R2);
        sentenciaTemp.append(registro.getId() != null ? registro.getId() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getIndicePlanilla() != null ? registro.getIndicePlanilla().getId() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSecuencia() != null ? registro.getSecuencia() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getTipoIdPensionado() != null ? "'" + registro.getTipoIdPensionado() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getIdPensionado() != null ? "'" + registro.getIdPensionado() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getPrimerApellido() != null ? "'" + registro.getPrimerApellido() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSegundoApellido() != null ? "'" + registro.getSegundoApellido() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getPrimerNombre() != null ? "'" + registro.getPrimerNombre() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSegundoNombre() != null ? "'" + registro.getSegundoNombre() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCodDepartamento() != null ? "'" + registro.getCodDepartamento() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCodMunicipio() != null ? "'" + registro.getCodMunicipio() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getTarifa() != null ? registro.getTarifa().toPlainString() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getValorAporte() != null ? registro.getValorAporte().toPlainString() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getValorMesada() != null ? registro.getValorMesada().toPlainString() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getDiasCotizados() != null ? registro.getDiasCotizados() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovING() != null ? "'" + registro.getNovING() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovRET() != null ? "'" + registro.getNovRET() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovVSP() != null ? "'" + registro.getNovVSP() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovSUS() != null ? "'" + registro.getNovSUS() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaIngreso() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaIngreso()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp
                .append(registro.getFechaRetiro() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaRetiro()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaInicioVSP() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioVSP()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getFechaInicioSuspension() != null
                ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioSuspension()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getFechaFinSuspension() != null
                ? "'" + FuncionesValidador.formatoFecha(registro.getFechaFinSuspension()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCorrecion() != null ? "'" + registro.getCorrecion() + "'" : null);
        sentenciaTemp.append(QueriesConstants.RIGHT_PARENTHESIS_SYMBOL);


        return sentenciaTemp.toString();
    }
}
