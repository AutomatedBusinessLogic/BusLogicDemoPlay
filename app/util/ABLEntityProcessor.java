package util;

import org.hibernate.LockOptions;
import org.hibernate.Session;

import play.db.jpa.JPA;
import play.db.jpa.Model;

import com.autobizlogic.abl.data.PersistentBean;
import com.autobizlogic.abl.logic.EntityProcessor;
import com.autobizlogic.abl.logic.Verb;

/**
 * This class must be registered with the ABL engine using the ABLConfig.properties
 * file. It handles some quirks of Play, namely:
 * <ul>
 * 	<li>Play's save tends to flush objects right away, which vacates them from the
 * persistence context. This is undesirable since the business rules still need
 * to deal with these objects.
 * <li>Play also has a peculiar Hibernate interceptor, which decides which objects
 * should be saved, and which should not. The only way to make sure that an object
 * gets persisted if it needs to be, is to call JPABase._save on that object.
 * </ul>
 */
public class ABLEntityProcessor implements EntityProcessor {

	/**
	 * Reattach the persistent bean to the current session, since more likely
	 * than not it has been flushed out by Play.
	 */
	@Override
	public void preProcess(Verb verb, PersistentBean bean) {
		// Note that we don't have to do anything for delete because, well,
		// the object is being deleted.
		if (verb != Verb.INSERT && verb != Verb.UPDATE)
			return;
		Session session = (Session)JPA.em().getDelegate();
		// This re-attaches the bean to the session
		session.buildLockRequest(LockOptions.NONE).lock(bean.getEntity());
	}

	/**
	 * Assure that the object really does get saved if it needs to be.
	 */
	@Override
	public void postProcess(Verb verb, PersistentBean bean) {
		if (verb != Verb.INSERT && verb != Verb.UPDATE)
			return;
		if (bean.getEntity() instanceof Model)
			((Model)bean.getEntity()).save();
	}

}
