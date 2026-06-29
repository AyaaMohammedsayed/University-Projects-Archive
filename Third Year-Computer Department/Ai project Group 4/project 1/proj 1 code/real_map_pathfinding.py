# read readme txt to install required libraries.
import osmnx as ox
import random
import heapq
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from collections import deque
from time import perf_counter_ns

# إArgentina capital 
# capital_federal_coords = (-34.6118, -58.4173)  
qalyub_coords = (30.20818, 31.24073)  # (latitude, longitude)
G = ox.graph_from_point(qalyub_coords, dist=1500, network_type='drive')
G = ox.project_graph(G) 
pos = {node: (data["x"], data["y"]) for node, data in G.nodes(data=True)}
# drawing the initial map before coloring paths and tracing paths
def draw_base_graph(ax):
    edge_colors=[G.edges[edge].get('color',"#d36206") for edge in G.edges]
    edge_alphas = [G.edges[edge].get("alpha", 0.2) for edge in G.edges]
    edge_widths = [G.edges[edge].get("linewidth", 0.5) for edge in G.edges]
    ox.plot_graph(
        G,
        ax=ax,
        node_size=5,
        node_color="white",
        edge_color=edge_colors,
        edge_alpha=edge_alphas,
        edge_linewidth=edge_widths,
        show=False,
        close=False
    )

# assign weight and max_speed for each edge of graph_edges where weight is edge avg time 
for edge in G.edges:
    maxspeed = 40
    globalmaxspeed=0
    if "maxspeed" in G.edges[edge]:
        maxspeed = G.edges[edge]["maxspeed"]
        if type(maxspeed) == list:
            speeds = [int(speed) for speed in maxspeed if speed.isdigit()]
            maxspeed = min(speeds) if speeds else 40
        elif type(maxspeed) == str and maxspeed.isdigit():
            maxspeed = int(maxspeed)
    G.edges[edge]["maxspeed"] = maxspeed
    globalmaxspeed=max(maxspeed,globalmaxspeed)
    G.edges[edge]["weight"] = (G.edges[edge]["length"] /(1000 * maxspeed)) * 3600
# draw start and end points (start: lime, end:red)
def draw_start_end_nodes(start, end,ax):
    x_start,y_start=pos[start]
    x_end, y_end = pos[end]
    ax.scatter(x_start, y_start, s=100, c='lime', label='Start', zorder=5, edgecolors='black')
    ax.scatter(x_end, y_end, s=100, c='red', label='End', zorder=5, edgecolors='black')
    ax.legend(loc='upper right')

# drawing the visited edges.
def draw_edge(ax,u, v, color='yellow', alpha=1, width=1):
    x1, y1 = pos[u]
    x2, y2 = pos[v]
    ax.plot([x1, x2], [y1, y2], color=color, alpha=alpha, linewidth=width)

# find the euclidean distance heuristic 
def distance(node1, node2):
    x1, y1 = pos[node1]
    x2, y2 = pos[node2]
    return ((x2 - x1)**2 + (y2 - y1)**2)**0.5

# find the Time heuristic 
def Euclidean_Time(node1,node2):
    return (distance(node1,node2)/(globalmaxspeed * 1000 )) * 3600 

# clear all the prev values for each node, g(n) f(n) 
def reset_node_values ():
      for node in G.nodes:
        G.nodes[node]["previous"] = None
        G.nodes[node]["g_score"] = float("inf")
        G.nodes[node]["f_score"] = float("inf")

# draw the explored tree in .dot format to open it read the readme.txt
def export_to_dot(tree, filename, start=None, end=None, path=None):
    with open(filename, 'w') as f:
        f.write('digraph ExplorationTree {\n')
        f.write('graph [rankdir="TB"];\n')
        f.write('node [shape=circle, style=filled];\n')
        f.write('edge [fontsize=20];\n') 
        
        # Highlight start and end nodes
        if start:
            f.write(f'{start} [fillcolor="limegreen"];\n')
        if end:
            f.write(f'{end} [fillcolor="red"];\n')
        
        # Add path edges
        if path:
            for u, v in path:
                length = G.edges[(u, v, 0)]["length"]
                weight = G.edges[(u, v, 0)]["weight"]
                f.write(f'{u} -> {v} [color="red", penwidth=2.0, label="L:{length:.1f}m\\nT:{weight:.1f}s"];\n')
        
        # Add exploration edges
        for parent, children in tree.items():
            for child in children:
                if path and (parent, child) in path:
                    continue  # Skip if already added as path
                    
                try:
                    length = G.edges[(parent, child, 0)]["length"]
                    weight = G.edges[(parent, child, 0)]["weight"]
                    f.write(f'{parent} -> {child} [color="blue", label="L:{length:.1f}m\\nT:{weight:.1f}s"];\n')
                except KeyError:
                    # Handle cases where edge might not exist
                    f.write(f'{parent} -> {child} [color="blue"];\n')
        
        f.write('}\n')

