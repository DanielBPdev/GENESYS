package com.asopagos.entidaddescuento.constants;


/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de las
 * NamedQueries del servicio de entidades de descuento<br>
 * <b>Módulo:</b> EntidadDescuentoService HU-311-440<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co">Roy López Cardona</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
public class NamedQueriesConstants {

    /* ----------------------------------------- INICIO CONTANTES CONSULTAS MODELO CORE ------------------------------------- */

    //HU-311-440 Consulta todas las entidades de descuento y se devuelven en forma de DTO
    public static final String CONSULTAR_ENTIDADES_DESCUENTO_DTO_EXTERNA ="EntidadDescuento.buscarTodas.DTO.EXTERNA";

    //HU 311-440 Constante para la consulta que permite obtener una entidad de descuento por su identificador
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_ID = "EntidadDescuento.consultar.entidadPorId";

    //Consulta que se trae las entidades (internas) que no tengan relación con una empresa
    public static final String CONSULTAR_ENTIDADES_DESCUENTO_DTO_INTERNA="EntidadDescuento.buscarTodas.DTO.INTERNA";

    //Consulta por codigo y nombre que devuelve un DTO de la entidad de descuento tipo externa con sus datos y los de la empresa
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_NOMBRE_EXTERNA = "EntidadDescuento.buscar.DTO.codigo.nombre.EXTERNA";

    // Consulta por codigo y nombre que devuelve un DTO de la entidad de descuento tipo interna con sus datos.
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_NOMBRE_INTERNA = "EntidadDescuento.buscar.DTO.codigo.nombre.INTERNA";

    //Consulta por código que devuelve un DTO de la entidad de descuento EXTERNA con sus datos y los de la empresa
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_EXTERNA= "EntidadDescuento.buscar.DTO.codigo.EXTERNA";

    //Consulta por código que devuelve un DTO de la entidad de descuento tipo INTERNA
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_INTERNA="EntidadDescuento.buscar.DTO.codigo.INTERNA";

    //Consulta por nombre que devuelve un DTO de la entidad de descuento externa con sus datos y los de la empresa
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_NOMBRE_EXTERNA= "EntidadDescuento.buscar.DTO.nombre.EXTERNA";

    //Connsulta por nombre que devuelve un DTO de la entidad de descuento interna con sus datos.
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_NOMBRE_INTERNA = "EntidadDescuento.buscar.DTO.nombre.INTERNA";

    // HU-311-440 Consulta que devuelve las prioridades asignadas a las entidades de descuento existentes.
    public static final String CONSULTAR_PRIORIDADES_ASIGNADAS = "EntidadDescuento.buscar.prioridades";

    //Consulta que devuelve el identificador de la entida de descuento si encuentra la prioridad registrada.
    public static final String CONSULTAR_PRIORIDAD = "EntidadDescuento.buscar.prioridad";

    // Consulta auxiliar que trae la persona que tiene asociación al id de una empresa.
    public static final String CONSULTAR_PERSONA_POR_IDEMPRESA = "EntidadDescuento.buscar.persona.idEmpresa";

    // Consulta que devuelve el código mayor que se haya registrado en la entidad de descuento
    public static final String CONSULTAR_CODIGO_VALOR_MAYOR="EntidadDescuento.buscar.codigo.mayor";

    // HU-311-440  Consulta que busca una entidad de descuento a partir del id
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_POR_ID="EntidadDescuento.buscar.id";

    //Consulta que devuelve una entidad de descuento tipo externa si el id de la empresa coincide
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_EXTERNA_POR_ID_EMPRESA="EntidadDescuento.buscar.idEmpresa";

    //Consulta que devuelve una entidad de descuento tipo interna si el nombre coincide
    public static final String CONSULTAR_ENTIDAD_DESCUENTO_INTERNA_POR_NOMBRE = "EntidadDescuento.buscar.nombre.INTERNA";

    //Consulta que devuelve la entidad correspondiente al archivo de descuento
    public static final String CONSULTAR_ARCHIVO_ENTIDAD_DESCUENTO_SUBSIDIO_PIGNORADO_POR_ID = "ArchivoEntidadDescuentoSubsidioPignorado.buscar.id";

    //Consulta que devuelve la entidad correspondiente a la trazabilidad de un archivo de descuento
    public static final String CONSULTAR_ARCHIVO_ENTIDAD_DESCUENTO_ESTADO_CARGADO_POR_CODENTIDAD = "ArchivoEntidadDescuentoSubsidioPignorado.buscar.codigoEntidadDescuento";

