package orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Properties;

import static util.ApplicationPropertyUtil.applicationProperty;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static final long STATEMENT_BATCH_SIZE = 1000;
    private static Session session;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties prop = new Properties();
                prop.setProperty(Environment.DRIVER, com.mysql.jdbc.Driver.class.getCanonicalName());
                prop.setProperty(Environment.URL, "jdbc:mysql://" + applicationProperty().get("sql.host") + ":3306/minfin?useSSL=false");
                prop.setProperty(Environment.USER, applicationProperty().get("sql.user").toString());
                prop.setProperty(Environment.PASS, applicationProperty().get("sql.pass").toString());
                prop.setProperty(Environment.DIALECT, org.hibernate.dialect.MySQL5Dialect.class.getCanonicalName());
                prop.setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, String.valueOf(false));
                prop.setProperty(Environment.STATEMENT_BATCH_SIZE, STATEMENT_BATCH_SIZE + "");

               prop.setProperty(Environment.POOL_SIZE, 1000 + "");

//                prop.setProperty(Environment.C3P0_MIN_SIZE, 5 + "");
//                prop.setProperty(Environment.C3P0_MAX_SIZE, 500 + "");
//                prop.setProperty(Environment.C3P0_TIMEOUT, 1000+"" );
//                prop.setProperty(Environment.C3P0_MAX_STATEMENTS, 100 + "");
//                prop.setProperty(Environment.C3P0_IDLE_TEST_PERIOD, 3000 + "");

                // prop.setProperty(Environment.SHOW_SQL, String.valueOf(true));
                // prop.setProperty(Environment.FORMAT_SQL, String.valueOf(true));

                prop.setProperty(Environment.GLOBALLY_QUOTED_IDENTIFIERS, String.valueOf(true));

                ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
                scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
                scanner.addIncludeFilter(new AnnotationTypeFilter(Embeddable.class));
                Configuration configuration = new Configuration().addProperties(prop);
                for (BeanDefinition bd : scanner.findCandidateComponents("orm.entity")) {
                    try {
                        configuration.addAnnotatedClass(Class.forName(bd.getBeanClassName()));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        if (session == null) {
            session = getSessionFactory().openSession();
        }
        return session;
    }

}
