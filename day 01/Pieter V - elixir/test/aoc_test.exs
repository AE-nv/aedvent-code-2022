defmodule AocTest do
  use ExUnit.Case
  doctest Aoc

  test "aoc1 satisfies demoinput" do
    {:ok,contents} = File.read("C:/Users/Pieter.Verlinden/Documents/AE Studio/advent of code/aoc/test/aoc1.input.test.txt")
    assert Aoc.aoc_1_1(contents) == 24000
    assert Aoc.aoc_1_2(contents) == 45000
  end
end
