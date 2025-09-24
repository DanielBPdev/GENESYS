package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.Cruce;
import com.asopagos.entidades.ccf.fovis.CruceDetalle;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.CausalCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoInformacionCruceEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del detalle de cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 * 321 - 033
 * 321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class CruceDetalleDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5146645999443616443L;

    /**
     * Identificador detalle cruce
     */
    private Long idCruceDetalle;

    /**
     * Informacion cruce
     */
    private CruceDTO cruce;

    /**
     * Causal cruce
     */
    private CausalCruceEnum causalCruce;

    /**
     * Nit entidad
     */
    private String nitEntidad;

    /**
     * Nombre entidad
     */
    private String nombreEntidad;

    /**
     * Identificacion
     */
    private String numeroIdentificacion;

    /**
     * Apellidos
     */
    private String apellidos;

    /**
     * Nombres
     */
    private String nombres;

    /**
     * Cedula catastral
     */
    private String cedulaCatastral;

    /**
     * Direccion inmueble
     */
    private String direccionInmueble;

    /**
     * Matricula inmobiliaria
     */
    private String matriculaInmobiliaria;

    /**
     * Departamento
     */
    private String departamento;

    /**
     * Municipio
     */
    private String municipio;

    /**
     * Fecha actualizacion ministerio
     */
    private Date fechaActualizacionMinisterio;

    /**
     * Fecha corte entidad
     */
    private Date fechaCorteEntidad;

    /**
     * Apellidos y Nombres
     */
    private String apellidosNombres;

    /**
     * Puntaje
     */
    private String puntaje;

    /**
     * Sexo
     */
    private String sexo;

    /**
     * Zona
     */
    private String zona;

    /**
     * Parentesco
     */
    private String parentesco;

    /**
     * Folio
     */
    private String folio;

    /**
     * Tipo documento
     */
    private String tipoDocumento;

    /**
     * Fecha de solicitud
     */
    private Date fechaSolicitud;

    /**
     * Entidad otorgante
     */
    private String entidadOtorgante;

    /**
     * Caja compensacion
     */
    private String cajaCompensacion;

    /**
     * Caja compensacion
     */
    private String asignadoPosterior;

    /**
     * Tipo informacion detalle
     */
    private TipoInformacionCruceEnum tipo;

    /**
     * Resultado validacion cruce
     */
    private String resultadoValidacion;

    /**
     * Clasificacion del objeto de validacion
     */
    private ClasificacionEnum clasificacion;

    /**
     * Constructor por defecto
     */
    public CruceDetalleDTO() {
        super();
    }

    /**
     * Construye el DTO a partir de la entidad
     * @param cruceDetalle
     */
    public CruceDetalleDTO(CruceDetalle cruceDetalle) {
        convertToDTO(cruceDetalle, null, null);
    }

    /**
     * Construye el cruce con detalle a partir de las entidades
     * @param cruce
     *        Información cruce
     * @param cruceDetalle
     *        Información detalle cruce
     */
    public CruceDetalleDTO(Cruce cruce, CruceDetalle cruceDetalle) {
        convertToDTO(cruceDetalle, cruce, null);
    }

    /**
     * Construye el cruce con detalle y la informacion de la persona asociada
     * @param persona
     *        Información persona asociada
     * @param cruceDetalle
     *        Información detalle cruce
     * @param cruce
     *        Información cruce
     */
    public CruceDetalleDTO(Persona persona, CruceDetalle cruceDetalle, Cruce cruce) {
        convertToDTO(cruceDetalle, cruce, persona);
    }

    /**
     * Constructor detalle cruce con la informacion de la hoja
     * @param infoHojaCruce
     *        Informacion hoja archivo cruce
     */
    public CruceDetalleDTO(CargueArchivoCruceFovisHojaDTO infoHojaCruce) {
        this.setApellidos(infoHojaCruce.getApellidos());
        this.setApellidosNombres(infoHojaCruce.getApellidosNombres());
        this.setAsignadoPosterior(infoHojaCruce.getAsignadoPosteriorReporte());
        this.setCajaCompensacion(infoHojaCruce.getCajaCompensacion());
        this.setCedulaCatastral(infoHojaCruce.getCedulaCatastral());
        this.setDepartamento(infoHojaCruce.getDepartamento());
        if (infoHojaCruce.getDireccionInmueble() != null && !infoHojaCruce.getDireccionInmueble().isEmpty()) {
            this.setDireccionInmueble(infoHojaCruce.getDireccionInmueble());
        }
        if (infoHojaCruce.getDireccion() != null && !infoHojaCruce.getDireccion().isEmpty()) {
            this.setDireccionInmueble(infoHojaCruce.getDireccion());
        }
        this.setEntidadOtorgante(infoHojaCruce.getEntidadOtorgante());
        this.setFechaSolicitud(infoHojaCruce.getFechaSolicitud());
        this.setFolio(infoHojaCruce.getFolio());
        this.setMatriculaInmobiliaria(infoHojaCruce.getMatriculaInmobiliaria());
        this.setMunicipio(infoHojaCruce.getMunicipio());
        this.setNitEntidad(infoHojaCruce.getNitEntidad());
        this.setNombreEntidad(infoHojaCruce.getNombreEntidad());
        this.setNombres(infoHojaCruce.getNombres());
        this.setNumeroIdentificacion(infoHojaCruce.getIdentificacion());
        this.setParentesco(infoHojaCruce.getParentesco());
        this.setPuntaje(infoHojaCruce.getPuntaje());
        this.setSexo(infoHojaCruce.getSexo());
        this.setTipoDocumento(infoHojaCruce.getTipoDocumento());
        this.setZona(infoHojaCruce.getZona());
    }

    /**
     * Convierte a DTO la informacion de la entidades
     * @param cruceDetalle
     *        Informacion detalle cruce
     * @param cruce
     *        Informacion basica cruce
     */
    public void convertToDTO(CruceDetalle cruceDetalle, Cruce cruce, Persona persona) {
        this.setApellidos(cruceDetalle.getApellidos());
        this.setApellidosNombres(cruceDetalle.getApellidosNombres());
        this.setAsignadoPosterior(cruceDetalle.getAsignadoPosterior());
        this.setCajaCompensacion(cruceDetalle.getCajaCompensacion());
        this.setCausalCruce(cruceDetalle.getCausalCruce());
        this.setCedulaCatastral(cruceDetalle.getCedulaCatastral());
        this.setClasificacion(cruceDetalle.getClasificacion());
        this.setDepartamento(cruceDetalle.getDepartamento());
        this.setDireccionInmueble(cruceDetalle.getDireccionInmueble());
        this.setEntidadOtorgante(cruceDetalle.getEntidadOtorgante());
        this.setFechaActualizacionMinisterio(cruceDetalle.getFechaActualizacionMinisterio());
        this.setFechaCorteEntidad(cruceDetalle.getFechaCorteEntidad());
        this.setFechaSolicitud(cruceDetalle.getFechaSolicitud());
        this.setFolio(cruceDetalle.getFolio());
        this.setIdCruceDetalle(cruceDetalle.getIdCruceDetalle());
        this.setMatriculaInmobiliaria(cruceDetalle.getMatriculaInmobiliaria());
        this.setMunicipio(cruceDetalle.getMunicipio());
        this.setNitEntidad(cruceDetalle.getNitEntidad());
        this.setNombreEntidad(cruceDetalle.getNombreEntidad());
        this.setNombres(cruceDetalle.getNombres());
        this.setNumeroIdentificacion(cruceDetalle.getNumeroIdentificacion());
        this.setParentesco(cruceDetalle.getParentesco());
        this.setPuntaje(cruceDetalle.getPuntaje());
        this.setResultadoValidacion(cruceDetalle.getResultadoValidacion());
        this.setSexo(cruceDetalle.getSexo());
        this.setTipo(cruceDetalle.getTipo());
        this.setTipoDocumento(cruceDetalle.getTipoDocumento());
        this.setZona(cruceDetalle.getZona());
        CruceDTO cruceDTO = null;
        if (cruce != null) {
            cruceDTO = new CruceDTO(cruce, persona);
        }
        else {
            cruceDTO = new CruceDTO();
            cruceDTO.setIdCruce(cruceDetalle.getIdCruce());
        }
        this.setCruce(cruceDTO);
    }

    /**
     * Convierte la entidad en DTO
     * @param cargueArchivoCruceFovis
     *        Entidad
     * @return DTO
     */
    public static CruceDetalleDTO convertEntityToDTO(CruceDetalle cruceDetalle) {
        CruceDetalleDTO cruceDetalleDTO = new CruceDetalleDTO(cruceDetalle);
        return cruceDetalleDTO;
    }

    /**
     * Convierte el DTO a entity
     * @return Entidad
     */
    public CruceDetalle convertToEntity() {
        CruceDetalle cruceDetalle = new CruceDetalle();
        cruceDetalle.setApellidos(this.getApellidos());
        cruceDetalle.setApellidosNombres(this.getApellidosNombres());
        cruceDetalle.setAsignadoPosterior(this.getAsignadoPosterior());
        cruceDetalle.setCajaCompensacion(this.getCajaCompensacion());
        cruceDetalle.setCausalCruce(this.getCausalCruce());
        cruceDetalle.setCedulaCatastral(this.getCedulaCatastral());
        cruceDetalle.setClasificacion(this.getClasificacion());
        cruceDetalle.setDepartamento(this.getDepartamento());
        cruceDetalle.setDireccionInmueble(this.getDireccionInmueble());
        cruceDetalle.setEntidadOtorgante(this.getEntidadOtorgante());
        cruceDetalle.setFechaActualizacionMinisterio(this.getFechaActualizacionMinisterio());
        cruceDetalle.setFechaCorteEntidad(this.getFechaCorteEntidad());
        cruceDetalle.setFechaSolicitud(this.getFechaSolicitud());
        cruceDetalle.setFolio(this.getFolio());
        if (this.getCruce() != null && this.getCruce().getIdCruce() != null) {
            cruceDetalle.setIdCruce(this.getCruce().getIdCruce());
        }
        cruceDetalle.setIdCruceDetalle(this.getIdCruceDetalle());
        cruceDetalle.setMatriculaInmobiliaria(this.getMatriculaInmobiliaria());
        cruceDetalle.setMunicipio(this.getMunicipio());
        cruceDetalle.setNitEntidad(this.getNitEntidad());
        cruceDetalle.setNombreEntidad(this.getNombreEntidad());
        cruceDetalle.setNombres(this.getNombres());
        cruceDetalle.setNumeroIdentificacion(this.getNumeroIdentificacion());
        cruceDetalle.setParentesco(this.getParentesco());
        cruceDetalle.setPuntaje(this.getPuntaje());
        cruceDetalle.setResultadoValidacion(this.getResultadoValidacion());
        cruceDetalle.setSexo(this.getSexo());
        cruceDetalle.setTipo(this.getTipo());
        cruceDetalle.setTipoDocumento(this.getTipoDocumento());
        cruceDetalle.setZona(this.getZona());
        return cruceDetalle;
    }

    /**
     * @return the idCruceDetalle
     */
    public Long getIdCruceDetalle() {
        return idCruceDetalle;
    }

    /**
     * @param idCruceDetalle
     *        the idCruceDetalle to set
     */
    public void setIdCruceDetalle(Long idCruceDetalle) {
        this.idCruceDetalle = idCruceDetalle;
    }

    /**
     * @return the cruce
     */
    public CruceDTO getCruce() {
        return cruce;
    }

    /**
     * @param cruce
     *        the cruce to set
     */
    public void setCruce(CruceDTO cruce) {
        this.cruce = cruce;
    }

    /**
     * @return the causalCruce
     */
    public CausalCruceEnum getCausalCruce() {
        return causalCruce;
    }

    /**
     * @param causalCruce
     *        the causalCruce to set
     */
    public void setCausalCruce(CausalCruceEnum causalCruce) {
        this.causalCruce = causalCruce;
    }

    /**
     * @return the nitEntidad
     */
    public String getNitEntidad() {
        return nitEntidad;
    }

    /**
     * @param nitEntidad
     *        the nitEntidad to set
     */
    public void setNitEntidad(String nitEntidad) {
        this.nitEntidad = nitEntidad;
    }

    /**
     * @return the nombreEntidad
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    /**
     * @param nombreEntidad
     *        the nombreEntidad to set
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
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
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos
     *        the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres
     *        the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the cedulaCatastral
     */
    public String getCedulaCatastral() {
        return cedulaCatastral;
    }

    /**
     * @param cedulaCatastral
     *        the cedulaCatastral to set
     */
    public void setCedulaCatastral(String cedulaCatastral) {
        this.cedulaCatastral = cedulaCatastral;
    }

    /**
     * @return the direccionInmueble
     */
    public String getDireccionInmueble() {
        return direccionInmueble;
    }

    /**
     * @param direccionInmueble
     *        the direccionInmueble to set
     */
    public void setDireccionInmueble(String direccionInmueble) {
        this.direccionInmueble = direccionInmueble;
    }

    /**
     * @return the matriculaInmobiliaria
     */
    public String getMatriculaInmobiliaria() {
        return matriculaInmobiliaria;
    }

    /**
     * @param matriculaInmobiliaria
     *        the matriculaInmobiliaria to set
     */
    public void setMatriculaInmobiliaria(String matriculaInmobiliaria) {
        this.matriculaInmobiliaria = matriculaInmobiliaria;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento
     *        the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio
     *        the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the fechaActualizacionMinisterio
     */
    public Date getFechaActualizacionMinisterio() {
        return fechaActualizacionMinisterio;
    }

    /**
     * @param fechaActualizacionMinisterio
     *        the fechaActualizacionMinisterio to set
     */
    public void setFechaActualizacionMinisterio(Date fechaActualizacionMinisterio) {
        this.fechaActualizacionMinisterio = fechaActualizacionMinisterio;
    }

    /**
     * @return the fechaCorteEntidad
     */
    public Date getFechaCorteEntidad() {
        return fechaCorteEntidad;
    }

    /**
     * @param fechaCorteEntidad
     *        the fechaCorteEntidad to set
     */
    public void setFechaCorteEntidad(Date fechaCorteEntidad) {
        this.fechaCorteEntidad = fechaCorteEntidad;
    }

    /**
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     * @param apellidosNombres
     *        the apellidosNombres to set
     */
    public void setApellidosNombres(String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     * @return the puntaje
     */
    public String getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje
     *        the puntaje to set
     */
    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo
     *        the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona
     *        the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * @return the parentesco
     */
    public String getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *        the parentesco to set
     */
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * @return the folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio
     *        the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento
     *        the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the fechaSolicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud
     *        the fechaSolicitud to set
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the entidadOtorgante
     */
    public String getEntidadOtorgante() {
        return entidadOtorgante;
    }

    /**
     * @param entidadOtorgante
     *        the entidadOtorgante to set
     */
    public void setEntidadOtorgante(String entidadOtorgante) {
        this.entidadOtorgante = entidadOtorgante;
    }

    /**
     * @return the cajaCompensacion
     */
    public String getCajaCompensacion() {
        return cajaCompensacion;
    }

    /**
     * @param cajaCompensacion
     *        the cajaCompensacion to set
     */
    public void setCajaCompensacion(String cajaCompensacion) {
        this.cajaCompensacion = cajaCompensacion;
    }

    /**
     * @return the asignadoPosterior
     */
    public String getAsignadoPosterior() {
        return asignadoPosterior;
    }

    /**
     * @param asignadoPosterior
     *        the asignadoPosterior to set
     */
    public void setAsignadoPosterior(String asignadoPosterior) {
        this.asignadoPosterior = asignadoPosterior;
    }

    /**
     * @return the tipo
     */
    public TipoInformacionCruceEnum getTipo() {
        return tipo;
    }

    /**
     * @param tipo
     *        the tipo to set
     */
    public void setTipo(TipoInformacionCruceEnum tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the resultadoValidacion
     */
    public String getResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * @param resultadoValidacion
     *        the resultadoValidacion to set
     */
    public void setResultadoValidacion(String resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion
     *        the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    @Override
    public String toString() {
        return "{" +
            " idCruceDetalle='" + getIdCruceDetalle() + "'" +
            ", cruce='" + getCruce() + "'" +
            ", causalCruce='" + getCausalCruce() + "'" +
            ", nitEntidad='" + getNitEntidad() + "'" +
            ", nombreEntidad='" + getNombreEntidad() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", cedulaCatastral='" + getCedulaCatastral() + "'" +
            ", direccionInmueble='" + getDireccionInmueble() + "'" +
            ", matriculaInmobiliaria='" + getMatriculaInmobiliaria() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", fechaActualizacionMinisterio='" + getFechaActualizacionMinisterio() + "'" +
            ", fechaCorteEntidad='" + getFechaCorteEntidad() + "'" +
            ", apellidosNombres='" + getApellidosNombres() + "'" +
            ", puntaje='" + getPuntaje() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", zona='" + getZona() + "'" +
            ", parentesco='" + getParentesco() + "'" +
            ", folio='" + getFolio() + "'" +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            ", entidadOtorgante='" + getEntidadOtorgante() + "'" +
            ", cajaCompensacion='" + getCajaCompensacion() + "'" +
            ", asignadoPosterior='" + getAsignadoPosterior() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", resultadoValidacion='" + getResultadoValidacion() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            "}";
    }
}
