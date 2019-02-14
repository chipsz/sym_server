package net.symbiosis.persistence.entity.super_class;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 */

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class sym_complex_enum<G extends sym_complex_enum> extends
        sym_enum_entity<G> {

//	protected ArrayList<G> groupItems;
//	protected G group;

    public sym_complex_enum() {
    }

    public sym_complex_enum(String name, Boolean enabled) {
        super(name, enabled);
    }

//	public sym_complex_enum(String name, Boolean enabled,
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
