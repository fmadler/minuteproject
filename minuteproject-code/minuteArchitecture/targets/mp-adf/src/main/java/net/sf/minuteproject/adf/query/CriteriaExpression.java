package net.sf.minuteproject.adf.query;

import java.util.ArrayList;
import java.util.List;


public class CriteriaExpression {
    
    protected List<CriteriaExpression> criteriaExpressions;
    protected List<ValueExpression> valueExpressions;
    protected LogicalOperationCode criteriaLogicOperation;

    /**
     * Gets the value of the criteriaExpressions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the criteriaExpressions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCriteriaExpressions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CriteriaExpression }
     * 
     * 
     */
    public List<CriteriaExpression> getCriteriaExpressions() {
        if (criteriaExpressions == null) {
            criteriaExpressions = new ArrayList<CriteriaExpression>();
        }
        return this.criteriaExpressions;
    }

    /**
     * Gets the value of the valueExpressions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueExpressions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueExpressions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueExpression }
     * 
     * 
     */
    public List<ValueExpression> getValueExpressions() {
        if (valueExpressions == null) {
            valueExpressions = new ArrayList<ValueExpression>();
        }
        return this.valueExpressions;
    }

    /**
     * Gets the value of the criteriaLogicOperation property.
     * 
     * @return
     *     possible object is
     *     {@link LogicalOperationCode }
     *     
     */
    public LogicalOperationCode getCriteriaLogicOperation() {
        return criteriaLogicOperation;
    }

    /**
     * Sets the value of the criteriaLogicOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalOperationCode }
     *     
     */
    public void setCriteriaLogicOperation(LogicalOperationCode value) {
        this.criteriaLogicOperation = value;
    }

}
