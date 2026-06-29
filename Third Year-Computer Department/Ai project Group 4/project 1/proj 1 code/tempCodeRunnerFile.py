def draw_start_end_nodes(start, end,ax):
    x_start,y_start=pos[start]
    x_end, y_end = pos[end]
    ax.scatter(x_start, y_start, s=100, c='lime', label='Start', zorder=5, edgecolors='black')
    ax.scatter(x_end, y_end, s=100, c='red', label='End', zorder=5, edgecolors='black')
    ax.legend(loc='upper right')
