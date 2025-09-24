package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.fovis.ProyectoSolucionVivienda;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;

/**
 * <b>Descripción: </b> DTO que representa la información de un registro de la
 * tabla <code>ProyectoSolucionVivienda</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class ProyectoSolucionViviendaModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -9219679611818437814L;

    /**
     * Identificador único, llave primaria
     */
    private Long idProyectoVivienda;

    /**
     * Nombre del Proyecto o Solución de vivienda
     */
    private String nombreProyecto;

    /**
     * Código del Proyecto o Solución de vivienda
     */
    private String codigoProyecto;

    /**
     * Número Documento Elegibilidad Proyecto o Solución de vivienda
     */
    private String numeroDocumentoElegibilidad;

    /**
     * Nombre entidad Elegibilidad Proyecto o Solución de vivienda
     */
    private String nombreEntidadElegibilidad;

    /**
     * Fecha Elegibilidad Proyecto o Solución de vivienda
     */
    private Long fechaElegibilidad;

    /**
     * Numero Soluciones Elegibilidad Proyecto o Solución de vivienda
     */
    private Integer numeroViviendaElegibilidad;

    /**
     * Tipos Inmuebles Elegibilidad del Proyecto o Solución de vivienda
     */
    private String tipoInmuebleElegibilidad;

    /**
     * Tipos Inmuebles Elegibilidad del Proyecto o Solución de vivienda
     */
    private String comentariosElegibilidad;

    /**
     * Tipos Inmuebles Elegibilidad del Proyecto o Solución de vivienda
     */
    private String observaciones;

    /**
     * Asociación del Oferente con el Proyecto de Vivienda
     */
    private OferenteModeloDTO oferente;

    /**
     * Asociación de la Modalidad con el Proyecto de Vivienda
     */
    private ModalidadEnum idModalidad;

    /**
     * Asociación de la Ubicación con el Proyecto de Vivienda
     */
    private UbicacionModeloDTO ubicacionProyecto;

    /**
     * Asociación del Medio de Pago con el Proyecto de Vivienda
     */
    private MedioDePagoModeloDTO medioPago;

    /**
     * Licencia del proyecto de vivienda
     */
    private List<LicenciaModeloDTO> licencias;

    /**
     * Lista de documentos soporte que se asociaran al proyecto de vivienda
     */
    private List<DocumentoSoporteModeloDTO> listaDocumentosSoporte;

    /**
     * Identifica si el proyecto de vivienda fue registrado
     */
    private Boolean registrado;

    /**
     * Identifica si el proyecto comparte la cuenta bancaria con el oferente
     */
    private Boolean comparteCuentaOferente;

    /**
     * Identifica si el proyecto dispone de cuenta bancaria
     */
    private Boolean disponeCuentaBancaria;

    /**
     * Constructor a partir de una entidad ProyectoSolucionVivienda
     * @param proyectoSolucionVivienda
     */
    public ProyectoSolucionViviendaModeloDTO(ProyectoSolucionVivienda proyectoSolucionVivienda) {

        convertToDTO(proyectoSolucionVivienda, null, null);
    }

    /**
     * Constructor a partir de las entidades de proyecto y ubicacion
     * @param proyectoSolucionVivienda
     * @param ubicacionProyecto
     * @param ubicacionVivienda
     */
    public ProyectoSolucionViviendaModeloDTO(ProyectoSolucionVivienda proyectoSolucionVivienda, Ubicacion ubicacionProyecto,
            Ubicacion ubicacionVivienda) {
        convertToDTO(proyectoSolucionVivienda, ubicacionProyecto, ubicacionVivienda);
    }

    /**
     * Constructor por defecto
     */
    public ProyectoSolucionViviendaModeloDTO() {
    }

    /**
     * Método que convierte una entidad <code>ProyectoSolucionVivienda</code> en
     * el DTO
     * 
     * @param proyectoSolucionVivienda
     *        La entidad a convertir
     */
    public void convertToDTO(ProyectoSolucionVivienda proyectoSolucionVivienda, Ubicacion ubicacionProyecto, Ubicacion ubicacionVivienda) {
        this.setCodigoProyecto(proyectoSolucionVivienda.getCodigoProyecto());
        this.setComentariosElegibilidad(proyectoSolucionVivienda.getComentariosElegibilidad());
        this.setComparteCuentaOferente(proyectoSolucionVivienda.getComparteCuentaOferente());
        this.setDisponeCuentaBancaria(proyectoSolucionVivienda.getDisponeCuentaBancaria());
        this.setFechaElegibilidad(
                proyectoSolucionVivienda.getFechaElegibilidad() != null ? proyectoSolucionVivienda.getFechaElegibilidad().getTime() : null);
        this.setIdModalidad(proyectoSolucionVivienda.getModalidad());
        this.setNombreEntidadElegibilidad(proyectoSolucionVivienda.getNombreEntidadElegibilidad());
        this.setNombreProyecto(proyectoSolucionVivienda.getNombreProyecto());
        this.setNumeroDocumentoElegibilidad(proyectoSolucionVivienda.getNumeroDocumentoElegibilidad());
        this.setNumeroViviendaElegibilidad(proyectoSolucionVivienda.getNumeroViviendaElegibilidad());
        this.setObservaciones(proyectoSolucionVivienda.getObservaciones());
        this.setRegistrado(proyectoSolucionVivienda.getRegistrado());
        this.setTipoInmuebleElegibilidad(proyectoSolucionVivienda.getTipoInmuebleElegibilidad());
        this.setIdProyectoVivienda(proyectoSolucionVivienda.getIdProyectoVivienda());
        if (proyectoSolucionVivienda.getIdOferente() != null) {
            OferenteModeloDTO oferenteDTO = new OferenteModeloDTO();
            oferenteDTO.setIdOferente(proyectoSolucionVivienda.getIdOferente());
            this.setOferente(oferenteDTO);
        }
        if (ubicacionProyecto != null) {
            UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
            ubicacionModeloDTO.convertToDTO(ubicacionProyecto);
            this.setUbicacionProyecto(ubicacionModeloDTO);
        }
        else if (proyectoSolucionVivienda.getIdUbicacionProyecto() != null) {
            UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
            ubicacionModeloDTO.setIdUbicacion(proyectoSolucionVivienda.getIdUbicacionProyecto());
            this.setUbicacionProyecto(ubicacionModeloDTO);
        }
//        if (ubicacionVivienda != null) {
//            UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
//            ubicacionModeloDTO.convertToDTO(ubicacionVivienda);
//            this.setUbicacionVivienda(ubicacionModeloDTO);
//        }
//        else if (proyectoSolucionVivienda.getIdUbicacionVivienda() != null) {
//            UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
//            ubicacionModeloDTO.setIdUbicacion(proyectoSolucionVivienda.getIdUbicacionVivienda());
//            this.setUbicacionVivienda(ubicacionModeloDTO);
//        }
    }

    /**
     * Métod que convierte el DTO en una entidad <code>ProyectoSolucionVivienda</code>
     * @return La entidad <code>ProyectoSolucionVivienda</code> equivalente
     */
    public ProyectoSolucionVivienda convertToEntity() {
        ProyectoSolucionVivienda proyectoSolucionVivienda = new ProyectoSolucionVivienda();
        proyectoSolucionVivienda.setCodigoProyecto(this.getCodigoProyecto());
        proyectoSolucionVivienda.setComentariosElegibilidad(this.getComentariosElegibilidad());
        if (this.getFechaElegibilidad() != null) {
            proyectoSolucionVivienda.setFechaElegibilidad(new Date(this.getFechaElegibilidad()));
        }
        proyectoSolucionVivienda.setIdOferente(this.getOferente() != null ? this.getOferente().getIdOferente() : null);
        proyectoSolucionVivienda.setIdProyectoVivienda(this.getIdProyectoVivienda());
        proyectoSolucionVivienda
                .setIdUbicacionProyecto(this.getUbicacionProyecto() != null ? this.getUbicacionProyecto().getIdUbicacion() : null);
//        proyectoSolucionVivienda
//                .setIdUbicacionVivienda(this.getUbicacionVivienda() != null ? this.getUbicacionVivienda().getIdUbicacion() : null);
        proyectoSolucionVivienda.setModalidad(this.getIdModalidad());
        proyectoSolucionVivienda.setNombreEntidadElegibilidad(this.getNombreEntidadElegibilidad());
        proyectoSolucionVivienda.setNombreProyecto(this.getNombreProyecto());
        proyectoSolucionVivienda.setNumeroDocumentoElegibilidad(this.getNumeroDocumentoElegibilidad());
        proyectoSolucionVivienda.setNumeroViviendaElegibilidad(this.getNumeroViviendaElegibilidad());
        proyectoSolucionVivienda.setObservaciones(this.getObservaciones());
        proyectoSolucionVivienda.setTipoInmuebleElegibilidad(this.getTipoInmuebleElegibilidad());
//        if (this.getUbicacionProyecto() != null && this.getUbicacionVivienda() != null) {
//            proyectoSolucionVivienda.setUbicacionIgualProyecto(
//                    this.getUbicacionProyecto().getIdUbicacion().equals(this.getUbicacionVivienda().getIdUbicacion()));
//        }
        proyectoSolucionVivienda.setRegistrado(this.getRegistrado());
        proyectoSolucionVivienda.setComparteCuentaOferente(this.getComparteCuentaOferente());
        proyectoSolucionVivienda.setDisponeCuentaBancaria(this.getDisponeCuentaBancaria());
        return proyectoSolucionVivienda;
    }

    /**
     * Obtiene el valor de idProyectoVivienda
     * 
     * @return El valor de idProyectoVivienda
     */
    public Long getIdProyectoVivienda() {
        return idProyectoVivienda;
    }

    /**
     * Establece el valor de idProyectoVivienda
     * 
     * @param idProyectoVivienda
     *        El valor de idProyectoVivienda por asignar
     */
    public void setIdProyectoVivienda(Long idProyectoVivienda) {
        this.idProyectoVivienda = idProyectoVivienda;
    }

    /**
     * Obtiene el valor de nombreProyecto
     * 
     * @return El valor de nombreProyecto
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     * Establece el valor de nombreProyecto
     * 
     * @param nombreProyecto
     *        El valor de nombreProyecto por asignar
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    /**
     * Obtiene el valor de codigoProyecto
     * 
     * @return El valor de codigoProyecto
     */
    public String getCodigoProyecto() {
        return codigoProyecto;
    }

    /**
     * Establece el valor de codigoProyecto
     * 
     * @param codigoProyecto
     *        El valor de codigoProyecto por asignar
     */
    public void setCodigoProyecto(String codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    /**
     * Obtiene el valor de numeroDocumentoElegibilidad
     * 
     * @return El valor de numeroDocumentoElegibilidad
     */
    public String getNumeroDocumentoElegibilidad() {
        return numeroDocumentoElegibilidad;
    }

    /**
     * Establece el valor de numeroDocumentoElegibilidad
     * 
     * @param numeroDocumentoElegibilidad
     *        El valor de numeroDocumentoElegibilidad por asignar
     */
    public void setNumeroDocumentoElegibilidad(String numeroDocumentoElegibilidad) {
        this.numeroDocumentoElegibilidad = numeroDocumentoElegibilidad;
    }

    /**
     * Obtiene el valor de nombreEntidadElegibilidad
     * 
     * @return El valor de nombreEntidadElegibilidad
     */
    public String getNombreEntidadElegibilidad() {
        return nombreEntidadElegibilidad;
    }

    /**
     * Establece el valor de nombreEntidadElegibilidad
     * 
     * @param nombreEntidadElegibilidad
     *        El valor de nombreEntidadElegibilidad por asignar
     */
    public void setNombreEntidadElegibilidad(String nombreEntidadElegibilidad) {
        this.nombreEntidadElegibilidad = nombreEntidadElegibilidad;
    }

    /**
     * Obtiene el valor de fechaElegibilidad
     * 
     * @return El valor de fechaElegibilidad
     */
    public Long getFechaElegibilidad() {
        return fechaElegibilidad;
    }

    /**
     * Establece el valor de fechaElegibilidad
     * 
     * @param fechaElegibilidad
     *        El valor de fechaElegibilidad por asignar
     */
    public void setFechaElegibilidad(Long fechaElegibilidad) {
        this.fechaElegibilidad = fechaElegibilidad;
    }

    /**
     * @return the numeroViviendaElegibilidad
     */
    public Integer getNumeroViviendaElegibilidad() {
        return numeroViviendaElegibilidad;
    }

    /**
     * @param numeroViviendaElegibilidad
     *        the numeroViviendaElegibilidad to set
     */
    public void setNumeroViviendaElegibilidad(Integer numeroViviendaElegibilidad) {
        this.numeroViviendaElegibilidad = numeroViviendaElegibilidad;
    }

    /**
     * @return the tipoInmuebleElegibilidad
     */
    public String getTipoInmuebleElegibilidad() {
        return tipoInmuebleElegibilidad;
    }

    /**
     * @param tipoInmuebleElegibilidad
     *        the tipoInmuebleElegibilidad to set
     */
    public void setTipoInmuebleElegibilidad(String tipoInmuebleElegibilidad) {
        this.tipoInmuebleElegibilidad = tipoInmuebleElegibilidad;
    }

    /**
     * Obtiene el valor de comentariosElegibilidad
     * 
     * @return El valor de comentariosElegibilidad
     */
    public String getComentariosElegibilidad() {
        return comentariosElegibilidad;
    }

    /**
     * Establece el valor de comentariosElegibilidad
     * 
     * @param comentariosElegibilidad
     *        El valor de comentariosElegibilidad por asignar
     */
    public void setComentariosElegibilidad(String comentariosElegibilidad) {
        this.comentariosElegibilidad = comentariosElegibilidad;
    }

    /**
     * Obtiene el valor de observaciones
     * 
     * @return El valor de observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece el valor de observaciones
     * 
     * @param observaciones
     *        El valor de observaciones por asignar
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the idModalidad
     */
    public ModalidadEnum getIdModalidad() {
        return idModalidad;
    }

    /**
     * @param idModalidad
     *        the idModalidad to set
     */
    public void setIdModalidad(ModalidadEnum idModalidad) {
        this.idModalidad = idModalidad;
    }

    /**
     * @return the ubicacionProyecto
     */
    public UbicacionModeloDTO getUbicacionProyecto() {
        return ubicacionProyecto;
    }

    /**
     * @param ubicacionProyecto
     *        the ubicacionProyecto to set
     */
    public void setUbicacionProyecto(UbicacionModeloDTO ubicacionProyecto) {
        this.ubicacionProyecto = ubicacionProyecto;
    }

    /**
     * Obtiene el valor de medioPago
     * 
     * @return El valor de medioPago
     */
    public MedioDePagoModeloDTO getMedioPago() {
        return medioPago;
    }

    /**
     * Establece el valor de medioPago
     * 
     * @param medioPago
     *        El valor de medioPago por asignar
     */
    public void setMedioPago(MedioDePagoModeloDTO medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * Obtiene el valor de oferente
     * 
     * @return El valor de oferente
     */
    public OferenteModeloDTO getOferente() {
        return oferente;
    }

    /**
     * Establece el valor de oferente
     * 
     * @param oferente
     *        El valor de oferente por asignar
     */
    public void setOferente(OferenteModeloDTO oferente) {
        this.oferente = oferente;
    }

    /**
     * @return the listaDocumentosSoporte
     */
    public List<DocumentoSoporteModeloDTO> getListaDocumentosSoporte() {
        return listaDocumentosSoporte;
    }

    /**
     * @param listaDocumentosSoporte
     *        the listaDocumentosSoporte to set
     */
    public void setListaDocumentosSoporte(List<DocumentoSoporteModeloDTO> listaDocumentosSoporte) {
        this.listaDocumentosSoporte = listaDocumentosSoporte;
    }

    /**
     * 
     * 
     * @return the licencias
     */
    public List<LicenciaModeloDTO> getLicencias() {
        return licencias;
    }

    /**
     * @param licencias
     *        the licencias to set
     */
    public void setLicencias(List<LicenciaModeloDTO> licencias) {
        this.licencias = licencias;
    }

    /**
     * @return the registrado
     */

    public Boolean getRegistrado() {
        return registrado;
    }

    /**
     * @param registrado
     *        the registrado to set
     */
    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }

    /**
     * @return the comparteCuentaOferente
     */
    public Boolean getComparteCuentaOferente() {
        return comparteCuentaOferente;
    }

    /**
     * @param comparteCuentaOferente
     *        the comparteCuentaOferente to set
     */
    public void setComparteCuentaOferente(Boolean comparteCuentaOferente) {
        this.comparteCuentaOferente = comparteCuentaOferente;
    }

    /**
     * @return the disponeCuentaBancaria
     */
    public Boolean getDisponeCuentaBancaria() {
        return disponeCuentaBancaria;
    }

    /**
     * @param disponeCuentaBancaria
     *        the disponeCuentaBancaria to set
     */
    public void setDisponeCuentaBancaria(Boolean disponeCuentaBancaria) {
        this.disponeCuentaBancaria = disponeCuentaBancaria;
    }
}
