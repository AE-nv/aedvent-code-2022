defmodule Aoc5 do
    def chunk(string, chunk_size) do
        string
        |> String.split("")
        |> Enum.chunk_every(chunk_size)
        |> Enum.filter(fn chunk -> Enum.count(chunk) == chunk_size end)
        |> Enum.map(&Enum.join(&1, ""))
        |> Enum.map(&String.trim/1)
      end

      def transpose(rows) do
        rows
        |> List.zip
        |> Enum.map(&Tuple.to_list/1)
      end

    def parse_machine_state(machine_state) do
        machine_state
        |> String.split("\r\n")
        |> Enum.map(&chunk(&1,4))
        |> transpose
        |> Enum.map(&Enum.reverse/1)
        |> Enum.map(fn [bucket | blocks] -> %{:bucket => bucket, :blocks =>
            Enum.filter(Enum.reverse(blocks), fn block -> String.length(block)>0 end)} end)

    end

    def parse_command(command)do
        regex = ~r/move\s(?<nMove>\d+)\sfrom\s(?<nFrom>\d+)\sto\s(?<nTo>\d+)/
        %{"nFrom" => f, "nMove" => m, "nTo" => t} = Regex.named_captures(regex, command)
        %{:move => m, :from => f, :to => t}
    end

    def parse_commands(raw_commands) do
        raw_commands
        |> String.split("\r\n")
        |> Enum.map(&parse_command/1)

    end

    def replace_bucket_conditionally(%{:bucket => b, :blocks => _blocks}, %{:bucket => b, :blocks => newBlocks}) do
        %{:bucket => b, :blocks => newBlocks}
    end
    def replace_bucket_conditionally(%{:bucket => b, :blocks => blocks}, %{:bucket => o, :blocks => _newBlocks})when b != o, do:
        %{:bucket => b, :blocks => blocks}

    def move_block(machine_state, from, to) do
        %{:blocks => [fHead|fTail], :bucket => fromBucket} = Enum.find(machine_state, fn %{:bucket => b, :blocks => _} -> b == from end)
        %{:blocks => toBlocks, :bucket => toBucket} = Enum.find(machine_state, fn %{:bucket => b, :blocks => _} -> b == to end)
        newFromRow = %{:blocks => fTail, :bucket => fromBucket}
        newToRow =  %{:blocks => [fHead | toBlocks], :bucket => toBucket}
        machine_state
        |> Enum.map(&(replace_bucket_conditionally(&1,newFromRow)))
        |> Enum.map(&(replace_bucket_conditionally(&1,newToRow)))

    end

    def apply_command_to_machine(machine_state, %{:from => f, :move => m, :to => t}) do
         Integer.parse(m)
        |> Kernel.then(fn {num,_} -> Enum.reduce(1..num,machine_state,fn(_n,state) ->  move_block(state, f,t) end) end)
    end

    def strip_brackets(block)do
        regex = ~r/\[(?<blockName>\w+)\]/
        %{"blockName" => n} = Regex.named_captures(regex,block)
        n
    end

    def aoc_5_1(input) do
        input
        |> String.split("\r\n\r\n")
        |> Kernel.then(fn [machine_state, commands] -> %{:ms => parse_machine_state(machine_state), :c => parse_commands(commands)} end)
        |> Kernel.then(fn %{:ms => machine_state, :c => commands} ->
            Enum.reduce(commands, machine_state, fn (command,acc) -> apply_command_to_machine(acc,command)
                end)
            end)
        |> Enum.map(fn %{:blocks => [h|_]} -> h end)
        |> Enum.map(&strip_brackets/1)
        |> Enum.join("")
    end
    def aoc_5_2(input) do end
end

{:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/aoc5.input.txt")
IO.puts(Aoc5.aoc_5_1(contents))
#IO.puts(Aoc5.aoc_5_2(contents))
