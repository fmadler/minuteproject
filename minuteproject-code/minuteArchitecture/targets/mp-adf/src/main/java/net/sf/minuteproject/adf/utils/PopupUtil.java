package net.sf.minuteproject.adf.utils;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * Patterns Utility class for public use.
 */
public class PopupUtil
{
  /**
   * Shows the specified popup component and its contents
   * @param popupId is the clientId of the popup to be shown
   * clientId is derived from backing bean for the af:popup using getClientId method
   */
  public static void invokePopup(String popupId)
  {
    invokePopup(popupId, null, null);
  }

  /**
   * Shows the specified popup and uses the specified hints to align the popup.
   * @param popupId is the clientId of the popup to be shown - clientId is derived from backing bean for the af:popup using getClientId method
   * @param align is a hint for the popup display. Check AdfRichPopup js javadoc for valid values. Supported value includes: "AdfRichPopup.ALIGN_START_AFTER", "AdfRichPopup.ALIGN_BEFORE_START" and "AdfRichPopup.ALIGN_END_BEFORE"
   * @param alignId is the clientId of the component the popup should align to - clientId is derived from backing bean for the component using getClientId method
   * align and alignId need to be specified together - specifying null for either of them will have no effect.
   */
  public static void invokePopup(String popupId, String align,
                                 String alignId)
  {
    if (popupId != null)
    {
      ExtendedRenderKitService service =
        Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                    ExtendedRenderKitService.class);

      StringBuffer showPopup = new StringBuffer();
      showPopup.append("var hints = new Object();");
      //Add hints only if specified - see javadoc for AdfRichPopup js for details on valid values and behavior
      if (align != null && alignId != null)
      {
        showPopup.append("hints[AdfRichPopup.HINT_ALIGN] = " + align +
                         ";");
        showPopup.append("hints[AdfRichPopup.HINT_ALIGN_ID] ='" + alignId +
                         "';");
      }
      showPopup.append("var popupObj=AdfPage.PAGE.findComponent('" +
                       popupId + "'); popupObj.show(hints);");
      service.addScript(FacesContext.getCurrentInstance(),
                        showPopup.toString());
    }
  }

  /**
   * Hides the specified popup.
   * @param popupId is the clientId of the popup to be hidden
   * clientId is derived from backing bean for the af:popup using getClientId method
   */
  public static void hidePopup(String popupId)
  {
    if (popupId != null)
    {
      ExtendedRenderKitService service =
        Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                    ExtendedRenderKitService.class);

      String hidePopup =
        "var popupObj=AdfPage.PAGE.findComponent('" + popupId +
        "'); popupObj.hide();";
      service.addScript(FacesContext.getCurrentInstance(), hidePopup);
    }
  }

  /*
  public static void hideDynamicGlobalPopup(ActionEvent actionEvent)
  {
    FacesContext fc = FacesContext.getCurrentInstance();

    ExpressionFactory ef = fc.getApplication().getExpressionFactory();
    String swapEmptyTaskFlow = "#{bindings.swapEmptyTaskFlow.execute}";
    MethodExpression me =
      ef.createMethodExpression(fc.getELContext(), swapEmptyTaskFlow,
                                String.class, new Class[]{ActionEvent.class});
    me.invoke(fc.getELContext(), new Object[] {actionEvent});

    if (actionEvent != null)
    {
      UIComponent source = (UIComponent)actionEvent.getSource();
      UIComponent popup = PatternsUtil.getParentByType(source,RichPopup.class);
      String popupId = popup.getClientId(fc);
      PatternsPublicUtil.hidePopup(popupId);     
    }
  }*/
}


