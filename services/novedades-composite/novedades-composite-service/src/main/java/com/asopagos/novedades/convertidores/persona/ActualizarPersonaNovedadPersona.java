package com.asopagos.novedades.convertidores.persona;

/**
 * 
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarAfiliado;
import com.asopagos.afiliados.clients.ConsultarBeneficiario;
import com.asopagos.afiliados.clients.ConsultarEstadoAfiliacionRespectoCCF;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.personas.Pais;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.SectorUbicacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.composite.clients.RegistrarRetiroAutomaticoPorFallecimiento;
import com.asopagos.novedades.composite.clients.VerificarPersonaNovedadRegistrarAnalisisFovis;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ActualizarDatosPersona;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.personas.clients.CrearPersona;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import com.asopagos.personas.clients.ActualizarCondicionInvalidez;  
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import com.asopagos.afiliados.clients.ActualizarRolesAfiliado;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.afiliados.clients.ConsultarCondicionInvalidezConyugeCuidador;


/**
 * Clase que contiene la lógica para actualizar los datos basicos de una persona
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarPersonaNovedadPersona implements NovedadCore {

    private static final List<TipoTransaccionEnum> txCambioTipoNumeroIdentificacion = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioTipoNumeroIdentificacionMasivo = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioNombreApellido = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioFechaNacimiento = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioFechaExpedicion = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioNivelEducativo = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txActivaDesactCondicionCabezaHogar = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioDatosCorrespondencia = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txActivaDesactAutorizacionCorreo = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioGenero = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioEstadoCivil = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txReporteFallecimiento = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txActualizaGradoCursado = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txActualizaInfoPadresBiologicos = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioOrientacionSexual = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioFactorVulnerabilidad = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txCambioPertenenciaEtnica = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txActualizaPaisResidencia = new ArrayList<>();
    private static final List<TipoTransaccionEnum> txActualizaDatosCaracterizacionPoblacion = new ArrayList<>();

    static {
        txCambioTipoNumeroIdentificacion.add(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS);

        // GLPI 96686 NOVEDAD Cambio de tipo y número de identificación - Personas Masivo
        txCambioTipoNumeroIdentificacionMasivo.add(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO);

        txCambioNombreApellido.add(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS);
        txCambioNombreApellido.add(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB);
        txCambioNombreApellido.add(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB);

        txCambioFechaNacimiento.add(TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_DEPWEB);
        txCambioFechaNacimiento.add(TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_PRESENCIAL);
        txCambioFechaNacimiento.add(TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_WEB);

        txCambioFechaExpedicion.add(TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB);
        txCambioFechaExpedicion.add(TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL);
        txCambioFechaExpedicion.add(TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_WEB);

        txCambioNivelEducativo.add(TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS);
        txCambioNivelEducativo.add(TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_DEPWEB);
        txCambioNivelEducativo.add(TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_WEB);

        txActivaDesactCondicionCabezaHogar.add(TipoTransaccionEnum.ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS);
        txActivaDesactCondicionCabezaHogar
                .add(TipoTransaccionEnum.ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS_DEPWEB);
        txActivaDesactCondicionCabezaHogar
                .add(TipoTransaccionEnum.ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS_WEB);

        txCambioDatosCorrespondencia.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS);
        txCambioDatosCorrespondencia.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB);
        txCambioDatosCorrespondencia.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_WEB);

        txActivaDesactAutorizacionCorreo
                .add(TipoTransaccionEnum.ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS);
        txActivaDesactAutorizacionCorreo
                .add(TipoTransaccionEnum.ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS_DEPWEB);

        txCambioGenero.add(TipoTransaccionEnum.CAMBIO_GENERO_PERSONAS);

        txCambioEstadoCivil.add(TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS);
        txCambioEstadoCivil.add(TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS_WEB);

        txReporteFallecimiento.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS);
        txReporteFallecimiento.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB);
        txReporteFallecimiento.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_WEB);

        txActualizaInfoPadresBiologicos
                .add(TipoTransaccionEnum.ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_DEPWEB);
        txActualizaInfoPadresBiologicos
                .add(TipoTransaccionEnum.ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_PRESENCIAL);
        txActualizaInfoPadresBiologicos
                .add(TipoTransaccionEnum.ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_WEB);

        txCambioOrientacionSexual.add(TipoTransaccionEnum.CAMBIO_ORIENTACION_SEXUAL_PERSONAS);
        txCambioOrientacionSexual.add(TipoTransaccionEnum.CAMBIO_ORIENTACION_SEXUAL_PERSONAS_DEPWEB);
        txCambioOrientacionSexual.add(TipoTransaccionEnum.CAMBIO_ORIENTACION_SEXUAL_PERSONAS_WEB);

        txCambioFactorVulnerabilidad.add(TipoTransaccionEnum.CAMBIO_FACTOR_VULNERABILIDAD_PERSONAS);
        txCambioFactorVulnerabilidad.add(TipoTransaccionEnum.CAMBIO_FACTOR_VULNERABILIDAD_PERSONAS_DEPWEB);
        txCambioFactorVulnerabilidad.add(TipoTransaccionEnum.CAMBIO_FACTOR_VULNERABILIDAD_PERSONAS_WEB);

        txCambioPertenenciaEtnica.add(TipoTransaccionEnum.CAMBIO_PERTENENCIA_ETNICA_PERSONAS);
        txCambioPertenenciaEtnica.add(TipoTransaccionEnum.CAMBIO_PERTENENCIA_ETNICA_PERSONAS_DEPWEB);
        txCambioPertenenciaEtnica.add(TipoTransaccionEnum.CAMBIO_PERTENENCIA_ETNICA_PERSONAS_WEB);

        txActualizaPaisResidencia.add(TipoTransaccionEnum.ACTUALIZACION_PAIS_RESIDENCIA_PERSONAS);
        txActualizaPaisResidencia.add(TipoTransaccionEnum.ACTUALIZACION_PAIS_RESIDENCIA_PERSONAS_DEPWEB);
        txActualizaPaisResidencia.add(TipoTransaccionEnum.ACTUALIZACION_PAIS_RESIDENCIA_PERSONAS_WEB);

        txActualizaDatosCaracterizacionPoblacion.add(TipoTransaccionEnum.CAMBIAR_DATOS_CARACTERIZACION_POBLACION);
        txActualizaDatosCaracterizacionPoblacion.add(TipoTransaccionEnum.CAMBIAR_DATOS_CARACTERIZACION_POBLACION_DEPWEB);
        txActualizaDatosCaracterizacionPoblacion.add(TipoTransaccionEnum.CAMBIAR_DATOS_CARACTERIZACION_POBLACION_WEB);
    }

    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        // Se transforma a un objeto de datos de la persona
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        // Para revisar si el afiliado esta activo o inactivo
        List<RolAfiliadoModeloDTO> rolAfiliadoDTO = new ArrayList<RolAfiliadoModeloDTO>();
        // Indica si se debe verificar la validacion de postulado FOVIS
        Boolean validarPostuladoFovis = false;
        // Indica si se registro un fallecimiento
        Boolean isFallecimiento = false;
        // Informacion persona existente 
        PersonaModeloDTO personaDTO = new PersonaModeloDTO();
        // Datos persona
        // Identificacion
        TipoIdentificacionEnum tipoIdentificacion = null;
        String numeroIdentificacion = null;
        // Nombres
        String primerApellido = null;
        String segundoApellido = null;
        String primerNombre = null;
        String segundoNombre = null;
        // Nivel Educativo
        NivelEducativoEnum nivelEducativo = null;
        Integer ocupacionProfesion = null;
        // Fechas
        Long fechaNacimiento = null;
        Long fechaExpedicion = null;
        // Cabeza Hogar
        Boolean cabezaHogar = null;
        // Grado Academico
        Short gradoAcademico = null;
        // Datos ubicacion
        UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
        Boolean autorizaEnvioMail = null;
        Boolean autorizaDatosPersonales = null;
        Boolean viveCasaPropia = null;
        Boolean resideSectorRural = null;
        // Genero
        GeneroEnum genero = null;
        // Estado civil
        EstadoCivilEnum estadoCivil = null;
        // Fallecimiento
        Boolean fallecido = null;
        Long fechaFallecido = null;
        Long fechaDefuncion = null;
        // Información Padres Biológicos
        PersonaModeloDTO padreBiologico = null;
        PersonaModeloDTO madreBiologica = null;
        // Orientacion Sexual
        OrientacionSexualEnum orientacionSexual = null;
        FactorVulnerabilidadEnum factorVulnerabilidad = null;
        PertenenciaEtnicaEnum pertenenciaEtnica = null;
        SectorUbicacionEnum sectorUbicacion = null;
        Long paisResidencia = null;

        Long idResguardo = null;
        Long idPuebloIndigena = null;
        // RolAfiliado para consultar si esta activo o inactivo
        Long idRolAfiliado = null;
        Boolean esBeneficiario = false;
        // Se inicializa el estado de la persona ACTIVO antes de la consulta
        EstadoAfiliadoEnum estadoPersona = EstadoAfiliadoEnum.ACTIVO;

        // Se consulta si se selecciono un beneficiario o un afiliado
        BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
        if (datosPersona.getIdBeneficiario() != null) {
            esBeneficiario = true;
            // Se consulta la persona
            ConsultarBeneficiario consultarBeneficiario = new ConsultarBeneficiario(datosPersona.getIdBeneficiario());
            consultarBeneficiario.execute();
            beneficiarioModeloDTO = consultarBeneficiario.getResult();

            personaDTO = new PersonaModeloDTO(beneficiarioModeloDTO.convertToPersonaEntity(),
                    beneficiarioModeloDTO.convertToPersonaDetalleEntity());

            tipoIdentificacion = datosPersona.getTipoIdentificacionBeneficiario();
            numeroIdentificacion = datosPersona.getNumeroIdentificacionBeneficiario();

            primerApellido = datosPersona.getPrimerApellidoBeneficiario();
            segundoApellido = datosPersona.getSegundoApellidoBeneficiario();
            primerNombre = datosPersona.getPrimerNombreBeneficiario();
            segundoNombre = datosPersona.getSegundoNombreBeneficiario();

            if (datosPersona.getProfesionConyuge() != null
                 && datosPersona.getProfesionConyuge().getIdOcupacionProfesion() != null 
                 && beneficiarioModeloDTO.getTipoBeneficiario() == ClasificacionEnum.CONYUGE) {
                ocupacionProfesion = datosPersona.getProfesionConyuge().getIdOcupacionProfesion();
                nivelEducativo = datosPersona.getNivelEducativoConyuge();
            }
            fechaNacimiento = datosPersona.getFechaNacimientoTrabajador();
            fechaExpedicion = datosPersona.getFechaExpedicionDocumentoTrabajador();

            if (datosPersona.getGradoCursadoHijo() != null
                    && datosPersona.getGradoCursadoHijo().getIdgradoAcademico() != null && (beneficiarioModeloDTO.getTipoBeneficiario() == ClasificacionEnum.HIJO_BIOLOGICO || beneficiarioModeloDTO.getTipoBeneficiario() == ClasificacionEnum.HIJASTRO)) {
                gradoAcademico = datosPersona.getGradoCursadoHijo().getIdgradoAcademico();
                ocupacionProfesion = datosPersona.getProfesionHijo().getIdOcupacionProfesion();
                nivelEducativo = datosPersona.getNivelEducativoHijo();
            }
            if (personaDTO.getUbicacionModeloDTO() != null
                    && personaDTO.getUbicacionModeloDTO().getIdUbicacion() != null) {
                ubicacionModeloDTO = personaDTO.getUbicacionModeloDTO();
            } else {
                personaDTO.setUbicacionModeloDTO(ubicacionModeloDTO);
            }
            autorizaEnvioMail = datosPersona.getAutorizacionEnvioCorreoElectronicoTrabajador();
            autorizaDatosPersonales = datosPersona.getAutorizaUtilizarDatosPersonales();

            genero = datosPersona.getGeneroBeneficiario();

            estadoCivil = datosPersona.getEstadoCivilBeneficiario();

            fallecido = datosPersona.getPersonaFallecidaTrabajador();
            fechaFallecido = datosPersona.getFechaReporteFallecimientoTrabajador();
            fechaDefuncion = datosPersona.getFechaDefuncion();

            padreBiologico = datosPersona.getPadreBiologico();
            madreBiologica = datosPersona.getMadreBiologica();
        } else {
            // Se consulta la persona
            ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(
                    datosPersona.getNumeroIdentificacion(),
                    datosPersona.getTipoIdentificacion());
            consultarDatosPersona.execute();
            personaDTO = consultarDatosPersona.getResult();

            // Se recuperan los datos actualizables del afiliado
            tipoIdentificacion = datosPersona.getTipoIdentificacionTrabajador();
            numeroIdentificacion = datosPersona.getNumeroIdentificacionTrabajador();

            primerApellido = datosPersona.getPrimerApellidoTrabajador();
            segundoApellido = datosPersona.getSegundoApellidoTrabajador();
            primerNombre = datosPersona.getPrimerNombreTrabajador();
            segundoNombre = datosPersona.getSegundoNombreTrabajador();

            nivelEducativo = datosPersona.getNivelEducativoTrabajador();
            if (datosPersona.getOcupacionProfesionTrabajador() != null
                    && datosPersona.getOcupacionProfesionTrabajador().getIdOcupacionProfesion() != null) {
                ocupacionProfesion = datosPersona.getOcupacionProfesionTrabajador().getIdOcupacionProfesion();
            }

            fechaNacimiento = datosPersona.getFechaNacimientoTrabajador();
            fechaExpedicion = datosPersona.getFechaExpedicionDocumentoTrabajador();

            cabezaHogar = datosPersona.getCabezaHogar();

            if (datosPersona.getGradoCursado() != null
                    && datosPersona.getGradoCursado().getIdgradoAcademico() != null) {
                gradoAcademico = datosPersona.getGradoCursado().getIdgradoAcademico();
            }

            if (personaDTO.getUbicacionModeloDTO() != null
                    && personaDTO.getUbicacionModeloDTO().getIdUbicacion() != null) {
                ubicacionModeloDTO = personaDTO.getUbicacionModeloDTO();
            } else {
                personaDTO.setUbicacionModeloDTO(ubicacionModeloDTO);
            }
            autorizaEnvioMail = datosPersona.getAutorizacionEnvioCorreoElectronicoTrabajador();
            autorizaDatosPersonales = datosPersona.getAutorizaUtilizarDatosPersonales();
            viveCasaPropia = datosPersona.getViveEnCasaPropia();
            resideSectorRural = datosPersona.getResideEnSectorRural();

            genero = datosPersona.getGeneroTrabajador();

            estadoCivil = datosPersona.getEstadoCivilTrabajador();

            fallecido = datosPersona.getPersonaFallecidaTrabajador();
            fechaFallecido = datosPersona.getFechaReporteFallecimientoTrabajador();
            fechaDefuncion = datosPersona.getFechaDefuncion();

            padreBiologico = datosPersona.getPadreBiologico();
            madreBiologica = datosPersona.getMadreBiologica();
            
            orientacionSexual = datosPersona.getOrientacionSexual();
            factorVulnerabilidad = datosPersona.getFactorVulnerabilidad();
            pertenenciaEtnica = datosPersona.getPertenenciaEtnica();
            sectorUbicacion = datosPersona.getSectorUbicacion();
            paisResidencia = datosPersona.getPaisResidencia();
            idResguardo = datosPersona.getIdResguardo();
            idPuebloIndigena = datosPersona.getIdPuebloIndigena();
        }

        // Se verifica la novedad para efectuar la actualizacion
        if (txCambioTipoNumeroIdentificacion.contains(novedad)) {
            // TODO Pendiente Anibol
            personaDTO.setTipoIdentificacion(tipoIdentificacion);
            personaDTO.setNumeroIdentificacion(numeroIdentificacion);
        } else if (txCambioTipoNumeroIdentificacionMasivo.contains(novedad)){
            personaDTO.setTipoIdentificacion(tipoIdentificacion);
            personaDTO.setNumeroIdentificacion(numeroIdentificacion);
        } else if (txCambioNombreApellido.contains(novedad)) {
            // TODO Pendiente Anibol
            personaDTO.setPrimerApellido(primerApellido);
            personaDTO.setSegundoApellido(segundoApellido);
            personaDTO.setPrimerNombre(primerNombre);
            personaDTO.setSegundoNombre(segundoNombre);
        } else if (txCambioFechaNacimiento.contains(novedad)) {
            personaDTO.setFechaNacimiento(fechaNacimiento);
        } else if (txCambioFechaExpedicion.contains(novedad)) {
            personaDTO.setFechaExpedicionDocumento(fechaExpedicion);
        } else if (txCambioNivelEducativo.contains(novedad)) {
            personaDTO.setNivelEducativo(nivelEducativo);
            personaDTO.setIdOcupacionProfesion(ocupacionProfesion);
            personaDTO.setGradoAcademico(gradoAcademico);
        } else if (txActivaDesactCondicionCabezaHogar.contains(novedad)) {
            personaDTO.setCabezaHogar(cabezaHogar);
        } else if (txCambioDatosCorrespondencia.contains(novedad)) {
            this.actualizarCorrespondenciaTrabajador(ubicacionModeloDTO, datosPersona);
            personaDTO.setHabitaCasaPropia(viveCasaPropia);
            personaDTO.setResideSectorRural(resideSectorRural);
        } else if (txActivaDesactAutorizacionCorreo.contains(novedad)) {
            ubicacionModeloDTO.setAutorizacionEnvioEmail(autorizaEnvioMail);
            personaDTO.setAutorizaUsoDatosPersonales(autorizaDatosPersonales);
        } else if (txCambioGenero.contains(novedad)) {
            personaDTO.setGenero(genero);
        } else if (txCambioEstadoCivil.contains(novedad)) {
            personaDTO.setEstadoCivil(estadoCivil);
        } else if (txReporteFallecimiento.contains(novedad)) {
            if (fallecido == null || !fallecido) {
                fallecido = Boolean.TRUE;
            }
            personaDTO.setFallecido(fallecido);
            personaDTO.setFechaFallecido(fechaFallecido);
            personaDTO.setFechaDefuncion(fechaDefuncion);
            validarPostuladoFovis = true;
            isFallecimiento = true;
            ConsultarRolesAfiliado rolesAfiliado = new ConsultarRolesAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,datosPersona.getNumeroIdentificacionTrabajador(),datosPersona.getTipoIdentificacionTrabajador());
            
            rolesAfiliado.execute();
            
            List<RolAfiliadoEmpleadorDTO> rolesEmpleador = rolesAfiliado.getResult();
            
            // El código anterior utiliza las expresiones stream y lambda de Java para iterar sobre
            // una lista de objetos "rolAfiliadoDTO". Para cada objeto "rolAfiliadoDTO" verifica si
            // el campo "fechaInicioCondicionVet" no es nulo. Si no es nulo establece el campo
            // "fechaFinCondicionVet" a la fecha actual. Finalmente, recopila los objetos
            // "rolAfiliadoDTO" modificados en una nueva lista.
            // rolAfiliadoDTO = rolesEmpleador.stream().map(rolAfiliado ->{
            //     if(rolAfiliado.getRolAfiliado().getClaseTrabajador().equals(ClaseTrabajadorEnum.VETERANO_FUERZA_PUBLICA) && rolAfiliado.getRolAfiliado().getFechaInicioCondicionVet() != null){
            //         rolAfiliado.getRolAfiliado().setFechaFinCondicionVet(new Date());
            //         rolAfiliado.getRolAfiliado().setFechaInicioCondicionVet(rolAfiliado.getRolAfiliado().getFechaInicioCondicionVet());
            //         rolAfiliado.getRolAfiliado().setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
            //     }
            //     return rolAfiliado.getRolAfiliado();
            // }).collect(Collectors.toList());

            // // El código anterior crea una instancia de la clase "ActualizarRolesAfiliado" y pasa
            // // un objeto "rolAfiliadoDTO" como parámetro a su constructor.
            // ActualizarRolesAfiliado actualizarRolesAfiliado = new ActualizarRolesAfiliado(rolAfiliadoDTO);

            // // El código anterior está ejecutando el método execute() sobre el objeto
            // // actualizarRolesAfiliado.
            // actualizarRolesAfiliado.execute();

            for(RolAfiliadoEmpleadorDTO rolEmpleador : rolesEmpleador) {
                RolAfiliadoModeloDTO rolafi = rolEmpleador.getRolAfiliado(); 
                if(rolafi.getClaseTrabajador().equals(ClaseTrabajadorEnum.VETERANO_FUERZA_PUBLICA) && rolafi.getFechaInicioCondicionVet() != null){
                    rolafi.setFechaFinCondicionVet(new Date());
                    rolafi.setFechaInicioCondicionVet(rolafi.getFechaInicioCondicionVet());
                    rolafi.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);

                    ActualizarRolesAfiliado actualizarRolesAfiliado = new ActualizarRolesAfiliado(rolAfiliadoDTO);

                    // El código anterior está ejecutando el método execute() sobre el objeto
                    // actualizarRolesAfiliado.
                    // System.out.println("rolesAfiliado"+rolAfiliadoDTO.get(0));
                    actualizarRolesAfiliado.execute();
                }

                // System.out.println("2g rolEmpleador.rolAfiliado.getRolAfiliado().setEstadoAfiliado "+rolafi.getEstadoAfiliado());

            }

            if (!esBeneficiario) {

                ConsultarEstadoAfiliacionRespectoCCF estadoSrv = new ConsultarEstadoAfiliacionRespectoCCF(null,
                        tipoIdentificacion, numeroIdentificacion);
                estadoSrv.execute();

                estadoPersona = estadoSrv.getResult().getEstadoAfiliacion();

            } else {
                estadoPersona = beneficiarioModeloDTO.getEstadoBeneficiarioAfiliado();
                


                if(beneficiarioModeloDTO.getTipoBeneficiario().equals(ClasificacionEnum.CONYUGE)){
                    ConsultarCondicionInvalidezConyugeCuidador consultarCondicionInvalidezConyugeCuidador = new ConsultarCondicionInvalidezConyugeCuidador(tipoIdentificacion, numeroIdentificacion);
                    consultarCondicionInvalidezConyugeCuidador.execute();

                    List<CondicionInvalidez> listaCondicionInvalidez = consultarCondicionInvalidezConyugeCuidador.getResult();
                    for(CondicionInvalidez condicionInvalidez : listaCondicionInvalidez) {
                        CondicionInvalidezModeloDTO condicionInvalidezModeloDTO = new CondicionInvalidezModeloDTO();
                        condicionInvalidezModeloDTO.convertToDTO(condicionInvalidez);
                        condicionInvalidezModeloDTO.setFechaFinConyugeCuidador(new Date().getTime());
                        condicionInvalidezModeloDTO.setConyugeCuidador(false);
                        ActualizarCondicionInvalidez actualizarCondicionInvalidez = new ActualizarCondicionInvalidez(condicionInvalidezModeloDTO);
                        actualizarCondicionInvalidez.execute();
                    }
                }
                
            }
        } else if (txActualizaInfoPadresBiologicos.contains(novedad)) {
            personaDTO.setIdPersonaPadre(validarRegistrarPersona(padreBiologico));
            personaDTO.setIdPersonaMadre(validarRegistrarPersona(madreBiologica));
        } else if (txCambioOrientacionSexual.contains(novedad)) {
            personaDTO.setOrientacionSexual(orientacionSexual);
        } else if (txCambioFactorVulnerabilidad.contains(novedad)) {
            personaDTO.setFactorVulnerabilidad(factorVulnerabilidad);
        } else if (txCambioPertenenciaEtnica.contains(novedad)) {
            personaDTO.setPertenenciaEtnica(pertenenciaEtnica);
        } else if (txActualizaPaisResidencia.contains(novedad)) {
            personaDTO.setIdPaisResidencia(paisResidencia);
        }else if (txActualizaDatosCaracterizacionPoblacion.contains(novedad)) {
            personaDTO.setPertenenciaEtnica(pertenenciaEtnica);
            personaDTO.setFactorVulnerabilidad(factorVulnerabilidad);
            personaDTO.setOrientacionSexual(orientacionSexual);
            personaDTO.setIdResguardo(idResguardo);
            personaDTO.setIdPuebloIndigena(idPuebloIndigena);

        }
        // Se verifica si se indico desde validaciones que la persona esta asociada a
        // una postulacion FOVIS
        // Se crea la tarea para revisarla en la HU325-77
        if (validarPostuladoFovis) {
            // Se verifica si la persona esta asociada a una postulacion FOVIS
            List<PersonaDTO> listaPersonas = new ArrayList<>();
            PersonaDTO persona = new PersonaDTO();
            persona.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
            persona.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
            listaPersonas.add(persona);
            // Se crea la tarea para revisarla en la HU325-77
            VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                    solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
            verificarPersonaNovedadRegistrarAnalisisFovis.execute();
        }

        // Se realiza la ejecución de retiro de la persona por el registro del
        // fallecimiento
        // if (rolAfiliadoDTO != null) {

        if ((isFallecimiento && estadoPersona.equals(EstadoAfiliadoEnum.ACTIVO))) {
            RegistrarRetiroAutomaticoPorFallecimiento retiroAutomaticoPorFallecimiento = new RegistrarRetiroAutomaticoPorFallecimiento(
                    solicitudNovedadDTO);
            retiroAutomaticoPorFallecimiento.execute();
        }
        // } else {
        // if (isFallecimiento) {
        // RegistrarRetiroAutomaticoPorFallecimiento retiroAutomaticoPorFallecimiento =
        // new RegistrarRetiroAutomaticoPorFallecimiento(solicitudNovedadDTO);
        // retiroAutomaticoPorFallecimiento.execute();
        // }
        // }

        ActualizarDatosPersona actualizarPersonaDTO = new ActualizarDatosPersona(personaDTO);
        return actualizarPersonaDTO;
    }

    /**
     * Actualiza los datos de Correspondencia asociados al Trabajador
     * Dependiente-Independiente-Pensionado
     * 
     * @param ubicacion
     * @param datosPersona
     */
    private void actualizarCorrespondenciaTrabajador(UbicacionModeloDTO ubicacionDTO, DatosPersonaNovedadDTO datosPersona) {
        System.out.println("***Weizman => NovedadesComposite.actualizarCorrespondenciaTrabajador => ubicacion beforeMerge -> " + ubicacionDTO.toString());
        UbicacionModeloDTO ubicacionDTONueva = new UbicacionModeloDTO();
        ubicacionDTONueva.setIdMunicipio(datosPersona.getMunicipioTrabajador() != null && datosPersona.getMunicipioTrabajador().getIdMunicipio() != null ? datosPersona.getMunicipioTrabajador().getIdMunicipio() : null);
        ubicacionDTONueva.setDireccionFisica(datosPersona.getDireccionResidenciaTrabajador());
        ubicacionDTONueva.setDescripcionIndicacion(datosPersona.getDescripcionIndicacionResidenciaTrabajador());
        ubicacionDTONueva.setCodigoPostal(datosPersona.getCodigoPostalTrabajador());
        ubicacionDTONueva.setIndicativoTelFijo(datosPersona.getIndicativoTelFijoTrabajador() != null ? datosPersona.getIndicativoTelFijoTrabajador().toString() : null);
        ubicacionDTONueva.setTelefonoFijo(datosPersona.getTelefonoFijoTrabajador());
        ubicacionDTONueva.setTelefonoCelular(datosPersona.getTelefonoCelularTrabajador());
        ubicacionDTONueva.setEmail(datosPersona.getCorreoElectronicoTrabajador());
        mergeObjects(ubicacionDTO, ubicacionDTONueva);
        System.out.println("***Weizman => NovedadesComposite.actualizarCorrespondenciaTrabajador => ubicacion afterMerge -> " + ubicacionDTO.toString());
    }

    /**
     * Verifica y registra la información de las persona enviada por parametro
     * 
     * @param persona Informacion persona padre/madre biológico
     * @return Identificar persona sistema
     */
    private Long validarRegistrarPersona(PersonaModeloDTO persona) {
        // Se verifica si se envio informacion de la persona
        if (persona == null || persona.getTipoIdentificacion() == null || persona.getNumeroIdentificacion() == null) {
            return null;
        }
        try {
            CrearPersona crearPersonaService = new CrearPersona(persona);
            crearPersonaService.execute();
            return crearPersonaService.getResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager,
            UserDTO userDTO) {
        // TODO Auto-generated method stub

    }

    /**
     * Realiza Merge entre 2 objetos del mismo tipo. Si le envías
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