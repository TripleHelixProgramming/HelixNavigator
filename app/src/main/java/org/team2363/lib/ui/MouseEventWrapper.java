package org.team2363.lib.ui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseEventWrapper {

    private final EventHandler<MouseEvent> onMousePressed;
    private final EventHandler<MouseEvent> onMouseDragBegin;
    private final EventHandler<MouseEvent> onMouseDragged;
    private final EventHandler<MouseEvent> onMouseDragEnd;
    private final EventHandler<MouseEvent> onMouseReleased;

    private final EventHandler<MouseEvent> wrappedOnMousePressed;
    private final EventHandler<MouseEvent> wrappedOnMouseDragged;
    private final EventHandler<MouseEvent> wrappedOnMouseReleased;

    private boolean inDrag = false;

    public MouseEventWrapper(
            EventHandler<MouseEvent> onMousePressed,
            EventHandler<MouseEvent> onMouseDragBegin,
            EventHandler<MouseEvent> onMouseDragged,
            EventHandler<MouseEvent> onMouseDragEnd,
            EventHandler<MouseEvent> onMouseReleased) {
        this.onMousePressed = onMousePressed;
        this.onMouseDragBegin = onMouseDragBegin;
        this.onMouseDragged = onMouseDragged;
        this.onMouseDragEnd = onMouseDragEnd;
        this.onMouseReleased = onMouseReleased;

        wrappedOnMousePressed = (event) -> {
            inDrag = false; // this is probably redundant, but it could help prevent bugs
            this.onMousePressed.handle(event);
        };
        wrappedOnMouseDragged = (event) -> {
            if (!inDrag) {
                this.onMouseDragBegin.handle(event);
                inDrag = true;
            }
            this.onMouseDragged.handle(event);
        };
        wrappedOnMouseReleased = (event) -> {
            if (inDrag) {
                this.onMouseDragEnd.handle(event);
                inDrag = false;
            } else {
                this.onMouseReleased.handle(event);
            }
        };
    }

    public EventHandler<MouseEvent> getOnMousePressed() {
        return wrappedOnMousePressed;
    }

    public EventHandler<MouseEvent> getOnMouseDragged() {
        return wrappedOnMouseDragged;
    }

    public EventHandler<MouseEvent> getOnMouseReleased() {
        return wrappedOnMouseReleased;
    }
}
