package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.enumeraciones.archivos.TipoPropietarioArchivoEnum;
import javax.ws.rs.FormParam;

public class ActualizarDatosIdentificacionDTO extends InformacionArchivoDTO {
    
    @FormParam("nuevoDocumento")
    private String nuevoDocumento;
    @FormParam("nuevoTipoDto")
    private TipoIdentificacionEnum nuevoTipoDto;

    // Constructor vacío
    public ActualizarDatosIdentificacionDTO() {
        super();
    }

    // Constructor lleno
    public ActualizarDatosIdentificacionDTO(String nuevoDocumento, 
                                            TipoIdentificacionEnum nuevoTipoDto,
                                            String processName,
                                            String idInstanciaProceso,
                                            String docName,
                                            String description,
                                            TipoIdentificacionEnum tipoIdentificacionPropietario,
                                            String numeroIdentificacionPropietario,
                                            TipoPropietarioArchivoEnum tipoPropietario,
                                            Long idRequisito,
                                            Long idSolicitud,
                                            Long idPersona,
                                            String jsonMetadata,
                                            boolean front,
                                            String fileName,
                                            String fileType,
                                            byte[] dataFile,
                                            String identificadorDocumento,
                                            String versionDocumento,
                                            Long size,
                                            String pathFile,
                                            Long fechaModificacion,
                                            boolean clasificable,
                                            String tipoCarga) {
        super();
        this.setProcessName(processName);
        this.setIdInstanciaProceso(idInstanciaProceso);
        this.setDocName(docName);
        this.setDescription(description);
        this.setTipoIdentificacionPropietario(tipoIdentificacionPropietario);
        this.setNumeroIdentificacionPropietario(numeroIdentificacionPropietario);
        this.setTipoPropietario(tipoPropietario);
        this.setIdRequisito(idRequisito);
        this.setIdSolicitud(idSolicitud);
        this.setIdPersona(idPersona);
        this.setJsonMetadata(jsonMetadata);
        this.setFront(front);
        this.setFileName(fileName);
        this.setFileType(fileType);
        this.setDataFile(dataFile);
        this.setIdentificadorDocumento(identificadorDocumento);
        this.setVersionDocumento(versionDocumento);
        this.setSize(size);
        this.setPathFile(pathFile);
        this.setFechaModificacion(fechaModificacion);
        this.setClasificable(clasificable);
        this.setTipoCarga(tipoCarga);

        this.nuevoDocumento = nuevoDocumento;
        this.nuevoTipoDto = nuevoTipoDto;
    }

    // Getters y Setters
    public String getNuevoDocumento() {
        return nuevoDocumento;
    }

    public void setNuevoDocumento(String nuevoDocumento) {
        this.nuevoDocumento = nuevoDocumento;
    }

    public TipoIdentificacionEnum getNuevoTipoDto() {
        return nuevoTipoDto;
    }

    public void setNuevoTipoDto(TipoIdentificacionEnum nuevoTipoDto) {
        this.nuevoTipoDto = nuevoTipoDto;
    }
}
