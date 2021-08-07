package io.github.seanlego23.customcompetitions.command;

import io.github.seanlego23.customcompetitions.commands.CCCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTree {

    private static final class CommandNode {

        private CCCommand command;
        private List<CommandNode> children;

        public CommandNode(CCCommand command) {
            this.command = command;
        }

        public CCCommand getCommand() {
            return command;
        }

        public String getCommandName() {
            return command.getName();
        }

        /**
         * Adds a child command if it doesn't exist.
         *
         * @param command the command to add.
         * @return the new child node, or the existing child node.
         */
        public CommandNode addChildCommand(CCCommand command) {
            if (children == null)
                children = new ArrayList<>();
            int min = 0;
            int max = children.size();
            while (min < max) {
                int half = min + (max - min) / 2;
                int comp = children.get(half).command.getName().compareToIgnoreCase(command.getName());
                if (comp > 0)
                    max = half;
                else if (comp < 0)
                    min = half;
                else
                    return children.get(half);
            }
            CommandNode newChild = new CommandNode(command);
            children.add(min, newChild);
            return newChild;
        }

        /**
         * Removes a child command.
         *
         * @param command the command to add.
         */
        public void removeChildCommand(CCCommand command) {
            for (int i = 0; i < children.size(); i++) {
                if (children.get(0).command.equals(command)) {
                    children.remove(i);
                    break;
                }
            }
        }

    }

    private final CommandNode root;

    public CommandTree(CCCommand rootCommand) {
        root = new CommandNode(rootCommand);
    }

    public boolean addCommand(CCCommand command, String path) {
        String[] splitPath = path.split("\\.");
        if (splitPath.length == 0 || !splitPath[0].equalsIgnoreCase(root.getCommandName()))
            return false;
        CommandNode current = root;
        for (int i = 1; i < splitPath.length; i++) {
            CommandNode temp = current;
            for (int j = 0; j < current.children.size(); j++) {
                if (current.children.get(j).command.getName().equalsIgnoreCase(splitPath[i])) {
                    current = current.children.get(j);
                    break;
                }
            }
            if (current == temp) {
                CommandInfo info = new CommandInfo("/" + String.join(" ",
                        Arrays.copyOfRange(splitPath, 0, i + 1)), "", null,
                        current.command.getCommandInfo().getPermission());
                current = current.addChildCommand(new CCCommand(splitPath[i], info, null, null));
            }
        }
        current.command = command;
        return true;
    }

    public boolean removeCommand(CCCommand command, String path) {
        String[] splitPath = path.split("\\.");
        if (splitPath.length == 0 || !splitPath[0].equalsIgnoreCase(root.getCommandName()))
            return false;
        CommandNode current = root;
        CommandNode parent = root;
        for (int i = 1; i < splitPath.length; i++) {
            CommandNode temp = current;
            for (int j = 0; j < current.children.size(); j++) {
                if (current.children.get(j).command.getName().equalsIgnoreCase(splitPath[i])) {
                    parent = current;
                    current = current.children.get(j);
                    break;
                }
            }
            if (current == temp)
                return false;
        }
        parent.removeChildCommand(command);
        return true;
    }

}
