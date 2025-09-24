package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoFEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
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
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 6 del archivo F<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 407 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class PersistirArchivoFRegistro6 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoFRegistro6.class);

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
        String firmaMetodo = "persistLine(List<LineArgumentDTO>, EntityManager)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se obtiene instancia del EM para modelo de datos PILA
        EntityManager em = FuncionesValidador.obtenerEntityPila();
        GestorStoredProceduresLocal gestorSP = FuncionesValidador.obtenerGestorSP();

        // se obtiene el contexto
        Map<String, Object> contexto = paramList.get(0).getContext();

        // se toma el indice de planilla del contexto

        IndicePlanillaOF indicePlanilla = (IndicePlanillaOF) paramList.get(0).getContext()
                .get(ConstantesComunesProcesamientoPILA.INDICE_PLANILLA);
        if (indicePlanilla == null) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA
                            + MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA
                                    .getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
            throw new FileProcessingException(
                    MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
        }

        List<PilaArchivoFRegistro6> registros = new ArrayList<>();
        List<Long> ids = gestorSP.obtenerValoresSecuencia(paramList.size(), "Sec_PilaArchivoFRegistro6");
            System.out.print("Sec_PilaArchivoFRegistro6 - >");
        for (LineArgumentDTO lineArgumentDTO : paramList) {
            Map<String, Object> datos = lineArgumentDTO.getLineValues();

            PilaArchivoFRegistro6 registro = new PilaArchivoFRegistro6();
            registro.setId(ids.get(0));
            ids.remove(0);

            registro.setIndicePlanilla(indicePlanilla);

            registro.setIdAportante((String) datos.get(EtiquetaArchivoFEnum.F62.getNombreCampo()));
            registro.setNombreAportante((String) datos.get(EtiquetaArchivoFEnum.F63.getNombreCampo()));
            registro.setCodBanco((String) datos.get(EtiquetaArchivoFEnum.F64.getNombreCampo()));
            registro.setNumeroPlanilla((String) datos.get(EtiquetaArchivoFEnum.F65.getNombreCampo()));
            registro.setPeriodoPago((String) datos.get(EtiquetaArchivoFEnum.F66.getNombreCampo()));
            registro.setCanalPago((String) datos.get(EtiquetaArchivoFEnum.F67.getNombreCampo()));
            registro.setCantidadRegistros((Integer) datos.get(EtiquetaArchivoFEnum.F68.getNombreCampo()));
            registro.setCodOperadorInformacion((String) datos.get(EtiquetaArchivoFEnum.F69.getNombreCampo()));

            // el valor de este campo incluye 2 posiciones decimales sin punto, se divide entre 100
            registro.setValorPlanilla(((BigDecimal) datos.get(EtiquetaArchivoFEnum.F610.getNombreCampo())).divide(BigDecimal.valueOf(100)));
            registro.setHoraMinuto((String) datos.get(EtiquetaArchivoFEnum.F611.getNombreCampo()));
            registro.setSecuencia((Integer) datos.get(EtiquetaArchivoFEnum.F612.getNombreCampo()));

            // se obtiene el ID del registro tipo 5 relacionado mediante el uso del índice de planilla y el número de lote
            Integer numeroLote = null;
            Map<Integer, ControlLoteOFDTO> mapaControlSumatorias = (Map<Integer, ControlLoteOFDTO>) contexto
                    .get(ConstantesContexto.MAPA_CONTROL_SUMATORIAS_LOTES_OF);
            if (mapaControlSumatorias != null) {
                numeroLote = FuncionesValidador.buscarLoteLinea(lineArgumentDTO.getLineNumber(), mapaControlSumatorias);
            }
            Long idRegistro5 = obtenerIdRegistro5(em, indicePlanilla, numeroLote != null ? numeroLote.shortValue() : null);

            registro.setIdPilaArchivoFRegistro5(idRegistro5);

            registros.add(registro);

            idRegistro.add(registro.getId());
        }

        persistirBarchXRegistros(em, registros);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
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
        EntityManager emPila = FuncionesValidador.obtenerEntityPila();

        if (idRegistro != null && !idRegistro.isEmpty()) {
            List<Long> listaIdsLote = new ArrayList<>(); 
            
            while (BATCH_SIZE < idRegistro.size()) {
                listaIdsLote.addAll(idRegistro.subList(0, BATCH_SIZE-1));
                idRegistro.removeAll(listaIdsLote);
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_F_REGISTRO_6).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_F_REGISTRO_6).setParameter("listaId", idRegistro).executeUpdate();
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
    private void persistirBatch(EntityManager em, List<PilaArchivoFRegistro6> registros) throws FileProcessingException {
        String firmaMetodo = "persistirBatch(EntityManager, List<PilaArchivoFRegistro6>) - ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String query = FuncionesValidador.prepararQueryInsercion(registros, 3);

        try {
            em.createNativeQuery(query).executeUpdate();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                    .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()), e);
            throw new FileProcessingException(MensajesValidacionEnum.ERROR_PERSISTENCIA_REGISTRO
                    .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString(), e.getMessage()));
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    private void persistirBarchXRegistros(EntityManager em, List<PilaArchivoFRegistro6> registros) throws FileProcessingException {
        int registrosXBatch = 900;
        for (int i = 0; i < registros.size(); i += registrosXBatch) {
            int fin = i + registrosXBatch;
            if (fin > registros.size()) {
                fin = registros.size();
            }

            List<PilaArchivoFRegistro6> subList = registros.subList(i, fin);
            persistirBatch(em, subList);
        }
    }

}
