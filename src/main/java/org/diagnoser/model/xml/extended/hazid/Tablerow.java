//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.07 at 10:51:34 AM CEST 
//


package org.diagnoser.model.xml.extended.hazid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}cause"/>
 *         &lt;element ref="{}deviation"/>
 *         &lt;element ref="{}implication"/>
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
    "cause",
    "deviation",
    "implication"
})
@XmlRootElement(name = "tablerow")
public class Tablerow {

    @XmlElement(required = true)
    protected Cause cause;
    @XmlElement(required = true)
    protected Deviation deviation;
    @XmlElement(required = true)
    protected Implication implication;

    /**
     * Gets the value of the cause property.
     * 
     * @return
     *     possible object is
     *     {@link Cause }
     *     
     */
    public Cause getCause() {
        return cause;
    }

    /**
     * Sets the value of the cause property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cause }
     *     
     */
    public void setCause(Cause value) {
        this.cause = value;
    }

    /**
     * Gets the value of the deviation property.
     * 
     * @return
     *     possible object is
     *     {@link Deviation }
     *     
     */
    public Deviation getDeviation() {
        return deviation;
    }

    /**
     * Sets the value of the deviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Deviation }
     *     
     */
    public void setDeviation(Deviation value) {
        this.deviation = value;
    }

    /**
     * Gets the value of the implication property.
     * 
     * @return
     *     possible object is
     *     {@link Implication }
     *     
     */
    public Implication getImplication() {
        return implication;
    }

    /**
     * Sets the value of the implication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Implication }
     *     
     */
    public void setImplication(Implication value) {
        this.implication = value;
    }

}