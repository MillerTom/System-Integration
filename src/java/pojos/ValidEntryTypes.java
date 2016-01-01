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
@Table(name = "valid_entry_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValidEntryTypes.findAll", query = "SELECT v FROM ValidEntryTypes v"),
    @NamedQuery(name = "ValidEntryTypes.findByVetId", query = "SELECT v FROM ValidEntryTypes v WHERE v.vetId = :vetId"),
    @NamedQuery(name = "ValidEntryTypes.findByVetDescription", query = "SELECT v FROM ValidEntryTypes v WHERE v.vetDescription = :vetDescription")})
public class ValidEntryTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "vet_id")
    private Integer vetId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "vet_description")
    private String vetDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veCategory")
    private Collection<ValidEntry> validEntryCollection;

    public ValidEntryTypes() {
    }

    public ValidEntryTypes(Integer vetId) {
        this.vetId = vetId;
    }

    public ValidEntryTypes(Integer vetId, String vetDescription) {
        this.vetId = vetId;
        this.vetDescription = vetDescription;
    }

    public Integer getVetId() {
        return vetId;
    }

    public void setVetId(Integer vetId) {
        this.vetId = vetId;
    }

    public String getVetDescription() {
        return vetDescription;
    }

    public void setVetDescription(String vetDescription) {
        this.vetDescription = vetDescription;
    }

    @XmlTransient
    public Collection<ValidEntry> getValidEntryCollection() {
        return validEntryCollection;
    }

    public void setValidEntryCollection(Collection<ValidEntry> validEntryCollection) {
        this.validEntryCollection = validEntryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vetId != null ? vetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValidEntryTypes)) {
            return false;
        }
        ValidEntryTypes other = (ValidEntryTypes) object;
        if ((this.vetId == null && other.vetId != null) || (this.vetId != null && !this.vetId.equals(other.vetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.ValidEntryTypes[ vetId=" + vetId + " ]";
    }
    
}
