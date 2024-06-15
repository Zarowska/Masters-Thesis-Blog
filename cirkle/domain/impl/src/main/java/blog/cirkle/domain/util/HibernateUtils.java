package blog.cirkle.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateUtils {

	public static <T> T unwrap(T entity) {
		if (entity == null) {
			throw new NullPointerException("Entity passed for initialization is null");
		}

		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
			entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		}

		return entity;
	}

}
