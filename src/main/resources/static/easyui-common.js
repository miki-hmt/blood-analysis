var dialog_obj = {
    title: '窗口',
    width: '60%',
    height: '80%',
    closed: false,
    cache: false,
    modal: true
};

var Frame = {
    openDialog : function (dialog) {
        //窗口id根据当前时间戳生成
        var dialogSign = Date.parse(new Date());
        var createDiv = document.createElement("div");
        createDiv.id = dialogSign;
        document.body.appendChild(createDiv);

        $('#' + dialogSign).dialog({
            title: dialog.title == null? dialog_obj.title : dialog.title,
            width: dialog.width == null? dialog_obj.width : dialog.width,
            height: dialog.height == null? dialog_obj.height : dialog.height,
            closed: dialog.closed == null? dialog_obj.closed : dialog.closed,
            cache: dialog.cache == null? dialog_obj.cache : dialog.cache,
            href: dialog.url,
            queryParams: dialog.data == null ? {} : dialog.data,
            modal: dialog.modal == null? dialog_obj.modal : dialog.modal,
        });
    }


}

