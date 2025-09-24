package com.asopagos.notificaciones.envio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.asopagos.bandejainconsistencias.clients.ActualizarActualizacionDatosEmp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.aportes.ActualizacionDatosEmpleadorModeloDTO;
import com.asopagos.enumeraciones.aportes.CanalContactoEmpleadoresEnum;
import com.asopagos.enumeraciones.aportes.TipoInconsistenciasDatosEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.NotificacionDTO;

public class EnvioComunicadoFallidoPila implements EnvioComunicado{

	/**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(EnvioComunicadoFallidoPila.class);
	
	@Override
	public void procesar(NotificacionDTO notificacion) {
		if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(
                TipoAfiliadoEnum.valueOf(notificacion.getParams().get("tipoAfiliado")))) {
            
            ActualizacionDatosEmpleadorModeloDTO ade = new ActualizacionDatosEmpleadorModeloDTO();

            ade.setTipoInconsistencia(TipoInconsistenciasDatosEmpleadorEnum.CORREO_ELECTRONICO);
            ade.setCanalContacto(CanalContactoEmpleadoresEnum.PILA);
            ade.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.PENDIENTE_GESTION);
            Date fechaIngreso = new Date();
            ade.setFechaIngreso(fechaIngreso);
            ade.setFechaRespuesta(null);
            ade.setObservaciones(null);
            ade.setEmpresa(Long.parseLong(notificacion.getParams().get("idEmpresa")));

            actualizarDatosEmpleador(ade);
        }
		
	}
	
	/**
     * Método encargado de hacer el llamado al microservicio que envía los datos del empleador,
     * al que no se le pudo enviar el comunicado, a una bandeja de incosnsitencia para que sea
     * atendida después manualmente
     * 
     * @param ade
     *        datos para la actualzación de los datos del empleador en la bandeja
     */
    private void actualizarDatosEmpleador(ActualizacionDatosEmpleadorModeloDTO ade) {
        try {
            List<ActualizacionDatosEmpleadorModeloDTO> lista = new ArrayList<>();
            lista.add(ade);

            ActualizarActualizacionDatosEmp actualizarDatos = new ActualizarActualizacionDatosEmp(lista);
            actualizarDatos.execute();
        } catch (Exception e) {
        	logger.debug(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,e);
        }
    }

}
