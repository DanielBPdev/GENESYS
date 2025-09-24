package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.modelo.DescuentoInteresMoraModeloDTO;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAPEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoFEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore;
import com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCore;
import com.asopagos.pila.dto.UbicacionCampoArchivoPilaDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import co.com.heinsohn.lion.fileprocessing.fileloader.FieldDefinitionLoad;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de realizar las consulta sobre la BD de core
 * @author abaquero
 */
@Stateless
public class ConsultaModeloDatosCore implements IConsultaModeloDatosCore, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultaModeloDatosCore.class);

    /**
     * Servicios de persistencia al modelo de datos Core
     */
    @Inject
    private IPersistenciaModeloDatosCore persistenciaCore;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarEmpleadorEntidadPagadora(java.lang.Short,
     * java.lang.String, java.lang.String)
     */
    @Override
    public Object consultarEmpleadorEntidadPagadora(Short tipoBusqueda, String tipoId, String numeroId) {
        logger.debug("Inicia consultarEmpleadorEntidadPagadora(Short, String, String)");
        Object result = null;

        // se busca en la enumeración de tipos, el valor leído en la planilla
        TipoIdentificacionEnum tipoIdEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId);

        try {
            if (tipoIdEnum != null) {
                if (tipoBusqueda == 1) {
                    result = persistenciaCore.consultarEmpresa(tipoIdEnum, numeroId);
                }
                else if (tipoBusqueda == 2) {
                    result = persistenciaCore.consultarPersona(tipoIdEnum, numeroId);
                }
            }
        } catch (ErrorFuncionalValidacionException e) {
            // en este punto no se considera un error el no encontrar resultados
        }
        logger.debug("Finaliza consultarEmpleadorEntidadPagadora(Short, String, String)");
        return result;
    }

    /**
     * 
     * @param tipoId
     * @param numeroId
     * @param nomSucursal
     * @param codSucursal
     * @return 
     */
    public PreRegistroEmpresaDesCentralizada consultarPersonaPorEmpresaDescentralizada(String tipoId, String numeroId, String nomSucursal, String codSucursal) {
        logger.debug("Inicia consultarPersonaPorempresaDescentralizada(String, String, String, String)");
        PreRegistroEmpresaDesCentralizada empresaDescentralizada = null;

        try {
            empresaDescentralizada = persistenciaCore.consultarEmpresaDescentralizada(tipoId, numeroId, nomSucursal, codSucursal);
        } catch (ErrorFuncionalValidacionException ex) {
            Logger.getLogger(ConsultaModeloDatosCore.class.getName()).log(Level.SEVERE, null, ex);
        }

        return empresaDescentralizada;
    }

    /**
     * 
     * @param numeroId
     * @param nomSucursal
     * @param codSucursal
     * @return 
     */
    @Override
    public PreRegistroEmpresaDesCentralizada consultarPersonaPorEmpresaDescentralizada(String numeroId, String nomSucursal, String codSucursal) {
        logger.info("Inicia consultarPersonaPorempresaDescentralizada(String, String, String)");
        PreRegistroEmpresaDesCentralizada empresaDescentralizada = null;

        try {

            empresaDescentralizada = persistenciaCore.consultarEmpresaDescentralizada(numeroId, nomSucursal, codSucursal);

        } catch (ErrorFuncionalValidacionException ex) {
            Logger.getLogger(ConsultaModeloDatosCore.class.getName()).log(Level.SEVERE, null, ex);
        }

        return empresaDescentralizada;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarDepartamentoMunicipio(int)
     */
    @Override
    public Set<String> consultarDepartamentoMunicipio(int tipoConsulta) {
        logger.debug("Inicia consultarDepartamentoMunicipio(int)");
        Set<String> result = null;
        List<String> resultados = null;

        switch (tipoConsulta) {
            case 1: // consulta de Departamentos
                result = new HashSet<>();
                resultados = persistenciaCore.consultarCodigosDepartamentos();

                if (resultados != null && !resultados.isEmpty()) {
                    for (String codigoDepartamento : resultados) {
                        if (codigoDepartamento.length() < 2)
                            codigoDepartamento = "0" + codigoDepartamento;

                        result.add(codigoDepartamento);
                    }
                }
                break;
            case 2: // consulta de Municipios
                result = new HashSet<>();
                resultados = persistenciaCore.consultarCodigosMunicipios();

                if (resultados != null) {
                    for (String codigoMunicipio : resultados) {
                        // sí el código tiene menos de 3 caracteres, se completa con ceros
                        while (codigoMunicipio.length() < 3)
                            codigoMunicipio = "0" + codigoMunicipio;

                        // sí el código tiene 5 dígitos, es porque incluye al código del departamento
                        if (codigoMunicipio.length() == 5)
                            codigoMunicipio = codigoMunicipio.substring(2, 5);

                        result.add(codigoMunicipio);
                    }
                }
                break;
        }
        logger.debug("Finaliza consultarDepartamentoMunicipio(int)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarCombinacionDepartamentoMunicipio()
     */
    @Override
    public Set<String[]> consultarCombinacionDepartamentoMunicipio() {
        logger.debug("Inicia consultarCombinacionDepartamentoMunicipio()");

        Set<String[]> result = new HashSet<>();
        List<String> resultados = persistenciaCore.consultarCodigosMunicipios();

        if (resultados != null) {
            for (String codigoMunicipioDepartamento : resultados) {

                // sí el código tiene 5 dígitos, es porque incluye al código del departamento
                if (codigoMunicipioDepartamento.length() == 5) {
                    String[] departamentoMunicipio = { codigoMunicipioDepartamento.substring(0, 2),
                            codigoMunicipioDepartamento.substring(2, 5) };

                    result.add(departamentoMunicipio);
                }
            }
        }

        logger.debug("Finaliza consultarCombinacionDepartamentoMunicipio()");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarCodigosCIIU()
     */
    @Override
    public Set<String> consultarCodigosCIIU() {
        logger.debug("Inicia consultarCodigosCIIU()");
        Set<String> result = new HashSet<String>();
        List<String> resultados = persistenciaCore.consultarCodigosCiiu();

        if (resultados != null) {
            for (String codigo : resultados) {
                while (codigo.length() < 4) {
                    codigo = "0" + codigo;
                }

                result.add(codigo);
            }
        }

        logger.debug("Finaliza consultarCodigosCIIU()");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarOperadoresFinancieros()
     */
    @Override
    public List<Banco> consultarOperadoresFinancieros() {
        logger.debug("Inicia consultarOperadoresFinancieros()");
        List<Banco> result = new ArrayList<>();

        try {
            result = persistenciaCore.consultarOperadoresFinancieros();
        } catch (ErrorFuncionalValidacionException e) {
            // No se encontraron bancos parametrizados, se debe retornar la lista vacía
        }

        logger.debug("Finaliza consultarOperadoresFinancieros()");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarBeneficio(com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum)
     */
    @Override
    public Beneficio consultarBeneficio(TipoBeneficioEnum beneficio) {
        String firmaMetodo = "ConsultaModeloDatosCore.consultarBeneficio(TipoBeneficioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Beneficio result = persistenciaCore.consultarBeneficio(beneficio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarDescuentosInteres()
     */
    @Override
    public List<DescuentoInteresMoraModeloDTO> consultarDescuentosInteres() {
        String firmaMetodo = "ConsultaModeloDatosCore.consultarDescuentosInteres()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DescuentoInteresMoraModeloDTO> result = persistenciaCore.consultarDescuentosInteres();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarUbicaciones(java.util.Set)
     */
    @Override
    public List<UbicacionCampoArchivoPilaDTO> consultarUbicaciones(Set<String> camposPorUbicar) {
        String firmaMetodo = "ConsultaModeloDatosCore.consultarUbicaciones(Set<String>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<UbicacionCampoArchivoPilaDTO> result = new ArrayList<>();
        
        List<String> nombresInternos = new ArrayList<>();
        
        for (String codigo : camposPorUbicar) {
            // sí la inconsistencia cuenta con un dato de código de campo
            if (codigo != null) {
                // se debe establecer sí el código del campo corresponde con algún valor de etiqueta

                Object etiqueta = null;
                String nombreInterno = null;

                // se compara con etiqueta de archivo A-AR
                etiqueta = EtiquetaArchivoAEnum.obtenerEtiqueta(codigo);
                nombreInterno = etiqueta != null ? ((EtiquetaArchivoAEnum) etiqueta).getNombreCampo() : null;

                // se compara con etiqueta de archivo AP-APR
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoAPEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoAPEnum) etiqueta).getNombreCampo() : null;
                }

                // se compara con etiqueta de archivo I-IR
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoIEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoIEnum) etiqueta).getNombreCampo() : null;
                }

                // se compara con etiqueta de archivo IP-IPR
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoIPEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoIPEnum) etiqueta).getNombreCampo() : null;
                }

                // se compara con etiqueta de archivo F
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoFEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoFEnum) etiqueta).getNombreCampo() : null;
                }
                
                if(nombreInterno != null && !nombresInternos.contains(nombreInterno)){
                    nombresInternos.add(nombreInterno);
                }
            }
        }
        
        if(!nombresInternos.isEmpty()){
            List<FieldDefinitionLoad> definiciones = persistenciaCore.consultarFieldDefinition(nombresInternos);
            UbicacionCampoArchivoPilaDTO ubicacion = null;
            Set<String> llavesAgregadas = new HashSet<>();
            
            for (FieldDefinitionLoad definicion : definiciones) {
                // se controla mediante un set, que no se repitan ubicaciones
                if(llavesAgregadas.add(definicion.getLabel())){
                    
                    ubicacion = new UbicacionCampoArchivoPilaDTO();
                    ubicacion.setNombreInterno(definicion.getLabel());
                    ubicacion.setNombreCampo(definicion.getFieldLoadCatalog().getDescription());

                    // las posiciones se encuentran como Long en el componente, se convierten a Short para el servicio
                    ubicacion.setPosicionInicial((short) (definicion.getInitialPosition() + 1));
                    ubicacion.setPosicionFinal(definicion.getFinalPosition().shortValue());
                    
                    result.add(ubicacion);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#consultarListasBlancasAportantes(java.util.String)
     */
    public ListasBlancasAportantes consultarListasBlancasAportantes(String numeroIdentificacion) {
    	return persistenciaCore.consultarListasBlancasAportantes(numeroIdentificacion);
    }
}
