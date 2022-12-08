using System;
using System.Linq;
using System.Collections.Generic;

namespace Day8
{

    class Program
    {
        static void Main(string[] args)
        {
            var lines = System.IO.File.ReadAllLines(@"input.txt");
            var splittedLines = lines.Select(x => x.ToCharArray().Select(y => int.Parse(y.ToString())).ToList()).ToList();
            var number = 0;
            var maxScore = 0;
            for (int i = 0; i < splittedLines.Count; i++)
            {
                for (int j= 0; j< splittedLines[0].Count; j++)
                {
                    if (CheckVisibleLeft(splittedLines, i, j) || CheckVisibleRight(splittedLines, i, j) || CheckVisibleUp(splittedLines, i, j) || CheckVisibleDown(splittedLines, i, j))
                        number++;
                    var currentScore = GetScoreLeft(splittedLines, i, j) * GetScoreRight(splittedLines, i, j) * GetScoreUp(splittedLines, i, j) * GetScoreDown(splittedLines, i, j);
                    if (currentScore > maxScore)
                    {
                        maxScore = currentScore;
                    }
                }
            }
            //Part 1
            Console.WriteLine(number);
            //Part 2
            Console.WriteLine(maxScore);            
        }

        private static bool CheckVisibleLeft(List<List<int>> input, int i, int j)
        {
            for (int x = 0; x < i; x++)
            {
                if (input[x][j] >= input[i][j]) return false;
            }
            return true;
        }
        private static bool CheckVisibleUp(List<List<int>> input, int i, int j)
        {
            for (int x = 0; x < j; x++)
            {
                if (input[i][x] >= input[i][j]) return false;
            }
            return true;
        }
        private static bool CheckVisibleRight(List<List<int>> input, int i, int j)
        {
            for (int x = i + 1; x < input.Count; x++)
            {
                if (input[x][j] >= input[i][j]) return false;
            }
            return true;
        }
        private static bool CheckVisibleDown(List<List<int>> input, int i, int j)
        {
            for (int x = j + 1; x < input[0].Count; x++)
            {
                if (input[i][x] >= input[i][j]) return false;
            }
            return true;
        }

        private static int GetScoreLeft(List<List<int>> input, int i, int j)
        {
            var score = 0;
            for (int x = i - 1; x >= 0; x--)
            {
                if (input[x][j] >= input[i][j]) return score + 1;
                score++;
            }
            return score;
        }
        private static int GetScoreUp(List<List<int>> input, int i, int j)
        {
            var score = 0;
            for (int x = j-1; x >= 0; x--)
            {
                if (input[i][x] >= input[i][j]) return score + 1;
                score++;
            }
            return score;
        }
        private static int GetScoreRight(List<List<int>> input, int i, int j)
        {
            var score = 0;
            for (int x = i + 1; x < input.Count; x++)
            {
                if (input[x][j] >= input[i][j]) return score +1;
                score++;
            }
            return score;
        }
        private static int GetScoreDown(List<List<int>> input, int i, int j)
        {
            var score = 0;
            for (int x = j + 1; x < input[0].Count; x++)
            {
                if (input[i][x] >= input[i][j]) return score +1;
                score++;
            }
            return score;
        }
    }
}
