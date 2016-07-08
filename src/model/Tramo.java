/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "tramo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tramo.findAll", query = "SELECT t FROM Tramo t"),
    @NamedQuery(name = "Tramo.findByTramId", query = "SELECT t FROM Tramo t WHERE t.tramId = :tramId"),
    @NamedQuery(name = "Tramo.findByTramNomb", query = "SELECT t FROM Tramo t WHERE t.tramNomb = :tramNomb"),
    @NamedQuery(name = "Tramo.findByTramCarr", query = "SELECT t FROM Tramo t WHERE t.tramCarr = :tramCarr"),
    @NamedQuery(name = "Tramo.findByTramKini", query = "SELECT t FROM Tramo t WHERE t.tramKini = :tramKini"),
    @NamedQuery(name = "Tramo.findByTramKfin", query = "SELECT t FROM Tramo t WHERE t.tramKfin = :tramKfin"),
    @NamedQuery(name = "Tramo.findByTramKtot", query = "SELECT t FROM Tramo t WHERE t.tramKtot = :tramKtot"),
    @NamedQuery(name = "Tramo.findByTramAper", query = "SELECT t FROM Tramo t WHERE t.tramAper = :tramAper"),
    @NamedQuery(name = "Tramo.findByTramEsta", query = "SELECT t FROM Tramo t WHERE t.tramEsta = :tramEsta"),
    @NamedQuery(name = "Tramo.findByAudiFreg", query = "SELECT t FROM Tramo t WHERE t.audiFreg = :audiFreg"),
    @NamedQuery(name = "Tramo.findByAudiFact", query = "SELECT t FROM Tramo t WHERE t.audiFact = :audiFact")})
public class Tramo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tram_id")
    private Integer tramId;
    @Basic(optional = false)
    @Column(name = "tram_nomb")
    private String tramNomb;
    @Basic(optional = false)
    @Column(name = "tram_carr")
    private String tramCarr;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "tram_kini")
    private BigDecimal tramKini;
    @Basic(optional = false)
    @Column(name = "tram_kfin")
    private BigDecimal tramKfin;
    @Basic(optional = false)
    @Column(name = "tram_ktot")
    private BigDecimal tramKtot;
    @Column(name = "tram_aper")
    private String tramAper;
    @Basic(optional = false)
    @Column(name = "tram_esta")
    private Character tramEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tramId")
    private Collection<Sector> sectorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tramId")
    private Collection<TramoCodigoElemento> tramoCodigoElementoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tramId")
    private Collection<ContratoTramo> contratoTramoCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;

    public Tramo() {
    }

    public Tramo(Integer tramId) {
        this.tramId = tramId;
    }

    public Tramo(Integer tramId, String tramNomb, String tramCarr, BigDecimal tramKini, BigDecimal tramKfin, BigDecimal tramKtot, Character tramEsta, Date audiFreg, Date audiFact) {
        this.tramId = tramId;
        this.tramNomb = tramNomb;
        this.tramCarr = tramCarr;
        this.tramKini = tramKini;
        this.tramKfin = tramKfin;
        this.tramKtot = tramKtot;
        this.tramEsta = tramEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getTramId() {
        return tramId;
    }

    public void setTramId(Integer tramId) {
        this.tramId = tramId;
    }

    public String getTramNomb() {
        return tramNomb;
    }

    public void setTramNomb(String tramNomb) {
        this.tramNomb = tramNomb;
    }

    public String getTramCarr() {
        return tramCarr;
    }

    public void setTramCarr(String tramCarr) {
        this.tramCarr = tramCarr;
    }

    public BigDecimal getTramKini() {
        return tramKini;
    }

    public void setTramKini(BigDecimal tramKini) {
        this.tramKini = tramKini;
    }

    public BigDecimal getTramKfin() {
        return tramKfin;
    }

    public void setTramKfin(BigDecimal tramKfin) {
        this.tramKfin = tramKfin;
    }

    public BigDecimal getTramKtot() {
        return tramKtot;
    }

    public void setTramKtot(BigDecimal tramKtot) {
        this.tramKtot = tramKtot;
    }

    public String getTramAper() {
        return tramAper;
    }

    public void setTramAper(String tramAper) {
        this.tramAper = tramAper;
    }

    public Character getTramEsta() {
        return tramEsta;
    }

    public void setTramEsta(Character tramEsta) {
        this.tramEsta = tramEsta;
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
    public Collection<Sector> getSectorCollection() {
        return sectorCollection;
    }

    public void setSectorCollection(Collection<Sector> sectorCollection) {
        this.sectorCollection = sectorCollection;
    }

    @XmlTransient
    public Collection<TramoCodigoElemento> getTramoCodigoElementoCollection() {
        return tramoCodigoElementoCollection;
    }

    public void setTramoCodigoElementoCollection(Collection<TramoCodigoElemento> tramoCodigoElementoCollection) {
        this.tramoCodigoElementoCollection = tramoCodigoElementoCollection;
    }

    @XmlTransient
    public Collection<ContratoTramo> getContratoTramoCollection() {
        return contratoTramoCollection;
    }

    public void setContratoTramoCollection(Collection<ContratoTramo> contratoTramoCollection) {
        this.contratoTramoCollection = contratoTramoCollection;
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
        hash += (tramId != null ? tramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tramo)) {
            return false;
        }
        Tramo other = (Tramo) object;
        if ((this.tramId == null && other.tramId != null) || (this.tramId != null && !this.tramId.equals(other.tramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tramo[ tramId=" + tramId + " ]";
    }
    
}
