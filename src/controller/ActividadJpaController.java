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
import model.TipoActividad;
import model.ContratoTramoActividad;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Actividad;

/**
 *
 * @author Luis
 */
public class ActividadJpaController implements Serializable {

    public ActividadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actividad actividad) {
        if (actividad.getContratoTramoActividadCollection() == null) {
            actividad.setContratoTramoActividadCollection(new ArrayList<ContratoTramoActividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = actividad.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                actividad.setUserId(userId);
            }
            TipoActividad tiacId = actividad.getTiacId();
            if (tiacId != null) {
                tiacId = em.getReference(tiacId.getClass(), tiacId.getTiacId());
                actividad.setTiacId(tiacId);
            }
            Collection<ContratoTramoActividad> attachedContratoTramoActividadCollection = new ArrayList<ContratoTramoActividad>();
            for (ContratoTramoActividad contratoTramoActividadCollectionContratoTramoActividadToAttach : actividad.getContratoTramoActividadCollection()) {
                contratoTramoActividadCollectionContratoTramoActividadToAttach = em.getReference(contratoTramoActividadCollectionContratoTramoActividadToAttach.getClass(), contratoTramoActividadCollectionContratoTramoActividadToAttach.getContTramActiId());
                attachedContratoTramoActividadCollection.add(contratoTramoActividadCollectionContratoTramoActividadToAttach);
            }
            actividad.setContratoTramoActividadCollection(attachedContratoTramoActividadCollection);
            em.persist(actividad);
            if (userId != null) {
                userId.getActividadCollection().add(actividad);
                userId = em.merge(userId);
            }
            if (tiacId != null) {
                tiacId.getActividadCollection().add(actividad);
                tiacId = em.merge(tiacId);
            }
            for (ContratoTramoActividad contratoTramoActividadCollectionContratoTramoActividad : actividad.getContratoTramoActividadCollection()) {
                Actividad oldActiIdOfContratoTramoActividadCollectionContratoTramoActividad = contratoTramoActividadCollectionContratoTramoActividad.getActiId();
                contratoTramoActividadCollectionContratoTramoActividad.setActiId(actividad);
                contratoTramoActividadCollectionContratoTramoActividad = em.merge(contratoTramoActividadCollectionContratoTramoActividad);
                if (oldActiIdOfContratoTramoActividadCollectionContratoTramoActividad != null) {
                    oldActiIdOfContratoTramoActividadCollectionContratoTramoActividad.getContratoTramoActividadCollection().remove(contratoTramoActividadCollectionContratoTramoActividad);
                    oldActiIdOfContratoTramoActividadCollectionContratoTramoActividad = em.merge(oldActiIdOfContratoTramoActividadCollectionContratoTramoActividad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actividad actividad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividad persistentActividad = em.find(Actividad.class, actividad.getActiId());
            Usuario userIdOld = persistentActividad.getUserId();
            Usuario userIdNew = actividad.getUserId();
            TipoActividad tiacIdOld = persistentActividad.getTiacId();
            TipoActividad tiacIdNew = actividad.getTiacId();
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionOld = persistentActividad.getContratoTramoActividadCollection();
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionNew = actividad.getContratoTramoActividadCollection();
            List<String> illegalOrphanMessages = null;
            for (ContratoTramoActividad contratoTramoActividadCollectionOldContratoTramoActividad : contratoTramoActividadCollectionOld) {
                if (!contratoTramoActividadCollectionNew.contains(contratoTramoActividadCollectionOldContratoTramoActividad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContratoTramoActividad " + contratoTramoActividadCollectionOldContratoTramoActividad + " since its actiId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                actividad.setUserId(userIdNew);
            }
            if (tiacIdNew != null) {
                tiacIdNew = em.getReference(tiacIdNew.getClass(), tiacIdNew.getTiacId());
                actividad.setTiacId(tiacIdNew);
            }
            Collection<ContratoTramoActividad> attachedContratoTramoActividadCollectionNew = new ArrayList<ContratoTramoActividad>();
            for (ContratoTramoActividad contratoTramoActividadCollectionNewContratoTramoActividadToAttach : contratoTramoActividadCollectionNew) {
                contratoTramoActividadCollectionNewContratoTramoActividadToAttach = em.getReference(contratoTramoActividadCollectionNewContratoTramoActividadToAttach.getClass(), contratoTramoActividadCollectionNewContratoTramoActividadToAttach.getContTramActiId());
                attachedContratoTramoActividadCollectionNew.add(contratoTramoActividadCollectionNewContratoTramoActividadToAttach);
            }
            contratoTramoActividadCollectionNew = attachedContratoTramoActividadCollectionNew;
            actividad.setContratoTramoActividadCollection(contratoTramoActividadCollectionNew);
            actividad = em.merge(actividad);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getActividadCollection().remove(actividad);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getActividadCollection().add(actividad);
                userIdNew = em.merge(userIdNew);
            }
            if (tiacIdOld != null && !tiacIdOld.equals(tiacIdNew)) {
                tiacIdOld.getActividadCollection().remove(actividad);
                tiacIdOld = em.merge(tiacIdOld);
            }
            if (tiacIdNew != null && !tiacIdNew.equals(tiacIdOld)) {
                tiacIdNew.getActividadCollection().add(actividad);
                tiacIdNew = em.merge(tiacIdNew);
            }
            for (ContratoTramoActividad contratoTramoActividadCollectionNewContratoTramoActividad : contratoTramoActividadCollectionNew) {
                if (!contratoTramoActividadCollectionOld.contains(contratoTramoActividadCollectionNewContratoTramoActividad)) {
                    Actividad oldActiIdOfContratoTramoActividadCollectionNewContratoTramoActividad = contratoTramoActividadCollectionNewContratoTramoActividad.getActiId();
                    contratoTramoActividadCollectionNewContratoTramoActividad.setActiId(actividad);
                    contratoTramoActividadCollectionNewContratoTramoActividad = em.merge(contratoTramoActividadCollectionNewContratoTramoActividad);
                    if (oldActiIdOfContratoTramoActividadCollectionNewContratoTramoActividad != null && !oldActiIdOfContratoTramoActividadCollectionNewContratoTramoActividad.equals(actividad)) {
                        oldActiIdOfContratoTramoActividadCollectionNewContratoTramoActividad.getContratoTramoActividadCollection().remove(contratoTramoActividadCollectionNewContratoTramoActividad);
                        oldActiIdOfContratoTramoActividadCollectionNewContratoTramoActividad = em.merge(oldActiIdOfContratoTramoActividadCollectionNewContratoTramoActividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividad.getActiId();
                if (findActividad(id) == null) {
                    throw new NonexistentEntityException("The actividad with id " + id + " no longer exists.");
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
            Actividad actividad;
            try {
                actividad = em.getReference(Actividad.class, id);
                actividad.getActiId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionOrphanCheck = actividad.getContratoTramoActividadCollection();
            for (ContratoTramoActividad contratoTramoActividadCollectionOrphanCheckContratoTramoActividad : contratoTramoActividadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Actividad (" + actividad + ") cannot be destroyed since the ContratoTramoActividad " + contratoTramoActividadCollectionOrphanCheckContratoTramoActividad + " in its contratoTramoActividadCollection field has a non-nullable actiId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = actividad.getUserId();
            if (userId != null) {
                userId.getActividadCollection().remove(actividad);
                userId = em.merge(userId);
            }
            TipoActividad tiacId = actividad.getTiacId();
            if (tiacId != null) {
                tiacId.getActividadCollection().remove(actividad);
                tiacId = em.merge(tiacId);
            }
            em.remove(actividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actividad> findActividadEntities() {
        return findActividadEntities(true, -1, -1);
    }

    public List<Actividad> findActividadEntities(int maxResults, int firstResult) {
        return findActividadEntities(false, maxResults, firstResult);
    }

    private List<Actividad> findActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actividad.class));
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

    public Actividad findActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actividad> rt = cq.from(Actividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
