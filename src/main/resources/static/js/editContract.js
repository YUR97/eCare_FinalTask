window.onload = function () {

    let optionsContract = [];
    let optionsAll = [];

    const optionsContractSize = document.getElementsByClassName("divOptionToDelete").length;
    const optionsAllSize = document.getElementsByClassName("divWithTableOptions").length;

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

    const elementIsEmpty = document.getElementById("ifNewOptionsEmpty");
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
                let element = document.getElementById(together[i][0]);
                let elementInPair = document.getElementById(together[i][1]);
                if (element.checked) {
                    elementInPair.checked = true;
                } else {
                    elementInPair.checked = false;
                }
            }
        }
    }

    let mayBeChange = false;
    for (let j = 0; j < apart.length; j++) {
        let element = document.getElementById(apart[j][1]);
        let elementInPair = document.getElementById(apart[j][0]);
        if (elementInPair.checked) {
            element.checked = false;
            element.disabled = true;
            for (let i = 0; i < together.length; i++) {
                if (together[i][0] === apart[j][1]) {
                    let elementTogether = document.getElementById(together[i][1]);
                    elementTogether.checked = false;
                    elementTogether.disabled = true;
                }
            }
        } else {
            let counter = 0;
            let counterCompare = 0;
            for (let k = 0; k < apart.length; k++) {
                if (apart[k][1] === apart[j][1]) {
                    counterCompare++;
                    let elementIn = document.getElementById(apart[k][0]);
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
                    if (together[i][0] === apart[j][1]) {
                        let elementElseTogether = document.getElementById(together[i][1]);
                        elementElseTogether.disabled = false;
                    }
                }
            }
        }
    }

}
