package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.generators.LineGenerator;

import javax.inject.Inject;

import static net.stickboyproductions.tetrisattack.constants.Directions.*;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.ROWS_IN_GRID;

/**
 * The holder of Level for a game.
 * <p/>
 * User: Pete
 * Date: 27/11/12
 * Time: 22:47
 */
public class Grid {

  private Block[][] grid =
    new Block[BLOCKS_IN_ROW_COUNT][ROWS_IN_GRID];
  private LineGenerator lineGenerator;

  @Inject
  public Grid(LineGenerator lineGenerator) {
    this.lineGenerator = lineGenerator;
  }

  public Block get(int x, int y) {
    if (x < BLOCKS_IN_ROW_COUNT && x >= 0 && y >= 0 && y < ROWS_IN_GRID) {
      return grid[x][y];
    }
    return null;
  }

  public void init() {
    for (int y = 0; y < ROWS_IN_GRID; y++) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        Block newBlock = new Block(this, x, y);
        grid[x][y] = newBlock;
      }
    }
  }

  public Block getBlockToTheDirection(Block block, int direction) {
    switch (direction) {
      case LEFT:
        return get(block.getX() - 1, block.getY());
      case RIGHT:
        return get(block.getX() + 1, block.getY());
      case UP:
        return get(block.getX(), block.getY() + 1);
      case DOWN:
        return get(block.getX(), block.getY() - 1);
    }
    return null;
  }

  public void moveAllUp() {
    Block[][] newGrid =
      new Block[BLOCKS_IN_ROW_COUNT][ROWS_IN_GRID];

    Block[] newLine = lineGenerator.generate(this);
    for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
      newGrid[x][0] = newLine[x];
    }

    for (int y = ROWS_IN_GRID - 2; y >= 0; y--) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        newGrid[x][y + 1] = grid[x][y];
        grid[x][y].setY(y + 1);
      }
    }

    grid = newGrid;
  }
}
