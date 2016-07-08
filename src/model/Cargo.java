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
@Table(name = "cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c"),
    @NamedQuery(name = "Cargo.findByCargId", query = "SELECT c FROM Cargo c WHERE c.cargId = :cargId"),
    @NamedQuery(name = "Cargo.findByCargNomb", query = "SELECT c FROM Cargo c WHERE c.cargNomb = :cargNomb"),
    @NamedQuery(name = "Cargo.findByCargEsta", query = "SELECT c FROM Cargo c WHERE c.cargEsta = :cargEsta"),
    @NamedQuery(name = "Cargo.findByAudiFreg", query = "SELECT c FROM Cargo c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "Cargo.findByAudiFact", query = "SELECT c FROM Cargo c WHERE c.audiFact = :audiFact")})
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "carg_id")
    private Integer cargId;
    @Basic(optional = false)
    @Column(name = "carg_nomb")
    private String cargNomb;
    @Basic(optional = false)
    @Column(name = "carg_esta")
    private Character cargEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargId")
    private Collection<CargoPermisoAccion> cargoPermisoAccionCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargId")
    private Collection<Personal> personalCollection;

    public Cargo() {
    }

    public Cargo(Integer cargId) {
        this.cargId = cargId;
    }

    public Cargo(Integer cargId, String cargNomb, Character cargEsta, Date audiFreg, Date audiFact) {
        this.cargId = cargId;
        this.cargNomb = cargNomb;
        this.cargEsta = cargEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getCargId() {
        return cargId;
    }

    public void setCargId(Integer cargId) {
        this.cargId = cargId;
    }

    public String getCargNomb() {
        return cargNomb;
    }

    public void setCargNomb(String cargNomb) {
        this.cargNomb = cargNomb;
    }

    public Character getCargEsta() {
        return cargEsta;
    }

    public void setCargEsta(Character cargEsta) {
        this.cargEsta = cargEsta;
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

    @XmlTransient
    public Collection<Personal> getPersonalCollection() {
        return personalCollection;
    }

    public void setPersonalCollection(Collection<Personal> personalCollection) {
        this.personalCollection = personalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargId != null ? cargId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.cargId == null && other.cargId != null) || (this.cargId != null && !this.cargId.equals(other.cargId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cargo[ cargId=" + cargId + " ]";
    }
    
}
