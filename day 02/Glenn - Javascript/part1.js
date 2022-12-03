const fs = require("fs");

(() => {
    let score = 0;

    fs.readFileSync("sample_input.txt", "utf8").split(/\r\n/).forEach(line => {

        if (line) {
            const [opponentsChoice, yourChoice] = line.split(" ");

            const opponentsValue = opponentsChoice.charCodeAt(0) - 64;
            const yourValue = yourChoice.charCodeAt(0) - 87;

            score += getScore(opponentsValue, yourValue);
        }
    });

    console.log(score);

    function getScore(opponentsValue, yourValue) {
        /* Rock = 1, Paper = 2, Scissors = 3
            2 wins from 1: diff = 1
            3 wins from 2: diff = 1
            1 wins from 3: diff = -2
         */
        if (opponentsValue === yourValue) {
            return 3 + yourValue; // draw
        } else if (yourValue - opponentsValue === 1 || yourValue - opponentsValue === -2) {
            return 6 + yourValue; // win
        } else {
            return yourValue; // lose
        }
    }
})();
