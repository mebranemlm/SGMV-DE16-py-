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
@Table(name = "elemento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Elemento.findAll", query = "SELECT e FROM Elemento e"),
    @NamedQuery(name = "Elemento.findByElemId", query = "SELECT e FROM Elemento e WHERE e.elemId = :elemId"),
    @NamedQuery(name = "Elemento.findByElemDesc", query = "SELECT e FROM Elemento e WHERE e.elemDesc = :elemDesc"),
    @NamedQuery(name = "Elemento.findByElemEsta", query = "SELECT e FROM Elemento e WHERE e.elemEsta = :elemEsta"),
    @NamedQuery(name = "Elemento.findByAudiFreg", query = "SELECT e FROM Elemento e WHERE e.audiFreg = :audiFreg"),
    @NamedQuery(name = "Elemento.findByAudiFact", query = "SELECT e FROM Elemento e WHERE e.audiFact = :audiFact")})
public class Elemento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "elem_id")
    private Integer elemId;
    @Basic(optional = false)
    @Column(name = "elem_desc")
    private String elemDesc;
    @Basic(optional = false)
    @Column(name = "elem_esta")
    private Character elemEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "elemId")
    private Collection<TramoCodigoElemento> tramoCodigoElementoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "elemId")
    private Collection<ControlActividadDetalle> controlActividadDetalleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "elemId")
    private Collection<CodigoElemento> codigoElementoCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;

    public Elemento() {
    }

    public Elemento(Integer elemId) {
        this.elemId = elemId;
    }

    public Elemento(Integer elemId, String elemDesc, Character elemEsta, Date audiFreg, Date audiFact) {
        this.elemId = elemId;
        this.elemDesc = elemDesc;
        this.elemEsta = elemEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getElemId() {
        return elemId;
    }

    public void setElemId(Integer elemId) {
        this.elemId = elemId;
    }

    public String getElemDesc() {
        return elemDesc;
    }

    public void setElemDesc(String elemDesc) {
        this.elemDesc = elemDesc;
    }

    public Character getElemEsta() {
        return elemEsta;
    }

    public void setElemEsta(Character elemEsta) {
        this.elemEsta = elemEsta;
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

    @XmlTransient
    public Collection<CodigoElemento> getCodigoElementoCollection() {
        return codigoElementoCollection;
    }

    public void setCodigoElementoCollection(Collection<CodigoElemento> codigoElementoCollection) {
        this.codigoElementoCollection = codigoElementoCollection;
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
        hash += (elemId != null ? elemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Elemento)) {
            return false;
        }
        Elemento other = (Elemento) object;
        if ((this.elemId == null && other.elemId != null) || (this.elemId != null && !this.elemId.equals(other.elemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Elemento[ elemId=" + elemId + " ]";
    }
    
}
