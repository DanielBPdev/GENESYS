package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro1;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
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
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 1 del archivo IP<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class PersistirArchivoIPRegistro1 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoIPRegistro1.class);

    private final int BATCH_SIZE = 100;
    
    /**
     * Este metodo se encarga de realizar la persistencia del contenido del registro
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

        IndicePlanilla indicePlanilla = (IndicePlanilla) paramList.get(0).getContext().get(ConstantesComunesProcesamientoPILA.INDICE_PLANILLA);
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

                PilaArchivoIPRegistro1 registro = new PilaArchivoIPRegistro1();
                registro.setIndicePlanilla(indicePlanilla);
                registro.setSecuencia((Integer) datos.get(EtiquetaArchivoIPEnum.IP12.getNombreCampo()));
                registro.setIdAdministradora((String) datos.get(EtiquetaArchivoIPEnum.IP13.getNombreCampo()));
                registro.setDigVerAdministradora(((Integer) datos.get(EtiquetaArchivoIPEnum.IP14.getNombreCampo())).shortValue());
                registro.setCodAdministradora((String) datos.get(EtiquetaArchivoIPEnum.IP15.getNombreCampo()));
                registro.setNombrePagador((String) datos.get(EtiquetaArchivoIPEnum.IP16.getNombreCampo()));
                registro.setTipoIdPagador((String) datos.get(EtiquetaArchivoIPEnum.IP17.getNombreCampo()));
                registro.setIdPagador((String) datos.get(EtiquetaArchivoIPEnum.IP18.getNombreCampo()));

                if (datos.get(EtiquetaArchivoIPEnum.IP19.getNombreCampo()) != null) {
                    registro.setDigVerPagador(((Integer) datos.get(EtiquetaArchivoIPEnum.IP19.getNombreCampo())).shortValue());
                }else{
                    registro.setDigVerPagador((short) 0);
                }

                registro.setPeriodoAporte((String) datos.get(EtiquetaArchivoIPEnum.IP110.getNombreCampo()));
                registro.setFechaPago((Date) datos.get(EtiquetaArchivoIPEnum.IP111.getNombreCampo()));
                registro.setNumPlanilla((String) datos.get(EtiquetaArchivoIPEnum.IP112.getNombreCampo()));
                registro.setFormaPresentacion((String) datos.get(EtiquetaArchivoIPEnum.IP113.getNombreCampo()));
                registro.setCodSucursal((String) datos.get(EtiquetaArchivoIPEnum.IP114.getNombreCampo()));
                registro.setNomSucursal((String) datos.get(EtiquetaArchivoIPEnum.IP115.getNombreCampo()));
                registro.setValorTotalMesadas(((BigDecimal) datos.get(EtiquetaArchivoIPEnum.IP116.getNombreCampo())).longValue());
                registro.setCantPensionados((Integer) datos.get(EtiquetaArchivoIPEnum.IP117.getNombreCampo()));
                registro.setDiasMora(((Integer) datos.get(EtiquetaArchivoIPEnum.IP118.getNombreCampo())).shortValue());
                registro.setCodOperador(((Integer) datos.get(EtiquetaArchivoIPEnum.IP119.getNombreCampo())).shortValue());
                registro.setCantidadReg2(((BigDecimal) datos.get(EtiquetaArchivoIPEnum.IP120.getNombreCampo())).intValue());
                registro.setFechaActualizacion((Date) datos.get(EtiquetaArchivoIPEnum.IP121.getNombreCampo()));

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
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_IP_REGISTRO_1).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_IP_REGISTRO_1).setParameter("listaId", idRegistro).executeUpdate();
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
