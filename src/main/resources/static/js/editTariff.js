window.onload = function () {

    let optionsTariff = [];
    let optionsAll = [];

    const optionsTariffSize = document.getElementsByClassName("divOptionToDelete").length;
    const optionsAllSize = document.getElementsByClassName("divWithTableOptions").length;

    for (let i = 0; i < optionsTariffSize; i++) {
        let text = document.getElementsByClassName("tariffOptionNames").item(i).value;
        optionsTariff.push(text);
    }

    for (let i = 0; i < optionsAllSize; i++) {
        let text = document.getElementsByClassName("allOptionsName").item(i).value;
        optionsAll.push(text);
    }

    for (let i = 0; i < optionsTariffSize; i++) {
        for (let j = 0; j < optionsAllSize; j++) {
            if (optionsTariff[i] === optionsAll[j]) {
                let element = document.getElementsByClassName("divWithTableOptions").item(j);
                element.style.display = "none";
            }
        }
    }

    const elementIsEmpty = document.getElementById("ifNewOptionsEmpty");
    if (!(optionsAllSize === optionsTariffSize)) {
        elementIsEmpty.style.display = "none";
    } else {
        elementIsEmpty.style.display = "inline";
    }

}