var userName = null;
var birthday = null;
var phone = null;
var userRole = null;
var saveBtn = null;
var backBtn = null;

$(function(){
	userName = $("#userName");
	birthday = $("#birthday");
	phone = $("#phone");
	userRole = $("#userRole");
	saveBtn = $("#save");
	backBtn = $("#back");
	
	userName.next().html("*");
	birthday.next().html("*");
	phone.next().html("*");
	userRole.next().html("*");
	
	
	$.ajax({
		type:"GET",//��������
		url:path+"/jsp/user.do",//�����url
		data:{method:"getrolelist"},//�������
		dataType:"json",//ajax�ӿڣ�����url�����ص���������
		success:function(data){//data���������ݣ�json����
			if(data != null){
				var rid = $("#rid").val();
				userRole.html("");
				var options = "<option value=\"0\">��ѡ��</option>";
				for(var i = 0; i < data.length; i++){
					//alert(data[i].id);
					//alert(data[i].roleName);
					if(rid != null && rid != undefined && data[i].id == rid ){
						options += "<option selected=\"selected\" value=\""+data[i].id+"\" >"+data[i].roleName+"</option>";
					}else{
						options += "<option value=\""+data[i].id+"\" >"+data[i].roleName+"</option>";
					}
					
				}
				userRole.html(options);
			}
		},
		error:function(data){//������ʱ��404��500 �ȷ�200�Ĵ���״̬��
			validateTip(userRole.next(),{"color":"red"},imgNo+" ��ȡ�û���ɫ�б�error",false);
		}
	});
	
	
	userName.on("focus",function(){
		validateTip(userName.next(),{"color":"#666666"},"* �û������ȱ����Ǵ���1С��10���ַ�",false);
	}).on("blur",function(){
		if(userName.val() != null && userName.val().length > 1 
				&& userName.val().length < 10){
			validateTip(userName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userName.next(),{"color":"red"},imgNo+" �û�������Ĳ����Ϲ淶������������",false);
		}
		
	});
	
	birthday.on("focus",function(){
		validateTip(birthday.next(),{"color":"#666666"},"* ��������ѡ������",false);
	}).on("blur",function(){
		if(birthday.val() != null && birthday.val() != ""){
			validateTip(birthday.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(birthday.next(),{"color":"red"},imgNo + " ѡ������ڲ���ȷ,����������",false);
		}
	});
	
	phone.on("focus",function(){
		validateTip(phone.next(),{"color":"#666666"},"* �������ֻ���",false);
	}).on("blur",function(){
		var patrn=/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if(phone.val().match(patrn)){
			validateTip(phone.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(phone.next(),{"color":"red"},imgNo + " ��������ֻ��Ÿ�ʽ����ȷ",false);
		}
	});
	
	userRole.on("focus",function(){
		validateTip(userRole.next(),{"color":"#666666"},"* ��ѡ���û���ɫ",false);
	}).on("blur",function(){
		if(userRole.val() != null && userRole.val() > 0){
			validateTip(userRole.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userRole.next(),{"color":"red"},imgNo+" ������ѡ���û���ɫ",false);
		}
		
	});
	
	saveBtn.on("click",function(){
		userName.blur();
		phone.blur();
		birthday.blur();
		userRole.blur();
		if(userName.attr("validateStatus") == "true" 
			&& phone.attr("validateStatus") == "true"
			&& birthday.attr("validateStatus") == "true"
			&& userRole.attr("validateStatus") == "true"){
			if(confirm("�Ƿ�ȷ��Ҫ�ύ���ݣ�")){
				$("#userForm").submit();
			}
		}
	});
	
	backBtn.on("click",function(){
		//alert("modify: "+referer);
		if(referer != undefined 
			&& null != referer 
			&& "" != referer
			&& "null" != referer
			&& referer.length > 4){
		 window.location.href = referer;
		}else{
			history.back(-1);
		}
	});
});