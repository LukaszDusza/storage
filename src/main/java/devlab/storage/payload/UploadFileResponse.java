package devlab.storage.payload;


import java.util.Date;


public class UploadFileResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileDeleteUri;
    private String fileType;
    private long size;
  //  private Date date;
  //  private String time;


    public UploadFileResponse(String fileName, String fileDownloadUri, String fileDeleteUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileDeleteUri = fileDeleteUri;
        this.fileType = fileType;
        this.size = size;
    }

    public UploadFileResponse() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileDeleteUri() {
        return fileDeleteUri;
    }

    public void setFileDeleteUri(String fileDeleteUri) {
        this.fileDeleteUri = fileDeleteUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
