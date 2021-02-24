package net.sf.minuteproject.adf.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.bean.DCDataRow;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.ControlBinding;

import oracle.binding.OperationBinding;


import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;

import org.apache.myfaces.trinidad.event.RowKeySetChangeEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


/**
 * A series of convenience functions for dealing with ADF Bindings.
 * Note: Updated for JDeveloper 11
 *
 * @author Duncan Mills
 * @author Steve Muench
 *
 * $Id: ADFUtils.java 2513 2007-09-20 20:39:13Z ralsmith $.
 */
public class ADFUtils {
    
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(ADFUtils.class);

    /**
     * Get application module for an application module data control by name.
     * @param name application module data control name
     * @return ApplicationModule
     */
    public static ApplicationModule getApplicationModuleForDataControl(String name) {
        return (ApplicationModule)JSFUtils.resolveExpression("#{data." + name + 
                                                             ".dataProvider}");
    }

    /**
     * A convenience method for getting the price of a bound attribute in the
     * current publicationPage context programatically.
     * @param attributeName of the bound price in the pageDef
     * @return price of the attribute
     */
    public static Object getBoundAttributeValue(String attributeName) {
        return findControlBinding(attributeName).getInputValue();
    }

    /**
     * A convenience method for setting the price of a bound attribute in the
     * context of the current publicationPage.
     * @param attributeName of the bound price in the pageDef
     * @param value to set
     */
    public static void setBoundAttributeValue(String attributeName, 
                                              Object value) {
        findControlBinding(attributeName).setInputValue(value);
    }

    /**
     * Returns the evaluated price of a pageDef parameter.
     * @param pageDefName reference to the publicationPage definition file of the publicationPage with the parameter
     * @param parameterName name of the pagedef parameter
     * @return evaluated price of the parameter as a String
     */
    public static Object getPageDefParameterValue(String pageDefName, 
                                                  String parameterName) {
        BindingContainer bindings = findBindingContainer(pageDefName);
        DCParameter param = 
            ((DCBindingContainer)bindings).findParameter(parameterName);
        return param.getValue();
    }

    /**
     * Convenience method to find a DCControlBinding as an AttributeBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param bindingContainer binding container
     * @param attributeName name of the attribute binding.
     * @return the control price binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(BindingContainer bindingContainer, 
                                                      String attributeName) {
        if (attributeName != null) {
            if (bindingContainer != null) {
                ControlBinding ctrlBinding = 
                    bindingContainer.getControlBinding(attributeName);
                if (ctrlBinding instanceof AttributeBinding) {
                    return (AttributeBinding)ctrlBinding;
                }
            }
        }
        return null;
    }

    /**
     * Convenience method to find a DCControlBinding as a JUCtrlValueBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param attributeName name of the attribute binding.
     * @return the control price binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(String attributeName) {
        return findControlBinding(getBindingContainer(), attributeName);
    }

    /**
     * Return the current publicationPage's binding container.
     * @return the current publicationPage's binding container
     */
    public static BindingContainer getBindingContainer() {
        return (BindingContainer)JSFUtils.resolveExpression("#{bindings}");
    }

