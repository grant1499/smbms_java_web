var proCode = null;
var proName = null;
var proContact = null;
var proPhone = null;
var addBtn = null;
var backBtn = null;

$(function(){
	proCode = $("#proCode");
	proName = $("#proName");
	proContact = $("#proContact");
	proPhone = $("#proPhone");
	addBtn = $("#add");
	backBtn = $("#back");
	//��ʼ����ʱ��Ҫ�����е���ʾ��Ϣ��Ϊ��* ����ʾ�����������Ҫд��ҳ����
	proCode.next().html("*");
	proName.next().html("*");
	proContact.next().html("*");
	proPhone.next().html("*");
	
	/*
	 * ��֤
	 * ʧ��\��
	 * jquery�ķ�������
	 */
	proCode.on("blur",function(){
		if(proCode.val() != null && proCode.val() != ""){
			validateTip(proCode.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proCode.next(),{"color":"red"},imgNo+" ���벻��Ϊ�գ�����������",false);
		}
	}).on("focus",function(){
		//��ʾ������ʾ
		validateTip(proCode.next(),{"color":"#666666"},"* �����빩Ӧ�̱���",false);
	}).focus();
	
	proName.on("focus",function(){
		validateTip(proName.next(),{"color":"#666666"},"* �����빩Ӧ������",false);
	}).on("blur",function(){
		if(proName.val() != null && proName.val() != ""){
			validateTip(proName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proName.next(),{"color":"red"},imgNo+" ��Ӧ�����Ʋ���Ϊ�գ�����������",false);
		}
		
	});
	
	proContact.on("focus",function(){
		validateTip(proContact.next(),{"color":"#666666"},"* ��������ϵ��",false);
	}).on("blur",function(){
		if(proContact.val() != null && proContact.val() != ""){
			validateTip(proContact.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proContact.next(),{"color":"red"},imgNo+" ��ϵ�˲���Ϊ�գ�����������",false);
		}
		
	});
	
	proPhone.on("focus",function(){
		validateTip(proPhone.next(),{"color":"#666666"},"* �������ֻ���",false);
	}).on("blur",function(){
		var patrn=/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if(proPhone.val().match(patrn)){
			validateTip(proPhone.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proPhone.next(),{"color":"red"},imgNo + " ��������ֻ��Ÿ�ʽ����ȷ",false);
		}
	});
	
	addBtn.bind("click",function(){
		if(proCode.attr("validateStatus") != "true"){
			proCode.blur();
		}else if(proName.attr("validateStatus") != "true"){
			proName.blur();
		}else if(proContact.attr("validateStatus") != "true"){
			proContact.blur();
		}else if(proPhone.attr("validateStatus") != "true"){
			proPhone.blur();
		}else{
			if(confirm("�Ƿ�ȷ���ύ����")){
				$("#providerForm").submit();
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