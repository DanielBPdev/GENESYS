/**
 * 
 */
package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.enumeraciones.FormaPresentacionEnum;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadPlanillaEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;

/**
 * Clase que contiene la lógica para validar
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class InformacionPagoDTO implements Serializable{

    /**
	 * Serial
	 */
	private static final long serialVersionUID = -2703831007105341428L;
	/**
     * Atribuo que indica el id de la solicitud correspondiente a la informacion del pago.
     */
    private Long idSolicitud;
    /**
     * Atributo que indica si el aportante se acoge a la ley 1429.
     */
    private Boolean aportanteAcogeLey1429;
    /**
     * Atributo que incida la clase de aportante.
     */
    private ClaseAportanteEnum claseAportante;
    /**
     * Atributo que contiene el codigo del departamento.
     */
    private String codigoDepartamento;
    /**
     * Atributo que contiene el código del municipio.
     */
    private String codigoMunicipio;
    /**
     * Atributo que contiene el código del operador.
     */
    private Long codigoOperador;
    /**
     * Atributo que contiene el código de la sucursal.
     */
    private String codigoSucursal;
    /**
     * Atributo que contiene el correo electrónico.
     */
    private String correoElectronico;
    /**
     * Atributo que contiene los días de mora
     */
    private Long diasMora;
    /**
     * Atributo que contiene la dirección del aportante.
     */
    private String direccion;
    /**
     * Atributo que contiene la descripción de la ubicación del aportante.
     */
    private String descripcionIndicacion;
    /**
     * Atributo que contiene el estado del aportante.
     */
    private EstadoEmpleadorEnum estadoAportante;
    /**
     * Atributo que contiene el fax.
     */
    private String fax;
    /**
     * Atributo que continee el indicativo del fax.
     */
    private String faxInd;
    /**
     * Atributo que contiene la fecha de la matricula mercantil.
     */
    private Long fechaMatriculaMercantil;
    /**
     * Atributo que contiene la fecha del pago.
     */
    private Long fechaPagoPlantilla;
    /**
     * Atributo que contiene la forma de presentación.
     */
    private FormaPresentacionEnum formaPresentacion;
    /**
     * Atributo que contiene la modalidad de la plantilla.
     */
    private ModalidadPlanillaEnum modalidadPlantilla;
    /**
     * Atributo que contiene la naturaleza jurídica.
     */
    private NaturalezaJuridicaEnum naturalezaJuridica;
    /**
     * Atributo que contiene el nombre de la sucursal.
     */
    private String nombreSucursal;
    /**
     * Atributo que contiene el número de afiliados.
     */
    private Long numeroAfiliadosAdministradora;
    /**
     * Atributo que contiene el número de empleados.
     */
    private Long numeroEmpleados;
    /**
     * Atributo que contiene el número de la planilla.
     */
    private Long numeroPlanilla;
    /**
     * Atributo que contiene el número de la radicación.
     */
    private String numeroRadicacion;
    /**
     * Atributo que contiene el número de registros de salida tipo 2.
     */
    private Long numeroRegistroSalida2;
    /**
     * Atributo que contiene el teléfono.
     */
    private String telefono;
    /**
     * Atributo que contiene el indicativo del teléfono.
     */
    private String telefonoInd;
    /**
     * Atributo que contiene el tipo de persona.
     */
    private TipoPersonaEnum tipoPersona;
    /**
     * Atributo que contiene el tipo de planilla.
     */
    private TipoPlanillaEnum tipoPlanilla;
    /**
     * Documentos asociados a la informacion de pago.
     */
    private List<DocumentoAdministracionEstadoSolicitudDTO> documentos;

    /**
     * Método que retorna el valor de idSolicitud.
     * @return valor de idSolicitud.
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Método encargado de modificar el valor de idSolicitud.
     * @param valor
     *        para modificar idSolicitud.
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Método que retorna el valor de aportanteAcogeLey1429.
     * @return valor de aportanteAcogeLey1429.
     */
    public Boolean getAportanteAcogeLey1429() {
        return aportanteAcogeLey1429;
    }

    /**
     * Método encargado de modificar el valor de aportanteAcogeLey1429.
     * @param valor
     *        para modificar aportanteAcogeLey1429.
     */
    public void setAportanteAcogeLey1429(Boolean aportanteAcogeLey1429) {
        this.aportanteAcogeLey1429 = aportanteAcogeLey1429;
    }

    /**
     * Método que retorna el valor de claseAportante.
     * @return valor de claseAportante.
     */
    public ClaseAportanteEnum getClaseAportante() {
        return claseAportante;
    }

    /**
     * Método encargado de modificar el valor de claseAportante.
     * @param valor
     *        para modificar claseAportante.
     */
    public void setClaseAportante(ClaseAportanteEnum claseAportante) {
        this.claseAportante = claseAportante;
    }

    /**
     * Método que retorna el valor de codigoDepartamento.
     * @return valor de codigoDepartamento.
     */
    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    /**
     * Método encargado de modificar el valor de codigoDepartamento.
     * @param valor
     *        para modificar codigoDepartamento.
     */
    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    /**
     * Método que retorna el valor de codigoMunicipio.
     * @return valor de codigoMunicipio.
     */
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * Método encargado de modificar el valor de codigoMunicipio.
     * @param valor
     *        para modificar codigoMunicipio.
     */
    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    /**
     * Método que retorna el valor de codigoOperador.
     * @return valor de codigoOperador.
     */
    public Long getCodigoOperador() {
        return codigoOperador;
    }

    /**
     * Método encargado de modificar el valor de codigoOperador.
     * @param valor
     *        para modificar codigoOperador.
     */
    public void setCodigoOperador(Long codigoOperador) {
        this.codigoOperador = codigoOperador;
    }

    /**
     * Método que retorna el valor de codigoSucursal.
     * @return valor de codigoSucursal.
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * Método encargado de modificar el valor de codigoSucursal.
     * @param valor
     *        para modificar codigoSucursal.
     */
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * Método que retorna el valor de correoElectronico.
     * @return valor de correoElectronico.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Método encargado de modificar el valor de correoElectronico.
     * @param valor
     *        para modificar correoElectronico.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Método que retorna el valor de diasMora.
     * @return valor de diasMora.
     */
    public Long getDiasMora() {
        return diasMora;
    }

    /**
     * Método encargado de modificar el valor de diasMora.
     * @param valor
     *        para modificar diasMora.
     */
    public void setDiasMora(Long diasMora) {
        this.diasMora = diasMora;
    }

    /**
     * Método que retorna el valor de direccion.
     * @return valor de direccion.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Método encargado de modificar el valor de direccion.
     * @param valor
     *        para modificar direccion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Método que retorna el valor de estadoAportante.
     * @return valor de estadoAportante.
     */
    public EstadoEmpleadorEnum getEstadoAportante() {
        return estadoAportante;
    }

    /**
     * Método encargado de modificar el valor de estadoAportante.
     * @param valor
     *        para modificar estadoAportante.
     */
    public void setEstadoAportante(EstadoEmpleadorEnum estadoAportante) {
        this.estadoAportante = estadoAportante;
    }

    /**
     * Método que retorna el valor de fax.
     * @return valor de fax.
     */
    public String getFax() {
        return fax;
    }

    /**
     * Método encargado de modificar el valor de fax.
     * @param valor
     *        para modificar fax.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Método que retorna el valor de fechaMatriculaMercantil.
     * @return valor de fechaMatriculaMercantil.
     */
    public Long getFechaMatriculaMercantil() {
        return fechaMatriculaMercantil;
    }

    /**
     * Método encargado de modificar el valor de fechaMatriculaMercantil.
     * @param valor
     *        para modificar fechaMatriculaMercantil.
     */
    public void setFechaMatriculaMercantil(Long fechaMatriculaMercantil) {
        this.fechaMatriculaMercantil = fechaMatriculaMercantil;
    }

    /**
     * Método que retorna el valor de fechaPagoPlantilla.
     * @return valor de fechaPagoPlantilla.
     */
    public Long getFechaPagoPlantilla() {
        return fechaPagoPlantilla;
    }

    /**
     * Método encargado de modificar el valor de fechaPagoPlantilla.
     * @param valor
     *        para modificar fechaPagoPlantilla.
     */
    public void setFechaPagoPlantilla(Long fechaPagoPlantilla) {
        this.fechaPagoPlantilla = fechaPagoPlantilla;
    }

    /**
     * Método que retorna el valor de formaPresentacion.
     * @return valor de formaPresentacion.
     */
    public FormaPresentacionEnum getFormaPresentacion() {
        return formaPresentacion;
    }

    /**
     * Método encargado de modificar el valor de formaPresentacion.
     * @param valor
     *        para modificar formaPresentacion.
     */
    public void setFormaPresentacion(FormaPresentacionEnum formaPresentacion) {
        this.formaPresentacion = formaPresentacion;
    }

    /**
     * Método que retorna el valor de modalidadPlantilla.
     * @return valor de modalidadPlantilla.
     */
    public ModalidadPlanillaEnum getModalidadPlantilla() {
        return modalidadPlantilla;
    }

    /**
     * Método encargado de modificar el valor de modalidadPlantilla.
     * @param valor
     *        para modificar modalidadPlantilla.
     */
    public void setModalidadPlantilla(ModalidadPlanillaEnum modalidadPlantilla) {
        this.modalidadPlantilla = modalidadPlantilla;
    }

    /**
     * Método que retorna el valor de naturalezaJuridica.
     * @return valor de naturalezaJuridica.
     */
    public NaturalezaJuridicaEnum getNaturalezaJuridica() {
        return naturalezaJuridica;
    }

    /**
     * Método encargado de modificar el valor de naturalezaJuridica.
     * @param valor
     *        para modificar naturalezaJuridica.
     */
    public void setNaturalezaJuridica(NaturalezaJuridicaEnum naturalezaJuridica) {
        this.naturalezaJuridica = naturalezaJuridica;
    }

    /**
     * Método que retorna el valor de nombreSucursal.
     * @return valor de nombreSucursal.
     */
    public String getNombreSucursal() {
        return nombreSucursal;
    }

    /**
     * Método encargado de modificar el valor de nombreSucursal.
     * @param valor
     *        para modificar nombreSucursal.
     */
    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    /**
     * Método que retorna el valor de numeroAfiliadosAdministradora.
     * @return valor de numeroAfiliadosAdministradora.
     */
    public Long getNumeroAfiliadosAdministradora() {
        return numeroAfiliadosAdministradora;
    }

    /**
     * Método encargado de modificar el valor de numeroAfiliadosAdministradora.
     * @param valor
     *        para modificar numeroAfiliadosAdministradora.
     */
    public void setNumeroAfiliadosAdministradora(Long numeroAfiliadosAdministradora) {
        this.numeroAfiliadosAdministradora = numeroAfiliadosAdministradora;
    }

    /**
     * Método que retorna el valor de numeroEmpleados.
     * @return valor de numeroEmpleados.
     */
    public Long getNumeroEmpleados() {
        return numeroEmpleados;
    }

    /**
     * Método encargado de modificar el valor de numeroEmpleados.
     * @param valor
     *        para modificar numeroEmpleados.
     */
    public void setNumeroEmpleados(Long numeroEmpleados) {
        this.numeroEmpleados = numeroEmpleados;
    }

    /**
     * Método que retorna el valor de numeroPlanilla.
     * @return valor de numeroPlanilla.
     */
    public Long getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * Método encargado de modificar el valor de numeroPlanilla.
     * @param valor
     *        para modificar numeroPlanilla.
     */
    public void setNumeroPlanilla(Long numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * Método que retorna el valor de numeroRadicacion.
     * @return valor de numeroRadicacion.
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * Método encargado de modificar el valor de numeroRadicacion.
     * @param valor
     *        para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * Método que retorna el valor de numeroRegistroSalida2.
     * @return valor de numeroRegistroSalida2.
     */
    public Long getNumeroRegistroSalida2() {
        return numeroRegistroSalida2;
    }

    /**
     * Método encargado de modificar el valor de numeroRegistroSalida2.
     * @param valor
     *        para modificar numeroRegistroSalida2.
     */
    public void setNumeroRegistroSalida2(Long numeroRegistroSalida2) {
        this.numeroRegistroSalida2 = numeroRegistroSalida2;
    }

    /**
     * Método que retorna el valor de telefono.
     * @return valor de telefono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Método encargado de modificar el valor de telefono.
     * @param valor
     *        para modificar telefono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Método que retorna el valor de telefonoInd.
     * @return valor de telefonoInd.
     */
    public String getTelefonoInd() {
        return telefonoInd;
    }

    /**
     * Método encargado de modificar el valor de telefonoInd.
     * @param valor
     *        para modificar telefonoInd.
     */
    public void setTelefonoInd(String telefonoInd) {
        this.telefonoInd = telefonoInd;
    }

    /**
     * Método que retorna el valor de tipoPersona.
     * @return valor de tipoPersona.
     */
    public TipoPersonaEnum getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Método encargado de modificar el valor de tipoPersona.
     * @param valor
     *        para modificar tipoPersona.
     */
    public void setTipoPersona(TipoPersonaEnum tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * Método que retorna el valor de tipoPlanilla.
     * @return valor de tipoPlanilla.
     */
    public TipoPlanillaEnum getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * Método encargado de modificar el valor de tipoPlanilla.
     * @param valor
     *        para modificar tipoPlanilla.
     */
    public void setTipoPlanilla(TipoPlanillaEnum tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * Método que retorna el valor de faxInd.
     * @return valor de faxInd.
     */
    public String getFaxInd() {
        return faxInd;
    }

    /**
     * Método encargado de modificar el valor de faxInd.
     * @param valor
     *        para modificar faxInd.
     */
    public void setFaxInd(String faxInd) {
        this.faxInd = faxInd;
    }

    /**
     * Método que retorna el valor de documentos.
     * @return valor de documentos.
     */
    public List<DocumentoAdministracionEstadoSolicitudDTO> getDocumentos() {
        return documentos;
    }

    /**
     * Método encargado de modificar el valor de documentos.
     * @param valor
     *        para modificar documentos.
     */
    public void setDocumentos(List<DocumentoAdministracionEstadoSolicitudDTO> documentos) {
        this.documentos = documentos;
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


    @Override
    public String toString() {
        return "{" +
            " idSolicitud='" + getIdSolicitud() + "'" +
            ", aportanteAcogeLey1429='" + getAportanteAcogeLey1429() + "'" +
            ", claseAportante='" + getClaseAportante() + "'" +
            ", codigoDepartamento='" + getCodigoDepartamento() + "'" +
            ", codigoMunicipio='" + getCodigoMunicipio() + "'" +
            ", codigoOperador='" + getCodigoOperador() + "'" +
            ", codigoSucursal='" + getCodigoSucursal() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", diasMora='" + getDiasMora() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", descripcionIndicacion='" + getDescripcionIndicacion() + "'" +
            ", estadoAportante='" + getEstadoAportante() + "'" +
            ", fax='" + getFax() + "'" +
            ", faxInd='" + getFaxInd() + "'" +
            ", fechaMatriculaMercantil='" + getFechaMatriculaMercantil() + "'" +
            ", fechaPagoPlantilla='" + getFechaPagoPlantilla() + "'" +
            ", formaPresentacion='" + getFormaPresentacion() + "'" +
            ", modalidadPlantilla='" + getModalidadPlantilla() + "'" +
            ", naturalezaJuridica='" + getNaturalezaJuridica() + "'" +
            ", nombreSucursal='" + getNombreSucursal() + "'" +
            ", numeroAfiliadosAdministradora='" + getNumeroAfiliadosAdministradora() + "'" +
            ", numeroEmpleados='" + getNumeroEmpleados() + "'" +
            ", numeroPlanilla='" + getNumeroPlanilla() + "'" +
            ", numeroRadicacion='" + getNumeroRadicacion() + "'" +
            ", numeroRegistroSalida2='" + getNumeroRegistroSalida2() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", telefonoInd='" + getTelefonoInd() + "'" +
            ", tipoPersona='" + getTipoPersona() + "'" +
            ", tipoPlanilla='" + getTipoPlanilla() + "'" +
            ", documentos='" + getDocumentos() + "'" +
            "}";
    }

}
