//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.03.12 a las 02:44:20 PM COT 
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
 *         &lt;element name="T_AFILIADOS_A_CARGO_2017C01">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TIP_IDENTIFICACION">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
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
 *                         &lt;totalDigits value="1"/>
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
 *                   &lt;element name="TIP_IDENTIFICACION_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_IDENTIFICACION_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="15"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PRI_NOMBRE_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SEG_NOMBRE_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PRI_APELLIDO_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SEG_APELLIDO_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FEC_NACIMIENTO_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="8"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="GEN_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PAR_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COD_MUNICIPIO_RESIDENCIA_DANE">
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
 *                   &lt;element name="DISC_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TIP_CUOTA_MONETARIA_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="VAL_CUOTA_MONETARIA_PERSONA_A_CARGO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="18"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_CUOTAS_PAGADAS">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="5"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_PERIODOS_PAGADAS">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="5"/>
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
    "tafiliadosacargo2017C01"
})
@XmlRootElement(name = "AFILIADOS_A_CARGO_2017C01")
public class AFILIADOSACARGO2017C01 {

    @XmlElement(name = "T_AFILIADOS_A_CARGO_2017C01")
    protected List<AFILIADOSACARGO2017C01 .TAFILIADOSACARGO2017C01> tafiliadosacargo2017C01;

    /**
     * Gets the value of the tafiliadosacargo2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tafiliadosacargo2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTAFILIADOSACARGO2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AFILIADOSACARGO2017C01 .TAFILIADOSACARGO2017C01 }
     * 
     * 
     */
    public List<AFILIADOSACARGO2017C01 .TAFILIADOSACARGO2017C01> getTAFILIADOSACARGO2017C01() {
        if (tafiliadosacargo2017C01 == null) {
            tafiliadosacargo2017C01 = new ArrayList<AFILIADOSACARGO2017C01 .TAFILIADOSACARGO2017C01>();
        }
        return this.tafiliadosacargo2017C01;
    }

    public AFILIADOSACARGO2017C01 withTAFILIADOSACARGO2017C01(AFILIADOSACARGO2017C01 .TAFILIADOSACARGO2017C01 ... values) {
        if (values!= null) {
            for (AFILIADOSACARGO2017C01 .TAFILIADOSACARGO2017C01 value: values) {
                getTAFILIADOSACARGO2017C01().add(value);
            }
        }
        return this;
    }

    public AFILIADOSACARGO2017C01 withTAFILIADOSACARGO2017C01(Collection<AFILIADOSACARGO2017C01 .TAFILIADOSACARGO2017C01> values) {
        if (values!= null) {
            getTAFILIADOSACARGO2017C01().addAll(values);
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
     *         &lt;element name="TIP_IDENTIFICACION">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
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
     *               &lt;totalDigits value="1"/>
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
     *         &lt;element name="TIP_IDENTIFICACION_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_IDENTIFICACION_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="15"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PRI_NOMBRE_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SEG_NOMBRE_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PRI_APELLIDO_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SEG_APELLIDO_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FEC_NACIMIENTO_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="8"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="GEN_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PAR_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COD_MUNICIPIO_RESIDENCIA_DANE">
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
     *         &lt;element name="DISC_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TIP_CUOTA_MONETARIA_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="VAL_CUOTA_MONETARIA_PERSONA_A_CARGO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="18"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_CUOTAS_PAGADAS">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="5"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_PERIODOS_PAGADAS">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="5"/>
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
        "tipidentificacion",
        "numidentificacionempresa",
        "tipidentificacionafiliado",
        "numidentificacionafiliado",
        "tipidentificacionpersonaacargo",
        "numidentificacionpersonaacargo",
        "prinombrepersonaacargo",
        "segnombrepersonaacargo",
        "priapellidopersonaacargo",
        "segapellidopersonaacargo",
        "fecnacimientopersonaacargo",
        "genpersonaacargo",
        "parpersonaacargo",
        "codmunicipioresidenciadane",
        "aregeograficaresidencia",
        "discpersonaacargo",
        "tipcuotamonetariapersonaacargo",
        "valcuotamonetariapersonaacargo",
        "numcuotaspagadas",
        "numperiodospagadas"
    })
    public static class TAFILIADOSACARGO2017C01 {

        @XmlElement(name = "TIP_IDENTIFICACION", required = true)
        protected String tipidentificacion;
        @XmlElement(name = "NUM_IDENTIFICACION_EMPRESA", required = true)
        protected String numidentificacionempresa;
        @XmlElement(name = "TIP_IDENTIFICACION_AFILIADO", required = true)
        protected String tipidentificacionafiliado;
        @XmlElement(name = "NUM_IDENTIFICACION_AFILIADO", required = true)
        protected String numidentificacionafiliado;
        @XmlElement(name = "TIP_IDENTIFICACION_PERSONA_A_CARGO", required = true)
        protected String tipidentificacionpersonaacargo;
        @XmlElement(name = "NUM_IDENTIFICACION_PERSONA_A_CARGO", required = true)
        protected String numidentificacionpersonaacargo;
        @XmlElement(name = "PRI_NOMBRE_PERSONA_A_CARGO", required = true)
        protected String prinombrepersonaacargo;
        @XmlElement(name = "SEG_NOMBRE_PERSONA_A_CARGO", required = true)
        protected String segnombrepersonaacargo;
        @XmlElement(name = "PRI_APELLIDO_PERSONA_A_CARGO", required = true)
        protected String priapellidopersonaacargo;
        @XmlElement(name = "SEG_APELLIDO_PERSONA_A_CARGO", required = true)
        protected String segapellidopersonaacargo;
        @XmlElement(name = "FEC_NACIMIENTO_PERSONA_A_CARGO", required = true)
        protected String fecnacimientopersonaacargo;
        @XmlElement(name = "GEN_PERSONA_A_CARGO", required = true)
        protected String genpersonaacargo;
        @XmlElement(name = "PAR_PERSONA_A_CARGO", required = true)
        protected String parpersonaacargo;
        @XmlElement(name = "COD_MUNICIPIO_RESIDENCIA_DANE", required = true)
        protected String codmunicipioresidenciadane;
        @XmlElement(name = "ARE_GEOGRAFICA_RESIDENCIA", required = true)
        protected String aregeograficaresidencia;
        @XmlElement(name = "DISC_PERSONA_A_CARGO", required = true)
        protected String discpersonaacargo;
        @XmlElement(name = "TIP_CUOTA_MONETARIA_PERSONA_A_CARGO", required = true)
        protected String tipcuotamonetariapersonaacargo;
        @XmlElement(name = "VAL_CUOTA_MONETARIA_PERSONA_A_CARGO", required = true)
        protected String valcuotamonetariapersonaacargo;
        @XmlElement(name = "NUM_CUOTAS_PAGADAS", required = true)
        protected String numcuotaspagadas;
        @XmlElement(name = "NUM_PERIODOS_PAGADAS", required = true)
        protected String numperiodospagadas;

