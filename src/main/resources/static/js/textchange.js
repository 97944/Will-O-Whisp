$('#div631').bind('textchange', function (event, previousText) {
    $('#div631-cd').html( 140 - parseInt($(this).val().length) );
});