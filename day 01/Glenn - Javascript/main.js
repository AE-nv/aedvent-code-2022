const fs = require("fs");

(() => {
    let topCalories = [];
    let caloriesOfElf = 0;

    fs.readFileSync("input.txt", "utf8").split(/\r\n/).forEach(line => {
        if (line === "") {
            topCalories.push(caloriesOfElf);
            topCalories.sort((a, b) => b - a);
            caloriesOfElf = 0;
        } else {
            caloriesOfElf += parseInt(line);
        }
    });

    console.log(topCalories[0] + topCalories[1] + topCalories[2]);
})();
