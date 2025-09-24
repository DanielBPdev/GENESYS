package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.ActualizarUbicacionEmpleadorEnumWS;
import com.asopagos.dto.UbicacionDTO;

public class ActualizarDatosEmpleadorInDTO {
    //iniciales para la consulta
    private TipoIdentificacionEnum tipoDcto;
    private Long documento;
    //Datos para la actualizacion del Representante Legal (RL)
    private TipoIdentificacionEnum tipoDctoRL;
    private Long documentoRL;
    private String primerNombreRL;
    private String segundoNombreRL;
    private String primerApellidoRL;
    private String segundoApellidoRL;
    private String correoElectronicoRL;
    private String numCelularRL;
    private String numTelefonoRL;
    //Datos para cambiar el nombre de razon social
    private String nombreRazonSocial;
    private String nombreComercial;
    //Datos cambio de Ubicacion Oficina Principal
    private UbicacionDTO ubicacion;
    private ActualizarUbicacionEmpleadorEnumWS ubicacionACambiar;

    // Constructor vacío
    public ActualizarDatosEmpleadorInDTO() {
    }

    // Constructor lleno
    public ActualizarDatosEmpleadorInDTO(
            TipoIdentificacionEnum tipoDcto,
            Long documento,
            TipoIdentificacionEnum tipoDctoRL,
            Long documentoRL,
            String primerNombreRL,
            String segundoNombreRL,
            String primerApellidoRL,
            String segundoApellidoRL,
            String correoElectronicoRL,
            String numCelularRL,
            String numTelefonoRL,
            String nombreRazonSocial,
            UbicacionDTO ubicacion,
            ActualizarUbicacionEmpleadorEnumWS ubicacionACambiar,
            String nombreComercial) {
        this.tipoDcto = tipoDcto;
        this.documento = documento;
        this.tipoDctoRL = tipoDctoRL;
        this.documentoRL = documentoRL;
        this.primerNombreRL = primerNombreRL;
        this.segundoNombreRL = segundoNombreRL;
        this.primerApellidoRL = primerApellidoRL;
        this.segundoApellidoRL = segundoApellidoRL;
        this.correoElectronicoRL = correoElectronicoRL;
        this.numCelularRL = numCelularRL;
        this.numTelefonoRL = numTelefonoRL;
        this.nombreRazonSocial = nombreRazonSocial;
        this.ubicacion = ubicacion;
        this.ubicacionACambiar = ubicacionACambiar;
        this.nombreComercial = nombreComercial;
    }

    // Getters y Setters
    public TipoIdentificacionEnum getTipoDcto() {
        return tipoDcto;
    }

    public void setTipoDcto(TipoIdentificacionEnum tipoDcto) {
        this.tipoDcto = tipoDcto;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public TipoIdentificacionEnum getTipoDctoRL() {
        return tipoDctoRL;
    }

    public void setTipoDctoRL(TipoIdentificacionEnum tipoDctoRL) {
        this.tipoDctoRL = tipoDctoRL;
    }

    public Long getDocumentoRL() {
        return documentoRL;
    }

    public void setDocumentoRL(Long documentoRL) {
        this.documentoRL = documentoRL;
    }

    public String getPrimerNombreRL() {
        return primerNombreRL;
    }

    public void setPrimerNombreRL(String primerNombreRL) {
        this.primerNombreRL = primerNombreRL;
    }

    public String getSegundoNombreRL() {
        return segundoNombreRL;
    }

    public void setSegundoNombreRL(String segundoNombreRL) {
        this.segundoNombreRL = segundoNombreRL;
    }

    public String getPrimerApellidoRL() {
        return primerApellidoRL;
    }

    public void setPrimerApellidoRL(String primerApellidoRL) {
        this.primerApellidoRL = primerApellidoRL;
    }

    public String getSegundoApellidoRL() {
        return segundoApellidoRL;
    }

    public void setSegundoApellidoRL(String segundoApellidoRL) {
        this.segundoApellidoRL = segundoApellidoRL;
    }

    public String getCorreoElectronicoRL() {
        return correoElectronicoRL;
    }

    public void setCorreoElectronicoRL(String correoElectronicoRL) {
        this.correoElectronicoRL = correoElectronicoRL;
    }

    public String getNumCelularRL() {
        return numCelularRL;
    }

    public void setNumCelularRL(String numCelularRL) {
        this.numCelularRL = numCelularRL;
    }

    public String getNumTelefonoRL() {
        return numTelefonoRL;
    }

    public void setNumTelefonoRL(String numTelefonoRL) {
        this.numTelefonoRL = numTelefonoRL;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public UbicacionDTO getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDTO ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ActualizarUbicacionEmpleadorEnumWS getUbicacionACambiar() {
        return ubicacionACambiar;
    }

    public void setUbicacionACambiar(ActualizarUbicacionEmpleadorEnumWS ubicacionACambiar) {
        this.ubicacionACambiar = ubicacionACambiar;
    }
    
    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

}
