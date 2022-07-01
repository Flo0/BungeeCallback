package com.gestankbratwurst.bungeecallbacks;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

@SuppressWarnings("UnstableApiUsage")
@CommandAlias("bungeerequest | brq")
public class ExampleCommand extends BaseCommand {

  private final PluginMessageBroker pluginMessageBroker;

  public ExampleCommand(PluginMessageBroker pluginMessageBroker) {
    this.pluginMessageBroker = pluginMessageBroker;
  }

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

}
