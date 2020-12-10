

function placeBid() {

}

function makeDeposit() {

}

function getProductBids(productId) {
    $.ajax({
        type: "GET",
        url: "/bid/get/"+productId,
        contentType: "application/json",
        success: function(data) {
            console.log(data);
        },
        error: function(x, s, e) {}
    })
}