package com.asopagos.aportes.masivos.service.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 *
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
    /**
     * Se consulta la persona con su tipo de solicitante relacionada al aporte
     * general
     */
    public static final String CONSULTAR_PERSONA_SOLICITANTE_APORTE_GENERAL_MASIVO = "Aportes.aporte.general.consultar.persona.solicitante.masivo";

    /**
     * Se consulta la persona con su tipo de solicitante relacionada al porte
     * general y a la empresa
     */
    public static final String CONSULTAR_EMPRESA_SOLICITANTE_APORTE_GENERAL_MASIVO = "Aportes.aporte.general.consultar.persona.empresa.solicitante.masivo";


    public static final String MASIVO_PRUEBA_PILA = "Masivos.Prueba.Pila";

    public static final String CONSULTA_DATOS_PLANILLA_APORTE_MASIVO = "Aportes.Consultar.RegistroGeneral.datosPlanillaAporteMasivo";
    
    public static final String CONSULTAR_PERSONA_MASIVO_TIPO_NUMERO_IDENTIFICACION = "Aportes.masivos.persona.tipo.numero.identificacion";
    
      public static final String CONSULTAR_EMPRESA_ID_PERSONA_MASIVO = "Aportes.masivos.empresa.id.persona";
      
    /**
     * Constante que representa la consulta de registros generales por listado de IDs
     * */
    public static final String CONSULTAR_MASIVO_REGISTRO_GENERAL_POR_LISTADO_ID = "Aportes.masivos.Consultar.RegistroGeneral.listadoIds";
    
     /**
     * Consulta el aporte general relacionado a un movimiento donde su tipo de ajuste es DEVOLUCIÓN o CORRECCION_A_LA_BAJA
     */
    public static final String CONSULTAR_APORTE_MASIVOS_GENERAL_Y_MOVIMIENTO = "Consultar.movimiento.aporte.masivos.general";
    
    /**
     * Constante que representa la consulta de empresas correspondientes a un listado de ID de persona
     * */
    public static final String CONSULTAR_MASIVO_EMPRESAS_ID_PERSONA_MASIVO = "Aportes.masivos.Consultar.Empresa.buscar.idPersona.masivo";
    
        /**
     * Constante que representa la consulta de empleadores correspondientes a un listado de ID de empresa
     * */
    public static final String CONSULTAR_MASIVO_EMPRESAS_ID_MASIVO = "Aportes.masivos.Consultar.Empresa.buscar.id.masivo";
    
    
    public static final String CONSULTAR_MASIVO_ESTADO_FORMALIZACION_DATOS_PLANILLA = "Aportes.masivos.consultar.PilaEstadoTransitoriaGestion";


    public static final String CONSULTAR_SOLICITUDES_DEVOLUCION_MASIVA = "Aportes.masivos.consultar.SolicitudesDevolucion";
    
     /**
     * Constante que representa la consulta de solicitud de aporte por listado de ids de registro general
     */
    public static final String CONSULTAR_MASIVO_SOLICITUD_APORTE_LISTADO_ID_REGGEN = "Aportes.masivos.Consultar.SolicitudAporte.ListadoId.RegistroGeneral";
    
    
     /**
     * Constante que representa la consulta de índice de planilla por listado de IDs
     * */
    public static final String CONSULTAR_MASIVO_INDICE_PLANILLA_LISTA_ID = "Aportes.masivos.Consultar.IndicePlanilla.listadoIds";

    
    public static final String BORRAR_MASIVO_ARCHIVO_POR_MASIVO_ARCHIVO = "Aportes.Masivos.Borrar.MasivoArchivo.Por.MasivoArchivo";
    public static final String BORRAR_MASIVO_GENERAL_POR_MASIVO_ARCHIVO = "Aportes.Masivos.Borrar.General.Por.MasivoArchivo";
    public static final String BORRAR_MASIVO_DETALLADO_POR_MASIVO_ARCHIVO = "Aportes.Masivos.Borrar.MasivoDetallado.Por.MasivoArchivo";


    public static final String CONSULTAR_ARCHIVO_EN_PROCESAMIENTO = "Aportes.Masivos.Consultar.Archivo.En.Procesamiento";
    public static final String POPULAR_DATOS_MASIVO_ARCHIVO_APORTES = "Aportes.Masivos.Popular.Datos.Masivo.Archivo.Aportes";
    /**
     * Constante que representa la consulta de pago de subsidio para un listado de cotizantes 
     */
    public static final String CONSULTA_MASIVO_PAGO_SUBSIDIO_COTIZANTES = "Aportes.masivos.Consultar.pagoSubsidio.cotizante";
    
    
    /**
     * Constante que representa la consulta de personas por un listado de IDs de persona
     * */
    public static final String CONSULTAR_MASIVO_PERSONAS_ID_LISTA_ID = "Aportes.masivos.Consultar.Persona.buscar.listaId";
    
    /**
     * Constante que representa la consulta de personas por un listado de IDs de persona
     * */
    public static final String CONSULTAR_MASIVO_PERSONAS_ID_LISTA_ID_EMPRESA = "Aportes.masivos.Consultar.Persona.buscar.listaIdEmpresa";
    
      /* Constante para la consulta que indica que un cotizante ha generado subsidio para un período */
    public static final String HAY_MASIVO_SUBSIDIO_PARA_COTIZANTE = "USP_SM_GET_CotizanteConSubsidioPeriodo";

    public static final String ASP_VALIDACION_INICIAL = "Aportes.masivos.StoredProcedures.ASP_Execute_Validacion_Inicial";

    public static final String ASP_FINALIZAR_APORTE_MASIVO = "Aportes.masivos.StoredProcedures.ASP_Execute_pila2FaseInicial";

    public static final String CONSULTAR_MASIVO_ARCHIVO_APORTE = "Aportes.masivos.archivo";

    public static final String CONSULTAR_MASIVO_SIMULADO = "Aportes.masivos.simulado";

    public static final String CONSULTAR_MASIVO_GENERAL= "Aportes.masivo.general";


    public static final String BUSCAR_CAMPOS_ARCHIVO = "Archivos.Aportes.Masivos.Buscar.File.Nombre.Campos";

    public static final String CONSULTAR_MASIVO_BUSCAR_CONSOLA = "Archivos.Aportes.Masivos.Buscar.Id.Consola";

    public static final String CONSULTAR_MASIVO_ARCHIVO_POR_ECM = "Archivos.Aportes.Masivos.Buscar.MasivoArchivo.Por.ECM";

    public static final String POPULAR_CORRECCION_APORTANTE = "Aportes.Masivos.Popular.Correccion.Aportante";

    public static final String ASP_CREAR_PERSONA = "Aportes.masivos.StoredProcedures.masivos.crearPersonas";


    public static final String CONSULTAR_SOLICITUD_POR_RADICADO = "Consultar.Solicitud.Por.Radicado";


    public static final String TIPO_CLASE_APORTANTE = "Aportes.masivos.Consultar.ClaseAportante.apgId";
    public static final String POPULAR_CORRECCION_APORTANTE_CORE = "Aportes.Masivos.Popular.Correccion.Aportante.Core";
    public static final String POPULAR_CORRECCION_APORTANTE_CORE_COTIZANTES = "Aportes.Masivos.Popular.Correccion.Aportante.Core.Cotizante";

    public static final String CONSULTAR_TIPO_COTIZANTE = "Aportes.Masivos.Consultar.Tipo.Cotizante";



    public static final String CONSULTAR_MASIVO_ARCHIVO_APORTES_DEVOLUCION_POR_RADICADO = "Archivos.Aportes.Masivos.Buscar.MasivoArchivo.AportesDevolucion.Por.Radicado";

    public static final String CONSULTAR_MASIVO_APORTES_SIMULADOS_DEVOLUCION_POR_RADICADO = "Archivos.Aportes.Masivos.Buscar.MasivoArchivo.AportesSimuladosDevolucion.Por.Radicado";

    public static final String ASP_CONSULTARAPORTESDEVOLUCIONMASIVOS = "ASP_ConsultarAportesDevolucionMasivos";

    public static final String ASP_CONSULTARAPORTESDEVOLUCIONMASIVOSDETALLE = "ASP_ConsultarAportesDevolucionMasivosDetalle";

    public static final String ASP_SIMULARAPORTESDEVOLUCIONMASIVOS= "ASP_SimularAportesDevolucionMasivos";

    public static final String ASP_SIMULARAPORTESDEVOLUCIONMASIVOSCORE= "ASP_SimularAportesDevolucionMasivosCore";

    public static final String ASP_SIMULARAPORTESDEVOLUCIONMASIVOSSOLICITUD= "ASP_SimularAportesDevolucionMasivosSolicitud";

    public static final String REPORTE_DEVOLUCION_DETALLE_1 = "Archivos.Aportes.Masivos.Detalle.Reporte.Devolucion.Masiva";

    public static final String CONSULTAR_ESTADO_AFIACION_EMPLEADOR = "Aportes.Masivos.Consultar.Estado.Afiliacion.Empleador";
    
    public static final String CONSULTAR_ESTADO_AFIACION_PERSONA = "Aportes.Masivos.Consultar.Estado.Afiliacion.Persona";

    public static final String BORRAR_MASIVO_SIMULADO_POR_MASIVO_ARCHIVO = "Aportes.Masivos.Borrar.Masivo.Simulado.Por.Masivo.Archivo";

    public static final String CONSULTAR_MASIVO_ARCHIVO_POR_RADICADO = "Aportes.Masivos.Consultar.Masivo.Archivo.Por.Radicado";

    public static final String CONSULTAR_MONTOS_RECAUDO_SIMULADO = "Aportes.Masivos.Consultar.Montos.Recaudo.Simulado";

    public static final String CONSULTAR_MASIVO_SIMULADO_REPORTE = "Aportes.masivos.simulado.reporte";

    public static final String CONSULTAR_MASIVO_SIMULADO_DEVOLUCIONES = "Aportes.masivos.simulado.devoluciones";

    public static final String ASP_JsonCruceAportes = "ASP_JsonCruceAportes";

  }
