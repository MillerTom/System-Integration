/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tmiller
 */
@Entity
@Table(name = "files")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Files.findAll", query = "SELECT f FROM Files f"),
    @NamedQuery(name = "Files.findByFId", query = "SELECT f FROM Files f WHERE f.fId = :fId"),
    @NamedQuery(name = "Files.findByFileName", query = "SELECT f FROM Files f WHERE f.fileName = :fileName"),
    @NamedQuery(name = "Files.findByFilePath", query = "SELECT f FROM Files f WHERE f.filePath = :filePath"),
    @NamedQuery(name = "Files.findByFileDate", query = "SELECT f FROM Files f WHERE f.fileDate = :fileDate"),
    @NamedQuery(name = "Files.findByFileTime", query = "SELECT f FROM Files f WHERE f.fileTime = :fileTime")})
public class Files implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "f_id")
    private Integer fId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "file_name")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "file_date")
    @Temporal(TemporalType.DATE)
    private Date fileDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "file_time")
    private String fileTime;
    @JoinColumn(name = "response_status", referencedColumnName = "ve_id")
    @ManyToOne
    private ValidEntry responseStatus;
    @JoinColumn(name = "request_status", referencedColumnName = "ve_id")
    @ManyToOne
    private ValidEntry requestStatus;
    @JoinColumn(name = "file_type", referencedColumnName = "ve_id")
    @ManyToOne
    private ValidEntry fileType;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "download_file_path")
    private String downloadFilePath;

    public Files() {
        
    }

    
    public Files(Integer fId) {
        this.fId = fId;
    }

    public Files(Integer fId, String fileName, Date fileDate, String fileTime) {
        this.fId = fId;
        this.fileName = fileName;
        this.fileDate = fileDate;
        this.fileTime = fileTime;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    public ValidEntry getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ValidEntry responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ValidEntry getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(ValidEntry requestStatus) {
        this.requestStatus = requestStatus;
    }

    public ValidEntry getFileType() {
        return fileType;
    }

    public void setFileType(ValidEntry fileType) {
        this.fileType = fileType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fId != null ? fId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Files)) {
            return false;
        }
        Files other = (Files) object;
        if ((this.fId == null && other.fId != null) || (this.fId != null && !this.fId.equals(other.fId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Files[ fId=" + fId + " ]";
    }
    
}
