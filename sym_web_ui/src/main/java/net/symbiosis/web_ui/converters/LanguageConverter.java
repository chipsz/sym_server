package net.symbiosis.web_ui.converters;

import net.symbiosis.persistence.entity.enumeration.sym_language;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static java.lang.Long.parseLong;
import static net.symbiosis.common.utilities.SymValidator.isNumeric;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@ApplicationScoped
@FacesConverter(value = "languageConverter")
public class LanguageConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (isNumeric(value)) {
            return getEntityManagerRepo().findById(sym_language.class, parseLong(value));
        } else {
            return findByName(sym_language.class, value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            return getEntityManagerRepo().findById(sym_language.class, parseLong(value.toString())).getName();
        } else if (value instanceof sym_language) {
            return ((sym_language) value).getName();
        } else return null;
    }
}