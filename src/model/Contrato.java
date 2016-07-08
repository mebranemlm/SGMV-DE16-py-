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
@Table(name = "contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c"),
    @NamedQuery(name = "Contrato.findByContId", query = "SELECT c FROM Contrato c WHERE c.contId = :contId"),
    @NamedQuery(name = "Contrato.findByContNomb", query = "SELECT c FROM Contrato c WHERE c.contNomb = :contNomb"),
    @NamedQuery(name = "Contrato.findByContProy", query = "SELECT c FROM Contrato c WHERE c.contProy = :contProy"),
    @NamedQuery(name = "Contrato.findByContFini", query = "SELECT c FROM Contrato c WHERE c.contFini = :contFini"),
    @NamedQuery(name = "Contrato.findByContFfin", query = "SELECT c FROM Contrato c WHERE c.contFfin = :contFfin"),
    @NamedQuery(name = "Contrato.findByContPlaz", query = "SELECT c FROM Contrato c WHERE c.contPlaz = :contPlaz"),
    @NamedQuery(name = "Contrato.findByContMont", query = "SELECT c FROM Contrato c WHERE c.contMont = :contMont"),
    @NamedQuery(name = "Contrato.findByContEspe", query = "SELECT c FROM Contrato c WHERE c.contEspe = :contEspe"),
    @NamedQuery(name = "Contrato.findByContEsta", query = "SELECT c FROM Contrato c WHERE c.contEsta = :contEsta"),
    @NamedQuery(name = "Contrato.findByAudiFreg", query = "SELECT c FROM Contrato c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "Contrato.findByAudiFact", query = "SELECT c FROM Contrato c WHERE c.audiFact = :audiFact")})
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cont_id")
    private Integer contId;
    @Basic(optional = false)
    @Column(name = "cont_nomb")
    private String contNomb;
    @Basic(optional = false)
    @Column(name = "cont_proy")
    private String contProy;
    @Basic(optional = false)
    @Column(name = "cont_fini")
    @Temporal(TemporalType.DATE)
    private Date contFini;
    @Basic(optional = false)
    @Column(name = "cont_ffin")
    @Temporal(TemporalType.DATE)
    private Date contFfin;
    @Basic(optional = false)
    @Column(name = "cont_plaz")
    private String contPlaz;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "cont_mont")
    private BigDecimal contMont;
    @Column(name = "cont_espe")
    private String contEspe;
    @Basic(optional = false)
    @Column(name = "cont_esta")
    private Character contEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contId")
    private Collection<ContratoTramo> contratoTramoCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "clie_id", referencedColumnName = "clie_id")
    @ManyToOne(optional = false)
    private Cliente clieId;

    public Contrato() {
    }

    public Contrato(Integer contId) {
        this.contId = contId;
    }

    public Contrato(Integer contId, String contNomb, String contProy, Date contFini, Date contFfin, String contPlaz, BigDecimal contMont, Character contEsta, Date audiFreg, Date audiFact) {
        this.contId = contId;
        this.contNomb = contNomb;
        this.contProy = contProy;
        this.contFini = contFini;
        this.contFfin = contFfin;
        this.contPlaz = contPlaz;
        this.contMont = contMont;
        this.contEsta = contEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getContId() {
        return contId;
    }

    public void setContId(Integer contId) {
        this.contId = contId;
    }

    public String getContNomb() {
        return contNomb;
    }

    public void setContNomb(String contNomb) {
        this.contNomb = contNomb;
    }

    public String getContProy() {
        return contProy;
    }

    public void setContProy(String contProy) {
        this.contProy = contProy;
    }

    public Date getContFini() {
        return contFini;
    }

    public void setContFini(Date contFini) {
        this.contFini = contFini;
    }

    public Date getContFfin() {
        return contFfin;
    }

    public void setContFfin(Date contFfin) {
        this.contFfin = contFfin;
    }

    public String getContPlaz() {
        return contPlaz;
    }

    public void setContPlaz(String contPlaz) {
        this.contPlaz = contPlaz;
    }

    public BigDecimal getContMont() {
        return contMont;
    }

    public void setContMont(BigDecimal contMont) {
        this.contMont = contMont;
    }

    public String getContEspe() {
        return contEspe;
    }

    public void setContEspe(String contEspe) {
        this.contEspe = contEspe;
    }

    public Character getContEsta() {
        return contEsta;
    }

    public void setContEsta(Character contEsta) {
        this.contEsta = contEsta;
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

    public Cliente getClieId() {
        return clieId;
    }

    public void setClieId(Cliente clieId) {
        this.clieId = clieId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contId != null ? contId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.contId == null && other.contId != null) || (this.contId != null && !this.contId.equals(other.contId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Contrato[ contId=" + contId + " ]";
    }
    
}
