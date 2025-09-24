package com.asopagos.archivos.service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import com.asopagos.archivos.dto.RespuestaECMExternoDTO;
import com.asopagos.dto.InformacionArchivoClasificacionDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion: </b> Interface para la exposición de servicios de gestión de
 * archivos
 * <b>Historia de Usuario:</b>
 *
 * @author Sergio Briñez <a href="mailto:sbrinez@heinsohn.com.co"></a>
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co"></a>
 */
@Path("archivos")
@Produces(MediaType.APPLICATION_JSON)
public interface ArchivosService {

    /**
     * Almacena el archivo y su información enviada en <code>infoFile</code>
     * @param infoFile
     *        Contiene la información del archivo a almacenar. Nombre del archivo, id de instancia de proceso realacionado e información de
     *        archivo
     * @param userDTO
     * @return <code>InformacionArchivoDTO</code>
     *         Clase que representa los metadatos de los archivos a cargar en el ECM.
     */
    @POST
    @Path("/almacenarArchivo")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public InformacionArchivoDTO almacenarArchivo(@MultipartForm InformacionArchivoDTO infoFile, @Context UserDTO userDTO);

    /**
     * Método que recupera el archivo identificado por
     * <code>identificadorArchivo</code>
     * 
     * @param identificadorArchivo
     * @param versionDocumento
     * @param userDTO
     * @return 
     */
    @GET
    @Path("{identificadorArchivo}")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public MultipartFormDataOutput consultarArchivo(@PathParam("identificadorArchivo") String identificadorArchivo,
            @QueryParam("versionDocumento") String versionDocumento, @Context UserDTO userDTO);

    /**
     * Método que recupera el archivo identificado por
     * <code>identificadorArchivo</code> adicionando metadata de archivo en el header de response para visualización en UI
     * 
     * @param identificadorArchivo
     * @param versionDocumento
     * @param toDownload
     * @param userDTO
     * @return 
     */
    @GET
    @Path("/infoHeader/{identificadorArchivo}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM })
    public Response consultarArchivoInfoHeader(@PathParam("identificadorArchivo") String identificadorArchivo,
            @QueryParam("versionDocumento") String versionDocumento, @QueryParam("toDownload") boolean toDownload,
            @Context UserDTO userDTO);

    /**
     * Método que recupera el archivo identificado por
     * <code>identificadorArchivo</code>
     * 
     * @param identificadorArchivo
     * @param userDTO
     * @return
     */
    @GET
    @Path("/obtenerArchivo/{identificadorArchivo}")
    public InformacionArchivoDTO obtenerArchivo(@PathParam("identificadorArchivo") String identificadorArchivo, @Context UserDTO userDTO);

    /**
     * Método que recupera el archivo identificado por
     * <code>identificadorArchivo</code>
     * 
     * @param objInformacionConvertDTO
     * @param userDTO
     * @return 
     */
    @POST
    @Path("/convertHTMLtoPDF")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public byte[] convertHTMLtoPDF(InformacionConvertDTO objInformacionConvertDTO, @Context UserDTO userDTO);

    /**
     * Método que elimina una familia de archivos identificado por <code>identificadorArchivo</code>en el ECM
     * 
     * @param identificadorArchivo
     *        <code>String</code> El identificador de la familia de archivos a eliminar
     * @param userDTO
     * 
     */
    @DELETE
    @Path("/eliminarArchivo/{identificadorArchivo}")
    public void eliminarArchivo(@PathParam("identificadorArchivo") String identificadorArchivo, @Context UserDTO userDTO);

    /**
     * Método que elimina un archivo identificado por
     * <code>identificadorArchivo</code> y <code>versionDocumento</code> en el ECM
     * 
     * @param identificadorArchivo
     *        <code>String</code> El identificador de la familia de archivos a eliminar
     * @param versionDocumento
     * @param userDTO
     * 
     */
    @POST
    @Path("/eliminarArchivo/{identificadorArchivo}")
    public void eliminarVersionArchivo(@PathParam("identificadorArchivo") String identificadorArchivo,
            @QueryParam("versionDocumento") String versionDocumento, @Context UserDTO userDTO);
    
    /**
     * Analiza el contenido de un archivo PDF y lo separa en varios archivos 
     * usando como separador el hallazgo de un código de barras
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	infoFile
	 * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Clase que representa los metadatos de los archivos a cargar en el ECM
     * 
     * @param 	userDTO
     * 		  	<code>UserDTO</code>
     * 		  	Clase que representa los datos de un usuario autenticado
     * 
     * @return <code>InformacionArchivoClasificacionDTO</code>
     * 		   Estrutura de datos que representa la informacion del procesamiento de analisis y 
     * 	  	   clasificación de un archivo procedente para escaneo masivo  
     */
    @POST
    @Path("/analizarPDF")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public InformacionArchivoClasificacionDTO analizarYSepararArchivoPDF(
    		@MultipartForm InformacionArchivoDTO infoFile, @Context UserDTO userDTO);
    
    /**
     * Método que persiste la información devuelta por el ECM externo al enviarle la metadata de un archivo.
     * 
     * @param respuesta <code>com.asopagos.archivos.dto.RespuestaECMExternoDTO</code>
     * 			DTO con la información enviada por el ECM externo. 
     */
    @POST
    @Path("/guardarRespuestaECMExterno")
    public void guardarRespuestaECMExterno(RespuestaECMExternoDTO respuesta);
}
