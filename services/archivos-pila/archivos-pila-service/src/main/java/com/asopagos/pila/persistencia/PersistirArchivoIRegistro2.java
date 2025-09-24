package com.asopagos.pila.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro2;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
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
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 2 del archivo I<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class PersistirArchivoIRegistro2 implements IPersistLine {
    private List<Long> idRegistro = new ArrayList<>();

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(PersistirArchivoIRegistro2.class);

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
        String firmaMetodo = "PersistirArchivoIRegistro2.persistLine(List<LineArgumentDTO>, EntityManager)";
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
                            .getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
            throw new FileProcessingException(
                    MensajesValidacionEnum.ERROR_IDENTIFICADOR_INDICE_PLANILLA.getReadableMessage(ConstantesParametroValidador.TIPO_ERROR));
        }

        List<PilaArchivoIRegistro2> registros = new ArrayList<>();
        List<Long> ids = gestorSP.obtenerValoresSecuencia(paramList.size(), "Sec_PilaArchivoIRegistro2");

        for (LineArgumentDTO lineArgumentDTO : paramList) {
            
            Map<String, Object> datos = lineArgumentDTO.getLineValues();

            PilaArchivoIRegistro2 registro = new PilaArchivoIRegistro2();
            registro.setId(ids.get(0));
            ids.remove(0);

            registro.setIndicePlanilla(indicePlanilla);

            registro.setSecuencia((Integer) datos.get(EtiquetaArchivoIEnum.I21.getNombreCampo()));
            registro.setTipoIdCotizante((String) datos.get(EtiquetaArchivoIEnum.I23.getNombreCampo()));
            registro.setIdCotizante((String) datos.get(EtiquetaArchivoIEnum.I24.getNombreCampo()));
            registro.setTipoCotizante(((Integer) datos.get(EtiquetaArchivoIEnum.I25.getNombreCampo())).shortValue());

            Object valor = datos.get(EtiquetaArchivoIEnum.I26.getNombreCampo());
            registro.setSubTipoCotizante(valor != null ? ((Integer) valor).shortValue() : (short) 0);

            registro.setExtrangeroNoObligado((String) datos.get(EtiquetaArchivoIEnum.I27.getNombreCampo()));
            registro.setColombianoExterior((String) datos.get(EtiquetaArchivoIEnum.I28.getNombreCampo()));
            registro.setCodDepartamento((String) datos.get(EtiquetaArchivoIEnum.I29.getNombreCampo()));
            registro.setCodMunicipio((String) datos.get(EtiquetaArchivoIEnum.I210.getNombreCampo()));
            
            //Se hace revisión de comilla entre nombres y apellidos para correccion mantis 260083
            String comillaSimple = "'"; 
            String primerApellido = (String) datos.get(EtiquetaArchivoIEnum.I211.getNombreCampo());
            if(primerApellido != null && primerApellido.indexOf(comillaSimple) > -1) {
            	primerApellido = primerApellido.replace("'", "''");
            	registro.setPrimerApellido(primerApellido);
            }else {
            	registro.setPrimerApellido((String) datos.get(EtiquetaArchivoIEnum.I211.getNombreCampo()));
            }
            
            String segundoApellido = (String) datos.get(EtiquetaArchivoIEnum.I212.getNombreCampo());
            if(segundoApellido != null && segundoApellido.indexOf(comillaSimple) > -1) {
            	segundoApellido = segundoApellido.replace("'", "''");
            	registro.setSegundoApellido(segundoApellido);
            }else {
            	registro.setSegundoApellido((String) datos.get(EtiquetaArchivoIEnum.I212.getNombreCampo()));
            }
            
            String primerNombre = (String) datos.get(EtiquetaArchivoIEnum.I213.getNombreCampo());
            if(primerNombre != null && primerNombre.indexOf(comillaSimple) > -1) {
            	primerNombre = primerNombre.replace("'", "''");
            	registro.setPrimerNombre(primerNombre);
            }else {
            	registro.setPrimerNombre((String) datos.get(EtiquetaArchivoIEnum.I213.getNombreCampo()));
            }
            
            String segundoNombre = (String) datos.get(EtiquetaArchivoIEnum.I214.getNombreCampo());
            if(segundoNombre != null && segundoNombre.indexOf(comillaSimple) > -1) {
            	segundoNombre = segundoNombre.replace("'", "''");
            	registro.setSegundoNombre(segundoNombre);
            }else {
            	registro.setSegundoNombre((String) datos.get(EtiquetaArchivoIEnum.I214.getNombreCampo()));
            }
            
            registro.setNovIngreso((String) datos.get(EtiquetaArchivoIEnum.I215.getNombreCampo()));
            registro.setNovRetiro((String) datos.get(EtiquetaArchivoIEnum.I216.getNombreCampo()));
            registro.setNovVSP((String) datos.get(EtiquetaArchivoIEnum.I217.getNombreCampo()));
            registro.setNovVST((String) datos.get(EtiquetaArchivoIEnum.I218.getNombreCampo()));
            registro.setNovSLN((String) datos.get(EtiquetaArchivoIEnum.I219.getNombreCampo()));
            registro.setNovIGE((String) datos.get(EtiquetaArchivoIEnum.I220.getNombreCampo()));
            registro.setNovLMA((String) datos.get(EtiquetaArchivoIEnum.I221.getNombreCampo()));
            registro.setNovVACLR((String) datos.get(EtiquetaArchivoIEnum.I222.getNombreCampo()));
            registro.setDiasIRL((String) datos.get(EtiquetaArchivoIEnum.I223.getNombreCampo()));
            registro.setDiasCotizados(((Integer) datos.get(EtiquetaArchivoIEnum.I224.getNombreCampo())).shortValue());
            registro.setSalarioBasico((BigDecimal) datos.get(EtiquetaArchivoIEnum.I225.getNombreCampo()));
            registro.setValorIBC((BigDecimal) datos.get(EtiquetaArchivoIEnum.I226.getNombreCampo()));
            registro.setTarifa((BigDecimal) datos.get(EtiquetaArchivoIEnum.I227.getNombreCampo()));
            registro.setAporteObligatorio((BigDecimal) datos.get(EtiquetaArchivoIEnum.I228.getNombreCampo()));
            registro.setCorrecciones((String) datos.get(EtiquetaArchivoIEnum.I229.getNombreCampo()));
            registro.setSalarioIntegral((String) datos.get(EtiquetaArchivoIEnum.I230.getNombreCampo()));
            registro.setFechaIngreso((Date) datos.get(EtiquetaArchivoIEnum.I231.getNombreCampo()));
            registro.setFechaRetiro((Date) datos.get(EtiquetaArchivoIEnum.I232.getNombreCampo()));
            registro.setFechaInicioVSP((Date) datos.get(EtiquetaArchivoIEnum.I233.getNombreCampo()));
            registro.setFechaInicioSLN((Date) datos.get(EtiquetaArchivoIEnum.I234.getNombreCampo()));
            registro.setFechaFinSLN((Date) datos.get(EtiquetaArchivoIEnum.I235.getNombreCampo()));
            registro.setFechaInicioIGE((Date) datos.get(EtiquetaArchivoIEnum.I236.getNombreCampo()));
            registro.setFechaFinIGE((Date) datos.get(EtiquetaArchivoIEnum.I237.getNombreCampo()));
            registro.setFechaInicioLMA((Date) datos.get(EtiquetaArchivoIEnum.I238.getNombreCampo()));
            registro.setFechaFinLMA((Date) datos.get(EtiquetaArchivoIEnum.I239.getNombreCampo()));
            registro.setFechaInicioVACLR((Date) datos.get(EtiquetaArchivoIEnum.I240.getNombreCampo()));
            registro.setFechaFinVACLR((Date) datos.get(EtiquetaArchivoIEnum.I241.getNombreCampo()));
            registro.setFechaInicioVCT((Date) datos.get(EtiquetaArchivoIEnum.I242.getNombreCampo()));
            registro.setFechaFinVCT((Date) datos.get(EtiquetaArchivoIEnum.I243.getNombreCampo()));
            registro.setFechaInicioIRL((Date) datos.get(EtiquetaArchivoIEnum.I244.getNombreCampo()));
            registro.setFechaFinIRL((Date) datos.get(EtiquetaArchivoIEnum.I245.getNombreCampo()));

            valor = datos.get(EtiquetaArchivoIEnum.I246.getNombreCampo());
            //registro.setHorasLaboradas(valor != null ? ((Integer) valor).shortValue() : (short) 0);
            registro.setHorasLaboradas(valor != null ? Short.parseShort((String) valor) : (short) 0);

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
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_2).setParameter("listaId", listaIdsLote).executeUpdate();
                listaIdsLote.clear();
            }
            
            if(idRegistro.size() > 0) {
                emPila.createNamedQuery(NamedQueriesConstants.ROLLBACK_ARCHIVO_I_REGISTRO_2).setParameter("listaId", idRegistro).executeUpdate();
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
    private void persistirBatch(EntityManager em, List<PilaArchivoIRegistro2> registros) throws FileProcessingException {
        String firmaMetodo = "persistirBatch(EntityManager, List<PilaArchivoIRegistro2>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String query = FuncionesValidador.prepararQueryInsercion(registros, 1);

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
