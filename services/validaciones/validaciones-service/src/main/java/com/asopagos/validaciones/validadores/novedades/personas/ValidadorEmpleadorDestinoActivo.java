package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.util.EstadosUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * El sistema verifica que parA el empleador de destino, el campo "estado de afiliación" tenga el valor "Activo".
 *  
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEmpleadorDestinoActivo extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorEmpleadorDestinoActivo.execute");
        try{
            Long idEmpleadorDestino = Long.parseLong(datosValidacion.get(ConstantesValidaciones.ID_EMPL_DEST_PARAM));
            
            // Se consulta el empleador con el id
            Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, idEmpleadorDestino)
                    .getSingleResult();

            List<ConsultarEstadoDTO> listConsulta = new ArrayList<ConsultarEstadoDTO>();
            ConsultarEstadoDTO paramsConsulta = new ConsultarEstadoDTO();
            paramsConsulta.setEntityManager(entityManager);
            paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
            paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
            paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
            listConsulta.add(paramsConsulta);

            List<EstadoDTO> listEstado = EstadosUtils.consultarEstadoCaja(listConsulta);
            empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(listEstado.get(0).getEstado().toString()));
            
            // Se listan los estados aceptados para la ejecución 
            // exitosa de la validación y habilitación de la novedad
            List<EstadoEmpleadorEnum> estadosValidos = new ArrayList<EstadoEmpleadorEnum>();
            estadosValidos.add(EstadoEmpleadorEnum.ACTIVO);
            
            // Se valida la condición
            if(estadosValidos.contains(empleador.getEstadoEmpleador())){
                logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorDestinoActivo.execute");
                // Validación exitosa
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_DESTINO_ACTIVO_PERSONA); 
            }else{
                logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorDestinoActivo.execute");
                // Validación no aprobada
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EMPL_DESTINO_DIFERENTE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_EMPLEADOR_DESTINO_ACTIVO_PERSONA,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
                    ValidacionCoreEnum.VALIDACION_EMPLEADOR_DESTINO_ACTIVO_PERSONA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
