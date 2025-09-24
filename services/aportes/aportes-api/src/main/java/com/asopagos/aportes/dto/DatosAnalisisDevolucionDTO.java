package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contiene los datos requeridos para la consulta del análisis
 * de devolución de aportes como parte de las vistas 360<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosAnalisisDevolucionDTO implements Serializable{
    private static final long serialVersionUID = 3692827380017167679L;
    
    /** Listado de IDs de registro general a consultar */
    private List<Long> idsRegistrosGeneral;
    
    /** Listado de IDs de devolución de aportes */
    private List<Long> idsAporteDevolucion;
    
    /** Listado de IDs de empresas aportantes */
    private List<Long> idsEmpresas;
    
    /** Listado de IDs de personas aportantes */
    private List<Long> idsPersonas;
    
    /** Listado de DTO con los datos para la consulta de subsidio pagado */
    private List<DatosConsultaSubsidioPagadoDTO> datosCotizantes;

    /** Mapa de registros generales */
    private Map<Long, RegistroGeneralModeloDTO> registrosGenerales;
    
    /** Mapa de identificadores de archivo de planilla PILA */
    private Map<Long, String[]> idsDocumentosPlanilla;
    
    /** Mapa de solicitudes de devolución */
    private Map<Long, SolicitudAporteModeloDTO> solicitudesDevolucion;
    
    /** Mapa de las empresas aportantes */
    private Map<Long, EmpresaModeloDTO> empresasAportantes;
    
    /** Mapa de las personas aportantes */
    private Map<Long, PersonaModeloDTO> personasAportantes;
    
    /** Constructor del DTO */
    public DatosAnalisisDevolucionDTO(){
        this.registrosGenerales = new HashMap<>();
        this.idsDocumentosPlanilla = new HashMap<>();
        this.solicitudesDevolucion = new HashMap<>();
    }

    /**
     * @return the registrosGenerales
     */
    public Map<Long, RegistroGeneralModeloDTO> getRegistrosGenerales() {
        return registrosGenerales;
    }

    /**
     * @param registrosGenerales the registrosGenerales to set
     */
    public void setRegistrosGenerales(Map<Long, RegistroGeneralModeloDTO> registrosGenerales) {
        this.registrosGenerales = registrosGenerales;
    }

    /**
     * @return the idsDocumentosPlanilla
     */
    public Map<Long, String[]> getIdsDocumentosPlanilla() {
        return idsDocumentosPlanilla;
    }

    /**
     * @param idsDocumentosPlanilla the idsDocumentosPlanilla to set
     */
    public void setIdsDocumentosPlanilla(Map<Long, String[]> idsDocumentosPlanilla) {
        this.idsDocumentosPlanilla = idsDocumentosPlanilla;
    }

    /**
     * @return the solicitudesDevolucion
     */
    public Map<Long, SolicitudAporteModeloDTO> getSolicitudesDevolucion() {
        return solicitudesDevolucion;
    }

    /**
     * @param solicitudesDevolucion the solicitudesDevolucion to set
     */
    public void setSolicitudesDevolucion(Map<Long, SolicitudAporteModeloDTO> solicitudesDevolucion) {
        this.solicitudesDevolucion = solicitudesDevolucion;
    }

    /**
     * @return the idsRegistrosGeneral
     */
    public List<Long> getIdsRegistrosGeneral() {
        return idsRegistrosGeneral;
    }

    /**
     * @param idsRegistrosGeneral the idsRegistrosGeneral to set
     */
    public void setIdsRegistrosGeneral(List<Long> idsRegistrosGeneral) {
        this.idsRegistrosGeneral = idsRegistrosGeneral;
    }

    /**
     * @return the idsAporteDevolucion
     */
    public List<Long> getIdsAporteDevolucion() {
        return idsAporteDevolucion;
    }

    /**
     * @param idsAporteDevolucion the idsAporteDevolucion to set
     */
    public void setIdsAporteDevolucion(List<Long> idsAporteDevolucion) {
        this.idsAporteDevolucion = idsAporteDevolucion;
    }

    /**
     * @return the datosCotizantes
     */
    public List<DatosConsultaSubsidioPagadoDTO> getDatosCotizantes() {
        return datosCotizantes;
    }

    /**
     * @param datosCotizantes the datosCotizantes to set
     */
    public void setDatosCotizantes(List<DatosConsultaSubsidioPagadoDTO> datosCotizantes) {
        this.datosCotizantes = datosCotizantes;
    }

    /**
     * @return the idsEmpresas
     */
    public List<Long> getIdsEmpresas() {
        return idsEmpresas;
    }

    /**
     * @param idsEmpresas the idsEmpresas to set
     */
    public void setIdsEmpresas(List<Long> idsEmpresas) {
        this.idsEmpresas = idsEmpresas;
    }

    /**
     * @return the empresasAportantes
     */
    public Map<Long, EmpresaModeloDTO> getEmpresasAportantes() {
        return empresasAportantes;
    }

    /**
     * @param empresasAportantes the empresasAportantes to set
     */
    public void setEmpresasAportantes(Map<Long, EmpresaModeloDTO> empresasAportantes) {
        this.empresasAportantes = empresasAportantes;
    }

    /**
     * @return the idsPersonas
     */
    public List<Long> getIdsPersonas() {
        return idsPersonas;
    }

    /**
     * @param idsPersonas the idsPersonas to set
     */
    public void setIdsPersonas(List<Long> idsPersonas) {
        this.idsPersonas = idsPersonas;
    }

    /**
     * @return the personasAportantes
     */
    public Map<Long, PersonaModeloDTO> getPersonasAportantes() {
        return personasAportantes;
    }

    /**
     * @param personasAportantes the personasAportantes to set
     */
    public void setPersonasAportantes(Map<Long, PersonaModeloDTO> personasAportantes) {
        this.personasAportantes = personasAportantes;
    }
}
