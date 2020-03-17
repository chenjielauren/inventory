//Initialize Select2 Elements
$('.select2').select2();
$(function () {
	var pageWidth = $("#jqGrid").parent().width() - 100;		
    $("#jqGrid").jqGrid({
        url: '/inventory/items/list',
        postData: { state: function () { return $('#state').prop('checked')?-1:1; },
        			vmi: function () { return $('#vmiCheck').prop('checked')?1:-1; }
        		  },
        datatype: "json",
        colModel: [
            {label: '部件号', name: 'itemNumber', index: 'itemNumber', width: (pageWidth*(5/100)), key:true},
            {label: '描述', name: 'itemDesc', index: 'itemDesc', width: (pageWidth*(40/100)),formatter:itemDescFormat},
            {label: '品牌', name: 'itemBrand', index: 'itemBrand', width: (pageWidth*(20/100))},
            {label: '型号', name: 'itemModel', index: 'itemModel', width: (pageWidth*(25/100))},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: (pageWidth*(10/100))}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
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
            sort:"sidx",
            order:"sord"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
   
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    //新增item时 当选中VMI时 显示VMI Vendor
    $('input[name=vmi]').click(function(){ 
    	if($(this).prop('checked')){
    		$('#vendDiv').css("display", "block");
    	}else{
    		$('input[name="vendIds"]').each(function(){
    	    	$(this).prop('checked',false);
    	    });
    		$('#vendDiv').css("display", "none");
    	}
    });
    
    //新增item时 其他天数默认隐藏
    $('input[type=radio]').click(function(){ 
    	if($(this).val() == ""){
    		$('#other').show();
    	}else{
    		$('#other').hide();
    	}
    });

    //查询条件状态点击
    $('#state').click(function(){   
    	if($('#state').prop('checked')){
    		$('#enableBtn').css("display", "block");
    	}else{
    		$('#enableBtn').css("display", "none")
    	}
    	search();    	
    });
})



function itemDescFormat( cellvalue, options, rowObject ){
	var str = (rowObject.vmi)?'<span class="badge badge-warning">vmi</span>':'';
	var state =(rowObject.state==1)?'<span class="badge badge-success">启用</span>':'<span class="badge badge-danger">停用</span>'; 
	return str+state+cellvalue;
}

function stateFormat( cellvalue, options, rowObject ){
	var state =(rowObject.state==1)?'<span class="btn btn-success">启用</span>':'<span class="btn btn-danger">停用</span>'; 
	return state;
}

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function itemAdd() {
    reset();    
    $('.modal-title').html('新增物品');
    $('#itemModal').modal('show');
}

function selectExpireDate(expireDate){
	$('#other').hide();
	if(expireDate == 360){
    	$('input:radio').eq(0).prop('checked',true);
    }else if(expireDate == 720){
    	$('input:radio').eq(1).prop('checked',true);
    }else if(expireDate == 1080){
    	$('input:radio').eq(2).prop('checked',true);
    }else{
    	$('input:radio').eq(3).prop('checked',true);
    	$("#other").val(expireDate);
    	$('#other').show();
    }
}

function selectVmi(vmi,vendIds){
	$("#vmi").prop("checked", vmi);
    if(vmi){
    	//set vendor according to vmi
    	var vendIds = vendIds;
    	$.each(vendIds, function( index, value ) {
    		$(":checkbox[value='"+value+"']").prop('checked', true);
		});	
    	$('#vendDiv').css('display','block');
    }else{
    	$('#vendDiv').css('display','none');
    }
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
	  var form = $("#itemForm");
	  if (form[0].checkValidity() === false) {
		  event.preventDefault();
		  event.stopPropagation();
		  form.addClass('was-validated');
      }else{
		  var itemDesc = $("#itemDesc").val();
		  var itemBrand = $("#itemBrand").val();
		  var itemModel = $("#itemModel").val();	
		  var vmi = $("#vmi").prop('checked');
		  var vendIds = [];
		  if(vmi){
			  $('input[name="vendIds"]:checked').each(function(){
				vendIds.push($(this).val());
			  }); 
		  }
		  var safeVmi = $("#safeVmi").val();
		  var itemUnit = $('#itemUnit').select2('val');
		  var expireDate=$('input[type="radio"]:checked').val();
	      if(expireDate == ""){
	    	  expireDate = $("#other").val();
	    	  $('input:radio').eq(3).val(expireDate);	      	
	      }
	      var params = $("#itemForm").serialize();
	      var url = '/inventory/items/save';
	      var id = getSelectedRowWithoutAlert();        
	      var itemNumber = $("#itemNumber").val(); 
	      if (id != null && itemNumber!='') {
	          url = '/inventory/items/update';
	      }	      
	      $.ajax({
	          type: 'POST',//方法类型
	          url: url,
	          data: params,
	          success: function (result) {
	              if (result.resultCode == 200) {
	              	$('#itemModal').modal('hide');
	              	if(result.data !=null){//新建存在类似的Item
	              		var data = result.data;
	              		var tds = ''; 
	              		$.each( data, function( key, item ) {
	              		  if(itemDesc == item.itemDesc && itemBrand ==item.itemBrand && itemModel == item.itemModel){
	              			  $('#continueAddButton').css('display','none');
	              			  tds +='<tr style="background-color:red;color:white;">';   
	              			  $('.modal-title').html('相同物品');
	              		  }else{
	              			  $('#continueAddButton').css('display','block');
	              			  tds +='<tr>';
	              			  $('.modal-title').html('类似物品');
	              		  }
	              		  tds += '<td>' + item.itemNumber + '</td>';
	            		      tds += '<td>' + item.itemDesc + '</td>';
	            		      tds += '<td>' + item.itemBrand + '</td>';
	            		      tds += '<td>' + item.itemModel + '</td>';
	            		      tds += '<td>' + (item.vmi?"是":"否") + '</td>';
	            		      tds += '<td>' + (item.state?"启用":"禁用") + '</td>';          		                  		      
	            		      tds +='</tr>';
	            		         		    	  
	                      });
	                      $('#showExistItemTable tbody').html(tds);
	                      $('#showExistItemModal').modal('show');
	                      //继续添加按钮点击
	                      $('#continueAddButton').click(function(){	                      	
	                      		$('#showExistItemModal').modal('hide');	                      		
	                          	itemAdd();
	                        	$("#itemDesc").val(itemDesc);
	                        	$("#itemBrand").val(itemBrand);
	                            $("#itemModel").val(itemModel);
	                            $("#safeVmi").val(safeVmi);
	                            $('#itemUnit').select2('val', itemUnit);
	                            //vmi       
	                            selectVmi(vmi,vendIds);
	                            //radio
	                            selectExpireDate(expireDate);
	                            
	                      })
	              	}else{
		                    swal("保存成功", {
		                        icon: "success",
		                    });
		                    reload();
	              	}
	              }
	              else {
	                  $('#itemModal').modal('hide');
	                  swal(result.message, {
	                      icon: "error",
	                  });
	              }
	             
	          },
	          error: function () {
	              swal("操作失败", {
	                  icon: "error",
	              });
	          }
	      });
      }
  
});

function itemEdit() {
    reset();
    var itemNumber = getSelectedRow();
    if (itemNumber == null) {
        return;
    }
    
  //请求数据
  $.ajax({
      type: 'GET',//方法类型
      url: "/inventory/items/edit/" + itemNumber,
      success: function (r) {
		  if (r.resultCode == 200 && r.data != null) {
        	$("#itemNumber").val(r.data.itemNumber);       	
        	$("#itemDesc").val(r.data.itemDesc);
        	$("#itemBrand").val(r.data.itemBrand);
            $("#itemModel").val(r.data.itemModel);
            $("#safeVmi").val(r.data.safeVmi);
            $('#itemUnit').select2('val', r.data.itemUnit);           
            selectVmi(r.data.vmi,r.data.vendIds);
            //radio
            selectExpireDate(r.data.expireDate);          
            //checkbox state为停用时选中
            $("#state").prop("checked", r.data.state?false:true);
            
  		  }
          $('.modal-title').html('修改物品');
    	  $('#itemModal').modal('show');   	  
      },
      error: function () {
          swal("操作失败", {
              icon: "error",
          });
      }
  });
}

function enableItem() {
	var itemNumbers = getSelectedRows();
    if (itemNumbers == null) {
        return;
    }    
    swal({
//        title: "确认启用选中物料吗",
        text: "确认启用选中物料吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
            	 $.ajax({
            	        type: "POST",
            	        url: "/inventory/items/enableItem",
            	        contentType: 'application/json; charset=utf-8',
            	        dataType: 'json',
            	        data:JSON.stringify(itemNumbers),
            	        success: function (r) {
            	            if (r.resultCode == 200) {
            	                swal("启用成功", {
            	                    icon: "success",
            	                });
            	                $("#jqGrid").trigger("reloadGrid");
            	            } else {
            	                swal(r.message, {
            	                    icon: "error",
            	                });
            	            }
            	        }
            	  });
            }
            
        }
    );
}
function disableItem() {
	//弹出该部件号所对应的的库存交易查询记录
    var itemNumbers = getSelectedRows();
    if (itemNumbers == null) {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/inventory/invs/getInvs",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data:JSON.stringify(itemNumbers),
        success: function (r) {
            if (r.resultCode == 200) {
                var data = r.data;
                var tds='';
                var qty=0;
                var disableItemNumbers=[];
                $.each( data, function( key, inv ) {
//            	  console.log( inv.itemNumber + ": " + inv.state + ": " + inv.qty);            	  
            	  tds +='<tr>';
    		      tds += '<td>' + inv.itemNumber + '</td>';
    		      tds += '<td>' + inv.itemDesc + '</td>';
    		      tds += '<td '+(inv.state ?'style="background-color:red;color:white;"':'style="background-color:yellowgreen;color:white;"')+'>' + inv.qty + '</td>';
    		      tds +='</tr>';
    		      if(inv.qty == 0){
    		    	  disableItemNumbers.push(inv.itemNumber);
    		    	  qty++;
    		      }   		    	  
            	});
                $('#showInvTable tbody').html(tds);
                $("#msg").html('共有'+itemNumbers.length+'条记录,其中<strong>'+qty+'</strong>条可停用');
                if(disableItemNumbers.length == 0){
                	$('#disableButton').css("display", "none");
                }else{
                	$('#disableButton').css("display", "block");
                }
                $('.modal-title').html('当前库存数');
                $('#showInvModal').modal('show');
	            $('#disableButton').click(function () {
	            	//直接停用
	            	$.ajax({
	                    type: "POST",
	                    url: "/inventory/items/disableItem",
	                    contentType: 'application/json; charset=utf-8',
	                    dataType: 'json',
	                    data:JSON.stringify(disableItemNumbers),
	                    success: function (r) {
	                        if (r.resultCode == 200) {
	                            swal("停用成功", {
	                                icon: "success",
	                            });
	                            $('#showInvModal').modal('hide');
	                            $("#jqGrid").trigger("reloadGrid");
	                        } else {
	                            swal(r.message, {
	                                icon: "error",
	                            });
	                        }
	                    }
	                });	            	
	            });
                
            } else {
                swal(r.message, {
                    icon: "error",
                });
            }
        }
  });
}


function reset() {
    $("#itemNumber").val('');
	$("#itemDesc").val('');
	$("#itemBrand").val('');
    $("#itemModel").val('');  
    $('#safeVmi').val(0); 
    $("#vmi").prop('checked',false);
    $("#vendDiv").css('display','none');
    $('input[name="vendIds"]').each(function(){
    	$(this).prop('checked',false);
    });
    $('#itemUnit').select2('val', '件'); 
    $('input[type=radio]:first').prop('checked',true);
    $('#other').val('');
    $('#other').hide();
    $('#edit-error-msg').css("display", "none");
    $('#itemForm').removeClass('was-validated');
    
}

/**
 * 搜索功能
 */
function search() {
    //标题关键字
    var keyword = $('#keyword').val();
    if (!validLength(keyword, 20)) {
        swal("搜索字段长度过大!", {
            icon: "error",
        });
        return false;
    }
    var state = $('#state').prop('checked')?-1:1;
    var vmi = $('#vmiCheck').prop('checked')?1:-1;    
    //数据封装
    var searchData = {keyword: keyword,state: state,vmi: vmi};
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/inventory/items/list'}).trigger("reloadGrid");
}