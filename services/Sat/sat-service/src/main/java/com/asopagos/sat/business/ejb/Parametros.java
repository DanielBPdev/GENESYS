/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.ejb;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.sat.business.interfaces.IParametros;
import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sergio Reyes
 */
@Stateless
public class Parametros implements IParametros,Serializable{
    
    private static final ILogger logger = LogManager.getLogger(EjecutarProcedimientoRecoleccion.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;

    @Override
    public Map<String, String> parametrosSat_WS_F2() {
        Map<String,String> parametros = new HashMap<String,String>();
        try{
            Query query = entityManager.createNativeQuery("SELECT prmNombre,prmValor FROM dbo.Parametro WHERE prmNombre like '%_WS_F2'");
            List<Object[]> listado =query.getResultList();
            for(Object[] obj: listado){
                if(obj[0].toString().equals("PASSWORD_SAT")){
                    logger.info("***CONTRASEÑA***:"+obj[0].toString());
                    logger.info("***CONTRASEÑAV***:"+obj[1].toString());
                    parametros.put(obj[0].toString(), obj[1].toString());
                }else{
                    logger.info("***LISTADO***:"+obj[0].toString());
                    logger.info("***LISTADOV***:"+obj[1].toString());
                    parametros.put(obj[0].toString(), obj[1].toString());
                }
            }
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("***PARAMETROS***:"+parametros.toString());
        return parametros;
    }

    @Override
    public Map<String, String> parametrosSat() {
        Map<String,String> parametros = new HashMap<String,String>();
        try{
            Query query = entityManager.createNativeQuery("SELECT prmNombre,prmValor FROM dbo.Parametro WHERE prmNombre like '%_SAT'");
            List<Object[]> listado =query.getResultList();
            for(Object[] obj: listado){
                if(obj[0].toString().equals("PASSWORD_SAT")){
                    logger.info("***CONTRASEÑA***:"+obj[0].toString());
                    logger.info("***CONTRASEÑAV***:"+obj[1].toString());
                    parametros.put(obj[0].toString(), obj[1].toString());
                }else{
                    logger.info("***LISTADO***:"+obj[0].toString());
                    logger.info("***LISTADOV***:"+obj[1].toString());
                    parametros.put(obj[0].toString(), obj[1].toString());
                }
            }
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("***PARAMETROS***:"+parametros.toString());
        return parametros;
    }

    @Override
    public Map<String, String> parametrosCorreo() {
        Map<String,String> parametros = new HashMap<String,String>();
        try{
            Query query = entityManager.createNativeQuery("Select prmNombre,prmValor from dbo.Parametro WHERE prmSubCategoriaParametro = 'MAIL_SMTP'");
            List<Object[]> listado =query.getResultList();
            for(Object[] obj: listado){
                if(obj[0].toString().equals("PASSWORD_SAT")){
                    parametros.put(obj[0].toString(), obj[1].toString());
                }else{
                    parametros.put(obj[0].toString(), obj[1].toString());
                }
            }
        }catch(Exception e){
            logger.error(e);
        }
        return parametros;
    }
    
}
