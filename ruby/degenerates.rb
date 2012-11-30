d = {}
l = [1.0,2.0,4.0]

range = (1..10)

range.each do |i|
    range.each do |j|
        range.each do |k|
            e = ((i/l[0])**2 + (j/l[1])**2 + (k/l[2])**2)
            d[e] ||= 0
            d[e] += 1
        end
    end
end

ground = nil;
max = 10

d.keys.sort.each_with_index do |key,i|
    break if max < i
    ground ||= key
    output = "Excited state " << i.to_s << ": "
    output << (key/ground).to_s
    output << " is degenerate" if d[key] > 1
    puts output
end
