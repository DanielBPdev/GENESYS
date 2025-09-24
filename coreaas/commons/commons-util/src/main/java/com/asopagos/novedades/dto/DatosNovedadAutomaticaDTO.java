/**
 * 
 */
package com.asopagos.novedades.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.dto.RolafiliadoNovedadAutomaticaDTO;

/**
 * DTO que contiene los campos que pueden modificar las novedades automaticas
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class DatosNovedadAutomaticaDTO implements Serializable{

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 6256234334504349001L;

    /**
     * Lista de identificadores de empleadores objeto de la novedad
     */
    private List<Long> idEmpleadores;

    /**
     * Lista de identificadores de personas afiliados objeto de la novedad
     */
    private List<Long> idPersonaAfiliados;

    /**
     * Lista de beneficiarios objeto de la novedad
     */
    private List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiarios;

    /**
     * Lista Personas para Retiro por Mora de Aportes.
     */
    private List<PersonaRetiroNovedadAutomaticaDTO> listaPersonasRetiro;

    /**
     * Campo utilizado en las novedades de retiro de empleadores automático<br/>
     * Indica el nombre del motivo de desafiliación 
     */
    private MotivoDesafiliacionEnum motivoDesafiliacion;

    // La línea `lista privada<Long> idRolafiliado;` declara una variable de instancia privada
    // `idRolafiliado` de tipo `List<Long>`. Esta variable no se utiliza ni se hace referencia a ella
    // en ningún otro lugar del código proporcionado. Es posible que esté destinado a ser utilizado en
    // alguna otra parte del código que no se muestra.
    private List<RolafiliadoNovedadAutomaticaDTO> listaRolafiliados;

    /**
     * Constructor por defecto
     */
    public DatosNovedadAutomaticaDTO() {
        super();
    }

    /**
     * @return the idEmpleadores
     */
    public List<Long> getIdEmpleadores() {
        return idEmpleadores;
    }

    /**
     * @param idEmpleadores
     *        the idEmpleadores to set
     */
    public void setIdEmpleadores(List<Long> idEmpleadores) {
        this.idEmpleadores = idEmpleadores;
    }

    /**
     * @return the idPersonaAfiliados
     */
    public List<Long> getIdPersonaAfiliados() {
        return idPersonaAfiliados;
    }

    /**
     * @param idPersonaAfiliados
     *        the idPersonaAfiliados to set
     */
    public void setIdPersonaAfiliados(List<Long> idPersonaAfiliados) {
        this.idPersonaAfiliados = idPersonaAfiliados;
    }

    /**
     * Retorna la lista de beneficiarios objeto de la novedad
     * 
     * @return the listaBeneficiarios
     */
    public List<BeneficiarioNovedadAutomaticaDTO> getListaBeneficiarios() {
        return listaBeneficiarios;
    }

    /**
     * Modifica la lista de beneficiarios objeto de la novedad
     * 
     * @param listaBeneficiarios
     *        the listaBeneficiarios to set
     */
    public void setListaBeneficiarios(List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiarios) {
        this.listaBeneficiarios = listaBeneficiarios;
    }

    /**
     * @return the listaPersonasRetiro
     */
    public List<PersonaRetiroNovedadAutomaticaDTO> getListaPersonasRetiro() {
        return listaPersonasRetiro;
    }

    /**
     * @param listaPersonasRetiro
     *        the listaPersonasRetiro to set
     */
    public void setListaPersonasRetiro(List<PersonaRetiroNovedadAutomaticaDTO> listaPersonasRetiro) {
        this.listaPersonasRetiro = listaPersonasRetiro;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    public List<RolafiliadoNovedadAutomaticaDTO> getListaRolafiliados(){
        return this.listaRolafiliados;
    }

    public void setListaRolafiliados(List<RolafiliadoNovedadAutomaticaDTO> rolafiliados){
        this.listaRolafiliados = rolafiliados;
    }

}
