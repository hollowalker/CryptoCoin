window.onload = function name(params) {
    // Example POST method implementation:
    async function postData(url = "", data = {}) {
        // Default options are marked with *
        const response = await fetch(url, {
        method: "POST", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(data), // body data type must match "Content-Type" header
        });
        return response.json(); // parses JSON response into native JavaScript objects
    };

    var searchButton = document.getElementById("crypto__button");
    searchButton.onclick = function() {
        var coinName = document.getElementById("crypto__search").value;
        document.getElementById("crypto__search").value = '';
        postData("/coin/getCoin", { name: coinName }).then((data) => {
            console.log(data); // JSON data parsed by `data.json()` call
            if (data["status"]!=200||data["error"]!=null) {
                document.getElementById("search_error").innerHTML = 'Status = ' + data["status"] + '; Error: ' + data["error"];
                document.getElementById("search_error").style.display = 'flex';
                document.getElementById("coin__table__head").style.display = 'none';
                document.getElementById("coin_info_wrap").style.display = 'none';
                document.getElementById("coin-img").src = '';
                document.getElementById("coin-uuid").innerHTML = '';
                document.getElementById("coin-name").innerHTML = '';
                document.getElementById("coin-price").innerHTML = '';
                document.getElementById("coin-curCurrency").innerHTML = '';
                document.getElementById("coin-price-changed").innerHTML = '';
            } else {
                document.getElementById("coin-img").src = data["imgUrl"];
                document.getElementById("coin-uuid").innerHTML = data["uuid"];
                document.getElementById("coin-name").innerHTML = data["name"];
                document.getElementById("coin-price").innerHTML = new Intl.NumberFormat('ru-Ru').format(data["price"]);
                document.getElementById("coin-curCurrency").innerHTML = data["curCurrency"];
                document.getElementById("coin-price-changed").innerHTML = data["priceChangedPercentage24h"] + '%';
                if (parseFloat(data["priceChangedPercentage24h"]) > 0) {
                    document.getElementById("coin-price-changed").style.color = '#05ad16';
                } else {
                    document.getElementById("coin-price-changed").style.color = '#f73816';
                }
                document.getElementById("search_error").style.display = 'none';
                document.getElementById("coin__table__head").style.display = 'flex';
                document.getElementById("coin_info_wrap").style.display = 'flex';
            }
        });
    };
};