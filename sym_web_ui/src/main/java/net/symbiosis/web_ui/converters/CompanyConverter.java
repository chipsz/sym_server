package net.symbiosis.web_ui.converters;

import net.symbiosis.persistence.entity.complex_type.sym_company;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static java.lang.Long.parseLong;
import static net.symbiosis.common.utilities.SymValidator.isNumeric;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@ApplicationScoped
@FacesConverter(value = "companyConverter")
public class CompanyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || !isNumeric(value)) {
            return null;
        } else {
            return getEntityManagerRepo().findById(sym_company.class, parseLong(value));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            sym_company result = getEntityManagerRepo().findById(sym_company.class, parseLong(value.toString()));
            return result.getId().toString();
        } else if (value instanceof sym_company) {
            sym_company input = (sym_company) value;
            return input.getId().toString();
        } else {
            return "";
        }
    }
}