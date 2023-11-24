package org.task.task1;

import java.util.Optional;

public class Task1 {
    public static void task1() {

        PositionCardDAO positionCardDAO;
        try {
            positionCardDAO = new PositionCardJDBC();
        } catch (ClassNotFoundException e) {
            return;
        }

        String groupId = "Hoovers";
        Optional<String> nameOptional = positionCardDAO.getPositionNameByGroupId(groupId);
        printTask1Result(groupId, nameOptional);


        groupId = "Washing Machines";
        nameOptional = positionCardDAO.getPositionNameByGroupId(groupId);
        printTask1Result(groupId, nameOptional);

        groupId = "Garden Equipment";
        nameOptional = positionCardDAO.getPositionNameByGroupId(groupId);
        printTask1Result(groupId, nameOptional);

        groupId = "The group is not listed";
        nameOptional = positionCardDAO.getPositionNameByGroupId(groupId);
        printTask1Result(groupId, nameOptional);

    }

    private static void printTask1Result(String groupId, Optional<String> nameOptional) {
        System.out.print("Task1: for the group_id=\"" + groupId);
        System.out.println(
                nameOptional.map(s -> "\" is a position_card with the name=\"" + s + "\" found.")
                        .orElse("\" there is no a position_card within the group hierarchy.")
        );
    }
}
