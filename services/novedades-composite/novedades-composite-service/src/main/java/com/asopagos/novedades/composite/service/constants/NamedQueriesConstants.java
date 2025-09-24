package com.asopagos.novedades.composite.service.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio de novedades composite
 * <b>Historia de Usuario:</b>Proceso: 1.3Novedades
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
    /**
     * Constante para consultar Solicitud por id
     */
    public static final String BUSCAR_MEDIO_PAGO_PERSONA_ID = "consultar.medio.pago.persona.id";
   
    public static final String BUSCAR_GRUPO_FAMILIAR = "consultar.grupo.familiar";
    
    public static final String NOVEDADES_COMPOSITE_CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL = "novedades.composite.consultar.solicitud.por.idSolicitudGlobal";

    public static final String CONSULTAR_EXISTE_ANALISIS_NOVEDADES_FOVIS = "consultar.si.existe.analisis.novedad.fovis";
    
    public static final String CONSULTAR_FECHA_ULTIMO_RETIRO_POR_SOLICITUD = "consultar.fecha.ultimo.retiro.por.rolAfiliado";

    public static final String CONSULTAR_FECHA_ULTIMO_RETIRO_POR_HISTORICO_ROLAFILIADO = "consultar.fecha.retiro.por.idrolAfiliado.hitoricoRolAfiliado";

    public static final String CONSULTAR_FECHA_RETIRO_ROL_AFILIDO_REINTEGROS = "consultar.fecha.retiro.por.idrolAfiliado";

    public static final String CONSULTAR_DATOS_EMPLEADOR_Y_SUCURSAL = "consultar.datos.empleador.y.sucursal";
    public static final String CONSULTAR_DATOS_EMPLEADOR_SOLO = "consultar.datos.empleador.solo";

    public static final String  STORED_PROCEDURE_INSERTAR_SOLICITUD_ANALISIS_NOVEDAD_FOVIS = "stored.procedure.novedades.composite.insertar.analisis.postulacion.fovis";

    /**
     * Buscar persona por tipo y numero identificacion
     */
    public static final String BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION = "Novedad.buscar.persona";

    public static final String BUSCAR_SOLICITUD_NOVEDAD_PERSONA = "Buscar.Solicitud.Novedad.Persona";
    public static final String BUSCAR_SOLICITUD_NOVEDAD_PILA = "Buscar.Solicitud.Novedad.Pila";
    
    public static final String VALIDACION_NAME_ARCHIVO_CONSOLA = "consultar.exist.archivo.consola";
	
    public static final String CONSULTAR_INFO_ADMIN_SUBSIDIO = "consultar.info.admin.subsidio";
     /**
     * PARAMETRO que permite validar si la caja autoriza el registro de la novedad de cambio de medio de pago
     */
    public static final String PARAMETRO_CONTROL_EJECUCION_ABONOS_AUTOMATICOS = "consultar.parametro.ejecucion.bonos.automaticos";

    public static final String SITIO_PAGO_CONFIRMACIÓN_CARGUE_MASIVO = "consultar.parametro.consultar.sitio.pago";

    public static final String CONSULTAR_EMEPLADOR_POR_ROL_AFILIADO = "consultar.empleador.por.rol.afiliado";

    public static final String CONSULTAR_ITEM_CHEQUEO_PERSONA = "consultar.item.chequeo.persona";

    public static final String CONSULTAR_SOLICITUD_ITEM_CHEQUEO = "consultar.solicitud.item.chequeo";

    public static final String CONSULTAR_SOLICITUD_ITEM_CHEQUEO_BENEFICIARIO = "consultar.solicitud.item.chequeo.beneficiario";

    public static final String STORED_PROCEDURE_ACTUALIZAR_NOVEDADES_REINTEGRO_BENEFICIARIOS_AFILIADOS_PILA=  "StoredProcedures.actualizar.reintegros.beneficiario.afiliado.novedades.pila";

    public static final String CONSULTAR_CANTIDAD_BENEFICIARIOS_AFILIADO = "consultar.cantidad.beneficiarios.afiliado";

    public static final String INSERCION_LOG_MONITOREO_NOVEDADES = "novedades.composite.monitoreo.novedades";

    public static final String CONSULTAR_BANCO_CODIGOPILA = "consultar.idBanco.por.codigoPila";
    
    public static final String CONSULTAR_SOLICITUD_AFILIACION = "consultar.solicitud.afiliacion";

    public static final String CONSULTAR_NOMBRE_PERSONA_CAMBIOMASIVO_TRASFERENCIA = "consultar.persona.cambioMasivo.transferencia";

    public static final String CONSULTAR_ARCHIVO_MASIVO_CERTIFICADOS = "consultar.archivo.masivo.certificados";

    public static final String CONSULTAR_ULTIMO_ARCHIVO_MASIVO_CERTIICADOS = "consultar.ultimo.archivo.certificados.masivos";

    public static final String CONSULTAR_SOLICITUD_NOVEDAD_PILA = "consultar.solicitud.novedad.pila";
}

