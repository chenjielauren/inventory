//Initialize Select2 Elements
$('.select2').select2();
var postdata = [];
var rowNum=1;
$(function () {
	//get invCodes
	//1:RI;2:VI;3:MI
	var invCodes = {};	
	$("#fromInv > option").each(function() {
		invCodes[this.value]=this.text;
	});
	//get locations
	//1:A;2:B;3:C
	var locs = {};
	$("#fromLoc > option").each(function() {
		locs[this.value]=this.text;
	});
	
	$("#addRow").click(function() {		
		//add row
		var tds='<tr>';
		tds+='<td><input type="checkbox" name="selectRow"></td>';
		tds+='<td><input type="text" class="form-control"  name="itemNumber" maxlength="6" placeholder="请输入部件号" onblur="requiredField(this)"></td>';
		tds+='<td><select class="select2 form-control"  name="fromInv">';
		for (var key in invCodes) {
		    if (invCodes.hasOwnProperty(key)) {
		        tds+=' <option value="'+key+'">'+invCodes[key]+'</option>'
		    }
		}
		tds+='</select></td>';
		tds+='<td><select class="form-control select2"  name="toInv">';
		for (var key in invCodes) {
		    if (invCodes.hasOwnProperty(key)) {
		        tds+=' <option value="'+key+'">'+invCodes[key]+'</option>'
		    }
		}
		tds+='</select></td>';
		tds+='<td><select class="form-control select2"  name="fromLoc">';
		for (var key in locs) {
		    if (locs.hasOwnProperty(key)) {
		        tds+=' <option value="'+key+'">'+locs[key]+'</option>'
		    }
		}
		tds+='</select></td>';
		tds+='<td><select class="form-control select2"  name="toLoc">';
		for (var key in locs) {
		    if (locs.hasOwnProperty(key)) {
		        tds+=' <option value="'+key+'">'+locs[key]+'</option>'

		    }
		}
		tds+='</select></td>';
		tds+='<td><input type="number" class="form-control"  name="qty" maxlength="6" placeholder="请输入数量" onblur="requiredField(this)"></td>';
		tds+='<td><input type="text" class="form-control"  name="lotNumber" maxlength="100" placeholder="请输入批号"  onblur="requiredField(this)"></td>';
		tds+='<td><div class="col-sm-12"><input type="text" onblur="requiredField(this)" class="form-control datetimepicker-input" name="invTime" id="invTime'+rowNum+'" placeholder="请输入日期" data-toggle="datetimepicker" data-target="#invTime'+rowNum+'"></div></td>';
		tds+='</tr>';
		$("#table tbody").append(tds);
		$('.select2').select2();
		var d = new Date();
		d.setDate(d.getDate()-7);
		$('#invTime'+rowNum+'').datetimepicker({
			format: 'YYYY-MM-DD HH:mm:ss',
			minDate: d,//Current day minus 7 days 
			maxDate:new Date() // Current day
		});
		rowNum++;
	});
	
	$("#removeRow").click(function() {	
		$('#table tbody tr').each(function (i, row) {
		  	  //get value
		      var $row = $(row),
		      selectRow = $row.find('input[name="selectRow"]').prop("checked");
		      if(selectRow){
		    	  $row.remove();
		      }
		})
	})
	
});

function requiredField(input) {
    if (input.value.length < 1) {
      //red border
       input.style.borderColor = "#e74c3c";
       input.title="Can not be null.";
    }
    else if (input.value == 0 && input.name == 'qty') {//qty can't be zero   	
    	input.style.borderColor = "#e74c3c";
        input.title="Can not be zero.";
    } else {
      //green border
    	input.title="";
        input.style.borderColor = "#2ecc71";
    }
}

