package com.asopagos.pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.ParameterTranslations;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.type.LiteralType;
import org.hibernate.type.Type;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripcion:</b> Clase de utilidad para la implementacion de servicios paginados<br/>
 * <b>Módulo:</b> Asopagos - HU transversal<br/>
 *
 * @author <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public class QueryBuilder {
    
    /**
     * Entity Manager
     */
    private EntityManager entityManager;
    
    /**
     * Order by
     */
    private List<String> orderBy = new ArrayList<>();

    /**
     * Default order
     */
    private List<String> defaultOrder = new ArrayList<>();

    /**
     * Limit
     */
    private Integer limit = null;

    /**
     * Offset
     */
    private Integer offset = null;

    /**
     * Variable to handle if exist limit or not
     */
    private Boolean existsLimit = false;

    /**
     * All Url params
     */
    private MultivaluedMap<String, String> queryParams;

    /**
     * Response
     */
    private HttpServletResponse response;

    /**
     * Parameters to be set in query
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * Named query object from file
     */
    private Object namedQuery;
    
    /**
     * Map that represents the hints
     */
    private Map<String, String> hints;
    
    private static final ILogger logger = LogManager.getLogger(QueryBuilder.class);
    

    
    /**
     * @return the hints
     */
    public Map<String, String> getHints() {
        return hints;
    }

    /**
     * @param hints
     *        the hints to set
     */
    public void setHints(Map<String, String> hints) {
        this.hints = hints;
    }


    /**
     * @return the namedQuery
     */
    public Object getNamedQuery() {
        return namedQuery;
    }

    /**
     * @param namedQuery
     *        the namedQuery to set
     */
    public void setNamedQuery(Object namedQuery) {
        this.namedQuery = namedQuery;
    }

    /**
     * Funcion que agrega un parametro al mapa de parametros
     * @param key
     * @param value
     */
    public void addParam(String key, Object value) {

        params.put(key, value);
    }

    /**
     * @return the response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @param response
     *        the response to set
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * @return the existsLimit
     */
    public Boolean getExistsLimit() {
        return existsLimit;
    }

    /**
     * @param existsLimit
     *        the existsLimit to set
     */
    public void setExistsLimit(Boolean existsLimit) {
        this.existsLimit = existsLimit;
    }

    /**
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit
     *        the limit to set
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return the offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset
     *        the offset to set
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return the queryParams
     */
    public MultivaluedMap<String, String> getQueryParams() {
        return queryParams;
    }

    /**
     * @param queryParams
     *        the queryParams to set
     */
    public void setQueryParams(MultivaluedMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }
    
    /**
     * Método constructor utilizado cuando los parametros se pasan directamente desde una pantalla, sin un composite en medio del proceso.
     * Este método se creo como consecuencia del error java.lang.unsupportedoperationexception unmodifiable multivaluemap después de que la
     * aplicación esta desplegada en contendores.
     * @param entityManager
     *        entity manager.
     * @param uri
     *        información uri que contiene parametros de la consulta paginada.
     * @param response
     *        response.
     */
    public QueryBuilder(EntityManager entityManager, UriInfo uri, HttpServletResponse response) {
        
        inicializarDatos(entityManager, uri.getQueryParameters(), response);
        
    }

    /**
     * Método constructur utilizado cuando los parametros se pasan desde un composite.
     * @param entityManager entity manager.
     * @param queryParams parametros de la consulta paginada.
     * @param response response.
     */
    public QueryBuilder(EntityManager entityManager, MultivaluedMap<String, String> queryParams, HttpServletResponse response) {
    
        inicializarDatos(entityManager, queryParams, response);
    }
    
    /**
     * Método que se encarga de inicializar los datos para la consulta paginada.
     * @param entityManager entity manager.
     * @param queryParams parametros de la consulta paginada.
     * @param response response.
     */
    private void inicializarDatos(EntityManager entityManager, MultivaluedMap<String, String> queryParams, HttpServletResponse response){
        setQueryParams(queryParams);

        setEntityManager(entityManager);

        setResponse(response);

   
        //order
        if (queryParams.containsKey(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor())) {

            List<String> orderByValue = queryParams.get(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor());
            addOrderByParameters(orderByValue);

        }

        //limit
        if (queryParams.containsKey(PaginationQueryParamsEnum.LIMIT.getValor()) && queryParams.containsKey(PaginationQueryParamsEnum.OFFSET.getValor())) {

            setLimit(Integer.parseInt(queryParams.get(PaginationQueryParamsEnum.LIMIT.getValor()).get(0)));
            setOffset(Integer.parseInt(queryParams.get(PaginationQueryParamsEnum.OFFSET.getValor()).get(0)));
            setExistsLimit(true);
        }

        //draw
        if (queryParams.containsKey(PaginationQueryParamsEnum.DRAW.getValor())) {
            getResponse().addHeader(PaginationQueryParamsEnum.DRAW.getValor(), queryParams.get(PaginationQueryParamsEnum.DRAW.getValor()).get(0));
        }
        
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param entityManager
     *        the entityManager to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addOrderByParameter(String orderby) {
        orderBy.add(orderby);
    }

    public void addOrderByDefaultParam(String orderBy) {

        defaultOrder.add(orderBy);
    }

    /**
     * Funcion que agrega el total de registros a las cabeceras de respuesta
     * @param total
     */
    public void setTotalRecordsToResponse(String total) {

        getResponse().addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(), total);
    }

    /**
     * Funcion que agrega el total de registros a las cabeceras de respuesta
     * @param total
     */
    public void setTotalRecordsToResponse(Long total) {

        getResponse().addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(),  Long.toString(total));
    }

    /**
     * Funcion que agrega el total de registros a las cabeceras de respuesta
     * @param total
     */
    public void setTotalRecordsToResponse(int total) {

        getResponse().addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(),  Integer.toString(total));
    }

    /**
     * Funcion que agrega el total de registros a las cabeceras de respuesta
     * @param total
     */
    public void setTotalRecordsToResponse(Integer total) {

        getResponse().addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(),  Integer.toString(total));
    }
    
    
    /**
     * funcion que crea un Query a partir de un NamedQuery y agrega parametros de ordenamiento segun sea el caso
     * @param namedQuery
     * @param entityClass
     * @param totalRecordsNamedQuery
     * @return
     */
    public <T> Query createQuery(String namedQuery, String totalRecordsNamedQuery) {

        Query finalQuery = null;

        setNamedQuery(namedQuery);

        setHintsFrom();
        
        String finalOrderQueryString = "";

        if (orderBy != null && !orderBy.isEmpty()) {

            finalOrderQueryString = getOrderQueryString(orderBy);

        }
        else if (defaultOrder != null && !defaultOrder.isEmpty()) {

            finalOrderQueryString = getOrderQueryString(defaultOrder);
        }

        String queryString = null;
        if(getNamedQuery() instanceof NamedNativeQueryRead){
            queryString =  ((NamedNativeQueryRead)getNamedQuery()).getQuery();
            String resultMapping = ((NamedNativeQueryRead)getNamedQuery()).getResultMapping();
            if (resultMapping != null) {
                finalQuery = getEntityManager().createNativeQuery(queryString + finalOrderQueryString, resultMapping);
            } 
            else {
                finalQuery = getEntityManager().createNativeQuery(queryString + finalOrderQueryString);
            }
            
        }
        if(getNamedQuery() instanceof NamedQueryRead){
            queryString =  ((NamedQueryRead)getNamedQuery()).getQuery();
            finalQuery = getEntityManager().createQuery(queryString + finalOrderQueryString);
        }
        if(getNamedQuery() instanceof String){
            queryString = (String) getNamedQuery();
            finalQuery = getEntityManager().createQuery(queryString + finalOrderQueryString);
        }

        finalQuery = setParams(finalQuery);

        if (getExistsLimit()) {

            finalQuery.setFirstResult(getOffset());
            finalQuery.setMaxResults(getLimit());
        }
        
        /**
         * If the total records query is defined
         */
        if(totalRecordsNamedQuery != null && !totalRecordsNamedQuery.trim().isEmpty()){

            Query totalRecordsQuery = getEntityManager().createNamedQuery(totalRecordsNamedQuery);
            totalRecordsQuery = setParams(totalRecordsQuery);
            
            Long totalRecords = (Long) totalRecordsQuery.getSingleResult(); 
            setTotalRecordsToResponse(Long.toString(totalRecords));   

        }else{

            executeTotalRecordsFirstQuery(queryString);
        }
        
        return finalQuery;
    }

        /**
     * funcion que crea un Query a partir de un NamedQuery y agrega parametros de ordenamiento segun sea el caso
     * @param namedQuery
     * @param entityClass
     * @param totalRecordsNamedQuery
     * @return
     */
    public <T> Query createNamedNativeQuery(String namedQuery, String totalRecordsNamedQuery) {

        Query finalQuery = null;
        
        String finalOrderQueryString = "";

        if (orderBy != null && !orderBy.isEmpty()) {

            finalOrderQueryString = getOrderQueryString(orderBy);
            
        }
        else if (defaultOrder != null && !defaultOrder.isEmpty()) {
            
            finalOrderQueryString = getOrderQueryString(defaultOrder);
        }
        finalQuery = getEntityManager().createNativeQuery(namedQuery + finalOrderQueryString);
        finalQuery = setParams(finalQuery);

        if (getExistsLimit()) {

            finalQuery.setFirstResult(getOffset());
            finalQuery.setMaxResults(getLimit());
        }

        /**
         * If the total records query is defined
         */
        if(totalRecordsNamedQuery != null && !totalRecordsNamedQuery.trim().isEmpty()){

            Query totalRecordsQuery = getEntityManager().createNamedQuery(totalRecordsNamedQuery);
            totalRecordsQuery = setParams(totalRecordsQuery);
            
            Integer totalRecords = (Integer) totalRecordsQuery.getSingleResult(); 
            setTotalRecordsToResponse(Integer.toString(totalRecords));   

        }
        
        return finalQuery;
    }

    /**
     * Crea la instancia de Query a partir de una consulta nativa y le agrega los parametros de ordenamiento y paginación
     * correspondientes
     * @param nativeQuery
     *        Consulta nativa a ordenar y paginar
     * @return Instancia query
     */
    public <T> Query createNativeQuery(String nativeQuery) {
        Query finalQuery = null;
        String finalOrderQueryString = "";
        if (orderBy != null && !orderBy.isEmpty()) {
            finalOrderQueryString = getOrderQueryString(orderBy);
        }
        else if (defaultOrder != null && !defaultOrder.isEmpty()) {
            finalOrderQueryString = getOrderQueryString(defaultOrder);
        }
        finalQuery = getEntityManager().createNativeQuery(nativeQuery + finalOrderQueryString);
        finalQuery = setParams(finalQuery);
        if (getExistsLimit()) {
            finalQuery.setFirstResult(getOffset());
            finalQuery.setMaxResults(getLimit());
        }
        executeTotalRecordsFirstQuery(nativeQuery);
        return finalQuery;
    }

    /**
     * Crea la instancia de Query a partir de una consulta nativa y le agrega los parametros de ordenamiento y paginación
     * correspondientes
     * @param nativeQuery Consulta nativa a ordenar y paginar
     * @param resultMapping Forma en que se entreg el resultado de la consulta
     * @return Instancia query
     */
    public <T> Query createNativeQuery(String nativeQuery, String resultMapping) {
        Query finalQuery = null;
        String finalOrderQueryString = "";
        if (orderBy != null && !orderBy.isEmpty()) {
            finalOrderQueryString = getOrderQueryString(orderBy);
        }
        else if (defaultOrder != null && !defaultOrder.isEmpty()) {
            finalOrderQueryString = getOrderQueryString(defaultOrder);
        }
        finalQuery = getEntityManager().createNativeQuery(nativeQuery + finalOrderQueryString, resultMapping);
        finalQuery = setParams(finalQuery);
        if (getExistsLimit()) {
            finalQuery.setFirstResult(getOffset());
            finalQuery.setMaxResults(getLimit());
        }
        executeTotalRecordsFirstQuery(nativeQuery);
        return finalQuery;
    }

    /**
     * Executes total of results from a named query string
     * @param queryString
     */
    public <T> void executeTotalRecordsFirstQuery(String queryString) {

        Session sessions = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = sessions.getSessionFactory();

        //Query namedQuerySetted = getEntityManager().createNamedQuery(namedQuery);
        //namedQuerySetted = setParams(namedQuerySetted);
        //namedQuerySetted.unwrap(org.hibernate.Query.class).getQueryString();

        String hqlQueryText = queryString;

        String sql = hqlQueryText;

        /* si se trata de una named query se realiza la transformación a nativa */
        if (getNamedQuery() instanceof NamedQueryRead || getNamedQuery() instanceof String) {
            SessionFactoryImplementor session = (SessionFactoryImplementor) sessionFactory;
            QueryTranslator translator = new ASTQueryTranslatorFactory().createQueryTranslator(hqlQueryText, hqlQueryText,
                    Collections.EMPTY_MAP, session, null);
            translator.compile(Collections.EMPTY_MAP, true);

            sql = translator.getSQLString();

            // parameters
            if (!params.isEmpty()) {
                TreeMap<Integer, String> tokenMap = new TreeMap<Integer, String>();
                ParameterTranslations parameterTranslations = translator.getParameterTranslations();
                Dialect dialect = session.getDialect();

                for (Iterator iter = parameterTranslations.getNamedParameterNames().iterator(); iter.hasNext();) {
                    String paramName = (String) iter.next();
                    Object paramValue = params.get(paramName);

                    Type type = parameterTranslations.getNamedParameterExpectedType(paramName);

                    LiteralType<Object> literal = (LiteralType<Object>) type;

                    String paramValueString = null;

                    //si es una lista
                    if (paramValue instanceof List<?>) {

                        List<T> a = (List<T>) paramValue;

                        //si no esta vacia
                        if (a != null && !a.isEmpty()) {

                            //si es una enumeracion
                            if (a.get(0) instanceof Enum) {

                                paramValueString = "";
                                for (int i = 0; i < a.size(); i++) {

                                    Enum e = (Enum) a.get(i);
                                    paramValueString += PaginationQueryParamsEnum.QUOTE_PARAM.getValor() + e.name()
                                            + PaginationQueryParamsEnum.QUOTE_PARAM.getValor();

                                    if (i < a.size() - 1) {
                                        paramValueString += PaginationQueryParamsEnum.COMMA_SEPARATOR.getValor();
                                    }
                                }
                            }
                            else {
                                //si no es una enumeracion
                                paramValueString = "";
                                for (int i = 0; i < a.size(); i++) {

                                    try {
                                        paramValueString += literal.objectToSQLString(a.get(i), dialect);
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        logger.info(paramName + ": " + e.getMessage());
                                        //e.printStackTrace();
                                    }

                                    if (i < a.size() - 1) {
                                        paramValueString += PaginationQueryParamsEnum.COMMA_SEPARATOR.getValor();
                                    }
                                }
                            }
                        }
                    }
                    else {
                        try {
                            if (paramValue!=null){
                                paramValueString = literal.objectToSQLString(paramValue, dialect);    
                            }
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            logger.info(paramName + ": " + e1.getMessage());
                            //e1.printStackTrace();
                        }

                    }

                    int[] locations = parameterTranslations.getNamedParameterSqlLocations(paramName);
                    for (int position : locations) {
                        tokenMap.put(position, paramValueString);
                    }
                }

                for (String value : tokenMap.values()) {
                    sql = sql.replaceFirst(PaginationQueryParamsEnum.UNKNOWN_PARAM.getValor(), Matcher.quoteReplacement(" " + value + " "));
                }

            }
            String queryCount = addCountStatement(sql);

            Query totalRecordsQuery = getEntityManager().createNativeQuery(queryCount);

            Long totalRecords = ((Number) totalRecordsQuery.getSingleResult()).longValue();

            setTotalRecordsToResponse(Long.toString(totalRecords));
        }
        else {
            /* si se trata de una native query */
            String queryCount = addCountStatement(sql);

            //System.out.println(queryCount);
            Query totalRecordsQuery = getEntityManager().createNativeQuery(queryCount);
            for (Entry<String, Object> e: params.entrySet()) {
                totalRecordsQuery.setParameter(e.getKey(), e.getValue());
            }

            Long totalRecords = ((Number) totalRecordsQuery.getSingleResult()).longValue();

            setTotalRecordsToResponse(Long.toString(totalRecords));

        }
        

    }
    
    
    /**
     * Transforms a query to a count query in string level
     * @param sqlQuery
     * @return
     */
    public String addCountStatement(String sqlQuery){
            
        return PaginationQueryParamsEnum.INIT_COUNT_STATEMENT.getValor() + sqlQuery + PaginationQueryParamsEnum.END_COUNT_STATEMENT.getValor();
        
    }

    /**
     * Método que asigna el named query
     * @param namedQuery
     */
    public void setNamedQuery(String namedQuery) {

        // Se obtiene la propiedad asopagos.namedquery.file del entitymanager para identificar el archivo que contiene las namedqueries para 
        // la unida de persistencia actual.
        // Si no se define la propiedad toma el path por defecto /META-INF/jpql/NamedQueries.xml para leer las namedqueries
        Map<String, NamedQueryRead> readedNamedQueries = null;
        String namedQueriesFile = (String) entityManager.getEntityManagerFactory().getProperties()
                .get(NamedQueriesReader.NAMED_QUERIES_XML_PATH_PROPERTIE_NAME);
        if (namedQueriesFile == null)
            readedNamedQueries = NamedQueriesReader.getInstance().getReadedNamedQueries();
        else
            readedNamedQueries = NamedQueriesReader.getInstance().getReadedNamedQueries(namedQueriesFile);
        Object queryEncontrada = readedNamedQueries.get(namedQuery);
        if (queryEncontrada != null) {
            setNamedQuery(queryEncontrada);
        }
        else {
            setNamedQuery((Object)namedQuery);
        }

    }

    /**
     * Método que asigna los hints del named query actual
     */
    public void setHintsFrom() {

        List<HintRead> hr = null;
        if(getNamedQuery() instanceof NamedNativeQueryRead){
            hr =  ((NamedNativeQueryRead)getNamedQuery()).getHints();
        }
        if(getNamedQuery() instanceof NamedQueryRead){
            hr =  ((NamedQueryRead)getNamedQuery()).getHints();
        }

        
        Map<String, String> hints = new HashMap<String, String>();

        if (hr != null) {
            for (HintRead hintRead : hr) {

                hints.put(hintRead.getName(), hintRead.getValue());
            }
        }

        setHints(hints);
    }

    /**
     * funcion que agrega los parametros al query
     * @param q
     * @return
     */
    public Query setParams(Query q) {

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return q;
    }


    /**
     * Funcion que agrega el ordenamiento de cada columa de la lista
     * @param orderList
     * @return
     */
    public String getOrderQueryString(List<String> orderList) {

        StringBuilder sb = new StringBuilder();

        sb.append(PaginationQueryParamsEnum.ORDER_BY.getValor());

        int limit = orderList.size();
        int i = 0;
        for (String columnDirOrder : orderList) {

            i = i + 1;

            sb.append(getColumnNameOrder(columnDirOrder));

            if (i < limit) {
                sb.append(PaginationQueryParamsEnum.COMMA_SEPARATOR.getValor());
            }

        }

        return sb.toString();
    }
    /**
     * Funcion que retorna el orden de la query hecha
     * @param orderList
     * @return
     */
    public String getBuildOrderBy() {
        return getOrderQueryString(orderBy);
    }
    /**
     * funcion que obtiene el nombre de la columna y la direccion de ordenamiento de un atributo
     * @param columnDirOrder
     *        con el -(guion medio) antes de nombre del parametro
     * @return
     */
    public String getColumnNameOrder(String columnDirOrder) {

        if (columnDirOrder.contains(PaginationQueryParamsEnum.DESCENDENT_IDENTIFIER.getValor()) && columnDirOrder.substring(0, 1).equals(PaginationQueryParamsEnum.DESCENDENT_IDENTIFIER.getValor())) {

            return getHintOrderCriteria(columnDirOrder.substring(1)) + " " + PaginationQueryParamsEnum.DESC.getValor() + " ";
        }

        return getHintOrderCriteria(columnDirOrder) + " " +PaginationQueryParamsEnum.ASC.getValor() + " ";
    }

    /**
     * funcion que obtiene el valor de un hint especificado para ordenamiento
     * si este no esta definido se retorna null
     * @param orderCriteria
     * @return
     */
    public String getHintOrderCriteria(String orderCriteria) {

        Map<String, String> hints = getHints();

        if (hints != null && !hints.isEmpty()) {
            
            return getHints().get(orderCriteria);
        }

        return null;
    }

    /**
     * @param orderByValue
     */
    public void addOrderByParameters(List<String> orderByValue) {
        orderBy.addAll(orderByValue);

    }
}
