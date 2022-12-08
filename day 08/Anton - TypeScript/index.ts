import * as common from '../common/index'

let trees: number[][] = [];

function challenge1() {
    let result = 0;
    const lines = common.getFileLines('input.txt');
    lines.forEach(line => {
        readLine(line);
    });
    trees.forEach((treeLine, y) => treeLine.forEach((tree, x) => result += visibility(tree, x, y)))
    console.log('total', result)
}

function readLine(line: string) {
    let result: number[] = [];

    for (let i = 0; i < line.length; i++) {
        result.push(parseInt(line.charAt(i)));
    }

    trees.push(result);
}

function visibility(height: number, x: number, y: number): number {
    return (
        (visibilityUp(height, x, y) +
            visibilityDown(height, x, y) +
            visibilityLeft(height, x, y) +
            visibilityRight(height, x, y)) == 0 ? 0 : 1
    )
}

function visibilityUp(height: number, x: number, y: number): number {
    while (y > 0) {
        let up = trees[--y][x];
        if (up >= height) return 0;
    }
    return 1;
}

function visibilityDown(height: number, x: number, y: number): number {
    while (y < trees.length - 1) {
        let down = trees[++y][x];
        if (down >= height) return 0;
    }
    return 1;
}

function visibilityLeft(height: number, x: number, y: number): number {
    while (x > 0) {
        let left = trees[y][--x];
        if (left >= height) return 0;
    }
    return 1;
}

function visibilityRight(height: number, x: number, y: number): number {
    while (x < trees[0].length - 1) {
        let right = trees[y][++x];
        if (right >= height) return 0;
    }
    return 1;
}

function challenge2() {
    let result = 0;
    const lines = common.getFileLines('input.txt');
    lines.forEach(line => {
        readLine(line);
    });
    trees.forEach((treeLine, y) => treeLine.forEach((tree, x) => {
        const score = scenicScore(tree, x, y);
        result = score > result ? score : result;
    }))
    console.log('highest scenic score', result)
}

function scenicScore(height: number, x: number, y: number): number {
    return (
        visibilityUp2(height, x, y) *
        visibilityDown2(height, x, y) *
        visibilityLeft2(height, x, y) *
        visibilityRight2(height, x, y)
    )
}

function visibilityUp2(height: number, x: number, y: number): number {
    let vis = 0;
    while (y > 0) {
        let up = trees[--y][x];
        vis++;
        if (up >= height) break;
    }
    return vis;
}

function visibilityDown2(height: number, x: number, y: number): number {
    let vis = 0;
    while (y < trees.length - 1) {
        let down = trees[++y][x];
        vis++;
        if (down >= height) break;
    }
    return vis;
}

function visibilityLeft2(height: number, x: number, y: number): number {
    let vis = 0;
    while (x > 0) {
        let left = trees[y][--x];
        vis++;
        if (left >= height) break;
    }
    return vis;
}

function visibilityRight2(height: number, x: number, y: number): number {
    let vis = 0;
    while (x < trees[0].length - 1) {
        let right = trees[y][++x];
        console.log(x)
        vis++;
        if (right >= height) break;
    }
    return vis;
}

// challenge1();
challenge2();