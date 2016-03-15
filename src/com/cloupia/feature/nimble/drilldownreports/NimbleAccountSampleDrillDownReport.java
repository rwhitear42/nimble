package com.cloupia.feature.nimble.drilldownreports;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.accounts.DummyAccount;
import com.cloupia.feature.nimble.accounts.model.DummyVLAN;
import com.cloupia.feature.nimble.accounts.pagination.NimbleAccountReportHandler;
import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.OperationConstants;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaEasyDrillableReport;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaEasyReportQueryBuilder;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReport;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;
import com.cloupia.service.cIM.inframgr.reports.simplified.actions.DrillDownAction;

public class NimbleAccountSampleDrillDownReport extends CloupiaEasyDrillableReport {
	
	static Logger logger = Logger.getLogger(NimbleAccountSampleDrillDownReport.class);
	
	private List<CloupiaReport> drillableReports = new ArrayList<CloupiaReport>();
	
	private List<CloupiaReportAction> apicActions = new ArrayList<CloupiaReportAction>();

	private static String report_Name;
	private static String report_Label;

	private static CloupiaEasyReportQueryBuilder proxyQueryBuilder = new CloupiaEasyReportQueryBuilder() {

		@Override
		public String buildQuery(ReportContext context) {

			return queryBuilder.buildQuery(context);

		}
	};

	private static CloupiaEasyReportQueryBuilder queryBuilder = new CloupiaEasyReportQueryBuilder() {

		@Override
		public String buildQuery(ReportContext context) {
			return " accountName == '" + context.getId() + "'";
		}
	};

	public NimbleAccountSampleDrillDownReport(String name, String label, Class dbSource) {
		this(name, label, dbSource, proxyQueryBuilder);
		super.setOperationLevel(OperationConstants.READ_STORAGE_INFRA);
		this.setMgmtDisplayColumnIndex(0);
		NimbleAccountSampleDrillDownChildReport drilChildReport = new NimbleAccountSampleDrillDownChildReport(
				"Nimble.drilldown.child.report", "Drill Down Child", DummyVLAN.class);

		drilChildReport.setMgmtDisplayColumnIndex(0);
		this.getDrillableReports().add(drilChildReport);
		this.report_Name = name;
		this.report_Label = label;

	}

	public NimbleAccountSampleDrillDownReport(String name, String label, Class dbSource,
			CloupiaEasyReportQueryBuilder queryBuilder) {
		super(name, label, dbSource);
		setQueryBuilder(queryBuilder);
		super.setOperationLevel(OperationConstants.READ_STORAGE_INFRA);
	}

	@Override
	public CloupiaReport[] getDrilldownReports() {

		if (drillableReports == null || drillableReports.isEmpty()) {
			return null;
		}
		CloupiaReport[] reports = new CloupiaReport[drillableReports.size()];

		return drillableReports.toArray(reports);
	}

	@Override
	public CloupiaReportAction[] getActions() {

		CloupiaReportAction[] actionsFilter = new CloupiaReportAction[] { new DrillDownAction() };
		return actionsFilter;
	}

	@Override
	public boolean isLeafReport() {
		return false;
	}

	@Override
	public int getContextLevel() {
		DynReportContext context = ReportContextRegistry.getInstance().getContextByName(
				NimbleConstants.Nimble_ACCOUNT_DRILLDOWN_NAME);
		logger.info("Context " + context.getId() + " " + context.getType());
		return context.getType();
	}

	public ContextMapRule[] getMapRules() {
		// i'm using an autogenerated report context (which I registered in
		// NimbleModule), as mentioned in documentation
		// the type may vary depending on deployments, so the safest way to
		// retrieve the auto generated type value
		// is to use the getContextByName api!
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(
				NimbleConstants.INFRA_ACCOUNT_TYPE);
		ContextMapRule rule = new ContextMapRule();
		rule.setContextName(dummyContextOneType.getId());
		rule.setContextType(dummyContextOneType.getType());

		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = rule;
		return rules;
	}

	@Override
	public Class getImplementationClass() {
		// this class handles all the report generation logic, look here for
		// more details
		// If the report is paginated model , return null for the
		// getImplementationClass().
		return null;
	}

	@Override
	public String getReportLabel() {
		return report_Label;
	}

	@Override
	public String getReportName() {
		return report_Name;
	}

	@Override
	public boolean isEasyReport() {
		return false;
	}

	@Override
	public int getMenuID() {
		return 51;// NimbleConstants.DUMMY_MENU_1;
	}

	@Override
	public Class getPaginationModelClass() {

		return DummyAccount.class;
	}

	/*
	 * This method is used to get the Pagination Handler class for the
	 * Pagination support
	 * 
	 * @return Class This returns Pagination handler class
	 */
	@Override
	public Class getPaginationProvider() {
		// TODO Auto-generated method stub
		return NimbleAccountReportHandler.class;
	}

	/*
	 * This method is used to check whether the report is Pagination or not.
	 * 
	 * @return boolean This returns true(if it is pagination supported report)
	 */
	@Override
	public boolean isPaginated() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * This method is used to get ReportHint type. If the report type is
	 * pagination,explicitly we have to specify the return type as
	 * ReportDefinition.REPORT_HINT_PAGINATED_TABLE.
	 * 
	 * @return int This returns PaginationHint
	 */
	@Override
	public int getReportHint() {
		return ReportDefinition.REPORT_HINT_PAGINATED_TABLE;
	}

	public List<CloupiaReport> getDrillableReports() {
		return drillableReports;
	}

	@Override
	public boolean isManagementReport() {
		return true;
	}

}
