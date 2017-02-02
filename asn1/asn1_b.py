#	Recursive Fibonacci function 
def recursiveFib(num):
    return fibHelper(0,1,num)

# Fibonacci helper method
def fibHelper(firstNum, secondNum, num):
    if (num == 0):
        return firstNum
    return fibHelper(secondNum, firstNum+secondNum, num - 1)

if __name__ == "__main__":
	# Handles 7b)
	for i in range(0,31):
		print(recursiveFib(i*10))
    print(recursiveFib(300))
