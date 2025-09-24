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
public class ConsultarInformacionCompletaAfiliadoDTO implements Serializable{
       /**
     * 
     */
 private static final long serialVersionUID = 1L;
 private String resultado_consulta;
private String pri_nom_afiliado;
private String seg_nom_afiliado;
private String pri_ape_afiliado;
private String seg_ape_afiliado;
private String tipo_doc_afiliado;
private String num_doc_afiliado;
private String estado_afiliado;
private String categoria_afiliado;
private String clasificacion;
private String clase_trabajador;
private String tipo_afiliacion;
private String cod_depto_ubicacion;
private String cod_mun_ubicacion;
private String correo_afiliado;
private String fecha_nacimiento;
private String tipo_doc_emp;
private String num_doc_emp;
private String estado_emp;
private String nombre_comercial;

    public ConsultarInformacionCompletaAfiliadoDTO() {
    }

    public ConsultarInformacionCompletaAfiliadoDTO(String resultado_consulta, String pri_nom_afiliado, String seg_nom_afiliado, String pri_ape_afiliado, String seg_ape_afiliado, String tipo_doc_afiliado, String num_doc_afiliado, String estado_afiliado, String categoria_afiliado, String clasificacion, String clase_trabajador, String tipo_afiliacion, String cod_depto_ubicacion, String cod_mun_ubicacion, String correo_afiliado, String fecha_nacimiento, String tipo_doc_emp, String num_doc_emp, String estado_emp, String nombre_comercial) {
        this.resultado_consulta = resultado_consulta;
        this.pri_nom_afiliado = pri_nom_afiliado;
        this.seg_nom_afiliado = seg_nom_afiliado;
        this.pri_ape_afiliado = pri_ape_afiliado;
        this.seg_ape_afiliado = seg_ape_afiliado;
        this.tipo_doc_afiliado = tipo_doc_afiliado;
        this.num_doc_afiliado = num_doc_afiliado;
        this.estado_afiliado = estado_afiliado;
        this.categoria_afiliado = categoria_afiliado;
        this.clasificacion = clasificacion;
        this.clase_trabajador = clase_trabajador;
        this.tipo_afiliacion = tipo_afiliacion;
        this.cod_depto_ubicacion = cod_depto_ubicacion;
        this.cod_mun_ubicacion = cod_mun_ubicacion;
        this.correo_afiliado = correo_afiliado;
        this.fecha_nacimiento = fecha_nacimiento;
        this.tipo_doc_emp = tipo_doc_emp;
        this.num_doc_emp = num_doc_emp;
        this.estado_emp = estado_emp;
        this.nombre_comercial = nombre_comercial;
    }

    public String getResultado_consulta() {
        return resultado_consulta;
    }

    public void setResultado_consulta(String resultado_consulta) {
        this.resultado_consulta = resultado_consulta;
    }

    public String getPri_nom_afiliado() {
        return pri_nom_afiliado;
    }

    public void setPri_nom_afiliado(String pri_nom_afiliado) {
        this.pri_nom_afiliado = pri_nom_afiliado;
    }

    public String getSeg_nom_afiliado() {
        return seg_nom_afiliado;
    }

    public void setSeg_nom_afiliado(String seg_nom_afiliado) {
        this.seg_nom_afiliado = seg_nom_afiliado;
    }

    public String getPri_ape_afiliado() {
        return pri_ape_afiliado;
    }

    public void setPri_ape_afiliado(String pri_ape_afiliado) {
        this.pri_ape_afiliado = pri_ape_afiliado;
    }

    public String getSeg_ape_afiliado() {
        return seg_ape_afiliado;
    }

    public void setSeg_ape_afiliado(String seg_ape_afiliado) {
        this.seg_ape_afiliado = seg_ape_afiliado;
    }

    public String getTipo_doc_afiliado() {
        return tipo_doc_afiliado;
    }

    public void setTipo_doc_afiliado(String tipo_doc_afiliado) {
        this.tipo_doc_afiliado = tipo_doc_afiliado;
    }

    public String getNum_doc_afiliado() {
        return num_doc_afiliado;
    }

    public void setNum_doc_afiliado(String num_doc_afiliado) {
        this.num_doc_afiliado = num_doc_afiliado;
    }

    public String getEstado_afiliado() {
        return estado_afiliado;
    }

    public void setEstado_afiliado(String estado_afiliado) {
        this.estado_afiliado = estado_afiliado;
    }

    public String getCategoria_afiliado() {
        return categoria_afiliado;
    }

    public void setCategoria_afiliado(String categoria_afiliado) {
        this.categoria_afiliado = categoria_afiliado;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClase_trabajador() {
        return clase_trabajador;
    }

    public void setClase_trabajador(String clase_trabajador) {
        this.clase_trabajador = clase_trabajador;
    }

    public String getTipo_afiliacion() {
        return tipo_afiliacion;
    }

    public void setTipo_afiliacion(String tipo_afiliacion) {
        this.tipo_afiliacion = tipo_afiliacion;
    }

    public String getCod_depto_ubicacion() {
        return cod_depto_ubicacion;
    }

    public void setCod_depto_ubicacion(String cod_depto_ubicacion) {
        this.cod_depto_ubicacion = cod_depto_ubicacion;
    }

    public String getCod_mun_ubicacion() {
        return cod_mun_ubicacion;
    }

    public void setCod_mun_ubicacion(String cod_mun_ubicacion) {
        this.cod_mun_ubicacion = cod_mun_ubicacion;
    }

    public String getCorreo_afiliado() {
        return correo_afiliado;
    }

    public void setCorreo_afiliado(String correo_afiliado) {
        this.correo_afiliado = correo_afiliado;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTipo_doc_emp() {
        return tipo_doc_emp;
    }

    public void setTipo_doc_emp(String tipo_doc_emp) {
        this.tipo_doc_emp = tipo_doc_emp;
    }

    public String getNum_doc_emp() {
        return num_doc_emp;
    }

    public void setNum_doc_emp(String num_doc_emp) {
        this.num_doc_emp = num_doc_emp;
    }

    public String getEstado_emp() {
        return estado_emp;
    }

    public void setEstado_emp(String estado_emp) {
        this.estado_emp = estado_emp;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }


}
