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
import model.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.ClienteTipo;

/**
 *
 * @author Luis
 */
public class ClienteTipoJpaController implements Serializable {

    public ClienteTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteTipo clienteTipo) {
        if (clienteTipo.getClienteCollection() == null) {
            clienteTipo.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = clienteTipo.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                clienteTipo.setUserId(userId);
            }
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : clienteTipo.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getClieId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            clienteTipo.setClienteCollection(attachedClienteCollection);
            em.persist(clienteTipo);
            if (userId != null) {
                userId.getClienteTipoCollection().add(clienteTipo);
                userId = em.merge(userId);
            }
            for (Cliente clienteCollectionCliente : clienteTipo.getClienteCollection()) {
                ClienteTipo oldCltiIdOfClienteCollectionCliente = clienteCollectionCliente.getCltiId();
                clienteCollectionCliente.setCltiId(clienteTipo);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldCltiIdOfClienteCollectionCliente != null) {
                    oldCltiIdOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldCltiIdOfClienteCollectionCliente = em.merge(oldCltiIdOfClienteCollectionCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteTipo clienteTipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteTipo persistentClienteTipo = em.find(ClienteTipo.class, clienteTipo.getCltiId());
            Usuario userIdOld = persistentClienteTipo.getUserId();
            Usuario userIdNew = clienteTipo.getUserId();
            Collection<Cliente> clienteCollectionOld = persistentClienteTipo.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = clienteTipo.getClienteCollection();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteCollectionOldCliente + " since its cltiId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                clienteTipo.setUserId(userIdNew);
            }
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getClieId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            clienteTipo.setClienteCollection(clienteCollectionNew);
            clienteTipo = em.merge(clienteTipo);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getClienteTipoCollection().remove(clienteTipo);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getClienteTipoCollection().add(clienteTipo);
                userIdNew = em.merge(userIdNew);
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    ClienteTipo oldCltiIdOfClienteCollectionNewCliente = clienteCollectionNewCliente.getCltiId();
                    clienteCollectionNewCliente.setCltiId(clienteTipo);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldCltiIdOfClienteCollectionNewCliente != null && !oldCltiIdOfClienteCollectionNewCliente.equals(clienteTipo)) {
                        oldCltiIdOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldCltiIdOfClienteCollectionNewCliente = em.merge(oldCltiIdOfClienteCollectionNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clienteTipo.getCltiId();
                if (findClienteTipo(id) == null) {
                    throw new NonexistentEntityException("The clienteTipo with id " + id + " no longer exists.");
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
            ClienteTipo clienteTipo;
            try {
                clienteTipo = em.getReference(ClienteTipo.class, id);
                clienteTipo.getCltiId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteTipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cliente> clienteCollectionOrphanCheck = clienteTipo.getClienteCollection();
            for (Cliente clienteCollectionOrphanCheckCliente : clienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteTipo (" + clienteTipo + ") cannot be destroyed since the Cliente " + clienteCollectionOrphanCheckCliente + " in its clienteCollection field has a non-nullable cltiId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario userId = clienteTipo.getUserId();
            if (userId != null) {
                userId.getClienteTipoCollection().remove(clienteTipo);
                userId = em.merge(userId);
            }
            em.remove(clienteTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteTipo> findClienteTipoEntities() {
        return findClienteTipoEntities(true, -1, -1);
    }

    public List<ClienteTipo> findClienteTipoEntities(int maxResults, int firstResult) {
        return findClienteTipoEntities(false, maxResults, firstResult);
    }

    private List<ClienteTipo> findClienteTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteTipo.class));
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

    public ClienteTipo findClienteTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteTipo> rt = cq.from(ClienteTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
