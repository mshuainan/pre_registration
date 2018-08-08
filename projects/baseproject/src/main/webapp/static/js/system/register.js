$(function() {
	$('#btnSubmitRegistration').click(doSubmit);
	toggle();
	$(":radio[name='domicileType']").change(function() {
		toggle();
	});
	$('#birthDate').datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear() - 6, 8, 1);
			return  date <= d1;
		}
	});
});

/** 下载PDF */
function exportPDF() {
	/*if(!!$('#studentIdentity').val()){
		exportExcelToUrl('/portalroot/newRegister/expRegPdf/' + $('#studentIdentity').val(), false);
	} else {
		Msg.showError('请填写正确的学生身份证号！');
	}*/
	//exportExcelToUrl('/portalroot/newRegister/expRegPdf/' + $('#studentIdentity').val(), false);
	if(!!$('#studentIdentity').val()){
		ajaxExecWithError('/portalroot/newRegister/expRegPdfValid/' + $('#studentIdentity').val(), null, function(){
			exportExcelToUrl('/portalroot/newRegister/expRegPdf/' + $('#studentIdentity').val(), false);
		},function() {
			
		}, true);
	} else {
		Msg.showError('请填写正确的学生身份证号！');
	}
	
}

/** 提交 */
function doSubmit() {
	//解除绑定， 确保不可重复提交
	/* $("#btnSubmitRegistration").unbind( "click" ); */
	/*alert($(":radio[name='domicileType']").val());
	if($(":radio[name='domicileType']").val() == "1"){
		$("#propertyOwner").val(null);
		$("#propertyOwnerName").val(null);
		$("#propertyCode").val(null);
		$("#propertySignDate").val(null);
	}*/
	$("#districtName").val($("#districtCode").combobox('getText'));
	$("#schoolName").val($("#schoolCode").combobox('getText'));
	easyUISubimitWhenError('registerForm', '/portalroot/newRegister/register',null,
		function() {
			
	}, true);
}

/** 隐藏和显示控制 */
function toggle() {
	if($(":radio[name='domicileType'][value=1]").prop('checked')) {
		$('td[group=domicileType1]').toggle(true);
		$('td[group=domicileType2]').toggle(false);
		/*$(":radio[name='domicileType'][value=1]").attr( "checked", false);*/
	} else {
		$('td[group=domicileType1]').toggle(false);
		$('td[group=domicileType2]').toggle(true);
		/*$(":radio[name='domicileType'][value=1]").attr( "checked", true );*/
	}
}

/** 上传学生头像 */
function changeStuFace() {
	PreviewImage($("input[name='studentFace']")[0], 'img2', 'img1');
}

/** 上传户口本和购房合同 */
function changeProFile() {
	PreviewImage($("input[name='propertyFile']")[0], 'img4', 'img3');
}

/** 上传显示 */
function PreviewImage(fileObj, imgPreviewId, divPreviewId) {
	var allowExtention = ".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;  
	var extention = fileObj.value.substring(fileObj.value.lastIndexOf(".") + 1).toLowerCase();
	var browserVersion = window.navigator.userAgent.toUpperCase();
	if (allowExtention.indexOf(extention) > -1) {
		if (fileObj.files) {//HTML5实现预览，兼容chrome、火狐7+等  
			if (window.FileReader) {
				var reader = new FileReader();
				reader.onload = function(e) {
					document.getElementById(imgPreviewId).setAttribute("src",e.target.result);
				}
				reader.readAsDataURL(fileObj.files[0]);
			} else if (browserVersion.indexOf("SAFARI") > -1) {
				alert("不支持Safari6.0以下浏览器的图片预览!");
			}
		} else if (browserVersion.indexOf("MSIE") > -1) {
			if (browserVersion.indexOf("MSIE 6") > -1) {//ie6  
				document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);
			} else {//ie[7-9]  
				fileObj.select();
				if (browserVersion.indexOf("MSIE 9") > -1)
					fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问  
				var newPreview = document.getElementById(divPreviewId + "New");
				if (newPreview == null) {
					newPreview = document.createElement("div");
					newPreview.setAttribute("id", divPreviewId + "New");
					newPreview.style.width = document.getElementById(imgPreviewId).width + "px";
					newPreview.style.height = document.getElementById(imgPreviewId).height+ "px";
					newPreview.style.border = "solid 1px #d2e2e2";
				}
				newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='"
						+ document.selection.createRange().text + "')";
				var tempDivPreview = document.getElementById(divPreviewId);
				tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);
				tempDivPreview.style.display = "none";
			}
		} else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox  
			var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
			if (firefoxVersion < 7) {//firefox7以下版本  
				document.getElementById(imgPreviewId).setAttribute("src",fileObj.files[0].getAsDataURL());
			} else {//firefox7.0+                      
				document.getElementById(imgPreviewId).setAttribute("src",window.URL.createObjectURL(fileObj.files[0]));
			}
		} else {
			document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);
		}
	} else {
		alert("仅支持" + allowExtention + "为后缀名的文件!");
		fileObj.value = "";//清空选中文件  
		if (browserVersion.indexOf("MSIE") > -1) {
			fileObj.select();
			document.selection.clear();
		}
		fileObj.outerHTML = fileObj.outerHTML;
	}
}