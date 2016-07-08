/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "tramo_codigo_elemento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramoCodigoElemento.findAll", query = "SELECT t FROM TramoCodigoElemento t"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelId", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelId = :tramCoelId"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelKini", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelKini = :tramCoelKini"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelKfin", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelKfin = :tramCoelKfin"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelAlto", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelAlto = :tramCoelAlto"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelLado", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelLado = :tramCoelLado"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelLong", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelLong = :tramCoelLong"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelAnch", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelAnch = :tramCoelAnch"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelCant", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelCant = :tramCoelCant"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelStot", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelStot = :tramCoelStot"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelObsv", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelObsv = :tramCoelObsv"),
    @NamedQuery(name = "TramoCodigoElemento.findByTramCoelEsta", query = "SELECT t FROM TramoCodigoElemento t WHERE t.tramCoelEsta = :tramCoelEsta"),
    @NamedQuery(name = "TramoCodigoElemento.findByAudiFreg", query = "SELECT t FROM TramoCodigoElemento t WHERE t.audiFreg = :audiFreg"),
    @NamedQuery(name = "TramoCodigoElemento.findByAudiFact", query = "SELECT t FROM TramoCodigoElemento t WHERE t.audiFact = :audiFact")})
public class TramoCodigoElemento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tram_coel_id")
    private Integer tramCoelId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tram_coel_kini")
    private BigDecimal tramCoelKini;
    @Column(name = "tram_coel_kfin")
    private BigDecimal tramCoelKfin;
    @Column(name = "tram_coel_alto")
    private BigDecimal tramCoelAlto;
    @Column(name = "tram_coel_lado")
    private Character tramCoelLado;
    @Column(name = "tram_coel_long")
    private BigDecimal tramCoelLong;
    @Column(name = "tram_coel_anch")
    private BigDecimal tramCoelAnch;
    @Column(name = "tram_coel_cant")
    private BigDecimal tramCoelCant;
    @Column(name = "tram_coel_stot")
    private BigDecimal tramCoelStot;
    @Column(name = "tram_coel_obsv")
    private String tramCoelObsv;
    @Basic(optional = false)
    @Column(name = "tram_coel_esta")
    private Character tramCoelEsta;
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
    @JoinColumn(name = "elem_id", referencedColumnName = "elem_id")
    @ManyToOne(optional = false)
    private Elemento elemId;
    @JoinColumn(name = "coel_id", referencedColumnName = "coel_id")
    @ManyToOne
    private CodigoElemento coelId;

    public TramoCodigoElemento() {
    }

    public TramoCodigoElemento(Integer tramCoelId) {
        this.tramCoelId = tramCoelId;
    }

    public TramoCodigoElemento(Integer tramCoelId, Character tramCoelEsta, Date audiFreg, Date audiFact) {
        this.tramCoelId = tramCoelId;
        this.tramCoelEsta = tramCoelEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getTramCoelId() {
        return tramCoelId;
    }

    public void setTramCoelId(Integer tramCoelId) {
        this.tramCoelId = tramCoelId;
    }

    public BigDecimal getTramCoelKini() {
        return tramCoelKini;
    }

    public void setTramCoelKini(BigDecimal tramCoelKini) {
        this.tramCoelKini = tramCoelKini;
    }

    public BigDecimal getTramCoelKfin() {
        return tramCoelKfin;
    }

    public void setTramCoelKfin(BigDecimal tramCoelKfin) {
        this.tramCoelKfin = tramCoelKfin;
    }

    public BigDecimal getTramCoelAlto() {
        return tramCoelAlto;
    }

    public void setTramCoelAlto(BigDecimal tramCoelAlto) {
        this.tramCoelAlto = tramCoelAlto;
    }

    public Character getTramCoelLado() {
        return tramCoelLado;
    }

    public void setTramCoelLado(Character tramCoelLado) {
        this.tramCoelLado = tramCoelLado;
    }

    public BigDecimal getTramCoelLong() {
        return tramCoelLong;
    }

    public void setTramCoelLong(BigDecimal tramCoelLong) {
        this.tramCoelLong = tramCoelLong;
    }

    public BigDecimal getTramCoelAnch() {
        return tramCoelAnch;
    }

    public void setTramCoelAnch(BigDecimal tramCoelAnch) {
        this.tramCoelAnch = tramCoelAnch;
    }

    public BigDecimal getTramCoelCant() {
        return tramCoelCant;
    }

    public void setTramCoelCant(BigDecimal tramCoelCant) {
        this.tramCoelCant = tramCoelCant;
    }

    public BigDecimal getTramCoelStot() {
        return tramCoelStot;
    }

    public void setTramCoelStot(BigDecimal tramCoelStot) {
        this.tramCoelStot = tramCoelStot;
    }

    public String getTramCoelObsv() {
        return tramCoelObsv;
    }

    public void setTramCoelObsv(String tramCoelObsv) {
        this.tramCoelObsv = tramCoelObsv;
    }

    public Character getTramCoelEsta() {
        return tramCoelEsta;
    }

    public void setTramCoelEsta(Character tramCoelEsta) {
        this.tramCoelEsta = tramCoelEsta;
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

    public Elemento getElemId() {
        return elemId;
    }

    public void setElemId(Elemento elemId) {
        this.elemId = elemId;
    }

    public CodigoElemento getCoelId() {
        return coelId;
    }

    public void setCoelId(CodigoElemento coelId) {
        this.coelId = coelId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tramCoelId != null ? tramCoelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramoCodigoElemento)) {
            return false;
        }
        TramoCodigoElemento other = (TramoCodigoElemento) object;
        if ((this.tramCoelId == null && other.tramCoelId != null) || (this.tramCoelId != null && !this.tramCoelId.equals(other.tramCoelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TramoCodigoElemento[ tramCoelId=" + tramCoelId + " ]";
    }
    
}
