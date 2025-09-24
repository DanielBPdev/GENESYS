package com.asopagos.dto.afiliaciones;

/**
 * <b>Descripción:</b> DTO para información del destinatario de la caja de 
 * correspondencia
 * <b>Historia de Usuario:</b> 111-071
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class DestinatarioCajaCorrespondenciaDTO {
    
    
    private String sedeDestinatario;
    
    private String destinantario;
    

    /**
     * @return the sedeDestinatario
     */
    public String getSedeDestinatario() {
        return sedeDestinatario;
    }

    /**
     * @param sedeDestinatario the sedeDestinatario to set
     */
    public void setSedeDestinatario(String sedeDestinatario) {
        this.sedeDestinatario = sedeDestinatario;
    }

    /**
     * @return the destinantario
     */
    public String getDestinantario() {
        return destinantario;
    }

    /**
     * @param destinantario the destinantario to set
     */
    public void setDestinantario(String destinantario) {
        this.destinantario = destinantario;
    }
    
}
