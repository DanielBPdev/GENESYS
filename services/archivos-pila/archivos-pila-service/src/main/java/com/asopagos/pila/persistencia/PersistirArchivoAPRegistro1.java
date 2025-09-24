/**
 * 
 */
package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoAPRegistro1;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAPEnum;
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
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 1 del archivo AP<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class PersistirArchivoAPRegistro1 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoAPRegistro1.class);

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
                PilaArchivoAPRegistro1 registro = new PilaArchivoAPRegistro1();
                registro.setIndicePlanilla(indicePlanilla);

                registro.setNombrePagador((String) datos.get(EtiquetaArchivoAPEnum.AP11.getNombreCampo()));
                registro.setTipoIdPagador((String) datos.get(EtiquetaArchivoAPEnum.AP12.getNombreCampo()));
                registro.setIdPagador((String) datos.get(EtiquetaArchivoAPEnum.AP13.getNombreCampo()));

                if (datos.get(EtiquetaArchivoAPEnum.AP14.getNombreCampo()) != null) {
                    registro.setDigVerPagador(((Integer) datos.get(EtiquetaArchivoAPEnum.AP14.getNombreCampo())).shortValue());
                }
                else {
                    registro.setDigVerPagador((short) 0);
                }

                registro.setCodSucursal((String) datos.get(EtiquetaArchivoAPEnum.AP15.getNombreCampo()));
                registro.setNomSucursal((String) datos.get(EtiquetaArchivoAPEnum.AP16.getNombreCampo()));
                registro.setClasePagador((String) datos.get(EtiquetaArchivoAPEnum.AP17.getNombreCampo()));
                registro.setNaturalezaJuridica(((Integer) datos.get(EtiquetaArchivoAPEnum.AP18.getNombreCampo())).shortValue());
                registro.setTipoPersona((String) datos.get(EtiquetaArchivoAPEnum.AP19.getNombreCampo()));
                registro.setFormaPresentacion((String) datos.get(EtiquetaArchivoAPEnum.AP110.getNombreCampo()));
                registro.setDireccion((String) datos.get(EtiquetaArchivoAPEnum.AP111.getNombreCampo()));
                registro.setCodCiudad((String) datos.get(EtiquetaArchivoAPEnum.AP112.getNombreCampo()));
                registro.setCodDepartamento((String) datos.get(EtiquetaArchivoAPEnum.AP113.getNombreCampo()));
                registro.setCodActividadEconomica(((Integer) datos.get(EtiquetaArchivoAPEnum.AP114.getNombreCampo())).shortValue());
                registro.setTelefono(((BigDecimal) datos.get(EtiquetaArchivoAPEnum.AP115.getNombreCampo())).longValue());
                registro.setFax(datos.get(EtiquetaArchivoAPEnum.AP116.getNombreCampo()) != null
                        ? ((BigDecimal) datos.get(EtiquetaArchivoAPEnum.AP116.getNombreCampo())).longValue() : null);
                registro.setEmail((String) datos.get(EtiquetaArchivoAPEnum.AP117.getNombreCampo()));
                registro.setIdRepresentante((String) datos.get(EtiquetaArchivoAPEnum.AP118.getNombreCampo()));

                if (datos.get(EtiquetaArchivoAPEnum.AP119.getNombreCampo()) != null) {
                    registro.setDigVerRepresentante(((Integer) datos.get(EtiquetaArchivoAPEnum.AP119.getNombreCampo())).shortValue());
                }
                else {
                    registro.setDigVerRepresentante((short) 0);
                }

                registro.setTipoIdRepresentante((String) datos.get(EtiquetaArchivoAPEnum.AP120.getNombreCampo()));
                registro.setPrimerApellidoRep((String) datos.get(EtiquetaArchivoAPEnum.AP121.getNombreCampo()));
                registro.setSegundoApellidoRep((String) datos.get(EtiquetaArchivoAPEnum.AP122.getNombreCampo()));
                registro.setPrimerNombreRep((String) datos.get(EtiquetaArchivoAPEnum.AP123.getNombreCampo()));
                registro.setSegundoNombreRep((String) datos.get(EtiquetaArchivoAPEnum.AP124.getNombreCampo()));
                registro.setCodOperador(((Integer) datos.get(EtiquetaArchivoAPEnum.AP125.getNombreCampo())).shortValue());
                registro.setPeriodoAporte((String) datos.get(EtiquetaArchivoAPEnum.AP126.getNombreCampo()));
                registro.setTipoPagador(((Integer) datos.get(EtiquetaArchivoAPEnum.AP127.getNombreCampo())).shortValue());

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
     * Metodo encargado de devolver los cambios
     * @param EntityManager
     *        objeto para los objetos
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
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_AP_REGISTRO_1).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_AP_REGISTRO_1).setParameter("listaId", idRegistro).executeUpdate();
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
