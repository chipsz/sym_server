package net.beyondtelecom.gopay.bt_persistence.entity.super_class;

import net.beyondtelecom.gopay.bt_common.utilities.PrintableStringClass;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/* *************************************************************************
 * Created:     18 / 09 / 2015                                             *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
*/

@MappedSuperclass
public abstract class bt_entity<E extends bt_entity> implements Serializable, PrintableStringClass
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

	public bt_entity() {}

    public Long getId() { return id; }

    /* this function is private because we do not want to be
     * able to update an entity id via the program ever */
    private E setId(Long id) { this.id = id; return (E)this; }

	@Override
	public String toString() {
		return this.toPrintableString();
	}

	public E save() {
		if (id != null) {
			return getEntityManagerRepo().saveOrUpdate(this);
		}
		return getEntityManagerRepo().save(this);
	}

	public bt_entity<E> refresh() { return getEntityManagerRepo().refresh(this); }

	public void delete() { getEntityManagerRepo().delete(this); }
}
