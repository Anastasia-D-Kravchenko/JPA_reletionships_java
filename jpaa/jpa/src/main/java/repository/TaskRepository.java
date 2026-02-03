package repository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import logic.Task;
import java.util.List;

public class TaskRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pjUniTest");

    public Task save(Task task) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
            return task;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error saving task", e);
        } finally {
            em.close();
        }
    }

    public void delete(Task task) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Task managed = em.find(Task.class, task.getId());
            if (managed != null) {
                em.remove(managed);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error deleting task", e);
        } finally {
            em.close();
        }
    }

    public List<Task> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Task t ORDER BY t.id", Task.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}