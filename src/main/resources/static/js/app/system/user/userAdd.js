var validator;
var $userAddForm = $("#user-add-form");
var $rolesSelect = $userAddForm.find("select[name='rolesSelect']");
var $roles = $userAddForm.find("input[name='roles']");

$(function () {
    //用户名
    validateRule();
    //角色
    initRole();
    //部门
    createDeptTree();

    //用户locked
    $("input[name='locked']").change(function () {
        var checked = $(this).is(":checked");
        console.log('锁定:' + checked);
        var $status_label = $("#lockedTip");
        if (checked) $status_label.html('锁定账户');
        else $status_label.html('不锁定账户');
    });

    /**
     * 新增+修改
     */
    $("#user-add .btn-save").click(function () {
        //按钮
        var name = $(this).attr("name");
        //deptId
        getDept();
        //校验
        var validator = $userAddForm.validate();
        var flag = validator.form();
        if (flag) {
            //新增
            if (name === "save") {
                $.post(ctx + "user/create", $userAddForm.serialize(), function (r) {
                    if (r.code === 200) {
                        closeModal();
                        $MB.n_success(r.message);
                        $MB.refreshTable("userTable");
                    } else $MB.n_danger(r.message);
                });
            }
            //修改
            if (name === "update") {
                $.post(ctx + "user/edit", $userAddForm.serialize(), function (r) {
                    if (r.code === 200) {
                        closeModal();
                        $MB.n_success(r.message);
                        $MB.refreshTable("userTable");
                    } else $MB.n_danger(r.message);
                });
            }
        }
    });

    $("#user-add .btn-close").click(function () {
        closeModal();
    });

});

//关闭
function closeModal() {
    $("#user-add-button").attr("name", "save");
    validator.resetForm();
    $rolesSelect.multipleSelect('setSelects', []);
    $rolesSelect.multipleSelect("refresh");
    $userAddForm.find("input[name='userName']").removeAttr("readonly");
    $userAddForm.find(".user_password").show();
    $userAddForm.find("input[name='locked']").prop("checked", true);
    $("#user-add-modal-title").html('新增用户');
    $("#locked").html('锁定账户');
    $MB.resetJsTree("deptTree");
    $MB.closeAndRestModal("user-add");

}

/**
 * 表单校验-用户新增
 */
function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $userAddForm.validate({
        rules: {
            userName: {
                required: true,
                minlength: 3,
                maxlength: 10,
                remote: {
                    url: "user/verifyUserName",
                    type: "post",
                    dataType: "json",
                    data: {
                        userName: function () {
                            return $("input[name='userName']").val().trim();
                        }
                    }
                }
            },
            mail: {
                email: true
            },
            roles: {
                required: true
            },
            phone: {
                checkPhone: true
            },
            gender: {
                required: true
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
            userName: {
                required: icon + "请输入用户名",
                minlength: icon + "用户名长度3到10个字符",
                remote: icon + "用户名已经存在"
            },
            roles: icon + "请选择用户角色",
            mail: icon + "邮箱格式不正确",
            gender: icon + "请选择性别"
        }
    });
}

/**
 * 角色选择
 */
function initRole() {
    $.get("role/list", {}, function (r) {
        var data = r.rows;
        var option = "";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].roleId + "'>" + data[i].roleName + "</option>"
        }
        $rolesSelect.html("").append(option);
        var options = {
            selectAllText: '所有角色',
            allSelected: '所有角色',
            width: '100%',
            onClose: function () {
                $roles.val($rolesSelect.val());
                validator.element("input[name='roles']");
            }
        };

        $rolesSelect.multipleSelect(options);
    });
}


/**
 * 部门选择
 */
function createDeptTree() {
    $.get("dept/tree", {}, function (r) {
        if (r.code === 200) {
            var data = r.data;
            $('#deptTree').jstree({
                "core": {
                    'data': data.children,
                    'multiple': false // 取消多选
                },
                "state": {
                    "disabled": true
                },
                "checkbox": {
                    "three_state": false // 取消选择父节点后选中所有子节点
                },
                "plugins": ["wholerow", "checkbox"]
            });
        } else {
            $MB.n_danger(r.message);
        }
    })
}

//deptId赋值
function getDept() {
    var ref = $('#deptTree').jstree(true);
    $("[name='deptId']").val(ref.get_selected()[0]);
}