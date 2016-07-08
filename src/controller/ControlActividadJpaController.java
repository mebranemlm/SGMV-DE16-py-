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
import model.ControlActividadDetalle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.ControlActividad;

/**
 *
 * @author Luis
 */
public class ControlActividadJpaController implements Serializable {

    public ControlActividadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlActividad controlActividad) {
        if (controlActividad.getControlActividadDetalleCollection() == null) {
            controlActividad.setControlActividadDetalleCollection(new ArrayList<ControlActividadDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = controlActividad.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                controlActividad.setUserId(userId);
            }
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollection = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalleToAttach : controlActividad.getControlActividadDetalleCollection()) {
                controlActividadDetalleCollectionControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollection.add(controlActividadDetalleCollectionControlActividadDetalleToAttach);
            }
            controlActividad.setControlActividadDetalleCollection(attachedControlActividadDetalleCollection);
            em.persist(controlActividad);
            if (userId != null) {
                userId.getControlActividadCollection().add(controlActividad);
                userId = em.merge(userId);
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalle : controlActividad.getControlActividadDetalleCollection()) {
                ControlActividad oldCtacIdOfControlActividadDetalleCollectionControlActividadDetalle = controlActividadDetalleCollectionControlActividadDetalle.getCtacId();
                controlActividadDetalleCollectionControlActividadDetalle.setCtacId(controlActividad);
                controlActividadDetalleCollectionControlActividadDetalle = em.merge(controlActividadDetalleCollectionControlActividadDetalle);
                if (oldCtacIdOfControlActividadDetalleCollectionControlActividadDetalle != null) {
                    oldCtacIdOfControlActividadDetalleCollectionControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionControlActividadDetalle);
                    oldCtacIdOfControlActividadDetalleCollectionControlActividadDetalle = em.merge(oldCtacIdOfControlActividadDetalleCollectionControlActividadDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlActividad controlActividad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlActividad persistentControlActividad = em.find(ControlActividad.class, controlActividad.getCtacId());
            Usuario userIdOld = persistentControlActividad.getUserId();
            Usuario userIdNew = controlActividad.getUserId();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOld = persistentControlActividad.getControlActividadDetalleCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionNew = controlActividad.getControlActividadDetalleCollection();
            List<String> illegalOrphanMessages = null;
            for (ControlActividadDetalle controlActividadDetalleCollectionOldControlActividadDetalle : controlActividadDetalleCollectionOld) {
                if (!controlActividadDetalleCollectionNew.contains(controlActividadDetalleCollectionOldControlActividadDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlActividadDetalle " + controlActividadDetalleCollectionOldControlActividadDetalle + " since its ctacId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                controlActividad.setUserId(userIdNew);
            }
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollectionNew = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalleToAttach : controlActividadDetalleCollectionNew) {
                controlActividadDetalleCollectionNewControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollectionNew.add(controlActividadDetalleCollectionNewControlActividadDetalleToAttach);
            }
            controlActividadDetalleCollectionNew = attachedControlActividadDetalleCollectionNew;
            controlActividad.setControlActividadDetalleCollection(controlActividadDetalleCollectionNew);
            controlActividad = em.merge(controlActividad);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getControlActividadCollection().remove(controlActividad);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getControlActividadCollection().add(controlActividad);
                userIdNew = em.merge(userIdNew);
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalle : controlActividadDetalleCollectionNew) {
                if (!controlActividadDetalleCollectionOld.contains(controlActividadDetalleCollectionNewControlActividadDetalle)) {
                    ControlActividad oldCtacIdOfControlActividadDetalleCollectionNewControlActividadDetalle = controlActividadDetalleCollectionNewControlActividadDetalle.getCtacId();
                    controlActividadDetalleCollectionNewControlActividadDetalle.setCtacId(controlActividad);
                    controlActividadDetalleCollectionNewControlActividadDetalle = em.merge(controlActividadDetalleCollectionNewControlActividadDetalle);
                    if (oldCtacIdOfControlActividadDetalleCollectionNewControlActividadDetalle != null && !oldCtacIdOfControlActividadDetalleCollectionNewControlActividadDetalle.equals(controlActividad)) {
                        oldCtacIdOfControlActividadDetalleCollectionNewControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionNewControlActividadDetalle);
                        oldCtacIdOfControlActividadDetalleCollectionNewControlActividadDetalle = em.merge(oldCtacIdOfControlActividadDetalleCollectionNewControlActividadDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlActividad.getCtacId();
                if (findControlActividad(id) == null) {
                    throw new NonexistentEntityException("The controlActividad with id " + id + " no longer exists.");
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
            ControlActividad controlActividad;
            try {
                controlActividad = em.getReference(ControlActividad.class, id);
                controlActividad.getCtacId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlActividad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOrphanCheck = controlActividad.getControlActividadDetalleCollection();
            for (ControlActividadDetalle controlActividadDetalleCollectionOrphanCheckControlActividadDetalle : controlActividadDetalleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ControlActividad (" + controlActividad + ") cannot be destroyed since the ControlActividadDetalle " + controlActividadDetalleCollectionOrphanCheckControlActividadDetalle + " in its controlActividadDetalleCollection field has a non-nullable ctacId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = controlActividad.getUserId();
            if (userId != null) {
                userId.getControlActividadCollection().remove(controlActividad);
                userId = em.merge(userId);
            }
            em.remove(controlActividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlActividad> findControlActividadEntities() {
        return findControlActividadEntities(true, -1, -1);
    }

    public List<ControlActividad> findControlActividadEntities(int maxResults, int firstResult) {
        return findControlActividadEntities(false, maxResults, firstResult);
    }

    private List<ControlActividad> findControlActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlActividad.class));
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

    public ControlActividad findControlActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlActividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlActividad> rt = cq.from(ControlActividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
