var validator;
var $userProfileForm = $("#update-profile-form");
$(function () {
    $(".profile__img").find("img").attr("src", avatar);
    $("#update-profile").bind('hide.bs.modal', function () {
        $(".modal-backdrop").remove();
    });
    $("#default-avatars").bind('hide.bs.modal', function () {
        $(".modal-backdrop").remove();
    });
    $("#update-profile .btn-close").click(function () {
        $MB.closeAndRestModal("update-profile");
        validator.resetForm();
        $MB.resetJsTree("deptTree");
    });
    $(".default_avatars_list").find("img").each(function () {
        var $this = $(this);

        /**
         * 个人信息的头像设置
         */
        $this.on("click", function () {
            var target_src = $(this).attr("src");
            $.post(ctx + "user/changeAvatar", {"imgName": target_src}, function (r) {
                if (r.code === 200) {
                    $("#close_update_avatar_button").trigger("click");
                    $MB.n_success(r.message);
                    refreshUserProfile();
                    $(".user__img").attr("src", ctx + target_src);
                } else $MB.n_danger(r.message);
            });
        });
    });
    $(".profile__img__edit").on('click', function () {
        $('#default-avatars').modal();
    });
    validateRule();
    createDeptTree();

    /**
     * 修改资料
     */
    $("#update-profile .btn-save").click(function () {
        //deptId
        getDept();
        var validator = $userProfileForm.validate();
        var flag = validator.form();
        console.log('validate='+flag);
        if (flag) {
            $.post(ctx + "user/updateUserProfile", $userProfileForm.serialize(), function (r) {
                if (r.code === 200) {
                    $("#update-profile .btn-close").trigger("click");
                    $MB.n_success(r.message);
                    refreshUserProfile();
                } else $MB.n_danger(r.message);
            });
        }
    });

});

/**
 * 修改资料后刷新个人信息
 */
function refreshUserProfile() {
    $.get(ctx + "user/profile", function (r) {
        $main_content.html('').append(r);
    });
}

/**
 * 点击[编辑资料]时获取当前用户的数据
 */
function editUserProfile() {
    $.get(ctx + "user/getUserProfile", {"userId": userId}, function (r) {
        if (r.code === 200) {
            var $form = $('#update-profile');
            var $deptTree = $('#deptTree');
            $form.modal();
            var user = r.data;
            $form.find("input[name='userName']").val(user.userName).attr("readonly", true);
            $form.find("input[name='oldusername']").val(user.userName);
            $form.find("input[name='userId']").val(user.userId);
            $form.find("input[name='mail']").val(user.mail);
            $form.find("input[name='phone']").val(user.phone);
            $form.find("input[name='description']").val('这个人很懒,什么也没留下');
            $("input:radio[value='" + user.gender + "']").attr("checked", true);
            $deptTree.jstree().open_all();
            $deptTree.jstree('select_node', user.deptId, true);
        } else {
            $MB.n_danger(r.message);
        }
    });
}

/**
 * 验证规则-profile
 */
function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i>";
    validator = $userProfileForm.validate({
        rules: {
            mail: {
                email: true
            },
            phone: {
                checkPhone: true
            },
            gender: {
                required: true
            },
            description: {
                maxlength: 100
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
            mail: icon + "邮箱格式不正确",
            gender: icon + "请选择性别",
            description: icon + "个人描述不能超过100个字符"
        }
    });
}

/**
 * 点击编辑资料时显示部门树结构
 */
function createDeptTree() {
    $.get("dept/tree", {}, function (r) {
        if (r.code === 200) {
            var data = r.data;
            $('#deptTree').jstree({
                "core": {
                    'data': data.children,
                    'multiple': false
                },
                "state": {
                    "disabled": true
                },
                "checkbox": {
                    "three_state": false
                },
                "plugins": ["wholerow", "checkbox"]
            });
        } else {
            $MB.n_danger(r.message);
        }
    })

}

/**
 * 表单元素name="deptId"赋值
 */
function getDept() {
    var ref = $('#deptTree').jstree(true);
    $("[name='deptId']").val(ref.get_selected()[0]);
}

