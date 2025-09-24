package com.asopagos.pila.business.interfaces;

import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import com.asopagos.dto.modelo.DescuentoInteresMoraModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.pila.dto.UbicacionCampoArchivoPilaDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripcion:</b> Interfaz que define los métodos para la consulta de información del modelo de datos transaccional<br/>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-391<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface IConsultaModeloDatosCore {

    /**
     * Función para la consulta de un número de identificación como empleador o como entidad pagadora de acuerdo
     * al bloque 5 de validación
     * 
     * @param tipoBusqueda
     *        Parámetro que indica el tipo de busqueda, ya sea como empleador (1) o como entidad pagadora (0)
     * @param tipoId
     *        Tipo de identificación
     * @param numeroId
     *        Número de identificación
     * @return <b>Object</b>
     *         DTO con el retultado de la consulta
     */
    public Object consultarEmpleadorEntidadPagadora(Short tipoBusqueda, String tipoId, String numeroId);

    /**
     * Función para la consulta de Departamentos o municipios
     * @param tipoConsulta
     *        Indicador del tipo de consulta realizada: 1. Departamentos - 2. Municipios
     * @return <b>Set<String></b>
     *         Listado con el contenido seleccionado
     * @throws ErrorFuncionalValidacionException
     */
    public Set<String> consultarDepartamentoMunicipio(int tipoConsulta);

    /**
     * Función para la consulta de la combinación de Departamentos y Municipios
     * @return Listado con las combinaciones de Departamentos y Municipios
     * @throws ErrorFuncionalValidacionException
     */
    public Set<String[]> consultarCombinacionDepartamentoMunicipio();

    /**
     * Función para la consulta de los códigos CIIU de actividades económicas
     * @return <b>Set<String></b>
     *         Listado de actividades económicas
     * @throws ErrorFuncionalValidacionException
     */
    public Set<String> consultarCodigosCIIU();

    /**
     * Función para obtener el listado de los Operadores Financieros (Bancos) parametrizados
     * @return <b>List<Banco></b>
     *         Listado de bancos parametrizados
     */
    public List<Banco> consultarOperadoresFinancieros();

    /**
     * Función que consulta la entrada de beneficio de acuerdo a parámetro enumerado
     * @param beneficio
     * @return <b>Beneficio</b>
     *         Entrada de los datos del beneficio consultado
     */
    public Beneficio consultarBeneficio(TipoBeneficioEnum beneficio);

    /**
     * Función encargada de la consulta de los casos parametrizados para la aplicación de descuentos en el valor
     * de los intereses de mora de un aporte
     * @return <b>List<DescuentoInteresMoraModeloDTO></b>
     *         Listado con los DTO con las condiciones de descuento en valor de mora
     */
    public List<DescuentoInteresMoraModeloDTO> consultarDescuentosInteres();

    /**
     * Función encargada de consultar un conjunto de ubicaciones de campos para los errores de PILA M1
     * @param camposPorUbicar
     *        Listado de códigos de campos encontrados en el listado de mensajes de error de validación
     * @return <b>List<UbicacionCampoArchivoPilaDTO></b>
     *         Listado de DTOs con las pocisiones y nombres de los campos en las inconsistencias de validación
     */
    public List<UbicacionCampoArchivoPilaDTO> consultarUbicaciones(Set<String> camposPorUbicar);

    /**
     * Método encargado de consultar si un número de identificacción se encuentra dentro de 
     * de una lista blanca de pila
     * 
     * @param numeroIdentificacion
     * @param tipoIdentificacion
     * @return ListasBlancasAportantes
     */
    public ListasBlancasAportantes consultarListasBlancasAportantes(String numeroIdentificacion);
    
    
    /**
     * 
     * @param numeroId
     * @param nomSucursal
     * @param codSucursal
     * @return 
     */
    public PreRegistroEmpresaDesCentralizada consultarPersonaPorEmpresaDescentralizada(String numeroId, String nomSucursal, String codSucursal);
    
    /**
     * 
     * @param tipoId
     * @param numeroId
     * @param nomSucursal
     * @param codSucursal
     * @return 
     */
    public PreRegistroEmpresaDesCentralizada consultarPersonaPorEmpresaDescentralizada(String tipoId, String numeroId, String nomSucursal, String codSucursal);
}
