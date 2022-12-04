defmodule Aoc3 do
    def to_priority([]), do: []
    def to_priority([h|t]), do: to_priority(h) ++ to_priority(t)

    def to_priority(char) do
    
       alpha = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
        "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]
            aLPHA = alpha |> Enum.map(&(String.upcase/1))

        (alpha ++ aLPHA) |> Enum.find_index(&(char == &1)) |>  Kernel.+(1) |> Integer.to_string |>List.wrap
        
    end

    def find_common_chars([h|tail]) do
        tail 
        |> Enum.reduce(h |> String.graphemes |> MapSet.new,
             fn str, acc -> MapSet.intersection(acc,str|>String.graphemes|>MapSet.new) end)
        |> MapSet.to_list

    end

    def chunk_by_three([]), do: []
    def chunk_by_three([a,b,c | tail]), do: [[a,b,c]] ++ chunk_by_three(tail)

    def aoc_3_1(input) do
        input 
        |> String.split("\r\n") 
        |> Enum.map(&String.split_at(&1,floor(String.length(&1)/2)))
        |> Enum.map(&Tuple.to_list/1)
        |> Enum.map(&find_common_chars/1)
        |> Enum.flat_map(&to_priority/1) 
        |> Enum.map(&Integer.parse/1)
        |> Enum.map(fn {num, _} -> num end)
        |> Enum.sum
    end

    def aoc_3_2(input) do
        input
        |> String.split("\r\n")
        |> chunk_by_three 
        |> Enum.map(&find_common_chars/1)
        |> Enum.flat_map(&to_priority/1)
        |> Enum.map(&Integer.parse/1)
        |> Enum.map(fn {num, _} -> num end)
        |> Enum.sum
    end
end                                                                                                                                                                                  
       
    {:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc3.input.txt")
    IO.puts(Aoc3.aoc_3_1(contents))
    IO.puts(Aoc3.aoc_3_2(contents))
            
                 
                      
                                                                                                                                                                                                 