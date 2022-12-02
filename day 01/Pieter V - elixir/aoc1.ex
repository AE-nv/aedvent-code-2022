defmodule Aoc1 do
  def aoc_1_base(input) do 
     input
    |> String.split("\r\n\r\n")
    |> Enum.map(&String.split(&1,"\r\n"))
    |> Enum.map(&Enum.map(&1,fn x -> Integer.parse(x) end))
    |> Enum.map(&Enum.map(&1,fn {num, _} -> num end))
    |> Enum.map(&Enum.sum(&1))
  end
  def aoc_1_1(input) do
    input
    |> aoc_1_base
    |> Enum.reduce(&max/2)
  end
  def aoc_1_2(input) do
    input
    |> aoc_1_base
    |> Enum.sort(:desc)
    |> Enum.take(3)
    |> Enum.sum
  end
  def run1_1 do
    {:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc1.input.txt")
    IO.puts(aoc_1_1(contents))
  end
  def run1_2 do
    {:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc1.input.txt")
    IO.puts(aoc_1_2(contents))
  end
end

Aoc1.run1_1()
Aoc1.run1_2()