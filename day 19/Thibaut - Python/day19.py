import copy
import re
import numpy as np
from copy import deepcopy

def get_optimal_robot_counts(og_resources, og_robot_counts, blueprint, time_left = 24):
    if time_left==0:
        return og_resources[3]
    resources = copy.deepcopy(og_resources)
    robot_counts = copy.deepcopy(og_robot_counts)
    #either you build a robot of a type or you don't
    n_geodes_options = []
    nb_buildable_robots = og_resources[0]//blueprint["ore_robot"]
    for n in range(0, nb_buildable_robots+1):
        resources -= np.array([n*blueprint["ore_robot"], 0, 0, 0])
        robot_counts += np.array([n, 0, 0, 0])
        nb_buildable_robots = resources[0] // blueprint["clay_robot"]
        for m in range(0, nb_buildable_robots+1):
            resources -= np.array([m * blueprint["clay_robot"], 0, 0, 0])
            robot_counts += np.array([0, m, 0, 0])
            nb_buildable_robots = min(resources[0] // blueprint["obsidian_robot"]["ore"],
                                      resources[1] // blueprint["obsidian_robot"]["clay"])
            for l in range(0, nb_buildable_robots+1):
                resources -= np.array([l * blueprint["obsidian_robot"]["ore"], l * blueprint["obsidian_robot"]["clay"], 0, 0])
                robot_counts += np.array([0, 0, l, 0])
                nb_buildable_robots = min(resources[0] // blueprint["geode_robot"]["ore"],
                                          resources[2] // blueprint["geode_robot"]["obsidian"])
                for k in range(0, nb_buildable_robots+1):
                    resources -= np.array([l * blueprint["geode_robot"]["ore"], 0, l * blueprint["geode_robot"]["obsidian"], 0])
                    robot_counts += np.array([0, 0, 0, k])
                    n_geodes_options.append(get_optimal_robot_counts(resources+robot_counts, robot_counts, blueprint, time_left-1))
    return max(n_geodes_options)

def get_nb_buildable_robots(resources, cost):
    constraints = []
    for i in range(0, len(cost)):
        if cost[i] > 0:
            constraints.append(resources[i] //cost[i])
    return min(constraints)

def get_max_costs(costs):
    max_costs = []
    for i in range(0,4):
        temp = []
        for cost, _ in costs:
            temp.append(cost[i])
        max_costs.append(max(temp))
    return max_costs

def get_optimal_robot_counts2(resources, robots, costs, time_left=24):
    if time_left==1:
        return resources[3]+robots[3]
    print(time_left)
    max_costs = get_max_costs(costs)
    nb_buildable_robots = get_nb_buildable_robots(resources, costs[0][0])
    if robots[0]>=max_costs[0]:
        nb_buildable_robots=0 #no need to build more robots of this type as I can't build

    n_geodes_options = []
    for a in range(0, nb_buildable_robots+1):
        post_ore_bot_resources = resources - a*costs[0][0]
        post_ore_bots = robots + a*costs[0][1]
        nb_buildable_robots = get_nb_buildable_robots(post_ore_bot_resources, costs[1][0])
        for b in range(0, nb_buildable_robots+1):
            post_clay_bot_resources = post_ore_bot_resources - b*costs[1][0]
            post_clay_bots = post_ore_bots + b*costs[1][1]
            nb_buildable_robots = get_nb_buildable_robots(post_clay_bot_resources, costs[2][0])
            for c in range(0, nb_buildable_robots+1):
                post_obsidian_bot_resources = post_clay_bot_resources - c*costs[2][0]
                post_obsidian_bots = post_clay_bots + c*costs[2][1]
                nb_buildable_robots = get_nb_buildable_robots(post_obsidian_bot_resources, costs[3][0])
                for d in range(0, nb_buildable_robots+1):
                    post_geode_bot_resources = post_obsidian_bot_resources - d*costs[2][0]
                    post_geode_bots = post_obsidian_bots + d*costs[2][1]
                    if time_left - (a + b + c + d) > 0:
                        n_geodes_options.append(get_optimal_robot_counts2(post_geode_bot_resources+post_geode_bots, post_geode_bots, costs, time_left - 1))
    return max(n_geodes_options)


if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    qualities = []
    blueprints = {}
    for blueprint in data:
        index, ore_robot_cost, clay_robot_cost, obsidian_robot_ore_cost, obsidian_robot_clay_cost, geode_robot_ore_cost, geode_robot_obsidian_cost = [int(c) for c in re.findall(r'\d+', blueprint.rstrip())]
        blueprints[index] = {"ore_robot": ore_robot_cost,
                             "clay_robot": clay_robot_cost,
                             "obsidian_robot": {
                                 "ore": obsidian_robot_ore_cost,
                                 "clay": obsidian_robot_clay_cost
                             },
                             "geode_robot": {
                                 "ore": geode_robot_ore_cost,
                                 "obsidian": geode_robot_obsidian_cost
                             }}
        costs = [(np.array([ore_robot_cost, 0, 0, 0]),np.array([1, 0, 0, 0])),
                 (np.array([clay_robot_cost, 0, 0, 0]), np.array([0, 1, 0, 0])),
                 (np.array([obsidian_robot_ore_cost, obsidian_robot_clay_cost, 0, 0]), np.array([0, 0, 1, 0])),
                 (np.array([geode_robot_ore_cost, 0, geode_robot_obsidian_cost, 0]), np.array([0, 0, 0, 1]))]

        resources = np.array([0, 0, 0, 0]) #ore, clay, obsidian, geodes
        robot_counts = np.array([1, 0, 0, 0])
        # print(get_optimal_robot_counts(resources, robot_counts, blueprints[index]))
        # print(get_optimal_robot_counts2(resources, robot_counts, costs))
        # print(resources)
        max_costs = get_max_costs(costs)
        frontier = [(list(resources), list(robot_counts), 24)]
        best = 0
        visited = set()
        last_time_left=24
        while len(frontier)>0:
            # print(len(frontier))
            resources, robots, time_left = frontier.pop(0)
            if time_left!=last_time_left:
                last_time_left = time_left
                print(len(frontier))
                print(time_left)



            resources = [min(r, max_costs[i]*time_left-robots[i]*(time_left-1)) for i, r in enumerate(resources[:3])]+[resources[3]]
            state = tuple(resources + robots + [time_left])
            if state in visited:
                continue
            else:
                visited.add(state)

            if time_left==0:
                if best<resources[3]:
                    best=resources[3]
                    print(best)
                    print(resources)
                    print(robots)
                    print(len(frontier))
                    print(max([t for _, _, t in frontier]))

                continue

            #Order of operations isn't correct, it should be first check, then mine, then have the robot complete

            frontier.append((list(np.array(resources) + np.array(robots)), copy.deepcopy(robots), time_left - 1))

            if get_nb_buildable_robots(np.array(resources), costs[0][0]) > 0 and robots[0]<=max_costs[0]:
                # print(get_nb_buildable_robots(np.array(resources), costs[0][0]))
                new_resources = np.array(resources)-costs[0][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[0][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

            if robots[1] <= max_costs[1] and get_nb_buildable_robots(np.array(resources), costs[1][0]) > 0:
                new_resources = np.array(resources)-costs[1][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[1][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

            if robots[2] <= max_costs[2] and get_nb_buildable_robots(np.array(resources), costs[2][0]) > 0:
                new_resources = np.array(resources)-costs[2][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[2][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

            if get_nb_buildable_robots(np.array(resources), costs[3][0]) > 0:
                new_resources = np.array(resources)-costs[3][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[3][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

        print("BEST: " + str(best) + " for index " + str(index))
        qualities.append(index*best)
    print("PART 1: "+str(sum(qualities)))

    qualities = []
    blueprints = {}
    for blueprint in data[:3]:
        index, ore_robot_cost, clay_robot_cost, obsidian_robot_ore_cost, obsidian_robot_clay_cost, geode_robot_ore_cost, geode_robot_obsidian_cost = [int(c) for c in re.findall(r'\d+', blueprint.rstrip())]
        blueprints[index] = {"ore_robot": ore_robot_cost,
                             "clay_robot": clay_robot_cost,
                             "obsidian_robot": {
                                 "ore": obsidian_robot_ore_cost,
                                 "clay": obsidian_robot_clay_cost
                             },
                             "geode_robot": {
                                 "ore": geode_robot_ore_cost,
                                 "obsidian": geode_robot_obsidian_cost
                             }}
        costs = [(np.array([ore_robot_cost, 0, 0, 0]),np.array([1, 0, 0, 0])),
                 (np.array([clay_robot_cost, 0, 0, 0]), np.array([0, 1, 0, 0])),
                 (np.array([obsidian_robot_ore_cost, obsidian_robot_clay_cost, 0, 0]), np.array([0, 0, 1, 0])),
                 (np.array([geode_robot_ore_cost, 0, geode_robot_obsidian_cost, 0]), np.array([0, 0, 0, 1]))]

        resources = np.array([0, 0, 0, 0]) #ore, clay, obsidian, geodes
        robot_counts = np.array([1, 0, 0, 0])
        # print(get_optimal_robot_counts(resources, robot_counts, blueprints[index]))
        # print(get_optimal_robot_counts2(resources, robot_counts, costs))
        # print(resources)
        max_costs = get_max_costs(costs)
        frontier = [(list(resources), list(robot_counts), 32)]
        best = 0
        visited = set()
        last_time_left=32
        while len(frontier)>0:
            # print(len(frontier))
            resources, robots, time_left = frontier.pop(0)
            if time_left!=last_time_left:
                last_time_left = time_left
                print(len(frontier))
                print(time_left)

            if time_left<10 and resources[3]==0:
                state = tuple(resources + robots + [time_left])
                visited.add(state)
                continue


            resources = [min(r, max_costs[i]*time_left-robots[i]*(time_left-1)) for i, r in enumerate(resources[:3])]+[resources[3]]
            state = tuple(resources + robots + [time_left])
            if state in visited:
                continue
            else:
                visited.add(state)

            if time_left==0:
                if best<resources[3]:
                    best=resources[3]
                continue

            frontier.append((list(np.array(resources) + np.array(robots)), copy.deepcopy(robots), time_left - 1))

            if get_nb_buildable_robots(np.array(resources), costs[0][0]) > 0 and robots[0]<=max_costs[0]:
                # print(get_nb_buildable_robots(np.array(resources), costs[0][0]))
                new_resources = np.array(resources)-costs[0][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[0][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

            if robots[1] <= max_costs[1] and get_nb_buildable_robots(np.array(resources), costs[1][0]) > 0:
                new_resources = np.array(resources)-costs[1][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[1][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

            if robots[2] <= max_costs[2] and get_nb_buildable_robots(np.array(resources), costs[2][0]) > 0:
                new_resources = np.array(resources)-costs[2][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[2][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

            if get_nb_buildable_robots(np.array(resources), costs[3][0]) > 0:
                new_resources = np.array(resources)-costs[3][0]
                new_resources += np.array(robots)
                new_robots = np.array(robots)+costs[3][1]
                frontier.append((list(new_resources), list(new_robots), time_left-1))

        print("BEST: " + str(best) + " for index " + str(index))
        qualities.append(best)
    print(qualities)
    print("PART 2: "+str(np.prod(qualities)))