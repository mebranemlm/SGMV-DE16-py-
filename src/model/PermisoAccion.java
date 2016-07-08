/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "permiso_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PermisoAccion.findAll", query = "SELECT p FROM PermisoAccion p"),
    @NamedQuery(name = "PermisoAccion.findByPermAcciId", query = "SELECT p FROM PermisoAccion p WHERE p.permAcciId = :permAcciId"),
    @NamedQuery(name = "PermisoAccion.findByPermAcciEsta", query = "SELECT p FROM PermisoAccion p WHERE p.permAcciEsta = :permAcciEsta"),
    @NamedQuery(name = "PermisoAccion.findByAudiFreg", query = "SELECT p FROM PermisoAccion p WHERE p.audiFreg = :audiFreg"),
    @NamedQuery(name = "PermisoAccion.findByAudiFact", query = "SELECT p FROM PermisoAccion p WHERE p.audiFact = :audiFact")})
public class PermisoAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "perm_acci_id")
    private Integer permAcciId;
    @Basic(optional = false)
    @Column(name = "perm_acci_esta")
    private Character permAcciEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permAcciId")
    private Collection<CargoPermisoAccion> cargoPermisoAccionCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "perm_id", referencedColumnName = "perm_id")
    @ManyToOne(optional = false)
    private Permiso permId;
    @JoinColumn(name = "acci_id", referencedColumnName = "acci_id")
    @ManyToOne(optional = false)
    private Accion acciId;

    public PermisoAccion() {
    }

    public PermisoAccion(Integer permAcciId) {
        this.permAcciId = permAcciId;
    }

    public PermisoAccion(Integer permAcciId, Character permAcciEsta, Date audiFreg, Date audiFact) {
        this.permAcciId = permAcciId;
        this.permAcciEsta = permAcciEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getPermAcciId() {
        return permAcciId;
    }

    public void setPermAcciId(Integer permAcciId) {
        this.permAcciId = permAcciId;
    }

    public Character getPermAcciEsta() {
        return permAcciEsta;
    }

    public void setPermAcciEsta(Character permAcciEsta) {
        this.permAcciEsta = permAcciEsta;
    }

    public Date getAudiFreg() {
        return audiFreg;
    }

    public void setAudiFreg(Date audiFreg) {
        this.audiFreg = audiFreg;
    }

    public Date getAudiFact() {
        return audiFact;
    }

    public void setAudiFact(Date audiFact) {
        this.audiFact = audiFact;
    }

    @XmlTransient
    public Collection<CargoPermisoAccion> getCargoPermisoAccionCollection() {
        return cargoPermisoAccionCollection;
    }

    public void setCargoPermisoAccionCollection(Collection<CargoPermisoAccion> cargoPermisoAccionCollection) {
        this.cargoPermisoAccionCollection = cargoPermisoAccionCollection;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    public Permiso getPermId() {
        return permId;
    }

    public void setPermId(Permiso permId) {
        this.permId = permId;
    }

    public Accion getAcciId() {
        return acciId;
    }

    public void setAcciId(Accion acciId) {
        this.acciId = acciId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permAcciId != null ? permAcciId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermisoAccion)) {
            return false;
        }
        PermisoAccion other = (PermisoAccion) object;
        if ((this.permAcciId == null && other.permAcciId != null) || (this.permAcciId != null && !this.permAcciId.equals(other.permAcciId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PermisoAccion[ permAcciId=" + permAcciId + " ]";
    }
    
}