    //Consulta que devuelve la lista de entidades correspondientes a la trazabilidad de un archivo con estado cargado para las entidades de descuento de convenio activo
    public static final String CONSULTAR_ARCHIVO_ENTIDADES_DESCUENTO_ESTADO_CARGADO_PROCESADO = "ArchivoEntidadDescuentoSubsidioPignorado.estadoCargadoProcesado.buscarTodas";

    //Consultar que devuelve la lista de registros asociados a la trazabilidad de un archivo de descuentos
    public static final String CONSULTAR_REGISTROS_ID_TRAZABILIDAD = "SubsidioMonetarioValorPignorado.buscar.idTrazabilidad";

    //Consulta que devuelve la entidad correspondiente a la trazabilidad de un archivo de descuento dado su identificador
    public static final String CONSULTAR_ARCHIVO_ENTIDAD_DESCUENTO_ID = "ArchivoEntidadDescuentoSubsidioPignorado.buscar.idTrazabilidad";

    //Consulta que permite eliminar la información de trazabilidad para los registros asociados a un archivo de descuento en caso de que se presente un error con Lion
    public static final String ELIMINAR_REGISTROS_ARCHIVO_DESCUENTOS = "ArchivoEntidadDescuentoSubsidioPignorado.eliminar.registrosArchivoDescuentos";

    //Consulta que permite obtener las entidades de descuento con estado activas
    public static final String CONSULTAR_ENTIDADES_DESCUENTO_ACTIVAS = "EntidadDescuento.buscar.estado.ACTIVA";

    //Consulta que permite obtener la lista de identificadores de entidad descuento dado un número de radicación relacionado a una solicitud de liquidación
    public static final String CONSULTAR_ENTIDADESDESCUENTO_NUMERO_RADICACION = "EntidadDescuento.buscar.subsidioMonetarioValorPignorado";

    //Constante para la consulta que permite actualizar los registros de valores a pignorar relacionados con los archivos de descuento de una liquidación cancelada
    public static final String ACTUALIZAR_VALORES_PIGNORAR_LIQUIDACION_CANCELADA = "EntidadDescuento.actualizar.valoresPignorar.liquidacionCancelada";

    //Constante para la consulta que permite actualizar los registros de valores a pignorar relacionados con los archivos de descuento de una liquidación cancelada
    public static final String ELIMINAR_REZAGOS_PIGNORAR_LIQUIDACION_CANCELADA = "EntidadDescuento.eliminar.rezagosPignorar.liquidacionCancelada";

    //Constante para la consulta que permite saber si existen saldos asociados a otras liquidaciones.
    public static final String ACTUALIZAR_SALDOS_PENDIENTE_DIFERENTE_LIQUIDACION = "EntidadDescuento.actualizar.saldosDiferenteLiquidacion";

    //Constante para la consulta que permite actualizar archivos de descuento de liquidaciones canceladas.
    public static final String ACTUALIZAR_ARCHIVO_DESCUENTO_LIQUIDACION_CANCELADA = "EntidadDescuento.actualizar.archivosDescuento.liquidacionCancelada";

    //Constante para la consulta que permite consultar el medio de pago asociado al grupo y administrador de subsidio
    public static final String CONSULTAR_MEDIOPAGO_ADMINSUBSIDIOGRUPO = "EntidadDescuento.consultarMedioPago.grupoAdminSubsidio";

    //Constante para la consulta que permite consultar el medio de pago asociado al grupo y administrador de subsidio
    public static final String CONSULTAR_ARCHIVO_SALIDA_DESCUENTO_ENTIDAD_SOLICITUD = "EntidadDescuento.consultarArchivoSalida.entidadSolicitud";

    //Consulta que ejecuta el SP ACTUALIZAR_DESCUENTOS_NUEVO_ARCHIVO para actualizar los archivos sin descuentos pendientes
    public static final String PROCEDURE_ACTUALIZAR_DESCUENTOS_NUEVO_ARCHIVO = "ACTUALIZAR_DESCUENTOS_NUEVO_ARCHIVO";

    public static final String CONSULTAR_ARCHIVO_ENTIDAD_DESCUENTO_SUBSIDIO_PIGNORADO_POR_NOMBRE = "ArchivoEntidadDescuentoSubsidioPignorado.buscar.nombre";

    /**
     * Constante encargada de consultar los nomrbes de los campos parametrizados
     */
    public static final String BUSCAR_CAMPOS_ARCHIVO = "novedades.buscar.file.nombreCampos";

}
