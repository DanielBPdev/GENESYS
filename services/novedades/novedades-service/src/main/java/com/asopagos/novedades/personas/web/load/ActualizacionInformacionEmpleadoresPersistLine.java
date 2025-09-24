package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:241 </br>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
public class ActualizacionInformacionEmpleadoresPersistLine implements IPersistLine {

    /**
     * (non-Javadoc)
     *
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        Map<String, Object> line;
        InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO = null;
        for (int i = 0; i < lines.size(); i++) {
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));
            if (hallazgos.isEmpty()) {
                try {
                    line = lineArgumentDTO.getLineValues();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO();

                    // Informacion basica empleador
                    EmpleadorModeloDTO empleadorModeloDTO = new EmpleadorModeloDTO();
                    // Se asigna el campo No 1 - Tipo de identificación del autorizado a reportar (empleador)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR)) {
                        String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR)
                            .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                            .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        empleadorModeloDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 2 - Número de identificación del autorizado a reportar (empleador)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
                        empleadorModeloDTO.setNumeroIdentificacion(numeroIdentificacion);
                    }
                    // Se asigna el campo No 3 - Digito de verificación 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.DIGITO_VERIFICACION_EMPLEADOR)) {
                        String digitoVerificacion = getValorCampo(line, ArchivoCampoNovedadConstante.DIGITO_VERIFICACION_EMPLEADOR);
                        empleadorModeloDTO.setDigitoVerificacion(Short.valueOf(digitoVerificacion));
                    }
                    // Se asigna el campo No 4 - Razón social del autorizado a reportar(empleador)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.RAZON_SOCIAL_EMPLEADOR)) {
                        String razonSocial = getValorCampo(line, ArchivoCampoNovedadConstante.RAZON_SOCIAL_EMPLEADOR);
                        empleadorModeloDTO.setRazonSocial(razonSocial);
                    }
                    // Se asigna el campo No 5 - Naturaleza jurídica  
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NATURALEZA_JURIDICA_EMPLEADOR)) {
                        String naturalezaJuridica = getValorCampo(line, ArchivoCampoNovedadConstante.NATURALEZA_JURIDICA_EMPLEADOR);
                        NaturalezaJuridicaEnum naturalezaJuridicaEnum = NaturalezaJuridicaEnum
                            .obtenerNaturalezaJuridica(Integer.parseInt(naturalezaJuridica));
                        empleadorModeloDTO.setNaturalezaJuridica(naturalezaJuridicaEnum);
                    }
                    // Se asigna el campo No 14 - Número de empleados 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_EMPLEADOS_EMPLEADOR)) {
                        String numeroTrabajadores = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_EMPLEADOS_EMPLEADOR);
                        empleadorModeloDTO.setNumeroTotalTrabajadores(Integer.parseInt(numeroTrabajadores));
                    }
                    // Se asigna el campo No 15 - Actividad económica principal
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.ACTIVIDAD_ECONOMICA_PRINCIPAL_EMPLEADOR)) {
                        String actividadPrincipal = getValorCampo(line,
                            ArchivoCampoNovedadConstante.ACTIVIDAD_ECONOMICA_PRINCIPAL_EMPLEADOR);

                        List<CodigoCIIU> listCodigoCIIU = ((List<CodigoCIIU>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU));
                        CodigoCIIU codigoCIIU = GetValueUtil.getCodigoCIIU(listCodigoCIIU, actividadPrincipal);
                        empleadorModeloDTO.setCodigoCIIU(codigoCIIU);
                    }
                    // Se asigna el campo No 17 - Fecha de constitución de la empresa 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_CONSTITUCION_EMPLEADOR)) {
                        String fechaConstitucionText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_CONSTITUCION_EMPLEADOR);
                        Date fecha = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaConstitucionText);
                        empleadorModeloDTO.setFechaConstitucion(fecha.getTime());
                    }

                    // Informacion basica representante legal
                    PersonaModeloDTO personaRepreLegal = new PersonaModeloDTO();
                    // Se asigna el campo No 6 - Tipo de identificación del representante legal
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR)) {
                        String tipoIdentificacion = getValorCampo(line,
                            ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR).toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                            .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        personaRepreLegal.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 7 - Número de identificación del representante legal 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR)) {
                        String numeroIdentificacion = getValorCampo(line,
                            ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR);
                        personaRepreLegal.setNumeroIdentificacion(numeroIdentificacion);
                    }
                    // Informacion ubicacion representante legal
                    UbicacionModeloDTO ubicacionRepreLegal = new UbicacionModeloDTO();
                    // Se asigna el campo No 8 - Correo electrónico del representante legal
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CORREO_ELECTRONICO_REPRE_LEGAL_EMPLEADOR)) {
                        String correoElectronico = getValorCampo(line,
                            ArchivoCampoNovedadConstante.CORREO_ELECTRONICO_REPRE_LEGAL_EMPLEADOR);
                        ubicacionRepreLegal.setEmail(correoElectronico);
                    }
                    // Se asigna el campo No 9 - Teléfono de contacto del representante legal 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TELEFONO_REPRE_LEGAL_EMPLEADOR)) {
                        String telefono = getValorCampo(line, ArchivoCampoNovedadConstante.TELEFONO_REPRE_LEGAL_EMPLEADOR);
                        ubicacionRepreLegal.setTelefonoCelular(telefono);
                    }
                    personaRepreLegal.setUbicacionModeloDTO(ubicacionRepreLegal);

                    // Informacion ubicacion empleador
                    UbicacionDTO ubicacionDTO = new UbicacionDTO();
                    // Se asigna el campo No 10 - Código del departamento de ubicación de la empresa 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EMPLEADOR)) {
                        String codigoDepartamento = getValorCampo(line, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EMPLEADOR);
                        List<Departamento> listDepartamento = ((List<Departamento>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
                        Departamento departamento = GetValueUtil.getDepartamento(listDepartamento, Long.parseLong(codigoDepartamento));
                        ubicacionDTO.setIdDepartamento(departamento.getIdDepartamento());
                    }
                    // Se asigna el campo No 11 - Código del municipio de ubicación de la empresa 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EMPLEADOR)) {
                        String codigoMunicipio = getValorCampo(line, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EMPLEADOR);
                        List<Municipio> listMunicipio = (List<Municipio>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
                        Municipio municipio = GetValueUtil.getMunicipio(listMunicipio, Long.parseLong(codigoMunicipio));
                        ubicacionDTO.setIdMunicipio(municipio.getIdMunicipio());
                    }
                    // Se asigna el campo No 12 - Dirección de ubicación de la empresa 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.DIRECCION_EMPLEADOR)) {
                        String direccionEmpleador = getValorCampo(line, ArchivoCampoNovedadConstante.DIRECCION_EMPLEADOR);
                        ubicacionDTO.setDireccion(direccionEmpleador);
                    }
                    UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
                    ubicacionModeloDTO.convertToDTO(UbicacionDTO.obtenerUbicacion(ubicacionDTO));
                    empleadorModeloDTO.setUbicacionModeloDTO(ubicacionModeloDTO);
                    // Se asigna el campo No 13 - Fecha de renovación de la Cámara de Comercio
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_RENOVACION_CAMARA_COMERCIO_EMPLEADOR)) {
                        String fechaRenovacionText = getValorCampo(line,
                            ArchivoCampoNovedadConstante.FECHA_RENOVACION_CAMARA_COMERCIO_EMPLEADOR);
                        Date fechaRenovacion = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaRenovacionText);
                        informacionActualizacionNovedadDTO.setFechaRenovacionCamaraComercio(fechaRenovacion.getTime());
                    }
                    // Se asigna el campo No 16 - Actividad económica secundaria 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.ACTIVIDADA_ECONOMICA_SECUNDARIA_EMPLEADOR)) {
                        String actividadSecundaria = getValorCampo(line,
                            ArchivoCampoNovedadConstante.ACTIVIDADA_ECONOMICA_SECUNDARIA_EMPLEADOR);
                        informacionActualizacionNovedadDTO.setActividadEconomicaSecundaria(actividadSecundaria);
                    }

                    // Se asigna el campo No 17 - Fecha de constitución de la empresa
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_CONSTITUCION_EMPLEADOR)) {
                        String fechaConstitucionText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_CONSTITUCION_EMPLEADOR);
                        Date fecha = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaConstitucionText);
                        empleadorModeloDTO.setFechaConstitucion(fecha.getTime());
                    }
                    // Se asigna el campo No 18 - Correo electrónico 1(oficina principal)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.EMAIL_1_OFICINA_PRINCIPAL)) {
                        String email1OficinaPrincipal = getValorCampo(line,
                            ArchivoCampoNovedadConstante.EMAIL_1_OFICINA_PRINCIPAL);
                        empleadorModeloDTO.setEmail1OficinaPrincipal(email1OficinaPrincipal);
                    }
                    // Se asigna el campo No 19 - Correo electrónico 2 (envió de correspondencia)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.EMAIL_2_ENVIO_DE_CORRESPONDENCIA)) {
                        String email2EnvioDeCorrespondencia = getValorCampo(line,
                            ArchivoCampoNovedadConstante.EMAIL_2_ENVIO_DE_CORRESPONDENCIA);
                        empleadorModeloDTO.setEmail2EnvioDeCorrespondencia(email2EnvioDeCorrespondencia);
                    }
                    // Se asigna el campo No 20 - Correo electrónico 3 (Notificación judicial)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.EMAIL_3_NOTIFICACION_JUDICIAL)) {
                        String email3NotificacionJudicial = getValorCampo(line,
                            ArchivoCampoNovedadConstante.EMAIL_3_NOTIFICACION_JUDICIAL);
                        empleadorModeloDTO.setEmail3NotificacionJudicial(email3NotificacionJudicial);
                    }
                    // Se asigna el campo No 21 - Teléfono 1(oficina principal)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TELEFONO_1_OFICINA_PRINCIPAL)) {
                        String telefono1OficinaPrincipal = getValorCampo(line,
                            ArchivoCampoNovedadConstante.TELEFONO_1_OFICINA_PRINCIPAL);
                        empleadorModeloDTO.setTelefono1OficinaPrincipal(telefono1OficinaPrincipal);
                    }
                    // Se asigna el campo No 22 - Teléfono 2(envió de correspondencia)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TELEFONO_2_ENVIO_DE_CORRESPONDENCIA)) {
                        String telefono2EnvioDeCorrespondencia = getValorCampo(line,
                            ArchivoCampoNovedadConstante.TELEFONO_2_ENVIO_DE_CORRESPONDENCIA);
                        empleadorModeloDTO.setTelefono2EnvioDeCorrespondencia(telefono2EnvioDeCorrespondencia);
                    }
                    // Se asigna el campo No 23 - Teléfono 3(Notificación judicial)
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TELEFONO_3_NOTIFICACION_JUDICIAL)) {
                        String telefono3NotificacionJudicial = getValorCampo(line,
                            ArchivoCampoNovedadConstante.TELEFONO_3_NOTIFICACION_JUDICIAL);
                        empleadorModeloDTO.setTelefono3NotificacionJudicial(telefono3NotificacionJudicial);
                    }
                    // Se asigna el campo No 24 -  Celular
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CELULAR_EMPLEADOR)) {
                        String celular = getValorCampo(line,
                            ArchivoCampoNovedadConstante.CELULAR_EMPLEADOR);
                        empleadorModeloDTO.setCelularOficinaPrincipal(celular);
                    }
                    // Se asigna el campo No 25 -  Medio de pago para subsidio monetario
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_EMPLEADOR)) {
                        try {
                            TipoMedioDePagoEnum medioDePagoParaSubsidioMonetario = TipoMedioDePagoEnum.valueOf(getValorCampo(line,
                                ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_EMPLEADOR));
                            empleadorModeloDTO.setMedioDePagoSubsidioMonetario(medioDePagoParaSubsidioMonetario);
                        } catch (Exception ex) {
                        }
                    }

                    informacionActualizacionNovedadDTO.setEmpleador(empleadorModeloDTO);
                    informacionActualizacionNovedadDTO.setRepresentanteLegal(personaRepreLegal);

                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                        .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                } catch (Exception e) {
                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                        .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                }
            }
        }
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private Boolean esCampoValido(Map<String, Object> line, String nombreCampo) {
        if (line.get(nombreCampo) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private String getValorCampo(Map<String, Object> line, String nombreCampo) {
        return ((String) line.get(nombreCampo));
    }

    /**
     * (non-Javadoc)
     *
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        throw new UnsupportedOperationException();
    }
}