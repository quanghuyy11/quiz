function InvalidMsg(textbox) {
    if (textbox.value == '') {
        textbox.setCustomValidity('Trường này bắt buộc điền!!');
    }
    else if (textbox.validity.typeMismatch){
        textbox.setCustomValidity('Vui lòng nhập thông tin đúng!!');
    }
    else {
        textbox.setCustomValidity('');
    }
    return true;
}