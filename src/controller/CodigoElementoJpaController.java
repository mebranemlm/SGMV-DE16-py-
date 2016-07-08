/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Usuario;
import model.Elemento;
import model.TramoCodigoElemento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.CodigoElemento;
import model.ControlActividadDetalle;

/**
 *
 * @author Luis
 */
public class CodigoElementoJpaController implements Serializable {

    public CodigoElementoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CodigoElemento codigoElemento) {
        if (codigoElemento.getTramoCodigoElementoCollection() == null) {
            codigoElemento.setTramoCodigoElementoCollection(new ArrayList<TramoCodigoElemento>());
        }
        if (codigoElemento.getControlActividadDetalleCollection() == null) {
            codigoElemento.setControlActividadDetalleCollection(new ArrayList<ControlActividadDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = codigoElemento.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                codigoElemento.setUserId(userId);
            }
            Elemento elemId = codigoElemento.getElemId();
            if (elemId != null) {
                elemId = em.getReference(elemId.getClass(), elemId.getElemId());
                codigoElemento.setElemId(elemId);
            }
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollection = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElementoToAttach : codigoElemento.getTramoCodigoElementoCollection()) {
                tramoCodigoElementoCollectionTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollection.add(tramoCodigoElementoCollectionTramoCodigoElementoToAttach);
            }
            codigoElemento.setTramoCodigoElementoCollection(attachedTramoCodigoElementoCollection);
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollection = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalleToAttach : codigoElemento.getControlActividadDetalleCollection()) {
                controlActividadDetalleCollectionControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollection.add(controlActividadDetalleCollectionControlActividadDetalleToAttach);
            }
            codigoElemento.setControlActividadDetalleCollection(attachedControlActividadDetalleCollection);
            em.persist(codigoElemento);
            if (userId != null) {
                userId.getCodigoElementoCollection().add(codigoElemento);
                userId = em.merge(userId);
            }
            if (elemId != null) {
                elemId.getCodigoElementoCollection().add(codigoElemento);
                elemId = em.merge(elemId);
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElemento : codigoElemento.getTramoCodigoElementoCollection()) {
                CodigoElemento oldCoelIdOfTramoCodigoElementoCollectionTramoCodigoElemento = tramoCodigoElementoCollectionTramoCodigoElemento.getCoelId();
                tramoCodigoElementoCollectionTramoCodigoElemento.setCoelId(codigoElemento);
                tramoCodigoElementoCollectionTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionTramoCodigoElemento);
                if (oldCoelIdOfTramoCodigoElementoCollectionTramoCodigoElemento != null) {
                    oldCoelIdOfTramoCodigoElementoCollectionTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionTramoCodigoElemento);
                    oldCoelIdOfTramoCodigoElementoCollectionTramoCodigoElemento = em.merge(oldCoelIdOfTramoCodigoElementoCollectionTramoCodigoElemento);
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalle : codigoElemento.getControlActividadDetalleCollection()) {
                CodigoElemento oldCoelIdOfControlActividadDetalleCollectionControlActividadDetalle = controlActividadDetalleCollectionControlActividadDetalle.getCoelId();
                controlActividadDetalleCollectionControlActividadDetalle.setCoelId(codigoElemento);
                controlActividadDetalleCollectionControlActividadDetalle = em.merge(controlActividadDetalleCollectionControlActividadDetalle);
                if (oldCoelIdOfControlActividadDetalleCollectionControlActividadDetalle != null) {
                    oldCoelIdOfControlActividadDetalleCollectionControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionControlActividadDetalle);
                    oldCoelIdOfControlActividadDetalleCollectionControlActividadDetalle = em.merge(oldCoelIdOfControlActividadDetalleCollectionControlActividadDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CodigoElemento codigoElemento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CodigoElemento persistentCodigoElemento = em.find(CodigoElemento.class, codigoElemento.getCoelId());
            Usuario userIdOld = persistentCodigoElemento.getUserId();
            Usuario userIdNew = codigoElemento.getUserId();
            Elemento elemIdOld = persistentCodigoElemento.getElemId();
            Elemento elemIdNew = codigoElemento.getElemId();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionOld = persistentCodigoElemento.getTramoCodigoElementoCollection();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionNew = codigoElemento.getTramoCodigoElementoCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOld = persistentCodigoElemento.getControlActividadDetalleCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionNew = codigoElemento.getControlActividadDetalleCollection();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                codigoElemento.setUserId(userIdNew);
            }
            if (elemIdNew != null) {
                elemIdNew = em.getReference(elemIdNew.getClass(), elemIdNew.getElemId());
                codigoElemento.setElemId(elemIdNew);
            }
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollectionNew = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach : tramoCodigoElementoCollectionNew) {
                tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollectionNew.add(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach);
            }
            tramoCodigoElementoCollectionNew = attachedTramoCodigoElementoCollectionNew;
            codigoElemento.setTramoCodigoElementoCollection(tramoCodigoElementoCollectionNew);
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollectionNew = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalleToAttach : controlActividadDetalleCollectionNew) {
                controlActividadDetalleCollectionNewControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollectionNew.add(controlActividadDetalleCollectionNewControlActividadDetalleToAttach);
            }
            controlActividadDetalleCollectionNew = attachedControlActividadDetalleCollectionNew;
            codigoElemento.setControlActividadDetalleCollection(controlActividadDetalleCollectionNew);
            codigoElemento = em.merge(codigoElemento);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getCodigoElementoCollection().remove(codigoElemento);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getCodigoElementoCollection().add(codigoElemento);
                userIdNew = em.merge(userIdNew);
            }
            if (elemIdOld != null && !elemIdOld.equals(elemIdNew)) {
                elemIdOld.getCodigoElementoCollection().remove(codigoElemento);
                elemIdOld = em.merge(elemIdOld);
            }
            if (elemIdNew != null && !elemIdNew.equals(elemIdOld)) {
                elemIdNew.getCodigoElementoCollection().add(codigoElemento);
                elemIdNew = em.merge(elemIdNew);
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionOldTramoCodigoElemento : tramoCodigoElementoCollectionOld) {
                if (!tramoCodigoElementoCollectionNew.contains(tramoCodigoElementoCollectionOldTramoCodigoElemento)) {
                    tramoCodigoElementoCollectionOldTramoCodigoElemento.setCoelId(null);
                    tramoCodigoElementoCollectionOldTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionOldTramoCodigoElemento);
                }
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElemento : tramoCodigoElementoCollectionNew) {
                if (!tramoCodigoElementoCollectionOld.contains(tramoCodigoElementoCollectionNewTramoCodigoElemento)) {
                    CodigoElemento oldCoelIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = tramoCodigoElementoCollectionNewTramoCodigoElemento.getCoelId();
                    tramoCodigoElementoCollectionNewTramoCodigoElemento.setCoelId(codigoElemento);
                    tramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                    if (oldCoelIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento != null && !oldCoelIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.equals(codigoElemento)) {
                        oldCoelIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                        oldCoelIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(oldCoelIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento);
                    }
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionOldControlActividadDetalle : controlActividadDetalleCollectionOld) {
                if (!controlActividadDetalleCollectionNew.contains(controlActividadDetalleCollectionOldControlActividadDetalle)) {
                    controlActividadDetalleCollectionOldControlActividadDetalle.setCoelId(null);
                    controlActividadDetalleCollectionOldControlActividadDetalle = em.merge(controlActividadDetalleCollectionOldControlActividadDetalle);
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalle : controlActividadDetalleCollectionNew) {
                if (!controlActividadDetalleCollectionOld.contains(controlActividadDetalleCollectionNewControlActividadDetalle)) {
                    CodigoElemento oldCoelIdOfControlActividadDetalleCollectionNewControlActividadDetalle = controlActividadDetalleCollectionNewControlActividadDetalle.getCoelId();
                    controlActividadDetalleCollectionNewControlActividadDetalle.setCoelId(codigoElemento);
                    controlActividadDetalleCollectionNewControlActividadDetalle = em.merge(controlActividadDetalleCollectionNewControlActividadDetalle);
                    if (oldCoelIdOfControlActividadDetalleCollectionNewControlActividadDetalle != null && !oldCoelIdOfControlActividadDetalleCollectionNewControlActividadDetalle.equals(codigoElemento)) {
                        oldCoelIdOfControlActividadDetalleCollectionNewControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionNewControlActividadDetalle);
                        oldCoelIdOfControlActividadDetalleCollectionNewControlActividadDetalle = em.merge(oldCoelIdOfControlActividadDetalleCollectionNewControlActividadDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = codigoElemento.getCoelId();
                if (findCodigoElemento(id) == null) {
                    throw new NonexistentEntityException("The codigoElemento with id " + id + " no longer exists.");
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
            CodigoElemento codigoElemento;
            try {
                codigoElemento = em.getReference(CodigoElemento.class, id);
                codigoElemento.getCoelId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The codigoElemento with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = codigoElemento.getUserId();
            if (userId != null) {
                userId.getCodigoElementoCollection().remove(codigoElemento);
                userId = em.merge(userId);
            }
            Elemento elemId = codigoElemento.getElemId();
            if (elemId != null) {
                elemId.getCodigoElementoCollection().remove(codigoElemento);
                elemId = em.merge(elemId);
            }
            Collection<TramoCodigoElemento> tramoCodigoElementoCollection = codigoElemento.getTramoCodigoElementoCollection();
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElemento : tramoCodigoElementoCollection) {
                tramoCodigoElementoCollectionTramoCodigoElemento.setCoelId(null);
                tramoCodigoElementoCollectionTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionTramoCodigoElemento);
            }
            Collection<ControlActividadDetalle> controlActividadDetalleCollection = codigoElemento.getControlActividadDetalleCollection();
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalle : controlActividadDetalleCollection) {
                controlActividadDetalleCollectionControlActividadDetalle.setCoelId(null);
                controlActividadDetalleCollectionControlActividadDetalle = em.merge(controlActividadDetalleCollectionControlActividadDetalle);
            }
            em.remove(codigoElemento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CodigoElemento> findCodigoElementoEntities() {
        return findCodigoElementoEntities(true, -1, -1);
    }

    public List<CodigoElemento> findCodigoElementoEntities(int maxResults, int firstResult) {
        return findCodigoElementoEntities(false, maxResults, firstResult);
    }

    private List<CodigoElemento> findCodigoElementoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CodigoElemento.class));
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

    public CodigoElemento findCodigoElemento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CodigoElemento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCodigoElementoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CodigoElemento> rt = cq.from(CodigoElemento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
