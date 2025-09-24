package com.asopagos.notificaciones.envio;

import com.asopagos.notificaciones.dto.NotificacionDTO;

public interface EnvioComunicado {

	public void procesar(NotificacionDTO notificacion);
	
}
