package util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.autobizlogic.abl.logic.analysis.ClassLoaderManager;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.classloading.ApplicationClasses.ApplicationClass;
import play.db.jpa.JPABase;

/**
 * Because of the peculiar way in which the Play framework handles classloading, we need to
 * pre-load the classes' bytecode before any business logic is executed. This is done by this
 * plugin, which must be activated in your play.plugins file (towards the end, after all the
 * system plugins).
 * 
 * For this plugin to work properly, you must not be running in production mode.
 * You can disable production mode by editing your PLAY_OPTS environment variable: 
 * $ heroku config:remove PLAY_OPTS 
 * $ heroku config:add PLAY_OPTS=--%prod 
 * Alternatively, you can edit your Procfile to read: 
 * web: play run --http.port=$PORT --%prod 
 * 
 */
public class ABLPlugin extends PlayPlugin {
	private static final Set<String> classesLoaded = new HashSet<String>();
	
	/**
	 * This gets called when the application starts. It passes the enhanced bytecode to ABL
	 * for analysis. This method gets called first when running in standalone mode, whereas
	 * enhance gets called when running on Heroku, so we need to support both.
	 */
	@Override
	public void onApplicationStart() {
		Logger.info("ABLPlugin started");
		List<ApplicationClass> classes = Play.classes.all();
		for (ApplicationClass cls: classes) {
			// We don't need to load the model classes
			if (cls.javaClass != null && JPABase.class.isAssignableFrom(cls.javaClass))
				continue;
			if (classesLoaded.contains(cls.name))
				continue;
			if (cls.enhancedByteCode != null)
				ClassLoaderManager.getInstance().defineClass(cls.name, cls.enhancedByteCode);
			else
				Logger.error("Enhanced bytecode not available for class " + cls.name + 
						". ABL engine will most likely not work properly. Make sure that you don't run in production mode.");
			Logger.debug("ABL plugin - onApplicationStart: passed enhanced bytecode to ABL for class : " + cls.name);
			classesLoaded.add(cls.name);
		}
	}
	
	/**
	 * This gets called at precompile time. We pass the bytecode for the logic classes
	 * to the ABL engine for analysis. Play handles classes in a very peculiar way: it instruments
	 * classes without (conceptually at least) writing the bytecode to disk. Therefore getting the bytecode
	 * for analysis can be a challenge. This is the best way we have found so far.
	 */
	@Override
	public void enhance(ApplicationClasses.ApplicationClass cls) {
		if (classesLoaded.contains(cls.name))
			return;
		
		// We don't need to load the model classes
		if (cls.javaClass != null && JPABase.class.isAssignableFrom(cls.javaClass))
			return;
		
		if (classesLoaded.isEmpty()) // Only do this for the first class
			Logger.info("ABLPlugin - loading bytecode");

		// Note that this will include some non-logic classes . Unfortunately, there is no
		// realistic way to determine which classes are related to the logic classes,
		// so we load them all.
		ClassLoaderManager.getInstance().defineClass(cls.name, cls.enhancedByteCode);
		Logger.info("ABL plugin - enhance: passed enhanced bytecode to ABL for class : " + cls.name);
		classesLoaded.add(cls.name);		
	}
}
