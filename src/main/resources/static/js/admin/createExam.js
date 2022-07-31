function a() {
    document.getElementById('forms1').submit();
}

function displayFormExam() {
    document.getElementById("wrapFormExam").style.display = "block";
    document.getElementById("wrapFormQuestion").style.display = "none";
}

function displayFormQuestion() {
    document.getElementById("de").innerText = "Bước 2: Thêm câu hỏi trong đề";
    document.getElementById("wrapFormExam").style.display = "none";
    document.getElementById("wrapFormQuestion").style.display = "block";
    document.getElementById("hidden1").style.display = "none";
    document.getElementById("step2").classList.add("active");
}

displayFormExam();

function validate() {

    var valid = true;
    valid = checkId($("#idName"));
    valid = valid && checkEmpty($("#subject"));
    valid = valid && checkEmpty($("#nameExam"));
    valid = valid && checkEmpty($("#numberQues"));
    valid = valid && checkEmpty($("#time"));

    $("#btn-submit").attr("disabled", true);
    if (valid) {
        $("#btn-submit").attr("disabled", false);
    }
}

function checkEmpty(obj) {
    if ($(obj).val() == "") {
        return false;
    }
    return true;
}

function checkId(obj) {
    var result = true;
    result = checkEmpty(obj);
    if (!result) {
        return false;
    }

    var code_regex = /^MD-[\d]{4}$/;
    result = code_regex.test($(obj).val());

    if (!result) {
        document.getElementById("idNameError").innerHTML = "Mã đề thi phải là định dạng MD-XXXX (XXXX là số)";
        return false;
    }

    return true;
}

function checkAmount(){
    var bol = $("input:checkbox:checked").length;
    var amount = $('input[name="numberQues"]:hidden').val();
    if (bol < amount){
        document.getElementById("danger").innerHTML = "Chưa chọn đủ câu hỏi";
        return false;
    } else {
        return true;
    }
}

$("input:checkbox").click(function () {
    var bol = $("input:checkbox:checked").length >= 50;
    $("input:checkbox").not(":checked").attr("disabled", bol);
});

$(document).ready(function () {
    $("#questForm").DataTable({
        lengthMenu: [ [15, 25, 50, 100, -1], [15, 25, 50, 100, "Hiện tất cả"] ],
        pageLength: 15,
        'aoColumnDefs': [{
            'bSortable': false,
            'aTargets': [-1], /* 1st one, start by the right */
        }],
        language: {
            processing: "Message khi đang tải dữ liệu",
            search: "tìm kiếm",
            lengthMenu: "Điều chỉnh số lượng bản ghi trên 1 trang _MENU_ ",
            info: "Bản ghi từ _START_ đến _END_ Tổng công _TOTAL_ bản ghi",
            infoEmpty: "Khi không có dữ liệu, Hiển thị 0 bản ghi trong 0 tổng cộng 0 ",
            infoFiltered: "(Message bổ sung cho info khi không search đc record nào _MAX_)",
            loadingRecords: "",
            zeroRecords: "Khi tìm kiếm không match với record nào",
            emptyTable: "Không có dữ liệu",
            paginate: {
                first: "Trang đầu",
                previous: "Trang trước",
                next: "Trang sau",
                last: "Trang cuối"
            },
        }
    });
})
