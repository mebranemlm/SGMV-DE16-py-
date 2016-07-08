/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
@Table(name = "contrato_tramo_personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoTramoPersonal.findAll", query = "SELECT c FROM ContratoTramoPersonal c"),
    @NamedQuery(name = "ContratoTramoPersonal.findByContTramPersId", query = "SELECT c FROM ContratoTramoPersonal c WHERE c.contTramPersId = :contTramPersId"),
    @NamedQuery(name = "ContratoTramoPersonal.findByContTramPersEsta", query = "SELECT c FROM ContratoTramoPersonal c WHERE c.contTramPersEsta = :contTramPersEsta"),
    @NamedQuery(name = "ContratoTramoPersonal.findByAudiFreg", query = "SELECT c FROM ContratoTramoPersonal c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "ContratoTramoPersonal.findByAudiFact", query = "SELECT c FROM ContratoTramoPersonal c WHERE c.audiFact = :audiFact")})
public class ContratoTramoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cont_tram_pers_id")
    private Integer contTramPersId;
    @Basic(optional = false)
    @Column(name = "cont_tram_pers_esta")
    private Character contTramPersEsta;
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
    @JoinColumn(name = "pers_id", referencedColumnName = "pers_id")
    @ManyToOne(optional = false)
    private Personal persId;
    @JoinColumn(name = "cont_tram_id", referencedColumnName = "cont_tram_id")
    @ManyToOne(optional = false)
    private ContratoTramo contTramId;

    public ContratoTramoPersonal() {
    }

    public ContratoTramoPersonal(Integer contTramPersId) {
        this.contTramPersId = contTramPersId;
    }

    public ContratoTramoPersonal(Integer contTramPersId, Character contTramPersEsta, Date audiFreg, Date audiFact) {
        this.contTramPersId = contTramPersId;
        this.contTramPersEsta = contTramPersEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getContTramPersId() {
        return contTramPersId;
    }

    public void setContTramPersId(Integer contTramPersId) {
        this.contTramPersId = contTramPersId;
    }

    public Character getContTramPersEsta() {
        return contTramPersEsta;
    }

    public void setContTramPersEsta(Character contTramPersEsta) {
        this.contTramPersEsta = contTramPersEsta;
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

    public Personal getPersId() {
        return persId;
    }

    public void setPersId(Personal persId) {
        this.persId = persId;
    }

    public ContratoTramo getContTramId() {
        return contTramId;
    }

    public void setContTramId(ContratoTramo contTramId) {
        this.contTramId = contTramId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contTramPersId != null ? contTramPersId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratoTramoPersonal)) {
            return false;
        }
        ContratoTramoPersonal other = (ContratoTramoPersonal) object;
        if ((this.contTramPersId == null && other.contTramPersId != null) || (this.contTramPersId != null && !this.contTramPersId.equals(other.contTramPersId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ContratoTramoPersonal[ contTramPersId=" + contTramPersId + " ]";
    }
    
}
