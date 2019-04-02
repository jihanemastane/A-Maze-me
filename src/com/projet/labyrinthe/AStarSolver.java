package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;

public class AStarSolver extends MinPathFinding{

    public AStarSolver(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe) {
        super(width, height, i, j, ia, ja, grid, tilegroupe);
        solve();
    }
    public void solve() {
    	if(Controller.animationSpeed==0) {
            while (!openList.isEmpty()) {
                posMin = getLowestScorePos(scors);
                current = openList.get(posMin);
                if (current.getRow() == ia && current.getCol() == ja) {
                    pathFound = true;
                    break;
                }
                OneIteration();
            }
            if (pathFound) {
                markThePath();
                drawPath();
            }
        }else if(Controller.animationSpeed==2)
        {
            pathFound = false;
            at = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    posMin = getLowestScorePos(scors);
                    current = openList.get(posMin);
                    if (current.getRow() == ia && current.getCol() == ja) {
                        markThePath();
                        drawPath();
                        this.stop();
                    }
                    OneIteration();
                }
            };
            at.start();
        }
    }
    public int calculateScor(int starti , int startj , int endi , int endj) {
        return Math.abs(endi-starti) + Math.abs(endj-startj);
    }

	@Override
	public void testNeighbors(int indexCurr, int indexNeighbor , int wall) {
		Cell neighbor = (indexNeighbor != -1) ? grid.get(indexNeighbor) : null;
		if (neighbor != null && !current.getPosWalls(wall) && !closedList.contains(neighbor)) {
            int scor = calculateScor(neighbor.getRow(), neighbor.getCol(), ia, ja) + scors.get(posMin) + 1;
            if (!openList.contains(neighbor)) {
                parents.add(indexCurr);
                childrens.add(indexNeighbor);
                openList.add(neighbor);
                scors.add(scor);
            } else if (scor < scors.get(openList.indexOf(neighbor))) {
                parents.set(parents.indexOf(indexNeighbor), indexCurr);
                scors.set(openList.indexOf(neighbor), scor);
            }
        }	
	}
}
