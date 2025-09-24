package com.asopagos.empleadores.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    // HU-TRA-329
    public static final String CONSULTAR_EMPLEADOR_ID = "Empleadores.id.buscarTodos";

    // HU-111-070
    public static final String CONSULTAR_UBICACIONES_EMPLEADOR = "Empleador.ubicaciones.buscarTodos";

    // HU-111-066
    public static final String CONSULTAR_SUCURSALES_EMPLEADOR = "Empleador.sucursales.buscarTodos";
    
    public static final String CONSULTAR_SUCURSALES_EMPLEADOR_POR_IDSUCURSAL = "Empleador.sucursales.buscar.por.idSucursalEmpresa";

    public static final String CONSULTAR_EMPRESA_ID = "Empleador.empresa.id";

    public static final String CONSULTAR_UBICACION_ID = "Empleador.ubicacion.id";

    public static final String CONSULTAR_CODIGOS_SUCURSALES_EMPRESA = "Empleador.SucursalEmpresa.consultarCodigos";

    public static final String BUSCAR_SUCURSAL_CODIGO_ID_EMPRESA = "Empresa.SucursalEmpresa.buscarSucursalPorCodigoIdEmpresa";

    // ESB
    public static final String CONSULTAR_EMPRESA_POR_PERSONA = "Empleador.Empresa.consultarEmpresaPorPersona";

    public static final String CONSULTAR_UBICACION_PRINCIPAL_POR_EMPRESA = "Empleador.Empresa.consultarUbicacionPrincipalPorEmpresa";

    public static final String CONSULTAR_PERSONA = "Empleador.Persona.consultarPersona";

    public static final String BUSCAR_SUCURSAL_POR_CODIGO_NOMBRE_E_ID_TRABAJADOR = "Empresa.SucursalEmpresa.buscarSucursalPorCodigoNombreIdTrabajador";

    public static final String CONSULTAR_SUCURSAL_POR_CODIGO = "Empresa.SucursalEmpresa.consultarSucursalEmpresaPorCodigo";

    public static final String CONSULTAR_SUCURSAL_POR_CODIGO_Y_DATOS_DE_IDENTIFICACION_EMPRESA = "Empresa.SucursalEmpresa.consultarSucursalPorCodigoEIdDeEmpresa";

    public static final String BUSCAR_SUCURSAL = "Empresa.SucursalEmpresa.buscarSucursalPorPersona";

    /**
     * Constante con el nombre de la consulta que obtiene la información de un
     * oferente, por identificación
     */
    public static final String CONSULTAR_OFERENTE_POR_IDENTIFICACION = "Fovis.Consultar.Oferente.Identificacion";

    /**
     * Constante que contiene el nombre de la consulta de empresas por razón social
     * HU 311-440
     */
    public static final String CONSULTAR_EMPRESA_POR_RAZONSOCIAL = "Empresa.consultar.razonSocial";

    /**
     * Constante que contiene el nombre de la consulta de personas por razón social
     * 
     */
    public static final String CONSULTAR_PERSONA_POR_RAZON_SOCIAL = "Empleador.Persona.consultarPersonaPorRazonSocial";
    
    /**
     * Constante con el nombre de la consulta que obtiene la información de un
     * oferente, por razon social
     */
    public static final String CONSULTAR_OFERENTE_POR_RAZON_SOCIAL = "Empleador.Consultar.Oferente.RazonSocial";
    
    /**
     * Constante con el nombre de la consulta que obtiene la información de una persona por ID
     */
    public static final String CONSULTAR_PERSONA_POR_ID = "Empresa.Persona.consultarPersonaPorId";
    
    public static final String CONSULTAR_REPRESENTANTE_LEGAL_PRIN_BY_ID_EMPRESA = "consultar.representante.legal.principal.by.id.empresa";
    
    public static final String CONSULTAR_REPRESENTANTE_LEGAL_SUPL_BY_ID_EMPRESA = "consultar.representante.legal.suplente.by.id.empresa";
    
     /**
     * Constante con el nombre de la consulta que obtiene la información de un
     * proveedor, por identificación - GLPI 49270
     */
    public static final String CONSULTAR_PROVEEDOR_POR_IDENTIFICACION = "Fovis.Consultar.Proveedor.Identificacion";
    
     /**
     * Constante con el nombre de la consulta que obtiene la información de un
     * proveedor, por razon social - GLPI 49270
     */
    public static final String CONSULTAR_PROVEEDOR_POR_RAZON_SOCIAL = "Empleador.Consultar.Proveedor.RazonSocial";
    
    /**
     * Constante con el nombre de la consulta que obtiene la información de una LegalizacionDesembolosoProveedorModeloDTO
     * oferente, por razon social
     */
   //49270BORRADOKT comentado ya que el glpi 49270 contempla la tabla LegalizacionDesembolosoProveedor pero esta nunca se inserta y no se utiliza

    //public static final String CONSULTAR_LEGALIZACION_DESEMBOLSO_PROVEEDOR = "Empleador.Consultar.Proveedor.Legalizacion.desemboloso";
    
    public static final String CONSULTAR_SUCURSAL_POR_CODIGO_Y_DATOS_DE_IDENTIFICACION_EMPRESA2 = "Empresa.SucursalEmpresa.consultarSucursalPorCodigoEIdDeEmpresa2";
    
    public static final String CONSULTAR_SUCURSAL_POR_NOMBRE_Y_DATOS_DE_IDENTIFICACION_EMPRESA = "Empresa.SucursalEmpresa.consultarSucursalPorNombreEIdDeEmpresa";

}