# creating the exploration tree that will be used in export_to_dot function
def create_exploration_tree(visited_edges):
    tree = {}
    for u, v in visited_edges:
        if u not in tree:
            tree[u] = []
        if v not in tree[u]:
            tree[u].append(v)
    return tree

# informed search algorithms  (A* and greedy search) with different heuristic (euclidean and time)
def a_star_with_steps(orig, dest,heuristic):
    explored_edge = []
    visited_nodes = set() 
    visited_edges = []
    exploration_tree = {}

    G.nodes[orig]["g_score"] = 0
    if heuristic== 'distance':
     G.nodes[orig]["f_score"] = distance(orig, dest)
    else :
     G.nodes[orig]["f_score"] = Euclidean_Time(orig, dest)

    pq = [(G.nodes[orig]["f_score"], orig)]
    exploration_tree[orig] = []
    while pq:
        _, node = heapq.heappop(pq)
        if node in visited_nodes: 
            continue
        if node == dest:
            visited_edges.append((G.nodes[node]['previous'],node))
            visited_nodes.add(node)
            break
        visited_nodes.add(node)
        if(node != orig):
            visited_edges.append((G.nodes[node]['previous'],node))
        for edge in G.out_edges(node):
            updated = edge + (0,)
            neighbor = edge[1]
            if heuristic=='distance':
             actual_g_score = G.nodes[node]["g_score"] + G.edges[updated]["length"] 
            else :
             actual_g_score = G.nodes[node]["g_score"] + G.edges[updated]["weight"]
            if actual_g_score < G.nodes[neighbor]["g_score"]:
                G.nodes[neighbor]["previous"] = node
                G.nodes[neighbor]["g_score"] = actual_g_score
                if heuristic=='distance':
                 G.nodes[neighbor]["f_score"] = actual_g_score + distance(neighbor, dest)
                else :
                 G.nodes[neighbor]["f_score"] = actual_g_score + Euclidean_Time(neighbor, dest)
                heapq.heappush(pq, (G.nodes[neighbor]["f_score"], neighbor))
                explored_edge.append(edge)
                
                if node not in exploration_tree:
                    exploration_tree[node] = []
                if neighbor not in exploration_tree[node]:
                    exploration_tree[node].append(neighbor)
    return explored_edge , visited_edges , visited_nodes ,exploration_tree

def Greedy_steps(orig, dest, heuristic):
    explored_edges = []
    visited_edges = []
    visited_nodes = set()
    exploration_tree = {}

    if heuristic == 'distance':
        G.nodes[orig]["f_score"] = distance(orig, dest)
    else:
        G.nodes[orig]["f_score"] = Euclidean_Time(orig, dest)
    pq = [(G.nodes[orig]["f_score"], orig)]
    exploration_tree[orig] = []
    
    while pq:
        _, node = heapq.heappop(pq)
        if node in visited_nodes:
            continue
        if node == dest:
            visited_edges.append((G.nodes[node]['previous'], node))
            visited_nodes.add(node)
            break      
        visited_nodes.add(node)
        if node != orig:
            visited_edges.append((G.nodes[node]['previous'], node))
            
        for edge in G.out_edges(node):
            neighbor = edge[1]
            if neighbor not in visited_nodes:
                G.nodes[neighbor]["previous"] = node
                if heuristic == "distance":
                    G.nodes[neighbor]["f_score"] = distance(neighbor, dest)
                else:
                    G.nodes[neighbor]["f_score"] = Euclidean_Time(neighbor, dest)
                heapq.heappush(pq, (G.nodes[neighbor]["f_score"], neighbor))
                explored_edges.append(edge)
                if node not in exploration_tree:
                    exploration_tree[node] = []
                if neighbor not in exploration_tree[node]:
                    exploration_tree[node].append(neighbor)

    return explored_edges, visited_edges, visited_nodes,exploration_tree

