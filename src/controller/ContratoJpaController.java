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
import model.Cliente;
import model.ContratoTramo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Contrato;

/**
 *
 * @author Luis
 */
public class ContratoJpaController implements Serializable {

    public ContratoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contrato contrato) {
        if (contrato.getContratoTramoCollection() == null) {
            contrato.setContratoTramoCollection(new ArrayList<ContratoTramo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = contrato.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                contrato.setUserId(userId);
            }
            Cliente clieId = contrato.getClieId();
            if (clieId != null) {
                clieId = em.getReference(clieId.getClass(), clieId.getClieId());
                contrato.setClieId(clieId);
            }
            Collection<ContratoTramo> attachedContratoTramoCollection = new ArrayList<ContratoTramo>();
            for (ContratoTramo contratoTramoCollectionContratoTramoToAttach : contrato.getContratoTramoCollection()) {
                contratoTramoCollectionContratoTramoToAttach = em.getReference(contratoTramoCollectionContratoTramoToAttach.getClass(), contratoTramoCollectionContratoTramoToAttach.getContTramId());
                attachedContratoTramoCollection.add(contratoTramoCollectionContratoTramoToAttach);
            }
            contrato.setContratoTramoCollection(attachedContratoTramoCollection);
            em.persist(contrato);
            if (userId != null) {
                userId.getContratoCollection().add(contrato);
                userId = em.merge(userId);
            }
            if (clieId != null) {
                clieId.getContratoCollection().add(contrato);
                clieId = em.merge(clieId);
            }
            for (ContratoTramo contratoTramoCollectionContratoTramo : contrato.getContratoTramoCollection()) {
                Contrato oldContIdOfContratoTramoCollectionContratoTramo = contratoTramoCollectionContratoTramo.getContId();
                contratoTramoCollectionContratoTramo.setContId(contrato);
                contratoTramoCollectionContratoTramo = em.merge(contratoTramoCollectionContratoTramo);
                if (oldContIdOfContratoTramoCollectionContratoTramo != null) {
                    oldContIdOfContratoTramoCollectionContratoTramo.getContratoTramoCollection().remove(contratoTramoCollectionContratoTramo);
                    oldContIdOfContratoTramoCollectionContratoTramo = em.merge(oldContIdOfContratoTramoCollectionContratoTramo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contrato contrato) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contrato persistentContrato = em.find(Contrato.class, contrato.getContId());
            Usuario userIdOld = persistentContrato.getUserId();
            Usuario userIdNew = contrato.getUserId();
            Cliente clieIdOld = persistentContrato.getClieId();
            Cliente clieIdNew = contrato.getClieId();
            Collection<ContratoTramo> contratoTramoCollectionOld = persistentContrato.getContratoTramoCollection();
            Collection<ContratoTramo> contratoTramoCollectionNew = contrato.getContratoTramoCollection();
            List<String> illegalOrphanMessages = null;
            for (ContratoTramo contratoTramoCollectionOldContratoTramo : contratoTramoCollectionOld) {
                if (!contratoTramoCollectionNew.contains(contratoTramoCollectionOldContratoTramo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContratoTramo " + contratoTramoCollectionOldContratoTramo + " since its contId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                contrato.setUserId(userIdNew);
            }
            if (clieIdNew != null) {
                clieIdNew = em.getReference(clieIdNew.getClass(), clieIdNew.getClieId());
                contrato.setClieId(clieIdNew);
            }
            Collection<ContratoTramo> attachedContratoTramoCollectionNew = new ArrayList<ContratoTramo>();
            for (ContratoTramo contratoTramoCollectionNewContratoTramoToAttach : contratoTramoCollectionNew) {
                contratoTramoCollectionNewContratoTramoToAttach = em.getReference(contratoTramoCollectionNewContratoTramoToAttach.getClass(), contratoTramoCollectionNewContratoTramoToAttach.getContTramId());
                attachedContratoTramoCollectionNew.add(contratoTramoCollectionNewContratoTramoToAttach);
            }
            contratoTramoCollectionNew = attachedContratoTramoCollectionNew;
            contrato.setContratoTramoCollection(contratoTramoCollectionNew);
            contrato = em.merge(contrato);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getContratoCollection().remove(contrato);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getContratoCollection().add(contrato);
                userIdNew = em.merge(userIdNew);
            }
            if (clieIdOld != null && !clieIdOld.equals(clieIdNew)) {
                clieIdOld.getContratoCollection().remove(contrato);
                clieIdOld = em.merge(clieIdOld);
            }
            if (clieIdNew != null && !clieIdNew.equals(clieIdOld)) {
                clieIdNew.getContratoCollection().add(contrato);
                clieIdNew = em.merge(clieIdNew);
            }
            for (ContratoTramo contratoTramoCollectionNewContratoTramo : contratoTramoCollectionNew) {
                if (!contratoTramoCollectionOld.contains(contratoTramoCollectionNewContratoTramo)) {
                    Contrato oldContIdOfContratoTramoCollectionNewContratoTramo = contratoTramoCollectionNewContratoTramo.getContId();
                    contratoTramoCollectionNewContratoTramo.setContId(contrato);
                    contratoTramoCollectionNewContratoTramo = em.merge(contratoTramoCollectionNewContratoTramo);
                    if (oldContIdOfContratoTramoCollectionNewContratoTramo != null && !oldContIdOfContratoTramoCollectionNewContratoTramo.equals(contrato)) {
                        oldContIdOfContratoTramoCollectionNewContratoTramo.getContratoTramoCollection().remove(contratoTramoCollectionNewContratoTramo);
                        oldContIdOfContratoTramoCollectionNewContratoTramo = em.merge(oldContIdOfContratoTramoCollectionNewContratoTramo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contrato.getContId();
                if (findContrato(id) == null) {
                    throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.");
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
            Contrato contrato;
            try {
                contrato = em.getReference(Contrato.class, id);
                contrato.getContId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ContratoTramo> contratoTramoCollectionOrphanCheck = contrato.getContratoTramoCollection();
            for (ContratoTramo contratoTramoCollectionOrphanCheckContratoTramo : contratoTramoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the ContratoTramo " + contratoTramoCollectionOrphanCheckContratoTramo + " in its contratoTramoCollection field has a non-nullable contId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = contrato.getUserId();
            if (userId != null) {
                userId.getContratoCollection().remove(contrato);
                userId = em.merge(userId);
            }
            Cliente clieId = contrato.getClieId();
            if (clieId != null) {
                clieId.getContratoCollection().remove(contrato);
                clieId = em.merge(clieId);
            }
            em.remove(contrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contrato> findContratoEntities() {
        return findContratoEntities(true, -1, -1);
    }

    public List<Contrato> findContratoEntities(int maxResults, int firstResult) {
        return findContratoEntities(false, maxResults, firstResult);
    }

    private List<Contrato> findContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contrato.class));
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

    public Contrato findContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contrato> rt = cq.from(Contrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
