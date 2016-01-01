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
@Table(name = "reports")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reports.findAll", query = "SELECT r FROM Reports r"),
    @NamedQuery(name = "Reports.findByRId", query = "SELECT r FROM Reports r WHERE r.rId = :rId"),
    @NamedQuery(name = "Reports.findByReportDate", query = "SELECT r FROM Reports r WHERE r.reportDate = :reportDate"),
    @NamedQuery(name = "Reports.findByReportTime", query = "SELECT r FROM Reports r WHERE r.reportTime = :reportTime"),
    @NamedQuery(name = "Reports.findByFileName", query = "SELECT r FROM Reports r WHERE r.fileName = :fileName")})
public class Reports implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "r_id")
    private Integer rId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "report_date")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    @Size(max = 12)
    @Column(name = "report_time")
    private String reportTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "file_name")
    private String fileName;
    @JoinColumn(name = "user", referencedColumnName = "user_name")
    @ManyToOne
    private Users user;
    @JoinColumn(name = "status", referencedColumnName = "ve_id")
    @ManyToOne
    private ValidEntry status;
    @JoinColumn(name = "report_type", referencedColumnName = "ve_id")
    @ManyToOne
    private ValidEntry reportType;
    @JoinColumn(name = "load_status", referencedColumnName = "ve_id")
    @ManyToOne
    private ValidEntry loadStatus;

    public Reports() {
    }

    public Reports(Integer rId) {
        this.rId = rId;
    }

    public Reports(Integer rId, Date reportDate, String fileName) {
        this.rId = rId;
        this.reportDate = reportDate;
        this.fileName = fileName;
    }

    public Integer getRId() {
        return rId;
    }

    public void setRId(Integer rId) {
        this.rId = rId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public ValidEntry getStatus() {
        return status;
    }

    public void setStatus(ValidEntry status) {
        this.status = status;
    }

    public ValidEntry getReportType() {
        return reportType;
    }

    public void setReportType(ValidEntry reportType) {
        this.reportType = reportType;
    }
    
    public ValidEntry getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(ValidEntry loadStatus) {
        this.loadStatus = loadStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rId != null ? rId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reports)) {
            return false;
        }
        Reports other = (Reports) object;
        if ((this.rId == null && other.rId != null) || (this.rId != null && !this.rId.equals(other.rId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Reports[ rId=" + rId + " ]";
    }
    
}
