$(document).ready(function() {
	$('.date-picker-div').datetimepicker({
		"format" : "DD/MM/YY"
	});

	$("form").attr("role", "form");
	applyNumericConstraint();
	$("#progressbar").progressbar({
		value : 0
	});

	if ($("#pageType").html() == "Report") {
		calcRptTotal();
		$(".total-field").html($("#details-tbody tr").length);
		if ($("#result-table").length > 0) {
			location.href = "#report-toolbar";
		}
		// else {
		// showLoader();
		// document.forms[0].submit();
		// }
		// $("#result-table").tablesorter({
		// sortList : [ [ 0, 0 ] ],
		// cancelSelection : true,
		// // headers : {
		// // 15 : {
		// // sorter : false
		// // }
		// // },
		// });
	}

});

function formatFileSize(bytes) {
	var thresh = 1024;
	if (Math.abs(bytes) < thresh) {
		return bytes + ' B';
	}
	var units = [ 'kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB' ];
	var u = -1;
	do {
		bytes /= thresh;
		++u;
	} while (Math.abs(bytes) >= thresh && u < units.length - 1);
	return bytes.toFixed(2) + ' ' + units[u];
}

function fillXmlSuccess(xml, select) {
	if (null != xml && "" != xml) {
		xmlDoc = $.parseXML(xml);
		var values = $(xmlDoc).find("value");
		if (values.length > 0)
			$(select).html("");
		values.each(function(i, data) {
			var str = "<option label='" + data.innerHTML + "' value='"
					+ data.innerHTML + "'>" + data.innerHTML + "</option>";
			$(select).append(str);
		});
		if ($(select).html() == "")
			$(select).html("<option value=''>--</option");
	}
}

function calcSecWtReport() {
	var tr = $(this).parent().parent();
	var thickness = $($(tr).find("[name=thickness]")[0]).val();
	var width = $($(tr).find("[name=width]")[0]).val();
	var length = $($(tr).find("[name=length]")[0]).val();
	var qty = $($(tr).find("[name=qty]")[0]).val();
	var secWt = calcSecWt(length, width, thickness, qty);
	$($(tr).find("[name=secWt]")[0]).val(secWt);
}

var record = {}

function editReportRow(ele) {
	var tr = $(ele).parent().parent();
	if ($(tr).hasClass("row-edit")) {
		updateReportData(tr);
	} else {
		makeRowEditable(tr);
	}
}

function deleteReportRow(ele) {
	var tr = $(ele).parent().parent();
	makeRowEditable(tr);
	deleteReportData(tr);
}

function makeRowEditable(tr) {
	$(tr).addClass("row-edit");
	var rowid = "row_" + $($(tr).find("td")[0]).html();
	var objRowId = {};
	$(tr).find("td").each(function(i, td) {
		var value = $(td).html();
		var name = $(td).attr("data-name");
		var type = $(td).attr("data-type");

		if (name)
			objRowId[name] = value;

		var options = {
			'name' : name,
			'type' : type,
			'value' : value,
			'class' : 'form-control'
		};
		if (type == "number") {
			options.step = $(td).attr("data-step");
			options.min = '0';
		}
		if (name) {
			$(td).html("");
			$('<input/>', options).appendTo(td);
			var nonArray = [ "beNo", "heatNo", "plateNo" ];
			if (type == "text" && nonArray.indexOf(name) == -1) {
				fillArray(name, 'query.unique.' + name);
			}
		}
	});
	record[rowid] = objRowId;
	var str = "<span class='glyphicon glyphicon glyphicon-ok'></span>";
	$($(tr).find(".cell-edit button")[0]).html(str);

	$(".row-edit [name=thickness]").change(calcSecWtReport);
	$(".row-edit [name=width]").change(calcSecWtReport);
	$(".row-edit [name=length]").change(calcSecWtReport);
	$(".row-edit [name=qty]").change(calcSecWtReport);
}

