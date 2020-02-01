var validator;
var $roleAddForm = $("#role-add-form");

$(function () {
    validateRule();
    createMenuTree();

    /**
     * 新增/修改
     */
    $("#role-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getMenu();
        var validator = $roleAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "role/create", $roleAddForm.serialize(), function (r) {
                    if (r.code === 200) {
                        closeModal();
                        $MB.n_success(r.message);
                        $MB.refreshTable("roleTable");
                    } else $MB.n_danger(r.message);
                });
            }
            if (name === "update") {
                $.post("role/edit", $roleAddForm.serialize(), function (r) {
                    if (r.code === 200) {
                        closeModal();
                        $MB.n_success(r.message);
                        $MB.refreshTable("roleTable");
                    } else $MB.n_danger(r.message);
                });
            }
        }
    });

    $("#role-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#role-add-button").attr("name", "save");
    $("#role-add-modal-title").html('新增角色');
    validator.resetForm();
    $MB.resetJsTree("menuTree");
    $MB.closeAndRestModal("role-add");
}

/**
 * 校验
 */
function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $roleAddForm.validate({
        rules: {
            roleKey: {
                required: true,
                minlength: 3,
                maxlength: 10,
                remote: {
                    url: "role/checkRoleKey",
                    type: "get",
                    dataType: "json",
                    data: {
                        roleKey: function () {
                            return $("input[name='roleKey']").val().trim();
                        }
                    }
                }
            },
            roleName: {
                maxlength: 50
            },
            menuId: {
                required: true
            }
        },
        messages: {
            roleKey: {
                required: icon + "请输入角色关键字",
                minlength: icon + "角色名称长度3到10个字符",
                remote: icon + "该角色关键字已经存在"
            },
            roleName: icon + "角色描述不能超过50个字符",
            menuId: icon + "请选择相应菜单权限"
        }
    });
}

/**
 * 菜单
 */
function createMenuTree() {
    $.get( "menu/menuButtonTree", {}, function (r) {
        if (r.code === 200) {
            var data = r.data;
            $('#menuTree').jstree({
                "core": {
                    'data': data.children
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

function getMenu() {
    var $menuTree = $('#menuTree');
    var ref = $menuTree.jstree(true);
    var menuIds = ref.get_checked();
    $menuTree.find(".jstree-undetermined").each(function (i, element) {
        menuIds.push($(element).closest('.jstree-node').attr("id"));
    });
    $("[name='menuId']").val(menuIds);
}