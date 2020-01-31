
function senAjax(data) {
    if (!data.data) {
        data.data={};
    }
    data.data.token=sessionStorage.getItem("token");
    $.ajax({
        url: data.url,
        data:data.data,
        dataType: data.dataType || "json",
        success: function(resp){
            if (resp.code == 9527) {
                layer.closeAll();
                layer.alert(resp.msg);
                return;
            }
            data.success(resp);
        }
    })
}