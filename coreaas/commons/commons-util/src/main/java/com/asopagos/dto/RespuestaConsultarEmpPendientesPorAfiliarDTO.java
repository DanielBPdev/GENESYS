package com.asopagos.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos de una empresa y
 * una lista de cotizantes que aun no estan afiliados<br/>
 * <b>Módulo:</b> Asopagos - HU 389<br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 */
public class RespuestaConsultarEmpPendientesPorAfiliarDTO {

    private ConsultasEmpresaPersonaDTO consulta;
    private List<EmpAporPendientesPorAfiliarDTO> listaPendientes = new ArrayList<>();

    /**
     * @return the consulta
     */
    public ConsultasEmpresaPersonaDTO getConsulta() {
        return consulta;
    }

    /**
     * @param consulta
     *        the consulta to set
     */
    public void setConsulta(ConsultasEmpresaPersonaDTO consulta) {
        this.consulta = consulta;
    }

    /**
     * @return the listaPendientes
     */
    public List<EmpAporPendientesPorAfiliarDTO> getListaPendientes() {
        return listaPendientes;
    }

    /**
     * @param listaPendientes
     *        the listaPendientes to set
     */
    public void setListaPendientes(List<EmpAporPendientesPorAfiliarDTO> listaPendientes) {
        this.listaPendientes = listaPendientes;
    }

}
