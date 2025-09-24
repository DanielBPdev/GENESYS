package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contiene el listado de aportes que se desea procesar<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class PaqueteProcesoAportesDTO implements Serializable {
    private static final long serialVersionUID = 4478547091671838649L;

    /** Mapa de los ID de Registro General que ya presentan aporte general y el Id del aporte */
    private Map<Long, Long> idsRegistrosGenerales;

    /**
     * Mapa de los aportes generales para registrar, la llave corresponde a una llave compuesta del
     * ID del registro general y el registro detallado en los casos de aportes de pensionados e independientes
     */
    private Map<String, AporteGeneralModeloDTO> aportesGenerales;

    /**
     * Mapa de los aportes detallados para registrar, la llave corresponde a una llave compuesta del
     * ID del registro general y el registro detallado en los casos de aportes de pensionados e independientes
     * (misma llave que la del mapa de aportes generales)
     */
    private Map<String, List<JuegoAporteMovimientoDTO>> aportesDetallados;

    /**
     * @return the idsRegistrosGenerales
     */
    public Map<Long, Long> getIdsRegistrosGenerales() {
        return idsRegistrosGenerales;
    }

    /**
     * @param idsRegistrosGenerales
     *        the idsRegistrosGenerales to set
     */
    public void setIdsRegistrosGenerales(Map<Long, Long> idsRegistrosGenerales) {
        this.idsRegistrosGenerales = idsRegistrosGenerales;
    }

    /**
     * @return the aportesGenerales
     */
    public Map<String, AporteGeneralModeloDTO> getAportesGenerales() {
        return aportesGenerales;
    }

    /**
     * @param aportesGenerales
     *        the aportesGenerales to set
     */
    public void setAportesGenerales(Map<String, AporteGeneralModeloDTO> aportesGenerales) {
        this.aportesGenerales = aportesGenerales;
    }

	/**
	 * @return the aportesDetallados
	 */
	public Map<String, List<JuegoAporteMovimientoDTO>> getAportesDetallados() {
		return aportesDetallados;
	}

	/**
	 * @param aportesDetallados the aportesDetallados to set
	 */
	public void setAportesDetallados(Map<String, List<JuegoAporteMovimientoDTO>> aportesDetallados) {
		this.aportesDetallados = aportesDetallados;
	}

	/** Método para la organización de los aportes en el DTO 
	 * @param apdExistentes 
	 * @param apgExistentes */
	@Deprecated
	public void prepararAportes(List<AporteDTO> aportes, List<AporteGeneralModeloDTO> apgExistentes,
			List<AporteDetalladoModeloDTO> apdExistentes) {
        this.aportesGenerales = new HashMap<>();
        this.aportesDetallados = new HashMap<>();
        
        Map<Long, Long> idsRegGenExistentes = new HashMap<>();
        Map<Long, Long> idsRegDetExistentes = new HashMap<>();
        
        JuegoAporteMovimientoDTO juegoAporteMovimiento = null;
        Boolean esEmpresa = null;
        String llave = null;
        
        for (AporteGeneralModeloDTO apg : apgExistentes) {
        	idsRegGenExistentes.put(apg.getIdRegistroGeneral(), apg.getId());
        }
        
        for (AporteDetalladoModeloDTO apd : apdExistentes) {
        	idsRegDetExistentes.put(apd.getIdRegistroDetallado(), apd.getId());
        }

		Long idApgGen = null;
        for (AporteDTO aporte : aportes) {
        	// sólo se preparan los aportes con la información base de la empresa o la persona aportante y la persona cotizante
			if (aporte.getAporteGeneral().getIdEmpresa() != null || aporte.getAporteGeneral().getIdPersona() != null
					&& aporte.getAporteDetallado().getIdPersona() != null){
                esEmpresa = aporte.getAporteGeneral().getIdEmpresa() != null ? Boolean.TRUE : Boolean.FALSE;

                llave = esEmpresa ? aporte.getAporteGeneral().getIdRegistroGeneral().toString()
                        : aporte.getAporteGeneral().getIdRegistroGeneral().toString()
                                + (aporte.getAporteDetallado() != null ? aporte.getAporteDetallado().getIdRegistroDetallado().toString() : "");

    			// sí es empresa y el id de reg gen no existe, se agrega
    			if (esEmpresa && !idsRegGenExistentes.containsKey(aporte.getAporteGeneral().getIdRegistroGeneral())
    					&& !this.aportesGenerales.containsKey(llave)) {
    				this.aportesGenerales.put(llave, aporte.getAporteGeneral());
    			}

    			// sí no es empresa, agregar los registros generales depende de la existencia
    			// del registro detallado
    			if (!esEmpresa && !idsRegDetExistentes.containsKey(aporte.getAporteDetallado().getIdRegistroDetallado())
    					&& !this.aportesGenerales.containsKey(llave)) {
    				this.aportesGenerales.put(llave, aporte.getAporteGeneral());
    			}
    			
    			// se agregan los aportes de los registros detallados no existentes
    			if (!idsRegDetExistentes.containsKey(aporte.getAporteDetallado().getIdRegistroDetallado())) {
    				
    				List<JuegoAporteMovimientoDTO> listaAportesDetallados = this.aportesDetallados.get(llave);
    				if (listaAportesDetallados == null) {
    					listaAportesDetallados = new ArrayList<>();
    					this.aportesDetallados.put(llave, listaAportesDetallados);
    				}
    				
    				// sí el aporte general relacionado con los nuevos aportes detallados, ya existe, se le agreaga su ID a los DTO
    				idApgGen = idsRegGenExistentes.get(aporte.getAporteGeneral().getIdRegistroGeneral());
    				if(idApgGen != null){
    					aporte.getAporteDetallado().setIdAporteGeneral(idApgGen);
    					aporte.getMovimiento().setIdAporteGeneral(idApgGen);
    				}

    				juegoAporteMovimiento = new JuegoAporteMovimientoDTO();
    				juegoAporteMovimiento.setAporteDetallado(aporte.getAporteDetallado());
    				juegoAporteMovimiento.setMovimientoAporte(aporte.getMovimiento());

    				listaAportesDetallados.add(juegoAporteMovimiento);
    			}
        	}
        }
    }
    
    /** 
     * Método para actualizar el ID de aporte general en los aportes detallados por registrar 
     * */
    public void actualizarIdsParaAportesDetallados(Map<String, Long> idsPersistidos){
    	Long id = null;
    	for(String llavePersistida : idsPersistidos.keySet()){
    		Object idObj = idsPersistidos.get(llavePersistida);
    		if(idObj instanceof Long){
    			id = (Long) idObj;
    		}else{
    			id = new Long(idObj.toString());
    		}    		
    		
    		AporteGeneralModeloDTO aporteGen = this.aportesGenerales.get(llavePersistida);
    		if(aporteGen != null){
    			aporteGen.setId(id);
    		}
    		
    		List<JuegoAporteMovimientoDTO> aportesDet = this.aportesDetallados.get(llavePersistida);
    		
    		for (JuegoAporteMovimientoDTO aporteDet : aportesDet) {
				aporteDet.getAporteDetallado().setIdAporteGeneral(id);
				aporteDet.getMovimientoAporte().setIdAporteGeneral(id);
				aporteDet.getAporteDetallado().setModalidadRecaudoAporte(aporteGen.getModalidadRecaudoAporte());
			}
    	}
    }
}
