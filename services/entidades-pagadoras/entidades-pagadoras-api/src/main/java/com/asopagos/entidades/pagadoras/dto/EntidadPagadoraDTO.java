package com.asopagos.entidades.pagadoras.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.TipoAfiliacionEntidadPagadoraEnum;
import com.asopagos.enumeraciones.core.CanalComunicacionEntidadPagadoraEnum;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import com.asopagos.enumeraciones.core.MedioComunicacionEntidadPagadoraEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO para el servicio entidades pagadoras
 * <b>Historia de Usuario:</b> HU-133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class EntidadPagadoraDTO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idEntidadPagadora;

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private String razonSocial;

    private Boolean aportante;

    private HabilitadoInhabilitadoEnum estadoEntidadPagadora;

    private EstadoEmpleadorEnum estadoEmpleador;

    private UbicacionDTO ubicacion;

    private CanalComunicacionEntidadPagadoraEnum canalComunicacion;

    private MedioComunicacionEntidadPagadoraEnum medioComunicacion;

    private String emailContacto;

    private String paginaWeb;

    private UbicacionDTO ubicacionCorrespondencia;

    private String nombreContacto;

    private String cargoContacto;

    private List<DocumentoEntidadPagadoraDTO> documentos;

    private Long idSucursalEmpresa;

    private TipoAfiliacionEntidadPagadoraEnum tipoAfiliacion;

    private Long idEmpresa;

    private Short digitoVerificacion;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private Date fechaCreacion;

    public EntidadPagadoraDTO() {
        super();
    }

    public EntidadPagadoraDTO(EntidadPagadora entidadPagadora, Empresa empresa, Persona persona, Ubicacion ubicacion) {
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.setDigitoVerificacion(persona.getDigitoVerificacion());
        this.setRazonSocial(persona.getRazonSocial());
        this.setIdEmpresa(empresa.getIdEmpresa());
        this.setPaginaWeb(empresa.getPaginaWeb());
        // Informacion entidad
        this.setIdEntidadPagadora(entidadPagadora.getIdEntidadPagadora());
        this.setAportante(entidadPagadora.getAportante());
        this.setEstadoEntidadPagadora(entidadPagadora.getEstadoEntidadPagadora());
        this.setTipoAfiliacion(entidadPagadora.getTipoAfiliacion());
        this.setCanalComunicacion(entidadPagadora.getCanalComunicacion());
        this.setMedioComunicacion(entidadPagadora.getMedioComunicacion());
        this.setEmailContacto(entidadPagadora.getEmailComunicacion());
        this.setIdSucursalEmpresa(entidadPagadora.getSucursalPagadora());
        this.setNombreContacto(entidadPagadora.getNombreContacto());
        this.setCargoContacto(entidadPagadora.getCargoContacto());
        this.setFechaCreacion(entidadPagadora.getFechaCreacion());
        // Informacion ubicacion principal
        this.setUbicacion(UbicacionDTO.obtenerUbicacionDTO(ubicacion));
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
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
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *        the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the aportante
     */
    public Boolean getAportante() {
        return aportante;
    }

    /**
     * @param aportante
     *        the aportante to set
     */
    public void setAportante(Boolean aportante) {
        this.aportante = aportante;
    }

    /**
     * @return the estadoEntidadPagadora
     */
    public HabilitadoInhabilitadoEnum getEstadoEntidadPagadora() {
        return estadoEntidadPagadora;
    }

    /**
     * @param estadoEntidadPagadora
     *        the estadoEntidadPagadora to set
     */
    public void setEstadoEntidadPagadora(HabilitadoInhabilitadoEnum estadoEntidadPagadora) {
        this.estadoEntidadPagadora = estadoEntidadPagadora;
    }

    /**
     * @return the ubicacion
     */
    public UbicacionDTO getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion
     *        the ubicacion to set
     */
    public void setUbicacion(UbicacionDTO ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the canalComunicacion
     */
    public CanalComunicacionEntidadPagadoraEnum getCanalComunicacion() {
        return canalComunicacion;
    }

    /**
     * @param canalComunicacion
     *        the canalComunicacion to set
     */
    public void setCanalComunicacion(CanalComunicacionEntidadPagadoraEnum canalComunicacion) {
        this.canalComunicacion = canalComunicacion;
    }

    /**
     * @return the medioComunicacion
     */
    public MedioComunicacionEntidadPagadoraEnum getMedioComunicacion() {
        return medioComunicacion;
    }

    /**
     * @param medioComunicacion
     *        the medioComunicacion to set
     */
    public void setMedioComunicacion(MedioComunicacionEntidadPagadoraEnum medioComunicacion) {
        this.medioComunicacion = medioComunicacion;
    }

    /**
     * @return the emailContacto
     */
    public String getEmailContacto() {
        return emailContacto;
    }

    /**
     * @param emailContacto
     *        the emailContacto to set
     */
    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    /**
     * @return the paginaWeb
     */
    public String getPaginaWeb() {
        return paginaWeb;
    }

    /**
     * @param paginaWeb
     *        the paginaWeb to set
     */
    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    /**
     * @return the ubicacionCorrespondencia
     */
    public UbicacionDTO getUbicacionCorrespondencia() {
        return ubicacionCorrespondencia;
    }

    /**
     * @param ubicacionCorrespondencia
     *        the ubicacionCorrespondencia to set
     */
    public void setUbicacionCorrespondencia(UbicacionDTO ubicacionCorrespondencia) {
        this.ubicacionCorrespondencia = ubicacionCorrespondencia;
    }

    /**
     * @return the nombreContacto
     */
    public String getNombreContacto() {
        return nombreContacto;
    }

    /**
     * @param nombreContacto
     *        the nombreContacto to set
     */
    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    /**
     * @return the documentos
     */
    public List<DocumentoEntidadPagadoraDTO> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos
     *        the documentos to set
     */
    public void setDocumentos(List<DocumentoEntidadPagadoraDTO> documentos) {
        this.documentos = documentos;
    }

    /**
     * @return the idSucursalEmpresa
     */
    public Long getIdSucursalEmpresa() {
        return idSucursalEmpresa;
    }

    /**
     * @param idSucursalEmpresa
     *        the idSucursalEmpresa to set
     */
    public void setIdSucursalEmpresa(Long idSucursalEmpresa) {
        this.idSucursalEmpresa = idSucursalEmpresa;
    }

    /**
     * @return the estadoEmpleador
     */
    public EstadoEmpleadorEnum getEstadoEmpleador() {
        return estadoEmpleador;
    }

    /**
     * @param estadoEmpleador
     *        the estadoEmpleador to set
     */
    public void setEstadoEmpleador(EstadoEmpleadorEnum estadoEmpleador) {
        this.estadoEmpleador = estadoEmpleador;
    }

    /**
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa
     *        the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the tipoAfiliacion
     */
    public TipoAfiliacionEntidadPagadoraEnum getTipoAfiliacion() {
        return tipoAfiliacion;
    }

    /**
     * @param tipoAfiliacion
     *        the tipoAfiliacion to set
     */
    public void setTipoAfiliacion(TipoAfiliacionEntidadPagadoraEnum tipoAfiliacion) {
        this.tipoAfiliacion = tipoAfiliacion;
    }

    /**
     * @return the cargoContacto
     */
    public String getCargoContacto() {
        return cargoContacto;
    }

    /**
     * @param cargoContacto
     *        the cargoContacto to set
     */
    public void setCargoContacto(String cargoContacto) {
        this.cargoContacto = cargoContacto;
    }

    /**
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion
     *        the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre
     *        the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre
     *        the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido
     *        the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido
     *        the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the idEntidadPagadora
     */
    public Long getIdEntidadPagadora() {
        return idEntidadPagadora;
    }

    /**
     * @param idEntidadPagadora
     *        the idEntidadPagadora to set
     */
    public void setIdEntidadPagadora(Long idEntidadPagadora) {
        this.idEntidadPagadora = idEntidadPagadora;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
