import java.util.ArrayList;

public class Cell {

    ArrayList<Cell> near;
    State state;

    public Cell() {
        state = State.NONE;
        near = new ArrayList<>();
    }

    void addNear(Cell cell) {
        near.add(cell);
    }

    void step1(){
        int around = countNearCells();
        state = state.step1(around);
    }

    void step2(){
        state = state.step2();
    }

     int countNearCells(){
        int count = 0;
        for (Cell cell : near) {
            if (cell.state.isAlive()) {
                    count++;
            }
        }
        return count;
    }

    public void turn() {
        state = state.isAlive() ? State.NONE : State.LIVE;
    }
}
