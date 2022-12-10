import * as common from '../common/index'

interface Position {
    x: number,
    y: number,
}


let headPosition: Position = { x: 0, y: 0 };
let tailPosition: Position = { x: 0, y: 0 };
let visitedPositions: Array<Position> = [{ ...tailPosition }];

function challenge1() {
    const lines = common.getFileLines('input.txt');
    lines.forEach(line => {
        const segments = line.split(' ');
        move(segments[0], parseInt(segments[1]));
    });
    console.log(visitedPositions.length);
}

function move(direction: string, amount: number) {
    switch (direction) {
        case 'U':
            moveUp(amount);
            break;
        case 'D':
            moveDown(amount);
            break;
        case 'R':
            moveRight(amount);
            break;
        case 'L':
            moveLeft(amount);
            break;
        default:
            console.log('Input not supported');
            break;
    }
}

function moveUp(amount: number) {
    for (let i = 0; i < amount; i++) {
        headPosition.y++;

        if (euclideanDistance(headPosition, tailPosition) < 1.5) continue;

        tailPosition.x = headPosition.x;
        tailPosition.y++;

        addVisited(tailPosition.x, tailPosition.y)
    }
}

function moveDown(amount: number) {
    for (let i = 0; i < amount; i++) {
        headPosition.y--;

        if (euclideanDistance(headPosition, tailPosition) < 1.5) continue;

        tailPosition.x = headPosition.x;
        tailPosition.y--;

        addVisited(tailPosition.x, tailPosition.y)
    }
}

function moveRight(amount: number) {
    for (let i = 0; i < amount; i++) {
        headPosition.x++;

        if (euclideanDistance(headPosition, tailPosition) < 1.5) continue;

        tailPosition.y = headPosition.y;
        tailPosition.x++;

        addVisited(tailPosition.x, tailPosition.y)
    }
}

function moveLeft(amount: number) {
    for (let i = 0; i < amount; i++) {
        headPosition.x--;

        if (euclideanDistance(headPosition, tailPosition) < 1.5) continue;

        tailPosition.y = headPosition.y;
        tailPosition.x--;

        addVisited(tailPosition.x, tailPosition.y)
    }
}

function euclideanDistance(pos1: Position, pos2: Position): number {
    const xdiff = Math.pow((pos1.x - pos2.x), 2);
    const ydiff = Math.pow((pos1.y - pos2.y), 2);
    return Math.sqrt(xdiff + ydiff);
}

function addVisited(x: number, y: number) {
    // Only add position if not added yet
    const notPresent = visitedPositions.find(p => p.x == x && p.y == y) == undefined;
    if (notPresent) visitedPositions.push({ x: x, y: y }); // Push copy
}

function challenge2() {
    const lines = common.getFileLines('test.txt');
    lines.forEach(line => { });
}

challenge1();
// challenge2();