defmodule Aoc do
  def aoc_1(input) do
    input
    |> String.split("\r\n\r\n")
    |> Enum.map(&String.split(&1,"\r\n"))
    |> Enum.map(&Enum.map(&1,fn x -> Integer.parse(x) end))
    |> Enum.map(&Enum.map(&1,fn {num, _} -> num end))
    |> Enum.map(&List.foldl(&1,0,fn(x,acc) -> x + acc end))
    |> Enum.reduce(&max/2)
  end
  def run1 do
    {:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc1.input.txt")
    IO.puts(aoc_1(contents))
  end
end

Aoc.run1()