package com.asopagos.constantes.parametros.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 *
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
    /**
     * Busca la sede de la caja de compensacion por identificador 
     */
    public static final String BUSCAR_SEDE_CAJA_COMPENSACION = "buscar.sede.caja.compensacion";
    
    /**
     * Busca todas las constantes 
     */
    public static final String BUSCAR_CONSTANTES = "buscar.constantes";
    
    /**
     * Busca todos los parametros
     */
    public static final String BUSCAR_PARAMETROS = "buscar.parametros";
    
    /**
     * Busca los parametros por el nombre 
     */
    public static final String BUSCAR_PARAMETROS_POR_NOMBRE = "buscar.parametros.por.nombre";
    
    /**
     * Busca las dependencias en AreaCajaCompensacion 
     */
    public static final String BUSCAR_DEPENDENCIAS = "buscar.dependencias";
    
    /**
     * Busca las dependencias en AreaCajaCompensacion por id
     */
    public static final String BUSCAR_DEPENDENCIAS_ID = "buscar.dependencias.id";
    

    /**
     * Busca las constantes que serán cargadas en el caché al inicio de la aplicación
     */
    public static final String BUSCAR_CONSTANTES_INICIO = "buscar.constantes.inicio";
    

    /**
     * Busca los parametros que serán cargados en el caché al inicio de la aplicación
     */
    public static final String BUSCAR_PARAMETROS_INICIO = "buscar.parametros.inicio";
    
    /**
     * Busca el valor de la costante dado su nombre
     */
    public static final String OBTENER_CONSTANTE = "constantes.parametros.obtener.constante";
    
    /**
     * Busca el valor del parametro dado su nombre
     */
    public static final String OBTENER_PARAMETRO = "constantes.parametros.obtener.parametro";
    
    /**
     * Busca los beneficios dada una lista de ellos
     */
    public static final String BUSCAR_BENEFICIOS = "constantes.parametros.buscar.beneficios";
    
    /**
     * Busca un parametro en base a un nombre dado
     */
    public static final String CONSULTAR_ACTUALIZAR_PARAMETRO = "Gestor.cache.consultar.actualizar.parametros";
    
    /**
     * Constante que representa la consulta de la caja de compensación dado su id.
     */
    public static final String CONSULTAR_CAJA_COMPENSACION = "Parametro.cosultar.cajaCompensacion";
    
    /**
     * Constante que representa la consulta del operadorInformacionCcf.
     */
    public static final String BUSCAR_OPERADOR_INFORMACION_CCF_POR_ID_OI = "Parametro.buscar.operadorInformacionYcajaPorId";
    
    /**
     * Constante que representa la consulta del operador de información.
     */
    public static final String BUSCAR_OPERADOR_INFORMACION = "Parametro.buscar.operadorInformacion";
    
    /**
     * Constante que representa la consulta de la conexión al FTP del operador de información dado el id del registro. 
     */
    public static final String BUSCAR_CONEXION_OPERADOR_INFORMACION_POR_ID = "Parametro.buscar.datosConexionOperadorInformacionPorId";
    
    /**
     * Constante que representa la consulta de las conexiones a los operadores de información que cumplan los filtro de busqueda.
     */
    public static final String CONSULTAR_CONEXIONES_OPERADOR_INFORMACION = "Parametro.buscar.conexionesOperadorInformacion";
    
    /**
     * Constante que representa la consulta de los operadores de información registrados en Genesys
     */
    public static final String CONSULTAR_OPERADORES_INFORMACION = "Parametro.buscar.operadoresInformacion";

    /**
     * Constante que representa el ID de un item de parametrizacionGaps.
     */
    public static final String CONSULTAR_ID_PARAMETRIZACION_GAPS = "Parametro.consultar.ParametrizacionGaps.id";

       /**
     * Constante que representa la consulta de un parametro Gap.
     */
    public static final String CONSULTAR_ESTADO_PARAMETRIZACION_GAPS ="Parametro.consultar.ParametrizacionGaps.id.estado";
    
    
    /**
     * Constante que representa la consulta del parametro de margen de tolerancia aportes para pila y pagos manuales
     */
    public static final String BUSCAR_PARAMETRO_MARGEN_TOLERANCIA_APORTE = "Parametro.buscar.parametroMargenToleranciaAporte";

    /**
     * Consultar los valores UVT
     */
    public static final String CONSULTAR_VALORES_UVT = "buscar.valores.uvt";

    /**
     * Consultar los valores UVT por Id
     */
    public static final String CONSULTAR_VALORES_UVT_POR_ID = "buscar.valoruvt.por.id";

    /**
     * Consultar los valores UVT por Id
     */
    public static final String CONSULTAR_VALORES_UVT_POR_ANIO = "buscar.valoruvt.por.anio";

    public static final String OBTENER_DUMMY = "Constantes.obtener.dummy";

}
