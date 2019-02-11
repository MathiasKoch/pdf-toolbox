
package com.microsoft.schemas.sharepoint.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Alert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Alert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EventType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertForTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertForUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EditAlertUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeliveryChannels" type="{http://schemas.microsoft.com/sharepoint/soap/2002/1/alerts/}ArrayOfDeliveryChannel" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alert", propOrder = {
    "id",
    "title",
    "active",
    "eventType",
    "alertForTitle",
    "alertForUrl",
    "editAlertUrl",
    "deliveryChannels"
})
public class Alert {

    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "Active")
    protected boolean active;
    @XmlElement(name = "EventType")
    protected String eventType;
    @XmlElement(name = "AlertForTitle")
    protected String alertForTitle;
    @XmlElement(name = "AlertForUrl")
    protected String alertForUrl;
    @XmlElement(name = "EditAlertUrl")
    protected String editAlertUrl;
    @XmlElement(name = "DeliveryChannels")
    protected ArrayOfDeliveryChannel deliveryChannels;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the eventType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the value of the eventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventType(String value) {
        this.eventType = value;
    }

    /**
     * Gets the value of the alertForTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertForTitle() {
        return alertForTitle;
    }

    /**
     * Sets the value of the alertForTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertForTitle(String value) {
        this.alertForTitle = value;
    }

    /**
     * Gets the value of the alertForUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertForUrl() {
        return alertForUrl;
    }

    /**
     * Sets the value of the alertForUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertForUrl(String value) {
        this.alertForUrl = value;
    }

    /**
     * Gets the value of the editAlertUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditAlertUrl() {
        return editAlertUrl;
    }

    /**
     * Sets the value of the editAlertUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditAlertUrl(String value) {
        this.editAlertUrl = value;
    }

    /**
     * Gets the value of the deliveryChannels property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDeliveryChannel }
     *     
     */
    public ArrayOfDeliveryChannel getDeliveryChannels() {
        return deliveryChannels;
    }

    /**
     * Sets the value of the deliveryChannels property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDeliveryChannel }
     *     
     */
    public void setDeliveryChannels(ArrayOfDeliveryChannel value) {
        this.deliveryChannels = value;
    }

}
