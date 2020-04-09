package edu.stanford.cs108.tetris;

//import java.awt.*;
import java.util.Random;

/**
 * Created by pyoung on 9/5/2016.
 */
public class TetrisBrainLogic extends TetrisLogic{
    protected Brain brain;
    protected Brain.Move bestMove;
    protected boolean brainMode = false;

    public TetrisBrainLogic(TetrisUIInterface uiInterface) {
        super(uiInterface);
        brain = new DefaultBrain();
        bestMove = null;
        /// TODO: Skipped Event Handlers

        /// TODO: Skipped Setting Up Timer
    }

    public void setBrainMode(boolean brainCheck) {
        brainMode = brainCheck;
    }
    /**
     Called to change the position of the current piece.
     Each key press calls this once with the verbs
     LEFT RIGHT ROTATE DROP for the user moves,
     and the timer calls it with the verb DOWN to move
     the piece down one square.

     Before this is called, the piece is at some location in the board.
     This advances the piece to be at its next location.

     Overriden by the brain when it plays.
     */

    @Override
    protected void tick(int verb) {
        if(brainMode && verb == DOWN) {
            board.undo();
            bestMove = brain.bestMove(board, currentPiece, HEIGHT, bestMove);

            if(!currentPiece.equals(bestMove.piece)) {
                super.tick(ROTATE);
            }
            if(currentX > bestMove.x) {
                super.tick(LEFT);
            } else if(currentX < bestMove.x) {
                super.tick(RIGHT);
            }
        }
        super.tick(verb);
    }
}
