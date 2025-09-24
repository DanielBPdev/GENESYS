/**
 * 
 */
package com.asopagos.cartera.composite.dto;

/**
 * Clase DTO que contiene los valores necesarios para devolver al iniciar una tarea.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ProcesoRadicadoDTO {

    /**
     * Atributo que contiene el id de instancia del proceso BPM.
     */
    private Long idInstancia;

    /**
     * Atributo que contiene el número de radicación.
     */
    private String numeroRadicacion;

    /**
     * Atributo que contiene el usuario asigando al BPM.
     */
    private String usuario;

    /**
     * Método que retorna el valor de idInstancia.
     * @return valor de idInstancia.
     */
    public Long getIdInstancia() {
        return idInstancia;
    }

    /**
     * Método que retorna el valor de numeroRadicacion.
     * @return valor de numeroRadicacion.
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * Método que retorna el valor de usuario.
     * @return valor de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Método encargado de modificar el valor de idInstancia.
     * @param valor
     *        para modificar idInstancia.
     */
    public void setIdInstancia(Long idInstancia) {
        this.idInstancia = idInstancia;
    }

    /**
     * Método encargado de modificar el valor de numeroRadicacion.
     * @param valor
     *        para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * Método encargado de modificar el valor de usuario.
     * @param valor
     *        para modificar usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
