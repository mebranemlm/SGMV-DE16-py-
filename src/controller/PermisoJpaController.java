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
import model.PermisoAccion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Permiso;

/**
 *
 * @author Luis
 */
public class PermisoJpaController implements Serializable {

    public PermisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permiso permiso) {
        if (permiso.getPermisoAccionCollection() == null) {
            permiso.setPermisoAccionCollection(new ArrayList<PermisoAccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = permiso.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                permiso.setUserId(userId);
            }
            Collection<PermisoAccion> attachedPermisoAccionCollection = new ArrayList<PermisoAccion>();
            for (PermisoAccion permisoAccionCollectionPermisoAccionToAttach : permiso.getPermisoAccionCollection()) {
                permisoAccionCollectionPermisoAccionToAttach = em.getReference(permisoAccionCollectionPermisoAccionToAttach.getClass(), permisoAccionCollectionPermisoAccionToAttach.getPermAcciId());
                attachedPermisoAccionCollection.add(permisoAccionCollectionPermisoAccionToAttach);
            }
            permiso.setPermisoAccionCollection(attachedPermisoAccionCollection);
            em.persist(permiso);
            if (userId != null) {
                userId.getPermisoCollection().add(permiso);
                userId = em.merge(userId);
            }
            for (PermisoAccion permisoAccionCollectionPermisoAccion : permiso.getPermisoAccionCollection()) {
                Permiso oldPermIdOfPermisoAccionCollectionPermisoAccion = permisoAccionCollectionPermisoAccion.getPermId();
                permisoAccionCollectionPermisoAccion.setPermId(permiso);
                permisoAccionCollectionPermisoAccion = em.merge(permisoAccionCollectionPermisoAccion);
                if (oldPermIdOfPermisoAccionCollectionPermisoAccion != null) {
                    oldPermIdOfPermisoAccionCollectionPermisoAccion.getPermisoAccionCollection().remove(permisoAccionCollectionPermisoAccion);
                    oldPermIdOfPermisoAccionCollectionPermisoAccion = em.merge(oldPermIdOfPermisoAccionCollectionPermisoAccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permiso permiso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso persistentPermiso = em.find(Permiso.class, permiso.getPermId());
            Usuario userIdOld = persistentPermiso.getUserId();
            Usuario userIdNew = permiso.getUserId();
            Collection<PermisoAccion> permisoAccionCollectionOld = persistentPermiso.getPermisoAccionCollection();
            Collection<PermisoAccion> permisoAccionCollectionNew = permiso.getPermisoAccionCollection();
            List<String> illegalOrphanMessages = null;
            for (PermisoAccion permisoAccionCollectionOldPermisoAccion : permisoAccionCollectionOld) {
                if (!permisoAccionCollectionNew.contains(permisoAccionCollectionOldPermisoAccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PermisoAccion " + permisoAccionCollectionOldPermisoAccion + " since its permId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                permiso.setUserId(userIdNew);
            }
            Collection<PermisoAccion> attachedPermisoAccionCollectionNew = new ArrayList<PermisoAccion>();
            for (PermisoAccion permisoAccionCollectionNewPermisoAccionToAttach : permisoAccionCollectionNew) {
                permisoAccionCollectionNewPermisoAccionToAttach = em.getReference(permisoAccionCollectionNewPermisoAccionToAttach.getClass(), permisoAccionCollectionNewPermisoAccionToAttach.getPermAcciId());
                attachedPermisoAccionCollectionNew.add(permisoAccionCollectionNewPermisoAccionToAttach);
            }
            permisoAccionCollectionNew = attachedPermisoAccionCollectionNew;
            permiso.setPermisoAccionCollection(permisoAccionCollectionNew);
            permiso = em.merge(permiso);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getPermisoCollection().remove(permiso);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getPermisoCollection().add(permiso);
                userIdNew = em.merge(userIdNew);
            }
            for (PermisoAccion permisoAccionCollectionNewPermisoAccion : permisoAccionCollectionNew) {
                if (!permisoAccionCollectionOld.contains(permisoAccionCollectionNewPermisoAccion)) {
                    Permiso oldPermIdOfPermisoAccionCollectionNewPermisoAccion = permisoAccionCollectionNewPermisoAccion.getPermId();
                    permisoAccionCollectionNewPermisoAccion.setPermId(permiso);
                    permisoAccionCollectionNewPermisoAccion = em.merge(permisoAccionCollectionNewPermisoAccion);
                    if (oldPermIdOfPermisoAccionCollectionNewPermisoAccion != null && !oldPermIdOfPermisoAccionCollectionNewPermisoAccion.equals(permiso)) {
                        oldPermIdOfPermisoAccionCollectionNewPermisoAccion.getPermisoAccionCollection().remove(permisoAccionCollectionNewPermisoAccion);
                        oldPermIdOfPermisoAccionCollectionNewPermisoAccion = em.merge(oldPermIdOfPermisoAccionCollectionNewPermisoAccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permiso.getPermId();
                if (findPermiso(id) == null) {
                    throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.");
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
            Permiso permiso;
            try {
                permiso = em.getReference(Permiso.class, id);
                permiso.getPermId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PermisoAccion> permisoAccionCollectionOrphanCheck = permiso.getPermisoAccionCollection();
            for (PermisoAccion permisoAccionCollectionOrphanCheckPermisoAccion : permisoAccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Permiso (" + permiso + ") cannot be destroyed since the PermisoAccion " + permisoAccionCollectionOrphanCheckPermisoAccion + " in its permisoAccionCollection field has a non-nullable permId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = permiso.getUserId();
            if (userId != null) {
                userId.getPermisoCollection().remove(permiso);
                userId = em.merge(userId);
            }
            em.remove(permiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permiso> findPermisoEntities() {
        return findPermisoEntities(true, -1, -1);
    }

    public List<Permiso> findPermisoEntities(int maxResults, int firstResult) {
        return findPermisoEntities(false, maxResults, firstResult);
    }

    private List<Permiso> findPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permiso.class));
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

    public Permiso findPermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permiso> rt = cq.from(Permiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
