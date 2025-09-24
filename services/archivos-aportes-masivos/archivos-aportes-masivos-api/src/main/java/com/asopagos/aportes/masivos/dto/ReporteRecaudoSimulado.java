package com.asopagos.aportes.masivos.dto;
import java.lang.Integer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.util.Date;

public class ReporteRecaudoSimulado {
    

    private Long numeroRegistro;
    private Date fechaRecaudo;
    private String nombreArchivo;
    private TipoIdentificacionEnum tipoIdentificacionAportante;
    private String numeroIdentificacionAportante;
    private String razonSocialAportante;
    private String periodoPago;
    private TipoSolicitanteMovimientoAporteEnum tipoAportante;
    private TipoIdentificacionEnum tipoIdentificacionCotizante;
    private String numeroIdentificacionCotizante; 
    private TipoCotizanteEnum tipoCotizante;
    private BigDecimal aporteObligatorio;
    private BigDecimal valorInteres;
    private BigDecimal totalAporte;
    private String error;

    

    public ReporteRecaudoSimulado(
        Long numeroRegistro,
        Date fechaRecaudo,
        String nombreArchivo,
        String tipoIdentificacionAportante,
        String numeroIdentificacionAportante,
        String razonSocialAportante,
        String periodoPago,
        String tipoAportante,
        String tipoIdentificacionCotizante,
        String numeroIdentificacionCotizante,
        String tipoCotizante,
        BigDecimal aporteObligatorio,
        BigDecimal valorInteres,
        BigDecimal totalAporte,
        String error) {
        this.numeroRegistro = numeroRegistro;
        this.fechaRecaudo = fechaRecaudo;
        this.nombreArchivo = nombreArchivo;
        if (tipoIdentificacionAportante != null && !tipoIdentificacionAportante.equals("")) this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
        this.razonSocialAportante = razonSocialAportante;
        this.periodoPago = periodoPago;
        if (tipoAportante != null && !tipoAportante.equals("")) this.tipoAportante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante);
        if (tipoIdentificacionCotizante != null && !tipoIdentificacionCotizante.equals("")) this.tipoIdentificacionCotizante = TipoIdentificacionEnum.valueOf(tipoIdentificacionCotizante);
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
        if (tipoCotizante != null && !tipoCotizante.equals("")) this.tipoCotizante = TipoCotizanteEnum.obtenerTipoCotizante(Integer.parseInt(tipoCotizante));
        this.aporteObligatorio = aporteObligatorio;
        this.valorInteres = valorInteres;
        this.totalAporte = totalAporte;
        this.error = error;
    }

    private void limpiarNulos() {
        if (numeroRegistro == null) numeroRegistro = 0L;
        if (fechaRecaudo == null) fechaRecaudo = new Date();
        if (nombreArchivo == null) nombreArchivo = "";
        if (tipoIdentificacionAportante == null) tipoIdentificacionAportante = TipoIdentificacionEnum.NIT;
        if (numeroIdentificacionAportante == null) numeroIdentificacionAportante = "";
        if (razonSocialAportante == null) razonSocialAportante = "";
        if (periodoPago == null) periodoPago = "";
        if (tipoAportante == null) tipoAportante = TipoSolicitanteMovimientoAporteEnum.EMPLEADOR;
        if (tipoIdentificacionCotizante == null) tipoIdentificacionCotizante = TipoIdentificacionEnum.CEDULA_CIUDADANIA;
        if (numeroIdentificacionCotizante == null) numeroIdentificacionCotizante = "";
        if (tipoCotizante == null) tipoCotizante = tipoCotizante = tipoCotizante = TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE;
        if (aporteObligatorio == null) new BigDecimal("0");
        if (valorInteres == null) valorInteres = new BigDecimal("0");
        if (totalAporte == null) totalAporte = new BigDecimal("0");
        if (error == null) error = "";
    }

    public String[] toFormatString() {
        limpiarNulos();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = formato.format(fechaRecaudo);
        return new String[] {
            Long.toString(numeroRegistro),
            fechaString,
            nombreArchivo,
            tipoIdentificacionAportante.getDescripcion(),
            numeroIdentificacionAportante,
            razonSocialAportante,
            periodoPago,
            tipoAportante.getDescripcion(),
            tipoIdentificacionCotizante.getDescripcion(),
            numeroIdentificacionCotizante,
            tipoCotizante.getDescripcion(),
            aporteObligatorio.toString(),
            valorInteres.toString(),
            totalAporte.toString (),
            error
        };
    }

}