def informed_reconstruct_path(orig, dest):
    path = []
    curr = dest
    while curr != orig:
        prev = G.nodes[curr]["previous"]
        path.append((prev, curr))
        curr = prev
    path.reverse()
    return path

# uninformed searches bfs and bidirectional bfs 
def bidirectional_bfs(start, end):
    queue_start = deque([start])
    queue_end = deque([end])

    visited_start = set()
    visited_end = set()

    prev_start = {start: None}
    prev_end = {end: None}

    meeting_node = None
    visited_edges = []
    explored_edges = []
    exploration_tree_start = {start: []}
    exploration_tree_end = {end: []}
    while queue_start and queue_end:
        # BFS from start
        current = queue_start.popleft()
        if(current in visited_start):
            continue
        visited_start.add(current)
        if(current != start):
            visited_edges.append((prev_start[current],current))
        # checking if it is in the visited_end then we will found the goal 
        if current in visited_end:
                meeting_node = current
                break
        # exploring neighbors
        for edge in G.out_edges(current):
            neighbor = edge[1]
            if neighbor in queue_start or neighbor in visited_start:
                continue
            prev_start[neighbor] = current
            queue_start.append(neighbor)
            explored_edges.append((current, neighbor))
            
            if current not in exploration_tree_start:
                exploration_tree_start[current] = []
            if neighbor not in exploration_tree_start[current]:
                exploration_tree_start[current].append(neighbor)
        # BFS from end
        current = queue_end.popleft()
        if(current in visited_end):
            continue 
        visited_end.add(current)
        if(current != end):
            visited_edges.append((prev_end[current],current))
        if current in visited_start:
            meeting_node = current 
            break
        for edge in G.in_edges(current):
            neighbor = edge[0]
            if neighbor in queue_end or neighbor in visited_end:
                continue
            prev_end[neighbor] = current
            queue_end.append(neighbor)
            explored_edges.append((current, neighbor))
            if current not in exploration_tree_end:
                exploration_tree_end[current] = []
            if neighbor not in exploration_tree_end[current]:
                exploration_tree_end[current].append(neighbor)
    return visited_edges , explored_edges , meeting_node , prev_start,prev_end , exploration_tree_start, exploration_tree_end
    
def bidirectional_reconstruct_path(start,end,meeting_node,prev_start,prev_end): 
      path = []
    # from meeting_node → start
      node = meeting_node
      while node != start :
         prev = prev_start[node]
         path.append((prev, node))
         node = prev
      path.reverse()
    # from meeting_node → end
      node = meeting_node
      while node != end :
         prev = prev_end[node]
         path.append((node, prev))
         node = prev
  
      return path

# bfs implementation 
def bfs(start, end):
    queue = deque([start])
    visited_nodes = set()
    prev_start = {start: None}  # needed for reconstructing the path
    visited_edges = []
    explored_edges = []
    exploration_tree = {}
    while queue:
        current = queue.popleft()
        if current in visited_nodes:
            continue
        visited_nodes.add(current)
        if current != start:
            visited_edges.append((prev_start[current], current))
        if current == end:
            break
        for edge in G.out_edges(current):
            neighbor = edge[1]
            if neighbor in visited_nodes or neighbor in queue:
                continue
            prev_start[neighbor] = current
            queue.append(neighbor)
            explored_edges.append((current, neighbor))
            if current not in exploration_tree:
                exploration_tree[current] = []
            if neighbor not in exploration_tree[current]:
                exploration_tree[current].append(neighbor)
    return explored_edges, visited_edges, prev_start, visited_nodes,exploration_tree

# reconstruct path of bfs 
def bfs_reconstruct_path(prev_start, end):
    path = [] 
    node = end
    while node != start :
         prev = prev_start[node]
         path.append((prev, node))
         node = prev
    path.reverse()
    return path

# run algorithms

