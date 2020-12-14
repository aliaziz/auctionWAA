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
            }, 2500);
        },
        error: function(x, s, e) {}
    })
}

function productReceived(productId) {
    let statusObj = $("#status");

    $.ajax({
        url: '/product/productReceived/'+productId,
        type: 'GET',
        contentType: 'application/json',
        success: function(data) {
            if (data) {
                statusObj.html("<p>The seller has been informed. Product shipment is being prepared. </p>")
            } else {
                statusObj.html("<p>An error occured. Contact admin </p>")
            }

            setTimeout(function() {
                statusObj.empty();
                location.reload();
            }, 2000);
        },
        error: function(x, s, e) {
            statusObj.html("<p>An error occured. Contact admin </p>")
        }
    })
}