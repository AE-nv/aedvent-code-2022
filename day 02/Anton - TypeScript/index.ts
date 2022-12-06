import * as common from '../common/index'

function challenge1() {
    let score = 0;

    const lines = common.getFileLines('input.txt');

    lines.forEach((line: string) => {
        const contents = line.split(' ')
        const first = contents[0]
        const second = contents[1]
        score += determineShapeScore(second) + determineOutcomeScore(first, second);
    });
    console.log(score)
}

function challenge2() {
    let score = 0;

    const lines = common.getFileLines('input.txt');

    lines.forEach((line: string) => {
        const contents = line.split(' ')
        const first = contents[0]
        const second = contents[1]
        score += determineShapeScore2(first, second) + determineOutcomeScore2(second);
    });
    console.log(score)
}

function determineShapeScore(shape: string): number {
    switch (shape) {
        case 'X': return 0;
        case 'Y': return 3;
        case 'Z': return 6;
        default: return 0;
    }
}

function determineOutcomeScore(opponent: string, our: string): number {
    if (opponent == 'A') {
        if (our == 'Y') return 6;
        if (our == 'Z') return 0;
    }

    if (opponent == 'B') {
        if (our == 'X') return 0;
        if (our == 'Z') return 6;
    }

    if (opponent == 'C') {
        if (our == 'X') return 6;
        if (our == 'Y') return 0;
    }

    return 3;
}

function determineShapeScore2(opponent: string, result: string): number {
    if (opponent == 'A') {
        if (result == 'Y') return 1;
        if (result == 'Z') return 2;
    }

    if (opponent == 'B') {
        if (result == 'X') return 1;
        if (result == 'Y') return 2;
    }

    if (opponent == 'C') {
        if (result == 'Z') return 1;
        if (result == 'X') return 2;
    }

    return 3;
}

function determineOutcomeScore2(our: string): number {
    switch (our) {
        case 'Y': return 3;
        case 'Z': return 6;
        case 'X': default: return 0;
    }
}

// challenge1()
challenge2();