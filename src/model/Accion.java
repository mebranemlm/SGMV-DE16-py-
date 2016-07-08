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
@Table(name = "accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accion.findAll", query = "SELECT a FROM Accion a"),
    @NamedQuery(name = "Accion.findByAcciId", query = "SELECT a FROM Accion a WHERE a.acciId = :acciId"),
    @NamedQuery(name = "Accion.findByAcciDesc", query = "SELECT a FROM Accion a WHERE a.acciDesc = :acciDesc"),
    @NamedQuery(name = "Accion.findByAcciEsta", query = "SELECT a FROM Accion a WHERE a.acciEsta = :acciEsta"),
    @NamedQuery(name = "Accion.findByAudiFreg", query = "SELECT a FROM Accion a WHERE a.audiFreg = :audiFreg"),
    @NamedQuery(name = "Accion.findByAudiFact", query = "SELECT a FROM Accion a WHERE a.audiFact = :audiFact")})
public class Accion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acci_id")
    private Integer acciId;
    @Basic(optional = false)
    @Column(name = "acci_desc")
    private String acciDesc;
    @Basic(optional = false)
    @Column(name = "acci_esta")
    private Character acciEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acciId")
    private Collection<PermisoAccion> permisoAccionCollection;

    public Accion() {
    }

    public Accion(Integer acciId) {
        this.acciId = acciId;
    }

    public Accion(Integer acciId, String acciDesc, Character acciEsta, Date audiFreg, Date audiFact) {
        this.acciId = acciId;
        this.acciDesc = acciDesc;
        this.acciEsta = acciEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getAcciId() {
        return acciId;
    }

    public void setAcciId(Integer acciId) {
        this.acciId = acciId;
    }

    public String getAcciDesc() {
        return acciDesc;
    }

    public void setAcciDesc(String acciDesc) {
        this.acciDesc = acciDesc;
    }

    public Character getAcciEsta() {
        return acciEsta;
    }

    public void setAcciEsta(Character acciEsta) {
        this.acciEsta = acciEsta;
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

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Collection<PermisoAccion> getPermisoAccionCollection() {
        return permisoAccionCollection;
    }

    public void setPermisoAccionCollection(Collection<PermisoAccion> permisoAccionCollection) {
        this.permisoAccionCollection = permisoAccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acciId != null ? acciId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accion)) {
            return false;
        }
        Accion other = (Accion) object;
        if ((this.acciId == null && other.acciId != null) || (this.acciId != null && !this.acciId.equals(other.acciId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Accion[ acciId=" + acciId + " ]";
    }
    
}
