const fs = require("fs");

(() => {
    let sum = 0;

    let index = 1;
    let groups = [];
    let group = [];

    fs.readFileSync("input.txt", "utf8").split(/\r\n/).forEach(line => {
        if (line) {
            group.push(line);

            if (index % 3 === 0) {
                groups.push(group);
                group = [];
            }
            index++;
        }
    });

    groups.forEach(group => {
        const groupArray = [];
        group.forEach(g => groupArray.push([...g.split("")]));
        const uniqueCommon = findUniqueCommonItems(groupArray);

        if (uniqueCommon.length) {
             const priority = uniqueCommon[0].toLowerCase() === uniqueCommon[0] ? uniqueCommon[0].charCodeAt(0) - 96 : uniqueCommon[0].charCodeAt(0) - 38;
             sum += parseInt(priority);
         }
    });

    console.log(sum);

    function findUniqueCommonItems(arrays) {
        return arrays.shift().reduce(function (res, v) {
            if (res.indexOf(v) === -1 && arrays.every(function (a) {
                return a.indexOf(v) !== -1;
            })) res.push(v);
            return res;
        }, []);
    }
})();
