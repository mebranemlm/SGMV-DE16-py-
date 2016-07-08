/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Usuario;
import model.Personal;
import model.ContratoTramo;
import model.ContratoTramoPersonal;

/**
 *
 * @author Luis
 */
public class ContratoTramoPersonalJpaController implements Serializable {

    public ContratoTramoPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratoTramoPersonal contratoTramoPersonal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = contratoTramoPersonal.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                contratoTramoPersonal.setUserId(userId);
            }
            Personal persId = contratoTramoPersonal.getPersId();
            if (persId != null) {
                persId = em.getReference(persId.getClass(), persId.getPersId());
                contratoTramoPersonal.setPersId(persId);
            }
            ContratoTramo contTramId = contratoTramoPersonal.getContTramId();
            if (contTramId != null) {
                contTramId = em.getReference(contTramId.getClass(), contTramId.getContTramId());
                contratoTramoPersonal.setContTramId(contTramId);
            }
            em.persist(contratoTramoPersonal);
            if (userId != null) {
                userId.getContratoTramoPersonalCollection().add(contratoTramoPersonal);
                userId = em.merge(userId);
            }
            if (persId != null) {
                persId.getContratoTramoPersonalCollection().add(contratoTramoPersonal);
                persId = em.merge(persId);
            }
            if (contTramId != null) {
                contTramId.getContratoTramoPersonalCollection().add(contratoTramoPersonal);
                contTramId = em.merge(contTramId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratoTramoPersonal contratoTramoPersonal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoTramoPersonal persistentContratoTramoPersonal = em.find(ContratoTramoPersonal.class, contratoTramoPersonal.getContTramPersId());
            Usuario userIdOld = persistentContratoTramoPersonal.getUserId();
            Usuario userIdNew = contratoTramoPersonal.getUserId();
            Personal persIdOld = persistentContratoTramoPersonal.getPersId();
            Personal persIdNew = contratoTramoPersonal.getPersId();
            ContratoTramo contTramIdOld = persistentContratoTramoPersonal.getContTramId();
            ContratoTramo contTramIdNew = contratoTramoPersonal.getContTramId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                contratoTramoPersonal.setUserId(userIdNew);
            }
            if (persIdNew != null) {
                persIdNew = em.getReference(persIdNew.getClass(), persIdNew.getPersId());
                contratoTramoPersonal.setPersId(persIdNew);
            }
            if (contTramIdNew != null) {
                contTramIdNew = em.getReference(contTramIdNew.getClass(), contTramIdNew.getContTramId());
                contratoTramoPersonal.setContTramId(contTramIdNew);
            }
            contratoTramoPersonal = em.merge(contratoTramoPersonal);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getContratoTramoPersonalCollection().remove(contratoTramoPersonal);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getContratoTramoPersonalCollection().add(contratoTramoPersonal);
                userIdNew = em.merge(userIdNew);
            }
            if (persIdOld != null && !persIdOld.equals(persIdNew)) {
                persIdOld.getContratoTramoPersonalCollection().remove(contratoTramoPersonal);
                persIdOld = em.merge(persIdOld);
            }
            if (persIdNew != null && !persIdNew.equals(persIdOld)) {
                persIdNew.getContratoTramoPersonalCollection().add(contratoTramoPersonal);
                persIdNew = em.merge(persIdNew);
            }
            if (contTramIdOld != null && !contTramIdOld.equals(contTramIdNew)) {
                contTramIdOld.getContratoTramoPersonalCollection().remove(contratoTramoPersonal);
                contTramIdOld = em.merge(contTramIdOld);
            }
            if (contTramIdNew != null && !contTramIdNew.equals(contTramIdOld)) {
                contTramIdNew.getContratoTramoPersonalCollection().add(contratoTramoPersonal);
                contTramIdNew = em.merge(contTramIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratoTramoPersonal.getContTramPersId();
                if (findContratoTramoPersonal(id) == null) {
                    throw new NonexistentEntityException("The contratoTramoPersonal with id " + id + " no longer exists.");
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
            ContratoTramoPersonal contratoTramoPersonal;
            try {
                contratoTramoPersonal = em.getReference(ContratoTramoPersonal.class, id);
                contratoTramoPersonal.getContTramPersId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratoTramoPersonal with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = contratoTramoPersonal.getUserId();
            if (userId != null) {
                userId.getContratoTramoPersonalCollection().remove(contratoTramoPersonal);
                userId = em.merge(userId);
            }
            Personal persId = contratoTramoPersonal.getPersId();
            if (persId != null) {
                persId.getContratoTramoPersonalCollection().remove(contratoTramoPersonal);
                persId = em.merge(persId);
            }
            ContratoTramo contTramId = contratoTramoPersonal.getContTramId();
            if (contTramId != null) {
                contTramId.getContratoTramoPersonalCollection().remove(contratoTramoPersonal);
                contTramId = em.merge(contTramId);
            }
            em.remove(contratoTramoPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratoTramoPersonal> findContratoTramoPersonalEntities() {
        return findContratoTramoPersonalEntities(true, -1, -1);
    }

    public List<ContratoTramoPersonal> findContratoTramoPersonalEntities(int maxResults, int firstResult) {
        return findContratoTramoPersonalEntities(false, maxResults, firstResult);
    }

    private List<ContratoTramoPersonal> findContratoTramoPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoTramoPersonal.class));
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

    public ContratoTramoPersonal findContratoTramoPersonal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoTramoPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoTramoPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoTramoPersonal> rt = cq.from(ContratoTramoPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
