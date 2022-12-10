import * as common from '../common/index'

let cycle = 1;
let register = 1;
let signalStrengths: number[] = [];

function challenge1() {
    const lines = common.getFileLines('input.txt');
    lines.forEach(line => {
        if (line == 'noop') {
            cycle++;
        } else {
            const value = parseInt(line.split(' ')[1]);
            cycle++;
            check();
            cycle++;
            register += value;
        }
        check();
    });
    console.log(signalStrengths.reduce((acc, value) => acc + value, 0))
}

function check() {
    if ((cycle - 20) % 40 == 0) signalStrengths.push(register * cycle); 
}

let crt: string = '';

function challenge2() {
    const lines = common.getFileLines('input.txt');
    draw();
    lines.forEach(line => {
        if (line == 'noop') {
            cycle++;
        } else {
            const value = parseInt(line.split(' ')[1]);
            cycle++;
            draw();
            cycle++;
            register += value;
        }
        draw();
    });
    console.log(crt);
}

function draw() {
    if ((cycle - 1) % 40 == 0) crt += '\n';
    let visible = (cycle - 2) % 40 == register || (cycle - 1) % 40 == register || cycle % 40 == register;
    visible ? crt += '#' : crt += '.';
}

// challenge1();
challenge2();