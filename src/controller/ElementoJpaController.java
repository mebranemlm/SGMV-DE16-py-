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
import model.TramoCodigoElemento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.ControlActividadDetalle;
import model.CodigoElemento;
import model.Elemento;

/**
 *
 * @author Luis
 */
public class ElementoJpaController implements Serializable {

    public ElementoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Elemento elemento) {
        if (elemento.getTramoCodigoElementoCollection() == null) {
            elemento.setTramoCodigoElementoCollection(new ArrayList<TramoCodigoElemento>());
        }
        if (elemento.getControlActividadDetalleCollection() == null) {
            elemento.setControlActividadDetalleCollection(new ArrayList<ControlActividadDetalle>());
        }
        if (elemento.getCodigoElementoCollection() == null) {
            elemento.setCodigoElementoCollection(new ArrayList<CodigoElemento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = elemento.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                elemento.setUserId(userId);
            }
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollection = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElementoToAttach : elemento.getTramoCodigoElementoCollection()) {
                tramoCodigoElementoCollectionTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollection.add(tramoCodigoElementoCollectionTramoCodigoElementoToAttach);
            }
            elemento.setTramoCodigoElementoCollection(attachedTramoCodigoElementoCollection);
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollection = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalleToAttach : elemento.getControlActividadDetalleCollection()) {
                controlActividadDetalleCollectionControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollection.add(controlActividadDetalleCollectionControlActividadDetalleToAttach);
            }
            elemento.setControlActividadDetalleCollection(attachedControlActividadDetalleCollection);
            Collection<CodigoElemento> attachedCodigoElementoCollection = new ArrayList<CodigoElemento>();
            for (CodigoElemento codigoElementoCollectionCodigoElementoToAttach : elemento.getCodigoElementoCollection()) {
                codigoElementoCollectionCodigoElementoToAttach = em.getReference(codigoElementoCollectionCodigoElementoToAttach.getClass(), codigoElementoCollectionCodigoElementoToAttach.getCoelId());
                attachedCodigoElementoCollection.add(codigoElementoCollectionCodigoElementoToAttach);
            }
            elemento.setCodigoElementoCollection(attachedCodigoElementoCollection);
            em.persist(elemento);
            if (userId != null) {
                userId.getElementoCollection().add(elemento);
                userId = em.merge(userId);
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElemento : elemento.getTramoCodigoElementoCollection()) {
                Elemento oldElemIdOfTramoCodigoElementoCollectionTramoCodigoElemento = tramoCodigoElementoCollectionTramoCodigoElemento.getElemId();
                tramoCodigoElementoCollectionTramoCodigoElemento.setElemId(elemento);
                tramoCodigoElementoCollectionTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionTramoCodigoElemento);
                if (oldElemIdOfTramoCodigoElementoCollectionTramoCodigoElemento != null) {
                    oldElemIdOfTramoCodigoElementoCollectionTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionTramoCodigoElemento);
                    oldElemIdOfTramoCodigoElementoCollectionTramoCodigoElemento = em.merge(oldElemIdOfTramoCodigoElementoCollectionTramoCodigoElemento);
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalle : elemento.getControlActividadDetalleCollection()) {
                Elemento oldElemIdOfControlActividadDetalleCollectionControlActividadDetalle = controlActividadDetalleCollectionControlActividadDetalle.getElemId();
                controlActividadDetalleCollectionControlActividadDetalle.setElemId(elemento);
                controlActividadDetalleCollectionControlActividadDetalle = em.merge(controlActividadDetalleCollectionControlActividadDetalle);
                if (oldElemIdOfControlActividadDetalleCollectionControlActividadDetalle != null) {
                    oldElemIdOfControlActividadDetalleCollectionControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionControlActividadDetalle);
                    oldElemIdOfControlActividadDetalleCollectionControlActividadDetalle = em.merge(oldElemIdOfControlActividadDetalleCollectionControlActividadDetalle);
                }
            }
            for (CodigoElemento codigoElementoCollectionCodigoElemento : elemento.getCodigoElementoCollection()) {
                Elemento oldElemIdOfCodigoElementoCollectionCodigoElemento = codigoElementoCollectionCodigoElemento.getElemId();
                codigoElementoCollectionCodigoElemento.setElemId(elemento);
                codigoElementoCollectionCodigoElemento = em.merge(codigoElementoCollectionCodigoElemento);
                if (oldElemIdOfCodigoElementoCollectionCodigoElemento != null) {
                    oldElemIdOfCodigoElementoCollectionCodigoElemento.getCodigoElementoCollection().remove(codigoElementoCollectionCodigoElemento);
                    oldElemIdOfCodigoElementoCollectionCodigoElemento = em.merge(oldElemIdOfCodigoElementoCollectionCodigoElemento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Elemento elemento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elemento persistentElemento = em.find(Elemento.class, elemento.getElemId());
            Usuario userIdOld = persistentElemento.getUserId();
            Usuario userIdNew = elemento.getUserId();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionOld = persistentElemento.getTramoCodigoElementoCollection();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionNew = elemento.getTramoCodigoElementoCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOld = persistentElemento.getControlActividadDetalleCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionNew = elemento.getControlActividadDetalleCollection();
            Collection<CodigoElemento> codigoElementoCollectionOld = persistentElemento.getCodigoElementoCollection();
            Collection<CodigoElemento> codigoElementoCollectionNew = elemento.getCodigoElementoCollection();
            List<String> illegalOrphanMessages = null;
            for (TramoCodigoElemento tramoCodigoElementoCollectionOldTramoCodigoElemento : tramoCodigoElementoCollectionOld) {
                if (!tramoCodigoElementoCollectionNew.contains(tramoCodigoElementoCollectionOldTramoCodigoElemento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TramoCodigoElemento " + tramoCodigoElementoCollectionOldTramoCodigoElemento + " since its elemId field is not nullable.");
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionOldControlActividadDetalle : controlActividadDetalleCollectionOld) {
                if (!controlActividadDetalleCollectionNew.contains(controlActividadDetalleCollectionOldControlActividadDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlActividadDetalle " + controlActividadDetalleCollectionOldControlActividadDetalle + " since its elemId field is not nullable.");
                }
            }
            for (CodigoElemento codigoElementoCollectionOldCodigoElemento : codigoElementoCollectionOld) {
                if (!codigoElementoCollectionNew.contains(codigoElementoCollectionOldCodigoElemento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CodigoElemento " + codigoElementoCollectionOldCodigoElemento + " since its elemId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                elemento.setUserId(userIdNew);
            }
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollectionNew = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach : tramoCodigoElementoCollectionNew) {
                tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollectionNew.add(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach);
            }
            tramoCodigoElementoCollectionNew = attachedTramoCodigoElementoCollectionNew;
            elemento.setTramoCodigoElementoCollection(tramoCodigoElementoCollectionNew);
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollectionNew = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalleToAttach : controlActividadDetalleCollectionNew) {
                controlActividadDetalleCollectionNewControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollectionNew.add(controlActividadDetalleCollectionNewControlActividadDetalleToAttach);
            }
            controlActividadDetalleCollectionNew = attachedControlActividadDetalleCollectionNew;
            elemento.setControlActividadDetalleCollection(controlActividadDetalleCollectionNew);
            Collection<CodigoElemento> attachedCodigoElementoCollectionNew = new ArrayList<CodigoElemento>();
            for (CodigoElemento codigoElementoCollectionNewCodigoElementoToAttach : codigoElementoCollectionNew) {
                codigoElementoCollectionNewCodigoElementoToAttach = em.getReference(codigoElementoCollectionNewCodigoElementoToAttach.getClass(), codigoElementoCollectionNewCodigoElementoToAttach.getCoelId());
                attachedCodigoElementoCollectionNew.add(codigoElementoCollectionNewCodigoElementoToAttach);
            }
            codigoElementoCollectionNew = attachedCodigoElementoCollectionNew;
            elemento.setCodigoElementoCollection(codigoElementoCollectionNew);
            elemento = em.merge(elemento);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getElementoCollection().remove(elemento);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getElementoCollection().add(elemento);
                userIdNew = em.merge(userIdNew);
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElemento : tramoCodigoElementoCollectionNew) {
                if (!tramoCodigoElementoCollectionOld.contains(tramoCodigoElementoCollectionNewTramoCodigoElemento)) {
                    Elemento oldElemIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = tramoCodigoElementoCollectionNewTramoCodigoElemento.getElemId();
                    tramoCodigoElementoCollectionNewTramoCodigoElemento.setElemId(elemento);
                    tramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                    if (oldElemIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento != null && !oldElemIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.equals(elemento)) {
                        oldElemIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                        oldElemIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(oldElemIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento);
                    }
                }
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalle : controlActividadDetalleCollectionNew) {
                if (!controlActividadDetalleCollectionOld.contains(controlActividadDetalleCollectionNewControlActividadDetalle)) {
                    Elemento oldElemIdOfControlActividadDetalleCollectionNewControlActividadDetalle = controlActividadDetalleCollectionNewControlActividadDetalle.getElemId();
                    controlActividadDetalleCollectionNewControlActividadDetalle.setElemId(elemento);
                    controlActividadDetalleCollectionNewControlActividadDetalle = em.merge(controlActividadDetalleCollectionNewControlActividadDetalle);
                    if (oldElemIdOfControlActividadDetalleCollectionNewControlActividadDetalle != null && !oldElemIdOfControlActividadDetalleCollectionNewControlActividadDetalle.equals(elemento)) {
                        oldElemIdOfControlActividadDetalleCollectionNewControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionNewControlActividadDetalle);
                        oldElemIdOfControlActividadDetalleCollectionNewControlActividadDetalle = em.merge(oldElemIdOfControlActividadDetalleCollectionNewControlActividadDetalle);
                    }
                }
            }
            for (CodigoElemento codigoElementoCollectionNewCodigoElemento : codigoElementoCollectionNew) {
                if (!codigoElementoCollectionOld.contains(codigoElementoCollectionNewCodigoElemento)) {
                    Elemento oldElemIdOfCodigoElementoCollectionNewCodigoElemento = codigoElementoCollectionNewCodigoElemento.getElemId();
                    codigoElementoCollectionNewCodigoElemento.setElemId(elemento);
                    codigoElementoCollectionNewCodigoElemento = em.merge(codigoElementoCollectionNewCodigoElemento);
                    if (oldElemIdOfCodigoElementoCollectionNewCodigoElemento != null && !oldElemIdOfCodigoElementoCollectionNewCodigoElemento.equals(elemento)) {
                        oldElemIdOfCodigoElementoCollectionNewCodigoElemento.getCodigoElementoCollection().remove(codigoElementoCollectionNewCodigoElemento);
                        oldElemIdOfCodigoElementoCollectionNewCodigoElemento = em.merge(oldElemIdOfCodigoElementoCollectionNewCodigoElemento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = elemento.getElemId();
                if (findElemento(id) == null) {
                    throw new NonexistentEntityException("The elemento with id " + id + " no longer exists.");
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
            Elemento elemento;
            try {
                elemento = em.getReference(Elemento.class, id);
                elemento.getElemId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The elemento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionOrphanCheck = elemento.getTramoCodigoElementoCollection();
            for (TramoCodigoElemento tramoCodigoElementoCollectionOrphanCheckTramoCodigoElemento : tramoCodigoElementoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Elemento (" + elemento + ") cannot be destroyed since the TramoCodigoElemento " + tramoCodigoElementoCollectionOrphanCheckTramoCodigoElemento + " in its tramoCodigoElementoCollection field has a non-nullable elemId field.");
            }
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOrphanCheck = elemento.getControlActividadDetalleCollection();
            for (ControlActividadDetalle controlActividadDetalleCollectionOrphanCheckControlActividadDetalle : controlActividadDetalleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Elemento (" + elemento + ") cannot be destroyed since the ControlActividadDetalle " + controlActividadDetalleCollectionOrphanCheckControlActividadDetalle + " in its controlActividadDetalleCollection field has a non-nullable elemId field.");
            }
            Collection<CodigoElemento> codigoElementoCollectionOrphanCheck = elemento.getCodigoElementoCollection();
            for (CodigoElemento codigoElementoCollectionOrphanCheckCodigoElemento : codigoElementoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Elemento (" + elemento + ") cannot be destroyed since the CodigoElemento " + codigoElementoCollectionOrphanCheckCodigoElemento + " in its codigoElementoCollection field has a non-nullable elemId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = elemento.getUserId();
            if (userId != null) {
                userId.getElementoCollection().remove(elemento);
                userId = em.merge(userId);
            }
            em.remove(elemento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Elemento> findElementoEntities() {
        return findElementoEntities(true, -1, -1);
    }

    public List<Elemento> findElementoEntities(int maxResults, int firstResult) {
        return findElementoEntities(false, maxResults, firstResult);
    }

    private List<Elemento> findElementoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Elemento.class));
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

    public Elemento findElemento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Elemento.class, id);
        } finally {
            em.close();
        }
    }

    public int getElementoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Elemento> rt = cq.from(Elemento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
