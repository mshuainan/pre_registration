var FcUtil = {
	
	hide : function(group) {
		$('td[group=' + group + ']').css('visibility', 'hidden');
	},
	
	show : function(group) {
		$('td[group=' + group + ']').css('visibility', 'visible');
	},
	
	toggle : function(group, isHidden) {
		if(isHidden)
			$('td[group=' + group + ']').css('visibility', 'hidden');
		else
			$('td[group=' + group + ']').css('visibility', 'visible');
	}
}