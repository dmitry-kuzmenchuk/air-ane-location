package ru.menu4me.extensions.location;

public class ServerResult {
    public int result = 0;
    public String resultCode = null;
    public ServerResultData data = null;

    public ServerResult() {
        data = new ServerResultData();
    }
}
