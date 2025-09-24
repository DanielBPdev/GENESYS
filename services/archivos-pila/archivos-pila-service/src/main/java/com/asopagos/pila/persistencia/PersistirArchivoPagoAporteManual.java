package com.asopagos.pila.persistencia;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.NamedQueriesConstants;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripción:</b> <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:jusanchez@heinsohn.com.co">Julian Andres Sanchez</a>
 */
public class PersistirArchivoPagoAporteManual implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<Long>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoPagoAporteManual.class);

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
    public void persistLine(List<LineArgumentDTO> paramList, EntityManager em) throws FileProcessingException {
        logger.debug("Inicia persistLine(List<LineArgumentDTO>, EntityManager)");

        // se toma el indice de planilla del contexto
/*
        IndicePlanilla indicePlanilla = (IndicePlanilla) paramList.get(0).getContext().get(ConstantesComunes.INDICE_PLANILLA);
        if (indicePlanilla == null) {
            logger.error("Finaliza persistLine(List<LineArgumentDTO>, EntityManager) - "
                    + MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA
                            .getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
            throw new FileProcessingException(
                    MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
        }

        try {
            for (LineArgumentDTO lineArgumentDTO : paramList) {
                Map<String, Object> datos = lineArgumentDTO.getLineValues();

                PilaArchivoIPRegistro2 registro = new PilaArchivoIPRegistro2();
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
                registro.setValorAporte(((BigDecimal) datos.get(EtiquetaArchivoIPEnum.IP212.getNombreCampo())).intValue());
                registro.setValorMesada(((BigDecimal) datos.get(EtiquetaArchivoIPEnum.IP213.getNombreCampo())).intValue());
                registro.setDiasCotizados(((Integer) datos.get(EtiquetaArchivoIPEnum.IP214.getNombreCampo())).shortValue());
                registro.setNovING((String) datos.get(EtiquetaArchivoIPEnum.IP215.getNombreCampo()));
                registro.setNovRET((String) datos.get(EtiquetaArchivoIPEnum.IP216.getNombreCampo()));
                registro.setNovVSP((String) datos.get(EtiquetaArchivoIPEnum.IP217.getNombreCampo()));
                registro.setNovSUS((String) datos.get(EtiquetaArchivoIPEnum.IP218.getNombreCampo()));
                registro.setFechaIngreso((Date) datos.get(EtiquetaArchivoIPEnum.IP219.getNombreCampo()));
                registro.setFechaRetiro((Date) datos.get(EtiquetaArchivoIPEnum.IP220.getNombreCampo()));
                registro.setFechaInicioVSP((Date) datos.get(EtiquetaArchivoIPEnum.IP221.getNombreCampo()));

                em.persist(registro);
                idRegistro.add(registro.getId());
                em.flush();
            }
        } catch (Exception e) {
            logger.error(
                    "Finaliza persistLine(List<LineArgumentDTO>, EntityManager) - " + MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                            .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()));
            throw new FileProcessingException(MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                    .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()));
        }
        logger.debug("Finaliza persistLine(List<LineArgumentDTO>, EntityManager)");*/
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
        if (idRegistro != null && !idRegistro.isEmpty()) {
            List<Long> listaIdsLote = new ArrayList<>(); 
            
            while (BATCH_SIZE < idRegistro.size()) {
                listaIdsLote.addAll(idRegistro.subList(0, BATCH_SIZE-1));
                idRegistro.removeAll(listaIdsLote);
                em.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_IP_REGISTRO_2).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                em.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_IP_REGISTRO_2).setParameter("listaId", idRegistro).executeUpdate();
            }
        }
        
    }

}
