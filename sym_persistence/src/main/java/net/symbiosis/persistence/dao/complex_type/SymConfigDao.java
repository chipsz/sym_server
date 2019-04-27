package net.symbiosis.persistence.dao.complex_type;

import net.symbiosis.persistence.dao.super_class.SymbisoisEnumEntityDao;
import net.symbiosis.persistence.entity.enumeration.sym_config;

import java.util.HashMap;

/***************************************************************************
 *                                                                         *
 * Created:     26 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface SymConfigDao extends SymbisoisEnumEntityDao<sym_config, Long> {
    HashMap<String, String> getAllConfigs();
    String getConfig(String configName);
}
