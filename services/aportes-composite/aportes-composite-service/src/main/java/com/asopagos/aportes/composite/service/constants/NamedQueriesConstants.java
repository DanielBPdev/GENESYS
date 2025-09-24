package com.asopagos.aportes.composite.service.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
/**
 * @author squintero
 *
 */
public class NamedQueriesConstants {
    /**
    /**
     * Constante con el nombre de la query para consultar una solicitud de
     * aporte por id de solicitud global.
     */
    public static final String STORED_PROCEDURE_PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_CONSULTAR_PLANILLAS_BLOQUE9 = "stored.procedure.aportes.process.parametrizacion.programada.liberacion.bloque9";
    
    public static final String APORTES_COMPOSITE_BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO = "Aportes.Composite.Afiliado.buscar.rolAfiliado.tipoIdentificacion.numIdentificacion.tipoAfiliado";

    public static final String APORTES_COMPOSITE_BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_DE_ID_DEL_AFI_Y_EMPLEDOR = "Aportes.Composite.RolAfiliado.buscar.rolAfiliado.por.tipo.numero.afi.y.empleador";

    public static final String APORTES_COMPOSITE_CONSULTAR_SALARIO_E_IBC ="Aportes.consultar.salario.e.ibc";
    public static final String APORTES_COMPOSITE_CONSULTAR_APORTE_OBLIGATORIO = "Aportes.consultar.aporte.obligatorio";
    public static final String APORTES_COMPOSITE_CONSULTAR_APORTE_OBLIGATORIO_ANT = "Aportes.consultar.aporte.obligatorio.ant";
    public static final String APORTES_COMPOSITE_CONSULTAR_EMPLEADOR_POR_ID_EMPRESA = "Aportes.Composite.Empleador.consultar.empleador.id.empresa";

    public static final String APORTES_COMPOSITE_CONSULTAR_REINTEGRO_DIFERENTE_EMPLEADOR = "Aportes.Composite.Consultar.Reintegro.Diferente.Empleador";

    public static final String CONSULTAR_APORTES_PEND_PROCESAR_CARTERA = "Consultar.aportes.pendiente.procesar.cartera";

    public static final String ELIMINAR_APORTES_PROCESADOS = "Aportes.Borrar.ControladorCarteraAportes";

    public static final String CONSULTAR_PLANILLA_PEND_PROCESAR_CARTERA = "Consultar.planilla.pendiente.procesar.cartera";

    public static final String ELIMINAR_PLANILLAS_PROCESADAS = "Aportes.Borrar.ControladorCarteraPlanilla";

}
