package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.afiliaciones.ProductoNoConforme;
import com.asopagos.enumeraciones.afiliaciones.ClasificacionTipoProductoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGestionProductoNoConformeSubsanableEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoErrorProductoNoConformeNoResueltoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoProductoNoConformeEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;

/**
 * <b>Descripción:</b> DTO que transporta la informacion del producto no conforme
 * encontrado en la solicitud
 * <b>Historia de Usuario:</b> Transversales
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class ProductoNoConformeDTO implements Serializable {

    private Long idProductoNoConformeNoResuelto;

    private Long idSolicitud;

    private Short numeroConsecutivo;

    private TipoProductoNoConformeEnum tipoProductoNoConforme;

    private TipoErrorProductoNoConformeNoResueltoEnum tipoError;

    private String seccion;

    private String grupoCampos;

    private String campo;

    private Boolean campoObligatorio;

    private Boolean subsanable;

    private String descripcion;

    private ResultadoGestionProductoNoConformeSubsanableEnum resultadoGestion;

    private String usuarioFront;

    private String valorCampoFront;

    private String comentariosFront;

    private String comentariosBack;

    private String usuarioBack;

    private String valorCampoBack;

    private Date fechaCreacion;

    private String identificadorDocumentoSoporteGestion;

    private ResultadoGestionProductoNoConformeSubsanableEnum resultadoRevisionBack;

    private String comentariosBack2;

    private ResultadoGestionProductoNoConformeSubsanableEnum resultadoRevisionBack2;

    private Long idBeneficiario;

    private ClasificacionEnum tipoBeneficiario;

    private Long idGrupoFamiliar;

    private Byte numeroGrupoFamiliar;

    private String nombreInconsistencia;

    private String descripcionInconsistencia;

    private ClasificacionTipoProductoEnum clasificacionTipoProducto;

    public ProductoNoConformeDTO() {
    }

    public ProductoNoConformeDTO(ProductoNoConforme productoNoConforme) {
        this.idProductoNoConformeNoResuelto = productoNoConforme.getIdProductoNoConformeNoResuelto();
        this.idSolicitud = productoNoConforme.getIdSolicitud();
        this.tipoProductoNoConforme = productoNoConforme.getTipoProductoNoConforme();
        this.tipoError = productoNoConforme.getTipoError();
        this.seccion = productoNoConforme.getSeccion();
        this.grupoCampos = productoNoConforme.getGrupoCampos();
        this.campo = productoNoConforme.getCampo();
        this.campoObligatorio = productoNoConforme.getCampoObligatorio();
        this.subsanable = productoNoConforme.getSubsanable();
        this.descripcion = productoNoConforme.getDescripcion();
        this.resultadoGestion = productoNoConforme.getResultadoGestion();
        this.usuarioFront = productoNoConforme.getUsuarioFront();
        this.valorCampoFront = productoNoConforme.getValorCampoFront();
        this.comentariosFront = productoNoConforme.getComentariosFront();
        this.comentariosBack = productoNoConforme.getComentariosBack();
        this.usuarioBack = productoNoConforme.getUsuarioBack();
        this.valorCampoBack = productoNoConforme.getValorCampoBack();
        this.fechaCreacion = productoNoConforme.getFechaCreacion();
        this.identificadorDocumentoSoporteGestion = productoNoConforme.getIdentificadorDocumentoSoporteGestion();
        this.resultadoRevisionBack = productoNoConforme.getResultadoRevisionBack();
        this.comentariosBack2 = productoNoConforme.getComentariosBack2();
        this.resultadoRevisionBack2 = productoNoConforme.getResultadoRevisionBack2();
        this.idBeneficiario = productoNoConforme.getIdBeneficiario();
        this.nombreInconsistencia = productoNoConforme.getNombreInconsistencia();
        this.descripcionInconsistencia = productoNoConforme.getDescripcionInconsistencia();
        this.clasificacionTipoProducto = productoNoConforme.getClasificacionTipoProducto();
        this.numeroConsecutivo = productoNoConforme.getNumeroConsecutivo();
    }

    /**
     * @return producto no conforme
     */
    public ProductoNoConforme obtenerProductoNoConforme() {
        ProductoNoConforme productoNoConforme = new ProductoNoConforme();
        productoNoConforme.setIdProductoNoConformeNoResuelto(this.idProductoNoConformeNoResuelto);
        productoNoConforme.setIdSolicitud(this.idSolicitud);
        productoNoConforme.setTipoProductoNoConforme(this.tipoProductoNoConforme);
        productoNoConforme.setTipoError(this.tipoError);
        productoNoConforme.setSeccion(this.seccion);
        productoNoConforme.setGrupoCampos(this.grupoCampos);
        productoNoConforme.setCampo(this.campo);
        productoNoConforme.setCampoObligatorio(this.campoObligatorio);
        productoNoConforme.setSubsanable(this.subsanable);
        productoNoConforme.setDescripcion(this.descripcion);
        productoNoConforme.setResultadoGestion(this.resultadoGestion);
        productoNoConforme.setUsuarioFront(this.usuarioFront);
        productoNoConforme.setValorCampoFront(this.valorCampoFront);
        productoNoConforme.setComentariosFront(this.comentariosFront);
        productoNoConforme.setComentariosBack(this.comentariosBack);
        productoNoConforme.setUsuarioBack(this.usuarioBack);
        productoNoConforme.setValorCampoBack(this.valorCampoBack);
        productoNoConforme.setFechaCreacion(this.fechaCreacion);
        productoNoConforme.setIdentificadorDocumentoSoporteGestion(this.identificadorDocumentoSoporteGestion);
        productoNoConforme.setResultadoRevisionBack(this.resultadoRevisionBack);
        productoNoConforme.setComentariosBack2(this.comentariosBack2);
        productoNoConforme.setResultadoRevisionBack2(this.resultadoRevisionBack2);
        productoNoConforme.setIdBeneficiario(this.idBeneficiario);
        productoNoConforme.setNombreInconsistencia(this.nombreInconsistencia);
        productoNoConforme.setDescripcionInconsistencia(this.descripcionInconsistencia);
        productoNoConforme.setClasificacionTipoProducto(this.clasificacionTipoProducto);
        productoNoConforme.setNumeroConsecutivo(this.numeroConsecutivo);
        return productoNoConforme;
    }

    /**
     * @return the idProductoNoConformeNoResuelto
     */
    public Long getIdProductoNoConformeNoResuelto() {
        return idProductoNoConformeNoResuelto;
    }

    /**
     * @param idProductoNoConformeNoResuelto
     *        the idProductoNoConformeNoResuelto to set
     */
    public void setIdProductoNoConformeNoResuelto(Long idProductoNoConformeNoResuelto) {
        this.idProductoNoConformeNoResuelto = idProductoNoConformeNoResuelto;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *        the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the numeroConsecutivo
     */
    public Short getNumeroConsecutivo() {
        return numeroConsecutivo;
    }

    /**
     * @param numeroConsecutivo
     *        the numeroConsecutivo to set
     */
    public void setNumeroConsecutivo(Short numeroConsecutivo) {
        this.numeroConsecutivo = numeroConsecutivo;
    }

    /**
     * @return the tipoProductoNoConforme
     */
    public TipoProductoNoConformeEnum getTipoProductoNoConforme() {
        return tipoProductoNoConforme;
    }

    /**
     * @param tipoProductoNoConforme
     *        the tipoProductoNoConforme to set
     */
    public void setTipoProductoNoConforme(TipoProductoNoConformeEnum tipoProductoNoConforme) {
        this.tipoProductoNoConforme = tipoProductoNoConforme;
    }

    /**
     * @return the tipoError
     */
    public TipoErrorProductoNoConformeNoResueltoEnum getTipoError() {
        return tipoError;
    }

    /**
     * @param tipoError
     *        the tipoError to set
     */
    public void setTipoError(TipoErrorProductoNoConformeNoResueltoEnum tipoError) {
        this.tipoError = tipoError;
    }

    /**
     * @return the seccion
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * @param seccion
     *        the seccion to set
     */
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    /**
     * @return the grupoCampos
     */
    public String getGrupoCampos() {
        return grupoCampos;
    }

    /**
     * @param grupoCampos
     *        the grupoCampos to set
     */
    public void setGrupoCampos(String grupoCampos) {
        this.grupoCampos = grupoCampos;
    }

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo
     *        the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @return the campoObligatorio
     */
    public Boolean getCampoObligatorio() {
        return campoObligatorio;
    }

    /**
     * @param campoObligatorio
     *        the campoObligatorio to set
     */
    public void setCampoObligatorio(Boolean campoObligatorio) {
        this.campoObligatorio = campoObligatorio;
    }

    /**
     * @return the subsanable
     */
    public Boolean getSubsanable() {
        return subsanable;
    }

    /**
     * @param subsanable
     *        the subsanable to set
     */
    public void setSubsanable(Boolean subsanable) {
        this.subsanable = subsanable;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *        the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the resultadoGestion
     */
    public ResultadoGestionProductoNoConformeSubsanableEnum getResultadoGestion() {
        return resultadoGestion;
    }

    /**
     * @param resultadoGestion
     *        the resultadoGestion to set
     */
    public void setResultadoGestion(ResultadoGestionProductoNoConformeSubsanableEnum resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
    }

    /**
     * @return the usuarioFront
     */
    public String getUsuarioFront() {
        return usuarioFront;
    }

    /**
     * @param usuarioFront
     *        the usuarioFront to set
     */
    public void setUsuarioFront(String usuarioFront) {
        this.usuarioFront = usuarioFront;
    }

    /**
     * @return the valorCampoFront
     */
    public String getValorCampoFront() {
        return valorCampoFront;
    }

    /**
     * @param valorCampoFront
     *        the valorCampoFront to set
     */
    public void setValorCampoFront(String valorCampoFront) {
        this.valorCampoFront = valorCampoFront;
    }

    /**
     * @return the comentariosFront
     */
    public String getComentariosFront() {
        return comentariosFront;
    }

    /**
     * @param comentariosFront
     *        the comentariosFront to set
     */
    public void setComentariosFront(String comentariosFront) {
        this.comentariosFront = comentariosFront;
    }

    /**
     * @return the comentariosBack
     */
    public String getComentariosBack() {
        return comentariosBack;
    }

    /**
     * @param comentariosBack
     *        the comentariosBack to set
     */
    public void setComentariosBack(String comentariosBack) {
        this.comentariosBack = comentariosBack;
    }

    /**
     * @return the usuarioBack
     */
    public String getUsuarioBack() {
        return usuarioBack;
    }

    /**
     * @param usuarioBack
     *        the usuarioBack to set
     */
    public void setUsuarioBack(String usuarioBack) {
        this.usuarioBack = usuarioBack;
    }

    /**
     * @return the valorCampoBack
     */
    public String getValorCampoBack() {
        return valorCampoBack;
    }

    /**
     * @param valorCampoBack
     *        the valorCampoBack to set
     */
    public void setValorCampoBack(String valorCampoBack) {
        this.valorCampoBack = valorCampoBack;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion
     *        the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the identificadorDocumentoSoporteGestion
     */
    public String getIdentificadorDocumentoSoporteGestion() {
        return identificadorDocumentoSoporteGestion;
    }

    /**
     * @param identificadorDocumentoSoporteGestion
     *        the identificadorDocumentoSoporteGestion to set
     */
    public void setIdentificadorDocumentoSoporteGestion(String identificadorDocumentoSoporteGestion) {
        this.identificadorDocumentoSoporteGestion = identificadorDocumentoSoporteGestion;
    }

    /**
     * @return the resultadoRevisionBack
     */
    public ResultadoGestionProductoNoConformeSubsanableEnum getResultadoRevisionBack() {
        return resultadoRevisionBack;
    }

    /**
     * @param resultadoRevisionBack
     *        the resultadoRevisionBack to set
     */
    public void setResultadoRevisionBack(ResultadoGestionProductoNoConformeSubsanableEnum resultadoRevisionBack) {
        this.resultadoRevisionBack = resultadoRevisionBack;
    }

    /**
     * @return the comentariosBack2
     */
    public String getComentariosBack2() {
        return comentariosBack2;
    }

    /**
     * @param comentariosBack2
     *        the comentariosBack2 to set
     */
    public void setComentariosBack2(String comentariosBack2) {
        this.comentariosBack2 = comentariosBack2;
    }

    /**
     * @return the resultadoRevisionBack2
     */
    public ResultadoGestionProductoNoConformeSubsanableEnum getResultadoRevisionBack2() {
        return resultadoRevisionBack2;
    }

    /**
     * @param resultadoRevisionBack2
     *        the resultadoRevisionBack2 to set
     */
    public void setResultadoRevisionBack2(ResultadoGestionProductoNoConformeSubsanableEnum resultadoRevisionBack2) {
        this.resultadoRevisionBack2 = resultadoRevisionBack2;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario
     *        the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the tipoBeneficiario
     */
    public ClasificacionEnum getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario
     *        the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(ClasificacionEnum tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar
     *        the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the numeroGrupoFamiliar
     */
    public Byte getNumeroGrupoFamiliar() {
        return numeroGrupoFamiliar;
    }

    /**
     * @param numeroGrupoFamiliar
     *        the numeroGrupoFamiliar to set
     */
    public void setNumeroGrupoFamiliar(Byte numeroGrupoFamiliar) {
        this.numeroGrupoFamiliar = numeroGrupoFamiliar;
    }

    /**
     * @return the nombreInconsistencia
     */
    public String getNombreInconsistencia() {
        return nombreInconsistencia;
    }

    /**
     * @param nombreInconsistencia
     *        the nombreInconsistencia to set
     */
    public void setNombreInconsistencia(String nombreInconsistencia) {
        this.nombreInconsistencia = nombreInconsistencia;
    }

    /**
     * @return the descripcionInconsistencia
     */
    public String getDescripcionInconsistencia() {
        return descripcionInconsistencia;
    }

    /**
     * @param descripcionInconsistencia
     *        the descripcionInconsistencia to set
     */
    public void setDescripcionInconsistencia(String descripcionInconsistencia) {
        this.descripcionInconsistencia = descripcionInconsistencia;
    }

    /**
     * @return the clasificacionTipoProducto
     */
    public ClasificacionTipoProductoEnum getClasificacionTipoProducto() {
        return clasificacionTipoProducto;
    }

    /**
     * @param clasificacionTipoProducto
     *        the clasificacionTipoProducto to set
     */
    public void setClasificacionTipoProducto(ClasificacionTipoProductoEnum clasificacionTipoProducto) {
        this.clasificacionTipoProducto = clasificacionTipoProducto;
    }

}
