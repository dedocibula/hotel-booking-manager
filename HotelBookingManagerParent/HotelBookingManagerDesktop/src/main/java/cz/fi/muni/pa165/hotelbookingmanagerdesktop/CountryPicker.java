package cz.fi.muni.pa165.hotelbookingmanagerdesktop;

import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JComboBox;

/**
 *
 * @author Filip Bogyai
 */
public class CountryPicker {
    
    private SortedSet<String> countries;

	public Set<String> getCountriesName() {
		if (countries != null && !countries.isEmpty()) {
			return countries;
		}

		countries = new TreeSet<>();
		for (String countryISO : Locale.getISOCountries()) {
			String displayCountry = new Locale("en", countryISO).getDisplayCountry();
			if (!"".equals(displayCountry) && displayCountry.length() <= 20) {
				countries.add(displayCountry);
			}
		}
		return countries;
	}
        
        public void initializeComboBox(JComboBox comboBox){
        
            Set<String> countries = getCountriesName();
            for(String country : countries){
                comboBox.addItem(country);
            }           
        
        }
    
}
