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
import model.Tramo;
import model.Elemento;
import model.CodigoElemento;
import model.TramoCodigoElemento;

/**
 *
 * @author Luis
 */
public class TramoCodigoElementoJpaController implements Serializable {

    public TramoCodigoElementoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TramoCodigoElemento tramoCodigoElemento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = tramoCodigoElemento.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                tramoCodigoElemento.setUserId(userId);
            }
            Tramo tramId = tramoCodigoElemento.getTramId();
            if (tramId != null) {
                tramId = em.getReference(tramId.getClass(), tramId.getTramId());
                tramoCodigoElemento.setTramId(tramId);
            }
            Elemento elemId = tramoCodigoElemento.getElemId();
            if (elemId != null) {
                elemId = em.getReference(elemId.getClass(), elemId.getElemId());
                tramoCodigoElemento.setElemId(elemId);
            }
            CodigoElemento coelId = tramoCodigoElemento.getCoelId();
            if (coelId != null) {
                coelId = em.getReference(coelId.getClass(), coelId.getCoelId());
                tramoCodigoElemento.setCoelId(coelId);
            }
            em.persist(tramoCodigoElemento);
            if (userId != null) {
                userId.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                userId = em.merge(userId);
            }
            if (tramId != null) {
                tramId.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                tramId = em.merge(tramId);
            }
            if (elemId != null) {
                elemId.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                elemId = em.merge(elemId);
            }
            if (coelId != null) {
                coelId.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                coelId = em.merge(coelId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TramoCodigoElemento tramoCodigoElemento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TramoCodigoElemento persistentTramoCodigoElemento = em.find(TramoCodigoElemento.class, tramoCodigoElemento.getTramCoelId());
            Usuario userIdOld = persistentTramoCodigoElemento.getUserId();
            Usuario userIdNew = tramoCodigoElemento.getUserId();
            Tramo tramIdOld = persistentTramoCodigoElemento.getTramId();
            Tramo tramIdNew = tramoCodigoElemento.getTramId();
            Elemento elemIdOld = persistentTramoCodigoElemento.getElemId();
            Elemento elemIdNew = tramoCodigoElemento.getElemId();
            CodigoElemento coelIdOld = persistentTramoCodigoElemento.getCoelId();
            CodigoElemento coelIdNew = tramoCodigoElemento.getCoelId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                tramoCodigoElemento.setUserId(userIdNew);
            }
            if (tramIdNew != null) {
                tramIdNew = em.getReference(tramIdNew.getClass(), tramIdNew.getTramId());
                tramoCodigoElemento.setTramId(tramIdNew);
            }
            if (elemIdNew != null) {
                elemIdNew = em.getReference(elemIdNew.getClass(), elemIdNew.getElemId());
                tramoCodigoElemento.setElemId(elemIdNew);
            }
            if (coelIdNew != null) {
                coelIdNew = em.getReference(coelIdNew.getClass(), coelIdNew.getCoelId());
                tramoCodigoElemento.setCoelId(coelIdNew);
            }
            tramoCodigoElemento = em.merge(tramoCodigoElemento);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                userIdNew = em.merge(userIdNew);
            }
            if (tramIdOld != null && !tramIdOld.equals(tramIdNew)) {
                tramIdOld.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                tramIdOld = em.merge(tramIdOld);
            }
            if (tramIdNew != null && !tramIdNew.equals(tramIdOld)) {
                tramIdNew.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                tramIdNew = em.merge(tramIdNew);
            }
            if (elemIdOld != null && !elemIdOld.equals(elemIdNew)) {
                elemIdOld.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                elemIdOld = em.merge(elemIdOld);
            }
            if (elemIdNew != null && !elemIdNew.equals(elemIdOld)) {
                elemIdNew.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                elemIdNew = em.merge(elemIdNew);
            }
            if (coelIdOld != null && !coelIdOld.equals(coelIdNew)) {
                coelIdOld.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                coelIdOld = em.merge(coelIdOld);
            }
            if (coelIdNew != null && !coelIdNew.equals(coelIdOld)) {
                coelIdNew.getTramoCodigoElementoCollection().add(tramoCodigoElemento);
                coelIdNew = em.merge(coelIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tramoCodigoElemento.getTramCoelId();
                if (findTramoCodigoElemento(id) == null) {
                    throw new NonexistentEntityException("The tramoCodigoElemento with id " + id + " no longer exists.");
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
            TramoCodigoElemento tramoCodigoElemento;
            try {
                tramoCodigoElemento = em.getReference(TramoCodigoElemento.class, id);
                tramoCodigoElemento.getTramCoelId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tramoCodigoElemento with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = tramoCodigoElemento.getUserId();
            if (userId != null) {
                userId.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                userId = em.merge(userId);
            }
            Tramo tramId = tramoCodigoElemento.getTramId();
            if (tramId != null) {
                tramId.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                tramId = em.merge(tramId);
            }
            Elemento elemId = tramoCodigoElemento.getElemId();
            if (elemId != null) {
                elemId.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                elemId = em.merge(elemId);
            }
            CodigoElemento coelId = tramoCodigoElemento.getCoelId();
            if (coelId != null) {
                coelId.getTramoCodigoElementoCollection().remove(tramoCodigoElemento);
                coelId = em.merge(coelId);
            }
            em.remove(tramoCodigoElemento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TramoCodigoElemento> findTramoCodigoElementoEntities() {
        return findTramoCodigoElementoEntities(true, -1, -1);
    }

    public List<TramoCodigoElemento> findTramoCodigoElementoEntities(int maxResults, int firstResult) {
        return findTramoCodigoElementoEntities(false, maxResults, firstResult);
    }

    private List<TramoCodigoElemento> findTramoCodigoElementoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TramoCodigoElemento.class));
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

    public TramoCodigoElemento findTramoCodigoElemento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TramoCodigoElemento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTramoCodigoElementoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TramoCodigoElemento> rt = cq.from(TramoCodigoElemento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