function updateReportData(tr) {
	var params = [], values = [];
	$(tr).find("input").each(function(i, input) {
		params.push(input.name);
		values.push(input.value)
	});
	var objRowId = {};
	var rowid = "row_" + values[0];
	var url = "./report?method=" + $(tr).attr("data-method");
	for (var i = 0; i < params.length; i++) {
		url = url + "&" + params[i] + "=" + encodeURIComponent(values[i]);
		objRowId[params[i]] = values[i];
	}
	
	var str = "<span class='glyphicon glyphicon glyphicon-pencil'></span>";
	$($(tr).find(".cell-edit button")[0]).html(str);

	if (JSON.stringify(record[rowid]) === JSON.stringify(objRowId)) {
		$(tr).find("td").each(function(i, td) {
			var input = $(td).find("input");
			if (input.length == 1) {
				input = input[0];
				var name = $(input).attr("name");
				var type = $(input).attr("type");
				var value = input.value;
				$(td).attr("data-name", name);
				$(td).attr("data-type", type);
				$(td).attr("data-value", value);
				if (name)
					$(td).html(value);
			}
		});
		calcRptTotal();
		$(tr).removeClass("row-edit");
	} else {
		showLoader();
		$.ajax({
			url : url,
			success : function(xml, textStatus, response) {
				if (null != xml && "" != xml) {
					xmlDoc = $.parseXML(xml);
					var message = $(xmlDoc).find("message")[0].innerHTML;
					if (message == "Success") {
						bootbox.alert("Record updated successfully",
								function() {
									fetchReport();
								});
					} else {
						bootbox.alert("Unable to Update Record", function() {
							fetchReport();
						});
					}
				}
			},
			error : function() {
				bootbox.alert("Unable to Update Record", function() {
					fetchReport();
				});

			}
		});
	}

}

function deleteReportData(tr) {
	var params = [], values = [];
	$(tr).find("input").each(function(i, input) {
		params.push(input.name);
		values.push(input.value)
	});
	var objRowId = {};
	var rowid = "row_" + values[0];
	var url = "./report?method=deletePortOutward";
	for (var i = 0; i < params.length; i++) {
		url = url + "&" + params[i] + "=" + encodeURIComponent(values[i]);
		objRowId[params[i]] = values[i];
	}
	
	var str = "<span class='glyphicon glyphicon glyphicon-remove'></span>";
	$($(tr).find(".cell-edit button")[0]).html(str);	
		showLoader();
		$.ajax({
			url : url,
			success : function(xml, textStatus, response) {
				if (null != xml && "" != xml) {
					xmlDoc = $.parseXML(xml);
					var message = $(xmlDoc).find("message")[0].innerHTML;
					if (message == "Success") {
						bootbox.alert("Record deleted successfully",
								function() {
									fetchReport();
								});
					} else {
						bootbox.alert("Unable to delete Record", function() {
							fetchReport();
						});
					}
				}
			},
			error : function() {
				bootbox.alert("Unable to delete Record", function() {
					fetchReport();
				});

			}
		});
	}



function resetReport() {
	$("[name='fromDate']").val("");
	$("[name='toDate']").val("");
	$("select").val("");
	$(".details-container").remove();
}

function fetchReport() {
	document.forms[0].genericListener = "getReport";
	document.forms[0].submit();
}

function commonSubmit(str) {
	if (!str || str == "")
		str = "Are you sure you want to Submit?";
	bootbox.confirm(str, function(result) {
		if (result) {
			document.forms[0].submit();
		}
	});
	return false;
}

function showLoader() {
	$("body").addClass("loading");
}

function hideLoader() {
	$("body").removeClass("loading");
}

function deleteMainRow(id) {
	if ($("#" + id).parent().find("tr.main-row").length > 1) {
		$("#" + id).remove();
		if ((id.match(/-/g) || []).length == 1) {
			var num = id.substring(id.lastIndexOf("-") + 1);
			$("#row-container-" + num).remove();
		}
	} else {
		bootbox.alert("Cannot Delete Last Row");
	}
		

	applyTotalCalc();
}

function calcSecWtRow(id) {
	var length = $($("#" + id + " input[name=length]")[0]).val();
	var width = $($("#" + id + " input[name=width]")[0]).val();
	var thickness = $($("#" + id + " input[name=thickness]")[0]).val();
	var qty = $($("#" + id + " input[name=qty]")[0]).val();
	var secWt = calcSecWt(length, width, thickness, qty);
	$($("#" + id + " input[name=secWt]")[0]).val(secWt);

	var num = id.substring(id.lastIndexOf("-") + 1);

	if ($("#row-container-" + num).length > 0) {
		$("#row-container-" + num).find("tr.sub-row").each(function() {
			var qty = $(this).find("input[name=subQty]")[0].value;
			var secWt = calcSecWt(length, width, thickness, qty);
			$(this).find("input[name=subSecWt]")[0].value = secWt;
		});
	}

	var btn1 = $("#" + id + " input[name=actWtUnit]").next();
	var btn3 = $("#" + id + " input[name=secWtUnit]").next();
	if (null != btn1 && null != btn3)
		btn3.html(btn1.html());

	var btn2 = $("#" + id + " input[name=wtUnit]").next();
	if (null != btn2 && null != btn3)
		btn3.html(btn2.html());

	applyTotalCalc();
}

