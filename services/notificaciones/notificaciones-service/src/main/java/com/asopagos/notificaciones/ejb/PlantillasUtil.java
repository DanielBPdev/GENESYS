package com.asopagos.notificaciones.ejb;

import java.util.List;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.comunicados.VariableComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;

/**
 * Clase que se encarga de construir cada uno de los tipos posibles de
 * plantillas comunicados
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
public class PlantillasUtil {

	/**
	 * Clase que determina cual constructor de notificación de usar
	 */
	public static NotificacionParametrizadaDTO construirPlantilla(NotificacionParametrizadaDTO notificacion,
			PlantillaComunicado plantilla, List<VariableComunicado> variables) {

	    if(notificacion.getAsunto()==null || notificacion.getAsunto().isEmpty()){
	        notificacion.setAsunto(plantilla.getAsunto());
	    }
		if(notificacion.getMensaje()==null || notificacion.getMensaje().isEmpty()){
		    notificacion.setMensaje(plantilla.getMensaje());
		}
		return notificacion;
	}
}
