package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro1;
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
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 1 del archivo I<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class PersistirArchivoIRegistro1 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoIRegistro1.class);

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

                PilaArchivoIRegistro1 registro = new PilaArchivoIRegistro1();
                registro.setIndicePlanilla(indicePlanilla);
                registro.setIdCCF((String) datos.get(EtiquetaArchivoIEnum.I14.getNombreCampo()));
                registro.setDigVerCCF(((Integer) datos.get(EtiquetaArchivoIEnum.I15.getNombreCampo())).shortValue());
                registro.setRazonSocial((String) datos.get(EtiquetaArchivoIEnum.I16.getNombreCampo()));
                registro.setTipoDocAportante((String) datos.get(EtiquetaArchivoIEnum.I17.getNombreCampo()));
                registro.setIdAportante((String) datos.get(EtiquetaArchivoIEnum.I18.getNombreCampo()));

                if (datos.get(EtiquetaArchivoIEnum.I19.getNombreCampo()) != null) {
                    registro.setDigVerAportante(((Integer) datos.get(EtiquetaArchivoIEnum.I19.getNombreCampo())).shortValue());
                }
                else {
                    registro.setDigVerAportante((short) 0);
                }

                registro.setTipoAportante((String) datos.get(EtiquetaArchivoIEnum.I110.getNombreCampo()));
                registro.setDireccion((String) datos.get(EtiquetaArchivoIEnum.I111.getNombreCampo()));
                registro.setCodCiudad((String) datos.get(EtiquetaArchivoIEnum.I112.getNombreCampo()));
                registro.setCodDepartamento((String) datos.get(EtiquetaArchivoIEnum.I113.getNombreCampo()));
                registro.setTelefono(((BigDecimal) datos.get(EtiquetaArchivoIEnum.I114.getNombreCampo())).longValue());
                registro.setFax(datos.get(EtiquetaArchivoIEnum.I115.getNombreCampo()) != null
                        ? ((BigDecimal) datos.get(EtiquetaArchivoIEnum.I115.getNombreCampo())).longValue() : null);
                registro.setEmail((String) datos.get(EtiquetaArchivoIEnum.I116.getNombreCampo()));
                registro.setPeriodoAporte((String) datos.get(EtiquetaArchivoIEnum.I117.getNombreCampo()));
                registro.setTipoPlanilla((String) datos.get(EtiquetaArchivoIEnum.I118.getNombreCampo()));
                registro.setFechaPagoAsociado((Date) datos.get(EtiquetaArchivoIEnum.I119.getNombreCampo()));
                registro.setFechaPago((Date) datos.get(EtiquetaArchivoIEnum.I120.getNombreCampo()));
                registro.setNumPlanillaAsociada((String) datos.get(EtiquetaArchivoIEnum.I121.getNombreCampo()));
                registro.setNumPlanilla((String) datos.get(EtiquetaArchivoIEnum.I122.getNombreCampo()));
                registro.setPresentacion((String) datos.get(EtiquetaArchivoIEnum.I123.getNombreCampo()));
                registro.setCodSucursal((String) datos.get(EtiquetaArchivoIEnum.I124.getNombreCampo()));
                registro.setNomSucursal((String) datos.get(EtiquetaArchivoIEnum.I125.getNombreCampo()));
                registro.setCantidadEmpleados((Integer) datos.get(EtiquetaArchivoIEnum.I126.getNombreCampo()));
                registro.setCantidadAfiliados((Integer) datos.get(EtiquetaArchivoIEnum.I127.getNombreCampo()));
                registro.setCodOperador(((Integer) datos.get(EtiquetaArchivoIEnum.I128.getNombreCampo())).shortValue());
                registro.setModalidadPlanilla(((Integer) datos.get(EtiquetaArchivoIEnum.I129.getNombreCampo())).shortValue());
                registro.setDiasMora(((Integer) datos.get(EtiquetaArchivoIEnum.I130.getNombreCampo())).shortValue());
                registro.setCantidadReg2(((BigDecimal) datos.get(EtiquetaArchivoIEnum.I131.getNombreCampo())).intValue());
                registro.setFechaMatricula((Date) datos.get(EtiquetaArchivoIEnum.I132.getNombreCampo()));
                registro.setCodDepartamentoBeneficio((String) datos.get(EtiquetaArchivoIEnum.I133.getNombreCampo()));
                registro.setAcogeBeneficio((String) datos.get(EtiquetaArchivoIEnum.I134.getNombreCampo()));
                registro.setClaseAportante((String) datos.get(EtiquetaArchivoIEnum.I135.getNombreCampo()));
                registro.setNaturalezaJuridica(((Integer) datos.get(EtiquetaArchivoIEnum.I136.getNombreCampo())).shortValue());
                registro.setTipoPersona((String) datos.get(EtiquetaArchivoIEnum.I137.getNombreCampo()));
                registro.setFechaActualizacion((Date) datos.get(EtiquetaArchivoIEnum.I138.getNombreCampo()));

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
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_1).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_1).setParameter("listaId", idRegistro).executeUpdate();
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
