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
	<div>
		

			<table id="jqGrid"></table>
			<div id="jqGridPager"></div>
		


	</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#jqGrid").jqGrid({
            url: './warehouseOutwardJsonServlet',
            mtype: "GET",
            datatype: "json",
            colModel: [
                { label: 'ID', name: 'warehouseOutwardId', key: true, width: 75 },
                { label: 'vehicleDate', name: 'vehicleDate', width: 150 },
                { label: 'vehicleNo', name: 'vehicleNo', width: 150 },
                { label: 'buyerName', name: 'buyerName', width: 150 },
                { label: 'sectionWt', name: 'sectionWt', width: 150 },
                { label: 'actualWeight', name: 'actualWeight', width: 150 },
                
                
            ],
            loadonce : true,
            width: 780,
            height: 250,
            rowNum: 10,
            // set the subGrid property to true to show expand buttons for each row
            subGrid: true,
            // javascript function that will take care of showing the child grid
            subGridRowExpanded: showChildGrid,
            subGridOptions : {
            // expand all rows on load
              expandOnLoad: false
            },
            pager: "#jqGridPager"
        });
    });

    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function showChildGrid(parentRowID, parentRowKey) {
        // create unique table and pager
        var childGridID = parentRowID + "_table";
        var childGridPagerID = parentRowID + "_pager";

        // send the parent row primary key to the server so that we know which grid to show
        var childGridURL = parentRowKey+".json";
        // add a table and pager HTML elements to the parent grid row - we will render the child grid here
        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + '></div>');

        $("#" + childGridID).jqGrid({
            url: childGridURL,
            mtype: "GET",
            datatype: "json",
            page: 1,
            colModel: [
                { label: 'Order ID', name: 'OrderID', key: true, width: 75 },
                { label: 'Required Date', name: 'RequiredDate', width: 100 },
                { label: 'Ship Name', name: 'ShipName', width: 100 },
                { label: 'Ship City', name: 'ShipCity', width: 100 },
                { label: 'Freight', name: 'Freight', width: 75 }
            ],
            loadonce: true,
            width: 500,
            height: '100%',
            pager: "#" + childGridPagerID
        });
    }

</script>


