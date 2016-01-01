/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tmiller
 */
@Entity
@Table(name = "valid_entry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValidEntry.findAll", query = "SELECT v FROM ValidEntry v"),
    @NamedQuery(name = "ValidEntry.findByVeId", query = "SELECT v FROM ValidEntry v WHERE v.veId = :veId"),
    @NamedQuery(name = "ValidEntry.findBetween", query = "SELECT v FROM ValidEntry v WHERE v.veId in(1,2)"),
    @NamedQuery(name = "ValidEntry.findByVeName", query = "SELECT v FROM ValidEntry v WHERE v.veName = :veName"),
    @NamedQuery(name = "ValidEntry.findByVeCategory", query = "SELECT v FROM ValidEntry v WHERE v.veCategory = :veCategory"),
    @NamedQuery(name = "ValidEntry.findByVeDescribtion", query = "SELECT v FROM ValidEntry v WHERE v.veDescribtion = :veDescribtion")})
public class ValidEntry implements Serializable {

    @JoinColumn(name = "ve_category", referencedColumnName = "vet_id")
    @ManyToOne(optional = false)
    private ValidEntryTypes veCategory;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ve_id")
    private Integer veId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ve_name")
    private String veName;
    @Size(max = 255)
    @Column(name = "ve_describtion")
    private String veDescribtion;
    @OneToMany(mappedBy = "responseStatus")
    private Collection<Files> filesCollection;
    @OneToMany(mappedBy = "requestStatus")
    private Collection<Files> filesCollection1;
    @OneToMany(mappedBy = "fileType")
    private Collection<Files> filesCollection2;
    @OneToMany(mappedBy = "status")
    private Collection<Reports> reportsCollection;
    @OneToMany(mappedBy = "reportType")
    private Collection<Reports> reportsCollection1;
    @OneToMany(mappedBy = "loadStatus")
    private Collection<Reports> reportsCollection2;
    @OneToMany(mappedBy = "type")
    private Collection<Users> usersCollection;

    public ValidEntry() {
    }

    public ValidEntry(Integer veId) {
        this.veId = veId;
    }

    public ValidEntry(Integer veId, String veName) {
        this.veId = veId;
        this.veName = veName;
    }

    public Integer getVeId() {
        return veId;
    }

    public void setVeId(Integer veId) {
        this.veId = veId;
    }

    public String getVeName() {
        return veName;
    }

    public void setVeName(String veName) {
        this.veName = veName;
    }

    public String getVeDescribtion() {
        return veDescribtion;
    }

    public void setVeDescribtion(String veDescribtion) {
        this.veDescribtion = veDescribtion;
    }

    @XmlTransient
    public Collection<Files> getFilesCollection() {
        return filesCollection;
    }

    public void setFilesCollection(Collection<Files> filesCollection) {
        this.filesCollection = filesCollection;
    }

    @XmlTransient
    public Collection<Files> getFilesCollection1() {
        return filesCollection1;
    }

    public void setFilesCollection1(Collection<Files> filesCollection1) {
        this.filesCollection1 = filesCollection1;
    }

    @XmlTransient
    public Collection<Files> getFilesCollection2() {
        return filesCollection2;
    }

    public void setFilesCollection2(Collection<Files> filesCollection2) {
        this.filesCollection2 = filesCollection2;
    }

    @XmlTransient
    public Collection<Reports> getReportsCollection() {
        return reportsCollection;
    }

    public void setReportsCollection(Collection<Reports> reportsCollection) {
        this.reportsCollection = reportsCollection;
    }

    @XmlTransient
    public Collection<Reports> getReportsCollection1() {
        return reportsCollection1;
    }

    public void setReportsCollection1(Collection<Reports> reportsCollection1) {
        this.reportsCollection1 = reportsCollection1;
    }
    
    @XmlTransient
    public Collection<Reports> getReportsCollection2() {
        return reportsCollection2;
    }

    public void setReportsCollection2(Collection<Reports> reportsCollection2) {
        this.reportsCollection2 = reportsCollection2;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veId != null ? veId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValidEntry)) {
            return false;
        }
        ValidEntry other = (ValidEntry) object;
        if ((this.veId == null && other.veId != null) || (this.veId != null && !this.veId.equals(other.veId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.ValidEntry[ veId=" + veId + " ]";
    }

    public ValidEntryTypes getVeCategory() {
        return veCategory;
    }

    public void setVeCategory(ValidEntryTypes veCategory) {
        this.veCategory = veCategory;
    }
}
