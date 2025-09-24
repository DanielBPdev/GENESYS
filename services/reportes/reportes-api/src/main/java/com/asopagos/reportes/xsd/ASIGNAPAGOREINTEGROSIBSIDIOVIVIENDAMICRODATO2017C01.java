//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.03.09 a las 04:09:03 PM COT 
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
 *         &lt;element name="T_ASIGNA_PAGO_REINTEGRO_SIBSIDIO_VIVIENDA_MICRODATO_2017C01">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TIP_IDENTIFICACION">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_IDENTIFICA_AFILIADO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="16"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COMPONENTE_HOGAR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TIP_IDENTIFICACION_INTEGRANTE_HOGAR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_IDENTIFICACION_INTEGRANTE_HOGAR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="16"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TITULAR_SUBSIDIO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
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
 *                   &lt;element name="PARENTESCO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="INGRESO_INTEGRANTE_HOGAR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="18"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NIVEL_INGRESO_HOGAR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COMPONENTE">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ANIO_ASIGNACION">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="4"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="EST_SUBSIDIO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="VAL_SUBSIDIO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="18"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COD_TIPO_PLAN_VIVIENDA">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FUE_FINANCIAMIENTO">
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
    "tasignapagoreintegrosibsidioviviendamicrodato2017C01"
})
@XmlRootElement(name = "ASIGNA_PAGO_REINTEGRO_SIBSIDIO_VIVIENDA_MICRODATO_2017C01")
public class ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 {

    @XmlElement(name = "T_ASIGNA_PAGO_REINTEGRO_SIBSIDIO_VIVIENDA_MICRODATO_2017C01")
    protected List<ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 .TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01> tasignapagoreintegrosibsidioviviendamicrodato2017C01;

    /**
     * Gets the value of the tasignapagoreintegrosibsidioviviendamicrodato2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tasignapagoreintegrosibsidioviviendamicrodato2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 .TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 }
     * 
     * 
     */
    public List<ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 .TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01> getTASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01() {
        if (tasignapagoreintegrosibsidioviviendamicrodato2017C01 == null) {
            tasignapagoreintegrosibsidioviviendamicrodato2017C01 = new ArrayList<ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 .TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01>();
        }
        return this.tasignapagoreintegrosibsidioviviendamicrodato2017C01;
    }

    public ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 withTASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01(ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01. TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 ... values) {
        if (values!= null) {
            for (ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01.TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 value: values) {
                getTASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01().add(value);
            }
        }
        return this;
    }

    public ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 withTASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01(Collection<ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 .TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01> values) {
        if (values!= null) {
            getTASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01().addAll(values);
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
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_IDENTIFICA_AFILIADO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="16"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COMPONENTE_HOGAR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TIP_IDENTIFICACION_INTEGRANTE_HOGAR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_IDENTIFICACION_INTEGRANTE_HOGAR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="16"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TITULAR_SUBSIDIO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
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
     *         &lt;element name="PARENTESCO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="INGRESO_INTEGRANTE_HOGAR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="18"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NIVEL_INGRESO_HOGAR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COMPONENTE">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ANIO_ASIGNACION">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="4"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="EST_SUBSIDIO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="VAL_SUBSIDIO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="18"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COD_TIPO_PLAN_VIVIENDA">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FUE_FINANCIAMIENTO">
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
        "tipidentificacion",
        "numidentificaafiliado",
        "componentehogar",
        "tipidentificacionintegrantehogar",
        "numidentificacionintegrantehogar",
        "titularsubsidio",
        "prinombre",
        "segnombre",
        "priapellido",
        "segapellido",
        "parentesco",
        "ingresointegrantehogar",
        "nivelingresohogar",
        "componente",
        "anioasignacion",
        "estsubsidio",
        "valsubsidio",
        "codtipoplanvivienda",
        "fuefinanciamiento"
    })
    public static class TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 {

        @XmlElement(name = "TIP_IDENTIFICACION", required = true)
        protected String tipidentificacion;
        @XmlElement(name = "NUM_IDENTIFICA_AFILIADO", required = true)
        protected String numidentificaafiliado;
        @XmlElement(name = "COMPONENTE_HOGAR", required = true)
        protected String componentehogar;
        @XmlElement(name = "TIP_IDENTIFICACION_INTEGRANTE_HOGAR", required = true)
        protected String tipidentificacionintegrantehogar;
        @XmlElement(name = "NUM_IDENTIFICACION_INTEGRANTE_HOGAR", required = true)
        protected String numidentificacionintegrantehogar;
        @XmlElement(name = "TITULAR_SUBSIDIO", required = true)
        protected String titularsubsidio;
        @XmlElement(name = "PRI_NOMBRE", required = true)
        protected String prinombre;
        @XmlElement(name = "SEG_NOMBRE", required = true)
        protected String segnombre;
        @XmlElement(name = "PRI_APELLIDO", required = true)
        protected String priapellido;
        @XmlElement(name = "SEG_APELLIDO", required = true)
        protected String segapellido;
        @XmlElement(name = "PARENTESCO", required = true)
        protected String parentesco;
        @XmlElement(name = "INGRESO_INTEGRANTE_HOGAR", required = true)
        protected String ingresointegrantehogar;
        @XmlElement(name = "NIVEL_INGRESO_HOGAR", required = true)
        protected String nivelingresohogar;
        @XmlElement(name = "COMPONENTE", required = true)
        protected String componente;
        @XmlElement(name = "ANIO_ASIGNACION", required = true)
        protected String anioasignacion;
        @XmlElement(name = "EST_SUBSIDIO", required = true)
        protected String estsubsidio;
        @XmlElement(name = "VAL_SUBSIDIO", required = true)
        protected String valsubsidio;
        @XmlElement(name = "COD_TIPO_PLAN_VIVIENDA", required = true)
        protected String codtipoplanvivienda;
        @XmlElement(name = "FUE_FINANCIAMIENTO", required = true)
        protected String fuefinanciamiento;

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
         * Obtiene el valor de la propiedad numidentificaafiliado.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMIDENTIFICAAFILIADO() {
            return numidentificaafiliado;
        }

