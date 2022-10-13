$(document).ready(function() {
    var results = $('#result') ;
    $('button').click(function(){
        //console.log($(this).text());
       $.ajax({
            type: "GET",
            url: "http://localhost:8080/SightAPI?zone="+$(this).text(),
            success: function(allSights){
              results.html("");
              $.each(allSights,function (i,sight) {
                  let address = "https://www.google.com.tw/maps/place/" + sight.address;
                  if (sight.photoURL == "null") {
                      results.append(
                          "<div class='card' id = o_card>" +
                          "<div class='card-header'>" + sight.sightName + "</div>" +
                          "<div class='card-body bg-dark text-white'>" +
                          "<p>" + "區域:" + sight.zone + "<br>" + "分類:" + sight.category +
                          `<form action= ${address}  target='_blank'> 
                                        <button type='submit'>地址</button>
                                     </form>` +
                          "</p>" +
                          `<div class="card">
                                    <div class="card-header">
                                        <a class="btn" data-bs-toggle="collapse" href="#collapse${i}">
                                            詳細資訊
                                        </a>
                                    </div>
                                    <div id="collapse${i}" class="collapse" data-bs-parent="#accordion">
                                        <div class="card-body">                                        
                                             <p style="color: black;">${sight.description}</p>
                                        </div>
                                    </div>
                                </div>` +

                          "</div>" +
                          "</div>"
                      )
                  } else{
                      results.append(
                          "<div class='card' id = o_card>" +
                          "<div class='card-header'>" + sight.sightName + "</div>" +
                          "<div class='card-body bg-dark text-white'>" +
                          "<p>" + "區域:" + sight.zone + "<br>" + "分類:" + sight.category +
                          `<form action= ${address}  target='_blank'> 
                                        <button type='submit'>地址</button>
                                     </form>` +
                          "</p>" +
                          `<div class="card">
                                    <div class="card-header">
                                        <a class="btn" data-bs-toggle="collapse" href="#collapse${i}">
                                            詳細資訊
                                        </a>
                                    </div>
                                    <div id="collapse${i}" class="collapse" data-bs-parent="#accordion">
                                        <div class="card-body">                                            
                                            <img src=${sight.photoURL} alt="無圖片" />
                                             <p style="color: black;">${sight.description}</p>
                                        </div>
                                    </div>
                                </div>` +

                          "</div>" +
                          "</div>"
                      )
              }
              });
            }
        });
    });
});