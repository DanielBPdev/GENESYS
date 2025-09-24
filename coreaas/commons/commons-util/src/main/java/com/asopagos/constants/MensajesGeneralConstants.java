package com.asopagos.constants;

/**
 * <b>Descripción:</b> Clase que define constantes para los mensajes de 
 * internacionalización en los servicios
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class MensajesGeneralConstants {
    
    
    public static final String ERROR_EJECUCION_PROCEDIMIENTO_JOBS = " Error de ejecucion del jobs actual.";
    /**
     * Servicio de listas
     */
    public static final String ERROR_PARAMETROS_INCOMPLETOS = "Parámetros incompletos";    
    
    /**
     * Mensaje transversal para consultas sin resultado
     */
    public static final String ERROR_RECURSO_NO_ENCONTRADO = "No existen resultados para el recurso solicitado";
    
    /**
     * Mensaje transversal para consultas que esperan un resultado pero existe más de uno
     */
    public static final String ERROR_MAS_DE_UN_UNICO_RECURSO = "Existe más de un recurso para la búsqueda única solicitada";
    
    
    /**
     * Mensaje transversal para errores de registros ya existentes
     */
    public static final String ERROR_RECURSO_YA_ESTA_REGISTRADO = "El recurso que desea agregar ya se encuentra registrado en el sistema";
    
    /**
     * Mensaje transversal para errores de parámetros incompletos
     */    
    public static final String ERROR_RECURSO_INCOMPLETO = "La solicitud tiene parametros incompleto";

    
    /**
     * Servicio de tareas humanas
     */
    public static final String ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE = "Error grave. La ejecución del proceso no se encuentra en un estado consistente";
    
    /**
     * Servicio de listas
     */
    public static final String ERROR_NOMBRE_O_VALOR_FILTRO_INVALIDO = "El nombre o el valor proporcionado para el filtro es inválido";
    
    /**
     * Servicio de listas
     */
    public static final String ERROR_CONFIGURACION_LISTA_INVALIDA = "Existen errores en la configuración de la lista/enumeracíon";


    /**
     * Mensaje transversal para errores de actualización de registros
     */
    public static final String ERROR_ACTUALIZAR_RECURSO = "Error en el momento de actualizar el recurso";

    /**
     * Mensaje transversal para errores de actualización de registros
     */
    public static final String ERROR_ELEMENTO_DE_LISTA_NULO = "El elemento de la lista no puede ser nulo";

    /**
     * Mensaje para actualización de rol de contacto de un empleador
     */
    public static final String ERROR_TIPO_ROL_CONTACTO_NO_COINCIDE = "No es posible actualizar el tipo de rol para el contacto recibido";

    /**
     * Mensaje transversal para errores de eliminación de requisitos
     */
    public static final String ERROR_ELIMINANDO_REQUISITO = "No es posible eliminar el requisito porque está siendo usado desde otros procesos";
    
    /**
     * Mensaje transversal para errores de eliminación de tipos de solicitante
     */    
    public static final String ERROR_ELIMINANDO_TIPO_SOLICITANTE = "No es posible eliminar el tipo de solictante porque está siendo usado desde otros procesos";
    
    /**
     * Mensaje para Proyecto Solucion Vivienda con matrícula Inmobiliaria repetido
     */    
    public static final String ERROR_CREAR_PROYECTO_MATRICULA_INMOBILIARIA = "MATRICULA_DUPLICADA:${0}";
    
    /**
     * Mensaje transversal para errores de parámetros incompletos
     */    
    public static final String ERROR_RECURSO_INCORRECTO = "La solicitud tiene parametros incorrectos";
    
    /**
     * Mensaje para el error que valida la igualdad de campos configurados en script Vs cantidad registros VariableComunicado 
     */    
    public static final String ERROR_CONFIGURACION_CAMPOS_SCRIPT = "La cantidad de campos configurados en el script consultado no es igual a la cantidad de registros consultados en VariableComunicado";
    
    /**
     * Mensaje para el error técnico inesperado 
     */    
    public static final String ERROR_TECNICO_INESPERADO = "Error técnico inesperado";

    /**
     * Mensaje para el error técnico inesperado
     */
    public static final String ERROR_USUARIO_CONTRASENNA = "Usuario y/o contraseña son incorrectos";

    /**
     * Mensaje para el error técnico inesperado
     */
    public static final String ERROR_VALIDACION_CAMPOS = "Por favor revisar,  faltan datos por ingresar.";

    /**
     * Mensaje para el error técnico inesperado
     */
    public static final String ERROR_TIPO_IDENTIFICACION = "Tipo de identificación no encontrados en el sistema";

    /**
     * Mensaje para el error técnico inesperado
     */
    public static final String ERROR_ADMINISTRADOR_SUBSIDIO = "Administrador de subsidio no existe";

    /**
     * Mensaje para el error técnico inesperado
     */
    public static final String ERROR_CEDULA_CIUDADANIA = "El valor de la CC debe ser Numerico y la longitud no puede ser mayor de 10 dígitos";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_NIT = "El valor del NIT debe ser Numerico y La longitud no puede ser mayor de 11 dígitos";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_PASAPORTE = "La longitud del Pasaporte no puede ser mayor de 16 caracteres";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_REGISTRO_CIVIL = "El registro civil debe tener 8, 10 u 11 caracteres.";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_TARJETA_IDENTIDAD = "El valor de la Tarjeta de Identidad debe ser Numerico y la longitud tarjeta de identidad solo puede ser de  10 u 11 dígitos.";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_CEDULA_EXTRANJERIA = "La longitud de la cedula de extranjeria debe tener máximo 16 caracteres";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_CARNE_DIPLOMATICO = "El carné diplomático debe tener máximo 15 caracteres.";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_SALVOCONDUCTO = "La longitud del Salvo conducto de permanencia  no puede ser mayor de 16 caracteres alfanumericos";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_PERM_ESP_PERMANENCIA = "La longitud del Permiso especial de permanencia debe ser de 15 dígitos";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_PERM_PROT_TEMPORAL = "La longitud del Permiso por protección temporal no puede ser mayor a 8 dígitos";

    /**
     * Mensaje para el error error nit
     */
    public static final String VALOR_SOLICITADO_MAYOR = "El valor solicitado es mayor al valor parametrizado como valor máximo";


    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_VALOR_ENTREGADO_VALOR_SOLICITADO = "El valor solicitado es diferente al registrado en la transacción de retiro";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_IDENTIFICADOR_TERCERO_PAGADOR = "El Identificador de transacción tercero pagador no coincide con el que fue ingresado al solicitar el retiro";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_USUARIO_CONVENIO = "El “usuario  asignado al convenio” no existe.";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_USUARIO_CONVENIO_INACTIVO = "El convenio tercero pagador esta inactivo.";

    /**
     * Mensaje para el error error nit
     */
    public static final String VALOR_SOLICITADO_MENOR = "El valor solicitado es menor al valor parametrizado como valor mínimo";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_VALOR_SOLICITADO_SALDO_SUBSIDIO = "El valor solicitado supera el saldo que actualmente tiene el administrador";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_TERCERO_PAGADOR = "Identificador de transacción no tiene la longitud especificada";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_PUNTO_COBRO = "El valor punto de cobro debe ser un valor alfanumerico de maximo 20 caracteres";

    /**
     * Mensaje para el error error nit
     */
    public static final String ERROR_VALOR_ENTREGADO_VALOR_SOLICITADO_VALIDACION = "El valor entregado es mayor al valor solicitado";

    /**
     * Mensaje para el error creacion de PDF 
     */    
    public static final String ERROR_CREACION_PDF = "Error al crear el PDF";
    
    /**
     * Solicitud de afiliación persona dependiente, con carga múltiple
     */
    public static final String ERROR_CONFIGURACION_CARGA_MULTIPLE = "Existen errores en la configuración para la carga múltiple";
	/**
     * Mensaje de error que indica que no se ha proporcionado un token
     */
    public static final String TOKEN_NO_PROPORCIONADO="No se ha proporcionado un token de acceso";
    
	/**
     * Mensaje de error que indica que no se ha proporcionado un token
     */
    public static final String ERROR_EXPORTANDO_ARCHIVO="Error al comprimir el archivo";
    
    /**
     * Mensaje de error que indica que el token proporcionado es invalido
     */
    public static final String TOKEN_INVALIDO="El token de autenticación proporcionado es invalido";
    
    /**
     * Mensaje de error que indica que el token proporcionado es invalido
     */
    public static final String GRUPO_NO_EXISTE="El grupo {0} no existe";

	public static final String ERROR_USUARIO_NO_EXISTE = "El usuario:{0} no se encuentra registrado en el sistema.";
	
	public static final String ERROR_USUARIO_NO_DISPONIBLE_PARA_ASIGNACION = "No existen usuarios disponibles para asignar "+
			 "tareas en el proceso: {0} con el método de asignacion: {1}";
	
	/**
	 * Error al convertir un objeto a un json o un json a un objeto
	 */
	public static final String ERROR_CONVERTIR_JSON = "Error al convertir un objeto a un json o viceversa";
	 
    public static final String ERROR_DATAS_CONFLICT = "El servicio determinó que no puede continuar con su ejecución debido a inexistencia de registros en la base de datos";
	
	/**
     * Errores al invocar los clientes rest
     */
    public static final String ERROR_HTTP_BAD_REQUEST = "La solicitud al servicio ha fallado pues no se ha cumplido con consistencia de los parámetros necesaria para su ejecución";
    public static final String ERROR_HTTP_UNAUTHORIZED = "La solicitud al servicio ha fallado pues no cuenta con la autorización para accederlo";
    public static final String ERROR_HTTP_NOT_FOUND = "No se ha encontrado del servicio";
    public static final String ERROR_HTTP_CONFLICT = "El servicio determinó que no puede continuar con su ejecución debido a inconsitencias en la información suministrada para su ejecución";
    public static final String ERROR_HTTP_INTERNAL_SERVER_ERROR = "La solicitud al servicio ha fallado posible error técnico inesperado";
    public static final String ERROR_EJECUCION_PROCEDIMIENTO_ALMACENADO = "Ocurrio un error ejecutando procedimiento almacenado";


}