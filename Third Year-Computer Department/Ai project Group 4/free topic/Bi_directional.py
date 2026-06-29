from collections import deque
graph = {
    0: [4],
    1: [4],
    2: [5],
    3: [5],
    4: [0, 1, 6],
    5: [2, 3, 6],
    6: [4, 5, 7],
    7: [6, 8],
    8: [7, 9, 10],
    9: [8, 11, 12],
    10: [8, 13, 14],
    11: [9],
    12: [9],
    13: [10],
    14: [10]
}
def bidirectional_search(graph, start, goal):
    if start ==goal:
        return [start]
    visited_from_start={start:None}
    visited_from_goal={goal:None}

    queue_from_start=deque([start])
    queue_from_goal=deque([goal])

    while queue_from_start and queue_from_goal:
        node_f_start=queue_from_start.popleft()
        for n in graph[node_f_start]:
            if n not in visited_from_start:
                visited_from_start[n]=node_f_start
                queue_from_start.append(n)
            if n  in visited_from_goal:
                return reconstruct_path(visited_from_start, visited_from_goal, start, goal, n)
        node_f_goal=queue_from_goal.popleft()
        for n in graph[node_f_goal]:
            if n not in visited_from_goal:
                visited_from_goal[n]=node_f_goal
                queue_from_goal.append(n)
            if n  in visited_from_start:
                return reconstruct_path(visited_from_start, visited_from_goal, start, goal, n)
    return None
def reconstruct_path(visited_from_start, visited_from_goal, start, goal, n):
    node =n
    path=[]
    while  node is not None:
        path.append(node)
        node=visited_from_start[node]
    path.reverse()
    path_goal=[]
    node=visited_from_goal[n]
    while node is not None:
        path_goal.append(node)
        node=visited_from_goal[node]
    return path +path_goal
start_node = 0
goal_node = 14
path = bidirectional_search(graph, start_node, goal_node)
print("The Shortest Path Between ", start_node, " & ", goal_node, "IS:", path)
