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
@Table(name = "permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p"),
    @NamedQuery(name = "Permiso.findByPermId", query = "SELECT p FROM Permiso p WHERE p.permId = :permId"),
    @NamedQuery(name = "Permiso.findByPermDesc", query = "SELECT p FROM Permiso p WHERE p.permDesc = :permDesc"),
    @NamedQuery(name = "Permiso.findByPermEsta", query = "SELECT p FROM Permiso p WHERE p.permEsta = :permEsta"),
    @NamedQuery(name = "Permiso.findByAudiFreg", query = "SELECT p FROM Permiso p WHERE p.audiFreg = :audiFreg"),
    @NamedQuery(name = "Permiso.findByAudiFact", query = "SELECT p FROM Permiso p WHERE p.audiFact = :audiFact")})
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "perm_id")
    private Integer permId;
    @Basic(optional = false)
    @Column(name = "perm_desc")
    private String permDesc;
    @Basic(optional = false)
    @Column(name = "perm_esta")
    private Character permEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permId")
    private Collection<PermisoAccion> permisoAccionCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;

    public Permiso() {
    }

    public Permiso(Integer permId) {
        this.permId = permId;
    }

    public Permiso(Integer permId, String permDesc, Character permEsta, Date audiFreg, Date audiFact) {
        this.permId = permId;
        this.permDesc = permDesc;
        this.permEsta = permEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public String getPermDesc() {
        return permDesc;
    }

    public void setPermDesc(String permDesc) {
        this.permDesc = permDesc;
    }

    public Character getPermEsta() {
        return permEsta;
    }

    public void setPermEsta(Character permEsta) {
        this.permEsta = permEsta;
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
    public Collection<PermisoAccion> getPermisoAccionCollection() {
        return permisoAccionCollection;
    }

    public void setPermisoAccionCollection(Collection<PermisoAccion> permisoAccionCollection) {
        this.permisoAccionCollection = permisoAccionCollection;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permId != null ? permId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.permId == null && other.permId != null) || (this.permId != null && !this.permId.equals(other.permId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Permiso[ permId=" + permId + " ]";
    }
    
}
