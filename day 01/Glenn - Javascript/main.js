const fs = require('fs');

const inputFile = "input.txt";

try {
    const data = fs.readFileSync(inputFile, 'utf8');
    const elves = process(data);

    calculateMaxCalories(elves);
} catch (err) {
    console.error(err);
}

function process(data) {
    let elves = [];
    let calories = 0;

    data.split(/\r\n/).forEach(line => {
        if (line === "") {
            console.log(`Elf carried ${calories} calories`);
            elves.push(calories);
            calories = 0;
        } else {
            calories += parseInt(line);
        }
    });

    return elves;
}

function calculateMaxCalories(elves) {
    let maxCaloriesSum = 0;

    for (let i = 0; i < 3; i++) {
        const maxCalories = Math.max(...elves);
        const maxCaloriesIndex = elves.indexOf(maxCalories);

        maxCaloriesSum += maxCalories;
        elves.splice(maxCaloriesIndex, 1);


        console.log(`=> Most carried calories by Elf ${maxCaloriesIndex}: ${maxCalories}`);
    }

    console.log(`=> Total carried calories by top 3 Elves: ${maxCaloriesSum}`);
}
