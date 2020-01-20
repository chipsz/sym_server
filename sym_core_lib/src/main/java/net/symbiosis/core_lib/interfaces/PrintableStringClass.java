package net.symbiosis.core_lib.interfaces;

import java.lang.reflect.Field;

/***************************************************************************
 * *
 * Created:     09 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface PrintableStringClass {

    default String toPrintableString() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("{ ");
            for (Field field : this.getClass().getDeclaredFields()) {
//                try {
//                    Method mGet = this.getClass().getDeclaredMethod("get");
//                    Method mGetName = this.getClass().getDeclaredMethod("getName");
//                    if (!mGet.canAccess(field) || !mGetName.canAccess(field)) {
//                        field.setAccessible(true);
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    return "";
//                }
                if (!field.isAccessible()) {
//                if (!this.canAccess(field)) {
                    field.setAccessible(true);
                }
                Object fieldValue = field.get(this);
                String fieldData;
                if (fieldValue == null) {
                    fieldData = "[" + field.getName() + ":null]";
                } else if (fieldValue instanceof String) {
                    fieldData = String.valueOf(fieldValue);
                } else if (fieldValue instanceof Number) {
                    fieldData = "" + fieldValue;
                } else if (fieldValue instanceof Boolean) {
                    fieldData = "" + fieldValue;
                } else {
                    fieldData = "[" + field.getName() + ":" +/* fieldValue +*/ "not null]";
                }
                stringBuilder.append(field.getName()).append("=\"").append(fieldData).append("\"\t");
            }
            stringBuilder.append("}");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
