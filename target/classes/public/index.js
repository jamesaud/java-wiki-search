$(document).ready( function(){

    function get_path(start){
        $("#path").html("Trying to find the path...");

        $.get("/path?start=" + start, function(data){
            console.log(data);
            let result;

            if (data.errors){
                result = data.errors;
            }
            else{
                result = "";
                for (let i=0; i < data.result.length; i++){
                    let link = data.result[i];
                    if (i === data.result.length - 1){  // The last element, don't add an arrow
                        result += `<div style="display: block;">${link}</div>`;
                    }
                    else{
                        result += `<div style="display: block;">${link} -></div>`;
                    }
                }
            }
            $("#path").html(result);
        });}


    $("#wiki-form").submit(function(e){
        console.log("Called function");
        e.preventDefault();
        let val = $('#wiki-input').val();
        get_path(val);
    });
});