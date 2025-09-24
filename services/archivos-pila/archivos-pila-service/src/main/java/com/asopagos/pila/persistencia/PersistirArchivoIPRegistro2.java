package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro2;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 2 del archivo IP<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class PersistirArchivoIPRegistro2 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoIPRegistro2.class);

    private final int BATCH_SIZE = 250;
    
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
    @SuppressWarnings("unchecked")
    @Override
    public void persistLine(List<LineArgumentDTO> paramList, EntityManager emCore) throws FileProcessingException {
        String firmaMetodo = "PersistirArchivoIPRegistro2.persistLine(List<LineArgumentDTO>, EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se obtiene instancia del EM para modelo de datos PILA
        EntityManager em = FuncionesValidador.obtenerEntityPila();
        GestorStoredProceduresLocal gestorSP = FuncionesValidador.obtenerGestorSP();

        Map<String, Object> contexto = paramList.get(0).getContext();

        // se toma el listado de errores del contexto
        List<ErrorDetalladoValidadorDTO> errores = (List<ErrorDetalladoValidadorDTO>) contexto.get(ConstantesContexto.ERRORES_DETALLADOS);

        // se toma el indice de planilla del contexto

        IndicePlanilla indicePlanilla = (IndicePlanilla) contexto.get(ConstantesComunesProcesamientoPILA.INDICE_PLANILLA);

        if (indicePlanilla == null) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA
                            + MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA
                                    .getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
            throw new FileProcessingException(
                    MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
        }

        List<PilaArchivoIPRegistro2> registros = new ArrayList<>();
        List<Long> ids = gestorSP.obtenerValoresSecuencia(paramList.size(), "Sec_PilaArchivoIPRegistro2");

        for (LineArgumentDTO lineArgumentDTO : paramList) {
            Map<String, Object> datos = lineArgumentDTO.getLineValues();
            
            Iterator it = datos.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }
            PilaArchivoIPRegistro2 registro = new PilaArchivoIPRegistro2();
            registro.setId(ids.get(0));
            ids.remove(0);
            
            registro.setIndicePlanilla(indicePlanilla);
            registro.setSecuencia((Integer) datos.get(EtiquetaArchivoIPEnum.IP22.getNombreCampo()));
            registro.setTipoIdPensionado((String) datos.get(EtiquetaArchivoIPEnum.IP23.getNombreCampo()));
            registro.setIdPensionado((String) datos.get(EtiquetaArchivoIPEnum.IP24.getNombreCampo()));
            registro.setPrimerApellido((String) datos.get(EtiquetaArchivoIPEnum.IP25.getNombreCampo()));
            registro.setSegundoApellido((String) datos.get(EtiquetaArchivoIPEnum.IP26.getNombreCampo()));
            registro.setPrimerNombre((String) datos.get(EtiquetaArchivoIPEnum.IP27.getNombreCampo()));
            registro.setSegundoNombre((String) datos.get(EtiquetaArchivoIPEnum.IP28.getNombreCampo()));
            registro.setCodDepartamento((String) datos.get(EtiquetaArchivoIPEnum.IP29.getNombreCampo()));
            registro.setCodMunicipio((String) datos.get(EtiquetaArchivoIPEnum.IP210.getNombreCampo()));
            registro.setTarifa((BigDecimal) datos.get(EtiquetaArchivoIPEnum.IP211.getNombreCampo()));
            registro.setValorAporte((BigDecimal) datos.get(EtiquetaArchivoIPEnum.IP212.getNombreCampo()));
            registro.setValorMesada((BigDecimal) datos.get(EtiquetaArchivoIPEnum.IP213.getNombreCampo()));
            registro.setDiasCotizados(((Integer) datos.get(EtiquetaArchivoIPEnum.IP214.getNombreCampo())).shortValue());
            registro.setNovING((String) datos.get(EtiquetaArchivoIPEnum.IP215.getNombreCampo()));
            registro.setNovRET((String) datos.get(EtiquetaArchivoIPEnum.IP216.getNombreCampo()));
            registro.setNovVSP((String) datos.get(EtiquetaArchivoIPEnum.IP217.getNombreCampo()));
            registro.setNovSUS((String) datos.get(EtiquetaArchivoIPEnum.IP218.getNombreCampo()));
            registro.setFechaIngreso((Date) datos.get(EtiquetaArchivoIPEnum.IP219.getNombreCampo()));
            registro.setFechaRetiro((Date) datos.get(EtiquetaArchivoIPEnum.IP220.getNombreCampo()));
            registro.setFechaInicioVSP((Date) datos.get(EtiquetaArchivoIPEnum.IP221.getNombreCampo()));
            registro.setFechaInicioSuspension((Date) datos.get(EtiquetaArchivoIPEnum.IP222.getNombreCampo()));
            registro.setFechaFinSuspension((Date) datos.get(EtiquetaArchivoIPEnum.IP223.getNombreCampo()));
            registro.setCorrecion((String) datos.get(EtiquetaArchivoIPEnum.IP224.getNombreCampo()));

            registros.add(registro);
            
            FuncionesValidador.asociarErroresBloque4(registro.getId(), lineArgumentDTO.getLineNumber(), errores);

            idRegistro.add(registro.getId());
        }

        persistirBatch(em, registros);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
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
        
        EntityManager emPila = FuncionesValidador.obtenerEntityPila();

        if (idRegistro != null && !idRegistro.isEmpty()) {
            List<Long> listaIdsLote = new ArrayList<>(); 
            
            while (BATCH_SIZE < idRegistro.size()) {
                listaIdsLote.addAll(idRegistro.subList(0, BATCH_SIZE-1));
                idRegistro.removeAll(listaIdsLote);
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_IP_REGISTRO_2).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_IP_REGISTRO_2).setParameter("listaId", idRegistro).executeUpdate();
            }
        }
    }

    /**
     * Método encargado de hacer la persistencia del batch de registros
     * @param em
     *        EntityManager para la BD de PILA
     * @param registros
     *        Listado de registros a persistir
     * @throws FileProcessingException
     */
    private void persistirBatch(EntityManager em, List<PilaArchivoIPRegistro2> registros) throws FileProcessingException {
        String firmaMetodo = "persistirBatch(EntityManager, List<PilaArchivoIPRegistro2>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String query = FuncionesValidador.prepararQueryInsercion(registros, 2);

        try {
            em.createNativeQuery(query).executeUpdate();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                    .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()), e);
            throw new FileProcessingException(MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                    .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
