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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByClieId", query = "SELECT c FROM Cliente c WHERE c.clieId = :clieId"),
    @NamedQuery(name = "Cliente.findByClieRuc", query = "SELECT c FROM Cliente c WHERE c.clieRuc = :clieRuc"),
    @NamedQuery(name = "Cliente.findByClieRsoc", query = "SELECT c FROM Cliente c WHERE c.clieRsoc = :clieRsoc"),
    @NamedQuery(name = "Cliente.findByClieDire", query = "SELECT c FROM Cliente c WHERE c.clieDire = :clieDire"),
    @NamedQuery(name = "Cliente.findByClieTelf", query = "SELECT c FROM Cliente c WHERE c.clieTelf = :clieTelf"),
    @NamedQuery(name = "Cliente.findByClieEsta", query = "SELECT c FROM Cliente c WHERE c.clieEsta = :clieEsta"),
    @NamedQuery(name = "Cliente.findByAudiFreg", query = "SELECT c FROM Cliente c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "Cliente.findByAudiFact", query = "SELECT c FROM Cliente c WHERE c.audiFact = :audiFact")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clie_id")
    private Integer clieId;
    @Basic(optional = false)
    @Column(name = "clie_ruc")
    private String clieRuc;
    @Basic(optional = false)
    @Column(name = "clie_rsoc")
    private String clieRsoc;
    @Column(name = "clie_dire")
    private String clieDire;
    @Column(name = "clie_telf")
    private String clieTelf;
    @Basic(optional = false)
    @Column(name = "clie_esta")
    private Character clieEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clieId")
    private Collection<Contrato> contratoCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "clti_id", referencedColumnName = "clti_id")
    @ManyToOne(optional = false)
    private ClienteTipo cltiId;

    public Cliente() {
    }

    public Cliente(Integer clieId) {
        this.clieId = clieId;
    }

    public Cliente(Integer clieId, String clieRuc, String clieRsoc, Character clieEsta, Date audiFreg, Date audiFact) {
        this.clieId = clieId;
        this.clieRuc = clieRuc;
        this.clieRsoc = clieRsoc;
        this.clieEsta = clieEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getClieId() {
        return clieId;
    }

    public void setClieId(Integer clieId) {
        this.clieId = clieId;
    }

    public String getClieRuc() {
        return clieRuc;
    }

    public void setClieRuc(String clieRuc) {
        this.clieRuc = clieRuc;
    }

    public String getClieRsoc() {
        return clieRsoc;
    }

    public void setClieRsoc(String clieRsoc) {
        this.clieRsoc = clieRsoc;
    }

    public String getClieDire() {
        return clieDire;
    }

    public void setClieDire(String clieDire) {
        this.clieDire = clieDire;
    }

    public String getClieTelf() {
        return clieTelf;
    }

    public void setClieTelf(String clieTelf) {
        this.clieTelf = clieTelf;
    }

    public Character getClieEsta() {
        return clieEsta;
    }

    public void setClieEsta(Character clieEsta) {
        this.clieEsta = clieEsta;
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
    public Collection<Contrato> getContratoCollection() {
        return contratoCollection;
    }

    public void setContratoCollection(Collection<Contrato> contratoCollection) {
        this.contratoCollection = contratoCollection;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    public ClienteTipo getCltiId() {
        return cltiId;
    }

    public void setCltiId(ClienteTipo cltiId) {
        this.cltiId = cltiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clieId != null ? clieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clieId == null && other.clieId != null) || (this.clieId != null && !this.clieId.equals(other.clieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cliente[ clieId=" + clieId + " ]";
    }
    
}
