declare const javaScriptRoutesAdminCalendar: any;


export class ReservationController {

    constructor() {
        this.ready();
    }

    protected ready(): void{
        alert("akjshdakjsdhksahkdj")
        this.readGETRequest();
    }

    protected readGETRequest(): any {
        let javaScriptRoutes : any = javaScriptRoutesAdminCalendar.controllers.HomeController.adminCheckReservations();
        fetch(javaScriptRoutes.url)
            .then(r => {
                if (r.status === 200){
                    return r.json();
                } else {
                    alert("error");
                    return;
                }
            }).then(rJson => {
                return this.handleReservationResponse(rJson);
        })
    }

    protected handleReservationResponse(responseJson: any) :void {
        let i : number = 1;
        responseJson.forEach((a: any) => {
            console.log("reservation: " + i);




                let e : HTMLDivElement = document.createElement("div");
                const time_from : string = a.timeFrom.hour + ":" + a.timeFrom.minute;
                const time_to: string = a.timeTo.hour + ":" + a.timeTo.minute;
                const day_of_week: string = a.reservationDate.dayOfWeek;
                const MS_PER_MINUTE : number = 60000;
                const durationInMinutes : number  = 30;


                const amountOfMinutesTimeFrom : number = ((a.timeFrom.hour - 1) * 60 + a.timeFrom.minute - durationInMinutes) * MS_PER_MINUTE;
                const amountOfMinutesTimeTo : number = ((a.timeTo.hour - 1) * 60 + a.timeTo.minute + durationInMinutes) * MS_PER_MINUTE;

                console.log(amountOfMinutesTimeFrom)
                console.log(amountOfMinutesTimeTo)

                const newTimeObjectTimeFrom : Date = new Date(amountOfMinutesTimeFrom);
                const newTimeObjectTimeToo : Date = new Date(amountOfMinutesTimeTo);
                console.log(newTimeObjectTimeFrom)
                console.log(newTimeObjectTimeToo)

                var newMinutesTimeFrom : number = newTimeObjectTimeFrom.getMinutes();
                var newHoursTimeFrom : number = newTimeObjectTimeFrom.getHours();

                if (newHoursTimeFrom === 8){
                    newHoursTimeFrom = 9;
                    newMinutesTimeFrom = 0;
                }

                var newMinutesTimeTo : number = newTimeObjectTimeToo.getMinutes();
                var newHoursTimeTo : number = newTimeObjectTimeToo.getHours();

                if (newHoursTimeTo === 19){
                    newMinutesTimeTo = 0;
                }

                console.log(newHoursTimeFrom + ":" + newMinutesTimeFrom);
                console.log(newHoursTimeTo + ":" + newMinutesTimeTo);

                // TODO                  1x   e.classList.add("start-" + variable)

                e.classList.add("start-" +  newHoursTimeFrom + newMinutesTimeFrom);
                e.classList.add("end-" +  newHoursTimeTo + newMinutesTimeTo);
                e.classList.add("reserved");

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

            i++;
        })

    }
}

const test = new ReservationController();