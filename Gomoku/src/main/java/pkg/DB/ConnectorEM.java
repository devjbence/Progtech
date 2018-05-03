package pkg.DB;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Singleton class for the configured session factory.
 */
public class ConnectorEM {
	/**
	 * Configuration of the session factory object.
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Configures the session factory.
	 * @return configured session factory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			return configuration.buildSessionFactory(
					new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("There was an error building the factory");
		}
	}
	
	/**
	 * Get session factory object.
	 * @return session factory object
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
