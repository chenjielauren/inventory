//Initialize Select2 Elements
$('.select2').select2();
$(function () {
    $("#jqGrid").jqGrid({
        url: '/inventory/items/list',
        datatype: "json",
        colModel: [
            {label: 'itemNumber', name: 'itemNumber', index: 'itemNumber', width: 50, key: true, hidden: true},
            {label: '描述', name: 'itemDesc', index: 'itemDesc', width: 50},
            {label: '品牌', name: 'itemBrand', index: 'itemBrand', width: 50},
            {label: '型号', name: 'itemModel', index: 'itemModel', width: 50},
            {label: '供应商', name: 'vendor', index: 'vendor', width: 50},
            {label: '供应商_PN', name: 'vendorPn', index: 'vendorPn', width: 50},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 120}
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
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
   
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    $('input[type=radio]').click(function(){    	
    	if($(this).val().indexOf('others')!=-1){
    		$('#other').prop('disabled',false);
    		$('#other').show();
    	}else{
    		$('#other').prop('disabled',true);  
    		$('#other').hide();
    	}
    })

});


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
    $('.modal-title').html('物品添加');
    $('#itemModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var itemDesc = $("#itemDesc").val();
    if (!validCN_ENString2_18(itemDesc)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的物品描述！");
    } else {
       if(('input[type=radio]:last').checked)
    	   ('input[type=radio]:last').attr('value',$("#other").val());
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
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                }
                else {
                    $('#itemModal').modal('hide');
                    swal(result.message, {
                        icon: "error",
                    });
                }
                ;
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
            $('#vendor').select2('val', r.data.vendor);
            $('#vendorPn').select2('val', r.data.vendorPn);
            $("#safeVmi").val(r.data.safeVmi);
            $("#itemUnit").val(r.data.itemUnit);
            //checkbox
            if (r.data.vmi == 1) {
                $("#vmi").prop("checked", true);
            }else{
            	$("#vmi").prop("checked", false);
            }
            //radio
            $('input[type=radio]').each(function(){
            	var value = $(this).attr('value');
            	var expireDate = r.data.expireDate; 
            	if(expireDate.indexOf(value)!=-1){
            		$(this).prop('checked',true);
            		//当用户选择其他时显示
            		if(expireDate.indexOf('others')!=-1){
            			expireDate = expireDate.substr(expireDate.indexOf(",") + 1);
            			$("#other").val(expireDate);
            			$("#other").prop('disabled',false);
            		}else{
            			$('#other').prop('disabled',true);  
                		$('#other').hide();
            		}
            	}
            	
            })
  		  }
          $('.modal-title').html('物品编辑');
    	  $('#itemModal').modal('show');   	  
      },
      error: function () {
          swal("操作失败", {
              icon: "error",
          });
      }
  });
}

function deleteItem() {
    var itemNumbers = getSelectedRows();
    if (itemNumbers == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
            	$.ajax({
                  type: "POST",
                  url: "/inventory/items/delete",
                  contentType: 'application/json; charset=utf-8',
                  dataType: 'json',
                  data:JSON.stringify(itemNumbers),
                  success: function (r) {
                      if (r.resultCode == 200) {
                          swal("删除成功", {
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


function reset() {
    $("#itemNumber").val('');
	$("#itemDesc").val('');
	$("#itemBrand").val('');
    $("#itemModel").val('');
    $('#vendor').select2('val', '');
    $('#vendorPn').select2('val', '');    
    $("#vmi").prop('checked',false);
    $("#safeVmi").val('');
    $("#itemUnit").val('');
    $('#other').val('');
    $('input[type=radio]:first').prop('checked',true);
    $('#edit-error-msg').css("display", "none");
}