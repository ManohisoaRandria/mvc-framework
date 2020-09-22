/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author manohisoa
 */
public class UploadFile {

    private HttpServletRequest req;
    private String filePath;
    private int maxFileSize;
    private String fileName;
    private String fileExtension;
    private int maxMemSize = 4 * 1024/*0,004096mb*/;
    private FileItem file;
    private File loadedfile;
    private boolean isFilePresent = false;
    private String[] contentTypeTest;
    private List fileItems;

    public UploadFile(int maxFileSize, HttpServletRequest areq) {
        this.maxFileSize = maxFileSize;
        this.req = areq;
    }

    public void loadFile(List fileit) throws Exception {
        this.fileItems = fileit;
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(this.maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(Outils.getBaseUrl("tempFramework/", this.req)));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(this.maxFileSize);

        try {
            // Parse the request to get file items.
            //misy erreur eto ra mi exceed le file size

            // Process the uploaded file items
            Iterator i = this.fileItems.iterator();

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();

                if (!fi.isFormField()) {
                    String nomfichier = "";
                    if (fi.getName() != null && !fi.getName().trim().equals("") && fi.getName().split("[.]").length == 1) {
                        throw new Exception("no file extension or no file found");
                    }
                    if (fi.getName() != null && !fi.getName().trim().equals("") && this.getFileName() != null) {
                        nomfichier = this.getFileName().split("[.]")[0].trim() + "." + fi.getName().split("[.]")[1];
                    } else {
                        nomfichier = fi.getName();
                    }
                    this.fileName = nomfichier;
                    this.fileExtension = nomfichier.split("[.]")[1].trim();
                    // Get the uploaded file parameters
                    //String fieldName = fi.getFieldName();

                    long sizeInBytes = fi.getSize();
                    if (sizeInBytes != 0) {
                        this.isFilePresent = true;
                    }
                    if (this.getContentTypeTest() != null && this.isFilePresent == true) {
                        String contentType = fi.getContentType();
                        boolean test = testContentType(this.getContentTypeTest(), contentType);
                        if (!test) {
                            throw new Exception("forbidden file type");
                        }
                    }
                    if (nomfichier.lastIndexOf("\\") >= 0) {
                        this.loadedfile = new File(this.filePath + nomfichier.substring(nomfichier.lastIndexOf("\\")));

                    } else {
                        this.loadedfile = new File(this.filePath + nomfichier.substring(nomfichier.lastIndexOf("\\") + 1));
                    }
                    this.file = fi;
                    break;
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void setFileName(String fileName) {
        fileName = fileName.split("[.]")[0];
        fileName = fileName.trim() + "." + this.fileExtension.trim();

        if (fileName.lastIndexOf("\\") >= 0) {
            this.loadedfile = new File(this.filePath + fileName.substring(fileName.lastIndexOf("\\")));

        } else {
            this.loadedfile = new File(this.filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
        }
        this.fileName = fileName;
    }

    private boolean testContentType(String[] contentTypeTest, String contentType) {
        for (String string : contentTypeTest) {
            if (string.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFileAnyFilePresent() {
        return this.isFilePresent;
    }

    public String upload() throws Exception {
        String path = "";
        if (this.file != null && this.loadedfile != null && this.isFilePresent == true) {
            this.file.write(this.loadedfile);
            path = this.loadedfile.getAbsolutePath();
            return path;
        }
        return null;
    }

    public String getFilePath() {
        if (this.isFilePresent) {
            return this.filePath;
        }
        return null;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public int getMaxMemSize() {
        return maxMemSize;
    }

    public void setMaxMemSize(int maxMemSize) {
        this.maxMemSize = maxMemSize;
    }

    public HttpServletRequest getReq() {
        return this.req;
    }

    public void setReq(HttpServletRequest aReq) {
        this.req = aReq;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String[] getContentTypeTest() {
        return contentTypeTest;
    }

    public void setContentTypeTest(String[] contentTypeTest) {
        this.contentTypeTest = contentTypeTest;
    }

}
