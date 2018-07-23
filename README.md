
## OS Project Phase Two, Spring 2018
Page replacement algorithms, LRU and FIFO.

## Input Structure
This program gets three lines of input:
- An integer, which contains the number of the pages
- A String, which contains the reference string
- A String, which contains the size of frame and the algorithm we want to use
#### Example One:
```````````
10
0 1 2 0 3 1 4 0 1 4
3 FIFO 
```````````
#### Example Two:
````````````
10
2 3 4 2 1 3 7 5 4 3
3 LRU 
````````````
## Output:
Each line of the output consists of three elements:
- The Number of the page
- Memory State: A sequence of pages in memory separated by space in two brackets
- A "page fault" to see whether we have a page fault or not
- NOTE: The output has a final line whose structure is: "total number of page faults is " + number of page faults

#### Example One Output:
````````````
0 [0] page fault
1 [0 1] page fault
2 [0 1 2] page fault
0 [0 1 2] 
3 [3 1 2] page fault
1 [3 1 2] 
4 [3 4 2] page fault
0 [3 4 0] page fault
1 [1 4 0] page fault
4 [1 4 0]
total number of page faults is 7
````````````
#### Example Two Output:
````````````
2 [2] page fault
3 [2 3] page fault
4 [2 3 4] page fault
2 [2 3 4]
1 [2 1 4] page fault
3 [2 1 3] page fault
7 [7 1 3] page fault
5 [7 5 3] page fault
4 [7 5 4] page fault
3 [3 5 4] page fault
total number of page faults is 9
````````````
