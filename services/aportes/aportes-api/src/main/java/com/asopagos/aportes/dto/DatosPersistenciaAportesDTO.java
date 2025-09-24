package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.transversal.core.Municipio;

/**
 * <b>Descripcion:</b> DTO que contiene la información unificada y 
 * requerida para la persistencia de aportes en Core<br/>
 * <b>Módulo:</b> Asopagos - HU-211 y HU-212 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosPersistenciaAportesDTO implements Serializable {
    private static final long serialVersionUID = -5200351317478068387L;

    /** Lista de Aportes Generales */
    private List<AporteGeneralModeloDTO> aportesGenerales;
    
    /** Lista de Aportes Detallados */
    private List<AporteDetalladoModeloDTO> aportesDetallados;
    
    /** Lista de municipios */
    private List<Municipio> municipios;
    
    /** Lista de personas aportantes */
    private List<PersonaModeloDTO> personasAportantes;
    
    /** Lista de personas cotizantes */
    private List<PersonaModeloDTO> personasCotizantes;
    
    /** Lista de personas tramitadoras */
    private List<PersonaModeloDTO> personasTramitadoras;
    
    /** Lista de empresas aportantes, terceros pagadores o pagadores por terceros */
    private List<EmpresaModeloDTO> empresasAportantes;
    
    /** Lista de empleadores aportantes */
    private List<EmpleadorModeloDTO> empleadoresAportantes;
    
    /** Lista de Afiliados cotizantes */
    private List<AfiliadoModeloDTO> afiliadosCotizantes;
    
    /** Lista de Roles Afiliado cotizantes */
    private List<RolAfiliadoModeloDTO> rolesAfiliadosCotizantes;
    
    /** Lista de Operadores de Información por los cuales se reciben los aportes */
    private List<OperadorInformacion> operadoresInformacion;
    
    /** Mapa de sucursales de empresas a las que se asocian cotizantes */
    private Map<Long, List<SucursalEmpresaModeloDTO>> sucursales;
    
    /** Lista de ID de personas que presentan registro de inconsistencia */
    private List<Long> idsPersonasConInconsistencia;
    
    /** Lista de Roles Afiliado por actualizar por motivo de marca de fiscalización */
    private List<RolAfiliadoModeloDTO> rolesAfiliadosPorActualizar;
    
    /** Lista de empleadores que aplican para reintegro */
    private List<ActivacionEmpleadorDTO> datosReintegroEmpleadores;
    
    /** Lista de ID de TemAporteProcesado existentes */
    private List<Long> idsTemAporteProcesado;
    
    /** Mapa de la presencia de novedades para los registros generales */
    private Map<Long, ConsultaPresenciaNovedadesDTO> mapaPresenciaNovedades;

    /**
     * Constructor de clase
     */
    public DatosPersistenciaAportesDTO() {
        this.aportesGenerales = new ArrayList<>();
        this.aportesDetallados = new ArrayList<>();
        this.municipios = new ArrayList<>();
        this.personasAportantes = new ArrayList<>();
        this.personasCotizantes = new ArrayList<>();
        this.personasTramitadoras = new ArrayList<>();
        this.empresasAportantes = new ArrayList<>();
        this.empleadoresAportantes = new ArrayList<>();
        this.afiliadosCotizantes = new ArrayList<>();
        this.rolesAfiliadosCotizantes = new ArrayList<>();
        this.operadoresInformacion = new ArrayList<>();
        this.sucursales = new HashMap<>();
        this.idsPersonasConInconsistencia = new ArrayList<>();
        this.rolesAfiliadosPorActualizar = new ArrayList<>();
        this.datosReintegroEmpleadores = new ArrayList<>();
    }

    /**
     * @return the aportesGenerales
     */
    public List<AporteGeneralModeloDTO> getAportesGenerales() {
        return aportesGenerales;
    }

    /**
     * @param aportesGenerales the aportesGenerales to set
     */
    public void setAportesGenerales(List<AporteGeneralModeloDTO> aportesGenerales) {
        this.aportesGenerales = aportesGenerales;
    }

    /**
     * @return the aportesDetallados
     */
    public List<AporteDetalladoModeloDTO> getAportesDetallados() {
        return aportesDetallados;
    }

    /**
     * @param aportesDetallados the aportesDetallados to set
     */
    public void setAportesDetallados(List<AporteDetalladoModeloDTO> aportesDetallados) {
        this.aportesDetallados = aportesDetallados;
    }

    /**
     * @return the personasAportantes
     */
    public List<PersonaModeloDTO> getPersonasAportantes() {
        return personasAportantes;
    }

    /**
     * @param personasAportantes the personasAportantes to set
     */
    public void setPersonasAportantes(List<PersonaModeloDTO> personasAportantes) {
        this.personasAportantes = personasAportantes;
    }

    /**
     * @return the personasCotizantes
     */
    public List<PersonaModeloDTO> getPersonasCotizantes() {
        return personasCotizantes;
    }

    /**
     * @param personasCotizantes the personasCotizantes to set
     */
    public void setPersonasCotizantes(List<PersonaModeloDTO> personasCotizantes) {
        this.personasCotizantes = personasCotizantes;
    }

    /**
     * @return the empresasAportantes
     */
    public List<EmpresaModeloDTO> getEmpresasAportantes() {
        return empresasAportantes;
    }

    /**
     * @param empresasAportantes the empresasAportantes to set
     */
    public void setEmpresasAportantes(List<EmpresaModeloDTO> empresasAportantes) {
        this.empresasAportantes = empresasAportantes;
    }

    /**
     * @return the afiliadosCotizantes
     */
    public List<AfiliadoModeloDTO> getAfiliadosCotizantes() {
        return afiliadosCotizantes;
    }

    /**
     * @param afiliadosCotizantes the afiliadosCotizantes to set
     */
    public void setAfiliadosCotizantes(List<AfiliadoModeloDTO> afiliadosCotizantes) {
        this.afiliadosCotizantes = afiliadosCotizantes;
    }

    /**
     * @return the rolesAfiliadosCotizantes
     */
    public List<RolAfiliadoModeloDTO> getRolesAfiliadosCotizantes() {
        return rolesAfiliadosCotizantes;
    }

    /**
     * @param rolesAfiliadosCotizantes the rolesAfiliadosCotizantes to set
     */
    public void setRolesAfiliadosCotizantes(List<RolAfiliadoModeloDTO> rolesAfiliadosCotizantes) {
        this.rolesAfiliadosCotizantes = rolesAfiliadosCotizantes;
    }

    /**
     * @return the operadoresInformacion
     */
    public List<OperadorInformacion> getOperadoresInformacion() {
        return operadoresInformacion;
    }

    /**
     * @param operadoresInformacion the operadoresInformacion to set
     */
    public void setOperadoresInformacion(List<OperadorInformacion> operadoresInformacion) {
        this.operadoresInformacion = operadoresInformacion;
    }

    /**
     * @return the municipios
     */
    public List<Municipio> getMunicipios() {
        return municipios;
    }

    /**
     * @param municipios the municipios to set
     */
    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    /**
     * @return the personasTramitadoras
     */
    public List<PersonaModeloDTO> getPersonasTramitadoras() {
        return personasTramitadoras;
    }

    /**
     * @param personasTramitadoras the personasTramitadoras to set
     */
    public void setPersonasTramitadoras(List<PersonaModeloDTO> personasTramitadoras) {
        this.personasTramitadoras = personasTramitadoras;
    }

    /**
     * @return the sucursales
     */
    public Map<Long, List<SucursalEmpresaModeloDTO>> getSucursales() {
        return sucursales;
    }

    /**
     * @param sucursales the sucursales to set
     */
    public void setSucursales(Map<Long, List<SucursalEmpresaModeloDTO>> sucursales) {
        this.sucursales = sucursales;
    }

    /**
     * @return the idsPersonasConInconsistencia
     */
    public List<Long> getIdsPersonasConInconsistencia() {
        return idsPersonasConInconsistencia;
    }

    /**
     * @param idsPersonasConInconsistencia the idsPersonasConInconsistencia to set
     */
    public void setIdsPersonasConInconsistencia(List<Long> idsPersonasConInconsistencia) {
        this.idsPersonasConInconsistencia = idsPersonasConInconsistencia;
    }

    /**
     * @return the empleadoresAportantes
     */
    public List<EmpleadorModeloDTO> getEmpleadoresAportantes() {
        return empleadoresAportantes;
    }

    /**
     * @param empleadoresAportantes the empleadoresAportantes to set
     */
    public void setEmpleadoresAportantes(List<EmpleadorModeloDTO> empleadoresAportantes) {
        this.empleadoresAportantes = empleadoresAportantes;
    }

    /**
     * @return the rolesAfiliadosPorActualizar
     */
    public List<RolAfiliadoModeloDTO> getRolesAfiliadosPorActualizar() {
        return rolesAfiliadosPorActualizar;
    }

    /**
     * @param rolesAfiliadosPorActualizar the rolesAfiliadosPorActualizar to set
     */
    public void setRolesAfiliadosPorActualizar(List<RolAfiliadoModeloDTO> rolesAfiliadosPorActualizar) {
        this.rolesAfiliadosPorActualizar = rolesAfiliadosPorActualizar;
    }

    /**
     * @return the datosReintegroEmpleadores
     */
    public List<ActivacionEmpleadorDTO> getDatosReintegroEmpleadores() {
        return datosReintegroEmpleadores;
    }

    /**
     * @param datosReintegroEmpleadores the datosReintegroEmpleadores to set
     */
    public void setDatosReintegroEmpleadores(List<ActivacionEmpleadorDTO> datosReintegroEmpleadores) {
        this.datosReintegroEmpleadores = datosReintegroEmpleadores;
    }

	/**
	 * @return the idsTemAporteProcesado
	 */
	public List<Long> getIdsTemAporteProcesado() {
		return idsTemAporteProcesado;
	}

	/**
	 * @param idsTemAporteProcesado the idsTemAporteProcesado to set
	 */
	public void setIdsTemAporteProcesado(List<Long> idsTemAporteProcesado) {
		this.idsTemAporteProcesado = idsTemAporteProcesado;
	}

    /**
     * @return the mapaPresenciaNovedades
     */
    public Map<Long, ConsultaPresenciaNovedadesDTO> getMapaPresenciaNovedades() {
        return mapaPresenciaNovedades;
    }

    /**
     * @param mapaPresenciaNovedades the mapaPresenciaNovedades to set
     */
    public void setMapaPresenciaNovedades(Map<Long, ConsultaPresenciaNovedadesDTO> mapaPresenciaNovedades) {
        this.mapaPresenciaNovedades = mapaPresenciaNovedades;
    }
}
