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
@Table(name = "contrato_tramo_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoTramoActividad.findAll", query = "SELECT c FROM ContratoTramoActividad c"),
    @NamedQuery(name = "ContratoTramoActividad.findByContTramActiId", query = "SELECT c FROM ContratoTramoActividad c WHERE c.contTramActiId = :contTramActiId"),
    @NamedQuery(name = "ContratoTramoActividad.findByContTramActiEsta", query = "SELECT c FROM ContratoTramoActividad c WHERE c.contTramActiEsta = :contTramActiEsta"),
    @NamedQuery(name = "ContratoTramoActividad.findByAudiFreg", query = "SELECT c FROM ContratoTramoActividad c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "ContratoTramoActividad.findByAudiFact", query = "SELECT c FROM ContratoTramoActividad c WHERE c.audiFact = :audiFact")})
public class ContratoTramoActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cont_tram_acti_id")
    private Integer contTramActiId;
    @Basic(optional = false)
    @Column(name = "cont_tram_acti_esta")
    private String contTramActiEsta;
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
    @JoinColumn(name = "cont_tram_id", referencedColumnName = "cont_tram_id")
    @ManyToOne(optional = false)
    private ContratoTramo contTramId;
    @JoinColumn(name = "acti_id", referencedColumnName = "acti_id")
    @ManyToOne(optional = false)
    private Actividad actiId;

    public ContratoTramoActividad() {
    }

    public ContratoTramoActividad(Integer contTramActiId) {
        this.contTramActiId = contTramActiId;
    }

    public ContratoTramoActividad(Integer contTramActiId, String contTramActiEsta, Date audiFreg, Date audiFact) {
        this.contTramActiId = contTramActiId;
        this.contTramActiEsta = contTramActiEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getContTramActiId() {
        return contTramActiId;
    }

    public void setContTramActiId(Integer contTramActiId) {
        this.contTramActiId = contTramActiId;
    }

    public String getContTramActiEsta() {
        return contTramActiEsta;
    }

    public void setContTramActiEsta(String contTramActiEsta) {
        this.contTramActiEsta = contTramActiEsta;
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

    public ContratoTramo getContTramId() {
        return contTramId;
    }

    public void setContTramId(ContratoTramo contTramId) {
        this.contTramId = contTramId;
    }

    public Actividad getActiId() {
        return actiId;
    }

    public void setActiId(Actividad actiId) {
        this.actiId = actiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contTramActiId != null ? contTramActiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratoTramoActividad)) {
            return false;
        }
        ContratoTramoActividad other = (ContratoTramoActividad) object;
        if ((this.contTramActiId == null && other.contTramActiId != null) || (this.contTramActiId != null && !this.contTramActiId.equals(other.contTramActiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ContratoTramoActividad[ contTramActiId=" + contTramActiId + " ]";
    }
    
}
