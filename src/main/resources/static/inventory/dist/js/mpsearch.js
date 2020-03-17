//Initialize Select2 Elements
$('.select2').select2();
//Initialize QrCode
//var qrcode = new QRCode("qrcode",{
//	width: 80,
//    height: 80
//	}),
//	printqrcode = new QRCode("printqrcode",{
//		width: 100,
//	    height: 100
//	});
//var qrcode = new QRCode("qrcode",{
//	width: 80,
//    height: 80
//	});

$(function () {	
	//startDate first day in current month
	$('#startDate').datetimepicker({
		format: 'YYYY-MM-DD'		
	});	
	$('#startDate').val(moment().startOf('month').format('YYYY-MM-DD'));
	$('#endDate').datetimepicker({
		format: 'YYYY-MM-DD'		
	});
	$('#endDate').val(moment().endOf('month').format('YYYY-MM-DD'));
	
	var pageWidth = $("#jqGrid").parent().width() - 100;
	//endDate current day 
	$("#jqGrid").jqGrid({
        datatype: "json",
        colModel: [
        	
            {label: '预约号', name: 'mpNumber', index: 'mpNumber', width: (pageWidth*(10/100)),key:true,formatter: statusFormatter},
            {label: '预约人', name: 'fullName', index: 'fullName',width: (pageWidth*(10/100))},
            {label: '预约数量', name: 'qty', index: 'qty',width: (pageWidth*(10/100))},
            {label: '预约时间', name: 'mpTime', index: 'mpTime',width: (pageWidth*(10/100))},
            {label: '状态', name: 'state', index: 'state', hidden: true}
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
        ondblClickRow: function(rowId) {
            var rowData = jQuery(this).getRowData(rowId); 
            showMpModal(rowData);
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


function statusFormatter(cellvalue, options, rowObject ){
	var state ='';
	if(rowObject.state == 0){
		state = '<span class="badge badge-secondary">未完成</span>';
	}
	if(rowObject.state == 1){
		state = '<span class="badge badge-success">已完成</span>';
	}
	if(rowObject.state == 2){
		state = '<span class="badge badge-warning">已取消</span>';
	}
	return state+cellvalue;
}

function search() {
    var mpNumber = $('#mpNumber').val();
    var createUser = $('#createUser').val();
    var startDate=$('#startDate').val();
    var endDate=$('#endDate').val();
    var state=$('#state').val();
    //数据封装
    var searchData = {mpNumber: mpNumber,userName: createUser,startDate: startDate,endDate: endDate,state: state};  
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/inventory/invs/mplist'}).trigger("reloadGrid");
}

function showMpModal(rowData){
	var mpNumber = rowData['mpNumber'].substring(rowData['mpNumber'].indexOf('M'));
	$('.modal-title').html('领料单');
	//显示预约号/预约人/预约时间
	var html = '<label class="control-label">预约人:</label>&nbsp;'+rowData['fullName']+'&nbsp;&nbsp;&nbsp;';
		html += '<label class="control-label">预约时间:</label>&nbsp;'+rowData['mpTime']+'&nbsp;&nbsp;&nbsp;';
	$('#showMp').html(html);
	$('#printMp').html(html);

//	var str = "预约号: "+mpNumber+"预约人: "+rowData['fullName']+"预约时间: "+rowData['mpTime'];
//	$("#barcodeTarget").barcode(str, "ean13");

	
//	$("#barcodeTarget").html("").show().barcode(str, "code128");
//	$("#barcodeTarget").barcode(str, "ean13", {barWidth:2, barHeight:30,bgColor:"#FFFFFF",color:"#000000"});
//	qrcode.makeCode(str); // make code.
//	$("#qrcode").barcode("111", "ean13",{barWidth:2, barHeight:30,bgColor:"#FFFFFF",color:"#000000"}); 
//	printqrcode.makeCode(str); 
//	$("#printqrcode").barcode(str, "ean13",{barWidth:2, barHeight:30});  
	$("#barcodeTarget").html('').barcode(mpNumber, "code39",{barWidth:1, barHeight:30,fontSize:14,fontFamily: "Times New Roman",textAlign:"right"});
//	$("#barcodeTarget").find("div:last").css("text-align","right");
	$("#printqrcode").html('').barcode(mpNumber, "code39",{barWidth:1, barHeight:40,fontSize:14,fontFamily: "Times New Roman",textAlign:"right"});
	$('#msgTable tbody tr td:eq(1)').text(rowData['fullName']);
	$('#mptime').text(rowData['mpTime'].substring(0,10));
	 $.ajax({
	      type: "GET",
	      url: "/inventory/invs/findItemByMp?mpNumber=" + mpNumber,
	      contentType: "application/json",	      
	      success: function (r) {
	          if (r.resultCode == 200) {
	        	 var fullName = r.data.fullName;
	        	 console.log(fullName)
	        	 $('#userTable tbody tr td:eq(5)').text(fullName);
	             var d = r.data.mpVoList;
	             var tds='';	             
	             for(var i=0;i<d.length;i++){
	            	 var item = d[i].item,
	            	 	qty = parseInt(d[i].qty),
	            	 	invList = d[i].invList
	            	 	showArr = [],
	            	 	cmpQty = qty;
	            	  
	       		      if(invList!=null && invList.length > 0){
		       		    for(var j=0;j<invList.length;j++){
	       		    		var inv = invList[j];
	       		    		//当预约数小于库存数时,库存信息显示预约数
	       		    		if(qty <= parseInt(inv.qty)){
	       		    			inv.qty = qty;
	       		    			showArr.push(inv);
	       		    			break;
	       		    		}else{
	       		    			cmpQty -= inv.qty;
	       		    			//当预约数大于库存数时,库存信息遍历显示至预约数
	       		    			if(cmpQty < 0){
	       		    				inv.qty = -cmpQty;
	       		    				showArr.push(inv);
	       		    				break;
	       		    			}
	       		    		}
	       		    		showArr.push(inv);
	       		    	}
		       		  //tr跨行
		       		  var rowspan = (showArr!=null && showArr.length > 1)?showArr.length  :1;
		       		  tds += '<tr><td  rowspan="'+rowspan+'" style="text-align:center">' + item.itemNumber + '</td>';
	       		      tds += '<td  rowspan="'+rowspan+'">' + item.itemDesc + '</td>';
	       		      tds += '<td  rowspan="'+rowspan+'">' + item.itemBrand + '</td>';
	       		      tds += '<td  rowspan="'+rowspan+'">' + item.itemModel + '</td>';
	       		      tds += '<td  rowspan="'+rowspan+'" style="text-align:right">' + qty + '</td>';
	       		      if(showArr!=null && showArr.length > 0){
	       		    	  for(var j=0;j<showArr.length;j++){
	       		    		var inv = showArr[j];
	       		    		if(j==0){//若遍历第一行 直接插入
	       		    			tds += '<td>' + inv.locName + '</td>';
		       		    		tds += '<td style="text-align:right" name="locQty">' + inv.qty + '</td>';
		       		    		tds += '<td>' + inv.invTime + '</td>';
	       		    		}else{//若遍历后续行 直接插入<tr>
	       		    			tds += '<tr><td>' + inv.locName + '</td>';
		       		    		tds += '<td  style="text-align:right" name="locQty">' + inv.qty + '</td>';
		       		    		tds += '<td>' + inv.invTime + '</td></tr>';	
	       		    		}
	       		    	  }
	       		      }
	       		    	
	       		      }else{
	       		    	tds += '';
	       		      }
	       		      tds +='</tr>';
	             }
	             $('#showItemTable tbody').html(tds);
//	             $('#printTable tbody').empty().html(tds);
	          } else {
	              swal(r.message, {
	                  icon: "error",
	              });
	          }
	      }
	  });
	
	if(rowData['state'] == '0'){//隐藏已完成按钮
		$("#stateBtn").show();
	}else{
		
		$('#stateBtn').hide();
	}
	$("#stateBtn").val(mpNumber);
    $('#showMpModal').modal('show');
}

/**
 * 已完成方法
 */
function checkDoneMp() {
    var mp = {"mpNumber":$("#stateBtn").val()}
    $.ajax({
      type: "POST",
      url: "/inventory/invs/checkDoneMp",
      contentType: "application/json",
      data: JSON.stringify(mp),
      success: function (r) {
          if (r.resultCode == 200) {
              swal("已完成", {
                  icon: "success",
              });
              $("#jqGrid").trigger("reloadGrid");
              $('#showMpModal').modal('hide');
          } else {
              swal(r.message, {
                  icon: "error",
              });
          }
      }
  });
}

function printMp() {
	 var css = '@page { size: A4 landscape; margin-top: 0mm;margin-left: 5mm;margin-right: 5mm;margin-bottom: 0mm;page-break-inside: avoid;page-break-after: always;}  '+
			 '@media print {' +
			 	'html, body { width: 297mm; height: 200mm;font-family: 微软雅黑; font-size: 14px;} '+
			 	'table th {border-bottom: 2px solid #000000;}' + 
			 	'#content {display: table;width:100%;counter-reset: page;}#pageFooter {display: table-footer-group;}#pageFooter:after {counter-increment: page;content: counter(page)}'+// "/" counter(pages)
			 	'#container{width:100%;height:100px;display:inline-block;margin:0 auto;}#left{float:left;width:160px;margin-top:10px;}#printqrcode{float:right;width:100px;margin-top:20px;}#center{width:800px;margin-top:10px;text-align: center;display:inline-block}' +
			 '}';
	var printWindow = window.frames["print_frame"].window;
	printWindow.document.body.innerHTML ="";
	printWindow.document.write("<HTML><Head><Title></Title><style type='text/css'  media='print'>"+css+"</style>");
    printWindow.document.write("</Head><Body style='margin-top:10px;font-weight:bold'>");//预约人字体加粗	
    var printHtml='',totalQty=0;
    //添加序号  style='text-align:center;'
    $('#showItemTable tbody tr').each(function(index){
    	printHtml+="<tr><td style='padding-left:15px'>"+(index+1)+"</td>"+$(this).html()+"</tr>";
    	var qty = $(this).find('td[name="locQty"]').html();
    	if(qty!=null){
    		totalQty+=parseInt(qty);
    	}
    })
//    if(totalQty!=0){
//    	printHtml+="<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>总计</td><td>"+totalQty+"</td></tr>";
//    }
    $('#printTable tbody').html(printHtml);
    $('#totalTable tbody tr td:eq(2)').text(totalQty);
    //打印时间
	$('#printDate').text(moment().format('YYYY-MM-DD'));

    printWindow.document.write(document.getElementById("printdiv").innerHTML);    
    printWindow.document.write('</Body></HTML>');
    setTimeout(function() {
    	printWindow.focus();
    	printWindow.print();
    }, 250);
 
}

