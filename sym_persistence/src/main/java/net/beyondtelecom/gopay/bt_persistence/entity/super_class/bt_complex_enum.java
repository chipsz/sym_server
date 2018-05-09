package net.beyondtelecom.gopay.bt_persistence.entity.super_class;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
*/

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class bt_complex_enum<G extends bt_complex_enum> extends
	bt_enum_entity<G> {

//	protected ArrayList<G> groupItems;
//	protected G group;

	public bt_complex_enum() {}

	public bt_complex_enum(String name, Boolean enabled) {
		super(name, enabled);
	}

//	public bt_complex_enum(String name, Boolean enabled,
//								  ArrayList<G> groupItems, G group) {
//		super(name, enabled);
//		this.groupItems = groupItems;
//		this.group = group;
//	}

//	@Basic(fetch = FetchType.LAZY, optional = false)
//	public G getGroup() {
//		return group;
//	}
//
//	protected void setGroup(G group) {
//		this.group = group;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY)
//	public ArrayList<G> getGroupItems() {
//		return groupItems;
//	}
//
//	public void setGroupItems(ArrayList<G> groupItems) {
//		this.groupItems = groupItems;
//	}

}
