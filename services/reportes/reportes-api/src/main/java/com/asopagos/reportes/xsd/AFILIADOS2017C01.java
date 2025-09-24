//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.03.10 a las 09:35:59 AM COT 
//


package com.asopagos.reportes.xsd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.asopagos.reportes.xsd.AFILIADOS2017C01;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="T_AFILIADOS_2017C01">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TIP_IDENTIFICACION_EMPRESA">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_IDENTIFICACION_EMPRESA">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="16"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TIP_IDENTIFICACION_AFILIADO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_IDENTIFICACION_AFILIADO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="15"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PRI_NOMBRE">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SEG_NOMBRE">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PRI_APELLIDO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SEG_APELLIDO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FEC_NACIMIENTO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="8"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="GENERO_CCF">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ORI_SEXUAL">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NIVEL_ESCOLARIDAD">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COD_OCUPACION_DANE">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="8"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FACTOR_VULNERABILIDAD">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ESTADO_CIVIL">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PERTENENCIA_ETNICA">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PAIS_RESIDENCIA_BENEFICIARIO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="3"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COD_MUNICIPIO_DANE">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="5"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ARE_GEOGRAFICA_RESIDENCIA">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COD_MUNICIPIO_LABOR_DANE">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="5"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="AREA_GEOGRAFICA_LABOR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SAL_BASICO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="18"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TIP_AFILIADO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CATEGORIA_CCF">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="BEN_CUOTA_MONETARIA">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tafiliados2017C01"
})
@XmlRootElement(name = "AFILIADOS_2017C01")
public class AFILIADOS2017C01 {

    @XmlElement(name = "T_AFILIADOS_2017C01")
    protected List<AFILIADOS2017C01 .TAFILIADOS2017C01> tafiliados2017C01;

    /**
     * Gets the value of the tafiliados2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tafiliados2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTAFILIADOS2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AFILIADOS2017C01 .TAFILIADOS2017C01 }
     * 
     * 
     */
    public List<AFILIADOS2017C01 .TAFILIADOS2017C01> getTAFILIADOS2017C01() {
        if (tafiliados2017C01 == null) {
            tafiliados2017C01 = new ArrayList<AFILIADOS2017C01 .TAFILIADOS2017C01>();
        }
        return this.tafiliados2017C01;
    }

    public AFILIADOS2017C01 withTAFILIADOS2017C01(AFILIADOS2017C01 .TAFILIADOS2017C01 ... values) {
        if (values!= null) {
            for (AFILIADOS2017C01 .TAFILIADOS2017C01 value: values) {
                getTAFILIADOS2017C01().add(value);
            }
        }
        return this;
    }

