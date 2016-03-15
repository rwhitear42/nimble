package com.cloupia.feature.nimble.menuProvider;

import java.util.ArrayList;
import java.util.List;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.model.cIM.AbstractTreeNodesProviderIf;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.model.cIM.UIMenuLHTreeNode;
import com.cloupia.model.cIM.UIMenuLHTreeProvider;

/**
 * This is an example that shows you how to build your own left hand navigation tree.  In this example,
 * you'll see this tree provider is "binded" to dummy_menu_1 as defined in our menu.xml.  Also, make sure
 * to note where in NimbleModule.java this provider is being instantiated and registered!
 *
 */
public class DummyMenuProvider extends AbstractTreeNodesProviderIf {

	@Override
	public List<UIMenuLHTreeNode> getLHTreeNodes() throws Exception {
		ArrayList<UIMenuLHTreeNode> nodes = new ArrayList<UIMenuLHTreeNode>();
		//for simplicity's sake i will only have one entry in my tree
		//if you need to, you can have a multiple level of nodes, you'll just need to specify
		//which UIMenuLHTreeNodes are children and which are parents.
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(NimbleConstants.DUMMY_CONTEXT_ONE);
		//i have a new set of reports that i want to show only under this dummy context one, this new node in
		//the tree will load all the reports applicable for this context.
		UIMenuLHTreeNode nodeOne = new UIMenuLHTreeNode("dummyOne", null, dummyContextOneType.getType(), null);
		//you can optionally provide a path to an icon image (your icon should've been placed in the resources
		//folder of your module, use the default open automation path to images "/app/uploads/openauto/".
		//you can also optionally provide the context id, this value will be passed along into the report context id
		//for the applicable reports, for example, any tabular report generator that may need a context id, should
		//be populated here when constructing the tree.
		nodes.add(nodeOne);
		return nodes;
	}

	@Override
	public void registerWithProvider() {
		//this is how you bind the tree provider to the menu item.
		UIMenuLHTreeProvider.getInstance().registerLHTreeNodeProvider( NimbleConstants.DUMMY_MENU_1 , this );
	}

}
