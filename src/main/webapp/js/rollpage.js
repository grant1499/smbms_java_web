function page_nav(frm,num){
		frm.pageIndex.value = num;
		frm.submit();
}

function jump_to(frm,num){
    //alert(num);
	//��֤�û�������
	var regexp=/^[1-9]\d*$/;
	var totalPageCount = document.getElementById("totalPageCount").value;
	//alert(totalPageCount);
	if(!regexp.test(num)){
		alert("���������0����������");
		return false;
	}else if((num-totalPageCount) > 0){
		alert("������С����ҳ����ҳ��");
		return false;
	}else{
		page_nav(frm,num);
	}
}