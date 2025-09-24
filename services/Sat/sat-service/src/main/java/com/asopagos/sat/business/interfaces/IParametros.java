/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.interfaces;

import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Sergio Reyes
 */
@Local
public interface IParametros {
    
    public Map<String,String> parametrosSat();
    
    public Map<String,String> parametrosCorreo();
    
    public Map<String,String> parametrosSat_WS_F2();  
}

