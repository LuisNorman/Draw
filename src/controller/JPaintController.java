package controller;

import controller.command.*;
import model.interfaces.IApplicationState;
import model.persistence.SelectedShapes;
import view.EventName;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

public class JPaintController implements IJPaintController {
    private final IUiModule uiModule;
    private final IApplicationState applicationState;
    private final PaintCanvasBase paintCanvas;
    private ICommand command;

    public JPaintController(IUiModule uiModule, IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        this.uiModule = uiModule;
        this.applicationState = applicationState;
        this.paintCanvas = paintCanvas;

    }

    @Override
    public void setup() {
        setupEvents();
    }

    private void setupEvents() {

        uiModule.addEvent(EventName.CHOOSE_SHAPE, () -> applicationState.setActiveShape());
        uiModule.addEvent(EventName.CHOOSE_PRIMARY_COLOR, () -> applicationState.setActivePrimaryColor());
        uiModule.addEvent(EventName.CHOOSE_SECONDARY_COLOR, () -> applicationState.setActiveSecondaryColor());
        uiModule.addEvent(EventName.CHOOSE_SHADING_TYPE, () -> applicationState.setActiveShadingType());
        uiModule.addEvent(EventName.CHOOSE_START_POINT_ENDPOINT_MODE, () -> applicationState.setActiveStartAndEndPointMode());
        uiModule.addEvent(EventName.DELETE, () -> new DeleteCommand(paintCanvas, SelectedShapes.getAll()).execute());
        uiModule.addEvent(EventName.COPY, () -> new CopyCommand().execute());
        uiModule.addEvent(EventName.PASTE, () -> new PasteCommand(paintCanvas, CopyCommand.getCopiedShapes()).execute());
        uiModule.addEvent(EventName.OUTLINE, () -> new OutlineCommand(paintCanvas, applicationState));
        uiModule.addEvent(EventName.GROUP, () -> new GroupCommand(SelectedShapes.getAll()).execute());
        uiModule.addEvent(EventName.UNGROUP, () -> new UngroupCommand(SelectedShapes.getAll()).execute());
        uiModule.addEvent(EventName.UNDO, () -> new UndoCommand(paintCanvas).execute());
    }
}
