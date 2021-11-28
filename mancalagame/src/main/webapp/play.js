function playGame(clicked_id){

        let data = JSON.stringify({
             index: clicked_id,
             pits: [document.getElementById("0").innerHTML,document.getElementById("1").innerHTML,document.getElementById("2").innerHTML,document.getElementById("3").innerHTML,document.getElementById("4").innerHTML,document.getElementById("5").innerHTML,document.getElementById("mb").innerHTML,document.getElementById("7").innerHTML,document.getElementById("8").innerHTML,document.getElementById("9").innerHTML,document.getElementById("10").innerHTML,document.getElementById("11").innerHTML,document.getElementById("12").innerHTML,document.getElementById("mt").innerHTML],
             playerTurn: document.getElementById("Player").value,
             status: document.getElementById("Gs").value,
             firstPlayer: {
                  largePit: 6,
                  playerType: 'FIRST'
             },
             secondPlayer: {
                               largePit: 13,
                               playerType: 'SECOND'
                          }
        });

    var xhr = new XMLHttpRequest();
    xhr.responseType = 'json';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if(xhr.status == 200){
                var jsonResponse = xhr.response;
                document.getElementById("0").innerHTML=jsonResponse.pits[0];
                document.getElementById("1").innerHTML=jsonResponse.pits[1];
                document.getElementById("2").innerHTML=jsonResponse.pits[2];
                document.getElementById("3").innerHTML=jsonResponse.pits[3];
                document.getElementById("4").innerHTML=jsonResponse.pits[4];
                document.getElementById("5").innerHTML=jsonResponse.pits[5];
                document.getElementById("7").innerHTML=jsonResponse.pits[7];
                document.getElementById("8").innerHTML=jsonResponse.pits[8];
                document.getElementById("9").innerHTML=jsonResponse.pits[9];
                document.getElementById("10").innerHTML=jsonResponse.pits[10];
                document.getElementById("11").innerHTML=jsonResponse.pits[11];
                document.getElementById("12").innerHTML=jsonResponse.pits[12];
                document.getElementById("mb").innerHTML=jsonResponse.pits[6];
                document.getElementById("mt").innerHTML=jsonResponse.pits[13];
                document.getElementById("Player").value=jsonResponse.playerTurn;
                document.getElementById("Gs").value=jsonResponse.status;
                if(jsonResponse.status=='FINISHED')
                {
                document.getElementById("winnerPlayer").value=jsonResponse.winnerPlayer.playerType;
                }
            }else{
                alert(xhr.response.errorCode +" - "+ xhr.response.message  );
            }
        }
    }
    xhr.open('PUT', '/plays', true);
   xhr.setRequestHeader("Content-Type", "application/json;charset=utf-8");

    xhr.send(data);
}