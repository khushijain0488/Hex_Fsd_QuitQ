package com.hibernate.service;

import com.hibernate.model.Fund;
import com.hibernate.model.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundService {

    @PersistenceContext // This activates EntityManager of Hibernate/JPA
    private EntityManager em;

    private final ManagerService managerService;

    public FundService(ManagerService managerService) {
        this.managerService = managerService;
    }

    // Task 2 - Add Fund
    @Transactional
    public void insert(Fund fund, int managerId) {
        // Fetch Manager by managerId -- throws ResourceNotFoundException if invalid
        Manager manager = managerService.getManagerById(managerId);
        // Attach manager to fund so hibernate saves manager_id as FK
        fund.setManager(manager);
        em.persist(fund);
    }

    // Get all funds
    public List<?> getAllFunds() {
        String jpql = "select f from Fund f";
        Query query = em.createQuery(jpql, Fund.class);
        return query.getResultList();
    }

    // Task 3 - Fetch all funds for a specific manager
    // JPQL: select f from Fund f where f.manager.id = :managerId
    public List<?> getFundsByManager(int managerId) {
        // First check manager exists
        managerService.getManagerById(managerId);

        String jpql = "select f from Fund f where f.manager.id = :managerId";
        Query query = em.createQuery(jpql, Fund.class);
        query.setParameter("managerId", managerId);
        return query.getResultList();
    }
}