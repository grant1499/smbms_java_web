var billObj;

//��������ҳ���ϵ��ɾ����ť����ɾ����(billlist.jsp)
function deleteBill(obj){
	$.ajax({
		type:"GET",
		url:path+"/jsp/bill.do",
		data:{method:"delbill",billid:obj.attr("billid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//ɾ���ɹ����Ƴ�ɾ����
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//ɾ��ʧ��
				//alert("�Բ���ɾ��������"+obj.attr("billcc")+"��ʧ��");
				changeDLGContent("�Բ���ɾ��������"+obj.attr("billcc")+"��ʧ��");
			}else if(data.delResult == "notexist"){
				//alert("�Բ��𣬶�����"+obj.attr("billcc")+"��������");
				changeDLGContent("�Բ��𣬶�����"+obj.attr("billcc")+"��������");
			}
		},
		error:function(data){
			alert("�Բ���ɾ��ʧ��");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeBi').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeBi').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	$(".viewBill").on("click",function(){
		//�����󶨵�Ԫ�أ�a��ת����jquery���󣬿���ʹ��jquery����
		var obj = $(this);
		window.location.href=path+"/jsp/bill.do?method=view&billid="+ obj.attr("billid");
	});
	
	$(".modifyBill").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/jsp/bill.do?method=modify&billid="+ obj.attr("billid");
	});
	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteBill(billObj);
	});

	$(".deleteBill").on("click",function(){
		billObj = $(this);
		changeDLGContent("��ȷ��Ҫɾ��������"+billObj.attr("billcc")+"����");
		openYesOrNoDLG();
	});
	
	/*$(".deleteBill").on("click",function(){
		var obj = $(this);
		if(confirm("��ȷ��Ҫɾ��������"+obj.attr("billcc")+"����")){
			$.ajax({
				type:"GET",
				url:path+"/bill.do",
				data:{method:"delbill",billid:obj.attr("billid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//ɾ���ɹ����Ƴ�ɾ����
						alert("ɾ���ɹ�");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//ɾ��ʧ��
						alert("�Բ���ɾ��������"+obj.attr("billcc")+"��ʧ��");
					}else if(data.delResult == "notexist"){
						alert("�Բ��𣬶�����"+obj.attr("billcc")+"��������");
					}
				},
				error:function(data){
					alert("�Բ���ɾ��ʧ��");
				}
			});
		}
	});*/
});