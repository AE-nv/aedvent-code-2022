const fs = require("fs");

(() => {
    let sum = 0;

    fs.readFileSync("input.txt", "utf8").split(/\r\n/).forEach(line => {

        if (line) {
            const items = line.split("");

            const firstCompartment = items.slice(0, items.length / 2);
            const secondCompartment = items.slice(items.length / 2);

            const common = firstCompartment.filter(value => secondCompartment.includes(value));
            const uniqueCommon = [...new Set(common)];

            if (uniqueCommon.length) {
                const priority = uniqueCommon[0].toLowerCase() === uniqueCommon[0] ? uniqueCommon[0].charCodeAt(0) - 96 : uniqueCommon[0].charCodeAt(0) - 38;
                sum += parseInt(priority);
            }
        }

    });

    console.log(sum);
})();
