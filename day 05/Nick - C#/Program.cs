using System;
using System.Linq;
using System.Collections.Generic;

namespace Day5
{
    class Program
    {
        static void Main(string[] args)
        {
            var lines = System.IO.File.ReadAllLines(@"input.txt").ToList();
            var numberOfStacks = 0;
            foreach (var line in lines)
            {
                if (line.Contains("1")) 
                {
                    numberOfStacks = int.Parse(line.Trim().Last().ToString());
                    break;
                }
            }
            var allStacks = new List<string>();

            for (int i=0; i<numberOfStacks; i++)
            {
                allStacks.Add("");
                allStacks[i] = "";
            }

            foreach (var line in lines)
            {
                if (string.IsNullOrEmpty(line) || line[1] =='1' )
                {
                    continue;
                }
                if(line.Contains('['))
                {
                    for (var i = 1; i < line.Length; i += 4)
                    {
                        allStacks[(i-1)/4] += line[i];
                    }
                    allStacks.ForEach(x => x.Trim());
                    continue;
                }
                var trimmedStacks = new List<string>();
                foreach (var stack in allStacks) {
                    trimmedStacks.Add(stack.Trim());
                }
                allStacks = trimmedStacks;
                var replacedline = line.Replace("move", "").Replace("from", "").Replace("to", "");          
                var lineArray = replacedline.Trim().Split(" ").ToList().Where(x => x.Length > 0).Select(x => int.Parse(x)).ToList();
                
                var itemToMove = allStacks[lineArray[1] -1].Substring(0, lineArray[0]);
                allStacks[lineArray[1] - 1] = allStacks[lineArray[1] -1].Remove(0, lineArray[0]);
                allStacks[lineArray[2] -1] = itemToMove + allStacks[lineArray[2] -1];
                
                
            }
            foreach (var stack in allStacks)
                Console.WriteLine(stack.First());
            

        }
    }
}
