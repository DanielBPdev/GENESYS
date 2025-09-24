package com.asopagos.archivos.almacenamiento;

import javax.ejb.Local;
import javax.ws.rs.core.Response;
import com.asopagos.dto.InformacionArchivoDTO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

/**
 * Interface que define la funcionanlidad de cualquier manejador de 
 * almacenamiento de archivos.
 * @author sbrinez
 */
@Local
public interface IAlmacenamientoArchivos {
    
    static final String VERSION_CLIENT_SEPARATOR = "_";
    
    public InformacionArchivoDTO almacenarArchivo(InformacionArchivoDTO inDTO);
    
    public InformacionArchivoDTO obtenerArchivo(String identificadorArchivo);
    
    public MultipartFormDataOutput consultarArchivo(String identificadorArchivo, String versionDocumento);
    
    public Response consultarArchivoInfoHeader(String identificadorArchivo, String versionDocumento, boolean toDownload);
    
    public void eliminarArchivo(String identificadorArchivo);
    
    public void eliminarVersionArchivo(String identificadorArchivo, String versionDocumento);
    
}
