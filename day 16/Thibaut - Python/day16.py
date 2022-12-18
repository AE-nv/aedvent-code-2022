import pprint
from copy import deepcopy
from functools import cache

def get_current_flow(valves):
    return sum([flow_rate for flow_rate, _, opened in valves.values() if opened])


def get_optimal_flow(valves, time_left, current_valve="AA"):
    current_flow = get_current_flow(valves)
    if time_left==0:
        return current_flow

    flow_rate, destinations, opened = valves[current_valve]
    if opened or flow_rate==0:
        best_potential_flow = 0
        for d in destinations:
            potential_flow = get_optimal_flow(valves, time_left-1, d)
            if best_potential_flow < potential_flow:
                best_potential_flow = potential_flow
        return current_flow+best_potential_flow
    else:
        #try path where valve is opened and where it is not opened
        valves[current_valve] = (flow_rate, destinations, True)
        best_potential_flow1 = 0
        if time_left>1:
            for d in destinations:
                potential_flow = get_optimal_flow(valves, time_left - 2, d)
                if best_potential_flow1 < potential_flow:
                    best_potential_flow1 = potential_flow

        valves[current_valve] = (flow_rate, destinations, False)
        best_potential_flow2 = 0
        for d in destinations:
            potential_flow = get_optimal_flow(valves, time_left - 1, d)
            if best_potential_flow2 < potential_flow:
                best_potential_flow2 = potential_flow
        return max([current_flow*2 + best_potential_flow1, current_flow+best_potential_flow2])

def get_optimal_flow2(valves, start = "AA"):
    frontier = [(start, 30, 0, [])]
    if valves[start][0]>0:
        frontier.append((start, 29, 0, [start]))
    best_flow=0

    while len(frontier)>0:
        print(len(frontier))
        current_valve, time_left, score, open_valves = frontier.pop()
        current_flow = sum([valves[v][0] for v in open_valves])
        if current_flow>0:
            print(current_flow)
        if time_left==0 and best_flow<current_flow+score:
            best_flow = current_flow+score
            continue

        if current_valve not in open_valves and valves[current_valve][0]>0:
            frontier.append((current_valve, time_left-1, score+current_flow, open_valves+[current_valve]))

        destinations = valves[current_valve][1]
        for d in destinations:
            frontier.append((d, time_left-1, score+current_flow, open_valves))
    return best_flow


def get_optimal_flow_using_advanced_valves(advanced_valves, total_time = 30, start="AA"):
    frontier = [(start, total_time, 0, [])]

    best_flow = 0
    while len(frontier)>0:
        # print(len(frontier))
        location, time_left, score, open_valves = frontier.pop()
        current_flow = sum([valves[v][0] for v in open_valves])
        frontier_expanded=False
        for next_valve, (distance, _) in advanced_valves[location][1].items():
            flow = current_flow*(distance+1) #+1 to also count the time it takes to open the next valve
            if distance < time_left and next_valve not in open_valves:
                frontier.append((next_valve, time_left-(distance+1), score+flow, open_valves+[next_valve]))
                frontier_expanded=True
        if not frontier_expanded:
            total_score = score+current_flow*time_left
            if total_score>best_flow:
                best_flow=total_score
    return best_flow


    # hop to valve that can be reached in the time that is left
    # take time to reach valve and multiply by current_flow, add to score for frontier point
    # make new frontier point at the valve for both opened valve (closed doesn't make sense cause you only visit a valve in case you will open it)


def find_shortest_path(valves, start, end, visited=[]):
    if start==end:
        return [end]
    else:
        shortest_path = list(valves.keys())
        for next_valve in valves[start][1]:
            if next_valve not in visited:
                path = [start] + find_shortest_path(valves, next_valve, end, [start]+visited)
                if len(path)<len(shortest_path):
                    shortest_path = path
        return shortest_path


