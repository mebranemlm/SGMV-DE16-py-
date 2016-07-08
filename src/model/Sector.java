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
@Table(name = "sector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sector.findAll", query = "SELECT s FROM Sector s"),
    @NamedQuery(name = "Sector.findBySectId", query = "SELECT s FROM Sector s WHERE s.sectId = :sectId"),
    @NamedQuery(name = "Sector.findBySectNomb", query = "SELECT s FROM Sector s WHERE s.sectNomb = :sectNomb"),
    @NamedQuery(name = "Sector.findBySectEsta", query = "SELECT s FROM Sector s WHERE s.sectEsta = :sectEsta"),
    @NamedQuery(name = "Sector.findByAudiFreg", query = "SELECT s FROM Sector s WHERE s.audiFreg = :audiFreg"),
    @NamedQuery(name = "Sector.findByAudiFact", query = "SELECT s FROM Sector s WHERE s.audiFact = :audiFact")})
public class Sector implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sect_id")
    private Integer sectId;
    @Basic(optional = false)
    @Column(name = "sect_nomb")
    private String sectNomb;
    @Basic(optional = false)
    @Column(name = "sect_esta")
    private String sectEsta;
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
    @JoinColumn(name = "tram_id", referencedColumnName = "tram_id")
    @ManyToOne(optional = false)
    private Tramo tramId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sectId")
    private Collection<ControlActividadDetalle> controlActividadDetalleCollection;

    public Sector() {
    }

    public Sector(Integer sectId) {
        this.sectId = sectId;
    }

    public Sector(Integer sectId, String sectNomb, String sectEsta, Date audiFreg, Date audiFact) {
        this.sectId = sectId;
        this.sectNomb = sectNomb;
        this.sectEsta = sectEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getSectId() {
        return sectId;
    }

    public void setSectId(Integer sectId) {
        this.sectId = sectId;
    }

    public String getSectNomb() {
        return sectNomb;
    }

    public void setSectNomb(String sectNomb) {
        this.sectNomb = sectNomb;
    }

    public String getSectEsta() {
        return sectEsta;
    }

    public void setSectEsta(String sectEsta) {
        this.sectEsta = sectEsta;
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

    public Tramo getTramId() {
        return tramId;
    }

    public void setTramId(Tramo tramId) {
        this.tramId = tramId;
    }

    @XmlTransient
    public Collection<ControlActividadDetalle> getControlActividadDetalleCollection() {
        return controlActividadDetalleCollection;
    }

    public void setControlActividadDetalleCollection(Collection<ControlActividadDetalle> controlActividadDetalleCollection) {
        this.controlActividadDetalleCollection = controlActividadDetalleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sectId != null ? sectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sector)) {
            return false;
        }
        Sector other = (Sector) object;
        if ((this.sectId == null && other.sectId != null) || (this.sectId != null && !this.sectId.equals(other.sectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Sector[ sectId=" + sectId + " ]";
    }
    
}
