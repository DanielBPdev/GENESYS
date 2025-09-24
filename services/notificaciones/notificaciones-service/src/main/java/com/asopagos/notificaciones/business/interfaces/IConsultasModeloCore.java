package com.asopagos.notificaciones.business.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.comunicados.VariableComunicado;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.entidades.transversal.core.ModuloPlantillaComunicado;

/**
 * Interfaz de contrato de servicios de consulta del model core
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
@Local
public interface IConsultasModeloCore {

	/**
	 * Método que consulta una plantilla por medio de la etiqueta especificada
	 * 
	 * @param etiqueta
	 * @return
	 */
	public PlantillaComunicado consultarPlantillaComunicado(EtiquetaPlantillaComunicadoEnum etiqueta);

	public ModuloPlantillaComunicado obtenerModuloDePlantilla(Long idPlantillaComunicado);

	/**
	 * Método que consulta la lista de las variables de la plantilla del
	 * comunicado especificado por el identificador
	 * 
	 * @param idPlantillaComunicado
	 * @return
	 */
	public List<VariableComunicado> consultarVariableComunicado(Long idPlantillaComunicado);
}
