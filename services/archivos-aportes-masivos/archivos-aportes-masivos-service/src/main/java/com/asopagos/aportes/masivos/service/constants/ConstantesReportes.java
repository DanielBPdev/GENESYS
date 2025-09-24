package com.asopagos.aportes.masivos.service.constants;

public class ConstantesReportes {
    
    public static final String NOMBRE_REPORTE_DEVOLUCION_DETALLE = "ReporteDevolucionDetalle";
    public static final String NOMBRE_REPORTE_DEVOLUCION_FINALIZADA = "ReporteDevolucionFinalizada";
    public static final String NOMBRE_REPORTE_RECAUDO_MASIVO_SIMULADO = "ReporteRecaudoMasivoSimulado";
    public static final String NOMBRE_REPORTE_DEVOLUCION_MASIVA_SIMULADO = "ReporteDevolucionMasivaSimulado";


    public static final String[] CABECERAS_REPORTE_DEVOLUCION_DETALLE = {
        "Número",
        "Fecha de recaudo",
        "Método de recaudo",
        "Con detalle",
        "Estado del archivo",
        "Tipo de identificación aportante",
        "Número de identificación aportante",
        "Nombre/Razón social",
        "Tipo de aportante",
        "Número de planilla",
        "Periodo",
        "Monto",
        "Intereses",
        "Total",
        "Tipo de identificación cotizante",
        "Número de identificación cotizante",
        "Primer Nombre",
        "Primer Apellido",
        "Segundo Nombre",
        "Segundo Apellido",
        "Fecha de pago",
        "IBC",
        "ING",
        "RET",
        "LMA",
        "IGE",
        "IRL",
        "SLN",
        "VAC",
        "VSP",
        "VST",
        "Días trabajados",
        "Salario",
        "Valor aporte"
    };

    public static final String[] CABECERAS_REPORTE_DEVOLUCION_FINALIZADA = {
        "Tipo de identificación aportante",
        "Número de identificación aportante",
        "Nombre/Razón social",
        "Tipo de aportante",
        "Número de planilla",
        "Periodo a devolver",
        "Tipo de identificación cotizante",
        "Número de identificación cotizante",
        "Nombre cotizante",
        "Fecha de pago",
        "IBC",
        "ING",
        "RET",
        "LMA",
        "IGE",
        "IRL",
        "SLN",
        "VAC",
        "VSP",
        "VST",
        "Días mora",
        "Salario",
        "Valor aporte a devolver",
        "Interes a devolver",
        "Total a devolver"
    };

    public static final String[] CABECERAS_APORTE_MASIVO_SIMULADO = {
        "No.",
        "Fecha y hora de carga",
        "Nombre del archivo",
        "Tipo de identificación del aportante",
        "Número de identificación del aportante",
        "Razón social/Nombre",
        "Período pago",
        "Tipo Aportante",
        "Tipo Identificación Cotizante",
        "Numero Identificación Cotizante",
        "Tipo Cotizante",
        "Aporte Obligatorio",
        "Valor Intereses",
        "Total Aporte",
        "Error"
               
    };
}