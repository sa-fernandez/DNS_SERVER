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
public class HostAddressIPv6JpaController implements Serializable {

    public HostAddressIPv6JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HostAddressIPv6 hostAddressIPv6) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(hostAddressIPv6);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHostAddressIPv6(hostAddressIPv6.getIp()) != null) {
                throw new PreexistingEntityException("HostAddressIPv6 " + hostAddressIPv6 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HostAddressIPv6 hostAddressIPv6) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            hostAddressIPv6 = em.merge(hostAddressIPv6);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = hostAddressIPv6.getIp();
                if (findHostAddressIPv6(id) == null) {
                    throw new NonexistentEntityException("The hostAddressIPv6 with id " + id + " no longer exists.");
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
            HostAddressIPv6 hostAddressIPv6;
            try {
                hostAddressIPv6 = em.getReference(HostAddressIPv6.class, id);
                hostAddressIPv6.getIp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hostAddressIPv6 with id " + id + " no longer exists.", enfe);
            }
            em.remove(hostAddressIPv6);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HostAddressIPv6> findHostAddressIPv6Entities() {
        return findHostAddressIPv6Entities(true, -1, -1);
    }

    public List<HostAddressIPv6> findHostAddressIPv6Entities(int maxResults, int firstResult) {
        return findHostAddressIPv6Entities(false, maxResults, firstResult);
    }

    private List<HostAddressIPv6> findHostAddressIPv6Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HostAddressIPv6.class));
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

    public HostAddressIPv6 findHostAddressIPv6(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HostAddressIPv6.class, id);
        } finally {
            em.close();
        }
    }

    public int getHostAddressIPv6Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HostAddressIPv6> rt = cq.from(HostAddressIPv6.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
