package intern.ecollabcad;

public class FileData {
    private int pos;
    private String fileName;
    private String fileType;


    public FileData(int pos, String fileName, String fileType) {
        this.pos = pos;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
