var validateUpdatePassword;
var $updatePasswordForm = $("#update-password-form");

$(function () {
    validateUpdatePasswordRule();

    /**
     * 修改密码点击事件
     */
    $("#update-password .btn-save").click(function () {
        //验证
        validateUpdatePassword = $updatePasswordForm.validate();
        var flag = validateUpdatePassword.form();
        if (flag) {
            $.post("user/updatePassword", $updatePasswordForm.serialize(), function (r) {
                if (r.code === 200) {
                    validateUpdatePassword.resetForm();
                    $MB.closeAndRestModal("update-password");
                    $MB.n_success(r.message);
                } else $MB.n_danger(r.message);
            });
        }
    });

    //关闭
    $("#update-password .btn-close").click(function () {
        validateUpdatePassword.resetForm();
        $MB.closeAndRestModal("update-password");
    });

});


/**
 * 表单验证
 */
function validateUpdatePasswordRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    //验证原始密码
    validateUpdatePassword = $updatePasswordForm.validate({
        rules: {
            originPwd: {
                required: true,
                remote: {
                    url: "user/verify",
                    type: "post",
                    dataType: "json",
                    data: {
                        originPwd: function () {
                            return $("input[name='originPwd']").val().trim();
                        }
                    }
                }
            },
            newPassword: {
                required: true,
                minlength: 6
            },
            confirm: {
                required: true,
                equalTo: "#newPassword"
            }
        },
        messages: {
            originPwd: {
                required: icon + "请输入原密码",
                remote: icon + "原密码错误"
            },
            newPassword: {
                required: icon + "请输入新密码",
                minlength: icon + "密码不能小于6个字符"
            },
            confirm: {
                required: icon + "请再次输入新密码",
                equalTo: icon + "两次密码输入不一致"
            }

        }
    });
}