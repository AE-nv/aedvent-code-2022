import * as common from '../common/index'

interface Range {
    start: number;
    end: number;
}

function challenge1() {
    let amountOverlapping = 0;

    const lines = common.getFileLines('input.txt');
    lines.forEach(line => {
        const both = line.split(',');
        const first: Range = { start: parseInt(both[0].split('-')[0]), end: parseInt(both[0].split('-')[1]) }
        const second: Range = { start: parseInt(both[1].split('-')[0]), end: parseInt(both[1].split('-')[1]) }
        if (isFullyContained(first, second) || isFullyContained(second, first)) amountOverlapping++;
    })

    console.log(amountOverlapping);
}

function isFullyContained(range: Range, container: Range): boolean {
    return range.start >= container.start && range.end <= container.end;
}

function challenge2() {
    let amountOverlapping = 0;

    const lines = common.getFileLines('input.txt');
    lines.forEach(line => {
        const both = line.split(',');
        const first: Range = { start: parseInt(both[0].split('-')[0]), end: parseInt(both[0].split('-')[1]) }
        const second: Range = { start: parseInt(both[1].split('-')[0]), end: parseInt(both[1].split('-')[1]) }
        if (hasOverlap(first, second)) amountOverlapping++;
    })

    console.log(amountOverlapping);
}

function hasOverlap(first: Range, second: Range): boolean {
    return !(first.end < second.start || second.end < first.start)
}

// challenge1()
challenge2()