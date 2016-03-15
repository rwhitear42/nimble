package com.cloupia.feature.nimble.drilldownreports;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ColumnDefinition;
import com.cloupia.model.cIM.Query;
import com.cloupia.model.cIM.QueryBuilder;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.pagination.PaginatedReportHandler;
import com.cloupia.model.cIM.pagination.TabularReportMetadata;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

/**
 * This  class play main role for the pagination support
 * This is the implementation of PaginatedReportHandler for pagination support it handles the base logic
 * to retrieve sub query generation
 *
 */
public class NimbleAccountDrillDownReportHandler extends PaginatedReportHandler {

	static Logger logger = Logger.getLogger(NimbleAccountDrillDownReportHandler.class);
	
	/*
	 * This method is used to get sub query.
	 * @return Query This returns Query(Ex:accountName= 'Test-Nimble-Account')
	 */
	@Override
	public Query appendContextSubQuery(ReportRegistryEntry entry,
			TabularReportMetadata md, ReportContext rc, Query query) {
		logger.info("entry.isPaginated():::::"+entry.isPaginated());
		// TODO Auto-generated method stub
		String contextID = rc.getId();
		

		if (contextID != null && !contextID.isEmpty()) {
			String str[] = contextID.split(";");
			String accountName = str[0]; 
			logger.info("paginated context ID = " + contextID);

			int mgmtColIndex = entry.getManagementColumnIndex();

			logger.info("mgmtColIndex :: " + mgmtColIndex);
			ColumnDefinition[] colDefs = md.getColumns();
			ColumnDefinition mgmtCol = colDefs[mgmtColIndex];
			String colId = mgmtCol.getColumnId();
			logger.info("colId :: " + colId);
			//sub query builder builds the context id sub query (e.g. id = 'xyz')
			QueryBuilder sqb = new QueryBuilder();
			//sqb.putParam()
			sqb.putParam(colId).eq(accountName);
			//qb ands sub query with actual query (e.g. (id = 'xyz') AND ((vmID = 36) AND (vdc = 'someVDC')))
			if (query == null) {
				//if query is null and the id field has actual value, we only want to return columnName = value of id
				//which is what sqb should be
				logger.info("query == null ::  ---- paginated context ID = " + contextID);
				Query q = sqb.get();
				logger.info("query == null ::  ---- paginated context ID1 = " + q);
				return q;
			} else {
				QueryBuilder qb = new QueryBuilder();
				qb.and(query, sqb.get());
				return qb.get();
			}
		} else {
			/*UserSession session = UserSessionUtil.getCurrentUserSession();
            if (session == null) {
                return query;
            }
            try {
	            session.retrieveProfileAndAccess();
	            if (session.getAccess().isMSPUser()) {
	            	logger.info("MSP User");
	            	Group[] grpList = GroupManagerImpl.getCustomerGroupsUnderMSP(Integer.parseInt(
	                        session.getLoginProfile().getCustomerId()));
		        	QueryBuilder sqb = new QueryBuilder();
					Query [] queries = new Query[grpList.length];
					int index = 0;
					for(Group g : grpList) {
						queries[index++] = new QueryBuilder().putParam("groupId").eq(
								g.getGroupId()).get();
					}
					query = sqb.or(queries).get();
	            }
            } catch(Exception ex) {
            	logger.error("Error while retrieving group info");
            }*/
			return query;
		}
	}



}
