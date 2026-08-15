
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
    var p4 = document.createElement("P");
    h3.appendChild(document.createTextNode("Имя тура " + tour.name));
    p1.appendChild(document.createTextNode("Количество занятых мест - " + tour.curNumberOfSeats));
    p2.appendChild(document.createTextNode("Максимальное количество мест - " + tour.maxNumberOfSeats));
    p3.appendChild(document.createTextNode("Дата - " + new Date(tour.date).toISOString()));
    p4.appendChild(document.createTextNode("Рейтинг тура - " + tour.rating))
    a.href = "/tour?tourId=" + tour.id;
    a.textContent = "Забронировать место и оценить";
    section.appendChild(h3);
    section.appendChild(p1);
    section.appendChild(p2);
    section.appendChild(p3);
    section.appendChild(p4);
    section.appendChild(a);
}



function loadSeats(id) {
    var request = new XMLHttpRequest();
    request.open("get", "api/seats/find?userId=" + id + "&seatPage=1", true);
    request.onreadystatechange = function (ev) {
        if (request.readyState === 4) {
            if (request.status === 200) {
                var response = JSON.parse(request.responseText);
                seatsLoaded(response.content);
            } else {
                loadSeatsFailed();
            }
        }
    };
    request.send();
}

function loadAssessments(id) {
    var request = new XMLHttpRequest();
    request.open("get", "api/assessments/find?userId=" + id + "&assessmentPage=1", true);
    request.onreadystatechange = function (ev) {
        if (request.readyState === 4) {
            if (request.status === 200) {
                var response = JSON.parse(request.responseText);
                assessmentsLoaded(response.content);
            } else {
                loadAssessmentsFailed();
            }
        }
    };
    request.send();
}

function loadSeatsFailed() {
    var div = document.getElementById("seats-list");
    clearNode(div);
    var errorMessageCell = document.createElement("P");
    errorMessageCell.appendChild(document.createTextNode("Ошибка загрузки забронированных мест"));
    div.appendChild(errorMessageCell);
}

function loadAssessmentsFailed() {
    var div = document.getElementById("assessments-list");
    clearNode(div);
    var errorMessageCell = document.createElement("P");
    errorMessageCell.appendChild(document.createTextNode("Ошибка загрузки поставленных оценок"));
    div.appendChild(errorMessageCell);
}

function assessmentsLoaded(assessments) {
    var div = document.getElementById("assessments-list");
    clearNode(div);
    for (var index = 0; index < assessments.length; index++) {
        var assessment = assessments[index];
        var p1 = document.createElement("P");
        p1.appendChild(document.createTextNode("Тур: " + assessment.tour.name + " Оценка: " + assessment.value));
        div.appendChild(p1);
    }

}

function seatsLoaded(seats) {
    var div = document.getElementById("seats-list");
    clearNode(div);
    for (var index = 0; index < seats.length; index++) {
        var seat = seats[index];
        var p1 = document.createElement("P");
        p1.appendChild(document.createTextNode("Тур: " + seat.tour.name + " Забронировано: " + seat.quantity));
        div.appendChild(p1);
    }
}








