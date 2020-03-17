//Initialize Select2 Elements
$('.select2').select2();
var postdata = [],//Item联合显示字符串
 invRowNum = 1,
 itemUnionArr = []
 vmiInv = [];//显示部件号/描述/型号

$(function () {	
//	$('#vmiInv').select2('val','');
	$('#vmiInv').val($('.select2 option:eq(0)').val());
	changeVmi();//选中供应商下的部件号
	//addRow
	$("#addRow").click(function() {
		$('#vmiInv').prop('disabled', true);
		itemUnionArr = generateItemUnionArr();
		//添加行时验证数据
		if(invRowNum ==1 || validateRow($('#tr'+(invRowNum-1)+''))){
			
			//add row
			var tds='<tr id="tr'+invRowNum+'">';
			//相同部件号累加库存数
			tds+='<td><input type="checkbox" name="selectRow"></td>';				
			tds+='<td><input type="text" class="form-control" id="itemNumber'+invRowNum+'"  name="itemNumber"  placeholder="请输入部件号" ></td>';			
			//显示部件号/描述/型号
			tds+='<td id="unionItem'+invRowNum+'" name="unionItem" style="vertical-align:middle"></td>';
			//显示当前库存数
			tds+='<td id="currentQty'+invRowNum+'" name="currentQty" style="vertical-align:middle"></td>';
			tds+='<td><input type="number" class="form-control"  name="qty" maxlength="6" placeholder="请输入入库数"  onblur="requiredField(this)" disabled></td>';
			tds+='<td><input type="text" class="form-control"  name="lotNumber" maxlength="100" placeholder="请输入批号"  disabled></td>';
			tds+='<td><div class="col-sm-12"><input type="text" disabled onblur="requiredField(this)" class="form-control datetimepicker-input" name="invTime" id="invTime'+invRowNum+'" placeholder="请输入入库日期" data-toggle="datetimepicker" data-target="#invTime'+invRowNum+'"></div></td>';
			tds+='<td><input type="text" class="form-control"  name="note" maxlength="100" placeholder="请输入备注" disabled></td>';
			tds+='</tr>';					
			$("#table tbody").append(tds);
			
			//show item no
			$('#itemNumber'+invRowNum).inputpicker({
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
			    filterField:['itemNumber', 'itemDesc','itemBrand', 'itemModel'],
				width:"350px"
			});	
			//选择部件号 生成当前库存数
			$('#itemNumber'+invRowNum).change(function(input){
				var $row =  $(this),
					itemNumber = $row.val(),
					vmiInv = $('#vmiInv').val().split(",");
				var unionItem = itemUnionArr[itemNumber];
				$row.closest('tr').find('td[name="unionItem"]').html(unionItem);	
				
				var productFilter = {
					"vmiInv" : vmiInv[1],
				    "itemNumber" : itemNumber
				}
				//get 当前库存数
			    $.ajax({
		    	  type: "POST",	
    	          url: "/inventory/vmi/getCurrentQty",
    	          contentType: 'application/json; charset=utf-8',
    	          dataType: 'json',
    	          data:JSON.stringify(productFilter),
			      success: function (r) {
					  if (r.resultCode == 200 && r.data != null) {						  
						  var currentQty = parseInt(r.data),
						  		qty = parseInt($row.closest("tr").find('input[name="qty"]').val());	  
						  $row.closest('tr').find('td[name="currentQty"]').html(currentQty);	
						  $('#inputpicker-'+(invRowNum-1)).css("border-color","#2ecc71");//设置inputpicker边框颜色						  					  
					  }else{
						  $("#table tbody td:eq(2)").html('');
						  $("#table tbody td:eq(3)").html('');	
					  }
					  $("#table tbody td").find("input").each(function(){
						 $(this).prop('disabled', false);
					  });
			      },
			      error: function () {
			          swal("操作失败", {
			              icon: "error",
			          });
			      }
			    });					
			})	
			$('#invTime'+invRowNum).datetimepicker({
				format: 'YYYY-MM-DD',
				minDate: moment().subtract(7, 'days').calendar(),//Current day minus 7 days 
				maxDate:moment() // Current day
			});
			if(invRowNum == 1){
				//第一行
				$('#invTime'+invRowNum).val(moment().format("YYYY-MM-DD"));
			}else{
				//第二行默认上一行数据,以此类推
				var datetime = $('#invTime'+invRowNum).closest("tr").prev().find("input[name='invTime']").val();
				if(!datetime)
					datetime = moment().format("YYYY-MM-DD");
				$('#invTime'+invRowNum).val(datetime);
			}
			invRowNum++;
		}			
	})
	
	//删除row
	$("#removeRow").click(function() {	
		$('#table tbody tr').each(function (i, row) {
		  	  //get value
		      var $row = $(row),
		      selectRow = $row.find('input[name="selectRow"]').prop("checked");
		      if(selectRow){
		    	  $row.remove();
		    	  invRowNum--;
		      }		      
		});	
		var tableLen = $("#table tbody").find("tr").length;
		if(tableLen == 0){
			$('#vmiInv').prop('disabled', false);
		}
	})
	
});
function changeVmi(){
	if($('.select2 option').length == 1){//若只有一个供应商，则禁用选择列表
	   $('#vmiInv').val($('.select2 option:eq(0)').val());
	   $("#vmiInv").prop('disabled', true);
 	}
	if($('#vmiInv').val() != null){
		vmiInv = $('#vmiInv').val().split(",");
		var vendId = vmiInv[0];
		if(vendId !=""){
			$.ajax({
		    	  type: "GET",
		          url: "/inventory/vmi/getItem?vendId=" + vmiInv[0],
		          contentType: 'application/json; charset=utf-8',
		          dataType: 'json',
			      success: function (r) {
					  if (r.resultCode == 200 && r.data != null) {
						 $("#itemList").val(r.data);
						 if(invRowNum >1 ){								 
							 itemUnionArr = generateItemUnionArr();
						 }
						 
						 $("#addRow").css('display','block');
					  }else{
						 $("#table tbody").find("input").each(function(){
							 $(this).prop('disabled', true);
						 });
						 $("#addRow").css('display','none'); 
					  }  	  
			      },
			      error: function () {
			          swal("操作失败", {
			              icon: "error",
			          });
			      }
			 });
			
			//用户选中供应商时显示InvCode
			$("#invCodeLabel").text(vmiInv[1]);
			$("#invCodeLabel").css('display','block');
		}else{
			$("#addRow").css('display','none');
			$("#invCodeLabel").css('display','none');
		}
	}
}           
//验证必输字段
function requiredField(input) {
	var errMsg = '';
    if (input.value.length < 1) {
      //red border
       input.style.borderColor = "#e74c3c";
       errMsg = input.placeholder;
       input.title=errMsg;
       input.focus();     
    }else if (input.name == 'qty') {//qty can't be zero  
		var itemNumber = parseInt($(input).closest('tr').find('input[name="itemNumber"]').val()),
			currentQty = parseInt($(input).closest('tr').find('td[name="currentQty"]').text()),
			qty = parseInt(input.value),
			sumQty = currentQty + qty; 
		if(input.value == 0 ){
			input.style.borderColor = "#e74c3c";
		    input.title="数量不可为0";
	        input.focus();
		}else if (sumQty < 0) {
	      //当前库存与入库库存之和不能小于0
	      $(input).css("border-color","#e74c3c");
	      $(input).attr('title',"数量不合法");
	      $(input).focus();
		}else{		  
		  $(input).css("border-color","#2ecc71");
		  $(input).attr('title',"");
		}   	
    } else {
    	//green border    	
        $(input).css("border-color","#2ecc71");
	    $(input).attr('title',"");
        $('#edit-error-msg').css("display", "none");          
        $('#edit-error-msg').html("");
    }
}

