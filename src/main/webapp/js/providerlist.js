var providerObj;

//��Ӧ�̹���ҳ���ϵ��ɾ����ť����ɾ����(providerlist.jsp)
function deleteProvider(obj){
	$.ajax({
		type:"GET",
		url:path+"/jsp/provider.do",
		data:{method:"delprovider",proid:obj.attr("proid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//ɾ���ɹ����Ƴ�ɾ����
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//ɾ��ʧ��
				//alert("�Բ���ɾ����Ӧ�̡�"+obj.attr("proname")+"��ʧ��");
				changeDLGContent("�Բ���ɾ����Ӧ�̡�"+obj.attr("proname")+"��ʧ��");
			}else if(data.delResult == "notexist"){
				//alert("�Բ��𣬹�Ӧ�̡�"+obj.attr("proname")+"��������");
				changeDLGContent("�Բ��𣬹�Ӧ�̡�"+obj.attr("proname")+"��������");
			}else{
				//alert("�Բ��𣬸ù�Ӧ�̡�"+obj.attr("proname")+"�����С�"+data.delResult+"��������������ɾ��");
				changeDLGContent("�Բ��𣬸ù�Ӧ�̡�"+obj.attr("proname")+"�����С�"+data.delResult+"��������������ɾ��");
			}
		},
		error:function(data){
			//alert("�Բ���ɾ��ʧ��");
			changeDLGContent("�Բ���ɾ��ʧ��");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeProv').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeProv').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}
$(function(){
	$(".viewProvider").on("click",function(){
		//�����󶨵�Ԫ�أ�a��ת����jquery���󣬿���ʹ��jquery����
		var obj = $(this);
		window.location.href=path+"/jsp/provider.do?method=view&proid="+ obj.attr("proid");
	});
	
	$(".modifyProvider").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/jsp/provider.do?method=modify&proid="+ obj.attr("proid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteProvider(providerObj);
	});

	$(".deleteProvider").on("click",function(){
		providerObj = $(this);
		changeDLGContent("��ȷ��Ҫɾ����Ӧ�̡�"+providerObj.attr("proname")+"����");
		openYesOrNoDLG();
	});
	
/*	$(".deleteProvider").on("click",function(){
		var obj = $(this);
		if(confirm("��ȷ��Ҫɾ����Ӧ�̡�"+obj.attr("proname")+"����")){
			$.ajax({
				type:"GET",
				url:path+"/jsp/provider.do",
				data:{method:"delprovider",proid:obj.attr("proid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//ɾ���ɹ����Ƴ�ɾ����
						alert("ɾ���ɹ�");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//ɾ��ʧ��
						alert("�Բ���ɾ����Ӧ�̡�"+obj.attr("proname")+"��ʧ��");
					}else if(data.delResult == "notexist"){
						alert("�Բ��𣬹�Ӧ�̡�"+obj.attr("proname")+"��������");
					}else{
						alert("�Բ��𣬸ù�Ӧ�̡�"+obj.attr("proname")+"�����С�"+data.delResult+"��������������ɾ��");
					}
				},
				error:function(data){
					alert("�Բ���ɾ��ʧ��");
				}
			});
		}
	});*/
});