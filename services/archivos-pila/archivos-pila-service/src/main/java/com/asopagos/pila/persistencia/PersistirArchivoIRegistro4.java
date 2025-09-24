package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro4;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
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
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 4 del archivo I<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 - CC 0226581 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class PersistirArchivoIRegistro4 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoIRegistro4.class);

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
        logger.debug("Inicia persistLine(List<LineArgumentDTO>, EntityManager)");

        // se obtiene instancia del EM para modelo de datos PILA
        EntityManager em = obtenerEntityPila();

        // se toma el indice de planilla del contexto

        IndicePlanilla indicePlanilla = (IndicePlanilla) paramList.get(0).getContext()
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

                PilaArchivoIRegistro4 registro = new PilaArchivoIRegistro4();
                registro.setIndicePlanilla(indicePlanilla);
                registro.setIndicadorUGPP(((Integer) datos.get(EtiquetaArchivoIEnum.I42.getNombreCampo())).shortValue());
                registro.setActoAdministrativo((String) datos.get(EtiquetaArchivoIEnum.I43.getNombreCampo()));
                if (datos.get(EtiquetaArchivoIEnum.I44) != null) {
                    registro.setFechaAperturaLiquidacionForzosa((Date) datos.get(EtiquetaArchivoIEnum.I44.getNombreCampo()));
                }
                if (datos.get(EtiquetaArchivoIEnum.I45) != null) {
                    registro.setNombreEntidadLiquidacionForzosa((String) datos.get(EtiquetaArchivoIEnum.I45.getNombreCampo()));
                }

                registro.setValorSancion((BigDecimal) datos.get(EtiquetaArchivoIEnum.I46.getNombreCampo()));

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
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_4).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_4).setParameter("listaId", idRegistro).executeUpdate();
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
