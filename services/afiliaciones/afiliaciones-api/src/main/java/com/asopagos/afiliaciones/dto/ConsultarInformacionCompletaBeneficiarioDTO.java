/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.afiliaciones.dto;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author Alexander.camelo
 */
@JsonInclude(Include.NON_EMPTY)
public class ConsultarInformacionCompletaBeneficiarioDTO implements Serializable{
       /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String resultado_consulta;
private String tipo_doc_ben;
private String num_doc_ben;
private String pri_nom_ben;
private String seg_nom_ben;
private String pri_ape_ben;
private String seg_ape_ben;
private String estado_beneficiario;
private String condicion_inv;
private String edad;
private String fecha_nacimiento;
private String num_grupo_fam;
private String grado;
private String nivel_educativo;
private String sitio_pago;
private String estado_grupo_fam;
private String parentesco;
private String tipo_doc_otro_padre;
private String num_doc_otro_padre;
private String certificado_vigente;
private String fecha_ini_cert;
private String fecha_fin_cert;
private String fecha_liquidacion;
private String tipo_doc_afi;
private String num_doc_afi;

    public ConsultarInformacionCompletaBeneficiarioDTO() {
    }

    public ConsultarInformacionCompletaBeneficiarioDTO(String resultado_consulta, String tipo_doc_ben, String num_doc_ben, String pri_nom_ben, String seg_nom_ben, String pri_ape_ben, String seg_ape_ben, String estado_beneficiario, String condicion_inv, String edad, String fecha_nacimiento, String num_grupo_fam, String grado, String nivel_educativo, String sitio_pago, String estado_grupo_fam, String parentesco, String tipo_doc_otro_padre, String num_doc_otro_padre, String certificado_vigente, String fecha_ini_cert, String fecha_fin_cert, String fecha_liquidacion, String tipo_doc_afi, String num_doc_afi) {
        this.resultado_consulta = resultado_consulta;
        this.tipo_doc_ben = tipo_doc_ben;
        this.num_doc_ben = num_doc_ben;
        this.pri_nom_ben = pri_nom_ben;
        this.seg_nom_ben = seg_nom_ben;
        this.pri_ape_ben = pri_ape_ben;
        this.seg_ape_ben = seg_ape_ben;
        this.estado_beneficiario = estado_beneficiario;
        this.condicion_inv = condicion_inv;
        this.edad = edad;
        this.fecha_nacimiento = fecha_nacimiento;
        this.num_grupo_fam = num_grupo_fam;
        this.grado = grado;
        this.nivel_educativo = nivel_educativo;
        this.sitio_pago = sitio_pago;
        this.estado_grupo_fam = estado_grupo_fam;
        this.parentesco = parentesco;
        this.tipo_doc_otro_padre = tipo_doc_otro_padre;
        this.num_doc_otro_padre = num_doc_otro_padre;
        this.certificado_vigente = certificado_vigente;
        this.fecha_ini_cert = fecha_ini_cert;
        this.fecha_fin_cert = fecha_fin_cert;
        this.fecha_liquidacion = fecha_liquidacion;
        this.tipo_doc_afi = tipo_doc_afi;
        this.num_doc_afi = num_doc_afi;
    }

    public String getResultado_consulta() {
        return resultado_consulta;
    }

    public void setResultado_consulta(String resultado_consulta) {
        this.resultado_consulta = resultado_consulta;
    }

    public String getTipo_doc_ben() {
        return tipo_doc_ben;
    }

    public void setTipo_doc_ben(String tipo_doc_ben) {
        this.tipo_doc_ben = tipo_doc_ben;
    }

    public String getNum_doc_ben() {
        return num_doc_ben;
    }

    public void setNum_doc_ben(String num_doc_ben) {
        this.num_doc_ben = num_doc_ben;
    }

    public String getPri_nom_ben() {
        return pri_nom_ben;
    }

    public void setPri_nom_ben(String pri_nom_ben) {
        this.pri_nom_ben = pri_nom_ben;
    }

    public String getSeg_nom_ben() {
        return seg_nom_ben;
    }

    public void setSeg_nom_ben(String seg_nom_ben) {
        this.seg_nom_ben = seg_nom_ben;
    }

    public String getPri_ape_ben() {
        return pri_ape_ben;
    }

    public void setPri_ape_ben(String pri_ape_ben) {
        this.pri_ape_ben = pri_ape_ben;
    }

    public String getSeg_ape_ben() {
        return seg_ape_ben;
    }

    public void setSeg_ape_ben(String seg_ape_ben) {
        this.seg_ape_ben = seg_ape_ben;
    }

    public String getEstado_beneficiario() {
        return estado_beneficiario;
    }

    public void setEstado_beneficiario(String estado_beneficiario) {
        this.estado_beneficiario = estado_beneficiario;
    }

    public String getCondicion_inv() {
        return condicion_inv;
    }

    public void setCondicion_inv(String condicion_inv) {
        this.condicion_inv = condicion_inv;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNum_grupo_fam() {
        return num_grupo_fam;
    }

    public void setNum_grupo_fam(String num_grupo_fam) {
        this.num_grupo_fam = num_grupo_fam;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getNivel_educativo() {
        return nivel_educativo;
    }

    public void setNivel_educativo(String nivel_educativo) {
        this.nivel_educativo = nivel_educativo;
    }

    public String getSitio_pago() {
        return sitio_pago;
    }

    public void setSitio_pago(String sitio_pago) {
        this.sitio_pago = sitio_pago;
    }

    public String getEstado_grupo_fam() {
        return estado_grupo_fam;
    }

    public void setEstado_grupo_fam(String estado_grupo_fam) {
        this.estado_grupo_fam = estado_grupo_fam;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTipo_doc_otro_padre() {
        return tipo_doc_otro_padre;
    }

    public void setTipo_doc_otro_padre(String tipo_doc_otro_padre) {
        this.tipo_doc_otro_padre = tipo_doc_otro_padre;
    }

    public String getNum_doc_otro_padre() {
        return num_doc_otro_padre;
    }

    public void setNum_doc_otro_padre(String num_doc_otro_padre) {
        this.num_doc_otro_padre = num_doc_otro_padre;
    }

    public String getCertificado_vigente() {
        return certificado_vigente;
    }

    public void setCertificado_vigente(String certificado_vigente) {
        this.certificado_vigente = certificado_vigente;
    }

    public String getFecha_ini_cert() {
        return fecha_ini_cert;
    }

    public void setFecha_ini_cert(String fecha_ini_cert) {
        this.fecha_ini_cert = fecha_ini_cert;
    }

    public String getFecha_fin_cert() {
        return fecha_fin_cert;
    }

    public void setFecha_fin_cert(String fecha_fin_cert) {
        this.fecha_fin_cert = fecha_fin_cert;
    }

    public String getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    public void setFecha_liquidacion(String fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }

    public String getTipo_doc_afi() {
        return tipo_doc_afi;
    }

    public void setTipo_doc_afi(String tipo_doc_afi) {
        this.tipo_doc_afi = tipo_doc_afi;
    }

    public String getNum_doc_afi() {
        return num_doc_afi;
    }

    public void setNum_doc_afi(String num_doc_afi) {
        this.num_doc_afi = num_doc_afi;
    }
    

}
