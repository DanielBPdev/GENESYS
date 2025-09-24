package com.asopagos.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;
import javax.ws.rs.core.MediaType;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.poi.util.IOUtils;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import co.com.heinsohn.lion.common.dto.FileLocation;
import co.com.heinsohn.lion.common.enums.Protocolo;
import co.com.heinsohn.lion.common.exception.LionCommonException;
import co.com.heinsohn.lion.common.util.Constants;
import co.com.heinsohn.lion.common.util.FileManagerUtil;

/**
 * <b>Descripcion:</b> Clase que se encarga de realizar una conexión con un servidor FTP para la lectura de sus archivos
 * de acuerdo a los atributos parametrizados<br/>
 *
 * @author <a href="mailto:jpiraban@heinsohn.com.co"> jpiraban</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co">Roy López Cardona.</a>
 */
public class ConexionServidorFTPUtil<E extends InformacionArchivoDTO> {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ConexionServidorFTPUtil.class);

    /**
     * Este atributo contiene el nombre del host
     */
    private String nombreHost;

    /**
     * Este atributo contiene el puerto del host
     */
    private String puerto;

    /**
     * Este atributo contiene el nombre del usuario
     */
    private String nombreUsuario;

    /**
     * Este atributo contiene la contraseña del usuario
     */
    private String contrasena;

    /**
     * Este atributo contiene el protocolo a emplear
     */
    private Protocolo protocolo;

    /**
     * Este atributo administra el acceso a archivos y directorios
     */
    private FileManagerUtil miFileManagerUtil;

    /**
     * Este atributo contiene la direccion del archivo
     */
    private String urlArchivos;

    /**
     * Este atributo contiene la conexion al archivo
     */
    private FileLocation miFileLocation;

    /**
     * Este atributo contiene los archivos encontrados
     */
    private List<E> archivosDescargados;

    /**
     * Constante para indicar el caracter para la separación de carpetas en el nombre del archivo
     */
    private String processName;

    /**
     * Constante para indicar el caracter para la separación de carpetas en el nombre del archivo
     */

    private static final String SEPARADOR_SUBDIRECTORIO = "/";

    /**
     * Constante para indicar el tipo de archivos descargados
     */
    private static final String TIPO_ARCHIVOS = MediaType.TEXT_PLAIN;

    /**
     * Constante que indica el protocolo de seguridad TLS
     */
    private static final String PROTOCOLO_SEGURIDAD = "TLS";

    /**
     * Este atributo almacena informacion sesion server SFTP
     */
    private Session session;

    /**
     * Este atributo almacena informacion conexion server SFTP
     */
    private ChannelSftp channelSftp;

    /**
     * Este atributo almacena informacion conexion server SFTP
     */
    private SSLSessionReuseFTPSClient miFTPSClient;

    /**
     * Este atributo contiene el acceso del operador
     */
    private FTPClient miFTPClient;

    /**
     * Este atributo contiene la clase
     */
    private Class<E> clase;

    /**
     * Constructor de la clase utilitaria
     * @param processName
     *        nombre del proceso asociado a los archivos que se leen del FTP
     * 
     * @param clazz
     *        Clase asociada al DTO que se desea obtener
     */
    public ConexionServidorFTPUtil(String processName, Class<E> clazz) {
        this.processName = processName;
        this.clase = clazz;
    }

    /**
     * Método general para la lectura de archivos remotos
     */
    public void conectarYRecorrer() {
        logger.debug("Inicia conectarYRecorrer()");
        crearFileLocation();
        obtenerArchivosDirectorio();
        logger.debug("Finaliza conectarYRecorrer()");
    }
    
    /**
     * Método para recorrer el FTP sin descargar el contenido del mismo 
     * */
    public void conectarRecorrerNoDescargar(){
        logger.info("Inicia conectarRecorrerNoDescargar()");
        crearFileLocation();

        String mensaje = null;

        archivosDescargados = new ArrayList<>();

        try {
            // se recorre el directorio remoto de acuerdo al protocolo especificado
            switch (protocolo) {
                case FTP:
                    miFTPClient = crearConexionFTP();
                    if (miFTPClient != null && miFTPClient.listFiles() != null) {

                        directorioRecursivo(miFTPClient, miFTPClient.printWorkingDirectory());
                    }
                    cerrarConexionFTP();
                    break;
                case FTPS:

                    miFTPSClient = crearConexionFTPS();

                    if (miFTPSClient != null && miFTPSClient.listFiles() != null) {
                        directorioRecursivo(miFTPSClient, miFTPSClient.printWorkingDirectory());
                    }

                    cerrarConexionFTPS();
                    break;
                case SFTP:

                    boolean conected = crearConexionSFTP();
                    if (conected && channelSftp != null) {
                        logger.info("Inicia conectarRecorrerNoDescargar - directorioRecursivo " );
                        directorioRecursivo(channelSftp, channelSftp.pwd());
                    }
                    cerrarConexionSFTP();
                    break;
                default:
                    break;

            }
        } catch (IOException | SftpException e) {
            mensaje = MensajesFTPErrorComunesEnum.ERROR_LECTURA_ARCHIVOS.getReadableMessage(this.getProtocolo().toString(), e.getMessage());

            logger.info("Finaliza conectarRecorrerNoDescargar() - " + mensaje);
            throw new TechnicalException(mensaje, e.getCause());
        }
        
        logger.info("Finaliza conectarRecorrerNoDescargar()");
    }
    
    /**
     * Método para recorrer el FTP sin descargar el contenido del mismo 
     * */
    public void conectarYDescargar(){
        logger.info("Inicia conectarYDescargar()");
        crearFileLocation();

        String mensaje = null;

        try {
            // se recorre el directorio remoto de acuerdo al protocolo especificado
            switch (protocolo) {
                case FTP:
                    miFTPClient = crearConexionFTP();
                    if (miFTPClient != null && miFTPClient.listFiles() != null) {

                        descargarContenidoRemoto();
                    }
                    cerrarConexionFTP();
                    break;
                case FTPS:

                    miFTPSClient = crearConexionFTPS();

                    if (miFTPSClient != null && miFTPSClient.listFiles() != null) {
                        descargarContenidoRemoto();
                    }

                    cerrarConexionFTPS();
                    break;
                case SFTP:

                    boolean conected = crearConexionSFTP();
                    if (conected && channelSftp != null) {
                        descargarContenidoRemoto();
                    }
                    cerrarConexionSFTP();
                    break;
                default:
                    break;

            }
        } catch (IOException e) {
            mensaje = MensajesFTPErrorComunesEnum.ERROR_LECTURA_ARCHIVOS.getReadableMessage(this.getProtocolo().toString(), e.getMessage());

            logger.info("Finaliza conectarYDescargar() - " + mensaje);
            throw new TechnicalException(mensaje, e.getCause());
        }
        
        logger.info("Finaliza conectarYDescargar()");
    }

    /**
     * Procedimiento para iniciar el FileLocation del objeto
     */
    private void crearFileLocation() {
        logger.debug("Inicia crearFileLocation()");
        miFileLocation = new FileLocation();

        miFileLocation.setPuerto(this.getPuerto());
        miFileLocation.setHost(this.getNombreHost());
        miFileLocation.setUsuario(this.getNombreUsuario());
        miFileLocation.setContrasena(this.getContrasena());
        miFileLocation.setProtocolo(this.getProtocolo());

        miFileLocation.setLocalPath("");
        miFileLocation.setRemotePath(this.getNombreHost());
        logger.debug("Finaliza crearFileLocation()");
    }

    /**
     * Procedimmiento conectar con un servidor y recorrer una carpeta remota
     */
    private void obtenerArchivosDirectorio() {
        logger.debug("Inicia obtenerArchivosDirectorio()");

        String mensaje = null;

        archivosDescargados = new ArrayList<>();

        try {
            // se recorre el directorio remoto de acuerdo al protocolo especificado
            switch (protocolo) {
                case FTP:
                    miFTPClient = crearConexionFTP();
                    if (miFTPClient != null && miFTPClient.listFiles() != null) {

                        directorioRecursivo(miFTPClient, miFTPClient.printWorkingDirectory());
                    }
                    descargarContenidoRemoto();
                    cerrarConexionFTP();
                    break;
                case FTPS:

                    miFTPSClient = crearConexionFTPS();

                    if (miFTPSClient != null && miFTPSClient.listFiles() != null) {
                        directorioRecursivo(miFTPSClient, miFTPSClient.printWorkingDirectory());
                        descargarContenidoRemoto();
                    }

                    cerrarConexionFTPS();
                    break;
                case SFTP:

                    boolean conected = crearConexionSFTP();
                    if (conected && channelSftp != null) {
                        directorioRecursivo(channelSftp, channelSftp.pwd());
                        descargarContenidoRemoto();
                    }
                    cerrarConexionSFTP();
                    break;
                default:
                    break;

            }
        } catch (IOException | SftpException e) {
            mensaje = MensajesFTPErrorComunesEnum.ERROR_LECTURA_ARCHIVOS.getReadableMessage(this.getProtocolo().toString(), e.getMessage());

            logger.debug("Finaliza obtenerArchivosDirectorio() - " + mensaje);
            throw new TechnicalException(mensaje, e.getCause());
        }

        logger.debug("Finaliza obtenerArchivosDirectorio()");
    }

    /**
     * Método para establecer una conexion FTP con el servidor
     * 
     * @return FTPClient
     *         Cliente FTP
     */
    public FTPClient crearConexionFTP() {
        logger.debug("Inicia crearConexionFTP()");

        miFTPClient = new FTPClient();

        miFileManagerUtil = new FileManagerUtil();
        try {
            miFTPClient = miFileManagerUtil.connectServerFTP(miFileLocation, true);
            miFTPClient.changeWorkingDirectory(getUrlArchivos());
        } catch (LionCommonException e) {
            logger.error("crearConexionFTP() :: Error al establecer conexión con el servidor - " + e.getMessage());
        } catch (IOException e) {
            logger.error("crearConexionFTP() :: Error al ingresar al directorio parametrizado - " + e.getMessage());
        }

        logger.debug("Finaliza crearConexionFTP()");
        return miFTPClient;
    }

    /**
     * Ese metodo se encarga de cerrar la conexión con el servidor FTP
     */
    public void cerrarConexionFTP() throws IOException {
        logger.debug("Inicia cerrarConexionFTP()");
        if (miFileManagerUtil != null) {
            miFileManagerUtil.disconnectServerFTP(miFTPClient);
        }
        logger.debug("Finaliza cerrarConexionFTP()");
    }

    /**
     * Método para establecer una conexion FTPS con el servidor
     * 
     * @return SSLSessionReuseFTPSClient
     *         Cliente FTPS
     */
    public SSLSessionReuseFTPSClient crearConexionFTPS() {
        logger.debug("Inicia crearConexionFTPS()");

        String mensaje = null;
        String finLoggerError = "Finaliza crearConexionFTPS() - ";

        miFTPSClient = null;

        //Administrador de conexiones
        miFileManagerUtil = new FileManagerUtil();
        try {
            miFTPSClient = new SSLSessionReuseFTPSClient(PROTOCOLO_SEGURIDAD, false);

            miFTPSClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

            miFTPSClient.connect(miFileLocation.getHost());

            int reply = miFTPSClient.getReplyCode();

            miFTPSClient.execPBSZ(0L);
            miFTPSClient.execPROT("P");

            miFTPSClient.enterLocalPassiveMode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                miFTPSClient = null;

                mensaje = MensajesFTPErrorComunesEnum.ERROR_DESCONEXION_SERVIDOR.getReadableMessage(this.protocolo.toString());

                logger.debug(finLoggerError + mensaje);
                throw new TechnicalException(mensaje, new Throwable());
            }

            if (!miFTPSClient.login(miFileLocation.getUsuario(), miFileLocation.getContrasena())) {
                miFTPSClient.logout();
                miFTPSClient.disconnect();
                miFTPSClient = null;

                mensaje = MensajesFTPErrorComunesEnum.ERROR_LOGIN_SERVIDOR.getReadableMessage(this.protocolo.toString());

                logger.debug(finLoggerError + mensaje);
                throw new TechnicalException(mensaje, new Throwable());
            }
        } catch (IOException e) {
            mensaje = MensajesFTPErrorComunesEnum.ERROR_LOGIN_SERVIDOR.getReadableMessage(this.protocolo.toString());

            logger.debug(finLoggerError + mensaje);
            throw new TechnicalException(mensaje, e.getCause());
        }

        logger.debug("Finaliza crearConexionFTPS()");
        return miFTPSClient;
    }

    /**
     * Este metodo se encarga de cerrar la conexion
     */
    public void cerrarConexionFTPS() throws IOException {
        logger.debug("Inicia cerrarConexionFTPS()");

        miFTPSClient.logout();
        miFTPSClient.disconnect();
        miFTPSClient = null;

        logger.debug("Finaliza cerrarConexionFTPS()");
    }

    /**
     * Metodo que establece la conexión cliente-servidor por SFTP
     * 
     * @throws CoreException
     */
    public boolean crearConexionSFTP() {
        logger.info("Inicia conexionSFTP()");

        String mensaje = null;

        int port = 21;
        int timeOut;
        boolean conected = false;
        try {
            timeOut = Constants.TIMEOUT;
        } catch (Exception e) {
            mensaje = MensajesFTPErrorComunesEnum.ERROR_CONEXION.getReadableMessage(this.getProtocolo().toString(), e.getMessage());

            logger.info("Finaliza conexionSFTP() - " + mensaje);
            throw new TechnicalException(mensaje, e.getCause());
        }
        if (miFileLocation.getPuerto() != null) {
            port = Integer.parseInt(miFileLocation.getPuerto());
        }
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(miFileLocation.getUsuario(), miFileLocation.getHost(), port);
            session.setPassword(miFileLocation.getContrasena());
            java.util.Properties config = new java.util.Properties();
            // strictHostKeyChecking: Especifica cómo se comprueban las claves
            // de host durante la conexión y la fase de autenticación
            // no-> se utiliza un archivo de host conocido de forma
            // "predeterminada" que gestiona el servidor
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            logger.info("CONECTADO: " + session.getHost());
            session.connect(timeOut);
            Channel channel = session.openChannel(Constants.canal);
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(urlArchivos);
            conected = true;
        } catch (Exception e) {
            mensaje = MensajesFTPErrorComunesEnum.ERROR_CONEXION.getReadableMessage(this.getProtocolo().toString(), e.getMessage());

            logger.info("Finaliza conexionSFTP() - " + mensaje);
            throw new TechnicalException(mensaje, e.getCause());
        }

        logger.info("Finaliza conexionSFTP()");
        return conected;
    }

    /**
     * Este metodo se encarga de cerrar la conexion sftp
     */
    public void cerrarConexionSFTP() {
        logger.debug("Inicia cerrarConexionSFTP()");

        if (channelSftp.isConnected())
            channelSftp.disconnect();
        if (session.isConnected())
            session.disconnect();

        logger.debug("Finaliza cerrarConexionSFTP()");
    }

    /**
     * Este metodo se encarga de extraer los archivos con el respectivo protocolo
     * @param miFTPClient
     */
    @SuppressWarnings("unchecked")
    private void directorioRecursivo(FTPClient miFTPClientLocal, String currentDirectory) throws IOException {

        miFTPClientLocal.changeWorkingDirectory(currentDirectory);
        if (miFTPClientLocal.listFiles() != null) {

            for (FTPFile iterable_element : miFTPClientLocal.listFiles()) {
                if (iterable_element.isDirectory()) {
                    if (!iterable_element.getName().endsWith(".")) {
                        directorioRecursivo(miFTPClientLocal, currentDirectory + SEPARADOR_SUBDIRECTORIO + iterable_element.getName());
                    }
                }
                else {

                    InformacionArchivoDTO informacionArchivoDTO;
                    try {
                        informacionArchivoDTO = clase.newInstance();

                        informacionArchivoDTO.setFileName(iterable_element.getName());
                        informacionArchivoDTO.setDocName(iterable_element.getName());
                        informacionArchivoDTO.setPathFile(currentDirectory + SEPARADOR_SUBDIRECTORIO + informacionArchivoDTO.getFileName());
                        informacionArchivoDTO.setProcessName(processName);
                        informacionArchivoDTO.setFileType(TIPO_ARCHIVOS);

                        try {
                            String fecModString = miFTPClientLocal.getModificationTime(informacionArchivoDTO.getPathFile());
                            agregarFechaModArchivo(fecModString, informacionArchivoDTO);
                        } catch (Exception e) {
                            logger.error("Error en la consulta o conversión de la fecha de modificación - " + e.getMessage());
                        }

                        if (informacionArchivoDTO.getFechaModificacion() == null) {
                            informacionArchivoDTO.setFechaModificacion(iterable_element.getTimestamp().getTime().getTime());
                        }

                        archivosDescargados.add((E) informacionArchivoDTO);
                    } catch (InstantiationException e) {
                        logger.error("Error en la conversión de la clase parametrizada - " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        logger.error("Error accediento la clase parametrizada - " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Este metodo extrae los archivos del directorio con protocolo sftp
     * 
     * @param ChannelSftp
     * @param currentDirectory
     *        directorio actual
     */
    @SuppressWarnings("unchecked")
    private void directorioRecursivo(ChannelSftp channelSftpLocal, String currentDirectory) throws SftpException {
        String currentDirectoryTemp = currentDirectory;
        channelSftpLocal.cd(currentDirectoryTemp);
     logger.info("inicia  - directorioRecursivo   " );
        List<ChannelSftp.LsEntry> directorioActua = channelSftpLocal.ls(currentDirectoryTemp);
        if (directorioActua != null) {
            for (ChannelSftp.LsEntry archivoDirectorio : directorioActua) {
                
                SftpATTRS atributos = archivoDirectorio.getAttrs();
        
                
                if (atributos.isDir()) {
                   
                    if (!archivoDirectorio.getFilename().endsWith(".")) {
                        currentDirectoryTemp = channelSftpLocal.pwd();
                        directorioRecursivo(channelSftpLocal,
                                currentDirectoryTemp + SEPARADOR_SUBDIRECTORIO + archivoDirectorio.getFilename());

                        // al terminar de recorrer la subcarpeta se ubica de nuevo en el directorio actual
                        channelSftpLocal.cd(currentDirectoryTemp);
                    }
                }
                else if (channelSftpLocal.isConnected()) {
                
                    InformacionArchivoDTO informacionArchivoDTO;
                    try {
                        logger.info(archivoDirectorio.getFilename());
                        informacionArchivoDTO = clase.newInstance();
                        informacionArchivoDTO.setFileName(archivoDirectorio.getFilename());
                        informacionArchivoDTO.setDocName(archivoDirectorio.getFilename());
                        informacionArchivoDTO.setPathFile(currentDirectoryTemp + SEPARADOR_SUBDIRECTORIO + archivoDirectorio.getFilename()); //
                        informacionArchivoDTO.setProcessName(processName);
                        informacionArchivoDTO.setFileType(TIPO_ARCHIVOS);

                        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
                        Date dateMod;

                        try {
                            dateMod = formatter.parse(atributos.getMtimeString());
                            informacionArchivoDTO.setFechaModificacion(dateMod.getTime());
                        } catch (ParseException e) {
                            logger.error("Error en la conversión de la fecha de modificación - " + e.getMessage());
                        }

                        archivosDescargados.add((E) informacionArchivoDTO);
                    } catch (InstantiationException e) {
                        logger.error("Error en la conversión de la clase parametrizada - " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        logger.error("Error accediento la clase parametrizada - " + e.getMessage());
                    }
                }
            }
     logger.info("Finaliza  - directorioRecursivo archivosDescargados size: " + archivosDescargados.size() );
        }
    }

    /**
     * Método para agregar la fecha de modificación del archivo al DTO de información
     * @param fecModString
     *        Fecha de modificación en String
     * @param informacionArchivoDTO
     *        DTO con la información del archivo
     * @throws ParseException
     *         Excepción de conversión de fecha
     */
    private void agregarFechaModArchivo(String fecModString, InformacionArchivoDTO informacionArchivoDTO) throws ParseException {
        String firmaMetodo = "agregarFechaModArchivo(String, InformacionArchivoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String[] arrayFechaMod = fecModString.split(" ");
        if (arrayFechaMod != null && arrayFechaMod.length > 0) {
            String timePart = arrayFechaMod[1];
            Date fechaMod = dateFormat.parse(timePart);

            informacionArchivoDTO.setFechaModificacion(fechaMod.getTime());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para la lectura del contenido de un archivo almacenado remotamente
     * 
     * @param archivo
     *        Definición de los datos de la ubicación del archivo a leer
     */
    private void descargarContenidoRemoto() {
        logger.debug("Inicia descargarContenidoRemoto()");

        try {
            // se define el proceso con base en el protocolo empleado
            switch (miFileLocation.getProtocolo()) {
                case FTP:

                    if (miFTPClient != null) {
                        miFTPClient.setFileType(FTP.BINARY_FILE_TYPE);

                        leerArchivoFTPyFTPS(miFTPClient);
                    }
                    break;
                case FTPS:
                    if (miFTPSClient != null) {
                        miFTPSClient.setFileType(FTP.BINARY_FILE_TYPE);

                        leerArchivoFTPyFTPS(miFTPSClient);
                    }

                    break;
                case SFTP:
                    if (channelSftp.isConnected()) {
                        leerArchivoSFTP();
                    }
                    break;
                default:
                    break;
            }

        } catch (SftpException | IOException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_DESCARGA_CONTENIDO.getReadableMessage(this.getProtocolo().toString());

            logger.debug("Finaliza descargarContenidoRemoto() - " + mensaje);
            throw new TechnicalException(mensaje, new Throwable());
        }

        logger.debug("Finaliza descargarContenidoRemoto()");
    }

    /**
     * Método para la lectura de archivos por FTP o FTPS
     * @param client
     *        Objeto con el cliente de la conexión al servidor
     * @throws IOException
     *         Error de lectura de InputStream
     */
    private void leerArchivoFTPyFTPS(Object client) throws IOException {
        String firmaMetodo = "leerArchivoFTPyFTPS(Object)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        int index = archivosDescargados.size() - 1;

        if (index >= 0) {
            Boolean completePendingCommand = null;

            do {
                InformacionArchivoDTO informacionArchivo = archivosDescargados.get(index);
                InputStream fileBytes = null;

                if (client instanceof FTPClient) {
                    fileBytes = ((FTPClient) client).retrieveFileStream(informacionArchivo.getPathFile());
                    completePendingCommand = ((FTPClient) client).completePendingCommand();
                }
                else if (client instanceof FTPSClient) {
                    fileBytes = ((FTPSClient) client).retrieveFileStream(informacionArchivo.getPathFile());
                    completePendingCommand = ((FTPSClient) client).completePendingCommand();
                }

                if (fileBytes != null) {
                    informacionArchivo.setDataFile(IOUtils.toByteArray(fileBytes));
                    informacionArchivo.setSize(Long.valueOf(informacionArchivo.getDataFile().length));
                }
                index--;
            } while (!(index < 0 && completePendingCommand));
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para la lectura de archivos por SFTP
     * @throws SftpException
     *         Error de lectura por SFTP
     * @throws IOException
     *         Error de lectura de InputStream
     */
    private void leerArchivoSFTP() throws SftpException, IOException {
        String firmaMetodo = "leerArchivoSFTP()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        int index = archivosDescargados.size() - 1;

        if (index >= 0) {
            do {
                InformacionArchivoDTO archivoPilaDTO = archivosDescargados.get(index);

                InputStream fileBytes = channelSftp.get(archivoPilaDTO.getPathFile());

                archivoPilaDTO.setDataFile(IOUtils.toByteArray(fileBytes));
                archivoPilaDTO.setSize(Long.valueOf(archivoPilaDTO.getDataFile().length));
                index--;
            } while (index >= 0);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método que se encarga de subir un archivo al servidor FTP
     * @param fileName
     *        nombre del archivo
     * @param contenido
     *        información del archivo
     */
    public void subirArchivoFTP(String fileName, byte[] contenido) {
        String firmaMetodo = "subirArchivoFTP(String, byte[])";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;
        try {
            crearFileLocation();
            FileManagerUtil fileManager = new FileManagerUtil();
            miFileLocation.setRemotePath(getUrlArchivos());
            fileManager.setFileIntoUbication(contenido, fileName, miFileLocation, true);

        } catch (LionCommonException e) {
            mensaje = MensajesFTPErrorComunesEnum.ERROR_ESCRITURA_ARCHIVOS.getReadableMessage(this.nombreHost);
            logger.debug("Finaliza obtenerArchivosDirectorio() - " + mensaje);
            logger.error("Finaliza obtenerArchivosDirectorio() - " + mensaje + "explica " +e);
            throw new TechnicalException(mensaje, e.getCause());
        }
    }

    /**
     * @return the nombreHost
     */
    public String getNombreHost() {
        return nombreHost;
    }

    /**
     * @param nombreHost
     *        the nombreHost to set
     */
    public void setNombreHost(String nombreHost) {
        this.nombreHost = nombreHost;
    }

    /**
     * @return the puerto
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     * @param puerto
     *        the puerto to set
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario
     *        the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena
     *        the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the miFileManagerUtil
     */
    public FileManagerUtil getMiFileManagerUtil() {
        return miFileManagerUtil;
    }

    /**
     * @param miFileManagerUtil
     *        the miFileManagerUtil to set
     */
    public void setMiFileManagerUtil(FileManagerUtil miFileManagerUtil) {
        this.miFileManagerUtil = miFileManagerUtil;
    }

    /**
     * @return the urlArchivos
     */
    public String getUrlArchivos() {
        return urlArchivos;
    }

    /**
     * @param urlArchivos
     *        the urlArchivos to set
     */
    public void setUrlArchivos(String urlArchivos) {
        this.urlArchivos = urlArchivos;
    }

    /**
     * @return the miFileLocation
     */
    public FileLocation getMiFileLocation() {
        return miFileLocation;
    }

    /**
     * @param miFileLocation
     *        the miFileLocation to set
     */
    public void setMiFileLocation(FileLocation miFileLocation) {
        this.miFileLocation = miFileLocation;
    }

    /**
     * @return the archivosDescargados
     */
    public List<E> getArchivosDescargados() {
        return archivosDescargados;
    }

    /**
     * @param archivosDescargados
     *        the archivosDescargados to set
     */
    public void setArchivosDescargados(List<E> archivosDescargados) {
        this.archivosDescargados = archivosDescargados;
    }

    /**
     * @return the protocolo
     */
    public Protocolo getProtocolo() {
        return protocolo;
    }

    /**
     * @param protocolo
     *        the protocolo to set
     */
    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }

}
