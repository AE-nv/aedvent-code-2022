using System;
using System.Linq;
using System.Collections.Generic;

namespace Day7
{

    class Program
    {
        static void Main(string[] args)
        {
            var lines = System.IO.File.ReadAllLines(@"input.txt");
            Directory currentDirectory = null;
            Directory startDirectory = null;
            var allDirectories = new List<Directory>();

            foreach (var line in lines)
            {
                if (line.StartsWith("$"))
                {
                    if (line.StartsWith("$ cd .."))
                    {
                        currentDirectory = currentDirectory.Parent;
                    }
                    else if (line.StartsWith("$ cd /"))
                    {
                        if (startDirectory == null)
                        {
                            startDirectory = new Directory("/", null);
                            allDirectories.Add(startDirectory); 
                        }
                        currentDirectory = startDirectory;
                    }
                    else if (line.StartsWith("$ cd "))
                    {
                        var dictName = line.Split("$ cd ").Last();
                        if (!currentDirectory.Children.Any(x => x.Name == dictName))
                        {
                            var newDirectory = new Directory("dictName", currentDirectory);
                            currentDirectory.Children.Add(newDirectory);
                            currentDirectory = newDirectory;
                            allDirectories.Add(newDirectory);
                        }
                        else
                        {
                            currentDirectory = currentDirectory.Children.Single(x => x.Name == dictName);
                        }
                    }
                    else if (line.StartsWith("$ ls"))
                    {
                        // nothing really to do here
                    }
                }
                else
                {
                    if (line.StartsWith("dir"))
                    {
                        if (!currentDirectory.Children.Any(x => x.Name == line.Split("dir ").Last()))
                        {
                            var nd = new Directory(line.Split("dir ").Last(), currentDirectory);
                            allDirectories.Add(nd);
                            currentDirectory.Children.Add(nd);
                        }
                    }
                    else
                    {
                        var splitted  = line.Split(" ");
                        currentDirectory.Files.Add(new File { Name = splitted[1], Size = int.Parse(splitted[0]) });
                    }
                }
            }
            //PART 1
            Console.WriteLine(allDirectories.Where(x => x.TotalSize() <= 100000).Sum(x => x.TotalSize()));
            //PART 2
            Console.WriteLine(allDirectories.Where(x => x.TotalSize() > (startDirectory.TotalSize() - 40000000)).OrderBy(x => x.TotalSize()).First().TotalSize());
            
        }
        
       

        public class Directory { 
            public string Name;
            public Directory Parent;
            public List<Directory> Children;
            public List<File> Files;

            public Directory(string name, Directory parent)
            {
                Name = name;
                Parent = parent;
                Children = new List<Directory>();
                Files = new List<File>();
            }

            public int TotalSize()
            {
                return Children.Sum(x => x.TotalSize()) + Files.Sum(x => x.Size);
            }
        }

        public class File
        {
            public string Name;
            public int Size;
        }
    }
}