function save() {
//	var itemNumber = $('input[name="itemNumber"]').val();
//	var qty = $('input[name="qty"]').val();
//	var lotNumber = $('input[name="lotNumber"]').val();
//	var invTime = $('input[name="invTime"]').val();
//	if (!itemNumber) {
//        $('#edit-error-msg').css("display", "block");
//        $('#edit-error-msg').html("请输入部件号！");
//        return;
//    }else if (!qty) {
//        $('#edit-error-msg').css("display", "block");
//        $('#edit-error-msg').html("请输入数量！");
//        return;
//    }else if (!lotNumber) {
//        $('#edit-error-msg').css("display", "block");
//        $('#edit-error-msg').html("请输入批号！");
//        return;
//    }else if (!invTime) {
//        $('#edit-error-msg').css("display", "block");
//        $('#edit-error-msg').html("请输入日期！");
//        return;
//    }else{
//    	$('#edit-error-msg').css("display", "none");
//    }
	
	$('#table tbody tr').each(function (i, row) {
  	  //get value
      var $row = $(row),
      itemNumber = $row.find('input[name="itemNumber"]').val(),	 	      
      qty = $row.find('input[name="qty"]').val(),
      lotNumber = $row.find('input[name="lotNumber"]').val(),
      invTime = $row.find('input[name="invTime"]').val();
	  //validate
      if (!itemNumber) {
          $('#edit-error-msg').css("display", "block");          
          $('#edit-error-msg').html("请输入部件号！");
          var td = $row.find('input[name="itemNumber"]');
          td.css("border-color","#e74c3c");
          td.focus();
          return false;
      }else if (!qty) {
          $('#edit-error-msg').css("display", "block");
          $('#edit-error-msg').html("请输入数量！");
          var td = $row.find('input[name="qty"]');
          td.css("border-color","#e74c3c");
          td.focus();
          return false;
      }else if (qty == 0) {
          $('#edit-error-msg').css("display", "block");
          $('#edit-error-msg').html("数量不可为0！");
          var td = $row.find('input[name="qty"]');
          td.css("border-color","#e74c3c");
          td.focus();
          return false;
      }else if (!lotNumber) {
          $('#edit-error-msg').css("display", "block");
          $('#edit-error-msg').html("请输入批号！");
          var td = $row.find('input[name="lotNumber"]');
          td.css("border-color","#e74c3c");
          td.focus();
          return false;
      }else if (!invTime) {
          $('#edit-error-msg').css("display", "block");
          $('#edit-error-msg').html("请输入日期！");
          var td = $row.find('input[name="invTime"]');
          td.css("border-color","#e74c3c");
          td.focus();
          return false;
      }else{
      	$('#edit-error-msg').css("display", "none");
      	$('#edit-error-msg').html("");
      	
      }
	})	
	
	if($('#edit-error-msg').html() == ""){
		postdata = [];	
		var tds='';
		var rowNumber=1;
	    $('#table tbody tr').each(function (i, row) {
	    	  //get value
		      var $row = $(row),
		      itemNumber = $row.find('input[name="itemNumber"]').val(),	
		      //$("#e2 option:selected").text();
		      fromInv = $row.find('select[name="fromInv"] option:selected').val(),
		      fromInvText = $row.find('select[name="fromInv"] option:selected').text(),
		      toInv = $row.find('select[name="toInv"] option:selected').val(),
		      toInvText = $row.find('select[name="toInv"] option:selected').text(),
		      fromLoc = $row.find('select[name="fromLoc"] option:selected').val(),
		      fromLocText = $row.find('select[name="fromLoc"] option:selected').text(),
		      toLoc = $row.find('select[name="toLoc"] option:selected').val(),	
		      toLocText = $row.find('select[name="toLoc"] option:selected').text(),
		      qty = $row.find('input[name="qty"]').val(),
		      lotNumber = $row.find('input[name="lotNumber"]').val(),
		      invTime = $row.find('input[name="invTime"]').val();
		      //generate show table
		      tds +='<tr>';
		      tds += '<td>' + rowNumber + '</td>';
		      tds += '<td>' + itemNumber + '</td>';
		      tds += '<td>' + fromInvText + '</td>';
		      tds += '<td>' + toInvText + '</td>';
		      tds += '<td>' + fromLocText + '</td>';
		      tds += '<td>' + toLocText + '</td>';
		      tds += '<td>' + qty + '</td>';
		      tds += '<td>' + lotNumber + '</td>';
		      tds += '<td>' + invTime + '</td>';
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
