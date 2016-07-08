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
import model.ControlActividadDetalle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Sector;

/**
 *
 * @author Luis
 */
public class SectorJpaController implements Serializable {

    public SectorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sector sector) {
        if (sector.getControlActividadDetalleCollection() == null) {
            sector.setControlActividadDetalleCollection(new ArrayList<ControlActividadDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = sector.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                sector.setUserId(userId);
            }
            Tramo tramId = sector.getTramId();
            if (tramId != null) {
                tramId = em.getReference(tramId.getClass(), tramId.getTramId());
                sector.setTramId(tramId);
            }
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollection = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalleToAttach : sector.getControlActividadDetalleCollection()) {
                controlActividadDetalleCollectionControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollection.add(controlActividadDetalleCollectionControlActividadDetalleToAttach);
            }
            sector.setControlActividadDetalleCollection(attachedControlActividadDetalleCollection);
            em.persist(sector);
            if (userId != null) {
                userId.getSectorCollection().add(sector);
                userId = em.merge(userId);
            }
            if (tramId != null) {
                tramId.getSectorCollection().add(sector);
                tramId = em.merge(tramId);
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionControlActividadDetalle : sector.getControlActividadDetalleCollection()) {
                Sector oldSectIdOfControlActividadDetalleCollectionControlActividadDetalle = controlActividadDetalleCollectionControlActividadDetalle.getSectId();
                controlActividadDetalleCollectionControlActividadDetalle.setSectId(sector);
                controlActividadDetalleCollectionControlActividadDetalle = em.merge(controlActividadDetalleCollectionControlActividadDetalle);
                if (oldSectIdOfControlActividadDetalleCollectionControlActividadDetalle != null) {
                    oldSectIdOfControlActividadDetalleCollectionControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionControlActividadDetalle);
                    oldSectIdOfControlActividadDetalleCollectionControlActividadDetalle = em.merge(oldSectIdOfControlActividadDetalleCollectionControlActividadDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sector sector) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sector persistentSector = em.find(Sector.class, sector.getSectId());
            Usuario userIdOld = persistentSector.getUserId();
            Usuario userIdNew = sector.getUserId();
            Tramo tramIdOld = persistentSector.getTramId();
            Tramo tramIdNew = sector.getTramId();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOld = persistentSector.getControlActividadDetalleCollection();
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionNew = sector.getControlActividadDetalleCollection();
            List<String> illegalOrphanMessages = null;
            for (ControlActividadDetalle controlActividadDetalleCollectionOldControlActividadDetalle : controlActividadDetalleCollectionOld) {
                if (!controlActividadDetalleCollectionNew.contains(controlActividadDetalleCollectionOldControlActividadDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlActividadDetalle " + controlActividadDetalleCollectionOldControlActividadDetalle + " since its sectId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                sector.setUserId(userIdNew);
            }
            if (tramIdNew != null) {
                tramIdNew = em.getReference(tramIdNew.getClass(), tramIdNew.getTramId());
                sector.setTramId(tramIdNew);
            }
            Collection<ControlActividadDetalle> attachedControlActividadDetalleCollectionNew = new ArrayList<ControlActividadDetalle>();
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalleToAttach : controlActividadDetalleCollectionNew) {
                controlActividadDetalleCollectionNewControlActividadDetalleToAttach = em.getReference(controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getClass(), controlActividadDetalleCollectionNewControlActividadDetalleToAttach.getCtacDetaId());
                attachedControlActividadDetalleCollectionNew.add(controlActividadDetalleCollectionNewControlActividadDetalleToAttach);
            }
            controlActividadDetalleCollectionNew = attachedControlActividadDetalleCollectionNew;
            sector.setControlActividadDetalleCollection(controlActividadDetalleCollectionNew);
            sector = em.merge(sector);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getSectorCollection().remove(sector);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getSectorCollection().add(sector);
                userIdNew = em.merge(userIdNew);
            }
            if (tramIdOld != null && !tramIdOld.equals(tramIdNew)) {
                tramIdOld.getSectorCollection().remove(sector);
                tramIdOld = em.merge(tramIdOld);
            }
            if (tramIdNew != null && !tramIdNew.equals(tramIdOld)) {
                tramIdNew.getSectorCollection().add(sector);
                tramIdNew = em.merge(tramIdNew);
            }
            for (ControlActividadDetalle controlActividadDetalleCollectionNewControlActividadDetalle : controlActividadDetalleCollectionNew) {
                if (!controlActividadDetalleCollectionOld.contains(controlActividadDetalleCollectionNewControlActividadDetalle)) {
                    Sector oldSectIdOfControlActividadDetalleCollectionNewControlActividadDetalle = controlActividadDetalleCollectionNewControlActividadDetalle.getSectId();
                    controlActividadDetalleCollectionNewControlActividadDetalle.setSectId(sector);
                    controlActividadDetalleCollectionNewControlActividadDetalle = em.merge(controlActividadDetalleCollectionNewControlActividadDetalle);
                    if (oldSectIdOfControlActividadDetalleCollectionNewControlActividadDetalle != null && !oldSectIdOfControlActividadDetalleCollectionNewControlActividadDetalle.equals(sector)) {
                        oldSectIdOfControlActividadDetalleCollectionNewControlActividadDetalle.getControlActividadDetalleCollection().remove(controlActividadDetalleCollectionNewControlActividadDetalle);
                        oldSectIdOfControlActividadDetalleCollectionNewControlActividadDetalle = em.merge(oldSectIdOfControlActividadDetalleCollectionNewControlActividadDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sector.getSectId();
                if (findSector(id) == null) {
                    throw new NonexistentEntityException("The sector with id " + id + " no longer exists.");
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
            Sector sector;
            try {
                sector = em.getReference(Sector.class, id);
                sector.getSectId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sector with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ControlActividadDetalle> controlActividadDetalleCollectionOrphanCheck = sector.getControlActividadDetalleCollection();
            for (ControlActividadDetalle controlActividadDetalleCollectionOrphanCheckControlActividadDetalle : controlActividadDetalleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sector (" + sector + ") cannot be destroyed since the ControlActividadDetalle " + controlActividadDetalleCollectionOrphanCheckControlActividadDetalle + " in its controlActividadDetalleCollection field has a non-nullable sectId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = sector.getUserId();
            if (userId != null) {
                userId.getSectorCollection().remove(sector);
                userId = em.merge(userId);
            }
            Tramo tramId = sector.getTramId();
            if (tramId != null) {
                tramId.getSectorCollection().remove(sector);
                tramId = em.merge(tramId);
            }
            em.remove(sector);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sector> findSectorEntities() {
        return findSectorEntities(true, -1, -1);
    }

    public List<Sector> findSectorEntities(int maxResults, int firstResult) {
        return findSectorEntities(false, maxResults, firstResult);
    }

    private List<Sector> findSectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sector.class));
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

    public Sector findSector(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sector.class, id);
        } finally {
            em.close();
        }
    }

    public int getSectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sector> rt = cq.from(Sector.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
