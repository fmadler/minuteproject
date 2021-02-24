package net.sf.minuteproject.adf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class FileUploadBackingBean {

    public void fileUploaded(ValueChangeEvent event) {
        InputStream in;
        FileOutputStream out;
        
        // Set fileUPloadLoc to "NIF.FILE_UPLOADS_DIR" context init parameter
        //String fileUploadLoc = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("NIF.FILE_UPLOADS_DIR");
        
        //java.io.tmpdir The directory in which java should create temporary files
        String fileUploadLoc = System.getProperty("java.io.tmpdir");
        
        if (fileUploadLoc == null) {
          fileUploadLoc = "/tmp/nif_fileuploads";
        }
        
        //get svrId and append to file upload location
        Integer svrId = (Integer)JSFUtils.getManagedBeanValue("userState.currentSvrId");
        fileUploadLoc += "/sr_" + svrId + "_uploadedfiles";
        
        // Create upload directory if it does not exists.
        boolean exists = (new File(fileUploadLoc)).exists();
        if (!exists) {
            (new File(fileUploadLoc)).mkdirs();
        }
        
        UploadedFile file = (UploadedFile)event.getNewValue();
        
        if (file != null && file.getLength()>0) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message =
                        new FacesMessage(//JSFUtils.getStringFromBundle("srmain.srfileupload.success")+" "+
                                       file.getFilename() + " (" +
                                       file.getLength() +
                                       " bytes)");
            context.addMessage(event.getComponent().getClientId(context),
                               message);
     
            try {
                out =
                    new FileOutputStream(fileUploadLoc + "/" + file.getFilename());
                in = file.getInputStream();
                
                for (int bytes = 0; bytes < file.getLength(); bytes++) {
                  out.write(in.read());
                }  
                  
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // need to check for null value here as otherwise closing
            // the dialog after a failed upload attempt will lead to
            // a nullpointer exception
            String filename = file != null ? file.getFilename() : null;
            String byteLength = file !=null ? "" + file.getLength() : "0";
            
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message =
            new FacesMessage(FacesMessage.SEVERITY_WARN, /*JSFUtils.getStringFromBundle("srmain.srfileupload.error") +*/ " " + 
                            filename + " (" + byteLength + " bytes)", null);
            context.addMessage(event.getComponent().getClientId(context),message);
        }
        
    }
}
