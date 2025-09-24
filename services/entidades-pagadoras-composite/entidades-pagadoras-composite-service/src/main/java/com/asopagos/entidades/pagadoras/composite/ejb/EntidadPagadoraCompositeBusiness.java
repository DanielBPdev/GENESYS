package com.asopagos.entidades.pagadoras.composite.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.empleadores.clients.ConsultarEstadoCajaEmpleador;
import com.asopagos.empresas.clients.ActualizarUbicacionesEmpresa;
import com.asopagos.empresas.clients.ConsultarEmpresa;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.empresas.clients.CrearSucursalEmpresa;
import com.asopagos.empresas.clients.ObtenerCodigoDisponibleSucursal;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.pagadoras.clients.BuscarEntidadPagadora;
import com.asopagos.entidades.pagadoras.clients.ConsultarEntidadPagadora;
import com.asopagos.entidades.pagadoras.clients.CrearEntidadPagadora;
import com.asopagos.entidades.pagadoras.composite.service.EntidadPagadoraCompositeService;
import com.asopagos.entidades.pagadoras.dto.ConsultarEntidadPagadoraOutDTO;
import com.asopagos.entidades.pagadoras.dto.EntidadPagadoraDTO;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.PersonasUtils;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados con las entidades pagadoras
 * <b>Historia de Usuario:</b> 121-109, 121-133
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@Stateless
public class EntidadPagadoraCompositeBusiness implements EntidadPagadoraCompositeService {
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger LOGGER = LogManager.getLogger(EntidadPagadoraCompositeBusiness.class);

