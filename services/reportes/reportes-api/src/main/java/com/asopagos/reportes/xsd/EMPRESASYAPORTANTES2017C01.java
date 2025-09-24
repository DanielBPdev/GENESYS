//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.08.15 a las 09:45:24 AM COT 
//


package com.asopagos.reportes.xsd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="T_EMPRESAS_Y_APORTANTES_2017C01" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="TIP_IDENTIFICACION"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="NUM_IDENTIFICACION"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="16"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="NOM_EMPRESA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="200"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="COD_MUNICIPIO_DANE"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="5"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="DIR_CORRESPONDECIA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="100"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="EST_VINCULACION"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="TIP_APORTANTE"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="TIP_SECTOR"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="ACT_ECONOMICA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="4"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="SIT_EMPRESA_LEY_1429"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="PRO_PAGO_LEY_1429"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="SIT_EMPRESA_LEY_590"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="PRO_PAGO_LEY_590"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="APO_TOTAL_MENSUAL"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *                         &lt;totalDigits value="18"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="INT_PAGADOS_MORA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *                         &lt;totalDigits value="18"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="VAL_REINTEGROS"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *                         &lt;totalDigits value="18"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tempresasyaportantes2017C01"
})
@XmlRootElement(name = "EMPRESAS_Y_APORTANTES_2017C01")
public class EMPRESASYAPORTANTES2017C01 {

    @XmlElement(name = "T_EMPRESAS_Y_APORTANTES_2017C01", required = true)
    protected List<EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01> tempresasyaportantes2017C01;

    /**
     * Gets the value of the tempresasyaportantes2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tempresasyaportantes2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTEMPRESASYAPORTANTES2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 }
     * 
     * 
     */
    public List<EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01> getTEMPRESASYAPORTANTES2017C01() {
        if (tempresasyaportantes2017C01 == null) {
            tempresasyaportantes2017C01 = new ArrayList<EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01>();
        }
        return this.tempresasyaportantes2017C01;
    }

    public EMPRESASYAPORTANTES2017C01 withTEMPRESASYAPORTANTES2017C01(EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 ... values) {
        if (values!= null) {
            for (EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 value: values) {
                getTEMPRESASYAPORTANTES2017C01().add(value);
            }
        }
        return this;
    }

