/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;

/**
 *
 * @author Alexander.camelo
 */

public class EspecieLiquidacionManualDTO implements Serializable {
	/** */
private static final long serialVersionUID = 1L;
private String resultado_consulta;
private String tipo_doc_adminsub;
private String num_doc_adminsub;
private String pri_nom_adminsub;
private String seg_nom_admin_sub;
private String pri_ape_admin_sub;
private String seg_ape_adminsub;
private String corr_ben_pago;
private String celular_ben_pago;
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
private String fecha_fall_trab;
private String cod_depto_ubicacion;
private String cod_mun_ubicacion;
private String correo_afiliado;
private String aporte_pila;
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
private String fecha_afiliacion_ben;
private String fecha_retiro_ben;
private String anio_certificado;
private String tipo_doc_emp;
private String num_doc_emp;
private String estado_emp;
private String nombre_comercial;
private String estado_mora;
private String estado_subsidio;
private String periodo_liquidado;
private String certificado_vigente;
private String fecha_ini_cert;
private String fecha_fin_cert;
private String fecha_liquidacion;

    public EspecieLiquidacionManualDTO() {
    }

    public EspecieLiquidacionManualDTO(String resultado_consulta, String tipo_doc_adminsub, String num_doc_adminsub, String pri_nom_adminsub, String seg_nom_admin_sub, String pri_ape_admin_sub, String seg_ape_adminsub, String corr_ben_pago, String celular_ben_pago, String pri_nom_afiliado, String seg_nom_afiliado, String pri_ape_afiliado, String seg_ape_afiliado, String tipo_doc_afiliado, String num_doc_afiliado, String estado_afiliado, String categoria_afiliado, String clasificacion, String clase_trabajador, String tipo_afiliacion, String fecha_fall_trab, String cod_depto_ubicacion, String cod_mun_ubicacion, String correo_afiliado, String aporte_pila, String tipo_doc_ben, String num_doc_ben, String pri_nom_ben, String seg_nom_ben, String pri_ape_ben, String seg_ape_ben, String estado_beneficiario, String condicion_inv, String edad, String fecha_nacimiento, String num_grupo_fam, String grado, String nivel_educativo, String sitio_pago, String estado_grupo_fam, String parentesco, String tipo_doc_otro_padre, String num_doc_otro_padre, String fecha_afiliacion_ben, String fecha_retiro_ben, String anio_certificado, String tipo_doc_emp, String num_doc_emp, String estado_emp, String nombre_comercial, String estado_mora, String estado_subsidio, String periodo_liquidado, String certificado_vigente, String fecha_ini_cert, String fecha_fin_cert, String fecha_liquidacion) {
        this.resultado_consulta = resultado_consulta;
        this.tipo_doc_adminsub = tipo_doc_adminsub;
        this.num_doc_adminsub = num_doc_adminsub;
        this.pri_nom_adminsub = pri_nom_adminsub;
        this.seg_nom_admin_sub = seg_nom_admin_sub;
        this.pri_ape_admin_sub = pri_ape_admin_sub;
        this.seg_ape_adminsub = seg_ape_adminsub;
        this.corr_ben_pago = corr_ben_pago;
        this.celular_ben_pago = celular_ben_pago;
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
        this.fecha_fall_trab = fecha_fall_trab;
        this.cod_depto_ubicacion = cod_depto_ubicacion;
        this.cod_mun_ubicacion = cod_mun_ubicacion;
        this.correo_afiliado = correo_afiliado;
        this.aporte_pila = aporte_pila;
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
        this.fecha_afiliacion_ben = fecha_afiliacion_ben;
        this.fecha_retiro_ben = fecha_retiro_ben;
        this.anio_certificado = anio_certificado;
        this.tipo_doc_emp = tipo_doc_emp;
        this.num_doc_emp = num_doc_emp;
        this.estado_emp = estado_emp;
        this.nombre_comercial = nombre_comercial;
        this.estado_mora = estado_mora;
        this.estado_subsidio = estado_subsidio;
        this.periodo_liquidado = periodo_liquidado;
        this.certificado_vigente = certificado_vigente;
        this.fecha_ini_cert = fecha_ini_cert;
        this.fecha_fin_cert = fecha_fin_cert;
        this.fecha_liquidacion = fecha_liquidacion;
    }

