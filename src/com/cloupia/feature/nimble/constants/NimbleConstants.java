package com.cloupia.feature.nimble.constants;


public class NimbleConstants {

	public static final String DEVICE_LIST_PROVIDER = "Nimble_DeviceListProvider";
	public static final String NEXUS_DEVICE_TABLE = "Nimble_DeviceTable";
	
	public static final String TEMP_EMAIL_ADDRESSES = "Nimble_email_address_list";
    public static final String NEXUS_DEVICE_LIST = "Nimble_nexus_device_list";
   
    public static final String EMAIL_TASK_OUTPUT_NAME = "Datacenter Email Addresses";
    public static final String EMAIL_TASK_OUTPUT_TYPE = "e-mail-as-string";
    
	public static final String NEXUS_DEVICES_LOV_PROVIDER = "NetworkDeviceList";

	public static final String Nimble_HELLO_WORLD_NAME = "Nimble_name_from_other_tasks";
	
	public static final String Nimble_MULTI_SELECT_TABULARVALUE_NAME = "NimbleSampleTabulerMultiInput";
	
	public static final String Nimble_NIMBLE_SAN_PROTOCOL_LOV_NAME = "Nimble_NimbleSanProtocolLovName";
	
	//this is the unique integer i'm giving for my dummy collector, it's a good idea
	//to use some large number past 1000 so you avoid any potential collisions
	public static final int DUMMY_ACCOUNT_TYPE = 6000;
	
	//some dummy strings used to represent inventory items
	public static final String INTERFACES = "interfaces";
	public static final String PORTS = "ports";
	
	public static final String DUMMY_INVENTORY_COLLECTOR_NAME = "Dummy_Inventory_Collector";
	
	public static final String DUMMY_VLAN_RESOURCE_TYPE = "Nimble.vlan.per.group.usage";
	public static final String DUMMY_VLAN_RESOURCE_DESC = "Max Dummy VLANs per group";
	
	public static final int DUMMY_MENU_1 = 11001;
	
	public static final String DUMMY_CONTEXT_ONE = "Nimble.dummy.context.one";
	public static final String DUMMY_CONTEXT_ONE_LABEL = "Dummy Context One";
	
	public static final String INFRA_ACCOUNT_LABEL = "Nimble Account";
	public static final String INFRA_ACCOUNT_TYPE = "Nimble Account";
	
	public static final String Nimble_ACCOUNT_DRILLDOWN_NAME = "Nimble.account.sample.child.drilldown.report";
	public static final String Nimble_ACCOUNT_DRILLDOWN_LABEL = "Nimble Account Drilldown Sample";
	
	public static final String Nimble_MY_FIRST_DROPDOWN = "Nimble.my.first.dropdown";
	public static final String Nimble_MY_FIRST_DROPDOWN_LABEL = "Nimble My First Dropdown Label";
	

	// Workflow task constants.
	public static final String GENERIC_TEXT_INPUT = "gen_text_input";
	public static final String PASSWORD = "password";
	public static final String BOOLEAN = "Boolean";
	
	// Workflow output values.
	public static final String NIMBLE_VOLUME_NAME = "NIMBLE_VOLUME_NAME";
	public static final String NIMBLE_VOLUME_COLLECTION_NAME = "NIMBLE_VOLUME_COLLECTION_NAME";
	public static final String NIMBLE_INITIATOR_GROUP_NAME = "NIMBLE_INITIATOR_GROUP_NAME";
	public static final String NIMBLE_INITIATOR_NAME = "NIMBLE_INITIATOR_NAME";
	
}
