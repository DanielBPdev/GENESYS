package com.asopagos.personas.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;

public class Ubicacion360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    

    private Long idUbicacion;

    private Short idDepartamento;

    private Short idMunicipio;

    private String direccion;

    private String codigoPostal;

    private String telefonoFijo;

    private String indicativoTelefonoFijo;

    private String telefonoCelular;

    private String correoElectronico;

    private Boolean autorizacionEnvioEmail;
    
    private String descripcionIndicacion;
    
    private TipoUbicacionEnum tipoUbicacion; 
    
    
    /**
     * Constructir de la clase
     */
    public Ubicacion360DTO (){
        
    }
    
    /**
     * Metodo que obtiene la ubicacion con su respectivo tipo
     */
    public Ubicacion360DTO (Ubicacion ubicacion, TipoUbicacionEnum tipoUbicacion){
        if (ubicacion != null) {
            this.setIdUbicacion(ubicacion.getIdUbicacion());
            this.setCodigoPostal(ubicacion.getCodigoPostal());
            this.setCorreoElectronico(ubicacion.getEmail());
            this.setDireccion(ubicacion.getDireccionFisica());
            if (ubicacion.getMunicipio() != null) {
                if (ubicacion.getMunicipio().getIdDepartamento() != null) {
                    this.setIdDepartamento(ubicacion.getMunicipio().getIdDepartamento());
                }
                if (ubicacion.getMunicipio().getIdMunicipio() != null) {
                    this.setIdMunicipio(ubicacion.getMunicipio().getIdMunicipio());
                }
            }
            this.setIndicativoTelefonoFijo(ubicacion.getIndicativoTelFijo());
            this.setTelefonoCelular(ubicacion.getTelefonoCelular());
            this.setTelefonoFijo(ubicacion.getTelefonoFijo());
            this.setAutorizacionEnvioEmail(ubicacion.getAutorizacionEnvioEmail());
            this.setDescripcionIndicacion(ubicacion.getDescripcionIndicacion());
            this.setTipoUbicacion(tipoUbicacion);
        }
    }
    
    /**
     * Asocia los datos del DTO a la Entidad
     * @return Ubicacion
     */
    public Ubicacion convertToEntity() {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setAutorizacionEnvioEmail(this.getAutorizacionEnvioEmail());
        ubicacion.setCodigoPostal(this.getCodigoPostal());
        ubicacion.setDescripcionIndicacion(this.getDescripcionIndicacion());
        ubicacion.setDireccionFisica(this.getDireccion());
        ubicacion.setEmail(this.getCorreoElectronico());
        ubicacion.setIdUbicacion(this.getIdUbicacion());
        ubicacion.setIndicativoTelFijo(this.getIndicativoTelefonoFijo());
        if (this.getIdMunicipio() != null) {
            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(this.getIdMunicipio());
            ubicacion.setMunicipio(municipio);  
        }
        ubicacion.setTelefonoCelular(this.getTelefonoCelular());
        ubicacion.setTelefonoFijo(this.getTelefonoFijo());
        return ubicacion;
    }

    /**
     * Método encargado de crear un objeto UbicacionDTO mediante la entidad
     * Ubicacion
     * 
     * @param ubicacion,
     *            entidad Ubicacion
     * @return el objeto dto Ubicacion
     */
    public static Ubicacion360DTO obtenerUbicacionDTO(Ubicacion ubicacion) {
        if (ubicacion != null) {
            Ubicacion360DTO ubicacionDTO = new Ubicacion360DTO();
            ubicacionDTO.setIdUbicacion(ubicacion.getIdUbicacion());
            ubicacionDTO.setCodigoPostal(ubicacion.getCodigoPostal());
            ubicacionDTO.setCorreoElectronico(ubicacion.getEmail());
            ubicacionDTO.setDireccion(ubicacion.getDireccionFisica());
            if (ubicacion.getMunicipio() != null) {
                if (ubicacion.getMunicipio().getIdDepartamento() != null) {
                    ubicacionDTO.setIdDepartamento(ubicacion.getMunicipio().getIdDepartamento());
                }
                if (ubicacion.getMunicipio().getIdMunicipio() != null) {
                    ubicacionDTO.setIdMunicipio(ubicacion.getMunicipio().getIdMunicipio());
                }
            }
            ubicacionDTO.setIndicativoTelefonoFijo(ubicacion.getIndicativoTelFijo());
            ubicacionDTO.setTelefonoCelular(ubicacion.getTelefonoCelular());
            ubicacionDTO.setTelefonoFijo(ubicacion.getTelefonoFijo());
            ubicacionDTO.setAutorizacionEnvioEmail(ubicacion.getAutorizacionEnvioEmail());
            ubicacionDTO.setDescripcionIndicacion(ubicacion.getDescripcionIndicacion());
            return ubicacionDTO;
        }
        return null;
    }
    
    /**
     * Método encargado de crear un objeto Ubicacion mediante el DTO
     * UbicacionDTO
     * 
     * @param ubicacionDTO,
     *            DTO Ubicacion
     * @return el objeto Ubicacion
     */
    public static Ubicacion obtenerUbicacion(UbicacionDTO ubicacionDTO) {
        if (ubicacionDTO != null) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setIdUbicacion(ubicacionDTO.getIdUbicacion());
            ubicacion.setCodigoPostal(ubicacionDTO.getCodigoPostal());
            ubicacion.setEmail(ubicacionDTO.getCorreoElectronico());
            ubicacion.setDireccionFisica(ubicacionDTO.getDireccion());
            if (ubicacionDTO.getIdMunicipio()!= null) {
                Municipio m = new Municipio();
                m.setIdMunicipio(ubicacionDTO.getIdMunicipio());
                ubicacion.setMunicipio(m);
            }
            ubicacion.setIndicativoTelFijo(ubicacionDTO.getIndicativoTelefonoFijo());
            ubicacion.setTelefonoCelular(ubicacionDTO.getTelefonoCelular());
            ubicacion.setTelefonoFijo(ubicacionDTO.getTelefonoFijo());
            ubicacion.setAutorizacionEnvioEmail(ubicacionDTO.getAutorizacionEnvioEmail());
            ubicacion.setDescripcionIndicacion(ubicacionDTO.getDescripcionIndicacion());
            return ubicacion;
        }
        return null;
    }
    
    /**
     * Asigna los datos asociados a la Ubicacion.
     * @param ubicacion
     * @return Ubicacion
     */
    public Ubicacion asignarDatosUbicacion(Ubicacion ubicacion) {
        if (getAutorizacionEnvioEmail() != null) {
            ubicacion.setAutorizacionEnvioEmail(getAutorizacionEnvioEmail());
        }
        if (getCodigoPostal() != null) {
            ubicacion.setCodigoPostal(getCodigoPostal());
        }
        if (getCorreoElectronico() != null) {
            ubicacion.setEmail(getCorreoElectronico());
        }
        if (getDireccion() != null) {
            ubicacion.setDireccionFisica(getDireccion());
        }
        if (getIdMunicipio() != null) {
            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(getIdMunicipio());
            ubicacion.setMunicipio(municipio);
        }
        if (getIndicativoTelefonoFijo() != null) {
            ubicacion.setIndicativoTelFijo(getIndicativoTelefonoFijo());
        }
        if (getTelefonoCelular() != null) {
            ubicacion.setTelefonoCelular(getTelefonoCelular()); 
        }
        if (getTelefonoFijo() != null) {
            ubicacion.setTelefonoFijo(getTelefonoFijo());
        }
        if (getDescripcionIndicacion() != null) {
            ubicacion.setDescripcionIndicacion(getDescripcionIndicacion());
        }
        
        return ubicacion;
    }
    
    /**
     * Método encargado de asignar los datos de la ubicación dto a la persona 
     * @param ubicacion, ubicación 
     * @return retorna la ubicación dto
     */
    public Ubicacion360DTO asignarDatosUbicacionDTO(Object[] ubicacion){
        Ubicacion360DTO ubicacionDTO=new Ubicacion360DTO();
        ubicacionDTO.setIdUbicacion(ubicacion[0] != null ? new Long(ubicacion[0].toString()) : null);
        ubicacionDTO.setAutorizacionEnvioEmail(ubicacion[1] != null ? new Boolean(ubicacion[1].toString()) : null);
        ubicacionDTO.setCodigoPostal(ubicacion[2] != null ? ubicacion[2].toString() : null);
        ubicacionDTO.setDireccion(ubicacion[3] != null ? ubicacion[3].toString() : null);
        ubicacionDTO.setCorreoElectronico(ubicacion[4] != null ? ubicacion[4].toString() : null);
        ubicacionDTO.setIndicativoTelefonoFijo(ubicacion[5] != null ? ubicacion[5].toString() : null);
        ubicacionDTO.setTelefonoCelular(ubicacion[6] != null ? ubicacion[6].toString() : null);
        ubicacionDTO.setTelefonoFijo(ubicacion[7] != null ? ubicacion[7].toString() : null);
        ubicacionDTO.setIdMunicipio(ubicacion[8] != null ? new Short(ubicacion[8].toString()) : null);
        ubicacionDTO.setDescripcionIndicacion(ubicacion[9] != null ? ubicacion[9].toString() : null);
        ubicacionDTO.setTipoUbicacion(ubicacion[10] != null ? TipoUbicacionEnum.valueOf(ubicacion[10].toString()): null);
        return ubicacionDTO;
    }
    
    /**
     * Método encargado de asignar los datos de la ubicación dto a la persona 
     * @param ubicacion, ubicación 
     * @return retorna la ubicación dto
     */
    public Ubicacion360DTO asignarDatosUbicacionAfiliadoDTO(Object[] ubicacion){
        Ubicacion360DTO ubicacionDTO=new Ubicacion360DTO();
        ubicacionDTO.setIdUbicacion(ubicacion[0] != null ? new Long(ubicacion[0].toString()) : null);
        ubicacionDTO.setAutorizacionEnvioEmail(ubicacion[1] != null ? new Boolean(ubicacion[1].toString()) : null);
        ubicacionDTO.setCodigoPostal(ubicacion[2] != null ? ubicacion[2].toString() : null);
        ubicacionDTO.setDireccion(ubicacion[3] != null ? ubicacion[3].toString() : null);
        ubicacionDTO.setCorreoElectronico(ubicacion[4] != null ? ubicacion[4].toString() : null);
        ubicacionDTO.setIndicativoTelefonoFijo(ubicacion[5] != null ? ubicacion[5].toString() : null);
        ubicacionDTO.setTelefonoCelular(ubicacion[6] != null ? ubicacion[6].toString() : null);
        ubicacionDTO.setTelefonoFijo(ubicacion[7] != null ? ubicacion[7].toString() : null);
        ubicacionDTO.setIdMunicipio(ubicacion[8] != null ? new Short(ubicacion[8].toString()) : null);
        ubicacionDTO.setDescripcionIndicacion(ubicacion[9] != null ? ubicacion[9].toString() : null);
        return ubicacionDTO;
    }
    
    /**
     * Método que retorna el valor de idUbicacion.
     * @return valor de idUbicacion.
     */
    public Long getIdUbicacion() {
        return idUbicacion;
    }

    /**
     * Método encargado de modificar el valor de idUbicacion.
     * @param valor para modificar idUbicacion.
     */
    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    /**
     * @return the idDepartamento
     */
    public Short getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * @param idDepartamento
     *            the idDepartamento to set
     */
    public void setIdDepartamento(Short idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * @return the idMunicipio
     */
    public Short getIdMunicipio() {
        return idMunicipio;
    }

    /**
     * @param idMunicipio
     *            the idMunicipio to set
     */
    public void setIdMunicipio(Short idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion
     *            the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal
     *            the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the telefonoFijo
     */
    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    /**
     * @param telefonoFijo
     *            the telefonoFijo to set
     */
    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    /**
     * @return the indicativoTelefonoFijo
     */
    public String getIndicativoTelefonoFijo() {
        return indicativoTelefonoFijo;
    }

    /**
     * @param indicativoTelefonoFijo
     *            the indicativoTelefonoFijo to set
     */
    public void setIndicativoTelefonoFijo(String indicativoTelefonoFijo) {
        this.indicativoTelefonoFijo = indicativoTelefonoFijo;
    }

    /**
     * @return the telefonoCelular
     */
    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    /**
     * @param telefonoCelular
     *            the telefonoCelular to set
     */
    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico
     *            the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the autorizacionEnvioEmail
     */
    public Boolean getAutorizacionEnvioEmail() {
        return autorizacionEnvioEmail;
    }

    /**
     * @param autorizacionEnvioEmail
     *            the autorizacionEnvioEmail to set
     */
    public void setAutorizacionEnvioEmail(Boolean autorizacionEnvioEmail) {
        this.autorizacionEnvioEmail = autorizacionEnvioEmail;
    }

    /**
     * @return the descripcionIndicacion
     */
    public String getDescripcionIndicacion() {
        return descripcionIndicacion;
    }

    /**
     * @param descripcionIndicacion the descripcionIndicacion to set
     */
    public void setDescripcionIndicacion(String descripcionIndicacion) {
        this.descripcionIndicacion = descripcionIndicacion;
    }

    /**
     * Método que retorna el valor de tipoUbicacion.
     * @return valor de tipoUbicacion.
     */
    public TipoUbicacionEnum getTipoUbicacion() {
        return tipoUbicacion;
    }

    /**
     * Método encargado de modificar el valor de tipoUbicacion.
     * @param valor para modificar tipoUbicacion.
     */
    public void setTipoUbicacion(TipoUbicacionEnum tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }
}
