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
@Table(name = "actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByActiId", query = "SELECT a FROM Actividad a WHERE a.actiId = :actiId"),
    @NamedQuery(name = "Actividad.findByActiItem", query = "SELECT a FROM Actividad a WHERE a.actiItem = :actiItem"),
    @NamedQuery(name = "Actividad.findByActiDesc", query = "SELECT a FROM Actividad a WHERE a.actiDesc = :actiDesc"),
    @NamedQuery(name = "Actividad.findByActiUnim", query = "SELECT a FROM Actividad a WHERE a.actiUnim = :actiUnim"),
    @NamedQuery(name = "Actividad.findByActiEsta", query = "SELECT a FROM Actividad a WHERE a.actiEsta = :actiEsta"),
    @NamedQuery(name = "Actividad.findByAudiFreg", query = "SELECT a FROM Actividad a WHERE a.audiFreg = :audiFreg"),
    @NamedQuery(name = "Actividad.findByAudiFact", query = "SELECT a FROM Actividad a WHERE a.audiFact = :audiFact")})
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acti_id")
    private Integer actiId;
    @Basic(optional = false)
    @Column(name = "acti_item")
    private String actiItem;
    @Basic(optional = false)
    @Column(name = "acti_desc")
    private String actiDesc;
    @Basic(optional = false)
    @Column(name = "acti_unim")
    private String actiUnim;
    @Basic(optional = false)
    @Column(name = "acti_esta")
    private Character actiEsta;
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
    @JoinColumn(name = "tiac_id", referencedColumnName = "tiac_id")
    @ManyToOne(optional = false)
    private TipoActividad tiacId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actiId")
    private Collection<ContratoTramoActividad> contratoTramoActividadCollection;

    public Actividad() {
    }

    public Actividad(Integer actiId) {
        this.actiId = actiId;
    }

    public Actividad(Integer actiId, String actiItem, String actiDesc, String actiUnim, Character actiEsta, Date audiFreg, Date audiFact) {
        this.actiId = actiId;
        this.actiItem = actiItem;
        this.actiDesc = actiDesc;
        this.actiUnim = actiUnim;
        this.actiEsta = actiEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getActiId() {
        return actiId;
    }

    public void setActiId(Integer actiId) {
        this.actiId = actiId;
    }

    public String getActiItem() {
        return actiItem;
    }

    public void setActiItem(String actiItem) {
        this.actiItem = actiItem;
    }

    public String getActiDesc() {
        return actiDesc;
    }

    public void setActiDesc(String actiDesc) {
        this.actiDesc = actiDesc;
    }

    public String getActiUnim() {
        return actiUnim;
    }

    public void setActiUnim(String actiUnim) {
        this.actiUnim = actiUnim;
    }

    public Character getActiEsta() {
        return actiEsta;
    }

    public void setActiEsta(Character actiEsta) {
        this.actiEsta = actiEsta;
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

    public TipoActividad getTiacId() {
        return tiacId;
    }

    public void setTiacId(TipoActividad tiacId) {
        this.tiacId = tiacId;
    }

    @XmlTransient
    public Collection<ContratoTramoActividad> getContratoTramoActividadCollection() {
        return contratoTramoActividadCollection;
    }

    public void setContratoTramoActividadCollection(Collection<ContratoTramoActividad> contratoTramoActividadCollection) {
        this.contratoTramoActividadCollection = contratoTramoActividadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actiId != null ? actiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.actiId == null && other.actiId != null) || (this.actiId != null && !this.actiId.equals(other.actiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Actividad[ actiId=" + actiId + " ]";
    }
    
}
