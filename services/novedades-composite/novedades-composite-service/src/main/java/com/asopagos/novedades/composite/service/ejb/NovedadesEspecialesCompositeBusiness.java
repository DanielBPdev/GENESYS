package com.asopagos.novedades.composite.service.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.afiliaciones.clients.ActualizarSolicitudAfiPersona;
import com.asopagos.afiliados.clients.ActualizarBeneficiario;
import com.asopagos.afiliados.clients.ActualizarDatosAfiliado;
import com.asopagos.afiliados.clients.ActualizarGrupoFamiliarPersona;
import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.afiliados.clients.AsociarBeneficiarioAGrupoFamiliar;
import com.asopagos.afiliados.clients.CrearActualizarRolSustitucionPatronal;
import com.asopagos.afiliados.clients.ValidarEmpleadorCeroTrabajadores;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.empleadores.clients.ActualizarRolContactoEmpleador;
import com.asopagos.empleadores.clients.AnularSolictudesEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleadorId;
import com.asopagos.empleadores.clients.ConsultarNombreUsuarioEmpleadores;
import com.asopagos.empleadores.clients.CrearRepresentanteLegal;
import com.asopagos.empleadores.clients.GestionarSociosEmpleador;
import com.asopagos.empleadores.clients.InactivarEmpleadores;
import com.asopagos.empleadores.clients.ModificarEmpleador;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.clients.ActualizarSolicitudNovedadPersona;
import com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO;
import com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO;
import com.asopagos.novedades.composite.dto.SolicitudAfiliacionRolDTO;
import com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService;
import com.asopagos.novedades.composite.service.util.NovedadesCompositeUtils;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.DatosNovedadRepresentanteDTO;
import com.asopagos.novedades.dto.DatosNovedadRolContactoDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ActualizarDatosPersona;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rutine.afiliadosrutines.actualizarrolafiliado.ActualizarRolAfiliadoRutine;
import com.asopagos.rutine.empleadores.ModificarEmpleadorRutine;
import com.asopagos.rutine.personasrutines.actualizardatospersonarutine.ActualizarDatosPersonaRutine;
import com.asopagos.usuarios.clients.InactivarUsuario;
import com.asopagos.usuarios.clients.InactivarUsuariosMasivo;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio para novedades especiales Composite <b>Historia de Usuario:</b>
 * proceso 1.3
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Stateless
public class NovedadesEspecialesCompositeBusiness implements NovedadesEspecialesCompositeService {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesEspecialesCompositeBusiness.class);

    private static final String PREFIJO_EMPLEADOR = "emp_";

    @PersistenceContext(unitName = "novedades_PU")
    private EntityManager entityManager;
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarCambiosRepresentantes(com.asopagos.novedades.dto.DatosNovedadRepresentanteDTO)
     */
    @Override
    public void ejecutarCambiosRepresentantes(DatosNovedadRepresentanteDTO datosNovedadRepresentanteDTO) {
        logger.debug("Inicio ejecutarCambiosRepresentantes(DatosNovedadRepresentanteDTO)");
        //Se ejecuta Actualización de Representante Legal.
        CrearRepresentanteLegal crearRepresentanteLegal = new CrearRepresentanteLegal(datosNovedadRepresentanteDTO.getIdEmpleador(),
                Boolean.TRUE, datosNovedadRepresentanteDTO.getRepresentanteLegal());
        crearRepresentanteLegal.execute();

        //Si se obtienen datos de Representante Legal Suplente se ejecuta su Actualización.
        if (datosNovedadRepresentanteDTO.getRepresentanteLegalSuplente() != null) {
            CrearRepresentanteLegal crearRepresentanteLegalSuplente = new CrearRepresentanteLegal(
                    datosNovedadRepresentanteDTO.getIdEmpleador(), Boolean.FALSE,
                    datosNovedadRepresentanteDTO.getRepresentanteLegalSuplente());
            crearRepresentanteLegalSuplente.execute();
        }

        //Si se obtienen datos de Socios se ejecuta su Actualización.
        if (datosNovedadRepresentanteDTO.getSociosEmpleador() != null) {
            GestionarSociosEmpleador gestionarSociosEmpleador = new GestionarSociosEmpleador(datosNovedadRepresentanteDTO.getIdEmpleador(),
                    datosNovedadRepresentanteDTO.getSociosEmpleador());
            gestionarSociosEmpleador.execute();
        }
        logger.debug("Fin ejecutarCambiosRepresentantes(DatosNovedadRepresentanteDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarCambiosRolesContacto(com.asopagos.novedades.dto.DatosNovedadRolContactoDTO)
     */
    @Override
    public void ejecutarCambiosRolesContacto(DatosNovedadRolContactoDTO datosNovedadRolContactoDTO) {
        logger.debug("Inicio de método ejecutarCambiosRolesContacto(DatosNovedadRolContactoDTO datosNovedadRolContactoDTO)");
        List<RolContactoEmpleador> listaRolContactoEmpleador = new ArrayList<RolContactoEmpleador>();
        //Se agrega Actualización Rol Afiliación.
        listaRolContactoEmpleador.add(datosNovedadRolContactoDTO.getRolAfiliacion());
        //Se ejecuta Actualización Rol Aportes.
        listaRolContactoEmpleador.add(datosNovedadRolContactoDTO.getRolAportes());
        //Se ejecuta Actualización Rol Subsidio.
        listaRolContactoEmpleador.add(datosNovedadRolContactoDTO.getRolSubsidio());
        ActualizarRolContactoEmpleador actualizarRolContactoSubsidio = new ActualizarRolContactoEmpleador(
                datosNovedadRolContactoDTO.getIdEmpleador(), listaRolContactoEmpleador);
        actualizarRolContactoSubsidio.execute();
        logger.debug("Fin de método ejecutarCambiosRolesContacto(DatosNovedadRolContactoDTO datosNovedadRolContactoDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarDesafiliacion(com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO)
     */
    @Override
    public void ejecutarDesafiliacion(EmpleadorAfiliadosDTO desafiliacionDTO) {
        logger.info("**__**Inicio ejecutarDesafiliacion(EmpleadorAfiliadosDTO)");
 
        ModificarEmpleador modificarEmpleadorService = new ModificarEmpleador(desafiliacionDTO.getEmpleador());
        modificarEmpleadorService.execute();

        /* Si requiere inactivar Cuenta Web se ejecuta servicio. */
        if (desafiliacionDTO.getInactivarCuentaWeb() != null) {
            String usuario = PREFIJO_EMPLEADOR + desafiliacionDTO.getEmpleador().getTipoIdentificacion() + "_"
                    + desafiliacionDTO.getEmpleador().getNumeroIdentificacion();
            InactivarUsuario inactivarUsuarioService = new InactivarUsuario(usuario, desafiliacionDTO.getInactivarCuentaWeb());
            inactivarUsuarioService.execute();
        } 
        logger.info("Fin ejecutarDesafiliacion(EmpleadorAfiliadosDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarSustitucionTrabajadores(com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO)
     */
    @Override
    public void ejecutarSustitucionTrabajadores(EmpleadorAfiliadosDTO sustitucion) {
        logger.debug("Inicio ejecutarSustitucionTrabajadores(EmpleadorAfiliadosDTO)");
        // Se crea el nuevo rol con el empleador destino
        CrearActualizarRolSustitucionPatronal crearActualizarRolSustitucionPatronal = new CrearActualizarRolSustitucionPatronal(
                sustitucion.getRoles());
        crearActualizarRolSustitucionPatronal.execute();
        // Se ejecuta la desafiliación del empleador origen si tiene cero trabajadores
        if (sustitucion.getEmpleador().getEstadoEmpleador().equals(EstadoEmpleadorEnum.INACTIVO)) {
            ModificarEmpleador modificarEmpleadorService = new ModificarEmpleador(sustitucion.getEmpleador());
            modificarEmpleadorService.execute();

            /* Si requiere inactivar Cuenta Web se ejecuta servicio. */
            if (sustitucion.getInactivarCuentaWeb() != null) {
                String usuario = PREFIJO_EMPLEADOR + sustitucion.getEmpleador().getTipoIdentificacion() + "_"
                        + sustitucion.getEmpleador().getNumeroIdentificacion();
                InactivarUsuario inactivarUsuarioService = new InactivarUsuario(usuario, sustitucion.getInactivarCuentaWeb());
                inactivarUsuarioService.execute();
            }
        }
        logger.debug("Fin ejecutarSustitucionTrabajadores(EmpleadorAfiliadosDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarRetiroTrabajadores(com.asopagos.dto.modelo.RolAfiliadoModeloDTO)
     */
    @Override
    public void ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarActualizacionBeneficiario(com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO)
     */
    @Override
    public void ejecutarActualizacionBeneficiario(BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO) {
       NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
       n.ejecutarActualizacionBeneficiario(beneficiarioGrupoAfiliadoDTO);
       
    }
 

    /** (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarActualizacionSolicitud(com.asopagos.novedades.composite.dto.SolicitudAfiliacionRolDTO)
     */
    @Override
    public void ejecutarActualizacionSolicitud(SolicitudAfiliacionRolDTO solicitudAfiliacionRolDTO) {
        logger.debug("Inicio ejecutarActualizacionSolicitud(SolicitudAfiliacionRolDTO)");
        ActualizarSolicitudAfiPersona actualizarSolicitudAfiliacionPersona = new ActualizarSolicitudAfiPersona(
                solicitudAfiliacionRolDTO.getSolicitudAfiliacion());
        actualizarSolicitudAfiliacionPersona.execute();

        ActualizarRolAfiliado actualizarRolAfiliadoService = new ActualizarRolAfiliado(solicitudAfiliacionRolDTO.getRol());
        actualizarRolAfiliadoService.execute();
        logger.debug("Fin ejecutarActualizacionSolicitud(SolicitudAfiliacionRolDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#desafiliarEmpleadoresAutomatico(com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO)
     */
    @Override
    public void desafiliarEmpleadoresAutomatico(DatosNovedadAutomaticaDTO datosEmpleadorNovedad) {
        logger.debug("Inicio desafiliarEmpleadoresAutomatico(DatosNovedadAutomaticaDTO)");
        
        Boolean inactivacionInmediata = Boolean.FALSE;
        if (!MotivoDesafiliacionEnum.CERO_TRABAJADORES_NOVEDAD_INTERNA.equals(datosEmpleadorNovedad.getMotivoDesafiliacion())) {
            inactivacionInmediata = Boolean.TRUE;
        }
        
        /* Se inactivan los Empleadores asociados. */
        InactivarEmpleadores inactivarEmpleadores = new InactivarEmpleadores(datosEmpleadorNovedad.getMotivoDesafiliacion(), datosEmpleadorNovedad.getIdEmpleadores());
        inactivarEmpleadores.execute();

        /*se anula la ultima afiliación de este empleador*/
        AnularSolictudesEmpleador actualizarSolicitud = new AnularSolictudesEmpleador(datosEmpleadorNovedad.getIdEmpleadores());
        actualizarSolicitud.execute();
        
        /* Se inactiva la cuenta Web de los Empleadores. */
        ConsultarNombreUsuarioEmpleadores consultarNombreUsuarioEmpleadores = new ConsultarNombreUsuarioEmpleadores(datosEmpleadorNovedad.getIdEmpleadores());
        consultarNombreUsuarioEmpleadores.execute();
        List<String> usuariosEmpleador = consultarNombreUsuarioEmpleadores.getResult();

        InactivarUsuariosMasivo inactivarUsuariosMasivo = new InactivarUsuariosMasivo(inactivacionInmediata, usuariosEmpleador);
        inactivarUsuariosMasivo.execute();
        logger.debug("Fin desafiliarEmpleadoresAutomatico(DatosNovedadAutomaticaDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadesEspecialesCompositeService#ejecutarTrasladoBeneficiariosGrupoFamiliar(com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO)
     */
    @Override
    public void ejecutarTrasladoBeneficiariosGrupoFamiliar(BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO) {
        logger.debug("Inicio ejecutarTrasladoBeneficiariosGrupoFamiliar(BeneficiarioGrupoAfiliadoDTO)");

        // Se obtiene el identificacion del grupo familiar
        beneficiarioGrupoAfiliadoDTO.getGrupoFamiliar().setIdAfiliado(beneficiarioGrupoAfiliadoDTO.getAfiliado().getIdAfiliado());
        ActualizarGrupoFamiliarPersona actualizarGrupoFamiliarService = new ActualizarGrupoFamiliarPersona(
                beneficiarioGrupoAfiliadoDTO.getGrupoFamiliar());
        actualizarGrupoFamiliarService.execute();
        Long idGrupoFamiliar = actualizarGrupoFamiliarService.getResult();

        // Se asocia el grupo familiar al beneficiario
        DatosBasicosIdentificacionDTO inDTO = new DatosBasicosIdentificacionDTO();
        inDTO.setPersona(PersonaDTO.convertPersonaToDTO(beneficiarioGrupoAfiliadoDTO.getBeneficiario().convertToPersonaEntity(), null));
        AsociarBeneficiarioAGrupoFamiliar asociarBeneficiarioAGrupoFamiliar = new AsociarBeneficiarioAGrupoFamiliar(idGrupoFamiliar,
                beneficiarioGrupoAfiliadoDTO.getAfiliado().getIdAfiliado(), inDTO);
        asociarBeneficiarioAGrupoFamiliar.execute();

        logger.debug("Fin ejecutarTrasladoBeneficiariosGrupoFamiliar(BeneficiarioGrupoAfiliadoDTO)");
    }

}
