using System;
using System.Linq;
using System.Collections.Generic;

namespace Day2
{
    class Program
    {
        static void Main(string[] args)
        {
            var input = System.IO.File.ReadAllLines(@"input.txt").Select(x => x.Split(' '));           
            var score = 0;
            foreach (var i in input)
            {
                if (i[1] == "X") 
                {
                    score += 0;
                    if (i[0] == "A") 
                    {                        
                        score += 3;
                        continue;
                    }
                    if (i[0] == "B")
                    {
                        score += 1;
                        continue;
                    }
                    if (i[0] == "C")
                    {
                        score += 2;
                        continue;
                    }
                }
                if (i[1] == "Y")
                {
                    score += 3;
                    if (i[0] == "A")
                    {
                        score += 1;
                        continue;
                    }
                    if (i[0] == "B")
                    {
                        score += 2;
                        continue;
                    }
                    if (i[0] == "C")
                    {
                        score += 3;
                        continue;
                    }
                }
                if (i[1] == "Z")
                {
                    score += 6;
                    if (i[0] == "A")
                    {
                        score += 2;
                        continue;
                    }
                    if (i[0] == "B")
                    {
                        score += 3;
                        continue;
                    }
                    if (i[0] == "C")
                    {
                        score += 1;
                        continue;
                    }
                }
            }
            Console.WriteLine(score);
        }
    }
}
