<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortInwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>


<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Outward Drillable Report</h3>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		

			<table id="jqGrid"></table>
			<div id="jqGridPager"></div>
		


	</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#jqGrid").jqGrid({
            url: './warehouseOutwardJsonServlet2',
            mtype: "GET",
            datatype: "json",
            colModel: [
            
			{
				label : 'ID',
				name : 'warehouse_outward_id',
				key : true,
				width : 75
			}, {
				label : 'Vehicle Date',
				name : 'vehicle_dt_str',
				width : 150
			}, {
				label : 'vehicle no',
				name : 'vehicle_no',
				width : 150,
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions : {
					sopt : [ 'cn', 'eq' ]
				}
			}, {
				label : 'buyer Name',
				name : 'buyerName',
				width : 150
			}, {
				label : 'section Wt',
				name : 'sectionWt',
				width : 150,
				search : false
			}, {
				label : 'actual wt',
				name : 'actual_wt',
				width : 150,
				search : false
			}, {
				label : 'transporter',
				name : 'transporter_name',
				width : 150,
				search : false
			}

			],
			loadonce : true,
			width : 780,
			height : 250,
			rowNum : 10,
			footerrow: true,
			loadComplete : function() {
				var $grid =  $("#jqGrid");
				var actualWeightSum = $grid.jqGrid('getCol', 'actual_wt', false, 'sum');
				console.log("actualWeightSum = "+actualWeightSum);

				$grid.jqGrid('footerData','set', {sectionWt: 'Total:', actual_wt: actualWeightSum});
			},
			// set the subGrid property to true to show expand buttons for each row
			subGrid : true,
			// javascript function that will take care of showing the child grid
			subGridRowExpanded : showChildGrid,
			subGridOptions : {
				// expand all rows on load
				expandOnLoad : false
			},
			pager : "#jqGridPager",
			gridComplete: function(){
				var $grid = $("#jqGrid");
				$grid.jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
        		
			}
		});
	});

	// the event handler on expanding parent row receives two parameters
	// the ID of the grid tow  and the primary key of the row
	function showChildGrid(parentRowID, parentRowKey) {

		console.log("parentRowID = "+parentRowID);
		console.log("parentRowKey = "+parentRowKey);
		// create unique table and pager
		var childGridID = parentRowID + "_table";
		var childGridPagerID = parentRowID + "_pager";

		// send the parent row primary key to the server so that we know which grid to show
		var childGridURL = "warehouseOutwardTempJsonServlet?warehouseOutwardId="+parentRowKey;
	
		// add a table and pager HTML elements to the parent grid row - we will render the child grid here
		$('#' + parentRowID)
				.append(
						'<table id=' + childGridID + '></table><div id=' + childGridPagerID + '></div>');

		$("#" + childGridID).jqGrid({
			url : childGridURL,
			mtype : "GET",
			datatype : "json",
			page : 1,
			colModel : [ {
				label : 'outward_temp_id',
				name : 'outward_temp_id',
				key : true,
				width : 75
			}, {
				label : 'mill_name',
				name : 'mill_name',
				width : 100
			}, {
				label : 'make',
				name : 'make',
				width : 100
			}, {
				label : 'grade',
				name : 'grade',
				width : 100
			}, {
				label : 'thickness',
				name : 'thickness',
				width : 75
			},{
				label : 'length',
				name : 'length',
				width : 75
			},{
				label : 'width',
				name : 'width',
				width : 75
			},{
				label : 'actual_wt',
				name : 'actual_wt',
				width : 75
			},{
				label : 'reqd_qty',
				name : 'reqd_qty',
				width : 75
			}, {
				label : 'taken_qty',
				name : 'taken_qty',
				width : 75
			}  ],
			loadonce : true,
			footerrow: true,
			loadComplete : function() {
				var $grid =  $("#" + childGridID);
				var actualWeightSum = $grid.jqGrid('getCol', 'actual_wt', false, 'sum');
				console.log("actualWeightSum = "+actualWeightSum);

				$grid.jqGrid('footerData','set', {width: 'Total:', actual_wt: actualWeightSum});
			},
			width : 500,
			height : '100%',
			pager : "#" + childGridPagerID
		});
	}
</script>


