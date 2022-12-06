import * as common from '../common/index'

let stacks: string[][] = [];
let setupPhase = true;

function main() {
    const lines = common.getFileLines('input.txt');
    lines.forEach((line, index) => {
        if (setupPhase) {
            handleSetupLine(line, index)
        } else {
            handleMoveLine(line)
        }
    });

    console.log(getMessage());
}

function handleSetupLine(line: string, index: number) {
    if (!line.includes('[')) {
        setupPhase = false;
        stacks.forEach(s => s.reverse())
        return;
    }

    line = line.replace(/   /g, '_').replace(/ /g, '').replace(/\[/g, '').replace(/\]/g, '');
    const crates = [...line];

    if (index == 0) {
        for (let i = 0; i < crates.length; i++) {
            stacks.push([])
        }
    }

    crates.forEach((char: string, index: number) => {
        if (char !== '_') stacks[index].push(char);
    })
}

function handleMoveLine(line: string) {
    const segments = line.split(' ');
    move2(parseInt(segments[1]), parseInt(segments[3]) - 1, parseInt(segments[5]) - 1);
}

function move(amount: number, from: number, to: number) {
    if (isNaN(amount)) return;
    for (let i = 0; i < amount; i++) {
        const crate = stacks[from].pop() as string;
        stacks[to].push(crate);
    }
}

function move2(amount: number, from: number, to: number) {
    if (isNaN(amount)) return;

    const crates = stacks[from].slice(stacks[from].length - amount);
    stacks[to].push(...crates);

    for (let i = 0; i < amount; i++) {
        stacks[from].pop();

    }
}

function getMessage(): string {
    let message: string = ''

    stacks.forEach(s => message += s.pop())

    return message;
}

main();