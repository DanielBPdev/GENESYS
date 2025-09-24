package com.asopagos.archivos.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.util.Interpolator;

/**
 * Class to read the text file cruce FOVIS
 * @author Diego Suesca <dsuesca@heinsohn.com.co>
 *
 */
public class TextReaderUtil {

    
    /**
     * Service to read the contents of the text file
     * @param file   
    
     * @return List string lines
     * @throws Exception
     */
    public static ArrayList<String> fileToListString(byte[] file) throws Exception {
    	ArrayList returList = new ArrayList<>();
    	BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file)));   
    	String strCurrentLine;
    	while ((strCurrentLine = br.readLine()) != null) {   
    		returList.add(strCurrentLine);  
    	}      
    	return returList;
    }
    
    /**
     * Service to read the contents of the text file
     * @param file   
    
     * @return List string lines
     * @throws Exception
     */
    public static ArrayList<String[]> fileToListString(byte[] file,String separator) throws Exception {
    	ArrayList returList = new ArrayList<>();
    	BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file)));   
    	String strCurrentLine;
    	while ((strCurrentLine = br.readLine()) != null) {   
    		returList.add(strCurrentLine.split(separator));  
    	}      
    	return returList;
    }

    
    /**
     * Método que crea un hallazgo según la información ingresada
     * 
     * @param lineNumber,
     *        numero de linea
     * @param campo,
     *        campo al que pertenece el hallazgo
     * @param errorMessage,
     *        mensaje de error
     * @return retorna el resultado de hallazgo DTO
     */
    private static ResultadoHallazgosValidacionArchivoDTO createHallazgo(Long lineNumber, String fieldName, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, fieldName, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(fieldName);
        hallazgo.setError(error);
        return hallazgo;
    }
}
