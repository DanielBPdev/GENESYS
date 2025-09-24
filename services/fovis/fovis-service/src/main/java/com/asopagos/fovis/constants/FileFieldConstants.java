package com.asopagos.fovis.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class FileFieldConstants {

    /*
     * Constantes Mensajes de campo de archivo
     */
    public static final String MSG_NRO_CEDULA = "Nro Cedula";
    public static final String MSG_NIT_ENTIDAD = "NIT ENTIDAD";
    public static final String MSG_NOMBRE_ENTIDAD = "NOMBRE ENTIDAD";
    public static final String MSG_ENTIDAD = "ENTIDAD";
    public static final String MSG_IDENTIFICACION = "IDENTIFICACIÓN";
    public static final String MSG_IDENTIFICACION_SIN_TILDE = "IDENTIFICACION";
    public static final String MSG_APELLIDOS = "APELLIDOS";
    public static final String MSG_NOMBRES = "NOMBRES";
    public static final String MSG_CEDULA_CATASTRAL = "CEDULA CATASTRAL";
    public static final String MSG_DIRECCION_INMUEBLE = "DIRECCIÓN INMUEBLE";
    public static final String MSG_DIRECCION = "DIRECCIÓN";
    public static final String MSG_DIRECCION_SIN_TILDE = "DIRECCION";
    public static final String MSG_MATRICULA_INMOBILIARIA = "MATRICULA INMOBILIARIA";
    public static final String MSG_DPTO = "DPTO.";
    public static final String MSG_MPIO = "MPIO.";
    public static final String MSG_MPIO_SIN_PUNTO = "MPIO";
    public static final String MSG_APELLIDOS_NOMBRES = "APELLIDOS Y NOMBRES";
    public static final String MSG_DEPARTAMENTO = "DEPARTAMENTO";
    public static final String MSG_MUNICIPIO = "MUNICIPIO";
    public static final String MSG_FECHA_SOLICITUD = "FECHA DE SOLICITUD";
    public static final String MSG_ENTIDAD_OTORGANTE = "ENTIDAD OTORGANTE";
    public static final String MSG_CAJA_COMPESACION = "CAJA DE COMPESACION";
    public static final String MSG_ASIGNADO_POSTERIOR_REPORTE = "ASIGNADO POSTERIOR A REPORTE";
    public static final String MSG_TIPO_INFORMACION = "TIPO DE INFORMACION";
    public static final String MSG_FECHA_CORTE = "FECHA DE CORTE";
    public static final String MSG_FECHA_ACTUALIZACION = "FECHA DE ACTUALIZACION";
    public static final String MSG_PUNTAJE = "PUNTAJE";
    public static final String MSG_SEXO = "SEXO";
    public static final String MSG_ZONA = "ZONA";
    public static final String MSG_PARENTESCO = "PARENTESCO";
    public static final String MSG_FOLIO = "FOLIO";
    public static final String MSG_DOCUMENTO = "DOCUMENTO";
    public static final String MSG_TIPO_DOCUMENTO = "TIPO DOCUMENTO";

    /*
     * Constantes para los campos
     */
    public static final String NRO_CEDULA = "nroCedula";
    public static final String NIT_ENTIDAD = "nitEntidad";
    public static final String NOMBRE_ENTIDAD = "nombreEntidad";
    public static final String ENTIDAD = "entidad";
    public static final String IDENTIFICACION = "identificacion";
    public static final String APELLIDOS = "apellidos";
    public static final String NOMBRES = "nombres";
    public static final String CEDULA_CATASTRAL = "cedulaCatastral";
    public static final String DIRECCION_INMUEBLE = "direccionInmueble";
    public static final String MATRICULA_INMOBILIARIA = "matriculaInmobiliaria";
    public static final String DEPARTAMENTO = "departamento";
    public static final String MUNICIPIO = "municipio";
    public static final String APELLIDOS_NOMBRES = "apellidosNombres";
    public static final String FECHA_SOLICITUD = "fechaSolicitud";
    public static final String DIRECCION = "direccion";
    public static final String ENTIDAD_OTORGANTE = "entidadOtorgante";
    public static final String CAJA_COMPESACION = "cajaCompensacion";
    public static final String ASIGNADO_POSTERIOR_REPORTE = "asignadoPosteriorReporte";
    public static final String TIPO_INFORMACION = "tipoInformacion";
    public static final String FECHA_CORTE = "fechaCorte";
    public static final String FECHA_ACTUALIZACION = "fechaActualizacion";
    public static final String PUNTAJE = "puntaje";
    public static final String SEXO = "sexo";
    public static final String ZONA = "zona";
    public static final String PARENTESCO = "parentesco";
    public static final String FOLIO = "folio";
    public static final String DOCUMENTO = "documento";
    public static final String TIPO_DOCUMENTO = "tipoDocumento";

    /**
     * Indica el valor por defecto para el campo Nit Entidad en las hojas del archivo
     */
    public static final String VALUE_DEFAULT_NIT_ENTIDAD = "!!!NINGUNA DE LAS CEDULAS RELACIONADAS TIENE CRUCE CON ESTA ENTIDAD!!!";
    
    
    public static final String MENSAJE_ERROR_ENCABEZADO= "El nombre del encabezado no es valido";
    
    public static final String MENSAJE_ERROR_FILE_PROCESSING = "Se presento una excepción procesando la hoja del archivo";
    
    public static final String MENSAJE_ERROR_DATABASE = "Se presento una excepción guardando los datos de la hoja del archivo";
    
    private FileFieldConstants() {

    }
}
