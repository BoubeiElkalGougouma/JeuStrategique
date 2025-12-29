package model;

public class GameMap {
    private final int width, height;
    private final Cell[][] cells;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        generateMap();
    }

    private void generateMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TerrainType type = Math.random() < 0.7 ? TerrainType.GRASS :
                        Math.random() < 0.5 ? TerrainType.FOREST : TerrainType.MOUNTAIN;
                cells[x][y] = new Cell(x, y, type);
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[x][y];
        }
        return null;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

