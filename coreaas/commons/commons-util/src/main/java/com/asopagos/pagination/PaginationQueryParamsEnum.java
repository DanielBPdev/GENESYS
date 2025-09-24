package com.asopagos.pagination;

/**
 * <b>Descripcion:</b> Clase de enumeracion, contiene los identificadores y valores necesarios para la 
 * implementación de QueryBuilder<br/>
 * <b>Módulo:</b> Asopagos - HU transversal<br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public enum PaginationQueryParamsEnum {
    
    DRAW("draw"),
    
    LIMIT("limit"),
    
    OFFSET("offset"),
    
    TOTAL_RECORDS("totalrecords"),
    
    ORDER_BY_QPARAM("orderby"),
    
    ORDER_BY(" ORDER BY "),
    
    ASC("ASC"),
    
    DESC("DESC"),
    
    DESCENDENT_IDENTIFIER("-"),
    
    QUOTE_PARAM("\'"),
    
    COMMA_SEPARATOR(", "),
    
    UNKNOWN_PARAM("\\?"),
    
    INIT_COUNT_STATEMENT("select count(*) from ("),
    
    END_COUNT_STATEMENT(") x"),
    
    FIRST_REQUEST("firstRequest"),
    
    FILTER("filter");
    
    private String valor;

    public String getValor(){
        
        return valor;
    }
    
    
    private PaginationQueryParamsEnum(String valor){
           
        this.valor = valor;
        
    } 

}
