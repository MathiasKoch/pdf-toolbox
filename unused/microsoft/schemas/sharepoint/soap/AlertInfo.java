
package com.microsoft.schemas.sharepoint.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AlertInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlertInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CurrentUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertServerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertServerUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertServerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertsManagementUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertWebTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewAlertUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertWebId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Alerts" type="{http://schemas.microsoft.com/sharepoint/soap/2002/1/alerts/}ArrayOfAlert" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlertInfo", propOrder = {
    "currentUser",
    "alertServerName",
    "alertServerUrl",
    "alertServerType",
    "alertsManagementUrl",
    "alertWebTitle",
    "newAlertUrl",
    "alertWebId",
    "alerts"
})
public class AlertInfo {

    @XmlElement(name = "CurrentUser")
    protected String currentUser;
    @XmlElement(name = "AlertServerName")
    protected String alertServerName;
    @XmlElement(name = "AlertServerUrl")
    protected String alertServerUrl;
    @XmlElement(name = "AlertServerType")
    protected String alertServerType;
    @XmlElement(name = "AlertsManagementUrl")
    protected String alertsManagementUrl;
    @XmlElement(name = "AlertWebTitle")
    protected String alertWebTitle;
    @XmlElement(name = "NewAlertUrl")
    protected String newAlertUrl;
    @XmlElement(name = "AlertWebId")
    protected String alertWebId;
    @XmlElement(name = "Alerts")
    protected ArrayOfAlert alerts;

    /**
     * Gets the value of the currentUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the value of the currentUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentUser(String value) {
        this.currentUser = value;
    }

    /**
     * Gets the value of the alertServerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertServerName() {
        return alertServerName;
    }

    /**
     * Sets the value of the alertServerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertServerName(String value) {
        this.alertServerName = value;
    }

    /**
     * Gets the value of the alertServerUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertServerUrl() {
        return alertServerUrl;
    }

    /**
     * Sets the value of the alertServerUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertServerUrl(String value) {
        this.alertServerUrl = value;
    }

    /**
     * Gets the value of the alertServerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertServerType() {
        return alertServerType;
    }

    /**
     * Sets the value of the alertServerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertServerType(String value) {
        this.alertServerType = value;
    }

    /**
     * Gets the value of the alertsManagementUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertsManagementUrl() {
        return alertsManagementUrl;
    }

    /**
     * Sets the value of the alertsManagementUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertsManagementUrl(String value) {
        this.alertsManagementUrl = value;
    }

    /**
     * Gets the value of the alertWebTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertWebTitle() {
        return alertWebTitle;
    }

    /**
     * Sets the value of the alertWebTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertWebTitle(String value) {
        this.alertWebTitle = value;
    }

    /**
     * Gets the value of the newAlertUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewAlertUrl() {
        return newAlertUrl;
    }

    /**
     * Sets the value of the newAlertUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewAlertUrl(String value) {
        this.newAlertUrl = value;
    }

    /**
     * Gets the value of the alertWebId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertWebId() {
        return alertWebId;
    }

    /**
     * Sets the value of the alertWebId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertWebId(String value) {
        this.alertWebId = value;
    }

    /**
     * Gets the value of the alerts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlert }
     *     
     */
    public ArrayOfAlert getAlerts() {
        return alerts;
    }

    /**
     * Sets the value of the alerts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlert }
     *     
     */
    public void setAlerts(ArrayOfAlert value) {
        this.alerts = value;
    }

}