def run_algorithm(algorithm,heuristic= None):
    if(algorithm == 'bfs'):
        start_time = perf_counter_ns()
        explored_edges , visited_edges , prev_start, visited_nodes,exploration_tree= bfs(start, end)
        elapsed_time = perf_counter_ns() - start_time 
        path_edges = bfs_reconstruct_path(prev_start,end)
        visited_nodes_length = len(visited_nodes)
        export_to_dot(exploration_tree, "bfs_exploration.dot", start, end, path_edges)
    elif(algorithm == 'bidirectional_bfs'):
        start_time = perf_counter_ns()
        visited_edges, explored_edges , meeting_node , prev_start, prev_end ,tree_start, tree_end= bidirectional_bfs(start, end)
        elapsed_time = perf_counter_ns() - start_time
        path_edges=bidirectional_reconstruct_path(start,end,meeting_node,prev_start,prev_end) 
        visited_nodes_length = len({u for u, _ in visited_edges} | {v for _, v in visited_edges})
        export_to_dot(tree_start, "bidirectional_start.dot", start, meeting_node)
        export_to_dot(tree_end, "bidirectional_end.dot", end, meeting_node)   
    elif(algorithm =="A*"):
        reset_node_values()
        start_time = perf_counter_ns()
        explored_edges , visited_edges , visited_nodes ,exploration_tree= a_star_with_steps(start, end, heuristic)
        elapsed_time = perf_counter_ns() - start_time 
        visited_nodes_length = len(visited_nodes)
        path_edges = informed_reconstruct_path(start, end)
        export_to_dot(exploration_tree, f"a_star_{heuristic}_exploration.dot", start, end, path_edges)
    else:
        reset_node_values()
        start_time = perf_counter_ns()
        explored_edges, visited_edges, visited_nodes,exploration_tree = Greedy_steps(start, end, heuristic)
        elapsed_time = perf_counter_ns() - start_time 
        visited_nodes_length = len(visited_nodes)
        path_edges = informed_reconstruct_path(start, end)
        export_to_dot(exploration_tree, f"greedy_{heuristic}_exploration.dot", start, end, path_edges)
    # setting the figure 10*10 with dark theme and collecting the longitude and latitude of each node and then we will print them on map 
    fig, ax = plt.subplots(figsize=(10, 10))
    fig.patch.set_facecolor('#18080e')
    ax.set_facecolor('#18080e') 
    draw_base_graph(ax)
    draw_start_end_nodes(start, end,ax)
    
    # Add text annotations
    ax.text(0, 1, 
            f"{algorithm} Algorithm\n"
            f"Elapsed time: {elapsed_time/1000000:.5f}ms\n"
            f"No of Path Edges: {len(path_edges)}\n"
            f"Heuristic: {heuristic}\n"
            f"Path length: {sum(G.edges[(u, v, 0)]['length'] for (u, v) in path_edges) if path_edges else 0:.1f}m\n"
            f"Path Time: {sum(G.edges[(u, v, 0)]['weight'] for (u, v) in path_edges) if path_edges else 0:.1f}s\n"
            f"Nodes visited: {len({edge[0] for edge in explored_edges} | {edge[1] for edge in explored_edges})}\n"
            f"Nodes explored: {visited_nodes_length}\n", #-destination
            transform=ax.transAxes,
            color='white',
            fontsize=10,
            verticalalignment='top',
            bbox=dict(facecolor='black', alpha=0.5, edgecolor='white'))

    def animate(i):
        if i < len(visited_edges):
            edge = visited_edges[i]
            draw_edge(ax,edge[0], edge[1], color="#e8a900", alpha=1, width=1)
        elif i < len(visited_edges) + len(path_edges):
            edge = path_edges[i - len(visited_edges)]
            draw_edge(ax,edge[0], edge[1], color="red", alpha=0.9, width=1)
        return ax,

    total_frames = len(visited_edges) + len(path_edges)
    anim = FuncAnimation(fig, animate, frames=total_frames, interval=50, repeat=False)
    return anim

# Pick random nodes (any one from both)
# start = random.choice(list(G.nodes))
# end = random.choice(list(G.nodes))
start = list(G.nodes)[0]
end = list(G.nodes)[100]
print(f"Finding path from node {start} to {end}")

# Run both algorithms
# run and compare between different algorithms (A*,greedy), 
# (A* with euclidean distance heuristic,A* with Euclidean time )->to have correct results check it on capital_federal_coords not on qalyob
# (A* with euclidean distance heuristic vs greedy distance heuristic) 
# (bi-bfs and bfs)
anim_a_star = run_algorithm("A*",'distance')
# anim_a_time_star = run_algorithm("A*",'Euclidean_Time')
anim_greedy_distance = run_algorithm("greedy","distance")
# anim_greedy_time = run_algorithm("greedy","Euclidean_Time")
# anim_bi_bfs = run_algorithm("bidirectional_bfs")
# anim_bfs = run_algorithm("bfs"); 

# Show both figures
plt.show()
