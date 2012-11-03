package com.bestbuy.bbym.ise.drp;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.resource.loader.IStringResourceLoader;

import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;

public class DatabaseStringResourceLoader implements IStringResourceLoader {

    private final DrpPropertyService drpPropertyService;

    public DatabaseStringResourceLoader(final DrpPropertyService drpPropertyService) {
	this.drpPropertyService = drpPropertyService;
    }

    /**
     * Get the requested string resource from the underlying resource bundle.
     * The bundle is selected by locale and the string obtained from the best
     * matching bundle.
     * 
     * @param clazz
     *            Not used for this implementation
     * @param key
     *            The key to obtain the string for
     * @param locale
     *            The locale identifying the resource set to select the strings
     *            from
     * @param style
     *            Not used for this implementation (see
     *            {@link org.apache.wicket.Session})
     * @return The string resource value or null if resource not found
     */
    public final String loadStringResource(Class<?> clazz, String key, Locale locale, String style, String variation) {
	return drpPropertyService.getWicketProperty(key);
    }

    @Override
    public final String loadStringResource(Component component, String key, Locale locale, String style,
	    String variation) {
	return drpPropertyService.getWicketProperty(key);
    }
}
