package cz.fi.muni.pa165.hotelbookingmanagerweb;

import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Andrej Galád
 */
public class CountryPicker {
    
    private SortedSet<String> countries;
    
    public Set<String> getCountriesName() {
        if (countries != null && !countries.isEmpty())
            return countries;
        
        countries = new TreeSet<>();
        for (String countryISO : Locale.getISOCountries()) {
            String displayCountry = new Locale("en", countryISO).getDisplayCountry();
            System.err.println(displayCountry);
            if (!"".equals(displayCountry) && displayCountry.length() <= 20)
                countries.add(displayCountry);
        }
        return countries;
    }
}
