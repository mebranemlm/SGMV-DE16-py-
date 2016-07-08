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
import model.Actividad;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.TipoActividad;

/**
 *
 * @author Luis
 */
public class TipoActividadJpaController implements Serializable {

    public TipoActividadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoActividad tipoActividad) {
        if (tipoActividad.getActividadCollection() == null) {
            tipoActividad.setActividadCollection(new ArrayList<Actividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = tipoActividad.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                tipoActividad.setUserId(userId);
            }
            Collection<Actividad> attachedActividadCollection = new ArrayList<Actividad>();
            for (Actividad actividadCollectionActividadToAttach : tipoActividad.getActividadCollection()) {
                actividadCollectionActividadToAttach = em.getReference(actividadCollectionActividadToAttach.getClass(), actividadCollectionActividadToAttach.getActiId());
                attachedActividadCollection.add(actividadCollectionActividadToAttach);
            }
            tipoActividad.setActividadCollection(attachedActividadCollection);
            em.persist(tipoActividad);
            if (userId != null) {
                userId.getTipoActividadCollection().add(tipoActividad);
                userId = em.merge(userId);
            }
            for (Actividad actividadCollectionActividad : tipoActividad.getActividadCollection()) {
                TipoActividad oldTiacIdOfActividadCollectionActividad = actividadCollectionActividad.getTiacId();
                actividadCollectionActividad.setTiacId(tipoActividad);
                actividadCollectionActividad = em.merge(actividadCollectionActividad);
                if (oldTiacIdOfActividadCollectionActividad != null) {
                    oldTiacIdOfActividadCollectionActividad.getActividadCollection().remove(actividadCollectionActividad);
                    oldTiacIdOfActividadCollectionActividad = em.merge(oldTiacIdOfActividadCollectionActividad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoActividad tipoActividad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoActividad persistentTipoActividad = em.find(TipoActividad.class, tipoActividad.getTiacId());
            Usuario userIdOld = persistentTipoActividad.getUserId();
            Usuario userIdNew = tipoActividad.getUserId();
            Collection<Actividad> actividadCollectionOld = persistentTipoActividad.getActividadCollection();
            Collection<Actividad> actividadCollectionNew = tipoActividad.getActividadCollection();
            List<String> illegalOrphanMessages = null;
            for (Actividad actividadCollectionOldActividad : actividadCollectionOld) {
                if (!actividadCollectionNew.contains(actividadCollectionOldActividad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Actividad " + actividadCollectionOldActividad + " since its tiacId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                tipoActividad.setUserId(userIdNew);
            }
            Collection<Actividad> attachedActividadCollectionNew = new ArrayList<Actividad>();
            for (Actividad actividadCollectionNewActividadToAttach : actividadCollectionNew) {
                actividadCollectionNewActividadToAttach = em.getReference(actividadCollectionNewActividadToAttach.getClass(), actividadCollectionNewActividadToAttach.getActiId());
                attachedActividadCollectionNew.add(actividadCollectionNewActividadToAttach);
            }
            actividadCollectionNew = attachedActividadCollectionNew;
            tipoActividad.setActividadCollection(actividadCollectionNew);
            tipoActividad = em.merge(tipoActividad);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getTipoActividadCollection().remove(tipoActividad);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getTipoActividadCollection().add(tipoActividad);
                userIdNew = em.merge(userIdNew);
            }
            for (Actividad actividadCollectionNewActividad : actividadCollectionNew) {
                if (!actividadCollectionOld.contains(actividadCollectionNewActividad)) {
                    TipoActividad oldTiacIdOfActividadCollectionNewActividad = actividadCollectionNewActividad.getTiacId();
                    actividadCollectionNewActividad.setTiacId(tipoActividad);
                    actividadCollectionNewActividad = em.merge(actividadCollectionNewActividad);
                    if (oldTiacIdOfActividadCollectionNewActividad != null && !oldTiacIdOfActividadCollectionNewActividad.equals(tipoActividad)) {
                        oldTiacIdOfActividadCollectionNewActividad.getActividadCollection().remove(actividadCollectionNewActividad);
                        oldTiacIdOfActividadCollectionNewActividad = em.merge(oldTiacIdOfActividadCollectionNewActividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoActividad.getTiacId();
                if (findTipoActividad(id) == null) {
                    throw new NonexistentEntityException("The tipoActividad with id " + id + " no longer exists.");
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
            TipoActividad tipoActividad;
            try {
                tipoActividad = em.getReference(TipoActividad.class, id);
                tipoActividad.getTiacId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoActividad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Actividad> actividadCollectionOrphanCheck = tipoActividad.getActividadCollection();
            for (Actividad actividadCollectionOrphanCheckActividad : actividadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoActividad (" + tipoActividad + ") cannot be destroyed since the Actividad " + actividadCollectionOrphanCheckActividad + " in its actividadCollection field has a non-nullable tiacId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = tipoActividad.getUserId();
            if (userId != null) {
                userId.getTipoActividadCollection().remove(tipoActividad);
                userId = em.merge(userId);
            }
            em.remove(tipoActividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoActividad> findTipoActividadEntities() {
        return findTipoActividadEntities(true, -1, -1);
    }

    public List<TipoActividad> findTipoActividadEntities(int maxResults, int firstResult) {
        return findTipoActividadEntities(false, maxResults, firstResult);
    }

    private List<TipoActividad> findTipoActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoActividad.class));
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

    public TipoActividad findTipoActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoActividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoActividad> rt = cq.from(TipoActividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