        /**
         * Obtiene el valor de la propiedad tipidentificacion.
         * 
         * @return
         *     possible object is
         *     {@link String }
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
         *     {@link String }
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
         * Obtiene el valor de la propiedad tipidentificacionpersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTIPIDENTIFICACIONPERSONAACARGO() {
            return tipidentificacionpersonaacargo;
        }

        /**
         * Define el valor de la propiedad tipidentificacionpersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTIPIDENTIFICACIONPERSONAACARGO(String value) {
            this.tipidentificacionpersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad numidentificacionpersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMIDENTIFICACIONPERSONAACARGO() {
            return numidentificacionpersonaacargo;
        }

        /**
         * Define el valor de la propiedad numidentificacionpersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMIDENTIFICACIONPERSONAACARGO(String value) {
            this.numidentificacionpersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad prinombrepersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPRINOMBREPERSONAACARGO() {
            return prinombrepersonaacargo;
        }

        /**
         * Define el valor de la propiedad prinombrepersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPRINOMBREPERSONAACARGO(String value) {
            this.prinombrepersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad segnombrepersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSEGNOMBREPERSONAACARGO() {
            return segnombrepersonaacargo;
        }

        /**
         * Define el valor de la propiedad segnombrepersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSEGNOMBREPERSONAACARGO(String value) {
            this.segnombrepersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad priapellidopersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPRIAPELLIDOPERSONAACARGO() {
            return priapellidopersonaacargo;
        }

        /**
         * Define el valor de la propiedad priapellidopersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPRIAPELLIDOPERSONAACARGO(String value) {
            this.priapellidopersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad segapellidopersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSEGAPELLIDOPERSONAACARGO() {
            return segapellidopersonaacargo;
        }

        /**
         * Define el valor de la propiedad segapellidopersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSEGAPELLIDOPERSONAACARGO(String value) {
            this.segapellidopersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad fecnacimientopersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFECNACIMIENTOPERSONAACARGO() {
            return fecnacimientopersonaacargo;
        }

        /**
         * Define el valor de la propiedad fecnacimientopersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFECNACIMIENTOPERSONAACARGO(String value) {
            this.fecnacimientopersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad genpersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGENPERSONAACARGO() {
            return genpersonaacargo;
        }

        /**
         * Define el valor de la propiedad genpersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGENPERSONAACARGO(String value) {
            this.genpersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad parpersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPARPERSONAACARGO() {
            return parpersonaacargo;
        }

        /**
         * Define el valor de la propiedad parpersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPARPERSONAACARGO(String value) {
            this.parpersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad codmunicipioresidenciadane.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODMUNICIPIORESIDENCIADANE() {
            return codmunicipioresidenciadane;
        }

        /**
         * Define el valor de la propiedad codmunicipioresidenciadane.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODMUNICIPIORESIDENCIADANE(String value) {
            this.codmunicipioresidenciadane = value;
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
         * Obtiene el valor de la propiedad discpersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDISCPERSONAACARGO() {
            return discpersonaacargo;
        }

        /**
         * Define el valor de la propiedad discpersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDISCPERSONAACARGO(String value) {
            this.discpersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad tipcuotamonetariapersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTIPCUOTAMONETARIAPERSONAACARGO() {
            return tipcuotamonetariapersonaacargo;
        }

        /**
         * Define el valor de la propiedad tipcuotamonetariapersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTIPCUOTAMONETARIAPERSONAACARGO(String value) {
            this.tipcuotamonetariapersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad valcuotamonetariapersonaacargo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVALCUOTAMONETARIAPERSONAACARGO() {
            return valcuotamonetariapersonaacargo;
        }

        /**
         * Define el valor de la propiedad valcuotamonetariapersonaacargo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVALCUOTAMONETARIAPERSONAACARGO(String value) {
            this.valcuotamonetariapersonaacargo = value;
        }

        /**
         * Obtiene el valor de la propiedad numcuotaspagadas.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMCUOTASPAGADAS() {
            return numcuotaspagadas;
        }

        /**
         * Define el valor de la propiedad numcuotaspagadas.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMCUOTASPAGADAS(String value) {
            this.numcuotaspagadas = value;
        }

        /**
         * Obtiene el valor de la propiedad numperiodospagadas.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMPERIODOSPAGADAS() {
            return numperiodospagadas;
        }

        /**
         * Define el valor de la propiedad numperiodospagadas.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMPERIODOSPAGADAS(String value) {
            this.numperiodospagadas = value;
        }

    }

}
