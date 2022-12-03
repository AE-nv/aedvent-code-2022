using static RPS;
using static Result;

var input = File.ReadAllLines("input.txt");
var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));
int Part1() => (
    from line in input
    let opponentMove = ToMove(line[0])
    let myMove = ToMove(line[2])
    select CalculateScore(myMove, Play(opponentMove, myMove))).Sum();
int Part2() => (
    from line in input
    let opponentMove = ToMove(line[0])
    let result = ToResult(line[2])
    let myMove = DefineMove(opponentMove, result)
    select CalculateScore(myMove, result)).Sum();
int CalculateScore(RPS move, Result result)
{
    var value = move switch
    {
        Rock => 1,
        Paper => 2,
        Scissors => 3
    };
    var score = result switch
    {
        Lose => 0,
        Draw => 3,
        Win => 6
    };
    return value + score;
}

RPS ToMove(char input) => input switch
{
    'A' or 'X' => Rock,
    'B' or 'Y' => Paper,
    'C' or 'Z' => Scissors
};
Result ToResult(char input) => input switch
{
    'X' => Lose,
    'Y' => Draw,
    'Z' => Win
};
Result Play(RPS opponentMove, RPS myMove) => (opponentMove, myMove) switch
{
    (Rock, Rock) => Draw,
    (Rock, Paper) => Win,
    (Rock, Scissors) => Lose,
    (Paper, Rock) => Lose,
    (Paper, Paper) => Draw,
    (Paper, Scissors) => Win,
    (Scissors, Rock) => Win,
    (Scissors, Paper) => Lose,
    (Scissors, Scissors) => Draw
};
RPS DefineMove(RPS opponentMove, Result outcome) => outcome switch
{
    Lose => opponentMove switch
    {
        Rock => Scissors,
        Paper => Rock,
        Scissors => Paper
    },
    Draw => opponentMove,
    Win => opponentMove switch
    {
        Rock => Paper,
        Paper => Scissors,
        Scissors => Rock
    }
};
enum RPS
{
    Rock,
    Paper,
    Scissors
}

enum Result
{
    Win,
    Draw,
    Lose
}