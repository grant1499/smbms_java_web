var userObj;

//�û�����ҳ���ϵ��ɾ����ť����ɾ����(userlist.jsp)
function deleteUser(obj){
	$.ajax({
		type:"GET",
		url:path+"/jsp/user.do",
		data:{method:"deluser",uid:obj.attr("userid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//ɾ���ɹ����Ƴ�ɾ����
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//ɾ��ʧ��
				//alert("�Բ���ɾ���û���"+obj.attr("username")+"��ʧ��");
				changeDLGContent("�Բ���ɾ���û���"+obj.attr("username")+"��ʧ��");
			}else if(data.delResult == "notexist"){
				//alert("�Բ����û���"+obj.attr("username")+"��������");
				changeDLGContent("�Բ����û���"+obj.attr("username")+"��������");
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
	$('#removeUse').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	//ͨ��jquery��classѡ���������飩
	//��ÿ��classΪviewUser��Ԫ�ؽ��ж����󶨣�click��
	/**
	 * bind��live��delegate
	 * on
	 */
	$(".viewUser").on("click",function(){
		//�����󶨵�Ԫ�أ�a��ת����jquery���󣬿���ʹ��jquery����
		var obj = $(this);
		window.location.href=path+"/jsp/user.do?method=view&uid="+ obj.attr("userid");
	});
	
	$(".modifyUser").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/jsp/user.do?method=modify&uid="+ obj.attr("userid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteUser(userObj);
	});

	$(".deleteUser").on("click",function(){
		userObj = $(this);
		changeDLGContent("��ȷ��Ҫɾ���û���"+userObj.attr("username")+"����");
		openYesOrNoDLG();
	});
	
	/*$(".deleteUser").on("click",function(){
		var obj = $(this);
		if(confirm("��ȷ��Ҫɾ���û���"+obj.attr("username")+"����")){
			$.ajax({
				type:"GET",
				url:path+"/jsp/user.do",
				data:{method:"deluser",uid:obj.attr("userid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//ɾ���ɹ����Ƴ�ɾ����
						alert("ɾ���ɹ�");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//ɾ��ʧ��
						alert("�Բ���ɾ���û���"+obj.attr("username")+"��ʧ��");
					}else if(data.delResult == "notexist"){
						alert("�Բ����û���"+obj.attr("username")+"��������");
					}
				},
				error:function(data){
					alert("�Բ���ɾ��ʧ��");
				}
			});
		}
	});*/
});