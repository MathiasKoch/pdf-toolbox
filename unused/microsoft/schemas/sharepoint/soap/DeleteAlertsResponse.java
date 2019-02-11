
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
 *         &lt;element name="DeleteAlertsResult" type="{http://schemas.microsoft.com/sharepoint/soap/2002/1/alerts/}ArrayOfDeleteFailure" minOccurs="0"/>
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
    "deleteAlertsResult"
})
@XmlRootElement(name = "DeleteAlertsResponse")
public class DeleteAlertsResponse {

    @XmlElement(name = "DeleteAlertsResult")
    protected ArrayOfDeleteFailure deleteAlertsResult;

    /**
     * Gets the value of the deleteAlertsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDeleteFailure }
     *     
     */
    public ArrayOfDeleteFailure getDeleteAlertsResult() {
        return deleteAlertsResult;
    }

    /**
     * Sets the value of the deleteAlertsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDeleteFailure }
     *     
     */
    public void setDeleteAlertsResult(ArrayOfDeleteFailure value) {
        this.deleteAlertsResult = value;
    }

}
