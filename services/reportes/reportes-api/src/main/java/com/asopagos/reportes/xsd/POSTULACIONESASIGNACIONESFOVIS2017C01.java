//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.08.15 a las 09:45:24 AM COT 
//


package com.asopagos.reportes.xsd;

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
 *         &lt;element name="T_POSTULACIONES_ASIGNACIONES_FOVIS_2017C01" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ANIO_VIGENCIA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="4"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="FEC_APERTURA_FOVIS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="FEC_CIERRE_FOVIS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="FEC_ASIGNACION_FOVIS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
    "tpostulacionesasignacionesfovis2017C01"
})
@XmlRootElement(name = "POSTULACIONES_ASIGNACIONES_FOVIS_2017C01")
public class POSTULACIONESASIGNACIONESFOVIS2017C01 {

    @XmlElement(name = "T_POSTULACIONES_ASIGNACIONES_FOVIS_2017C01", required = true)
    protected List<POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01> tpostulacionesasignacionesfovis2017C01;

    /**
     * Gets the value of the tpostulacionesasignacionesfovis2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tpostulacionesasignacionesfovis2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTPOSTULACIONESASIGNACIONESFOVIS2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01 }
     * 
     * 
     */
    public List<POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01> getTPOSTULACIONESASIGNACIONESFOVIS2017C01() {
        if (tpostulacionesasignacionesfovis2017C01 == null) {
            tpostulacionesasignacionesfovis2017C01 = new ArrayList<POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01>();
        }
        return this.tpostulacionesasignacionesfovis2017C01;
    }

    public POSTULACIONESASIGNACIONESFOVIS2017C01 withTPOSTULACIONESASIGNACIONESFOVIS2017C01(POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01 ... values) {
        if (values!= null) {
            for (POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01 value: values) {
                getTPOSTULACIONESASIGNACIONESFOVIS2017C01().add(value);
            }
        }
        return this;
    }

    public POSTULACIONESASIGNACIONESFOVIS2017C01 withTPOSTULACIONESASIGNACIONESFOVIS2017C01(Collection<POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01> values) {
        if (values!= null) {
            getTPOSTULACIONESASIGNACIONESFOVIS2017C01().addAll(values);
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
     *         &lt;element name="ANIO_VIGENCIA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="4"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="FEC_APERTURA_FOVIS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="FEC_CIERRE_FOVIS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="FEC_ASIGNACION_FOVIS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
        "aniovigencia",
        "fecaperturafovis",
        "feccierrefovis",
        "fecasignacionfovis"
    })
    public static class TPOSTULACIONESASIGNACIONESFOVIS2017C01 {

        @XmlElement(name = "ANIO_VIGENCIA", required = true)
        protected BigInteger aniovigencia;
        @XmlElement(name = "FEC_APERTURA_FOVIS", required = true)
        protected String fecaperturafovis = new String();
        @XmlElement(name = "FEC_CIERRE_FOVIS", required = true)
        protected String feccierrefovis = new String();
        @XmlElement(name = "FEC_ASIGNACION_FOVIS", required = true)
        protected String fecasignacionfovis = new String();

        /**
         * Obtiene el valor de la propiedad aniovigencia.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getANIOVIGENCIA() {
            return aniovigencia;
        }

        /**
         * Define el valor de la propiedad aniovigencia.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setANIOVIGENCIA(BigInteger value) {
            this.aniovigencia = value;
        }

        /**
         * Obtiene el valor de la propiedad fecaperturafovis.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getFECAPERTURAFOVIS() {
            return fecaperturafovis;
        }

        /**
         * Define el valor de la propiedad fecaperturafovis.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setFECAPERTURAFOVIS(String value) {
            this.fecaperturafovis = value;
        }

        /**
         * Obtiene el valor de la propiedad feccierrefovis.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public String getFECCIERREFOVIS() {
            return feccierrefovis;
        }

        /**
         * Define el valor de la propiedad feccierrefovis.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setFECCIERREFOVIS(String value) {
            this.feccierrefovis = value;
        }

        /**
         * Obtiene el valor de la propiedad fecasignacionfovis.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public String getFECASIGNACIONFOVIS() {
            return fecasignacionfovis;
        }

        /**
         * Define el valor de la propiedad fecasignacionfovis.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setFECASIGNACIONFOVIS(String value) {
            this.fecasignacionfovis = value;
        }

        public POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01 withANIOVIGENCIA(BigInteger value) {
            setANIOVIGENCIA(value);
            return this;
        }

        public POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01 withFECAPERTURAFOVIS(String value) {
            setFECAPERTURAFOVIS(value);
            return this;
        }

        public POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01 withFECCIERREFOVIS(String value) {
            setFECCIERREFOVIS(value);
            return this;
        }

        public POSTULACIONESASIGNACIONESFOVIS2017C01 .TPOSTULACIONESASIGNACIONESFOVIS2017C01 withFECASIGNACIONFOVIS(String value) {
            setFECASIGNACIONFOVIS(value);
            return this;
        }

    }

}
