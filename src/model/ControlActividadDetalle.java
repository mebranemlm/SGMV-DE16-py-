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
@Table(name = "control_actividad_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlActividadDetalle.findAll", query = "SELECT c FROM ControlActividadDetalle c"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaId", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaId = :ctacDetaId"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaKini", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaKini = :ctacDetaKini"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaKfin", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaKfin = :ctacDetaKfin"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaLado", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaLado = :ctacDetaLado"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaLong", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaLong = :ctacDetaLong"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaAnch", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaAnch = :ctacDetaAnch"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaAlto", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaAlto = :ctacDetaAlto"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaCant", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaCant = :ctacDetaCant"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaSubt", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaSubt = :ctacDetaSubt"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaObsv", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaObsv = :ctacDetaObsv"),
    @NamedQuery(name = "ControlActividadDetalle.findByCtacDetaEsta", query = "SELECT c FROM ControlActividadDetalle c WHERE c.ctacDetaEsta = :ctacDetaEsta"),
    @NamedQuery(name = "ControlActividadDetalle.findByAudiFreg", query = "SELECT c FROM ControlActividadDetalle c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "ControlActividadDetalle.findByAudiFact", query = "SELECT c FROM ControlActividadDetalle c WHERE c.audiFact = :audiFact")})
public class ControlActividadDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ctac_deta_id")
    private Integer ctacDetaId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ctac_deta_kini")
    private BigDecimal ctacDetaKini;
    @Column(name = "ctac_deta_kfin")
    private BigDecimal ctacDetaKfin;
    @Column(name = "ctac_deta_lado")
    private String ctacDetaLado;
    @Column(name = "ctac_deta_long")
    private BigDecimal ctacDetaLong;
    @Column(name = "ctac_deta_anch")
    private BigDecimal ctacDetaAnch;
    @Column(name = "ctac_deta_alto")
    private BigDecimal ctacDetaAlto;
    @Column(name = "ctac_deta_cant")
    private BigDecimal ctacDetaCant;
    @Column(name = "ctac_deta_subt")
    private BigDecimal ctacDetaSubt;
    @Column(name = "ctac_deta_obsv")
    private String ctacDetaObsv;
    @Basic(optional = false)
    @Column(name = "ctac_deta_esta")
    private Character ctacDetaEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @JoinColumn(name = "elem_id", referencedColumnName = "elem_id")
    @ManyToOne(optional = false)
    private Elemento elemId;
    @JoinColumn(name = "sect_id", referencedColumnName = "sect_id")
    @ManyToOne(optional = false)
    private Sector sectId;
    @JoinColumn(name = "coel_id", referencedColumnName = "coel_id")
    @ManyToOne
    private CodigoElemento coelId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "ctac_id", referencedColumnName = "ctac_id")
    @ManyToOne(optional = false)
    private ControlActividad ctacId;

    public ControlActividadDetalle() {
    }

    public ControlActividadDetalle(Integer ctacDetaId) {
        this.ctacDetaId = ctacDetaId;
    }

    public ControlActividadDetalle(Integer ctacDetaId, Character ctacDetaEsta, Date audiFreg, Date audiFact) {
        this.ctacDetaId = ctacDetaId;
        this.ctacDetaEsta = ctacDetaEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getCtacDetaId() {
        return ctacDetaId;
    }

    public void setCtacDetaId(Integer ctacDetaId) {
        this.ctacDetaId = ctacDetaId;
    }

    public BigDecimal getCtacDetaKini() {
        return ctacDetaKini;
    }

    public void setCtacDetaKini(BigDecimal ctacDetaKini) {
        this.ctacDetaKini = ctacDetaKini;
    }

    public BigDecimal getCtacDetaKfin() {
        return ctacDetaKfin;
    }

    public void setCtacDetaKfin(BigDecimal ctacDetaKfin) {
        this.ctacDetaKfin = ctacDetaKfin;
    }

    public String getCtacDetaLado() {
        return ctacDetaLado;
    }

    public void setCtacDetaLado(String ctacDetaLado) {
        this.ctacDetaLado = ctacDetaLado;
    }

    public BigDecimal getCtacDetaLong() {
        return ctacDetaLong;
    }

    public void setCtacDetaLong(BigDecimal ctacDetaLong) {
        this.ctacDetaLong = ctacDetaLong;
    }

    public BigDecimal getCtacDetaAnch() {
        return ctacDetaAnch;
    }

    public void setCtacDetaAnch(BigDecimal ctacDetaAnch) {
        this.ctacDetaAnch = ctacDetaAnch;
    }

    public BigDecimal getCtacDetaAlto() {
        return ctacDetaAlto;
    }

    public void setCtacDetaAlto(BigDecimal ctacDetaAlto) {
        this.ctacDetaAlto = ctacDetaAlto;
    }

    public BigDecimal getCtacDetaCant() {
        return ctacDetaCant;
    }

    public void setCtacDetaCant(BigDecimal ctacDetaCant) {
        this.ctacDetaCant = ctacDetaCant;
    }

    public BigDecimal getCtacDetaSubt() {
        return ctacDetaSubt;
    }

    public void setCtacDetaSubt(BigDecimal ctacDetaSubt) {
        this.ctacDetaSubt = ctacDetaSubt;
    }

    public String getCtacDetaObsv() {
        return ctacDetaObsv;
    }

    public void setCtacDetaObsv(String ctacDetaObsv) {
        this.ctacDetaObsv = ctacDetaObsv;
    }

    public Character getCtacDetaEsta() {
        return ctacDetaEsta;
    }

    public void setCtacDetaEsta(Character ctacDetaEsta) {
        this.ctacDetaEsta = ctacDetaEsta;
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

    public Elemento getElemId() {
        return elemId;
    }

    public void setElemId(Elemento elemId) {
        this.elemId = elemId;
    }

    public Sector getSectId() {
        return sectId;
    }

    public void setSectId(Sector sectId) {
        this.sectId = sectId;
    }

    public CodigoElemento getCoelId() {
        return coelId;
    }

    public void setCoelId(CodigoElemento coelId) {
        this.coelId = coelId;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    public ControlActividad getCtacId() {
        return ctacId;
    }

    public void setCtacId(ControlActividad ctacId) {
        this.ctacId = ctacId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctacDetaId != null ? ctacDetaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlActividadDetalle)) {
            return false;
        }
        ControlActividadDetalle other = (ControlActividadDetalle) object;
        if ((this.ctacDetaId == null && other.ctacDetaId != null) || (this.ctacDetaId != null && !this.ctacDetaId.equals(other.ctacDetaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ControlActividadDetalle[ ctacDetaId=" + ctacDetaId + " ]";
    }
    
}
