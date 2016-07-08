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
import model.ContratoTramo;
import model.Actividad;
import model.ContratoTramoActividad;

/**
 *
 * @author Luis
 */
public class ContratoTramoActividadJpaController implements Serializable {

    public ContratoTramoActividadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratoTramoActividad contratoTramoActividad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = contratoTramoActividad.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                contratoTramoActividad.setUserId(userId);
            }
            ContratoTramo contTramId = contratoTramoActividad.getContTramId();
            if (contTramId != null) {
                contTramId = em.getReference(contTramId.getClass(), contTramId.getContTramId());
                contratoTramoActividad.setContTramId(contTramId);
            }
            Actividad actiId = contratoTramoActividad.getActiId();
            if (actiId != null) {
                actiId = em.getReference(actiId.getClass(), actiId.getActiId());
                contratoTramoActividad.setActiId(actiId);
            }
            em.persist(contratoTramoActividad);
            if (userId != null) {
                userId.getContratoTramoActividadCollection().add(contratoTramoActividad);
                userId = em.merge(userId);
            }
            if (contTramId != null) {
                contTramId.getContratoTramoActividadCollection().add(contratoTramoActividad);
                contTramId = em.merge(contTramId);
            }
            if (actiId != null) {
                actiId.getContratoTramoActividadCollection().add(contratoTramoActividad);
                actiId = em.merge(actiId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratoTramoActividad contratoTramoActividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoTramoActividad persistentContratoTramoActividad = em.find(ContratoTramoActividad.class, contratoTramoActividad.getContTramActiId());
            Usuario userIdOld = persistentContratoTramoActividad.getUserId();
            Usuario userIdNew = contratoTramoActividad.getUserId();
            ContratoTramo contTramIdOld = persistentContratoTramoActividad.getContTramId();
            ContratoTramo contTramIdNew = contratoTramoActividad.getContTramId();
            Actividad actiIdOld = persistentContratoTramoActividad.getActiId();
            Actividad actiIdNew = contratoTramoActividad.getActiId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                contratoTramoActividad.setUserId(userIdNew);
            }
            if (contTramIdNew != null) {
                contTramIdNew = em.getReference(contTramIdNew.getClass(), contTramIdNew.getContTramId());
                contratoTramoActividad.setContTramId(contTramIdNew);
            }
            if (actiIdNew != null) {
                actiIdNew = em.getReference(actiIdNew.getClass(), actiIdNew.getActiId());
                contratoTramoActividad.setActiId(actiIdNew);
            }
            contratoTramoActividad = em.merge(contratoTramoActividad);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getContratoTramoActividadCollection().remove(contratoTramoActividad);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getContratoTramoActividadCollection().add(contratoTramoActividad);
                userIdNew = em.merge(userIdNew);
            }
            if (contTramIdOld != null && !contTramIdOld.equals(contTramIdNew)) {
                contTramIdOld.getContratoTramoActividadCollection().remove(contratoTramoActividad);
                contTramIdOld = em.merge(contTramIdOld);
            }
            if (contTramIdNew != null && !contTramIdNew.equals(contTramIdOld)) {
                contTramIdNew.getContratoTramoActividadCollection().add(contratoTramoActividad);
                contTramIdNew = em.merge(contTramIdNew);
            }
            if (actiIdOld != null && !actiIdOld.equals(actiIdNew)) {
                actiIdOld.getContratoTramoActividadCollection().remove(contratoTramoActividad);
                actiIdOld = em.merge(actiIdOld);
            }
            if (actiIdNew != null && !actiIdNew.equals(actiIdOld)) {
                actiIdNew.getContratoTramoActividadCollection().add(contratoTramoActividad);
                actiIdNew = em.merge(actiIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratoTramoActividad.getContTramActiId();
                if (findContratoTramoActividad(id) == null) {
                    throw new NonexistentEntityException("The contratoTramoActividad with id " + id + " no longer exists.");
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
            ContratoTramoActividad contratoTramoActividad;
            try {
                contratoTramoActividad = em.getReference(ContratoTramoActividad.class, id);
                contratoTramoActividad.getContTramActiId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratoTramoActividad with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = contratoTramoActividad.getUserId();
            if (userId != null) {
                userId.getContratoTramoActividadCollection().remove(contratoTramoActividad);
                userId = em.merge(userId);
            }
            ContratoTramo contTramId = contratoTramoActividad.getContTramId();
            if (contTramId != null) {
                contTramId.getContratoTramoActividadCollection().remove(contratoTramoActividad);
                contTramId = em.merge(contTramId);
            }
            Actividad actiId = contratoTramoActividad.getActiId();
            if (actiId != null) {
                actiId.getContratoTramoActividadCollection().remove(contratoTramoActividad);
                actiId = em.merge(actiId);
            }
            em.remove(contratoTramoActividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratoTramoActividad> findContratoTramoActividadEntities() {
        return findContratoTramoActividadEntities(true, -1, -1);
    }

    public List<ContratoTramoActividad> findContratoTramoActividadEntities(int maxResults, int firstResult) {
        return findContratoTramoActividadEntities(false, maxResults, firstResult);
    }

    private List<ContratoTramoActividad> findContratoTramoActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoTramoActividad.class));
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

    public ContratoTramoActividad findContratoTramoActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoTramoActividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoTramoActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoTramoActividad> rt = cq.from(ContratoTramoActividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
