layui.use('table', function(){
    var table = layui.table,
        $ = layui.$;

    table.render({
        elem: '#fileList'
        ,url:'http://localhost:8080/FileList'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,cols: [[
            {field:'uid', width: '30%', title: 'uid'}
            ,{field:'oldname', width:150, title: '文件名'}
            ,{field:'createAt', width:200, title: '上传时间', sort: true}
            ,{field:'filetype', width:200, title: '文件类型', sort: true}
            ,{field:'fileSize',minWidth: 100, title: '文件大小(KB)',templet: function(res){
                    return res.fileSize +'KB'
                }}
            ,{fixed: 'right', title:'操作', toolbar: '#bar', width:150}
        ]]
        // ,done: function(res, curr, count){
        //     //如果是异步请求数据方式，res即为你接口返回的信息。
        //     //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        //     console.log(res.data);
        //
        //     //得到当前页码
        //     console.log(curr);
        //
        //     //得到数据总量
        //     console.log(count);
        // }
    });


    //监听行工具事件
    table.on('tool(fileList)', function(obj){
        var data = obj.data;

        if(obj.event === 'download'){
            layer.confirm('是否下载文件', function(index){
                $.ajax({
                    type: 'get',
                    url: "/download",//发送请求
                    data: {"Uid":data.uid,"filetype":data.filetype},
                    success: function (result) {
                        console.log(result);
                    }
                });
                layer.close(index);
            });
        }
    });

});