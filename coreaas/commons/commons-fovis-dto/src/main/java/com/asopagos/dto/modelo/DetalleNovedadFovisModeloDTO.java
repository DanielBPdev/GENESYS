package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.dto.fovis.CondicionHogarNovedadDTO;
import com.asopagos.entidades.ccf.fovis.DetalleNovedadFovis;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DetalleNovedadFovisModeloDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 5132244616447857138L;

    /**
     * Identificador del registro detalle
     */
    private Long idDetalleNovedadFovis;

    /**
     * Referencia a la solicitud novedad que complementa
     */
    private Long idSolicitudNovedad;

    /**
     * Valor de SFV simulado con ajuste del SMLMV
     */
    private BigDecimal valorSFVSimuladoConAjuste;

    /**
     * Cantidad de SMLMV del SFV simulado con ajuste
     */
    private BigDecimal valorEquivalenciaSFVSimuladoConAjuste;

    /**
     * Valor de diferencia entre el valor simulado con ajuste y el valor asignado
     */
    private BigDecimal valorDiferenciaAjuste;

    /**
     * Cantidad de SMLMV del valor diferencia ajuste
     */
    private BigDecimal valorEquivalenciaDiferenciaAjuste;

    /**
     * Valor de SFV Simulado con adición rango actual parametrizado
     */
    private BigDecimal valorSFVSimuladoConAdicion;

    /**
     * Cantidad de SMLMV del valor simulado con adición
     */
    private BigDecimal valorEquivalenciaSFVSimuladoConAdicion;

    /**
     * Valor de diferencia entre el valor simulado con adicion y el valor simulado con ajuste
     */
    private BigDecimal valorDiferenciaAdicion;

    /**
     * Cantidad de SMLMV del valor diferencia con adición
     */
    private BigDecimal valorEquivalenciaDiferenciaAdicion;

    /**
     * Valor de diferencia entre el valor simulado con adicion y el valor asignado
     */
    private BigDecimal valorTotalDiferencia;

    /**
     * Cantidad de SMLMV del valor total diferencia
     */
    private BigDecimal valorEquivalenciaTotalDiferencia;

    /**
     * Valor de SFV ajustado con la adición
     */
    private BigDecimal valorSFVAjusteAdicion;

    /**
     * Cantidad de SMLMV del valor de SFV ajustado con adición
     */
    private BigDecimal valorEquivalenciaSFVAjusteAdicion;

    /**
     * Valor del SFV ajustado con adición aprobado para el cambio
     */
    private BigDecimal valorSFVAprobado;

    /**
     * Cantidad de SMLMV del valor SFV aprobado
     */
    private BigDecimal valorEquivalenciaSFVAprobado;

    /**
     * Identificador del archivo agregado como documento soporte
     */
    private String identificadorDocumentoSoporte;

    /**
     * Información de las condiciones de hogar agregadas en la novedad de SFV 133 2018
     */
    private String condicionesHogar;

    /**
     * Lista de condiciones hogar registradas en la novedad de 31 Ajuste y actualización valor SFV (Decreto 133 de 2018)
     */
    private List<CondicionHogarNovedadDTO> listaCondicionesHogar;

    /**
     * Indica el resultado de validacion de las condiciones de hogar
     */
    private Boolean resultadoCondiciones;

    /**
     * Valor total de ingresos del hogar al momento de la asignación
     */
    private BigDecimal totalIngresosHogarAsignacion;

    /**
     * Valor total de ingresos del hogar al momento de registro de la novedad
     */
    private BigDecimal totalIngresosHogar;

    /**
     * Valor de tope SMLMV asociado al rango de la modalidad al momento de la asignación
     */
    private BigDecimal rangoTopeSMLMVAsignacion;

    /**
     * Valor de tope SMLMV asociado al rango de la modalidad al momento del registro de la novedad
     */
    private BigDecimal rangoTopeSMLMV;
    /**
     * Valor del salaria minimo legal vigente de la fecha de asignacion
     */
    private String salarioMMLV;

    /**
     * Valor real del ajuste de indexacion
     */
    private BigDecimal valorReal;

    /**
     * Valor real equivalente del ajuste
     */
    private BigDecimal valorRealEquivalente;

    /**
     * Constructor por defecto
     */
    public DetalleNovedadFovisModeloDTO() {
        super();
    }

    /**
     * Construye con la información de la entidad
     * @param detalleNovedadFovis
     *        Información entidad
     */
    public DetalleNovedadFovisModeloDTO(DetalleNovedadFovis detalleNovedadFovis) {
        convertToDTO(detalleNovedadFovis);
    }

    /**
     * Convierte la información de la entidad a la clase DTO
     * @param detalleNovedadFovis
     *        Información entidad
     */
    public void convertToDTO(DetalleNovedadFovis detalleNovedadFovis) {
        this.setCondicionesHogar(detalleNovedadFovis.getCondicionesHogar());
        this.setIdDetalleNovedadFovis(detalleNovedadFovis.getIdDetalleNovedadFovis());
        this.setIdentificadorDocumentoSoporte(detalleNovedadFovis.getIdentificadorDocumentoSoporte());
        this.setIdSolicitudNovedad(detalleNovedadFovis.getIdSolicitudNovedad());
        this.setRangoTopeSMLMV(detalleNovedadFovis.getRangoTopeSMLMV());
        this.setRangoTopeSMLMVAsignacion(detalleNovedadFovis.getRangoTopeSMLMVAsignacion());
        this.setTotalIngresosHogar(detalleNovedadFovis.getTotalIngresosHogar());
        this.setTotalIngresosHogarAsignacion(detalleNovedadFovis.getTotalIngresosHogarAsignacion());
        this.setValorDiferenciaAdicion(detalleNovedadFovis.getValorDiferenciaAdicion());
        this.setValorDiferenciaAjuste(detalleNovedadFovis.getValorDiferenciaAjuste());
        this.setValorEquivalenciaDiferenciaAdicion(detalleNovedadFovis.getValorEquivalenciaDiferenciaAdicion());
        this.setValorEquivalenciaDiferenciaAjuste(detalleNovedadFovis.getValorEquivalenciaDiferenciaAjuste());
        this.setValorEquivalenciaSFVAjusteAdicion(detalleNovedadFovis.getValorEquivalenciaSFVAjusteAdicion());
        this.setValorEquivalenciaSFVSimuladoConAdicion(detalleNovedadFovis.getValorEquivalenciaSFVSimuladoConAdicion());
        this.setValorEquivalenciaSFVSimuladoConAjuste(detalleNovedadFovis.getValorEquivalenciaSFVSimuladoConAjuste());
        this.setValorEquivalenciaTotalDiferencia(detalleNovedadFovis.getValorEquivalenciaTotalDiferencia());
        this.setValorSFVAjusteAdicion(detalleNovedadFovis.getValorSFVAjusteAdicion());
        this.setValorSFVSimuladoConAdicion(detalleNovedadFovis.getValorEquivalenciaSFVSimuladoConAdicion());
        this.setValorSFVSimuladoConAjuste(detalleNovedadFovis.getValorEquivalenciaSFVSimuladoConAjuste());
        this.setValorTotalDiferencia(detalleNovedadFovis.getValorTotalDiferencia());
    }

    /**
     * Convierte la información del DTO a la estructura de la entidad
     * @return EntidadDetalleNovedadFovis
     */
    public DetalleNovedadFovis converToEntity() {
        DetalleNovedadFovis detalleNovedadFovis = new DetalleNovedadFovis();
        if (this.getListaCondicionesHogar() != null) {
            detalleNovedadFovis.setCondicionesHogar(convertListToJson(this.getListaCondicionesHogar()));
        }
        else {
            detalleNovedadFovis.setCondicionesHogar(this.getCondicionesHogar());
        }
        detalleNovedadFovis.setIdDetalleNovedadFovis(this.getIdDetalleNovedadFovis());
        detalleNovedadFovis.setIdentificadorDocumentoSoporte(this.getIdentificadorDocumentoSoporte());
        detalleNovedadFovis.setIdSolicitudNovedad(this.getIdSolicitudNovedad());
        detalleNovedadFovis.setRangoTopeSMLMV(this.getRangoTopeSMLMV());
        detalleNovedadFovis.setRangoTopeSMLMVAsignacion(this.getRangoTopeSMLMVAsignacion());
        detalleNovedadFovis.setTotalIngresosHogar(this.getTotalIngresosHogar());
        detalleNovedadFovis.setTotalIngresosHogarAsignacion(this.getTotalIngresosHogarAsignacion());
        detalleNovedadFovis.setValorDiferenciaAdicion(this.getValorDiferenciaAdicion());
        detalleNovedadFovis.setValorDiferenciaAjuste(this.getValorDiferenciaAjuste());
        detalleNovedadFovis.setValorEquivalenciaDiferenciaAdicion(this.getValorEquivalenciaDiferenciaAdicion());
        detalleNovedadFovis.setValorEquivalenciaDiferenciaAjuste(this.getValorEquivalenciaDiferenciaAjuste());
        detalleNovedadFovis.setValorEquivalenciaSFVAjusteAdicion(this.getValorEquivalenciaSFVAjusteAdicion());
        detalleNovedadFovis.setValorEquivalenciaSFVSimuladoConAdicion(this.getValorEquivalenciaSFVSimuladoConAdicion());
        detalleNovedadFovis.setValorEquivalenciaSFVSimuladoConAjuste(this.getValorEquivalenciaSFVSimuladoConAjuste());
        detalleNovedadFovis.setValorEquivalenciaTotalDiferencia(this.getValorEquivalenciaTotalDiferencia());
        detalleNovedadFovis.setValorSFVAjusteAdicion(this.getValorSFVAjusteAdicion());
        detalleNovedadFovis.setValorSFVSimuladoConAdicion(this.getValorSFVSimuladoConAdicion());
        detalleNovedadFovis.setValorSFVSimuladoConAjuste(this.getValorSFVSimuladoConAjuste());
        detalleNovedadFovis.setValorTotalDiferencia(this.getValorTotalDiferencia());
        return detalleNovedadFovis;
    }

    /**
     * Convierte la lista de condiciones agregadas en pantalla a cadena de texto para guardarla
     * @param listaCondiciones
     *        Lista de condiciones
     * @return payload
     */
    private String convertListToJson(List<CondicionHogarNovedadDTO> listaCondiciones) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this.getListaCondicionesHogar());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return the idDetalleNovedadFovis
     */
    public Long getIdDetalleNovedadFovis() {
        return idDetalleNovedadFovis;
    }

    /**
     * @param idDetalleNovedadFovis
     *        the idDetalleNovedadFovis to set
     */
    public void setIdDetalleNovedadFovis(Long idDetalleNovedadFovis) {
        this.idDetalleNovedadFovis = idDetalleNovedadFovis;
    }

    /**
     * @return the idSolicitudNovedad
     */
    public Long getIdSolicitudNovedad() {
        return idSolicitudNovedad;
    }

    /**
     * @param idSolicitudNovedad
     *        the idSolicitudNovedad to set
     */
    public void setIdSolicitudNovedad(Long idSolicitudNovedad) {
        this.idSolicitudNovedad = idSolicitudNovedad;
    }

    /**
     * @return the valorSFVSimuladoConAjuste
     */
    public BigDecimal getValorSFVSimuladoConAjuste() {
        return valorSFVSimuladoConAjuste;
    }

    /**
     * @param valorSFVSimuladoConAjuste
     *        the valorSFVSimuladoConAjuste to set
     */
    public void setValorSFVSimuladoConAjuste(BigDecimal valorSFVSimuladoConAjuste) {
        this.valorSFVSimuladoConAjuste = valorSFVSimuladoConAjuste;
    }

    /**
     * @return the valorEquivalenciaSFVSimuladoConAjuste
     */
    public BigDecimal getValorEquivalenciaSFVSimuladoConAjuste() {
        return valorEquivalenciaSFVSimuladoConAjuste;
    }

    /**
     * @param valorEquivalenciaSFVSimuladoConAjuste
     *        the valorEquivalenciaSFVSimuladoConAjuste to set
     */
    public void setValorEquivalenciaSFVSimuladoConAjuste(BigDecimal valorEquivalenciaSFVSimuladoConAjuste) {
        this.valorEquivalenciaSFVSimuladoConAjuste = valorEquivalenciaSFVSimuladoConAjuste;
    }

    /**
     * @return the valorDiferenciaAjuste
     */
    public BigDecimal getValorDiferenciaAjuste() {
        return valorDiferenciaAjuste;
    }

    /**
     * @param valorDiferenciaAjuste
     *        the valorDiferenciaAjuste to set
     */
    public void setValorDiferenciaAjuste(BigDecimal valorDiferenciaAjuste) {
        this.valorDiferenciaAjuste = valorDiferenciaAjuste;
    }

    /**
     * @return the valorEquivalenciaDiferenciaAjuste
     */
    public BigDecimal getValorEquivalenciaDiferenciaAjuste() {
        return valorEquivalenciaDiferenciaAjuste;
    }

    /**
     * @param valorEquivalenciaDiferenciaAjuste
     *        the valorEquivalenciaDiferenciaAjuste to set
     */
    public void setValorEquivalenciaDiferenciaAjuste(BigDecimal valorEquivalenciaDiferenciaAjuste) {
        this.valorEquivalenciaDiferenciaAjuste = valorEquivalenciaDiferenciaAjuste;
    }

    /**
     * @return the valorSFVSimuladoConAdicion
     */
    public BigDecimal getValorSFVSimuladoConAdicion() {
        return valorSFVSimuladoConAdicion;
    }

    /**
     * @param valorSFVSimuladoConAdicion
     *        the valorSFVSimuladoConAdicion to set
     */
    public void setValorSFVSimuladoConAdicion(BigDecimal valorSFVSimuladoConAdicion) {
        this.valorSFVSimuladoConAdicion = valorSFVSimuladoConAdicion;
    }

    /**
     * @return the valorEquivalenciaSFVSimuladoConAdicion
     */
    public BigDecimal getValorEquivalenciaSFVSimuladoConAdicion() {
        return valorEquivalenciaSFVSimuladoConAdicion;
    }

    /**
     * @param valorEquivalenciaSFVSimuladoConAdicion
     *        the valorEquivalenciaSFVSimuladoConAdicion to set
     */
    public void setValorEquivalenciaSFVSimuladoConAdicion(BigDecimal valorEquivalenciaSFVSimuladoConAdicion) {
        this.valorEquivalenciaSFVSimuladoConAdicion = valorEquivalenciaSFVSimuladoConAdicion;
    }

    /**
     * @return the valorDiferenciaAdicion
     */
    public BigDecimal getValorDiferenciaAdicion() {
        return valorDiferenciaAdicion;
    }

    /**
     * @param valorDiferenciaAdicion
     *        the valorDiferenciaAdicion to set
     */
    public void setValorDiferenciaAdicion(BigDecimal valorDiferenciaAdicion) {
        this.valorDiferenciaAdicion = valorDiferenciaAdicion;
    }

    /**
     * @return the valorEquivalenciaDiferenciaAdicion
     */
    public BigDecimal getValorEquivalenciaDiferenciaAdicion() {
        return valorEquivalenciaDiferenciaAdicion;
    }

    /**
     * @param valorEquivalenciaDiferenciaAdicion
     *        the valorEquivalenciaDiferenciaAdicion to set
     */
    public void setValorEquivalenciaDiferenciaAdicion(BigDecimal valorEquivalenciaDiferenciaAdicion) {
        this.valorEquivalenciaDiferenciaAdicion = valorEquivalenciaDiferenciaAdicion;
    }

    /**
     * @return the valorTotalDiferencia
     */
    public BigDecimal getValorTotalDiferencia() {
        return valorTotalDiferencia;
    }

    /**
     * @param valorTotalDiferencia
     *        the valorTotalDiferencia to set
     */
    public void setValorTotalDiferencia(BigDecimal valorTotalDiferencia) {
        this.valorTotalDiferencia = valorTotalDiferencia;
    }

    /**
     * @return the valorEquivalenciaTotalDiferencia
     */
    public BigDecimal getValorEquivalenciaTotalDiferencia() {
        return valorEquivalenciaTotalDiferencia;
    }

    /**
     * @param valorEquivalenciaTotalDiferencia
     *        the valorEquivalenciaTotalDiferencia to set
     */
    public void setValorEquivalenciaTotalDiferencia(BigDecimal valorEquivalenciaTotalDiferencia) {
        this.valorEquivalenciaTotalDiferencia = valorEquivalenciaTotalDiferencia;
    }

    /**
     * @return the valorSFVAjusteAdicion
     */
    public BigDecimal getValorSFVAjusteAdicion() {
        return valorSFVAjusteAdicion;
    }

    /**
     * @param valorSFVAjusteAdicion
     *        the valorSFVAjusteAdicion to set
     */
    public void setValorSFVAjusteAdicion(BigDecimal valorSFVAjusteAdicion) {
        this.valorSFVAjusteAdicion = valorSFVAjusteAdicion;
    }

    /**
     * @return the valorEquivalenciaSFVAjusteAdicion
     */
    public BigDecimal getValorEquivalenciaSFVAjusteAdicion() {
        return valorEquivalenciaSFVAjusteAdicion;
    }

    /**
     * @param valorEquivalenciaSFVAjusteAdicion
     *        the valorEquivalenciaSFVAjusteAdicion to set
     */
    public void setValorEquivalenciaSFVAjusteAdicion(BigDecimal valorEquivalenciaSFVAjusteAdicion) {
        this.valorEquivalenciaSFVAjusteAdicion = valorEquivalenciaSFVAjusteAdicion;
    }

    /**
     * @return the valorSFVAprobado
     */
    public BigDecimal getValorSFVAprobado() {
        return valorSFVAprobado;
    }

    /**
     * @param valorSFVAprobado
     *        the valorSFVAprobado to set
     */
    public void setValorSFVAprobado(BigDecimal valorSFVAprobado) {
        this.valorSFVAprobado = valorSFVAprobado;
    }

    /**
     * @return the valorEquivalenciaSFVAprobado
     */
    public BigDecimal getValorEquivalenciaSFVAprobado() {
        return valorEquivalenciaSFVAprobado;
    }

    /**
     * @param valorEquivalenciaSFVAprobado
     *        the valorEquivalenciaSFVAprobado to set
     */
    public void setValorEquivalenciaSFVAprobado(BigDecimal valorEquivalenciaSFVAprobado) {
        this.valorEquivalenciaSFVAprobado = valorEquivalenciaSFVAprobado;
    }

    /**
     * @return the identificadorDocumentoSoporte
     */
    public String getIdentificadorDocumentoSoporte() {
        return identificadorDocumentoSoporte;
    }

    /**
     * @param identificadorDocumentoSoporte
     *        the identificadorDocumentoSoporte to set
     */
    public void setIdentificadorDocumentoSoporte(String identificadorDocumentoSoporte) {
        this.identificadorDocumentoSoporte = identificadorDocumentoSoporte;
    }

    /**
     * @return the condicionesHogar
     */
    public String getCondicionesHogar() {
        return condicionesHogar;
    }

    /**
     * @param condicionesHogar
     *        the condicionesHogar to set
     */
    public void setCondicionesHogar(String condicionesHogar) {
        this.condicionesHogar = condicionesHogar;
    }

    /**
     * @return the listaCondicionesHogar
     */
    public List<CondicionHogarNovedadDTO> getListaCondicionesHogar() {
        return listaCondicionesHogar;
    }

    /**
     * @param listaCondicionesHogar
     *        the listaCondicionesHogar to set
     */
    public void setListaCondicionesHogar(List<CondicionHogarNovedadDTO> listaCondicionesHogar) {
        this.listaCondicionesHogar = listaCondicionesHogar;
    }

    /**
     * @return the resultadoCondiciones
     */
    public Boolean getResultadoCondiciones() {
        return resultadoCondiciones;
    }

    /**
     * @param resultadoCondiciones
     *        the resultadoCondiciones to set
     */
    public void setResultadoCondiciones(Boolean resultadoCondiciones) {
        this.resultadoCondiciones = resultadoCondiciones;
    }

    /**
     * @return the totalIngresosHogarAsignacion
     */
    public BigDecimal getTotalIngresosHogarAsignacion() {
        return totalIngresosHogarAsignacion;
    }

    /**
     * @param totalIngresosHogarAsignacion the totalIngresosHogarAsignacion to set
     */
    public void setTotalIngresosHogarAsignacion(BigDecimal totalIngresosHogarAsignacion) {
        this.totalIngresosHogarAsignacion = totalIngresosHogarAsignacion;
    }

    /**
     * @return the totalIngresosHogar
     */
    public BigDecimal getTotalIngresosHogar() {
        return totalIngresosHogar;
    }

    /**
     * @param totalIngresosHogar the totalIngresosHogar to set
     */
    public void setTotalIngresosHogar(BigDecimal totalIngresosHogar) {
        this.totalIngresosHogar = totalIngresosHogar;
    }

    /**
     * @return the rangoTopeSMLMVAsignacion
     */
    public BigDecimal getRangoTopeSMLMVAsignacion() {
        return rangoTopeSMLMVAsignacion;
    }

    /**
     * @param rangoTopeSMLMVAsignacion the rangoTopeSMLMVAsignacion to set
     */
    public void setRangoTopeSMLMVAsignacion(BigDecimal rangoTopeSMLMVAsignacion) {
        this.rangoTopeSMLMVAsignacion = rangoTopeSMLMVAsignacion;
    }

    /**
     * @return the rangoTopeSMLMV
     */
    public BigDecimal getRangoTopeSMLMV() {
        return rangoTopeSMLMV;
    }

    /**
     * @param rangoTopeSMLMV the rangoTopeSMLMV to set
     */
    public void setRangoTopeSMLMV(BigDecimal rangoTopeSMLMV) {
        this.rangoTopeSMLMV = rangoTopeSMLMV;
    }

    /**
     * @return the SalarioMMLV
     */
    public String getSalarioMMLV() {
        return salarioMMLV;
    }

    /**
     * @param SalarioMMLV the SalarioMMLV to set
     */
    public void setSalarioMMLV(String salarioMMLV) {
        this.salarioMMLV = salarioMMLV;
    }

    /**
     * @return the valorReal
     */
    public BigDecimal getValorReal() { return valorReal;
    }

    /**
     * @param valorReal
     *        the valorReal to set
     */
    public void setValorReal(BigDecimal valorReal) { this.valorReal = valorReal;
    }

    /**
     * @return the valorRealEquivalente
     */
    public BigDecimal getvalorRealEquivalente() { return valorRealEquivalente;
    }

    /**
     * @param valorRealEquivalente
     *        the valorReal to set
     */
    public void setvalorRealEquivalente(BigDecimal valorRealEquivalente) { this.valorRealEquivalente = valorRealEquivalente;
    }

}
