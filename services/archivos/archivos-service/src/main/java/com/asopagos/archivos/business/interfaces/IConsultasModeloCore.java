package com.asopagos.archivos.business.interfaces;

import java.util.List;
import javax.ejb.Local;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.archivos.ArchivoAlmacenado;
import com.asopagos.entidades.ccf.archivos.PropietarioArchivo;
import com.asopagos.entidades.ccf.archivos.VersionArchivo;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos Core para el servicio de <code>ArchivosService</code><br/>
 * 
 * <b>Módulo:</b> Asopagos - HU-TRA-444 <br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 */
@Local
public interface IConsultasModeloCore {

	/**
     * Metodo que permite realizar la clasificacion de un requisito documental
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	informacionArchivoDTO
     * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Estructura de datos con la informacion del archivo a clasificar
     * 
     * @return  <code>boolean</code>
     * 		 	Si se logro realizar el proceso o no 			
     */
    public boolean clasificarRequisitoDocumental(InformacionArchivoDTO informacionArchivoDTO);
    
    /**
     * Metodo que permite realizar la consulta de un requisito documental asociado a una 
     * solicitud, requisito y persona
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	idSolicitud
     * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @param 	idRequisito
     * 	 	  	<code>Long</code>
     * 		  	El identificador del requisito documental asociado
     * 
     * @param 	idPersona
     * 	 	  	<code>Long</code>
     * 		  	El identificador de la persona
     * 
     * @return  <code>ItemChequeo</code>
     * 		 	El registro del requisito documental encontrado 			
     */
    public ItemChequeo buscarRequisitoDocumental(Long idSolicitud, Long idRequisito, Long idPersona);
	
	 /**
     * Metodo que permite crear el registro de PropietarioArchivo 
     * para una nueva solicitud de almacenamiento del archivo en CGS 
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	infoFile
     * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Estructura de datos con la informacion del archivo
     * 
	 * @return <code>PropietarioArchivo</code>
	 * 		  El registro que representa al dueño o propietario de un archivo almacenado
     */
	public PropietarioArchivo registrarPropietarioArchivo(InformacionArchivoDTO infoFile);
	
	/**
     * Metodo que permite crear el registro de ArchivoAlmacenado 
     * para una nueva solicitud de almacenamiento del archivo en CGS 
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     *
     * @param 	idPropietario
     * 	 	  	<code>Long</code>
     * 		  	EL identificador del propietario del documento
     * 
     * @param 	infoFile
     * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Estructura de datos con la informacion del archivo
     * 
	 * @return <code>ArchivoAlmacenado</code>
	 * 		  El registro que representa una archivo en un sistema de almacenamiento
     */
	public ArchivoAlmacenado registrarArchivoAlmacenado(Long idPropietario, InformacionArchivoDTO infoFile);
	
	/**
     * Metodo que permite crear el registro de VersionArchivo 
     * para una nueva solicitud de almacenamiento del archivo en CGS 
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     *
     * @param 	idArchivoAlmacenado
     * 	 	  	<code>Long</code>
     * 		  	EL identificador que representa una archivo en un sistema de almacenamiento
	 *
     * @param 	nombreArchivo
     * 	 	  	<code>String</code>
     * 		  	EL nombre del archivo en el sistema de almacenamiento
     *
     * @param 	version
     * 	 	  	<code>String</code>
     * 		  	EL numero de la version del archivo en el sistema de almacenamiento
     * 
     * @param 	infoFile
     * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Estructura de datos con la informacion del archivo
     * 
	 * @return <code>ArchivoAlmacenado</code>
	 * 		  El registro que representa una archivo en un sistema de almacenamiento
     */
	public VersionArchivo registrarVersionArchivo( Long idArchivoAlmacenado, 
		String nombreArchivo, String version, InformacionArchivoDTO infoFile );
	
	/**
     * Metodo que permite consultar un propietario de documento por tipo y numero de documento y tipo de propietario
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	infoFile
     * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Estructura de datos con la informacion del archivo
     * 
	 * @return <code>Long</code>
	 * 		   El identificador del propietario del documento
     */
	public Long consultarPropietarioDocumento( InformacionArchivoDTO infoFile );
	
	/**
     * Metodo que permite consultar un archivo en un sistema de almacenamiento 
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	idPropietario
     * 	 	  	<code>Long</code>
     * 		  	EL identificador del propietario del documento
     * 
     * @param 	infoFile
     * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Estructura de datos con la informacion del archivo
     * 
	 * @return <code>Long</code>
	 * 		   El identificador de un archivo almacenado en el sistema 
     */
	public Long consultarRequisitoPropietarioDocumento(Long idPropietario, InformacionArchivoDTO infoFile );
	
	/**
	 * Metodo que permite realizar la eliminación de un registro de la 
	 * entidad <code>VersionArchivo</code> por identificador del archivo 
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param identificadorArchivo
	 * 		  <code>String</code>
	 * 		  El identificador del archivo a eliminar
	 *  
	 * @return <code>boolean</code>
	 * 	       Resultado de la operación Si fue satisfactoria o no 
	 */
	public boolean eliminarRegistroVersionArchivo(String identificadorArchivo);
	
	/**
	 * Metodo que permite realizar la eliminación de un registro de la 
	 * entidad <code>VersionArchivo</code> por identificador y version del archivo 
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param identificadorArchivo
	 * 		  <code>String</code>
	 * 		  El identificador del archivo a eliminar
	 * 
	 * @param versionDocumento
	 * 		  <code>String</code>
	 * 		  La version del archivo a eliminar
	 *  
	 * @return <code>boolean</code>
	 * 	       Resultado de la operación Si fue satisfactoria o no 
	 */
	public boolean eliminarRegistroVersionArchivo(String identificadorArchivo, String versionDocumento);
	
	/**
	 * Metodo que permite realizar la consulta de los identificadores de la entidad <code>VersionArchivo</code> 
	 * por identificador del archivo 
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param identificadorArchivo
	 * 		  <code>String</code>
	 * 		  El identificador del archivo a buscar
	 *  
	 * @return <code>List<Long></code>
	 * 	       La lista con los identificadores de los registros de <code>VersionArchvo</code>
	 * 		   asociados al identificador de archivo indicado por parametro 
	 */
	public List<Long> consultarRegistrosVersionArchivoPorId(String identificadorArchivo);
	
	/**
	 * Metodo que permite realizar la eliminación de un registro de la 
	 * entidad <code>ArchivoAlmacenado</code> por identificador del archivo 
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param identificadorArchivo
	 * 		  <code>Long</code>
	 * 		  El identificador del archivo asocidado a eliminar
	 *  
	 * @return <code>boolean</code>
	 * 	       Resultado de la operación Si fue satisfactoria o no 
	 */
	public boolean eliminarRegistroArchivoAlmacenado(Long idArchivoAlmacenado);
	
	/**
	 * Método encargado de consultar la metadata restante de un archivo.
	 * 
	 * @param tipoId
	 * 			<code>TipoIdentificacionEnum</code>
	 * 			tipo de dentificación de la persona.
	 * 
	 * @param numeroId
	 * 			<code>String</code>
	 * 			númerod e identificación de la persona.
	 * 
	 * @return <code>List<Object[]></code>
	 * 			Lista con la información consultada.
	 */
	public List<Object[]> consultarMetadataArchivo(TipoIdentificacionEnum tipoId, String numeroId);
}
