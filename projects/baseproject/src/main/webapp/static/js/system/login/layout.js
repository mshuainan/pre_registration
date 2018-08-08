$(function() {
	initStyle();
	mainPlatform.init();
	ContentArea.init();
});

function initStyle() {
	$('.easyui-tabs1').tabs({
      tabHeight: 36,
      onSelect:function(title,index){
    	  
      }
	});
	 
    $(window).resize(function(){
          $('.tabs-panels').height($("#pf-page").height()-46);
          $('.panel-body').height($("#pf-page").height()-76)
    }).resize();
	 
	var page = 0, pages = ($('.pf-nav').height() / 70) - 1;
	if(pages === 0){
		$('.pf-nav-prev,.pf-nav-next').hide();
    }
	$(document).on('click', '.pf-nav-prev,.pf-nav-next', function() {
		if ($(this).hasClass('disabled'))
			return;
		if ($(this).hasClass('pf-nav-next')) {
			page++;
			$('.pf-nav').stop().animate({'margin-top' : -70 * page}, 200);
			if (page == pages) {
				$(this).addClass('disabled');
				$('.pf-nav-prev').removeClass('disabled');
			}else{
		        $('.pf-nav-prev').removeClass('disabled');
	        }

		} else {
			page--;
			$('.pf-nav').stop().animate({'margin-top' : -70 * page}, 200);
			if (page == 0) {
				$(this).addClass('disabled');
				$('.pf-nav-next').removeClass('disabled');
			}else{
		        $('.pf-nav-next').removeClass('disabled');
	        }
		}
	});
};

function show(msg){  
	 $.messager.show({
	     title:I18N.getText('common.systemannounce'),
	     msg:msg,
	     timeout:0,
	     height:250,
	     width:250,
	     showType:'show'
	 });
} 

function logout() {
	window.location = CtxVar.path + '/system/logout';
};

var mainPlatform = {
	init: function(){
		this.bindEvent();
	},

	bindEvent: function(){
		var self = this;
		// 顶部大菜单单击事件
		$(document).on('click', '.pf-nav-item', function() {
            $('.pf-nav-item').removeClass('current');
            $(this).addClass('current');

            $("div[submenu='true']").hide();
            var parentId = $(this).data('menu');
            $("div[parentId='" + parentId + "']").show();
        });
		
		//二级菜单单击事件
		$(document).on('click', 'a[level="second"]', function() {
			$('.sider-nav li').removeClass('current');
			$(this).parents('li').addClass('current');
			
			if(!!$(this).data('url')) {
				addOrSelectTab($(this).data('text'), $(this).data('url'));
			}
		});

		//三级菜单单击事件
		$(document).on('click', 'a[level="third"]', function() {
			$('.sider-nav-s li').removeClass('active');
			$(this).parents('li').addClass('active');
			addOrSelectTab($(this).data('text'), $(this).data('url'));
		});
		
		//左侧菜单收起
        $(document).on('click', '.toggle-icon', function() {
            $(this).closest("#pf-bd").toggleClass("toggle");
            setTimeout(function(){
            	$(window).resize();
            },300)
        });
	}
};

function addOrSelectTab(text, url) {
	if ($("#tabs").tabs('exists', text)) {
		$('#tabs').tabs('select', text);
	} else {
		addTab('tabs', text, url, true);
    }
};

/** 工作区域 */
var ContentArea = {
	init : function() {
		// 绑定tabs的右键菜单
		$("#tabs").tabs({
			onContextMenu : function(e, title) {
				e.preventDefault();
				if ('首页' != title) {
					$('#tabsMenu').menu('show', {
						left : e.pageX,
						top : e.pageY
					}).data("tabTitle", title);
				}
			},
		});
		addTab("tabs","注册信息","/portalroot/newRegister/index", false);
		// 实例化menu的onClick事件
		$("#tabsMenu").menu({
			onClick : function(item) {
				ContentArea.closeTab(this, item.name);
			}
		});
	},
	
	closeTab : function(menu, type) {
	    var curTabTitle = $(menu).data("tabTitle");
	    var tabs = $("#tabs");
	    if (type === "close") {
	        tabs.tabs("close", curTabTitle);
	        return;
	    }
	    var allTabs = tabs.tabs("tabs");
	    var closeTabsTitle = [];
	    $.each(allTabs, function () {
	        var opt = $(this).panel("options");
	        if (opt.closable && opt.title != curTabTitle && type === "Other") {
	            closeTabsTitle.push(opt.title);
	        } else if (opt.closable && type === "All") {
	            closeTabsTitle.push(opt.title);
	        }
	    });
	    for (var i = 0; i < closeTabsTitle.length; i++) {
	        tabs.tabs("close", closeTabsTitle[i]);
	    }
	}
};