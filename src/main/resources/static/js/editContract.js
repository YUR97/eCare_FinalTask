window.onload = function () {

    let optionsContract = [];
    let optionsAll = [];

    let optionsContractSize = document.getElementsByClassName("divOptionToDelete").length;
    let optionsAllSize = document.getElementsByClassName("divWithTableOptions").length;

    for (let i = 0; i < optionsContractSize; i++) {
        let text = document.getElementsByClassName("contractOptionNames").item(i).textContent;
        optionsContract.push(text);
    }
    for (let i = 0; i < optionsAllSize; i++) {
        let text = document.getElementsByClassName("allOptionsName").item(i).value;
        optionsAll.push(text);
    }


    for (let i = 0; i < optionsContractSize; i++) {
        for (let j = 0; j < optionsAllSize; j++) {
            if (optionsContract[i] === optionsAll[j]) {
                let element = document.getElementsByClassName("divWithTableOptions").item(j);
                element.style.display = "none";

            }
        }
    }

    let elementIsEmpty = document.getElementById("ifNewOptionsEmpty");
    if (!(optionsAllSize === optionsContractSize)) {
        elementIsEmpty.style.display = "none";
    } else {
        elementIsEmpty.style.display = "inline";
    }

    console.log(optionsContractSize);
    console.log(optionsAllSize);
}
