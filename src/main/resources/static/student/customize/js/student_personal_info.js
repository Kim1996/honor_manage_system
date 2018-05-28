var studentInfoApp = new Vue({
    el:'#studentInfoApp',
    data:{
        changePassword: {},
        newPasswordAgain:'',
    },
    methods:{
        ModifyPassword: function(){
            $("#psdResetApp").modal();
        },
        close_model: function () {
            $('#psdResetApp').modal('hide');
        },
        change_password: function () {
            if (this.verification_password) {

                studentInfoApp.changePassword.newPassword
                    = md5(md5(studentInfoApp.changePassword.newPassword) + '8');
                studentInfoApp.changePassword.newPassword
                    = md5(md5(studentInfoApp.changePassword.newPassword) + '8');


                studentInfoApp.changePassword.oldPassword
                    = md5(md5(studentInfoApp.changePassword.oldPassword) + '8');
                studentInfoApp.changePassword.oldPassword
                    = md5(md5(studentInfoApp.changePassword.oldPassword) + '8');

                $.ajax({
                    url: '/api/student/change_studentPassword',
                    type: 'POST',
                    data: JSON.stringify(studentInfoApp.changePassword, null, 4),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data) {
                        if (data.message === "change password success") {
                            alert("修改成功");
                            studentInfoApp.reset_data();
                        }else if(data.message === "old password error"){
                            alert("原密码错误");
                        }
                    },
                    error: function (XMLResponse) {
                        alert(XMLResponse.responseText);
                    }
                });
            }
        },
        verification_password: function () {
            if (this.changePassword.newPassword === this.newPasswordAgain) {
                return true;
            }
            else{
                alert("修改密码与确认密码不一致!");
                return false;
            }
        },
        reset_data:function () {
            studentInfoApp.changePassword = {};
            studentInfoApp.newPasswordAgain = '';
        },
    }
});
