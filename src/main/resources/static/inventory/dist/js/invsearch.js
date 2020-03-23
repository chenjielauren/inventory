//Initialize Select2 Elements
$('.select2').select2();

$(function () {
	//show item no
	$('#itemNumber').inputpicker({
		width:"500px",
		data:JSON.parse($("#itemList").val()),
	    fields:[
	        {name:'itemNumber',text:'部件号'},
	        {name:'itemDesc',text:'描述'},
	        {name:'itemBrand',text:'品牌'},
	        {name:'itemModel',text:'型号'}
        ],
	    fieldText:'itemNumber',
	    fieldValue:'itemNumber',
	    multiple: true,
	    autoOpen: true,
	    headShow:true,
	    filterOpen :true,
	    filterType:'start', 
	    filterField:['itemNumber', 'itemDesc','itemBrand', 'itemModel']
	});	
	//startDate first day in current month
	$('#startDate').datetimepicker({
		format: 'YYYY-MM-DD'		
	});	
	$('#startDate').val(moment().startOf('month').format('YYYY-MM-DD'));
	$('#endDate').datetimepicker({
		format: 'YYYY-MM-DD'		
	});
	$('#endDate').val(moment().format('YYYY-MM-DD'));
	
	var pageWidth = $("#jqGrid").parent().width() - 100;
	//endDate current day 
	$("#jqGrid").jqGrid({
        datatype: "json",
        colModel: [
            {label: '部件号', name: 'itemNumber', index: 'itemNumber', width: (pageWidth*(5/100)),key:true,
            	cellattr: function (rowId, val, rawObject, cm, rdata) {
            		var itemTooltip = 'title="描述:'+rawObject.itemDesc+
            		'</br>品牌:'+rawObject.itemBrand+
            		'</br>型号:'+rawObject.itemModel+
            		'</br>VMI:'+(rawObject.vmi?"是":"否")+
            		'</br>安全库存数:'+rawObject.safeVmi+
            		'</br>单位:'+rawObject.itemUnit+
            		'</br>有效期:'+rawObject.expireDate+'"';
            		return 'data-toggle="tooltip" data-container="body" data-html="true" data-placement="bottom"' + itemTooltip;}},
            {label: 'From_Inv', name: 'fromInv', index: 'fromInv', width: (pageWidth*(10/100))},
            {label: 'To_Inv', name: 'toInv', index: 'toInv',width: (pageWidth*(10/100))},
            {label: 'From_Loc', name: 'fromLoc', index: 'fromLoc',width: (pageWidth*(10/100))},
            {label: 'To_Loc', name: 'toLoc', index: 'toLoc', width: (pageWidth*(10/100))},
            {label: '数量', name: 'qty', index: 'qty', width: (pageWidth*(5/100))},
            {label: '批号', name: 'lotNumber', index: 'lotNumber', width: (pageWidth*(8/100))},
            {label: '交易人', name: 'fullName', index: 'fullName', width: (pageWidth*(10/100))},
            {label: '交易日期', name: 'invTime', index: 'invTime', width: (pageWidth*(8/100))},           
            {label: '创建时间', name: 'createTime', index: 'createTime', width: (pageWidth*(10/100))}
        ],
        height: 520,
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
            $('[data-toggle="tooltip"]').tooltip();

        }
	})	
	
});

function search() {
	var itemNumbers = $('#itemNumber').val();
    var fromInv = $('#fromInv').val();
    var toInv = $('#toInv').val();
    var fromLoc = $('#fromLoc').val();
    var toLoc = $('#toLoc').val();
    var startDate=$('#startDate').val();
    var endDate=$('#endDate').val();
    //数据封装
    var searchData = {itemNumbers: itemNumbers,fromInv: fromInv,toInv: toInv,fromLoc: fromLoc,toLoc: toLoc,startDate: startDate,endDate: endDate,vmi:0};
   
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/inventory/invs/list'}).trigger("reloadGrid");
}