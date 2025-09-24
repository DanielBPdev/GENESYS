package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.subsidiomonetario.pagos.ConvenioTerceroPagador;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoConvenioEnum;

/**
 * <b>Descripción:</b> DTO que contiene los datos necesarios para gestionar un convenio del tercero pagador.
 * <b>Módulo:</b> Asopagos - HU-31-210<br/>
 * 
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class ConvenioTercerPagadorDTO extends EmpresaModeloDTO implements Serializable {

    private static final long serialVersionUID = 4617551990731504954L;

    /**
     * Identificador del convenio en base de datos
     */
    private Long idConvenio;

    /**
     * Nombre del convenio
     */
    @NotNull
    private String nombreConvenio;

    /**
     * Estado en el cual se encuentra el convenio.
     */
    private EstadoConvenioEnum estadoConvenio;

    /**
     * Nombres y apellidos de la persona de contecto asociada al convenio
     */
    private String nombreCompletoContacto;

    /**
     * Cargo que tiene la persona de contacto del convenio.
     */
    private String cargoContacto;

    /**
     * Indicativo del Teléfono fijo de la persona de contacto del convenio
     */
    private String indTelFijoContacto;

    /**
     * Teléfono fijo de la persona de contacto del convenio
     */
    private String telefonoFijoContacto;

    /**
     * Teléfono celular de la persona de contacto del convenio
     */
    private String telefonoCelularContacto;

    /**
     * Correo electrónico de la persona de contacto del convenio
     */
    private String emailContacto;

    /**
     * Tipo de medio de pago por el cual se realiza el convenio
     */
    private TipoMedioDePagoEnum tipoMedioDePago;

    /**
     * Acuerdo de facturación para el registro
     * de solicitud del convenio.
     */
    private String acuerdoDeFacturacion;

    /**
     * Fecha de creación de la solicitud del registro
     * del convenio
     */
    private Date fechaCreacionRegistroConvenio;

    /**
     * Nombre del usuario de Genesys relacionado con el convenio.
     */
    @NotNull
    private String nombreUsuarioGenesys;

    /**
     * Lista de los documentos de soporte del convenio que se han cargado
     * para asociarse con el convenio.
     */
    private List<DocumentoSoporteConvenioModeloDTO> listaDocumentosSoporte;

    /**
     * Constructor vacio de la clase.
     */
    public ConvenioTercerPagadorDTO() {
    }

    /**
     * Constructor que instancia el objeto con los atributos del id y del nombre
     * 
     * @param idConvenio
     *        identificador de base de datos del convenio
     * @param nombre
     *        nombre del convenio
     */
    public ConvenioTercerPagadorDTO(Long idConvenio, String nombre) {
        this.setIdConvenio(idConvenio);
        this.setNombreConvenio(nombre);
    }

    /**
     * Constructor que inicializa el convenio a partir de la información de la
     * entidad del convenio y la empresa.
     * 
     * @param convenio
     *        entidad que contiene los datos del convenio.
     * @param empresa
     *        entidad que contiene los datos del convenio relacionados con una empresa.
     * @param dsc
     *        entidad que contiene el documento de soporte relacionado al convenio
     * @param ubicacion
     *        entidad que contiene los datos de la ubicación relacionada con el convenio.
     */
    public ConvenioTercerPagadorDTO(ConvenioTerceroPagador convenio, Empresa empresa, DocumentoSoporte dsc, Ubicacion ubicacion) {

        super.convertToDTO(empresa);
        this.setIdConvenio(convenio.getIdConvenio());
        this.setNombreConvenio(convenio.getNombre());
        this.setEstadoConvenio(convenio.getEstado());
        this.setNombreCompletoContacto(convenio.getNombreCompletoContacto());
        this.setCargoContacto(convenio.getCargoContacto());
        this.setIndTelFijoContacto(convenio.getIndTelFijoContacto());
        this.setTelefonoCelularContacto(convenio.getTelefonoCelularContacto());
        this.setEmailContacto(convenio.getEmailContacto());
        this.setTipoMedioDePago(convenio.getMedioDePago());
        this.setNombreUsuarioGenesys(convenio.getUsuarioAsignadoConvenio());
        this.setAcuerdoDeFacturacion(convenio.getAcuerdoDeFacturacion());
        this.setTelefonoFijoContacto(convenio.getTelefonoFijoContacto());
        UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
        ubicacionModeloDTO.convertToDTO(ubicacion);
        this.setUbicacionModeloDTO(ubicacionModeloDTO);

        List<DocumentoSoporteConvenioModeloDTO> listaDocumentos = new ArrayList<>();
        DocumentoSoporteConvenioModeloDTO documentoSoporteConvenioModeloDTO = new DocumentoSoporteConvenioModeloDTO(dsc);
        documentoSoporteConvenioModeloDTO.setIdConvenioTerceroPagador(this.getIdConvenio());
        listaDocumentos.add(documentoSoporteConvenioModeloDTO);
        this.setListaDocumentosSoporte(listaDocumentos);
    }
    
    /**
     * Constructor que inicializa la clase por medio de la entidad del convenio tercero pagador.
     * @param convenio
     *        Entidad que representa al convenio.
     */
    public ConvenioTercerPagadorDTO(ConvenioTerceroPagador convenio) {

        this.setIdConvenio(convenio.getIdConvenio());
        this.setNombreConvenio(convenio.getNombre());
        this.setEstadoConvenio(convenio.getEstado());
        this.setNombreCompletoContacto(convenio.getNombreCompletoContacto());
        this.setCargoContacto(convenio.getCargoContacto());
        this.setIndTelFijoContacto(convenio.getIndTelFijoContacto());
        this.setTelefonoCelularContacto(convenio.getTelefonoCelularContacto());
        this.setEmailContacto(convenio.getEmailContacto());
        this.setTipoMedioDePago(convenio.getMedioDePago());
        this.setNombreUsuarioGenesys(convenio.getUsuarioAsignadoConvenio());
        this.setAcuerdoDeFacturacion(convenio.getAcuerdoDeFacturacion());
        this.setIdEmpresa(convenio.getIdEmpresa());
        this.setTelefonoFijoContacto(convenio.getTelefonoFijoContacto());
    }
    
    /**
     * Constructor que inicializa el convenio a partir de la información de la
     * entidad del convenio y la empresa.
     * 
     * @param convenio
     *        entidad que contiene los datos del convenio
     * @param empresa
     *        entidad que contiene los datos del convenio relacionados con una empresa
     * @param ubicacion
     *        entidad que contiene los datos de la ubicación relacionada con el convenio.
     */
    public ConvenioTercerPagadorDTO(ConvenioTerceroPagador convenio, Empresa empresa, Ubicacion ubicacion) {

        super.convertToDTO(empresa);
        this.setIdConvenio(convenio.getIdConvenio());
        this.setNombreConvenio(convenio.getNombre());
        this.setEstadoConvenio(convenio.getEstado());
        this.setNombreCompletoContacto(convenio.getNombreCompletoContacto());
        this.setCargoContacto(convenio.getCargoContacto());
        this.setIndTelFijoContacto(convenio.getIndTelFijoContacto());
        this.setTelefonoCelularContacto(convenio.getTelefonoCelularContacto());
        this.setEmailContacto(convenio.getEmailContacto());
        this.setTipoMedioDePago(convenio.getMedioDePago());
        this.setNombreUsuarioGenesys(convenio.getUsuarioAsignadoConvenio());
        this.setAcuerdoDeFacturacion(convenio.getAcuerdoDeFacturacion());
        this.setTelefonoFijoContacto(convenio.getTelefonoFijoContacto());
        UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
        ubicacionModeloDTO.convertToDTO(ubicacion);
        this.setUbicacionModeloDTO(ubicacionModeloDTO);
    }

    /**
     * Metodo que convierte el DTO de convenio en entidad.
     * @return entidad del convenio del tercero pagador
     */
    public ConvenioTerceroPagador convertToConvenioTerceroPagadorEntity() {

        ConvenioTerceroPagador convenioTerceroPagador = new ConvenioTerceroPagador();

        convenioTerceroPagador.setNombre(this.getNombreConvenio());
        convenioTerceroPagador.setEstado(this.getEstadoConvenio());
        convenioTerceroPagador.setMedioDePago(this.getTipoMedioDePago());
        convenioTerceroPagador.setUsuarioAsignadoConvenio(this.getNombreUsuarioGenesys());
        convenioTerceroPagador.setNombreCompletoContacto(this.getNombreCompletoContacto());
        convenioTerceroPagador.setCargoContacto(this.getCargoContacto());
        convenioTerceroPagador.setIndTelFijoContacto(this.getIndTelFijoContacto());
        convenioTerceroPagador.setTelefonoFijoContacto(this.getTelefonoFijoContacto());
        convenioTerceroPagador.setTelefonoCelularContacto(this.getTelefonoCelularContacto());
        convenioTerceroPagador.setEmailContacto(this.getEmailContacto());
        convenioTerceroPagador.setIdEmpresa(this.getIdEmpresa());
        convenioTerceroPagador.setAcuerdoDeFacturacion(this.getAcuerdoDeFacturacion());
        convenioTerceroPagador.setIdUbicacion(this.getUbicacionModeloDTO().getIdUbicacion());

        return convenioTerceroPagador;
    }

    /**
     * Metodo que separa los documentos y los agrega al convenio correspondiente.
     * 
     * @param listaConvenios
     *        lista que contiene los convenios y documentos sin ordendar.
     * @return lista de convenios
     */
    public List<ConvenioTercerPagadorDTO> unirDocumentosConConvenio(List<ConvenioTercerPagadorDTO> listaConvenios) {

        List<ConvenioTercerPagadorDTO> convenios = new ArrayList<>();

        int j = 0;

        for (int i = 0; i < listaConvenios.size(); i++) {

            Long idConvenioRelacionado = listaConvenios.get(i).getIdConvenio();

            List<DocumentoSoporteConvenioModeloDTO> listaDocumentos = new ArrayList<>();

            j = i + 1;

            if (i != listaConvenios.size() - 1) {

                for (; j < listaConvenios.size(); j++) {

                    if (idConvenioRelacionado
                            .equals(listaConvenios.get(j).getListaDocumentosSoporte().get(0).getIdConvenioTerceroPagador())) {

                        //se guardan los documentos pertenecientes al convenio
                        listaDocumentos.add(listaConvenios.get(j).getListaDocumentosSoporte().get(0));
                    }

                }
                //si el convenio no se ha guardado, se procede.
                if (!isConvenio(convenios, listaConvenios.get(i).getIdConvenio())) {
                    //se agrega el el documento perteneciente al convenio que se esta comparando (i)
                    listaDocumentos.add(listaConvenios.get(i).getListaDocumentosSoporte().get(0));

                    ConvenioTercerPagadorDTO convenioAuxDTO = listaConvenios.get(i);
                    convenioAuxDTO.setListaDocumentosSoporte(listaDocumentos);
                    convenios.add(convenioAuxDTO);
                }

            }
            else {

                idConvenioRelacionado = listaConvenios.get(listaConvenios.size() - 1).getIdConvenio();

                for (int k = listaConvenios.size() - 2; k >= 0; k--) {

                    if (idConvenioRelacionado == listaConvenios.get(k).getIdConvenio()) {

                        //se guardan los documentos pertenecientes al convenio
                        listaDocumentos.add(listaConvenios.get(k).getListaDocumentosSoporte().get(0));
                    }
                }

                //si el convenio no se ha guardado, se procede.
                if (!isConvenio(convenios, listaConvenios.get(i).getIdConvenio())) {
                    //se agrega el el documento perteneciente al convenio que se esta comparando (i)
                    listaDocumentos.add(listaConvenios.get(i).getListaDocumentosSoporte().get(0));

                    ConvenioTercerPagadorDTO convenioAuxDTO = listaConvenios.get(i);
                    convenioAuxDTO.setListaDocumentosSoporte(listaDocumentos);
                    convenios.add(convenioAuxDTO);
                }
            }

        }

        return convenios;
    }

    /**
     * Metodo que valida si existe el identificador del convenio en la lista
     * @param transaccionesSeparadas
     *        lista de transacciones
     * @param idTransaccion
     *        identificador de transacción fallida que se desea verificar si existe.
     * @return true si ese identificador de transacción existe; false si no.
     */
    private boolean isConvenio(List<ConvenioTercerPagadorDTO> conveniosSeparados, Long idConvenio) {

        boolean respuesta = false;

        for (ConvenioTercerPagadorDTO convenio : conveniosSeparados) {
            if (convenio.getIdConvenio() == idConvenio) {
                respuesta = true;
                break;
            }
            else
                respuesta = false;
        }

        return respuesta;
    }

    /**
     * @return the idConvenio
     */
    public Long getIdConvenio() {
        return idConvenio;
    }

    /**
     * @param idConvenio
     *        the idConvenio to set
     */
    public void setIdConvenio(Long idConvenio) {
        this.idConvenio = idConvenio;
    }

    /**
     * @return the nombre
     */
    public String getNombreConvenio() {
        return nombreConvenio;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombreConvenio(String nombre) {
        this.nombreConvenio = nombre;
    }

    /**
     * @return the estadoConvenio
     */
    public EstadoConvenioEnum getEstadoConvenio() {
        return estadoConvenio;
    }

    /**
     * @param estadoConvenio
     *        the estadoConvenio to set
     */
    public void setEstadoConvenio(EstadoConvenioEnum estadoConvenio) {
        this.estadoConvenio = estadoConvenio;
    }

    /**
     * @return the nombreCompletoContacto
     */
    public String getNombreCompletoContacto() {
        return nombreCompletoContacto;
    }

    /**
     * @param nombreCompletoContacto
     *        the nombreCompletoContacto to set
     */
    public void setNombreCompletoContacto(String nombreCompletoContacto) {
        this.nombreCompletoContacto = nombreCompletoContacto;
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
     * @return the indTelefonoFijoContacto
     */
    public String getIndTelFijoContacto() {
        return indTelFijoContacto;
    }

    /**
     * @param indTelefonoFijoContacto
     *        the indTelefonoFijoContacto to set
     */
    public void setIndTelFijoContacto(String indTelefonoFijoContacto) {
        this.indTelFijoContacto = indTelefonoFijoContacto;
    }

    /**
     * @return the telefonoFijoContacto
     */
    public String getTelefonoFijoContacto() {
        return telefonoFijoContacto;
    }

    /**
     * @param telefonoFijoContacto
     *        the telefonoFijoContacto to set
     */
    public void setTelefonoFijoContacto(String telefonoFijoContacto) {
        this.telefonoFijoContacto = telefonoFijoContacto;
    }

    /**
     * @return the telefonoCelularContacto
     */
    public String getTelefonoCelularContacto() {
        return telefonoCelularContacto;
    }

    /**
     * @param telefonoCelularContacto
     *        the telefonoCelularContacto to set
     */
    public void setTelefonoCelularContacto(String telefonoCelularContacto) {
        this.telefonoCelularContacto = telefonoCelularContacto;
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
     * @return the tipoMedioDePago
     */
    public TipoMedioDePagoEnum getTipoMedioDePago() {
        return tipoMedioDePago;
    }

    /**
     * @param tipoMedioDePago
     *        the tipoMedioDePago to set
     */
    public void setTipoMedioDePago(TipoMedioDePagoEnum tipoMedioDePago) {
        this.tipoMedioDePago = tipoMedioDePago;
    }

    /**
     * @return the acuerdoDeFacturacion
     */
    public String getAcuerdoDeFacturacion() {
        return acuerdoDeFacturacion;
    }

    /**
     * @param acuerdoDeFacturacion
     *        the acuerdoDeFacturacion to set
     */
    public void setAcuerdoDeFacturacion(String acuerdoDeFacturacion) {
        this.acuerdoDeFacturacion = acuerdoDeFacturacion;
    }

    /**
     * @return the fechaCreacionRegistroConvenio
     */
    public Date getFechaCreacionRegistroConvenio() {
        return fechaCreacionRegistroConvenio;
    }

    /**
     * @param fechaCreacionRegistroConvenio
     *        the fechaCreacionRegistroConvenio to set
     */
    public void setFechaCreacionRegistroConvenio(Date fechaCreacionRegistroConvenio) {
        this.fechaCreacionRegistroConvenio = fechaCreacionRegistroConvenio;
    }

    /**
     * @return the nombreUsuarioGenesys
     */
    public String getNombreUsuarioGenesys() {
        return nombreUsuarioGenesys;
    }

    /**
     * @param nombreUsuarioGenesys
     *        the nombreUsuarioGenesys to set
     */
    public void setNombreUsuarioGenesys(String nombreUsuarioGenesys) {
        this.nombreUsuarioGenesys = nombreUsuarioGenesys;
    }

    /**
     * @return the listaDocumentosSoporte
     */
    public List<DocumentoSoporteConvenioModeloDTO> getListaDocumentosSoporte() {
        return listaDocumentosSoporte;
    }

    /**
     * @param listaDocumentosSoporte
     *        the listaDocumentosSoporte to set
     */
    public void setListaDocumentosSoporte(List<DocumentoSoporteConvenioModeloDTO> listaDocumentosSoporte) {
        this.listaDocumentosSoporte = listaDocumentosSoporte;
    }

}
