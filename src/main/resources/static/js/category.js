(function ($) {
    $.fn.serializeFormJSON = function () {

        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);

$(document).ready(function() {
    $("#categorySubmitBtn").click(function() {
        let data = JSON.stringify($("#categoryForm").serializeFormJSON());
        $.ajax({
            type : "POST",
            url : "/category_save",
            data : data,
            contentType: "application/json",
            dataType : "json",
            success : function(data){
                $("#categoryAddForm")[0].reset();
                $("#result").empty();
                $('#result').append('<H3 align="center"> OK!! <H3><p>').show();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.responseJSON);
                $("#result").empty();

                if (XMLHttpRequest.responseJSON.errorType == "ValidationError") {
                    let errorMsg = '<h3> Error(s)!! </h3>';
                    errorMsg += "<p>";
                    var errorList = XMLHttpRequest.responseJSON.fieldErrors;
                    $.each(errorList, function(i, error) {
                        errorMsg = errorMsg +error.message + '<br>';
                    });
                    errorMsg += '</p>';
                    $('#result').append(errorMsg);
                    $('#result').show();
                } else {
                    alert(errorObject.responseJSON.errors(0)); // "non" Validation
                }
            }
        });
    });
});