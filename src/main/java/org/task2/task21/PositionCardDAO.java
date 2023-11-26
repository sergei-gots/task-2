package org.task2.task21;

import java.util.Optional;

public interface PositionCardDAO {
    /**
     * @param groupId the query parameter
     * @return the name (positionCard.name) of the card product by its group.
     * If the group does not have a product card specified,
     * then to this product the  card of its parent group (etc.) is applied.
     */
    Optional<String> getPositionNameByGroupId(String groupId);
}
