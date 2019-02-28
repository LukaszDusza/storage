package devlab.storage.localStorage.model;


public class LocalFile {

    private String name;
    private String creationTime;
    private String lastModified;
    private Long size;
    private String downloadUri;
    private String fileDeleteUri;
    private String fileType;

    public LocalFile(String name, String creationTime, String lastModified, Long size, String downloadUri, String fileDeleteUri, String fileType) {
        this.name = name;
        this.creationTime = creationTime;
        this.lastModified = lastModified;
        this.size = size;
        this.downloadUri = downloadUri;
        this.fileDeleteUri = fileDeleteUri;
        this.fileType = fileType;
    }

    public LocalFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
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
}
