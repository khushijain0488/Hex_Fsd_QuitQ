package com.hibernate.service;

import com.hibernate.exception.ResourceNotFoundException;
import com.hibernate.model.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManagerService {

    @PersistenceContext // This activates EntityManager of Hibernate/JPA
    private EntityManager em;

    // Task 1 - Add Manager
    @Transactional
    public void insert(Manager manager) {
        em.persist(manager);
    }

    // Get all managers
    public List<?> getAllManagers() {
        String jpql = "select m from Manager m";
        Query query = em.createQuery(jpql, Manager.class);
        return query.getResultList();
    }

    // Find Manager by ID - throws exception if not found
    public Manager getManagerById(int managerId) {
        Manager manager = em.find(Manager.class, managerId);
        if (manager == null)
            throw new ResourceNotFoundException("Invalid manager id..");
        return manager;
    }
}