/*年月二*/
$.extend($.fn.combobox.methods, {
    yearAndMonth: function (jq) {
        return jq.each(function () {
            var obj = $(this).combobox();
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var table = $('<table>');
            var tr1 = $('<tr>');
            var tr1td1 = $('<td>', {
                text: '-',
                click: function () {
                    var y = $(this).next().html();
                    y = parseInt(y) - 1;
                    $(this).next().html(y);
                }
            });
            tr1td1.appendTo(tr1);
            var tr1td2 = $('<td>', {
                text: year
            }).appendTo(tr1);

            var tr1td3 = $('<td>', {
                text: '+',
                click: function () {
                    var y = $(this).prev().html();
                    y = parseInt(y) + 1;
                    $(this).prev().html(y);
                }
            }).appendTo(tr1);
            tr1.appendTo(table);

            var n = 1;
            for (var i = 1; i <= 4; i++) {
                var tr = $('<tr>');
                for (var m = 1; m <= 3; m++) {
                    var td = $('<td>', {
                        text: n,
                        click: function () {
                            var yyyy = $(table).find("tr:first>td:eq(1)").html();
                            var cell = $(this).html();
                            var v = yyyy + '-' + (cell.length < 2 ? '0' + cell : cell);
                            obj.combobox('setValue', v).combobox('hidePanel');

                        }
                    });
                    if (n == month) {
                        td.addClass('tdbackground');
                    }
                    td.appendTo(tr);
                    n++;
                }
                tr.appendTo(table);
            }
            table.addClass('mytable cursor');
            table.find('td').hover(function () {
                $(this).addClass('tdbackground');
            }, function () {
                $(this).removeClass('tdbackground');
            });
            table.appendTo(obj.combobox("panel"));

        });
    }
});


/*年月一*/
function yyyyMM(obj){
	   $('#'+obj).datebox({
           onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
               span.trigger('click'); //触发click事件弹出月份层
               if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                   tds = p.find('div.calendar-menu-month-inner td');
                   tds.click(function (e) {
                       e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                       var year = /\d{4}/.exec(span.html())[0]//得到年份
                       , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                       $('#'+obj).datebox('hidePanel')//隐藏日期对象
                       .datebox('setValue', year + '-' + month); //设置日期的值
                   });
               }, 0);
               yearIpt.unbind();//解绑年份输入框中任何事件
           },
           parser: function (s) {
               if (!s) return new Date();
               var arr = s.split('-');
               return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
           },
           formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);/*getMonth返回的是0开始的，忘记了。。已修正*/ }
       });
       var p = $('#'+obj).datebox('panel'), //日期选择对象
       tds = false, //日期选择对象中月份
       yearIpt = p.find('input.calendar-menu-year'),//年份输入框
       span = p.find('span.calendar-text'); //显示月份层的触发控件
       //console.log(yearIpt)

}
         