//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.31 at 04:18:36 PM CET 
//


package org.diagnoser.model.xml.compact.hazid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{}rootcause"/>
 *         &lt;element ref="{}ref"/>
 *         &lt;element ref="{}dev"/>
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
    "rootcause",
    "ref",
    "dev"
})
@XmlRootElement(name = "cause")
public class Cause {

    protected Rootcause rootcause;
    protected Ref ref;
    protected Dev dev;

    /**
     * Gets the value of the rootcause property.
     * 
     * @return
     *     possible object is
     *     {@link Rootcause }
     *     
     */
    public Rootcause getRootcause() {
        return rootcause;
    }

    /**
     * Sets the value of the rootcause property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rootcause }
     *     
     */
    public void setRootcause(Rootcause value) {
        this.rootcause = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link Ref }
     *     
     */
    public Ref getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ref }
     *     
     */
    public void setRef(Ref value) {
        this.ref = value;
    }

    /**
     * Gets the value of the dev property.
     * 
     * @return
     *     possible object is
     *     {@link Dev }
     *     
     */
    public Dev getDev() {
        return dev;
    }

    /**
     * Sets the value of the dev property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dev }
     *     
     */
    public void setDev(Dev value) {
        this.dev = value;
    }

}
