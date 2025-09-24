package com.asopagos.novedades.personas.web.load.validator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-449 , HU-550 <br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */

public class NovedadTrabajadorLineValidator extends LineValidator {

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(NovedadTrabajadorLineValidator.class);
    /**
     * Constante que contiene el valor de la fecha en el año 1952
     */
    private static final int MIL_NOVECIENTOS_CINCUENTA_DOS = 1952;
    /**
     * Constante que contiene el valor de la fecha en el año 1900
     */
    private static final int MIL_NOVECIENTOS = 1900;
    /**
     * 
     */
    private Calendar cNacimiento = Calendar.getInstance();
    /**
     * 
     */
    private Calendar cNacimientoMilNovecientos = Calendar.getInstance();
    /**
     * Calendar
     */
    private Calendar cInicioLabores = Calendar.getInstance();
    /*
     * Inicializacion de la lista de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public NovedadTrabajadorLineValidator() {
        // Se asigna la fecha del año establecido
        cNacimiento.set(Calendar.YEAR, MIL_NOVECIENTOS_CINCUENTA_DOS);
        cNacimiento.set(Calendar.MONTH, 1);
        cNacimiento.set(Calendar.DAY_OF_YEAR, 1);
        // Se asigna la fecha del año de nacimiento minima
        cNacimientoMilNovecientos.set(Calendar.YEAR, MIL_NOVECIENTOS);
        cNacimientoMilNovecientos.set(Calendar.MONTH, 1);
        cNacimientoMilNovecientos.set(Calendar.DAY_OF_YEAR, 1);

        cInicioLabores.set(Calendar.YEAR, 1965);
        cInicioLabores.set(Calendar.MONTH, 1);
        cInicioLabores.set(Calendar.DAY_OF_YEAR, 1);
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        /*
         * Se valida a partir de la segunda linea del cargue
         */
        if (arguments.getLineNumber() > ArchivoMultipleCampoConstants.LINEA_ENCABEZADO) {
              try {
                    lstHallazgos = new ArrayList<>();
                    Map<String, Object> line = arguments.getLineValues();
                    //Verificación datos de identificación
                    verificarDatosIdentificacion(arguments, line);
                    //Verificación de la ubicación de la persona 
                    verificarUbicacion(arguments, line);
                    //Verificación laboral
                    verificarInformacionLaboral(arguments, line);
                } finally {
                    ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
                    ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
                    if (!lstHallazgos.isEmpty()) {
                        ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
                    }
                }
        }
    }

    /**
     * Verificación de la información laboral 
     * @param arguments
     * @param line
     */
    private void verificarInformacionLaboral(LineArgumentDTO arguments, Map<String, Object> line) {
        // Clase de trabajador
        try {
            if (line.get(ArchivoCampoNovedadConstante.CLASE_TRABAJADOR) != null) {
                ClaseTrabajadorEnum claseTrabajadorEnum = GetValueUtil
                        .getClaseTrabajadorDescripcion(((String) line.get(ArchivoCampoNovedadConstante.CLASE_TRABAJADOR)));
                if (claseTrabajadorEnum == null) {
                    throw new Exception("Invalido");
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.CLASE_TRABAJADOR_MSG,
                    "Clase trabajador debe ser valida"));
        }
        Calendar sistema = Calendar.getInstance();
        // Fecha de inicio labores con el empleador
        verificacionFechaInicioLabEmpleador(arguments, line, sistema);
        // Validación valor salario mensual
        verificarSalarioMensual(arguments);
        // Cambio de nivel educativo
        try {
            if (line.get(ArchivoCampoNovedadConstante.CAMBIO_NIVEL_EDUCATIVO) != null) {
                NivelEducativoEnum nivel = GetValueUtil.getNivelEducativoEnumDescripcion(
                        ((String) line.get(ArchivoCampoNovedadConstante.CAMBIO_NIVEL_EDUCATIVO)));
                if (nivel == null) {
                    throw new Exception("Invalido");
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.CAMBIO_NIVEL_EDUCATIVO_MSG,
                    "Cambio de nivel educativo invalido"));
        }
        // Cambio tipo Salario
        try {
            if (line.get(ArchivoCampoNovedadConstante.CAMBIO_TIPO_SALARIO) != null) {
                TipoSalarioEnum tipoSalarioEnum = GetValueUtil
                        .getTipoSalarioDescripcion(((String) line.get(ArchivoCampoNovedadConstante.CAMBIO_TIPO_SALARIO)));
                if (tipoSalarioEnum == null ) {
                    throw new Exception("Invalido");
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.CAMBIO_TIPO_SALARIO_MSG,
                    "Cambio de tipo salario invalido"));
        }
        // Cargo u oficio desempeñado
        if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.CARGO_OFICIO) != null) {
            String cargoOficina = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.CARGO_OFICIO);
            if (cargoOficina.length() > ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.CARGO_OFICIO_MSG,
                        "Caracteres permitidos máximo: " + ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES));
            }
        }
        // Cambio tipo Contrato
        try {
            if (line.get(ArchivoCampoNovedadConstante.TIPO_CONTRATO) != null) {
                TipoContratoEnum tipoContratoEnum = GetValueUtil.getTipoContratoDescripcion(((String) line.get(ArchivoCampoNovedadConstante.TIPO_CONTRATO)));
                if (tipoContratoEnum == null) {
                    throw new Exception("Invalido");
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_CONTRATO_MSG,
                    "Tipo de contrato invalido"));
        }
    }

    /**
     * Método encargado de verificar los datos de identificación de la persona
     * @param arguments
     * @param line
     */
    private void verificarDatosIdentificacion(LineArgumentDTO arguments, Map<String, Object> line) {
        /*
         * se simula la captura de los datos desde la pantalla para
         * poder utilizar el mismo servicios en el momento de
         * persistir la información completa.
         */
        TipoIdentificacionEnum tipoIdentificacion = null;
        // se valida el tipo de identificación
        try {
            tipoIdentificacion = GetValueUtil
                    .getTipoIdentificacionDescripcion(((String) line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION)));
            if (tipoIdentificacion == null) {
                 throw new Exception("Invalido");
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_MSG,
                    "Tipo de identificación invalido"));
            tipoIdentificacion = null;
        }
        // // Se valida el numero de documento
        verificarNumeroDocumento(tipoIdentificacion, arguments);
        // // se valida el primer nombre
        validarRegex(arguments, ArchivoCampoNovedadConstante.PRIMER_NOMBRE, ExpresionesRegularesConstants.PRIMER_NOMBRE,
                "Digite el primer nombre únicamente sin incluir espacios.", ArchivoCampoNovedadConstante.PRIMER_NOMBRE_MSG);
        // // se valida el segundo nombre
        validarRegex(arguments, ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE, ExpresionesRegularesConstants.SEGUNDO_NOMBRE,
                "Digite el segundo nombre. Si requiere ingresar más nombres, separelos mediante espacio. Ejemplo: Andrea Carolina",
                ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_MSG);
        // // se valida el primer apellido
        validarRegex(arguments, ArchivoCampoNovedadConstante.PRIMER_APELLIDO, ExpresionesRegularesConstants.PRIMER_APELLIDO,
                "Digite el primer apellido. Si el apellido tiene varias palabras, separelas con espacio. Ejemplo. De la Peña",
                ArchivoCampoNovedadConstante.PRIMER_APELLIDO_MSG);
        // se valida el segundo apellido
        validarRegex(arguments, ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO, ExpresionesRegularesConstants.SEGUNDO_APELLIDO,
                "Digite el primer apellido. Si el apellido tiene varias palabras, separelas con espacio. Ejemplo: De la Peña",
                ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_MSG);
    }

    /**
     * Método encargado de verificar la ubicación de la persona por me dio del Departamento, Municipio, y lugar de residencia
     * @param arguments
     * @param line
     */
    private void verificarUbicacion(LineArgumentDTO arguments, Map<String, Object> line) {
        // se Valida el Departamento
        Departamento departamento = null;
        List<Departamento> lstDepartamento = null;
        boolean hayDepartamento = false;
        try {
            lstDepartamento = ((List<Departamento>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
            if (line.get(ArchivoCampoNovedadConstante.DEPARTAMENTO) != null) {
                hayDepartamento = true;
                String nombreDepartamento = ((String) line.get(ArchivoCampoNovedadConstante.DEPARTAMENTO)).toUpperCase();
                departamento = GetValueUtil.getDepartamentoNombre(lstDepartamento, nombreDepartamento);
                if (departamento == null) {
                    throw new Exception("Invalido");
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.DEPARTAMENTO_MSG,
                    "Debe ser un departamento valido"));
            departamento = null;
        }
        Municipio municipio = null;
        List<Municipio> lstMunicipio = null;
        try {
            lstMunicipio = ((List<Municipio>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO));
            if (hayDepartamento && line.get(ArchivoCampoNovedadConstante.MUNICIPIO) != null) {
                String nombreMunicipio = ((String) line.get(ArchivoCampoNovedadConstante.MUNICIPIO)).toUpperCase();
                municipio = GetValueUtil.getMunicipioNombre(lstMunicipio, nombreMunicipio, departamento.getIdDepartamento());
                if (municipio == null) {
                    throw new Exception("Invalido");
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.MUNICIPIO_MSG,
                    " El municipio debe de pertenecer al departamento ingresado"));
            municipio = null;
        }

        if (line.get(ArchivoCampoNovedadConstante.DIRECCION_RESIDENCIA) != null) {
            // Validación de la Dirección de residencia
            String direccionResidencia = ((String) line.get(ArchivoCampoNovedadConstante.DIRECCION_RESIDENCIA)).toUpperCase();
            if (!(direccionResidencia.length() > ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES
                    && direccionResidencia.length() <= ArchivoMultipleCampoConstants.LONGITUD_100_CARACTERES)) {
                lstHallazgos
                        .add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.DIRECCION_RESIDENCIA_MSG,
                                "La longitud de la dirección de residencia no está entre "
                                        + ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES + " y "
                                        + ArchivoMultipleCampoConstants.LONGITUD_100_CARACTERES + " caracteres."));
            }
        }
        String telefonoFijo = null;
        if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TELEFONO_FIJO) != null) {
            // Teléfono fijo
            telefonoFijo = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TELEFONO_FIJO);
            validarRegex(arguments, ArchivoCampoNovedadConstante.TELEFONO_FIJO, ExpresionesRegularesConstants.TELEFONO_FIJO,
                    "Número de teléfono fijo invalido", ArchivoCampoNovedadConstante.TELEFONO_FIJO_MSG);
        }
        String telefonoCelular = null;
        if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TELEFONO_CELULAR) != null) {
            // Teléfono celular
            telefonoCelular = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TELEFONO_CELULAR);
            validarRegex(arguments, ArchivoCampoNovedadConstante.TELEFONO_CELULAR, ExpresionesRegularesConstants.TELEFONO_CELULAR,
                    "Número de teléfono celular invalido", ArchivoCampoNovedadConstante.TELEFONO_CELULAR_MSG);

        }
        if ((telefonoFijo == null || telefonoFijo.equals("")) && (telefonoCelular == null || telefonoCelular.equals(""))) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TELEFONO_CELULAR_MSG,
                    "Se debe de llenar mínimo un numero de teléfono fijo o celular"));
        }
        // Correo electronico
        verificarCorreoElectronico(arguments);
        // Validacion de Reside en sector rural
        validarRegex(arguments, ArchivoCampoNovedadConstante.RESIDE_SECTOR_RURAL,
                ExpresionesRegularesConstants.SI_NO_IGNORE_CASE, "Valor perteneciente a reside en sector rural invalido, debe ser "
                        + ArchivoMultipleCampoConstants.SI + " o " + ArchivoMultipleCampoConstants.NO,
                ArchivoCampoNovedadConstante.RESIDE_SECTOR_RURAL_MSG);
        // Condición casa propia
        validarRegex(arguments, ArchivoCampoNovedadConstante.CONDICION_CASA_PROPIA,
                ExpresionesRegularesConstants.SI_NO_IGNORE_CASE, "Valor perteneciente Condición casa propia invalido, debe ser "
                        + ArchivoMultipleCampoConstants.SI + " o " + ArchivoMultipleCampoConstants.NO,
                ArchivoCampoNovedadConstante.CONDICION_CASA_PROPIA);

        // Codigo Postal
        validarRegex(arguments, ArchivoCampoNovedadConstante.CODIGO_POSTAL, ExpresionesRegularesConstants.CODIGO_POSTAL,
                "Valor perteneciente Código postal. Invalido debe de estar en entre el rango de valores permitidos:"
                        + " Mínimo: 080000 y Máximo: 999999",
                ArchivoCampoNovedadConstante.CONDICION_CASA_PROPIA_MSG);
    }

    /**
     * Método que crea un hallazgo según la información ingresada
     * 
     * @param lineNumber
     * @param campo
     * @param errorMessage
     * @return retorna el resultado hallazgo dto
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
        return hallazgo;
    }

    /**
     * Validador de campo aplicando una expresión regular.
     * 
     * @param arguments
     * @param campoVal
     * @param regex
     * @param mensaje
     */
    private void validarRegex(LineArgumentDTO arguments, String campoVal, String regex, String mensaje, String campoMSG) {
        try {
            ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
            String valorCampo = "";
            if ((String) (arguments.getLineValues()).get(campoVal) != null && (campoVal.equals(ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE)
                    || campoVal.equals(ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO)
                    || campoVal.equals(ArchivoCampoNovedadConstante.CODIGO_POSTAL)
                    || campoVal.equals(ArchivoCampoNovedadConstante.CONDICION_CASA_PROPIA)
                    || campoVal.equals(ArchivoCampoNovedadConstante.RESIDE_SECTOR_RURAL))) {
                String campoValidar = ((String) (arguments.getLineValues()).get(campoVal)).trim();
                if (campoVal.equals(ArchivoCampoNovedadConstante.PRIMER_NOMBRE)
                        || campoVal.equals(ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE)
                        || campoVal.equals(ArchivoCampoNovedadConstante.PRIMER_APELLIDO)
                        || campoVal.equals(ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO)) {
                    valorCampo = campoValidar.toLowerCase();
                }
                else {
                    if (campoVal.equals(ArchivoCampoNovedadConstante.RESIDE_SECTOR_RURAL)
                            || campoVal.equals(ArchivoCampoNovedadConstante.CONDICION_CASA_PROPIA)) {
                        valorCampo = campoValidar.toUpperCase();
                    }
                    else {
                        valorCampo = campoValidar;
                    }
                } 
                if (valorCampo != null && !(valorCampo.matches(regex))) {
                    hallazgo = crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje);

                }
                if (hallazgo != null) {
                    lstHallazgos.add(hallazgo);
                    hallazgo = null;
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, " Valor perteneciente " + campoMSG + " invalido"));
        }
    }

    /**
     * Metodo encargado de verificar el correo electronico
     * 
     * @param arguments
     * @param 
     */
    private void verificarCorreoElectronico(LineArgumentDTO arguments) {
        if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.CORREO_ELECTRONICO) != null) {
            String correoElectronico = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.CORREO_ELECTRONICO);
            if (!(correoElectronico.length() >= ArchivoMultipleCampoConstants.LONGITUD_8_CARACTERES
                    && correoElectronico.length() <= ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES)) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.CORREO_ELECTRONICO_MSG,
                        "Mínimo " + ArchivoMultipleCampoConstants.LONGITUD_8_CARACTERES + " y máximo "
                                + ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES
                                + "caracteres permitidos en el correo electrónico "));
            }
            else {
                validarRegex(arguments, ArchivoCampoNovedadConstante.CORREO_ELECTRONICO,
                        ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL, "Correo electrónico invalido",
                        ArchivoCampoNovedadConstante.CORREO_ELECTRONICO_MSG);
            }
        }
    }

    /**
     * Metodo encargado de verifiar el salario mensual
     * 
     * @param arguments
     * @param line
     */
    private void verificarSalarioMensual(LineArgumentDTO arguments) {
        try {
            if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL) != null) {
                BigDecimal salarioMensual = new BigDecimal(
                        (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL).toString());
                if ((salarioMensual.toString().length() > ArchivoMultipleCampoConstants.LONGITUD_9_CARACTERES)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG,
                            "Valor salario mensual debe de tener una longitud menor igual a "
                                    + ArchivoMultipleCampoConstants.LONGITUD_9_CARACTERES + " caracteres"));
                }
                else {
                    if (!(salarioMensual.longValue() >= ArchivoMultipleCampoConstants.VALOR_MINIMO_SALARIO
                            && salarioMensual.longValue() <= ArchivoMultipleCampoConstants.VALOR_MAXIMO_SALARIO)) {
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG,
                                "El valor del salario no esta entre " + ArchivoMultipleCampoConstants.VALOR_MINIMO_SALARIO + " y "
                                        + ArchivoMultipleCampoConstants.VALOR_MAXIMO_SALARIO));
                    }
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG,
                    " Valor perteneciente " + ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG
                            + " invalido. Únicamente debe contener caracteres numéricos"));
        }
    }

    /**
     * Metodo encargado de verificar la Fecha inicio de labores con el empleador
     * 
     * @param arguments
     * @param line,
     *        linea a la cual se le realiza la validacion
     * @param fechaNacimiento,
     *        fecha de nacimiento
     * @throws ParseException
     */
    private void verificacionFechaInicioLabEmpleador(LineArgumentDTO arguments, Map<String, Object> line, Calendar sistema) {
        try {
            if (line.get(ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR) != null) {
                String strFInicioLabores = line.get(ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR).toString();

                Date fInicioLabores = new Date(CalendarUtils.convertirFechaDate(strFInicioLabores));
                if (!CalendarUtils.esFechaMayor(fInicioLabores, cInicioLabores.getTime())) {
                    lstHallazgos.add(
                            crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
                                    "La fecha de inicio de labores con empleador no es mayor a 1965"));
                }
                if (CalendarUtils.esFechaMayor(fInicioLabores, sistema.getTime())) {
                    lstHallazgos.add(
                            crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
                                    "La fecha de inicio de labores con empleador es mayor a la fecha actual"));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
                    " Digite la " + ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG
                            + " en formato día - mes - año. Ejemplo: 02-10-1990"));
        }
    }

    /**
     * Metodo encargado de verificar el numero de documento respecto a tipo de
     * de documento
     * 
     * @param tipoIdentificacion
     *        tipo de identificación
     * @param arguments,
     *        argumentos
     */
    public void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, LineArgumentDTO arguments) {
        if (tipoIdentificacion != null) {
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {
                validarRegex(arguments, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                        "La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.",
                        ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_MSG);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
                validarRegex(arguments, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION,
                        ExpresionesRegularesConstants.CEDULA_EXTRANJERIA, "La cédula de extranjería debe tener máximo 16 caracteres.",
                        ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_MSG);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD)) {
                validarRegex(arguments, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION, ExpresionesRegularesConstants.TARJETA_IDENTIDAD,
                        "La tarjeta de identidad solo puede ser de 10 u 11 dígitos.",
                        ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_MSG);
                return;
            }
        }
        else {
            validarRegex(arguments, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                    "El número de identificación debe de tener un valor valido", ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_MSG);

        }
    }
}