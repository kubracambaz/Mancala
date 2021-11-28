function newGame(){

        let data = JSON.stringify({
             pitCount: 6,
             stoneCount: 6
        });

    var xhr = new XMLHttpRequest();
    xhr.responseType = 'json';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if(xhr.status == 201){
                var jsonResponse = xhr.response;
                document.getElementById("5").innerHTML=jsonResponse.pits[0];
                document.getElementById("4").innerHTML=jsonResponse.pits[0];
                document.getElementById("3").innerHTML=jsonResponse.pits[0];
                document.getElementById("2").innerHTML=jsonResponse.pits[0];
                document.getElementById("1").innerHTML=jsonResponse.pits[0];
                document.getElementById("0").innerHTML=jsonResponse.pits[0];
                document.getElementById("7").innerHTML=jsonResponse.pits[0];
                document.getElementById("8").innerHTML=jsonResponse.pits[0];
                document.getElementById("9").innerHTML=jsonResponse.pits[0];
                document.getElementById("10").innerHTML=jsonResponse.pits[0];
                document.getElementById("11").innerHTML=jsonResponse.pits[0];
                document.getElementById("12").innerHTML=jsonResponse.pits[0];
                document.getElementById("mb").innerHTML=0;
                document.getElementById("mt").innerHTML=0;
            }else{
                alert(xhr.responseText);
            }
        }
    }
    xhr.open('POST', '/games', true);
   xhr.setRequestHeader("Content-Type", "application/json;charset=utf-8");

    xhr.send(data);
}