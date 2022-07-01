package com.gestankbratwurst.bungeecallbacks;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class DelegatePluginMessageListener implements PluginMessageListener {

  private final PluginMessageBroker broker;

  public DelegatePluginMessageListener(PluginMessageBroker broker) {
    this.broker = broker;
  }

  @Override
  public void onPluginMessageReceived(String channel, @NotNull Player player, byte[] message) {
    if(!channel.equals("BungeeCord")) {
      return;
    }
    ByteArrayDataInput in = ByteStreams.newDataInput(message);
    String subChannel = in.readUTF();
    broker.consume(subChannel, player, message);
  }
}
