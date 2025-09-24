package com.asopagos.aportes.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.NovedadDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * Clase DTO con los datos de las novedades de aportes.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
/**
 * Clase que contiene la lógica para validar 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class HistoricoNovedadesDTO implements Serializable {
    /**
     * Fecha de registro de la novedad.
     */
    private Long fechaRegistro;
    /**
	 * Código identificador de la persona. 
	 */
	private CanalRecepcionEnum canal;
	/**
	 * Código identificador del empleador. 
	 */
	private String empleador;
	/**
	 * Estado de la novedad.
	 */
	private Boolean estado;
    /**
     * Fecha inicio de la novedad.
     */
    private Long fechaInicio;
    /**
     * Fecha fin de la novedad.
     */
    private Long fechaFin;
    
    /**
     * Tipo de novedad.
     */
    private TipoTransaccionEnum tipoNovedad;

        
    public HistoricoNovedadesDTO(){
    }
    /**
     * Método constructor del historico de novedades.
     * @param novedadDetalle novedad detalle que contiene el historico de novedades de aportes.
     * @param solicitud solicitud de novedad qeu contiene el canal y fecha de registro.
     * @param rolAfiliado rol afiliado que contiene el empleador.
     * @param intentoNovedad intento de novedad que contiene las novedades no aplicadas.
     */
    public HistoricoNovedadesDTO(NovedadDetalle novedadDetalle,Solicitud solicitud,RolAfiliado rolAfiliado){
        this.setFechaRegistro(solicitud.getFechaCreacion().getTime());
        this.setCanal(solicitud.getCanalRecepcion());
        if(novedadDetalle!=null){
            this.setFechaFin(novedadDetalle.getFechaFin().getTime());
            this.setFechaInicio(novedadDetalle.getFechaInicio().getTime());
            if(ResultadoProcesoEnum.APROBADA.equals(solicitud.getResultadoProceso())){
                this.setEstado(Boolean.TRUE);    
            }else{
                this.setEstado(Boolean.FALSE);
            }
            if(rolAfiliado.getEmpleador()!=null){
                this.setEmpleador(rolAfiliado.getEmpleador().getEmpresa().getPersona().getRazonSocial());
            }
        }
        this.setTipoNovedad(solicitud.getTipoTransaccion());
    }
    /**
     * Método constructor del historico de novedades de retiro.
     * @param novedadDetalle novedad detalle que contiene el historico de novedades de aportes.
     * @param solicitud solicitud de novedad qeu contiene el canal y fecha de registro.
     * @param rolAfiliado rol afiliado que contiene el empleador.
     * @param intentoNovedad intento de novedad que contiene las novedades no aplicadas.
     */
    public HistoricoNovedadesDTO(Solicitud solicitud,RolAfiliado rolAfiliado){
        this.setFechaRegistro(solicitud.getFechaCreacion().getTime());
        this.setCanal(solicitud.getCanalRecepcion());
        if(rolAfiliado!=null){
            if(rolAfiliado.getFechaRetiro()!=null){
                this.setFechaInicio(rolAfiliado.getFechaRetiro().getTime());
            }
            if(ResultadoProcesoEnum.APROBADA.equals(solicitud.getResultadoProceso())){
                this.setEstado(Boolean.TRUE);    
            }else{
                this.setEstado(Boolean.FALSE);
            }
            if(rolAfiliado.getEmpleador()!=null){
                this.setEmpleador(rolAfiliado.getEmpleador().getEmpresa().getPersona().getRazonSocial());
            }
        }
        this.setTipoNovedad(solicitud.getTipoTransaccion());
    }
    
    /**
     * Método constructor del historico de novedades.
     * @param novedadDetalle novedad detalle que contiene el historico de novedades de aportes.
     * @param solicitud solicitud de novedad qeu contiene el canal y fecha de registro.
     * @param intentoNovedad intento de novedad que contiene las novedades no aplicadas.
     */
    public HistoricoNovedadesDTO(NovedadDetalle novedadDetalle,Solicitud solicitud){
        this.setFechaRegistro(solicitud.getFechaCreacion().getTime());
        this.setCanal(solicitud.getCanalRecepcion());
        if(novedadDetalle!=null){
            this.setFechaFin(novedadDetalle.getFechaFin().getTime());
            this.setFechaInicio(novedadDetalle.getFechaInicio().getTime());
            if(ResultadoProcesoEnum.APROBADA.equals(solicitud.getResultadoProceso())){
                this.setEstado(Boolean.TRUE);    
            }else{
                this.setEstado(Boolean.FALSE);
            }
        }
        this.setTipoNovedad(solicitud.getTipoTransaccion());
    }
    
    /**
     * Método que retorna el valor de fechaRegistro.
     * @return valor de fechaRegistro.
     */
    public Long getFechaRegistro() {
        return fechaRegistro;
    }
    /**
     * Método encargado de modificar el valor de fechaRegistro.
     * @param valor para modificar fechaRegistro.
     */
    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    /**
     * Método que retorna el valor de canal.
     * @return valor de canal.
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }
    /**
     * Método encargado de modificar el valor de canal.
     * @param valor para modificar canal.
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }
    /**
     * Método que retorna el valor de empleador.
     * @return valor de empleador.
     */
    public String getEmpleador() {
        return empleador;
    }
    /**
     * Método encargado de modificar el valor de empleador.
     * @param valor para modificar empleador.
     */
    public void setEmpleador(String empleador) {
        this.empleador = empleador;
    }
    /**
     * Método que retorna el valor de estado.
     * @return valor de estado.
     */
    public Boolean getEstado() {
        return estado;
    }
    /**
     * Método encargado de modificar el valor de estado.
     * @param valor para modificar estado.
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    /**
     * Método que retorna el valor de fechaInicio.
     * @return valor de fechaInicio.
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }
    /**
     * Método encargado de modificar el valor de fechaInicio.
     * @param valor para modificar fechaInicio.
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    /**
     * Método que retorna el valor de fechaFin.
     * @return valor de fechaFin.
     */
    public Long getFechaFin() {
        return fechaFin;
    }
    /**
     * Método encargado de modificar el valor de fechaFin.
     * @param valor para modificar fechaFin.
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }
    /**
     * Método que retorna el valor de tipoNovedad.
     * @return valor de tipoNovedad.
     */
    public TipoTransaccionEnum getTipoNovedad() {
        return tipoNovedad;
    }
    /**
     * Método encargado de modificar el valor de tipoNovedad.
     * @param valor para modificar tipoNovedad.
     */
    public void setTipoNovedad(TipoTransaccionEnum tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }
    
}
