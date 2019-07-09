package net.symbiosis.web_ui.common;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;

import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_SYSTEM_NAME;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 07 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@ManagedBean
@Component
public class SystemSettings {

	private String systemName;

	SystemSettings() {
		this.systemName = getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME);
	}

	public String getSystemName() { return systemName; }

	public String getSystemNameToLower() { return getSystemName().toLowerCase(); }
}
