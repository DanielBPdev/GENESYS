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
    "tnumerocuotas2017C01"
})
@XmlRootElement(name = "NUMERO_CUOTAS_2017C1")
public class NUMEROCUOTAS2017C1 {

    @XmlElement(name = "T_NUMERO_CUOTAS_2017C1", required = true)
    protected List<NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1> tnumerocuotas2017C01;

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
     * {@link NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1 }
     * 
     * 
     */
    public List<NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1> getTNUMEROCUOTAS2017C01() {
        if (tnumerocuotas2017C01 == null) {
            tnumerocuotas2017C01 = new ArrayList<NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1>();
        }
        return this.tnumerocuotas2017C01;
    }

    public NUMEROCUOTAS2017C1 withTNUMEROCUOTAS2017C01(NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1 ... values) {
        if (values!= null) {
            for (NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1 value: values) {
                getTNUMEROCUOTAS2017C01().add(value);
            }
        }
        return this;
    }

    public NUMEROCUOTAS2017C1 withTUNMEROCUOTAS2017C01(Collection<NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1> values) {
        if (values!= null) {
            getTNUMEROCUOTAS2017C01().addAll(values);
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
        "codigoDepartamento",
        "anio",
        "valor"       
    })
    public static class TNUMEROCUOTAS2017C1 {

    	@XmlElement(name = "CODIGO_DEPARTAMENTO", required = true)
         protected String codigoDepartamento = new String();
        @XmlElement(name = "ANIO", required = true)
        protected BigInteger anio;
        @XmlElement(name = "VALOR", required = true)
        protected BigInteger valor;

        /**
         * Obtiene el valor de la propiedad aniovigencia.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getANIO() {
            return anio;
        }

        /**
         * Define el valor de la propiedad aniovigencia.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setANIO(BigInteger value) {
            this.anio = value;
        }

        /**
         * Obtiene el valor de la propiedad fecaperturafovis.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getCODIGODEPARTAMENTO() {
            return codigoDepartamento;
        }

        /**
         * Define el valor de la propiedad fecaperturafovis.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setCODIGODEPARTAMENTO(String value) {
            this.codigoDepartamento = value;
        }
        
        /**
         * Obtiene el valor de la propiedad fecaperturafovis.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getVALOR() {
            return valor;
        }

        /**
         * Define el valor de la propiedad fecaperturafovis.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setVALOR(BigInteger value) {
            this.valor = value;
        }

    
        public NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1 withANIO(BigInteger value) {
            setANIO(value);
            return this;
        }

        public NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1 withCODIGODEPARTAMENTO(String value) {
            setCODIGODEPARTAMENTO(value);
            return this;
        }
     
        public NUMEROCUOTAS2017C1 .TNUMEROCUOTAS2017C1 withVALOR(BigInteger value) {
            setVALOR(value);
            return this;
        }
    }

}
