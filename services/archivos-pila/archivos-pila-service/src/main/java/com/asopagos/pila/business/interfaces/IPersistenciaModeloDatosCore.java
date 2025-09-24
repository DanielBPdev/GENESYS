package com.asopagos.pila.business.interfaces;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.dto.modelo.DescuentoInteresMoraModeloDTO;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import co.com.heinsohn.lion.fileprocessing.fileloader.FieldDefinitionLoad;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;

/**
 * Servicios de persistencia con el modelo de datos Core
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IPersistenciaModeloDatosCore {

    /**
     * Método para consultar una entidad pagadora a partir de un número de ID y un tipo de ID
     * @param tipoId
     *        Tipo de ID consultado
     * @param numeroIdentificacion
     *        Número de ID consultado
     * @return EntidadPagadora
     *         Instancia de entidad pagadora consultada
     * @throws ErrorFuncionalValidacionException
     */
    public EntidadPagadora consultarEntidadPagadora(TipoIdentificacionEnum tipoId, String numeroIdentificacion)
            throws ErrorFuncionalValidacionException;

    /**
     * Método para consultar una empresa a partir de un número de ID y un tipo de ID
     * @param tipoId
     *        Tipo de ID consultado
     * @param numeroIdentificacion
     *        Número de ID consultado
     * @return Empresa
     *         Instancia de empresa consultada
     * @throws ErrorFuncionalValidacionException
     */
    public Empresa consultarEmpresa(TipoIdentificacionEnum tipoId, String numeroIdentificacion)
            throws ErrorFuncionalValidacionException;

    /**
     * Método para consultar una persona a partir de un número de ID y un tipo de ID
     * @param tipoId
     *        Tipo de ID consultado
     * @param numeroIdentificacion
     *        Número de ID consultado
     * @return EntidadPagadora
     *         Instancia de persona consultada
     * @throws ErrorFuncionalValidacionException
     */
    public Persona consultarPersona(TipoIdentificacionEnum tipoId, String numeroIdentificacion) throws ErrorFuncionalValidacionException;

    /**
     * Método para consultar códigos de departamentos
     * @return List<String>
     *         Listado de códigos de departamentos
     * @throws ErrorFuncionalValidacionException
     */
    public List<String> consultarCodigosDepartamentos();

    /**
     * Método para consultar códigos de municipios
     * @return List<String>
     *         Listado de códigos de municipios
     * @throws ErrorFuncionalValidacionException
     */
    public List<String> consultarCodigosMunicipios();

    /**
     * Método para consultar códigos CIIU
     * @return List<String>
     *         Listado de códigos CIIU
     * @throws ErrorFuncionalValidacionException
     */
    public List<String> consultarCodigosCiiu();

    /**
     * Método para consultar la definición de un campo en el componente FileProcessor
     * @param llaveCampo
     *        Lista de etiquetas mediante la cual se ha de realizar la búsqueda de la definición
     * @return <b>List<FieldDefinitionLoad></b>
     *         Lista de entradas de definición de campo en la parametrización del componente
     */
    public List<FieldDefinitionLoad> consultarFieldDefinition(List<String> llaveCampo);
    
    /**
     * Función para consultar los operadores financieros
     * @return List<Banco>
     *         Listado de operadores financieros
     * @throws ErrorFuncionalValidacionException
     */
    public List<Banco> consultarOperadoresFinancieros() throws ErrorFuncionalValidacionException;

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
     * Método encargado de consultar si un número de identificacción se encuentra dentro de 
     * de una lista blanca de pila
     * 
     * @param numeroIdentificacion
     * @param tipoIdentificacion
     * @return ListasBlancasAportantes
     */
    public ListasBlancasAportantes consultarListasBlancasAportantes(String numeroIdentificacion);
    
    /**
     * Metodo para consultar las empresas descentralizadas
     * @return 
     */
    public List<PreRegistroEmpresaDesCentralizada> consultarEmpresasDescentralizadas();
    
    
    /**
     * 
     * @param tipoId
     * @param numeroId
     * @param nomSucursal
     * @param codSucursal
     * @return 
     */
    public PreRegistroEmpresaDesCentralizada consultarEmpresaDescentralizada(String tipoId, String numeroId, String nomSucursal, String codSucursal) throws ErrorFuncionalValidacionException;
    
    /**
     * 
     * @param numeroId
     * @param nomSucursal
     * @param codSucursal
     * @return 
     */
    public PreRegistroEmpresaDesCentralizada consultarEmpresaDescentralizada(String numeroId, String nomSucursal, String codSucursal) throws ErrorFuncionalValidacionException;
}
