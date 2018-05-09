package net.beyondtelecom.web_ui.converters;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_company;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static java.lang.Long.parseLong;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isNumeric;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@ManagedBean
@FacesConverter(value="companyConverter")
public class CompanyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || !isNumeric(value)) { return null; }
        else { return getEntityManagerRepo().findById(bt_company.class, parseLong(value)); }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            bt_company result = getEntityManagerRepo().findById(bt_company.class, parseLong(value.toString()));
            return result.getId().toString();
        } else if (value instanceof bt_company) {
            bt_company input = (bt_company)value;
            return input.getId().toString();
        } else { return ""; }
    }
}