    public String getResultado_consulta() {
        return resultado_consulta;
    }

    public void setResultado_consulta(String resultado_consulta) {
        this.resultado_consulta = resultado_consulta;
    }

    public String getTipo_doc_adminsub() {
        return tipo_doc_adminsub;
    }

    public void setTipo_doc_adminsub(String tipo_doc_adminsub) {
        this.tipo_doc_adminsub = tipo_doc_adminsub;
    }

    public String getNum_doc_adminsub() {
        return num_doc_adminsub;
    }

    public void setNum_doc_adminsub(String num_doc_adminsub) {
        this.num_doc_adminsub = num_doc_adminsub;
    }

    public String getPri_nom_adminsub() {
        return pri_nom_adminsub;
    }

    public void setPri_nom_adminsub(String pri_nom_adminsub) {
        this.pri_nom_adminsub = pri_nom_adminsub;
    }

    public String getSeg_nom_admin_sub() {
        return seg_nom_admin_sub;
    }

    public void setSeg_nom_admin_sub(String seg_nom_admin_sub) {
        this.seg_nom_admin_sub = seg_nom_admin_sub;
    }

    public String getPri_ape_admin_sub() {
        return pri_ape_admin_sub;
    }

    public void setPri_ape_admin_sub(String pri_ape_admin_sub) {
        this.pri_ape_admin_sub = pri_ape_admin_sub;
    }

    public String getSeg_ape_adminsub() {
        return seg_ape_adminsub;
    }

    public void setSeg_ape_adminsub(String seg_ape_adminsub) {
        this.seg_ape_adminsub = seg_ape_adminsub;
    }

    public String getCorr_ben_pago() {
        return corr_ben_pago;
    }

    public void setCorr_ben_pago(String corr_ben_pago) {
        this.corr_ben_pago = corr_ben_pago;
    }

    public String getCelular_ben_pago() {
        return celular_ben_pago;
    }

    public void setCelular_ben_pago(String celular_ben_pago) {
        this.celular_ben_pago = celular_ben_pago;
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

    public String getFecha_fall_trab() {
        return fecha_fall_trab;
    }

    public void setFecha_fall_trab(String fecha_fall_trab) {
        this.fecha_fall_trab = fecha_fall_trab;
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

    public String getAporte_pila() {
        return aporte_pila;
    }

    public void setAporte_pila(String aporte_pila) {
        this.aporte_pila = aporte_pila;
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

    public String getFecha_afiliacion_ben() {
        return fecha_afiliacion_ben;
    }

    public void setFecha_afiliacion_ben(String fecha_afiliacion_ben) {
        this.fecha_afiliacion_ben = fecha_afiliacion_ben;
    }

    public String getFecha_retiro_ben() {
        return fecha_retiro_ben;
    }

    public void setFecha_retiro_ben(String fecha_retiro_ben) {
        this.fecha_retiro_ben = fecha_retiro_ben;
    }

    public String getAnio_certificado() {
        return anio_certificado;
    }

    public void setAnio_certificado(String anio_certificado) {
        this.anio_certificado = anio_certificado;
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

    public String getEstado_mora() {
        return estado_mora;
    }

    public void setEstado_mora(String estado_mora) {
        this.estado_mora = estado_mora;
    }

    public String getEstado_subsidio() {
        return estado_subsidio;
    }

    public void setEstado_subsidio(String estado_subsidio) {
        this.estado_subsidio = estado_subsidio;
    }

    public String getPeriodo_liquidado() {
        return periodo_liquidado;
    }

    public void setPeriodo_liquidado(String periodo_liquidado) {
        this.periodo_liquidado = periodo_liquidado;
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


 }