    public AFILIADOS2017C01 withTAFILIADOS2017C01(Collection<AFILIADOS2017C01 .TAFILIADOS2017C01> values) {
        if (values!= null) {
            getTAFILIADOS2017C01().addAll(values);
        }
        return this;
    }

    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="TIP_IDENTIFICACION_EMPRESA">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_IDENTIFICACION_EMPRESA">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="16"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TIP_IDENTIFICACION_AFILIADO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_IDENTIFICACION_AFILIADO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="15"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PRI_NOMBRE">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SEG_NOMBRE">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PRI_APELLIDO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SEG_APELLIDO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FEC_NACIMIENTO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="8"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="GENERO_CCF">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ORI_SEXUAL">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NIVEL_ESCOLARIDAD">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COD_OCUPACION_DANE">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="8"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FACTOR_VULNERABILIDAD">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ESTADO_CIVIL">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PERTENENCIA_ETNICA">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PAIS_RESIDENCIA_BENEFICIARIO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="3"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COD_MUNICIPIO_DANE">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="5"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ARE_GEOGRAFICA_RESIDENCIA">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COD_MUNICIPIO_LABOR_DANE">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="5"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="AREA_GEOGRAFICA_LABOR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SAL_BASICO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="18"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TIP_AFILIADO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CATEGORIA_CCF">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="BEN_CUOTA_MONETARIA">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tipidentificacionempresa",
        "numidentificacionempresa",
        "tipidentificacionafiliado",
        "numidentificacionafiliado",
        "prinombre",
        "segnombre",
        "priapellido",
        "segapellido",
        "fecnacimiento",
        "generoccf",
        "orisexual",
        "nivelescolaridad",
        "codocupaciondane",
        "factorvulnerabilidad",
        "estadocivil",
        "pertenenciaetnica",
        "paisresidenciabeneficiario",
        "codmunicipiodane",
        "aregeograficaresidencia",
        "codmunicipiolabordane",
        "areageograficalabor",
        "salbasico",
        "tipafiliado",
        "categoriaccf",
        "bencuotamonetaria"
    })
    public static class TAFILIADOS2017C01 {

        @XmlElement(name = "TIP_IDENTIFICACION_EMPRESA", required = true)
        protected String tipidentificacionempresa;
        @XmlElement(name = "NUM_IDENTIFICACION_EMPRESA", required = true)
        protected String numidentificacionempresa;
        @XmlElement(name = "TIP_IDENTIFICACION_AFILIADO", required = true)
        protected String tipidentificacionafiliado;
        @XmlElement(name = "NUM_IDENTIFICACION_AFILIADO", required = true)
        protected String numidentificacionafiliado;
        @XmlElement(name = "PRI_NOMBRE", required = true)
        protected String prinombre;
        @XmlElement(name = "SEG_NOMBRE", required = true)
        protected String segnombre;
        @XmlElement(name = "PRI_APELLIDO", required = true)
        protected String priapellido;
        @XmlElement(name = "SEG_APELLIDO", required = true)
        protected String segapellido;
        @XmlElement(name = "FEC_NACIMIENTO", required = true)
        protected String fecnacimiento;
        @XmlElement(name = "GENERO_CCF", required = true)
        protected String generoccf;
        @XmlElement(name = "ORI_SEXUAL", required = true)
        protected String orisexual;
        @XmlElement(name = "NIVEL_ESCOLARIDAD", required = true)
        protected String nivelescolaridad;
        @XmlElement(name = "COD_OCUPACION_DANE", required = true)
        protected String codocupaciondane;
        @XmlElement(name = "FACTOR_VULNERABILIDAD", required = true)
        protected String factorvulnerabilidad;
        @XmlElement(name = "ESTADO_CIVIL", required = true)
        protected String estadocivil;
        @XmlElement(name = "PERTENENCIA_ETNICA", required = true)
        protected String pertenenciaetnica;
        @XmlElement(name = "PAIS_RESIDENCIA_BENEFICIARIO", required = true)
        protected String paisresidenciabeneficiario;
        @XmlElement(name = "COD_MUNICIPIO_DANE", required = true)
        protected String codmunicipiodane;
        @XmlElement(name = "ARE_GEOGRAFICA_RESIDENCIA", required = true)
        protected String aregeograficaresidencia;
        @XmlElement(name = "COD_MUNICIPIO_LABOR_DANE", required = true)
        protected String codmunicipiolabordane;
        @XmlElement(name = "AREA_GEOGRAFICA_LABOR", required = true)
        protected String areageograficalabor;
        @XmlElement(name = "SAL_BASICO", required = true)
        protected String salbasico;
        @XmlElement(name = "TIP_AFILIADO", required = true)
        protected String tipafiliado;
        @XmlElement(name = "CATEGORIA_CCF", required = true)
        protected String categoriaccf;
        @XmlElement(name = "BEN_CUOTA_MONETARIA", required = true)
        protected String bencuotamonetaria;

        /**
         * Obtiene el valor de la propiedad tipidentificacionempresa.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTIPIDENTIFICACIONEMPRESA() {
            return tipidentificacionempresa;
        }

        /**
         * Define el valor de la propiedad tipidentificacionempresa.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTIPIDENTIFICACIONEMPRESA(String value) {
            this.tipidentificacionempresa = value;
        }

        /**
         * Obtiene el valor de la propiedad numidentificacionempresa.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMIDENTIFICACIONEMPRESA() {
            return numidentificacionempresa;
        }

        /**
         * Define el valor de la propiedad numidentificacionempresa.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMIDENTIFICACIONEMPRESA(String value) {
            this.numidentificacionempresa = value;
        }

        /**
         * Obtiene el valor de la propiedad tipidentificacionafiliado.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTIPIDENTIFICACIONAFILIADO() {
            return tipidentificacionafiliado;
        }

        /**
         * Define el valor de la propiedad tipidentificacionafiliado.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTIPIDENTIFICACIONAFILIADO(String value) {
            this.tipidentificacionafiliado = value;
        }

        /**
         * Obtiene el valor de la propiedad numidentificacionafiliado.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMIDENTIFICACIONAFILIADO() {
            return numidentificacionafiliado;
        }

        /**
         * Define el valor de la propiedad numidentificacionafiliado.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMIDENTIFICACIONAFILIADO(String value) {
            this.numidentificacionafiliado = value;
        }

        /**
         * Obtiene el valor de la propiedad prinombre.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPRINOMBRE() {
            return prinombre;
        }

        /**
         * Define el valor de la propiedad prinombre.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPRINOMBRE(String value) {
            this.prinombre = value;
        }

        /**
         * Obtiene el valor de la propiedad segnombre.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSEGNOMBRE() {
            return segnombre;
        }

        /**
         * Define el valor de la propiedad segnombre.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSEGNOMBRE(String value) {
            this.segnombre = value;
        }

        /**
         * Obtiene el valor de la propiedad priapellido.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPRIAPELLIDO() {
            return priapellido;
        }

        /**
         * Define el valor de la propiedad priapellido.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPRIAPELLIDO(String value) {
            this.priapellido = value;
        }

        /**
         * Obtiene el valor de la propiedad segapellido.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSEGAPELLIDO() {
            return segapellido;
        }

        /**
         * Define el valor de la propiedad segapellido.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSEGAPELLIDO(String value) {
            this.segapellido = value;
        }

        /**
         * Obtiene el valor de la propiedad fecnacimiento.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFECNACIMIENTO() {
            return fecnacimiento;
        }

        /**
         * Define el valor de la propiedad fecnacimiento.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFECNACIMIENTO(String value) {
            this.fecnacimiento = value;
        }

        /**
         * Obtiene el valor de la propiedad generoccf.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGENEROCCF() {
            return generoccf;
        }

        /**
         * Define el valor de la propiedad generoccf.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGENEROCCF(String value) {
            this.generoccf = value;
        }

        /**
         * Obtiene el valor de la propiedad orisexual.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getORISEXUAL() {
            return orisexual;
        }

        /**
         * Define el valor de la propiedad orisexual.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setORISEXUAL(String value) {
            this.orisexual = value;
        }

        /**
         * Obtiene el valor de la propiedad nivelescolaridad.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNIVELESCOLARIDAD() {
            return nivelescolaridad;
        }

        /**
         * Define el valor de la propiedad nivelescolaridad.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNIVELESCOLARIDAD(String value) {
            this.nivelescolaridad = value;
        }

        /**
         * Obtiene el valor de la propiedad codocupaciondane.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODOCUPACIONDANE() {
            return codocupaciondane;
        }

        /**
         * Define el valor de la propiedad codocupaciondane.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODOCUPACIONDANE(String value) {
            this.codocupaciondane = value;
        }

        /**
         * Obtiene el valor de la propiedad factorvulnerabilidad.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFACTORVULNERABILIDAD() {
            return factorvulnerabilidad;
        }

        /**
         * Define el valor de la propiedad factorvulnerabilidad.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFACTORVULNERABILIDAD(String value) {
            this.factorvulnerabilidad = value;
        }

        /**
         * Obtiene el valor de la propiedad estadocivil.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getESTADOCIVIL() {
            return estadocivil;
        }

        /**
         * Define el valor de la propiedad estadocivil.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setESTADOCIVIL(String value) {
            this.estadocivil = value;
        }

        /**
         * Obtiene el valor de la propiedad pertenenciaetnica.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPERTENENCIAETNICA() {
            return pertenenciaetnica;
        }

        /**
         * Define el valor de la propiedad pertenenciaetnica.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPERTENENCIAETNICA(String value) {
            this.pertenenciaetnica = value;
        }

        /**
         * Obtiene el valor de la propiedad paisresidenciabeneficiario.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPAISRESIDENCIABENEFICIARIO() {
            return paisresidenciabeneficiario;
        }

        /**
         * Define el valor de la propiedad paisresidenciabeneficiario.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPAISRESIDENCIABENEFICIARIO(String value) {
            this.paisresidenciabeneficiario = value;
        }

        /**
         * Obtiene el valor de la propiedad codmunicipiodane.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODMUNICIPIODANE() {
            return codmunicipiodane;
        }

        /**
         * Define el valor de la propiedad codmunicipiodane.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODMUNICIPIODANE(String value) {
            this.codmunicipiodane = value;
        }

        /**
         * Obtiene el valor de la propiedad aregeograficaresidencia.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAREGEOGRAFICARESIDENCIA() {
            return aregeograficaresidencia;
        }

        /**
         * Define el valor de la propiedad aregeograficaresidencia.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAREGEOGRAFICARESIDENCIA(String value) {
            this.aregeograficaresidencia = value;
        }

        /**
         * Obtiene el valor de la propiedad codmunicipiolabordane.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODMUNICIPIOLABORDANE() {
            return codmunicipiolabordane;
        }

        /**
         * Define el valor de la propiedad codmunicipiolabordane.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODMUNICIPIOLABORDANE(String value) {
            this.codmunicipiolabordane = value;
        }

        /**
         * Obtiene el valor de la propiedad areageograficalabor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAREAGEOGRAFICALABOR() {
            return areageograficalabor;
        }

        /**
         * Define el valor de la propiedad areageograficalabor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAREAGEOGRAFICALABOR(String value) {
            this.areageograficalabor = value;
        }

        /**
         * Obtiene el valor de la propiedad salbasico.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSALBASICO() {
            return salbasico;
        }

        /**
         * Define el valor de la propiedad salbasico.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSALBASICO(String value) {
            this.salbasico = value;
        }

        /**
         * Obtiene el valor de la propiedad tipafiliado.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTIPAFILIADO() {
            return tipafiliado;
        }

        /**
         * Define el valor de la propiedad tipafiliado.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTIPAFILIADO(String value) {
            this.tipafiliado = value;
        }

        /**
         * Obtiene el valor de la propiedad categoriaccf.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCATEGORIACCF() {
            return categoriaccf;
        }

        /**
         * Define el valor de la propiedad categoriaccf.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCATEGORIACCF(String value) {
            this.categoriaccf = value;
        }

        /**
         * Obtiene el valor de la propiedad bencuotamonetaria.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBENCUOTAMONETARIA() {
            return bencuotamonetaria;
        }

        /**
         * Define el valor de la propiedad bencuotamonetaria.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBENCUOTAMONETARIA(String value) {
            this.bencuotamonetaria = value;
        }

    }

}