    public EMPRESASYAPORTANTES2017C01 withTEMPRESASYAPORTANTES2017C01(Collection<EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01> values) {
        if (values!= null) {
            getTEMPRESASYAPORTANTES2017C01().addAll(values);
        }
        return this;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="TIP_IDENTIFICACION"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="NUM_IDENTIFICACION"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="16"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="NOM_EMPRESA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="200"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="COD_MUNICIPIO_DANE"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="5"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="DIR_CORRESPONDECIA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="100"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="EST_VINCULACION"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="TIP_APORTANTE"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="TIP_SECTOR"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="ACT_ECONOMICA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="4"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="SIT_EMPRESA_LEY_1429"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="PRO_PAGO_LEY_1429"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="SIT_EMPRESA_LEY_590"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="PRO_PAGO_LEY_590"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="APO_TOTAL_MENSUAL"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
     *               &lt;totalDigits value="18"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="INT_PAGADOS_MORA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
     *               &lt;totalDigits value="18"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="VAL_REINTEGROS"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
     *               &lt;totalDigits value="18"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tipidentificacion",
        "numidentificacion",
        "nomempresa",
        "codmunicipiodane",
        "dircorrespondecia",
        "estvinculacion",
        "tipaportante",
        "tipsector",
        "acteconomica",
        "sitempresaley1429",
        "propagoley1429",
        "sitempresaley590",
        "propagoley590",
        "apototalmensual",
        "intpagadosmora",
        "valreintegros"
    })
    public static class TEMPRESASYAPORTANTES2017C01 {

        @XmlElement(name = "TIP_IDENTIFICACION", required = true)
        protected String tipidentificacion;
        @XmlElement(name = "NUM_IDENTIFICACION", required = true)
        protected String numidentificacion;
        @XmlElement(name = "NOM_EMPRESA", required = true)
        protected String nomempresa;
        @XmlElement(name = "COD_MUNICIPIO_DANE", required = true)
        protected String codmunicipiodane;
        @XmlElement(name = "DIR_CORRESPONDECIA", required = true)
        protected String dircorrespondecia;
        @XmlElement(name = "EST_VINCULACION", required = true)
        protected String estvinculacion;
        @XmlElement(name = "TIP_APORTANTE", required = true)
        protected String tipaportante;
        @XmlElement(name = "TIP_SECTOR", required = true)
        protected String tipsector;
        @XmlElement(name = "ACT_ECONOMICA", required = true)
        protected String acteconomica;
        @XmlElement(name = "SIT_EMPRESA_LEY_1429", required = true)
        protected String sitempresaley1429;
        @XmlElement(name = "PRO_PAGO_LEY_1429", required = true)
        protected String propagoley1429;
        @XmlElement(name = "SIT_EMPRESA_LEY_590", required = true)
        protected String sitempresaley590;
        @XmlElement(name = "PRO_PAGO_LEY_590", required = true)
        protected String propagoley590;
        @XmlElement(name = "APO_TOTAL_MENSUAL", required = true)
        protected BigInteger apototalmensual;
        @XmlElement(name = "INT_PAGADOS_MORA", required = true)
        protected BigInteger intpagadosmora;
        @XmlElement(name = "VAL_REINTEGROS", required = true)
        protected BigInteger valreintegros;

        /**
         * Obtiene el valor de la propiedad tipidentificacion.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getTIPIDENTIFICACION() {
            return tipidentificacion;
        }

        /**
         * Define el valor de la propiedad tipidentificacion.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTIPIDENTIFICACION(String value) {
            this.tipidentificacion = value;
        }

        /**
         * Obtiene el valor de la propiedad numidentificacion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMIDENTIFICACION() {
            return numidentificacion;
        }

        /**
         * Define el valor de la propiedad numidentificacion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMIDENTIFICACION(String value) {
            this.numidentificacion = value;
        }

        /**
         * Obtiene el valor de la propiedad nomempresa.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNOMEMPRESA() {
            return nomempresa;
        }

        /**
         * Define el valor de la propiedad nomempresa.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNOMEMPRESA(String value) {
            this.nomempresa = value;
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
         * Obtiene el valor de la propiedad dircorrespondecia.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDIRCORRESPONDECIA() {
            return dircorrespondecia;
        }

        /**
         * Define el valor de la propiedad dircorrespondecia.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDIRCORRESPONDECIA(String value) {
            this.dircorrespondecia = value;
        }

        /**
         * Obtiene el valor de la propiedad estvinculacion.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getESTVINCULACION() {
            return estvinculacion;
        }

        /**
         * Define el valor de la propiedad estvinculacion.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setESTVINCULACION(String value) {
            this.estvinculacion = value;
        }

        /**
         * Obtiene el valor de la propiedad tipaportante.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getTIPAPORTANTE() {
            return tipaportante;
        }

        /**
         * Define el valor de la propiedad tipaportante.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTIPAPORTANTE(String value) {
            this.tipaportante = value;
        }

        /**
         * Obtiene el valor de la propiedad tipsector.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getTIPSECTOR() {
            return tipsector;
        }

        /**
         * Define el valor de la propiedad tipsector.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTIPSECTOR(String value) {
            this.tipsector = value;
        }

        /**
         * Obtiene el valor de la propiedad acteconomica.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getACTECONOMICA() {
            return acteconomica;
        }

        /**
         * Define el valor de la propiedad acteconomica.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setACTECONOMICA(String value) {
            this.acteconomica = value;
        }

        /**
         * Obtiene el valor de la propiedad sitempresaley1429.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getSITEMPRESALEY1429() {
            return sitempresaley1429;
        }

        /**
         * Define el valor de la propiedad sitempresaley1429.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSITEMPRESALEY1429(String value) {
            this.sitempresaley1429 = value;
        }

        /**
         * Obtiene el valor de la propiedad propagoley1429.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getPROPAGOLEY1429() {
            return propagoley1429;
        }

        /**
         * Define el valor de la propiedad propagoley1429.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPROPAGOLEY1429(String value) {
            this.propagoley1429 = value;
        }

        /**
         * Obtiene el valor de la propiedad sitempresaley590.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getSITEMPRESALEY590() {
            return sitempresaley590;
        }

        /**
         * Define el valor de la propiedad sitempresaley590.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSITEMPRESALEY590(String value) {
            this.sitempresaley590 = value;
        }

        /**
         * Obtiene el valor de la propiedad propagoley590.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getPROPAGOLEY590() {
            return propagoley590;
        }

        /**
         * Define el valor de la propiedad propagoley590.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPROPAGOLEY590(String value) {
            this.propagoley590 = value;
        }

        /**
         * Obtiene el valor de la propiedad apototalmensual.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigInteger getAPOTOTALMENSUAL() {
            return apototalmensual;
        }

        /**
         * Define el valor de la propiedad apototalmensual.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setAPOTOTALMENSUAL(BigInteger value) {
            this.apototalmensual = value;
        }

        /**
         * Obtiene el valor de la propiedad intpagadosmora.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigInteger getINTPAGADOSMORA() {
            return intpagadosmora;
        }

        /**
         * Define el valor de la propiedad intpagadosmora.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setINTPAGADOSMORA(BigInteger value) {
            this.intpagadosmora = value;
        }

        /**
         * Obtiene el valor de la propiedad valreintegros.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigInteger getVALREINTEGROS() {
            return valreintegros;
        }

        /**
         * Define el valor de la propiedad valreintegros.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setVALREINTEGROS(BigInteger value) {
            this.valreintegros = value;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withTIPIDENTIFICACION(String value) {
            setTIPIDENTIFICACION(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withNUMIDENTIFICACION(String value) {
            setNUMIDENTIFICACION(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withNOMEMPRESA(String value) {
            setNOMEMPRESA(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withCODMUNICIPIODANE(String value) {
            setCODMUNICIPIODANE(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withDIRCORRESPONDECIA(String value) {
            setDIRCORRESPONDECIA(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withESTVINCULACION(String value) {
            setESTVINCULACION(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withTIPAPORTANTE(String value) {
            setTIPAPORTANTE(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withTIPSECTOR(String value) {
            setTIPSECTOR(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withACTECONOMICA(String value) {
            setACTECONOMICA(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withSITEMPRESALEY1429(String value) {
            setSITEMPRESALEY1429(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withPROPAGOLEY1429(String value) {
            setPROPAGOLEY1429(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withSITEMPRESALEY590(String value) {
            setSITEMPRESALEY590(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withPROPAGOLEY590(String value) {
            setPROPAGOLEY590(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withAPOTOTALMENSUAL(BigInteger value) {
            setAPOTOTALMENSUAL(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withINTPAGADOSMORA(BigInteger value) {
            setINTPAGADOSMORA(value);
            return this;
        }

        public EMPRESASYAPORTANTES2017C01 .TEMPRESASYAPORTANTES2017C01 withVALREINTEGROS(BigInteger value) {
            setVALREINTEGROS(value);
            return this;
        }

    }

}
