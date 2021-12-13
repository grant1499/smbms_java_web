var userCode = null;
var userName = null;
var userPassword = null;
var ruserPassword = null;
var phone = null;
var birthday = null;
var userRole = null;
var addBtn = null;
var backBtn = null;


$(function(){
	userCode = $("#userCode");
	userName = $("#userName");
	userPassword = $("#userPassword");
	ruserPassword = $("#ruserPassword");
	phone = $("#phone");
	birthday = $("#birthday");
	userRole = $("#userRole");
	addBtn = $("#add");
	backBtn = $("#back");
	//��ʼ����ʱ��Ҫ�����е���ʾ��Ϣ��Ϊ��* ����ʾ�����������Ҫд��ҳ����
	userCode.next().html("*");
	userName.next().html("*");
	userPassword.next().html("*");
	ruserPassword.next().html("*");
	phone.next().html("*");
	birthday.next().html("*");
	userRole.next().html("*");
	
	$.ajax({
		type:"GET",//��������
		url:path+"/jsp/user.do",//�����url
		data:{method:"getrolelist"},//�������
		dataType:"json",//ajax�ӿڣ�����url�����ص���������
		success:function(data){//data���������ݣ�json����
			if(data != null){
				userRole.html("");
				var options = "<option value=\"0\">��ѡ��</option>";
				for(var i = 0; i < data.length; i++){
					//alert(data[i].id);
					//alert(data[i].roleName);
					options += "<option value=\""+data[i].id+"\">"+data[i].roleName+"</option>";
				}
				userRole.html(options);
			}
		},
		error:function(data){//������ʱ��404��500 �ȷ�200�Ĵ���״̬��
			validateTip(userRole.next(),{"color":"red"},imgNo+" ��ȡ�û���ɫ�б�error",false);
		}
	});
	
	
	
	/*
	 * ��֤
	 * ʧ��\��
	 * jquery�ķ�������
	 */
	userCode.bind("blur",function(){
		//ajax��̨��֤--userCode�Ƿ��Ѵ���
		//user.do?method=ucexist&userCode=**
		$.ajax({
			type:"GET",//��������
			url:path+"/jsp/user.do",//�����url
			data:{method:"ucexist",userCode:userCode.val()},//�������
			dataType:"json",//ajax�ӿڣ�����url�����ص���������
			success:function(data){//data���������ݣ�json����
				if(data.userCode == "exist"){//�˺��Ѵ��ڣ�������ʾ
					validateTip(userCode.next(),{"color":"red"},imgNo+ " ���û��˺��Ѵ��ڻ��ʽ���Ϸ�",false);
				}else{//�˺ſ��ã���ȷ��ʾ
					validateTip(userCode.next(),{"color":"green"},imgYes+" ���˺ſ���ʹ��",true);
				}
			},
			error:function(data){//������ʱ��404��500 �ȷ�200�Ĵ���״̬��
				validateTip(userCode.next(),{"color":"red"},imgNo+" �����ʵ�ҳ�治����",false);
			}
		});
		
		
	}).bind("focus",function(){
		//��ʾ������ʾ
		validateTip(userCode.next(),{"color":"#666666"},"* �û�����������¼ϵͳ���˺�",false);
	}).focus();
	
	userName.bind("focus",function(){
		validateTip(userName.next(),{"color":"#666666"},"* �û������ȱ����Ǵ���1С��10���ַ�",false);
	}).bind("blur",function(){
		if(userName.val() != null && userName.val().length > 1
				&& userName.val().length < 10){
			validateTip(userName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userName.next(),{"color":"red"},imgNo+" �û�������Ĳ����Ϲ淶������������",false);
		}
		
	});
	
	userPassword.bind("focus",function(){
		validateTip(userPassword.next(),{"color":"#666666"},"* ���볤�ȱ����Ǵ���6С��20",false);
	}).bind("blur",function(){
		if(userPassword.val() != null && userPassword.val().length > 6
				&& userPassword.val().length < 20 ){
			validateTip(userPassword.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userPassword.next(),{"color":"red"},imgNo + " �������벻���Ϲ淶������������",false);
		}
	});
	
	ruserPassword.bind("focus",function(){
		validateTip(ruserPassword.next(),{"color":"#666666"},"* ������������һֻ������",false);
	}).bind("blur",function(){
		if(ruserPassword.val() != null && ruserPassword.val().length > 6
				&& ruserPassword.val().length < 20 && userPassword.val() == ruserPassword.val()){
			validateTip(ruserPassword.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(ruserPassword.next(),{"color":"red"},imgNo + " �����������벻һ�£�����������",false);
		}
	});
	
	
	birthday.bind("focus",function(){
		validateTip(birthday.next(),{"color":"#666666"},"* ��������ѡ������",false);
	}).bind("blur",function(){
		if(birthday.val() != null && birthday.val() != ""){
			validateTip(birthday.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(birthday.next(),{"color":"red"},imgNo + " ѡ������ڲ���ȷ,����������",false);
		}
	});
	
	phone.bind("focus",function(){
		validateTip(phone.next(),{"color":"#666666"},"* �������ֻ���",false);
	}).bind("blur",function(){
		var patrn=/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if(phone.val().match(patrn)){
			validateTip(phone.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(phone.next(),{"color":"red"},imgNo + " ��������ֻ��Ÿ�ʽ����ȷ",false);
		}
	});
	
	userRole.bind("focus",function(){
		validateTip(userRole.next(),{"color":"#666666"},"* ��ѡ���û���ɫ",false);
	}).bind("blur",function(){
		if(userRole.val() != null && userRole.val() > 0){
			validateTip(userRole.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userRole.next(),{"color":"red"},imgNo + " ������ѡ���û���ɫ",false);
		}
	});
	
	addBtn.bind("click",function(){
		if(userCode.attr("validateStatus") != "true"){
			userCode.blur();
		}else if(userName.attr("validateStatus") != "true"){
			userName.blur();
		}else if(userPassword.attr("validateStatus") != "true"){
			userPassword.blur();
		}else if(ruserPassword.attr("validateStatus") != "true"){
			ruserPassword.blur();
		}else if(birthday.attr("validateStatus") != "true"){
			birthday.blur();
		}else if(phone.attr("validateStatus") != "true"){
			phone.blur();
		}else if(userRole.attr("validateStatus") != "true"){
			userRole.blur();
		}else{
			if(confirm("�Ƿ�ȷ���ύ����")){
				$("#userForm").submit();
			}
		}
	});
	
	backBtn.on("click",function(){
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