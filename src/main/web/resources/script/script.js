
function createLi(tour) {
    var li = document.createElement("li");
    var a = document.createElement("a");
    a.textContent = tour.name;
    a.href = "#tourId=" + tour.id;
    a.addEventListener("click", function () {
        tourLoaded(tour);
    });
    li.appendChild(a);
    return li;
}

function toursLoaded(tours) {
    var ul = document.getElementById("tours-list");
    clearNode(ul);
    for (var index = 0; index < tours.length; index++) {
        var tour = tours[index];
        var li = createLi(tour);
        ul.appendChild(li);
    }
}

function loadToursFailed() {
    var ul = document.getElementById("tours-list");
    clearNode(ul);
    var errorMessageCell = document.createElement("li");
    errorMessageCell.colspan = 4;
    errorMessageCell.textContent = "Ошибка загрузки списка туров";
    ul.appendChild(errorMessageCell);
}


function loadTours() {
    var request = new XMLHttpRequest();
    request.open("get", "api/tours/find?tourPage=1", true);
    request.onreadystatechange = function (ev) {
        if (request.readyState === 4) {
            if (request.status === 200) {
                var response = JSON.parse(request.responseText);
                toursLoaded(response.content);
            } else {
                loadToursFailed();
            }
        }
    };
    request.send();
}

function clearNode(node) {
    while (node.hasChildNodes()) {
        node.removeChild(node.firstChild);
    }
}



function tourLoaded(tour) {
    var section = document.getElementById("tour-info");
    clearNode(section);
    var h3 = document.createElement("H3");
    var p1 = document.createElement("P");
    var p2 = document.createElement("P");
    var p3 = document.createElement("P");
    var a = document.createElement("a");
    h3.appendChild(document.createTextNode("Имя тура " + tour.name));
    p1.appendChild(document.createTextNode("Количество занятых мест - " + tour.curNumberOfSeats));
    p2.appendChild(document.createTextNode("Максимальное количество мест - " + tour.maxNumberOfSeats));
    p3.appendChild(document.createTextNode("Дата - " + new Date(tour.date).toISOString()));
    a.href = "/tour?tourId=" + tour.id;
    a.textContent = "Забронировать место";
    section.appendChild(h3);
    section.appendChild(p1);
    section.appendChild(p2);
    section.appendChild(p3);
    section.appendChild(a);
}





$(document).ready(function(){
    function move(e, obj){
        var summ = 0;
        var id = obj.next().attr('id').substr(1);
        var progress = e.pageX - obj.offset().left;
        var rating = progress * 5 / $('.stars').width();
        $('#param'+id).text(rating.toFixed(1));
        obj.next().width(progress);
        $('.rating').each(function(){ summ += parseFloat($(this).text()); });
        summ = summ / $('.rating').length;
        $('#sum_progress').width(Math.round($('.stars').width() * summ / 5));
        $('#summ').text(summ.toFixed(2));
    }

    $('#rating .stars').click(function(e){
        $(this).toggleClass('fixed');
        move(e, $(this));
    });

    $('#rating .stars').on('mousemove', function(e){
        if ($(this).hasClass('fixed')==false) move(e, $(this));
    });

    $('#rating [type=submit]').click(function(){
        summ = parseFloat($('#summ').text());
        jQuery.post('change_rating.php', {
            obj_id: $(this).attr('id').substr(3),
            rating: summ
        }, notice);
    });

    function notice(text){
        $('#message').fadeOut(500, function(){ $(this).text(text); }).fadeIn(2000);
    }
});
