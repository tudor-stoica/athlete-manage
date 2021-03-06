$(document).ready(function(){
    $('.sidenav').sidenav();
    $('.tooltipped').tooltip();
    $('select').formSelect();
    $('.datepicker').datepicker();

    $("button#delete").click(function () {
        var url = $(this).data("url");
        var rUrl = $(this).data("r-url");
        if (confirm("Really delete?"))
            sendDeleteRequest(url, rUrl);
    });

    $("button#update").click(function () {
        var formId = $(this).data("form-id");
        var rUrl = $(this).data("r-url");
        sendPutRequest(formId, rUrl);
    });
});

function sendDeleteRequest(url, rUrl) {
    $.ajax({
        url: url,
        method: "DELETE",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function () {
            window.location = rUrl;
        },
        error: function () {
            window.location.reload();
        }
    });
}

function sendPutRequest(formId, rUrl) {
    var form = $('#' + formId);
    $.ajax({
        url: form.attr('action'),
        type: 'PUT',
        data: form.serialize(),
        success: function () {
            window.location = rUrl;
        },
        error: function () {
            window.location.reload();
        }
    });
}