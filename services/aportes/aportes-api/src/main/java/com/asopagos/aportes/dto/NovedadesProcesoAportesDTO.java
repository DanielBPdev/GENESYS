package com.asopagos.aportes.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO con las novedades a ser procesadas.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 *
 */
public class NovedadesProcesoAportesDTO {

    /**
     * Canal de recepción de las novedades
     */
    private CanalRecepcionEnum canal;
    
    /**
     * Referencia al registro detallado del aporte (staging)
     */
    private Long idRegistroDetallado;
    
    /**
     * Referencia al registro general del aporte (staging)
     */
    private Long idRegistroGeneral;
    
    /**
     * Referencia al aporte al cual pertenecen las novedades
     */
    private Long idTransaccion;
    
    /**
     * Tipo de identificación del aportante
     */    
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número de identificación del aportante
     */
    private String numeroIdentificacion;
    
    /**
     * Listado de novedades a procesar
     */
    private List<NovedadPilaDTO> novedades;


    
    /**
     * constructor de clase por defecto
     */
    public NovedadesProcesoAportesDTO() {
    }

    /**
     * @param canal
     * @param idRegistroDetallado
     * @param idRegistroGeneral
     * @param idTransaccion
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param novedades
     */
    public NovedadesProcesoAportesDTO(CanalRecepcionEnum canal, Long idRegistroDetallado, Long idRegistroGeneral, Long idTransaccion, 
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<NovedadPilaDTO> novedades) {
        this.canal = canal;
        this.idRegistroDetallado = idRegistroDetallado;
        this.idRegistroGeneral = idRegistroGeneral;
        this.idTransaccion = idTransaccion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.novedades = novedades;
    }
    
    /**
     * Constructor por JPA
     * @param canal 
     * @param tenRegistroDetallado 
     * @param tenRegistroGeneral 
     * @param tenIdTransaccion 
     * @param tenTipoIdAportante 
     * @param tenNumeroIdAportante 
     * @param tenMarcaNovedadSimulado 
     * @param tenMarcaNovedadManual 
     * @param tenTipoTransaccion 
     * @param tenEsIngreso 
     * @param tenEsRetiro 
     * @param tenTipoIdCotizante 
     * @param tenNumeroIdCotizante 
     * @param tenFechaInicioNovedad 
     * @param tenFechaFinNovedad 
     * @param tenAccionNovedad 
     * @param tenMensajeNovedad 
     * @param tenTipoCotizante 
     * @param tenValor 
     * @param tenEsTrabajadorReintegrable 
     * @param tenEsEmpleadorReintegrable 
     * @param tenRegistroDetalladoNovedad 
     * @param idTenNovedad 
     * @param novedadAplicada 
     * */
	public NovedadesProcesoAportesDTO(String canal, Long tenRegistroDetallado, Long tenRegistroGeneral,
			Long tenIdTransaccion, String tenTipoIdAportante, String tenNumeroIdAportante,
			Boolean tenMarcaNovedadSimulado, Boolean tenMarcaNovedadManual, String tenTipoTransaccion,
			Boolean tenEsIngreso, Boolean tenEsRetiro, String tenTipoIdCotizante, String tenNumeroIdCotizante,
			Date tenFechaInicioNovedad, Date tenFechaFinNovedad, String tenAccionNovedad, String tenMensajeNovedad,
			String tenTipoCotizante, String tenValor, Boolean tenEsTrabajadorReintegrable,
			Boolean tenEsEmpleadorReintegrable, Long tenRegistroDetalladoNovedad,
			Long idTenNovedad, Boolean novedadexistenteCore, String beneficiarios){
		
		this.canal = CanalRecepcionEnum.valueOf(canal);
		this.idRegistroDetallado = tenRegistroDetallado;
        this.idRegistroGeneral = tenRegistroGeneral;
        this.idTransaccion = tenIdTransaccion;
        this.tipoIdentificacion = TipoIdentificacionEnum.valueOf(tenTipoIdAportante);
        this.numeroIdentificacion = tenNumeroIdAportante;
        
        this.novedades = new ArrayList<>();
		NovedadPilaDTO novedad = new NovedadPilaDTO(tenMarcaNovedadSimulado, tenMarcaNovedadManual,
				tenTipoTransaccion != null ? TipoTransaccionEnum.valueOf(tenTipoTransaccion) : null, tenEsIngreso, tenEsRetiro,
				TipoIdentificacionEnum.valueOf(tenTipoIdCotizante), tenNumeroIdCotizante, tenFechaInicioNovedad,
				tenFechaFinNovedad, tenAccionNovedad, tenMensajeNovedad, TipoAfiliadoEnum.valueOf(tenTipoCotizante),
				tenIdTransaccion, tenRegistroGeneral, idTenNovedad,novedadexistenteCore);
		novedad.setIdRegistroDetallado(tenRegistroDetallado);
		novedad.setIdRegistroDetalladoNovedad(tenRegistroDetalladoNovedad);
		novedad.setEsEmpleadorReintegrable(tenEsEmpleadorReintegrable);
		novedad.setEsTrabajadorReintegrable(tenEsTrabajadorReintegrable);
        novedad.setBeneficiarios(beneficiarios);
		this.novedades.add(novedad);
    }
    
    /**
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the idTransaccion
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion the idTransaccion to set
     */
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    
    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the novedades
     */
    public List<NovedadPilaDTO> getNovedades() {
        return novedades;
    }

    /**
     * @param novedades
     *        the novedades to set
     */
    public void setNovedades(List<NovedadPilaDTO> novedades) {
        this.novedades = novedades;
    }


    @Override
    public String toString() {
        return "NovedadesProcesoAportesDTO{" +
                "canal=" + canal +
                ", idRegistroDetallado=" + idRegistroDetallado +
                ", idRegistroGeneral=" + idRegistroGeneral +
                ", idTransaccion=" + idTransaccion +
                ", tipoIdentificacion=" + tipoIdentificacion +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", novedades=" + novedades +
                '}';
    }
}
