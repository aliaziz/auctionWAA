function placeBid(productId) {
    let bidPrice = $('#bidPrice').val();
    $.ajax({
        url: "/bid/place/"+productId+"/"+bidPrice,
        type: "GET",
        contentType: "application/json",
        success: function(data) {
            let statusResponse = $("#status-area");
            if (data === "Bid saved") {
                statusResponse
                    .html("<p class = 'alert-success alert'> Bid has been placed</p>")
            } else {
                statusResponse
                    .html("<p class= 'alert-warning alert'>"+data+"</p>")
            }

            setTimeout(function() {
                statusResponse.empty();
                location.reload();
            }, 4000);
        },
        error: function(x, s, e) {}
    })
}