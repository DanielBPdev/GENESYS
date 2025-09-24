package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro3;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.EntityManagerProceduresPeristanceLocal;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 3 del archivo I<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class PersistirArchivoIRegistro3 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoIRegistro3.class);

    private final int BATCH_SIZE = 100;
    
    /**
     * Este metodo se encarga de realizar la persistendia del contenido del registro
     * 
     * @param List<LineArgumentDTO>
     *        Lista de parametros (incluye al contexto y el contenido de la línea)
     * @param EntityManager
     *        Objeto para el manejo de la persistencia
     * @exception FileProcessingException
     *            Error al almacenar el registro
     */
    @Override
    public void persistLine(List<LineArgumentDTO> paramList, EntityManager emCore) throws FileProcessingException {
        logger.info("Inicia persistLine(List<LineArgumentDTO>, EntityManager)ll");

        // se obtiene instancia del EM para modelo de datos PILA
        EntityManager em = obtenerEntityPila();

        // se toma el indice de planilla del contexto
 
        IndicePlanilla indicePlanilla = (IndicePlanilla) paramList.get(0).getContext().get(ConstantesComunesProcesamientoPILA.INDICE_PLANILLA);
        if (indicePlanilla == null) {
            logger.error("Finaliza persistLine(List<LineArgumentDTO>, EntityManager) - "
                    + MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA
                            .getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
            throw new FileProcessingException(
                    MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
        }

        try {
            /*
             * El registro tipo 3 consta de 3 líneas diferentes, en consecuencia, para su persistencia en una sola línea
             * es necesario establecer si se ha ingresado alguna parte del mismo registro en otra ocasión con el fin de
             * actualizar el registro con los nuevos valores entrantes o sino se procede a crear el registro
             */
            PilaArchivoIRegistro3 registro = null;
            try {
                registro = (PilaArchivoIRegistro3) em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_I_REGISTRO_3)
                        .setParameter(ConstantesComunesProcesamientoPILA.INDICE_PLANILLA, indicePlanilla).getSingleResult();

                if (registro != null) {
                    int i = 1;
                    for (LineArgumentDTO lineArgumentDTO : paramList) {
                        
                        if (i > 0 && i % BATCH_SIZE == 0) {
                            em.flush();
                            em.clear();
                        }
                        
                        Map<String, Object> datos = lineArgumentDTO.getLineValues();
                        agregarValores(registro, datos);
                        System.out.print("PersistirArchivoIRegistro3 -> "+indicePlanilla.getId()+" registro.getId()"+registro.getId());
                        // actualizo un registro existente
                        em.merge(registro);
                        i++;
                    }
                    em.flush();
                }
            } catch (NoResultException e) {
                
                int i = 1;
                for (LineArgumentDTO lineArgumentDTO : paramList) {
                    
                    if (i > 0 && i % BATCH_SIZE == 0) {
                        em.flush();
                        em.clear();
                    }
                    
                    Map<String, Object> datos = lineArgumentDTO.getLineValues();

                    registro = new PilaArchivoIRegistro3();
                    registro.setIndicePlanilla(indicePlanilla);

                    agregarValores(registro, datos);

                    // agrego un nuevo registro
                    em.persist(registro);
                    System.out.print("PersistirArchivoIRegistro3 registro.getId()-> "+registro.getId());
                    idRegistro.add(registro.getId());
                    i++;
                }
                em.flush();
            }
        } catch (Exception e) {
            logger.error("Finaliza persistLine(List<LineArgumentDTO>, EntityManager) - "
                    + MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
            throw new FileProcessingException(
                    MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
        }

        logger.info("Finaliza persistLine(List<LineArgumentDTO>, EntityManager)ll");
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
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_3).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_3).setParameter("listaId", idRegistro).executeUpdate();
            }
        }
    }

    /**
     * Procedimiento para ingresar los valores de campo en el DTO de la entidad
     * @param registro
     *        DTO de la entidad
     * @param datos
     *        Valores de los campos de la línea
     */
    private void agregarValores(PilaArchivoIRegistro3 registro, Map<String, Object> datos) {
        logger.debug("Inicia agregarValores(PilaArchivoIRegistro3, Map<String, Object>)");

        String campoTemp = "";
        try {
            if (registro.getValorTotalIBC() == null) {
                campoTemp = EtiquetaArchivoIEnum.I3_13.getNombreCampo();
                registro.setValorTotalIBC((BigDecimal) datos.get(campoTemp));
            }

            if (registro.getValorTotalAporteObligatorio() == null) {
                campoTemp = EtiquetaArchivoIEnum.I3_14.getNombreCampo();
                registro.setValorTotalAporteObligatorio((BigDecimal) datos.get(campoTemp));
            }

            if (registro.getDiasMora() == null) {
                campoTemp = EtiquetaArchivoIEnum.I3_23.getNombreCampo();
                registro.setDiasMora(((Integer) datos.get(campoTemp)).shortValue());
            }

            if (registro.getValorMora() == null) {
                campoTemp = EtiquetaArchivoIEnum.I3_24.getNombreCampo();
                registro.setValorMora((BigDecimal) datos.get(campoTemp));
            }

            if (registro.getValorTotalAportes() == null) {
                campoTemp = EtiquetaArchivoIEnum.I3_33.getNombreCampo();
                registro.setValorTotalAportes((BigDecimal) datos.get(campoTemp));
            }
        } catch (NullPointerException e) {
            // Un puntero nulo en esta función se da porque las líneas como se lee no tienen todos los campos
        }

        logger.debug("Finaliza agregarValores(PilaArchivoIRegistro3, Map<String, Object>)");
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
