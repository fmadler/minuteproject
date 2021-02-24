package net.sf.minuteproject.adf.utils;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.SortEvent;

public abstract class TableAbstractPaginator {
    
    protected Integer pageNumber=1;
    protected short RANGE_SIZE=10;
    protected Long numberOfRecords=0L;
    
    //pagination components
    protected RichCommandImageLink firstPageUIComp;
    protected RichCommandImageLink previousPageUIComp;
    protected RichCommandImageLink nextPageUIComp;
    protected RichCommandImageLink lastPageUIComp;
    protected RichSelectOneChoice rangeSizeUIComp;
    protected RichInputText pageNoUIComp;
    protected RichOutputText lastPageNoUIComp;
    protected RichOutputText numberOfRecordsUIComp;
    protected RichTable tableUIComp;
    
    
    //actions ---------------------------------------------------------------
    /**
     * Invoked when user click to go to the last publicationPage
     * @return
     */
    public void goFirstPage(){
        goToPage(1);
      /*  pageNumber=1;
        rangeBinding.getIteratorBinding().getNavigatableRowIterator().setRangeStart(0);
        refreshTable(); */
    }
    
    /**
     * Invoked when user click to go to the next publicationPage
     * @return
     */
    public void goNextSet(){
        if(pageNumber < getLastPage()) {
            goToPage(pageNumber+1);
        }
    }
    
    /**
     * Invoked when user click to go to the previous publicationPage
     * @return
     */
    public void goPreviousSet(){
        if (pageNumber>1){
            goToPage(pageNumber-1);
        }
    }
    
    /**
     * Invoked when user click to go to the last publicationPage
     * @return
     */
    public void goLastPage(){
        Integer lastPage = getLastPage();
        goToPage(lastPage);
    }
    
    /**
     * Get the last publicationPage number
     * @return
     */
    public abstract Integer getLastPage();
    
    /**
     * Is go to previous publicationPage enabled
     * @return
     */
    public boolean isPreviousEnabled(){
        return this.pageNumber>1;
    }
    
    /**
     * Is go to next publicationPage enabled
     * @return
     */
    public boolean isNextEnabled(){
        long lastPageNum = getLastPage();
        return this.pageNumber<lastPageNum;
        
       /* long lastPageNum = Math.max(1,(rangeBinding.getIteratorBinding().getEstimatedRowCount()-1)/RANGE_SIZE+1);
        long currentPage = rangeBinding.getIteratorBinding().getNavigatableRowIterator().getRangeStart()/RANGE_SIZE+1;
        return currentPage<lastPageNum; */
    }
    //end of actions ---------------------------------------------------------
    
    
    
    //listeners --------------------------------------------------------------
    
    public void rangeSizeListener(ValueChangeEvent valueChangeEvent) {
        Short newRangeSize = null;
        try{
            newRangeSize= (Short)valueChangeEvent.getNewValue();
        }catch(Exception e){}
        
        setRangeSize(newRangeSize);
        
    }
    
    /**
     * Invoked when user click the Go button to go to the target page
     * @return
     */
    public void pageNumberListener(ValueChangeEvent valueChangeEvent) {
        Integer newPageNumber = null;
        try{
            newPageNumber= (Integer)valueChangeEvent.getNewValue();
        }catch(Exception e){}
        
        goToPage(newPageNumber);
    }
    
  
    
    //end of listeners -------------------------------------------------------
    
    //utils-------------------------------------------------------------------
    public abstract void whenGoToPage(Integer newPageNumber);
    
    public abstract void whenChangeRangeSize(Integer newRangeSize);
    
    public abstract void whenRefreshTable();
    
    protected void goToPage(Integer newPageNumber){
        if (newPageNumber!=null && newPageNumber<=getLastPage() && newPageNumber>=0) {
            whenGoToPage(newPageNumber);
            this.pageNumber = newPageNumber;
            refreshTable();
        }
    }
    
