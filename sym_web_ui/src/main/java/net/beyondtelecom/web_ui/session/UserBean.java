package net.beyondtelecom.web_ui.session;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.web_ui.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;
import static net.beyondtelecom.web_ui.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class UserBean extends JSFUpdatable {

    private static final String TABLE_NAME = "System Users";
    private List<bt_user> users;

    @Autowired
    public UserBean(SessionBean sessionBean) { super(sessionBean); }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_USERNAME);
        updatableColumns.add(HEADER_TEXT_FIRST_NAME);
        updatableColumns.add(HEADER_TEXT_LAST_NAME);
        updatableColumns.add(HEADER_TEXT_MSISDN);
        updatableColumns.add(HEADER_TEXT_MSISDN2);
        updatableColumns.add(HEADER_TEXT_EMAIL);
        updatableColumns.add(HEADER_TEXT_LANGUAGE);
        updatableColumns.add(HEADER_TEXT_COUNTRY);
        initializeUsers();
    }

    public void initializeUsers() { users = getEntityManagerRepo().findAll(bt_user.class, true); }

    public List<bt_user> getUsers() { return users; }

    public void setUsers(List<bt_user> users) { this.users = users; }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

