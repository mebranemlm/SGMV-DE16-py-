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
import model.PermisoAccion;
import model.Cargo;
import model.CargoPermisoAccion;

/**
 *
 * @author Luis
 */
public class CargoPermisoAccionJpaController implements Serializable {

    public CargoPermisoAccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CargoPermisoAccion cargoPermisoAccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = cargoPermisoAccion.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                cargoPermisoAccion.setUserId(userId);
            }
            PermisoAccion permAcciId = cargoPermisoAccion.getPermAcciId();
            if (permAcciId != null) {
                permAcciId = em.getReference(permAcciId.getClass(), permAcciId.getPermAcciId());
                cargoPermisoAccion.setPermAcciId(permAcciId);
            }
            Cargo cargId = cargoPermisoAccion.getCargId();
            if (cargId != null) {
                cargId = em.getReference(cargId.getClass(), cargId.getCargId());
                cargoPermisoAccion.setCargId(cargId);
            }
            em.persist(cargoPermisoAccion);
            if (userId != null) {
                userId.getCargoPermisoAccionCollection().add(cargoPermisoAccion);
                userId = em.merge(userId);
            }
            if (permAcciId != null) {
                permAcciId.getCargoPermisoAccionCollection().add(cargoPermisoAccion);
                permAcciId = em.merge(permAcciId);
            }
            if (cargId != null) {
                cargId.getCargoPermisoAccionCollection().add(cargoPermisoAccion);
                cargId = em.merge(cargId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CargoPermisoAccion cargoPermisoAccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargoPermisoAccion persistentCargoPermisoAccion = em.find(CargoPermisoAccion.class, cargoPermisoAccion.getCargPermAcciId());
            Usuario userIdOld = persistentCargoPermisoAccion.getUserId();
            Usuario userIdNew = cargoPermisoAccion.getUserId();
            PermisoAccion permAcciIdOld = persistentCargoPermisoAccion.getPermAcciId();
            PermisoAccion permAcciIdNew = cargoPermisoAccion.getPermAcciId();
            Cargo cargIdOld = persistentCargoPermisoAccion.getCargId();
            Cargo cargIdNew = cargoPermisoAccion.getCargId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                cargoPermisoAccion.setUserId(userIdNew);
            }
            if (permAcciIdNew != null) {
                permAcciIdNew = em.getReference(permAcciIdNew.getClass(), permAcciIdNew.getPermAcciId());
                cargoPermisoAccion.setPermAcciId(permAcciIdNew);
            }
            if (cargIdNew != null) {
                cargIdNew = em.getReference(cargIdNew.getClass(), cargIdNew.getCargId());
                cargoPermisoAccion.setCargId(cargIdNew);
            }
            cargoPermisoAccion = em.merge(cargoPermisoAccion);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getCargoPermisoAccionCollection().remove(cargoPermisoAccion);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getCargoPermisoAccionCollection().add(cargoPermisoAccion);
                userIdNew = em.merge(userIdNew);
            }
            if (permAcciIdOld != null && !permAcciIdOld.equals(permAcciIdNew)) {
                permAcciIdOld.getCargoPermisoAccionCollection().remove(cargoPermisoAccion);
                permAcciIdOld = em.merge(permAcciIdOld);
            }
            if (permAcciIdNew != null && !permAcciIdNew.equals(permAcciIdOld)) {
                permAcciIdNew.getCargoPermisoAccionCollection().add(cargoPermisoAccion);
                permAcciIdNew = em.merge(permAcciIdNew);
            }
            if (cargIdOld != null && !cargIdOld.equals(cargIdNew)) {
                cargIdOld.getCargoPermisoAccionCollection().remove(cargoPermisoAccion);
                cargIdOld = em.merge(cargIdOld);
            }
            if (cargIdNew != null && !cargIdNew.equals(cargIdOld)) {
                cargIdNew.getCargoPermisoAccionCollection().add(cargoPermisoAccion);
                cargIdNew = em.merge(cargIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargoPermisoAccion.getCargPermAcciId();
                if (findCargoPermisoAccion(id) == null) {
                    throw new NonexistentEntityException("The cargoPermisoAccion with id " + id + " no longer exists.");
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
            CargoPermisoAccion cargoPermisoAccion;
            try {
                cargoPermisoAccion = em.getReference(CargoPermisoAccion.class, id);
                cargoPermisoAccion.getCargPermAcciId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargoPermisoAccion with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = cargoPermisoAccion.getUserId();
            if (userId != null) {
                userId.getCargoPermisoAccionCollection().remove(cargoPermisoAccion);
                userId = em.merge(userId);
            }
            PermisoAccion permAcciId = cargoPermisoAccion.getPermAcciId();
            if (permAcciId != null) {
                permAcciId.getCargoPermisoAccionCollection().remove(cargoPermisoAccion);
                permAcciId = em.merge(permAcciId);
            }
            Cargo cargId = cargoPermisoAccion.getCargId();
            if (cargId != null) {
                cargId.getCargoPermisoAccionCollection().remove(cargoPermisoAccion);
                cargId = em.merge(cargId);
            }
            em.remove(cargoPermisoAccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CargoPermisoAccion> findCargoPermisoAccionEntities() {
        return findCargoPermisoAccionEntities(true, -1, -1);
    }

    public List<CargoPermisoAccion> findCargoPermisoAccionEntities(int maxResults, int firstResult) {
        return findCargoPermisoAccionEntities(false, maxResults, firstResult);
    }

    private List<CargoPermisoAccion> findCargoPermisoAccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CargoPermisoAccion.class));
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

    public CargoPermisoAccion findCargoPermisoAccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CargoPermisoAccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoPermisoAccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CargoPermisoAccion> rt = cq.from(CargoPermisoAccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
