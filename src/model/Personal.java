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
import javax.persistence.OneToOne;
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
@Table(name = "personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personal.findAll", query = "SELECT p FROM Personal p"),
    @NamedQuery(name = "Personal.findByPersId", query = "SELECT p FROM Personal p WHERE p.persId = :persId"),
    @NamedQuery(name = "Personal.findByPersDni", query = "SELECT p FROM Personal p WHERE p.persDni = :persDni"),
    @NamedQuery(name = "Personal.findByPersNomb", query = "SELECT p FROM Personal p WHERE p.persNomb = :persNomb"),
    @NamedQuery(name = "Personal.findByPersApel", query = "SELECT p FROM Personal p WHERE p.persApel = :persApel"),
    @NamedQuery(name = "Personal.findByPersFnac", query = "SELECT p FROM Personal p WHERE p.persFnac = :persFnac"),
    @NamedQuery(name = "Personal.findByPersDire", query = "SELECT p FROM Personal p WHERE p.persDire = :persDire"),
    @NamedQuery(name = "Personal.findByPersTelf", query = "SELECT p FROM Personal p WHERE p.persTelf = :persTelf"),
    @NamedQuery(name = "Personal.findByPersFing", query = "SELECT p FROM Personal p WHERE p.persFing = :persFing"),
    @NamedQuery(name = "Personal.findByPersEmai", query = "SELECT p FROM Personal p WHERE p.persEmai = :persEmai"),
    @NamedQuery(name = "Personal.findByPersEsta", query = "SELECT p FROM Personal p WHERE p.persEsta = :persEsta"),
    @NamedQuery(name = "Personal.findByAudiFreg", query = "SELECT p FROM Personal p WHERE p.audiFreg = :audiFreg"),
    @NamedQuery(name = "Personal.findByAudiFact", query = "SELECT p FROM Personal p WHERE p.audiFact = :audiFact")})
public class Personal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pers_id")
    private Integer persId;
    @Basic(optional = false)
    @Column(name = "pers_dni")
    private String persDni;
    @Basic(optional = false)
    @Column(name = "pers_nomb")
    private String persNomb;
    @Basic(optional = false)
    @Column(name = "pers_apel")
    private String persApel;
    @Column(name = "pers_fnac")
    @Temporal(TemporalType.DATE)
    private Date persFnac;
    @Column(name = "pers_dire")
    private String persDire;
    @Column(name = "pers_telf")
    private String persTelf;
    @Basic(optional = false)
    @Column(name = "pers_fing")
    @Temporal(TemporalType.DATE)
    private Date persFing;
    @Column(name = "pers_emai")
    private String persEmai;
    @Basic(optional = false)
    @Column(name = "pers_esta")
    private Character persEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persId")
    private Usuario usuario;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "carg_id", referencedColumnName = "carg_id")
    @ManyToOne(optional = false)
    private Cargo cargId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persId")
    private Collection<ContratoTramoPersonal> contratoTramoPersonalCollection;

    public Personal() {
    }

    public Personal(Integer persId) {
        this.persId = persId;
    }

    public Personal(Integer persId, String persDni, String persNomb, String persApel, Date persFing, Character persEsta, Date audiFreg, Date audiFact) {
        this.persId = persId;
        this.persDni = persDni;
        this.persNomb = persNomb;
        this.persApel = persApel;
        this.persFing = persFing;
        this.persEsta = persEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getPersId() {
        return persId;
    }

    public void setPersId(Integer persId) {
        this.persId = persId;
    }

    public String getPersDni() {
        return persDni;
    }

    public void setPersDni(String persDni) {
        this.persDni = persDni;
    }

    public String getPersNomb() {
        return persNomb;
    }

    public void setPersNomb(String persNomb) {
        this.persNomb = persNomb;
    }

    public String getPersApel() {
        return persApel;
    }

    public void setPersApel(String persApel) {
        this.persApel = persApel;
    }

    public Date getPersFnac() {
        return persFnac;
    }

    public void setPersFnac(Date persFnac) {
        this.persFnac = persFnac;
    }

    public String getPersDire() {
        return persDire;
    }

    public void setPersDire(String persDire) {
        this.persDire = persDire;
    }

    public String getPersTelf() {
        return persTelf;
    }

    public void setPersTelf(String persTelf) {
        this.persTelf = persTelf;
    }

    public Date getPersFing() {
        return persFing;
    }

    public void setPersFing(Date persFing) {
        this.persFing = persFing;
    }

    public String getPersEmai() {
        return persEmai;
    }

    public void setPersEmai(String persEmai) {
        this.persEmai = persEmai;
    }

    public Character getPersEsta() {
        return persEsta;
    }

    public void setPersEsta(Character persEsta) {
        this.persEsta = persEsta;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    public Cargo getCargId() {
        return cargId;
    }

    public void setCargId(Cargo cargId) {
        this.cargId = cargId;
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
        hash += (persId != null ? persId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personal)) {
            return false;
        }
        Personal other = (Personal) object;
        if ((this.persId == null && other.persId != null) || (this.persId != null && !this.persId.equals(other.persId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Personal[ persId=" + persId + " ]";
    }
    
}
