
package com.microsoft.schemas.sharepoint.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="GetSiteResult" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="sSiteMetadata" type="{http://schemas.microsoft.com/sharepoint/soap/}_sSiteMetadata"/>
 *         &lt;element name="vWebs" type="{http://schemas.microsoft.com/sharepoint/soap/}ArrayOf_sWebWithTime" minOccurs="0"/>
 *         &lt;element name="strUsers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strGroups" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vGroups" type="{http://schemas.microsoft.com/sharepoint/soap/}ArrayOfString" minOccurs="0"/>
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
    "getSiteResult",
    "sSiteMetadata",
    "vWebs",
    "strUsers",
    "strGroups",
    "vGroups"
})
@XmlRootElement(name = "GetSiteResponse")
public class GetSiteResponse {

    @XmlElement(name = "GetSiteResult")
    @XmlSchemaType(name = "unsignedInt")
    protected long getSiteResult;
    @XmlElement(required = true)
    protected SSiteMetadata sSiteMetadata;
    protected ArrayOfSWebWithTime vWebs;
    protected String strUsers;
    protected String strGroups;
    protected ArrayOfString vGroups;

    /**
     * Gets the value of the getSiteResult property.
     * 
     */
    public long getGetSiteResult() {
        return getSiteResult;
    }

    /**
     * Sets the value of the getSiteResult property.
     * 
     */
    public void setGetSiteResult(long value) {
        this.getSiteResult = value;
    }

    /**
     * Gets the value of the sSiteMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link SSiteMetadata }
     *     
     */
    public SSiteMetadata getSSiteMetadata() {
        return sSiteMetadata;
    }

    /**
     * Sets the value of the sSiteMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link SSiteMetadata }
     *     
     */
    public void setSSiteMetadata(SSiteMetadata value) {
        this.sSiteMetadata = value;
    }

    /**
     * Gets the value of the vWebs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSWebWithTime }
     *     
     */
    public ArrayOfSWebWithTime getVWebs() {
        return vWebs;
    }

    /**
     * Sets the value of the vWebs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSWebWithTime }
     *     
     */
    public void setVWebs(ArrayOfSWebWithTime value) {
        this.vWebs = value;
    }

    /**
     * Gets the value of the strUsers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrUsers() {
        return strUsers;
    }

    /**
     * Sets the value of the strUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrUsers(String value) {
        this.strUsers = value;
    }

    /**
     * Gets the value of the strGroups property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrGroups() {
        return strGroups;
    }

    /**
     * Sets the value of the strGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrGroups(String value) {
        this.strGroups = value;
    }

    /**
     * Gets the value of the vGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getVGroups() {
        return vGroups;
    }

    /**
     * Sets the value of the vGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setVGroups(ArrayOfString value) {
        this.vGroups = value;
    }

}
