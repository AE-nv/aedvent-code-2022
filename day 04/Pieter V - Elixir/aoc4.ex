defmodule Aoc4 do
    def to_flat_coordinates([r1,r2]) do
        [{s1,_},{e1,_}] = r1 |> String.split("-") |> Enum.map(&Integer.parse/1)
        [{s2,_},{e2,_}] = r2 |> String.split("-") |> Enum.map(&Integer.parse/1)
        {s1,e1,s2,e2}

    end
    
    def is_encapsulated_in({s1,e1,s2,e2}) when s1-s2 <= 0 and e1-e2 >=0, do: true
    def is_encapsulated_in({s1,e1,s2,e2}) when s1-s2 >= 0 and e1-e2 <=0, do: true
    def is_encapsulated_in(_), do: false

    def has_overlap({s1,e1,s2,e2}) when e1-s2 >= 0 and e2-s1 >= 0,do: true
    def has_overlap(_), do: false

    def aoc_4_base(input) do
        input 
        |> String.split("\r\n") 
        |> Enum.map(&String.split(&1, ","))
        |> Enum.map(&to_flat_coordinates/1)
    end

    def aoc_4_1(input) do
        input
        |> aoc_4_base
        |> Enum.map(&is_encapsulated_in/1)
        |> Enum.count(&(&1==true))
    end 

    def aoc_4_2(input) do 
        input
        |> aoc_4_base
        |> Enum.map(&has_overlap/1)
        |> Enum.count(&(&1==true))
    end 
end

{:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc4.input.txt")
IO.puts(Aoc4.aoc_4_1(contents))
IO.puts(Aoc4.aoc_4_2(contents))