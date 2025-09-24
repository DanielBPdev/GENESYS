/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que transporta los datos de cruce de Fovis.
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class InformacionCruceFovisDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 1401288321909936902L;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisCedulaDTO> cedulas;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisAfiliadoDTO> afiliados;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisBeneficiarioDTO> beneficiarios;

    /**
     * Lista de registros hoja beneficiarios del archivo
     */
    private List<CargueArchivoCruceFovisBeneficiarioArriendoDTO> beneficiariosArriendo;

    /**
     * Lista de registros hoja catastros del archivo
     */
    private List<CargueArchivoCruceFovisCatastrosDTO> catastros;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisCatAntDTO> catAnt;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisCatBogDTO> catBog;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisCatCaliDTO> catCali;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisCatMedDTO> catMed;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisIGACDTO> igac;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisNuevoHogarDTO> nuevoHogar;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisFechasCorteDTO> fechaCorte;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisSisbenDTO> sisben;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisUnidosDTO> unidos;

    /**
     * Lista de registros hoja cedulas del archivo
     */
    private List<CargueArchivoCruceFovisReunidosDTO> reunidos;

    /**
     * Constructor por defecto
     */
    public InformacionCruceFovisDTO() {
        super();
    }

    /**
     * @return the cedulas
     */
    public List<CargueArchivoCruceFovisCedulaDTO> getCedulas() {
        return cedulas;
    }

    /**
     * @param cedulas
     *        the cedulas to set
     */
    public void setCedulas(List<CargueArchivoCruceFovisCedulaDTO> cedulas) {
        this.cedulas = cedulas;
    }

    /**
     * @return the afiliados
     */
    public List<CargueArchivoCruceFovisAfiliadoDTO> getAfiliados() {
        return afiliados;
    }

    /**
     * @param afiliados
     *        the afiliados to set
     */
    public void setAfiliados(List<CargueArchivoCruceFovisAfiliadoDTO> afiliados) {
        this.afiliados = afiliados;
    }

    /**
     * @return the beneficiarios
     */
    public List<CargueArchivoCruceFovisBeneficiarioDTO> getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios
     *        the beneficiarios to set
     */
    public void setBeneficiarios(List<CargueArchivoCruceFovisBeneficiarioDTO> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    /**
     * @return the beneficiariosArriendo
     */
    public List<CargueArchivoCruceFovisBeneficiarioArriendoDTO> getBeneficiariosArriendo() {
        return beneficiariosArriendo;
    }

    /**
     * @param beneficiariosArriendo
     */
    public void setBeneficiariosArriendo(List<CargueArchivoCruceFovisBeneficiarioArriendoDTO> beneficiariosArriendo) {
        this.beneficiariosArriendo = beneficiariosArriendo;
    }

    /**
     * @return the catastros
     */
    public List<CargueArchivoCruceFovisCatastrosDTO> getCatastros() {
        return catastros;
    }

    /**
     * @param catastros
     */
    public void setCatastros(List<CargueArchivoCruceFovisCatastrosDTO> catastros) {
        this.catastros = catastros;
    }

    /**
     * @return the catAnt
     */
    public List<CargueArchivoCruceFovisCatAntDTO> getCatAnt() {
        return catAnt;
    }

    /**
     * @param catAnt
     *        the catAnt to set
     */
    public void setCatAnt(List<CargueArchivoCruceFovisCatAntDTO> catAnt) {
        this.catAnt = catAnt;
    }

    /**
     * @return the catBog
     */
    public List<CargueArchivoCruceFovisCatBogDTO> getCatBog() {
        return catBog;
    }

    /**
     * @param catBog
     *        the catBog to set
     */
    public void setCatBog(List<CargueArchivoCruceFovisCatBogDTO> catBog) {
        this.catBog = catBog;
    }

    /**
     * @return the catCali
     */
    public List<CargueArchivoCruceFovisCatCaliDTO> getCatCali() {
        return catCali;
    }

    /**
     * @param catCali
     *        the catCali to set
     */
    public void setCatCali(List<CargueArchivoCruceFovisCatCaliDTO> catCali) {
        this.catCali = catCali;
    }

    /**
     * @return the catMed
     */
    public List<CargueArchivoCruceFovisCatMedDTO> getCatMed() {
        return catMed;
    }

    /**
     * @param catMed
     *        the catMed to set
     */
    public void setCatMed(List<CargueArchivoCruceFovisCatMedDTO> catMed) {
        this.catMed = catMed;
    }

    /**
     * @return the igac
     */
    public List<CargueArchivoCruceFovisIGACDTO> getIgac() {
        return igac;
    }

    /**
     * @param igac
     *        the igac to set
     */
    public void setIgac(List<CargueArchivoCruceFovisIGACDTO> igac) {
        this.igac = igac;
    }

    /**
     * @return the nuevoHogar
     */
    public List<CargueArchivoCruceFovisNuevoHogarDTO> getNuevoHogar() {
        return nuevoHogar;
    }

    /**
     * @param nuevoHogar
     *        the nuevoHogar to set
     */
    public void setNuevoHogar(List<CargueArchivoCruceFovisNuevoHogarDTO> nuevoHogar) {
        this.nuevoHogar = nuevoHogar;
    }

    /**
     * @return the fechaCorte
     */
    public List<CargueArchivoCruceFovisFechasCorteDTO> getFechaCorte() {
        return fechaCorte;
    }

    /**
     * @param fechaCorte
     *        the fechaCorte to set
     */
    public void setFechaCorte(List<CargueArchivoCruceFovisFechasCorteDTO> fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    /**
     * @return the sisben
     */
    public List<CargueArchivoCruceFovisSisbenDTO> getSisben() {
        return sisben;
    }

    /**
     * @param sisben
     *        the sisben to set
     */
    public void setSisben(List<CargueArchivoCruceFovisSisbenDTO> sisben) {
        this.sisben = sisben;
    }

    /**
     * @return the unidos
     */
    public List<CargueArchivoCruceFovisUnidosDTO> getUnidos() {
        return unidos;
    }

    /**
     * @param unidos
     *        the unidos to set
     */
    public void setUnidos(List<CargueArchivoCruceFovisUnidosDTO> unidos) {
        this.unidos = unidos;
    }

    /**
     * @return the reunidos
     */
    public List<CargueArchivoCruceFovisReunidosDTO> getReunidos() {
        return reunidos;
    }

    /**
     * @param reunidos
     *        the reunidos to set
     */
    public void setReunidos(List<CargueArchivoCruceFovisReunidosDTO> reunidos) {
        this.reunidos = reunidos;
    }

}
