package com.gestankbratwurst.bungeecallbacks;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessageBroker {

  private final Map<String, Deque<BiConsumer<Player, byte[]>>> callbacks = new HashMap<>();
  private final JavaPlugin plugin;

  public PluginMessageBroker(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  public void request(Player target, String bungeeSubChannel, BiConsumer<Player, byte[]> callback, String... arguments) {
    callbacks.computeIfAbsent(bungeeSubChannel, key -> new ArrayDeque<>()).add(callback);
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(bungeeSubChannel);
    for(String arg : arguments) {
      out.writeUTF(arg);
    }
    target.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
  }

  protected void consume(String bungeeSubChannel, Player receiver, byte[] message) {
    Deque<BiConsumer<Player, byte[]>> callbackQueue = callbacks.computeIfAbsent(bungeeSubChannel, key -> new ArrayDeque<>());
    if (!callbackQueue.isEmpty()) {
      callbackQueue.poll().accept(receiver, message);
    }
  }

}