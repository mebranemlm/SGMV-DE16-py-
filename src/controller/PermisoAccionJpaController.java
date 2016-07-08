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
import model.Permiso;
import model.Accion;
import model.CargoPermisoAccion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.PermisoAccion;

/**
 *
 * @author Luis
 */
public class PermisoAccionJpaController implements Serializable {

    public PermisoAccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PermisoAccion permisoAccion) {
        if (permisoAccion.getCargoPermisoAccionCollection() == null) {
            permisoAccion.setCargoPermisoAccionCollection(new ArrayList<CargoPermisoAccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = permisoAccion.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                permisoAccion.setUserId(userId);
            }
            Permiso permId = permisoAccion.getPermId();
            if (permId != null) {
                permId = em.getReference(permId.getClass(), permId.getPermId());
                permisoAccion.setPermId(permId);
            }
            Accion acciId = permisoAccion.getAcciId();
            if (acciId != null) {
                acciId = em.getReference(acciId.getClass(), acciId.getAcciId());
                permisoAccion.setAcciId(acciId);
            }
            Collection<CargoPermisoAccion> attachedCargoPermisoAccionCollection = new ArrayList<CargoPermisoAccion>();
            for (CargoPermisoAccion cargoPermisoAccionCollectionCargoPermisoAccionToAttach : permisoAccion.getCargoPermisoAccionCollection()) {
                cargoPermisoAccionCollectionCargoPermisoAccionToAttach = em.getReference(cargoPermisoAccionCollectionCargoPermisoAccionToAttach.getClass(), cargoPermisoAccionCollectionCargoPermisoAccionToAttach.getCargPermAcciId());
                attachedCargoPermisoAccionCollection.add(cargoPermisoAccionCollectionCargoPermisoAccionToAttach);
            }
            permisoAccion.setCargoPermisoAccionCollection(attachedCargoPermisoAccionCollection);
            em.persist(permisoAccion);
            if (userId != null) {
                userId.getPermisoAccionCollection().add(permisoAccion);
                userId = em.merge(userId);
            }
            if (permId != null) {
                permId.getPermisoAccionCollection().add(permisoAccion);
                permId = em.merge(permId);
            }
            if (acciId != null) {
                acciId.getPermisoAccionCollection().add(permisoAccion);
                acciId = em.merge(acciId);
            }
            for (CargoPermisoAccion cargoPermisoAccionCollectionCargoPermisoAccion : permisoAccion.getCargoPermisoAccionCollection()) {
                PermisoAccion oldPermAcciIdOfCargoPermisoAccionCollectionCargoPermisoAccion = cargoPermisoAccionCollectionCargoPermisoAccion.getPermAcciId();
                cargoPermisoAccionCollectionCargoPermisoAccion.setPermAcciId(permisoAccion);
                cargoPermisoAccionCollectionCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionCargoPermisoAccion);
                if (oldPermAcciIdOfCargoPermisoAccionCollectionCargoPermisoAccion != null) {
                    oldPermAcciIdOfCargoPermisoAccionCollectionCargoPermisoAccion.getCargoPermisoAccionCollection().remove(cargoPermisoAccionCollectionCargoPermisoAccion);
                    oldPermAcciIdOfCargoPermisoAccionCollectionCargoPermisoAccion = em.merge(oldPermAcciIdOfCargoPermisoAccionCollectionCargoPermisoAccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PermisoAccion permisoAccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PermisoAccion persistentPermisoAccion = em.find(PermisoAccion.class, permisoAccion.getPermAcciId());
            Usuario userIdOld = persistentPermisoAccion.getUserId();
            Usuario userIdNew = permisoAccion.getUserId();
            Permiso permIdOld = persistentPermisoAccion.getPermId();
            Permiso permIdNew = permisoAccion.getPermId();
            Accion acciIdOld = persistentPermisoAccion.getAcciId();
            Accion acciIdNew = permisoAccion.getAcciId();
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionOld = persistentPermisoAccion.getCargoPermisoAccionCollection();
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionNew = permisoAccion.getCargoPermisoAccionCollection();
            List<String> illegalOrphanMessages = null;
            for (CargoPermisoAccion cargoPermisoAccionCollectionOldCargoPermisoAccion : cargoPermisoAccionCollectionOld) {
                if (!cargoPermisoAccionCollectionNew.contains(cargoPermisoAccionCollectionOldCargoPermisoAccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargoPermisoAccion " + cargoPermisoAccionCollectionOldCargoPermisoAccion + " since its permAcciId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                permisoAccion.setUserId(userIdNew);
            }
            if (permIdNew != null) {
                permIdNew = em.getReference(permIdNew.getClass(), permIdNew.getPermId());
                permisoAccion.setPermId(permIdNew);
            }
            if (acciIdNew != null) {
                acciIdNew = em.getReference(acciIdNew.getClass(), acciIdNew.getAcciId());
                permisoAccion.setAcciId(acciIdNew);
            }
            Collection<CargoPermisoAccion> attachedCargoPermisoAccionCollectionNew = new ArrayList<CargoPermisoAccion>();
            for (CargoPermisoAccion cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach : cargoPermisoAccionCollectionNew) {
                cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach = em.getReference(cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach.getClass(), cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach.getCargPermAcciId());
                attachedCargoPermisoAccionCollectionNew.add(cargoPermisoAccionCollectionNewCargoPermisoAccionToAttach);
            }
            cargoPermisoAccionCollectionNew = attachedCargoPermisoAccionCollectionNew;
            permisoAccion.setCargoPermisoAccionCollection(cargoPermisoAccionCollectionNew);
            permisoAccion = em.merge(permisoAccion);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getPermisoAccionCollection().remove(permisoAccion);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getPermisoAccionCollection().add(permisoAccion);
                userIdNew = em.merge(userIdNew);
            }
            if (permIdOld != null && !permIdOld.equals(permIdNew)) {
                permIdOld.getPermisoAccionCollection().remove(permisoAccion);
                permIdOld = em.merge(permIdOld);
            }
            if (permIdNew != null && !permIdNew.equals(permIdOld)) {
                permIdNew.getPermisoAccionCollection().add(permisoAccion);
                permIdNew = em.merge(permIdNew);
            }
            if (acciIdOld != null && !acciIdOld.equals(acciIdNew)) {
                acciIdOld.getPermisoAccionCollection().remove(permisoAccion);
                acciIdOld = em.merge(acciIdOld);
            }
            if (acciIdNew != null && !acciIdNew.equals(acciIdOld)) {
                acciIdNew.getPermisoAccionCollection().add(permisoAccion);
                acciIdNew = em.merge(acciIdNew);
            }
            for (CargoPermisoAccion cargoPermisoAccionCollectionNewCargoPermisoAccion : cargoPermisoAccionCollectionNew) {
                if (!cargoPermisoAccionCollectionOld.contains(cargoPermisoAccionCollectionNewCargoPermisoAccion)) {
                    PermisoAccion oldPermAcciIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion = cargoPermisoAccionCollectionNewCargoPermisoAccion.getPermAcciId();
                    cargoPermisoAccionCollectionNewCargoPermisoAccion.setPermAcciId(permisoAccion);
                    cargoPermisoAccionCollectionNewCargoPermisoAccion = em.merge(cargoPermisoAccionCollectionNewCargoPermisoAccion);
                    if (oldPermAcciIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion != null && !oldPermAcciIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion.equals(permisoAccion)) {
                        oldPermAcciIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion.getCargoPermisoAccionCollection().remove(cargoPermisoAccionCollectionNewCargoPermisoAccion);
                        oldPermAcciIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion = em.merge(oldPermAcciIdOfCargoPermisoAccionCollectionNewCargoPermisoAccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permisoAccion.getPermAcciId();
                if (findPermisoAccion(id) == null) {
                    throw new NonexistentEntityException("The permisoAccion with id " + id + " no longer exists.");
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
            PermisoAccion permisoAccion;
            try {
                permisoAccion = em.getReference(PermisoAccion.class, id);
                permisoAccion.getPermAcciId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permisoAccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CargoPermisoAccion> cargoPermisoAccionCollectionOrphanCheck = permisoAccion.getCargoPermisoAccionCollection();
            for (CargoPermisoAccion cargoPermisoAccionCollectionOrphanCheckCargoPermisoAccion : cargoPermisoAccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PermisoAccion (" + permisoAccion + ") cannot be destroyed since the CargoPermisoAccion " + cargoPermisoAccionCollectionOrphanCheckCargoPermisoAccion + " in its cargoPermisoAccionCollection field has a non-nullable permAcciId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = permisoAccion.getUserId();
            if (userId != null) {
                userId.getPermisoAccionCollection().remove(permisoAccion);
                userId = em.merge(userId);
            }
            Permiso permId = permisoAccion.getPermId();
            if (permId != null) {
                permId.getPermisoAccionCollection().remove(permisoAccion);
                permId = em.merge(permId);
            }
            Accion acciId = permisoAccion.getAcciId();
            if (acciId != null) {
                acciId.getPermisoAccionCollection().remove(permisoAccion);
                acciId = em.merge(acciId);
            }
            em.remove(permisoAccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PermisoAccion> findPermisoAccionEntities() {
        return findPermisoAccionEntities(true, -1, -1);
    }

    public List<PermisoAccion> findPermisoAccionEntities(int maxResults, int firstResult) {
        return findPermisoAccionEntities(false, maxResults, firstResult);
    }

    private List<PermisoAccion> findPermisoAccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PermisoAccion.class));
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

    public PermisoAccion findPermisoAccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PermisoAccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoAccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PermisoAccion> rt = cq.from(PermisoAccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
