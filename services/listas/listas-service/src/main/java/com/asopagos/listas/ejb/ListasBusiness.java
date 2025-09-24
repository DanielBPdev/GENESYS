package com.asopagos.listas.ejb;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.commons.beanutils.PropertyUtils;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.dto.modelo.CuentasBancariasRecaudoDTO;
import com.asopagos.listas.config.ConfiguracionLista;
import com.asopagos.listas.config.ConfiguracionListaReader;
import com.asopagos.listas.constants.NamedQueriesConstants;
import com.asopagos.listas.service.ListasService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la consulta de Listas genéricas <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class ListasBusiness implements ListasService {

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "listas_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ListasBusiness.class);

    /**
     * Método que implementa la capacidad consultarListasValores de la
     * especificación de servicios
     * 
     * @see com.asopagos.listas.ejb.ListasBusiness#consultarListasValores(java.util.List)
     * @param idsListaValores
     * @return
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ElementoListaDTO> consultarListasValores(List<Integer> idsListaValores) {
        List<ElementoListaDTO> elementos = new ArrayList<ElementoListaDTO>();
        try {
            StringBuilder consulta = new StringBuilder();
            for (Integer idListaValores : idsListaValores) {
                ConfiguracionLista configLista = ConfiguracionListaReader.obtenerConfiguracionLista(idListaValores);
                if (configLista != null && configLista.getNombreClase() != null) {
                    if (Class.forName(configLista.getNombreClase()).isEnum()) {
                        // Se procesa la lista de tipo enumeración
                        List<ElementoListaDTO> temElementos = consultarEnumeracion(configLista, null, null);
                        elementos.addAll(consultarEnumeracion(configLista, null, null));
                        // Ajuste temporal Enum de migracion de estado
                        
                        
                        for (int i = 0 ; i < temElementos.size(); i++) {
                            if (temElementos.get(i).getIdLista() == 84 && temElementos.get(i).getIdLista() == 183
                            && temElementos.get(i).getIdentificador() != null && !temElementos.get(i).getIdentificador().toString().equals("MIGRACION")) {
                                elementos.add(temElementos.get(i));
                            }
                        } 
                    }
                    else {
                    	Map<Integer, String> mapAtributosOrdenados = new HashMap<>();
                        // Se procesa la lista de tipo entidad
                        getQueryLista(consulta, configLista, null, null, mapAtributosOrdenados);
                        Date inicioAcceso = new Date();
                        List<Object[]> data = entityManager.createQuery(consulta.toString()).getResultList();
                        poblarListaElementos(elementos, data, configLista, mapAtributosOrdenados);
                        consulta.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            String mensajeError = "Error procesando el archivo de onfiguración de listas";
            logger.error(mensajeError, e);
            throw new TechnicalException(mensajeError, e);
        }
        return elementos;
    }

    /**
     * Método que implementa la capacidad consultarListaValores de la
     * especificación de servicios
     * 
     * @see com.asopagos.listas.ejb.ListasBusiness#consultarListaValores(java.lang.Integer)
     * @param idListaValores
     * @return
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ElementoListaDTO> consultarListaValores(Integer idListaValores, String nombreFiltro, String valorFiltro) {
        List<ElementoListaDTO> elementos = null;
        try {

            // Se consulta la parametrización de la lista
            ConfiguracionLista configLista = ConfiguracionListaReader.obtenerConfiguracionLista(idListaValores);
            if (Class.forName(configLista.getNombreClase()).isEnum()) {
                return consultarEnumeracion(configLista, nombreFiltro, valorFiltro);
            }
            
            // Se instancia el objeto de retorno
            StringBuilder consulta = new StringBuilder();
            Map<Integer, String> mapAtributosOrdenados = new HashMap<>();
            getQueryLista(consulta, configLista, nombreFiltro, valorFiltro, mapAtributosOrdenados);
            try {
                if (consulta.length() != 0) {
                    elementos = new ArrayList<ElementoListaDTO>();
                    Query query = entityManager.createQuery(consulta.toString());
                    if (nombreFiltro != null && valorFiltro != null) {
                        Metamodel meta = entityManager.getMetamodel();
                        String className = configLista.getNombreClase();
                        Class clazz = Class.forName(className);
                        EntityType entityModel = meta.entity(clazz);
                        Attribute attribute = entityModel.getAttribute(nombreFiltro);
                        Matcher matcher = ConfiguracionListaReader.pattern.matcher(valorFiltro);
                        if (matcher.matches()) {
                            if (attribute.getJavaType().equals(Short.class)) {
                                query.setParameter("valorFiltro", Short.valueOf(valorFiltro));
                            }
                            if (attribute.getJavaType().equals(Integer.class)) {
                                query.setParameter("valorFiltro", Integer.valueOf(valorFiltro));
                            }
                            if (attribute.getJavaType().equals(Long.class)) {
                                query.setParameter("valorFiltro", Long.valueOf(valorFiltro));
                            }
                        }
                        else if (attribute.getJavaType().isEnum()) {
                            query.setParameter("valorFiltro", Enum.valueOf(attribute.getJavaType(), valorFiltro));
                        }
                        else {
                            if (attribute.getJavaType().equals(Boolean.class)) {
                                query.setParameter("valorFiltro", Boolean.valueOf(valorFiltro));
                            }else{
                                query.setParameter("valorFiltro", valorFiltro);
                            }
                        }
                    }
                    List<Object[]> data = query.getResultList();
                    poblarListaElementos(elementos, data, configLista, mapAtributosOrdenados);
                }
            } catch (IllegalArgumentException ex) {
                logger.error(ex.getMessage(), ex);
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_NOMBRE_O_VALOR_FILTRO_INVALIDO);
            } catch (PersistenceException ex) {
                logger.error(ex.getMessage(), ex);
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_NOMBRE_O_VALOR_FILTRO_INVALIDO);
            } catch (ClassNotFoundException ex) {
                logger.error(ex.getMessage(), ex);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_NOMBRE_O_VALOR_FILTRO_INVALIDO, ex);
            }
        } catch (Exception e) {
            String mensajeError = "Error procesando el archivo de onfiguración de listas";
            logger.error(mensajeError, e);
            throw new TechnicalException(mensajeError, e);
        }
        return elementos;
    }

    /**
     * Método que agrega a la consulta global una consulta a la tabla
     * identificada por <source>idLista</source>
     */
    private void getQueryLista(StringBuilder consulta, ConfiguracionLista configLista, String nombreFiltro, String valorFiltro, Map<Integer, String> mapAtributosOrdenados) {

        if (configLista != null) {
            consulta.append(ConsultasDinamicasConstants.SELECT);
            consulta.append(configLista.getIdLista());
            consulta.append(",");
            consulta.append(configLista.getCampoCodigo());
            consulta.append(",");
            consulta.append(configLista.getCampoNombre());
            if (configLista.getAtributos() != null) {
            	int numeroAtributo = 3;
                for (Entry entryAtributo : configLista.getAtributos().entrySet()) {
                    consulta.append(",");
                    consulta.append(entryAtributo.getKey());
                    mapAtributosOrdenados.put(numeroAtributo++, (String)entryAtributo.getKey());
                }
            }
            consulta.append(ConsultasDinamicasConstants.FROM);
            consulta.append(configLista.getNombreClase());
            if (nombreFiltro != null && valorFiltro != null) {
                consulta.append(ConsultasDinamicasConstants.WHERE);
                consulta.append(nombreFiltro);
                consulta.append(ConsultasDinamicasConstants.IGUAL);
                consulta.append(":valorFiltro");
            }
        }
    }

    /**
     * Método que construye el objeto ElementoListaDTO a partir de los
     * resultados de la consulta
     */
    private void poblarListaElementos(List<ElementoListaDTO> elementos, List<Object[]> data, ConfiguracionLista configLista,
    		Map<Integer, String> mapAtributosOrdenados) {

        for (Object[] row : data) {
            ElementoListaDTO elemento = new ElementoListaDTO();
            elemento.setIdLista((Integer) row[0]);
            /*Verifica si el identificador es un Enumerado*/
            if (row[1] instanceof Enum) {
            	/*En dado caso asocia el Name del Enumerado.*/
            	elemento.setIdentificador(((Enum)row[2]).name());
            }else{
            	elemento.setIdentificador(row[1]);
            }
            /*Verifica si el Valor es un Enumerado*/
            if (row[2] instanceof Enum) {
            	String valor = "";
            	Enum enumerado = (Enum)row[2];
            	Class<? extends Enum> clazzEnum = enumerado.getClass();
            	try {
            		Method metodoDescripcion = clazzEnum.getMethod("getDescripcion");
            		valor = (String)metodoDescripcion.invoke(enumerado);
            		elemento.setValor(valor);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					/*Si no existe el método getDescripcion() se obtiene el Name del enumerado.*/
					elemento.setValor(enumerado.name());
				}
            }else {
            	 elemento.setValor((String) row[2]);
            }
            if (configLista.getAtributos() != null) {
                elemento.setAtributos(new HashMap<String, Object>(configLista.getAtributos()));
                for (Integer numeroAtributo: mapAtributosOrdenados.keySet()) {
                    elemento.getAtributos().put(mapAtributosOrdenados.get(numeroAtributo), row[numeroAtributo]);
                }
            }
            elementos.add(elemento);
        }
    }

    /**
     * Método que implementa la capacidad consultarEnumeracion de la
     * especificación de servicios
     * 
     * @see com.asopagos.listas.ejb.EnumeracionesBusiness#consultarEnumeracion(java.lang.Integer)
     * @param idEnumeracion
     * @return
     */
    private List<ElementoListaDTO> consultarEnumeracion(ConfiguracionLista configLista, String nombreFiltro, String valorFiltro) {

        List<ElementoListaDTO> elementos = new ArrayList<ElementoListaDTO>();

        try {
            if (configLista != null) {
                Class classEnum = Class.forName(configLista.getNombreClase());
                if (classEnum.isEnum()) {
                    Matcher matcher;
                    Object[] constantesEnum = classEnum.getEnumConstants();
                    for (Object constanteEnum : constantesEnum) {
                        Enum enumConstant = (Enum) constanteEnum;
                        // Excluir el valor "PENSION_FAMILIAR" en todos los casos
                        if ("PENSION_FAMILIAR".equals(enumConstant.name())) {
                            continue;
                        }
                        ElementoListaDTO elemento = new ElementoListaDTO();
                        if (configLista.getAtributos() != null) {
                            Map<String, Object> map = new HashMap<>();
                            map.putAll(configLista.getAtributos());
                            elemento.setAtributos(map);
                        }
                        else {
                            elemento.setAtributos(configLista.getAtributos());
                        }

                        // Aplicación de filtro
                        if (nombreFiltro != null && valorFiltro != null) {
                            if (nombreFiltro.equals("name")) {
                                if (!enumConstant.name().equals(valorFiltro)) {
                                    // Se filtra este enumValue
                                    continue;
                                }
                            }
                            else {
                                Object valorFiltroEnum = PropertyUtils.getProperty(constanteEnum, nombreFiltro);
                                if (valorFiltroEnum instanceof Number) {
                                    matcher = ConfiguracionListaReader.pattern.matcher(valorFiltro);
                                    // Si el valor del filtro es un número
                                    if (matcher.matches()) {
                                        if (!((Number) valorFiltroEnum).toString().equals(valorFiltro)) {
                                            // Se filtra este enumValue
                                            continue;
                                        }
                                    }
                                    else {
                                        throw new ParametroInvalidoExcepcion(nombreFiltro);
                                    }
                                }
                                else if (valorFiltroEnum instanceof String) {
                                    if (!valorFiltroEnum.equals(valorFiltro)) {
                                        // Se filtra este enumValue
                                        continue;
                                    }
                                }
                                else if (valorFiltroEnum instanceof Enum) {
                                    if (!((Enum) valorFiltroEnum).name().equals(valorFiltro)) {
                                        // Se filtra este enumValue
                                        continue;
                                    }
                                }
                            }
                        }
                        // Llenado de atributos
                        if (configLista.getAtributos() != null) {
                            for (String atributo : elemento.getAtributos().keySet()) {
                                elemento.getAtributos().put(atributo, PropertyUtils.getProperty(constanteEnum, atributo));
                            }
                        }
                        elemento.setIdLista(configLista.getIdLista());
                        elemento.setIdentificador(enumConstant.name());
                        elemento.setValor((String) PropertyUtils.getProperty(constanteEnum, configLista.getCampoNombre()));
                        elementos.add(elemento);
                    }
                }
            }
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
        return elementos;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object pruebaCacheDos(String key) {
        return CacheManager.getConstante(key);
    }
    
	@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String consultarParametro(String key){
        return String.valueOf(CacheManager.getParametro(key));
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String consultarConstante(String key){
        return String.valueOf(CacheManager.getConstante(key));
    }

    @Override
    public List<CuentasBancariasRecaudoDTO> consultarCuentasBancariasRecaudo() {
        List<CuentasBancariasRecaudoDTO> cuentasBancariasRecaudoDTO = new ArrayList<>();
        try {
                
                final int varID = 0;
                final int varBANCO = 1;
                final int varTIPO = 2;
                final int varNUMERO_CUENTA = 3;
                final int varTIPO_RECAUDO = 4;                
                
                List<Object[]> resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_BANCARIAS_RECAUDO)
                        .getResultList();

                for (Object[] obj : resultado) {
                    CuentasBancariasRecaudoDTO objLocal = new  CuentasBancariasRecaudoDTO();
                    
                    objLocal.setID(String.valueOf(String.valueOf(obj[varID] == null ? "0": obj[varID].toString())));
                    objLocal.setBANCO(String.valueOf(String.valueOf(obj[varBANCO] == null ? "0": obj[varBANCO].toString())));
                    objLocal.setTIPO(String.valueOf(String.valueOf(obj[varTIPO] == null ? "0": obj[varTIPO].toString())));
                    objLocal.setNUMERO_CUENTA(String.valueOf(String.valueOf(obj[varNUMERO_CUENTA] == null ? "0": obj[varNUMERO_CUENTA].toString())));
                    objLocal.setTIPO_RECAUDO(String.valueOf(String.valueOf(obj[varTIPO_RECAUDO] == null ? "0": obj[varTIPO_RECAUDO].toString())));
                    
                    cuentasBancariasRecaudoDTO.add(objLocal);
                }
                
	} catch (NoResultException e) {
				logger.debug("Sin resultados para los criterios de busqueda");
	} catch (NonUniqueResultException e) {
				logger.debug("Finaliza el servicio encabezado aportes futuros");
				throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, e);
	}
        
        return cuentasBancariasRecaudoDTO;
    
    }
    
}
