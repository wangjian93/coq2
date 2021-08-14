/**
 * @author:  wangjian
 * @time:    2021-06-25
 * @content: 自定义选择弹窗
 */

"use strict";

let selectDialog = function(options) {
    // 解构赋值
    let {
        id,
        id_,
        title = "请选择",
        type = 0,
        easyClose = false,
        form,
        select = noSelectFunc,
        afterInit = noAfterInit,

        url,
        cols = [],
        data = [],
        callback = function(d) {
            $("#"+id_).val(JSON.stringify(d));
        }
    } = options;

    checkValue(id, form); //检查数据格式
    id_ = id;
    id = 'id'+"_Dialog";
    initSelectDialog(id, title); //初始化
    renderSelectTable(id, data, cols, url, callback); //渲染数据表格
    afterInit();
    openDialog(id);

    //关闭窗口被点击
    $("#" + id + " .btnCancel").bind("click", {
        id: id
    }, function(event) {
        //执行关闭对话框函数
        closeDialog(event.data.id);
    });

    $("." + "black_overlay").bind("click", {
        id: id
    }, function(event) {
        //执行关闭对话框函数
        closeDialog(event.data.id);
    });


};

// function initSelectDialog(id, title) {
//     let str = `
//         <div id="${id}" class="box white_content">
//             <div class="dialog-card">
//                 <div class="dialog-head">
//                     <h4>${title}</h4>
//                 </div>
//                 <div class="dialog-body">
//                     <div>
//                         <input/>
//                         <button>搜索</button>
//                     </div>
//                     <div>
//                         <table id="${id}_table"></table>
//                     </div>
//                 </div>
//             </div>
//         </div>
//         <div class="black_overlay fade"></div>
//      `;
//     let element = document.createElement('div');
//     element.innerHTML = str;
//     $("body")[0].appendChild(element);
// }

function initSelectDialog(id, title) {
    let str = `
        <div id="${id}" class="box white_content">
            <div class="layui-card">
                <div class="layui-card-header" style="text-align: left;">
                    <i class="layui-icon layui-icon-tabs"></i><span>${title}</span>
                </div>
                <div class="layui-card-body">
                    <div style="text-align: left">
                        <div class="layui-inline">
                            <label class="layui-form-label" style="padding: 5px 0px;">筛选条件：</label>
                            <div class="layui-input-inline">
                                <input class="layui-input" style="height: 30px;width: 240px;" name="search" autocomplete="off">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button type="button" class="layui-btn layui-btn-normal layui-btn-sm" name="search_button"><i class="layui-icon"></i> 搜 索</button>
                        </div>
                    </div>
                     <table id="${id}_table" lay-filter="${id}_table"></table>
                </div>
            </div>
        </div>
        <div class="black_overlay fade"></div>
     `;
    let element = document.createElement('div');
    element.innerHTML = str;
    $("body")[0].appendChild(element);
}

function renderSelectTable(id, data, cols, url, callback) {
    if((!data || data.length===0) && url) {
        url = addArgToUrl(url, 'search', '');
        $.ajax({
            url: url,
            async: false,
            success: function(d){
                data = d.data
            }
        });
    }

    if(!cols || cols.length === 0) {
        if(data.length > 0) {
            var data_ = data[0];
            for( var key in data_) {
                console.log(key);
                cols.push({
                    field: key,
                    title: key
                });
            }
        }
    }

    createTable(id, data, cols, url, callback);
}

function createTable(id, data, cols, url, callback) {
    layui.use(['table'], function() {
        var table = layui.table;
        var cols_ = [{type: 'numbers', align: 'center'}];
        for(var i=0; i<cols.length; i++) {
            cols[i].align = 'center';
            if(!cols[i].width) {
                cols[i].width = 150;
            }
            cols_.push(cols[i]);
        }
        table.render({
            elem: '#'+id+'_table',
            data: data,
            cols: [cols_],
            height: 460,
            page: {layout: ['prev', 'count', 'next']},
            size: 'sm',
            limit: 50
        });
        //行单击事件
        table.on('row('+id+'_table)', function(obj){
            callback(obj.data);
            closeDialog(id);
        });

        //绑定条件筛选
        $("#"+id+" [name='search_button']").click(function() {
            var search = $("#"+id+" [name='search']").val();
            url = addArgToUrl(url, 'search', search);
            $.get(url, function(d) {
                table.reload(id+'_table', {data: d.data});
            });
        });

        $("#"+id+" [name='search']").keydown(function(e) {
            if (e.keyCode == 13) {
                var search = $("#"+id+" [name='search']").val();
                url = addArgToUrl(url, 'search', search);
                $.get(url, function(d) {
                    table.reload(id+'_table', {data: d.data});
                });
            }
        });
    });
}


function openDialog(id) {
    let element = "#" + id;
    $(element)[0].style.display = "block";
    $(element).next()[0].style.display = "block";
}

function closeDialog(id) {
    let element = "#" + id;
    $(element)[0].style.display = "none";
    $(element).next()[0].style.display = "none";

    $(element).next()[0].remove();
    $(element)[0].remove();

}


/*函数功能 判断是否为空*/
function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}


//函数功能 把form表单转化成对象
function serilizeToJSON(form) {
    let result = {};
    let arr = form.serializeArray();
    arr.forEach((item) => {
        result[item.name] = item.value;
    })
    return result;
}

//函数功能 检查数据格式
function checkValue(id, form) {
    //格式检查
    if (isEmpty(id)) {
        try {
            throw new Error('你必须初始化时设定id的值');
        } catch (e) {
            console.error(e.name + ': ' + e.message);
        }
    }

    if (typeof id !== "string") {
        try {
            throw new Error('id的值的数据类型必须是字符串');
        } catch (e) {
            console.error(e.name + ': ' + e.message);
        }
    }
}

//函数功能: 把form表单里的数据清空
function clearAllData(id) {
    let obj = $("#" + id + " form input");
    for (var i = 0; i < obj.length; i++) {
        obj[i].value = "";
    }
}


//函数功能：向下移动bottom_btn的位置
function moveBtn(id, height) {
    let obj = $("#" + id + " .bottom_btn")[0];
    obj.style.top = "" + height + "px";
}


function noSelectFunc() {
    console.log("您初始化时没有设置submit参数!!!");
}


function noAfterInit() {
    // console.log("您初始化时没有设置AfterInit参数!!!");
}

function renderTable() {

}

//向url添加参数
function addArgToUrl(url, paramName, replaceWith){
    //url字符串添加参数
    //url:路径地址 paramName：参数名 replaceWith：参数值
    if (url.indexOf(paramName) > -1) {
        var re = eval('/(' + paramName + '=)([^&]*)/gi');
        url = url.replace(re, paramName + '=' + replaceWith);
    } else {
        var paraStr = paramName + '=' + replaceWith;

        var idx = url.indexOf('?');
        if (idx < 0)
            url += '?';
        else if (idx >= 0 && idx != url.length - 1)
            url += '&';
        url=url + paraStr;
    }
    return url;
}

