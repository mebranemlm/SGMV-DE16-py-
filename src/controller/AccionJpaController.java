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
import model.Accion;

/**
 *
 * @author Luis
 */
public class AccionJpaController implements Serializable {

    public AccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accion accion) {
        if (accion.getPermisoAccionCollection() == null) {
            accion.setPermisoAccionCollection(new ArrayList<PermisoAccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = accion.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                accion.setUserId(userId);
            }
            Collection<PermisoAccion> attachedPermisoAccionCollection = new ArrayList<PermisoAccion>();
            for (PermisoAccion permisoAccionCollectionPermisoAccionToAttach : accion.getPermisoAccionCollection()) {
                permisoAccionCollectionPermisoAccionToAttach = em.getReference(permisoAccionCollectionPermisoAccionToAttach.getClass(), permisoAccionCollectionPermisoAccionToAttach.getPermAcciId());
                attachedPermisoAccionCollection.add(permisoAccionCollectionPermisoAccionToAttach);
            }
            accion.setPermisoAccionCollection(attachedPermisoAccionCollection);
            em.persist(accion);
            if (userId != null) {
                userId.getAccionCollection().add(accion);
                userId = em.merge(userId);
            }
            for (PermisoAccion permisoAccionCollectionPermisoAccion : accion.getPermisoAccionCollection()) {
                Accion oldAcciIdOfPermisoAccionCollectionPermisoAccion = permisoAccionCollectionPermisoAccion.getAcciId();
                permisoAccionCollectionPermisoAccion.setAcciId(accion);
                permisoAccionCollectionPermisoAccion = em.merge(permisoAccionCollectionPermisoAccion);
                if (oldAcciIdOfPermisoAccionCollectionPermisoAccion != null) {
                    oldAcciIdOfPermisoAccionCollectionPermisoAccion.getPermisoAccionCollection().remove(permisoAccionCollectionPermisoAccion);
                    oldAcciIdOfPermisoAccionCollectionPermisoAccion = em.merge(oldAcciIdOfPermisoAccionCollectionPermisoAccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accion accion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accion persistentAccion = em.find(Accion.class, accion.getAcciId());
            Usuario userIdOld = persistentAccion.getUserId();
            Usuario userIdNew = accion.getUserId();
            Collection<PermisoAccion> permisoAccionCollectionOld = persistentAccion.getPermisoAccionCollection();
            Collection<PermisoAccion> permisoAccionCollectionNew = accion.getPermisoAccionCollection();
            List<String> illegalOrphanMessages = null;
            for (PermisoAccion permisoAccionCollectionOldPermisoAccion : permisoAccionCollectionOld) {
                if (!permisoAccionCollectionNew.contains(permisoAccionCollectionOldPermisoAccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PermisoAccion " + permisoAccionCollectionOldPermisoAccion + " since its acciId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                accion.setUserId(userIdNew);
            }
            Collection<PermisoAccion> attachedPermisoAccionCollectionNew = new ArrayList<PermisoAccion>();
            for (PermisoAccion permisoAccionCollectionNewPermisoAccionToAttach : permisoAccionCollectionNew) {
                permisoAccionCollectionNewPermisoAccionToAttach = em.getReference(permisoAccionCollectionNewPermisoAccionToAttach.getClass(), permisoAccionCollectionNewPermisoAccionToAttach.getPermAcciId());
                attachedPermisoAccionCollectionNew.add(permisoAccionCollectionNewPermisoAccionToAttach);
            }
            permisoAccionCollectionNew = attachedPermisoAccionCollectionNew;
            accion.setPermisoAccionCollection(permisoAccionCollectionNew);
            accion = em.merge(accion);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getAccionCollection().remove(accion);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getAccionCollection().add(accion);
                userIdNew = em.merge(userIdNew);
            }
            for (PermisoAccion permisoAccionCollectionNewPermisoAccion : permisoAccionCollectionNew) {
                if (!permisoAccionCollectionOld.contains(permisoAccionCollectionNewPermisoAccion)) {
                    Accion oldAcciIdOfPermisoAccionCollectionNewPermisoAccion = permisoAccionCollectionNewPermisoAccion.getAcciId();
                    permisoAccionCollectionNewPermisoAccion.setAcciId(accion);
                    permisoAccionCollectionNewPermisoAccion = em.merge(permisoAccionCollectionNewPermisoAccion);
                    if (oldAcciIdOfPermisoAccionCollectionNewPermisoAccion != null && !oldAcciIdOfPermisoAccionCollectionNewPermisoAccion.equals(accion)) {
                        oldAcciIdOfPermisoAccionCollectionNewPermisoAccion.getPermisoAccionCollection().remove(permisoAccionCollectionNewPermisoAccion);
                        oldAcciIdOfPermisoAccionCollectionNewPermisoAccion = em.merge(oldAcciIdOfPermisoAccionCollectionNewPermisoAccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accion.getAcciId();
                if (findAccion(id) == null) {
                    throw new NonexistentEntityException("The accion with id " + id + " no longer exists.");
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
            Accion accion;
            try {
                accion = em.getReference(Accion.class, id);
                accion.getAcciId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PermisoAccion> permisoAccionCollectionOrphanCheck = accion.getPermisoAccionCollection();
            for (PermisoAccion permisoAccionCollectionOrphanCheckPermisoAccion : permisoAccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Accion (" + accion + ") cannot be destroyed since the PermisoAccion " + permisoAccionCollectionOrphanCheckPermisoAccion + " in its permisoAccionCollection field has a non-nullable acciId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = accion.getUserId();
            if (userId != null) {
                userId.getAccionCollection().remove(accion);
                userId = em.merge(userId);
            }
            em.remove(accion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Accion> findAccionEntities() {
        return findAccionEntities(true, -1, -1);
    }

    public List<Accion> findAccionEntities(int maxResults, int firstResult) {
        return findAccionEntities(false, maxResults, firstResult);
    }

    private List<Accion> findAccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accion.class));
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

    public Accion findAccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accion> rt = cq.from(Accion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
