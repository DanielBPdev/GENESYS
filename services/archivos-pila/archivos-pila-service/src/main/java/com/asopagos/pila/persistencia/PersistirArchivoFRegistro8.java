package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro8;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoFEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.EntityManagerProceduresPeristanceLocal;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.dto.ControlLoteOFDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 8 del archivo F<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 407 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class PersistirArchivoFRegistro8 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoFRegistro8.class);

    private final int BATCH_SIZE = 100;
    
    /**
     * Este metodo se encarga de realizar la persistencia del contenido del registro
     * 
     * @param EntityManager
     *        Objeto para el manejo de la persistencia
     * @exception FileProcessingException
     *            Error al almacenar el registro
     */
    @SuppressWarnings("unchecked")
    @Override
    public void persistLine(List<LineArgumentDTO> paramList, EntityManager emCore) throws FileProcessingException {
        logger.debug("Inicia persistLine(List<LineArgumentDTO>, EntityManager)");

        // se obtiene instancia del EM para modelo de datos PILA
        EntityManager em = obtenerEntityPila();

        // se obtiene el contexto
        Map<String, Object> contexto = paramList.get(0).getContext();

        // se toma el indice de planilla del contexto

        IndicePlanillaOF indicePlanilla = (IndicePlanillaOF) paramList.get(0).getContext()
                .get(ConstantesComunesProcesamientoPILA.INDICE_PLANILLA);
        if (indicePlanilla == null) {
            logger.error("Finaliza persistLine(List<LineArgumentDTO>, EntityManager) - "
                    + MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA
                            .getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
            throw new FileProcessingException(
                    MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
        }

        try {
            int i = 1;
            for (LineArgumentDTO lineArgumentDTO : paramList) {
                
                if (i > 0 && i % BATCH_SIZE == 0) {
                    em.flush();
                    em.clear();
                }
                
                Map<String, Object> datos = lineArgumentDTO.getLineValues();

                PilaArchivoFRegistro8 registro = new PilaArchivoFRegistro8();
                registro.setIndicePlanilla(indicePlanilla);

                registro.setCantidadPlanillas((Integer) datos.get(EtiquetaArchivoFEnum.F82.getNombreCampo()));
                registro.setCantidadRegistros((Integer) datos.get(EtiquetaArchivoFEnum.F83.getNombreCampo()));
                // el valor de este campo incluye 2 posiciones decimales sin punto, se divide entre 100
                registro.setValorRecaudado(
                        ((BigDecimal) datos.get(EtiquetaArchivoFEnum.F84.getNombreCampo())).divide(BigDecimal.valueOf(100)));

                // se obtiene el ID del registro tipo 5 relacionado mediante el uso del índice de planilla y el número de lote
                Integer numeroLote = null;
                Map<Integer, ControlLoteOFDTO> mapaControlSumatorias = (Map<Integer, ControlLoteOFDTO>) contexto
                        .get(ConstantesContexto.MAPA_CONTROL_SUMATORIAS_LOTES_OF);
                if (mapaControlSumatorias != null) {
                    numeroLote = FuncionesValidador.buscarLoteLinea(lineArgumentDTO.getLineNumber(), mapaControlSumatorias);
                }
                Long idRegistro5 = obtenerIdRegistro5(em, indicePlanilla, numeroLote != null ? numeroLote.shortValue() : null);

                registro.setIdPilaArchivoFRegistro5(idRegistro5);

                em.persist(registro);
                idRegistro.add(registro.getId());
                i++;
            }
            em.flush();
            
        } catch (Exception e) {
            logger.error(
                    "Finaliza persistLine(List<LineArgumentDTO>, EntityManager) - " + MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                            .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()));
            throw new FileProcessingException(MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                    .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()));
        }
        logger.debug("Finaliza persistLine(List<LineArgumentDTO>, EntityManager)");
    }

    /**
     * Método empleado para consultar el ID del registro 5 asociado
     * @param em
     *        EntityManager para el modelo de datos de PILA
     * @param indicePlanilla
     *        Entrada de índice de planilla del archivo
     * @param numeroLote
     *        Número de lote del registro 5 empleado para la consulta
     * @return <b>Long</b>
     *         Id del registro tipo 5 asociado
     */
    private Long obtenerIdRegistro5(EntityManager em, IndicePlanillaOF indicePlanilla, Short numeroLote) {
        logger.debug("Inicia obtenerIdRegistro5(IndicePlanillaOF, Integer)");

        Long result = null;

        result = (Long) em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_F_ID_REGISTRO_5)
                .setParameter("indicePlanilla", indicePlanilla).setParameter("numeroLote", numeroLote).setMaxResults(1).getSingleResult();

        logger.debug("Finaliza obtenerIdRegistro5(IndicePlanillaOF, Integer)");
        return result;
    }

    /**
     * Este metodo se encarga de devolver los cambios
     * 
     * @param EntityManager
     *        objeto para el manejo de la persistencia
     * @exception FileProcessingException
     *            error al devolver los cambios
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        // se obtiene instancia del EM para modelo de datos PILA
        EntityManager emPila = obtenerEntityPila();

        if (idRegistro != null && !idRegistro.isEmpty()) {
            List<Long> listaIdsLote = new ArrayList<>(); 
            
            while (BATCH_SIZE < idRegistro.size()) {
                listaIdsLote.addAll(idRegistro.subList(0, BATCH_SIZE-1));
                idRegistro.removeAll(listaIdsLote);
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_F_REGISTRO_8).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_F_REGISTRO_8).setParameter("listaId", idRegistro).executeUpdate();
            }
        }
    }

    /**
     * Método encargado de obtener una instancia del EntityManager direccionado al modelo de datos de PILA
     * @return <b>EntityManager</b>
     *         Instancia del EntityManager direccionado al modelo de datos de PILA
     */
    private EntityManager obtenerEntityPila() {
        EntityManagerProceduresPeristanceLocal emPila = ResourceLocator.lookupEJBReference(EntityManagerProceduresPeristanceLocal.class);
        return emPila.getEntityManager();
    }

}
