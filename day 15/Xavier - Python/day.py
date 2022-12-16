import re
from typing import Generator, Optional

def get_sensor_and_beacon_coords(line: str) -> tuple[tuple[int, int], tuple[int, int]]:
    match = re.search(r'Sensor at x=(-?\d*), y=(-?\d*): closest beacon is at x=(-?\d*), y=(-?\d*)', line)
    coords = (0,0,0,0)
    if match is not None:
        coords = match.groups()
    sensor_x, sensor_y, beacon_x, beacon_y = map(int, coords)
    return (sensor_x, sensor_y), (beacon_x, beacon_y)

def manhattan_distance(p1: tuple[int, int], p2: tuple[int, int]) -> int:
    return abs(p1[0]-p2[0]) + abs(p1[1]-p2[1])

def get_impossible_beacon_locations_at_y(y: int, sensors: list[tuple[int, int]], beacons: list[tuple[int, int]]) -> set[int]:
    impossible_points: set[int] = set()
    for i in range(len(sensors)):
        sensor, beacon = sensors[i], beacons[i]
        distance = manhattan_distance(sensor, beacon)
        dx_edge = distance-abs(y-sensor[1])
        if dx_edge >= 0:
            impossible_points.update(list(range(sensor[0]-dx_edge, sensor[0]+dx_edge+1)))
    beacons_at_y = set(beacon[0] for beacon in beacons if beacon[1] == y)
    return impossible_points - beacons_at_y

def generate_points_at_distance(sensor: tuple[int, int], distance: int) -> Generator[tuple[int, int], None, None]:
    sx, sy = sensor
    for x in range(0, distance+1):
        y = distance-x
        yield (sx+x,sy+y)
        yield (sx-x,sy-y)
        if not y == 0 and not x == 0:
            yield (sx+-x,sy+y)
            yield (sx+x,sy-y)

def undetectable(p: tuple[int, int], sensors: list[tuple[int, int]], beacons: list[tuple[int, int]]) -> bool:
    for i in range(len(sensors)):
        if manhattan_distance(p, sensors[i]) <= manhattan_distance(sensors[i], beacons[i]):
            return False
    return True

def tuning_frequency(beacon: tuple[int, int]) -> int:
    return beacon[0]*4_000_000 + beacon[1]

def find_distress_beacon(sensors: list[tuple[int, int]], beacons: list[tuple[int, int]], min: int, max: int) -> Optional[tuple[int, int]]:
    for i in range(len(sensors)):
        sensor, beacon = sensors[i], beacons[i]
        distance = manhattan_distance(sensor, beacon)+1
        for p in generate_points_at_distance(sensor, distance):
            if min<=p[0]<=max and min<=p[1]<=max and undetectable(p, sensors, beacons):
                return p
    return None

if __name__ == "__main__":
    input = open('input.txt').read().splitlines()
    sensors: list[tuple[int, int]] = []
    beacons: list[tuple[int, int]] = []
    for line in input:
        sensor, beacon = get_sensor_and_beacon_coords(line)
        sensors.append(sensor)
        beacons.append(beacon)

    impossible_beacon_locations: set[int] = get_impossible_beacon_locations_at_y(2_000_000, sensors, beacons)
    print(len(impossible_beacon_locations))

    distress_beacon = find_distress_beacon(sensors, beacons, 0, 4_000_000)
    f = tuning_frequency(distress_beacon) if distress_beacon is not None else 0
    print(f)
