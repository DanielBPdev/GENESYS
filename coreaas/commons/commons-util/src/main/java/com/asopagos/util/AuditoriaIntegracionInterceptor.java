package com.asopagos.util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import java.time.Instant;
import java.time.Duration;
import javax.interceptor.Interceptor;
import java.util.Map;
import com.asopagos.entidades.ccf.core.AuditoriaIntegracionServicios;
import javax.servlet.http.HttpServletRequest;
// TODO 21: Importar javax.ws.rs.core.Response; java.time.Duration; java.time.Instant; com.asopagos.util.AuditoriaIntegracionInterceptor
import javax.ws.rs.core.Response; 
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.time.Instant;
import com.asopagos.util.AuditoriaIntegracionInterceptor;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.NullPointerException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class AuditoriaIntegracionInterceptor{

	private final ILogger logger = LogManager.getLogger(AuditoriaIntegracionInterceptor.class);


    public static String convertParametrosToJsonString(Object parametros){
        try {

        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(parametros);
        return jsonPayload;
        } catch (Exception e) {
            return "";
        }
    }

public static Response integracionServiciosErroresyExitosos(
  Instant start,Exception e, Object salida, EntityManager entityManager, AuditoriaIntegracionServicios auditoriaIntegracionServicios)
 {

    persistirDatosAuditoria(start, salida, e, entityManager, auditoriaIntegracionServicios);
    
    Response resp; 
    if(e == null){
      Response.ResponseBuilder res = null;
      res = Response.ok(salida);
      res.header("Content-Type", MediaType.APPLICATION_JSON);
      resp = res.build();

    }else{
        Response.ResponseBuilder res = null;
        res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(salida);
        res.header("Content-Type", MediaType.APPLICATION_JSON);
        resp = res.build();

    }
    return resp;
  }

  public static void persistirDatosAuditoria(Instant start, Object salida, Exception e, EntityManager entityManager, AuditoriaIntegracionServicios auditoriaIntegracionServicios) {
        System.out.println("Persistir datos auditorias");
        Integer codigoEstado ;
        Instant finish = Instant.now();
        long duracion = Duration.between(start, finish).toMillis();
        String detallesErrores = "";
        Boolean resultado = true;
        String parametrosSalida = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(salida);
        codigoEstado = Response.Status.OK.getStatusCode();
        if (e != null) {
            resultado = false;
            detallesErrores = e.getMessage();
            codigoEstado = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        }
        if(e instanceof NullPointerException){
                detallesErrores = "NullPointerException";
        }
        
        auditoriaIntegracionServicios.setDuracion((int) duracion);
        auditoriaIntegracionServicios.setDatosRespuesta(parametrosSalida);
        auditoriaIntegracionServicios.setDetallesErrores(detallesErrores);
        auditoriaIntegracionServicios.setCodigoEstado(codigoEstado);
        auditoriaIntegracionServicios.setResultado(resultado);
        entityManager.persist(auditoriaIntegracionServicios);
        
        System.out.println("Fin Persistir datos auditorias");
  }
}