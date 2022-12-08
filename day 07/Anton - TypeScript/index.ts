import * as common from '../common/index'

class Dir {
    name: string;
    size: number = 0;
    children: Dir[] = [];
    parent: Dir | null;
    level: number;

    constructor(name: string, parent: Dir | null, level: number) {
        this.name = name;
        this.parent = parent;
        this.level = level;
    }
}

let knownDirs: Dir[] = [];
let currDir: Dir;

function main() {
    currDir = new Dir('~', null, 0);
    knownDirs.push(currDir);

    const lines = common.getFileLines('input.txt');
    lines.forEach(line => {
        if (line.includes('$ cd')) handleCd(line.split(' ')[2]);
        else if (line.includes('$ ls')) return;
        else handleLsLine(line);
    });

    setActualDirSize();
    // printResult1();
    printResult2();
}

function handleCd(name: string) {
    switch (name) {
        case '..':
            if (currDir.parent) currDir = currDir.parent!;
            break;
        case '/':
            currDir = knownDirs.filter(d => d.name == '~')[0]
            break;
        default:
            currDir.children.push(new Dir(name, currDir, currDir.level + 1));
            currDir = currDir.children.filter(d => d.name == name)[0];
            knownDirs.push(currDir);
    }
}

function handleLsLine(line: string) {
    if (line.includes('dir')) return;
    currDir.size += parseInt(line.split(' ')[0]);
}

function setActualDirSize() {
    knownDirs.sort((d1, d2) => d2.level - d1.level).forEach(d =>
        d.children.forEach(c => d.size += c.size)
    )
}

function printResult1() {
    let result = 0;
    knownDirs.forEach(d => d.size <= 100000 ? result += d.size : null);
    console.log(result);
}

function printResult2() {
    const spaceToClear = 30000000 - (70000000 - knownDirs.find(d => d.level == 0)!.size);
    console.log(spaceToClear);
    const dirs = knownDirs.filter(d => d.size >= spaceToClear).sort((d1, d2) => d1.size - d2.size);
    dirs.forEach(d => console.log(d.name, d.size))
}

main();