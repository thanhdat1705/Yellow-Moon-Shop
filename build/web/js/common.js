
function decrementValueCS(AmountID, defaultAmountID, prevAmountID, totalID, UpdateAmountID, ChangeID, txtPrice, itemID, itemname) {
    var txtAmount = document.getElementById(AmountID).value;
    var txtDefaultAmount = document.getElementById(defaultAmountID).value;
    var defaultAmount = parseInt(txtDefaultAmount);
    var amount = parseInt(txtAmount);
    var price = parseFloat(txtPrice);
    if (amount > 1) {
        var total = parseFloat(document.getElementById("totalAllItem").value) - amount * price;
        amount = amount - 1;
        checkChangeAmount(amount, defaultAmount, ChangeID);
        document.getElementById(totalID).innerHTML = (amount * price).toFixed(2);
        document.getElementById(UpdateAmountID).value = itemID + ";;;;;" + itemname + ";;;;;" + amount;
        document.getElementById("totalAllItem").value = (total + amount * price).toFixed(2);
        document.getElementById(prevAmountID).value = amount;
        document.getElementById(AmountID).value = amount;
    }
}
function incrementValueCS(AmountID, defaultAmountID, prevAmountID, totalID, UpdateAmountID, ChangeID, txtPrice, itemID, itemname) {
    var txtAmount = document.getElementById(AmountID).value;
    var txtDefaultAmount = document.getElementById(defaultAmountID).value;
    var defaultAmount = parseInt(txtDefaultAmount);
    var amount = parseInt(txtAmount);
    var price = parseFloat(txtPrice);
    var total = parseFloat(document.getElementById("totalAllItem").value) - amount * price;
    amount = amount + 1;
    checkChangeAmount(amount, defaultAmount, ChangeID);
    document.getElementById(totalID).innerHTML = (amount * price).toFixed(2);
    document.getElementById(UpdateAmountID).value = itemID + ";;;;;" + itemname + ";;;;;" + amount;
    document.getElementById("totalAllItem").value = (total + amount * price).toFixed(2);
    document.getElementById(prevAmountID).value = amount;
    document.getElementById(AmountID).value = amount;
}

function changeValueOnInputCS(AmountID, defaultAmountID, prevAmountID, totalID, UpdateAmountID, ChangeID, txtPrice, itemID, itemname) {
    var txtPrevAmount = document.getElementById(prevAmountID).value;
    var txtAmount = document.getElementById(AmountID).value;
    var txtDefaultAmount = document.getElementById(defaultAmountID).value;
    var defaultAmount = parseInt(txtDefaultAmount);
    var prevAmount = parseInt(txtPrevAmount);
    var amount = parseInt(txtAmount);
    var price = parseFloat(txtPrice);
    var total = parseFloat(document.getElementById("totalAllItem").value) - prevAmount * price;
    if (amount > 0 || txtAmount === "") {
        if (txtAmount === "") {
            amount = 0;
        }
    } else {
        amount = 1;
    }
    document.getElementById(totalID).innerHTML = (amount * price).toFixed(2);
    document.getElementById(UpdateAmountID).value = itemID + ";;;;;" + itemname + ";;;;;" + amount;
    checkChangeAmount(amount, defaultAmount, ChangeID);
    document.getElementById("totalAllItem").value = (total + amount * price).toFixed(2);
    document.getElementById(prevAmountID).value = amount;
    document.getElementById(AmountID).value = amount;
}

function checkChangeAmount(amount, defaultAmount, ChangeID) {
    var change = parseInt(document.getElementById(ChangeID).value);
    var totalcheck = parseInt(document.getElementById("checkChangeAmount").value);
//    check change or not-===============================
    if (change > 0) {
        if (parseInt(amount) === parseInt(defaultAmount)) {
            document.getElementById(ChangeID).value = 0;
            totalcheck = totalcheck - 1;
        }
    } else {
        if (parseInt(amount) === parseInt(defaultAmount)) {
            document.getElementById(ChangeID).value = 0;
        } else {
            document.getElementById(ChangeID).value = 1;
            totalcheck = totalcheck + 1;
        }
    }
//  check total change or not-===================================
    if (totalcheck > 0) {
        document.getElementById("confirm").name = "btnAction";
        document.getElementById("confirm").value = "Save";
        document.getElementById("confirm").type = "submit";
        document.getElementById("confirm").setAttribute("onClick", false);
    } else {
        document.getElementById("confirm").name = "";
        document.getElementById("confirm").value = "Purchase";
        document.getElementById("confirm").type = "button";
        document.getElementById("confirm").setAttribute("onClick", "javascript: popupConfirmwindow('confirmWindow');");
    }
    document.getElementById("checkChangeAmount").value = totalcheck;
}

function checkDelete(checkBoxId) {
    var check = document.getElementById(checkBoxId);
    var totalCheck = parseInt(document.getElementById('checkDeleteItems').value);
    if (check.checked) {
        totalCheck = totalCheck + 1;
    } else {
        totalCheck = totalCheck - 1;
    }
    if (totalCheck > 0) {
        document.getElementById('delete').disabled = false;
    } else {
        document.getElementById('delete').disabled = true;
    }
    document.getElementById("checkDeleteItems").value = totalCheck;
}

function popupDeletewindow(id) {
    document.getElementById(id).style.height = "100%";
    document.getElementById("warningMessage").style.height = "100%";
}
function closeDeletewindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningMessage").style.height = "0%";
}
function popupConfirmwindow(id) {
    document.getElementById(id).style.height = "100%";
    document.getElementById("warningMessage").style.height = "100%";
}
function closeConfirmwindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningMessage").style.height = "0%";
}

function closeItemWindow(id) {
    document.getElementById(id).style.height = "0%";
}

//function setInputFilter(textbox, inputFilter) {
//    ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function (event) {
//        textbox.addEventListener(event, function () {
//            if (inputFilter(this.value)) {
//                this.oldValue = this.value;
//                this.oldSelectionStart = this.selectionStart;
//                this.oldSelectionEnd = this.selectionEnd;
//            } else if (this.hasOwnProperty("oldValue")) {
//                this.value = this.oldValue;
//                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
//            } else {
//                this.value = "";
//            }
//        });
//    });
//}

//setInputFilter(document.getElementById("intTextBox"), function (value) {
//    return /^-?\d*$/.test(value);
//});
//setInputFilter(document.getElementById("uintTextBox"), function (value) {
//    return /^\d*$/.test(value);
//});
//setInputFilter(document.getElementById("intLimitTextBox"), function (value) {
//    return /^\d*$/.test(value) && (value === "" || parseInt(value) <= 500);
//});
//setInputFilter(document.getElementById("floatTextBox"), function (value) {
//    return /^-?\d*[.,]?\d*$/.test(value);
//});
//setInputFilter(document.getElementById("currencyTextBox"), function (value) {
//    return /^-?\d*[.,]?\d{0,2}$/.test(value);
//});
//setInputFilter(document.getElementById("latinTextBox"), function (value) {
//    return /^[a-z]*$/i.test(value);
//});
//setInputFilter(document.getElementById("hexTextBox"), function (value) {
//    return /^[0-9a-f]*$/i.test(value);
//});