function validateRow(tr) {//验证上一行数据
	var itemNumber=tr.find('input[name="itemNumber"]').val(),
	currentQty = parseInt(tr.find('td[name="currentQty"]').text()),
    qty = parseInt(tr.find('input[name="qty"]').val()),
	invTime=tr.find('input[name="invTime"]').val();
	if (!itemNumber) {
      var td = tr.find('input[name="itemNumber"]').next().find('input[type="text"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入部件号");
      td.focus();
      return false;
	}else if (!qty) {
      var td = tr.find('input[name="qty"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入数量");
      td.focus();
      return false;
	}else if (qty == 0) {
      var td = tr.find(	'input[name="qty"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"数量不可为0");
      td.focus();
      return false;
	}else if ((currentQty+qty) < 0) {
      //若当前库存和入库数之和小于0，则数量不合法
	  var td = tr.find('input[name="qty"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"数量不合法");
      td.focus();
      return false;
	}else if (!invTime) {
      var td = tr.find('input[name="invTime"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入日期");
      td.focus();
      return false;
	}
	return true;
}

function findDuplicates() {//验证重复数据
	var contents = {},
    	duplicates = false;
	$("#table tbody tr").each(function(i, row) {
	    var $row = $(row),
	      itemNumber = $row.find('input[name="itemNumber"]').val()+","+
	       				$row.find('input[name="lotNumber"]').val();	
	    if (contents[itemNumber]) {
	        duplicates = true;
	        return false;
	    }
	    contents[itemNumber] = true;	    
	}); 	
	if(duplicates){
		swal("请修改相同批号入库记录", {
            icon: "error",
        });
	}
	return duplicates;
}
function save() {
	if(findDuplicates()){
		return;
	}
	postdata = [];	
	var tds='';
	var rowNumber=1;
    $('#table tbody tr').each(function (i, row) {
    	  //get value
	      var $row = $(row),
	      toInv = vmiInv[1],
	      itemNumber = $row.find('input[name="itemNumber"]').val(),	
	      unionItem = $row.find('td[name="unionItem"]').text(),
	      currentQty = $row.find('td[name="currentQty"]').text(),
	      qty = $row.find('input[name="qty"]').val(),
	      lotNumber = $row.find('input[name="lotNumber"]').val(),
	      invTime = $row.find('input[name="invTime"]').val(),
	      note = $row.find('input[name="note"]').val();
	      //generate show table
	      tds +='<tr>';
	      tds += '<td>' + rowNumber + '</td>';		      
	      tds += '<td>' + itemNumber + '</td>';	
	      tds += '<td>' + unionItem + '</td>';	
	      tds += '<td>' + currentQty + '</td>';	
	      tds += '<td>' + qty + '</td>';
	      tds += '<td>' + (parseInt(currentQty)+parseInt(qty)) + '</td>';
	      tds += '<td>' + lotNumber + '</td>';
	      tds += '<td>' + invTime + '</td>';
	      tds += '<td>' + note + '</td>';
	      tds +='</tr>';
	      rowNumber++;

	      //posdataList
	      var obj={};
	      obj["fromInv"] = "VA";
	      obj["toInv"]=toInv;
	      obj["itemNumber"]=itemNumber;	      		      
	      obj["qty"]=qty;
	      obj["lotNumber"]=lotNumber;
	      obj["invTime"]=invTime;
	      obj["note"]=note;
	      postdata.push(obj);
    });
    
    $('#showTable tbody').html(tds);
	$('.modal-title').html('VMI入库记录');
	if(postdata.length >0){
		$('#invModal').modal('show');	
	}else{
		swal("请添加VMI入库记录", {
            icon: "error",
        });
	}
	
}
//保存VMI入库记录
$('#saveButton').click(function () {
	$.ajax({
	    type: 'POST',//方法类型
	    url: "/inventory/invs/save",
	    contentType: 'application/json; charset=utf-8',
        dataType: 'json',
	    data: JSON.stringify(postdata),
	    success: function (result) {
	        if (result.resultCode == 200) {
	            $('#invModal').modal('hide');
	            swal("保存成功", {
	                icon: "success",
	            });
	            $("#table tbody").html('');
	            //供应商选择为空
	            $("#vmiInv").select2('val', '');
	            //隐藏显示的InvCode
	            $("#invCodeLabel").css('display','none');
	            $("#addRow").css('display', 'none');
	            $("#vmiInv").prop('disabled', false);
	            
	            //行数设为1
	            invRowNum = 1;
	        }
	        else {
	            $('#itemModal').modal('hide');
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
});