package orm.entity;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import orm.HibernateUtil;

import javax.persistence.Table;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiFunction;

import static orm.HibernateUtil.*;
import static orm.HibernateUtil.getSession;

public abstract class GenericAbstractDAO<E> {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private final Class<E> entityClass;

    public GenericAbstractDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public void update(E entity) {
        beginTransaction();
        getSession().update(entity);
        getSession().flush();
        commitTransaction();
        closeSession();
        LOGGER.info("update " + entity);
    }

    public Serializable save(E entity) {
        beginTransaction();
        Serializable saveResult = getSession().save(entity);
        commitTransaction();
        closeSession();
        LOGGER.info("save " + entity);
        return saveResult;
    }

    public void deleteAllWhere(String fieldName, Object value) {
        beginTransaction();
        BiFunction<CriteriaBuilder, Root<E>, Predicate> where = whereEqual(fieldName, value);
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaDelete<E> delete = builder.createCriteriaDelete(entityClass);
        Root<E> root = delete.from(entityClass);
        delete.where(where.apply(builder, root));
        getSession().createQuery(delete).executeUpdate();
        commitTransaction();
        closeSession();
        LOGGER.info("deleteAllWhere(" + fieldName + " == " + value + ")");
    }

    public void saveBatch(List<E> entityList) {
        Long start = new Date().getTime();
        beginTransaction();
        for (int i = 0; i < entityList.size(); i++) {
            getSession().saveOrUpdate(entityList.get(i));
            if (i % STATEMENT_BATCH_SIZE == 0) {
                LOGGER.info(new Date() + " " + getCatalogName() + "." + getTableName() + " add " + i + " records");
                getSession().flush();
                getSession().clear();
            }
        }
        commitTransaction();
        closeSession();
        LOGGER.info("save " + entityList.size() + " records to " + getTableName() + " for " + (new Date().getTime() - start) + " ms");
    }

    public void updateBatch(List<E> entityList) {
        beginTransaction();
        for (int i = 0; i < entityList.size(); i++) {
            getSession().update(entityList.get(i));
            if (i % STATEMENT_BATCH_SIZE == 0) {
                getSession().flush();
                getSession().clear();
            }
        }
        commitTransaction();
        closeSession();
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
        beginTransaction();
        getSession().clear();
        commitTransaction();
    }

    public void closeSession() {
        getSession().close();
        HibernateUtil.session = null;
    }

    public void truncateTable() {
        try {
            beginTransaction();
            getSession().createSQLQuery("truncate table " + getCatalogName() + "." + getTableName()).executeUpdate();
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSession();
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
        beginTransaction();
        res = getSession().createSQLQuery(sql).list().size();
        commitTransaction();
        closeSession();
        return res;
    }

    public long getCountWhereEqual(String name, Object value) {
        return getCountWhere(whereEqual(name, value));
    }

    public Long count() {
        return getCountWhere(null);
    }

    public List<String> selectListResult(String query) {
        Long start = new Date().getTime();
        beginTransaction();
        List<String> res = getSession().createSQLQuery(query).list();
        commitTransaction();
        closeSession();
        LOGGER.info("SQL Response : " + res.size() + " records [query : " + query + "]");
        return res;
    }

    private E findWhere(BiFunction<CriteriaBuilder, Root<E>, Predicate> where, String message) {
        List<E> res = findAllWhere(where);
        if (res.isEmpty()) {
            System.out.println("can't find any " + getTableName() + " where '" + message + "'");
            return null;
        }
        return res.get(0);
    }

    public E findById(final Serializable id) {
        E res = null;
        beginTransaction();
        res = session.get(entityClass, id);
        commitTransaction();
        closeSession();
        return res;
    }

    private List<E> findAllWhere(BiFunction<CriteriaBuilder, Root<E>, Predicate> where) {
        beginTransaction();
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(this.entityClass);
        Root<E> root = query.from(this.entityClass);
        query.select(root);
        if (where != null) {
            query.where(where.apply(cb, root));
        }

        List<E> res = getSession().createQuery(query).getResultList();
        commitTransaction();
        closeSession();
        LOGGER.info("findAllWhere(" + where + ") " + getTableName() + " : " + res.size());
        return res;
    }

    public <T> List<T> findAllInTimeRange(String fieldNameToReturn, Class<T> fieldType, String timeField, Date startDate, Date endDate) {
        return findAllWhere(whereTimeInRange(timeField, startDate, endDate), fieldNameToReturn, fieldType);
    }

    public E findLatestWhere(String fieldName, Map<String, Object> whereEqualMap) {
        List<E> res = new ArrayList<>();
        beginTransaction();

        CriteriaBuilder cb = getSession().getCriteriaBuilder();

        CriteriaQuery<E> q = cb.createQuery(entityClass);
        Root<E> root = q.from(entityClass);
        q.select(root);
        for (Map.Entry<String, Object> entry : whereEqualMap.entrySet()) {
            q = q.where(cb.equal(root.get(entry.getKey()), entry.getValue()));
        }

        q.orderBy(cb.desc(root.get(fieldName)));
        try {
            res = getSession().createQuery(q).getResultList();
        } catch (Exception ignored) {
        }
        commitTransaction();

        if (res.size() == 0) {
            LOGGER.warn("can't find latest object in " + getTableName() + " by Date field " + fieldName);
            return null;
        }
        getSession().refresh(res.get(0));
        closeSession();
        return res.get(0);
    }

    public E findLatest(String fieldName) {
        return (findLatestWhere(fieldName, new HashMap<>()));
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
        closeSession();
        return res;
    }

    private long getCountWhere(BiFunction<CriteriaBuilder, Root<E>, Predicate> where) {
        long res = 0;
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
        closeSession();
        LOGGER.info("getCountWhere(" + where + ") : " + res);
        return res;
    }

    private void commitTransaction() {
        getSession().getTransaction().commit();
    }

    private void beginTransaction() {
        long start = new Date().getTime();
        long ping = 1000L;
        long timeout = 10000L;
        while ((new Date().getTime() - start < timeout)) {
            try {
                getSession().beginTransaction();
                return;
            } catch (Exception e) {
                commitTransaction();
                closeSession();
            }
            try {
                Thread.sleep(ping);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("WAIT FOR success transaction");
        }
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

    public Long sum(String name) {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setProjection(Projections.sum(name));
        List list = criteria.list();
        commitTransaction();
        long res = 0;
        if (list.size() > 0) {
            res = (long) list.get(0);
        }
        closeSession();
        LOGGER.info("sum(" + name + ")." + getTableName() + " : " + res);
        return res;
    }

    public void deleteById(Integer id) {
        beginTransaction();
        getSession().delete(getSession().load(entityClass, id));
        commitTransaction();
        closeSession();
        LOGGER.info("deleteById[" + id + "] " + getTableName());
    }

}