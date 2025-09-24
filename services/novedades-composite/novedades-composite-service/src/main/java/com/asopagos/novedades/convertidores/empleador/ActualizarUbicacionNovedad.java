/**
 *
 */
package com.asopagos.novedades.convertidores.empleador;

import javax.persistence.EntityManager;

import com.asopagos.dto.UbicacionDTO;
import com.asopagos.empresas.clients.ActualizarUbicacion;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ConsultarUbicacion;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

import java.lang.reflect.Field;

/**
 * Clase que contiene la lógica para actualizar la ubicación por novedad.
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ActualizarUbicacionNovedad implements NovedadCore {

    private final ILogger logger = LogManager.getLogger(ActualizarUbicacionNovedad.class);

    /* (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.debug("Inicio de método ActualizarUbicacionNovedad.transformarServicio");

        /*se transforma a un objeto de datos del empleador*/
        DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        switch (novedad) {
            case ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL:
                ubicacionDTO = modificarUbicacionPrincipal(ubicacionDTO, datosEmpleador);
                break;
            case ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB:
                ubicacionDTO = modificarUbicacionPrincipal(ubicacionDTO, datosEmpleador);
                break;
            case ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL:
                ubicacionDTO = modificarUbicacionCorrespondencia(ubicacionDTO, datosEmpleador);
                break;
            case ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB:
                ubicacionDTO = modificarUbicacionCorrespondencia(ubicacionDTO, datosEmpleador);
                break;
            case ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL:
                ubicacionDTO = modificarUbicacionJudicial(ubicacionDTO, datosEmpleador);
                break;
            case ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB:
                ubicacionDTO = modificarUbicacionJudicial(ubicacionDTO, datosEmpleador);
                break;
            case CAMBIO_DATOS_SUCURSAL_PRESENCIAL:
                ubicacionDTO = modificarUbicacionSucursal(ubicacionDTO, datosEmpleador);
                break;
            case CAMBIO_DATOS_SUCURSAL_WEB:
                ubicacionDTO = modificarUbicacionSucursal(ubicacionDTO, datosEmpleador);
                break;
            default:
                break;
        }
        Ubicacion ubicacion = UbicacionDTO.obtenerUbicacion(ubicacionDTO);
        /*se instancia el servicio de la novedad*/
        ActualizarUbicacion actualizarUbicacionService = new ActualizarUbicacion(ubicacion);


        logger.debug("Fin de método ActualizarUbicacionNovedad.transformarServicio");
        return actualizarUbicacionService;
    }

    /**
     * Método que se encargad de modificar los datos de la ubicación de la oficina principal.
     *
     * @param ubicacion
     * @param datosEmpleador
     */
    private UbicacionDTO modificarUbicacionPrincipal(UbicacionDTO ubicacion, DatosEmpleadorNovedadDTO datosEmpleador) {
        logger.debug("Inicio de método modificarUbicacionPrincipal(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");
        //Se consulta la ubicación
        ConsultarUbicacion consultarUbicacionService = new ConsultarUbicacion(datosEmpleador.getIdUbicacionPrincipal());
        consultarUbicacionService.execute();
        ubicacion = (UbicacionDTO) consultarUbicacionService.getResult();
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionPrincipal => ubicacion beforeMerge -> " + ubicacion.toString());

        UbicacionDTO nuevaUbicacion = new UbicacionDTO();
        nuevaUbicacion.setIdMunicipio(datosEmpleador.getMunicipioOficinaPrincipal() != null && datosEmpleador.getMunicipioOficinaPrincipal().getIdMunicipio() != null ? datosEmpleador.getMunicipioOficinaPrincipal().getIdMunicipio() : null);
        nuevaUbicacion.setDireccion(datosEmpleador.getDireccionFisicaOficinaPrincipal());
        nuevaUbicacion.setDescripcionIndicacion(datosEmpleador.getDescripcionIndicacionOficinaPrincipal());
        nuevaUbicacion.setCodigoPostal(datosEmpleador.getCodigoPostalOficinaPrincipal());
        nuevaUbicacion.setIndicativoTelefonoFijo(datosEmpleador.getIndicativoTelFijoOficinaPrincipal() != null ? datosEmpleador.getIndicativoTelFijoOficinaPrincipal().toString() : null);
        nuevaUbicacion.setTelefonoFijo(datosEmpleador.getTelefonoFijoOficinaPrincipal());
        nuevaUbicacion.setTelefonoCelular(datosEmpleador.getTelefonoCelularOficinaPrincipal());
        nuevaUbicacion.setAutorizacionEnvioEmail(datosEmpleador.getAutorizacionEnvioEmail());
        nuevaUbicacion.setCorreoElectronico(datosEmpleador.getEmail());
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionPrincipal => nuevaUbicacion beforeMerge -> " + nuevaUbicacion.toString());

        mergeObjects(ubicacion, nuevaUbicacion);
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionPrincipal => ubicacion afterMerge -> " + ubicacion.toString());
        logger.debug("Fin de método modificarUbicacionPrincipal(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");
        return ubicacion;
    }


    /**
     * Método que se encargad de modificar los datos de la ubicación de la correspondencia.
     *
     * @param ubicacion
     * @param datosEmpleador
     */
    private UbicacionDTO modificarUbicacionCorrespondencia(UbicacionDTO ubicacion, DatosEmpleadorNovedadDTO datosEmpleador) {
        logger.debug("Inicio de método modificarUbicacionCorrespondencia(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");

        ConsultarUbicacion consultarUbicacionService = new ConsultarUbicacion(datosEmpleador.getIdUbicacion());
        consultarUbicacionService.execute();
        ubicacion = (UbicacionDTO) consultarUbicacionService.getResult();
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionCorrespondencia => ubicacion beforeMerge -> " + ubicacion.toString());

        UbicacionDTO nuevaUbicacion = new UbicacionDTO();
        nuevaUbicacion.setIdMunicipio(datosEmpleador.getMunicipio() != null && datosEmpleador.getMunicipio().getIdMunicipio() != null ? datosEmpleador.getMunicipio().getIdMunicipio() : null);
        nuevaUbicacion.setDireccion(datosEmpleador.getDireccionFisica());
        nuevaUbicacion.setDescripcionIndicacion(datosEmpleador.getDescripcionIndicacion());
        nuevaUbicacion.setCodigoPostal(datosEmpleador.getCodigoPostal());
        nuevaUbicacion.setIndicativoTelefonoFijo(datosEmpleador.getIndicativoTelFijo() != null ? datosEmpleador.getIndicativoTelFijo().toString() : null);
        nuevaUbicacion.setCorreoElectronico(datosEmpleador.getEmail());
        nuevaUbicacion.setTelefonoFijo(datosEmpleador.getTelefonoFijo());
        nuevaUbicacion.setTelefonoCelular(datosEmpleador.getTelefonoCelular());
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionCorrespondencia => nuevaUbicacion beforeMerge -> " + nuevaUbicacion.toString());

        mergeObjects(ubicacion, nuevaUbicacion);
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionCorrespondencia => ubicacion afterMerge -> " + ubicacion.toString());
        logger.debug("Fin de método modificarUbicacionCorrespondencia(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");
        return ubicacion;
    }

    /**
     * Método que se encargad de modificar los datos de la ubicación judicial
     *
     * @param ubicacion
     * @param datosEmpleador
     */
    private UbicacionDTO modificarUbicacionJudicial(UbicacionDTO ubicacion, DatosEmpleadorNovedadDTO datosEmpleador) {
        logger.debug("Inicio de método modificarUbicacionJudicial(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");

        ConsultarUbicacion consultarUbicacionService = new ConsultarUbicacion(datosEmpleador.getIdUbicacionJudicial());
        consultarUbicacionService.execute();
        ubicacion = (UbicacionDTO) consultarUbicacionService.getResult();
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionJudicial => ubicacion beforeMerge -> " + ubicacion.toString());

        UbicacionDTO nuevaUbicacion = new UbicacionDTO();
        nuevaUbicacion.setIdMunicipio(datosEmpleador.getMunicipioJudicial() != null && datosEmpleador.getMunicipioJudicial().getIdMunicipio() != null ? datosEmpleador.getMunicipioJudicial().getIdMunicipio() : null);
        nuevaUbicacion.setDireccion(datosEmpleador.getDireccionFisicaJudicial());
        nuevaUbicacion.setDescripcionIndicacion(datosEmpleador.getDescripcionIndicacionJudicial());
        nuevaUbicacion.setCodigoPostal(datosEmpleador.getCodigoPostalJudicial());
        nuevaUbicacion.setIndicativoTelefonoFijo(datosEmpleador.getIndicativoTelFijoJudicial() != null ? datosEmpleador.getIndicativoTelFijoJudicial().toString() : null);
        nuevaUbicacion.setTelefonoFijo(datosEmpleador.getTelefonoFijoJudicial());
        nuevaUbicacion.setTelefonoCelular(datosEmpleador.getTelefonoCelularJudicial());
        nuevaUbicacion.setCorreoElectronico(datosEmpleador.getEmail());
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionJudicial => nuevaUbicacion beforeMerge -> " + nuevaUbicacion.toString());

        mergeObjects(ubicacion, nuevaUbicacion);
        logger.info("***Weizman => NovedadesComposite.modificarUbicacionJudicial => ubicacion afterMerge -> " + ubicacion.toString());
        logger.debug("Fin de método modificarUbicacionJudicial(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");
        return ubicacion;

    }


    /**
     * Método que se encargad de modificar los datos de la ubicación de una sucursal
     *
     * @param ubicacion
     * @param datosEmpleador
     */
    private UbicacionDTO modificarUbicacionSucursal(UbicacionDTO ubicacion, DatosEmpleadorNovedadDTO datosEmpleador) {
        logger.debug("Inicio de método modificarUbicacionSucursal(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");

        ConsultarUbicacion consultarUbicacionService = new ConsultarUbicacion(datosEmpleador.getIdUbicacionSucursal());
        consultarUbicacionService.execute();
        ubicacion = (UbicacionDTO) consultarUbicacionService.getResult();

        ubicacion.setIdMunicipio(datosEmpleador.getMunicipioSucursal().getIdMunicipio());
        ubicacion.setDireccion(datosEmpleador.getDireccionFisicaSucursal());
        ubicacion.setDescripcionIndicacion(datosEmpleador.getDescripcionIndicacionSucursal());
        ubicacion.setCodigoPostal(datosEmpleador.getCodigoPostalSucursal());
        if (datosEmpleador.getIndicativoTelFijoSucursal() != null) {
            ubicacion.setIndicativoTelefonoFijo(datosEmpleador.getIndicativoTelFijoSucursal().toString());
        } else {
            ubicacion.setIndicativoTelefonoFijo(null);
        }
        ubicacion.setTelefonoFijo(datosEmpleador.getTelefonoFijoSucursal());
        ubicacion.setTelefonoCelular(datosEmpleador.getTelefonoCelularSucursal());
        logger.debug("Fin de método modificarUbicacionSucursal(Ubicacion ubicacion, DatosEmpleadorNovedadDTO datosEmpleador)");
        return ubicacion;

    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub

    }

    /**
     * Realiza Merge entre 2 objetos del mismo tipo. Si le envías
     *
     * @param target objeto actual
     * @param source objeto nuevo con la nueva infomación a actualizar
     * @return tarjet retorna el objeto que le enviemos como primer parámetro (ojeto actual)
     */
    public static <T> T mergeObjects(T target, T source) {
        if (target == null || source == null) {
            throw new IllegalArgumentException("Both target and source objects must be non-null.");
        }

        Class<?> targetClass = target.getClass();
        Class<?> sourceClass = source.getClass();

        if (!targetClass.equals(sourceClass)) {
            throw new IllegalArgumentException("Both target and source objects must be of the same class.");
        }

        try {
            Field[] fields = targetClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object sourceValue = field.get(source);
                if (sourceValue != null) {
                    field.set(target, sourceValue);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return target;
    }
}