function calcSecWt(length, width, thickness, qty) {
	if ("" == length)
		length = 0;
	if ("" == width)
		width = 0;
	if ("" == thickness)
		thickness = 0;
	if ("" == qty)
		qty = 0;
	var secWt = (length * width * thickness * 7.85) / 1000000000 * qty;
	secWt = secWt.toFixed(3);
	return secWt;
}




function applyNumericConstraint() {
	$("input[type='number']").keydown(function(e) {
		var key = e.which || e.keyCode;
		if (!e.shiftKey && !e.altKey && !e.ctrlKey &&
		// numbers
		key >= 48 && key <= 57 ||
		// Numeric keypad
		key >= 96 && key <= 105 ||
		// period and . on keypad
		key == 188 || key == 110 || key == 190 ||
		// Backspace and Tab and Enter
		key == 8 || key == 9 || key == 13 ||
		// Home and End
		key == 35 || key == 36 ||
		// left and right arrows
		key == 37 || key == 39 ||
		// Del and Ins
		key == 46 || key == 45)
			return true;
		else
			return false;
	});
	applyTotalCalc();

}

function applyTotalCalc() {
	$("[name='beWt']").keyup(function() {
		calcBeWtTotal();
	});
	$("[name='beWt']").change(function() {
		calcBeWtTotal();
	});
	calcBeWtTotal();

	$("[name='secWt']").keyup(function() {
		calcSecWtTotal();
	});
	$("[name='secWt']").change(function() {
		calcSecWtTotal();
	});
	calcSecWtTotal();

	$("[name='actualWt']").keyup(function() {
		calcActualWtTotal();
	});
	$("[name='actualWt']").change(function() {
		calcActualWtTotal();
	});
	calcActualWtTotal();

	$("[name='qty']").keyup(function() {
		calcQtyTotal();
	});
	$("[name='qty']").change(function() {
		calcQtyTotal();
	});
	
	
	calcQtyTotal();
}

function calcBeWtTotal() {
	var sum = 0.000;
	$("[name='beWt']").each(function() {
		if (!isNaN(parseFloat($(this).val())))
			sum = sum + parseFloat($(this).val());
	});
	$("#beWtTotal").html(sum.toFixed(3));
}

function calcSecWtTotal() {
	var sum = 0.000;
	$("[name='secWt']").each(function() {
		if (!isNaN(parseFloat($(this).val())))
			sum = sum + parseFloat($(this).val());
	});
	$("#secWtTotal").html(sum.toFixed(3));
}

function calcActualWtTotal() {
	var sum = 0.000;
	$("[name='actualWt']").each(function() {
		if (!isNaN(parseFloat($(this).val())))
			sum = sum + parseFloat($(this).val());
	});
	$("#actualWtTotal").html(sum.toFixed(3));
}

function calcQtyTotal() {
	var sum = 0;
	$("[name='qty']").each(function() {
		if (!isNaN(parseFloat($(this).val())))
			sum = sum + parseInt($(this).val());
	});
	$("#qtyTotal").html(sum.toFixed(0));
}

function calcRptTotal() {
	var sum = 0.000;
	$("[data-name='beWt']").each(function() {
		if (!isNaN(parseFloat($(this).html())))
			sum = sum + parseFloat($(this).html());
	});
	$("#beWtTotal").html(sum.toFixed(3));

	sum = 0.000;
	$("[data-name='secWt']").each(function() {
		if (!isNaN(parseFloat($(this).html())))
			sum = sum + parseFloat($(this).html());
	});
	$("#secWtTotal").html(sum.toFixed(3));

	sum = 0.000;
	$("[data-name='actualWt']").each(function() {
		if (!isNaN(parseFloat($(this).html())))
			sum = sum + parseFloat($(this).html());
	});
	$("#actualWtTotal").html(sum.toFixed(3));

	sum = 0;
	$("[data-name='qty']").each(function() {
		if (!isNaN(parseFloat($(this).html())))
			sum = sum + parseInt($(this).html());
	});
	$("#qtyTotal").html(sum.toFixed(0));
}

