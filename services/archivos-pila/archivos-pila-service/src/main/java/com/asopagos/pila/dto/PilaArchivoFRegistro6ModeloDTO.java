package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.asopagos.constants.QueriesConstants;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;

/**
 * <b>Descripcion:</b> DTO que representa la entidad PilaArchivoFRegistro6<br/>
 * <b>Módulo:</b> Asopagos - HU-211-407 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class PilaArchivoFRegistro6ModeloDTO implements Serializable {
    private static final long serialVersionUID = -8715868409052322835L;

    /**
     * Lista de los nombres de campos de la tabla
     */
    private List<String> nombresCampos;

    /**
     * Código identificador de llave primaria del registro tipo 6
     */
    private Long id;

    /**
     * Referencia a la entrada del índice de planilla de Operador Financiero para el registro
     */
    private IndicePlanillaOF indicePlanilla;

    /**
     * Contenido del Registro tipo 6 campo 2: Identificación del aportante
     */
    private String idAportante;

    /**
     * Contenido del Registro tipo 6 campo 3: Nombre del aportante
     */
    private String nombreAportante;

    /**
     * Contenido del Registro tipo 6 campo 4: Código del banco autorizador
     */
    private String codBanco;

    /**
     * Contenido del Registro tipo 6 campo 5: Número de planilla de liquidación
     */
    private String numeroPlanilla;

    /**
     * Contenido del Registro tipo 6 campo 6: Período de pago
     */
    private String periodoPago;

    /**
     * Contenido del Registro tipo 6 campo 7: Canal de pago
     */
    private String canalPago;

    /**
     * Contenido del Registro tipo 6 campo 8: Número de registros
     */
    private Integer cantidadRegistros;

    /**
     * Contenido del Registro tipo 6 campo 9: Código del Operador de Información
     */
    private String codOperadorInformacion;

    /**
     * Contenido del Registro tipo 6 campo 10: Valor de la planilla
     */
    private BigDecimal valorPlanilla;

    /**
     * Contenido del Registro tipo 6 campo 11: Hora-Minuto
     */
    private String horaMinuto;

    /**
     * Contenido del Registro tipo 6 campo 12: Número de secuencia
     */
    private Integer secuencia;

    /**
     * Campo especial: Descripción del estado de conciliación del registro tipo 6
     * respecto a archivo de Operador de Información
     */
    private EstadoConciliacionArchivoFEnum estadoConciliacion;

    /**
     * Referencia a el identificador de la tabla PilaArchivoFRegistro5
     * Datos de los registros tipo 5 Operador Financiero
     */
    private Long idPilaArchivoFRegistro5;

    /**
     * Constructor con listado de campos
     */
    public PilaArchivoFRegistro6ModeloDTO() {
        super();
        this.nombresCampos = new ArrayList<>();
        this.nombresCampos.add("pf6Id");
        this.nombresCampos.add("pf6IndicePlanillaOF");
        this.nombresCampos.add("pf6PilaArchivoFRegistro5");
        this.nombresCampos.add("pf6IdAportante");
        this.nombresCampos.add("pf6NombreAportante");
        this.nombresCampos.add("pf6CodBanco");
        this.nombresCampos.add("pf6NumeroPlanilla");
        this.nombresCampos.add("pf6PeriodoPago");
        this.nombresCampos.add("pf6CanalPago");
        this.nombresCampos.add("pf6CantidadRegistros");
        this.nombresCampos.add("pf6CodOperadorInformacion");
        this.nombresCampos.add("pf6ValorPlanilla");
        this.nombresCampos.add("pf6HoraMinuto");
        this.nombresCampos.add("pf6Secuencia");
        this.nombresCampos.add("pf6EstadoConciliacion");
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
    public IndicePlanillaOF getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla
     *        the indicePlanilla to set
     */
    public void setIndicePlanilla(IndicePlanillaOF indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the idAportante
     */
    public String getIdAportante() {
        return idAportante;
    }

    /**
     * @param idAportante
     *        the idAportante to set
     */
    public void setIdAportante(String idAportante) {
        this.idAportante = idAportante;
    }

    /**
     * @return the nombreAportante
     */
    public String getNombreAportante() {
        return nombreAportante;
    }

    /**
     * @param nombreAportante
     *        the nombreAportante to set
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }

    /**
     * @return the codBanco
     */
    public String getCodBanco() {
        return codBanco;
    }

    /**
     * @param codBanco
     *        the codBanco to set
     */
    public void setCodBanco(String codBanco) {
        this.codBanco = codBanco;
    }

    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla
     *        the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the periodoPago
     */
    public String getPeriodoPago() {
        return periodoPago;
    }

    /**
     * @param periodoPago
     *        the periodoPago to set
     */
    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }

    /**
     * @return the canalPago
     */
    public String getCanalPago() {
        return canalPago;
    }

    /**
     * @param canalPago
     *        the canalPago to set
     */
    public void setCanalPago(String canalPago) {
        this.canalPago = canalPago;
    }

    /**
     * @return the cantidadRegistros
     */
    public Integer getCantidadRegistros() {
        return cantidadRegistros;
    }

    /**
     * @param cantidadRegistros
     *        the cantidadRegistros to set
     */
    public void setCantidadRegistros(Integer cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    /**
     * @return the codOperadorInformacion
     */
    public String getCodOperadorInformacion() {
        return codOperadorInformacion;
    }

    /**
     * @param codOperadorInformacion
     *        the codOperadorInformacion to set
     */
    public void setCodOperadorInformacion(String codOperadorInformacion) {
        this.codOperadorInformacion = codOperadorInformacion;
    }

    /**
     * @return the valorPlanilla
     */
    public BigDecimal getValorPlanilla() {
        return valorPlanilla;
    }

    /**
     * @param valorPlanilla
     *        the valorPlanilla to set
     */
    public void setValorPlanilla(BigDecimal valorPlanilla) {
        this.valorPlanilla = valorPlanilla;
    }

    /**
     * @return the horaMinuto
     */
    public String getHoraMinuto() {
        return horaMinuto;
    }

    /**
     * @param horaMinuto
     *        the horaMinuto to set
     */
    public void setHoraMinuto(String horaMinuto) {
        this.horaMinuto = horaMinuto;
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
     * @return the estadoConciliacion
     */
    public EstadoConciliacionArchivoFEnum getEstadoConciliacion() {
        return estadoConciliacion;
    }

    /**
     * @param estadoConciliacion
     *        the estadoConciliacion to set
     */
    public void setEstadoConciliacion(EstadoConciliacionArchivoFEnum estadoConciliacion) {
        this.estadoConciliacion = estadoConciliacion;
    }

    /**
     * @return the idPilaArchivoFRegistro5
     */
    public Long getIdPilaArchivoFRegistro5() {
        return idPilaArchivoFRegistro5;
    }

    /**
     * @param idPilaArchivoFRegistro5
     *        the idPilaArchivoFRegistro5 to set
     */
    public void setIdPilaArchivoFRegistro5(Long idPilaArchivoFRegistro5) {
        this.idPilaArchivoFRegistro5 = idPilaArchivoFRegistro5;
    }

    /**
     * @return the nombresCampos
     */
    public List<String> getNombresCampos() {
        return nombresCampos;
    }

    /**
     * Método encargado de añadir un registro a manera de value para insert
     */
    public String crearLineaValues(PilaArchivoFRegistro6 registro) {
        StringBuilder sentenciaTemp = new StringBuilder();

        sentenciaTemp.append(QueriesConstants.LEFT_PARENTHESIS_SYMBOL);
        //sentenciaTemp.append(QueriesConstants.NEXT_VALUE_FOR_CLAUSE + ConstantesComunesProcesamientoPILA.SEC_PILA_ARCHIVO_F_R6);
        sentenciaTemp.append(registro.getId() != null ? registro.getId() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getIndicePlanilla() != null ? registro.getIndicePlanilla().getId() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getIdPilaArchivoFRegistro5() != null ? registro.getIdPilaArchivoFRegistro5() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getIdAportante() != null ? "'" + registro.getIdAportante() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNombreAportante() != null ? "'" + registro.getNombreAportante() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCodBanco() != null ? "'" + registro.getCodBanco() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNumeroPlanilla() != null ? "'" + registro.getNumeroPlanilla() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getPeriodoPago() != null ? "'" + registro.getPeriodoPago() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCanalPago() != null ? "'" + registro.getCanalPago() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCantidadRegistros() != null ? registro.getCantidadRegistros() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCodOperadorInformacion() != null ? "'" + registro.getCodOperadorInformacion() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getValorPlanilla() != null ? registro.getValorPlanilla() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getHoraMinuto() != null ? "'" + registro.getHoraMinuto() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSecuencia() != null ? registro.getSecuencia() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getEstadoConciliacion() != null ? "'" + registro.getEstadoConciliacion() + "'" : null);
        sentenciaTemp.append(QueriesConstants.RIGHT_PARENTHESIS_SYMBOL);

        return sentenciaTemp.toString();
    }

}