    /**
     * Return the Binding Container as a DCBindingContainer.
     * @return current binding container as a DCBindingContainer
     */
    public static DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer)getBindingContainer();
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the price of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the price attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsForIterator(String iteratorName, 
                                                          String valueAttrName, 
                                                          String displayAttrName) {
        return selectItemsForIterator(findIterator(iteratorName), 
                                      valueAttrName, displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the price of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the price attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute to use for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List<SelectItem> selectItemsForIterator(String iteratorName, 
                                                          String valueAttrName, 
                                                          String displayAttrName, 
                                                          String descriptionAttrName) {
        return selectItemsForIterator(findIterator(iteratorName), 
                                      valueAttrName, displayAttrName, 
                                      descriptionAttrName);
    }

    /**
     * Get List of attribute values for an iterator.
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName price attribute to use
     * @return List of attribute values for an iterator
     */
    public static List attributeListForIterator(String iteratorName, 
                                                String valueAttrName) {
        return attributeListForIterator(findIterator(iteratorName), 
                                        valueAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iteratorName iterabot binding name
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(String iteratorName) {
        return keyListForIterator(findIterator(iteratorName));
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iter iterator binding
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(DCIteratorBinding iter) {
        List<Key> attributeList = new ArrayList<Key>();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getKey());
        }
        return attributeList;
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     * @param iteratorName iterator binding name
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(String iteratorName, 
                                                   String keyAttrName) {
        return keyAttrListForIterator(findIterator(iteratorName), keyAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     * 
     * @param iter iterator binding
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(DCIteratorBinding iter, 
                                                   String keyAttrName) {
        List<Key> attributeList = new ArrayList<Key>();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));
        }
        return attributeList;
    }

    /**
     * Get a List of attribute values for an iterator.
     *
     * @param iter iterator binding
     * @param valueAttrName name of price attribute to use
     * @return List of attribute values
     */
    public static List attributeListForIterator(DCIteratorBinding iter, 
                                                String valueAttrName) {
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getAttribute(valueAttrName));
        }
        return attributeList;
    }

    /**
     * Find an iterator binding in the current binding container by name.
     * 
     * @param name iterator binding name
     * @return iterator binding
     */
    public static DCIteratorBinding findIterator(String name) {
        DCIteratorBinding iter = 
            getDCBindingContainer().findIteratorBinding(name);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + name + "' not found");
        }
        return iter;
    }
    
    public static DCIteratorBinding findIterator(String bindingContainer, String iterator) {
        DCBindingContainer bindings = 
            (DCBindingContainer)JSFUtils.resolveExpression("#{" + bindingContainer + "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" + 
                                       bindingContainer + "' not found");
        }
        DCIteratorBinding iter = bindings.findIteratorBinding(iterator);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + iterator + "' not found");
        }
        return iter;
    }

    public static JUCtrlValueBinding findCtrlBinding(String name){
        JUCtrlValueBinding rowBinding = 
            (JUCtrlValueBinding)getDCBindingContainer().findCtrlBinding(name);    
        if (rowBinding == null) {
            throw new RuntimeException("CtrlBinding " + name + "' not found");
        }
        return rowBinding;
    }

    /**
     * Find an operation binding in the current binding container by name.
     * 
     * @param name operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(String name) {
        OperationBinding op = 
            getDCBindingContainer().getOperationBinding(name);
        if (op == null) {
            throw new RuntimeException("Operation '" + name + "' not found");
        }
        return op;
    }

    /**
     * Find an operation binding in the current binding container by name.
     * 
     * @param bindingContianer binding container name
     * @param opName operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(String bindingContianer, 
                                                 String opName) {
        DCBindingContainer bindings = 
            (DCBindingContainer)JSFUtils.resolveExpression("#{" + bindingContianer + "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" + 
                                       bindingContianer + "' not found");
        }
        OperationBinding op = 
            bindings.getOperationBinding(opName);
        if (op == null) {
            throw new RuntimeException("Operation '" + opName + "' not found");
        }
        return op;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the price of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of price attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, 
                                                          String valueAttrName, 
                                                          String displayAttrName, 
                                                          String descriptionAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName), 
                                           (String)r.getAttribute(displayAttrName), 
                                           (String)r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the price of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of price attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, 
                                                          String valueAttrName, 
                                                          String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName), 
                                           (String)r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     * 
     * Uses the rowKey of each row as the SelectItem key.
     * 
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, 
                                                               String displayAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName), 
                                           displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     * 
     * Uses the rowKey of each row as the SelectItem key.
     * 
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, 
                                                               String displayAttrName, 
                                                               String descriptionAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName), 
                                           displayAttrName, 
                                           descriptionAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     * 
     * Uses the rowKey of each row as the SelectItem key.
     * 
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, 
                                                               String displayAttrName, 
                                                               String descriptionAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(), 
                                           (String)r.getAttribute(displayAttrName), 
                                           (String)r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     * 
     * Uses the rowKey of each row as the SelectItem key.
     * 
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return List of ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, 
                                                               String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(), 
                                           (String)r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Find the BindingContainer for a publicationPage definition by name.
     *
     * Typically used to refer eagerly to publicationPage definition parameters. It is
     * not best practice to reference or set bindings in binding containers
     * that are not the one for the current publicationPage.
     *
     * @param pageDefName name of the publicationPage defintion XML file to use
     * @return BindingContainer ref for the named definition
     */
    private static BindingContainer findBindingContainer(String pageDefName) {
        BindingContext bctx = getDCBindingContainer().getBindingContext();
        BindingContainer foundContainer = 
            bctx.findBindingContainer(pageDefName);
        return foundContainer;
    }

    public static void printOperationBindingExceptions(List opList){
        if(opList != null && !opList.isEmpty()){
            for(Object error:opList){
                LOGGER.severe( error.toString() );
            }
        }
    }
    
    public static ViewObject getViewObjectByIterator(String itName) {
       return getDCBindingContainer().findIteratorBinding(itName).getViewObject();
    }

    public static Object invokeOperation(String methodAction) {
      return invokeOperation(methodAction, null, null);
    }

    public static Object invokeOperation(String methodAction,
                                         Map <String, Object> params) {
      BindingContainer dCBindingContainer =
        BindingContext.getCurrent().getCurrentBindingsEntry();
      OperationBinding ob = dCBindingContainer.getOperationBinding(methodAction);
      if (null != params && !params.isEmpty()) {
        ob.getParamsMap().putAll(params);
      }

      ob.execute();
      Object result = ob.getResult();

      return result;
    }

    public static <T> T invokeOperation(String methodAction,
                                        Class<T> resultType) {
      return (T)invokeOperation(methodAction, null, resultType);
    }


    public static <T> T invokeOperation(String methodAction,
                                        Map<String, Object> params,
                                        Class<T> resultType) {
      T t = null;
      Object result = invokeOperation(methodAction, params);
      if (result != null) {
        if (resultType != null) {
          t = resultType.cast(result);
        }
      }
      return t;
    }

    public static <T> T getBoundAttributeValue(Class<T> clazz,
                                               String attributeName) {
      Object value = getBoundAttributeValue(attributeName);
      T t = null;
      if (null != value) {
        t = clazz.cast(value);
      }
      return t;
    }   
    
    public static void refreshMainPage() {
      refreshMainPage("f1");
    }

    public static void refreshMainPage(String mainFormID) {
      AdfFacesContext context = AdfFacesContext.getCurrentInstance();
      UIComponent form = findComponent(mainFormID);

      if (null != form) {
        context.addPartialTarget(form);
      } else {

      }
    }

    public static <T extends UIComponent> T findComponent(String id) {

      FacesContext facesCtx = FacesContext.getCurrentInstance();
      UIComponent comp = facesCtx.getViewRoot().findComponent(id);
      return (T)comp;
    }

    public static UIComponent findComponentInRoot(String id) {
      UIComponent component = null;

      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (facesContext != null) {
        UIComponent root = facesContext.getViewRoot();
        component = findComponent(root, id);
      }

      return component;
    }

    public static UIComponent findComponent(UIComponent base, String id) {
      if (id.equals(base.getId()))
        return base;

      UIComponent kid = null;
      UIComponent result = null;
      Iterator kids = base.getFacetsAndChildren();
      while (kids.hasNext() && (result == null)) {
        kid = (UIComponent)kids.next();
        if (id.equals(kid.getId())) {
          result = kid;
          break;
        }
        result = findComponent(kid, id);
        if (result != null) {
          break;
        }
      }
      return result;
    }
    
    public static <T extends Object> T getDisclosedTableRowValue(RowKeySetChangeEvent rowDisclosureEvent,
                                                                 Class<T> clazz) {
      T inst = null;
      RichTable table = (RichTable)rowDisclosureEvent.getSource();
      RowKeySet discloseRowKeySet = table.getDisclosedRowKeys();
      RowKeySet lastAddedRowKeySet = rowDisclosureEvent.getAddedSet();
      Iterator lastAddedRowKeySetIter = lastAddedRowKeySet.iterator();
      if (lastAddedRowKeySetIter.hasNext()) {
        discloseRowKeySet.clear();
        Object lastRowKey = lastAddedRowKeySetIter.next();
        discloseRowKeySet.add(lastRowKey);

        //makeDisclosedRowCurrent(table, lastAddedRowKeySet);
        CollectionModel tableModel = (CollectionModel)table.getValue();
        JUCtrlHierBinding tableHierBinding = null;
        tableHierBinding = (JUCtrlHierBinding)(tableModel).getWrappedData();
        DCIteratorBinding dCIteratorBindin = null;
        dCIteratorBindin = tableHierBinding.getDCIteratorBinding();
        Iterator keySetIter = lastAddedRowKeySet.iterator();
        List firstKey = (List)keySetIter.next();
        oracle.jbo.Key key = (oracle.jbo.Key)firstKey.get(0);


        
        boolean isInsideIterator=false;
        for (Row row:dCIteratorBindin.getAllRowsInRange()) {
            if (row.getKey().toStringFormat(true).equals(key.toStringFormat(true))) {
                isInsideIterator = true;
            }
        }

        if (!dCIteratorBindin.getCurrentRowKeyString().equals(key.toStringFormat(true)) && isInsideIterator) {
            dCIteratorBindin.setCurrentRowWithKey(key.toStringFormat(true));
        }
        
          table.setSelectedRowKeys(lastAddedRowKeySet);

        //setSelectedMeasure
        //CollectionModel tableModel = (CollectionModel)table.getValue();
        if (tableModel != null) {
          tableHierBinding = null;
          tableHierBinding = (JUCtrlHierBinding)(tableModel).getWrappedData();
          DCDataRow currentDataRow = (DCDataRow)tableHierBinding.getCurrentRow();
          Object val = currentDataRow.getDataProvider();
          if (val != null) {
            if (clazz.isAssignableFrom(val.getClass())) {
              inst = clazz.cast(val);
            } else {
              throw new IllegalArgumentException("Cannot cast table row value of type [" +
                                                 val.getClass() + " to " +
                                                 clazz + "]");
            }
          }
        }
      }
      return inst;
    }
    //Method to set the value of page flow scope created on runtime
    public static void setPageFlowScopeValue(String name, Object value) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        pageFlowScope.put(name, value);
    }
    
    //method to get the value of page flow scope created on runtime
    public static Object getPageFlowScopeValue(String name) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        Object val = pageFlowScope.get(name);
     
        if (val == null)
            return 0;
        else
            return val;
    }
	
    public static void setApplicationScopeValue(String name, Object value) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map scope = adfCtx.getApplicationScope();
        scope.put(name, value);
    }
    
    //method to get the value of page flow scope created on runtime
    public static Object getApplicationScopeValue(String name) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map scope = adfCtx.getApplicationScope();
        Object val = scope.get(name);
     
        if (val == null)
            return 0;
        else
            return val;
    }
}
