package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoARegistro1;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAEnum;
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
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 1 del archivo A<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class PersistirArchivoARegistro1 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoARegistro1.class);

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

                PilaArchivoARegistro1 registro = new PilaArchivoARegistro1();
                registro.setIndicePlanilla(indicePlanilla);

                registro.setNombreAportante((String) datos.get(EtiquetaArchivoAEnum.A11.getNombreCampo()));
                registro.setTipoIdAportante((String) datos.get(EtiquetaArchivoAEnum.A12.getNombreCampo()));
                registro.setIdAportante((String) datos.get(EtiquetaArchivoAEnum.A13.getNombreCampo()));

                if (datos.get(EtiquetaArchivoAEnum.A14.getNombreCampo()) != null) {
                    registro.setDigVerAportante(((Integer) datos.get(EtiquetaArchivoAEnum.A14.getNombreCampo())).shortValue());
                }
                else {
                    registro.setDigVerAportante((short) 0);
                }

                registro.setCodSucursal((String) datos.get(EtiquetaArchivoAEnum.A15.getNombreCampo()));
                registro.setNomSucursal((String) datos.get(EtiquetaArchivoAEnum.A16.getNombreCampo()));
                registro.setClaseAportante((String) datos.get(EtiquetaArchivoAEnum.A17.getNombreCampo()));
                registro.setNaturalezaJuridica(((Integer) datos.get(EtiquetaArchivoAEnum.A18.getNombreCampo())).shortValue());
                registro.setTipoPersona((String) datos.get(EtiquetaArchivoAEnum.A19.getNombreCampo()));
                registro.setFormaPresentacion((String) datos.get(EtiquetaArchivoAEnum.A110.getNombreCampo()));
                registro.setDireccion((String) datos.get(EtiquetaArchivoAEnum.A111.getNombreCampo()));
                registro.setCodCiudad((String) datos.get(EtiquetaArchivoAEnum.A112.getNombreCampo()));
                registro.setCodDepartamento((String) datos.get(EtiquetaArchivoAEnum.A113.getNombreCampo()));
                registro.setCodActividadEconomica(((Integer) datos.get(EtiquetaArchivoAEnum.A114.getNombreCampo())).shortValue());
                registro.setTelefono(((BigDecimal) datos.get(EtiquetaArchivoAEnum.A115.getNombreCampo())).longValue());
                registro.setFax(datos.get(EtiquetaArchivoAEnum.A116.getNombreCampo()) != null
                        ? ((BigDecimal) datos.get(EtiquetaArchivoAEnum.A116.getNombreCampo())).longValue() : null);
                registro.setEmail((String) datos.get(EtiquetaArchivoAEnum.A117.getNombreCampo()));
                registro.setIdRepresentante((String) datos.get(EtiquetaArchivoAEnum.A118.getNombreCampo()));

                if (datos.get(EtiquetaArchivoAEnum.A119.getNombreCampo()) != null) {
                    registro.setDigVerRepresentante(((Integer) datos.get(EtiquetaArchivoAEnum.A119.getNombreCampo())).shortValue());
                }
                else {
                    registro.setDigVerRepresentante((short) 0);
                }

                registro.setTipoIdRepresentante((String) datos.get(EtiquetaArchivoAEnum.A120.getNombreCampo()));
                registro.setPrimerApellidoRep((String) datos.get(EtiquetaArchivoAEnum.A121.getNombreCampo()));
                registro.setSegundoApellidoRep((String) datos.get(EtiquetaArchivoAEnum.A122.getNombreCampo()));
                registro.setPrimerNombreRep((String) datos.get(EtiquetaArchivoAEnum.A123.getNombreCampo()));
                registro.setSegundoNombreRep((String) datos.get(EtiquetaArchivoAEnum.A124.getNombreCampo()));
                registro.setFechaInicioConcordato((Date) datos.get(EtiquetaArchivoAEnum.A125.getNombreCampo()));

                if (datos.get(EtiquetaArchivoAEnum.A126.getNombreCampo()) != null) {
                    registro.setTipoAccion(((Integer) datos.get(EtiquetaArchivoAEnum.A126.getNombreCampo())).shortValue());
                }
                else {
                    registro.setTipoAccion((short) 0);
                }

                registro.setFechaFinActividades((Date) datos.get(EtiquetaArchivoAEnum.A127.getNombreCampo()));

                if (datos.get(EtiquetaArchivoAEnum.A128.getNombreCampo()) != null) {
                    registro.setCodOperador(((Integer) datos.get(EtiquetaArchivoAEnum.A128.getNombreCampo())).shortValue());
                }
                else {
                    registro.setCodOperador((short) 0);
                }

                registro.setPeriodoAporte((String) datos.get(EtiquetaArchivoAEnum.A129.getNombreCampo()));
                registro.setTipoAportante(((Integer) datos.get(EtiquetaArchivoAEnum.A130.getNombreCampo())).shortValue());
                registro.setFechaMatricula((Date) datos.get(EtiquetaArchivoAEnum.A131.getNombreCampo()));
                registro.setCodDepartamentoBene((String) datos.get(EtiquetaArchivoAEnum.A132.getNombreCampo()));
                registro.setAportanteExonerado((String) datos.get(EtiquetaArchivoAEnum.A133.getNombreCampo()));
                registro.setAcogeBeneficio((String) datos.get(EtiquetaArchivoAEnum.A134.getNombreCampo()));

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
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_A_REGISTRO_1).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_A_REGISTRO_1).setParameter("listaId", idRegistro).executeUpdate();
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
