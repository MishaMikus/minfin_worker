package orm;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static final long STATEMENT_BATCH_SIZE = 1000;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties cred_prop = new Properties();
                InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties");
                cred_prop.load(stream);

                Properties prop = new Properties();
                prop.setProperty(Environment.DRIVER, com.mysql.jdbc.Driver.class.getCanonicalName());
                prop.setProperty(Environment.URL, "jdbc:mysql://"+cred_prop.get("host")+":3306/minfin?useSSL=false");
                prop.setProperty(Environment.USER, cred_prop.get("user").toString());
                prop.setProperty(Environment.PASS, cred_prop.get("pass").toString());
                prop.setProperty(Environment.DIALECT, org.hibernate.dialect.MySQL5Dialect.class.getCanonicalName());
                prop.setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, String.valueOf(false));
                prop.setProperty(Environment.STATEMENT_BATCH_SIZE, STATEMENT_BATCH_SIZE +"");

                //prop.setProperty(Environment.SHOW_SQL, String.valueOf(true));
                //prop.setProperty(Environment.FORMAT_SQL, String.valueOf(true));
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

}