def get_optimal_flow_using_elephant(advanced_valves, valves_of_interest, start = "AA", total_time=26):
    frontier = [((start, start), total_time, 0, [])]

    for valve in valves_of_interest:
        advanced_valves[valve][1][valve]=(0,[valve])

    best_flow = 0
    while len(frontier)>0:
        # print(len(frontier))
        (me, elephant), time_left, score, open_valves = frontier.pop()
        current_flow = sum([valves[v][0] for v in open_valves])
        frontier_expanded = False

        closed_valves = [valve for valve in valves_of_interest if valve not in open_valves]

        # case where there is only one closed valve:
        # find closest individual and multiply distance by current_flow, recalculate current flow to include new valve
        # frontier_expanded stays False
        for valve1 in closed_valves:
            for valve2 in closed_valves:
                if valve1!=valve2:
                    #Case for when equidistant
                    d1, p1 = advanced_valves[me][1][valve1]
                    d2, p2 = advanced_valves[elephant][1][valve2]
                    if d1==d2 and d1<time_left:
                        frontier_expanded=True
                        flow = current_flow * (d1 + 1)
                        frontier.append(((valve1, valve2), time_left-(d1+1), score+flow, [valve1, valve2]+open_valves))
                    else:
                        # I go to v1, elephant goes to v2
                        # closes distance determines the time passage
                        # Both end up the time_passage index of their path
                        d1, p1 = advanced_valves[me][1][valve1]
                        d2, p2 = advanced_valves[elephant][1][valve2]
                        lowest_distance = min(d1, d2)
                        if lowest_distance < time_left:
                            frontier_expanded = True
                            flow = current_flow * (lowest_distance + 1)
                            if lowest_distance == d1:
                                opened_valve = valve1
                                me_next = valve1
                                elephant_next = p2[lowest_distance + 1]
                            else:
                                opened_valve = valve2
                                me_next = p1[lowest_distance + 1]
                                elephant_next = valve2
                            frontier.append(((me_next, elephant_next), time_left - (lowest_distance + 1), score + flow,
                                             [opened_valve] + open_valves))

                    d1, p1 = advanced_valves[me][1][valve2]
                    d2, p2 = advanced_valves[elephant][1][valve1]
                    if d1==d2 and d1<time_left:
                        frontier_expanded = True
                        flow = current_flow * (d1 + 1)
                        frontier.append(((valve2, valve1), time_left - (d1 + 1), score + flow, [valve1, valve2] + open_valves))
                    else:
                        #I go to v2, elephant goes to v1
                        #closes distance determines the time passage
                        #Both end up the time_passage index of their path
                        d1, p1 = advanced_valves[me][1][valve2]
                        d2, p2 = advanced_valves[elephant][1][valve1]
                        lowest_distance = min(d1, d2)
                        if lowest_distance < time_left:
                            frontier_expanded = True
                            flow = current_flow*(lowest_distance+1)
                            if lowest_distance == d1:
                                opened_valve = valve2
                                me_next = valve2
                                if lowest_distance+1>=len(p2):
                                    print(d1,d2)
                                    print(p2)
                                elephant_next = p2[lowest_distance+1]
                            else:
                                opened_valve = valve1
                                me_next = p1[lowest_distance+1]
                                elephant_next = valve1
                            frontier.append(((me_next, elephant_next), time_left-(lowest_distance+1), score+flow, [opened_valve] + open_valves))
        if not frontier_expanded:
            if len(closed_valves) == 1:
                last_valve = closed_valves[0]
                d1, _ = advanced_valves[me][1][last_valve]
                d2, _ = advanced_valves[elephant][1][last_valve]
                lowest_distance = min(d1, d2)
                if lowest_distance < time_left:
                    flow = current_flow * (lowest_distance + 1)
                    score+=flow
                    open_valves+=[last_valve]
                    current_flow = sum([valves[v][0] for v in open_valves])
                    time_left -= (lowest_distance+1)
            total_score = score + current_flow * time_left
            if total_score > best_flow:
                print(open_valves)
                print(total_score)
                best_flow = total_score

    return best_flow

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    valves={}
    valves_of_interest = []
    for l in data:
        P1, P2 = l.split(';')
        _, name, _, _, rate = P1.split()
        flow_rate = int(rate.split('=')[1])
        if flow_rate>0:
            valves_of_interest.append(name)
        destinations = [d[:2] for d in P2.split()[4:]]
        opened = False
        valves[name] = [flow_rate, destinations, opened]

    #optimize pathing to know how long it takes to go from node to nodes that have flows>0
    advanced_valves={}
    for valve in valves.keys():
        paths = {}
        for d in valves_of_interest:
            if d!=valve:
                path = find_shortest_path(valves, valve, d)
                paths[d] = (len(path)-1, path)

        print("processed valve: "+valve)
        advanced_valves[valve] = (valves[valve][0], paths)

    pprint.pprint(advanced_valves)

    flow = get_optimal_flow_using_advanced_valves(advanced_valves)
    print("PART 1: "+str(flow))

    flow = get_optimal_flow_using_elephant(advanced_valves, valves_of_interest)
    print("PART 2:" + str(flow))

    #PART 2: keep history for two individuals
    #alter advanced valves to keep shortest path as well
    # => use the path to determine new starting point for next calculation for the individual on the way to a valve
    #iterate over possible next valves that are not in each others history
    #   add points to frontier for individual one visiting a node and individual two visiting a node
    #count time upwards from 0, add pressure to calculation once the history adds up to the time