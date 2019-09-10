function updateUser() {
    var selected = $("#userTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的用户！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个用户！');
        return;
    }
    var userId = selected[0].userId;
    //点击修改用户时,数据显现
    $.get("user/getUser", {"userId": userId}, function (r) {
        if (r.code === 200) {
            var $form = $('#user-add');
            var $deptTree = $('#deptTree');
            $form.modal();
            var user = r.data;
            $form.find(".user_password").hide();
            $("#user-add-modal-title").html('修改用户');
            $form.find("input[name='userName']").val(user.userName).attr("readonly", true);
            //$form.find("input[name='oldusername']").val(user.username);
            $form.find("input[name='userId']").val(user.userId);
            $form.find("input[name='mail']").val(user.mail);
            $form.find("input[name='phone']").val(user.phone);
            var roleArr = [];
            for (var i = 0; i < user.roleIdList.length; i++) {
                roleArr.push(user.roleIdList[i]);
            }
            $form.find("select[name='rolesSelect']").multipleSelect('setSelects', roleArr);
            $form.find("input[name='roles']").val($form.find("select[name='rolesSelect']").val());
            var $locked = $form.find("input[name='locked']");
            console.log('用户锁定的:' + user.locked);
            if (user.locked === true) {
                $locked.prop("checked", true);
                $locked.parent().next().html('用户锁定');
            } else {
                $locked.prop("checked", false);
                $locked.parent().next().html('用户未锁定');
            }
            $("input:radio[value='" + user.gender + "']").prop("checked", true);
            $deptTree.jstree().open_all();
            $deptTree.jstree('select_node', user.deptId, true);
            $("#user-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.message);
        }
    });
}