var postdata = [];
var invRowNum = 1;
var removeRowFlag = false;
$(function () {	
	//addRow
	$("#addRow").click(function() {	
		var itemUnionArr = [];
		$.each(JSON.parse($("#itemList").val()), function(key, value) {
			var itemNumber = value.itemNumber;
			var unionStr = value.itemDesc+"/"+value.itemBrand+"/"+value.itemModel;
			itemUnionArr[itemNumber] = unionStr;			
		});
		var tr =  $('#tr'+(invRowNum-1)+'');
		//添加行时验证数据
		if(invRowNum ==1 || validate(tr)){
			//add row
			var tds='<tr style="width: 20px" id="tr'+invRowNum+'">';	
			tds+='<td style="width: 20px"><input type="checkbox" name="selectRow"></td>';							
			tds+='<td style="width: 350px"><input type="text" class="form-control" id="itemNumber'+invRowNum+'"  name="itemNumber"  placeholder="请输入部件号" onblur="requiredField(this)"></td>';
			//显示部件号/描述/型号
			tds+='<td style="width: 450px" id="unionItem'+invRowNum+'" name="unionItem"  style="vertical-align:middle"></td>';
			tds+='<td style="width: 120px"><input type="text" class="form-control" id="fromInv'+invRowNum+'"  name="fromInv"  placeholder="请输入From_Inv"  onblur="requiredField(this)"></td>';
			tds+='<td style="width: 120px"><input type="text" class="form-control" id="toInv'+invRowNum+'"  name="toInv"  placeholder="请输入To_Inv"  onblur="requiredField(this)"></td>';
			tds+='<td style="width: 120px"><input type="text" class="form-control" id="fromLoc'+invRowNum+'"  name="fromLoc"  placeholder="请输入From_Loc" onblur="requiredField(this)"></td>';
			tds+='<td style="width: 120px"><input type="text" class="form-control" id="toLoc'+invRowNum+'"  name="toLoc"  placeholder="请输入To_Loc" onblur="requiredField(this)"></td>';
			tds+='<td style="width: 100px"><input type="number" class="form-control"  name="qty" maxlength="6" placeholder="请输入数量"  onblur="requiredField(this)"></td>';
			tds+='<td style="width: 150px"><input type="text" class="form-control"  name="lotNumber" maxlength="100" placeholder="请输入批号"  onblur="requiredField(this)"></td>';
			tds+='<td style="width: 200px"><div class="col-sm-12"><input type="text" onblur="requiredField(this)" class="form-control datetimepicker-input" name="invTime" id="invTime'+invRowNum+'" placeholder="请输入交易日期" data-toggle="datetimepicker" data-target="#invTime'+invRowNum+'"></div></td>';
			tds+='<td style="width: 400px"><input type="text" class="form-control"  name="note" maxlength="100" placeholder="请输入备注"></td>';
			tds+='</tr>';	
				
			$("#table tbody").append(tds);
			
			//show item no
			$('#itemNumber'+invRowNum).inputpicker({
				width:"350px",
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
			$('#itemNumber'+invRowNum).change(function(input){
				var unionItem = itemUnionArr[$(this).val()];
				$('#unionItem'+(invRowNum-1)).html(unionItem);
				$('#inputpicker-'+((invRowNum-2)*5+1)).css("border-color","#2ecc71");
				
			})
	
			//show fromInv
			$('#fromInv'+invRowNum).inputpicker({
				width:"120px",
				data:JSON.parse($("#invList").val()),
			    fields:[
			        {name:'invCode',text:'Code'},
			        {name:'invDesc',text:' 描述'},
		        ],
			    fieldText:'invCode',
			    fieldValue:'invCode',
			    autoOpen: true,
			    headShow:true,
			    filterOpen:true,
			    filterType:'start', 
			    filterField:['invCode','invDesc']
			});
			$('#fromInv'+invRowNum).keypress(function(input){
				$('#toInv'+invRowNum).val('').trigger('change');
			});

			$('#fromInv'+invRowNum).change(function(input){
				$('#inputpicker-'+((invRowNum-2)*5+2)).css("border-color","#2ecc71");
				$('#toInv'+(invRowNum-1)).val('').trigger('change');
				var fromInv = $(this).val();
				var productFilter = {
				    "fromInv" : fromInv
				}
				//inputpicker调用change方法以后invRowNum自增
				$('#toInv'+(invRowNum-1)).inputpicker({
					width:"120px",
					urlParam : productFilter,
			        url: "/inventory/invs/getTransCode",
					fields:[
				        {name:'invCode',text:'Code'},
				        {name:'invDesc',text:' 描述'},
				    ],
				    fieldText:'invCode',
				    fieldValue:'invCode',
				    autoOpen: true,
				    headShow:true,
				    filterOpen:true,
				    filterType:'start', 
				    filterField:['invCode','invDesc'] 
			    });					
			})
			$('#toInv'+invRowNum).change(function(input){
				$('#inputpicker-'+((invRowNum-2)*5+5)).css("border-color","#2ecc71");
			})
	
			//show fromLoc
			$('#fromLoc'+invRowNum).inputpicker({
				width:"120px",
				data:JSON.parse($("#locList").val()),
			    fields:[
			        {name:'locCode',text:'Code'},
			        {name:'locDesc',text:' 描述'},
		        ],
			    fieldText:'locCode',
			    fieldValue:'locCode',
			    autoOpen: true,
			    headShow:true,
			    filterOpen:true,
			    filterType:'start', 
			    filterField:['locCode','locDesc']
			});
			$('#fromLoc'+invRowNum).change(function(input){
				$('#inputpicker-'+((invRowNum-2)*5+3)).css("border-color","#2ecc71");
			})
			
			//show toLoc
			$('#toLoc'+invRowNum).inputpicker({	
				width:"120px",
				data:JSON.parse($("#locList").val()),
			    fields:[
			        {name:'locCode',text:'Code'},
			        {name:'locDesc',text:' 描述'},
		        ],
			    fieldText:'locCode',
			    fieldValue:'locCode',
			    autoOpen: true,
			    headShow:true,
			    filterOpen:true,
			    filterType:'start', 
			    filterField:['locCode','locDesc']
			});
			$('#toLoc'+invRowNum).change(function(input){
				$('#inputpicker-'+((invRowNum-2)*5+4)).css("border-color","#2ecc71");
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
	})
	
});

function requiredField(input) {
	var errMsg = '';
    if (input.value.length < 1) {
      //red border
       input.style.borderColor = "#e74c3c";
       errMsg = input.placeholder;
       input.title=errMsg;
       input.focus();     
    }
    else if(input.placeholder.indexOf("部件号")!=-1 && $("#itemList").val().indexOf(input.value) == -1){    		
	    input.style.borderColor = "#e74c3c";	       
	    errMsg = "部件号不合法";       
	    input.title=errMsg;
	    input.focus();  
	}else if(input.placeholder.indexOf("From_Inv")!=-1 && $("#invList").val().indexOf(input.value) == -1){
		//red border
	    input.style.borderColor = "#e74c3c";
	    errMsg = "From_Inv不合法";       
	    input.title=errMsg;
	    input.focus();  
	}else if(input.placeholder.indexOf("To_Inv")!=-1 && $("#invList").val().indexOf(input.value) == -1){
		//red border
	     input.style.borderColor = "#e74c3c";
	     errMsg = "To_Inv不合法";       
	     input.title=errMsg;
	     input.focus();  
	}else if(input.placeholder.indexOf("From_Loc")!=-1 && $("#locList").val().indexOf(input.value) == -1){
		//red border
	     input.style.borderColor = "#e74c3c";
	     errMsg = "From_Loc不合法";       
	     input.title=errMsg;
	     input.focus();  
	}else if(input.placeholder.indexOf("To_Loc")!=-1 && $("#locList").val().indexOf(input.value) == -1){
		//red border
	     input.style.borderColor = "#e74c3c";
	     errMsg = "To_Loc不合法";       
	     input.title=errMsg;
	     input.focus();  
	}else if (input.value == 0 && input.name == 'qty') {//qty can't be zero   	
    	input.style.borderColor = "#e74c3c";
        $('#edit-error-msg').css("display", "block");          
        $('#edit-error-msg').html("数量不能为0");
        input.focus();
    } else {
      //green border
    	input.title="";
        input.style.borderColor = "#2ecc71";
        $('#edit-error-msg').css("display", "none");          
        $('#edit-error-msg').html("");
    }
}

function validate(tr) {//验证上一行数据
	var itemNumber=tr.find('input[name="itemNumber"]').val(),
	fromInv=tr.find('input[name="fromInv"]').val(),
	toInv=tr.find('input[name="toInv"]').val(),
	fromLoc=tr.find('input[name="fromLoc"]').val(),
	toLoc=tr.find('input[name="toLoc"]').val(),
	qty=tr.find('input[name="qty"]').val(),
	lotNumber=tr.find('input[name="lotNumber"]').val(),
	invTime=tr.find('input[name="invTime"]').val();
	if (!itemNumber) {
      var td = tr.find('input[name="itemNumber"]').next().find('input[type="text"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入部件号");
      td.focus();
      return false;
	}else if (!fromInv) {
      var td = tr.find('input[name="fromInv"]').next().find('input[type="text"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入From_Inv");
      td.focus();
      return false;
	}else if (!toInv) {
      var td = tr.find('input[name="toInv"]').next().find('input[type="text"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入To_Inv");
      td.focus();
      return false;
	}else if (!fromLoc) {
      var td = tr.find('input[name="fromLoc"]').next().find('input[type="text"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入From_Loc");
      td.focus();
      return false;
	}else if (!toLoc) {
      var td = tr.find('input[name="toLoc"]').next().find('input[type="text"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入To_Loc");
      td.focus();
      return false;
	}else if (!qty) {
      var td = tr.find('input[name="qty"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入数量");
      td.focus();
      return false;
	}else if (qty == 0) {
      var td = tr.find('input[name="qty"]');
      td.css("border-color","#e74c3c");
      td.attr('title',"数量不可为0");
      td.focus();
      return false;
	}else if (!lotNumber) {
      var td = tr.find('input[name="lotNumber"]');     
      td.css("border-color","#e74c3c");
      td.attr('title',"请输入批号");
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
function save() {
	var flag = true; 
	$('#table tbody tr').each(function (i, row) {
		flag = validate($(row));      
	})
	if(flag){
		postdata = [];	
		var tds='';
		var rowNumber=1;
	    $('#table tbody tr').each(function (i, row) {
	    	  //get value
		      var $row = $(row),
		      itemNumber = $row.find('input[name="itemNumber"]').val(),	
		      unionItem = $row.find('td[name="unionItem"]').text(),
		      fromInv = $row.find('input[name="fromInv"]').val(),
		      toInv = $row.find('input[name="toInv"]').val(),
		      fromLoc = $row.find('input[name="fromLoc"]').val(),
		      toLoc = $row.find('input[name="toLoc"]').val(),	
		      qty = $row.find('input[name="qty"]').val(),
		      lotNumber = $row.find('input[name="lotNumber"]').val(),
		      invTime = $row.find('input[name="invTime"]').val(),
		      note = $row.find('input[name="note"]').val();
		      //generate show table
		      tds +='<tr>';
		      tds += '<td style="width: 20px">' + rowNumber + '</td>';
		      tds += '<td style="width: 50px">' + itemNumber + '</td>';
		      //tds += '<td style="width: 350px">' + unionItem + '</td>';
		      tds += '<td style="width: 80px">' + fromInv + '</td>';
		      tds += '<td style="width: 80px">' + toInv + '</td>';
		      tds += '<td style="width: 80px">' + fromLoc + '</td>';
		      tds += '<td style="width: 80px">' + toLoc + '</td>';
		      tds += '<td style="width: 80px">' + qty + '</td>';
		      tds += '<td style="width: 120px">' + lotNumber + '</td>';
		      tds += '<td style="width: 150px">' + invTime + '</td>';
		      tds += '<td style="width: 150px">' + note + '</td>';
		      tds +='</tr>';
		      rowNumber++;

		      //posdataList
		      var obj={};
		      obj["itemNumber"]=itemNumber;	      
		      obj["fromInv"]=fromInv;
		      obj["toInv"]=toInv;
		      obj["fromLoc"]=fromLoc;      
		      obj["toLoc"]=toLoc;
		      obj["qty"]=qty;
		      obj["lotNumber"]=lotNumber;
		      obj["invTime"]=invTime;
		      obj["note"]=note;
		      postdata.push(obj);
	    });
	    $('#showTable tbody').html(tds);
		$('.modal-title').html('');
	    $('#invModal').modal('show');
	}
	
}

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
