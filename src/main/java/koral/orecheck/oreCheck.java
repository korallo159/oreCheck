package koral.orecheck;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

import static org.bukkit.Material.DIAMOND_ORE;

public final class oreCheck extends JavaPlugin implements Listener, CommandExecutor {
    HashMap<Player, Integer > message;

    public oreCheck() {
        this.message = new HashMap<Player, Integer>();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("orecheck");
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(player.hasPermission("orecheck.admin"))
            {
                this.message.put(player, 1);
            }
            else
            {
                this.message.put(player, 0);
            }
        }

    }

    @Override
    public void onDisable() {
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if(p.hasPermission("orecheck.admin"))
        {
            this.message.put(p, 1);
            p.sendMessage(ChatColor.RED + "Wyswietlanie wykopanych rud"  + " diamentu" + ChatColor.GREEN + " włączone");
            p.sendMessage(ChatColor.YELLOW + "/orecheck on | off" + ChatColor.RED +" aby manipulować wyświetlaniem wiadomości.");
        }

    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (e.getBlock().getType().equals(DIAMOND_ORE)) {
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                if (this.message.get(all) == 1) {
                    {
                        all.sendMessage(ChatColor.RED + p.getDisplayName() + ChatColor.GRAY + " wykopał rude" + ChatColor.AQUA + " diamentu");
                    }
                }
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (label.equalsIgnoreCase("orecheck")) {
                if (args.length == 0) {

                    player.sendMessage(ChatColor.RED + "Użycie: /orecheck on | off");
                    return true;

                }
                if (args[0].equalsIgnoreCase("on") && this.message.get(player) == 1) {
                    player.sendMessage(ChatColor.GREEN + "Wysyłanie powiadomień jest juz włączone");
                    return true;
                }

                if (args[0].equalsIgnoreCase("on") && this.message.get(player) == 0) {
                    this.message.put(player, 1);
                    player.sendMessage(ChatColor.GREEN +"Włączyłeś wysyłanie powiadomień");
                    return true;
                }
                if (args[0].equalsIgnoreCase("off") && this.message.get(player) == 0)
                {
                    player.sendMessage(ChatColor.RED +"Wysylanie powiadomien jest juz wylaczone!");
                    return true;
                }

                if (args[0].equalsIgnoreCase("off") && this.message.get(player) == 1)
                {
                    this.message.put(player, 0);
                    player.sendMessage(ChatColor.RED +"Wylączyłes wysyłanie powiadomień");
                    return true;
                }



            }
        }
        return true;
    }







}
