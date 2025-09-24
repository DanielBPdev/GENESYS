package com.asopagos.afiliaciones.empleadores.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;

@XmlRootElement
public class GuardarDataTemporal implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EmpleadorDTO empleador;
    
    private Persona representante1;
    
    private Persona representante2;
    
    private List<String> responsables;
    
    private List<RolContactoEmpleador> rolafiliaciones;
    
    private List<SocioEmpleador> socios;
    
    private  List<SucursalEmpresa> sucursales;
    
    private List<UbicacionEmpresa> ubicaciones;
    
    public GuardarDataTemporal() {
        
    }

    /**
     * @return the empleador
     */
    public EmpleadorDTO getEmpleador() {
        return empleador;
    }


    /**
     * @param empleador the empleador to set
     */
    public void setEmpleador(EmpleadorDTO empleador) {
        this.empleador = empleador;
    }


    /**
     * @return the representante1
     */
    public Persona getRepresentante1() {
        return representante1;
    }


    /**
     * @param representante1 the representante1 to set
     */
    public void setRepresentante1(Persona representante1) {
        this.representante1 = representante1;
    }


    /**
     * @return the representante2
     */
    public Persona getRepresentante2() {
        return representante2;
    }


    /**
     * @param representante2 the representante2 to set
     */
    public void setRepresentante2(Persona representante2) {
        this.representante2 = representante2;
    }


    /**
     * @return the responsables
     */
    public List<String> getResponsables() {
        return responsables;
    }


    /**
     * @param responsables the responsables to set
     */
    public void setResponsables(List<String> responsables) {
        this.responsables = responsables;
    }


    /**
     * @return the rolAfiliaciones
     */
    public List<RolContactoEmpleador> getRolafiliaciones() {
        return rolafiliaciones;
    }


    /**
     * @param rolAfiliaciones the rolAfiliaciones to set
     */
    public void setRolafiliaciones(List<RolContactoEmpleador> rolafiliaciones) {
        this.rolafiliaciones = rolafiliaciones;
    }


    /**
     * @return the socios
     */
    public List<SocioEmpleador> getSocios() {
        return socios;
    }


    /**
     * @param socios the socios to set
     */
    public void setSocios(List<SocioEmpleador> socios) {
        this.socios = socios;
    }


    /**
     * @return the sucursales
     */
    public List<SucursalEmpresa> getSucursales() {
        return sucursales;
    }


    /**
     * @param sucursales the sucursales to set
     */
    public void setSucursales(List<SucursalEmpresa> sucursales) {
        this.sucursales = sucursales;
    }


    /**
     * @return the ubicaciones
     */
    public List<UbicacionEmpresa> getUbicaciones() {
        return ubicaciones;
    }


    /**
     * @param ubicaciones the ubicaciones to set
     */
    public void setUbicaciones(List<UbicacionEmpresa> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
    
    
    
    

}
