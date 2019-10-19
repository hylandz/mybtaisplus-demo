$(function () {
    var $userTableForm = $(".user-table-form");
    //前台页面参数
    var settings = {
        url: "user/list",
        pageSize: 10,
        //请求参数
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                userName: $userTableForm.find("input[name='userName']").val().trim(),
                gender: $userTableForm.find("select[name='gender']").val(),
                locked: $userTableForm.find("select[name='locked']").val()
            };
        },
        //前台显示页面设置
        columns: [{
            checkbox: true
        }, {
            field: 'userId',
            visible: false
        }, {
            field: 'userName',
            title: '用户名'
        }, {
            field: 'deptName',
            title: '部门'
        }, {
            field: 'mail',
            title: '邮箱'
        }, {
            field: 'phone',
            title: '手机'
        }, {
            field: 'gender',
            title: '性别',
            formatter: function (value, row, index) {
                if (value === 1) return '男';
                else if (value === 0) return '女';
                else return '保密';
            }
        }, {
            field: 'gmtCreate',
            title: '创建时间'
        }, {
            field: 'locked',
            title: '状态',
            formatter: function (value, row, index) {
                if (value === false){ return '<span class="badge badge-success">有效</span>';}
                if (value === true){return '<span class="badge badge-warning">锁定</span>';}
            }
        }

        ]
    };

    //表单初始化
    $MB.initTable('userTable', settings);
});

/**
 * 条件查询
 */
function search() {
    $MB.refreshTable('userTable');
}

/**
 * 刷新
 */
function refresh() {
    $(".user-table-form")[0].reset();
    $MB.refreshTable('userTable');
}

/**
 * 删除用户
 */
function deleteUsers() {
    var selected = $("#userTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的用户！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].userId;
        if (i !== (selected_length - 1)) ids += ",";
            if (userName === selected[i].userName) contain = true;
    }
    if (contain) {
        $MB.n_warning('勾选用户中包含当前登录用户，无法删除！');
        return;
    }

    $MB.confirm({
        text: "确定删除选中用户？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post('user/remove', {"ids": ids}, function (r) {
            if (r.code === 200) {
                $MB.n_success(r.message);
                refresh();
            } else {
                $MB.n_danger(r.message);
            }
        });
    });
}


/**
 * 导出Excel格式
 */
function exportUserExcel() {
    $.post("user/excel", $(".user-table-form").serialize(), function (r) {
        if (r.code === 200) {
            $MB.n_success(r.message);
            //window.location.href = "common/download?fileName=" + r.message + "&delete=" + true;
        } else {
            $MB.n_warning(r.message);
        }
    });
}


/**
 * 导出csv格式
 */
function exportUserCsv() {
    $.post("user/csv", $(".user-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}