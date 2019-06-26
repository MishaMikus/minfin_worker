package orm.entity;

import org.hibernate.Session;

import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import static orm.HibernateUtil.STATEMENT_BATCH_SIZE;
import static orm.HibernateUtil.getSessionFactory;

public abstract class GenericAbstractDAO<E> {

    private boolean connected = false;
    private final Class<E> entityClass;

    public GenericAbstractDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public Serializable save(E entity) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        Serializable saveResult = session.save(entity);
        session.getTransaction().commit();
        session.close();
        return saveResult;
    }

    public void saveBatch(List<E> entityList) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        for (int i = 0; i < entityList.size(); i++) {
            session.saveOrUpdate(entityList.get(i));
            if (i % STATEMENT_BATCH_SIZE == 0) {
                System.out.println(new Date() + " " + getCatalogName() + "." + getTableName() + " add " + i + " records");
                session.flush();
                session.clear();
            }
        }

        session.getTransaction().commit();
        session.close();
    }

    public void updateBatch(List<E> entityList) {
        if (connected) {
            beginTransaction();
            Date start = new Date();
            for (int i = 0; i < entityList.size(); i++) {
                getSession().update(entityList.get(i));
                if (i % STATEMENT_BATCH_SIZE == 0) {
                    getSession().flush();
                    getSession().clear();
                }
            }
            commitTransaction();
        }
    }

    public List<E> findAll() {
        return findAllWhere(null);
    }

    public E findWhereEqual(String name, Object value) {
        return findWhere(whereEqual(name, value), name + "=" + value);
    }

    public E findWhereContains(String name, String value) {
        return findWhere(whereContains(name, value), "contains(" + name + ", " + value + ")");
    }

    public List<E> findAllWhereEqual(String name, Object value) {
        return findAllWhere(whereEqual(name, value));
    }

    public List<E> findAllWhereContains(String name, String value) {
        return findAllWhere(whereContains(name, value));
    }

    public void clear() {
        if (connected) {
            beginTransaction();
            getSession().clear();
            commitTransaction();
        }
    }

    public void close() {
        getSession().close();
    }

    public void truncateTable() {
        if (connected) {
            try {
                beginTransaction();
                getSession().createSQLQuery("truncate table " + getCatalogName() + "." + getTableName()).executeUpdate();
                commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> String tableLabel(Class<T> eventQueueClass) {
        return getCatalogName(eventQueueClass) + "." + getTableName(eventQueueClass);
    }

    public String getTableName() {
        return getTableName(entityClass);
    }

    private static String getTableName(Class entityClass) {
        Table table = getTable(entityClass);
        if (table != null) {
            return table.name();
        }
        return null;
    }

    private static Table getTable(Class entityClass) {
        for (Annotation annotation : entityClass.getAnnotations()) {
            if (annotation instanceof Table) {
                return ((Table) annotation);
            }
        }
        System.out.println("Entity '" + entityClass + "' must have @Table annotation");
        return null;
    }

    public String getCatalogName() {
        return getCatalogName(entityClass);
    }

    private static String getCatalogName(Class entityClass) {
        Table table = getTable(entityClass);
        if (table != null) {
            return table.catalog();
        }
        return null;
    }

    public long getDuplicatedEventQueueSize(String sql) {
        long res = 0;
        if (connected) {
            beginTransaction();
            res = getSession().createSQLQuery(sql).list().size();
            commitTransaction();
        }
        return res;
    }

    public long getCountWhereEqual(String name, Object value) {
        return getCountWhere(whereEqual(name, value));
    }

    public Long count() {
        return getCountWhere(null);
    }

    public void update(E entity) {
        if (connected) {
            beginTransaction();
            getSession().update(entity);
            commitTransaction();
        }
    }

    public List<String> selectListResult(String query) {
        Long start = new Date().getTime();
        beginTransaction();
        List<String> res = getSession().createSQLQuery(query).list();
        commitTransaction();
        System.out.println("SQL Response : " + res.size() + " records [query : " + query + "]");
        return res;
    }

    private E findWhere(BiFunction<CriteriaBuilder, Root<E>, Predicate> where, String message) {
        List<E> res = findAllWhere(where);
        if (res == null || res.isEmpty()) {
            System.out.println("can't find any " + getTableName() + " where '" + message + "'");
            return null;
        }
        return res.get(0);
    }

    public E findById(final Serializable id) {
        E res = null;
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        res = session.get(entityClass, id);
        session.getTransaction().commit();
        session.close();
        return res;
    }

    private List<E> findAllWhere(BiFunction<CriteriaBuilder, Root<E>, Predicate> where) {
        List<E> res;
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(entityClass);
        Root<E> root = query.from(entityClass);
        query.select(root);
        if (where != null) {
            query.where(where.apply(cb, root));
        }

        res = getSession().createQuery(query).getResultList();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public <T> List<T> findAllInTimeRange(String fieldNameToReturn, Class<T> fieldType, String timeField, Date startDate, Date endDate) {
        return findAllWhere(whereTimeInRange(timeField, startDate, endDate), fieldNameToReturn, fieldType);
    }

    public List<E> findAllInTimeRange(String timeField, Date startDate, Date endDate) {
        return findAllWhere(whereTimeInRange(timeField, startDate, endDate));
    }

    private <T> List<T> findAllWhere(BiFunction<CriteriaBuilder, Root<E>, Predicate> where, String fieldName, Class<T> fieldType) {
        List<T> res = new ArrayList<>();
        beginTransaction();

        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = cb
                .createQuery(fieldType);

        Root<E> root = query.from(entityClass);
        query.select(root.get(fieldName));
        if (where != null) {
            query.where(where.apply(cb, root));
        }

        res = getSession().createQuery(query).getResultList();
        commitTransaction();
        return res;
    }

    private long getCountWhere(BiFunction<CriteriaBuilder, Root<E>, Predicate> where) {
        long res = 0;
        if (connected) {
            beginTransaction();
            CriteriaBuilder builder = getSession().getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            Root<E> root = query.from(entityClass);
            query.select(builder.count(root));

            if (where != null) {
                query.where(where.apply(builder, root));
            }

            res = getSession().createQuery(query).getSingleResult();
            commitTransaction();
        }
        return res;
    }

    private Session getSession() {
        try {
            return getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return getSessionFactory().getCurrentSession();
        }
    }

    private void commitTransaction() {
        getSession().getTransaction().commit();
    }

    private void beginTransaction() {
        getSession().beginTransaction();
    }

    private BiFunction<CriteriaBuilder, Root<E>, Predicate> whereEqual(String name, Object value) {
        return (cb, root) -> cb.equal(root.get(name), value);
    }

    private BiFunction<CriteriaBuilder, Root<E>, Predicate> whereContains(String name, String value) {
        return (cb, root) -> cb.like(root.get(name), "%" + value + "%");
    }

    private BiFunction<CriteriaBuilder, Root<E>, Predicate> whereTimeInRange(String name, Date startDate, Date endDate) {
        Timestamp end = new Timestamp(endDate.getTime());
        Timestamp start = new Timestamp(startDate.getTime());
        return (cb, root) -> cb.between(root.get(name).as(Timestamp.class), start, end);
    }
}