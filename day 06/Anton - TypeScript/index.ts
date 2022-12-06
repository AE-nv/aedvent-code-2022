import * as common from '../common/index'

const AMOUNT: number = 14;
let lastX: string[] = [];

function challenge1() {
    const line = common.getFileLines('input.txt')[0];
    let chars = [...line];
    for (const {c, i} of chars.map((c,i) => ({c,i}))) {
        if (markerFound(c)) {
            console.log(i + 1);
            break;
        }
    }
}

function markerFound(char: string): boolean {
    lastX.push(char)

    if (lastX.length >= AMOUNT) {
        if (lastX.length == AMOUNT + 1) lastX = lastX.slice(-AMOUNT)

        for (let i = 0; i < AMOUNT; i++) {
            for (let j = 0; j < AMOUNT; j++) {
                if (i < j && lastX[i] == lastX[j]) return false;
            }
        }
        
        return true;
    }

    return false;
}

challenge1();