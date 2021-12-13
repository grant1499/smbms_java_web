var billCode = null;
var productName = null;
var productUnit = null;
var productCount = null;
var totalPrice = null;
var providerId = null;
var saveBtn = null;
var backBtn = null;

function priceReg (value){
	value = value.replace(/[^\d.]/g,"");  //��������֡��͡�.��������ַ�
		value = value.replace(/^\./g,"");  //��֤��һ���ַ������ֶ�����.
    value = value.replace(/\.{2,}/g,"."); //ֻ������һ��. ��������.
    value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//ȥ��������ţ�
	if(value.indexOf(".")>0){
		value = value.substring(0,value.indexOf(".")+3);
	}
	return value;
}


$(function(){
	billCode = $("#billCode");
	productName = $("#productName");
	productUnit = $("#productUnit");
	productCount = $("#productCount");
	totalPrice = $("#totalPrice");
	providerId = $("#providerId");
	addBtn = $("#save");
	backBtn = $("#back");
	
	//��ʼ����ʱ��Ҫ�����е���ʾ��Ϣ��Ϊ��* ����ʾ�����������Ҫд��ҳ����
	billCode.next().html("*");
	productName.next().html("*");
	productUnit.next().html("*");
	productCount.next().html("*");
	totalPrice.next().html("*");
	providerId.next().html("*");
	
	$.ajax({
		type:"GET",//��������
		url:path+"/jsp/bill.do",//�����url
		data:{method:"getproviderlist"},//�������
		dataType:"json",//ajax�ӿڣ�����url�����ص���������
		success:function(data){//data���������ݣ�json����
			if(data != null){
				var pid = $("#pid").val();
				$("select").html("");//ͨ����ǩѡ�������õ�select��ǩ��������ҳ����ֻ��һ��select
				var options = "<option value=\"0\">��ѡ��</option>";
				for(var i = 0; i < data.length; i++){
					//alert(data[i].id);
					//alert(data[i].proName);
					if(pid != null && pid != undefined && data[i].id == pid ){
						options += "<option selected=\"selected\" value=\""+data[i].id+"\" >"+data[i].proName+"</option>";
					}else{
						options += "<option value=\""+data[i].id+"\" >"+data[i].proName+"</option>";
					}
					
				}
				$("select").html(options);
			}
		},
		error:function(data){//������ʱ��404��500 �ȷ�200�Ĵ���״̬��
			validateTip(providerId.next(),{"color":"red"},imgNo+" ��ȡ��Ӧ���б�error",false);
		}
	});
	/*
	 * ��֤
	 * ʧ��\��
	 * jquery�ķ�������
	 */
	
	productName.on("focus",function(){
		validateTip(productName.next(),{"color":"#666666"},"* ��������Ʒ����",false);
	}).on("blur",function(){
		if(productName.val() != null && productName.val() != ""){
			validateTip(productName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(productName.next(),{"color":"red"},imgNo+" ��Ʒ���Ʋ���Ϊ�գ�����������",false);
		}
		
	});
	
	productUnit.on("focus",function(){
		validateTip(productUnit.next(),{"color":"#666666"},"* ��������Ʒ��λ",false);
	}).on("blur",function(){
		if(productUnit.val() != null && productUnit.val() != ""){
			validateTip(productUnit.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(productUnit.next(),{"color":"red"},imgNo+" ��λ����Ϊ�գ�����������",false);
		}
		
	});
	
	providerId.on("focus",function(){
		validateTip(providerId.next(),{"color":"#666666"},"* ��ѡ��Ӧ��",false);
	}).on("blur",function(){
		if(providerId.val() != null && providerId.val() != "" && providerId.val() != 0){
			validateTip(providerId.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(providerId.next(),{"color":"red"},imgNo+" ��Ӧ�̲���Ϊ�գ���ѡ��",false);
		}
		
	});
	
	productCount.on("focus",function(){
		validateTip(productCount.next(),{"color":"#666666"},"* ���������0������Ȼ����С�������2λ",false);
	}).on("keyup",function(){
		this.value = priceReg(this.value);
	}).on("blur",function(){
		this.value = priceReg(this.value);
	});
	
	totalPrice.on("focus",function(){
		validateTip(totalPrice.next(),{"color":"#666666"},"* ���������0������Ȼ����С�������2λ",false);
	}).on("keyup",function(){
		this.value = priceReg(this.value);
	}).on("blur",function(){
		this.value = priceReg(this.value);
	});
	
	addBtn.on("click",function(){
		productName.blur();
		productUnit.blur();
		providerId.blur();
		if(productName.attr("validateStatus") == "true" 
			&& productUnit.attr("validateStatus") == "true" 
			&& providerId.attr("validateStatus") == "true"){
			if(confirm("�Ƿ�ȷ���ύ����")){
				$("#billForm").submit();
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