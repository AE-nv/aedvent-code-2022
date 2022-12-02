defmodule Aoc2 do
    def toRPS(input) do
        case input do
            "X" -> :rock
            "A" -> :rock
            "Y" -> :paper
            "B" -> :paper
            "Z" -> :scissors
            "C" -> :scissors
        end
    end
    def toDesiredOutcome(input) do
        case input do
            "X" -> :lose
            "Y" -> :draw
            "Z" -> :win 
        end
    end
    def score(opponentRPS, playerRPS) do
        case {opponentRPS, playerRPS} do
            {:rock,:rock} -> 1 + 3
            {:rock,:paper} -> 2 + 6
            {:rock,:scissors} -> 3 + 0
            {:paper,:rock} -> 1 + 0
            {:paper,:paper} -> 2 + 3
            {:paper,:scissors} -> 3 + 6
            {:scissors,:rock} -> 1 + 6
            {:scissors,:paper} -> 2 + 0
            {:scissors,:scissors} -> 3 + 3
            _ -> 0
        end
    end

    def to_player_strategy(opponentRPS, desiredOutcome) do
        case {opponentRPS,desiredOutcome} do 
            {_,:draw} -> opponentRPS
            {:rock,:lose} -> :scissors
            {:rock,:win} -> :paper
            {:paper,:lose} -> :rock
            {:paper,:win} -> :scissors
            {:scissors,:lose} -> :paper
            {:scissors,:win} -> :rock
        end
    end

    def aoc_2_base(input) do
        input
        |> String.split("\r\n")
        |> Enum.map(&String.split(&1," "))
    end

    def aoc_2_1(input) do
        input
        |> aoc_2_base
        |> Enum.map(fn [fst,sec] -> [toRPS(fst),toRPS(sec)] end)
        |> Enum.map(fn [oRPS,pRPS] -> score(oRPS,pRPS) end)
        |> Enum.sum
    end

    def aoc_2_2(input) do
        input
        |> aoc_2_base
        |> Enum.map(fn [fst,sec] -> [toRPS(fst),toDesiredOutcome(sec)]end)
        |> Enum.map(fn [oRPS,dom] -> [oRPS,to_player_strategy(oRPS,dom)]end)
        |> Enum.map(fn [oRPS,pRPS] -> score(oRPS,pRPS)end)
        |> Enum.sum
    end
    
end

    {:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc2.input.txt")
    IO.puts(Aoc2.aoc_2_1(contents))
    IO.puts(Aoc2.aoc_2_2(contents))