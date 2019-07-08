package net.symbiosis.web_ui.common;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public class SystemPages {
    public static final SystemPage PAGE_LOGIN = new SystemPage("login", "login.xhtml", null, null);
    public static final SystemPage PAGE_REGISTRATION = new SystemPage("authentication", "registration.xhtml", null, null);
    public static final SystemPage PAGE_RESET_PASSWORD = new SystemPage("resetPassword", "reset_password.xhtml", null, null);
    public static final SystemPage PAGE_SUMMARY = new SystemPage("summary", "admin.xhtml", "summary.xhtml", null);
    public static final SystemPage PAGE_V_IMPORT = new SystemPage("vImport", "admin.xhtml", "v_import.xhtml", null);
    public static final SystemPage PAGE_WGVD_UPDATE = new SystemPage("wgvdEdit", "admin.xhtml", "wgvd_update.xhtml", null);
    public static final SystemPage PAGE_WGTC_UPDATE = new SystemPage("wgtcEdit", "admin.xhtml", "wgtc_update.xhtml", null);
    public static final SystemPage PAGE_V_UPDATE = new SystemPage("vEdit", "admin.xhtml", "v_update.xhtml", null);
    public static final SystemPage PAGE_V_CREATE = new SystemPage("vCreate", "admin.xhtml", "v_create.xhtml", null);
    public static final SystemPage PAGE_M_CREATE = new SystemPage("mCreate", "admin.xhtml", "m_create.xhtml", null);
    public static final SystemPage PAGE_WG_UPDATE = new SystemPage("wgEdit", "admin.xhtml", "wg_update.xhtml", null);
    public static final SystemPage PAGE_VT_UPDATE = new SystemPage("vtEdit", "admin.xhtml", "vt_update.xhtml", null);
    public static final SystemPage PAGE_SP_UPDATE = new SystemPage("spEdit", "admin.xhtml", "sp_update.xhtml", null);
    public static final SystemPage PAGE_VP_UPDATE = new SystemPage("vpEdit", "admin.xhtml", "vp_update.xhtml", null);

    public static final SystemPage PAGE_M_UPDATE = new SystemPage("mEdit", "admin.xhtml", "m_update.xhtml", null);
    public static final SystemPage PAGE_USER_UPDATE = new SystemPage("userEdit", "admin.xhtml", "u_update.xhtml", null);
    public static final SystemPage PAGE_AU_UPDATE = new SystemPage("auEdit", "admin.xhtml", "au_update.xhtml", null);
    public static final SystemPage PAGE_PM_UPDATE = new SystemPage("pmEdit", "admin.xhtml", "pm_update.xhtml", null);

    public static final SystemPage PAGE_TRAN_REPORT = new SystemPage("tranReport", "admin.xhtml", "t_report.xhtml", null);
    public static final SystemPage PAGE_PMNT_REPORT = new SystemPage("pmntReport", "admin.xhtml", "p_report.xhtml", null);
    public static final SystemPage PAGE_AUTH_REPORT = new SystemPage("authReport", "admin.xhtml", "a_report.xhtml", null);
    public static final SystemPage PAGE_SYS_REPORT = new SystemPage("sysReport", "admin.xhtml", "s_report.xhtml", null);

    public static final SystemPage PAGE_S_TRAN_REPORT = new SystemPage("sTranReport", "admin.xhtml", "s_t_report.xhtml", null);
    public static final SystemPage PAGE_S_PMNT_REPORT = new SystemPage("sPmntReport", "admin.xhtml", "s_p_report.xhtml", null);
    public static final SystemPage PAGE_S_AUTH_REPORT = new SystemPage("sAuthReport", "admin.xhtml", "s_a_report.xhtml", null);

    public static final SystemPage PAGE_U_SETTINGS = new SystemPage("uSettings", "admin.xhtml", "settings.xhtml", null);

}
