//Initialize Select2 Elements
$('.select2').select2();
var invCodes = {};	
var locs = {};
$(function () {
	//get invCodes
	//1:RI;2:VI;3:MI	
	$("#fromInv > option").each(function() {
		invCodes[this.value]=this.text;
	});
	//get locations
	//1:A;2:B;3:C	
	$("#fromLoc > option").each(function() {
		locs[this.value]=this.text;
	});
	//startDate first day in current month
	$('#startDate').datetimepicker({
		format: 'YYYY-MM-DD HH:mm:ss'		
	});	
	$('#startDate').val(moment().startOf('month').format('YYYY-MM-DD HH:mm:ss'));
	$('#endDate').datetimepicker({
		format: 'YYYY-MM-DD HH:mm:ss'		
	});
	$('#endDate').val(moment().format('YYYY-MM-DD HH:mm:ss'));
	//endDate current day 
	$("#jqGrid").jqGrid({
        datatype: "json",
        colModel: [
            {label: '部件号', name: 'itemNumber', index: 'itemNumber', width: 50},
            {label: 'From_Inv', name: 'fromInv', index: 'fromInv',formatter:invFmatter, width: 50},
            {label: 'To_Inv', name: 'toInv', index: 'toInv',formatter:invFmatter,width: 50},
            {label: 'From_Loc', name: 'fromLoc', index: 'fromLoc',formatter:locFmatter,width: 50},
            {label: 'To_Loc', name: 'toLoc', index: 'toLoc',formatter:locFmatter, width: 50},
            {label: '数量', name: 'qty', index: 'qty', width: 50},
            {label: '批号', name: 'lotNumber', index: 'lotNumber', width: 50},
            {label: '日期', name: 'invTime', index: 'invTime', width: 120}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
	        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
//            alert($("#jqGridPager").find("span"));
        }
	});
//	alert($("#jqGridPager").find("span").css());
//	$("#first_jqGridPager").find("span").css("class","fa fa-step-backward");
//	$("#prev_jqGridPager").find("span").css("class","fa fa-backward");
//	$("#next_jqGridPager").find("span").css("class","fa fa-forward");
//	$("#last_jqGridPager").find("span").css("class","fa fa-step-forward");
	
});
function invFmatter (cellvalue, options, rowObject)
{
	for (var key in invCodes) {
	    if (key == cellvalue) {
	        return invCodes[key];
	    }
	} 
}

function locFmatter (cellvalue, options, rowObject)
{
	for (var key in locs) {
	    if (key == cellvalue) {
	        return locs[key];
	    }
	} 
}
function search() {
	var itemNumber = $('#itemNumber').val();
    var fromInv = $('#fromInv').val();
    var toInv = $('#toInv').val();
    var fromLoc = $('#fromLoc').val();
    var toLoc = $('#toLoc').val();
    var startDate=$('#startDate').val();
    var endDate=$('#endDate').val();
    //数据封装
    var searchData = {itemNumber: itemNumber,fromInv: fromInv,toInv: toInv,fromLoc: fromLoc,toLoc: toLoc,startDate: startDate,endDate: endDate};
   
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/inventory/invs/list'}).trigger("reloadGrid");
}