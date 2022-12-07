using System;
using System.Linq;
using System.Collections.Generic;

namespace Day4
{
    class Program
    {
        static void Main(string[] args)
        {
            //READ INPUT
            var lines = System.IO.File.ReadAllLines(@"input.txt").ToList();
            var sum = 0;
            foreach (var line in lines)
            {
                var splittedLines = line.Split(',').Select(x => x.Split('-').ToList()).ToList();
                var overLap = Math.Min(int.Parse(splittedLines[0][1]), int.Parse(splittedLines[1][1])) - Math.Max(int.Parse(splittedLines[0][0]), int.Parse(splittedLines[1][0])) +1;
                // if (overLap >= int.Parse(splittedLines[0][1]) - int.Parse(splittedLines[0][0]) + 1) { sum++; continue; };
                //if (overLap >= int.Parse(splittedLines[1][1]) - int.Parse(splittedLines[1][0]) + 1) sum++;
                if (overLap > 0) sum++;
            }
            
            Console.WriteLine(sum);
        }
       
    }
}
