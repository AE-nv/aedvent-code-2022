using System;
using System.Collections.Generic;
using System.Linq;

namespace Day1
{
    class Program
    {
        static void Main(string[] args)
        {
            var lines = System.IO.File.ReadAllLines(@"input.txt");
            var numbers = new List<List<int>>();
            var tempList = new List<int>();
            foreach (var line in lines) 
            {
                if (line.Length > 0) {
                    tempList.Add(int.Parse(line));
                }
                else {
                    numbers.Add(tempList.Select(x=> x).ToList());
                    tempList = new List<int>();
                }
            }
            numbers.Add(tempList);
            var sortedList = numbers.Select(x => x.Sum()).OrderByDescending(x => x).ToList();

            Console.WriteLine(sortedList.First() + sortedList[1] + sortedList[2]);

            
        }
    }
}
