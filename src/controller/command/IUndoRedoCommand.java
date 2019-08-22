package controller.command;

public interface IUndoRedoCommand {
    void undo();
    void redo();
}
