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
@Table(name = "cargo_permiso_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargoPermisoAccion.findAll", query = "SELECT c FROM CargoPermisoAccion c"),
    @NamedQuery(name = "CargoPermisoAccion.findByCargPermAcciId", query = "SELECT c FROM CargoPermisoAccion c WHERE c.cargPermAcciId = :cargPermAcciId"),
    @NamedQuery(name = "CargoPermisoAccion.findByCargPermAcciEsta", query = "SELECT c FROM CargoPermisoAccion c WHERE c.cargPermAcciEsta = :cargPermAcciEsta"),
    @NamedQuery(name = "CargoPermisoAccion.findByAudiFreg", query = "SELECT c FROM CargoPermisoAccion c WHERE c.audiFreg = :audiFreg"),
    @NamedQuery(name = "CargoPermisoAccion.findByAudiFact", query = "SELECT c FROM CargoPermisoAccion c WHERE c.audiFact = :audiFact")})
public class CargoPermisoAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "carg_perm_acci_id")
    private Integer cargPermAcciId;
    @Basic(optional = false)
    @Column(name = "carg_perm_acci_esta")
    private Character cargPermAcciEsta;
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
    @JoinColumn(name = "perm_acci_id", referencedColumnName = "perm_acci_id")
    @ManyToOne(optional = false)
    private PermisoAccion permAcciId;
    @JoinColumn(name = "carg_id", referencedColumnName = "carg_id")
    @ManyToOne(optional = false)
    private Cargo cargId;

    public CargoPermisoAccion() {
    }

    public CargoPermisoAccion(Integer cargPermAcciId) {
        this.cargPermAcciId = cargPermAcciId;
    }

    public CargoPermisoAccion(Integer cargPermAcciId, Character cargPermAcciEsta, Date audiFreg, Date audiFact) {
        this.cargPermAcciId = cargPermAcciId;
        this.cargPermAcciEsta = cargPermAcciEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getCargPermAcciId() {
        return cargPermAcciId;
    }

    public void setCargPermAcciId(Integer cargPermAcciId) {
        this.cargPermAcciId = cargPermAcciId;
    }

    public Character getCargPermAcciEsta() {
        return cargPermAcciEsta;
    }

    public void setCargPermAcciEsta(Character cargPermAcciEsta) {
        this.cargPermAcciEsta = cargPermAcciEsta;
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

    public PermisoAccion getPermAcciId() {
        return permAcciId;
    }

    public void setPermAcciId(PermisoAccion permAcciId) {
        this.permAcciId = permAcciId;
    }

    public Cargo getCargId() {
        return cargId;
    }

    public void setCargId(Cargo cargId) {
        this.cargId = cargId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargPermAcciId != null ? cargPermAcciId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargoPermisoAccion)) {
            return false;
        }
        CargoPermisoAccion other = (CargoPermisoAccion) object;
        if ((this.cargPermAcciId == null && other.cargPermAcciId != null) || (this.cargPermAcciId != null && !this.cargPermAcciId.equals(other.cargPermAcciId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CargoPermisoAccion[ cargPermAcciId=" + cargPermAcciId + " ]";
    }
    
}
