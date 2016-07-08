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
@Table(name = "control_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlActividad.findAll", query = "SELECT c FROM ControlActividad c"),
    @NamedQuery(name = "ControlActividad.findByCtacId", query = "SELECT c FROM ControlActividad c WHERE c.ctacId = :ctacId"),
    @NamedQuery(name = "ControlActividad.findByCtacNume", query = "SELECT c FROM ControlActividad c WHERE c.ctacNume = :ctacNume"),
    @NamedQuery(name = "ControlActividad.findByContTramActiId", query = "SELECT c FROM ControlActividad c WHERE c.contTramActiId = :contTramActiId"),
    @NamedQuery(name = "ControlActividad.findByCtacFech", query = "SELECT c FROM ControlActividad c WHERE c.ctacFech = :ctacFech"),
    @NamedQuery(name = "ControlActividad.findByCtacTota", query = "SELECT c FROM ControlActividad c WHERE c.ctacTota = :ctacTota"),
    @NamedQuery(name = "ControlActividad.findByCtacEsta", query = "SELECT c FROM ControlActividad c WHERE c.ctacEsta = :ctacEsta"),
    @NamedQuery(name = "ControlActividad.findByCtacFreg", query = "SELECT c FROM ControlActividad c WHERE c.ctacFreg = :ctacFreg"),
    @NamedQuery(name = "ControlActividad.findByCtacFact", query = "SELECT c FROM ControlActividad c WHERE c.ctacFact = :ctacFact")})
public class ControlActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ctac_id")
    private Integer ctacId;
    @Basic(optional = false)
    @Column(name = "ctac_nume")
    private String ctacNume;
    @Basic(optional = false)
    @Column(name = "cont_tram_acti_id")
    private int contTramActiId;
    @Basic(optional = false)
    @Column(name = "ctac_fech")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctacFech;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "ctac_tota")
    private BigDecimal ctacTota;
    @Basic(optional = false)
    @Column(name = "ctac_esta")
    private Character ctacEsta;
    @Basic(optional = false)
    @Column(name = "ctac_freg")
    private String ctacFreg;
    @Basic(optional = false)
    @Column(name = "ctac_fact")
    private String ctacFact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ctacId")
    private Collection<ControlActividadDetalle> controlActividadDetalleCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;

    public ControlActividad() {
    }

    public ControlActividad(Integer ctacId) {
        this.ctacId = ctacId;
    }

    public ControlActividad(Integer ctacId, String ctacNume, int contTramActiId, Date ctacFech, BigDecimal ctacTota, Character ctacEsta, String ctacFreg, String ctacFact) {
        this.ctacId = ctacId;
        this.ctacNume = ctacNume;
        this.contTramActiId = contTramActiId;
        this.ctacFech = ctacFech;
        this.ctacTota = ctacTota;
        this.ctacEsta = ctacEsta;
        this.ctacFreg = ctacFreg;
        this.ctacFact = ctacFact;
    }

    public Integer getCtacId() {
        return ctacId;
    }

    public void setCtacId(Integer ctacId) {
        this.ctacId = ctacId;
    }

    public String getCtacNume() {
        return ctacNume;
    }

    public void setCtacNume(String ctacNume) {
        this.ctacNume = ctacNume;
    }

    public int getContTramActiId() {
        return contTramActiId;
    }

    public void setContTramActiId(int contTramActiId) {
        this.contTramActiId = contTramActiId;
    }

    public Date getCtacFech() {
        return ctacFech;
    }

    public void setCtacFech(Date ctacFech) {
        this.ctacFech = ctacFech;
    }

    public BigDecimal getCtacTota() {
        return ctacTota;
    }

    public void setCtacTota(BigDecimal ctacTota) {
        this.ctacTota = ctacTota;
    }

    public Character getCtacEsta() {
        return ctacEsta;
    }

    public void setCtacEsta(Character ctacEsta) {
        this.ctacEsta = ctacEsta;
    }

    public String getCtacFreg() {
        return ctacFreg;
    }

    public void setCtacFreg(String ctacFreg) {
        this.ctacFreg = ctacFreg;
    }

    public String getCtacFact() {
        return ctacFact;
    }

    public void setCtacFact(String ctacFact) {
        this.ctacFact = ctacFact;
    }

    @XmlTransient
    public Collection<ControlActividadDetalle> getControlActividadDetalleCollection() {
        return controlActividadDetalleCollection;
    }

    public void setControlActividadDetalleCollection(Collection<ControlActividadDetalle> controlActividadDetalleCollection) {
        this.controlActividadDetalleCollection = controlActividadDetalleCollection;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctacId != null ? ctacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlActividad)) {
            return false;
        }
        ControlActividad other = (ControlActividad) object;
        if ((this.ctacId == null && other.ctacId != null) || (this.ctacId != null && !this.ctacId.equals(other.ctacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ControlActividad[ ctacId=" + ctacId + " ]";
    }
    
}