    protected void setRangeSize(Short newRangeSize) {
        if (newRangeSize!=null) {
            this.RANGE_SIZE = newRangeSize;
            this.pageNumber = 1;
            whenChangeRangeSize(newRangeSize.intValue());
            refreshTable();
        }
    }
    
    protected void refreshTable(){
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        
                if (firstPageUIComp!=null) {
                    adfFacesContext.addPartialTarget(firstPageUIComp);
                }
                if (previousPageUIComp!=null) {
                    adfFacesContext.addPartialTarget(previousPageUIComp);
                }        
                if (nextPageUIComp!=null) {
                    adfFacesContext.addPartialTarget(nextPageUIComp);
                }
                if (lastPageUIComp!=null) {
                    adfFacesContext.addPartialTarget(lastPageUIComp);
                }
                if (rangeSizeUIComp!=null) {
                    adfFacesContext.addPartialTarget(rangeSizeUIComp);
                }
                if (pageNoUIComp!=null) {
                    adfFacesContext.addPartialTarget(pageNoUIComp);
                }
                if (lastPageNoUIComp!=null) {
                    adfFacesContext.addPartialTarget(lastPageNoUIComp);
                }
                if (tableUIComp!=null) {
                    adfFacesContext.addPartialTarget(tableUIComp);
                }
                if (numberOfRecordsUIComp!=null) {
                    adfFacesContext.addPartialTarget(numberOfRecordsUIComp);
                }
                
        whenRefreshTable();
    }
    
    //end of utils -----------------------------------------------------------
    
    
    
    //setters and getters ----------------------------------------------------

    public void setFirstPageUIComp(RichCommandImageLink firstPageUIComp) {
        this.firstPageUIComp = firstPageUIComp;
    }

    public RichCommandImageLink getFirstPageUIComp() {
        return firstPageUIComp;
    }

    public void setPreviousPageUIComp(RichCommandImageLink previousPageUIComp) {
        this.previousPageUIComp = previousPageUIComp;
    }

    public RichCommandImageLink getPreviousPageUIComp() {
        return previousPageUIComp;
    }

    public void setNextPageUIComp(RichCommandImageLink nextPageUIComp) {
        this.nextPageUIComp = nextPageUIComp;
    }

    public RichCommandImageLink getNextPageUIComp() {
        return nextPageUIComp;
    }

    public void setLastPageUIComp(RichCommandImageLink lastPageUIComp) {
        this.lastPageUIComp = lastPageUIComp;
    }

    public RichCommandImageLink getLastPageUIComp() {
        return lastPageUIComp;
    }

    public void setRangeSizeUIComp(RichSelectOneChoice rangeSizeUIComp) {
        this.rangeSizeUIComp = rangeSizeUIComp;
    }

    public RichSelectOneChoice getRangeSizeUIComp() {
        return rangeSizeUIComp;
    }

    public void setPageNoUIComp(RichInputText pageNoUIComp) {
        this.pageNoUIComp = pageNoUIComp;
    }

    public RichInputText getPageNoUIComp() {
        return pageNoUIComp;
    }

    public void setLastPageNoUIComp(RichOutputText lastPageNoUIComp) {
        this.lastPageNoUIComp = lastPageNoUIComp;
    }

    public RichOutputText getLastPageNoUIComp() {
        return lastPageNoUIComp;
    }

    public void setTableUIComp(RichTable tableUIComp) {
        this.tableUIComp = tableUIComp;
    }

    public RichTable getTableUIComp() {
        return tableUIComp;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setRANGE_SIZE(short RANGE_SIZE) {
        this.RANGE_SIZE = RANGE_SIZE;
    }

    public short getRANGE_SIZE() {
        return RANGE_SIZE;
    }

    public void setNumberOfRecords(Long numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public Long getNumberOfRecords(){
        return this.numberOfRecords;
    }

    public void setNumberOfRecordsUIComp(RichOutputText numberOfRecordsUIComp) {
        this.numberOfRecordsUIComp = numberOfRecordsUIComp;
    }

    public RichOutputText getNumberOfRecordsUIComp() {
        return numberOfRecordsUIComp;
    }
}
