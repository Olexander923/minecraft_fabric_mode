package shadrin.dev;

import net.fabricmc.api.ModInitializer;
import shadrin.dev.network.ModPackets;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("[MYMOD] Initializing mod on SERVER side");
        ModPackets.register();
        System.out.println("[MYMOD] Packets registered");
    }
}