package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BancoModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.*;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.personas.clients.ConsultarPaisPorCodigo;
import com.asopagos.personas.dto.PaisDTO;
import com.asopagos.pila.clients.ConsultarBancosParametrizados;
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
public class ActualizacionInformacionAfiliadosPersistLine implements IPersistLine {

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

                    // Informacion basica afiliado
                    AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)) {
                        String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)
                            .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                            .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        afiliadoModeloDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
                        afiliadoModeloDTO.setNumeroIdentificacion(numeroIdentificacion);
                    }
                    // Se asigna el campo No 3 - Primer apellido del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.PRIMER_APELLIDO_AFILIADO)) {
                        String primerApellido = getValorCampo(line, ArchivoCampoNovedadConstante.PRIMER_APELLIDO_AFILIADO);
                        afiliadoModeloDTO.setPrimerApellido(primerApellido);
                    }
                    // Se asigna el campo No 4 - Segundo apellido del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_AFILIADO)) {
                        String segundoApellido = getValorCampo(line, ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_AFILIADO);
                        afiliadoModeloDTO.setSegundoApellido(segundoApellido);
                    }
                    // Se asigna el campo No 5 - Primer nombre del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.PRIMER_NOMBRE_AFILIADO)) {
                        String primerNombre = getValorCampo(line, ArchivoCampoNovedadConstante.PRIMER_NOMBRE_AFILIADO);
                        afiliadoModeloDTO.setPrimerNombre(primerNombre);
                    }
                    // Se asigna el campo No 6 - Segundo nombre del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_AFILIADO)) {
                        String segundoNombre = getValorCampo(line, ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_AFILIADO);
                        afiliadoModeloDTO.setSegundoNombre(segundoNombre);
                    }
                    // Se asigna el campo No 7 - Fecha de nacimiento del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_AFILIADO)) {
                        String fechaNacimientoText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_AFILIADO);
                        Date fechaNacimiento = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaNacimientoText);
                        afiliadoModeloDTO.setFechaNacimiento(fechaNacimiento.getTime());
                    }
                    // Se asigna el campo No 8 - Genero del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.GENERO_AFILIADO)) {
                        String genero = getValorCampo(line, ArchivoCampoNovedadConstante.GENERO_AFILIADO);
                        GeneroEnum generoEnum = GetValueUtil.getGeneroCodigo(genero);
                        afiliadoModeloDTO.setGenero(generoEnum);
                    }
                    // Se asigna el campo No 14 - Fecha de expedición del documento de Identificación
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_EXPEDICION_DOC_AFILIADO)) {
                        String fechaExpedicionDocText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_EXPEDICION_DOC_AFILIADO);
                        Date fechaExpedicionDoc = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaExpedicionDocText);
                        afiliadoModeloDTO.setFechaExpedicionDocumento(fechaExpedicionDoc.getTime());
                    }

                    // Informacion ubicacion nacimiento
                    UbicacionDTO ubicacionNacimientoDTO = new UbicacionDTO();
                    // Se asigna el campo No 9 - Departamento de nacimiento del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_NACIMIENTO_AFILIADO)) {
                        String codigoDepartamento = getValorCampo(line, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_NACIMIENTO_AFILIADO);
                        List<Departamento> listDepartamento = ((List<Departamento>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
                        Departamento departamento = GetValueUtil.getDepartamento(listDepartamento, Long.parseLong(codigoDepartamento));
                        ubicacionNacimientoDTO.setIdDepartamento(departamento.getIdDepartamento());
                    }
                    // Se asigna el campo No 10 - Municipio de nacimiento del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_NACIMIENTO_AFILIADO)) {
                        String codigoMunicipio = getValorCampo(line, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_NACIMIENTO_AFILIADO);
                        List<Municipio> listMunicipio = (List<Municipio>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
                        Municipio municipio = GetValueUtil.getMunicipio(listMunicipio, Long.parseLong(codigoMunicipio));
                        ubicacionNacimientoDTO.setIdMunicipio(municipio.getIdMunicipio());
                    }
                    UbicacionModeloDTO ubicacionNacimientoModeloDTO = new UbicacionModeloDTO();
                    ubicacionNacimientoModeloDTO.convertToDTO(UbicacionDTO.obtenerUbicacion(ubicacionNacimientoDTO));
                    informacionActualizacionNovedadDTO.setUbicacionNacimientoModeloDTO(ubicacionNacimientoModeloDTO);

                    // Se asigna el campo No 11 - Nacionalidad del trabajador o cabeza de familia
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NACIONALIDAD_AFILIADO)) {
                        String nacionalidad = getValorCampo(line, ArchivoCampoNovedadConstante.NACIONALIDAD_AFILIADO);
                        informacionActualizacionNovedadDTO.setNacionalidad(nacionalidad);
                    }
                    // Informacion ubicacion expedicion Documento
                    UbicacionDTO ubicacionExpedicionDocDTO = new UbicacionDTO();
                    // Se asigna el campo No 12 - Departamento de expedición del documento de identificación
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EXPEDICION_DOC_AFILIADO)) {
                        String codigoDepartamento = getValorCampo(line, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EXPEDICION_DOC_AFILIADO);
                        List<Departamento> listDepartamento = ((List<Departamento>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
                        Departamento departamento = GetValueUtil.getDepartamento(listDepartamento, Long.parseLong(codigoDepartamento));
                        ubicacionExpedicionDocDTO.setIdDepartamento(departamento.getIdDepartamento());
                    }
                    // Se asigna el campo No 13 - Municipio de expedición del documento de identificación
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EXPEDICION_DOC_AFILIADO)) {
                        String codigoMunicipio = getValorCampo(line, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EXPEDICION_DOC_AFILIADO);
                        List<Municipio> listMunicipio = (List<Municipio>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
                        Municipio municipio = GetValueUtil.getMunicipio(listMunicipio, Long.parseLong(codigoMunicipio));
                        ubicacionExpedicionDocDTO.setIdMunicipio(municipio.getIdMunicipio());
                    }

                    // Se asigna el campo No 15 - Salario
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SALARIO)) {
                        String salario = getValorCampo(line, ArchivoCampoNovedadConstante.SALARIO);
                        afiliadoModeloDTO.setSalarioAfiliado(salario);
                    }
                    // Se asigna el campo No 16 - Orientación sexual
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.ORIENTACION_SEXUAL)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.ORIENTACION_SEXUAL);
                        OrientacionSexualEnum newInput = OrientacionSexualEnum.valueOf(input);
                        afiliadoModeloDTO.setOrientacionSexual(newInput);
                    }
                    // Se asigna el campo No 17 - Factor de vulnerabilidad
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FACTOR_DE_VULNERABILIDAD)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.FACTOR_DE_VULNERABILIDAD);
                        FactorVulnerabilidadEnum newInput = FactorVulnerabilidadEnum.valueOf(input);
                        afiliadoModeloDTO.setFactorVulnerabilidad(newInput);
                    }
                    // Se asigna el campo No 18 - Estado civil
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.ESTADO_CIVIL)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.ESTADO_CIVIL);
                        EstadoCivilEnum newInput = EstadoCivilEnum.valueOf(input);
                        afiliadoModeloDTO.setEstadoCivil(newInput);
                    }
                    // Se asigna el campo No 19 - Pertenencia étnica
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.PERTENENCIA_ETNICA)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.PERTENENCIA_ETNICA);
                        PertenenciaEtnicaEnum newInput = PertenenciaEtnicaEnum.valueOf(input);
                        afiliadoModeloDTO.setPertenenciaEtnica(newInput);
                    }
                    // Se asigna el campo No 20 - País
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.PAIS)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.PAIS);
                        if(input != null){
                            ConsultarPaisPorCodigo consultarPaisPorCodigo = new ConsultarPaisPorCodigo(Long.parseLong(input));
                            consultarPaisPorCodigo.execute();
                            PaisDTO paisDTO = consultarPaisPorCodigo.getResult();
                            afiliadoModeloDTO.setPais(paisDTO.obtenerPais(paisDTO));
                        }
                    }
                    // Se asigna el campo No 21 - Correo electrónico
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.EMAIL)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.EMAIL);
                        afiliadoModeloDTO.setEmail(input);
                    }
                    // Se asigna el campo No 22 - Celular
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CELULAR_AFILIADO)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.CELULAR_AFILIADO);
                        afiliadoModeloDTO.setCelular(input);
                    }
                    // Se asigna el campo No 23 - Teléfono
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TELEFONO_AFILIADO)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.TELEFONO_AFILIADO);
                        afiliadoModeloDTO.setTelefono(input);
                    }
                    // Se asigna el campo No 24 - Medio de pago para subsidio monetario
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO)) {
                        String input = getValorCampo(line, ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO);
                        TipoMedioDePagoEnum tipoMedioDePagoEnum = TipoMedioDePagoEnum.valueOf(input);
                        MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
                        // Se asigna el campo No 24 - Medio de pago para subsidio monetario al objeto
                        medioDePagoModeloDTO.setTipoMedioDePago(tipoMedioDePagoEnum);
                        if (tipoMedioDePagoEnum.equals(TipoMedioDePagoEnum.TRANSFERENCIA)) {
                            // Se asigna el campo No 26 - tipoCuenta
                            if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_DE_CUENTA)) {
                                String tipoCuenta = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_DE_CUENTA);
                                TipoCuentaEnum tipoCuentaEnum = TipoCuentaEnum.valueOf(tipoCuenta);
                                medioDePagoModeloDTO.setTipoCuenta(tipoCuentaEnum);
                            }
                            // Se asigna el campo No 27 - numeroCuenta
                            if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_DE_CUENTA)) {
                                String numeroCuenta = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_DE_CUENTA);
                                medioDePagoModeloDTO.setNumeroCuenta(numeroCuenta);
                            }
                            // Se asigna el campo No 28 - tipoIdentificacionTitular
                            if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_TITULAR)) {
                                String numeroCuenta = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_TITULAR);
                                TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(numeroCuenta);
                                medioDePagoModeloDTO.setTipoIdentificacionTitular(tipoIdentificacionEnum);
                            }
                            // Se asigna el campo No 29 - numeroIdentificacionTitular
                            if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_TITULAR)) {
                                String numeroIdentificacionEnum = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_TITULAR);
                                medioDePagoModeloDTO.setNumeroIdentificacionTitular(numeroIdentificacionEnum);
                            }
                            // Se asigna el campo No 30 - nombreTitularCuenta
                            if (esCampoValido(line, ArchivoCampoNovedadConstante.NOMBRE_TITULAR_CUENTA)) {
                                String nombreTitularCuenta = getValorCampo(line, ArchivoCampoNovedadConstante.NOMBRE_TITULAR_CUENTA);
                                medioDePagoModeloDTO.setNombreTitularCuenta(nombreTitularCuenta);
                            }
                            // Se asigna el campo No 31 - NIT Banco
                            if (esCampoValido(line, ArchivoCampoNovedadConstante.NIT_BANCO)) {
                                String nit = getValorCampo(line, ArchivoCampoNovedadConstante.NIT_BANCO);

                                //Se valida el NIT del banco
                                ConsultarBancosParametrizados consultarBancosParametrizados = new ConsultarBancosParametrizados();
                                consultarBancosParametrizados.execute();
                                List<BancoModeloDTO> listaBancos = consultarBancosParametrizados.getResult();

                                // Filtramos la lista de bancos por el NIT
                                if (listaBancos != null && !listaBancos.isEmpty()) {
                                    for (BancoModeloDTO banco : listaBancos) {
                                        String bancoNit = banco.getNit();
                                        if (medioDePagoModeloDTO.getTipoCuenta().equals(TipoCuentaEnum.DAVIPLATA)) {
                                            //Cuando el tipo de cuenta sea daviplata por defecto en banco se debe colocar Davivienda.
                                            String nombreBanco = banco.getNombre();
                                            if (nombreBanco != null && nombreBanco.contains("DAVIVIENDA")) {
                                                medioDePagoModeloDTO.setBancoModeloDTO(banco);
                                                break; // Salir del bucle una vez que se encuentra el banco
                                            }
                                        } else {
                                            bancoNit = bancoNit.split("-")[0]; // Tomar la primera parte del NIT antes del guión
                                            if (bancoNit.equals(nit)) {
                                                medioDePagoModeloDTO.setBancoModeloDTO(banco);
                                                break; // Salir del bucle una vez que se encuentra el banco
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        afiliadoModeloDTO.setMedioDePagoModeloDTO(medioDePagoModeloDTO);
                    }

                    UbicacionModeloDTO ubicacionExpedicionDocModeloDTO = new UbicacionModeloDTO();
                    ubicacionExpedicionDocModeloDTO.convertToDTO(UbicacionDTO.obtenerUbicacion(ubicacionExpedicionDocDTO));
                    informacionActualizacionNovedadDTO.setUbicacionExpedicionDocModeloDTO(ubicacionExpedicionDocModeloDTO);

                    informacionActualizacionNovedadDTO.setAfiliado(afiliadoModeloDTO);

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
      //  throw new UnsupportedOperationException();
    }
}