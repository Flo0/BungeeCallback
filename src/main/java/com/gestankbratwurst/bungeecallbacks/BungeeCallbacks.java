package com.gestankbratwurst.bungeecallbacks;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeCallbacks extends JavaPlugin {

  private final PluginMessageBroker pluginMessageBroker = new PluginMessageBroker(this);

  @Override
  public void onEnable() {
    DelegatePluginMessageListener listener = new DelegatePluginMessageListener(pluginMessageBroker);
    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", listener);

    BukkitCommandManager commandManager = new BukkitCommandManager(this);
    commandManager.registerCommand(new ExampleCommand(pluginMessageBroker));
  }

}
