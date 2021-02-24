package net.sf.minuteproject.adf.query;

public class Criteria {
    
    protected CriteriaExpression criteriaExpression;

    /**
     * Gets the value of the criteriaExpression property.
     * 
     * @return
     *     possible object is
     *     {@link CriteriaExpression }
     *     
     */
    public CriteriaExpression getCriteriaExpression() {
        return criteriaExpression;
    }

    /**
     * Sets the value of the criteriaExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link CriteriaExpression }
     *     
     */
    public void setCriteriaExpression(CriteriaExpression value) {
        this.criteriaExpression = value;
    }
}
