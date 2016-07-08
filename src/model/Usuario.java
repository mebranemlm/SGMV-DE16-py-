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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUserId", query = "SELECT u FROM Usuario u WHERE u.userId = :userId"),
    @NamedQuery(name = "Usuario.findByUserUser", query = "SELECT u FROM Usuario u WHERE u.userUser = :userUser"),
    @NamedQuery(name = "Usuario.findByUserPass", query = "SELECT u FROM Usuario u WHERE u.userPass = :userPass"),
    @NamedQuery(name = "Usuario.findByUserEsta", query = "SELECT u FROM Usuario u WHERE u.userEsta = :userEsta"),
    @NamedQuery(name = "Usuario.findByAudiFreg", query = "SELECT u FROM Usuario u WHERE u.audiFreg = :audiFreg"),
    @NamedQuery(name = "Usuario.findByAudiFact", query = "SELECT u FROM Usuario u WHERE u.audiFact = :audiFact")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "user_user")
    private String userUser;
    @Basic(optional = false)
    @Column(name = "user_pass")
    private String userPass;
    @Basic(optional = false)
    @Column(name = "user_esta")
    private Character userEsta;
    @Basic(optional = false)
    @Column(name = "audi_freg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFreg;
    @Basic(optional = false)
    @Column(name = "audi_fact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audiFact;
    @OneToMany(mappedBy = "userId")
    private Collection<Sector> sectorCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<TramoCodigoElemento> tramoCodigoElementoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<ClienteTipo> clienteTipoCollection;
    @OneToMany(mappedBy = "userUserId")
    private Collection<Usuario> usuarioCollection;
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userUserId;
    @JoinColumn(name = "pers_id", referencedColumnName = "pers_id")
    @OneToOne(optional = false)
    private Personal persId;
    @OneToMany(mappedBy = "userId")
    private Collection<ControlActividadDetalle> controlActividadDetalleCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Actividad> actividadCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<CodigoElemento> codigoElementoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<CargoPermisoAccion> cargoPermisoAccionCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<ContratoTramo> contratoTramoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Accion> accionCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Cargo> cargoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Contrato> contratoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<ContratoTramoActividad> contratoTramoActividadCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<PermisoAccion> permisoAccionCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Personal> personalCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Permiso> permisoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Cliente> clienteCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<ContratoTramoPersonal> contratoTramoPersonalCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<TipoActividad> tipoActividadCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Elemento> elementoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<ControlActividad> controlActividadCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Tramo> tramoCollection;

    public Usuario() {
    }

    public Usuario(Integer userId) {
        this.userId = userId;
    }

    public Usuario(Integer userId, String userUser, String userPass, Character userEsta, Date audiFreg, Date audiFact) {
        this.userId = userId;
        this.userUser = userUser;
        this.userPass = userPass;
        this.userEsta = userEsta;
        this.audiFreg = audiFreg;
        this.audiFact = audiFact;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserUser() {
        return userUser;
    }

    public void setUserUser(String userUser) {
        this.userUser = userUser;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public Character getUserEsta() {
        return userEsta;
    }

    public void setUserEsta(Character userEsta) {
        this.userEsta = userEsta;
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
    public Collection<Sector> getSectorCollection() {
        return sectorCollection;
    }

    public void setSectorCollection(Collection<Sector> sectorCollection) {
        this.sectorCollection = sectorCollection;
    }

    @XmlTransient
    public Collection<TramoCodigoElemento> getTramoCodigoElementoCollection() {
        return tramoCodigoElementoCollection;
    }

    public void setTramoCodigoElementoCollection(Collection<TramoCodigoElemento> tramoCodigoElementoCollection) {
        this.tramoCodigoElementoCollection = tramoCodigoElementoCollection;
    }

    @XmlTransient
    public Collection<ClienteTipo> getClienteTipoCollection() {
        return clienteTipoCollection;
    }

    public void setClienteTipoCollection(Collection<ClienteTipo> clienteTipoCollection) {
        this.clienteTipoCollection = clienteTipoCollection;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Usuario getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(Usuario userUserId) {
        this.userUserId = userUserId;
    }

    public Personal getPersId() {
        return persId;
    }

    public void setPersId(Personal persId) {
        this.persId = persId;
    }

    @XmlTransient
    public Collection<ControlActividadDetalle> getControlActividadDetalleCollection() {
        return controlActividadDetalleCollection;
    }

    public void setControlActividadDetalleCollection(Collection<ControlActividadDetalle> controlActividadDetalleCollection) {
        this.controlActividadDetalleCollection = controlActividadDetalleCollection;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    @XmlTransient
    public Collection<CodigoElemento> getCodigoElementoCollection() {
        return codigoElementoCollection;
    }

    public void setCodigoElementoCollection(Collection<CodigoElemento> codigoElementoCollection) {
        this.codigoElementoCollection = codigoElementoCollection;
    }

    @XmlTransient
    public Collection<CargoPermisoAccion> getCargoPermisoAccionCollection() {
        return cargoPermisoAccionCollection;
    }

    public void setCargoPermisoAccionCollection(Collection<CargoPermisoAccion> cargoPermisoAccionCollection) {
        this.cargoPermisoAccionCollection = cargoPermisoAccionCollection;
    }

    @XmlTransient
    public Collection<ContratoTramo> getContratoTramoCollection() {
        return contratoTramoCollection;
    }

    public void setContratoTramoCollection(Collection<ContratoTramo> contratoTramoCollection) {
        this.contratoTramoCollection = contratoTramoCollection;
    }

    @XmlTransient
    public Collection<Accion> getAccionCollection() {
        return accionCollection;
    }

    public void setAccionCollection(Collection<Accion> accionCollection) {
        this.accionCollection = accionCollection;
    }

    @XmlTransient
    public Collection<Cargo> getCargoCollection() {
        return cargoCollection;
    }

    public void setCargoCollection(Collection<Cargo> cargoCollection) {
        this.cargoCollection = cargoCollection;
    }

    @XmlTransient
    public Collection<Contrato> getContratoCollection() {
        return contratoCollection;
    }

    public void setContratoCollection(Collection<Contrato> contratoCollection) {
        this.contratoCollection = contratoCollection;
    }

    @XmlTransient
    public Collection<ContratoTramoActividad> getContratoTramoActividadCollection() {
        return contratoTramoActividadCollection;
    }

    public void setContratoTramoActividadCollection(Collection<ContratoTramoActividad> contratoTramoActividadCollection) {
        this.contratoTramoActividadCollection = contratoTramoActividadCollection;
    }

    @XmlTransient
    public Collection<PermisoAccion> getPermisoAccionCollection() {
        return permisoAccionCollection;
    }

    public void setPermisoAccionCollection(Collection<PermisoAccion> permisoAccionCollection) {
        this.permisoAccionCollection = permisoAccionCollection;
    }

    @XmlTransient
    public Collection<Personal> getPersonalCollection() {
        return personalCollection;
    }

    public void setPersonalCollection(Collection<Personal> personalCollection) {
        this.personalCollection = personalCollection;
    }

    @XmlTransient
    public Collection<Permiso> getPermisoCollection() {
        return permisoCollection;
    }

    public void setPermisoCollection(Collection<Permiso> permisoCollection) {
        this.permisoCollection = permisoCollection;
    }

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @XmlTransient
    public Collection<ContratoTramoPersonal> getContratoTramoPersonalCollection() {
        return contratoTramoPersonalCollection;
    }

    public void setContratoTramoPersonalCollection(Collection<ContratoTramoPersonal> contratoTramoPersonalCollection) {
        this.contratoTramoPersonalCollection = contratoTramoPersonalCollection;
    }

    @XmlTransient
    public Collection<TipoActividad> getTipoActividadCollection() {
        return tipoActividadCollection;
    }

    public void setTipoActividadCollection(Collection<TipoActividad> tipoActividadCollection) {
        this.tipoActividadCollection = tipoActividadCollection;
    }

    @XmlTransient
    public Collection<Elemento> getElementoCollection() {
        return elementoCollection;
    }

    public void setElementoCollection(Collection<Elemento> elementoCollection) {
        this.elementoCollection = elementoCollection;
    }

    @XmlTransient
    public Collection<ControlActividad> getControlActividadCollection() {
        return controlActividadCollection;
    }

    public void setControlActividadCollection(Collection<ControlActividad> controlActividadCollection) {
        this.controlActividadCollection = controlActividadCollection;
    }

    @XmlTransient
    public Collection<Tramo> getTramoCollection() {
        return tramoCollection;
    }

    public void setTramoCollection(Collection<Tramo> tramoCollection) {
        this.tramoCollection = tramoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Usuario[ userId=" + userId + " ]";
    }
    
}
