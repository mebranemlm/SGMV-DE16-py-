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
@Table(name = "cliente_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteTipo.findAll", query = "SELECT c FROM ClienteTipo c"),
    @NamedQuery(name = "ClienteTipo.findByCltiId", query = "SELECT c FROM ClienteTipo c WHERE c.cltiId = :cltiId"),
    @NamedQuery(name = "ClienteTipo.findByCltiNomb", query = "SELECT c FROM ClienteTipo c WHERE c.cltiNomb = :cltiNomb"),
    @NamedQuery(name = "ClienteTipo.findByCltiEsta", query = "SELECT c FROM ClienteTipo c WHERE c.cltiEsta = :cltiEsta"),
    @NamedQuery(name = "ClienteTipo.findByAudiFreg", query = "SELECT c FROM ClienteTipo c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "ClienteTipo.findByAudiFact", query = "SELECT c FROM ClienteTipo c WHERE c.audiFact = :audiFact")})
public class ClienteTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clti_id")
    private Integer cltiId;
    @Basic(optional = false)
    @Column(name = "clti_nomb")
    private String cltiNomb;
    @Basic(optional = false)
    @Column(name = "clti_esta")
    private Character cltiEsta;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cltiId")
    private Collection<Cliente> clienteCollection;

    public ClienteTipo() {
    }

    public ClienteTipo(Integer cltiId) {
        this.cltiId = cltiId;
    }

    public ClienteTipo(Integer cltiId, String cltiNomb, Character cltiEsta, Date audiFreg, Date audiFact) {
        this.cltiId = cltiId;
        this.cltiNomb = cltiNomb;
        this.cltiEsta = cltiEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getCltiId() {
        return cltiId;
    }

    public void setCltiId(Integer cltiId) {
        this.cltiId = cltiId;
    }

    public String getCltiNomb() {
        return cltiNomb;
    }

    public void setCltiNomb(String cltiNomb) {
        this.cltiNomb = cltiNomb;
    }

    public Character getCltiEsta() {
        return cltiEsta;
    }

    public void setCltiEsta(Character cltiEsta) {
        this.cltiEsta = cltiEsta;
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

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cltiId != null ? cltiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteTipo)) {
            return false;
        }
        ClienteTipo other = (ClienteTipo) object;
        if ((this.cltiId == null && other.cltiId != null) || (this.cltiId != null && !this.cltiId.equals(other.cltiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ClienteTipo[ cltiId=" + cltiId + " ]";
    }
    
}
