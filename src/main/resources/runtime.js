print = null;
file = File;
Object.freeze(ll);
Object.freeze(mc);
const Format = Object.freeze({
    Black: "§0",
    DarkBlue: "§1",
    DarkGreen: "§2",
    DarkAqua: "§3",
    DarkRed: "§4",
    DarkPurple: "§5",
    Gold: "§6",
    Gray: "§7",
    DarkGray: "§8",
    Blue: "§9",
    Green: "§a",
    Aqua: "§b",
    Red: "§c",
    LightPurple: "§d",
    Yellow: "§e",
    White: "§f",
    MinecoinGold: "§g",
    Bold: "§l",
    Italics: "§o",
    Underline: "§n",
    StrikeThrough: "§m",
    Random: "§r",
    Clear: "§r",
});
const PermType = Object.freeze({
    Any: 0,
    GameMasters: 1,
    Console: 2
});
const ParamType = Object.freeze({
    Bool: 0,
    Int: 1,
    Float: 2,
    String: 3,
    Actor: 4,
    Player: 5,
    BlockPos: 6,
    Vec3: 7,
    RawText: 8,
    Message: 9,
    JsonValue: 10,
    Item: 11,
    Block: 12,
    Effect: 13,
    Enum: 14,
    SoftEnum: 15,
    ActorType: 16,
    GameMode: 17,
    Command: 18,
});