        /**
         * Define el valor de la propiedad numidentificaafiliado.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMIDENTIFICAAFILIADO(String value) {
            this.numidentificaafiliado = value;
        }

        /**
         * Obtiene el valor de la propiedad componentehogar.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCOMPONENTEHOGAR() {
            return componentehogar;
        }

        /**
         * Define el valor de la propiedad componentehogar.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCOMPONENTEHOGAR(String value) {
            this.componentehogar = value;
        }

        /**
         * Obtiene el valor de la propiedad tipidentificacionintegrantehogar.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTIPIDENTIFICACIONINTEGRANTEHOGAR() {
            return tipidentificacionintegrantehogar;
        }

        /**
         * Define el valor de la propiedad tipidentificacionintegrantehogar.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTIPIDENTIFICACIONINTEGRANTEHOGAR(String value) {
            this.tipidentificacionintegrantehogar = value;
        }

        /**
         * Obtiene el valor de la propiedad numidentificacionintegrantehogar.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMIDENTIFICACIONINTEGRANTEHOGAR() {
            return numidentificacionintegrantehogar;
        }

        /**
         * Define el valor de la propiedad numidentificacionintegrantehogar.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMIDENTIFICACIONINTEGRANTEHOGAR(String value) {
            this.numidentificacionintegrantehogar = value;
        }

        /**
         * Obtiene el valor de la propiedad titularsubsidio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTITULARSUBSIDIO() {
            return titularsubsidio;
        }

        /**
         * Define el valor de la propiedad titularsubsidio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTITULARSUBSIDIO(String value) {
            this.titularsubsidio = value;
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
         * Obtiene el valor de la propiedad parentesco.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPARENTESCO() {
            return parentesco;
        }

        /**
         * Define el valor de la propiedad parentesco.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPARENTESCO(String value) {
            this.parentesco = value;
        }

        /**
         * Obtiene el valor de la propiedad ingresointegrantehogar.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getINGRESOINTEGRANTEHOGAR() {
            return ingresointegrantehogar;
        }

        /**
         * Define el valor de la propiedad ingresointegrantehogar.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setINGRESOINTEGRANTEHOGAR(String value) {
            this.ingresointegrantehogar = value;
        }

        /**
         * Obtiene el valor de la propiedad nivelingresohogar.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNIVELINGRESOHOGAR() {
            return nivelingresohogar;
        }

        /**
         * Define el valor de la propiedad nivelingresohogar.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNIVELINGRESOHOGAR(String value) {
            this.nivelingresohogar = value;
        }

        /**
         * Obtiene el valor de la propiedad componente.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCOMPONENTE() {
            return componente;
        }

        /**
         * Define el valor de la propiedad componente.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCOMPONENTE(String value) {
            this.componente = value;
        }

        /**
         * Obtiene el valor de la propiedad anioasignacion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getANIOASIGNACION() {
            return anioasignacion;
        }

        /**
         * Define el valor de la propiedad anioasignacion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setANIOASIGNACION(String value) {
            this.anioasignacion = value;
        }

        /**
         * Obtiene el valor de la propiedad estsubsidio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getESTSUBSIDIO() {
            return estsubsidio;
        }

        /**
         * Define el valor de la propiedad estsubsidio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setESTSUBSIDIO(String value) {
            this.estsubsidio = value;
        }

        /**
         * Obtiene el valor de la propiedad valsubsidio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVALSUBSIDIO() {
            return valsubsidio;
        }

        /**
         * Define el valor de la propiedad valsubsidio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVALSUBSIDIO(String value) {
            this.valsubsidio = value;
        }

        /**
         * Obtiene el valor de la propiedad codtipoplanvivienda.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODTIPOPLANVIVIENDA() {
            return codtipoplanvivienda;
        }

        /**
         * Define el valor de la propiedad codtipoplanvivienda.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODTIPOPLANVIVIENDA(String value) {
            this.codtipoplanvivienda = value;
        }

        /**
         * Obtiene el valor de la propiedad fuefinanciamiento.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFUEFINANCIAMIENTO() {
            return fuefinanciamiento;
        }

        /**
         * Define el valor de la propiedad fuefinanciamiento.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFUEFINANCIAMIENTO(String value) {
            this.fuefinanciamiento = value;
        }

    }

}
