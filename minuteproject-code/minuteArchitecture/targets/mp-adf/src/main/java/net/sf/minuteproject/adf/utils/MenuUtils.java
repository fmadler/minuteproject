package net.sf.minuteproject.adf.utils;

//#import eu.europa.ec.security.authorisation.services.interfaces.v1.GetProfileResponse;
//#import eu.europa.ec.soa.web.filter.SecundaFilterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;

import org.apache.commons.lang.StringUtils;
//import org.apache.myfaces.trinidad.menu.ImmutableItemNode;
//TODO change for the not deprecated version
import org.apache.myfaces.trinidadinternal.menu.ImmutableItemNode;
import org.apache.myfaces.trinidadinternal.menu.ImmutableGroupNode;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.model.XMLMenuModel;


public class MenuUtils {

    private List<TreeItem> treeList = new ArrayList<TreeItem>();
    private String menuModelName;
    private Boolean isRootMenu = false;
    
   // static private final String _ROOT_MODEL_KEY ="org.apache.myfaces.trinidad.model.XMLMenuModel.__root_menu__";
  //  static private final String _MENUCONTENTHANDLER_SERVICE = "org.apache.myfaces.trinidad.model.XMLMenuModel$MenuContentHandler";
    
    public MenuUtils(){
        if (StringUtils.isNotBlank(menuModelName)) {
            init();
        }
    }
    
    
    
    private void init(){        
        XMLMenuModel model =getModel();
        TreeModel tree = (TreeModel)model.getWrappedData();
        
        if (tree.getRowCount()>0)
        {
            
            tree.setRowIndex(0);
            //skip the European Commision group node used in breadCrumbs if isRootMenu is true
            if (isRootMenu) {
                tree.enterContainer();
            }
            
            int nrows = tree.getRowCount();            
            for( int i = 0; i < tree.getRowCount(); i++) {
                tree.setRowIndex(i);
                Object node = tree.getRowData();
                TreeItem treeItem = new TreeItem(node);
                
                if (tree.isContainer() && !tree.isContainerEmpty())
                {
                    treeItem.setHasChildren(true);
                    tree.enterContainer();
                    List<TreeItem> treeList2 = new ArrayList<TreeItem>();
                    for( int j = 0; j < tree.getRowCount(); j++) {
                        boolean displaySubMenu = false;
                        tree.setRowIndex(j);
                        Object node2 = tree.getRowData();
                        TreeItem treeItem2 = new TreeItem(node2);
                        //System.out.println ("!!!!!!!!!!!!!!!!!!! node2 = "+node2);
                        if (tree.isContainer() && !tree.isContainerEmpty()){
                            treeItem2.setHasChildren(true);
                            tree.enterContainer();
                            List<Object> treeList3 = new ArrayList<Object>();
                            for( int k = 0; k < tree.getRowCount(); k++) {
                                tree.setRowIndex(k);
                                Object node3 = tree.getRowData();
                                //System.out.println (">>>>>>>>>>> node3 = "+node3);
                                if (node3 instanceof ImmutableItemNode) {
                                    //System.out.println (">>>>>>>>>>> node3 ImmutableItemNode ");
                                    ImmutableItemNode iin = (ImmutableItemNode)node3;
                                    if (isToDisplay(iin)) {
                                        displaySubMenu = true;
                                        //System.out.println ("!!!!!!!!!!!!!! node3 = "+iin.getId()+" added");
                                        treeList3.add(node3);
                                    }
                                }
                            }
                            treeItem2.setChildren(treeList3);
                            tree.exitContainer();
 
                        }
                        if (displaySubMenu) {
                            treeList2.add(treeItem2);
                        }                    

                    }                        
                    treeItem.setChildren(treeList2);
                    tree.exitContainer();
                }
                else {
                    treeItem.setHasChildren(false);
                }
                treeList.add(treeItem);
            }
            if (isRootMenu) {
                tree.exitContainer();
            }
            
        }
    }
    
            
    private boolean isToDisplay(ImmutableItemNode iin) {
        return isToDisplay(iin.getId()+"-access");
    }
    private boolean isToDisplay(String assertionId) {
//#        System.out.println(">>> display menu assertion "+assertionId);
//#        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//#        Object o = session.getAttribute(SecundaFilterUtils.SECUNDA_PROFILE);
//#        if (o!=null) {
//#                GetProfileResponse gpr = (GetProfileResponse) o;
//#                return getDisplay(gpr, assertionId);
//#        }
 //#       System.out.println(">>> display menu assertion end");   
 //#       return false;
          return true;
    }

//#        private boolean getDisplay(GetProfileResponse gpr, String assertionId) {
//#                return SecundaFilterUtils.assertFilterAssertion (gpr, assertionId);
//#        }
        
    public XMLMenuModel getModel()
    {
        ExpressionFactory ef = 
          FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        return (XMLMenuModel) ef.createValueExpression(context
                                              ,"#{"+menuModelName+"}"
                                              , XMLMenuModel.class).getValue(context);
    }
    
    public List<TreeItem> getTreeList() { 
        if (treeList.isEmpty() && StringUtils.isNotBlank(menuModelName)) {
            init();
        }
        return treeList;
    }
     
    
    
    
    
    
//    private Map<Integer,XMLMenuModel> getRootModelMap()
//    {
//       FacesContext facesContext = FacesContext.getCurrentInstance();
//       Map<String, Object> requestMap =
//         facesContext.getExternalContext().getRequestMap();
//       Map<Integer,XMLMenuModel> map = (Map<Integer,XMLMenuModel>) requestMap.get(_ROOT_MODEL_KEY);
//      return map;
//    }

    private Map<String, Object> getRequestMap()
    {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         Map<String, Object> requestMap =
           facesContext.getExternalContext().getRequestMap();
         return requestMap;
    }

    public void doAction(javax.faces.event.ActionEvent event) {
        XMLMenuModel model =getModel();        
        RichCommandImageLink comp = (RichCommandImageLink)event.getComponent();
        //XMLMenuModel model = (XMLMenuModel) comp.getAttributes().get("model");
        String currentNodeId = (String) comp.getAttributes().get("nodeId");
        Object nodeLast = (Object) comp.getAttributes().get("node");
        Object nodeAction = (Object) comp.getAttributes().get("nodeAction");
        
        if (currentNodeId!=null) {
            Object currentNode = model.getNode(currentNodeId);
            model.setRowKey(currentNode);
            model.setCurrentlyPostedNode(currentNode);
            
        }    
    }

    public void setMenuModelName(String menuModelName) {
        this.menuModelName = menuModelName;
    }

    public String getMenuModelName() {
        return menuModelName;
    }

    public void setIsRootMenu(Boolean isRootMenu) {
        this.isRootMenu = isRootMenu;
    }

    public Boolean getIsRootMenu() {
        return isRootMenu;
    }
}
