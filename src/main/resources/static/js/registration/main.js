$(document).ready(function (){
    $("#resendButtton").click(function (event){
    event.preventDefault();
        var user_id = $(this).attr("data");
    $.ajax({
        url: 'http://localhost:8888/resendVerificationCode/'+user_id,
        type: 'PUT',
        dataType: "json",
        success: function (response) {
            location.reload(true);
            alert("Verification Code sent to your email!");
            },
        error: function () {
            alert('Error while sending verification Code');
        }
    });

});

    $("#sendPassword").click(function (event){
       event.preventDefault();
       var emailAddress = document.getElementById("resetPass").value

        $.ajax({
            url: 'http://localhost:8888/user/sendEmail/'+emailAddress,
            type: 'PUT',
            dataType: "json",
            success: function (response) {
                location.reload(true);
                alert("Password changing code sent to your email....");
            },
            error: function () {
                alert('Error while sending verification Code');
            }
        });

    });
    $("#showError").hide();

    $("#secondPass").keyup(function (){
       let passOne = $("#firstPass").val();
       let passTwo = $("#secondPass").val();
       if (passOne != passTwo){
           $("#showError").show();
       }else{
           $("#showError").hide();
       }

    });

});