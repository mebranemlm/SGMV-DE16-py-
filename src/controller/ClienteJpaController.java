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
import model.ClienteTipo;
import model.Contrato;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cliente;

/**
 *
 * @author Luis
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getContratoCollection() == null) {
            cliente.setContratoCollection(new ArrayList<Contrato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = cliente.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                cliente.setUserId(userId);
            }
            ClienteTipo cltiId = cliente.getCltiId();
            if (cltiId != null) {
                cltiId = em.getReference(cltiId.getClass(), cltiId.getCltiId());
                cliente.setCltiId(cltiId);
            }
            Collection<Contrato> attachedContratoCollection = new ArrayList<Contrato>();
            for (Contrato contratoCollectionContratoToAttach : cliente.getContratoCollection()) {
                contratoCollectionContratoToAttach = em.getReference(contratoCollectionContratoToAttach.getClass(), contratoCollectionContratoToAttach.getContId());
                attachedContratoCollection.add(contratoCollectionContratoToAttach);
            }
            cliente.setContratoCollection(attachedContratoCollection);
            em.persist(cliente);
            if (userId != null) {
                userId.getClienteCollection().add(cliente);
                userId = em.merge(userId);
            }
            if (cltiId != null) {
                cltiId.getClienteCollection().add(cliente);
                cltiId = em.merge(cltiId);
            }
            for (Contrato contratoCollectionContrato : cliente.getContratoCollection()) {
                Cliente oldClieIdOfContratoCollectionContrato = contratoCollectionContrato.getClieId();
                contratoCollectionContrato.setClieId(cliente);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
                if (oldClieIdOfContratoCollectionContrato != null) {
                    oldClieIdOfContratoCollectionContrato.getContratoCollection().remove(contratoCollectionContrato);
                    oldClieIdOfContratoCollectionContrato = em.merge(oldClieIdOfContratoCollectionContrato);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getClieId());
            Usuario userIdOld = persistentCliente.getUserId();
            Usuario userIdNew = cliente.getUserId();
            ClienteTipo cltiIdOld = persistentCliente.getCltiId();
            ClienteTipo cltiIdNew = cliente.getCltiId();
            Collection<Contrato> contratoCollectionOld = persistentCliente.getContratoCollection();
            Collection<Contrato> contratoCollectionNew = cliente.getContratoCollection();
            List<String> illegalOrphanMessages = null;
            for (Contrato contratoCollectionOldContrato : contratoCollectionOld) {
                if (!contratoCollectionNew.contains(contratoCollectionOldContrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contrato " + contratoCollectionOldContrato + " since its clieId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                cliente.setUserId(userIdNew);
            }
            if (cltiIdNew != null) {
                cltiIdNew = em.getReference(cltiIdNew.getClass(), cltiIdNew.getCltiId());
                cliente.setCltiId(cltiIdNew);
            }
            Collection<Contrato> attachedContratoCollectionNew = new ArrayList<Contrato>();
            for (Contrato contratoCollectionNewContratoToAttach : contratoCollectionNew) {
                contratoCollectionNewContratoToAttach = em.getReference(contratoCollectionNewContratoToAttach.getClass(), contratoCollectionNewContratoToAttach.getContId());
                attachedContratoCollectionNew.add(contratoCollectionNewContratoToAttach);
            }
            contratoCollectionNew = attachedContratoCollectionNew;
            cliente.setContratoCollection(contratoCollectionNew);
            cliente = em.merge(cliente);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getClienteCollection().remove(cliente);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getClienteCollection().add(cliente);
                userIdNew = em.merge(userIdNew);
            }
            if (cltiIdOld != null && !cltiIdOld.equals(cltiIdNew)) {
                cltiIdOld.getClienteCollection().remove(cliente);
                cltiIdOld = em.merge(cltiIdOld);
            }
            if (cltiIdNew != null && !cltiIdNew.equals(cltiIdOld)) {
                cltiIdNew.getClienteCollection().add(cliente);
                cltiIdNew = em.merge(cltiIdNew);
            }
            for (Contrato contratoCollectionNewContrato : contratoCollectionNew) {
                if (!contratoCollectionOld.contains(contratoCollectionNewContrato)) {
                    Cliente oldClieIdOfContratoCollectionNewContrato = contratoCollectionNewContrato.getClieId();
                    contratoCollectionNewContrato.setClieId(cliente);
                    contratoCollectionNewContrato = em.merge(contratoCollectionNewContrato);
                    if (oldClieIdOfContratoCollectionNewContrato != null && !oldClieIdOfContratoCollectionNewContrato.equals(cliente)) {
                        oldClieIdOfContratoCollectionNewContrato.getContratoCollection().remove(contratoCollectionNewContrato);
                        oldClieIdOfContratoCollectionNewContrato = em.merge(oldClieIdOfContratoCollectionNewContrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getClieId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getClieId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Contrato> contratoCollectionOrphanCheck = cliente.getContratoCollection();
            for (Contrato contratoCollectionOrphanCheckContrato : contratoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Contrato " + contratoCollectionOrphanCheckContrato + " in its contratoCollection field has a non-nullable clieId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = cliente.getUserId();
            if (userId != null) {
                userId.getClienteCollection().remove(cliente);
                userId = em.merge(userId);
            }
            ClienteTipo cltiId = cliente.getCltiId();
            if (cltiId != null) {
                cltiId.getClienteCollection().remove(cliente);
                cltiId = em.merge(cltiId);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
