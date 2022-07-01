# BungeeCallback
Example implementation for callbacks throught the plugin messaging channel.

For details visit: [Spigot article](https://www.spigotmc.org/threads/bungeecord-messaging-with-callbacks.562809/)

```java
  @Subcommand("server")
  public void onCommand(Player sender) {
    sender.sendMessage("§7Requesting server list...");
    pluginMessageBroker.request(sender, "GetServers", (rec, msg) -> {
      ByteArrayDataInput in = ByteStreams.newDataInput(msg);
      in.readUTF();
      String[] serverList = in.readUTF().split(", ");
      sender.sendMessage("§6Servers: §7[§f" + serverList.length + "§7]");
      for (String serverName : serverList) {
        sender.sendMessage("§7- §f" + serverName);
      }
    });
  }
```

![screen-gif](https://media2.giphy.com/media/lpfTjSJJRJkXShsH1Y/giphy.gif?cid=790b7611b1517b8ce82325f8b043b6b432a38f313073ada3&rid=giphy.gif&ct=g)
