package net.symbiosis.web_ui.converters;

import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;

import javax.faces.bean.ManagedBean;
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
@ManagedBean
@FacesConverter(value="merchantConverter")
public class MerchantConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!isNumeric(value)) { return null; }
        else { return getEntityManagerRepo().findById(sym_wallet.class, parseLong(value)); }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            sym_wallet result = getEntityManagerRepo().findById(sym_wallet.class, parseLong(value.toString()));
            return result.getId().toString();
        } else if (value instanceof sym_wallet) {
            sym_wallet input = (sym_wallet)value;
            return input.getId().toString();
        } else { return ""; }
    }
}