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
import model.Sector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.TramoCodigoElemento;
import model.ContratoTramo;
import model.Tramo;

/**
 *
 * @author Luis
 */
public class TramoJpaController implements Serializable {

    public TramoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tramo tramo) {
        if (tramo.getSectorCollection() == null) {
            tramo.setSectorCollection(new ArrayList<Sector>());
        }
        if (tramo.getTramoCodigoElementoCollection() == null) {
            tramo.setTramoCodigoElementoCollection(new ArrayList<TramoCodigoElemento>());
        }
        if (tramo.getContratoTramoCollection() == null) {
            tramo.setContratoTramoCollection(new ArrayList<ContratoTramo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = tramo.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                tramo.setUserId(userId);
            }
            Collection<Sector> attachedSectorCollection = new ArrayList<Sector>();
            for (Sector sectorCollectionSectorToAttach : tramo.getSectorCollection()) {
                sectorCollectionSectorToAttach = em.getReference(sectorCollectionSectorToAttach.getClass(), sectorCollectionSectorToAttach.getSectId());
                attachedSectorCollection.add(sectorCollectionSectorToAttach);
            }
            tramo.setSectorCollection(attachedSectorCollection);
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollection = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElementoToAttach : tramo.getTramoCodigoElementoCollection()) {
                tramoCodigoElementoCollectionTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollection.add(tramoCodigoElementoCollectionTramoCodigoElementoToAttach);
            }
            tramo.setTramoCodigoElementoCollection(attachedTramoCodigoElementoCollection);
            Collection<ContratoTramo> attachedContratoTramoCollection = new ArrayList<ContratoTramo>();
            for (ContratoTramo contratoTramoCollectionContratoTramoToAttach : tramo.getContratoTramoCollection()) {
                contratoTramoCollectionContratoTramoToAttach = em.getReference(contratoTramoCollectionContratoTramoToAttach.getClass(), contratoTramoCollectionContratoTramoToAttach.getContTramId());
                attachedContratoTramoCollection.add(contratoTramoCollectionContratoTramoToAttach);
            }
            tramo.setContratoTramoCollection(attachedContratoTramoCollection);
            em.persist(tramo);
            if (userId != null) {
                userId.getTramoCollection().add(tramo);
                userId = em.merge(userId);
            }
            for (Sector sectorCollectionSector : tramo.getSectorCollection()) {
                Tramo oldTramIdOfSectorCollectionSector = sectorCollectionSector.getTramId();
                sectorCollectionSector.setTramId(tramo);
                sectorCollectionSector = em.merge(sectorCollectionSector);
                if (oldTramIdOfSectorCollectionSector != null) {
                    oldTramIdOfSectorCollectionSector.getSectorCollection().remove(sectorCollectionSector);
                    oldTramIdOfSectorCollectionSector = em.merge(oldTramIdOfSectorCollectionSector);
                }
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionTramoCodigoElemento : tramo.getTramoCodigoElementoCollection()) {
                Tramo oldTramIdOfTramoCodigoElementoCollectionTramoCodigoElemento = tramoCodigoElementoCollectionTramoCodigoElemento.getTramId();
                tramoCodigoElementoCollectionTramoCodigoElemento.setTramId(tramo);
                tramoCodigoElementoCollectionTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionTramoCodigoElemento);
                if (oldTramIdOfTramoCodigoElementoCollectionTramoCodigoElemento != null) {
                    oldTramIdOfTramoCodigoElementoCollectionTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionTramoCodigoElemento);
                    oldTramIdOfTramoCodigoElementoCollectionTramoCodigoElemento = em.merge(oldTramIdOfTramoCodigoElementoCollectionTramoCodigoElemento);
                }
            }
            for (ContratoTramo contratoTramoCollectionContratoTramo : tramo.getContratoTramoCollection()) {
                Tramo oldTramIdOfContratoTramoCollectionContratoTramo = contratoTramoCollectionContratoTramo.getTramId();
                contratoTramoCollectionContratoTramo.setTramId(tramo);
                contratoTramoCollectionContratoTramo = em.merge(contratoTramoCollectionContratoTramo);
                if (oldTramIdOfContratoTramoCollectionContratoTramo != null) {
                    oldTramIdOfContratoTramoCollectionContratoTramo.getContratoTramoCollection().remove(contratoTramoCollectionContratoTramo);
                    oldTramIdOfContratoTramoCollectionContratoTramo = em.merge(oldTramIdOfContratoTramoCollectionContratoTramo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tramo tramo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tramo persistentTramo = em.find(Tramo.class, tramo.getTramId());
            Usuario userIdOld = persistentTramo.getUserId();
            Usuario userIdNew = tramo.getUserId();
            Collection<Sector> sectorCollectionOld = persistentTramo.getSectorCollection();
            Collection<Sector> sectorCollectionNew = tramo.getSectorCollection();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionOld = persistentTramo.getTramoCodigoElementoCollection();
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionNew = tramo.getTramoCodigoElementoCollection();
            Collection<ContratoTramo> contratoTramoCollectionOld = persistentTramo.getContratoTramoCollection();
            Collection<ContratoTramo> contratoTramoCollectionNew = tramo.getContratoTramoCollection();
            List<String> illegalOrphanMessages = null;
            for (Sector sectorCollectionOldSector : sectorCollectionOld) {
                if (!sectorCollectionNew.contains(sectorCollectionOldSector)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sector " + sectorCollectionOldSector + " since its tramId field is not nullable.");
                }
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionOldTramoCodigoElemento : tramoCodigoElementoCollectionOld) {
                if (!tramoCodigoElementoCollectionNew.contains(tramoCodigoElementoCollectionOldTramoCodigoElemento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TramoCodigoElemento " + tramoCodigoElementoCollectionOldTramoCodigoElemento + " since its tramId field is not nullable.");
                }
            }
            for (ContratoTramo contratoTramoCollectionOldContratoTramo : contratoTramoCollectionOld) {
                if (!contratoTramoCollectionNew.contains(contratoTramoCollectionOldContratoTramo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContratoTramo " + contratoTramoCollectionOldContratoTramo + " since its tramId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                tramo.setUserId(userIdNew);
            }
            Collection<Sector> attachedSectorCollectionNew = new ArrayList<Sector>();
            for (Sector sectorCollectionNewSectorToAttach : sectorCollectionNew) {
                sectorCollectionNewSectorToAttach = em.getReference(sectorCollectionNewSectorToAttach.getClass(), sectorCollectionNewSectorToAttach.getSectId());
                attachedSectorCollectionNew.add(sectorCollectionNewSectorToAttach);
            }
            sectorCollectionNew = attachedSectorCollectionNew;
            tramo.setSectorCollection(sectorCollectionNew);
            Collection<TramoCodigoElemento> attachedTramoCodigoElementoCollectionNew = new ArrayList<TramoCodigoElemento>();
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach : tramoCodigoElementoCollectionNew) {
                tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach = em.getReference(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getClass(), tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach.getTramCoelId());
                attachedTramoCodigoElementoCollectionNew.add(tramoCodigoElementoCollectionNewTramoCodigoElementoToAttach);
            }
            tramoCodigoElementoCollectionNew = attachedTramoCodigoElementoCollectionNew;
            tramo.setTramoCodigoElementoCollection(tramoCodigoElementoCollectionNew);
            Collection<ContratoTramo> attachedContratoTramoCollectionNew = new ArrayList<ContratoTramo>();
            for (ContratoTramo contratoTramoCollectionNewContratoTramoToAttach : contratoTramoCollectionNew) {
                contratoTramoCollectionNewContratoTramoToAttach = em.getReference(contratoTramoCollectionNewContratoTramoToAttach.getClass(), contratoTramoCollectionNewContratoTramoToAttach.getContTramId());
                attachedContratoTramoCollectionNew.add(contratoTramoCollectionNewContratoTramoToAttach);
            }
            contratoTramoCollectionNew = attachedContratoTramoCollectionNew;
            tramo.setContratoTramoCollection(contratoTramoCollectionNew);
            tramo = em.merge(tramo);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getTramoCollection().remove(tramo);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getTramoCollection().add(tramo);
                userIdNew = em.merge(userIdNew);
            }
            for (Sector sectorCollectionNewSector : sectorCollectionNew) {
                if (!sectorCollectionOld.contains(sectorCollectionNewSector)) {
                    Tramo oldTramIdOfSectorCollectionNewSector = sectorCollectionNewSector.getTramId();
                    sectorCollectionNewSector.setTramId(tramo);
                    sectorCollectionNewSector = em.merge(sectorCollectionNewSector);
                    if (oldTramIdOfSectorCollectionNewSector != null && !oldTramIdOfSectorCollectionNewSector.equals(tramo)) {
                        oldTramIdOfSectorCollectionNewSector.getSectorCollection().remove(sectorCollectionNewSector);
                        oldTramIdOfSectorCollectionNewSector = em.merge(oldTramIdOfSectorCollectionNewSector);
                    }
                }
            }
            for (TramoCodigoElemento tramoCodigoElementoCollectionNewTramoCodigoElemento : tramoCodigoElementoCollectionNew) {
                if (!tramoCodigoElementoCollectionOld.contains(tramoCodigoElementoCollectionNewTramoCodigoElemento)) {
                    Tramo oldTramIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = tramoCodigoElementoCollectionNewTramoCodigoElemento.getTramId();
                    tramoCodigoElementoCollectionNewTramoCodigoElemento.setTramId(tramo);
                    tramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                    if (oldTramIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento != null && !oldTramIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.equals(tramo)) {
                        oldTramIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento.getTramoCodigoElementoCollection().remove(tramoCodigoElementoCollectionNewTramoCodigoElemento);
                        oldTramIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento = em.merge(oldTramIdOfTramoCodigoElementoCollectionNewTramoCodigoElemento);
                    }
                }
            }
            for (ContratoTramo contratoTramoCollectionNewContratoTramo : contratoTramoCollectionNew) {
                if (!contratoTramoCollectionOld.contains(contratoTramoCollectionNewContratoTramo)) {
                    Tramo oldTramIdOfContratoTramoCollectionNewContratoTramo = contratoTramoCollectionNewContratoTramo.getTramId();
                    contratoTramoCollectionNewContratoTramo.setTramId(tramo);
                    contratoTramoCollectionNewContratoTramo = em.merge(contratoTramoCollectionNewContratoTramo);
                    if (oldTramIdOfContratoTramoCollectionNewContratoTramo != null && !oldTramIdOfContratoTramoCollectionNewContratoTramo.equals(tramo)) {
                        oldTramIdOfContratoTramoCollectionNewContratoTramo.getContratoTramoCollection().remove(contratoTramoCollectionNewContratoTramo);
                        oldTramIdOfContratoTramoCollectionNewContratoTramo = em.merge(oldTramIdOfContratoTramoCollectionNewContratoTramo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tramo.getTramId();
                if (findTramo(id) == null) {
                    throw new NonexistentEntityException("The tramo with id " + id + " no longer exists.");
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
            Tramo tramo;
            try {
                tramo = em.getReference(Tramo.class, id);
                tramo.getTramId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tramo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sector> sectorCollectionOrphanCheck = tramo.getSectorCollection();
            for (Sector sectorCollectionOrphanCheckSector : sectorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tramo (" + tramo + ") cannot be destroyed since the Sector " + sectorCollectionOrphanCheckSector + " in its sectorCollection field has a non-nullable tramId field.");
            }
            Collection<TramoCodigoElemento> tramoCodigoElementoCollectionOrphanCheck = tramo.getTramoCodigoElementoCollection();
            for (TramoCodigoElemento tramoCodigoElementoCollectionOrphanCheckTramoCodigoElemento : tramoCodigoElementoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tramo (" + tramo + ") cannot be destroyed since the TramoCodigoElemento " + tramoCodigoElementoCollectionOrphanCheckTramoCodigoElemento + " in its tramoCodigoElementoCollection field has a non-nullable tramId field.");
            }
            Collection<ContratoTramo> contratoTramoCollectionOrphanCheck = tramo.getContratoTramoCollection();
            for (ContratoTramo contratoTramoCollectionOrphanCheckContratoTramo : contratoTramoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tramo (" + tramo + ") cannot be destroyed since the ContratoTramo " + contratoTramoCollectionOrphanCheckContratoTramo + " in its contratoTramoCollection field has a non-nullable tramId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = tramo.getUserId();
            if (userId != null) {
                userId.getTramoCollection().remove(tramo);
                userId = em.merge(userId);
            }
            em.remove(tramo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tramo> findTramoEntities() {
        return findTramoEntities(true, -1, -1);
    }

    public List<Tramo> findTramoEntities(int maxResults, int firstResult) {
        return findTramoEntities(false, maxResults, firstResult);
    }

    private List<Tramo> findTramoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tramo.class));
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

    public Tramo findTramo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tramo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTramoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tramo> rt = cq.from(Tramo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
