//Initialize Select2 Elements
$('.select2').select2();

$(function () {	
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
            {label: 'IC_Number', name: 'icNumber', index: 'icNumber', width: (pageWidth*(10/100)),key:true},
            {label: '供应商名称', name: 'vendorName', index: 'vendorName',width: (pageWidth*(10/100))},
            {label: 'Inv', name: 'vmiInv', index: 'vmiInv',width: (pageWidth*(10/100))},
            {label: '操作人', name: 'fullName', index: 'fullName',width: (pageWidth*(10/100))},
            {label: '操作时间', name: 'createTime', index: 'createTime',width: (pageWidth*(10/100))}
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
            records: "data.totalCount",
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },  
        subGrid:true,
        subGridOptions: {
            plusicon: 'fa fa-plus',
            minusicon: 'fa fa-minus',
            selectOnExpand: false,
            reloadOnExpand: false
        },
        subGridRowExpanded: function(subgrid_id, row_id) {  // (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数  
        	var keyValue = $("#jqGrid").jqGrid('getRowData', row_id)['icNumber'];
        	var subgrid_table_id;  
            subgrid_table_id = subgrid_id + "_t";   // (3)根据subgrid_id定义对应的子表格的table的id  
              
            var subgrid_pager_id;  
            subgrid_pager_id = subgrid_id + "_pgr"  // (4)根据subgrid_id定义对应的子表格的pager的id  
              
            // (5)动态添加子报表的table和pager   
            $("#" + subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"' class='scroll'></div>");  
              
            // (6)创建jqGrid对象  
            $("#" + subgrid_table_id).jqGrid({  
                url: "/inventory/invs/list",  // (7)子表格数据对应的url
                postData: { icNumber: keyValue },
                datatype: "json",  
                colModel: [       	
                  { label: '部件号', name: 'itemNumber', index: 'itemNumber',key: true, width: (pageWidth*(20/100)),
                	  cellattr: function (rowId, val, rawObject, cm, rdata) {
                  		var itemTooltip = 'title="描述:'+rawObject.itemDesc+
                  		'</br>品牌:'+rawObject.itemBrand+
                  		'</br>型号:'+rawObject.itemModel+
                  		'</br>VMI:'+(rawObject.vmi?"是":"否")+
                  		'</br>安全库存数:'+rawObject.safeVmi+
                  		'</br>单位:'+rawObject.itemUnit+
                  		'</br>有效期:'+rawObject.expireDate+'"';
                  		return 'data-toggle="tooltip" data-container="body" data-html="true" data-placement="top"' + itemTooltip;}},
                  { label: 'From_Inv', name: 'fromInv', index: 'fromInv', width: (pageWidth*(10/100))},
                  { label: 'To_Inv', name: 'toInv', index: 'toInv', width: (pageWidth*(10/100)) },
                  { label: '数量', name: 'qty', index: 'qty', width: (pageWidth*(10/100)) },
                  { label: '批号', name: 'lotNumber', index: 'lotNumber', width: (pageWidth*(20/100)) },   
                  { label: '日期', name: 'invTime', index: 'invTime', width: (pageWidth*(10/100)) },
                  { label: '备注', name: 'note', index: 'note', width: (pageWidth*(20/100)) },
                ],  
                autowidth: true,
                jsonReader: {
                    root: "data.list",
                    page: "data.currPage",
                    total: "data.totalPage",
                    records: "data.totalCount",
                }, 
                prmNames: {
                    page: "page",
                    rows: "limit",
                    sort:"sidx",
                    order:"sord"
                },  
                pager: subgrid_pager_id,  
                height: "100%", 
                rowList: [10, 20, 50],
                styleUI: 'Bootstrap',
                loadtext: '信息读取中...',
                rowNum: 10,
                gridComplete: function () {
                    //隐藏grid底部滚动条       	
                	$("#" + subgrid_table_id).closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                	$('[data-toggle="tooltip"]').tooltip();
                }
           });  
        },  
        gridComplete: function () {
            //隐藏grid底部滚动条       	
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});            
            $('[data-toggle="tooltip"]').tooltip();

        }
	})
	
});

function search() {
    var icNumber = $('#icNumber').val();
    var vmiInv = $('#vmiInv').val();
    var startDate=$('#startDate').val();
    var endDate=$('#endDate').val();
    //数据封装
    var searchData = {icNumber: icNumber,toInv: vmiInv,startDate: startDate,endDate: endDate};  
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/inventory/invs/vmilist'}).trigger("reloadGrid");
}