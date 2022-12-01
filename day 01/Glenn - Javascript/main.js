const fs = require("fs");

process();

function process() {
    const data = fs.readFileSync("input.txt", "utf8");
    let elves = [];
    let calories = 0;

    data.split(/\r\n/).forEach(line => {
        if (line === "") {
            elves.push(calories);
            elves.sort((a, b) => b - a);
            calories = 0;
        } else {
            calories += parseInt(line);
        }
    });

    calories = elves[0] + elves[1] + elves[2];
    console.log(calories);
}
