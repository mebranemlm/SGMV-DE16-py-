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
@Table(name = "codigo_elemento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodigoElemento.findAll", query = "SELECT c FROM CodigoElemento c"),
    @NamedQuery(name = "CodigoElemento.findByCoelId", query = "SELECT c FROM CodigoElemento c WHERE c.coelId = :coelId"),
    @NamedQuery(name = "CodigoElemento.findByCoelCodi", query = "SELECT c FROM CodigoElemento c WHERE c.coelCodi = :coelCodi"),
    @NamedQuery(name = "CodigoElemento.findByCoelEsta", query = "SELECT c FROM CodigoElemento c WHERE c.coelEsta = :coelEsta"),
    @NamedQuery(name = "CodigoElemento.findByAudiFreg", query = "SELECT c FROM CodigoElemento c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "CodigoElemento.findByAudiFact", query = "SELECT c FROM CodigoElemento c WHERE c.audiFact = :audiFact")})
public class CodigoElemento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coel_id")
    private Integer coelId;
    @Basic(optional = false)
    @Column(name = "coel_codi")
    private String coelCodi;
    @Basic(optional = false)
    @Column(name = "coel_esta")
    private Character coelEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(mappedBy = "coelId")
    private Collection<TramoCodigoElemento> tramoCodigoElementoCollection;
    @OneToMany(mappedBy = "coelId")
    private Collection<ControlActividadDetalle> controlActividadDetalleCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "elem_id", referencedColumnName = "elem_id")
    @ManyToOne(optional = false)
    private Elemento elemId;

    public CodigoElemento() {
    }

    public CodigoElemento(Integer coelId) {
        this.coelId = coelId;
    }

    public CodigoElemento(Integer coelId, String coelCodi, Character coelEsta, Date audiFreg, Date audiFact) {
        this.coelId = coelId;
        this.coelCodi = coelCodi;
        this.coelEsta = coelEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getCoelId() {
        return coelId;
    }

    public void setCoelId(Integer coelId) {
        this.coelId = coelId;
    }

    public String getCoelCodi() {
        return coelCodi;
    }

    public void setCoelCodi(String coelCodi) {
        this.coelCodi = coelCodi;
    }

    public Character getCoelEsta() {
        return coelEsta;
    }

    public void setCoelEsta(Character coelEsta) {
        this.coelEsta = coelEsta;
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
    public Collection<TramoCodigoElemento> getTramoCodigoElementoCollection() {
        return tramoCodigoElementoCollection;
    }

    public void setTramoCodigoElementoCollection(Collection<TramoCodigoElemento> tramoCodigoElementoCollection) {
        this.tramoCodigoElementoCollection = tramoCodigoElementoCollection;
    }

    @XmlTransient
    public Collection<ControlActividadDetalle> getControlActividadDetalleCollection() {
        return controlActividadDetalleCollection;
    }

    public void setControlActividadDetalleCollection(Collection<ControlActividadDetalle> controlActividadDetalleCollection) {
        this.controlActividadDetalleCollection = controlActividadDetalleCollection;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    public Elemento getElemId() {
        return elemId;
    }

    public void setElemId(Elemento elemId) {
        this.elemId = elemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coelId != null ? coelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoElemento)) {
            return false;
        }
        CodigoElemento other = (CodigoElemento) object;
        if ((this.coelId == null && other.coelId != null) || (this.coelId != null && !this.coelId.equals(other.coelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CodigoElemento[ coelId=" + coelId + " ]";
    }
    
}
