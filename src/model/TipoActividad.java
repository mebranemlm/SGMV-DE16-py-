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
@Table(name = "tipo_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoActividad.findAll", query = "SELECT t FROM TipoActividad t"),
    @NamedQuery(name = "TipoActividad.findByTiacId", query = "SELECT t FROM TipoActividad t WHERE t.tiacId = :tiacId"),
    @NamedQuery(name = "TipoActividad.findByTiacDesc", query = "SELECT t FROM TipoActividad t WHERE t.tiacDesc = :tiacDesc"),
    @NamedQuery(name = "TipoActividad.findByTiacEsta", query = "SELECT t FROM TipoActividad t WHERE t.tiacEsta = :tiacEsta"),
    @NamedQuery(name = "TipoActividad.findByAudiFreg", query = "SELECT t FROM TipoActividad t WHERE t.audiFreg = :audiFreg"),
    @NamedQuery(name = "TipoActividad.findByAudiFact", query = "SELECT t FROM TipoActividad t WHERE t.audiFact = :audiFact")})
public class TipoActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tiac_id")
    private Integer tiacId;
    @Basic(optional = false)
    @Column(name = "tiac_desc")
    private String tiacDesc;
    @Basic(optional = false)
    @Column(name = "tiac_esta")
    private Character tiacEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiacId")
    private Collection<Actividad> actividadCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;

    public TipoActividad() {
    }

    public TipoActividad(Integer tiacId) {
        this.tiacId = tiacId;
    }

    public TipoActividad(Integer tiacId, String tiacDesc, Character tiacEsta, Date audiFreg, Date audiFact) {
        this.tiacId = tiacId;
        this.tiacDesc = tiacDesc;
        this.tiacEsta = tiacEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getTiacId() {
        return tiacId;
    }

    public void setTiacId(Integer tiacId) {
        this.tiacId = tiacId;
    }

    public String getTiacDesc() {
        return tiacDesc;
    }

    public void setTiacDesc(String tiacDesc) {
        this.tiacDesc = tiacDesc;
    }

    public Character getTiacEsta() {
        return tiacEsta;
    }

    public void setTiacEsta(Character tiacEsta) {
        this.tiacEsta = tiacEsta;
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
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
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
        hash += (tiacId != null ? tiacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoActividad)) {
            return false;
        }
        TipoActividad other = (TipoActividad) object;
        if ((this.tiacId == null && other.tiacId != null) || (this.tiacId != null && !this.tiacId.equals(other.tiacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TipoActividad[ tiacId=" + tiacId + " ]";
    }
    
}
