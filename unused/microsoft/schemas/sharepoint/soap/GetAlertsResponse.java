
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
 *         &lt;element name="GetAlertsResult" type="{http://schemas.microsoft.com/sharepoint/soap/2002/1/alerts/}AlertInfo"/>
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
    "getAlertsResult"
})
@XmlRootElement(name = "GetAlertsResponse")
public class GetAlertsResponse {

    @XmlElement(name = "GetAlertsResult", required = true)
    protected AlertInfo getAlertsResult;

    /**
     * Gets the value of the getAlertsResult property.
     * 
     * @return
     *     possible object is
     *     {@link AlertInfo }
     *     
     */
    public AlertInfo getGetAlertsResult() {
        return getAlertsResult;
    }

    /**
     * Sets the value of the getAlertsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlertInfo }
     *     
     */
    public void setGetAlertsResult(AlertInfo value) {
        this.getAlertsResult = value;
    }

}
