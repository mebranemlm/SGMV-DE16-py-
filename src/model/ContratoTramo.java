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
@Table(name = "contrato_tramo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoTramo.findAll", query = "SELECT c FROM ContratoTramo c"),
    @NamedQuery(name = "ContratoTramo.findByContTramId", query = "SELECT c FROM ContratoTramo c WHERE c.contTramId = :contTramId"),
    @NamedQuery(name = "ContratoTramo.findByContTramEsta", query = "SELECT c FROM ContratoTramo c WHERE c.contTramEsta = :contTramEsta"),
    @NamedQuery(name = "ContratoTramo.findByAudiFreg", query = "SELECT c FROM ContratoTramo c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "ContratoTramo.findByAudiFact", query = "SELECT c FROM ContratoTramo c WHERE c.audiFact = :audiFact")})
public class ContratoTramo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cont_tram_id")
    private Integer contTramId;
    @Basic(optional = false)
    @Column(name = "cont_tram_esta")
    private Character contTramEsta;
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
    @JoinColumn(name = "cont_id", referencedColumnName = "cont_id")
    @ManyToOne(optional = false)
    private Contrato contId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contTramId")
    private Collection<ContratoTramoActividad> contratoTramoActividadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contTramId")
    private Collection<ContratoTramoPersonal> contratoTramoPersonalCollection;

    public ContratoTramo() {
    }

    public ContratoTramo(Integer contTramId) {
        this.contTramId = contTramId;
    }

    public ContratoTramo(Integer contTramId, Character contTramEsta, Date audiFreg, Date audiFact) {
        this.contTramId = contTramId;
        this.contTramEsta = contTramEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getContTramId() {
        return contTramId;
    }

    public void setContTramId(Integer contTramId) {
        this.contTramId = contTramId;
    }

    public Character getContTramEsta() {
        return contTramEsta;
    }

    public void setContTramEsta(Character contTramEsta) {
        this.contTramEsta = contTramEsta;
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

    public Contrato getContId() {
        return contId;
    }

    public void setContId(Contrato contId) {
        this.contId = contId;
    }

    @XmlTransient
    public Collection<ContratoTramoActividad> getContratoTramoActividadCollection() {
        return contratoTramoActividadCollection;
    }

    public void setContratoTramoActividadCollection(Collection<ContratoTramoActividad> contratoTramoActividadCollection) {
        this.contratoTramoActividadCollection = contratoTramoActividadCollection;
    }

    @XmlTransient
    public Collection<ContratoTramoPersonal> getContratoTramoPersonalCollection() {
        return contratoTramoPersonalCollection;
    }

    public void setContratoTramoPersonalCollection(Collection<ContratoTramoPersonal> contratoTramoPersonalCollection) {
        this.contratoTramoPersonalCollection = contratoTramoPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contTramId != null ? contTramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratoTramo)) {
            return false;
        }
        ContratoTramo other = (ContratoTramo) object;
        if ((this.contTramId == null && other.contTramId != null) || (this.contTramId != null && !this.contTramId.equals(other.contTramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ContratoTramo[ contTramId=" + contTramId + " ]";
    }
    
}