function getValByFieldName(container, name) {
	return $.trim($(container + " [name='" + name + "']").val());
}

function btnGroupChange(ele) {
	elem = ele;
	var value = $(ele).find("a")[0].innerHTML;
	text = value + " <span class='caret'></span>";
	var btn = $(ele).parent().prev();
	$(btn).html(text);
	var hidden = $(btn).prev();
	$(hidden).val(value);
}

function rowBtnGroupChange(ele) {
	var value = $(ele).find("a")[0].innerHTML;
	btnGroupChange(ele);
	var trRowContainer = $(ele).parent().parent().parent().parent().parent()
			.next();
	$(trRowContainer).find(".weight-group").each(function() {
		var btn = $(this).find("button")[0];
		text = value + " <span class='caret'></span>";
		$(btn).html(text);
		var hidden = $(btn).prev();
		$(hidden).val(value);
	});
}

function fillArray(arrayName, query) {
	if (!window[arrayName] || !Array.isArray(window[arrayName]))
		window[arrayName] = [];
	if (eval(arrayName).length > 0)
		applyTypeAhead(arrayName);
	else
		$.ajax({
			url : "./xml?query=" + query,
			success : function(xml, textStatus, response) {
				eval(arrayName).length = 0;
				if (null != xml && "" != xml) {
					xmlDoc = $.parseXML(xml);
					$(xmlDoc).find("value").each(function(i, data) {
						eval(arrayName).push(data.innerHTML);
					});
					applyTypeAhead(arrayName);
				}
			},
			error : function() {
				bootbox.alert("Unable to fill Autocomplete for " + arrayName);
			}
		});
}

function applyTypeAhead(arrayName) {
	$("input[name='" + arrayName + "']").autocomplete({
		source : eval(arrayName)
	});
}

function resetForm() {
	$("input[type='text']").val("");
	$("input[type='number']").val("");
	$("select").each(function() {
		$($(this).children("option").get(0)).attr("selected", "selected");
	});
	applyTotalCalc();
}

String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

function getCommaSeperatedList(list) {
	var str = "";
	list.each(function() {
		str = str + $.trim(this.value) + ",";
	});
	if (str.endsWith(","))
		str = str.substr(0, str.length - 1);
	return escape(str);
}

var substringMatcher = function(strs) {
	return function findMatches(q, cb) {
		var matches, substrRegex;
		matches = [];
		substrRegex = new RegExp(q, 'i');
		$.each(strs, function(i, str) {
			if (substrRegex.test(str)) {
				matches.push({
					value : str
				});
			}
		});
		cb(matches);
	};
};

var tableToExcel = (function() {
	var uri = 'data:application/vnd.ms-excel;base64,';
	var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head> <!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets> <x:ExcelWorksheet><x:Name>{worksheet}</x:Name> <x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions> </x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook> </xml><![endif]--></head><body> <table>{table}</table></body></html>';
	var base64 = function(s) {
		return window.btoa(unescape(encodeURIComponent(s)));
	};
	var format = function(s, c) {
		return s.replace(/{(\w+)}/g, function(m, p) {
			return c[p];
		})
	};
	return function(table, name) {
		if (!table.nodeType)
			table = document.getElementById(table);
		var ctx = {
			worksheet : name || 'Worksheet',
			table : table.innerHTML
		};
		window.location.href = uri + base64(format(template, ctx));
	}
})();

var tableToPDF = function() {
	var pdf = new jsPDF('p', 'pt', 'letter');
	source = $('#result-table')[0];
	specialElementHandlers = {
		'#bypassme' : function(element, renderer) {
			return true
		}
	};
	margins = {
		top : 80,
		bottom : 60,
		left : 40,
		width : 800
	};
	pdf.fromHTML(source, margins.left, margins.top, {
		'width' : margins.width,
		'elementHandlers' : specialElementHandlers
	}, function(dispose) {
		pdf.save('Report.pdf');
	}, margins);
}

function StringSet() {
    var setObj = {}, uniqueArr = [];

    this.add = function(str) {
        
    	
    	if(!this.contains(str)){
    		setObj[str] = 1;
    		uniqueArr.push(str);
    		
    	}
        
    };

    this.contains = function(str) {
        return setObj[str] === 1;
    };
    
    this.has = function(str) {
        return this.contains(str);
    };

    this.remove = function(str) {
        delete setObj[str];
    };

    this.values = function() {
        
        return uniqueArr;
    };
}

