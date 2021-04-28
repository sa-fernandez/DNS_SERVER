/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.exceptions.NonexistentEntityException;
import database.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Santiago Fernandez - Fabian Olarte - Andres Vasquez
 */
public class HostAddressJpaController implements Serializable {

    public HostAddressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HostAddress hostAddress) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(hostAddress);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHostAddress(hostAddress.getIp()) != null) {
                throw new PreexistingEntityException("HostAddress " + hostAddress + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HostAddress hostAddress) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            hostAddress = em.merge(hostAddress);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = hostAddress.getIp();
                if (findHostAddress(id) == null) {
                    throw new NonexistentEntityException("The hostAddress with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HostAddress hostAddress;
            try {
                hostAddress = em.getReference(HostAddress.class, id);
                hostAddress.getIp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hostAddress with id " + id + " no longer exists.", enfe);
            }
            em.remove(hostAddress);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HostAddress> findHostAddressEntities() {
        return findHostAddressEntities(true, -1, -1);
    }

    public List<HostAddress> findHostAddressEntities(int maxResults, int firstResult) {
        return findHostAddressEntities(false, maxResults, firstResult);
    }

    private List<HostAddress> findHostAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HostAddress.class));
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

    public HostAddress findHostAddress(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HostAddress.class, id);
        } finally {
            em.close();
        }
    }

    public int getHostAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HostAddress> rt = cq.from(HostAddress.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
