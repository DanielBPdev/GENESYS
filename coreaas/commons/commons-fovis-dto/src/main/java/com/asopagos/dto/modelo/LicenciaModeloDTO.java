package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;
import com.asopagos.entidades.ccf.fovis.Licencia;
import com.asopagos.enumeraciones.fovis.EntidadExpideLicenciaEnum;
import com.asopagos.enumeraciones.fovis.TipoLicenciaEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de la tabla
 * <code>Licencia</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class LicenciaModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -4702240162193205232L;

    /**
     * Identificador único, llave primaria
     */
    private Long idLicencia;

    /**
     * Entidad que expide la licencia
     */
    private EntidadExpideLicenciaEnum entidadExpide;

    /**
     * Número de la licencia
     */
    private String numero;

    /**
     * Número de la matricula inmoviliaria
     */
    private String matriculaInmobiliaria;

    /**
     * Referencia al proyecto de vivienda
     */
    private Long idProyecto;

    /**
     * Tipo de licencia
     */
    private TipoLicenciaEnum tipoLincencia;

    /**
     * Fecha Inicio de la Licencia Detalle
     */
    private Long fechaInicioVigenciaLicencia;

    /**
     * Fecha Fin de la Licencia Detalle
     */
    private Long fechaFinVigenciaLicencia;

    /**
     * Número de Resolucion de la Licencia de construccion o urbanismo
     */
    private String numeroResolucion;

    /**
     * Detalle Licencia
     */
    private List<LicenciaDetalleModeloDTO> licenciaDetalle;

    /**
     * Constructor por defecto
     */
    public LicenciaModeloDTO() {
    }

    /**
     * Constructor a partir de una entidad Licencia
     * 
     * @param licencia
     */
    public LicenciaModeloDTO(Licencia licencia) {

        this.setEntidadExpide(licencia.getEntidadExpide());
        this.setIdLicencia(licencia.getIdLicencia());
        this.setIdProyecto(licencia.getIdProyectoSolucionVivienda());
        this.setMatriculaInmobiliaria(licencia.getMatriculaInmobiliaria());
        this.setNumero(licencia.getNumeroLicencia());
        this.setTipoLincencia(licencia.getTipoLincencia());
    }

    /**
     * Método que convierte el DTO en entidad
     * 
     * @return La entidad <code>Licencia</code> equivalente
     */
    public Licencia convertToEntity() {
        Licencia licencia = new Licencia();
        licencia.setEntidadExpide(this.entidadExpide);
        licencia.setIdProyectoSolucionVivienda(this.idProyecto);
        licencia.setMatriculaInmobiliaria(this.matriculaInmobiliaria);
        licencia.setNumeroLicencia(this.numero);
        licencia.setIdLicencia(this.idLicencia);
        licencia.setTipoLincencia(this.tipoLincencia);
        return licencia;
    }

    /**
     * Método que convierte una entidad <code>Licencia</code> en
     * el DTO
     * 
     * @param licencia
     *        La entidad a convertir
     */
    public void convertToDTO(Licencia licencia) {
        this.idLicencia = licencia.getIdLicencia();
        this.idProyecto = licencia.getIdProyectoSolucionVivienda();
        this.entidadExpide = licencia.getEntidadExpide();

        this.matriculaInmobiliaria = licencia.getMatriculaInmobiliaria();
        this.numero = licencia.getNumeroLicencia();
        this.tipoLincencia = licencia.getTipoLincencia();
    }

    /**
     * Obtiene el valor de entidadExpide
     * 
     * @return El valor de entidadExpide
     */
    public EntidadExpideLicenciaEnum getEntidadExpide() {
        return entidadExpide;
    }

    /**
     * Establece el valor de entidadExpide
     * 
     * @param entidadExpide
     *        El valor de entidadExpide por asignar
     */
    public void setEntidadExpide(EntidadExpideLicenciaEnum entidadExpide) {
        this.entidadExpide = entidadExpide;
    }

    /**
     * Obtiene el valor de numero
     * 
     * @return El valor de numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el valor de numero
     * 
     * @param numero
     *        El valor de numero por asignar
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el valor de matriculaInmobiliaria
     * 
     * @return El valor de matriculaInmobiliaria
     */
    public String getMatriculaInmobiliaria() {
        return matriculaInmobiliaria;
    }

    /**
     * Establece el valor de matriculaInmobiliaria
     * 
     * @param matriculaInmobiliaria
     *        El valor de matriculaInmobiliaria por asignar
     */
    public void setMatriculaInmobiliaria(String matriculaInmobiliaria) {
        this.matriculaInmobiliaria = matriculaInmobiliaria;
    }

    /**
     * Obtiene el valor de idProyecto
     * 
     * @return El valor de idProyecto
     */
    public Long getIdProyecto() {
        return idProyecto;
    }

    /**
     * Establece el valor de idProyecto
     * 
     * @param idProyecto
     *        El valor de idProyecto por asignar
     */
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * Obtiene el valor de idLicencia
     * 
     * @return El valor de idLicencia
     */
    public Long getIdLicencia() {
        return idLicencia;
    }

    /**
     * Establece el valor de idLicencia
     * 
     * @param idLicencia
     *        El valor de idLicencia por asignar
     */
    public void setIdLicencia(Long idLicencia) {
        this.idLicencia = idLicencia;
    }

    /**
     * @return the tipoLincencia
     */
    public TipoLicenciaEnum getTipoLincencia() {
        return tipoLincencia;
    }

    /**
     * @param tipoLincencia
     *        the tipoLincencia to set
     */
    public void setTipoLincencia(TipoLicenciaEnum tipoLincencia) {
        this.tipoLincencia = tipoLincencia;
    }

    /**
     * 
     * @return the fechaInicioVigenciaLicencia
     */
    public Long getFechaInicioVigenciaLicencia() {
        return fechaInicioVigenciaLicencia;
    }

    /**
     * @param fechaInicioVigenciaLicencia
     *        the fechaInicioVigenciaLicencia to set
     */
    public void setFechaInicioVigenciaLicencia(Long fechaInicioVigenciaLicencia) {
        this.fechaInicioVigenciaLicencia = fechaInicioVigenciaLicencia;
    }

    /**
     * @return the fechaFinVigenciaLicencia
     */
    public Long getFechaFinVigenciaLicencia() {
        return fechaFinVigenciaLicencia;
    }

    /**
     * @param fechaFinVigenciaLicencia
     *        the fechaFinVigenciaLicencia to set
     */
    public void setFechaFinVigenciaLicencia(Long fechaFinVigenciaLicencia) {
        this.fechaFinVigenciaLicencia = fechaFinVigenciaLicencia;
    }

    /**
     * 
     * @return the licenciaDetalle
     */
    public List<LicenciaDetalleModeloDTO> getLicenciaDetalle() {
        return licenciaDetalle;
    }

    /**
     * @param licenciaDetalle
     *        the licenciaDetalle to set
     */
    public void setLicenciaDetalle(List<LicenciaDetalleModeloDTO> licenciaDetalle) {
        this.licenciaDetalle = licenciaDetalle;
    }

    /**
     * @return the numeroResolucion
     */
    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    /**
     * @param numeroResolucion
     *        the numeroResolucion to set
     */
    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }
}
