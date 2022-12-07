using System;
using System.Linq;
using System.Collections.Generic;
using System.Runtime.CompilerServices;

namespace Day3
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] lines = System.IO.File.ReadAllLines(@"input.txt");
            var allLines = new List<List<List<char>>>();
            for (int i = 0; i < lines.Length; i += 3) {
                allLines.Add(new List<List<char>> { lines[i].ToCharArray().ToList(), lines[i+1].ToCharArray().ToList(), lines[i+2].ToCharArray().ToList() });
            }
            //foreach (var line in lines) {
            //    var first = line.Substring(0, (int)(line.Length / 2));
            //    var last = line.Substring((int)(line.Length / 2), (int)(line.Length / 2));
            //    allLines.Add(new Tuple<List<char>, List<char>>(first.ToCharArray().ToList(), last.ToCharArray().ToList()));
            //}
            var som = 0;
            foreach (var item in allLines) {
                var commonItem = item[0].Intersect(item[1]).Intersect(item[2]).First();
                var priority = (int)commonItem;
                if (priority > 96) priority -= 96;
                else priority -= 38;
                som += priority;
            }            
            
            Console.WriteLine(som);

           
        }
    }

}
