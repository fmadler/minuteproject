package net.sf.minuteproject.adf.query;

import java.util.List;
import java.util.Stack;

public abstract class QueryResponse<T> {
  protected String objectType;
  protected Integer totalRowsCount;
  protected Integer payloadRowsCount;
  protected Integer sequenceNumber;
  protected List<T> payload;

  public String getObjectType() {
    return objectType;
  }

  /**
   * Sets the value of the objectType property.
   *
   * @param value
   *     allowed object is
   *     {@link String }
   *
   */
  public void setObjectType(String value) {
    this.objectType = value;
  }

  /**
   * Gets the value of the totalRowsCount property.
   *
   * @return
   *     possible object is
   *     {@link Integer }
   *
   */
  public Integer getTotalRowsCount() {
    return totalRowsCount;
  }

  /**
   * Sets the value of the totalRowsCount property.
   *
   * @param value
   *     allowed object is
   *     {@link Integer }
   *
   */
  public void setTotalRowsCount(Integer value) {
    this.totalRowsCount = value;
  }

  /**
   * Gets the value of the payloadRowsCount property.
   *
   * @return
   *     possible object is
   *     {@link Integer }
   *
   */
  public Integer getPayloadRowsCount() {
    return payloadRowsCount;
  }

  /**
   * Sets the value of the payloadRowsCount property.
   *
   * @param value
   *     allowed object is
   *     {@link Integer }
   *
   */
  public void setPayloadRowsCount(Integer value) {
    this.payloadRowsCount = value;
  }

  /**
   * Gets the value of the sequenceNumber property.
   *
   * @return
   *     possible object is
   *     {@link Integer }
   *
   */
  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  /**
   * Sets the value of the sequenceNumber property.
   *
   * @param value
   *     allowed object is
   *     {@link Integer }
   *
   */
  public void setSequenceNumber(Integer value) {
    this.sequenceNumber = value;
  }
  
  protected List<T> getPayload(){
    if(null == payload){
      payload = new Stack<T>();
    }
    return payload;
  }
  
  protected void setPayload(List<T> t){
    List<T> els = getPayload();
    els.clear();
    els.addAll(t);
  }
}
