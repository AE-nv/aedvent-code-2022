const fs = require("fs");

(() => {
    let score = 0;

    fs.readFileSync("input.txt", "utf8").split(/\r\n/).forEach(line => {

        if (line) {
            const [opponentsChoice, desiredOutcome] = line.split(" ");

            const opponentsValue = opponentsChoice.charCodeAt(0) - 64;
            const outcomeValue = getOutcomeValue(desiredOutcome);

            score += getScore(opponentsValue, outcomeValue);
        }
    });

    console.log(score);

    function getOutcomeValue(val) {
        if (val === "X") {
            return 0;
        } else if (val === "Y") {
            return 3
        } else {
            return 6;
        }
    }

    function getScore(opponentsValue, outcomeValue) {
        /* Rock = 1, Paper = 2, Scissors = 3
            2 wins from 1: diff = 1
            3 wins from 2: diff = 1
            1 wins from 3: diff = -2
         */
        let score;

        if (outcomeValue === 3) {
            score = opponentsValue; // draw
        } else if (outcomeValue === 6) {
            // win
            if (opponentsValue === 1 || opponentsValue === 2) {
                score = opponentsValue + 1;
            } else if(opponentsValue === 3) {
                score = 1;
            }
        } else {
            // lose
            if (opponentsValue === 2 || opponentsValue === 3) {
                score = opponentsValue - 1;
            } else if(opponentsValue === 1) {
                score = 3;
            }
        }

        return score + outcomeValue;
    }


})();
