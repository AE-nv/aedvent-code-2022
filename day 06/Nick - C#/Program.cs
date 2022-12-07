using System;
using System.Linq;

namespace Day6
{
    class Program
    {
        static void Main(string[] args)
        {
            var input = System.IO.File.ReadAllLines(@"input.txt");
            for(var i = 13; i < input[0].Length; i++)
            {
                var l = input[0].Substring(i - 13, 14).ToCharArray().ToList();
                if (l.Distinct().Count() == 14) {
                    Console.WriteLine(i + 1);
                    break;
                }
            }
            
        }
    }
}
