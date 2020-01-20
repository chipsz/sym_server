package net.symbiosis.web_ui.converters;

import net.symbiosis.persistence.entity.complex_type.voucher.sym_service_provider;

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
@FacesConverter(value="spConverter")
public class ServiceProviderConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (isNumeric(value)) {
            return getEntityManagerRepo().findById(sym_service_provider.class, parseLong(value));
        } else {
            return findByName(sym_service_provider.class, value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            return getEntityManagerRepo().findById(sym_service_provider.class, parseLong(value.toString())).getName();
        } else if (value instanceof sym_service_provider) {
            return String.valueOf(((sym_service_provider)value).getName());
        } else return null;
    }
}