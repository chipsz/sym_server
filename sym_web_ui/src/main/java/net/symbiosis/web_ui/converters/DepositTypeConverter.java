package net.symbiosis.web_ui.converters;

import net.symbiosis.persistence.entity.enumeration.sym_deposit_type;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static java.lang.Long.parseLong;
import static net.symbiosis.core_lib.utilities.SymValidator.isNumeric;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@ManagedBean
@FacesConverter(value = "dtConverter")
public class DepositTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (isNumeric(value)) {
            return getEntityManagerRepo().findById(sym_deposit_type.class, parseLong(value));
        } else {
            return findByName(sym_deposit_type.class, value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            return getEntityManagerRepo().findById(sym_deposit_type.class, parseLong(value.toString())).getName();
        } else if (value instanceof sym_deposit_type) {
            return ((sym_deposit_type) value).getName();
        } else return null;
    }
}