package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.modelo.PersonaModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contiene los datos requeridos para la consulta de cuenta aportes <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosConsultaCuentaAporteDTO implements Serializable {
    private static final long serialVersionUID = 4416267303416351758L;
    
    /** Listado de IDs de aportes generales */
    private List<Long> idsAporteGeneral;
    
    /** Listado de IDs de aportes generales */
    private List<Long> idsRegistroGeneral;
    
    /** Listado de IDs de persona */
    private List<Long> idsPersona;
    
    /** Listado de IDs de empresa */
    private List<Long> idsEmpresa;

    /** Mapa de Personas para la consulta (por empresa) */
    private Map<Long, PersonaModeloDTO> personasPorEmpresa;

    /** Mapa de Personas para la consulta (por persona) */
    private Map<Long, PersonaModeloDTO> personas;
    
    /** Mapa de números de operación */
    private Map<Long, String> numerosOperacion;
    
    /**
     * Constructor de clase
     * */
    public DatosConsultaCuentaAporteDTO(){
        this.personas = new HashMap<>();
        this.personasPorEmpresa = new HashMap<>();
        this.numerosOperacion = new HashMap<>();
    }

    /**
     * @return the idsAporteGeneral
     */
    public List<Long> getIdsAporteGeneral() {
        return idsAporteGeneral;
    }

    /**
     * @param idsAporteGeneral the idsAporteGeneral to set
     */
    public void setIdsAporteGeneral(List<Long> idsAporteGeneral) {
        this.idsAporteGeneral = idsAporteGeneral;
    }

    /**
     * @return the idsPersona
     */
    public List<Long> getIdsPersona() {
        return idsPersona;
    }

    /**
     * @param idsPersona the idsPersona to set
     */
    public void setIdsPersona(List<Long> idsPersona) {
        this.idsPersona = idsPersona;
    }

    /**
     * @return the idsEmpresa
     */
    public List<Long> getIdsEmpresa() {
        return idsEmpresa;
    }

    /**
     * @param idsEmpresa the idsEmpresa to set
     */
    public void setIdsEmpresa(List<Long> idsEmpresa) {
        this.idsEmpresa = idsEmpresa;
    }

    /**
     * @return the personas
     */
    public Map<Long, PersonaModeloDTO> getPersonas() {
        return personas;
    }

    /**
     * @param personas the personas to set
     */
    public void setPersonas(Map<Long, PersonaModeloDTO> personas) {
        this.personas = personas;
    }

    /**
     * @return the numerosOperacion
     */
    public Map<Long, String> getNumerosOperacion() {
        return numerosOperacion;
    }

    /**
     * @param numerosOperacion the numerosOperacion to set
     */
    public void setNumerosOperacion(Map<Long, String> numerosOperacion) {
        this.numerosOperacion = numerosOperacion;
    }

    /**
     * @return the personasPorEmpresa
     */
    public Map<Long, PersonaModeloDTO> getPersonasPorEmpresa() {
        return personasPorEmpresa;
    }

    /**
     * @param personasPorEmpresa the personasPorEmpresa to set
     */
    public void setPersonasPorEmpresa(Map<Long, PersonaModeloDTO> personasPorEmpresa) {
        this.personasPorEmpresa = personasPorEmpresa;
    }

    /**
     * @return the idsRegistroGeneral
     */
    public List<Long> getIdsRegistroGeneral() {
        return idsRegistroGeneral;
    }

    /**
     * @param idsRegistroGeneral the idsRegistroGeneral to set
     */
    public void setIdsRegistroGeneral(List<Long> idsRegistroGeneral) {
        this.idsRegistroGeneral = idsRegistroGeneral;
    }
}
