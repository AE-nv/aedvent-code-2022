defmodule Aoc6 do
  def aoc_6_1_multiline(input) do
    input
    |> String.split("\r\n")
    |> Enum.map(&aoc_6_1/1)
  end

  def aoc_6_2_multiline(input) do
    input
    |> String.split("\r\n")
    |> Enum.map(&aoc_6_2/1)
  end

  def find_start_of_packet([a,b,c,d|tail], acc) when a==b or a==c or a==d or b==c or b==d or c==d, do: find_start_of_packet([b,c,d|tail],[a|acc])
  def find_start_of_packet([a,b,c,d|_], acc) when a != b and a != c and a != d and b != c and b != d and c != d, do: acc


  def all_distinct_elements(list) do
    list
    |> Enum.frequencies
    |> Enum.map(fn {k,v} ->  if v == 1, do: k  end)
    |> Enum.reject( &is_nil/1)
    |> Kernel.then(fn newList -> length(newList) == length(list) end)
  end

  def find_start_of_message(message_chars, acc) when length(message_chars) < 14, do: [acc| message_chars]
  def find_start_of_message(message_chars,acc) do
    [h|t] = Enum.take(message_chars,14)
    if all_distinct_elements([h|t]) do acc
    else find_start_of_message(Enum.drop(message_chars,1),[h|acc])
  end


  end

  def aoc_6_1(input) do
    input
    |> String.graphemes
    |> find_start_of_packet([])
    |> length
    |> Kernel.+(4)
  end
  def aoc_6_2(input) do
    input
    |> String.graphemes
    |> find_start_of_message([])
    |> length
    |> Kernel.+(14)
  end

end

{:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc6.input.txt")
IO.puts(Aoc6.aoc_6_1(contents))
IO.puts(Aoc6.aoc_6_2(contents))
