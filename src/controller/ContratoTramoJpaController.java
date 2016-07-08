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
import model.Tramo;
import model.Contrato;
import model.ContratoTramoActividad;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.ContratoTramo;
import model.ContratoTramoPersonal;

/**
 *
 * @author Luis
 */
public class ContratoTramoJpaController implements Serializable {

    public ContratoTramoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratoTramo contratoTramo) {
        if (contratoTramo.getContratoTramoActividadCollection() == null) {
            contratoTramo.setContratoTramoActividadCollection(new ArrayList<ContratoTramoActividad>());
        }
        if (contratoTramo.getContratoTramoPersonalCollection() == null) {
            contratoTramo.setContratoTramoPersonalCollection(new ArrayList<ContratoTramoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = contratoTramo.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                contratoTramo.setUserId(userId);
            }
            Tramo tramId = contratoTramo.getTramId();
            if (tramId != null) {
                tramId = em.getReference(tramId.getClass(), tramId.getTramId());
                contratoTramo.setTramId(tramId);
            }
            Contrato contId = contratoTramo.getContId();
            if (contId != null) {
                contId = em.getReference(contId.getClass(), contId.getContId());
                contratoTramo.setContId(contId);
            }
            Collection<ContratoTramoActividad> attachedContratoTramoActividadCollection = new ArrayList<ContratoTramoActividad>();
            for (ContratoTramoActividad contratoTramoActividadCollectionContratoTramoActividadToAttach : contratoTramo.getContratoTramoActividadCollection()) {
                contratoTramoActividadCollectionContratoTramoActividadToAttach = em.getReference(contratoTramoActividadCollectionContratoTramoActividadToAttach.getClass(), contratoTramoActividadCollectionContratoTramoActividadToAttach.getContTramActiId());
                attachedContratoTramoActividadCollection.add(contratoTramoActividadCollectionContratoTramoActividadToAttach);
            }
            contratoTramo.setContratoTramoActividadCollection(attachedContratoTramoActividadCollection);
            Collection<ContratoTramoPersonal> attachedContratoTramoPersonalCollection = new ArrayList<ContratoTramoPersonal>();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionContratoTramoPersonalToAttach : contratoTramo.getContratoTramoPersonalCollection()) {
                contratoTramoPersonalCollectionContratoTramoPersonalToAttach = em.getReference(contratoTramoPersonalCollectionContratoTramoPersonalToAttach.getClass(), contratoTramoPersonalCollectionContratoTramoPersonalToAttach.getContTramPersId());
                attachedContratoTramoPersonalCollection.add(contratoTramoPersonalCollectionContratoTramoPersonalToAttach);
            }
            contratoTramo.setContratoTramoPersonalCollection(attachedContratoTramoPersonalCollection);
            em.persist(contratoTramo);
            if (userId != null) {
                userId.getContratoTramoCollection().add(contratoTramo);
                userId = em.merge(userId);
            }
            if (tramId != null) {
                tramId.getContratoTramoCollection().add(contratoTramo);
                tramId = em.merge(tramId);
            }
            if (contId != null) {
                contId.getContratoTramoCollection().add(contratoTramo);
                contId = em.merge(contId);
            }
            for (ContratoTramoActividad contratoTramoActividadCollectionContratoTramoActividad : contratoTramo.getContratoTramoActividadCollection()) {
                ContratoTramo oldContTramIdOfContratoTramoActividadCollectionContratoTramoActividad = contratoTramoActividadCollectionContratoTramoActividad.getContTramId();
                contratoTramoActividadCollectionContratoTramoActividad.setContTramId(contratoTramo);
                contratoTramoActividadCollectionContratoTramoActividad = em.merge(contratoTramoActividadCollectionContratoTramoActividad);
                if (oldContTramIdOfContratoTramoActividadCollectionContratoTramoActividad != null) {
                    oldContTramIdOfContratoTramoActividadCollectionContratoTramoActividad.getContratoTramoActividadCollection().remove(contratoTramoActividadCollectionContratoTramoActividad);
                    oldContTramIdOfContratoTramoActividadCollectionContratoTramoActividad = em.merge(oldContTramIdOfContratoTramoActividadCollectionContratoTramoActividad);
                }
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionContratoTramoPersonal : contratoTramo.getContratoTramoPersonalCollection()) {
                ContratoTramo oldContTramIdOfContratoTramoPersonalCollectionContratoTramoPersonal = contratoTramoPersonalCollectionContratoTramoPersonal.getContTramId();
                contratoTramoPersonalCollectionContratoTramoPersonal.setContTramId(contratoTramo);
                contratoTramoPersonalCollectionContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionContratoTramoPersonal);
                if (oldContTramIdOfContratoTramoPersonalCollectionContratoTramoPersonal != null) {
                    oldContTramIdOfContratoTramoPersonalCollectionContratoTramoPersonal.getContratoTramoPersonalCollection().remove(contratoTramoPersonalCollectionContratoTramoPersonal);
                    oldContTramIdOfContratoTramoPersonalCollectionContratoTramoPersonal = em.merge(oldContTramIdOfContratoTramoPersonalCollectionContratoTramoPersonal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratoTramo contratoTramo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoTramo persistentContratoTramo = em.find(ContratoTramo.class, contratoTramo.getContTramId());
            Usuario userIdOld = persistentContratoTramo.getUserId();
            Usuario userIdNew = contratoTramo.getUserId();
            Tramo tramIdOld = persistentContratoTramo.getTramId();
            Tramo tramIdNew = contratoTramo.getTramId();
            Contrato contIdOld = persistentContratoTramo.getContId();
            Contrato contIdNew = contratoTramo.getContId();
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionOld = persistentContratoTramo.getContratoTramoActividadCollection();
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionNew = contratoTramo.getContratoTramoActividadCollection();
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionOld = persistentContratoTramo.getContratoTramoPersonalCollection();
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionNew = contratoTramo.getContratoTramoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (ContratoTramoActividad contratoTramoActividadCollectionOldContratoTramoActividad : contratoTramoActividadCollectionOld) {
                if (!contratoTramoActividadCollectionNew.contains(contratoTramoActividadCollectionOldContratoTramoActividad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContratoTramoActividad " + contratoTramoActividadCollectionOldContratoTramoActividad + " since its contTramId field is not nullable.");
                }
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionOldContratoTramoPersonal : contratoTramoPersonalCollectionOld) {
                if (!contratoTramoPersonalCollectionNew.contains(contratoTramoPersonalCollectionOldContratoTramoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContratoTramoPersonal " + contratoTramoPersonalCollectionOldContratoTramoPersonal + " since its contTramId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                contratoTramo.setUserId(userIdNew);
            }
            if (tramIdNew != null) {
                tramIdNew = em.getReference(tramIdNew.getClass(), tramIdNew.getTramId());
                contratoTramo.setTramId(tramIdNew);
            }
            if (contIdNew != null) {
                contIdNew = em.getReference(contIdNew.getClass(), contIdNew.getContId());
                contratoTramo.setContId(contIdNew);
            }
            Collection<ContratoTramoActividad> attachedContratoTramoActividadCollectionNew = new ArrayList<ContratoTramoActividad>();
            for (ContratoTramoActividad contratoTramoActividadCollectionNewContratoTramoActividadToAttach : contratoTramoActividadCollectionNew) {
                contratoTramoActividadCollectionNewContratoTramoActividadToAttach = em.getReference(contratoTramoActividadCollectionNewContratoTramoActividadToAttach.getClass(), contratoTramoActividadCollectionNewContratoTramoActividadToAttach.getContTramActiId());
                attachedContratoTramoActividadCollectionNew.add(contratoTramoActividadCollectionNewContratoTramoActividadToAttach);
            }
            contratoTramoActividadCollectionNew = attachedContratoTramoActividadCollectionNew;
            contratoTramo.setContratoTramoActividadCollection(contratoTramoActividadCollectionNew);
            Collection<ContratoTramoPersonal> attachedContratoTramoPersonalCollectionNew = new ArrayList<ContratoTramoPersonal>();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach : contratoTramoPersonalCollectionNew) {
                contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach = em.getReference(contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach.getClass(), contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach.getContTramPersId());
                attachedContratoTramoPersonalCollectionNew.add(contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach);
            }
            contratoTramoPersonalCollectionNew = attachedContratoTramoPersonalCollectionNew;
            contratoTramo.setContratoTramoPersonalCollection(contratoTramoPersonalCollectionNew);
            contratoTramo = em.merge(contratoTramo);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getContratoTramoCollection().remove(contratoTramo);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getContratoTramoCollection().add(contratoTramo);
                userIdNew = em.merge(userIdNew);
            }
            if (tramIdOld != null && !tramIdOld.equals(tramIdNew)) {
                tramIdOld.getContratoTramoCollection().remove(contratoTramo);
                tramIdOld = em.merge(tramIdOld);
            }
            if (tramIdNew != null && !tramIdNew.equals(tramIdOld)) {
                tramIdNew.getContratoTramoCollection().add(contratoTramo);
                tramIdNew = em.merge(tramIdNew);
            }
            if (contIdOld != null && !contIdOld.equals(contIdNew)) {
                contIdOld.getContratoTramoCollection().remove(contratoTramo);
                contIdOld = em.merge(contIdOld);
            }
            if (contIdNew != null && !contIdNew.equals(contIdOld)) {
                contIdNew.getContratoTramoCollection().add(contratoTramo);
                contIdNew = em.merge(contIdNew);
            }
            for (ContratoTramoActividad contratoTramoActividadCollectionNewContratoTramoActividad : contratoTramoActividadCollectionNew) {
                if (!contratoTramoActividadCollectionOld.contains(contratoTramoActividadCollectionNewContratoTramoActividad)) {
                    ContratoTramo oldContTramIdOfContratoTramoActividadCollectionNewContratoTramoActividad = contratoTramoActividadCollectionNewContratoTramoActividad.getContTramId();
                    contratoTramoActividadCollectionNewContratoTramoActividad.setContTramId(contratoTramo);
                    contratoTramoActividadCollectionNewContratoTramoActividad = em.merge(contratoTramoActividadCollectionNewContratoTramoActividad);
                    if (oldContTramIdOfContratoTramoActividadCollectionNewContratoTramoActividad != null && !oldContTramIdOfContratoTramoActividadCollectionNewContratoTramoActividad.equals(contratoTramo)) {
                        oldContTramIdOfContratoTramoActividadCollectionNewContratoTramoActividad.getContratoTramoActividadCollection().remove(contratoTramoActividadCollectionNewContratoTramoActividad);
                        oldContTramIdOfContratoTramoActividadCollectionNewContratoTramoActividad = em.merge(oldContTramIdOfContratoTramoActividadCollectionNewContratoTramoActividad);
                    }
                }
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionNewContratoTramoPersonal : contratoTramoPersonalCollectionNew) {
                if (!contratoTramoPersonalCollectionOld.contains(contratoTramoPersonalCollectionNewContratoTramoPersonal)) {
                    ContratoTramo oldContTramIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal = contratoTramoPersonalCollectionNewContratoTramoPersonal.getContTramId();
                    contratoTramoPersonalCollectionNewContratoTramoPersonal.setContTramId(contratoTramo);
                    contratoTramoPersonalCollectionNewContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionNewContratoTramoPersonal);
                    if (oldContTramIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal != null && !oldContTramIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal.equals(contratoTramo)) {
                        oldContTramIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal.getContratoTramoPersonalCollection().remove(contratoTramoPersonalCollectionNewContratoTramoPersonal);
                        oldContTramIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal = em.merge(oldContTramIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratoTramo.getContTramId();
                if (findContratoTramo(id) == null) {
                    throw new NonexistentEntityException("The contratoTramo with id " + id + " no longer exists.");
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
            ContratoTramo contratoTramo;
            try {
                contratoTramo = em.getReference(ContratoTramo.class, id);
                contratoTramo.getContTramId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratoTramo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ContratoTramoActividad> contratoTramoActividadCollectionOrphanCheck = contratoTramo.getContratoTramoActividadCollection();
            for (ContratoTramoActividad contratoTramoActividadCollectionOrphanCheckContratoTramoActividad : contratoTramoActividadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ContratoTramo (" + contratoTramo + ") cannot be destroyed since the ContratoTramoActividad " + contratoTramoActividadCollectionOrphanCheckContratoTramoActividad + " in its contratoTramoActividadCollection field has a non-nullable contTramId field.");
            }
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionOrphanCheck = contratoTramo.getContratoTramoPersonalCollection();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionOrphanCheckContratoTramoPersonal : contratoTramoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ContratoTramo (" + contratoTramo + ") cannot be destroyed since the ContratoTramoPersonal " + contratoTramoPersonalCollectionOrphanCheckContratoTramoPersonal + " in its contratoTramoPersonalCollection field has a non-nullable contTramId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = contratoTramo.getUserId();
            if (userId != null) {
                userId.getContratoTramoCollection().remove(contratoTramo);
                userId = em.merge(userId);
            }
            Tramo tramId = contratoTramo.getTramId();
            if (tramId != null) {
                tramId.getContratoTramoCollection().remove(contratoTramo);
                tramId = em.merge(tramId);
            }
            Contrato contId = contratoTramo.getContId();
            if (contId != null) {
                contId.getContratoTramoCollection().remove(contratoTramo);
                contId = em.merge(contId);
            }
            em.remove(contratoTramo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratoTramo> findContratoTramoEntities() {
        return findContratoTramoEntities(true, -1, -1);
    }

    public List<ContratoTramo> findContratoTramoEntities(int maxResults, int firstResult) {
        return findContratoTramoEntities(false, maxResults, firstResult);
    }

    private List<ContratoTramo> findContratoTramoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoTramo.class));
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

    public ContratoTramo findContratoTramo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoTramo.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoTramoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoTramo> rt = cq.from(ContratoTramo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
