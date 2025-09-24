/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.interfaces;

import com.asopagos.sat.dto.RespuestaEstandar;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Sergio Reyes
 */
@Local
public interface IEnviarDatosSAT {
    
    public List<RespuestaEstandar> enviarInformacionSAT();
    public String getTokenSatToGenesys(Map<String,String> parametros,String nombreCLientId);
}
