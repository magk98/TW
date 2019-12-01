import threading
import random
import time

filosophers_num = 5
iters = 10


class Philosopher(threading.Thread):

    def __init__(self, number, left_fork, right_fork, iterations):
        threading.Thread.__init__(self)
        self.number = number
        self.leftFork = left_fork
        self.iterations = iterations
        self.rightFork = right_fork
        self.resultTime = 0

    def run(self):
        while self.iterations > 0:
            time.sleep(random.uniform(0, 1))
            print('Philosopher %d is hungry' % self.number)
            self.getForksAndEat()
            self.iterations -= 1

    def getForksAndEat(self):
        start_time = time.time()
        while True:
            self.leftFork.acquire(True)
            locked = self.rightFork.acquire(True)
            if locked: break
        elapsed_time = time.time() - start_time
        self.resultTime += elapsed_time
        self.eat()
        self.leftFork.release()
        self.rightFork.release()

    def eat(self):
        print('%d starts eating' % self.number)
        time.sleep(random.uniform(0, 1))
        print('%d finishes eating and leaves to think' % self.number)


if __name__ == '__main__':
    forks = [threading.Lock() for i in range(filosophers_num)]

    philosophers = [Philosopher(i, forks[i % 5], forks[(i + 1) % 5], iters) for i in range(5)]
    random.seed(275538)
    for p in philosophers: p.start()
    result_array = []
    for p in philosophers:
        p.join()
        result_array.append(p.resultTime / iters)
    print("Now we're finishing.")
    res_sum = 0
    for r in result_array:
        res_sum += r
    res_sum /= len(result_array)
    print(res_sum)
