
package DAO;
import Entidad.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TipoSensorDAO {
    private static final EntityManagerFactory 
            emf= Persistence.createEntityManagerFactory("SensorAppPU");
    
    public void crear (TipoSensor object){
        EntityManager em= emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public boolean eliminar (TipoSensor object){
        EntityManager em= emf.createEntityManager();
        em.getTransaction().begin();
        boolean ret= false;
        try {
            em.remove(object);
            em.getTransaction().commit();
            ret=true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            return ret;
        }      
    }
    
    public TipoSensor leer (TipoSensor par){
        EntityManager em= emf.createEntityManager();
        TipoSensor s= null;
        Query q= em.createQuery("SELECT u FROM TipoSensor u " + 
                " WHERE u.tipo LIKE :tipo" + 
                " AND u.nombre LIKE :nombre " + 
                " AND u.minimo LIKE :minimo " +
                " AND u.maximo LIKE :maximo ")
                .setParameter("tipo", par.getTipo())
                .setParameter("nombre", par.getNombre())
                .setParameter("minimo", par.getMinimo())
                .setParameter("maximo", par.getMaximo());
        try {
            s = (TipoSensor) q.getSingleResult();
        } catch (NonUniqueResultException e) {
            s = (TipoSensor) q.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            return s;
        }
    }
    
    public boolean actualizar (TipoSensor object, TipoSensor nuevoObjeto){
        EntityManager em= emf.createEntityManager();
        em.getTransaction().begin();
        boolean ret= false;
        try {
            object= leer(object);
            object.setTipo(nuevoObjeto.getTipo());
            object.setNombre(nuevoObjeto.getNombre());
            object.setMinimo(nuevoObjeto.getMinimo());
            object.setMaximo(nuevoObjeto.getMaximo());
            em.merge(object);
            em.getTransaction().commit();
            ret=true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            return ret;
        } 
    }
}
