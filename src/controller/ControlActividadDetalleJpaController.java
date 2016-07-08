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
import model.Elemento;
import model.Sector;
import model.CodigoElemento;
import model.Usuario;
import model.ControlActividad;
import model.ControlActividadDetalle;

/**
 *
 * @author Luis
 */
public class ControlActividadDetalleJpaController implements Serializable {

    public ControlActividadDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlActividadDetalle controlActividadDetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elemento elemId = controlActividadDetalle.getElemId();
            if (elemId != null) {
                elemId = em.getReference(elemId.getClass(), elemId.getElemId());
                controlActividadDetalle.setElemId(elemId);
            }
            Sector sectId = controlActividadDetalle.getSectId();
            if (sectId != null) {
                sectId = em.getReference(sectId.getClass(), sectId.getSectId());
                controlActividadDetalle.setSectId(sectId);
            }
            CodigoElemento coelId = controlActividadDetalle.getCoelId();
            if (coelId != null) {
                coelId = em.getReference(coelId.getClass(), coelId.getCoelId());
                controlActividadDetalle.setCoelId(coelId);
            }
            Usuario userId = controlActividadDetalle.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                controlActividadDetalle.setUserId(userId);
            }
            ControlActividad ctacId = controlActividadDetalle.getCtacId();
            if (ctacId != null) {
                ctacId = em.getReference(ctacId.getClass(), ctacId.getCtacId());
                controlActividadDetalle.setCtacId(ctacId);
            }
            em.persist(controlActividadDetalle);
            if (elemId != null) {
                elemId.getControlActividadDetalleCollection().add(controlActividadDetalle);
                elemId = em.merge(elemId);
            }
            if (sectId != null) {
                sectId.getControlActividadDetalleCollection().add(controlActividadDetalle);
                sectId = em.merge(sectId);
            }
            if (coelId != null) {
                coelId.getControlActividadDetalleCollection().add(controlActividadDetalle);
                coelId = em.merge(coelId);
            }
            if (userId != null) {
                userId.getControlActividadDetalleCollection().add(controlActividadDetalle);
                userId = em.merge(userId);
            }
            if (ctacId != null) {
                ctacId.getControlActividadDetalleCollection().add(controlActividadDetalle);
                ctacId = em.merge(ctacId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlActividadDetalle controlActividadDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlActividadDetalle persistentControlActividadDetalle = em.find(ControlActividadDetalle.class, controlActividadDetalle.getCtacDetaId());
            Elemento elemIdOld = persistentControlActividadDetalle.getElemId();
            Elemento elemIdNew = controlActividadDetalle.getElemId();
            Sector sectIdOld = persistentControlActividadDetalle.getSectId();
            Sector sectIdNew = controlActividadDetalle.getSectId();
            CodigoElemento coelIdOld = persistentControlActividadDetalle.getCoelId();
            CodigoElemento coelIdNew = controlActividadDetalle.getCoelId();
            Usuario userIdOld = persistentControlActividadDetalle.getUserId();
            Usuario userIdNew = controlActividadDetalle.getUserId();
            ControlActividad ctacIdOld = persistentControlActividadDetalle.getCtacId();
            ControlActividad ctacIdNew = controlActividadDetalle.getCtacId();
            if (elemIdNew != null) {
                elemIdNew = em.getReference(elemIdNew.getClass(), elemIdNew.getElemId());
                controlActividadDetalle.setElemId(elemIdNew);
            }
            if (sectIdNew != null) {
                sectIdNew = em.getReference(sectIdNew.getClass(), sectIdNew.getSectId());
                controlActividadDetalle.setSectId(sectIdNew);
            }
            if (coelIdNew != null) {
                coelIdNew = em.getReference(coelIdNew.getClass(), coelIdNew.getCoelId());
                controlActividadDetalle.setCoelId(coelIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                controlActividadDetalle.setUserId(userIdNew);
            }
            if (ctacIdNew != null) {
                ctacIdNew = em.getReference(ctacIdNew.getClass(), ctacIdNew.getCtacId());
                controlActividadDetalle.setCtacId(ctacIdNew);
            }
            controlActividadDetalle = em.merge(controlActividadDetalle);
            if (elemIdOld != null && !elemIdOld.equals(elemIdNew)) {
                elemIdOld.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                elemIdOld = em.merge(elemIdOld);
            }
            if (elemIdNew != null && !elemIdNew.equals(elemIdOld)) {
                elemIdNew.getControlActividadDetalleCollection().add(controlActividadDetalle);
                elemIdNew = em.merge(elemIdNew);
            }
            if (sectIdOld != null && !sectIdOld.equals(sectIdNew)) {
                sectIdOld.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                sectIdOld = em.merge(sectIdOld);
            }
            if (sectIdNew != null && !sectIdNew.equals(sectIdOld)) {
                sectIdNew.getControlActividadDetalleCollection().add(controlActividadDetalle);
                sectIdNew = em.merge(sectIdNew);
            }
            if (coelIdOld != null && !coelIdOld.equals(coelIdNew)) {
                coelIdOld.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                coelIdOld = em.merge(coelIdOld);
            }
            if (coelIdNew != null && !coelIdNew.equals(coelIdOld)) {
                coelIdNew.getControlActividadDetalleCollection().add(controlActividadDetalle);
                coelIdNew = em.merge(coelIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getControlActividadDetalleCollection().add(controlActividadDetalle);
                userIdNew = em.merge(userIdNew);
            }
            if (ctacIdOld != null && !ctacIdOld.equals(ctacIdNew)) {
                ctacIdOld.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                ctacIdOld = em.merge(ctacIdOld);
            }
            if (ctacIdNew != null && !ctacIdNew.equals(ctacIdOld)) {
                ctacIdNew.getControlActividadDetalleCollection().add(controlActividadDetalle);
                ctacIdNew = em.merge(ctacIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlActividadDetalle.getCtacDetaId();
                if (findControlActividadDetalle(id) == null) {
                    throw new NonexistentEntityException("The controlActividadDetalle with id " + id + " no longer exists.");
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
            ControlActividadDetalle controlActividadDetalle;
            try {
                controlActividadDetalle = em.getReference(ControlActividadDetalle.class, id);
                controlActividadDetalle.getCtacDetaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlActividadDetalle with id " + id + " no longer exists.", enfe);
            }
            Elemento elemId = controlActividadDetalle.getElemId();
            if (elemId != null) {
                elemId.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                elemId = em.merge(elemId);
            }
            Sector sectId = controlActividadDetalle.getSectId();
            if (sectId != null) {
                sectId.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                sectId = em.merge(sectId);
            }
            CodigoElemento coelId = controlActividadDetalle.getCoelId();
            if (coelId != null) {
                coelId.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                coelId = em.merge(coelId);
            }
            Usuario userId = controlActividadDetalle.getUserId();
            if (userId != null) {
                userId.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                userId = em.merge(userId);
            }
            ControlActividad ctacId = controlActividadDetalle.getCtacId();
            if (ctacId != null) {
                ctacId.getControlActividadDetalleCollection().remove(controlActividadDetalle);
                ctacId = em.merge(ctacId);
            }
            em.remove(controlActividadDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlActividadDetalle> findControlActividadDetalleEntities() {
        return findControlActividadDetalleEntities(true, -1, -1);
    }

    public List<ControlActividadDetalle> findControlActividadDetalleEntities(int maxResults, int firstResult) {
        return findControlActividadDetalleEntities(false, maxResults, firstResult);
    }

    private List<ControlActividadDetalle> findControlActividadDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlActividadDetalle.class));
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

    public ControlActividadDetalle findControlActividadDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlActividadDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlActividadDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlActividadDetalle> rt = cq.from(ControlActividadDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
