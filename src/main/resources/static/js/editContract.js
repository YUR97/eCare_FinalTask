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

}

function editStatus(value) {

    let inTogether = false;
    for (let i = 0; i < together.length; i++) {
        if (together[i][0] === value) {
            inTogether = true;
        }
    }
    if (inTogether) {
        for (let i = 0; i < together.length; i++) {
            if (together[i][0] === value) {
                if (document.getElementById(together[i][0]).checked) {
                    document.getElementById(together[i][1]).checked = true;
                } else {
                    document.getElementById(together[i][1]).checked = false;
                }
            }
        }
    }

    let mayBeChange = false;
    for (let j = 0; j < noTogether.length; j++) {
        let element = document.getElementById(noTogether[j][1]);
        if (document.getElementById(noTogether[j][0]).checked) {
            element.checked = false;
            element.disabled = true;
            for (let i = 0; i < together.length; i++) {
                if (together[i][0] === noTogether[j][1]) {
                    let elementTogether = document.getElementById(together[i][1]);
                    elementTogether.checked = false;
                    elementTogether.disabled = true;
                }
            }
        } else {
            let counter = 0;
            let counterCompare = 0;
            for (let k = 0; k < noTogether.length; k++) {
                if (noTogether[k][1] === noTogether[j][1]) {
                    counterCompare++;
                    let elementIn = document.getElementById(noTogether[k][0]);
                    if (elementIn.checked) {
                        mayBeChange = false;
                    } else {
                        counter++;
                    }
                }
            }
            if (counter === counterCompare) {
                mayBeChange = true;
            }
            if (mayBeChange) {
                element.disabled = false;
                for (let i = 0; i < together.length; i++) {
                    if (together[i][0] === noTogether[j][1]) {
                        let elementElseTogether = document.getElementById(together[i][1]);
                        elementElseTogether.disabled = false;
                    }
                }
            }
        }
    }
}
