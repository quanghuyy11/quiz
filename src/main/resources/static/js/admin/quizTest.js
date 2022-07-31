var tab = 0;
$(document).on('visibilitychange', function() {
    if(document.visibilityState == 'hidden') {
        tab++;
        $('input[name="error"]:hidden').val(tab);
        document.getElementById('amountTab').innerHTML = 'Số lần tab ra ngoài ' + tab;
    }

    if (tab == 3){
        document.getElementById("time").value = 0;
        $('#formQuestion').submit();
    }
});

$(document).ready(function () {
    let futureValue = [[${#calendars.format(futureDate,'yyyy/MM/dd HH:mm:ss')}]];
    let futureDate = new Date(futureValue.toString())
    countdown();

    function countdown() {
        let now = new Date().getTime();
        let distance = futureDate - now;
        // let days = Math.floor(distance / (1000 * 60 * 60 * 24));
        let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((distance % (1000 * 60)) / 1000);
        let display = (minutes < 10 ? '0' + minutes : '' + minutes) + ':' + (seconds < 10 ? '0' + seconds : '' + seconds);
        $('#clock').text(display);
        if (minutes <= 0) {
            $('#clock').addClass('text-danger');
        }

        if (seconds <= 0 && minutes <= 0) {
            clearInterval(timer);
            document.getElementById("time").value = 0;
            $('#formQuestion').submit();
        }
    }

    $("#formQuestion").submit(function (event) {
        // event.preventDefault();
        var time = document.getElementById("time").value;
        if (time == 0) {
            return true;
        } else {
            let isSubmit = true;
            let listQuestion = $('.ques-box');
            for (let i = 0; i < listQuestion.length; i++) {
                let answer = listQuestion[i].childNodes;
                let statusCheck = false;
                for (let k = 0; k < answer.length; k++) {
                    let check = true;
                    if (answer[k].nodeName === 'DIV') {
                        let option = answer[k].childNodes;
                        for (let j = 0; j < option.length; j++) {
                            if (option[j].nodeName === 'INPUT') {
                                if (option[j].checked === true) {
                                    break;
                                }
                                check = false;
                            }
                        }
                        if (check) {
                            statusCheck = true;
                            break;
                        }
                    }
                }
                if (!statusCheck) {
                    listQuestion[i].querySelector('#alert' + i).classList.add('d-block')
                    listQuestion[i].querySelector('#alert' + i).classList.remove('d-none')
                    isSubmit = false;
                } else {
                    listQuestion[i].querySelector('#alert' + i).classList.remove('d-block')
                    listQuestion[i].querySelector('#alert' + i).classList.add('d-none')
                }
                statusCheck = true;
            }
            if (!isSubmit) {
                event.preventDefault();
            }
        }
    });


    let timer = setInterval(function () {
        countdown();
    }, 1000);
})

