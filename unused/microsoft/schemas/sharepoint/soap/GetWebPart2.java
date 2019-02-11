
package com.microsoft.schemas.sharepoint.soap;

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
 *         &lt;element name="pageurl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storageKey" type="{http://microsoft.com/wsdl/types/}guid"/>
 *         &lt;element name="storage" type="{http://microsoft.com/sharepoint/webpartpages}Storage"/>
 *         &lt;element name="behavior" type="{http://microsoft.com/sharepoint/webpartpages}SPWebServiceBehavior"/>
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
    "pageurl",
    "storageKey",
    "storage",
    "behavior"
})
@XmlRootElement(name = "GetWebPart2")
public class GetWebPart2 {

    protected String pageurl;
    @XmlElement(required = true)
    protected String storageKey;
    @XmlElement(required = true)
    protected Storage storage;
    @XmlElement(required = true)
    protected SPWebServiceBehavior behavior;

    /**
     * Gets the value of the pageurl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageurl() {
        return pageurl;
    }

    /**
     * Sets the value of the pageurl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageurl(String value) {
        this.pageurl = value;
    }

    /**
     * Gets the value of the storageKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageKey() {
        return storageKey;
    }

    /**
     * Sets the value of the storageKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageKey(String value) {
        this.storageKey = value;
    }

    /**
     * Gets the value of the storage property.
     * 
     * @return
     *     possible object is
     *     {@link Storage }
     *     
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Sets the value of the storage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Storage }
     *     
     */
    public void setStorage(Storage value) {
        this.storage = value;
    }

    /**
     * Gets the value of the behavior property.
     * 
     * @return
     *     possible object is
     *     {@link SPWebServiceBehavior }
     *     
     */
    public SPWebServiceBehavior getBehavior() {
        return behavior;
    }

    /**
     * Sets the value of the behavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPWebServiceBehavior }
     *     
     */
    public void setBehavior(SPWebServiceBehavior value) {
        this.behavior = value;
    }

}
