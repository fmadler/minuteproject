package net.sf.minuteproject.adf.query;

import java.util.ArrayList;
import java.util.List;

public class QueryRequest {
    protected List<Criteria> criterias;
    protected Pagination pagination;
    protected SortType sort;

    public enum PrototypeMatchType{ALL, ANY};
    protected PrototypeMatchType matchType;
    
    public enum PrototypeType{EQUALS, CONTAINS, LIKE, STARTWITH};
    protected PrototypeType type;
    
    public enum PrototypeCase{SENSITIVE, INSENSITIVE};
    protected PrototypeCase caseSensitiveness;
    /**
     * Gets the value of the criterias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the criterias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCriterias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Criteria }
     * 
     * 
     */
    public List<Criteria> getCriterias() {
        if (criterias == null) {
            criterias = new ArrayList<Criteria>();
        }
        return this.criterias;
    }

    /**
     * Gets the value of the pagination property.
     * 
     * @return
     *     possible object is
     *     {@link Pagination }
     *     
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Sets the value of the pagination property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pagination }
     *     
     */
    public void setPagination(Pagination value) {
        this.pagination = value;
    }
    
    /**
     * Sets the value of the sort property.
     * 
     * @param sort
     *     allowed object is
     *     {@link SortType }
     *     
     */
    public void setSort(SortType sort) {
        this.sort = sort;
    }

    /**
     * Gets the value of the sort property.
     * 
     * @return
     *     possible object is
     *     {@link SortType }
     *     
     */
    public SortType getSort() {
        return sort;
    }

    public void setMatchType(QueryRequest.PrototypeMatchType matchType) {
        this.matchType = matchType;
    }

    public QueryRequest.PrototypeMatchType getMatchType() {
        return matchType;
    }

    public void setType(QueryRequest.PrototypeType type) {
        this.type = type;
    }

    public QueryRequest.PrototypeType getType() {
        return type;
    }

    public void setCaseSensitiveness(QueryRequest.PrototypeCase caseSensitiveness) {
        this.caseSensitiveness = caseSensitiveness;
    }

    public QueryRequest.PrototypeCase getCaseSensitiveness() {
        return caseSensitiveness;
    }

}
