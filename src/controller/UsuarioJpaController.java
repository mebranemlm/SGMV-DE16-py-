/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Usuario;
import model.Personal;
import model.Sector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.TramoCodigoElemento;
import model.ClienteTipo;
import model.ControlActividadDetalle;
import model.Actividad;
import model.CodigoElemento;
import model.CargoPermisoAccion;
import model.ContratoTramo;
import model.Accion;
import model.Cargo;
import model.Contrato;
import model.ContratoTramoActividad;
import model.PermisoAccion;
import model.Permiso;
import model.Cliente;
import model.ContratoTramoPersonal;
import model.TipoActividad;
import model.Elemento;
import model.ControlActividad;
import model.Tramo;

/**
 *
 * @author Luis
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws IllegalOrphanException {
        if (usuario.getSectorCollection() == null) {
            usuario.setSectorCollection(new ArrayList<Sector>());
        }
        if (usuario.getTramoCodigoElementoCollection() == null) {
            usuario.setTramoCodigoElementoCollection(new ArrayList<TramoCodigoElemento>());
        }
        if (usuario.getClienteTipoCollection() == null) {
            usuario.setClienteTipoCollection(new ArrayList<ClienteTipo>());
        }
        if (usuario.getUsuarioCollection() == null) {
            usuario.setUsuarioCollection(new ArrayList<Usuario>());
        }
        if (usuario.getControlActividadDetalleCollection() == null) {
            usuario.setControlActividadDetalleCollection(new ArrayList<ControlActividadDetalle>());
        }
        if (usuario.getActividadCollection() == null) {
            usuario.setActividadCollection(new ArrayList<Actividad>());
        }
        if (usuario.getCodigoElementoCollection() == null) {
            usuario.setCodigoElementoCollection(new ArrayList<CodigoElemento>());
        }
        if (usuario.getCargoPermisoAccionCollection() == null) {
            usuario.setCargoPermisoAccionCollection(new ArrayList<CargoPermisoAccion>());
        }
        if (usuario.getContratoTramoCollection() == null) {
            usuario.setContratoTramoCollection(new ArrayList<ContratoTramo>());
        }
        if (usuario.getAccionCollection() == null) {
            usuario.setAccionCollection(new ArrayList<Accion>());
        }
        if (usuario.getCargoCollection() == null) {
            usuario.setCargoCollection(new ArrayList<Cargo>());
        }
        if (usuario.getContratoCollection() == null) {
            usuario.setContratoCollection(new ArrayList<Contrato>());
        }
        if (usuario.getContratoTramoActividadCollection() == null) {
            usuario.setContratoTramoActividadCollection(new ArrayList<ContratoTramoActividad>());
        }
        if (usuario.getPermisoAccionCollection() == null) {
            usuario.setPermisoAccionCollection(new ArrayList<PermisoAccion>());
        }
        if (usuario.getPersonalCollection() == null) {
            usuario.setPersonalCollection(new ArrayList<Personal>());
        }
        if (usuario.getPermisoCollection() == null) {
            usuario.setPermisoCollection(new ArrayList<Permiso>());
        }
        if (usuario.getClienteCollection() == null) {
            usuario.setClienteCollection(new ArrayList<Cliente>());
        }
        if (usuario.getContratoTramoPersonalCollection() == null) {
            usuario.setContratoTramoPersonalCollection(new ArrayList<ContratoTramoPersonal>());
        }
        if (usuario.getTipoActividadCollection() == null) {
            usuario.setTipoActividadCollection(new ArrayList<TipoActividad>());
        }
        if (usuario.getElementoCollection() == null) {
            usuario.setElementoCollection(new ArrayList<Elemento>());
        }
        if (usuario.getControlActividadCollection() == null) {
            usuario.setControlActividadCollection(new ArrayList<ControlActividad>());
        }
        if (usuario.getTramoCollection() == null) {
            usuario.setTramoCollection(new ArrayList<Tramo>());
        }
        List<String> illegalOrphanMessages = null;
        Personal persIdOrphanCheck = usuario.getPersId();
        if (persIdOrphanCheck != null) {
            Usuario oldUsuarioOfPersId = persIdOrphanCheck.getUsuario();
            if (oldUsuarioOfPersId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Personal " + persIdOrphanCheck + " already has an item of type Usuario whose persId column cannot be null. Please make another selection for the persId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userUserId = usuario.getUserUserId();
            if (userUserId != null) {
                userUserId = em.getReference(userUserId.getClass(), userUserId.getUserId());
                usuario.setUserUserId(userUserId);
            }
            Personal persId = usuario.getPersId();
            if (persId != null) {
                persId = em.getReference(persId.getClass(), persId.getPersId());
                usuario.setPersId(persId);
            }
            Collection<Sector> attachedSectorCollection = new ArrayList<Sector>();
            for (Sector sectorCollectionSectorToAttach : usuario.getSectorCollection()) {
                sectorCollectionSectorToAttach = em.getReference(sectorCollectionSectorToAttach.getClass(), sectorCollectionSectorToAttach.getSectId());
                attachedSectorCollection.add(sectorCollectionSectorToAttach);
            }
            usuario.setSectorCollection(attachedSectorCollection);
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollection = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElementoToAttach : usuario.getTramoCodigoElementoCollection()) {
                tramoCodigoElementoCollectionTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollection.add(tramoCodigoElementoCollectionTramoCodigoElementoToAttach);
            }
            usuario.setTramoCodigoElementoCollection(attachedTramoCodigoElementoCollection);
            Collection<ClienteTipo> attachedClienteTipoCollection = new ArrayList<ClienteTipo>();
            for (ClienteTipo clienteTipoCollectionClienteTipoToAttach : usuario.getClienteTipoCollection()) {
                clienteTipoCollectionClienteTipoToAttach = em.getReference(clienteTipoCollectionClienteTipoToAttach.getClass(), clienteTipoCollectionClienteTipoToAttach.getCltiId());
                attachedClienteTipoCollection.add(clienteTipoCollectionClienteTipoToAttach);
            }
            usuario.setClienteTipoCollection(attachedClienteTipoCollection);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : usuario.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getUserId());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            usuario.setUsuarioCollection(attachedUsuarioCollection);
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollection = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalleToAttach : usuario.getControlActividadDetalleCollection()) {
                controlActividadDetalleCollectionControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollection.add(controlActividadDetalleCollectionControlActividadDetalleToAttach);
            }
            usuario.setControlActividadDetalleCollection(attachedControlActividadDetalleCollection);
            Collection<Actividad> attachedActividadCollection = new ArrayList<Actividad>();
            for (Actividad actividadCollectionActividadToAttach : usuario.getActividadCollection()) {
                actividadCollectionActividadToAttach = em.getReference(actividadCollectionActividadToAttach.getClass(), actividadCollectionActividadToAttach.getActiId());
                attachedActividadCollection.add(actividadCollectionActividadToAttach);
            }
            usuario.setActividadCollection(attachedActividadCollection);
            Collection<CodigoElemento> attachedCodigoElementoCollection = new ArrayList<CodigoElemento>();
            for (CodigoElemento codigoElementoCollectionCodigoElementoToAttach : usuario.getCodigoElementoCollection()) {
                codigoElementoCollectionCodigoElementoToAttach = em.getReference(codigoElementoCollectionCodigoElementoToAttach.getClass(), codigoElementoCollectionCodigoElementoToAttach.getCoelId());
                attachedCodigoElementoCollection.add(codigoElementoCollectionCodigoElementoToAttach);
            }
            usuario.setCodigoElementoCollection(attachedCodigoElementoCollection);
            Collection<CargoPermisoAccion> attachedCargoPermisoAccionCollection = new ArrayList<CargoPermisoAccion>();
            for (CargoPermisoAccion cargoPermisoAccionCollectionCargoPermisoAccionToAttach : usuario.getCargoPermisoAccionCollection()) {
                cargoPermisoAccionCollectionCargoPermisoAccionToAttach = em.getReference(cargoPermisoAccionCollectionCargoPermisoAccionToAttach.getClass(), cargoPermisoAccionCollectionCargoPermisoAccionToAttach.getCargPermAcciId());
                attachedCargoPermisoAccionCollection.add(cargoPermisoAccionCollectionCargoPermisoAccionToAttach);
            }
            usuario.setCargoPermisoAccionCollection(attachedCargoPermisoAccionCollection);
            Collection<ContratoTramo> attachedContratoTramoCollection = new ArrayList<ContratoTramo>();
            for (ContratoTramo contratoTramoCollectionContratoTramoToAttach : usuario.getContratoTramoCollection()) {
                contratoTramoCollectionContratoTramoToAttach = em.getReference(contratoTramoCollectionContratoTramoToAttach.getClass(), contratoTramoCollectionContratoTramoToAttach.getContTramId());
                attachedContratoTramoCollection.add(contratoTramoCollectionContratoTramoToAttach);
            }
            usuario.setContratoTramoCollection(attachedContratoTramoCollection);
            Collection<Accion> attachedAccionCollection = new ArrayList<Accion>();
            for (Accion accionCollectionAccionToAttach : usuario.getAccionCollection()) {
                accionCollectionAccionToAttach = em.getReference(accionCollectionAccionToAttach.getClass(), accionCollectionAccionToAttach.getAcciId());
                attachedAccionCollection.add(accionCollectionAccionToAttach);
            }
            usuario.setAccionCollection(attachedAccionCollection);
            Collection<Cargo> attachedCargoCollection = new ArrayList<Cargo>();
            for (Cargo cargoCollectionCargoToAttach : usuario.getCargoCollection()) {
                cargoCollectionCargoToAttach = em.getReference(cargoCollectionCargoToAttach.getClass(), cargoCollectionCargoToAttach.getCargId());
                attachedCargoCollection.add(cargoCollectionCargoToAttach);
            }
            usuario.setCargoCollection(attachedCargoCollection);
            Collection<Contrato> attachedContratoCollection = new ArrayList<Contrato>();
            for (Contrato contratoCollectionContratoToAttach : usuario.getContratoCollection()) {
                contratoCollectionContratoToAttach = em.getReference(contratoCollectionContratoToAttach.getClass(), contratoCollectionContratoToAttach.getContId());
                attachedContratoCollection.add(contratoCollectionContratoToAttach);
            }
            usuario.setContratoCollection(attachedContratoCollection);
            Collection<ContratoTramoActividad> attachedContratoTramoActividadCollection = new ArrayList<ContratoTramoActividad>();
            for (ContratoTramoActividad contratoTramoActividadCollectionContratoTramoActividadToAttach : usuario.getContratoTramoActividadCollection()) {
                contratoTramoActividadCollectionContratoTramoActividadToAttach = em.getReference(contratoTramoActividadCollectionContratoTramoActividadToAttach.getClass(), contratoTramoActividadCollectionContratoTramoActividadToAttach.getContTramActiId());
                attachedContratoTramoActividadCollection.add(contratoTramoActividadCollectionContratoTramoActividadToAttach);
            }
            usuario.setContratoTramoActividadCollection(attachedContratoTramoActividadCollection);
            Collection<PermisoAccion> attachedPermisoAccionCollection = new ArrayList<PermisoAccion>();
            for (PermisoAccion permisoAccionCollectionPermisoAccionToAttach : usuario.getPermisoAccionCollection()) {
                permisoAccionCollectionPermisoAccionToAttach = em.getReference(permisoAccionCollectionPermisoAccionToAttach.getClass(), permisoAccionCollectionPermisoAccionToAttach.getPermAcciId());
                attachedPermisoAccionCollection.add(permisoAccionCollectionPermisoAccionToAttach);
            }
            usuario.setPermisoAccionCollection(attachedPermisoAccionCollection);
            Collection<Personal> attachedPersonalCollection = new ArrayList<Personal>();
            for (Personal personalCollectionPersonalToAttach : usuario.getPersonalCollection()) {
                personalCollectionPersonalToAttach = em.getReference(personalCollectionPersonalToAttach.getClass(), personalCollectionPersonalToAttach.getPersId());
                attachedPersonalCollection.add(personalCollectionPersonalToAttach);
            }
            usuario.setPersonalCollection(attachedPersonalCollection);
            Collection<Permiso> attachedPermisoCollection = new ArrayList<Permiso>();
            for (Permiso permisoCollectionPermisoToAttach : usuario.getPermisoCollection()) {
                permisoCollectionPermisoToAttach = em.getReference(permisoCollectionPermisoToAttach.getClass(), permisoCollectionPermisoToAttach.getPermId());
                attachedPermisoCollection.add(permisoCollectionPermisoToAttach);
            }
            usuario.setPermisoCollection(attachedPermisoCollection);
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : usuario.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getClieId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            usuario.setClienteCollection(attachedClienteCollection);
            Collection<ContratoTramoPersonal> attachedContratoTramoPersonalCollection = new ArrayList<ContratoTramoPersonal>();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionContratoTramoPersonalToAttach : usuario.getContratoTramoPersonalCollection()) {
                contratoTramoPersonalCollectionContratoTramoPersonalToAttach = em.getReference(contratoTramoPersonalCollectionContratoTramoPersonalToAttach.getClass(), contratoTramoPersonalCollectionContratoTramoPersonalToAttach.getContTramPersId());
                attachedContratoTramoPersonalCollection.add(contratoTramoPersonalCollectionContratoTramoPersonalToAttach);
            }
            usuario.setContratoTramoPersonalCollection(attachedContratoTramoPersonalCollection);
            Collection<TipoActividad> attachedTipoActividadCollection = new ArrayList<TipoActividad>();
            for (TipoActividad tipoActividadCollectionTipoActividadToAttach : usuario.getTipoActividadCollection()) {
                tipoActividadCollectionTipoActividadToAttach = em.getReference(tipoActividadCollectionTipoActividadToAttach.getClass(), tipoActividadCollectionTipoActividadToAttach.getTiacId());
                attachedTipoActividadCollection.add(tipoActividadCollectionTipoActividadToAttach);
            }
            usuario.setTipoActividadCollection(attachedTipoActividadCollection);
            Collection<Elemento> attachedElementoCollection = new ArrayList<Elemento>();
            for (Elemento elementoCollectionElementoToAttach : usuario.getElementoCollection()) {
                elementoCollectionElementoToAttach = em.getReference(elementoCollectionElementoToAttach.getClass(), elementoCollectionElementoToAttach.getElemId());
                attachedElementoCollection.add(elementoCollectionElementoToAttach);
            }
            usuario.setElementoCollection(attachedElementoCollection);
            Collection<ControlActividad> attachedControlActividadCollection = new ArrayList<ControlActividad>();
            for (ControlActividad controlActividadCollectionControlActividadToAttach : usuario.getControlActividadCollection()) {
                controlActividadCollectionControlActividadToAttach = em.getReference(controlActividadCollectionControlActividadToAttach.getClass(), controlActividadCollectionControlActividadToAttach.getCtacId());
                attachedControlActividadCollection.add(controlActividadCollectionControlActividadToAttach);
            }
            usuario.setControlActividadCollection(attachedControlActividadCollection);
            Collection<Tramo> attachedTramoCollection = new ArrayList<Tramo>();
            for (Tramo tramoCollectionTramoToAttach : usuario.getTramoCollection()) {
                tramoCollectionTramoToAttach = em.getReference(tramoCollectionTramoToAttach.getClass(), tramoCollectionTramoToAttach.getTramId());
                attachedTramoCollection.add(tramoCollectionTramoToAttach);
            }
            usuario.setTramoCollection(attachedTramoCollection);
            em.persist(usuario);
            if (userUserId != null) {
                userUserId.getUsuarioCollection().add(usuario);
                userUserId = em.merge(userUserId);
            }
            if (persId != null) {
                persId.setUsuario(usuario);
                persId = em.merge(persId);
            }
            for (Sector sectorCollectionSector : usuario.getSectorCollection()) {
                Usuario oldUserIdOfSectorCollectionSector = sectorCollectionSector.getUserId();
                sectorCollectionSector.setUserId(usuario);
                sectorCollectionSector = em.merge(sectorCollectionSector);
                if (oldUserIdOfSectorCollectionSector != null) {
                    oldUserIdOfSectorCollectionSector.getSectorCollection().remove(sectorCollectionSector);
                    oldUserIdOfSectorCollectionSector = em.merge(oldUserIdOfSectorCollectionSector);
                }
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElemento : usuario.getTramoCodigoElementoCollection()) {
                Usuario oldUserIdOfTramoCodigoElementoCollectionTramoCodigoElemento = tramoCodigoElementoCollectionTramoCodigoElemento.getUserId();
                tramoCodigoElementoCollectionTramoCodigoElemento.setUserId(usuario);
                tramoCodigoElementoCollectionTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionTramoCodigoElemento);
                if (oldUserIdOfTramoCodigoElementoCollectionTramoCodigoElemento != null) {
                    oldUserIdOfTramoCodigoElementoCollectionTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionTramoCodigoElemento);
                    oldUserIdOfTramoCodigoElementoCollectionTramoCodigoElemento = em.merge(oldUserIdOfTramoCodigoElementoCollectionTramoCodigoElemento);
                }
            }
            for (ClienteTipo clienteTipoCollectionClienteTipo : usuario.getClienteTipoCollection()) {
                Usuario oldUserIdOfClienteTipoCollectionClienteTipo = clienteTipoCollectionClienteTipo.getUserId();
                clienteTipoCollectionClienteTipo.setUserId(usuario);
                clienteTipoCollectionClienteTipo = em.merge(clienteTipoCollectionClienteTipo);
                if (oldUserIdOfClienteTipoCollectionClienteTipo != null) {
                    oldUserIdOfClienteTipoCollectionClienteTipo.getClienteTipoCollection().remove(clienteTipoCollectionClienteTipo);
                    oldUserIdOfClienteTipoCollectionClienteTipo = em.merge(oldUserIdOfClienteTipoCollectionClienteTipo);
                }
            }
            for (Usuario usuarioCollectionUsuario : usuario.getUsuarioCollection()) {
                Usuario oldUserUserIdOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getUserUserId();
                usuarioCollectionUsuario.setUserUserId(usuario);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldUserUserIdOfUsuarioCollectionUsuario != null) {
                    oldUserUserIdOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldUserUserIdOfUsuarioCollectionUsuario = em.merge(oldUserUserIdOfUsuarioCollectionUsuario);
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalle : usuario.getControlActividadDetalleCollection()) {
                Usuario oldUserIdOfControlActividadDetalleCollectionControlActividadDetalle = controlActividadDetalleCollectionControlActividadDetalle.getUserId();
                controlActividadDetalleCollectionControlActividadDetalle.setUserId(usuario);
                controlActividadDetalleCollectionControlActividadDetalle = em.merge(controlActividadDetalleCollectionControlActividadDetalle);
                if (oldUserIdOfControlActividadDetalleCollectionControlActividadDetalle != null) {
                    oldUserIdOfControlActividadDetalleCollectionControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionControlActividadDetalle);
                    oldUserIdOfControlActividadDetalleCollectionControlActividadDetalle = em.merge(oldUserIdOfControlActividadDetalleCollectionControlActividadDetalle);
                }
            }
            for (Actividad actividadCollectionActividad : usuario.getActividadCollection()) {
                Usuario oldUserIdOfActividadCollectionActividad = actividadCollectionActividad.getUserId();
                actividadCollectionActividad.setUserId(usuario);
                actividadCollectionActividad = em.merge(actividadCollectionActividad);
                if (oldUserIdOfActividadCollectionActividad != null) {
                    oldUserIdOfActividadCollectionActividad.getActividadCollection().remove(actividadCollectionActividad);
                    oldUserIdOfActividadCollectionActividad = em.merge(oldUserIdOfActividadCollectionActividad);
                }
            }
            for (CodigoElemento codigoElementoCollectionCodigoElemento : usuario.getCodigoElementoCollection()) {
                Usuario oldUserIdOfCodigoElementoCollectionCodigoElemento = codigoElementoCollectionCodigoElemento.getUserId();
                codigoElementoCollectionCodigoElemento.setUserId(usuario);
                codigoElementoCollectionCodigoElemento = em.merge(codigoElementoCollectionCodigoElemento);
                if (oldUserIdOfCodigoElementoCollectionCodigoElemento != null) {
                    oldUserIdOfCodigoElementoCollectionCodigoElemento.getCodigoElementoCollection().remove(codigoElementoCollectionCodigoElemento);
                    oldUserIdOfCodigoElementoCollectionCodigoElemento = em.merge(oldUserIdOfCodigoElementoCollectionCodigoElemento);
                }
            }
            for (CargoPermisoAccion cargoPermisoAccionCollectionCargoPermisoAccion : usuario.getCargoPermisoAccionCollection()) {
                Usuario oldUserIdOfCargoPermisoAccionCollectionCargoPermisoAccion = cargoPermisoAccionCollectionCargoPermisoAccion.getUserId();
                cargoPermisoAccionCollectionCargoPermisoAccion.setUserId(usuario);
                cargoPermisoAccionCollectionCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionCargoPermisoAccion);
                if (oldUserIdOfCargoPermisoAccionCollectionCargoPermisoAccion != null) {
                    oldUserIdOfCargoPermisoAccionCollectionCargoPermisoAccion.getCargoPermisoAccionCollection().remove(cargoPermisoAccionCollectionCargoPermisoAccion);
                    oldUserIdOfCargoPermisoAccionCollectionCargoPermisoAccion = em.merge(oldUserIdOfCargoPermisoAccionCollectionCargoPermisoAccion);
                }
            }
            for (ContratoTramo contratoTramoCollectionContratoTramo : usuario.getContratoTramoCollection()) {
                Usuario oldUserIdOfContratoTramoCollectionContratoTramo = contratoTramoCollectionContratoTramo.getUserId();
                contratoTramoCollectionContratoTramo.setUserId(usuario);
                contratoTramoCollectionContratoTramo = em.merge(contratoTramoCollectionContratoTramo);
                if (oldUserIdOfContratoTramoCollectionContratoTramo != null) {
                    oldUserIdOfContratoTramoCollectionContratoTramo.getContratoTramoCollection().remove(contratoTramoCollectionContratoTramo);
                    oldUserIdOfContratoTramoCollectionContratoTramo = em.merge(oldUserIdOfContratoTramoCollectionContratoTramo);
                }
            }
            for (Accion accionCollectionAccion : usuario.getAccionCollection()) {
                Usuario oldUserIdOfAccionCollectionAccion = accionCollectionAccion.getUserId();
                accionCollectionAccion.setUserId(usuario);
                accionCollectionAccion = em.merge(accionCollectionAccion);
                if (oldUserIdOfAccionCollectionAccion != null) {
                    oldUserIdOfAccionCollectionAccion.getAccionCollection().remove(accionCollectionAccion);
                    oldUserIdOfAccionCollectionAccion = em.merge(oldUserIdOfAccionCollectionAccion);
                }
            }
            for (Cargo cargoCollectionCargo : usuario.getCargoCollection()) {
                Usuario oldUserIdOfCargoCollectionCargo = cargoCollectionCargo.getUserId();
                cargoCollectionCargo.setUserId(usuario);
                cargoCollectionCargo = em.merge(cargoCollectionCargo);
                if (oldUserIdOfCargoCollectionCargo != null) {
                    oldUserIdOfCargoCollectionCargo.getCargoCollection().remove(cargoCollectionCargo);
                    oldUserIdOfCargoCollectionCargo = em.merge(oldUserIdOfCargoCollectionCargo);
                }
            }
            for (Contrato contratoCollectionContrato : usuario.getContratoCollection()) {
                Usuario oldUserIdOfContratoCollectionContrato = contratoCollectionContrato.getUserId();
                contratoCollectionContrato.setUserId(usuario);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
                if (oldUserIdOfContratoCollectionContrato != null) {
                    oldUserIdOfContratoCollectionContrato.getContratoCollection().remove(contratoCollectionContrato);
                    oldUserIdOfContratoCollectionContrato = em.merge(oldUserIdOfContratoCollectionContrato);
                }
            }
            for (ContratoTramoActividad contratoTramoActividadCollectionContratoTramoActividad : usuario.getContratoTramoActividadCollection()) {
                Usuario oldUserIdOfContratoTramoActividadCollectionContratoTramoActividad = contratoTramoActividadCollectionContratoTramoActividad.getUserId();
                contratoTramoActividadCollectionContratoTramoActividad.setUserId(usuario);
                contratoTramoActividadCollectionContratoTramoActividad = em.merge(contratoTramoActividadCollectionContratoTramoActividad);
                if (oldUserIdOfContratoTramoActividadCollectionContratoTramoActividad != null) {
                    oldUserIdOfContratoTramoActividadCollectionContratoTramoActividad.getContratoTramoActividadCollection().remove(contratoTramoActividadCollectionContratoTramoActividad);
                    oldUserIdOfContratoTramoActividadCollectionContratoTramoActividad = em.merge(oldUserIdOfContratoTramoActividadCollectionContratoTramoActividad);
                }
            }
            for (PermisoAccion permisoAccionCollectionPermisoAccion : usuario.getPermisoAccionCollection()) {
                Usuario oldUserIdOfPermisoAccionCollectionPermisoAccion = permisoAccionCollectionPermisoAccion.getUserId();
                permisoAccionCollectionPermisoAccion.setUserId(usuario);
                permisoAccionCollectionPermisoAccion = em.merge(permisoAccionCollectionPermisoAccion);
                if (oldUserIdOfPermisoAccionCollectionPermisoAccion != null) {
                    oldUserIdOfPermisoAccionCollectionPermisoAccion.getPermisoAccionCollection().remove(permisoAccionCollectionPermisoAccion);
                    oldUserIdOfPermisoAccionCollectionPermisoAccion = em.merge(oldUserIdOfPermisoAccionCollectionPermisoAccion);
                }
            }
            for (Personal personalCollectionPersonal : usuario.getPersonalCollection()) {
                Usuario oldUserIdOfPersonalCollectionPersonal = personalCollectionPersonal.getUserId();
                personalCollectionPersonal.setUserId(usuario);
                personalCollectionPersonal = em.merge(personalCollectionPersonal);
                if (oldUserIdOfPersonalCollectionPersonal != null) {
                    oldUserIdOfPersonalCollectionPersonal.getPersonalCollection().remove(personalCollectionPersonal);
                    oldUserIdOfPersonalCollectionPersonal = em.merge(oldUserIdOfPersonalCollectionPersonal);
                }
            }
            for (Permiso permisoCollectionPermiso : usuario.getPermisoCollection()) {
                Usuario oldUserIdOfPermisoCollectionPermiso = permisoCollectionPermiso.getUserId();
                permisoCollectionPermiso.setUserId(usuario);
                permisoCollectionPermiso = em.merge(permisoCollectionPermiso);
                if (oldUserIdOfPermisoCollectionPermiso != null) {
                    oldUserIdOfPermisoCollectionPermiso.getPermisoCollection().remove(permisoCollectionPermiso);
                    oldUserIdOfPermisoCollectionPermiso = em.merge(oldUserIdOfPermisoCollectionPermiso);
                }
            }
            for (Cliente clienteCollectionCliente : usuario.getClienteCollection()) {
                Usuario oldUserIdOfClienteCollectionCliente = clienteCollectionCliente.getUserId();
                clienteCollectionCliente.setUserId(usuario);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldUserIdOfClienteCollectionCliente != null) {
                    oldUserIdOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldUserIdOfClienteCollectionCliente = em.merge(oldUserIdOfClienteCollectionCliente);
                }
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionContratoTramoPersonal : usuario.getContratoTramoPersonalCollection()) {
                Usuario oldUserIdOfContratoTramoPersonalCollectionContratoTramoPersonal = contratoTramoPersonalCollectionContratoTramoPersonal.getUserId();
                contratoTramoPersonalCollectionContratoTramoPersonal.setUserId(usuario);
                contratoTramoPersonalCollectionContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionContratoTramoPersonal);
                if (oldUserIdOfContratoTramoPersonalCollectionContratoTramoPersonal != null) {
                    oldUserIdOfContratoTramoPersonalCollectionContratoTramoPersonal.getContratoTramoPersonalCollection().remove(contratoTramoPersonalCollectionContratoTramoPersonal);
                    oldUserIdOfContratoTramoPersonalCollectionContratoTramoPersonal = em.merge(oldUserIdOfContratoTramoPersonalCollectionContratoTramoPersonal);
                }
            }
            for (TipoActividad tipoActividadCollectionTipoActividad : usuario.getTipoActividadCollection()) {
                Usuario oldUserIdOfTipoActividadCollectionTipoActividad = tipoActividadCollectionTipoActividad.getUserId();
                tipoActividadCollectionTipoActividad.setUserId(usuario);
                tipoActividadCollectionTipoActividad = em.merge(tipoActividadCollectionTipoActividad);
                if (oldUserIdOfTipoActividadCollectionTipoActividad != null) {
                    oldUserIdOfTipoActividadCollectionTipoActividad.getTipoActividadCollection().remove(tipoActividadCollectionTipoActividad);
                    oldUserIdOfTipoActividadCollectionTipoActividad = em.merge(oldUserIdOfTipoActividadCollectionTipoActividad);
                }
            }
            for (Elemento elementoCollectionElemento : usuario.getElementoCollection()) {
                Usuario oldUserIdOfElementoCollectionElemento = elementoCollectionElemento.getUserId();
                elementoCollectionElemento.setUserId(usuario);
                elementoCollectionElemento = em.merge(elementoCollectionElemento);
                if (oldUserIdOfElementoCollectionElemento != null) {
                    oldUserIdOfElementoCollectionElemento.getElementoCollection().remove(elementoCollectionElemento);
                    oldUserIdOfElementoCollectionElemento = em.merge(oldUserIdOfElementoCollectionElemento);
                }
            }
            for (ControlActividad controlActividadCollectionControlActividad : usuario.getControlActividadCollection()) {
                Usuario oldUserIdOfControlActividadCollectionControlActividad = controlActividadCollectionControlActividad.getUserId();
                controlActividadCollectionControlActividad.setUserId(usuario);
                controlActividadCollectionControlActividad = em.merge(controlActividadCollectionControlActividad);
                if (oldUserIdOfControlActividadCollectionControlActividad != null) {
                    oldUserIdOfControlActividadCollectionControlActividad.getControlActividadCollection().remove(controlActividadCollectionControlActividad);
                    oldUserIdOfControlActividadCollectionControlActividad = em.merge(oldUserIdOfControlActividadCollectionControlActividad);
                }
            }
            for (Tramo tramoCollectionTramo : usuario.getTramoCollection()) {
                Usuario oldUserIdOfTramoCollectionTramo = tramoCollectionTramo.getUserId();
                tramoCollectionTramo.setUserId(usuario);
                tramoCollectionTramo = em.merge(tramoCollectionTramo);
                if (oldUserIdOfTramoCollectionTramo != null) {
                    oldUserIdOfTramoCollectionTramo.getTramoCollection().remove(tramoCollectionTramo);
                    oldUserIdOfTramoCollectionTramo = em.merge(oldUserIdOfTramoCollectionTramo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUserId());
            Usuario userUserIdOld = persistentUsuario.getUserUserId();
            Usuario userUserIdNew = usuario.getUserUserId();
            Personal persIdOld = persistentUsuario.getPersId();
            Personal persIdNew = usuario.getPersId();
            Collection<Sector> sectorCollectionOld = persistentUsuario.getSectorCollection();
            Collection<Sector> sectorCollectionNew = usuario.getSectorCollection();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionOld = persistentUsuario.getTramoCodigoElementoCollection();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionNew = usuario.getTramoCodigoElementoCollection();
            Collection<ClienteTipo> clienteTipoCollectionOld = persistentUsuario.getClienteTipoCollection();
            Collection<ClienteTipo> clienteTipoCollectionNew = usuario.getClienteTipoCollection();
            Collection<Usuario> usuarioCollectionOld = persistentUsuario.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = usuario.getUsuarioCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOld = persistentUsuario.getControlActividadDetalleCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionNew = usuario.getControlActividadDetalleCollection();
            Collection<Actividad> actividadCollectionOld = persistentUsuario.getActividadCollection();
            Collection<Actividad> actividadCollectionNew = usuario.getActividadCollection();
            Collection<CodigoElemento> codigoElementoCollectionOld = persistentUsuario.getCodigoElementoCollection();
            Collection<CodigoElemento> codigoElementoCollectionNew = usuario.getCodigoElementoCollection();
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionOld = persistentUsuario.getCargoPermisoAccionCollection();
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionNew = usuario.getCargoPermisoAccionCollection();
            Collection<ContratoTramo> contratoTramoCollectionOld = persistentUsuario.getContratoTramoCollection();
            Collection<ContratoTramo> contratoTramoCollectionNew = usuario.getContratoTramoCollection();
            Collection<Accion> accionCollectionOld = persistentUsuario.getAccionCollection();
            Collection<Accion> accionCollectionNew = usuario.getAccionCollection();
            Collection<Cargo> cargoCollectionOld = persistentUsuario.getCargoCollection();
            Collection<Cargo> cargoCollectionNew = usuario.getCargoCollection();
            Collection<Contrato> contratoCollectionOld = persistentUsuario.getContratoCollection();
            Collection<Contrato> contratoCollectionNew = usuario.getContratoCollection();
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionOld = persistentUsuario.getContratoTramoActividadCollection();
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionNew = usuario.getContratoTramoActividadCollection();
            Collection<PermisoAccion> permisoAccionCollectionOld = persistentUsuario.getPermisoAccionCollection();
            Collection<PermisoAccion> permisoAccionCollectionNew = usuario.getPermisoAccionCollection();
            Collection<Personal> personalCollectionOld = persistentUsuario.getPersonalCollection();
            Collection<Personal> personalCollectionNew = usuario.getPersonalCollection();
            Collection<Permiso> permisoCollectionOld = persistentUsuario.getPermisoCollection();
            Collection<Permiso> permisoCollectionNew = usuario.getPermisoCollection();
            Collection<Cliente> clienteCollectionOld = persistentUsuario.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = usuario.getClienteCollection();
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionOld = persistentUsuario.getContratoTramoPersonalCollection();
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionNew = usuario.getContratoTramoPersonalCollection();
            Collection<TipoActividad> tipoActividadCollectionOld = persistentUsuario.getTipoActividadCollection();
            Collection<TipoActividad> tipoActividadCollectionNew = usuario.getTipoActividadCollection();
            Collection<Elemento> elementoCollectionOld = persistentUsuario.getElementoCollection();
            Collection<Elemento> elementoCollectionNew = usuario.getElementoCollection();
            Collection<ControlActividad> controlActividadCollectionOld = persistentUsuario.getControlActividadCollection();
            Collection<ControlActividad> controlActividadCollectionNew = usuario.getControlActividadCollection();
            Collection<Tramo> tramoCollectionOld = persistentUsuario.getTramoCollection();
            Collection<Tramo> tramoCollectionNew = usuario.getTramoCollection();
            List<String> illegalOrphanMessages = null;
            if (persIdNew != null && !persIdNew.equals(persIdOld)) {
                Usuario oldUsuarioOfPersId = persIdNew.getUsuario();
                if (oldUsuarioOfPersId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Personal " + persIdNew + " already has an item of type Usuario whose persId column cannot be null. Please make another selection for the persId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userUserIdNew != null) {
                userUserIdNew = em.getReference(userUserIdNew.getClass(), userUserIdNew.getUserId());
                usuario.setUserUserId(userUserIdNew);
            }
            if (persIdNew != null) {
                persIdNew = em.getReference(persIdNew.getClass(), persIdNew.getPersId());
                usuario.setPersId(persIdNew);
            }
            Collection<Sector> attachedSectorCollectionNew = new ArrayList<Sector>();
            for (Sector sectorCollectionNewSectorToAttach : sectorCollectionNew) {
                sectorCollectionNewSectorToAttach = em.getReference(sectorCollectionNewSectorToAttach.getClass(), sectorCollectionNewSectorToAttach.getSectId());
                attachedSectorCollectionNew.add(sectorCollectionNewSectorToAttach);
            }
            sectorCollectionNew = attachedSectorCollectionNew;
            usuario.setSectorCollection(sectorCollectionNew);
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollectionNew = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach : tramoCodigoElementoCollectionNew) {
                tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollectionNew.add(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach);
            }
            tramoCodigoElementoCollectionNew = attachedTramoCodigoElementoCollectionNew;
            usuario.setTramoCodigoElementoCollection(tramoCodigoElementoCollectionNew);
            Collection<ClienteTipo> attachedClienteTipoCollectionNew = new ArrayList<ClienteTipo>();
            for (ClienteTipo clienteTipoCollectionNewClienteTipoToAttach : clienteTipoCollectionNew) {
                clienteTipoCollectionNewClienteTipoToAttach = em.getReference(clienteTipoCollectionNewClienteTipoToAttach.getClass(), clienteTipoCollectionNewClienteTipoToAttach.getCltiId());
                attachedClienteTipoCollectionNew.add(clienteTipoCollectionNewClienteTipoToAttach);
            }
            clienteTipoCollectionNew = attachedClienteTipoCollectionNew;
            usuario.setClienteTipoCollection(clienteTipoCollectionNew);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getUserId());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            usuario.setUsuarioCollection(usuarioCollectionNew);
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollectionNew = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalleToAttach : controlActividadDetalleCollectionNew) {
                controlActividadDetalleCollectionNewControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollectionNew.add(controlActividadDetalleCollectionNewControlActividadDetalleToAttach);
            }
            controlActividadDetalleCollectionNew = attachedControlActividadDetalleCollectionNew;
            usuario.setControlActividadDetalleCollection(controlActividadDetalleCollectionNew);
            Collection<Actividad> attachedActividadCollectionNew = new ArrayList<Actividad>();
            for (Actividad actividadCollectionNewActividadToAttach : actividadCollectionNew) {
                actividadCollectionNewActividadToAttach = em.getReference(actividadCollectionNewActividadToAttach.getClass(), actividadCollectionNewActividadToAttach.getActiId());
                attachedActividadCollectionNew.add(actividadCollectionNewActividadToAttach);
            }
            actividadCollectionNew = attachedActividadCollectionNew;
            usuario.setActividadCollection(actividadCollectionNew);
            Collection<CodigoElemento> attachedCodigoElementoCollectionNew = new ArrayList<CodigoElemento>();
            for (CodigoElemento codigoElementoCollectionNewCodigoElementoToAttach : codigoElementoCollectionNew) {
                codigoElementoCollectionNewCodigoElementoToAttach = em.getReference(codigoElementoCollectionNewCodigoElementoToAttach.getClass(), codigoElementoCollectionNewCodigoElementoToAttach.getCoelId());
                attachedCodigoElementoCollectionNew.add(codigoElementoCollectionNewCodigoElementoToAttach);
            }
            codigoElementoCollectionNew = attachedCodigoElementoCollectionNew;
            usuario.setCodigoElementoCollection(codigoElementoCollectionNew);
            Collection<CargoPermisoAccion> attachedCargoPermisoAccionCollectionNew = new ArrayList<CargoPermisoAccion>();
            for (CargoPermisoAccion cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach : cargoPermisoAccionCollectionNew) {
                cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach = em.getReference(cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach.getClass(), cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach.getCargPermAcciId());
                attachedCargoPermisoAccionCollectionNew.add(cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach);
            }
            cargoPermisoAccionCollectionNew = attachedCargoPermisoAccionCollectionNew;
            usuario.setCargoPermisoAccionCollection(cargoPermisoAccionCollectionNew);
            Collection<ContratoTramo> attachedContratoTramoCollectionNew = new ArrayList<ContratoTramo>();
            for (ContratoTramo contratoTramoCollectionNewContratoTramoToAttach : contratoTramoCollectionNew) {
                contratoTramoCollectionNewContratoTramoToAttach = em.getReference(contratoTramoCollectionNewContratoTramoToAttach.getClass(), contratoTramoCollectionNewContratoTramoToAttach.getContTramId());
                attachedContratoTramoCollectionNew.add(contratoTramoCollectionNewContratoTramoToAttach);
            }
            contratoTramoCollectionNew = attachedContratoTramoCollectionNew;
            usuario.setContratoTramoCollection(contratoTramoCollectionNew);
            Collection<Accion> attachedAccionCollectionNew = new ArrayList<Accion>();
            for (Accion accionCollectionNewAccionToAttach : accionCollectionNew) {
                accionCollectionNewAccionToAttach = em.getReference(accionCollectionNewAccionToAttach.getClass(), accionCollectionNewAccionToAttach.getAcciId());
                attachedAccionCollectionNew.add(accionCollectionNewAccionToAttach);
            }
            accionCollectionNew = attachedAccionCollectionNew;
            usuario.setAccionCollection(accionCollectionNew);
            Collection<Cargo> attachedCargoCollectionNew = new ArrayList<Cargo>();
            for (Cargo cargoCollectionNewCargoToAttach : cargoCollectionNew) {
                cargoCollectionNewCargoToAttach = em.getReference(cargoCollectionNewCargoToAttach.getClass(), cargoCollectionNewCargoToAttach.getCargId());
                attachedCargoCollectionNew.add(cargoCollectionNewCargoToAttach);
            }
            cargoCollectionNew = attachedCargoCollectionNew;
            usuario.setCargoCollection(cargoCollectionNew);
            Collection<Contrato> attachedContratoCollectionNew = new ArrayList<Contrato>();
            for (Contrato contratoCollectionNewContratoToAttach : contratoCollectionNew) {
                contratoCollectionNewContratoToAttach = em.getReference(contratoCollectionNewContratoToAttach.getClass(), contratoCollectionNewContratoToAttach.getContId());
                attachedContratoCollectionNew.add(contratoCollectionNewContratoToAttach);
            }
            contratoCollectionNew = attachedContratoCollectionNew;
            usuario.setContratoCollection(contratoCollectionNew);
            Collection<ContratoTramoActividad> attachedContratoTramoActividadCollectionNew = new ArrayList<ContratoTramoActividad>();
            for (ContratoTramoActividad contratoTramoActividadCollectionNewContratoTramoActividadToAttach : contratoTramoActividadCollectionNew) {
                contratoTramoActividadCollectionNewContratoTramoActividadToAttach = em.getReference(contratoTramoActividadCollectionNewContratoTramoActividadToAttach.getClass(), contratoTramoActividadCollectionNewContratoTramoActividadToAttach.getContTramActiId());
                attachedContratoTramoActividadCollectionNew.add(contratoTramoActividadCollectionNewContratoTramoActividadToAttach);
            }
            contratoTramoActividadCollectionNew = attachedContratoTramoActividadCollectionNew;
            usuario.setContratoTramoActividadCollection(contratoTramoActividadCollectionNew);
            Collection<PermisoAccion> attachedPermisoAccionCollectionNew = new ArrayList<PermisoAccion>();
            for (PermisoAccion permisoAccionCollectionNewPermisoAccionToAttach : permisoAccionCollectionNew) {
                permisoAccionCollectionNewPermisoAccionToAttach = em.getReference(permisoAccionCollectionNewPermisoAccionToAttach.getClass(), permisoAccionCollectionNewPermisoAccionToAttach.getPermAcciId());
                attachedPermisoAccionCollectionNew.add(permisoAccionCollectionNewPermisoAccionToAttach);
            }
            permisoAccionCollectionNew = attachedPermisoAccionCollectionNew;
            usuario.setPermisoAccionCollection(permisoAccionCollectionNew);
            Collection<Personal> attachedPersonalCollectionNew = new ArrayList<Personal>();
            for (Personal personalCollectionNewPersonalToAttach : personalCollectionNew) {
                personalCollectionNewPersonalToAttach = em.getReference(personalCollectionNewPersonalToAttach.getClass(), personalCollectionNewPersonalToAttach.getPersId());
                attachedPersonalCollectionNew.add(personalCollectionNewPersonalToAttach);
            }
            personalCollectionNew = attachedPersonalCollectionNew;
            usuario.setPersonalCollection(personalCollectionNew);
            Collection<Permiso> attachedPermisoCollectionNew = new ArrayList<Permiso>();
            for (Permiso permisoCollectionNewPermisoToAttach : permisoCollectionNew) {
                permisoCollectionNewPermisoToAttach = em.getReference(permisoCollectionNewPermisoToAttach.getClass(), permisoCollectionNewPermisoToAttach.getPermId());
                attachedPermisoCollectionNew.add(permisoCollectionNewPermisoToAttach);
            }
            permisoCollectionNew = attachedPermisoCollectionNew;
            usuario.setPermisoCollection(permisoCollectionNew);
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getClieId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            usuario.setClienteCollection(clienteCollectionNew);
            Collection<ContratoTramoPersonal> attachedContratoTramoPersonalCollectionNew = new ArrayList<ContratoTramoPersonal>();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach : contratoTramoPersonalCollectionNew) {
                contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach = em.getReference(contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach.getClass(), contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach.getContTramPersId());
                attachedContratoTramoPersonalCollectionNew.add(contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach);
            }
            contratoTramoPersonalCollectionNew = attachedContratoTramoPersonalCollectionNew;
            usuario.setContratoTramoPersonalCollection(contratoTramoPersonalCollectionNew);
            Collection<TipoActividad> attachedTipoActividadCollectionNew = new ArrayList<TipoActividad>();
            for (TipoActividad tipoActividadCollectionNewTipoActividadToAttach : tipoActividadCollectionNew) {
                tipoActividadCollectionNewTipoActividadToAttach = em.getReference(tipoActividadCollectionNewTipoActividadToAttach.getClass(), tipoActividadCollectionNewTipoActividadToAttach.getTiacId());
                attachedTipoActividadCollectionNew.add(tipoActividadCollectionNewTipoActividadToAttach);
            }
            tipoActividadCollectionNew = attachedTipoActividadCollectionNew;
            usuario.setTipoActividadCollection(tipoActividadCollectionNew);
            Collection<Elemento> attachedElementoCollectionNew = new ArrayList<Elemento>();
            for (Elemento elementoCollectionNewElementoToAttach : elementoCollectionNew) {
                elementoCollectionNewElementoToAttach = em.getReference(elementoCollectionNewElementoToAttach.getClass(), elementoCollectionNewElementoToAttach.getElemId());
                attachedElementoCollectionNew.add(elementoCollectionNewElementoToAttach);
            }
            elementoCollectionNew = attachedElementoCollectionNew;
            usuario.setElementoCollection(elementoCollectionNew);
            Collection<ControlActividad> attachedControlActividadCollectionNew = new ArrayList<ControlActividad>();
            for (ControlActividad controlActividadCollectionNewControlActividadToAttach : controlActividadCollectionNew) {
                controlActividadCollectionNewControlActividadToAttach = em.getReference(controlActividadCollectionNewControlActividadToAttach.getClass(), controlActividadCollectionNewControlActividadToAttach.getCtacId());
                attachedControlActividadCollectionNew.add(controlActividadCollectionNewControlActividadToAttach);
            }
            controlActividadCollectionNew = attachedControlActividadCollectionNew;
            usuario.setControlActividadCollection(controlActividadCollectionNew);
            Collection<Tramo> attachedTramoCollectionNew = new ArrayList<Tramo>();
            for (Tramo tramoCollectionNewTramoToAttach : tramoCollectionNew) {
                tramoCollectionNewTramoToAttach = em.getReference(tramoCollectionNewTramoToAttach.getClass(), tramoCollectionNewTramoToAttach.getTramId());
                attachedTramoCollectionNew.add(tramoCollectionNewTramoToAttach);
            }
            tramoCollectionNew = attachedTramoCollectionNew;
            usuario.setTramoCollection(tramoCollectionNew);
            usuario = em.merge(usuario);
            if (userUserIdOld != null && !userUserIdOld.equals(userUserIdNew)) {
                userUserIdOld.getUsuarioCollection().remove(usuario);
                userUserIdOld = em.merge(userUserIdOld);
            }
            if (userUserIdNew != null && !userUserIdNew.equals(userUserIdOld)) {
                userUserIdNew.getUsuarioCollection().add(usuario);
                userUserIdNew = em.merge(userUserIdNew);
            }
            if (persIdOld != null && !persIdOld.equals(persIdNew)) {
                persIdOld.setUsuario(null);
                persIdOld = em.merge(persIdOld);
            }
            if (persIdNew != null && !persIdNew.equals(persIdOld)) {
                persIdNew.setUsuario(usuario);
                persIdNew = em.merge(persIdNew);
            }
            for (Sector sectorCollectionOldSector : sectorCollectionOld) {
                if (!sectorCollectionNew.contains(sectorCollectionOldSector)) {
                    sectorCollectionOldSector.setUserId(null);
                    sectorCollectionOldSector = em.merge(sectorCollectionOldSector);
                }
            }
            for (Sector sectorCollectionNewSector : sectorCollectionNew) {
                if (!sectorCollectionOld.contains(sectorCollectionNewSector)) {
                    Usuario oldUserIdOfSectorCollectionNewSector = sectorCollectionNewSector.getUserId();
                    sectorCollectionNewSector.setUserId(usuario);
                    sectorCollectionNewSector = em.merge(sectorCollectionNewSector);
                    if (oldUserIdOfSectorCollectionNewSector != null && !oldUserIdOfSectorCollectionNewSector.equals(usuario)) {
                        oldUserIdOfSectorCollectionNewSector.getSectorCollection().remove(sectorCollectionNewSector);
                        oldUserIdOfSectorCollectionNewSector = em.merge(oldUserIdOfSectorCollectionNewSector);
                    }
                }
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionOldTramoCodigoElemento : tramoCodigoElementoCollectionOld) {
                if (!tramoCodigoElementoCollectionNew.contains(tramoCodigoElementoCollectionOldTramoCodigoElemento)) {
                    tramoCodigoElementoCollectionOldTramoCodigoElemento.setUserId(null);
                    tramoCodigoElementoCollectionOldTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionOldTramoCodigoElemento);
                }
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElemento : tramoCodigoElementoCollectionNew) {
                if (!tramoCodigoElementoCollectionOld.contains(tramoCodigoElementoCollectionNewTramoCodigoElemento)) {
                    Usuario oldUserIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = tramoCodigoElementoCollectionNewTramoCodigoElemento.getUserId();
                    tramoCodigoElementoCollectionNewTramoCodigoElemento.setUserId(usuario);
                    tramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                    if (oldUserIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento != null && !oldUserIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.equals(usuario)) {
                        oldUserIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                        oldUserIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(oldUserIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento);
                    }
                }
            }
            for (ClienteTipo clienteTipoCollectionOldClienteTipo : clienteTipoCollectionOld) {
                if (!clienteTipoCollectionNew.contains(clienteTipoCollectionOldClienteTipo)) {
                    clienteTipoCollectionOldClienteTipo.setUserId(null);
                    clienteTipoCollectionOldClienteTipo = em.merge(clienteTipoCollectionOldClienteTipo);
                }
            }
            for (ClienteTipo clienteTipoCollectionNewClienteTipo : clienteTipoCollectionNew) {
                if (!clienteTipoCollectionOld.contains(clienteTipoCollectionNewClienteTipo)) {
                    Usuario oldUserIdOfClienteTipoCollectionNewClienteTipo = clienteTipoCollectionNewClienteTipo.getUserId();
                    clienteTipoCollectionNewClienteTipo.setUserId(usuario);
                    clienteTipoCollectionNewClienteTipo = em.merge(clienteTipoCollectionNewClienteTipo);
                    if (oldUserIdOfClienteTipoCollectionNewClienteTipo != null && !oldUserIdOfClienteTipoCollectionNewClienteTipo.equals(usuario)) {
                        oldUserIdOfClienteTipoCollectionNewClienteTipo.getClienteTipoCollection().remove(clienteTipoCollectionNewClienteTipo);
                        oldUserIdOfClienteTipoCollectionNewClienteTipo = em.merge(oldUserIdOfClienteTipoCollectionNewClienteTipo);
                    }
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.setUserUserId(null);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Usuario oldUserUserIdOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getUserUserId();
                    usuarioCollectionNewUsuario.setUserUserId(usuario);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldUserUserIdOfUsuarioCollectionNewUsuario != null && !oldUserUserIdOfUsuarioCollectionNewUsuario.equals(usuario)) {
                        oldUserUserIdOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldUserUserIdOfUsuarioCollectionNewUsuario = em.merge(oldUserUserIdOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionOldControlActividadDetalle : controlActividadDetalleCollectionOld) {
                if (!controlActividadDetalleCollectionNew.contains(controlActividadDetalleCollectionOldControlActividadDetalle)) {
                    controlActividadDetalleCollectionOldControlActividadDetalle.setUserId(null);
                    controlActividadDetalleCollectionOldControlActividadDetalle = em.merge(controlActividadDetalleCollectionOldControlActividadDetalle);
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalle : controlActividadDetalleCollectionNew) {
                if (!controlActividadDetalleCollectionOld.contains(controlActividadDetalleCollectionNewControlActividadDetalle)) {
                    Usuario oldUserIdOfControlActividadDetalleCollectionNewControlActividadDetalle = controlActividadDetalleCollectionNewControlActividadDetalle.getUserId();
                    controlActividadDetalleCollectionNewControlActividadDetalle.setUserId(usuario);
                    controlActividadDetalleCollectionNewControlActividadDetalle = em.merge(controlActividadDetalleCollectionNewControlActividadDetalle);
                    if (oldUserIdOfControlActividadDetalleCollectionNewControlActividadDetalle != null && !oldUserIdOfControlActividadDetalleCollectionNewControlActividadDetalle.equals(usuario)) {
                        oldUserIdOfControlActividadDetalleCollectionNewControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionNewControlActividadDetalle);
                        oldUserIdOfControlActividadDetalleCollectionNewControlActividadDetalle = em.merge(oldUserIdOfControlActividadDetalleCollectionNewControlActividadDetalle);
                    }
                }
            }
            for (Actividad actividadCollectionOldActividad : actividadCollectionOld) {
                if (!actividadCollectionNew.contains(actividadCollectionOldActividad)) {
                    actividadCollectionOldActividad.setUserId(null);
                    actividadCollectionOldActividad = em.merge(actividadCollectionOldActividad);
                }
            }
            for (Actividad actividadCollectionNewActividad : actividadCollectionNew) {
                if (!actividadCollectionOld.contains(actividadCollectionNewActividad)) {
                    Usuario oldUserIdOfActividadCollectionNewActividad = actividadCollectionNewActividad.getUserId();
                    actividadCollectionNewActividad.setUserId(usuario);
                    actividadCollectionNewActividad = em.merge(actividadCollectionNewActividad);
                    if (oldUserIdOfActividadCollectionNewActividad != null && !oldUserIdOfActividadCollectionNewActividad.equals(usuario)) {
                        oldUserIdOfActividadCollectionNewActividad.getActividadCollection().remove(actividadCollectionNewActividad);
                        oldUserIdOfActividadCollectionNewActividad = em.merge(oldUserIdOfActividadCollectionNewActividad);
                    }
                }
            }
            for (CodigoElemento codigoElementoCollectionOldCodigoElemento : codigoElementoCollectionOld) {
                if (!codigoElementoCollectionNew.contains(codigoElementoCollectionOldCodigoElemento)) {
                    codigoElementoCollectionOldCodigoElemento.setUserId(null);
                    codigoElementoCollectionOldCodigoElemento = em.merge(codigoElementoCollectionOldCodigoElemento);
                }
            }
            for (CodigoElemento codigoElementoCollectionNewCodigoElemento : codigoElementoCollectionNew) {
                if (!codigoElementoCollectionOld.contains(codigoElementoCollectionNewCodigoElemento)) {
                    Usuario oldUserIdOfCodigoElementoCollectionNewCodigoElemento = codigoElementoCollectionNewCodigoElemento.getUserId();
                    codigoElementoCollectionNewCodigoElemento.setUserId(usuario);
                    codigoElementoCollectionNewCodigoElemento = em.merge(codigoElementoCollectionNewCodigoElemento);
                    if (oldUserIdOfCodigoElementoCollectionNewCodigoElemento != null && !oldUserIdOfCodigoElementoCollectionNewCodigoElemento.equals(usuario)) {
                        oldUserIdOfCodigoElementoCollectionNewCodigoElemento.getCodigoElementoCollection().remove(codigoElementoCollectionNewCodigoElemento);
                        oldUserIdOfCodigoElementoCollectionNewCodigoElemento = em.merge(oldUserIdOfCodigoElementoCollectionNewCodigoElemento);
                    }
                }
            }
            for (CargoPermisoAccion cargoPermisoAccionCollectionOldCargoPermisoAccion : cargoPermisoAccionCollectionOld) {
                if (!cargoPermisoAccionCollectionNew.contains(cargoPermisoAccionCollectionOldCargoPermisoAccion)) {
                    cargoPermisoAccionCollectionOldCargoPermisoAccion.setUserId(null);
                    cargoPermisoAccionCollectionOldCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionOldCargoPermisoAccion);
                }
            }
            for (CargoPermisoAccion cargoPermisoAccionCollectionNewCargoPermisoAccion : cargoPermisoAccionCollectionNew) {
                if (!cargoPermisoAccionCollectionOld.contains(cargoPermisoAccionCollectionNewCargoPermisoAccion)) {
                    Usuario oldUserIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion = cargoPermisoAccionCollectionNewCargoPermisoAccion.getUserId();
                    cargoPermisoAccionCollectionNewCargoPermisoAccion.setUserId(usuario);
                    cargoPermisoAccionCollectionNewCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionNewCargoPermisoAccion);
                    if (oldUserIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion != null && !oldUserIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion.equals(usuario)) {
                        oldUserIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion.getCargoPermisoAccionCollection().remove(cargoPermisoAccionCollectionNewCargoPermisoAccion);
                        oldUserIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion = em.merge(oldUserIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion);
                    }
                }
            }
            for (ContratoTramo contratoTramoCollectionOldContratoTramo : contratoTramoCollectionOld) {
                if (!contratoTramoCollectionNew.contains(contratoTramoCollectionOldContratoTramo)) {
                    contratoTramoCollectionOldContratoTramo.setUserId(null);
                    contratoTramoCollectionOldContratoTramo = em.merge(contratoTramoCollectionOldContratoTramo);
                }
            }
            for (ContratoTramo contratoTramoCollectionNewContratoTramo : contratoTramoCollectionNew) {
                if (!contratoTramoCollectionOld.contains(contratoTramoCollectionNewContratoTramo)) {
                    Usuario oldUserIdOfContratoTramoCollectionNewContratoTramo = contratoTramoCollectionNewContratoTramo.getUserId();
                    contratoTramoCollectionNewContratoTramo.setUserId(usuario);
                    contratoTramoCollectionNewContratoTramo = em.merge(contratoTramoCollectionNewContratoTramo);
                    if (oldUserIdOfContratoTramoCollectionNewContratoTramo != null && !oldUserIdOfContratoTramoCollectionNewContratoTramo.equals(usuario)) {
                        oldUserIdOfContratoTramoCollectionNewContratoTramo.getContratoTramoCollection().remove(contratoTramoCollectionNewContratoTramo);
                        oldUserIdOfContratoTramoCollectionNewContratoTramo = em.merge(oldUserIdOfContratoTramoCollectionNewContratoTramo);
                    }
                }
            }
            for (Accion accionCollectionOldAccion : accionCollectionOld) {
                if (!accionCollectionNew.contains(accionCollectionOldAccion)) {
                    accionCollectionOldAccion.setUserId(null);
                    accionCollectionOldAccion = em.merge(accionCollectionOldAccion);
                }
            }
            for (Accion accionCollectionNewAccion : accionCollectionNew) {
                if (!accionCollectionOld.contains(accionCollectionNewAccion)) {
                    Usuario oldUserIdOfAccionCollectionNewAccion = accionCollectionNewAccion.getUserId();
                    accionCollectionNewAccion.setUserId(usuario);
                    accionCollectionNewAccion = em.merge(accionCollectionNewAccion);
                    if (oldUserIdOfAccionCollectionNewAccion != null && !oldUserIdOfAccionCollectionNewAccion.equals(usuario)) {
                        oldUserIdOfAccionCollectionNewAccion.getAccionCollection().remove(accionCollectionNewAccion);
                        oldUserIdOfAccionCollectionNewAccion = em.merge(oldUserIdOfAccionCollectionNewAccion);
                    }
                }
            }
            for (Cargo cargoCollectionOldCargo : cargoCollectionOld) {
                if (!cargoCollectionNew.contains(cargoCollectionOldCargo)) {
                    cargoCollectionOldCargo.setUserId(null);
                    cargoCollectionOldCargo = em.merge(cargoCollectionOldCargo);
                }
            }
            for (Cargo cargoCollectionNewCargo : cargoCollectionNew) {
                if (!cargoCollectionOld.contains(cargoCollectionNewCargo)) {
                    Usuario oldUserIdOfCargoCollectionNewCargo = cargoCollectionNewCargo.getUserId();
                    cargoCollectionNewCargo.setUserId(usuario);
                    cargoCollectionNewCargo = em.merge(cargoCollectionNewCargo);
                    if (oldUserIdOfCargoCollectionNewCargo != null && !oldUserIdOfCargoCollectionNewCargo.equals(usuario)) {
                        oldUserIdOfCargoCollectionNewCargo.getCargoCollection().remove(cargoCollectionNewCargo);
                        oldUserIdOfCargoCollectionNewCargo = em.merge(oldUserIdOfCargoCollectionNewCargo);
                    }
                }
            }
            for (Contrato contratoCollectionOldContrato : contratoCollectionOld) {
                if (!contratoCollectionNew.contains(contratoCollectionOldContrato)) {
                    contratoCollectionOldContrato.setUserId(null);
                    contratoCollectionOldContrato = em.merge(contratoCollectionOldContrato);
                }
            }
            for (Contrato contratoCollectionNewContrato : contratoCollectionNew) {
                if (!contratoCollectionOld.contains(contratoCollectionNewContrato)) {
                    Usuario oldUserIdOfContratoCollectionNewContrato = contratoCollectionNewContrato.getUserId();
                    contratoCollectionNewContrato.setUserId(usuario);
                    contratoCollectionNewContrato = em.merge(contratoCollectionNewContrato);
                    if (oldUserIdOfContratoCollectionNewContrato != null && !oldUserIdOfContratoCollectionNewContrato.equals(usuario)) {
                        oldUserIdOfContratoCollectionNewContrato.getContratoCollection().remove(contratoCollectionNewContrato);
                        oldUserIdOfContratoCollectionNewContrato = em.merge(oldUserIdOfContratoCollectionNewContrato);
                    }
                }
            }
            for (ContratoTramoActividad contratoTramoActividadCollectionOldContratoTramoActividad : contratoTramoActividadCollectionOld) {
                if (!contratoTramoActividadCollectionNew.contains(contratoTramoActividadCollectionOldContratoTramoActividad)) {
                    contratoTramoActividadCollectionOldContratoTramoActividad.setUserId(null);
                    contratoTramoActividadCollectionOldContratoTramoActividad = em.merge(contratoTramoActividadCollectionOldContratoTramoActividad);
                }
            }
            for (ContratoTramoActividad contratoTramoActividadCollectionNewContratoTramoActividad : contratoTramoActividadCollectionNew) {
                if (!contratoTramoActividadCollectionOld.contains(contratoTramoActividadCollectionNewContratoTramoActividad)) {
                    Usuario oldUserIdOfContratoTramoActividadCollectionNewContratoTramoActividad = contratoTramoActividadCollectionNewContratoTramoActividad.getUserId();
                    contratoTramoActividadCollectionNewContratoTramoActividad.setUserId(usuario);
                    contratoTramoActividadCollectionNewContratoTramoActividad = em.merge(contratoTramoActividadCollectionNewContratoTramoActividad);
                    if (oldUserIdOfContratoTramoActividadCollectionNewContratoTramoActividad != null && !oldUserIdOfContratoTramoActividadCollectionNewContratoTramoActividad.equals(usuario)) {
                        oldUserIdOfContratoTramoActividadCollectionNewContratoTramoActividad.getContratoTramoActividadCollection().remove(contratoTramoActividadCollectionNewContratoTramoActividad);
                        oldUserIdOfContratoTramoActividadCollectionNewContratoTramoActividad = em.merge(oldUserIdOfContratoTramoActividadCollectionNewContratoTramoActividad);
                    }
                }
            }
            for (PermisoAccion permisoAccionCollectionOldPermisoAccion : permisoAccionCollectionOld) {
                if (!permisoAccionCollectionNew.contains(permisoAccionCollectionOldPermisoAccion)) {
                    permisoAccionCollectionOldPermisoAccion.setUserId(null);
                    permisoAccionCollectionOldPermisoAccion = em.merge(permisoAccionCollectionOldPermisoAccion);
                }
            }
            for (PermisoAccion permisoAccionCollectionNewPermisoAccion : permisoAccionCollectionNew) {
                if (!permisoAccionCollectionOld.contains(permisoAccionCollectionNewPermisoAccion)) {
                    Usuario oldUserIdOfPermisoAccionCollectionNewPermisoAccion = permisoAccionCollectionNewPermisoAccion.getUserId();
                    permisoAccionCollectionNewPermisoAccion.setUserId(usuario);
                    permisoAccionCollectionNewPermisoAccion = em.merge(permisoAccionCollectionNewPermisoAccion);
                    if (oldUserIdOfPermisoAccionCollectionNewPermisoAccion != null && !oldUserIdOfPermisoAccionCollectionNewPermisoAccion.equals(usuario)) {
                        oldUserIdOfPermisoAccionCollectionNewPermisoAccion.getPermisoAccionCollection().remove(permisoAccionCollectionNewPermisoAccion);
                        oldUserIdOfPermisoAccionCollectionNewPermisoAccion = em.merge(oldUserIdOfPermisoAccionCollectionNewPermisoAccion);
                    }
                }
            }
            for (Personal personalCollectionOldPersonal : personalCollectionOld) {
                if (!personalCollectionNew.contains(personalCollectionOldPersonal)) {
                    personalCollectionOldPersonal.setUserId(null);
                    personalCollectionOldPersonal = em.merge(personalCollectionOldPersonal);
                }
            }
            for (Personal personalCollectionNewPersonal : personalCollectionNew) {
                if (!personalCollectionOld.contains(personalCollectionNewPersonal)) {
                    Usuario oldUserIdOfPersonalCollectionNewPersonal = personalCollectionNewPersonal.getUserId();
                    personalCollectionNewPersonal.setUserId(usuario);
                    personalCollectionNewPersonal = em.merge(personalCollectionNewPersonal);
                    if (oldUserIdOfPersonalCollectionNewPersonal != null && !oldUserIdOfPersonalCollectionNewPersonal.equals(usuario)) {
                        oldUserIdOfPersonalCollectionNewPersonal.getPersonalCollection().remove(personalCollectionNewPersonal);
                        oldUserIdOfPersonalCollectionNewPersonal = em.merge(oldUserIdOfPersonalCollectionNewPersonal);
                    }
                }
            }
            for (Permiso permisoCollectionOldPermiso : permisoCollectionOld) {
                if (!permisoCollectionNew.contains(permisoCollectionOldPermiso)) {
                    permisoCollectionOldPermiso.setUserId(null);
                    permisoCollectionOldPermiso = em.merge(permisoCollectionOldPermiso);
                }
            }
            for (Permiso permisoCollectionNewPermiso : permisoCollectionNew) {
                if (!permisoCollectionOld.contains(permisoCollectionNewPermiso)) {
                    Usuario oldUserIdOfPermisoCollectionNewPermiso = permisoCollectionNewPermiso.getUserId();
                    permisoCollectionNewPermiso.setUserId(usuario);
                    permisoCollectionNewPermiso = em.merge(permisoCollectionNewPermiso);
                    if (oldUserIdOfPermisoCollectionNewPermiso != null && !oldUserIdOfPermisoCollectionNewPermiso.equals(usuario)) {
                        oldUserIdOfPermisoCollectionNewPermiso.getPermisoCollection().remove(permisoCollectionNewPermiso);
                        oldUserIdOfPermisoCollectionNewPermiso = em.merge(oldUserIdOfPermisoCollectionNewPermiso);
                    }
                }
            }
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.setUserId(null);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Usuario oldUserIdOfClienteCollectionNewCliente = clienteCollectionNewCliente.getUserId();
                    clienteCollectionNewCliente.setUserId(usuario);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldUserIdOfClienteCollectionNewCliente != null && !oldUserIdOfClienteCollectionNewCliente.equals(usuario)) {
                        oldUserIdOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldUserIdOfClienteCollectionNewCliente = em.merge(oldUserIdOfClienteCollectionNewCliente);
                    }
                }
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionOldContratoTramoPersonal : contratoTramoPersonalCollectionOld) {
                if (!contratoTramoPersonalCollectionNew.contains(contratoTramoPersonalCollectionOldContratoTramoPersonal)) {
                    contratoTramoPersonalCollectionOldContratoTramoPersonal.setUserId(null);
                    contratoTramoPersonalCollectionOldContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionOldContratoTramoPersonal);
                }
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionNewContratoTramoPersonal : contratoTramoPersonalCollectionNew) {
                if (!contratoTramoPersonalCollectionOld.contains(contratoTramoPersonalCollectionNewContratoTramoPersonal)) {
                    Usuario oldUserIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal = contratoTramoPersonalCollectionNewContratoTramoPersonal.getUserId();
                    contratoTramoPersonalCollectionNewContratoTramoPersonal.setUserId(usuario);
                    contratoTramoPersonalCollectionNewContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionNewContratoTramoPersonal);
                    if (oldUserIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal != null && !oldUserIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal.equals(usuario)) {
                        oldUserIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal.getContratoTramoPersonalCollection().remove(contratoTramoPersonalCollectionNewContratoTramoPersonal);
                        oldUserIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal = em.merge(oldUserIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal);
                    }
                }
            }
            for (TipoActividad tipoActividadCollectionOldTipoActividad : tipoActividadCollectionOld) {
                if (!tipoActividadCollectionNew.contains(tipoActividadCollectionOldTipoActividad)) {
                    tipoActividadCollectionOldTipoActividad.setUserId(null);
                    tipoActividadCollectionOldTipoActividad = em.merge(tipoActividadCollectionOldTipoActividad);
                }
            }
            for (TipoActividad tipoActividadCollectionNewTipoActividad : tipoActividadCollectionNew) {
                if (!tipoActividadCollectionOld.contains(tipoActividadCollectionNewTipoActividad)) {
                    Usuario oldUserIdOfTipoActividadCollectionNewTipoActividad = tipoActividadCollectionNewTipoActividad.getUserId();
                    tipoActividadCollectionNewTipoActividad.setUserId(usuario);
                    tipoActividadCollectionNewTipoActividad = em.merge(tipoActividadCollectionNewTipoActividad);
                    if (oldUserIdOfTipoActividadCollectionNewTipoActividad != null && !oldUserIdOfTipoActividadCollectionNewTipoActividad.equals(usuario)) {
                        oldUserIdOfTipoActividadCollectionNewTipoActividad.getTipoActividadCollection().remove(tipoActividadCollectionNewTipoActividad);
                        oldUserIdOfTipoActividadCollectionNewTipoActividad = em.merge(oldUserIdOfTipoActividadCollectionNewTipoActividad);
                    }
                }
            }
            for (Elemento elementoCollectionOldElemento : elementoCollectionOld) {
                if (!elementoCollectionNew.contains(elementoCollectionOldElemento)) {
                    elementoCollectionOldElemento.setUserId(null);
                    elementoCollectionOldElemento = em.merge(elementoCollectionOldElemento);
                }
            }
            for (Elemento elementoCollectionNewElemento : elementoCollectionNew) {
                if (!elementoCollectionOld.contains(elementoCollectionNewElemento)) {
                    Usuario oldUserIdOfElementoCollectionNewElemento = elementoCollectionNewElemento.getUserId();
                    elementoCollectionNewElemento.setUserId(usuario);
                    elementoCollectionNewElemento = em.merge(elementoCollectionNewElemento);
                    if (oldUserIdOfElementoCollectionNewElemento != null && !oldUserIdOfElementoCollectionNewElemento.equals(usuario)) {
                        oldUserIdOfElementoCollectionNewElemento.getElementoCollection().remove(elementoCollectionNewElemento);
                        oldUserIdOfElementoCollectionNewElemento = em.merge(oldUserIdOfElementoCollectionNewElemento);
                    }
                }
            }
            for (ControlActividad controlActividadCollectionOldControlActividad : controlActividadCollectionOld) {
                if (!controlActividadCollectionNew.contains(controlActividadCollectionOldControlActividad)) {
                    controlActividadCollectionOldControlActividad.setUserId(null);
                    controlActividadCollectionOldControlActividad = em.merge(controlActividadCollectionOldControlActividad);
                }
            }
            for (ControlActividad controlActividadCollectionNewControlActividad : controlActividadCollectionNew) {
                if (!controlActividadCollectionOld.contains(controlActividadCollectionNewControlActividad)) {
                    Usuario oldUserIdOfControlActividadCollectionNewControlActividad = controlActividadCollectionNewControlActividad.getUserId();
                    controlActividadCollectionNewControlActividad.setUserId(usuario);
                    controlActividadCollectionNewControlActividad = em.merge(controlActividadCollectionNewControlActividad);
                    if (oldUserIdOfControlActividadCollectionNewControlActividad != null && !oldUserIdOfControlActividadCollectionNewControlActividad.equals(usuario)) {
                        oldUserIdOfControlActividadCollectionNewControlActividad.getControlActividadCollection().remove(controlActividadCollectionNewControlActividad);
                        oldUserIdOfControlActividadCollectionNewControlActividad = em.merge(oldUserIdOfControlActividadCollectionNewControlActividad);
                    }
                }
            }
            for (Tramo tramoCollectionOldTramo : tramoCollectionOld) {
                if (!tramoCollectionNew.contains(tramoCollectionOldTramo)) {
                    tramoCollectionOldTramo.setUserId(null);
                    tramoCollectionOldTramo = em.merge(tramoCollectionOldTramo);
                }
            }
            for (Tramo tramoCollectionNewTramo : tramoCollectionNew) {
                if (!tramoCollectionOld.contains(tramoCollectionNewTramo)) {
                    Usuario oldUserIdOfTramoCollectionNewTramo = tramoCollectionNewTramo.getUserId();
                    tramoCollectionNewTramo.setUserId(usuario);
                    tramoCollectionNewTramo = em.merge(tramoCollectionNewTramo);
                    if (oldUserIdOfTramoCollectionNewTramo != null && !oldUserIdOfTramoCollectionNewTramo.equals(usuario)) {
                        oldUserIdOfTramoCollectionNewTramo.getTramoCollection().remove(tramoCollectionNewTramo);
                        oldUserIdOfTramoCollectionNewTramo = em.merge(oldUserIdOfTramoCollectionNewTramo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUserId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Usuario userUserId = usuario.getUserUserId();
            if (userUserId != null) {
                userUserId.getUsuarioCollection().remove(usuario);
                userUserId = em.merge(userUserId);
            }
            Personal persId = usuario.getPersId();
            if (persId != null) {
                persId.setUsuario(null);
                persId = em.merge(persId);
            }
            Collection<Sector> sectorCollection = usuario.getSectorCollection();
            for (Sector sectorCollectionSector : sectorCollection) {
                sectorCollectionSector.setUserId(null);
                sectorCollectionSector = em.merge(sectorCollectionSector);
            }
            Collection<TramoCodigoElemento> tramoCodigoElementoCollection = usuario.getTramoCodigoElementoCollection();
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElemento : tramoCodigoElementoCollection) {
                tramoCodigoElementoCollectionTramoCodigoElemento.setUserId(null);
                tramoCodigoElementoCollectionTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionTramoCodigoElemento);
            }
            Collection<ClienteTipo> clienteTipoCollection = usuario.getClienteTipoCollection();
            for (ClienteTipo clienteTipoCollectionClienteTipo : clienteTipoCollection) {
                clienteTipoCollectionClienteTipo.setUserId(null);
                clienteTipoCollectionClienteTipo = em.merge(clienteTipoCollectionClienteTipo);
            }
            Collection<Usuario> usuarioCollection = usuario.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.setUserUserId(null);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            Collection<ControlActividadDetalle> controlActividadDetalleCollection = usuario.getControlActividadDetalleCollection();
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalle : controlActividadDetalleCollection) {
                controlActividadDetalleCollectionControlActividadDetalle.setUserId(null);
                controlActividadDetalleCollectionControlActividadDetalle = em.merge(controlActividadDetalleCollectionControlActividadDetalle);
            }
            Collection<Actividad> actividadCollection = usuario.getActividadCollection();
            for (Actividad actividadCollectionActividad : actividadCollection) {
                actividadCollectionActividad.setUserId(null);
                actividadCollectionActividad = em.merge(actividadCollectionActividad);
            }
            Collection<CodigoElemento> codigoElementoCollection = usuario.getCodigoElementoCollection();
            for (CodigoElemento codigoElementoCollectionCodigoElemento : codigoElementoCollection) {
                codigoElementoCollectionCodigoElemento.setUserId(null);
                codigoElementoCollectionCodigoElemento = em.merge(codigoElementoCollectionCodigoElemento);
            }
            Collection<CargoPermisoAccion> cargoPermisoAccionCollection = usuario.getCargoPermisoAccionCollection();
            for (CargoPermisoAccion cargoPermisoAccionCollectionCargoPermisoAccion : cargoPermisoAccionCollection) {
                cargoPermisoAccionCollectionCargoPermisoAccion.setUserId(null);
                cargoPermisoAccionCollectionCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionCargoPermisoAccion);
            }
            Collection<ContratoTramo> contratoTramoCollection = usuario.getContratoTramoCollection();
            for (ContratoTramo contratoTramoCollectionContratoTramo : contratoTramoCollection) {
                contratoTramoCollectionContratoTramo.setUserId(null);
                contratoTramoCollectionContratoTramo = em.merge(contratoTramoCollectionContratoTramo);
            }
            Collection<Accion> accionCollection = usuario.getAccionCollection();
            for (Accion accionCollectionAccion : accionCollection) {
                accionCollectionAccion.setUserId(null);
                accionCollectionAccion = em.merge(accionCollectionAccion);
            }
            Collection<Cargo> cargoCollection = usuario.getCargoCollection();
            for (Cargo cargoCollectionCargo : cargoCollection) {
                cargoCollectionCargo.setUserId(null);
                cargoCollectionCargo = em.merge(cargoCollectionCargo);
            }
            Collection<Contrato> contratoCollection = usuario.getContratoCollection();
            for (Contrato contratoCollectionContrato : contratoCollection) {
                contratoCollectionContrato.setUserId(null);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
            }
            Collection<ContratoTramoActividad> contratoTramoActividadCollection = usuario.getContratoTramoActividadCollection();
            for (ContratoTramoActividad contratoTramoActividadCollectionContratoTramoActividad : contratoTramoActividadCollection) {
                contratoTramoActividadCollectionContratoTramoActividad.setUserId(null);
                contratoTramoActividadCollectionContratoTramoActividad = em.merge(contratoTramoActividadCollectionContratoTramoActividad);
            }
            Collection<PermisoAccion> permisoAccionCollection = usuario.getPermisoAccionCollection();
            for (PermisoAccion permisoAccionCollectionPermisoAccion : permisoAccionCollection) {
                permisoAccionCollectionPermisoAccion.setUserId(null);
                permisoAccionCollectionPermisoAccion = em.merge(permisoAccionCollectionPermisoAccion);
            }
            Collection<Personal> personalCollection = usuario.getPersonalCollection();
            for (Personal personalCollectionPersonal : personalCollection) {
                personalCollectionPersonal.setUserId(null);
                personalCollectionPersonal = em.merge(personalCollectionPersonal);
            }
            Collection<Permiso> permisoCollection = usuario.getPermisoCollection();
            for (Permiso permisoCollectionPermiso : permisoCollection) {
                permisoCollectionPermiso.setUserId(null);
                permisoCollectionPermiso = em.merge(permisoCollectionPermiso);
            }
            Collection<Cliente> clienteCollection = usuario.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.setUserId(null);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollection = usuario.getContratoTramoPersonalCollection();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionContratoTramoPersonal : contratoTramoPersonalCollection) {
                contratoTramoPersonalCollectionContratoTramoPersonal.setUserId(null);
                contratoTramoPersonalCollectionContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionContratoTramoPersonal);
            }
            Collection<TipoActividad> tipoActividadCollection = usuario.getTipoActividadCollection();
            for (TipoActividad tipoActividadCollectionTipoActividad : tipoActividadCollection) {
                tipoActividadCollectionTipoActividad.setUserId(null);
                tipoActividadCollectionTipoActividad = em.merge(tipoActividadCollectionTipoActividad);
            }
            Collection<Elemento> elementoCollection = usuario.getElementoCollection();
            for (Elemento elementoCollectionElemento : elementoCollection) {
                elementoCollectionElemento.setUserId(null);
                elementoCollectionElemento = em.merge(elementoCollectionElemento);
            }
            Collection<ControlActividad> controlActividadCollection = usuario.getControlActividadCollection();
            for (ControlActividad controlActividadCollectionControlActividad : controlActividadCollection) {
                controlActividadCollectionControlActividad.setUserId(null);
                controlActividadCollectionControlActividad = em.merge(controlActividadCollectionControlActividad);
            }
            Collection<Tramo> tramoCollection = usuario.getTramoCollection();
            for (Tramo tramoCollectionTramo : tramoCollection) {
                tramoCollectionTramo.setUserId(null);
                tramoCollectionTramo = em.merge(tramoCollectionTramo);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
