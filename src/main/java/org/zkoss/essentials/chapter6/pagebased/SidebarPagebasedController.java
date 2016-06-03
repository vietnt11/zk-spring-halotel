/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.essentials.chapter6.pagebased;

import org.zkoss.essentials.services.SidebarPage;
import org.zkoss.essentials.services.SidebarPageConfig;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SidebarPagebasedController extends SelectorComposer<Component>{

	private static final long serialVersionUID = 1L;
	@Wire
	Grid fnList;
	
	//wire service
	@WireVariable("sidebarPageConfigPagebase")
	SidebarPageConfig pageConfig;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		//to initial view after view constructed.
		Rows rows = fnList.getRows();
		
		for(SidebarPage page:pageConfig.getPages()){
			Row row = constructSidebarRow(page.getName(),page.getLabel(),page.getIconUri(),page.getUri());
			rows.appendChild(row);
		}
	}

	private Row constructSidebarRow(String name,String label, String imageSrc, final String locationUri) {
		
		//construct component and hierarchy
		Row row = new Row();
		Image image = new Image(imageSrc);
		Label lab = new Label(label);
		
		row.appendChild(image);
		row.appendChild(lab);
		
		//set style attribute
		row.setSclass("sidebar-fn");
			
		EventListener<Event> actionListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				//redirect current url to new location
				Executions.getCurrent().sendRedirect(locationUri);
			}
		};
		
		row.addEventListener(Events.ON_CLICK, actionListener);

		return row;
	}
}
