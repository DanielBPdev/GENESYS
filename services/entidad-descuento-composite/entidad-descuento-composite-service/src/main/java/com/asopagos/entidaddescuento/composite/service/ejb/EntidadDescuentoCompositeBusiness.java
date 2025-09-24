package com.asopagos.entidaddescuento.composite.service.ejb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import javax.ejb.Stateless;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarProcesoConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueProcesoMasivo;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.*;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empresas.clients.ConsultarUbicacionesEmpresa;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.empresas.clients.CrearUbicacionesEmpresa;
import com.asopagos.entidaddescuento.clients.ConsultarEntidadDescuentoPorNombreCodigo;
import com.asopagos.entidaddescuento.clients.GestionarEntidadDescuento;
import com.asopagos.entidaddescuento.clients.GestionarTrazabilidadArchivoDescuentos;
import com.asopagos.entidaddescuento.clients.ObtenerArchivosDescuento;
import com.asopagos.entidaddescuento.clients.ObtenerInformacionTrazabilidad;
import com.asopagos.entidaddescuento.clients.ValidarEstructuraArchivoDescuentos;
import com.asopagos.entidaddescuento.clients.EjecutarActualizacionArchivosDescuento;
import com.asopagos.entidaddescuento.composite.service.EntidadDescuentoCompositeService;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoDTO;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import com.asopagos.enumeraciones.core.*;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.TipoEntidadDescuentoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoEntidadDescuentoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoCargaArchivoDescuentoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.CausalAnulacionArchivoDescuentoEnum;


/**
 * <b>Descripcion:</b> Clase que implementa los metodos de composicion para el modulo
 * de entidad de descuento <br/>
 * <b>Módulo:</b> Asopagos - HU 440<br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 * @author  <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */

@Stateless
public class EntidadDescuentoCompositeBusiness implements EntidadDescuentoCompositeService {
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(EntidadDescuentoCompositeBusiness.class);


    /** (non-Javadoc)
     * @see EntidadDescuentoCompositeService#gestionarEntidadDescuento(EntidadDescuentoModeloDTO)
     */
    @Override
    public String gestionarEntidadDescuentoComposite(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO) {
        String firmaServicio = "EntidadDescuentoCompositeBusiness.gestionarEntidadDescuentoComposite(EntidadDescuentoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        if (entidadDescuentoModeloDTO == null) {
            logger.debug("Finaliza crearEmpresa(empresa): No parámetros no válidos, la entidad de descuento no puede ser nula");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }

        //si el tipo de entidad de descuento es externa, se crea o busca una empresa para asociarse
        // a la entidad de descuento
        if (entidadDescuentoModeloDTO.getTipoEntidad().equals(TipoEntidadDescuentoEnum.EXTERNA)) {

            if (entidadDescuentoModeloDTO.getTipoIdentificacion() == null || entidadDescuentoModeloDTO.getNumeroIdentificacion() == null
                    || entidadDescuentoModeloDTO.getRazonSocial() == null
                    || entidadDescuentoModeloDTO.getUbicacionModeloDTO().getDireccionFisica() == null) {

                logger.debug("Finaliza crearEmpresa(empresa): No parámetros no válidos");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);

            }

            EmpresaModeloDTO empresaModeloDTO = new EmpresaModeloDTO();

            empresaModeloDTO.setTipoIdentificacion(entidadDescuentoModeloDTO.getTipoIdentificacion());
            empresaModeloDTO.setNumeroIdentificacion(entidadDescuentoModeloDTO.getNumeroIdentificacion());
            empresaModeloDTO.setRazonSocial(entidadDescuentoModeloDTO.getRazonSocial());
            empresaModeloDTO.setDigitoVerificacion(entidadDescuentoModeloDTO.getDigitoVerificacion());
            empresaModeloDTO.setPrimerNombre(entidadDescuentoModeloDTO.getPrimerNombre());
            empresaModeloDTO.setSegundoNombre(entidadDescuentoModeloDTO.getSegundoNombre());
            empresaModeloDTO.setPrimerApellido(entidadDescuentoModeloDTO.getPrimerApellido());
            empresaModeloDTO.setSegundoApellido(entidadDescuentoModeloDTO.getSegundoApellido());

            UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
            ubicacionModeloDTO.setTelefonoCelular(entidadDescuentoModeloDTO.getUbicacionModeloDTO().getTelefonoCelular());
            ubicacionModeloDTO.setTelefonoFijo(entidadDescuentoModeloDTO.getUbicacionModeloDTO().getTelefonoFijo());
            ubicacionModeloDTO.setIndicativoTelFijo(entidadDescuentoModeloDTO.getUbicacionModeloDTO().getIndicativoTelFijo());
            ubicacionModeloDTO.setDireccionFisica(entidadDescuentoModeloDTO.getUbicacionModeloDTO().getDireccionFisica());

            empresaModeloDTO.setUbicacionModeloDTO(ubicacionModeloDTO);

            //se crea o se busca la empresa y se obtiene el id
            Long empresa = crearEmpresa(empresaModeloDTO);
            entidadDescuentoModeloDTO.setIdEmpresa(empresa);

            //Se valida la existencia de la ubicación principal para la empresa
            List<UbicacionEmpresa> ubicacionesEmpresa = consultarUbicacionesEmpresa(empresa);

            if (ubicacionesEmpresa.isEmpty()) {
                //Se procede a crear la ubicación principal para la empresa
                UbicacionEmpresa ubicacionPrincipal = new UbicacionEmpresa();
                ubicacionPrincipal.setIdEmpresa(empresa);
                ubicacionPrincipal.setUbicacion(ubicacionModeloDTO.convertToEntity());
                ubicacionPrincipal.setTipoUbicacion(TipoUbicacionEnum.UBICACION_PRINCIPAL);

                ubicacionesEmpresa = new ArrayList<UbicacionEmpresa>();
                ubicacionesEmpresa.add(ubicacionPrincipal);

                CrearUbicacionesEmpresa crearUbicaciones = new CrearUbicacionesEmpresa(empresa, ubicacionesEmpresa);
                crearUbicaciones.execute();
            }

        }
        else if (entidadDescuentoModeloDTO.getIdEntidadDescuento() != null) {

            if (entidadDescuentoModeloDTO.getNombreEntidad() == null) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        String cadena = "\"";
        //se llama el micro servicio para crear o actualizar la entidad de descuento.
        // y se retorna el id de la entidad de descuento
        return cadena + gestionarEntidadDescuento(entidadDescuentoModeloDTO) + cadena;
    }

    /**
     * Método encargado de obtener las ubicaciones de una empresa a partir de su identificador
     * @param idEmpresa
     *        valor del identificador de la empresa
     * @return lista de ubicaciones
     */
    private List<UbicacionEmpresa> consultarUbicacionesEmpresa(Long idEmpresa) {
        ConsultarUbicacionesEmpresa consultarUbicaciones = new ConsultarUbicacionesEmpresa(idEmpresa);
        consultarUbicaciones.execute();
        return consultarUbicaciones.getResult();
    }

    /**
     * Metodo encargado de llamar el servicio de creación de una empresa.
     *
     * @param empresaModeloDTO
     *        variable que contiene los valores necesarios para crear una empresa.
     * @return el id de la empresa.
     */
    private Long crearEmpresa(EmpresaModeloDTO empresaModeloDTO){

        CrearEmpresa crearEmpresa = new CrearEmpresa(empresaModeloDTO);
        crearEmpresa.execute();

        return crearEmpresa.getResult();

    }

    /**
     * Metodo encargado de llamar el servicio de gestionar entidad de descuento.
     *
     * @param entidadDescuentoModeloDTO
     *        variable que contiene los valores necesarios para crear una entidad de descuento.
     * @return el id de la entidad de descuento.
     */
    private String gestionarEntidadDescuento(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO) {

        GestionarEntidadDescuento gestionarEntidadDescuento = new GestionarEntidadDescuento(entidadDescuentoModeloDTO);
        gestionarEntidadDescuento.execute();

        return gestionarEntidadDescuento.getResult();
    }

    /**
     * (non-Javadoc)
     * @see EntidadDescuentoCompositeService#cargarAutomaticamenteArchivosEntidadDescuentoComposite()
     */
    @Override
    public void cargarAutomaticamenteArchivosEntidadDescuentoComposite() {
        String firmaServicio = "EntidadDescuentoCompositeBusiness.cargarAutomaticamenteArchivosEntidadDescuentoComposite(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ArchivoEntidadDescuentoDTO> archivosDescuento = obtenerArchivosDescuento();
        if (archivosDescuento != null) {
            

            List<EntidadDescuentoModeloDTO> entidadesDescuento = obtenerEntidadesDescuento();
            logger.info("*__* entidadesDescuento" + entidadesDescuento.toString());

            List<String> nombresArchivos = obtenerNombresArchivos(archivosDescuento);

            List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> informacionTrazabilidadArchivos = obtenerInformacionTrazabilidadArchivos(
                    nombresArchivos);
            logger.info("*__* informacionTrazabilidadArchivos" + informacionTrazabilidadArchivos.toString());
            logger.info("*__* archivosDescuento" + archivosDescuento.toString());
            validarYRegistrarArchivos(archivosDescuento, entidadesDescuento, informacionTrazabilidadArchivos);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Método que se encarga de obtener los archivos de descuento
     * @return DTO´s con la información de los archivos de descuento
     */
    private List<ArchivoEntidadDescuentoDTO> obtenerArchivosDescuento(){
        ObtenerArchivosDescuento archivosDescuento = new ObtenerArchivosDescuento();
        archivosDescuento.execute();
        return archivosDescuento.getResult();
    }

    /**
     * Método que se encarga de obtener los nombres de los archivos de descuento almacenados en el ECM
     * @param archivosDTO DTO´s con la información de los archivos
     * @return lista de nombres de los archivos de descuento
     */
    private List<String> obtenerNombresArchivos(List<ArchivoEntidadDescuentoDTO> archivosDTO){
        List<String> nombres = new ArrayList<>();
        for (ArchivoEntidadDescuentoDTO archivoEntidadDescuentoDTO : archivosDTO) {
            if(archivoEntidadDescuentoDTO.getFileName().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_DESCUENTOS)){
                try {
                    int anho = Integer.parseInt(archivoEntidadDescuentoDTO.getFileName().substring(8, 12));
                    int mes = Integer.parseInt(archivoEntidadDescuentoDTO.getFileName().substring(13, 15));
                    int dia = Integer.parseInt(archivoEntidadDescuentoDTO.getFileName().substring(16, 18));
                    LocalDate.of(anho, mes, dia);
                    nombres.add(archivoEntidadDescuentoDTO.getFileName());
                } catch (Exception e) {
                    logger.debug(ConstantesComunes.FIN_LOGGER
                            + " Se intentó subir un archivo con fecha no calida en el nombre "
                            + archivoEntidadDescuentoDTO.getFileName());
                }


            }
        }
        return nombres;
    }

    /**
     * Método que se encarga de obtener la lista de entidades de descuento registradas
     * @return DTO´s con la información de la entidades de descuento
     */
    private List<EntidadDescuentoModeloDTO> obtenerEntidadesDescuento(){
        ConsultarEntidadDescuentoPorNombreCodigo consulta = new ConsultarEntidadDescuentoPorNombreCodigo(null, null);
        consulta.execute();
        return consulta.getResult();
    }

    /**
     * Método que se encarga de obtener la lista de registros de la información de trazabilidad de los archivos con estado CARGADO
     * @return DTO´s con la información de trazabilidad
     */
    private List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> obtenerInformacionTrazabilidadArchivos(List<String> nombresArchivos){
        ObtenerInformacionTrazabilidad obtenerInformacion = new ObtenerInformacionTrazabilidad(nombresArchivos);
        obtenerInformacion.execute();
        return obtenerInformacion.getResult();
    }

    /**
     * Método que permite validar y registrar la información de los archivos en caso de que sean validos
     * @param archivosDescuento
     *        lista de DTO´s con la información de los archivos cargados
     */
    public void validarYRegistrarArchivos(List<ArchivoEntidadDescuentoDTO> archivosDescuento,
                                          List<EntidadDescuentoModeloDTO> entidadesDescuento,
                                          List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> informacionTrazabilidadArchivos) {

        String firmaServicio = "EntidadDescuentoCompositeBusiness.validarYRegistrarArchivos(List<ArchivoEntidadDescuentoDTO>,List<EntidadDescuentoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<String> codigosArchivosRegistrados = new ArrayList<>();

        synchronized (this.getClass()) {
            for (ArchivoEntidadDescuentoDTO archivoEntidadDescuentoDTO : archivosDescuento) {
                if (archivoEntidadDescuentoDTO.getFileName().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_DESCUENTOS)) {
                    //Se verifica la existencia de la entidad de descuento dado el codigo del nombre del archivo de descuento
                    String codigoEntidad = archivoEntidadDescuentoDTO.getFileName().substring(3, 7);
                    EntidadDescuentoModeloDTO entidadDescuentoDTO = buscarEntidadDescuentoPorCodigo(entidadesDescuento, codigoEntidad);
                    logger.info("*__* entidadDescuentoDTO " + entidadDescuentoDTO.toString());
                    //Se valida si existe algun archivo cargado para la entidad de descuento con un "convenio" activo
                    if (entidadDescuentoDTO != null && entidadDescuentoDTO.getEstadoEntidad().equals(EstadoEntidadDescuentoEnum.ACTIVA)) {

                        //Se obtiene el archivo más reciente por entidad de descuento
                        //ArchivoEntidadDescuentoDTO archivoMasReciente = obtenerArchivoMasReciente(codigoEntidad, archivosDescuento);
                        if (!codigosArchivosRegistrados.contains(archivoEntidadDescuentoDTO.getFileName().substring(3, 7))) {
                            ConsolaEstadoProcesoDTO consolaEstadoProceso = new ConsolaEstadoProcesoDTO();
                            consolaEstadoProceso.setFechaInicio(Calendar.getInstance().getTimeInMillis());
                            consolaEstadoProceso.setProceso(TipoProcesosMasivosEnum.CARGUE_ARCHIVOS_DESCUENTO);
                            consolaEstadoProceso.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
                            consolaEstadoProceso.setGradoAvance(BigDecimal.valueOf(0));
                            Long idConsolaProceso = registrarConsolaEstadoProcesosMasivos(consolaEstadoProceso);
                            consolaEstadoProceso.setIdConsolaEstadoProcesoMasivo(idConsolaProceso);

                            logger.info("*__* archivoEntidadDescuentoDTO " + archivoEntidadDescuentoDTO.toString() + archivoEntidadDescuentoDTO.getFileName());

                            //Se valida la existencia de un archivo con estado CARGADO y se cambia a ANULADO, si esta PROCESADO se omite
                            ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO archivoRegistradoEstadoCargadoProcesado = obtenerArchivoEstadoCargadoProcesado(
                                    entidadDescuentoDTO, informacionTrazabilidadArchivos, archivoEntidadDescuentoDTO.getFileName());

                            //logger.info ("*__* archivoRegistradoEstadoCargadoProcesado " + archivoRegistradoEstadoCargadoProcesado.toString());
                            consolaEstadoProceso.setGradoAvance(BigDecimal.valueOf(10));
                            actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);

                            if (archivoRegistradoEstadoCargadoProcesado != null) {
                                if (archivoRegistradoEstadoCargadoProcesado.getEstadoCarga().equals(EstadoCargaArchivoDescuentoEnum.CARGADO) ||
                                        archivoRegistradoEstadoCargadoProcesado.getEstadoCarga().equals(EstadoCargaArchivoDescuentoEnum.PROCESADO)) {
                                    logger.warn("continue; if 1");
                                    consolaEstadoProceso.setFechaFin(Calendar.getInstance().getTimeInMillis());
                                    consolaEstadoProceso.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                                    actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);
                                    continue;
                                }
                            }

                            //Se almacena el archivo en el ECM y se registra la trazabilidad del archivo
                            InformacionArchivoDTO informacionArchivo = new InformacionArchivoDTO();
                            consolaEstadoProceso.setGradoAvance(BigDecimal.valueOf(15));
                            actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(),consolaEstadoProceso);
                            try {
                                informacionArchivo = almacenarArchivo(archivoEntidadDescuentoDTO);
                                informacionArchivo = obtenerArchivo(informacionArchivo.getIdentificadorDocumento());
                                informacionArchivo.setFileName(archivoEntidadDescuentoDTO.getFileName());
                                logger.info("try informacionArchivo-->" + informacionArchivo);
                            } catch (Exception e) {
                                consolaEstadoProceso.setFechaFin(Calendar.getInstance().getTimeInMillis());
                                consolaEstadoProceso.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                                actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);
                                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                                logger.warn("continue; try 1");
                                continue;
                                //throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
                            }

                            ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO infoTrazabilidadNuevo = new ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO();
                            Long idTrazabilidad = null;
                            consolaEstadoProceso.setGradoAvance(BigDecimal.valueOf(18));
                            actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);
                            try {
                                infoTrazabilidadNuevo.setCodigoIdentificacionECM(informacionArchivo.getIdentificadorDocumento());
                                infoTrazabilidadNuevo.setFechaRecepcion(new Date(archivoEntidadDescuentoDTO.getFechaModificacion()));
                                infoTrazabilidadNuevo.setEstadoCarga(EstadoCargaArchivoDescuentoEnum.RECIBIDO);
                                infoTrazabilidadNuevo.setFechaCargue(new Date());
                                infoTrazabilidadNuevo.setIdEntidadDescuento(entidadDescuentoDTO.getIdEntidadDescuento());
                                infoTrazabilidadNuevo.setNombre(informacionArchivo.getFileName());
                                Map<String, Object> map = gestionarTrazabilidadArchivoDescuentos(infoTrazabilidadNuevo);
                                idTrazabilidad = Long.parseLong(map.get("idTrazabilidad").toString());
                                infoTrazabilidadNuevo.setIdArchivoEntidadDescuentoSubsidioMonetario(idTrazabilidad);
                                boolean duplicado = Boolean.parseBoolean(map.get("duplicado").toString());
                                if (duplicado) throw new Exception("duplicado");
                            } catch (Exception e) {
                                infoTrazabilidadNuevo.setIdArchivoEntidadDescuentoSubsidioMonetario(idTrazabilidad);
                                infoTrazabilidadNuevo.setEstadoCarga(EstadoCargaArchivoDescuentoEnum.ANULADO);
                                gestionarTrazabilidadArchivoDescuentos(infoTrazabilidadNuevo);

                                consolaEstadoProceso.setFechaFin(Calendar.getInstance().getTimeInMillis());
                                consolaEstadoProceso.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                                actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);
                                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                                continue;
                                //                            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
                            }

                            consolaEstadoProceso.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
                            consolaEstadoProceso.setGradoAvance(BigDecimal.valueOf(20));
                            actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);

                            //valida con Lion y registra lineas
                            ResultadoValidacionArchivoDTO resultadoValidacionDTO = null;
                            String nombreArchivo = informacionArchivo.getFileName();
                            try {
                                String nombreValidator = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('_')) + nombreArchivo.substring(nombreArchivo.lastIndexOf('.'));

                                informacionArchivo.setFileName(nombreValidator);
                                logger.warn("try2 primero " + idTrazabilidad + "-" + informacionArchivo);
                                resultadoValidacionDTO = validarArchivoDescuentos(idTrazabilidad, informacionArchivo);
                                if (resultadoValidacionDTO.getEstadoCargueArchivoDescuento()
                                        .equals(EstadoCargaArchivoDescuentoEnum.CARGADO)) {
                                    infoTrazabilidadNuevo.setEstadoCarga(EstadoCargaArchivoDescuentoEnum.CARGADO);
                                } else {
                                    //Se actualiza trazabilidad por anulación con causa Estructura de Contenido incorrecta
                                    infoTrazabilidadNuevo.setEstadoCarga(EstadoCargaArchivoDescuentoEnum.ANULADO);
                                    infoTrazabilidadNuevo.setCausalAnulacion(CausalAnulacionArchivoDescuentoEnum.CONTENIDO_INCORRECTO);
                                }
                                infoTrazabilidadNuevo.setIdArchivoEntidadDescuentoSubsidioMonetario(idTrazabilidad);
                                gestionarTrazabilidadArchivoDescuentos(infoTrazabilidadNuevo);
                                logger.info("try2 gestionarTrazabilidadArchivoDescuentos--> ");
                            } catch (Exception e) {
                                consolaEstadoProceso.setFechaFin(Calendar.getInstance().getTimeInMillis());
                                consolaEstadoProceso.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                                actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);
                                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                                logger.warn("continue; try 3");
                                continue;
                                //                            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
                            }

                            ConsolaEstadoCargueProcesoDTO consolaCarga = new ConsolaEstadoCargueProcesoDTO();
                            String codigoCaja;
                            try {
                                codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
                            } catch (Exception e) {
                                codigoCaja = null;
                            }

                            Long lineasConError = 0L;
                            Long lineaError = -1L;
                            for (ResultadoHallazgosValidacionArchivoDTO hall : resultadoValidacionDTO.getResultadoHallazgosValidacionArchivoDTO()) {
                                if (lineaError != hall.getNumeroLinea()) {
                                    lineasConError++;
                                }
                                lineaError = hall.getNumeroLinea();
                            }

                            consolaCarga.setCcf(codigoCaja);
                            consolaCarga.setCargue_id(idTrazabilidad);
                            consolaCarga.setEstado(lineasConError > 0 ? EstadoCargueMasivoEnum.FIN_ERROR : EstadoCargueMasivoEnum.FINALIZADO);
                            consolaCarga.setFileLoaded_id(resultadoValidacionDTO.getFileDefinitionId());
                            consolaCarga.setGradoAvance(new BigDecimal(100));
                            consolaCarga.setIdentificacionECM(informacionArchivo.getIdentificadorDocumento());
                            consolaCarga.setNumRegistroConErrores(lineasConError);
                            consolaCarga.setLstErroresArhivo(resultadoValidacionDTO.getResultadoHallazgosValidacionArchivoDTO());
                            consolaCarga.setNumRegistroObjetivo(resultadoValidacionDTO.getTotalRegistro());
                            consolaCarga.setNumRegistroProcesado(resultadoValidacionDTO.getTotalRegistro());
                            consolaCarga.setNumRegistroValidados(resultadoValidacionDTO.getRegistrosValidos());
                            consolaCarga.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_ARCHIVO_DESCUENTOS);
                            consolaCarga.setUsuario(null);
                            consolaCarga.setNombreArchivo(nombreArchivo);
                            registrarConsolaEstado(consolaCarga);

                            consolaEstadoProceso.setFechaFin(Calendar.getInstance().getTimeInMillis());
                            consolaEstadoProceso.setGradoAvance(BigDecimal.valueOf(100));
                            consolaEstadoProceso.setEstado(lineasConError > 0 ? EstadoCargueMasivoEnum.FIN_ERROR : EstadoCargueMasivoEnum.FINALIZADO);
                            consolaEstadoProceso.setError(lineasConError > 0 ? ErroresConsolaEnum.ERROR_TECNICO : null);
                            actualizarProcesoConsolaEstado(consolaEstadoProceso.getIdConsolaEstadoProcesoMasivo(), consolaEstadoProceso);

                            //Se obtiene los archivos cargados y validados
                            logger.info("*__* infoTrazabilidadNuevo " + infoTrazabilidadNuevo.toString());

                            Long resultActualizacion = ejecutarActualizacionArchivosDescuento(infoTrazabilidadNuevo.getIdArchivoEntidadDescuentoSubsidioMonetario(), infoTrazabilidadNuevo.getIdEntidadDescuento());

                            logger.info("*__* resultActualizacion " + resultActualizacion);
                        }

                    } else {
                        //Registrar trazabilidad convenio no activo, estado anulado, causal convenio no activo
                        if (entidadDescuentoDTO != null) {
                            ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO infoTrazabilidadNuevo = new ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO();
                            infoTrazabilidadNuevo.setFechaRecepcion(new Date(archivoEntidadDescuentoDTO.getFechaModificacion()));
                            infoTrazabilidadNuevo.setEstadoCarga(EstadoCargaArchivoDescuentoEnum.ANULADO);
                            infoTrazabilidadNuevo.setFechaCargue(new Date());
                            infoTrazabilidadNuevo.setCausalAnulacion(CausalAnulacionArchivoDescuentoEnum.CONVENIO_INACTIVO);
                            infoTrazabilidadNuevo.setIdEntidadDescuento(entidadDescuentoDTO.getIdEntidadDescuento());
                            infoTrazabilidadNuevo.setNombre(archivoEntidadDescuentoDTO.getFileName());
                            gestionarTrazabilidadArchivoDescuentos(infoTrazabilidadNuevo);
                        }
                        //codigosArchivosRegistrados.add(codigoEntidad);
                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                    }
                }
            }
        }
    }

    /**
     * Método que se encarga de buscar una entidad de descuento en la lista de entidades de descuento con base en su código
     * @param entidadesDescuento
     *        DTO´s con la información de las entidades de descuento
     * @param codigoEntidad
     *        valor del código que contiene el nombre del archivo
     * @return DTO con la información de la entidad de descuento que corresponde con el código del archivo
     */
    private EntidadDescuentoModeloDTO buscarEntidadDescuentoPorCodigo(List<EntidadDescuentoModeloDTO> entidadesDescuento,
                                                                      String codigoEntidad) {

        String codigo;

        for (EntidadDescuentoModeloDTO entidadDescuentoModeloDTO : entidadesDescuento) {
            codigo = String.format("%04d", Long.parseLong(entidadDescuentoModeloDTO.getCodigo()));
            if (codigoEntidad.equals(codigo)) {
                return entidadDescuentoModeloDTO;
            }
        }
        return null;
    }

    /**
     * Método que permite obtener el archivo de descuento más reciente por entidad de descuento
     * @param codigoEntidad
     *        código de la entidad de descuento
     * @param archivosDescuento
     *        DTO´s con la información de los archivos de descuento
     * @return DTO con la información del archivo de descuento
     */