    private static final String NOMBRE_SUCURSAL = "Sucursal pagadora";

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.entidades.pagadoras.composite.service.EntidadPagadoraCompositeService#buscarEntidadPagadoraIdentificacion(com.asopagos.
     * enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultarEntidadPagadoraOutDTO> buscarEntidadPagadoraIdentificacion(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String razonSocial) {
        try {
            LOGGER.debug("Inicia servicio buscarEntidadPagadoraIdentificacion(TipoIdentificacionEnum, String, String)");
            // Consulta la entidad pagadora por los datos enviados
            BuscarEntidadPagadora buscarEntidadPagadora = new BuscarEntidadPagadora(numeroIdentificacion, tipoIdentificacion, razonSocial);
            buscarEntidadPagadora.execute();
            LOGGER.debug("Finaliza servicio buscarEntidadPagadoraIdentificacion(TipoIdentificacionEnum, String, String)");
            return buscarEntidadPagadora.getResult();
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio buscarEntidadPagadoraIdentificacion(TipoIdentificacionEnum, String, String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private EstadoDTO consultarEmpleadorEstadoCaja(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        ConsultarEstadoCajaEmpleador consultarEstadoCajaEmpleador = new ConsultarEstadoCajaEmpleador(numeroIdentificacion,
                tipoIdentificacion);
        consultarEstadoCajaEmpleador.execute();
        return consultarEstadoCajaEmpleador.getResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.entidades.pagadoras.composite.service.EntidadPagadoraCompositeService#registrarEntidadPagadora(com.asopagos.entidades.
     * pagadoras.dto.EntidadPagadoraDTO)
     */
    @Override
    public Long registrarEntidadPagadora(EntidadPagadoraDTO entidadPagadoraDTO) {
        try {
            LOGGER.debug("Inicia servicio registrarEntidadPagadora(EntidadPagadoraDTO)");
            
            if(entidadPagadoraDTO.getUbicacion() == null){
            	entidadPagadoraDTO.setUbicacion(new UbicacionDTO());
            }

            Long idEmpresa = null;
            // Se verifica si existe como empresa
            ConsultarEmpresa consultarEmpresa = new ConsultarEmpresa(entidadPagadoraDTO.getNumeroIdentificacion(),
                    entidadPagadoraDTO.getTipoIdentificacion(), null);
            consultarEmpresa.execute();
            List<EmpresaModeloDTO> listResultEmpresa = consultarEmpresa.getResult();
            if (listResultEmpresa!= null && !listResultEmpresa.isEmpty()) {
                EmpresaModeloDTO empresaModeloDTO = listResultEmpresa.iterator().next();
                Empresa empresa = empresaModeloDTO.convertToEmpresaEntity();
                idEmpresa = empresa.getIdEmpresa();
            }
            else {
                // Se registra la nueva empresa con la información suministrada
                CrearEmpresa crearEmpresa = new CrearEmpresa(obtenerDatosEmpresaEntidad(entidadPagadoraDTO));
                crearEmpresa.execute();
                idEmpresa = crearEmpresa.getResult();
            }
            entidadPagadoraDTO.setIdEmpresa(idEmpresa);
            // Se registra la sucursal de la empresa si NO se selecciono una existente
            if (entidadPagadoraDTO.getIdSucursalEmpresa() == null || entidadPagadoraDTO.getIdSucursalEmpresa().equals(0L)) {
                CrearSucursalEmpresa crearSucursalEmpresa = new CrearSucursalEmpresa(idEmpresa,
                        obtenerListadoSucursalEmpresaEntidad(entidadPagadoraDTO));
                crearSucursalEmpresa.execute();
                List<Long> idenSucursal = crearSucursalEmpresa.getResult();
                entidadPagadoraDTO.setIdSucursalEmpresa(idenSucursal.iterator().next());
            }

            // Se actualiza la informacion de la ubicacion
            ActualizarUbicacionesEmpresa actualizarUbicacionesEmpresa = new ActualizarUbicacionesEmpresa(idEmpresa,
                    obtenerListaUbicacionesEmpresa(entidadPagadoraDTO));
            actualizarUbicacionesEmpresa.execute();

            // Se envia la info al registro de la entidad pagadora
            // Se registra los documentos adjuntados
            CrearEntidadPagadora crearEntidadPagadora = new CrearEntidadPagadora(entidadPagadoraDTO);
            crearEntidadPagadora.execute();
            LOGGER.debug("Finaliza servicio registrarEntidadPagadora(EntidadPagadoraDTO)");
            return crearEntidadPagadora.getResult();
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio registrarEntidadPagadora(EntidadPagadoraDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Obtiene la informacion de la empresa a registrar como entidad pagadora
     * @param entidadPagadoraDTO
     *        Info entidad pagadora
     * @return Informacion entidad pagadora formato DTO empresa
     */
    private EmpresaModeloDTO obtenerDatosEmpresaEntidad(EntidadPagadoraDTO entidadPagadoraDTO) {
        EmpresaModeloDTO empresaModeloDTO = new EmpresaModeloDTO();
        // Datos basicos
        empresaModeloDTO.setTipoIdentificacion(entidadPagadoraDTO.getTipoIdentificacion());
        empresaModeloDTO.setNumeroIdentificacion(entidadPagadoraDTO.getNumeroIdentificacion());
        if (entidadPagadoraDTO.getRazonSocial() != null && !entidadPagadoraDTO.getRazonSocial().isEmpty()) {
            empresaModeloDTO.setRazonSocial(entidadPagadoraDTO.getRazonSocial());
        } else {
            empresaModeloDTO.setPrimerNombre(entidadPagadoraDTO.getPrimerNombre());
            empresaModeloDTO.setSegundoNombre(entidadPagadoraDTO.getSegundoNombre());
            empresaModeloDTO.setPrimerApellido(entidadPagadoraDTO.getPrimerApellido());
            empresaModeloDTO.setSegundoApellido(entidadPagadoraDTO.getSegundoApellido());
            empresaModeloDTO.setRazonSocial(PersonasUtils.obtenerNombreORazonSocial(empresaModeloDTO.convertToPersonaEntity()));
        }
        
        empresaModeloDTO.setDigitoVerificacion(entidadPagadoraDTO.getDigitoVerificacion());
        empresaModeloDTO.setPaginaWeb(entidadPagadoraDTO.getPaginaWeb());
        // Datos ubicacion
        UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
        ubicacionModeloDTO.convertToDTO(UbicacionDTO.obtenerUbicacion(entidadPagadoraDTO.getUbicacion()));
        empresaModeloDTO.setUbicacionModeloDTO(ubicacionModeloDTO);
        return empresaModeloDTO;
    }

    /**
     * Obtiene la informacion de la sucursal Pagadora de la empresa a registrar
     * @param entidadPagadoraDTO
     *        Informacion de la entidad pagadora
     * @return
     */
    private List<SucursalEmpresa> obtenerListadoSucursalEmpresaEntidad(EntidadPagadoraDTO entidadPagadoraDTO) {
        List<SucursalEmpresa> listadoSucursales = new ArrayList<>();
        SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
        sucursalEmpresa.setIdEmpresa(entidadPagadoraDTO.getIdEmpresa());
        sucursalEmpresa.setUbicacion(UbicacionDTO.obtenerUbicacion(entidadPagadoraDTO.getUbicacion()));
        sucursalEmpresa.setNombre(NOMBRE_SUCURSAL);
        // Codigo de la sucursal
        ObtenerCodigoDisponibleSucursal codigoDisponibleSucursal = new ObtenerCodigoDisponibleSucursal(entidadPagadoraDTO.getIdEmpresa());
        codigoDisponibleSucursal.execute();
        sucursalEmpresa.setCodigo(codigoDisponibleSucursal.getResult());

        listadoSucursales.add(sucursalEmpresa);
        return listadoSucursales;
    }

    /**
     * Obtiene la lista de ubicaciones a actualizar de la entidad pagadora
     * @param entidadPagadoraDTO
     *        Informacion entidad pagadora
     * @return Lista de ubicacione de empresa entidad pagadora
     */
    private List<UbicacionEmpresa> obtenerListaUbicacionesEmpresa(EntidadPagadoraDTO entidadPagadoraDTO) {
        List<UbicacionEmpresa> listUbicaciones = new ArrayList<>();
        // Ubicacion Principal
        UbicacionEmpresa ubicacionEmpresaPrincipal = new UbicacionEmpresa();
        ubicacionEmpresaPrincipal.setIdEmpresa(entidadPagadoraDTO.getIdEmpresa());
        ubicacionEmpresaPrincipal.setTipoUbicacion(TipoUbicacionEnum.UBICACION_PRINCIPAL);
        ubicacionEmpresaPrincipal.setUbicacion(UbicacionDTO.obtenerUbicacion(entidadPagadoraDTO.getUbicacion()));
        listUbicaciones.add(ubicacionEmpresaPrincipal);

        // Ubicacion Correspondencia
        UbicacionEmpresa ubicacionEmpresaCorrespondencia = new UbicacionEmpresa();
        ubicacionEmpresaCorrespondencia.setIdEmpresa(entidadPagadoraDTO.getIdEmpresa());
        ubicacionEmpresaCorrespondencia.setTipoUbicacion(TipoUbicacionEnum.ENVIO_CORRESPONDENCIA);
        ubicacionEmpresaCorrespondencia.setUbicacion(UbicacionDTO.obtenerUbicacion(entidadPagadoraDTO.getUbicacionCorrespondencia()));
        listUbicaciones.add(ubicacionEmpresaCorrespondencia);

        return listUbicaciones;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.entidades.pagadoras.composite.service.EntidadPagadoraCompositeService#consultarInformacionEntidadPagadora(com.asopagos.
     * enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EntidadPagadoraDTO consultarInformacionEntidadPagadora(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            LOGGER.debug("Inicia servicio consultarInformacionEntidadPagadora(TipoIdentificacionEnum, String)");
            // Se consulta la informacion basica de la entidad pagadora
            ConsultarEntidadPagadora consultarEntidadPagadora = new ConsultarEntidadPagadora(numeroIdentificacion, tipoIdentificacion);
            consultarEntidadPagadora.execute();
            EntidadPagadoraDTO result = consultarEntidadPagadora.getResult();
            EstadoDTO estadoDTO = consultarEmpleadorEstadoCaja(tipoIdentificacion, numeroIdentificacion);
            result.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoDTO.getEstado().name()));
            LOGGER.debug("Finaliza servicio consultarInformacionEntidadPagadora(TipoIdentificacionEnum, String)");
            return result;
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio consultarInformacionEntidadPagadora(TipoIdentificacionEnum, String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
}
