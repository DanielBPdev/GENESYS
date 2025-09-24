package com.asopagos.validaciones.fovis.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ParametroConsultaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasFOVISUtils;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * CLASE QUE VALIDA:
 * Cuando la persona objeto de la novedad está incluida en una
 * postulación de subsidio FOVIS vigente, se debe enviar una notificación
 * que será recibida y ejecutada por la HU-325-77 Analizar
 * novedad persona que afecta postulación FOVIS y registrar resultado
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPostuladoFOVIS extends ValidadorFovisAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorPostuladoFOVIS.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacion = null;
            String numeroIdentificacion = null;
            // Se identifica si se valida la informacion de un beneficiario
            if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null) {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
            }
            else {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            }
            
            // Se realiza la consulta por medio del utilitario
            List<ParametroConsultaDTO> listaParametros = new ArrayList<>();
            ParametroConsultaDTO dto = new ParametroConsultaDTO();
            dto.setEntityManager(entityManager);
            dto.setNumeroIdentificacion(numeroIdentificacion);
            dto.setTipoIdentificacion(tipoIdentificacion);
            listaParametros.add(dto);
            List<PersonaPostulacionDTO> listPostulaciones = PersonasFOVISUtils.consultarPostulacionVigente(listaParametros);
            
            if (listPostulaciones != null && !listPostulaciones.isEmpty()) {
                logger.debug("NO HABILITADA- Fin de método ValidadorPostuladoFOVIS.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_POSTULADO_FOVIS),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_POSTULADO_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            } else {
                logger.debug("HABILITADA- Fin de método ValidadorPostuladoFOVIS.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_POSTULADO_FOVIS);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_POSTULADO_FOVIS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}