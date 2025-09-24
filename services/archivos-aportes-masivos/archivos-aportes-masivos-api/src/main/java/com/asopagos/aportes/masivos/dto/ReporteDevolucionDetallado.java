package com.asopagos.aportes.masivos.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
import java.util.Date;

public class ReporteDevolucionDetallado {
    

    private Long numeroRegistro;
    private String fechaRecaudo;
    private ModalidadRecaudoAporteEnum modalidadRecaudo;
    private Boolean aporteConDetalle;
    private EstadoProcesoArchivoEnum estadoArchivo;
    private TipoIdentificacionEnum tipoIdentificacionAportante;
    private String numeroIdentificacionAportante;
    private String razonSocialAportante;
    private TipoAportanteEnum tipoAportante;
    private Long indicePlanilla;
    private String periodoAporte;
    private BigDecimal monto;
    private BigDecimal intereses;
    private BigDecimal totalAporte;
    private TipoIdentificacionEnum tipoIdentificacionCotizante;
    private String numeroIdentificacionCotizante;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String fechaDePago;
    private BigDecimal ibc;
    private Boolean ing;
    private Boolean ret;
    private Boolean lma;
    private Boolean ige;
    private Boolean irl;
    private Boolean sln;
    private Boolean vac;
    private Boolean vsp;
    private Boolean vst;
    private Integer diasTrabajados;
    private BigDecimal salario;
    private BigDecimal valorAporte;

    public ReporteDevolucionDetallado(
        Long numeroRegistro,
        String fechaRecaudo,
        String modalidadRecaudo,
        String aporteConDetalle,
        String estadoArchivo,
        String tipoIdentificacionAportante,
        String numeroIdentificacionAportante,
        String razonSocialAportante,
        String tipoAportante,
        Long indicePlanilla,
        String periodoAporte,
        BigDecimal monto,
        BigDecimal intereses,
        BigDecimal totalAporte,
        String tipoIdentificacionCotizante,
        String numeroIdentificacionCotizante,
        String primerNombre,
        String segundoNombre,
        String primerApellido,
        String segundoApellido,
        String fechaDePago,
        BigDecimal ibc,
        String ing,
        String ret,
        String lma,
        String ige,
        String irl,
        String sln,
        String vac,
        String vsp,
        String vst,
        Integer diasTrabajados,
        BigDecimal salario,
        BigDecimal valorAporte
    ) {
        this.numeroRegistro = numeroRegistro;
        this.fechaRecaudo = fechaRecaudo;
        this.modalidadRecaudo = ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudo);
        this.aporteConDetalle = aporteConDetalle.equals("1") ? Boolean.TRUE : Boolean.FALSE;
        this.estadoArchivo = EstadoProcesoArchivoEnum.valueOf(estadoArchivo);
        this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
        this.razonSocialAportante = razonSocialAportante;
        this.tipoAportante = TipoAportanteEnum.valueOf(tipoAportante);
        this.indicePlanilla = indicePlanilla;
        this.periodoAporte = periodoAporte;
        this.monto = monto;
        this.intereses = intereses;
        this.totalAporte = totalAporte;
        this.tipoIdentificacionCotizante = TipoIdentificacionEnum.valueOf(tipoIdentificacionCotizante);
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaDePago = fechaDePago;
        this.ibc = ibc;
        this.ing = ing != null && ing.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.ret = ret != null && ret.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.lma = lma != null && lma.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.ige = ige != null && ige.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.irl = irl != null && irl.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.sln = sln != null && sln.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.vac = vac != null && vac.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.vsp = vsp != null && vsp.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.vst = vst != null && vst.equals("X") ? Boolean.TRUE : Boolean.FALSE;
        this.diasTrabajados = diasTrabajados;
        this.salario = salario;
        this.valorAporte = valorAporte;
    }


    public String[] toFromatString() {
        limpiarNulos();
        return new String[] {
            Long.toString(numeroRegistro),
            fechaRecaudo,
            modalidadRecaudo.getDescripcion(),
            Boolean.TRUE.equals(aporteConDetalle) ? "Si" : "No",
            estadoArchivo.getDescripcion(),
            tipoIdentificacionAportante.getDescripcion(),
            numeroIdentificacionAportante,
            razonSocialAportante,
            tipoAportante.getDescripcion(),
            Long.toString(indicePlanilla),
            periodoAporte,
            monto.toString(),
            intereses.toString(),
            totalAporte.toString(),
            tipoIdentificacionCotizante.getDescripcion(),
            numeroIdentificacionCotizante,
            primerNombre,
            primerApellido,
            segundoNombre,
            segundoApellido,
            fechaDePago,
            ibc.toString(),
            Boolean.TRUE.equals(ing) ? "Si" : "No",
            Boolean.TRUE.equals(ret) ? "Si" : "No",
            Boolean.TRUE.equals(lma) ? "Si" : "No",
            Boolean.TRUE.equals(ige) ? "Si" : "No",
            Boolean.TRUE.equals(irl) ? "Si" : "No",
            Boolean.TRUE.equals(sln) ? "Si" : "No",
            Boolean.TRUE.equals(vac) ? "Si" : "No",
            Boolean.TRUE.equals(vsp) ? "Si" : "No",
            Boolean.TRUE.equals(vst) ? "Si" : "No",
            Integer.toString(diasTrabajados),
            salario.toString(),
            valorAporte.toString()
        };
    }

    private void limpiarNulos() {
        if (fechaRecaudo == null) fechaRecaudo = "";
        if (periodoAporte == null) periodoAporte = "";
        if (fechaDePago == null) fechaDePago = "";
        if (modalidadRecaudo == null) modalidadRecaudo = ModalidadRecaudoAporteEnum.PILA;
        if (estadoArchivo == null) estadoArchivo = EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO;
        if (tipoIdentificacionAportante == null) tipoIdentificacionAportante = TipoIdentificacionEnum.NIT;
        if (numeroIdentificacionAportante == null) numeroIdentificacionAportante = "";
        if (razonSocialAportante == null) razonSocialAportante = "";
        if (indicePlanilla == null) indicePlanilla = 0L;
        if (monto == null) monto = new BigDecimal(0);
        if (intereses == null) intereses = new BigDecimal(0);
        if (totalAporte == null) totalAporte = new BigDecimal(0);
        if (tipoIdentificacionCotizante == null) tipoIdentificacionCotizante = TipoIdentificacionEnum.CEDULA_CIUDADANIA;
        if (numeroIdentificacionCotizante == null) numeroIdentificacionCotizante = "";
        if (primerNombre == null) primerNombre = "";
        if (segundoNombre == null) segundoNombre = "";
        if (primerApellido == null) primerApellido = "";
        if (segundoApellido == null) segundoApellido = "";
        if (ibc == null) ibc = new BigDecimal(0);
        if (salario == null) salario = new BigDecimal(0);
        if (valorAporte == null) valorAporte = new BigDecimal(0);
        if (primerNombre == null) primerNombre = "";
        if (segundoNombre == null) segundoNombre = "";
        if (primerApellido == null) primerApellido = "";
        if (segundoApellido == null) segundoApellido = "";
        if (tipoAportante == null) tipoAportante = TipoAportanteEnum.EMPLEADOR;
    } 

}
