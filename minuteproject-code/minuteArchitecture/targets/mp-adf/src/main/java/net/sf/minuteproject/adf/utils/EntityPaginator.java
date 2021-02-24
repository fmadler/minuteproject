package net.sf.minuteproject.adf.utils;

import java.math.BigInteger;

import java.util.Map;

import net.sf.minuteproject.adf.query.Pagination;
import net.sf.minuteproject.adf.query.QueryRequest;
import net.sf.minuteproject.adf.query.QueryResponse;

import org.apache.myfaces.trinidad.event.SortEvent;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import org.apache.commons.lang.StringUtils;

public class EntityPaginator <T extends QueryRequest> extends TableAbstractPaginator {

    private String operationBinding;
    private T request;  
    private Class<T> clazz;

    public EntityPaginator(Class<T> clazz, String operationBinding) {
        this.operationBinding = operationBinding;
        this.clazz = clazz;
        this.request =  getRequest();
        this.setNumberOfRecords(null);
    }
    
    public T buildOne()
    {
        try {
            request = clazz.newInstance();
            Pagination pagination = new Pagination();
            request.setPagination( pagination); 
            pagination.setFetchSize(new BigInteger(String.valueOf(this.RANGE_SIZE)));  
            pagination.setStartPosition(new BigInteger("0"));
            return request;
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }
    
    
    public Integer getLastPage() {
        Long lastPageNum = 1L;
        if (this.getNumberOfRecords()>0) {
            lastPageNum = (this.getNumberOfRecords()-1)/getRANGE_SIZE()+1;
        }
        return lastPageNum.intValue();
    }

    
    public void whenGoToPage(Integer integer) {
    }

    public void whenChangeRangeSize(Integer integer) {
    }

    public void whenRefreshTable() {
        
        Pagination pag= getRequest().getPagination();
        pag.setFetchSize(BigInteger.valueOf(Integer.valueOf(this.getRANGE_SIZE())));
        pag.setStartPosition(BigInteger.valueOf(this.getRANGE_SIZE()*(pageNumber-1)));
        
        BindingContext bindingCtx = BindingContext.getCurrent();
        BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
        
        OperationBinding ob = bindings.getOperationBinding(operationBinding);
        ob.getParamsMap().put("request", request);
        ob.execute();

        QueryResponse response = (QueryResponse)ob.getResult();
        if (response!=null && response.getTotalRowsCount()!=null) {
            setNumberOfRecords(Long.valueOf(response.getTotalRowsCount().intValue()) );
        }
    }
    
    public void doSearch(){
        this.setPageNumber(1);
        this.getRequest().getCriterias().clear();
        this.refreshTable();
        
    }
    
    
    public Long getNumberOfRecords(){
        if(this.numberOfRecords==null){
            this.setNumberOfRecords(0L);
            BindingContext bindingCtx = BindingContext.getCurrent();
            BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
            OperationBinding ob = bindings.getOperationBinding(operationBinding);
            if (ob.getResult()!=null && ((QueryResponse)ob.getResult()).getTotalRowsCount()!=null) {
                Integer totalRowsCount = ((QueryResponse)ob.getResult()).getTotalRowsCount();
                this.setNumberOfRecords(totalRowsCount.longValue());
            }
        }
        
        
        return this.numberOfRecords;
    }


    public void setRequest(T request) {
        this.request = request;
    }

    public T getRequest() {
        if (request==null)
            request = buildOne();
        return request;
    }
    
    public void setRangeSize(Integer rangeSize) {
        this.RANGE_SIZE = rangeSize.shortValue();
    }

    public Integer getRangeSize() {
        
        return Integer.valueOf(this.RANGE_SIZE);
    }

    
}
