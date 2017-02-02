#	Recursive Fibonacci function 
def recursiveFib(num):
	if (num == 0):
		return 0
	elif (num == 1):
		return 1
	else:
		return recursiveFib(num - 1) + recursiveFib(num - 2)


if __name__ == "__main__":
	# Handles 7a)
    for i in range(0,8):
        print(recursiveFib(i*5))
    
#	print(recursiveFib(50))
