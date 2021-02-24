package net.sf.minuteproject.adf.query;

import java.util.ArrayList;
import java.util.List;


public class ValueExpression {
    protected List<String> values;
    protected String criteriaAttribute;
    protected UnaryOperationCode operationCode;

    /**
     * Gets the value of the values property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the values property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getValues() {
        if (values == null) {
            values = new ArrayList<String>();
        }
        return this.values;
    }

    /**
     * Gets the value of the criteriaAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCriteriaAttribute() {
        return criteriaAttribute;
    }

    /**
     * Sets the value of the criteriaAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCriteriaAttribute(String value) {
        this.criteriaAttribute = value;
    }

    /**
     * Gets the value of the operationCode property.
     * 
     * @return
     *     possible object is
     *     {@link UnaryOperationCode }
     *     
     */
    public UnaryOperationCode getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnaryOperationCode }
     *     
     */
    public void setOperationCode(UnaryOperationCode value) {
        this.operationCode = value;
    }

}