function showLocationDropdown(stockRecordId){
	console.log(stockRecordId);
	var stockItemLoctionDivId = "#stock-item-location-"+stockRecordId;
	var locationListDropdownDivId = "#stock-item-location-shifting-dropdown-"+stockRecordId
	var remarksInputId = "stock-item-remarks-input-"+stockRecordId;
	var remarksDivId = "#stock-item-remarks-div-"+stockRecordId;
	var remarksTd = "#stock-item-remarks-td-"+stockRecordId;
	
	$(stockItemLoctionDivId).hide();
	//$(remarksDivId).hide();
	
	var locationListDropdownTemplate = $("#locationListDropdownTemplate").html();
	locationListDropdownTemplate = locationListDropdownTemplate.replace("locationListDropdownSelectTemplate","locationListDropdown-"+stockRecordId);
	//var $locationListDropdownTemplate = $(locationListDropdownTemplate).attr("id","newmyid");
	$(locationListDropdownDivId).html(locationListDropdownTemplate);
	$(locationListDropdownDivId).show();
	
	$("#showLocationDropdownBtn-"+stockRecordId).hide();
	$("#changeLocationBtn-"+stockRecordId).show();
	$("#cancelLocationChange-"+stockRecordId).show();
	
	var currentRemarks = $(remarksDivId).html().trim();
	console.log(currentRemarks);
	var remarksInputTag = "<input id='"+remarksInputId+"' />";
	$(remarksTd).html(remarksInputTag);
	$("#"+remarksInputId).val(currentRemarks);
	
}

function cancelLocationChange(stockRecordId){
	
	var stockItemLoctionDivId = "#stock-item-location-"+stockRecordId;
	var locationListDropdownDivId = "#stock-item-location-shifting-dropdown-"+stockRecordId
	$(stockItemLoctionDivId).show();
	$(locationListDropdownDivId).empty();
	$(locationListDropdownDivId).hide();

	
	$("#showLocationDropdownBtn-"+stockRecordId).show();
	$("#changeLocationBtn-"+stockRecordId).hide();
	$("#cancelLocationChange-"+stockRecordId).hide();
}

function saveChangedLocation(stockRecordId){
	
	var newLocation = $("#locationListDropdown-"+stockRecordId).val();
	var remarks = $("#stock-item-remarks-input-"+stockRecordId).val();
	
	
	$.ajax({
		type: "POST",
		url:"./updateStockLocationJsonServlet?stockBalanceDbId="+stockRecordId+"&newStockLocation="+newLocation,
		data:{
			stockBalanceDbId : stockRecordId,
			newStockLocation : newLocation,
			remark : remarks
		},
		success:function(resp){
			handleUpdateLocationSuccess(resp, stockRecordId, newLocation);
		},
		error:function(resp){
			bootbox.alert("Error 3209 : Failed to update the location of stock.");
		}
		
	});
	

}

function handleUpdateLocationSuccess(resp, stockRecordId, newLocation){
	
	var responseJson = JSON.parse(resp);
	console.log(responseJson);
	
	var stockItemLoctionDivId = "#stock-item-location-"+stockRecordId;
	var locationListDropdownDivId = "#stock-item-location-shifting-dropdown-"+stockRecordId
	
	if(responseJson.status == "success"){
		$(stockItemLoctionDivId).html(newLocation);
		
	}else{
		bootbox.alert("Error 2093 : Failed to update the location of stock.");
	}
	
	$(stockItemLoctionDivId).show();
	$(locationListDropdownDivId).empty();
	$(locationListDropdownDivId).hide();

	
	$("#showLocationDropdownBtn-"+stockRecordId).show();
	$("#changeLocationBtn-"+stockRecordId).hide();
	$("#cancelLocationChange-"+stockRecordId).hide();
	
	
	var remarks = $("#stock-item-remarks-input-"+stockRecordId).val();
	var remarksTd = "#stock-item-remarks-td-"+stockRecordId;
	
	var remarksDiv = "<div id='stock-item-remarks-div-"+stockRecordId+"'>"+remarks+"</div>"
	
	$(remarksTd).html(remarksDiv);
	
	
}