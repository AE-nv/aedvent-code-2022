lines = open('input.txt').readlines()


class SensorBeaconPair:
    def __init__(self, xs, ys, xb, yb):
        self.xs = xs
        self.ys = ys
        self.xb = xb
        self.yb = yb
        self.distance = abs(xs-xb) + abs(ys-yb)

    def cover_at_y(self, y):
        y_cover = self.distance - abs(self.ys - y) + 1
        if y_cover >= 0:
            return y_cover
        else:
            return 0

    def get_segment(self, y):
        my_y_cover = self.cover_at_y(y)
        if my_y_cover == 0:
            return None
        a = self.xs - (my_y_cover-1)
        b = self.xs + (my_y_cover-1)
        return [a,b]


#   A B
#      C D

#   A B
#    C D

#   A   B
#    C D

#    A B
#   C D

#    A B
#   C   D

#       A B
#   C D

# if overlap: return the joined segment
# if no overlap: return None
def join(existing_segment, new_segment, y):
    [a,b] = existing_segment
    [c,d] = new_segment
    if b < c:
        return None
    if a <= c <= b <= d:
        return [a, d]
    if a <= c <= d <= b:
        return [a,b]
    if c <= a <= d <= b:
        return [c,b]
    if c <= a <= b <= d:
        return [c,d]
    if c <= d < a <= b:
        return None
    else:
        print("unexpected")


def add_to_segments(segments, sensor: SensorBeaconPair, y: int):
    sensor_segment = sensor.get_segment(y)
    if sensor_segment is None:
        return segments

    new_segments = []
    for s in segments:
        r = join(s, sensor_segment, y)
        if r is None:
            new_segments += [s]
        else:
            sensor_segment = r
    new_segments += [sensor_segment]
    return new_segments



sensors = []
for line in lines:
    l, r = line.strip().split(":")
    xl, yl = l.split(',')
    xr, yr = r.split(',')
    sbp = SensorBeaconPair(int(xl.split('=')[1]),int(yl.split('=')[1]),int(xr.split('=')[1]),int(yr.split('=')[1]))
    sensors += [sbp]


def get_segments_at(y, sensors):
    segments = []
    for sensor in sensors:
        segments = add_to_segments(segments, sensor, y)
    return segments

def get_beacons_at(y, sensors):
    beacons = set()
    for sensor in sensors:
        if sensor.yb == y:
            beacons.add(str([sensor.xb,sensor.yb]))
    return beacons

y=2000000
s = get_segments_at(2000000, sensors)

size = 0
for [a,b] in s:
    size += b - a + 1
size -= len(get_beacons_at(y, sensors))
print(size)


for y in range(0,4000000):
    s = get_segments_at(y, sensors)
    if y % 10000 == 0:
        print(y)
    if len(s) > 1:
        print("found!")
        print(str(s[0][1]+1) + ':' + str(y))
        print(str((s[0][1]+1)*4000000 + y))
        print(s)

