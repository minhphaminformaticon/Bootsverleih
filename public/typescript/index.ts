declare const javaScriptRoutesDisplayReservationsOnCalendar: any ;

export class ReservationController {

    constructor() {
        this.ready();
    }

    protected ready(): void {
        alert("typescript working");
        this.readGETRequest();
    }

    protected readGETRequest(): any{
        let javaScriptRoutes : any = javaScriptRoutesDisplayReservationsOnCalendar.controllers.HomeController.displayReservationsOnCalendar();
        fetch(javaScriptRoutes.url)
            .then(response => {
                if (response.status === 200){
                    return response.json();
                } else {
                    alert('error');
                    return;
                }
            }).then(responseJson => {
            alert("hesfdsklf")
            return this.handleReservationResponse(responseJson);
        });
    }

    protected handleReservationResponse(responseJson : any ) :void {
        let i = 1;
        responseJson.forEach((a: any) => {
            console.log("reservation nr. " + i + ":\n" + "id = " + a.id + ", boat model = " + a.boatTable.model + ", reservation date = " + a.reservationDate.dayOfMonth + a.reservationDate.monthValue + a.reservationDate.year + ", time from = " + a.timeFrom.hour + ":" + a.timeFrom.minute + ", time to = " + a.timeTo.hour + ":" + a.timeTo.minute);
            i++;
            let e : HTMLDivElement = document.createElement("div");
            const time_from : string = a.timeFrom.hour + ":" + a.timeFrom.minute;
            const time_to: string = a.timeTo.hour + ":" + a.timeTo.minute;
            const day_of_week: string = a.reservationDate.dayOfWeek;

            const timeObject = new Date();
            timeObject.setHours(a.timeFrom.hour );
            timeObject.setMinutes(a.timeFrom.minute);

            // TODO -15 minuten rechnen auf timeObject
            // TODO berechnete stunden und minuten extrahieren
            timeObject.getHours()

            // TODO                  1x   e.classList.add("start-" + variable)

            debugger; // LOADER fehlt

            switch (time_from){
                case "9:0":
                    e.classList.add("start-9")
                    e.classList.add("reserved")
                    break;
                case "9:15":
                    e.classList.add("start-9")
                    e.classList.add("reserved")
                    break;
                case "9:30":
                    e.classList.add("start-915")
                    e.classList.add("reserved")
                    break;
                case "9:45":
                    e.classList.add("start-930")
                    e.classList.add("reserved")
                    break;
                case "10:0":
                    e.classList.add("start-945")
                    e.classList.add("reserved")
                    break;
                case "10:15":
                    e.classList.add("start-10")
                    e.classList.add("reserved")
                    break;
                case "10:30":
                    e.classList.add("start-1015")
                    e.classList.add("reserved")
                    break;
                case "10:45":
                    e.classList.add("start-1030")
                    e.classList.add("reserved")
                    break;
                case "11:0":
                    e.classList.add("start-1045")
                    e.classList.add("reserved")
                    break;
                case "11:15":
                    e.classList.add("start-11")
                    e.classList.add("reserved")
                    break;
                case "11:30":
                    e.classList.add("start-1115")
                    e.classList.add("reserved")
                    break;
                case "11:45":
                    e.classList.add("start-1130")
                    e.classList.add("reserved")
                    break;
                case "12:0":
                    e.classList.add("start-1145")
                    e.classList.add("reserved")
                    break;
                case "12:15":
                    e.classList.add("start-12")
                    e.classList.add("reserved")
                    break;
                case "12:30":
                    e.classList.add("start-1215")
                    e.classList.add("reserved")
                    break;
                case "12:45":
                    e.classList.add("start-1230")
                    e.classList.add("reserved")
                    break;
                case "13:0":
                    e.classList.add("start-1245")
                    e.classList.add("reserved")
                    break;
                case "13:15":
                    e.classList.add("start-13")
                    e.classList.add("reserved")
                    break;
                case "13:30":
                    e.classList.add("start-1315")
                    e.classList.add("reserved")
                    break;
                case "13:45":
                    e.classList.add("start-1330")
                    e.classList.add("reserved")
                    break;
                case "14:0":
                    e.classList.add("start-1345")
                    e.classList.add("reserved")
                    break;
                case "14:15":
                    e.classList.add("start-14")
                    e.classList.add("reserved")
                    break;
                case "14:30":
                    e.classList.add("start-1415")
                    e.classList.add("reserved")
                    break;
                case "14:45":
                    e.classList.add("start-1430")
                    e.classList.add("reserved")
                    break;
                case "15:0":
                    e.classList.add("start-1445")
                    e.classList.add("reserved")
                    break;
                case "15:15":
                    e.classList.add("start-15")
                    e.classList.add("reserved")
                    break;
                case "15:30":
                    e.classList.add("start-1515")
                    e.classList.add("reserved")
                    break;
                case "15:45":
                    e.classList.add("start-1530")
                    e.classList.add("reserved")
                    break;
                case "16:0":
                    e.classList.add("start-1545")
                    e.classList.add("reserved")
                    break;
                case "16:15":
                    e.classList.add("start-16")
                    e.classList.add("reserved")
                    break;
                case "16:30":
                    e.classList.add("start-1615")
                    e.classList.add("reserved")
                    break;
                case "16:45":
                    e.classList.add("start-1630")
                    e.classList.add("reserved")
                    break;
                case "17:0":
                    e.classList.add("start-1645")
                    e.classList.add("reserved")
                    break;
                case "17:15":
                    e.classList.add("start-17")
                    e.classList.add("reserved")
                    break;
                case "17:30":
                    e.classList.add("start-1715")
                    e.classList.add("reserved")
                    break;
                case "17:45":
                    e.classList.add("start-1730")
                    e.classList.add("reserved")
                    break;
                case "18:0":
                    e.classList.add("start-1745")
                    e.classList.add("reserved")
                    break;
                case "18:15":
                    e.classList.add("start-18")
                    e.classList.add("reserved")
                    break;
                case "18:30":
                    e.classList.add("start-1815")
                    e.classList.add("reserved")
                    break;
                case "18:45":
                    e.classList.add("start-1830")
                    e.classList.add("reserved")
                    break;
                case "19:00":
                    e.classList.add("start-1845")
                    e.classList.add("reserved")
            }

            switch (time_to) {
                case "9:0":
                    e.classList.add("end-915");
                    break;
                case "9:15":
                    e.classList.add("end-930");
                    break;
                case "9:30":
                    e.classList.add("end-945");
                    break;
                case "9:45":
                    e.classList.add("end-10");
                    break;
                case "10:0":
                    e.classList.add("end-1015");
                    break;
                case "10:15":
                    e.classList.add("end-1030");
                    break;
                case "10:30":
                    e.classList.add("end-1045");
                    break;
                case "10:45":
                    e.classList.add("end-11");
                    break;
                case "11:0":
                    e.classList.add("end-1115");
                    break;
                case "11:15":
                    e.classList.add("end-1130");
                    break;
                case "11:30":
                    e.classList.add("end-1145");
                    break;
                case "11:45":
                    e.classList.add("end-12");
                    break;
                case "12:0":
                    e.classList.add("end-1215");
                    break;
                case "12:15":
                    e.classList.add("end-1230");
                    break;
                case "12:30":
                    e.classList.add("end-1245");
                    break;
                case "12:45":
                    e.classList.add("end-13");
                    break;
                case "13:0":
                    e.classList.add("end-1315");
                    break;
                case "13:15":
                    e.classList.add("end-1330");
                    break;
                case "13:30":
                    e.classList.add("end-1345");
                    break;
                case "13:45":
                    e.classList.add("end-14");
                    break;
                case "14:0":
                    e.classList.add("end-1415");
                    break;
                case "14:15":
                    e.classList.add("end-1430");
                    break;
                case "14:30":
                    e.classList.add("end-1445");
                    break;
                case "14:45":
                    e.classList.add("end-15");
                    break;
                case "15:0":
                    e.classList.add("end-1515");
                    break;
                case "15:15":
                    e.classList.add("end-1530");
                    break;
                case "15:30":
                    e.classList.add("end-1545");
                    break;
                case "15:45":
                    e.classList.add("end-16");
                    break;
                case "16:0":
                    e.classList.add("end-1615");
                    break;
                case "16:15":
                    e.classList.add("end-1630");
                    break;
                case "16:30":
                    e.classList.add("end-1645");
                    break;
                case "16:45":
                    e.classList.add("end-17");
                    break;
                case "17:0":
                    e.classList.add("end-1715");
                    break;
                case "17:15":
                    e.classList.add("end-1730");
                    break;
                case "17:30":
                    e.classList.add("end-1745");
                    break;
                case "17:45":
                    e.classList.add("end-18");
                    break;
                case "18:0":
                    e.classList.add("end-1815");
                    break;
                case "18:15":
                    e.classList.add("end-1830");
                    break;
                case "18:30":
                    e.classList.add("end-1845");
                    break;
                case "18:45":
                    e.classList.add("end-19");
                    break;
                case "19:0":
                    e.classList.add("end-19");
                    break;
            }

            switch (day_of_week){
                case "MONDAY":
                    document.getElementById("eventMon").appendChild(e);
                    break;
                case "TUESDAY":
                    document.getElementById("eventTue").appendChild(e);
                    break;
                case "WEDNESDAY":
                    document.getElementById("eventWed").appendChild(e);
                    break;
                case "THURSDAY":
                    document.getElementById("eventThurs").appendChild(e);
                    break;
                case "FRIDAY":
                    document.getElementById("eventFri").appendChild(e);
                    break;
                case "SATURDAY":
                    document.getElementById("eventSat").appendChild(e);
                    break;
                case "SUNDAY":
                    document.getElementById("eventSun").appendChild(e);

                    break;
            }


            e.innerHTML = a.reservationDate.dayOfMonth + ". " + a.reservationDate.monthValue + ". " + a.reservationDate.year + " boat: " + a.boatTable.vehicleLicensePlate;
        });
    }

}

const test = new ReservationController();