import * as common from '../common/index'

function main() {
    let score = 0;
    const lines = common.getFileLines('input.txt');

    lines.forEach((line: string) => {
        const first = line.slice(0, line.length / 2);
        const second = line.slice(line.length / 2);
        const char = getCommonChar(first, second);
        const priority = getPriority(char);
        score += priority;
    });

    console.log(score)
}

function main2() {
    let score = 0;
    let first: string;
    let second: string;
    let third: string;

    const lines = common.getFileLines('input.txt');

    lines.forEach((line: string, index: number) => {
        if ((index + 1) % 3 == 1) {
            first = line
        }

        if ((index + 1) % 3 == 2) {
            second = line
        }

        if (index > 0 && (index + 1) % 3 == 0) {
            third = line
            const char = getCommonChar2(first, second, third);
            const priority = getPriority(char);
            score += priority;
        }
    });

    console.log(score)
}

function getCommonChar(first: string, second: string): string {
    let i = first.length;
    while (i--) {
        if (second.includes(first[i])) return first[i];
    }
    return '';
}

function getCommonChar2(first: string, second: string, third: string): string {
    let i = first.length;
    while (i--) {
        if (second.includes(first[i]) && third.includes(first[i])) return first[i];
    }
    return '';
}

function getPriority(charAsString: string): number {
    const charCode = charAsString.charCodeAt(0);
    if (charCode >= 65 && charCode <= 90) return charCode - 38;
    if (charCode >= 97 && charCode <= 122) return charCode - 96;
    return 0;
}

// main();
main2();