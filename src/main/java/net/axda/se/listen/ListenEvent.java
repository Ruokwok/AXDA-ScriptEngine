package net.axda.se.listen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ListenEvent {

    PlayerPreJoin("onPreJoin"),
    PlayerOnJoin("onJoin"),
    PlayerOnLeft("onLeft"),
    PlayerJump("onJump"),
    PlayerRespawn("onRespawn"),
    PlayerDeath("onPlayerDie"),
    PlayerCmd("onPlayerCmd"),
    PlayerChat("onChat"),
    PlayerChangeDim("onChangeDim"),
    PlayerSneak("onSneak"),
    PlayerAttackEntity("onAttackEntity"),
    PlayerAttackBlock("onAttackBlock"),
    PlayerUseItemOn("onUseItemOn"),
    PlayerUseItem("onUseItem"),
    PlayerOnUseBucketPlace("onUseBucketPlace"),
    PlayerOnUseBucketTake("onUseBucketTake"),
    PlayerOnTakeItem("onTakeItem"),
    PlayerOnDropItem("onDropItem"),
    PlayerOnAte("onAte"),
    ;

    private static List<String> list;

    private String event;

    ListenEvent(String event) {
        this.event = event;
    }


    @Override
    public String toString() {
        return event;
    }

    public String getValue() {
        return event;
    }

    public static List<String> getAllEvents() {
        if (list == null) {
            list = Arrays.stream(values())
                    .map(ListenEvent::getValue)
                    .collect(Collectors.toList());
        }
        return list;
    }
}
