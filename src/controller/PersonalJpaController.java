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
import model.Cargo;
import model.ContratoTramoPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Personal;

/**
 *
 * @author Luis
 */
public class PersonalJpaController implements Serializable {

    public PersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personal personal) {
        if (personal.getContratoTramoPersonalCollection() == null) {
            personal.setContratoTramoPersonalCollection(new ArrayList<ContratoTramoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = personal.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUserId());
                personal.setUsuario(usuario);
            }
            Usuario userId = personal.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                personal.setUserId(userId);
            }
            Cargo cargId = personal.getCargId();
            if (cargId != null) {
                cargId = em.getReference(cargId.getClass(), cargId.getCargId());
                personal.setCargId(cargId);
            }
            Collection<ContratoTramoPersonal> attachedContratoTramoPersonalCollection = new ArrayList<ContratoTramoPersonal>();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionContratoTramoPersonalToAttach : personal.getContratoTramoPersonalCollection()) {
                contratoTramoPersonalCollectionContratoTramoPersonalToAttach = em.getReference(contratoTramoPersonalCollectionContratoTramoPersonalToAttach.getClass(), contratoTramoPersonalCollectionContratoTramoPersonalToAttach.getContTramPersId());
                attachedContratoTramoPersonalCollection.add(contratoTramoPersonalCollectionContratoTramoPersonalToAttach);
            }
            personal.setContratoTramoPersonalCollection(attachedContratoTramoPersonalCollection);
            em.persist(personal);
            if (usuario != null) {
                Personal oldPersIdOfUsuario = usuario.getPersId();
                if (oldPersIdOfUsuario != null) {
                    oldPersIdOfUsuario.setUsuario(null);
                    oldPersIdOfUsuario = em.merge(oldPersIdOfUsuario);
                }
                usuario.setPersId(personal);
                usuario = em.merge(usuario);
            }
            if (userId != null) {
                Personal oldPersIdOfUserId = userId.getPersId();
                if (oldPersIdOfUserId != null) {
                    oldPersIdOfUserId.setUserId(null);
                    oldPersIdOfUserId = em.merge(oldPersIdOfUserId);
                }
                userId.setPersId(personal);
                userId = em.merge(userId);
            }
            if (cargId != null) {
                cargId.getPersonalCollection().add(personal);
                cargId = em.merge(cargId);
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionContratoTramoPersonal : personal.getContratoTramoPersonalCollection()) {
                Personal oldPersIdOfContratoTramoPersonalCollectionContratoTramoPersonal = contratoTramoPersonalCollectionContratoTramoPersonal.getPersId();
                contratoTramoPersonalCollectionContratoTramoPersonal.setPersId(personal);
                contratoTramoPersonalCollectionContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionContratoTramoPersonal);
                if (oldPersIdOfContratoTramoPersonalCollectionContratoTramoPersonal != null) {
                    oldPersIdOfContratoTramoPersonalCollectionContratoTramoPersonal.getContratoTramoPersonalCollection().remove(contratoTramoPersonalCollectionContratoTramoPersonal);
                    oldPersIdOfContratoTramoPersonalCollectionContratoTramoPersonal = em.merge(oldPersIdOfContratoTramoPersonalCollectionContratoTramoPersonal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personal personal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personal persistentPersonal = em.find(Personal.class, personal.getPersId());
            Usuario usuarioOld = persistentPersonal.getUsuario();
            Usuario usuarioNew = personal.getUsuario();
            Usuario userIdOld = persistentPersonal.getUserId();
            Usuario userIdNew = personal.getUserId();
            Cargo cargIdOld = persistentPersonal.getCargId();
            Cargo cargIdNew = personal.getCargId();
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionOld = persistentPersonal.getContratoTramoPersonalCollection();
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionNew = personal.getContratoTramoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Usuario " + usuarioOld + " since its persId field is not nullable.");
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Usuario " + userIdOld + " since its persId field is not nullable.");
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionOldContratoTramoPersonal : contratoTramoPersonalCollectionOld) {
                if (!contratoTramoPersonalCollectionNew.contains(contratoTramoPersonalCollectionOldContratoTramoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContratoTramoPersonal " + contratoTramoPersonalCollectionOldContratoTramoPersonal + " since its persId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUserId());
                personal.setUsuario(usuarioNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                personal.setUserId(userIdNew);
            }
            if (cargIdNew != null) {
                cargIdNew = em.getReference(cargIdNew.getClass(), cargIdNew.getCargId());
                personal.setCargId(cargIdNew);
            }
            Collection<ContratoTramoPersonal> attachedContratoTramoPersonalCollectionNew = new ArrayList<ContratoTramoPersonal>();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach : contratoTramoPersonalCollectionNew) {
                contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach = em.getReference(contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach.getClass(), contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach.getContTramPersId());
                attachedContratoTramoPersonalCollectionNew.add(contratoTramoPersonalCollectionNewContratoTramoPersonalToAttach);
            }
            contratoTramoPersonalCollectionNew = attachedContratoTramoPersonalCollectionNew;
            personal.setContratoTramoPersonalCollection(contratoTramoPersonalCollectionNew);
            personal = em.merge(personal);
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Personal oldPersIdOfUsuario = usuarioNew.getPersId();
                if (oldPersIdOfUsuario != null) {
                    oldPersIdOfUsuario.setUsuario(null);
                    oldPersIdOfUsuario = em.merge(oldPersIdOfUsuario);
                }
                usuarioNew.setPersId(personal);
                usuarioNew = em.merge(usuarioNew);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                Personal oldPersIdOfUserId = userIdNew.getPersId();
                if (oldPersIdOfUserId != null) {
                    oldPersIdOfUserId.setUserId(null);
                    oldPersIdOfUserId = em.merge(oldPersIdOfUserId);
                }
                userIdNew.setPersId(personal);
                userIdNew = em.merge(userIdNew);
            }
            if (cargIdOld != null && !cargIdOld.equals(cargIdNew)) {
                cargIdOld.getPersonalCollection().remove(personal);
                cargIdOld = em.merge(cargIdOld);
            }
            if (cargIdNew != null && !cargIdNew.equals(cargIdOld)) {
                cargIdNew.getPersonalCollection().add(personal);
                cargIdNew = em.merge(cargIdNew);
            }
            for (ContratoTramoPersonal contratoTramoPersonalCollectionNewContratoTramoPersonal : contratoTramoPersonalCollectionNew) {
                if (!contratoTramoPersonalCollectionOld.contains(contratoTramoPersonalCollectionNewContratoTramoPersonal)) {
                    Personal oldPersIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal = contratoTramoPersonalCollectionNewContratoTramoPersonal.getPersId();
                    contratoTramoPersonalCollectionNewContratoTramoPersonal.setPersId(personal);
                    contratoTramoPersonalCollectionNewContratoTramoPersonal = em.merge(contratoTramoPersonalCollectionNewContratoTramoPersonal);
                    if (oldPersIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal != null && !oldPersIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal.equals(personal)) {
                        oldPersIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal.getContratoTramoPersonalCollection().remove(contratoTramoPersonalCollectionNewContratoTramoPersonal);
                        oldPersIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal = em.merge(oldPersIdOfContratoTramoPersonalCollectionNewContratoTramoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personal.getPersId();
                if (findPersonal(id) == null) {
                    throw new NonexistentEntityException("The personal with id " + id + " no longer exists.");
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
            Personal personal;
            try {
                personal = em.getReference(Personal.class, id);
                personal.getPersId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Usuario usuarioOrphanCheck = personal.getUsuario();
            if (usuarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personal (" + personal + ") cannot be destroyed since the Usuario " + usuarioOrphanCheck + " in its usuario field has a non-nullable persId field.");
            }
            Usuario userIdOrphanCheck = personal.getUserId();
            if (userIdOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personal (" + personal + ") cannot be destroyed since the Usuario " + userIdOrphanCheck + " in its userId field has a non-nullable persId field.");
            }
            Collection<ContratoTramoPersonal> contratoTramoPersonalCollectionOrphanCheck = personal.getContratoTramoPersonalCollection();
            for (ContratoTramoPersonal contratoTramoPersonalCollectionOrphanCheckContratoTramoPersonal : contratoTramoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personal (" + personal + ") cannot be destroyed since the ContratoTramoPersonal " + contratoTramoPersonalCollectionOrphanCheckContratoTramoPersonal + " in its contratoTramoPersonalCollection field has a non-nullable persId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cargo cargId = personal.getCargId();
            if (cargId != null) {
                cargId.getPersonalCollection().remove(personal);
                cargId = em.merge(cargId);
            }
            em.remove(personal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personal> findPersonalEntities() {
        return findPersonalEntities(true, -1, -1);
    }

    public List<Personal> findPersonalEntities(int maxResults, int firstResult) {
        return findPersonalEntities(false, maxResults, firstResult);
    }

    private List<Personal> findPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personal.class));
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

    public Personal findPersonal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personal> rt = cq.from(Personal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
