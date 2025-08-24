package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.utils.LoginChainData;
import org.graalvm.polyglot.HostAccess;

public class Device {

    @HostAccess.Export
    public final String ip;

    @HostAccess.Export
    public final int ping = 0;

    @HostAccess.Export
    public final float avgPacketLoss = 0f;

    @HostAccess.Export
    public final int lastPing = 0;

    @HostAccess.Export
    public final float lastPacketLoss = 0f;

    @HostAccess.Export
    public final String os;

    @HostAccess.Export
    public final int inputMode;

    @HostAccess.Export
    public final String serverAddress;

    @HostAccess.Export
    public final String clientId;

    public Device(Player player) {
        LoginChainData data = player.getLoginChainData();
        this.ip = player.getAddress();
        this.os = getOS(data.getDeviceOS());
        this.inputMode = data.getCurrentInputMode();
        this.serverAddress = data.getServerAddress();
        this.clientId = data.getDeviceId();
    }

    public String getOS(int i) {
        switch (i) {
            case 1: return "Android";
            case 2: return "iOS";
            case 3: return "OSX";
            case 4: return "Amazon";
            case 5: return "GearVR";
            case 6: return "Hololens";
            case 7: return "Windows10";
            case 8: return "Win32";
            case 9: return "Dedicated";
            case 10: return "TVOS";
            case 11: return "PlayStation";
            case 12: return "Nintendo";
            case 13: return "Xbox";
            case 14: return "WindowsPhone";
            default: return "Unknown";
        }
    }

}
