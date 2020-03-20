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
	    autoOpen: true,
	    headShow:true,
	    filterOpen :true,
	    filterType:'start', 
	    filterField:['itemNumber', 'itemDesc','itemBrand', 'itemModel']
	});	
	
	var pageWidth = $("#jqGrid").parent().width() - 100;
	//endDate current day 
	$("#jqGrid").jqGrid({
        datatype: "json",
        colModel: [
            {label: '部件号', name: 'itemNumber', index: 'itemNumber', width: (pageWidth*(5/100)),key:true},
            {label: '描述', name: 'itemDesc', index: 'itemDesc', width: (pageWidth*(10/100)),key:true},
            {label: '品牌', name: 'itemBrand', index: 'itemBrand', width: (pageWidth*(10/100)),key:true},
            {label: '型号', name: 'itemModel', index: 'itemModel', width: (pageWidth*(10/100)),key:true},
            {label: '库(INV)', name: 'invName', index: 'invName', width: (pageWidth*(10/100))},
            {label: '库位(LOC)', name: 'locName', index: 'locName',width: (pageWidth*(10/100))},
            {label: '数量', name: 'qty', index: 'qty',width: (pageWidth*(5/100))},
            {label: '单位', name: 'itemUnit', index: 'itemUnit', width: (pageWidth*(10/100))},
            {label: '已预约数', name: 'mpQty', index: 'mpQty', width: (pageWidth*(10/100))},
            {label: '可用数', name: 'allowQty', index: 'allowQty', width: (pageWidth*(10/100))},
            {label: '安全库存', name: 'safeVmi', index: 'safeVmi', width: (pageWidth*(10/100))}
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
            $('[data-toggle="tooltip"]').tooltip();

        }
	})	
	
});

function search() {
	var itemNumber = $('#itemNumber').val();
    var inv = $('#inv').val();
    var loc = $('#loc').val();
    //数据封装
    var searchData = {itemNumber: itemNumber,inv: inv,loc: loc,vmi:1};
   
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/inventory/invs/invqtylist'}).trigger("reloadGrid");
}