$(document).ready(function () {
    getDataTable();
});

var table;

var addReportRecordApp = new Vue({
    el:'#addReportRecordApp',
    data:{
        honorInfo:'',
    },
    methods:{
        get_honorInfo_id: function (id) {
            $.get("/api/manager_student/get_honorInfo_id",
                {
                    id: id
                },
                function (honorInfo) {
                    addReportRecordApp.honorInfo = honorInfo;
                });
        },
        selectHonorInfo: function(){
            $("#honorInfoApp").modal();
        },
        close_model: function () {
            $('#honorInfoApp').modal('hide');
        },
        getAnnex: function(){
            $("#annex").click();
            $("#annex").change(function() {
                $('#annexname').val($(this).val());
            });
        },
        add_reportRecord: function () {
            var formData = new FormData($("form")[0]);
            formData.append("honorInfoId",addReportRecordApp.honorInfo.id);
            $.ajax({
                url: '/api/student/add_reportRecord',
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false,
                dataType: "json",
                success: function (data) {
                    if (data.message === "add reportRecord success") {
                        alert("申报成功");
                        location.reload();
                    }
                },
                error: function (XMLResponse) {
                    alert(XMLResponse.responseText);
                }
            });
        },
        button_confirm:function (status) {
            if (confirm("是否确认您的操作") === true) {
                this.add_reportRecord();
               /* alert(1);*/
            }
        },
        /*button_confirm:function () {
            if (confirm("是否确认您的操作") === true) {
                this.add_reportRecord();
            }
        },*/
        reset_data:function () {
            addReportRecordApp.honorInfo = {};
            addReportRecordApp.annex = '';
            addReportRecordApp.annexname = '';
        },
    }
});

function getDataTable() {
    table = $("#show_honor_table").DataTable(
    {
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        responsive: true,
        bAutoWidth: true,
        processing: true,
        serverSide: false,
        aLengthMenu: [5, 10, 25, 50], //更改显示记录数选项
        ajax: {
            url: "/api/student/get_honorInfo_all",
            type: 'GET',
            dataSrc: ''
        },
        columns: [
            {data: "id"},
            {data: "level"},
            {data: "kind"},
            {data: "name"},
            {data: "rank"},
            {data: "year"}
        ],
        columnDefs: []
    });

    $('#show_honor_table tbody').on('click', 'tr', function () {
        // console.log(table.row(this).data());
        var honor = table.row(this).data();
        addReportRecordApp.get_honorInfo_id(honor.id);
        $('#honorInfoApp').modal('hide');
    });
}