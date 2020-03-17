//Initialize Select2 Elements
$('.select2').select2();
var postdata = [],
	mpRowNum = 1,
	itemArr = [];//Item联合显示字符串
$(function () {
	var pageWidth = $("#mpGrid").parent().width() - 100;
	$("#mpGrid").jqGrid({
        url: '/inventory/invs/mplist',
        postData: { state: function () { return 0; }
        		  },
        datatype: "json",
        colModel: [
        	{label: 'id', name: 'id', index: 'id', hidden: true},
            {label: '预约号', name: 'mpNumber', index: 'mpNumber', width: (pageWidth*(10/100)), key:true},
            {label: '预约人', name: 'fullName', index: 'fullName', width: (pageWidth*(10/100))},
            {label: '预约数', name: 'qty', index: 'qty', width: (pageWidth*(10/100))},
            {label: '预约时间', name: 'mpTime', index: 'mpTime', width: (pageWidth*(20/100))},
            {label: '操作', name: 'action', index: 'action', width: (pageWidth*(20/100)),formatter:actionFormat},
            {label: '状态', name: 'state', index: 'state', hidden: true},
            {label: '登录用户', name: 'loginUser', index: 'loginUser', hidden: true},
        ],
        height: 450,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        pager: "#mpGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            sort:"sidx",
            order:"sord"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#mpGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
	
	//添加领料计划
	$('#tab a[href="#newMp"]').on('click', function (e) {
	  e.preventDefault();
	  addNewMp();
	  $(this).tab('show');	 	  
	})
	
	showItemNumber();
	
	$('#mpTime').datetimepicker({
		format: 'YYYY-MM-DD HH:mm:ss',
		minDate: moment(),//Current day 
		maxDate:moment().add(14,'days') // Current day add 14 daysS
	});
	$('#mpTime').val(moment().add(1,'days').format("YYYY-MM-DD HH:mm:ss"));
	
	$("#mpModal").on('hidden.bs.modal', function(){
		$("#table tbody").empty();
	});
	
});

function showItemNumber(){
	//show item no
	$('#itemNumber').inputpicker({
		width:"400px",
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
	itemArr = generateItem();//显示联合字符串
	//change itemNumber
	$('#itemNumber').change(function(input){
		var itemNumber = $(this).val();
		if($(this).val()!=""){//itemNumber值不为空
			//get 当前库存数
		    $.ajax({
	    	  type: "GET",	
	          url: "/inventory/mp/getMpQty?itemNumber="+itemNumber,
	          contentType: 'application/json; charset=utf-8',
	          dataType: 'json',
		      success: function (r) {
				  if (r.resultCode == 200 && r.data != null) {	
					  var d = r.data,
					  	  currentQty = d.currentQty!=null?parseInt(d.currentQty):0,//当前库存
					  	  existQty = d.existQty!=null?parseInt(d.existQty):0,//已预约数
					  	  minusQty = (currentQty - existQty),//相减
					  	  $row = $("#showQty tbody tr:eq(0)"),
					  	  mpList = d.mpVoList;				  
					  $row.find("td:eq(0)").html('<label class="control-label">'+currentQty+'</label>');
					  if(existQty !=0){
						  var html = showMyMpTable(mpList);
						  $row.find("td:eq(2)").empty().html('<span class="spnDetails"><label class="control-label">'+existQty+'</label></span>'
								  +'<div class="spnTooltip">'+html+'</div>');
						 
					  }else{
						  $row.find("td:eq(2)").empty().html('<label class="control-label">'+existQty+'</label>');// Hide and destroy tooltips
					  }
					  console.log(minusQty);
					  $('#minusTd').html('<label class="control-label">'+minusQty+'</label>')
//					  $row.find("td:eq(4)").html();
					  $('#qty').val('');
				  }else{
					  $row.find("td:eq(0) label").html('');
					  $row.find("td:eq(2) label").html('');
					  $('#minusTd label').html(''); 	
				  }			  
		      },
		      error: function () {
		          swal("操作失败", {
		              icon: "error",
		          });
		      }
		    });		
		}
	})
}

function actionFormat(cellvalue, options, rowObject) {
    var mpNumber= rowObject.mpNumber;
    var btns = "<button type=\"button\" class=\"btn btn-sm btn-default\" title=\"查看预约\"  onclick=\"viewMp(this)\" style=\"width: 15%;margin-top: 10px;\" value=\""+mpNumber+"\"><span class=\"fa fa-bars\"></span></button>&nbsp;&nbsp;&nbsp;&nbsp;";	
	btns += "<button type=\"button\" class=\"btn btn-sm btn-default\"  title=\"修改预约\"   onclick=\"editMp(this)\" style=\"width: 15%;margin-top: 10px;\" value=\""+mpNumber+"\"><span class=\"fa fa-edit\"></span></button>"	
    return btns;
}

//0:禁用按钮;1:启用按钮
function showorHideBtn(value){
	$('#mpTime').val(moment().add(1,'days').format("YYYY-MM-DD HH:mm:ss"));
	if(value==0){
		$('.modal-body').find(".grid-btn").each(function (i) {
			$(this).hide();
		});
		$("#table thead tr th:eq(0)").hide();//显示checkbox
	}else{
		$('.modal-body').find(".grid-btn").each(function (i) {
			$(this).show();
		});
		$("#table thead tr th:eq(0)").show();
	}
}
//新增预约
function addNewMp(){
	clearQtyData();
	showorHideBtn(1);
	var btns='<button type="button" class="btn btn-primary ml-auto" id="saveButton" onclick="submit()">确认</button>';
	$('.modal-title').html('新增预约');	
	$('.modal-footer').empty().html(btns);
	$('#mpModal').modal('show');	
}

//添加领料
function addRow() {	
	var itemNumber = $('#itemNumber').val();
	if(!itemNumber){
		swal("请选择部件号", {
            icon: "error",
        });
		return;
	}
	var minusQty = parseInt($("#minusTd label").html());
	var qty = $('#qty').val()!=""?parseInt($('#qty').val()):null;
	if(!qty){
		swal("请输入预约数", {
            icon: "error",
        });
		return;
	}
	if(qty<0){
		swal("预约数必须大于0", {
            icon: "error",
        });
		return;
	}
	if(qty>minusQty){
		swal("预约数不合法", {
            icon: "error",
        });
		return;
	}
	var length = $('#table tr > td[name="itemNumber"]:contains('+itemNumber+')').length;
	if(length > 0){
		swal("已存在相同部件号", {
            icon: "error",
        });
		return;
	}
	//add row
	var tds='<tr>';
	tds+='<td><input type="checkbox" name="selectRow"></td>';	
	tds+='<td name="itemNumber" style="vertical-align:middle">'+itemNumber+'</td>';
	tds+='<td name="itemDesc" style="vertical-align:middle">'+itemArr[itemNumber].itemDesc+'</td>';
	tds+='<td name="itemBrand" style="vertical-align:middle">'+itemArr[itemNumber].itemBrand+'</td>';
	tds+='<td name="itemModel" style="vertical-align:middle">'+itemArr[itemNumber].itemModel+'</td>';
	tds+='<td><input type="number" class="form-control"  name="qty" placeholder="请输入预约数" value="'+qty+'" max="'+minusQty+'"  onblur="requiredField(this)" data-toggle="tooltip" title="预约数最大值:'+minusQty+'"></td>';
	tds+='<td><input type="text" class="form-control"  name="note" maxlength="100" placeholder="请输入备注" ></td></tr>';
	$("#table tbody").append(tds);	
	$('[data-toggle="tooltip"]').tooltip({
		 placement: "right",
		 trigger: "focus"
	});
	clearQtyData();
}

//clear data
function clearQtyData(){
	//clear data after add row
	$('#itemNumber').val('').trigger('change');
	var $row = $("#showQty tbody tr:eq(0)");
	$row.find("td:eq(0) label").remove();
	$row.find("td:eq(2) label").remove();
	$('#minusTd label').remove();
	$('#qty').val('');
}
//验证必输字段
function requiredField(input) {
	if (input.name == 'qty') {//qty can't be zero  
		var max = parseInt(input.max);
		var qty = parseInt(input.value);
		if(qty <=0){
			//green border    	
			$(input).css("border-color","#e74c3c");
		    $(input).attr('title',"预约值必须大于0");
		    $(input).focus();
		}
		if(qty > max){
			//green border    	
			$(input).css("border-color","#e74c3c");
		    $(input).attr('title',"预约数最大值为"+max+"");
		    $(input).focus();
		}else{
			//green border    	
	        $(input).css("border-color","#2ecc71");
		    $(input).attr('title',"");
		}
	}
}
//删除领料
function removeRow() {
	$('#table tbody tr').each(function (i, row) {
	  	  //get value
	      var $row = $(row),
	      selectRow = $row.find('input[name="selectRow"]').prop("checked");
	      if(selectRow){
	    	  $row.remove();
	      }		      
	});
}
//提交物料
function submit(){
	//保存
	var postdata = [];
	var mpNumber = $("#cancelBtn").val();
	//遍历
	$('#table tbody tr').each(function (i, row) {
	      var $row = $(row),
	      itemNumber = $row.find('td[name="itemNumber"]').text(),
	      id = $row.find('td[name="id"]').text(),
	      createUser = $row.find('td[name="createUser"]').text(),
	      createTime = $row.find('td[name="createTime"]').text(),
	      state = $row.find('td[name="state"]').text(),
	      qty = $row.find('input[name="qty"]').val(),
	      mpTime = $('#mpTime').val(),
	      note = $row.find('input[name="note"]').val();
	    //posdataList
	      var obj={};
	      if(mpNumber!=null && id!=null){
	    	  obj["id"] = id;
	    	  obj["mpNumber"] = mpNumber;
	    	  obj["createUser"] = createUser;
	    	  obj["createTime"] = createTime;
	    	  obj["state"] = state;
	      }
	      obj["itemNumber"]=itemNumber;	      		      
	      obj["qty"]=qty;
	      obj["mpTime"]=mpTime;
	      obj["note"]=note;
	      postdata.push(obj);
	})
	var data ={"mpNumber":mpNumber,"mpList":postdata};
	if(null!=postdata && postdata.length > 0){
		$.ajax({
		    type: 'POST',//方法类型
		    url: "/inventory/mp/save",
		    contentType: 'application/json; charset=utf-8',
	        dataType: 'json',
		    data: JSON.stringify(data),
		    success: function (result) {
		        if (result.resultCode == 200) {
		            swal("保存成功", {
		                icon: "success",
		            });
		            clearQtyData();	
		            //更新预约列表
		            reload(0);
		            $('#mpModal').modal('hide');
					$("#table tbody").empty();
		        }
		        else {
		            swal(result.message, {
		                icon: "error",
		            });
		        };
		    },
		    error: function () {
		        swal("操作失败", {
		            icon: "error",
		        });
		    }
		}); 
	}else{
		swal("请添加领料计划", {
            icon: "error",
        });
	}	 
}
//展示我的领料
function showMyMpTable(data){
	var tds ='<table style="border-collapse:separate; border-spacing:5px 5px;">'+'<thead>'+
			'<tr style="text-align: left"><th>预约号</th>'+
	  		'<th>预约人</th>'+
	  		'<th>预约数量</th>'+
	  		'<th>预约时间</th>'+
	  	'</tr></thead><tbody>';
	for(var i=0;i<data.length;i++){
		tds+='<tr  style="text-align: left">'+
			'<td>'+data[i].mpNumber+'</td>' +
			'<td>'+data[i].createUser+'</td>' +
			'<td>'+data[i].qty+'</td>' +
			'<td>'+data[i].mpTime+'</td></tr>';

	}
	tds+='</tbody></table>';
	return tds;
}


function showMyMp(data){
	var tds = '';
	for(var i=0;i<data.length;i++){      
		tds += '<tr><td>'+data[i].mpNumber+'</td><td>'+data[i].qty+'</td><td>'+data[i].mpTime+'</td>'+
		'<td><button id="bView" type="button" class="btn btn-sm btn-default" onclick="viewMp(this)" value="'+data[i].mpNumber+'"><span class="fa fa-bars"></span></button>'+
		'<button id="bEdit" type="button" class="btn btn-sm btn-default" onclick="editMp(this)"  value="'+data[i].mpNumber+'"><span class="fa fa-edit"></span></button></td></tr>';// text="'+data[i].mpNumber+'('+data[i].qty+')'+'"
	}
	$("#showMyMpTable tbody").empty().append(tds);
}
//查看领料计划
function viewMp(object){
	var mpNumber = object.value;
	$.get("/inventory/inv/" + mpNumber, function (r) {
		if (r.resultCode == 200 && r.data != null) {
			var data = r.data,
			tds = '';
			//禁用按钮
			showorHideBtn(0);
			$('#mpTime').val(moment(data[0].mpTime).format("YYYY-MM-DD HH:mm:ss"));
			for(var i=0;i<data.length;i++){
				var itemNumber = data[i].itemNumber;
				//add row
				tds+='<tr>';
				tds+='<td name="itemNumber" style="vertical-align:middle">'+itemNumber+'</td>';
				tds+='<td name="itemDesc" style="vertical-align:middle">'+itemArr[itemNumber].itemDesc+'</td>';
				tds+='<td name="itemBrand" style="vertical-align:middle">'+itemArr[itemNumber].itemBrand+'</td>';
				tds+='<td name="itemModel" style="vertical-align:middle">'+itemArr[itemNumber].itemModel+'</td>';
				tds+='<td>'+data[i].qty+'</td>';
				tds+='<td>'+data[i].note+'</td></tr>';
			}
			$("#table tbody").empty().append(tds);
			$('.modal-title').html('查看预约(预约号:'+mpNumber+')');
			var btns='<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';
			$('.modal-footer').empty().append(btns);
			$('#mpModal').modal('show');
		}
	});
	
}
//更新领料计划
function editMp(object){
	var mpNumber = object.value;
	$.get("/inventory/inv/" + mpNumber, function (r) {
		if (r.resultCode == 200 && r.data != null) {
			var data = r.data,
				tds = '';
			//启用按钮
			showorHideBtn(1);
			$('#mpTime').val(moment(data[0].mpTime).format("YYYY-MM-DD HH:mm:ss"));
			for(var i=0;i<data.length;i++){
				var itemNumber = data[i].itemNumber;
				//add row
				tds+='<tr>';
				tds+='<td name="id" style="display:none">'+data[i].id+'</td>';
				tds+='<td name="createUser" style="display:none">'+data[i].createUser+'</td>';
				tds+='<td name="createTime" style="display:none">'+data[i].createTime+'</td>';
				tds+='<td name="state" style="display:none">'+data[i].state+'</td>';
				tds+='<td><input type="checkbox" name="selectRow"></td>';
				tds+='<td name="itemNumber" style="vertical-align:middle">'+itemNumber+'</td>';
				tds+='<td name="itemDesc" style="vertical-align:middle">'+itemArr[itemNumber].itemDesc+'</td>';
				tds+='<td name="itemBrand" style="vertical-align:middle">'+itemArr[itemNumber].itemBrand+'</td>';
				tds+='<td name="itemModel" style="vertical-align:middle">'+itemArr[itemNumber].itemModel+'</td>';
				tds+='<td><input type="number" class="form-control"  name="qty" placeholder="请输入预约数" value="'+data[i].qty+'" max="'+data[i].minusQty+'" onblur="requiredField(this)" data-toggle="tooltip" title="预约数最大值:'+data[i].minusQty+'" ></td>';
				tds+='<td><input type="text" class="form-control"  name="note" maxlength="100" placeholder="请输入备注" value="'+data[i].note+'"></td></tr>';
			}
			$("#table tbody").empty().append(tds);	
			$('[data-toggle="tooltip"]').tooltip({
				 placement: "right",
				 trigger: "focus"
			});
		}
	});
	$('.modal-title').html('修改预约(预约号:'+mpNumber+')');	
	var btns='<button type="button" class="btn btn-warning mr-auto" onclick="cancelMp(this)" id="cancelBtn" value="'+mpNumber+'">取消预约</button>'+
	'<button type="button" class="btn btn-success" onclick="submit()">更新</button>';	
	$('.modal-footer').empty().html(btns);
	clearQtyData();
	$('#mpModal').modal('show');
}

//取消预约
function cancelMp(object){
	var mpNumber = object.value;
	$.post("/inventory/inv/cancelMp/" + mpNumber, function (r) {
		if (r.resultCode == 200 && r.data != null) {
			swal("取消预约成功", {
                icon: "success",
            });
			//刷新列表
			reload(0);
			$('#mpModal').modal('hide');
			//回到新增界面
		}
	});
}

/**
 * value:0 未完成的预约
 * value:1 已完成的预约
 * value:2 已取消的预约
 */
function reload(value) {	
	var searchData = {state: value};
	if(value == 0){
		$("#mpGrid").jqGrid("setGridParam", {postData: searchData});
	    $("#mpGrid").jqGrid("setGridParam", {page: 1});
	    $("#mpGrid").jqGrid("setGridParam", {url: '/inventory/invs/mplist'}).trigger("reloadGrid");
	}
	
    
}