//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.07 at 10:51:31 AM CEST 
//


package org.diagnoser.model.xml.extended.trace;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.diagnoser.model.xml.extended.trace package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Input_QNAME = new QName("", "input");
    private final static QName _Name_QNAME = new QName("", "name");
    private final static QName _Output_QNAME = new QName("", "output");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.diagnoser.model.xml.extended.trace
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Inputs }
     * 
     */
    public Inputs createInputs() {
        return new Inputs();
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link Outputs }
     * 
     */
    public Outputs createOutputs() {
        return new Outputs();
    }

    /**
     * Create an instance of {@link Trace }
     * 
     */
    public Trace createTrace() {
        return new Trace();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "input")
    public JAXBElement<String> createInput(String value) {
        return new JAXBElement<String>(_Input_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "output")
    public JAXBElement<String> createOutput(String value) {
        return new JAXBElement<String>(_Output_QNAME, String.class, null, value);
    }

}
