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
import model.CargoPermisoAccion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cargo;
import model.Personal;

/**
 *
 * @author Luis
 */
public class CargoJpaController implements Serializable {

    public CargoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargo cargo) {
        if (cargo.getCargoPermisoAccionCollection() == null) {
            cargo.setCargoPermisoAccionCollection(new ArrayList<CargoPermisoAccion>());
        }
        if (cargo.getPersonalCollection() == null) {
            cargo.setPersonalCollection(new ArrayList<Personal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = cargo.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                cargo.setUserId(userId);
            }
            Collection<CargoPermisoAccion> attachedCargoPermisoAccionCollection = new ArrayList<CargoPermisoAccion>();
            for (CargoPermisoAccion cargoPermisoAccionCollectionCargoPermisoAccionToAttach : cargo.getCargoPermisoAccionCollection()) {
                cargoPermisoAccionCollectionCargoPermisoAccionToAttach = em.getReference(cargoPermisoAccionCollectionCargoPermisoAccionToAttach.getClass(), cargoPermisoAccionCollectionCargoPermisoAccionToAttach.getCargPermAcciId());
                attachedCargoPermisoAccionCollection.add(cargoPermisoAccionCollectionCargoPermisoAccionToAttach);
            }
            cargo.setCargoPermisoAccionCollection(attachedCargoPermisoAccionCollection);
            Collection<Personal> attachedPersonalCollection = new ArrayList<Personal>();
            for (Personal personalCollectionPersonalToAttach : cargo.getPersonalCollection()) {
                personalCollectionPersonalToAttach = em.getReference(personalCollectionPersonalToAttach.getClass(), personalCollectionPersonalToAttach.getPersId());
                attachedPersonalCollection.add(personalCollectionPersonalToAttach);
            }
            cargo.setPersonalCollection(attachedPersonalCollection);
            em.persist(cargo);
            if (userId != null) {
                userId.getCargoCollection().add(cargo);
                userId = em.merge(userId);
            }
            for (CargoPermisoAccion cargoPermisoAccionCollectionCargoPermisoAccion : cargo.getCargoPermisoAccionCollection()) {
                Cargo oldCargIdOfCargoPermisoAccionCollectionCargoPermisoAccion = cargoPermisoAccionCollectionCargoPermisoAccion.getCargId();
                cargoPermisoAccionCollectionCargoPermisoAccion.setCargId(cargo);
                cargoPermisoAccionCollectionCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionCargoPermisoAccion);
                if (oldCargIdOfCargoPermisoAccionCollectionCargoPermisoAccion != null) {
                    oldCargIdOfCargoPermisoAccionCollectionCargoPermisoAccion.getCargoPermisoAccionCollection().remove(cargoPermisoAccionCollectionCargoPermisoAccion);
                    oldCargIdOfCargoPermisoAccionCollectionCargoPermisoAccion = em.merge(oldCargIdOfCargoPermisoAccionCollectionCargoPermisoAccion);
                }
            }
            for (Personal personalCollectionPersonal : cargo.getPersonalCollection()) {
                Cargo oldCargIdOfPersonalCollectionPersonal = personalCollectionPersonal.getCargId();
                personalCollectionPersonal.setCargId(cargo);
                personalCollectionPersonal = em.merge(personalCollectionPersonal);
                if (oldCargIdOfPersonalCollectionPersonal != null) {
                    oldCargIdOfPersonalCollectionPersonal.getPersonalCollection().remove(personalCollectionPersonal);
                    oldCargIdOfPersonalCollectionPersonal = em.merge(oldCargIdOfPersonalCollectionPersonal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargo cargo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getCargId());
            Usuario userIdOld = persistentCargo.getUserId();
            Usuario userIdNew = cargo.getUserId();
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionOld = persistentCargo.getCargoPermisoAccionCollection();
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionNew = cargo.getCargoPermisoAccionCollection();
            Collection<Personal> personalCollectionOld = persistentCargo.getPersonalCollection();
            Collection<Personal> personalCollectionNew = cargo.getPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (CargoPermisoAccion cargoPermisoAccionCollectionOldCargoPermisoAccion : cargoPermisoAccionCollectionOld) {
                if (!cargoPermisoAccionCollectionNew.contains(cargoPermisoAccionCollectionOldCargoPermisoAccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargoPermisoAccion " + cargoPermisoAccionCollectionOldCargoPermisoAccion + " since its cargId field is not nullable.");
                }
            }
            for (Personal personalCollectionOldPersonal : personalCollectionOld) {
                if (!personalCollectionNew.contains(personalCollectionOldPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personal " + personalCollectionOldPersonal + " since its cargId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                cargo.setUserId(userIdNew);
            }
            Collection<CargoPermisoAccion> attachedCargoPermisoAccionCollectionNew = new ArrayList<CargoPermisoAccion>();
            for (CargoPermisoAccion cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach : cargoPermisoAccionCollectionNew) {
                cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach = em.getReference(cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach.getClass(), cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach.getCargPermAcciId());
                attachedCargoPermisoAccionCollectionNew.add(cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach);
            }
            cargoPermisoAccionCollectionNew = attachedCargoPermisoAccionCollectionNew;
            cargo.setCargoPermisoAccionCollection(cargoPermisoAccionCollectionNew);
            Collection<Personal> attachedPersonalCollectionNew = new ArrayList<Personal>();
            for (Personal personalCollectionNewPersonalToAttach : personalCollectionNew) {
                personalCollectionNewPersonalToAttach = em.getReference(personalCollectionNewPersonalToAttach.getClass(), personalCollectionNewPersonalToAttach.getPersId());
                attachedPersonalCollectionNew.add(personalCollectionNewPersonalToAttach);
            }
            personalCollectionNew = attachedPersonalCollectionNew;
            cargo.setPersonalCollection(personalCollectionNew);
            cargo = em.merge(cargo);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getCargoCollection().remove(cargo);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getCargoCollection().add(cargo);
                userIdNew = em.merge(userIdNew);
            }
            for (CargoPermisoAccion cargoPermisoAccionCollectionNewCargoPermisoAccion : cargoPermisoAccionCollectionNew) {
                if (!cargoPermisoAccionCollectionOld.contains(cargoPermisoAccionCollectionNewCargoPermisoAccion)) {
                    Cargo oldCargIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion = cargoPermisoAccionCollectionNewCargoPermisoAccion.getCargId();
                    cargoPermisoAccionCollectionNewCargoPermisoAccion.setCargId(cargo);
                    cargoPermisoAccionCollectionNewCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionNewCargoPermisoAccion);
                    if (oldCargIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion != null && !oldCargIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion.equals(cargo)) {
                        oldCargIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion.getCargoPermisoAccionCollection().remove(cargoPermisoAccionCollectionNewCargoPermisoAccion);
                        oldCargIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion = em.merge(oldCargIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion);
                    }
                }
            }
            for (Personal personalCollectionNewPersonal : personalCollectionNew) {
                if (!personalCollectionOld.contains(personalCollectionNewPersonal)) {
                    Cargo oldCargIdOfPersonalCollectionNewPersonal = personalCollectionNewPersonal.getCargId();
                    personalCollectionNewPersonal.setCargId(cargo);
                    personalCollectionNewPersonal = em.merge(personalCollectionNewPersonal);
                    if (oldCargIdOfPersonalCollectionNewPersonal != null && !oldCargIdOfPersonalCollectionNewPersonal.equals(cargo)) {
                        oldCargIdOfPersonalCollectionNewPersonal.getPersonalCollection().remove(personalCollectionNewPersonal);
                        oldCargIdOfPersonalCollectionNewPersonal = em.merge(oldCargIdOfPersonalCollectionNewPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargo.getCargId();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getCargId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionOrphanCheck = cargo.getCargoPermisoAccionCollection();
            for (CargoPermisoAccion cargoPermisoAccionCollectionOrphanCheckCargoPermisoAccion : cargoPermisoAccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the CargoPermisoAccion " + cargoPermisoAccionCollectionOrphanCheckCargoPermisoAccion + " in its cargoPermisoAccionCollection field has a non-nullable cargId field.");
            }
            Collection<Personal> personalCollectionOrphanCheck = cargo.getPersonalCollection();
            for (Personal personalCollectionOrphanCheckPersonal : personalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the Personal " + personalCollectionOrphanCheckPersonal + " in its personalCollection field has a non-nullable cargId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = cargo.getUserId();
            if (userId != null) {
                userId.getCargoCollection().remove(cargo);
                userId = em.merge(userId);
            }
            em.remove(cargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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

    public Cargo findCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
