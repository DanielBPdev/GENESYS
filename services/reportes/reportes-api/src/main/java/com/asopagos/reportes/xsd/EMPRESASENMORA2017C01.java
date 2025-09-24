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
import java.util.Date;
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
 *         &lt;element name="T_EMPRESAS_EN_MORA_2017C01" maxOccurs="unbounded"&gt;
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
 *                   &lt;element name="NUM_IDENTIFICACION_EMPRESA"&gt;
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
 *                   &lt;element name="DIR_EMPRESA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="100"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="REP_LEGAL"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="250"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="FEC_INICIO_MORA" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="SAL_MORA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="18"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="PER_MORA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="10"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="GES_COBRO"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="ACU_PAGO"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="CAR_RECUPERADA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="18"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="COR_ELECTRONICO"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="100"/&gt;
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
    "tempresasenmora2017C01"
})
@XmlRootElement(name = "EMPRESAS_EN_MORA_2017C01")
public class EMPRESASENMORA2017C01 {

    @XmlElement(name = "T_EMPRESAS_EN_MORA_2017C01", required = true)
    protected List<EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01> tempresasenmora2017C01;

    /**
     * Gets the value of the tempresasenmora2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tempresasenmora2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTEMPRESASENMORA2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 }
     * 
     * 
     */
    public List<EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01> getTEMPRESASENMORA2017C01() {
        if (tempresasenmora2017C01 == null) {
            tempresasenmora2017C01 = new ArrayList<EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01>();
        }
        return this.tempresasenmora2017C01;
    }

    public EMPRESASENMORA2017C01 withTEMPRESASENMORA2017C01(EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 ... values) {
        if (values!= null) {
            for (EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 value: values) {
                getTEMPRESASENMORA2017C01().add(value);
            }
        }
        return this;
    }

    public EMPRESASENMORA2017C01 withTEMPRESASENMORA2017C01(Collection<EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01> values) {
        if (values!= null) {
            getTEMPRESASENMORA2017C01().addAll(values);
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
     *         &lt;element name="NUM_IDENTIFICACION_EMPRESA"&gt;
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
     *         &lt;element name="DIR_EMPRESA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="100"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="REP_LEGAL"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="250"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="FEC_INICIO_MORA" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="SAL_MORA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="18"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="PER_MORA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="10"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="GES_COBRO"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="ACU_PAGO"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="CAR_RECUPERADA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="18"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="COR_ELECTRONICO"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="100"/&gt;
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
        "numidentificacionempresa",
        "nomempresa",
        "codmunicipiodane",
        "dirempresa",
        "replegal",
        "feciniciomora",
        "salmora",
        "permora",
        "gescobro",
        "acupago",
        "carrecuperada",
        "corelectronico"
    })
    public static class TEMPRESASENMORA2017C01 {

        @XmlElement(name = "TIP_IDENTIFICACION", required = true)
        protected String tipidentificacion;
        @XmlElement(name = "NUM_IDENTIFICACION_EMPRESA", required = true)
        protected String numidentificacionempresa;
        @XmlElement(name = "NOM_EMPRESA", required = true)
        protected String nomempresa;
        @XmlElement(name = "COD_MUNICIPIO_DANE", required = true)
        protected String codmunicipiodane;
        @XmlElement(name = "DIR_EMPRESA", required = true)
        protected String dirempresa;
        @XmlElement(name = "REP_LEGAL", required = true)
        protected String replegal;
        @XmlElement(name = "FEC_INICIO_MORA", required = true)
        protected String feciniciomora;
        @XmlElement(name = "SAL_MORA", required = true)
        protected String salmora;
        @XmlElement(name = "PER_MORA", required = true)
        protected String permora;
        @XmlElement(name = "GES_COBRO", required = true)
        protected String gescobro;
        @XmlElement(name = "ACU_PAGO", required = true)
        protected String acupago;
        @XmlElement(name = "CAR_RECUPERADA", required = true)
        protected String carrecuperada;
        @XmlElement(name = "COR_ELECTRONICO", required = true)
        protected String corelectronico;

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
         * Obtiene el valor de la propiedad dirempresa.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDIREMPRESA() {
            return dirempresa;
        }

        /**
         * Define el valor de la propiedad dirempresa.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDIREMPRESA(String value) {
            this.dirempresa = value;
        }

        /**
         * Obtiene el valor de la propiedad replegal.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getREPLEGAL() {
            return replegal;
        }

        /**
         * Define el valor de la propiedad replegal.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setREPLEGAL(String value) {
            this.replegal = value;
        }

        /**
         * Obtiene el valor de la propiedad feciniciomora.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getFECINICIOMORA() {
            return feciniciomora;
        }

        /**
         * Define el valor de la propiedad feciniciomora.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setFECINICIOMORA(String value) {
            this.feciniciomora = value;
        }

        /**
         * Obtiene el valor de la propiedad salmora.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getSALMORA() {
            return salmora;
        }

        /**
         * Define el valor de la propiedad salmora.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSALMORA(String value) {
            this.salmora = value;
        }

        /**
         * Obtiene el valor de la propiedad permora.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getPERMORA() {
            return permora;
        }

        /**
         * Define el valor de la propiedad permora.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPERMORA(String value) {
            this.permora = value;
        }

        /**
         * Obtiene el valor de la propiedad gescobro.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getGESCOBRO() {
            return gescobro;
        }

        /**
         * Define el valor de la propiedad gescobro.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setGESCOBRO(String value) {
            this.gescobro = value;
        }

        /**
         * Obtiene el valor de la propiedad acupago.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getACUPAGO() {
            return acupago;
        }

        /**
         * Define el valor de la propiedad acupago.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setACUPAGO(String value) {
            this.acupago = value;
        }

        /**
         * Obtiene el valor de la propiedad carrecuperada.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getCARRECUPERADA() {
            return carrecuperada;
        }

        /**
         * Define el valor de la propiedad carrecuperada.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCARRECUPERADA(String value) {
            this.carrecuperada = value;
        }

        /**
         * Obtiene el valor de la propiedad corelectronico.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCORELECTRONICO() {
            return corelectronico;
        }

        /**
         * Define el valor de la propiedad corelectronico.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCORELECTRONICO(String value) {
            this.corelectronico = value;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withTIPIDENTIFICACION(String value) {
            setTIPIDENTIFICACION(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withNUMIDENTIFICACIONEMPRESA(String value) {
            setNUMIDENTIFICACIONEMPRESA(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withNOMEMPRESA(String value) {
            setNOMEMPRESA(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withCODMUNICIPIODANE(String value) {
            setCODMUNICIPIODANE(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withDIREMPRESA(String value) {
            setDIREMPRESA(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withREPLEGAL(String value) {
            setREPLEGAL(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withFECINICIOMORA(String value) {
            setFECINICIOMORA(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withSALMORA(String value) {
            setSALMORA(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withPERMORA(String value) {
            setPERMORA(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withGESCOBRO(String value) {
            setGESCOBRO(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withACUPAGO(String value) {
            setACUPAGO(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withCARRECUPERADA(String value) {
            setCARRECUPERADA(value);
            return this;
        }

        public EMPRESASENMORA2017C01 .TEMPRESASENMORA2017C01 withCORELECTRONICO(String value) {
            setCORELECTRONICO(value);
            return this;
        }

    }

}
