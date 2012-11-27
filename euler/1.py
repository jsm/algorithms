max = 1000

def sum_of(n, to):
    return (int(to/n)+1)*(int(to/n)*n/2.0)

print sum_of(3,max-1) + sum_of(5,max-1) - sum_of(15,max-1)
