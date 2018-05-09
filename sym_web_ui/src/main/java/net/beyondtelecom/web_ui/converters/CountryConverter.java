package net.beyondtelecom.web_ui.converters;

import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_country;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static java.lang.Long.parseLong;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isNumeric;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@ManagedBean
@FacesConverter(value="countryConverter")
public class CountryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (isNumeric(value)) {
            return getEntityManagerRepo().findById(bt_country.class, parseLong(value));
        } else {
            return findByName(bt_country.class, value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            return getEntityManagerRepo().findById(bt_country.class, parseLong(value.toString())).getName();
        } else if (value instanceof bt_country) {
            return ((bt_country)value).getName();
        } else return null;
    }
}