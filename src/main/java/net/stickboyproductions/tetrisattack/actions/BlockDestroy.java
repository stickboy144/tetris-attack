package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Score;

import static net.stickboyproductions.tetrisattack.constants.SpeedConstants.*;

/**
 * User: Pete
 * Date: 29/10/12
 * Time: 21:35
 */
public class BlockDestroy implements TimeTickingAction {

  private Block block;
  private int numberInCombo;
  private Score score;

  private int nextFlash = BLOCK_DESTROY_FLASH_MS;

  public BlockDestroy(Block block, int numberInCombo, Score score) {
    this.block = block;
    this.numberInCombo = numberInCombo;
    this.score = score;
  }

  @Override
  public void tick(long timeElapsed) {
    // Flash for the first second
    if (timeElapsed <= BLOCK_DESTROY_FLASH_PHASE_MS) {
      if (timeElapsed >= nextFlash) {
        if (block.getFrame() == 0.0) {
          block.setFrame(0.25);
        } else {
          block.setFrame(0.0);
        }
        nextFlash += BLOCK_DESTROY_FLASH_MS;
      }
    } else if (timeElapsed <= BLOCK_DESTROY_FLASH_PHASE_MS + (BLOCK_DESTROY_CELL_MS * numberInCombo)) {
      block.setFrame(5.0);
    } else if (block.getBlockState().equals(BlockState.DESTROYING_END)) {
      // Do nothing
    } else {
      score.addToScore(10);
      block.setBlockState(BlockState.DESTROYING_END);
    }
  }

  @Override
  public boolean isFinished() {
    return BlockState.DESTROYING_END.equals(block.getBlockState());
  }

  @Override
  public void start() {
    block.setBlockState(BlockState.DESTROYING);
  }

  @Override
  public void end() {
    block.setFrame(0.0);
    block.setBlockState(BlockState.EMPTY);
  }
}
