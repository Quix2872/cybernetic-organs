package com.cybernetic;

import java.time.LocalDateTime;

public class SystemOperationsLog {
    public static class SystemOperation {
        private String operationId;
        private String operationType;
        private LocalDateTime timestamp;
        private String description;
        private boolean isReversible;

        public SystemOperation(String operationId, String operationType, LocalDateTime timestamp, String description, boolean isReversible) {
            this.operationId = operationId;
            this.operationType = operationType;
            this.timestamp = timestamp;
            this.description = description;
            this.isReversible = isReversible;
        }

        public String getOperationId() {
            return operationId;
        }

        public String getOperationType() {
            return operationType;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getDescription() {
            return description;
        }

        public boolean isReversible() {
            return isReversible;
        }
    }

    private SystemOperation[] stack;
    private int top;

    public SystemOperationsLog(int size) {
        stack = new SystemOperation[size];
        top = -1;
    }

    public void pushOperation(String operationId, String operationType, LocalDateTime timestamp, String description, boolean isReversible) {
        if (top == stack.length - 1) {
            throw new StackOverflowError("Operation log is full!");
        }
        stack[++top] = new SystemOperation(operationId, operationType, timestamp, description, isReversible);
    }

    public SystemOperation popLastOperation() {
        if (top == -1) {
            throw new IllegalStateException("No operations to undo!");
        }
        return stack[top--];
    }

    public SystemOperation peekLastOperation() {
        if (top == -1) {
            throw new IllegalStateException("No operations available!");
        }
        return stack[top];
    }

    public void displayRecentOperations(int count) {
        for (int i = top; i >= Math.max(0, top - count + 1); i--) {
            SystemOperation op = stack[i];
            System.out.printf("Operation ID: %s, Type: %s, Description: %s%n",
                    op.getOperationId(), op.getOperationType(), op.getDescription());
        }
    }
}