//    private ArchivoEntidadDescuentoDTO obtenerArchivoMasReciente(String codigoEntidad, List<ArchivoEntidadDescuentoDTO> archivosDescuento) {
//        ArchivoEntidadDescuentoDTO archivoMasReciente = null;
//        for (ArchivoEntidadDescuentoDTO archivoEntidadDescuentoDTO : archivosDescuento) {
//            if (archivoEntidadDescuentoDTO.getFileName().substring(3, 7).equals(codigoEntidad)) {
//                if (archivoMasReciente == null) {
//                    archivoMasReciente = archivoEntidadDescuentoDTO;
//                }
//                else if (archivoMasReciente.getFechaModificacion() < archivoEntidadDescuentoDTO.getFechaModificacion()) {
//                    archivoMasReciente = archivoEntidadDescuentoDTO;
//                }
//            }
//        }
//        return archivoMasReciente;
//    }

    /**
     * Método que se encarga de obtener la información de trazabilidad de un archivo registrado
     * @param entidadDescuentoDTO
     * @param informacionTrazabilidadArchivos
     * @return DTO con la información del archivo correspondiente
     */
    private ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO obtenerArchivoEstadoCargadoProcesado(
            EntidadDescuentoModeloDTO entidadDescuentoDTO,
            List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> informacionTrazabilidadArchivos, String fileName) {

        ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO archivoRegistradoProcesado = null;
        for (ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO archivoDescuentoModeloDTO : informacionTrazabilidadArchivos) {

            if ((archivoDescuentoModeloDTO.getEstadoCarga().equals(EstadoCargaArchivoDescuentoEnum.CARGADO)
                    || archivoDescuentoModeloDTO.getEstadoCarga().equals(EstadoCargaArchivoDescuentoEnum.PROCESADO))
                    && archivoDescuentoModeloDTO.getNombre().equals(fileName)) {
                archivoRegistradoProcesado = archivoDescuentoModeloDTO;
                break;
            }
        }
        return archivoRegistradoProcesado;
    }

    /**
     * Método que se encarga de gestionar la información de trazabilidad para un archivo de descuento
     * @param informacionTrazabilidad DTO con la información de la trazabilidad
     */
    private Map<String, Object> gestionarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informacionTrazabilidad){
        GestionarTrazabilidadArchivoDescuentos gestionTrazabilidad = new GestionarTrazabilidadArchivoDescuentos(informacionTrazabilidad);
        gestionTrazabilidad.execute();
        return gestionTrazabilidad.getResult();
    }

    /**
     * Método que se encarga de almacenar el archivo en el Enterprise Content Management
     * @param archivoEntidadDescuentoDTO DTO con la información del archivo
     * @return DTO con la información del archivo almacenado
     */
    private InformacionArchivoDTO almacenarArchivo(ArchivoEntidadDescuentoDTO archivoEntidadDescuentoDTO){
        InformacionArchivoDTO informacionArchivo = archivoEntidadDescuentoDTO.convertToParent();
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(informacionArchivo);
        almacenarArchivo.execute();
        return almacenarArchivo.getResult();

    }

    /**
     * Método que se encarga de consultar un archivo a partir de su indentificador en el Enterprise Content Management
     * @param identificadorECM valor del identificador del archivo
     * @return DTO con la información del archivo
     */
    private InformacionArchivoDTO obtenerArchivo(String identificadorECM){
        ObtenerArchivo obtenerArchivo = new ObtenerArchivo(identificadorECM);
        obtenerArchivo.execute();
        return obtenerArchivo.getResult();
    }

    /**
     * Método que se encarga de consultar el microservicio para la validación del archivo
     * @param informacionDTO DTO con la información del archivo
     * @return DTO con la información de resultado
     */
    private ResultadoValidacionArchivoDTO validarArchivoDescuentos(Long idTrazabilidad, InformacionArchivoDTO informacionDTO){
        ValidarEstructuraArchivoDescuentos validacion = new ValidarEstructuraArchivoDescuentos(idTrazabilidad, informacionDTO);
        validacion.execute();
        return validacion.getResult();
    }

    /**
     * Método encargado de llamar el cliente que se encarga de registrar en
     * consola de estado de cargue múltiple
     *
     * @param consolaEstadoCargueProcesoDTO
     */
    private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaEstadoCargueProcesoDTO);
        registroConsola.execute();
    }

    private Long ejecutarActualizacionArchivosDescuento(Long idArchivo, Long codigoEntidad){
        EjecutarActualizacionArchivosDescuento EjecutarActualizacionArchivosDescuento = new EjecutarActualizacionArchivosDescuento(idArchivo, codigoEntidad);
        EjecutarActualizacionArchivosDescuento.execute();
        return EjecutarActualizacionArchivosDescuento.getResult();
    }

    private Long registrarConsolaEstadoProcesosMasivos(ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        RegistrarCargueProcesoMasivo registroConsolaProceso = new RegistrarCargueProcesoMasivo(consolaEstadoProcesoDTO);
        registroConsolaProceso.execute();
        return registroConsolaProceso.getResult();
    }

    private void actualizarProcesoConsolaEstado(Long idConsolaEstadoProcesoMasivo, ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        ActualizarProcesoConsolaEstado actualizacionProceso = new ActualizarProcesoConsolaEstado(idConsolaEstadoProcesoMasivo,
                consolaEstadoProcesoDTO);
        actualizacionProceso.execute();
    }
}